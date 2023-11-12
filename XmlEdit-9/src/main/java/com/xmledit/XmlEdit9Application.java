package com.xmledit;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.xml.transform.TransformerFactoryConfigurationError;

public class XmlEdit9Application extends Modify{
	public static void main(String[] args) throws TransformerFactoryConfigurationError, Exception {
		
		Modify m = new Modify();
		
		JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		
	    j.setAcceptAllFileFilterUsed(false);

	    j.setDialogTitle("Select a .xls file");

	    FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .xls files", "xls");
	    j.addChoosableFileFilter(restrict);
		
		int r = j.showSaveDialog(null);
		
		if (r == JFileChooser.APPROVE_OPTION) {
			File myObj = new File(j.getSelectedFile().getAbsolutePath());
			String message =    m.Entry(myObj);
			JOptionPane.showMessageDialog(null, message);
		}
   }
}



