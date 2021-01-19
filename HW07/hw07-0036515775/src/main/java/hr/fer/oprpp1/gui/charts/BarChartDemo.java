package hr.fer.oprpp1.gui.charts;

import java.awt.Container;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class BarChartDemo extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	String filePath;
	
	public BarChartDemo(String filePath) throws IOException {
		this.filePath = filePath;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}
	
	public void initGUI() throws IOException {
		Container cp = getContentPane();
		JLabel label = new JLabel(filePath);
		label.setHorizontalTextPosition(JLabel.CENTER);
		cp.add(label);
		
		Path file = Paths.get(filePath);
		Scanner sc = new Scanner(file);
		
		String xDesc = sc.nextLine();
		String yDesc = sc.nextLine();
		String[] xyv = sc.nextLine().split("\s+");
		Integer yMin = Integer.parseInt(sc.nextLine());
		Integer yMax = Integer.parseInt(sc.nextLine());
		Integer yInterval = Integer.parseInt(sc.nextLine());
		
		List<XYValue> values = new ArrayList<>();
		for (int i = 0; i < xyv.length; i++) {
			String[] xy = xyv[i].trim().split(",");
			int x = Integer.parseInt(xy[0]);
			int y = Integer.parseInt(xy[1]);
			values.add(new XYValue(x, y));
		}
		
		BarChart chart = new BarChart(values, xDesc, yDesc, yMin, yMax, yInterval);
		
		cp.add(new BarChartComponent(chart));
		
		sc.close();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				new BarChartDemo(args[0]).setVisible(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
