package resilience;

import java.util.*;
import java.io.*;

public class BuildingMap implements Serializable {

	private BuildingGrid bgrid;
	private FloodGrid fgrid;
	private Map<Integer, Building> map;

	public BuildingMap(BuildingGrid bgrid, FloodGrid fgrid, File file)
			throws FileNotFoundException, BadFileFormatException {
		this.bgrid = bgrid;
		this.fgrid = fgrid;
		this.map = new HashMap<Integer, Building>();
		buildMap(file);
	}

	private void buildMap(File file) throws FileNotFoundException, BadFileFormatException {
		Scanner input = new Scanner(file);
		if (!input.hasNextLine()) {
			input.close();
			throw new BadFileFormatException(file.getName());
		}
		input.nextLine();
		while (input.hasNextLine()) {
			Scanner line = new Scanner(input.nextLine());
			if (!line.hasNextInt()) {
				input.close();
				throw new BadFileFormatException(file.getName());
			}
			int index = line.nextInt();
			if (!line.hasNextInt()) {
				input.close();
				throw new BadFileFormatException(file.getName());
			}
			map.put(index, new Building(line.nextInt()));
		}
		input.close();
	}

	// public void fillMap(Scanner input) {
	// int maxrow = bgrid.nrows() + bgrid.row_shift();
	// int maxcol = bgrid.ncols() + bgrid.col_shift();
	// for ( int row = bgrid.row_shift(); row < maxrow; row++ )
	// for ( int col = bgrid.col_shift(); col < maxcol; col++ ) {
	// int index = input.nextInt();
	// if ( index != bgrid.NODATA_value() ) {
	// if ( row >= 0 && row < fgrid.nrows() && col >= 0 && col < fgrid.ncols() )
	// {
	// Building b = map.get(index);
	// b.add(row,col);
	// b.add(fgrid.contour(row, col));
	// }
	// }
	// }
	// }

	public void fillMap(File bFile) throws IOException, FileNotFoundException {
		IntegerFileReader input = new IntegerFileReader(bFile);
		int maxrow = bgrid.nrows() + bgrid.row_shift();
		int maxcol = bgrid.ncols() + bgrid.col_shift();
		for (int row = bgrid.row_shift(); row < maxrow; row++)
			for (int col = bgrid.col_shift(); col < maxcol; col++) {
				int index = input.nextInt();
				if (index != bgrid.NODATA_value()) {
					if (row >= 0 && row < fgrid.nrows() && col >= 0 && col < fgrid.ncols()) {
						Building b = map.get(index);
						b.add(row, col);
						b.add(fgrid.contour(row, col));
					}
				}
			}
	}

	public void fillMap(FloodMap flood) {
		for (Integer index : map.keySet())
			map.get(index).add(flood);
	}

	public Building get(int index) {
		return map.get(index);
	}

	public Set<Integer> indexes() {
		return map.keySet();
	}

	public void floodLevel(FloodLevel fLevel, FloodMap fMap) {
		for (Integer index : map.keySet()) {
			map.get(index).floodLevel(fLevel, fMap);
		}
	}
}
