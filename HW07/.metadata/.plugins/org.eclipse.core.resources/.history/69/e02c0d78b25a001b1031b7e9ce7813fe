package hr.fer.oprpp1.gui.charts;

import java.util.List;

public class BarChart {
	
	private List<XYValue> list;
	private String xDesc, yDesc;
	private int yMin, yMax, yInterval;
	
	public BarChart(List<XYValue> list, String xDesc, String yDesc, int yMin, int yMax, int yInterval) {
		this.list = list;
		this.xDesc = xDesc;
		this.yDesc = yDesc;
		
		if (yMin < 0 || yMax <= yMin) throw new IllegalArgumentException();
		this.yMin = yMin;
		this.yMax = yMax;
		
		while ((yMax - yMin) % yInterval != 0) {
			yInterval++;
		}
		this.yInterval = yInterval;
		
		for (XYValue v : list) {
			if (v.getY() < this.yMin) throw new IllegalStateException();
		}
	}
}
