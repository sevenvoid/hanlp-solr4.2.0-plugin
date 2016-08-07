package com.hankcs.lucene;

import java.io.StringReader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import com.hankcs.hanlp.HanLP;

import junit.framework.TestCase;

public class HanLPTokenizerTest extends TestCase
{
    Tokenizer tokenizer;

    @Override
    public void setUp() throws Exception
    {
    	StringReader reader = new StringReader("林志玲亮相网友:确定不是波多野结衣？");
        tokenizer = new HanLPTokenizer(reader, HanLP.newSegment().enableJapaneseNameRecognize(true).enableIndexMode(true), null, false);
        tokenizer.reset();
    }

    public void testIncrementToken() throws Exception
    {
        while (tokenizer.incrementToken())
        {
            CharTermAttribute attribute = tokenizer.getAttribute(CharTermAttribute.class);
            // 偏移量
            OffsetAttribute offsetAtt = tokenizer.getAttribute(OffsetAttribute.class);
            // 距离
            PositionIncrementAttribute positionAttr = tokenizer.getAttribute(PositionIncrementAttribute.class);
            // 词性
            TypeAttribute typeAttr = tokenizer.getAttribute(TypeAttribute.class);
            System.out.printf("%s \n",attribute);
//            System.out.printf("[%d:%d %d] %s/%s\n", offsetAtt.startOffset(), offsetAtt.endOffset(), positionAttr.getPositionIncrement(), attribute, typeAttr.type());
        }
    }
}