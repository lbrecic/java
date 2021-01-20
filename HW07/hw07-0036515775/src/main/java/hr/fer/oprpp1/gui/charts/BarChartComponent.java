package hr.fer.oprpp1.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

public class BarChartComponent extends JComponent {
	
	private static final long serialVersionUID = 1L;
	
	private BarChart chart;
	private int fixedY = 20;
	private int fixedX = 10;
	
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setFont(new Font(g.getFont().getName(), Font.PLAIN, 16));
		g.setColor(Color.BLACK);
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform defaultAt = g2.getTransform();
		
		AffineTransform at = new AffineTransform();
		at.rotate(- Math.PI / 2);
		g2.setTransform(at);
		g2.drawString(chart.getyDesc(), -fixedX, fixedY);
		
		g2.setTransform(defaultAt);
		
		g2.drawLine(100 + g.getFont().getSize(), 400 + g.getFont().getSize(),
				100 + g.getFont().getSize(), 400 + g.getFont().getSize() - 15*chart.getyMax());
	
		g2.drawLine(100 + g.getFont().getSize(), 400 + g.getFont().getSize(),
				100 + g.getFont().getSize() + 75*chart.getList().size(), 400 + g.getFont().getSize());
		
		g2.drawString(chart.getxDesc(), 100 + g.getFont().getSize() + 50, 400 + g.getFont().getSize() + 50);
	}
	
}
