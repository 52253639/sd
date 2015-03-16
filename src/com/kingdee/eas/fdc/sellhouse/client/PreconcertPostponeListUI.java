/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.security.util.SystemUtils;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.PreconcertPostponeFactory;
import com.kingdee.eas.fdc.sellhouse.PreconcertPostponeInfo;
import com.kingdee.eas.fdc.sellhouse.SellOrderInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.Uuid;

/**
 * PreconcertPostponeListUI
 * 
 * @author can_liu
 */
public class PreconcertPostponeListUI extends AbstractPreconcertPostponeListUI {
	
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	
	public PreconcertPostponeListUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
	}
	
	private void initButtonState() {
		actionAddNew.setEnabled(false);
		actionCreateTo.setVisible(false);
		actionTraceUp.setVisible(false);
		actionTraceDown.setVisible(false);
		actionWorkFlowG.setVisible(false);
		actionAuditResult.setVisible(false);
		actionQuery.setVisible(false);
		actionLocate.setVisible(false);
		actionAttachment.setVisible(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		actionAudit.setEnabled(true);
		actionUnAudit.setEnabled(true);
		actionEdit.setEnabled(true);
		actionRemove.setEnabled(true);
		
		this.menuBiz.setVisible(false);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		initButtonState();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
	
	protected void initTree() throws Exception {
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		
		this.treeMain.setModel(SHEHelper.getPlanisphere(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));		
		
		this.treeMain.expandOnLevel(3);		
		
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
	}
	
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof SellOrderInfo) {
			SellOrderInfo building = (SellOrderInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("sellOrder.id", building.getId().toString()));
			this.actionAddNew.setEnabled(false);
		} 
		else if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProjectInfo = (SellProjectInfo) node.getUserObject();

			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectInfo.getId().toString()));
			this.actionAddNew.setEnabled(false);
		} 
		else if(node.getUserObject() instanceof BuildingInfo){
			BuildingInfo buildingInfo = (BuildingInfo) node.getUserObject();

			filter.getFilterItems().add(new FilterItemInfo("building.id", buildingInfo.getId().toString()));
			this.actionAddNew.setEnabled(true);
		}
		else if(node.getUserObject() instanceof BuildingUnitInfo){
			BuildingUnitInfo buildingUnitInfo = (BuildingUnitInfo) node.getUserObject();

			filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildingUnitInfo.getId().toString()));
			this.actionAddNew.setEnabled(true);
		}
		else if(!node.isRoot()){			
			filter.getFilterItems().add(new FilterItemInfo("id", null));
			this.actionAddNew.setEnabled(false);
		}
		this.mainQuery.setFilter(filter);
		this.tblMain.removeRows();
		this.execQuery();
	}

	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}
	
	protected void execQuery() {
		super.execQuery();
	}

	//审批
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		PreconcertPostponeInfo info = getSelectInfo();
		if(info.getState().equals(FDCBillStateEnum.SUBMITTED)){			
			PreconcertPostponeFactory.getRemoteInstance().audit(BOSUuid.read(getSelsectRowId()));
			refreshList();
			MsgBox.showInfo("单据审批成功！");
		}else{			
			MsgBox.showInfo("单据状态不符合要求！");
		}
	}
	
	//反审批
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();	
		PreconcertPostponeInfo info = getSelectInfo();
		if(info.getState().equals(FDCBillStateEnum.AUDITTED)){				
			PreconcertPostponeFactory.getRemoteInstance().unAudit(BOSUuid.read(getSelsectRowId()));
			refreshList();
			MsgBox.showInfo("单据反审批成功！");
		}else{			
			MsgBox.showInfo("单据状态不符合要求！");
		}
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();	
		PreconcertPostponeInfo info = getSelectInfo();
		if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
			MsgBox.showInfo("单据已审批,不能删除！");
			SysUtil.abort();
		}		
		super.actionRemove_actionPerformed(e);
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();	
		PreconcertPostponeInfo info = getSelectInfo();
		if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
			MsgBox.showInfo("单据已审批,不能编辑！");
			SysUtil.abort();
		}
		
		super.actionEdit_actionPerformed(e);
	}
	
	//返回选中的行
	private PreconcertPostponeInfo getSelectInfo() throws EASBizException, BOSException{
		PreconcertPostponeInfo info = PreconcertPostponeFactory.getRemoteInstance().
		getPreconcertPostponeInfo( new ObjectUuidPK(getSelsectRowId()));
		return info;
	}
	
	//获取选 中的行ID
	public String getSelsectRowId(){
		IRow selectRow = KDTableUtil.getSelectedRow(tblMain);
		return selectRow.getCell("id").getValue().toString();
	}
	
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
	}

	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PreconcertPostponeFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		PreconcertPostponeInfo objectValue = new PreconcertPostponeInfo();

		return objectValue;
	}

	protected String getEditUIName() {
		return PreconcertPostponeEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
	
	/**
	 * 在填入表格之前做预处理。
	 * add by warship 
	 */
	protected void getRowSetBeforeFillTable(IRowSet rowSet) {
		try {
			rowSet.beforeFirst();
			while (rowSet.next()) {
				Date nowavailab = rowSet.getDate("nowavailab");
				Date originalAvailab = rowSet.getDate("originalAvailab");
				// 这三个字段在Query中使用toChar转换日期格式在DB2中不兼容，所以Query中不能硬拼接，改在这里转换格式
				rowSet.updateInt("proLong", FDCDateHelper.getDiffDays(nowavailab, originalAvailab));
			}
			rowSet.beforeFirst();
		} catch (SQLException e) {
			this.handleException(e);
		}
	}

}