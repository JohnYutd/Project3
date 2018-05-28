package resilience;

import java.util.Set;

public class AverageFlood implements FloodLevel {

	@Override
	public double compute(Set<Coordinates> s, FloodMap fMap) {
		double avge = 0.0;
		for( Coordinates c : s )
			avge += fMap.floodLevel(c.x,c.y);
		return avge / s.size();
	}

}
