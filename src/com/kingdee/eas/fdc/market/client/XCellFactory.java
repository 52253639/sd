package com.kingdee.eas.fdc.market.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import com.kingdee.eas.fdc.market.DocumentSubjectInfo;

public class XCellFactory {
	
	private static Map createrMap = new HashMap();
	
	public static JPanel[] createXCell(List buttonAndTestList,DocumentSubjectInfo doc,int width) throws Exception{
		XCellCreaterInterface creater = (XCellCreaterInterface)createrMap.get(doc.getXCellCreter());
		if(creater == null){
			creater = implementClass(doc.getXCellCreter());
			createrMap.put(doc.getXCellCreter(), creater);
		}
		return creater.createXCell(buttonAndTestList,doc, width);
	}
	
	public static XCellCreaterInterface implementClass(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return (XCellCreaterInterface)Class.forName(className).newInstance();
	}
	
}
