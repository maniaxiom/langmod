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
		// TODO Auto-generated method stub
		if(this.val>o.val){
			return 1;
		}
		else if(this.val<o.val){
			return -1;
		}
		return 0;
	}
}
