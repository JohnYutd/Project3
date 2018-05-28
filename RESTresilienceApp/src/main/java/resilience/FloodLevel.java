package resilience;

import java.util.*;

public interface FloodLevel {
	
	public double compute(Set<Coordinates> s, FloodMap fMap);

}
