package edu.usc.NLP_finalproject;

public class Word {

	public Word(String text, Enums.POS pos, int index) {
		this.text = text;
		this.pos = pos;
		this.index = index;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setPOS(Enums.POS pos) {
		this.pos = pos;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public String getText() {
		return text;
	}

	public Enums.POS getPOS() {
		return pos;
	}

	private String text;
	private Enums.POS pos;
	private int index;
}
