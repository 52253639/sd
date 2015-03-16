/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.math.BigDecimal;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.RentAdjustEntrysCollection;
import com.kingdee.eas.fdc.tenancy.RentAdjustEntrysFactory;
import com.kingdee.eas.fdc.tenancy.RentAdjustEntrysInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRentAdjustFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRentAdjustInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRentBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRentBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TenancyRentAdjustListUI extends AbstractTenancyRentAdjustListUI
{
	private static final Logger logger = CoreUIObject.getLogger(TenancyRentAdjustListUI.class);
	//组织单元
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	
	/**
	 * output class constructor
	 */
	public TenancyRentAdjustListUI() throws Exception
	{
		super();
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
	protected String getEditUIName() {
		return TenancyRentAdjustEditUI.class.getName();
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}
	
	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		initTree();
		this.treeMain.setSelectionRow(0);
		this.menuItemCreateTo.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.menuItemCopyTo.setVisible(false);
		this.menuItemTraceUp.setVisible(false);
		this.menuItemTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
//		this.actionAttachment.setVisible(false);
		this.actionAttachment.setVisible(true);
		this.btnUnAudit.setVisible(false);
		this.btnExecute.setEnabled(true);
		this.menuItemExecut.setEnabled(true);
		this.actionAuditResult.setVisible(false);
	}
	
	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return TenancyRentAdjustFactory.getRemoteInstance();
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof String) {
			MsgBox.showInfo("请选择具体销售项目！");
			this.abort();
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
			.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
		super.prepareUIContext(uiContext, e);
	}
	
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
	throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		
		if (node.getUserObject() instanceof SellProjectInfo) {
			if (this.saleOrg.isIsBizUnit()) {
				this.actionAddNew.setEnabled(true);
			}
		} else {
			this.actionAddNew.setEnabled(false);
		}
		this.execQuery();
	}
	
	/**
	 *查询过滤条件
	 * */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
			.getLastSelectedPathComponent();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			
			FilterInfo filter = new FilterInfo();
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems()
				.add(
						new FilterItemInfo("project.id", pro.getId()
								.toString()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}
			
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	/**
	 *调阻单审核事件
	 * */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("state")
		.getValue();
		if (FDCBillStateEnum.SAVED_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("调租单没有提交!");
			return;
		}
		if (FDCBillStateEnum.AUDITTED_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("调租单已经审核!");
			return;
		}
		if (FDCBillStateEnum.INVALID_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("调租单已经作废!");
			return;
		}
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		TenancyRentAdjustFactory.getRemoteInstance().audit(BOSUuid.read(id));
		this.refresh(null);
		
	}
	
	/**
	 *调阻单执行
	 * */
	public void actionExecute_actionPerformed(ActionEvent e) throws Exception {
		super.actionExecute_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		Boolean isExecuted = (Boolean) row.getCell("isExecuted").getValue();
		if (isExecuted.booleanValue()) {
			MsgBox.showInfo("调租单已经执行!");
			return;
		}
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName == null) {
			MsgBox.showInfo("调租单没有审核!");
			return;
		}
		BizEnumValueInfo rentAdjustState = (BizEnumValueInfo) row.getCell(
		"state").getValue();
		if(FDCBillStateEnum.INVALID_VALUE.equals(rentAdjustState.getValue()))
		{
			MsgBox.showInfo("调租单已经作废!");
			return;
		}
		if (MsgBox.showConfirm2New(this, "是否执行?") == MsgBox.YES) {
			String id = (String) row.getCell(this.getKeyFieldName()).getValue();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("head.id", id));
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("room.*");
			view.getSelector().add("room.tenancyState");
			
			RentAdjustEntrysCollection entrys = RentAdjustEntrysFactory.getRemoteInstance().getRentAdjustEntrysCollection(view);
			StringBuffer stringName = new StringBuffer();
			for (int i = 0; i < entrys.size(); i++) {
				RentAdjustEntrysInfo entry = entrys.get(i);
				RoomInfo room = entry.getRoom();
				BigDecimal actualBuildingArea = FDCHelper.ZERO;
				BigDecimal actualRoomArea = FDCHelper.ZERO;
				if(room.isIsActualAreaAudited())
				{
					actualBuildingArea =  room.getActualBuildingArea();
					actualRoomArea =  room.getActualRoomArea();
					if(actualBuildingArea==null)
					{
						actualBuildingArea = FDCHelper.ZERO;
					}
					if(actualRoomArea== null)
					{
						actualRoomArea = FDCHelper.ZERO;
					}
				}else
				{
					actualBuildingArea = room.getBuildingArea();
					actualRoomArea = room.getRoomArea();
				}
				
				BigDecimal buildingArea = entry.getOldBuildingArea();
				BigDecimal roomArea = entry.getOldRoomArea();
				if(roomArea.compareTo(actualRoomArea)!=0 || buildingArea.compareTo(actualBuildingArea)!=0)
				{
					if(stringName==null ||stringName.length()==0)
					{
						stringName.append(room.getName());
					}else
					{
						stringName.append(","+room.getName());
					}			
				}
			}
			if(stringName.length()>0)
			{
				MsgBox.showInfo("房间面积与调租面积不同，房间"+stringName.toString()+"需要重新定价!");
			}
			if (TenancyRentAdjustFactory.getRemoteInstance().execute(id)) {
				MsgBox.showInfo("执行成功!");
				this.refresh(null);
			}
		}
	}
	
	/**
	 *调阻单删除
	 * */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName != null) {
			MsgBox.showInfo("调价单已经审批,不能删除!");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}
	
	/**
	 *调阻单作废
	 * */
	public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception {
		super.actionBlankOut_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		if(((Boolean)row.getCell("isExecuted").getValue()).booleanValue())
		{
			MsgBox.showInfo("调租单已执行，不能作废!");
			return;
		}
		BizEnumValueInfo rentAdjustState = (BizEnumValueInfo) row.getCell(
		"state").getValue();
		if(FDCBillStateEnum.INVALID_VALUE.equals(rentAdjustState.getValue()))
		{
			MsgBox.showInfo("调租单已经作废!");
			return;
		}
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		if (MsgBox.showConfirm2New(this, "作废后不可恢复，请确认是否作废?") == MsgBox.YES) {
			//作废时需要把状态写入单据中，并且停止当前正在运行的工作流
			TenancyRentAdjustInfo rentAdjustInfo = TenancyRentAdjustFactory.getRemoteInstance()
			.getTenancyRentAdjustInfo(new ObjectUuidPK(BOSUuid.read(id)));
			rentAdjustInfo.setState(FDCBillStateEnum.INVALID);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("state");
			TenancyRentAdjustFactory.getRemoteInstance().updatePartial(rentAdjustInfo, sels);
			
			ProcessInstInfo instInfo = null;
			ProcessInstInfo[] procInsts = null;
			IEnactmentService service = EnactmentServiceFactory
			.createRemoteEnactService();
			procInsts = service.getProcessInstanceByHoldedObjectId(rentAdjustInfo.getId()
					.toString());
			for (int j = 0; j < procInsts.length; j++) {
				if ("open.running".equals(procInsts[j].getState())) {
					instInfo = procInsts[j];
					service.abortProcessInst(instInfo.getProcInstId());
				}
				
			}
			this.refresh(null);
		}
	}
	
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName != null) {
			MsgBox.showInfo("调租单已经审批,不能修改!");
			return;
		}
		super.actionEdit_actionPerformed(arg0);
	}
}