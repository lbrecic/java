package hr.fer.oprpp1.hw08.jnotepadpp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJLabel;

public class StatusPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel docModel;
	private ILocalizationProvider flp;
	private Timer t;
	private DocumentListener l;
	private ChangeListener listener;

	public StatusPanel(DefaultMultipleDocumentModel docModel, ILocalizationProvider flp, Timer t) {
		super();
		
		this.docModel = docModel;
		this.flp = flp;
		this.t = t;
		
		setLayout(new GridLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		initGUI();
		
		docModel.addChangeListener(listener);
	}
	
	private void initGUI() {
		LJLabel label1 = new LJLabel("length", flp);
		label1.setHorizontalTextPosition(SwingConstants.LEFT);
		add(label1, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout());
		panel.setAlignmentX(LEFT_ALIGNMENT);
		addCaretPanel(panel);
		add(panel, BorderLayout.CENTER);

		JLabel label3 = new JLabel();
		label3.setAlignmentX(SwingConstants.RIGHT);
		label3.setHorizontalTextPosition(SwingConstants.RIGHT);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		t.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				label3.setText(LocalDateTime.now().format(formatter));

			}
		}, 0, 1000);

		add(label3, BorderLayout.EAST);

		l = new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				label1.setText(flp.getString("length" )+ " " + docModel.getCurrentDocument().getTextComponent().getText().length());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				label1.setText(flp.getString("length" )+ " " + docModel.getCurrentDocument().getTextComponent().getText().length());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				label1.setText(flp.getString("length" )+ " " + docModel.getCurrentDocument().getTextComponent().getText().length());
			}
		};

	}
	
	private void addCaretPanel(JPanel panel) {
		
		LJLabel line = new LJLabel("line", flp);
		LJLabel column = new LJLabel("col", flp);
		LJLabel select = new LJLabel("sel", flp);
		
		panel.add(line, BorderLayout.WEST);
		panel.add(column, BorderLayout.CENTER);
		panel.add(select, BorderLayout.EAST);

		listener = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (docModel.getSelectedIndex() >= 0) {
					docModel.setCurrent(docModel.getDocument(docModel.getSelectedIndex()));
					
				docModel.getCurrentDocument().getTextComponent().getDocument().addDocumentListener(l);
				docModel.getCurrentDocument().getTextComponent().addCaretListener(new CaretListener() {

					@Override
					public void caretUpdate(CaretEvent e) {
						int sel = 0;
						if (docModel.getCurrentDocument().getTextComponent().getSelectedText() != null) {
							sel = docModel.getCurrentDocument().getTextComponent().getSelectedText().length();
						} 

						int ln = 1, col = 1;
						try {
							int caretpos = docModel.getCurrentDocument().getTextComponent().getCaretPosition();
							ln = docModel.getCurrentDocument().getTextComponent().getLineOfOffset(caretpos);
							col = caretpos - docModel.getCurrentDocument().getTextComponent().getLineStartOffset(ln);
							ln += 1;
							col += 1;

						} catch (Exception ex) {
							ex.printStackTrace();
						}

						line.setText(flp.getString("line") + ": " + ln); 
						column.setText(flp.getString("col") + ": " + col);
						select.setText(flp.getString("sel") + ": " + sel);
					}

				});
				
				} else {
					line.setText(flp.getString("line") + ": "); 
					column.setText(flp.getString("col") + ": ");
					select.setText(flp.getString("sel") + ": ");
				}
			}
		};
	}

}
