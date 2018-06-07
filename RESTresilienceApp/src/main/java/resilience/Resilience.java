package resilience;

import java.io.*;
import java.util.*;

public class Resilience {
	
	private File bFile;
	private File fFile;
	private File lFile;
	private File output;
	
	public Resilience(File bFile, File fFile, File lFile, File output) {
		this.bFile = bFile;
		this.fFile = fFile;
		this.lFile = lFile;
		this.output = output;
	}

	//计算文件中的数据
	public void compute() throws IOException,FileNotFoundException, BadFileFormatException  {
		Scanner bScanner = new Scanner(bFile);//用户输入的文件名
		Scanner fScanner = new Scanner(fFile);
		PrintWriter pw = new PrintWriter(output);//write in server
		
		BuildingGrid bGrid = new BuildingGrid(bScanner,bFile);
		bScanner.close(); ////
		FloodGrid fGrid = new FloodGrid(fScanner, fFile);
		fScanner.close(); ////
		bGrid.shift(fGrid);
		BuildingMap bMap = new BuildingMap(bGrid,fGrid,lFile);
		bMap.fillMap(bFile);
		FloodMap fMap = new FloodMap(fGrid);
		bMap.fillMap(fMap);
		fMap.update(fFile);
		FloodLevel fLevel = new MaxFlood();
		bMap.floodLevel(fLevel, fMap);
		for ( Integer index : bMap.indexes() )
			pw.println(index + " : " + bMap.get(index).floodLevel() );
		pw.close();
	}
}
