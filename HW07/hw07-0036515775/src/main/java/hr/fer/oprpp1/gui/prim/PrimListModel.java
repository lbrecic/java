package hr.fer.oprpp1.gui.prim;

import java.util.List;
import java.util.ArrayList;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimListModel implements ListModel<Integer> {
	
	private List<ListDataListener> listeners;
	private List<Integer> elements;
	private boolean isPrim;
	private boolean flag;
	
	public PrimListModel() {
		listeners = new ArrayList<>();
		elements = new ArrayList<>();
		elements.add(1);
		isPrim = false;
		flag = true;
	}
	
	public void next() {
		int prim = elements.get(elements.size() - 1);
		
		while (!isPrim) {
			prim += 1;
			for (int i = 2; i < prim; i++) {
				if (prim % i == 0) {
					flag = false;
					break;
				}
			}
			
			if (flag) {
				isPrim = true;
			}
			
			flag = true;
		}
		
		isPrim = false;
		elements.add(prim);
		
		for (ListDataListener l : listeners) {
			l.intervalAdded(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, elements.size() - 1, elements.size() - 1));
		}
	}

	@Override
	public int getSize() {
		return elements.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return elements.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}
	
}
