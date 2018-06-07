package resilience;

import java.util.*;

public class Building {
	
	private int landuse;
	private Set<Coordinates> bset;
	private Set<Coordinates> fset;
	private double damage;
	private double floodLevel;
	
	public Building(int landuse) {
		this.landuse = landuse;
		this.bset = new HashSet<Coordinates>();
		this.fset = new HashSet<Coordinates>();
	}
	
	public void add(int x, int y) {
		Coordinates c = new Coordinates(x,y);
		bset.add(c);
		fset.remove(c);//¥Ê‘⁄‘Ú…æ≥˝
	}

	public void add(Collection<Coordinates> fcells)  {
		fset.addAll(fcells);
		fset.removeAll(bset);
	}
	
	public void add(FloodMap flood) {
		for( Coordinates c : fset )
			flood.add(c.x,c.y);
	}
	
	public void floodLevel(FloodLevel fLevel,FloodMap fMap) {
		floodLevel = fLevel.compute(fset, fMap);
	}
	
	public double floodLevel() {
		return floodLevel;
	}

	@Override
	public String toString() {
		return bset.toString() + "\n" + fset.toString();
	}
}
