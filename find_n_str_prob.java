import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.PooledConnection;


public class find_n_str_prob {
	static float long_sentence_adder = 0.5f;
	int n;
	int max_freq_in_model = 0;
	int max2 = 0;
	float mean = 0.0f;
	String maxString = "", max2string= "";
	static int k_num_max;
	String trainer;
	HashMap<String,MutInt> frequency;
	static String mysentence = "";
	static int numsen = 5;
	static String fileString;
	static int final_no_of_res;
	static PriorityQueue<Pair<String,Float>> finalSet;
	
	public static void printFinalSet(){
		Pair<String,Float> p = finalSet.poll();
		while(p!=null){
			System.out.println("["+p.entry2+"]:\t"+p.entry1);
			p = finalSet.poll();
		}
	}
	
	public Pair<String,Float> suggestSentence(String str, int num, 
			HashMap<String, MutInt> vocab,int wordnum, int wordlimit, 
			find_n_str_prob prevgram, String completeStringTillNow,
			HTree posNode){
		if(wordnum==0){
			finalSet = new PriorityQueue<Pair<String,Float>>();
		}
		str = str.replaceAll("[-]", " ");
		String[] tokens = str.split(" ");
		StringBuilder stbr = new StringBuilder();
		int i;
		i = tokens.length - num;
		if(i >= 0){
			while(i<tokens.length){
				if(i==tokens.length - num){
					stbr.append(tokens[i]);
				}
				else{
					stbr.append(" "+tokens[i]);
				}
				i++;
			}
		}
		String key_arg = stbr.toString().toLowerCase();
		float maxf = 0;
		String strans = "";
		MutInt prevf = prevgram.getFrequency().get(key_arg);
		Pair<String,Float> newval = null;
		PriorityQueue<Pair<String,Integer>> topk = new PriorityQueue<Pair<String,Integer>>();
		if(prevf!=null){
			topk.addAll(prevf.topk);
		}
		int runno = 1;
		HTree possible_tags;
		if(wordnum==0){
			possible_tags = returnTagsFromTree(completeStringTillNow);
		}
		else{
			possible_tags = posNode;
		}
		while(!topk.isEmpty()){
			String s = topk.poll().entry1;
			String keyarg2_tag = returnTags(s).get(0);
			HTree newNode = null;
			if(possible_tags!=null){
				newNode = possible_tags.options.get(keyarg2_tag);
			}
			if(newNode != null){
				System.out.println("Ho2: "+keyarg2_tag);
				System.out.println(completeStringTillNow + " "+s);
				System.out.println("----------");
				String next = key_arg.concat(" "+s);
				MutInt currf = frequency.get(next);
				if(currf!=null && prevf!=null){
					float currval = (float)currf.val/prevf.val;
					int under_consideration = 0;
					float currval2 = 0;
					if(wordnum<wordlimit-1){
						newval = suggestSentence(next, num, vocab, wordnum+1, wordlimit, prevgram,str.concat(" "+s),newNode);
						if(newval!=null){
							under_consideration = 1;
							currval2 = currval;
							currval2 *= newval.entry2;
							currval2 += (0.5);
							if(currval2>maxf){
								maxf = currval2;
								strans = s.concat(" "+newval.entry1);
							}
						}
						else{
							under_consideration = 2;
							if(currval>maxf){
								maxf = currval;
								strans = s;
							}
						}
					}
					else if(wordnum == wordlimit - 1){
						under_consideration=2;
						if(currval>maxf){
							System.out.println("Currstr: "+s + " ["+
						currval+"] replaces string: "+strans+" ["+maxf+"]");
							maxf = currval;
							strans = s;
						}
					}
					if(wordnum == 0){
						if(finalSet.size()<final_no_of_res){
							if(under_consideration == 1){
								finalSet.add(new Pair(s.concat(" "+newval.entry1),currval2));
							}
							else{
								finalSet.add(new Pair(s,currval));
							}
						}
						else if(under_consideration==1){
							if(currval2>finalSet.peek().entry2){
								finalSet.poll();
								finalSet.add(new Pair(newval.entry1,currval2));
							}
						}
						else if(under_consideration==2){
							if(currval>finalSet.peek().entry2){
								finalSet.poll();
								finalSet.add(new Pair(s,currval));
							}
						}
					}
				}
			}
		}
		if(maxf==0){
			return null;
		}
		return new Pair<String,Float>(strans,maxf);

	}
	
	
	public PriorityQueue<Pair<String, Integer>> predNext(String str, int num, 
			HashMap<String,MutInt> vocab, int wordnum, String completeStringTillNow){
		
		PriorityQueue<Pair<String,Integer>> topk = new PriorityQueue<Pair<String,Integer>>();
		str = str.replaceAll("[-]", " ");
		String[] tokens = str.split(" ");
		int k_num = k_num_max;
		StringBuilder stbr = new StringBuilder();
		int i;
		i = tokens.length - num;
		if(i >= 0){
			while(i<tokens.length){
				stbr.append(tokens[i]+" ");
				i++;
			}
		}
		String key_arg = stbr.toString().toLowerCase();
		LinkedHashSet<String> possibleTags = possibleTags(returnTags(completeStringTillNow));
		for(Map.Entry<String, MutInt> keyvalpair: vocab.entrySet()){
			String s = keyvalpair.getKey();
				String keyarg2_tag = returnTags(s).get(0);
				System.out.print(s + "\t");
				System.out.println("keyarg2tag: "+keyarg2_tag);
				System.out.println("possibl tags: "+possibleTags.toString());
				if(possibleTags.contains(keyarg2_tag)){
					String keyarg2 = key_arg.concat(s);
					System.out.println("This is keyarg2: "+keyarg2);
					MutInt currf = frequency.get(keyarg2);
					if(currf!=null){
						int useval = currf.val
						if(k_num>0){
							topk.add(new Pair<String, Integer>(s,useval));
							k_num--;
						}
						else{
							Pair<String, Integer> minstr = topk.peek();
							if(useval>minstr.entry2){
								topk.poll();
								topk.add(new Pair<String, Integer>(s, useval));
							}
							else if(currf.val==minstr.entry2){
								float choose_item = (float) Math.random();
								if(choose_item>0.5){
									topk.poll();
									topk.add(new Pair<String, Integer>(s, useval));								
								}
							}
						}
					}
			}
		}
		return topk;
	}
	
	
	
