// CLASS DESCRIPTION:
// An object of this class holds a recursive tree data structure of Hashmaps.
// This is used for find the next set of POS tags after a particular word in a sentence efficiently.
// Eg.) We have the following POS Tags - A,B,C,D,E.
// We have POS tags of sentences in corpus as in: A->B->C , A->B->D, B->A->E, B->B->D
// We have a root HTree Object in Tagger class called POSTree (Let's call the object root. 
// KEY: P.options = [{Q, HTree(Q)=PQ}] 
// This means that the Hashmap options of object referred to by P contains Key String Q and it's value is a new HTree, which we will refer by PQ.
// This is for representation purpose only. In the actual working, we return the actual HTree object to look for further POS options, instead of referring them by name.
// 
// root.options = [{A, HTree(A)=A},{B, HTree(B)=B}]
// A.options = [{B, HTree(B)=AB}]
// AB.options = [{C, HTree(C)=ABC}, {D, HTree(D)=ABD}]
// B.options = [{A, HTree(A)=BA}, {B, HTree(B)=BB}]
// BA.options = [{E, HTree(E)=BAE]
// BB.options = [{D, HTree(D)=BBD}]
// 
// Hence if I have a sentence that starts with POS Tag A. The next possible tags are the keys in A.options, i.e, B.
// Then the next possible tags are the keys in AB.options, i.e., C and D.
// 


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class HTree {
	// options contains list of next possible tags for the current HTree instance.
	HashMap<String, HTree> options;
	HTree(){
		options = new HashMap<String, HTree>();
	}
	
	// Return next set of possible tags
	public HashSet<String> returnKeys(){
		return (HashSet<String>) options.keySet();
	}
	
	// Check if a tag is possible after the give HTree object
	public boolean searchElement(String tagname){
		if(options.containsKey(tagname)){
			return true;
		}
		return false;
	}
	
	// Add in a new next possible POS tag if it does not exist
	public HashMap<String,HTree> searchAndAddElement(String tagname, HashMap<String, HTree> opt){
		HTree curr = opt.get(tagname);
		if(curr!=null){
			return curr.options;
		}
		else{
			opt.put(tagname,new HTree());
			return opt.get(tagname).options;
		}
	}
	
	// Takes an arraylist in order of POS tags of a sentence and adds it to the POSTree Data Structure
	public boolean addToTree(ArrayList<String> pos_sent){
		HashMap<String,HTree> currBranch = options;
		for(String tagname: pos_sent){
			currBranch = searchAndAddElement(tagname, currBranch);
		}
		return false;
	}
	
	// Function to Print the entire POS Tree Recursively
	public void printTree(HashMap<String, HTree> opt, int prevspace){
		if(opt.size()==0){
			System.out.println();
			return;
		}
		int i = 0;
		for(HashMap.Entry<String, HTree> entry: opt.entrySet()){
			String tbprinted = "";
			if(i==0){
				tbprinted = entry.getKey()+"->";
				System.out.print(tbprinted);
				printTree(entry.getValue().options, prevspace+tbprinted.length());
			}
			else{
				if(prevspace!=0){
					tbprinted = String.format("%0$"+prevspace+"s"," ");
				}
				tbprinted += entry.getKey()+"->";
				System.out.print(tbprinted);
				printTree(entry.getValue().options, tbprinted.length());
			
}			i++;
		}
	}
	
}
