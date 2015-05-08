package com.kingdee.eas.fdc.schedule.client;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FilterType extends FileFilter{

	public boolean accept(File f) {
		if(f.isDirectory()){
			return true;
		}else{
			return f.getName().endsWith(".mpp");
		}
	}

	public String getDescription() {
		return ".mpp";
	}

}
