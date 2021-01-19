package hr.fer.oprpp1.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

import hr.fer.oprpp1.gui.calc.model.CalcModel;
import hr.fer.oprpp1.gui.calc.model.CalcValueListener;
import hr.fer.oprpp1.gui.calc.model.CalculatorInputException;

public class CalcModelImpl implements CalcModel {

	private boolean editable;
	private boolean negative;
	private String data;
	private Double value;
	private String frozen;
	private Double activeOperand;
	private DoubleBinaryOperator pendingOperator;
	private List<CalcValueListener> listeners;
	private Stack<Double> stack;

	public CalcModelImpl() {
		editable = true;
		negative = false;
		data = "";
		value = 0.0;
		frozen = null;
		activeOperand = null;
		pendingOperator = null;
		listeners = new ArrayList<>();
		stack = new Stack<>();
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		return this.value;
	}

	@Override
	public void setValue(double value) {
		if (value < 0.0)
			this.negative = true;
		this.value = value;
		this.data = Double.toString(value);
		this.editable = false;
		
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}

	@Override
	public boolean isEditable() {
		return this.editable;
	}

	@Override
	public void clear() {
		this.value = null;
		this.data = "";
		this.editable = true;
	}

	@Override
	public void clearAll() {
		this.activeOperand = null;
		this.pendingOperator = null;
		this.editable = true;
		this.value = null;
		this.data = "";
		this.negative = false;
		
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!editable)
			throw new CalculatorInputException();

		frozen = null;

		if (value != 0.0) {
			if (negative) {
				data = data.substring(1);
			} else {
				data = "-" + data;
			}
		}
		
		if (!data.equals(""))
			value = Double.parseDouble(data);

		negative = !negative;
		
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!editable || data.equals(""))
			throw new CalculatorInputException();

		if (data.contains("."))
			throw new CalculatorInputException();

		data += ".";
		value = Double.parseDouble(data);
		frozen = null;
		
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!editable)
			throw new CalculatorInputException();
		
		if (Double.parseDouble(data + Integer.toString(digit)) > Double.MAX_VALUE)
				throw new CalculatorInputException();

		if (digit == 0 && data.equals("0"))
			return;
		if (digit != 0 && data.equals("0")) {
			data = "";
		}

		try {
			Double.parseDouble(data + Integer.toString(digit));
		} catch (Exception e) {
			throw new CalculatorInputException();
		}

		data = data + Integer.toString(digit);
		value = Double.parseDouble(data);
		frozen = null;
		
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet())
			throw new IllegalStateException();

		return this.activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		this.activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return this.pendingOperator;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperator = op;
	}

	@Override
	public String toString() {
		if (frozen != null) {
			return frozen;
		} else if (data.equals("") || data.equals("0")) {
			if (this.negative) {
				return "-0";
			} else {
				return "0";
			}
		} else if (value == Double.NEGATIVE_INFINITY) {
			return "-Infinity";
		} else if (value == Double.POSITIVE_INFINITY) {
			return "Infinity";
		} else if (value == Double.NaN) {
			return "NAN";
		} else {
			return data;
		}
	}

	public void freezeValue(String value) {
		this.frozen = value;
	}

	public boolean hasFrozenValue() {
		return frozen != null;
	}

	public Stack<Double> getStack() {
		return stack;
	}

}
