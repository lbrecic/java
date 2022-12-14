package hr.fer.oprpp1.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

public class CalcLayout implements LayoutManager2 {

	private int distance;
	private Map<Component, RCPosition> constraint;

	public CalcLayout() {
		this(0);
	}

	public CalcLayout(int distance) {
		constraint = new HashMap<>();
		this.distance = distance;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		constraint.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		int width = 0, height = 0;

		for (Component comp : parent.getComponents()) {
			if (constraint.get(comp).getRow() == 1 &&
					constraint.get(comp).getColumn() == 1) {
				continue;
			}
			
			if (comp.getPreferredSize().getWidth() > width) width = (int)comp.getPreferredSize().getWidth();
			if (comp.getPreferredSize().getHeight() > height) height = (int)comp.getPreferredSize().getHeight();
			
		}
		
		return new Dimension(width * 7 + distance * 6, height * 5 + distance * 4);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		int width = 0, height = 0;

		for (Component comp : parent.getComponents()) {
			if (constraint.get(comp).getRow() == 1 &&
					constraint.get(comp).getColumn() == 1) {
				continue;
			}
			
			if (comp.getMinimumSize().getWidth() > width) width = (int)comp.getPreferredSize().getWidth();
			if (comp.getMinimumSize().getHeight() > height) height = (int)comp.getPreferredSize().getHeight();
			
		}
		
		return new Dimension(width * 7 + distance * 6, height * 5 + distance * 4);
	}

	@Override
	public void layoutContainer(Container parent) {
		int width = (int) ((parent.getSize().getWidth() - parent.getInsets().left - parent.getInsets().right - 6 * distance) / 7);
		int height = (int) ((parent.getSize().getHeight() - parent.getInsets().top - parent.getInsets().bottom - 4 * distance) / 5);

		for (Component comp : parent.getComponents()) {
			RCPosition rcp = constraint.get(comp);

			if (rcp.getRow() == 1 && rcp.getColumn() == 1) {
				
				comp.setBounds(parent.getInsets().left, parent.getInsets().top, 5 * width + 4 * distance, height);
				
			} else {
				comp.setBounds(
					parent.getInsets().left + (rcp.getColumn() - 1) * width + (rcp.getColumn() - 1) * distance,
					parent.getInsets().top + (rcp.getRow() - 1) * height + (rcp.getRow() - 1) * distance,
					width,
					height
				);
			}
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (comp == null || constraints == null)
			throw new NullPointerException();

		RCPosition cons;
		if (constraints instanceof String) {
			cons = RCPosition.parse((String) constraints);
		} else if (constraints instanceof RCPosition) {
			cons = (RCPosition) constraints;

			if (cons.getRow() == 1 && (cons.getColumn() > 1 || cons.getColumn() < 6)) {
				// throw new IllegalArgumentException();
			}

			if (constraint.values().contains(cons)) {
				throw new CalcLayoutException();
			}
		} else {
			throw new IllegalArgumentException();
		}

		constraint.put(comp, cons);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		int width = 0, height = 0;

		for (Component comp : target.getComponents()) {
			if (constraint.get(comp).getRow() == 1 &&
					constraint.get(comp).getColumn() == 1) {
				continue;
			}
			
			if (comp.getMaximumSize().getWidth() > width) width = (int)comp.getMaximumSize().getWidth();
			if (comp.getMaximumSize().getHeight() > height) height = (int)comp.getMaximumSize().getHeight();
			
		}
		
		return new Dimension(width * 7 + distance * 6, height * 5 + distance * 4);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {

	}
	
}
