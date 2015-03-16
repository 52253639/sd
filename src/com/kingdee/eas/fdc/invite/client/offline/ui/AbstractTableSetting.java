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

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDButton;
import com.kingdee.bos.ctrl.swing.KDNumberTextField;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;

abstract public class AbstractTableSetting extends JDialog {

	Logger logger = Logger.getLogger(AbstractTableSetting.class);

	protected KDTabbedPane tabbedPane = new KDTabbedPane();

	protected KDTable table = new KDTable();

	protected KDTable settingTable;

	protected JButton defaultButton = new KDButton("恢复默认");

	protected JButton OKButton = new KDButton("确定");

	protected JButton cancelButton = new KDButton("取消");

	protected KDNumberTextField height = new KDNumberTextField();

	boolean heightChanged = false;

	public AbstractTableSetting(JFrame frame, KDTable tbl) {
		super(frame, "表格设置", true);
		// setIconImage(ResourceTool.getImage("eas_32.gif"));
		settingTable = tbl;
		init();
		setActionListener();
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (size.width - this.getWidth()) / 2;
		int y = (size.height - this.getHeight()) / 2;
		this.setLocation(x, y);
		this.setResizable(false);
	}

	private void init() {
		this.setSize(430, 390);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.add(table, "常规");
		JPanel p = new JPanel();
		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());
		p.setLayout(new FlowLayout());
		p.add(new JLabel("行高  "));
		p.add(height);
		panel.add(p, BorderLayout.WEST);
		tabbedPane.add(panel, "其他");

		panel = new JPanel();
		panel.setLayout(new BorderLayout());

		p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(defaultButton);
		panel.add(p, BorderLayout.WEST);

		p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(OKButton);
		p.add(cancelButton);

		panel.add(p, BorderLayout.EAST);
		c.add(panel, BorderLayout.SOUTH);
		setTitle(null);
	}

	private void setActionListener() {
		defaultButton.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "setDefault"));
		OKButton.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "OK"));
		cancelButton.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "cancel"));
		height.addDataChangeListener(new DataChangeListener() {

			public void dataChanged(DataChangeEvent eventObj) {
				heightChanged();

			}

		});
		// height.addActionListener((ActionListener)
		// EventHandler.create(ActionListener.class, this, "height"));
	}

	abstract public void setDefault();

	abstract public void OK();

	abstract public void cancel();

	public void heightChanged() {
		this.heightChanged = true;
	}

	public void setTitle(String title) {
		super.setTitle("表格设置");
	}

}
