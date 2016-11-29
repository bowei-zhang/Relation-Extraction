package edu.usc.NLP_finalproject;

import opennlp.tools.util.Span;

import java.util.*;

public class Extraction {
    public Extraction(String string){
        //this.originalStr = originalStr;
        this.sentence = new Sentence(string);
        tuples = new ArrayList<Tuple>();
    }

    public List<Sentence> relationExtraction(){
        List<Sentence> ret = new ArrayList<>();
        int lastStart = -2, lastEnd = -2;
        List<Word> listOfWords = sentence.getSentence();
        for(int i=0; i<listOfWords.size(); i++){
            if(!Enums.isV(listOfWords.get(i).getPOS())) continue;
            //we find a verb
            if(i>lastEnd+1){    //no overlap/adjacent
                if(lastStart>=0 && lastEnd>=0)
                    ret.add(new Sentence(listOfWords.subList(lastStart,lastEnd)));
                lastStart = i;
            }
            int j = i+1;
            boolean hasW = false;
            while(j<listOfWords.size()){
                if(Enums.isW(listOfWords.get(j).getPOS())){
                    hasW = true;
                }else break;   // end of W*
                j++;
            }
            if(j>=listOfWords.size()){
                lastEnd = i+1;
                break;
            }
            if(hasW){
                if(j<listOfWords.size() && Enums.isP(listOfWords.get(j).getPOS()) ){    //VW*P
                    lastEnd = j+1;
                }else
                    lastEnd = i+1;
            }else if(i+1<listOfWords.size() && Enums.isP(listOfWords.get(i+1).getPOS())){     //VP
                lastEnd = ++i + 1;
            }else{      //V
                lastEnd = i+1;
            }
        }
        if(lastStart>=0 && lastEnd>=0)  ret.add(new Sentence(listOfWords.subList(lastStart,lastEnd)));
        return ret;
    }

    public void argumentExtraction(List<Span> npList, List<Sentence> relations){
        for(Sentence r : relations){
            int beginIndex = r.getSentence().get(0).getIndex();  // index of first word
            int endIndex = r.getSentence().get(r.getSentence().size()-1).getIndex();    //index of last word
            // find the np before the relation
            Span beforeNp = null, afterNp = null;
            for(int i=npList.size()-1; i>=0; i--){
                if(npList.get(i).getEnd()<=beginIndex){
                    if((npList.get(i).getEnd() - npList.get(i).getStart() == 1 && Enums.isExcluded(sentence.getSentence().get(npList.get(i).getStart()).getPOS())))
                        continue;
                    if(npList.get(i).getStart()>0 && Enums.isP(sentence.getSentence().get(npList.get(i).getStart()-1).getPOS()))	// She clung to him for hours trying to fight the tiredness that was overtaklng her.
                    	continue;
                    if(isInRelation(relations,npList.get(i))) continue;	// I stayed a night in the hostel there, renewing my friendship with the managers.
                    beforeNp = npList.get(i);
                    break;
                }
            }
            for(int i=0; i<npList.size(); i++){
                if(npList.get(i).getStart()>endIndex){
                    if((npList.get(i).getEnd() - npList.get(i).getStart() == 1 && Enums.isExcluded(sentence.getSentence().get(npList.get(i).getStart()).getPOS())))
                        continue;
                    afterNp = npList.get(i);
                    break;
                }
            }
            if(beforeNp!=null && afterNp!=null){
                tuples.add(new Tuple(sentence.subSentence(beforeNp.getStart(),beforeNp.getEnd()).toString(),r.toString(),sentence.subSentence(afterNp.getStart(),afterNp.getEnd()).toString()));
            }
        }
    }
    
    private boolean isInRelation(List<Sentence> relations, Span np){
    	for(Sentence r:relations){
    		if(r.getSentence().get(0).getIndex()<=np.getStart() && r.getSentence().get(r.getSentence().size()-1).getIndex()>=np.getEnd()) return true;
    	}
    	return false;
    }

    // return the extraction result
    public List<Tuple> getTuples(){
        return tuples;
    }

    public String getTuple(){
    	String str = "";
        for(Tuple t : tuples){
        	str = str + "( " + t.x + ", " + t.r + ", " + t.y + " )" + "\n";
            //System.out.println("( " + t.x + ", " + t.r + ", " + t.y + " )");
        }
        
        return str;
    }

    //private String originalStr;
    private Sentence sentence;
    private List<Tuple> tuples;

    public class Tuple{
        public Tuple(String x,String r,String y) {
            this.x = x;
            this.y = y;
            this.r = r;
        }

        private String x;
        private String r;
        private String y;
    }
}

