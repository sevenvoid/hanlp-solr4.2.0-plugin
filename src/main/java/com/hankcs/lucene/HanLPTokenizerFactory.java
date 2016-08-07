package com.hankcs.lucene;

import java.io.Reader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.TraditionalChineseTokenizer;

public class HanLPTokenizerFactory extends TokenizerFactory
{
    private boolean enableIndexMode;
    private boolean enablePorterStemming;
    private boolean enableNumberQuantifierRecognize;
    private boolean enableCustomDictionary;
    private boolean enableTranslatedNameRecognize;
    private boolean enableJapaneseNameRecognize;
    private boolean enableOrganizationRecognize;
    private boolean enablePlaceRecognize;
    private boolean enableNameRecognize;
    private boolean enableTraditionalChineseMode;
    private Set<String> stopWordDictionary;
    
    public HanLPTokenizerFactory() {
    }
    
    public HanLPTokenizerFactory(Map<String, String> args) {
    	initValues(args);
    }
    
    /**
     * @param args 通过这个Map保存xml中的配置项
     * 
     */
    @Override
    public void init(Map<String,String> args){
    	initValues(args);
    }
    
    private void initValues(Map<String, String> args) {
    	if(null == args || args.isEmpty()) {
    		return;
    	}
    	 enableIndexMode = getBoolean(args, "enableIndexMode", true);
         enablePorterStemming = getBoolean(args, "enablePorterStemming", false);
         enableNumberQuantifierRecognize = getBoolean(args, "enableNumberQuantifierRecognize", false);
         enableCustomDictionary = getBoolean(args, "enableCustomDictionary", true);
         enableTranslatedNameRecognize = getBoolean(args, "enableTranslatedNameRecognize", false);
         enableJapaneseNameRecognize = getBoolean(args, "enableJapaneseNameRecognize", false);
         enableOrganizationRecognize = getBoolean(args, "enableOrganizationRecognize", false);
         enableNameRecognize = getBoolean(args, "enableNameRecognize", false);
         enablePlaceRecognize = getBoolean(args, "enablePlaceRecognize", false);
         enableTraditionalChineseMode = getBoolean(args, "enableTraditionalChineseMode", false);
         HanLP.Config.Normalization  = getBoolean(args, "enableNormalization", HanLP.Config.Normalization);
         Set<String> customDictionaryPathSet = getSet(args, "customDictionaryPath");
         if (customDictionaryPathSet != null)
         {
             HanLP.Config.CustomDictionaryPath = customDictionaryPathSet.toArray(new String[0]);
         }
         String stopWordDictionaryPath = get(args, "stopWordDictionaryPath");
         if (stopWordDictionaryPath != null)
         {
             stopWordDictionary = new TreeSet<>();
             stopWordDictionary.addAll(IOUtil.readLineListWithLessMemory(stopWordDictionaryPath));
         }
         if (getBoolean(args, "enableDebug", false)) {
           HanLP.Config.enableDebug();
         }
    }

    @Override
    public Tokenizer create(Reader paramReader)
    {
        Segment segment = HanLP.newSegment().enableOffset(true).enableIndexMode(enableIndexMode)
                .enableNameRecognize(enableNameRecognize)
                .enableNumberQuantifierRecognize(enableNumberQuantifierRecognize)
                .enableCustomDictionary(enableCustomDictionary)
                .enableTranslatedNameRecognize(enableTranslatedNameRecognize)
                .enableJapaneseNameRecognize(enableJapaneseNameRecognize)
                .enableOrganizationRecognize(enableOrganizationRecognize)
                .enablePlaceRecognize(enablePlaceRecognize);
        
        if (enableTraditionalChineseMode)
        {
            segment.enableIndexMode(false);
            Segment inner = segment;
            TraditionalChineseTokenizer.SEGMENT = inner;
            segment = new Segment()
            {
                @Override
                protected List<Term> segSentence(char[] sentence)
                {
                    List<Term> termList = TraditionalChineseTokenizer.segment(new String(sentence));
                    return termList;
                }
            };
        }

        return new HanLPTokenizer(paramReader, segment, stopWordDictionary, enablePorterStemming);
    }
    
    private boolean getBoolean(Map<String,String> args, String name, boolean defaultVal) {
        String s = args.remove(name);
        return s == null ? defaultVal : Boolean.parseBoolean(s);
    }
    
    private String get(Map<String,String> args, String name) {
        return args.remove(name); // defaultVal = null
    }
    
    private static final Pattern ITEM_PATTERN = Pattern.compile("[^,\\s]+");
    
    /** Returns whitespace- and/or comma-separated set of values, or null if none are found */
    private Set<String> getSet(Map<String,String> args, String name) {
      String s = args.remove(name);
      if (s == null) {
       return null;
      } else {
        Set<String> set = null;
        Matcher matcher = ITEM_PATTERN.matcher(s);
        if (matcher.find()) {
          set = new HashSet<>();
          set.add(matcher.group(0));
          while (matcher.find()) {
            set.add(matcher.group(0));
          }
        }
        return set;
      }
    }
    
}
