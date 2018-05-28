package resilience;

import java.io.*;

public class DoubleFileReader {

	private BufferedReader br;
	private String buffer;
	private int bufferSize;
	private int index;
	
	public DoubleFileReader(File input) throws IOException, FileNotFoundException {
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
	
	public double nextDouble() throws IOException {
		int i = index++;
		while ( index < bufferSize && buffer.charAt(index) != ' ' )
			index++;
		int j = index;
		double val = Double.parseDouble(buffer.substring(i, j));
		gotoNext();
		return val;
	}
}
