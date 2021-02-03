package hr.fer.oprpp1.hw08.jnotepadpp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;
	
	private List<SingleDocumentModel> documents;
	private SingleDocumentModel current;
	private List<MultipleDocumentListener> listeners;
	private SingleDocumentListener sdl;
	private ImageIcon unmodified;
	private ImageIcon modified;
	
	public DefaultMultipleDocumentModel() throws IOException {
		documents = new ArrayList<>();
		listeners = new ArrayList<>();
		
		unmodified = getIcons("unmodified.png");
		modified = getIcons("modified.png");
		
		sdl = new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				if (model.isModified()) {
					setIconAt(getSelectedIndex(), modified);
				} else {
					setIconAt(getSelectedIndex(), unmodified);
				}
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				if (getCurrentDocument().getFilePath() == null) {
					getCurrentDocument().setFilePath(model.getFilePath());
				} else {
					setTitleAt(getSelectedIndex(), getCurrentDocument().getFilePath().getFileName().toString()); 
				}
			}
			
		};
	}

	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newDocument = new DefaultSingleDocumentModel(null, "");
		newDocument.addSingleDocumentListener(sdl);
		documents.add(newDocument);
		setCurrent(newDocument);
		addTab("unnamed", unmodified, new JScrollPane(newDocument.getTextComponent()));
		
		for (MultipleDocumentListener mdl : listeners) {
			mdl.documentAdded(current);
		}
		
		return newDocument;
	}

	public SingleDocumentModel getCurrentDocument() {
		return current;
	}

	public SingleDocumentModel loadDocument(Path path) {
		for (SingleDocumentModel model : documents) {
			if (model.getFilePath() != null && model.getFilePath().equals(path)) {
				
				for (MultipleDocumentListener mdl : listeners) {
					mdl.documentAdded(model);
				}
				
				setCurrent(model);
				
				return current;
			}
		}
		
		String text = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile())));
			
			String buff = br.readLine();
			while (buff != null) {
				text += buff;
				buff = br.readLine();
			}
			
			br.close();	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DefaultSingleDocumentModel model = new DefaultSingleDocumentModel(path, text);
		model.addSingleDocumentListener(sdl);
		setCurrent(model);
		documents.add(current);
		addTab(current.getFilePath().getFileName().toString(), unmodified, new JScrollPane(current.getTextComponent()));
		
		for (MultipleDocumentListener mdl : listeners) {
			mdl.documentAdded(current);
		}
		
		return current;
	}

	public void saveDocument(SingleDocumentModel model, Path newPath) {
		String text = model.getTextComponent().getText();
		
		try {
			Files.write(newPath, text.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		model.setFilePath(newPath);
		model.setModified(false);
		
		for (MultipleDocumentListener mdl : listeners) {
			mdl.currentDocumentChanged(model, model);
		}
	}

	public void closeDocument(SingleDocumentModel model) {
		if (model.isModified()) {
			JOptionPane.showOptionDialog(getParent(), "Document is modified! Do you still want to close it?", 
				"Document unsaved!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, 
				null, null, JOptionPane.NO_OPTION);
		} else {
			
			for (MultipleDocumentListener mdl : listeners) {
				mdl.documentRemoved(model);
			}

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
	
	public ImageIcon getIcons(String iconName) throws IOException {
		InputStream is = this.getClass().getResourceAsStream("./icons/" + iconName);
		if (is == null) throw new IOException();
		byte[] bytes = is.readAllBytes();
		is.close();
		return new ImageIcon(bytes);
	}

}
