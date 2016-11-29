package edu.usc.NLP_finalproject;

import java.util.*;

public class Sentence {
	public Sentence(String taggedStr) {
		String[] parts = taggedStr.split(" ");
		for (int i = 0; i < parts.length; i++) {
			String[] textToPos = parts[i].split("_");
			sentence.add(new Word(textToPos[0], Enums.getPosEnum(textToPos[1]), i));
		}
	}

	public Sentence(List<Word> listOfWords) {
		sentence = listOfWords;
	}

	public List<Word> getSentence() {
		return sentence;
	}

	public Sentence subSentence(int start, int end) {
		List<Word> subSen = new ArrayList<Word>();
		for (int i = start; i < end; i++) {
			subSen.add(sentence.get(i));
		}
		return new Sentence(subSen);
	}

	public void printSentence() {
		for (Word w : sentence) {
			System.out.print(w.getText() + " ");
		}
		System.out.println();
	}

	public String toString() {
		String ret = "";
		for (Word w : sentence) {
			ret = ret + w.getText() + " ";
		}
		return ret.substring(0, ret.length() - 1);
	}

	private List<Word> sentence = new ArrayList<Word>();

}
