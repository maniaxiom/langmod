import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class HTree {
	HashMap<String, HTree> options;
	HTree(){
		options = new HashMap<String, HTree>();
	}
	
	public HashSet<String> returnKeys(){
		return (HashSet<String>) options.keySet();
	}
	
	public boolean searchElement(String tagname){
		if(options.containsKey(tagname)){
			return true;
		}
		return false;
	}
	
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
	
	public boolean addToTree(ArrayList<String> pos_sent){
		HashMap<String,HTree> currBranch = options;
		for(String tagname: pos_sent){
			currBranch = searchAndAddElement(tagname, currBranch);
		}
		return false;
	}
	
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
//				i++;
			}
			else{
				if(prevspace!=0){
					tbprinted = String.format("%0$"+prevspace+"s"," ");
				}
				tbprinted += entry.getKey()+"->";
//				System.out.print(prevspace+"\t"+tbprinted.length());
				System.out.print(tbprinted);
				printTree(entry.getValue().options, tbprinted.length());
			}
			
//			System.out.println("\tTBP:\t$"+tbprinted+"$\t "+tbprinted.length());
			i++;
		}
	}
	
}
