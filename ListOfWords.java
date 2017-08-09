import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ListOfWords {
	static ArrayList<String> low;
	public ListOfWords(String filename) {
		// TODO Auto-generated constructor stub
		_init(filename);
	}
	
	public static void _init(String filename){
		try{
			low = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String s = br.readLine();
			int i = 0;
			while(s!=null && i<3000){
				low.add(s);
				s=br.readLine();
				i++;
			}
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
}
