import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class mainframe {
	
	static int pos_loaded = 0;
	static find_n_str_prob[] gram_models;
	static find_n_str_prob unigram;
	
	public static void main(String[] args) throws IOException{
		Scanner sc = new Scanner(System.in);
		Tagger.allFileTags = new LinkedHashSet<ArrayList<String>>();
		try {
			Tagger.tagger = new MaxentTagger("taggers/bidirectional-distsim-wsj-0-18.tagger");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		-------------------------------------------------------------
		
		System.out.println("Has your file already been converted to a pos tag?(y/n)");
		if(sc.nextLine().equalsIgnoreCase("y")){
			System.out.println("Name of pos_tagged file");
			String filename = sc.nextLine();
//			Tagger.loadTagsFromFile(filename);
			Tagger.loadTagsIntoTree(filename);
			System.out.println(Tagger.allFileTags.size());
			System.out.println(Tagger.maxsize);
			pos_loaded = 1;
		}
		
//		-------------------------------------------------------------
		
//		-------------------------------------------------------------
//		Tagger.AddNewTags("You are          fake news." +
//				"It are real book. Hi there! What is this ? Wanna,  bitch." +
//				"Short-skirted hoe. a--;??!!");
//		System.out.println(Tagger.allFileTags.toString());
		
		
//		-------------------------------------------------------------
		ListOfWords._init("commonwords.txt");
		System.out.println("Enter File Name (in same dir as jar):");
		String trainer_filename = sc.nextLine();
		find_n_str_prob.makeFileToString(trainer_filename);
		if(pos_loaded==0){
			System.out.println("File Name for pos tagged file: ");
			String filename = sc.nextLine();
			Tagger.loadTagsToFile(filename);
			Tagger.loadTagsIntoTree(filename);			
		}
		System.out.println("Enter preceding number of words: ");
		int n4ngram = sc.nextInt();
		System.out.println("Enter predictive capability num: ");
		find_n_str_prob.k_num_max = sc.nextInt();
		System.out.println("Enter max num of suggestions: ");
		find_n_str_prob.final_no_of_res = sc.nextInt();
//		System.out.println(find_n_str_prob.k_num);
		n4ngram++;
		gram_models = new find_n_str_prob[n4ngram];
		unigram = new find_n_str_prob(1, trainer_filename);
		find_n_str_prob n_currgram = new find_n_str_prob(n4ngram+1, trainer_filename);
		find_n_str_prob n_prevgram = new find_n_str_prob(n4ngram, trainer_filename, n_currgram.frequency);
//		System.out.println(n_currgram.frequency.toString());
//		System.out.println(n_prevgram.frequency.toString());
//		n_currgram.printSet();
//		System.out.println("Max 1: "+unigram.max_freq_in_model+"\t->"+unigram.maxString);
//		System.out.println("Max 2: "+unigram.max2+"\t->"+unigram.max2string);
		System.out.println("Mean: "+unigram.mean+" Size of vocab: "+unigram.frequency.size());
		
		System.out.println("Enter query string (num words should be equal to "+(n4ngram-1)+"):");
		String query = sc.nextLine();
		query = sc.nextLine();
//		System.out.println("Pehli qyeury: "+query);
		while(!query.equals("exit")){
			System.out.println("Quer: "+query);
//			Pair<String,Integer> ans_pair = n_currgram.predNext(query, n4ngram-1, unigram.getFrequency());
			System.out.println("Max len of newly predicted words in sentence: ");
			int wordlim = sc.nextInt();
			Pair<String,Float> ans_pair = n_currgram.suggestSentence(query, n4ngram-1, unigram.getFrequency(),0,wordlim,n_prevgram,query,null);
			if(ans_pair!=null /*&& !pred_word.equals("<EOS>")*/){
//				System.out.println("here");
				query = query.concat(" "+ans_pair.getEntry1());
//				pred_word = n_currgram.predNext(query, n4ngram-1, unigram.getFrequency());
				System.out.println(query);
				System.out.println(ans_pair.entry2);
			}
			find_n_str_prob.printFinalSet();
			System.out.println("Query Complete.");
			System.out.println("Enter query string (num words should be equal to "+(n4ngram-1)+"):");
			query = sc.nextLine();query = sc.nextLine();
		}
	}
//
//	
//	static void _init(int n, String trainer) throws IOException{
//		int i = 1;
//		for(i=1;i<=n;i++){
//			gram_models[i] = new find_n_str_prob(i+1, trainer);
//		}
//	}
//	
//	static String ngrampred(String s){
////		String 
//		return null;
//	}
	
	
}

//public class mainframe{
//	public static void main(String args[]){
//		String filename = "loltext_pos_2";
//		Tagger.loadTagsIntoTree(filename);
//	}
//}

