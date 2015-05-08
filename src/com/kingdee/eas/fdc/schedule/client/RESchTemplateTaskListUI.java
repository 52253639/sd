/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class RESchTemplateTaskListUI extends AbstractRESchTemplateTaskListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RESchTemplateTaskListUI.class);

	/**
	 * output class constructor
	 */
	public RESchTemplateTaskListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	
	
	protected void bpRESchTemplate_dataChanged(DataChangeEvent e)
			throws Exception {
		RESchTemplateInfo tmpInfo = (RESchTemplateInfo) this.bpRESchTemplate.getValue();
		GetAllData(tmpInfo);
		
//		this.tblMain.setRowCount(10000); 
//		super.bpRESchTemplate_dataChanged(e);
	}
	 /**
	  * 屏蔽UIToolBar
	  */
	public void initUIToolBarLayout() {
		
	}
	public void initUIMenuBarLayout() {
	}
	protected void initWorkButton() {
		super.initWorkButton();
		this.btnAddNew.setVisible(false);
		this.btnAddNew.setEnabled(false);
		this.btnAttachment.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnLocate.setVisible(false);
		this.btnPageSetup.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnQuery.setVisible(false);
		this.btnQueryScheme.setVisible(false);
		this.btnRefresh.setVisible(false);
		this.btnView.setVisible(false);
		this.btnOk.setEnabled(true);
		this.btnOk.setVisible(true);
		this.btnCancel1.setEnabled(true);
		this.btnCancel1.setVisible(true);
	
		
	}
	 
	public void onLoad() throws Exception {
		
		super.onLoad();
		tblMain.removeRows(false);
		boundEvent();
		tblMain.getColumn("name").setWidth(350);
		this.toolBar.setVisible(false);
		this.toolBar.setSize(0, 0);
		getUICustomToolBar().removeAll();
	}
	/**
	 * 本方法主要实现查询后将名称列设置单元格树<br>
	 * 1、取得原名称<br>
	 * 2、取得树级次和是否明细节点<br>
	 * 3、构建单元格树节点并set到单元格<br>
	 * 4、最后添加所有任务行
	 * * @description 查看数据
	 * @author 车忠伟
	 * @createDate 2011-8-13
	 * @version EAS7.0
	 * @see
	 */
	public void onShow() throws Exception {
		super.onShow();
		int count = this.tblMain.getRowCount();
		for (int i = 0; i < count; i++) {
			IRow row = this.tblMain.getRow(i);
			String value = (String) row.getCell("name").getValue();
			boolean isLeaf = ((Boolean) row.getCell("isLeaf").getValue())
					.booleanValue();
			int level = ((Integer) row.getCell("level").getValue()).intValue();

			String longNumber = row.getCell("longNumber").getValue().toString();
			longNumber = longNumber.replaceAll("!", ".");
			row.getCell("longNumber").setValue(longNumber);

			CellTreeNode treeNode = new CellTreeNode();
			treeNode.setValue(value);// 显示的值，此处是‘任务名称’字符串
			treeNode.setTreeLevel(level);// 级次，从0开始，此处为任务的树级次
			treeNode.setHasChildren(!isLeaf);
			treeNode.isCollapse();
			treeNode.addClickListener(new NodeClickListener() {
				public void doClick(CellTreeNode source, ICell cell, int type) {
					tblMain.revalidate();
				}
			});

			row.getStyleAttributes().setLocked(true);
			row.getCell("name").getStyleAttributes().setLocked(false);
			row.getCell("name").setValue(treeNode);// 设置前面构建的单元格树到表

		}
		IRow indexRow = tblMain.addRow(0);
		indexRow.getCell("name").setValue("所有任务");
		tblMain.setRowCount(tblMain.getBody().getRows().size());
		
	}
    
	/**
	 * 
	 * @description 得到模板下的所有数据
	 * @author 车忠伟
	 * @createDate 2011-8-9
	 * @version EAS7.0
	 * @see
	 */
	 private void GetAllData(RESchTemplateInfo template)throws Exception{
		 List taskList = new ArrayList();
		 if (template != null) {
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add(new SelectorItemInfo("*"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("template.id", template.getId().toString()));
			view.setFilter(filter);

			SorterItemInfo sii = new SorterItemInfo();
			sii.setPropertyName("longNumber");
			sii.setSortType(SortType.ASCEND);
			view.getSorter().add(sii);

			RESchTemplateTaskCollection collection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
			for (int i = 0; i < collection.size(); i++) {
				taskList.add(collection.get(i));
			}

		}
		setTblMainData(taskList);
	 }
//	 /**
//	  * 清空tblMain的数据
//	  */
//	 public void cleanTblMainData(){
//		tblMain.removeRows();
//	 }
	 /**
		 * 本方法主要实现选择模板后重新将名称列设置单元格树
		 * @description 选择模板后重新设置表格中的数据
		 * @author 车忠伟
		 * @createDate 2011-8-9
		 * @version EAS7.0
		 * @see
		 */
	 private void setTblMainData(List taskList) {
		// 清空表数据
		tblMain.removeRows(false);
		tblMain.addRows(taskList.size());
		tblMain.setRowCount(taskList.size() + 1);
		// if(taskSet.size()==0){
		// FDCMsgBox.showWarning("您选择模板下没有有数据，请重新选择计划模板");
		// return;
		//				
		// }
		for (int k = 0; k < taskList.size(); k ++) {
			RESchTemplateTaskInfo taskInfo = (RESchTemplateTaskInfo) taskList.get(k);
			IRow row = this.tblMain.getRow(k);
			row.getCell("id").setValue(taskInfo.getId().toString());
			row.getCell("name").setValue(taskInfo.getName());
			row.getCell("isLeaf").setValue(Boolean.valueOf(taskInfo.isIsLeaf()));
			row.getCell("level").setValue(taskInfo.getLevel() + "");
			row.getCell("longNumber").setValue(taskInfo.getLongNumber());
			CellTreeNode treeNode = new CellTreeNode();
			treeNode.setValue(taskInfo.getName());// 显示的值，此处是‘任务名称’字符串
			treeNode.setTreeLevel(taskInfo.getLevel());// 级次，从0开始，此处为任务的树级次
			treeNode.setHasChildren(!taskInfo.isIsLeaf());
			// treeNode.isCollapse();
			treeNode.addClickListener(new NodeClickListener() {
				public void doClick(CellTreeNode source, ICell cell, int type) {
					tblMain.revalidate();
				}
			});

			row.getStyleAttributes().setLocked(true);
			row.getCell("name").getStyleAttributes().setLocked(false);
			row.getCell("name").setValue(treeNode);// 设置前面构建的单元格树到表

		}
		IRow indexRow = tblMain.addRow(0);
		indexRow.getCell("name").setValue("所有任务");
		tblMain.setRowCount(tblMain.getBody().getRows().size());
		
	}
	 /**
		 * 
		 * @description 屏蔽表格不能点击
		 * @author 车忠伟
		 * @createDate 2011-8-9
		 * @version EAS7.0
		 * @see
		 */
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
	}
	
	public void boundEvent() throws Exception {

		RESchTemplateTreeSelector selector = new RESchTemplateTreeSelector();
		this.bpRESchTemplate.setSelector(selector);
		if (bpRESchTemplate.getValue() instanceof RESchTemplateInfo) {
			RESchTemplateInfo schTemplateInfo = (RESchTemplateInfo) bpRESchTemplate.getValue();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("template.id", schTemplateInfo.getId());
			view.setFilter(filter);
			RESchTemplateTaskCollection rESchTemplateTaskCollection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
			this.tblMain.setSize(195, rESchTemplateTaskCollection.size());
		}
		
		destroyWindow();
		
	}
	/**
	 * 
	 * @description 响应选择模板界面的确定按钮
	 * @author 车忠伟
	 * @createDate 2011-8-9
	 * @version EAS7.0
	 * @see
	 */
	public void actionOK_actionPerformed(ActionEvent e) throws Exception {
		if(this.bpRESchTemplate.getText()==null||"".equals(this.bpRESchTemplate.getText().trim())){
			 FDCMsgBox.showWarning("请先选择模板，才能导入");
			 return;
		}
		else{
			RESchTemplateInfo template=(RESchTemplateInfo) this.bpRESchTemplate.getValue();
			String templateID=template.getId().toString();
			
			if(this.getUIContext().get("standatrdUI") instanceof StandardTaskGuideNewListUI){
				EntityViewInfo view =new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("template.id",templateID));
				view.setFilter(filter);
				RESchTemplateTaskCollection collection=RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
				if(collection.size()==0){
					FDCMsgBox.showWarning("您选择的模板下面没有数据，请重新选择");
					return;
				}
				StandardTaskGuideNewListUI standardTaskGuideNewListUI =(StandardTaskGuideNewListUI)this.getUIContext().get("standatrdUI");
				standardTaskGuideNewListUI.templateImport(templateID);
				destroyWindow();
			}
//			getUIContext().put("templateID", templateID);
			
			 /* 存入选择的模板id */
			if (bpRESchTemplate.getValue() instanceof RESchTemplateInfo) {
				RESchTemplateInfo schTemplateInfo = (RESchTemplateInfo) bpRESchTemplate.getValue();
				/* 调用导入模板方法 */
				if (getUIContext().get("RESchTemplateListUI") instanceof RESchTemplateListUI) {
					RESchTemplateListUI ui = (RESchTemplateListUI) getUIContext().get("RESchTemplateListUI");
					ui.importTemplate(String.valueOf(null == schTemplateInfo.getId() ? "" : schTemplateInfo.getId().toString()));
					destroyWindow();
				}
			}
		}
		
	}
 
	public void actionNo_actionPerformed(ActionEvent e) throws Exception {
		destroyWindow();
	}
   
	protected ICoreBase getBizInterface() throws Exception {

		return RESchTemplateTaskFactory.getRemoteInstance();

	}

	protected String getEditUIName() {
		return null;
	}
	
	
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
	}
	protected void fillFirstData(RequestRowSetEvent arg0) {
		super.fillFirstData(arg0);
		
	}
	
	/**
	 * 不进行cu隔离
	 */
	protected boolean isIgnoreCUFilter() { 
    	return true;
    }
}