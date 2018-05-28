package resilience;

import java.io.*;

public class IntegerFileReader {

	private BufferedReader br;
	private String buffer;
	private int bufferSize;
	private int index;
	
	public IntegerFileReader(File input) throws IOException, FileNotFoundException {
		br = new BufferedReader(new FileReader(input));
		for (int i = 0; i < 6; i++ )
			br.readLine();
		buffer = br.readLine();
		bufferSize = buffer.length();
		index = 0;
		gotoNext();
	}
	
	private void gotoNext() throws IOException {
		while ( index < bufferSize && buffer.charAt(index) == ' ' )
			index++;
		if ( index == bufferSize ) {
			buffer = br.readLine();
			if ( buffer != null ) {
				bufferSize = buffer.length();
				index = 0;
				gotoNext();
			}
		}					
	}
	
	public int nextInt() throws IOException {
		int i = index++;
		while ( index < bufferSize && buffer.charAt(index) != ' ' )
			index++;
		int j = index;
		int val = Integer.parseInt(buffer.substring(i, j));
		gotoNext();
		return val;
	}
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		String filepath = "/home/marc/resilience/numbers.txt";
		File file = new File(filepath);
		IntegerFileReader ifr = new IntegerFileReader(file);
		
		for ( int i = 1; i <= 10; i++ )
			System.out.println(i + " : " + ifr.nextInt());
		
	}
}
