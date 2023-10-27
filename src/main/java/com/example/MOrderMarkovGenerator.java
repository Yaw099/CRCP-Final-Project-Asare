
//Programmer: Courtney Brown
//Date: 2017-23
//Class: MOrderMarkovGenerator
//Desc: Markov Generator that uses a m-order markov chain, Class Project 3 Template

package com.example;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MOrderMarkovGenerator<E> extends ProbabilityGenerator<E> {

	// nested convenience class to return two arrays from sortTransitionTable()
	// method
	// students do not need to use this class
	protected class SortTTOutputSequences {
		public ArrayList<E> symbolsListSorted;
		public ArrayList<ArrayList<E>> sequencesSorted;
		ArrayList<ArrayList<Float>> ttSorted;
	}

	int M = 0;

	MOrderMarkovGenerator(int order) {
		M = order;
	}

	ArrayList<ArrayList<Float>> transitionTable = new ArrayList<ArrayList<Float>>();
	ArrayList<ArrayList<E>> uniqueAlphabetSequences = new ArrayList<ArrayList<E>>();
	ArrayList<E> curSequence = new ArrayList<E>();

	public void train(ArrayList<E> newTokens) {
		int lastIndex = -1;
		if (tokenCounts == null) {
			tokenCounts = new ArrayList<Float>();
			transitionTable = new ArrayList<ArrayList<Float>>();
		}
		if (lastIndex > -1){
			for (int i = M - 1; i < newTokens.size() - 1; ++i){
				for (int j = 1; j < M; ++j) {// Add sequences into Arraylist
					curSequence.add(newTokens.get(lastIndex));
					uniqueAlphabetSequences.add(curSequence);
				}
			++lastIndex;
		}
		}
	}

	// sort the symbols list and the counts list, so that we can easily print the
	// probability distribution for testing
	// symbols -- your alphabet or list of symbols (input)
	// sequences -- your list of sequences of symbols found (input)
	// tt -- the unsorted transition table (input)
	// symbolsListSorted -- your SORTED alphabet or list of symbols (output)
	// ttSorted -- the transition table that changes reflecting the symbols sorting
	// to remain accurate (output)
	public SortTTOutputSequences sortTTSequences(ArrayList<ArrayList<E>> sequences, ArrayList<E> symbols,
			ArrayList<ArrayList<Float>> tt) {

		SortTTOutputSequences sortArraysOutput = new SortTTOutputSequences();

		sortArraysOutput.symbolsListSorted = new ArrayList<E>(symbols);
		sortArraysOutput.ttSorted = new ArrayList<ArrayList<Float>>();
		sortArraysOutput.sequencesSorted = new ArrayList<ArrayList<E>>(sequences);

		// sort the symbols list
		Collections.sort(sortArraysOutput.symbolsListSorted, new Comparator<E>() {
			@Override
			public int compare(E o1, E o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});

		// sort the sequences list
		Collections.sort(sortArraysOutput.sequencesSorted, new Comparator<ArrayList<E>>() {
			@Override
			public int compare(ArrayList<E> o1, ArrayList<E> o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});

		// use the current sorted lists to reference the counts & sequences and get the
		// sorted counts
		for (int i = 0; i < sortArraysOutput.sequencesSorted.size(); i++) {
			int index = sequences.indexOf(sortArraysOutput.sequencesSorted.get(i));
			sortArraysOutput.ttSorted.add(new ArrayList<Float>());
			for (int j = 0; j < tt.get(index).size(); j++) {
				int index2 = symbols.indexOf(sortArraysOutput.symbolsListSorted.get(j));
				sortArraysOutput.ttSorted.get(i).add(tt.get(index).get(index2));
			}
		}

		return sortArraysOutput;
	}

	// by default, don't round. See below for the other printProbabilityDistribution
	// method functionality
	public void printProbabilityDistribution(ArrayList<ArrayList<E>> sequences, ArrayList<E> symbols,
			ArrayList<ArrayList<Float>> tt, int mOrder) {
		printProbabilityDistribution(false, sequences, symbols, tt, mOrder);
	}

	// this prints the transitionTable
	// round -- whether to round the probabilities to 2 decimal places (input) --
	// Training unit tests should be run with round = false, Generate, with round =
	// true
	// sequences -- your list of sequences of symbols found (input)
	// symbols -- your alphabet or list of symbols (input)
	// tt -- the unsorted transition table -- NOTE!!!: expects **probabilities** NOT
	// counts in the table (input)
	// mOrder -- the order of the markov chain (input)
	public void printProbabilityDistribution(boolean round, ArrayList<ArrayList<E>> sequences, ArrayList<E> symbols,
			ArrayList<ArrayList<Float>> tt, int mOrder) {
		SortTTOutputSequences sortArraysOutput = sortTTSequences(sequences, symbols, tt);
		ArrayList<ArrayList<Float>> ttSorted = sortArraysOutput.ttSorted;
		ArrayList<E> symbolsSorted = sortArraysOutput.symbolsListSorted;
		ArrayList<ArrayList<E>> sequencesSorted = sortArraysOutput.sequencesSorted;

		System.out.println("-----Transition Table for Order " + mOrder + "-----");

		if (sequencesSorted.size() > 0) {
			String sym = sequencesSorted.get(0).toString();
			for (int k = 0; k < sym.length(); k++) {
				System.out.print(" ");
			}
		}

		System.out.println(symbolsSorted);

		for (int i = 0; i < ttSorted.size(); i++) {
			for (int j = 0; j < ((ArrayList) ttSorted.get(i)).size(); j++) {
				double out = ((ArrayList<Float>) ttSorted.get(i)).get(j);
				if (round) {
					DecimalFormat df = new DecimalFormat("#.##");
					System.out.print(df.format(out) + " ");
				} else
					System.out.print(out + " ");

			}
			System.out.println();
		}
		System.out.println();

		System.out.println("------------");
	}

}
