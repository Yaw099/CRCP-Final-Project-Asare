/*
 * c2017-2023 Courtney Brown (NOTE: you'll have to change the name and give me a bit of credit!)
 * -modified by Yaw Asare
 * Class: ProbabliityGenerator
 * 
 */


package com.example;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProbabilityGenerator <E>
{
	ArrayList<E> alphabet = new ArrayList<E>();
	ArrayList<Float> tokenCounts = new ArrayList<Float>();
	double tokenCount = 0;

	//trains a probability distribution from data
	public void train(ArrayList<E> data) {
	
		for(int i = 0; i < data.size(); i++){
			int index = alphabet.indexOf(data.get(i));//the index of alphabet where a given note is located

			if (index == -1) {//if the index is notfound in the arraylist
				index = alphabet.size();//set to the sixe of alpha, or the last index
				alphabet.add(data.get(i));//add the new note to alphabet
				tokenCounts.add(0.f);//add 0 to tokenCounts
			}
			Float count = tokenCounts.get(index) + 1 ;//increment tokenCounts
			tokenCounts.set(index, count);//set tokenCounts

		}
			tokenCount += data.size();//the total number of tokens

	}

	public E generate(){
		return generate(tokenCount);
	}

	 public E generate(double sum)
	{
		E note = alphabet.get(alphabet.size()-1);
		
		double val;//individual values of the probability distribution
		float rTest;//random value 0-1

			rTest = (float) Math.random();//randomization
			double target = 0;//what the random value will be tested against

			for(int i = 0; i < tokenCounts.size(); i++){
				val = tokenCounts.get(i) / sum;//normalization
				target += val;//increasing the size of target for each index

				if(rTest < target){//if random value is within the target range
					note = alphabet.get(i);//add that to the arraylist we will return
					break;//exit the loop
				}
			}
				
		return note;
	}

	public ArrayList<E> generate(int x) {

		ArrayList<E> listOfNotes = new ArrayList<>();//what will be returned

		for(int i = 1; i < x; ++i)
		{
			listOfNotes.add(generate());
		}
		return listOfNotes;
	}

	//nested convenience class to return two arrays from sortArrays() method
	//students do not need to use this class
	protected class SortArraysOutput
	{
		public ArrayList<E> symbolsListSorted;
		public ArrayList<Float> symbolsCountSorted;
	}

	//sort the symbols list and the counts list, so that we can easily print the probability distribution for testing
	//symbols -- your alphabet or list of symbols (input)
	//counts -- the number of times each symbol occurs (input)
	//symbolsListSorted -- your SORTED alphabet or list of symbols (output)
	//symbolsCountSorted -- list of the number of times each symbol occurs inorder of symbolsListSorted  (output)
	public SortArraysOutput sortArrays(ArrayList<E> symbols, ArrayList<Float> counts)	{

		SortArraysOutput sortArraysOutput = new SortArraysOutput(); 
		
		sortArraysOutput.symbolsListSorted = new ArrayList<E>(symbols);
		sortArraysOutput.symbolsCountSorted = new ArrayList<Float>();
	
		//sort the symbols list
		Collections.sort(sortArraysOutput.symbolsListSorted, new Comparator<E>() {
			@Override
			public int compare(E o1, E o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});

		//use the current sorted list to reference the counts and get the sorted counts
		for(int i=0; i<sortArraysOutput.symbolsListSorted.size(); i++)
		{
			int index = symbols.indexOf(sortArraysOutput.symbolsListSorted.get(i));
			sortArraysOutput.symbolsCountSorted.add(counts.get(index));
		}

		return sortArraysOutput;

	}
	
	//Students should USE this method in your unit tests to print the probability distribution
	//HINT: you can overload this function so that it uses your class variables instead of taking in parameters
	//boolean is FALSE to test train() method & TRUE to test generate() method
	//symbols -- your alphabet or list of symbols (input)
	//counts -- the number of times each symbol occurs (input)
	//sumSymbols -- the count of how many tokens we have encountered (input)
	public void printProbabilityDistribution(boolean round, ArrayList<E> symbols, ArrayList<Float> counts, double sumSymbols)
	{
		//sort the arrays so that elements appear in the same order every time and it is easy to test.
		SortArraysOutput sortResult = sortArrays(symbols, counts);
		ArrayList<E> symbolsListSorted = sortResult.symbolsListSorted;
		ArrayList<Float> symbolsCountSorted = sortResult.symbolsCountSorted;

		System.out.println("-----Probability Distribution-----");
		
		for (int i = 0; i < symbols.size(); i++)
		{
			if (round){
				DecimalFormat df = new DecimalFormat("#.##");
				System.out.println("Data: " + symbolsListSorted.get(i) + " | Probability: " + df.format((double)symbolsCountSorted.get(i) / sumSymbols));
			}
			else
			{
				System.out.println("Data: " + symbolsListSorted.get(i) + " | Probability: " + (double)symbolsCountSorted.get(i) / sumSymbols);
			}
		}
		
		System.out.println("------------");
	}

	public void printProbabilityDistribution(boolean round)
	{
		printProbabilityDistribution(round, alphabet, tokenCounts, tokenCount);
	}
}
