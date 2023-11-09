package com;

import com.example.MarkovChainGenerator;
import com.example.MelodyPlayer;
import com.example.MidiFileToNotes;
import com.example.ProbabilityGenerator;

public class MOrderMarkovGeneratorUnitTest {
    
    static MelodyPlayer player; // play a midi sequence
    MidiFileToNotes midiNotes;

        MarkovChainGenerator<Integer> pitch2 = new MarkovChainGenerator<Integer>();
        MarkovChainGenerator<Double> rhythm2 = new MarkovChainGenerator<Double>();    
    

    MOrderMarkovGeneratorUnitTest(MidiFileToNotes midiNotes){
    this.midiNotes = midiNotes;
    }
        ProbabilityGenerator<Integer> pitchGen = new ProbabilityGenerator<Integer>();
	    ProbabilityGenerator<Double> rhythmGen = new ProbabilityGenerator<Double>();

        ProbabilityGenerator<Integer> pitchGen2 = new ProbabilityGenerator<Integer>();
		ProbabilityGenerator<Double> rhythmGen2 = new ProbabilityGenerator<Double>();
}
