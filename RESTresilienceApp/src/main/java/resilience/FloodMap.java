package resilience;

import java.util.*;
import java.io.*;

public class FloodMap {
	
	private Map<Integer,Map<Integer,Double>> map;
	private FloodGrid fgrid;
	private static double NO_VALUE = -999;
	
	public FloodMap(FloodGrid fgrid) {
		this.fgrid = fgrid;
		this.map = new HashMap<Integer,Map<Integer,Double>>();
	}
	
	public void add(int row, int col) {
		Map<Integer,Double> mcol = map.get(row);
		if ( mcol == null ) {
			mcol = new HashMap<Integer,Double>();
			map.put(row, mcol);
		}
		mcol.put(col, NO_VALUE);
	}
		
//	public void update(Scanner input) {
//		Map<Integer,Double> mcol;
//		int maxrows = fgrid.nrows();
//		int maxcols = fgrid.ncols();
//		for ( int row = 0; row < maxrows; row++ )
//			for ( int col = 0; col < maxcols; col++ ) {
//				double value = input.nextDouble();
//				if ( value != fgrid.NODATA_value() && (mcol = map.get(row)) != null )
//					mcol.put(col, value);
//			}
//	}
	
	public void update(File fFile) throws IOException, FileNotFoundException {
		DoubleFileReader input = new DoubleFileReader(fFile);
		Map<Integer,Double> mcol;
		int maxrows = fgrid.nrows();
		int maxcols = fgrid.ncols();
		for ( int row = 0; row < maxrows; row++ )
			for ( int col = 0; col < maxcols; col++ ) {
				double value = input.nextDouble();
				if ( value != fgrid.NODATA_value() && (mcol = map.get(row)) != null )
					mcol.put(col, value);
			}
	}
	
	public double floodLevel(int row, int col) {
		return map.get(row).get(col);
	}
}
