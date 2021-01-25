package hr.fer.oprpp1.hw08.jnotepadpp;

import java.net.http.WebSocket.Listener;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;
	
	private List<SingleDocumentModel> documents;
	private SingleDocumentModel current;
	private List<MultipleDocumentListener> listeners;
	private SingleDocumentListener sdl;
	
	public DefaultMultipleDocumentModel() {
		documents = new ArrayList<>();
		listeners = new ArrayList<>();
		
		// img
		
		sdl = new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				// promjena tab ikone
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				// promjena naziva prozora
			}
			
		};
	}

	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newDocument = new DefaultSingleDocumentModel(null, "");
		newDocument.addSingleDocumentListener(sdl);
		documents.add(newDocument);
		current = newDocument;
		add("unnamed", new JScrollPane(newDocument.getTextComponent()));
		
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
		
		
		current = new DefaultSingleDocumentModel(path, );
		
		documents.add(current);
		//addTab(current.getFilePath().getFileName().toString(), icon, new JScrollPane(current.getTextComponent()), tip);
		
		return current;
	}

	public void saveDocument(SingleDocumentModel model, Path newPath) {
		
		model.setModified(false);
	}

	public void closeDocument(SingleDocumentModel model) {
		if (model.isModified()) {
			JOptionPane.showOptionDialog(getParent(), "Document is not modified! Do you still want to close it?", 
				"Document unsaved!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, 
				null, null, JOptionPane.NO_OPTION);
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

	public List<SingleDocumentModel> getDocuments() {
		return documents;
	}

	public void setDocuments(List<SingleDocumentModel> documents) {
		this.documents = documents;
	}

	public SingleDocumentModel getCurrent() {
		return current;
	}

	public void setCurrent(SingleDocumentModel current) {
		this.current = current;
	}

	public List<MultipleDocumentListener> getListeners() {
		return listeners;
	}

	public void setListeners(List<MultipleDocumentListener> listeners) {
		this.listeners = listeners;
	}
	
	

}
