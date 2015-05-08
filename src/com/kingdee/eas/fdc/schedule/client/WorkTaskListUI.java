/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
//import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientCtrler;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientCtrler;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
//import com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI;
import com.kingdee.eas.fdc.schedule.WorkTaskFactory;
import com.kingdee.eas.fdc.schedule.WorkTaskInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class WorkTaskListUI extends AbstractWorkTaskListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(WorkTaskListUI.class);

	/**
	 * output class constructor
	 */
	public WorkTaskListUI() throws Exception {
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
		if (node.getUserObject() instanceof WorkTaskInfo) {
			WorkTaskInfo workTaskInfo = (WorkTaskInfo) node.getUserObject();
			String innerSQL = "select fid from t_sch_worktask where flongnumber like '"
				+workTaskInfo.getLongNumber()+"!%' or fid='"+workTaskInfo.getId().toString()+"'" ;
			filter.getFilterItems().add(new FilterItemInfo("id",innerSQL,CompareType.INNER));
		} 
		this.mainQuery.setFilter(filter);
//		tblMain.removeRows();// 触发新查询
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
		if(root==node){
			tblMain.removeRows();
			tblMain.getSelectManager().removeAll();
			return;
		}
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
	
	public void onLoad() throws Exception {
		super.onLoad();
		treeMain.setRootVisible(true);
		this.tblMain.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
			public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
				try {
					_tblMain_tableSelectChanged(e);
				} catch (Exception exc) {
					handUIException(exc);
				}
			}
		});
		menuBiz.setEnabled(true);
		actionQuery.setVisible(false);
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
	
	
	protected ITreeBase getTreeInterface() throws Exception {
		return WorkTaskFactory.getRemoteInstance();
	}
	protected void initWorkButton() {
		super.initWorkButton();
		actionPrint.setEnabled(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setEnabled(false);
		actionPrintPreview.setVisible(false);
		actionLocate.setEnabled(false);
		actionLocate.setVisible(false);
		actionCancel.setVisible(true);
		actionCancelCancel.setVisible(true);
		menuBiz.setVisible(true);
	}
	public SelectorItemCollection getSelectors() {
		return super.getSelectors();
	}
	
	protected String getEditUIName() {
		return WorkTaskEditUI.class.getName();
	}
	
	protected String getEditUIModal() {
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return openModel;
		}
		return UIFactoryName.MODEL;
	}
	
	protected String getRootName() {
		return EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,FDCBaseDataClientUtils.WORKTASK);
	}
//	新增操作
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_ADDNEW);
		super.actionAddNew_actionPerformed(e);
	}
//	删除操作
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = tblMain.getSelectManager().getActiveRowIndex();
		FDCSCHClientHelper.checkIsEnabled(tblMain, "工作任务已启用，不能删除！");
		if (FDCSCHClientHelper.isSystemDefaultData(activeRowIndex,tblMain)) {
			MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
			return;
		}
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_DELETE);
		super.actionRemove_actionPerformed(e);
	}
//	修改操作
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = tblMain.getSelectManager().getActiveRowIndex();
		FDCSCHClientHelper.checkIsEnabled(tblMain, "工作任务已启用，不能修改！");
		if (FDCSCHClientHelper.isSystemDefaultData(activeRowIndex,tblMain)) {
			MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
			return;
		}
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
		super.actionEdit_actionPerformed(e);
	}
//	禁用操作
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		if(actRowIdx < 0){
			FDCMsgBox.showWarning("请选中要操作的行！");
			SysUtil.abort();
		}
		String workTaskID = tblMain.getCell(actRowIdx,"id").getValue().toString();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id",workTaskID));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		if(WorkTaskFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showError("有下级未禁用，请先禁用下级！");
			SysUtil.abort();
		}
		setIsEnabled(false);
	}
//	启用操作
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		if(actRowIdx < 0){
			FDCMsgBox.showWarning("请选中要操作的行！");
			SysUtil.abort();
		}
		String workTaskID = tblMain.getCell(actRowIdx,"id").getValue().toString();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select t1.* from t_sch_worktask t1" +
				"	inner join t_sch_worktask t2 on t1.fid=t2.fparentid" +
				"	where t1.fisEnabled=0 and t2.fid=?");
		builder.addParam(workTaskID);
		IRowSet rowSet = builder.executeQuery();
		if(rowSet.size() >=1){
			FDCMsgBox.showError("上级未启用，请先启用上级！");
			SysUtil.abort();
		}
		setIsEnabled(true);
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
		WorkTaskInfo info = new WorkTaskInfo();
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