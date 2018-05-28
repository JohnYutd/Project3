package resilience;

import java.util.*;
import java.io.*;

public class BuildingGrid extends Grid {
	
	private int col_shift;
	private int row_shift;

	public BuildingGrid(Scanner input, File file) throws BadFileFormatException {
		super(input,file);
	}
	
	public void shift(FloodGrid fgrid) {
		this.row_shift = (int) Math.round((fgrid.yllcorner() + fgrid.nrows() * cellsize() - yllcorner() - nrows() * cellsize())/cellsize());
		this.col_shift = (int) Math.round((xllcorner() - fgrid.xllcorner())/cellsize());
	}
	
	public int row_shift() {
		return row_shift;
	}
	
	public int col_shift() {
		return col_shift;
	}	
}
