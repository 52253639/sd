package com.kingdee.eas.fdc.schedule.test;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.kingdee.bos.ctrl.swing.KDButton;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDList;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;
import com.kingdee.eas.fdc.schedule.test.ScheduleDemoData.ScheduleDataItem;

public class ScheduleDemoDataSelectUI{
	private final Frame parentUI;
	private final ScheduleDemoData data;
	public ScheduleDemoDataSelectUI (Frame parentUI){
		this.parentUI=parentUI;
		data=new ScheduleDemoData();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ScheduleDemoDataSelectUI ui=new ScheduleDemoDataSelectUI(null);
		ui.getDialog().setDefaultCloseOperation(KDDialog.DISPOSE_ON_CLOSE);
		ui.show();
	}
	
	public void show(){
		getDialog().setVisible(true);
	}
	private KDDialog diag;
	public KDDialog getDialog(){
		if(diag==null){
			init();
		}
		return diag;
	}
	
	private void init(){
		diag=new KDDialog(this.parentUI,"测试数据选择",true);
		diag.setSize(400, 300);
		FDCClientHelper.centerWindow(diag);
		diag.getContentPane().setLayout(new java.awt.BorderLayout());
		final KDList list=new KDList(getTestData().toArray());
		diag.getContentPane().add(new KDScrollPane(list),java.awt.BorderLayout.NORTH);
		
		final KDTextArea tx=new KDTextArea();
		diag.getContentPane().add(new KDScrollPane(tx),java.awt.BorderLayout.CENTER);
		JPanel downPanel=new JPanel();
		downPanel.setLayout(new BoxLayout(downPanel, BoxLayout.X_AXIS));
		
		KDButton button=new KDButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScheduleDataItem item=(ScheduleDataItem)list.getSelectedValue();
				retValue=item.getInfo();
				if(retValue!=null){
					tx.setText(retValue.toString());
				}
			}
		});
		button.setText("查看");
		downPanel.add(button);
		button=new KDButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScheduleDataItem item=(ScheduleDataItem)list.getSelectedValue();
				retValue=item.getInfo();
				diag.setVisible(false);
			}
		});
		button.setText("确定");
		downPanel.add(button);
		button=new KDButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				diag.setVisible(false);
			}
		});
		button.setText("取消");
		downPanel.add(button);
		
		diag.getContentPane().add(downPanel,java.awt.BorderLayout.SOUTH);
		
	}
	
	private ScheduleBaseInfo retValue;
	public ScheduleBaseInfo getRetValue(){
		return this.retValue;
	}
	protected List getTestData(){
		return data.getListItems();
	}
}
