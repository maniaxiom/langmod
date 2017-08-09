// CLASS DESCRIPTION:
// Integer Class to store and efficiently increment the integral frequencyss when looked up in a HashMap.
// Priority Queue topk is used for some MutInt objects where it stores the top k most occuring words after a given phrase.

import java.util.PriorityQueue;

public class MutInt implements Comparable<MutInt>{
	int val = 1;
	boolean isCommon = false;
	int position=0;
	PriorityQueue<Pair<String,Integer>> topk;

	public MutInt(){
		topk = new PriorityQueue<Pair<String,Integer>>();
	}

	public void increment(){
		this.val++;
	}

	public int get(){
		return this.val;
	}
	
	@Override
	public int compareTo(MutInt o) {
		if(this.val>o.val){
			return 1;
		}
		else if(this.val<o.val){
			return -1;
		}
		return 0;
	}
}
