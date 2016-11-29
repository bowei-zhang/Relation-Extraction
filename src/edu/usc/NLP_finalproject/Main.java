package edu.usc.NLP_finalproject;

import java.io.*;
import java.util.*;

import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class Main {
	public static void main(String[] args) throws IOException {
		List<String> inputStringList = new ArrayList<String>();

		BufferedReader br = new BufferedReader(new FileReader("sentences.txt"));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				line = line.substring(line.indexOf('.') + 2, line.length());
				inputStringList.add(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
		} finally {
			br.close();
		}

		POSModel model = new POSModelLoader().load(new File("en-pos-maxent.bin"));
		PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
		POSTaggerME tagger = new POSTaggerME(model);

		List<String> finalLog = new ArrayList<String>();
		for (String sentence : inputStringList) {
			System.out.println(sentence);
			finalLog.add(sentence);
			String taggedPOSStr = POSTagger.getTaggedPOSStr(sentence, perfMon, tagger);

			//System.out.println(taggedPOSStr);
			Chunker chunking = new Chunker(sentence, tagger);
			Extraction extr = new Extraction(taggedPOSStr);
			List<Sentence> relations = extr.relationExtraction();

			extr.argumentExtraction(chunking.getNpList(), relations);
			String str = extr.getTuple();
			finalLog.add(str);
			System.out.println(str);
		}
		
		try{
		    PrintWriter writer = new PrintWriter("result.txt", "UTF-8");
		    for (String str: finalLog) {
		    	writer.println(str);
		    }
		    
		    writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
