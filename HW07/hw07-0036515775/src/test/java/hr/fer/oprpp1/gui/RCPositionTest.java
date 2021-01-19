package hr.fer.oprpp1.gui;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

public class RCPositionTest {
	
	@Test
	public void wrongRCPositionDimensions1() {
		assertThrows(CalcLayoutException.class, () -> {new RCPosition(0,5);});
		assertThrows(CalcLayoutException.class, () -> {new RCPosition(8,5);});
	}
	
	@Test
	public void wrongRCPositionDimensions2() {
		assertThrows(CalcLayoutException.class, () -> {new RCPosition(2,0);});
		assertThrows(CalcLayoutException.class, () -> {new RCPosition(2,8);});
	}
	
	@Test
	public void wrongRCPositionDimensions3() {
		assertThrows(CalcLayoutException.class, () -> {new RCPosition(1,3);});
	}
	
	@Test
	public void wrongRCPositionDimensions4() {
		JPanel p = new JPanel(new CalcLayout(2));
		p.add(new JLabel(""), new RCPosition (1,1));
		assertThrows(CalcLayoutException.class, () -> {p.add(new JLabel(""), new RCPosition (1,1));});
	}
	
}
