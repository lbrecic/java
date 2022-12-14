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
		at.rotate(-Math.PI / 2);
		g2.setTransform(at);
		g2.drawString(chart.getyDesc(), -getHeight() / 2 - g2.getFontMetrics().stringWidth(chart.getyDesc()) / 2,
				margin);

		g2.setTransform(defaultAt);

		g2.drawLine(margin + fixed, margin + fixed, margin + fixed, getHeight() - margin - fixed);

		g2.drawString(chart.getxDesc(), getWidth() / 2 - g2.getFontMetrics().stringWidth(chart.getxDesc()) / 2,
				getHeight() - margin + g2.getFontMetrics().getHeight() / 2);

		g2.drawLine(margin + fixed, getHeight() - margin - fixed, getWidth() - margin - fixed,
				getHeight() - margin - fixed);

		int arrLen = 10;

		int[] xPoints = new int[] { margin + fixed - arrLen / 2, margin + fixed + arrLen / 2, margin + fixed };

		int[] yPoints = new int[] { (int) (margin + fixed + Math.round(arrLen / 2 * Math.sqrt(3))),
				(int) (margin + fixed + Math.round(arrLen / 2 * Math.sqrt(3))), margin + fixed };

		g2.fillPolygon(xPoints, yPoints, 3);

		xPoints = new int[] { getWidth() - margin - fixed, getWidth() - margin - fixed,
				(int) (getWidth() - margin - fixed + Math.round(arrLen / 2 * Math.sqrt(3))) };

		yPoints = new int[] { getHeight() - margin - fixed - arrLen / 2, getHeight() - margin - fixed + arrLen / 2,
				getHeight() - margin - fixed };

		g2.fillPolygon(xPoints, yPoints, 3);
		
		double n = (chart.getyMax() - chart.getyMin()) / chart.getyInterval();
		for (double i = chart.getyMin(), j = getHeight() - margin - fixed; i <= chart.getyMax(); i += chart.getyInterval(), j -= (getHeight() - 2*(margin + fixed) - Math.round(arrLen / 2 * Math.sqrt(3)) - 2)/n) {
			g2.drawString(Integer.toString((int) i), margin + fixed - g2.getFontMetrics().stringWidth(Integer.toString((int) i)) - 10, (int) j + g2.getFontMetrics().getHeight() / 3);
			g2.drawLine(margin + fixed, (int) j, margin + fixed - 5, (int) j);
		}
		
		double el = (getWidth() - 2*(margin + fixed) - Math.round(arrLen / 2 * Math.sqrt(3)) - 2) / chart.getList().size();
		int j = 0;
		for (double i = margin + fixed; j < chart.getList().size(); i += el, j++) {
			g2.setColor(Color.black);
			g2.drawString(Integer.toString(chart.getList().get(j).getX()), (int) (i + el/2), getHeight() - margin - fixed + g2.getFontMetrics().getHeight());
			g2.drawLine((int) i, getHeight() - margin - fixed, (int) i, getHeight() - margin - fixed + 5);
			
			g2.setColor(Color.orange);
			int h = (chart.getyMax() - chart.getList().get(j).getY()) / chart.getyInterval();
			double height = getHeight() - 2 * (margin + fixed) - Math.round(arrLen / 2 * Math.sqrt(3)) - 2 -  h * ((double) (getHeight() - 2 * (margin + fixed) - Math.round(arrLen / 2 * Math.sqrt(3)) - 2) / n);
			g2.fillRect((int) i + 2, (int) (getHeight() - margin - fixed - height), (int) el - 2, (int) height);
		}
		g2.setColor(Color.black);
		g2.drawLine((int) (margin + fixed + el * chart.getList().size()), getHeight() - margin - fixed, (int) (margin + fixed + el * chart.getList().size()), getHeight() - margin - fixed + 5);
		
	}

}
