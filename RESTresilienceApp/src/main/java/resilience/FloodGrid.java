package resilience;

import java.util.*;
import java.io.*;

public class FloodGrid extends Grid {
	
	public FloodGrid(Scanner input, File file) throws BadFileFormatException {
		super(input,file);
	}
	
	/*
	 * 函数返回一个集合，用来存储
	 * 
	 * 
	 * 
	 * */
	public Collection<Coordinates> contour(int row, int col) {
		Collection<Coordinates> l = new LinkedList<Coordinates>(); //坐标
		if ( row - 1 >= 0 ) {
			l.add(new Coordinates(row-1,col));   //下
			if ( col - 1 >= 0 )
				l.add(new Coordinates(row-1,col-1)); //左下
			if ( col + 1 < ncols() )
				l.add(new Coordinates(row-1,col+1)); //右下
		}
		if ( col - 1 >= 0 )
			l.add(new Coordinates(row,col-1));  //左
		if ( col + 1 < ncols() )
			l.add(new Coordinates(row,col+1));  //右
		if ( row + 1 < nrows() ) {
			l.add(new Coordinates(row+1,col));  //上
			if ( col - 1 >= 0 )
				l.add(new Coordinates(row+1,col-1));  //左上
			if ( col + 1 < ncols() )
				l.add(new Coordinates(row+1,col+1));  //右上
		}
		return l;
	}
}
