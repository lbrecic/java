package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	private JTextArea textArea;
	private Path filePath;
	private boolean modified;
	private List<SingleDocumentListener> listeners;
	
	public DefaultSingleDocumentModel(Path filePath, String textContent) {
		this.filePath = filePath;
		
		textArea = new JTextArea();
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
		textArea.setText(textContent);
		
		modified = false;
		
		listeners = new ArrayList<>();
	}

	public JTextArea getTextComponent() {
		return this.textArea;
	}

	public Path getFilePath() {
		return this.filePath;
	}

	public void setFilePath(Path path) {
		this.filePath = path;
	}

	public boolean isModified() {
		return this.modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
		for (SingleDocumentListener l : listeners) {
			l.documentModifyStatusUpdated(this);
		}
	}

	public void addSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.add(l);
	}

	public void removeSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.remove(l);
	}
}
