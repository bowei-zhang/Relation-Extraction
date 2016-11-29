package edu.usc.NLP_finalproject;

import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

public class POSTagger {

	public static String getTaggedPOSStr(String input, PerformanceMonitor perfMon, POSTaggerME tagger){
		String ret = new String();
		
		String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(input);
		String[] tags = tagger.tag(whitespaceTokenizerLine);
		POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
		ret = sample.toString();
		
		return ret;
	}
}
