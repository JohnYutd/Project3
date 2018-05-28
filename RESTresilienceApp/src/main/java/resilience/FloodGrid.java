package resilience;

import java.util.*;
import java.io.*;

public class FloodGrid extends Grid {
	
	public FloodGrid(Scanner input, File file) throws BadFileFormatException {
		super(input,file);
	}
	
	public Collection<Coordinates> contour(int row, int col) {
		Collection<Coordinates> l = new LinkedList<Coordinates>();
		if ( row - 1 >= 0 ) {
			l.add(new Coordinates(row-1,col));
			if ( col - 1 >= 0 )
				l.add(new Coordinates(row-1,col-1));
			if ( col + 1 < ncols() )
				l.add(new Coordinates(row-1,col+1));
		}
		if ( col - 1 >= 0 )
			l.add(new Coordinates(row,col-1));
		if ( col + 1 < ncols() )
			l.add(new Coordinates(row,col+1));
		if ( row + 1 < nrows() ) {
			l.add(new Coordinates(row+1,col));
			if ( col - 1 >= 0 )
				l.add(new Coordinates(row+1,col-1));
			if ( col + 1 < ncols() )
				l.add(new Coordinates(row+1,col+1));
		}
		return l;
	}
}
