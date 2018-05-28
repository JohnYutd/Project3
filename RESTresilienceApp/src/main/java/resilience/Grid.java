package resilience;

import java.util.*;
import java.io.*;

public abstract class Grid {
	
	private int ncols;
	private int nrows;
	private double xllcorner;
	private double yllcorner;
	private int cellsize;
	private int NODATA_value;
	
	public Grid(Scanner input,File file) throws BadFileFormatException {
		this.ncols = readNextInt(input,file);
		this.nrows = readNextInt(input,file);
		this.xllcorner = readNextDouble(input,file);
		this.yllcorner = readNextDouble(input,file);
		this.cellsize = readNextInt(input,file);
		this.NODATA_value = readNextInt(input,file);
	}
	
	private int readNextInt(Scanner input,File file) throws BadFileFormatException {
		if ( ! input.hasNextLine() )
			throw new BadFileFormatException(file.getName());
		input = new Scanner(input.nextLine());
		if ( ! input.hasNext() )
			throw new BadFileFormatException(file.getName());
		input.next();
		if ( ! input.hasNextInt() )
			throw new BadFileFormatException(file.getName());
		return input.nextInt();		
	}
	
	private double readNextDouble(Scanner input,File file) throws BadFileFormatException {
		if ( ! input.hasNextLine() )
			throw new BadFileFormatException(file.getName());
		input = new Scanner(input.nextLine());
		if ( ! input.hasNext() )
			throw new BadFileFormatException(file.getName());
		input.next();
		if ( ! input.hasNextDouble() )
			throw new BadFileFormatException(file.getName());
		return input.nextDouble();		
	}

	public int ncols() {
		return ncols;
	}
	
	public int nrows() {
		return nrows;
	}
	
	public double xllcorner() {
		return xllcorner;
	}
	
	public double yllcorner() {
		return yllcorner;
	}
	
	public int cellsize() {
		return cellsize;
	}
	
	public int NODATA_value() {
		return NODATA_value;
	}

	@Override
	public String toString() {
        String s = "ncols         " + ncols;
		s += "\nnrows         " + nrows;
		s += "\nxllcorner     " + xllcorner;
		s += "\nyllcorner     " + yllcorner;
		s += "\ncellsize      " + cellsize;
		s += "\nNODATA_value  " + NODATA_value;
		return s;
	}
	
}
