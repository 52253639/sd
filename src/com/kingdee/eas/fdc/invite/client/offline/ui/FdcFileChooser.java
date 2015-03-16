package com.kingdee.eas.fdc.invite.client.offline.ui;

import java.awt.Dimension;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;

import com.kingdee.eas.fdc.invite.client.offline.util.ResourceHelper;

public class FdcFileChooser extends JFileChooser {

	private static final long serialVersionUID = 8315166753274837808L;

	private transient FileFilter fdcFileFilter = null;

	private transient FileView fdcFileView = null;
	
	public static String XlsFileExt = ".xls";
	public static String XlsFileExtDesc = "修改好的Excel文件(.xls)";
	public static String FDCFileExt = ".fdc";
	public static String FDCFileExtDesc = "房地产报价文件(*.fdc)";
	
	private String fileExt = FDCFileExt;
	private String fileExtDesc = FDCFileExtDesc;
	

	public FdcFileChooser() {
		this.setSize(new Dimension(290, 280));
	}
	
	public FdcFileChooser(String fileExt,String fileExtDesc) {
		this.setSize(new Dimension(290, 280));
		this.fileExt = fileExt;
		this.fileExtDesc = fileExtDesc;
	}

	public FdcFileChooser(String currentDirectoryPath,String fileExt,String fileExtDesc) {
		super(currentDirectoryPath);
		this.fileExt = fileExt;
		this.fileExtDesc = fileExtDesc;
	}

	public FdcFileChooser(File currentDirectory,String fileExt,String fileExtDesc) {
		super(currentDirectory);
		this.fileExt = fileExt;
		this.fileExtDesc = fileExtDesc;
	}

	public FdcFileChooser(FileSystemView fsv) {
		super(fsv);
	}

	public FdcFileChooser(File currentDirectory, FileSystemView fsv) {
		super(currentDirectory, fsv);
	}

	public FdcFileChooser(String currentDirectoryPath, FileSystemView fsv) {
		super(currentDirectoryPath, fsv);
	}

    public FileFilter getAcceptAllFileFilter() {
    	fdcFileFilter = new FileFilter() {
			public boolean accept(File f) {					
				return f.isDirectory() || f.getName().endsWith(getFileExt());
			}

			public String getDescription() {
				return getFileExtDesc();
			}
		};
		return fdcFileFilter;
	}

    public FileView getFileView() {
    	if (fdcFileView == null) {
	    	fdcFileView = new FileView() {
				public Icon getIcon(File f) {
					if (f.getName().endsWith(FDCFileExt))
						return ResourceHelper.getIcon("fdc_icon.gif");
					return null;
				}
			};
    	}
    	return fdcFileView;
    }

	public void approveSelection() {
		File file = this.getSelectedFile();
		
		if (file != null && !file.getName().endsWith(this.getFileExt())) {
			file = new File(file.getAbsolutePath() + this.getFileExt());
		}
		if (this.getDialogType() == JFileChooser.SAVE_DIALOG) {
			if (file != null && file.exists()) {
				boolean fOverwrite = JOptionPane.showConfirmDialog(this, "该文件已存在，确定要覆盖吗？", "保存提示对话框", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION;
				if (!fOverwrite)
					return;
				setSelectedFile(file);
				super.approveSelection();
			} else {
				setSelectedFile(file);
				super.approveSelection();
			}

		} else if (this.getDialogType() == JFileChooser.OPEN_DIALOG) {
			if (file != null && !file.exists()) {
				JOptionPane.showConfirmDialog(this, "此文件不存在，请重新选择文件", "错误提示", JOptionPane.DEFAULT_OPTION);
			} else {
				setSelectedFile(file);
				super.approveSelection();
			}
		}
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getFileExtDesc() {
		return fileExtDesc;
	}

	public void setFileExtDesc(String fileExtDesc) {
		this.fileExtDesc = fileExtDesc;
	}
}
