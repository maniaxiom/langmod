
public class Pair<K extends Comparable<K>,T extends Comparable<T>> implements Comparable<Pair<K,T>>{
	K entry1;
	T entry2;
	public Pair(K entry1, T entry2) {
		super();
		this.entry1 = entry1;
		this.entry2 = entry2;
	}
	public K getEntry1() {
		return entry1;
	}
	public void setEntry1(K entry1) {
		this.entry1 = entry1;
	}
	public T getEntry2() {
		return entry2;
	}
	public void setEntry2(T entry2) {
		this.entry2 = entry2;
	}
	public int compareTo(Pair<K,T> o) {
		// TODO Auto-generated method stub
		return this.entry2.compareTo(o.getEntry2());
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Pair<K,T> o = (Pair<K,T>) obj;
		return this.entry1.equals(o.entry1);
//		return super.equals(obj);
	}
	public boolean equals(Pair<K,T> o) {
		// TODO Auto-generated method stub
		System.out.println("1: "+this.entry1.toString()+"\t2: "+o.entry1.toString());
		return this.entry1.equals(o.entry1);
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return entry1.toString();
	}
	
	
}
