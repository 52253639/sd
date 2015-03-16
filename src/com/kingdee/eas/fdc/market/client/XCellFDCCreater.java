package com.kingdee.eas.fdc.market.client;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.kingdee.eas.fdc.market.DocumentSubjectInfo;

public class XCellFDCCreater implements XCellCreaterInterface {

	public XJPanel[] createXCell(List buttonAndTestList,DocumentSubjectInfo doc, int width) {
		XJPanel[] jp = new XJPanel[1];
		jp[0] = new XJPanel(new BorderLayout());
		jp[0].setSize(width,50);
		jp[0].add(BorderLayout.CENTER,new JButton("Button"));
		return jp;
	}
}
