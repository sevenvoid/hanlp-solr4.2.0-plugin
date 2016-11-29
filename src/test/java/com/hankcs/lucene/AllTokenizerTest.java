/**
 * 
 */
package com.hankcs.lucene;

import java.util.List;

import core.seg.Segment;
import core.seg.CRF.CRFSegment;
import core.seg.Dijkstra.DijkstraSegment;
import core.seg.NShort.NShortSegment;
import core.seg.common.Term;
import core.tokenizer.NLPTokenizer;
import core.tokenizer.SpeedTokenizer;
import junit.framework.TestCase;

/**
 * @author luope
 *
 */
public class AllTokenizerTest extends TestCase{

	/**
	 * @param args
	 */
	public void testN(){
		Segment nShortSegment = new NShortSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
		Segment shortestSegment = new DijkstraSegment().enableCustomDictionary(false).enablePlaceRecognize(false).enableOrganizationRecognize(false);
		String[] testCase = new String[]{
		       
		        "江西鄱阳湖干枯，中国最大淡水湖变成大草原",
		        };
		for (String sentence : testCase)
		{
		    System.out.println("N-最短分词：" + nShortSegment.seg(sentence) + "\n最短路分词：" + shortestSegment.seg(sentence));
		}

	}
	
	public void testHighSpeedSegment() {
		 String text = "江西鄱阳湖干枯，中国最大淡水湖变成大草原";
	        System.out.println(SpeedTokenizer.segment(text));
	        long start = System.currentTimeMillis();
	        int pressure = 1000000;
	        for (int i = 0; i < pressure; ++i)
	        {
	            SpeedTokenizer.segment(text);
	        }
	        double costTime = (System.currentTimeMillis() - start) / (double)1000;
	        System.out.printf("分词速度：%.2f字每秒", text.length() * pressure / costTime);
	}
	
	public void testCRFSegment() {
		Segment segment = new CRFSegment();
		segment.enablePartOfSpeechTagging(false);
		List<Term> termList = segment.seg("你看过穆赫兰道吗");
		System.out.println(termList);
		for (Term term : termList)
		{
		    if (term.nature == null)
		    {
		        System.out.println("识别到新词：" + term.word);
		    }
		}
	}
	
	public void testNLPSegment() {
		List<Term> termList = NLPTokenizer.segment("宝宝的经纪人睡了宝宝的宝宝，宝宝不知道宝宝的宝宝是不是宝宝亲生的宝宝，宝宝的宝宝为什么要这样对待宝宝！宝宝真的很难过！宝宝现在最担心的是宝宝的宝宝是不是宝宝的宝宝，如果宝宝的宝宝不是宝宝的宝宝那真的吓死宝宝了");
		System.out.println(termList);
	}
}
