package edu.usc.NLP_finalproject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.Span;

//extract noun phrases from a single sentence using OpenNLP
public class Chunker {
    List<Span> npList;

    public Chunker(String sentence, POSTaggerME tagger) {
        npList = new ArrayList<Span>();

        try {
            String whitespaceTokenizerLine[] = null;

            String[] tags = null;
            whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE.tokenize(sentence);
            tags = tagger.tag(whitespaceTokenizerLine);

            // chunk
            InputStream is = new FileInputStream("en-chunker.bin");
            ChunkerModel cModel = new ChunkerModel(is);
            ChunkerME chunkerME = new ChunkerME(cModel);

            Span[] span = chunkerME.chunkAsSpans(whitespaceTokenizerLine, tags);
            for (Span s : span) {
                if (s.getType().equals("NP")) {
                    npList.add(s);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //System.out.println(npList);
    }

    public List<Span> getNpList() {
        return npList;
    }
}
