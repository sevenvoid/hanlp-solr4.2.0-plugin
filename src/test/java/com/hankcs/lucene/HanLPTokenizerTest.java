package com.hankcs.lucene;

import java.io.StringReader;
import java.util.List;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import core.HanLP;
import core.dictionary.py.Pinyin;
import junit.framework.TestCase;

public class HanLPTokenizerTest extends TestCase
{
//    Tokenizer tokenizer;
//
//    @Override
//    public void setUp() throws Exception
//    {
//    	StringReader reader = new StringReader("林志玲亮相网友:确定不是波多野结衣？");
//        tokenizer = new HanLPTokenizer(reader, HanLP.newSegment().enableJapaneseNameRecognize(true).enableIndexMode(true), null, false);
//        tokenizer.reset();
//    }
//
//    public void testIncrementToken() throws Exception
//    {
//        while (tokenizer.incrementToken())
//        {
//            CharTermAttribute attribute = tokenizer.getAttribute(CharTermAttribute.class);
//            // 偏移量
//            OffsetAttribute offsetAtt = tokenizer.getAttribute(OffsetAttribute.class);
//            // 距离
//            PositionIncrementAttribute positionAttr = tokenizer.getAttribute(PositionIncrementAttribute.class);
//            // 词性
//            TypeAttribute typeAttr = tokenizer.getAttribute(TypeAttribute.class);
//            System.out.printf("%s \n",attribute);
////            System.out.printf("[%d:%d %d] %s/%s\n", offsetAtt.startOffset(), offsetAtt.endOffset(), positionAttr.getPositionIncrement(), attribute, typeAttr.type());
//        }
//    }
    
    public void testPinyin() {
    	 String text = "重载不是重任";
         List<Pinyin> pinyinList = HanLP.convertToPinyinList(text);
         System.out.print("原文,");
         for (char c : text.toCharArray())
         {
             System.out.printf("%c,", c);
         }
         System.out.println();

         System.out.print("拼音（数字音调）,");
         for (Pinyin pinyin : pinyinList)
         {
             System.out.printf("%s,", pinyin);
         }
         System.out.println();

         System.out.print("拼音（符号音调）,");
         for (Pinyin pinyin : pinyinList)
         {
             System.out.printf("%s,", pinyin.getPinyinWithToneMark());
         }
         System.out.println();

         System.out.print("拼音（无音调）,");
         for (Pinyin pinyin : pinyinList)
         {
             System.out.printf("%s,", pinyin.getPinyinWithoutTone());
         }
         System.out.println();

         System.out.print("声调,");
         for (Pinyin pinyin : pinyinList)
         {
             System.out.printf("%s,", pinyin.getTone());
         }
         System.out.println();

         System.out.print("声母,");
         for (Pinyin pinyin : pinyinList)
         {
             System.out.printf("%s,", pinyin.getShengmu());
         }
         System.out.println();

         System.out.print("韵母,");
         for (Pinyin pinyin : pinyinList)
         {
             System.out.printf("%s,", pinyin.getYunmu());
         }
         System.out.println();

         System.out.print("输入法头,");
         for (Pinyin pinyin : pinyinList)
         {
             System.out.printf("%s,", pinyin.getHead());
         }
         System.out.println();
    }
}