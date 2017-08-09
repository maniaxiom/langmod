import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class Tagger {
	static Set<ArrayList<String>> allFileTags;
	static HTree POSTree = new HTree();
	static MaxentTagger tagger;
	static int a = 0;
	static int maxsize = 0;
	public static void AddNewTags(String s){
			a++;
//			System.out.println(a);
			String[] strs = s.split("(?<=[?!.])");
			int n_tbtag = strs.length;
			int slen = s.length();
			if(slen>0){
			if(!(s.charAt(slen-1)=='.'|s.charAt(slen-1)=='!'|s.charAt(slen-1)=='?')){
				n_tbtag--;
			}
			}
			int i = 0;
			for(i=0;i<n_tbtag;i++){
				strs[i] = strs[i].replaceAll("-", " ");
				String t = tagger.tagString(strs[i]);
				String tokens[] = t.split(" +");
				ArrayList<String> tags_tbappend = new ArrayList<>(tokens.length);
				for(String tok: tokens){
					String[] tok_of_tok = tok.split("/+");
					tags_tbappend.add(tok_of_tok[tok_of_tok.length-1]);
				}
//				System.out.println(tags_tbappend.toString());
				allFileTags.add(tags_tbappend);
			}
		}
	
	public static void loadTagsToFile(String filename){
		try{
			PrintWriter pr = new PrintWriter(filename);
			for(ArrayList<String> sentence_tag: allFileTags){
				pr.println();
				for(String s: sentence_tag){
					pr.println(s);
				}
			}
			pr.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public static void loadTagsFromFile(String filename){
		BufferedReader br;
		try{
			br = new BufferedReader(new FileReader(filename));
			String line = br.readLine();
			int newEle = 0;
			ArrayList<String> tags_tbappend = new ArrayList<String>();
			while(line!=null){
				if(line.equals("")){
//					System.out.println("here");
					newEle=1;
				}
				if(newEle==1){
//					System.out.println(tags_tbappend);
					if(tags_tbappend.size()>0){
						if(tags_tbappend.size()>maxsize){
							maxsize = tags_tbappend.size();
						}
						allFileTags.add(tags_tbappend);
					}
					tags_tbappend = new ArrayList<String>();
					newEle = 0;
				}
				else{
					tags_tbappend.add(line);
				}
//				System.out.println(line);
				line = br.readLine();
			}
			if(newEle==0 && tags_tbappend.size()>0){
				allFileTags.add(tags_tbappend);
			}
			br.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void loadTagsIntoTree(String filename){
		BufferedReader br;
		
		try{
			br = new BufferedReader(new FileReader(filename));
			String line = br.readLine();
			int newEle = 0;
			ArrayList<String> tags_tbappend = new ArrayList<String>();
			while(line!=null){
				if(line.equals("")){
//					System.out.println("here");
					newEle=1;
				}
				if(newEle==1){
//					System.out.println(tags_tbappend);
					if(tags_tbappend.size()>0){
						if(tags_tbappend.size()>maxsize){
							maxsize = tags_tbappend.size();
						}
						POSTree.addToTree(tags_tbappend);
					}
					tags_tbappend = new ArrayList<String>();
					newEle = 0;
				}
				else{
					tags_tbappend.add(line);
				}
//				System.out.println(line);
				line = br.readLine();
			}
			if(newEle==0 && tags_tbappend.size()>0){
				POSTree.addToTree(tags_tbappend);
			}
			br.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		POSTree.printTree(POSTree.options, 0);
	}
	
	
}
