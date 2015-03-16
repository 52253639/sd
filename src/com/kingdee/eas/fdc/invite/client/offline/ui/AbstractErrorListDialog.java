package com.kingdee.eas.fdc.invite.client.offline.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.beans.EventHandler;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDButton;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;

public abstract class AbstractErrorListDialog extends JDialog {
	Logger logger = Logger.getLogger(AbstractErrorListDialog.class);
	protected KDTabbedPane tabbedPane = new KDTabbedPane();
	protected KDTable table = new KDTable();
	protected JButton OKButton = new KDButton("确定");
	public AbstractErrorListDialog(JFrame frame) {
		super(frame, "错误列表", true);
		// setIconImage(ResourceTool.getImage("eas_32.gif"));		
		init();
		setActionListener();
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (size.width - this.getWidth()) / 2;
		int y = (size.height - this.getHeight()) / 2;
		this.setLocation(x, y);
		this.setResizable(false);
	}
	private void init() {
		initTable();
		this.setSize(430, 390);
		Container c = getContentPane();
		c.setFocusable(false);
		c.setLayout(new BorderLayout());
		tabbedPane.add(table, "错误列表");
		
		JPanel p = new JPanel();
		
		p.setFocusable(false);
		p.setLayout(new BorderLayout());
		p.add(table, BorderLayout.CENTER);
		
		c.add(p, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		

		p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(OKButton);

		panel.add(p, BorderLayout.EAST);
		c.add(panel, BorderLayout.SOUTH);		
		setTitle(null);
	}
	private void initTable(){
		this.table = new KDTable();
		IColumn column = table.addColumn();
		table.getStyleAttributes().setWrapText(true);
		column.setKey("errormsg");
		column.setWidth(375);
		column.setResizeable(false);
		IRow row = table.addHeadRow();
		row.getCell("errormsg").setValue("错误说明");
		table.getStyleAttributes().setLocked(true);
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
	private void setActionListener() {
		OKButton.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "OK"));
	}
	abstract public void OK();
	public void setTitle(String title) {
		super.setTitle("错误列表");
	}
}
