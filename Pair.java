
// CLASS DESCRIPTION:
// Pair stores a set of 2 values. Usage: Storing a String and it's frequency value for instance.
// Methods implemented are basic getters and setters.

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
		return this.entry2.compareTo(o.getEntry2());
	}
	@Override
	public boolean equals(Object obj) {
		Pair<K,T> o = (Pair<K,T>) obj;
		return this.entry1.equals(o.entry1);
	}
	public boolean equals(Pair<K,T> o) {
		System.out.println("1: "+this.entry1.toString()+"\t2: "+o.entry1.toString());
		return this.entry1.equals(o.entry1);
	}
	@Override
	public String toString() {
		return entry1.toString();
	}
	
	
}
