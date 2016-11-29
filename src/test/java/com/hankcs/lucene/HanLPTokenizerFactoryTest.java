package com.hankcs.lucene;

import junit.framework.TestCase;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.analysis.util.TokenizerFactory;

import java.io.File;
import java.io.StringReader;
import java.util.Map;
import java.util.TreeMap;

public class HanLPTokenizerFactoryTest extends TestCase
{

    public void testCreate() throws Exception
    {
    	
//    	StringBuilder sbInfo = new StringBuilder("========Tips========\n请将HanLP.properties放在下列目录：\n"); // 打印一些友好的tips
//        String classPath = (String) System.getProperties().get("java.class.path");
//        if (classPath != null)
//        {
//            for (String path : classPath.split(";"))
//            {
//                if (new File(path).isDirectory())
//                {
//                    sbInfo.append(path).append('\n');
//                }
//            }
//        }
//        sbInfo.append("Web项目则请放到下列目录：\n" +
//                              "Webapp/WEB-INF/lib\n" +
//                              "Webapp/WEB-INF/classes\n" +
//                              "Appserver/lib\n" +
//                              "JRE/lib\n");
//        sbInfo.append("并且编辑root=PARENT/path/to/your/data\n");
//        sbInfo.append("现在HanLP将尝试从").append(System.getProperties().get("user.dir")).append("读取data……");
//        System.out.println("hanlp.properties，进入portable模式。若需要自定义HanLP，请按下列提示操作：\n" + sbInfo);
        
        Map<String, String> args = new TreeMap<>();
//        args.put("enableTraditionalChineseMode", "true");
        args.put("enableNormalization", "true");
        args.put("enablePlaceRecognize", "true");
        args.put("enableCustomDictionary", "true");
//        args.put("enableDebug", "true");
//        args.put("enableJapaneseNameRecognize", "true");
//        args.put("customDictionaryPath", "dic/CustomDictionary.txt");
        args.put("stopWordDictionaryPath", "F:/Solr/hanlp-solr4.2.0-plugin/data/dictionary/stopwords.txt");
        
        String text = "家中的长辈";
//        String text = "云之家是一个专注企业互联网社交的企业,林志玲亮相网友，你们说的不都是没有道理的，BRT快速公交運營公司，不干不净吃了没病";
        
        StringReader reader = new StringReader(text);
        TokenizerFactory factory = new HanLPTokenizerFactory(args);
        Tokenizer tokenizer = factory.create(reader);
        tokenizer.reset();
        while (tokenizer.incrementToken())
        {
            CharTermAttribute attribute = tokenizer.getAttribute(CharTermAttribute.class);
            System.out.printf("%s\n",attribute);
        }
        tokenizer.close();
        reader.close();
    }
}