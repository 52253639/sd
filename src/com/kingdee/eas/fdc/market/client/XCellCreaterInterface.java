package com.kingdee.eas.fdc.market.client;

import java.util.List;

import javax.swing.JPanel;

import com.kingdee.eas.fdc.market.DocumentSubjectInfo;

public interface XCellCreaterInterface {
	XJPanel[] createXCell(List buttonAndTestList,DocumentSubjectInfo doc,int width);
}
