package edu.usc.NLP_finalproject;

public class Enums {

    public enum POS {
        VERB,
        NOUN,
        ADJ,
        ADV,
        PRON,
        DET,
        PREP,
        PARTICLE,
        INF_MAKER,
        WH_ADVERB,
        EX,
        WDT
    }

    public static boolean isV(POS p){
        return p.equals(POS.VERB);
    }

    public static boolean isW(POS p){
        return p.equals(POS.NOUN) || p.equals(POS.ADV) || p.equals(POS.ADJ) || p.equals(POS.PRON) || p.equals(POS.DET);
    }

    public static boolean isP(POS p){
        return p.equals(POS.PREP) || p.equals(POS.PARTICLE) || p.equals(POS.INF_MAKER);
    }

    public static boolean isExcluded(POS p){
        return p.equals(POS.EX) || p.equals(POS.WH_ADVERB) || p.equals(POS.WDT);
    }

    public static POS getPosEnum(String pos){   //based on PennPOS
        switch(pos){
            case "VB" :
            case "VBD" :
            case "VBG" :
            case "VBN" :
            case "VBP" :
            case "VBZ" :
                return POS.VERB;
            case "NN" :
            case "NNS" :
            case "NNP" :
            case "NNPS" :
                return POS.NOUN;
            case "JJ" :
            case "JJR" :
            case "JJS" :
                return POS.ADJ;
            case "RB" :
            case "RBR" :
            case "RBS" :
                return POS.ADV;
            case "PRP" :
            case "PRP$" :
                return POS.PRON;
            case "DT" :
                return POS.DET;
            case "IN" :
                return POS.PREP;
            case "RP" :
                return POS.PARTICLE;
            case "TO" :
                return POS.INF_MAKER;
            case "EX" :
                return POS.EX;
            case "WRB" :
                return POS.WH_ADVERB;
            case "WDT":
                return POS.WDT;
            default:
                return POS.NOUN;
        }
    }
}
