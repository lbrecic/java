package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	private JTextArea textContent;
	private Path filePath;
	private boolean modified;
	
	public DefaultSingleDocumentModel() {
		
	}

	public JTextArea getTextComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	public Path getFilePath() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setFilePath(Path path) {
		// TODO Auto-generated method stub
		
	}

	public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setModified(boolean modified) {
		// TODO Auto-generated method stub
		
	}

	public void addSingleDocumentListener(SingleDocumentListener l) {
		// TODO Auto-generated method stub
		
	}

	public void removeSingleDocumentListener(SingleDocumentListener l) {
		// TODO Auto-generated method stub
		
	}

}
