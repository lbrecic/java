package hr.fer.oprpp1.gui;

public class RCPosition {
	
	private int row, column;

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public RCPosition(int row, int column) {
		if (row < 1 || row > 5 || column < 1 || column > 7 || (row == 1 && column > 1 && column < 6)) {
			throw new CalcLayoutException();
		}
		
		this.row = row;
		this.column = column;
	}

	public static RCPosition parse(String text) {
		int r, c;
		String[] args = text.split(", ");
		
		r = Integer.parseInt(args[0]);
		c = Integer.parseInt(args[1]);
		
		return new RCPosition(r, c);
	}
}
