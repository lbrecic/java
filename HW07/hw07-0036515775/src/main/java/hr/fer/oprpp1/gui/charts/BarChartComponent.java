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
	private int fixed = 40;
	private int margin = 50;
	
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
		g2.drawString(chart.getyDesc(), 
				-getHeight()/2 - g2.getFontMetrics().stringWidth(chart.getyDesc())/2, 
				margin);
		
		g2.setTransform(defaultAt);
		
		g2.drawLine(margin + fixed, margin + fixed, margin + fixed, getHeight() - margin - fixed);
		
		g2.drawString(chart.getxDesc(), 
				getWidth()/2 - g2.getFontMetrics().stringWidth(chart.getxDesc())/2, 
				getHeight() - margin + g2.getFontMetrics().getHeight()/2);

		g2.drawLine(margin + fixed, 
				getHeight() - margin - fixed, 
				getWidth() - margin - fixed, 
				getHeight() - margin - fixed);
		
		int arrLen = 10; 
		
		int[] xPoints = new int[] {
				margin + fixed - arrLen/2,
				margin + fixed + arrLen/2,
				margin + fixed
		};
		
		int[] yPoints = new int[] {
				(int) (margin + fixed + Math.round(arrLen/2 * Math.sqrt(3))),
				(int) (margin + fixed + Math.round(arrLen/2 * Math.sqrt(3))),
				margin + fixed
		};
		
		g2.fillPolygon(xPoints, yPoints, 3);
		
		xPoints = new int[] {
				getWidth() - margin - fixed,
				getWidth() - margin - fixed,
				(int) (getWidth() - margin - fixed + Math.round(arrLen/2 * Math.sqrt(3)))
		};
		
		yPoints = new int[] {
				getHeight() - margin - fixed - arrLen/2,
				getHeight() - margin - fixed + arrLen/2,
				getHeight() - margin - fixed
		};
		
		g2.fillPolygon(xPoints, yPoints, 3);
		
		}
	
}
