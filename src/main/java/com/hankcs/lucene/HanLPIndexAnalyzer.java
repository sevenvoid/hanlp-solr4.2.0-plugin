package com.hankcs.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

import core.HanLP;

import java.io.Reader;
import java.util.Set;

public class HanLPIndexAnalyzer extends Analyzer
{

    private boolean pstemming;
    private Set<String> filter;

    /**
     * @param filter    停用词
     * @param pstemming 是否分析词干
     */
    public HanLPIndexAnalyzer(Set<String> filter, boolean pstemming)
    {
        this.filter = filter;
        this.pstemming = pstemming;
    }

    /**
     * @param pstemming 是否分析词干.进行单复数,时态的转换
     */
    public HanLPIndexAnalyzer(boolean pstemming)
    {
        this.pstemming = pstemming;
    }

    public HanLPIndexAnalyzer()
    {
        super();
    }

	@Override
	protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
		 Tokenizer tokenizer = new HanLPTokenizer(reader, HanLP.newSegment().enableIndexMode(true), filter, pstemming);
	     return new TokenStreamComponents(tokenizer);
	}
}
