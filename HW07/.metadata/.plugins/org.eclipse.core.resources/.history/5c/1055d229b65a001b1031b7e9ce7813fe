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

	public List<XYValue> getList() {
		return list;
	}

	public void setList(List<XYValue> list) {
		this.list = list;
	}

	public String getxDesc() {
		return xDesc;
	}

	public void setxDesc(String xDesc) {
		this.xDesc = xDesc;
	}

	public String getyDesc() {
		return yDesc;
	}

	public void setyDesc(String yDesc) {
		this.yDesc = yDesc;
	}

	public int getyMin() {
		return yMin;
	}

	public void setyMin(int yMin) {
		this.yMin = yMin;
	}

	public int getyMax() {
		return yMax;
	}

	public void setyMax(int yMax) {
		this.yMax = yMax;
	}

	public int getyInterval() {
		return yInterval;
	}

	public void setyInterval(int yInterval) {
		this.yInterval = yInterval;
	}
	
	
}
