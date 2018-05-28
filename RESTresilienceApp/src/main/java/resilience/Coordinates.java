package resilience;

import java.util.*;

public class Coordinates {

	public int x;
	public int y;
	
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o) {
		return x == ((Coordinates) o).x && y == ((Coordinates) o).y;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(x, y);
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