	public find_n_str_prob(int n, String trainer) throws IOException {
		this.n = n;
		this.trainer = trainer;
		frequency = new HashMap<String, MutInt>();
		this.calc_freq(fileString);
		this.my_max_freq();
	}
	
	public find_n_str_prob(int n, String trainer, HashMap<String,MutInt> nplusfreq) throws IOException {
		this.n = n;
		this.trainer = trainer;
		frequency = new HashMap<String, MutInt>();
		this.genfreq(fileString, n, nplusfreq);
		this.my_max_freq();
	}
	
	public HashMap<String, MutInt> getFrequency(){
		return frequency;
	}
	
	public int getFrequencyOf(String str, int num){
		str.replaceAll("-", " ");
		String[] tokens = str.split(" +");
		StringBuilder stbr = new StringBuilder();
		int i;
		i = tokens.length - num;
		if(i >= 0){
			while(i<tokens.length){
				stbr.append(" "+tokens[i]);
				i++;
			}
		}
		String key_arg = stbr.toString().toLowerCase();
		MutInt ansMut = frequency.get(key_arg);
		if(ansMut!=null){
			return ansMut.val;
		}
		return 0;
	}
	
	public static void makeFileToString(String trainer) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(trainer));
		StringBuilder stbr = new StringBuilder();
		String line = br.readLine();
		while(line!=null){
			if(mainframe.pos_loaded==0){
				Tagger.AddNewTags(line);
			}
			stbr.append(line);
			stbr.append(" ");
			line = br.readLine();
		}
		br.close();
		fileString = stbr.toString();
	}
	
	public void calc_freq(String s){
		s = s.replaceAll("[.!?]+", " . ");
		String[] tokens = s.split(" +");
		int len = tokens.length;
		TreeSet<String> t = new TreeSet<String>();
		for(String sl: tokens){
			t.add(sl.toLowerCase());
		}
		for(int i = 0;i<len-n+1;i++){
			StringBuilder strb = new StringBuilder();
			for(int j = i;j<i+n-1;j++){
					if(j==i){
						strb.append(tokens[j]);
					}
					else{
						strb.append(" "+tokens[j]);
					}
			}
			String str = strb.toString().toLowerCase();
				MutInt freq_str = frequency.get(str);
				if(freq_str==null){
					MutInt corrfreq = new MutInt();
					corrfreq.isCommon = ListOfWords.low.contains(str);
					frequency.put(str, corrfreq);
				}
				else{
					freq_str.increment();
				}
		}
	}
	
	public void genfreq(String text, int gramnum, HashMap<String, MutInt> nplusgram){
		System.out.print("g");
		text = text.replaceAll("[.?!]+", " . ");
		String[] words = text.split(" +");
		for(int i = 0; i <words.length - gramnum; i++){
			StringBuilder strval = new StringBuilder();
			for(int j = 0; j<gramnum-2;j++){
				strval.append(words[i+j]+" ");
			}
			strval.append(words[i+gramnum-2]);
			String str = strval.toString().toLowerCase();
				MutInt freq_str = frequency.get(str);
				if(freq_str==null){
					MutInt corrfreq = new MutInt();
					corrfreq.isCommon = ListOfWords.low.contains(str);
					corrfreq.topk = new PriorityQueue<Pair<String,Integer>>();
					frequency.put(str, corrfreq);
				}
				else{
					freq_str.increment();
				}
				if(i+gramnum<words.length){
					strval.append(" "+words[i+gramnum-1]);
					MutInt nextval = nplusgram.get(strval.toString().toLowerCase());
					freq_str = frequency.get(str);
					if(nextval!=null){
						if(freq_str.topk.size()<k_num_max){
							Pair<String,Integer> nextpair = new Pair<String,Integer>(words[i+gramnum-1].toLowerCase(),nextval.val);
							if(!freq_str.topk.contains(nextpair)){
								freq_str.topk.add(nextpair);
							}
						}
						else if(nextval.val > freq_str.topk.peek().entry2){
							Pair<String,Integer> nextpair = new Pair<String,Integer>(words[i+gramnum-1].toLowerCase(),nextval.val);
							if(!freq_str.topk.contains(nextpair)){
								freq_str.topk.add(nextpair);
							}
							if(freq_str.topk.size()>k_num_max){
								freq_str.topk.poll();
							}
						}
					}
				}
				
		}
	}
	
	
	
	public static HTree returnTagsFromTree(String phrase){
		String t = Tagger.tagger.tagString(phrase);
		String tokens[] = t.split(" +");
		HTree curr_node = Tagger.POSTree;
		for(String tag: tokens){
			String tok_of_tok[] = tag.split("/+");
			System.out.println("Ho "+tok_of_tok[tok_of_tok.length-1]);
			if(curr_node==null){
				return null;
			}
			curr_node = curr_node.options.get(tok_of_tok[tok_of_tok.length-1]);
						
		}
		return curr_node;
	}
	
	public static HTree returnTagsFromTree(HTree root){
		
		return null;
	}
	
	public static ArrayList<String> returnTags(String s){
		String t = Tagger.tagger.tagString(s);
		String tokens[] = t.split(" +");
		ArrayList<String> tags_tbappend = new ArrayList<>(tokens.length);
		for(String tok: tokens){
			String[] tok_of_tok = tok.split("/+");
			tags_tbappend.add(tok_of_tok[tok_of_tok.length-1]);
		}
		return tags_tbappend;
	}
	
	public static LinkedHashSet<String> possibleTags(ArrayList<String> prevtags){
		int i = 0, j = 0;
		LinkedHashSet<String> nextSet = new LinkedHashSet<String>();
		Iterator<ArrayList<String>> iter_taglist = Tagger.allFileTags.iterator();
		for(ArrayList<String> curr_tag_line = iter_taglist.next();iter_taglist.hasNext();curr_tag_line = iter_taglist.next()){
			boolean canAdd = true;
			if(curr_tag_line.size()>prevtags.size()){
				for(j=0;j<prevtags.size() ;j++){
					if(!prevtags.get(j).equals(curr_tag_line.get(j))){
						canAdd = false;
						break;
					}
				}
				if(canAdd == true){
					nextSet.add(curr_tag_line.get(j));
				}
			}
			else{
				canAdd = false;
			}
		}
		return nextSet;
	}
	
}