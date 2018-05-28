package resilience;

import java.util.Set;

public class MaxFlood implements FloodLevel {

	@Override
	public double compute(Set<Coordinates> s, FloodMap fMap) {
		double max = 0.0;
		for( Coordinates c : s ) {
			double fl = fMap.floodLevel(c.x,c.y);
			if ( fl > max )
				max = fl;
		}
		return max;
	}
}
