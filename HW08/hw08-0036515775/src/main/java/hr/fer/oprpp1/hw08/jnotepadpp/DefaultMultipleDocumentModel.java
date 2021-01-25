package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTabbedPane;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<SingleDocumentModel> documents;
	private SingleDocumentModel current;
	private List<MultipleDocumentListener> listeners;
	
	public DefaultMultipleDocumentModel() {
		
	}

	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newDocument = new DefaultSingleDocumentModel(null, "");
		documents.add(newDocument);
		
		return newDocument;
	}

	public SingleDocumentModel getCurrentDocument() {
		return current;
	}

	public SingleDocumentModel loadDocument(Path path) {
		for (SingleDocumentModel model : documents) {
			if (model.getFilePath().equals(path)) {
				current = model;
				return current;
			}
		}
		
		current = new DefaultSingleDocumentModel(path, "");
		documents.add(current);
		//addTab(current.getFilePath().getFileName().toString(), icon, current.getTextComponent(), tip);
		
		return current;
	}

	public void saveDocument(SingleDocumentModel model, Path newPath) {
		// TODO Auto-generated method stub
		
	}

	public void closeDocument(SingleDocumentModel model) {
		if (model.isModified()) {
			
		} else {
			documents.remove(model);
		}
		
	}

	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	public int getNumberOfDocuments() {
		return documents.size();
	}

	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new Iterator<SingleDocumentModel>() {
			
			int i = 0;

			@Override
			public boolean hasNext() {
				if (i >= documents.size()) {
					return false;
				} 
				return true;
			}

			@Override
			public SingleDocumentModel next() {
				return documents.get(i++);
			}
			
		};
	}

}
