package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.Iterator;

import javax.swing.JTabbedPane;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	public Iterator<SingleDocumentModel> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public SingleDocumentModel createNewDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	public SingleDocumentModel getCurrentDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	public SingleDocumentModel loadDocument(Path path) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveDocument(SingleDocumentModel model, Path newPath) {
		// TODO Auto-generated method stub
		
	}

	public void closeDocument(SingleDocumentModel model) {
		// TODO Auto-generated method stub
		
	}

	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		// TODO Auto-generated method stub
		
	}

	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		// TODO Auto-generated method stub
		
	}

	public int getNumberOfDocuments() {
		// TODO Auto-generated method stub
		return 0;
	}

	public SingleDocumentModel getDocument(int index) {
		// TODO Auto-generated method stub
		return null;
	}

}
