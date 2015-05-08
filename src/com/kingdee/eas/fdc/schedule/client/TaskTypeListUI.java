/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientCtrler;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.schedule.TaskTypeFactory;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TaskTypeListUI extends AbstractTaskTypeListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(TaskTypeListUI.class);

	/**
	 * output class constructor
	 */
	public TaskTypeListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(node == null) return;
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof TaskTypeInfo) {
			TaskTypeInfo taskTypeinfo = (TaskTypeInfo) node.getUserObject();
			String innerSQL = "select fid from t_sch_tasktype where flongnumber like '"
				+taskTypeinfo.getLongNumber()+"!%' or fid='"+taskTypeinfo.getId().toString()+"'";
			filter.getFilterItems().add(new FilterItemInfo("id",innerSQL,CompareType.INNER));
		} 
		this.mainQuery.setFilter(filter);
//		tblMain.removeRows();// 触发新查询
		this.refresh(null);
	}
	
	protected void refresh(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.refresh(e);
		execQuery();
	}
	
	protected void execQuery() {
		// TODO Auto-generated method stub
		super.execQuery();
		tblMain.removeRows();
		SwingUtilities.invokeLater(new Runnable(){
    		public void run() {
    			if(tblMain.getRowCount()>0){
    				try {
						initTree();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				tblMain.getSelectManager().select(0, 0);//.setActiveRowIndex(0);
    			}
    		}
    	});
	}
	protected ITreeBase getTreeInterface() throws Exception {
		return TaskTypeFactory.getRemoteInstance();
	}

	protected void initWorkButton() {
		super.initWorkButton();
		actionPrint.setEnabled(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setEnabled(false);
		actionPrintPreview.setVisible(false);
		actionLocate.setEnabled(false);
		actionLocate.setVisible(false);
		actionCancel.setEnabled(true);
		actionCancel.setVisible(true);
		actionCancelCancel.setEnabled(true);
		actionCancelCancel.setVisible(true);
	}



	protected String getRootName() {
		return EASResource.getString(
				FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,
				FDCBaseDataClientUtils.TASKTYPE);
	}

	protected String getEditUIName() {
		return TaskTypeEditUI.class.getName();
	}

	protected String getEditUIModal() {
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return openModel;
		}
		return UIFactoryName.MODEL;
	}

	public SelectorItemCollection getSelectors() {
		return super.getSelectors();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		treeMain.setRootVisible(false);
		this.tblMain.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
			public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
				try {
					_tblMain_tableSelectChanged(e);
				} catch (Exception exc) {
					handUIException(exc);
				}
			}
		});
		menuBiz.setVisible(true);
		menuBiz.setEnabled(true);
		treeMain.expandAllNodes(true, (TreeNode)treeMain.getModel().getRoot());
	}
//	点击表格行时激活的事件
	final protected void _tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex != -1) {
			if (this.tblMain.getRow(activeRowIndex).getCell("isEnabled") != null) {
				boolean status = ((Boolean) this.tblMain.getCell(activeRowIndex, "isEnabled").getValue()).booleanValue();
				actionCancelCancel.setEnabled(!status);
				actionCancel.setEnabled(status);
			}
		} else {
			actionCancelCancel.setEnabled(false);
			actionCancel.setEnabled(false);
		}
	}
//	新增操作
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_ADDNEW);
//		super.actionAddNew_actionPerformed(e);
	}
//	修改操作
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(activeRowIndex < 0) return;
		FDCSCHClientHelper.checkIsEnabled(tblMain, "任务专业属性已启用，不能修改！");
		if (FDCSCHClientHelper.isSystemDefaultData(activeRowIndex,tblMain)) {
			MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
			return;
		}
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
//		super.actionEdit_actionPerformed(e);
	}
//	删除操作
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(activeRowIndex < 0) return;
		FDCSCHClientHelper.checkIsEnabled(tblMain, "任务专业属性已启用，不能删除！");
		if (FDCSCHClientHelper.isSystemDefaultData(activeRowIndex,tblMain)) {
			MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
			return;
		}
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_DELETE);
//		super.actionRemove_actionPerformed(e);
	}
//	禁用操作
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
//		setIsEnabled(false);
	}
//	启用操作
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
//		setIsEnabled(true);
	}
	
	protected void setIsEnabled(boolean flag) throws Exception {
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		// 不允许操作系统预设数据
		if (!flag)
			if (FDCSCHClientHelper.isSystemDefaultData(activeRowIndex,tblMain)) {
				MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
				return;
			}
		String id = tblMain.getRow(activeRowIndex).getCell("id").getValue().toString().trim();
		TaskTypeInfo info = new TaskTypeInfo();
		info.setId(BOSUuid.read(id));
		info.setIsEnabled(flag);
		//便于启用、禁用时得到编码及名称
		String number = tblMain.getRow(activeRowIndex).getCell("number").getValue().toString().trim();
		String name = tblMain.getRow(activeRowIndex).getCell("name").getValue().toString().trim();
		info.setNumber(number);
		info.setName(name);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("isEnabled"));
		String message = null;
		if (flag) {
			getBizInterface().updatePartial(info, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK");
		}
		else {
			getBizInterface().updatePartial(info, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK");
		}
		setMessageText(message);
		showMessage();
		tblMain.refresh();
		loadFields();
	}
//	房地产基础数据客户端帮助类
	private FDCBaseDataClientCtrler ctrler=null;
	protected FDCBaseDataClientCtrler getCtrler(){
		if(ctrler==null){
			try {
				ctrler=new FDCBaseDataClientCtrler(this,getBizInterface());
			} catch (Exception e) {
				this.handUIExceptionAndAbort(e);
			}
		}
		return ctrler;
	}
}