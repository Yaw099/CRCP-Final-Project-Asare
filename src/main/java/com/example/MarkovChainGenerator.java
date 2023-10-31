//By David Asare October 12
package com.example;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.example.MarkovChainGenerator.SortTTOutput;

public class MarkovChainGenerator<E> extends ProbabilityGenerator<E> {

	ArrayList<ArrayList<Float>> transitionTable = new ArrayList<ArrayList<Float>>();
	ProbabilityGenerator<E> probGen = new ProbabilityGenerator<E>();

	public void train(ArrayList<E> data) {
		probGen.train(data);
		// transitionTable.add(tokenCounts);
		// transitionTable.get(0).get(0);
		int lastIndex = -1;
		if (tokenCounts == null) {
			tokenCounts = new ArrayList<Float>();
			transitionTable = new ArrayList<ArrayList<Float>>();
		}

		for (int i = 0; i < data.size(); ++i) {
			int tokenIndex = alphabet.indexOf(data.get(i));// the index of the token in the alphabet
			if (tokenIndex == -1) {// if the current token is not found in the alphabet
				tokenIndex = alphabet.size();// size of alphabet
				ArrayList<Float> row = new ArrayList<>();// create a new array that is the size of the alphabet
				for (int j = 0; j < alphabet.size(); ++j) {
					row.add(0.f);
				}
				transitionTable.add(row);// add to the transition table
				for (int j = 0; j < transitionTable.size(); ++j) {// add a 0 on to all of the arrays in the transition
																	// table.
					transitionTable.get(j).add(0.f);// for each array in the transition
				}
				alphabet.add(data.get(i));// add the token to the alphabet array
				tokenCounts.add(0.f);
			}
			// add counts to the transition table
			if (lastIndex > -1) {// if this is not the first iteration
				float hold = transitionTable.get(lastIndex).get(tokenIndex);
				transitionTable.get(lastIndex).set(tokenIndex, hold + 1.f);// Add 1 to the specific
				tokenCounts.set(lastIndex, tokenCounts.get(lastIndex) + 1.f);// add 0 to tokenCounts
			}
			lastIndex = tokenIndex; // setting current to previous for next round
		}
	}

	public ArrayList<E> generate(int count) {
		return generate(probGen.generate(), count)	;
	}

	public ArrayList<E> generate(E initToken, int count) {
		// double val;//individual values of the probability distribution
		// float rTest;//random value 0-1
		E genToken;
		ArrayList<E> listOfNotes = new ArrayList<>();// what will be returned
		listOfNotes.add(initToken);
		for(int i = 0; i < count; ++i){
			genToken = generate(initToken);
			listOfNotes.add(genToken);
			initToken = genToken;
		}
		// initToken = super.generate();
		// for(int j = 1; j < x; j++){

		// rTest = (float) Math.random();//randomization
		// double target = 0;//what the random value will be tested against

		// for(int i = 0; i < tokenCounts.size(); i++){
		// val = tokenCounts.get(i) / tokenCount;//normalization
		// target += val;//increasing the size of target for each index

		// if(rTest < target){//if random value is within the target range
		// listOfNotes.add(alphabet.get(i));//add that to the arraylist we will return
		// break;//exit the loop
		// }
		// }
		// }
		return listOfNotes;
	}

	public E generate(E initToken) {

		ArrayList<Float> row = transitionTable.get(alphabet.indexOf(initToken));
		tokenCounts = row;
		float sum = listSum(row);
		// System.out.print(sum + "   ");
		if (sum == 0.f) {
			return probGen.generate();
		}
		return super.generate(sum);
	}

	// nested convenience class to return two arrays from sortTransitionTable()
	// method
	// students do not need to use this class
	protected class SortTTOutput {
		public ArrayList<E> symbolsListSorted;
		ArrayList<ArrayList<Float>> ttSorted;
	}

	// sort the symbols list and the counts list, so that we can easily print the
	// probability distribution for testing
	// symbols -- your alphabet or list of symbols (input)
	// tt -- the unsorted transition table (input)
	// symbolsListSorted -- your SORTED alphabet or list of symbols (output)
	// ttSorted -- the transition table that changes reflecting the symbols sorting
	// to remain accurate (output)
	public SortTTOutput sortTT(ArrayList<E> symbols, ArrayList<ArrayList<Float>> tt) {

		SortTTOutput sortArraysOutput = new SortTTOutput();

		sortArraysOutput.symbolsListSorted = new ArrayList<E>(symbols);
		sortArraysOutput.ttSorted = new ArrayList<ArrayList<Float>>();

		// sort the symbols list
		Collections.sort(sortArraysOutput.symbolsListSorted, new Comparator<E>() {
			@Override
			public int compare(E o1, E o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});

		// use the current sorted list to reference the counts and get the sorted counts
		for (int i = 0; i < sortArraysOutput.symbolsListSorted.size(); i++) {
			int index = symbols.indexOf(sortArraysOutput.symbolsListSorted.get(i));
			sortArraysOutput.ttSorted.add(new ArrayList<Float>());
			for (int j = 0; j < tt.get(index).size(); j++) {
				int index2 = symbols.indexOf(sortArraysOutput.symbolsListSorted.get(j));
				sortArraysOutput.ttSorted.get(i).add(tt.get(index).get(index2));
			}
		}

		return sortArraysOutput;

	}

	public float listSum(ArrayList<Float> list) {
		float sum = 0.f;
		for (int i = 0; i < list.size(); ++i) {
			sum += list.get(i);
		}
		return sum;
	}

	double normalize(ArrayList<E> symbols, ArrayList<ArrayList<Float>> tt) {
		double result;
		double data;
		double sum;
		// sort the transition table
		SortTTOutput sorted = sortTT(symbols, tt);
		symbols = sorted.symbolsListSorted;
		tt = sorted.ttSorted;

		for (int i = 0; i < tt.size(); i++) {
			for (int j = 0; j < tt.get(i).size(); j++) {
				data = tt.get(i).get(j);
				sum = listSum(tt.get(i));
				result = data / sum;
				return result;
			}
		}
		return 0.0;
	}

	// this prints the transition table
	// symbols - the alphabet or list of symbols found in the data
	// tt -- the transition table of probabilities (not COUNTS!) for each symbol
	// coming after another
	public void printProbabilityDistribution(boolean round, ArrayList<E> symbols, ArrayList<ArrayList<Float>> tt) {
		// sort the transition table
		SortTTOutput sorted = sortTT(symbols, tt);
		symbols = sorted.symbolsListSorted;
		tt = sorted.ttSorted;

		System.out.println("-----Transition Table -----");

		System.out.println(symbols);
		// double division = normalize(symbols, tt);
		for (int i = 0; i < tt.size(); i++) {
			System.out.print("[" + symbols.get(i) + "] ");
			for (int j = 0; j < tt.get(i).size(); j++) {
				// normalization
				double data = tt.get(i).get(j);
				double sum = listSum(tt.get(i));
				if(sum == 0){sum =1;}
				double division = data / sum;
				if (round) {
					DecimalFormat df = new DecimalFormat("#.##");
					// System.out.print(division);
					System.out.print(df.format(division) + " ");
				} // (double)tt.get(i).get(j)
				else {
					System.out.print(division + " ");
				}

			}
			System.out.println();

		}
		System.out.println();

		System.out.println("------------");
	}

	public void printProbabilityDistribution(boolean round) {
		printProbabilityDistribution(round, alphabet, transitionTable);
	}
}
