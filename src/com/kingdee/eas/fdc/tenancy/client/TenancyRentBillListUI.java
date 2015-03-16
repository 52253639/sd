/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
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
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.BuildigRentEntrysCollection;
import com.kingdee.eas.fdc.tenancy.BuildigRentEntrysInfo;
import com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysCollection;
import com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRentBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRentBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TenancyRentBillListUI extends AbstractTenancyRentBillListUI
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2602893629149299728L;
	private static final Logger logger = CoreUIObject.getLogger(TenancyRentBillListUI.class);
	//组织单元
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	public TenancyRentBillListUI() throws Exception
	{
		super();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected String getEditUIName() {
		return TenancyRentBillEditUI.class.getName();
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
		this.btnUnAudit.setVisible(false);
		this.btnExecute.setEnabled(true);
		this.menuItemExecut.setEnabled(true);

		this.MenuItemAttachment.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionAuditResult.setVisible(false);
		//by tim_gao 
		this.btnAudit.setVisible(true);
		this.btnAudit.setEnabled(true);
		this.actionAudit.setEnabled(true);
		this.btnUnAudit.setVisible(true);
		this.btnUnAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(true);

	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad, MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof String) {
			MsgBox.showInfo("请选择具体销售项目！");
			this.abort();
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
		super.prepareUIContext(uiContext, e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return TenancyRentBillFactory.getRemoteInstance();
	}

	protected String getLongNumberFieldName() {
		return "number";
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}

		if (node.getUserObject() instanceof SellProjectInfo  &&  this.saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(true);
		} else {
			this.actionAddNew.setEnabled(false);
		}
		this.execQuery();
	}

	/**
	 *查询过滤条件
	 * */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();

			FilterInfo filter = new FilterInfo();
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("project.id", pro.getId().toString()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}

			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			this.handleException(e);
		}
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	/**
	 * 定租单删除事件
	 * */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("state")
		.getValue();
		Boolean flag = (Boolean)row.getCell("isExecuted").getValue();
		
		if (flag.booleanValue()) {
			MsgBox.showInfo("定租单已经执行!");
			return;
		}
		if (row.getCell("state") != null&&(FDCBillStateEnum.SUBMITTED_VALUE.equals(state.getValue())||
				FDCBillStateEnum.SAVED_VALUE.equals(state.getValue())||
				FDCBillStateEnum.INVALID_VALUE.equals(state.getValue()))){
			
			FDCClientUtils.checkBillInWorkflow(this, getSelectedKeyValue());//检查单据是否在工作流中
			super.actionRemove_actionPerformed(e);
		
		}else{
			MsgBox.showInfo("未审核或者作废的定租单才可删除!");
			return;
		}
	}

	/**
	 * 定租单审核事件
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
			MsgBox.showInfo("定租单没有提交!");
			return;
		}
		if (FDCBillStateEnum.AUDITTED_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("定租单已经审核!");
			return;
		}
		if (FDCBillStateEnum.INVALID_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("定租单已经作废!");
			return;
		}
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		TenancyRentBillFactory.getRemoteInstance().audit(BOSUuid.read(id));
		this.refresh(null);
		// by tim_gao 
		verifyAuditState();
	}

	/**
	 * 定租单反审核事件
	 * */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
	 super.actionUnAudit_actionPerformed(e);
	 int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("state")
		.getValue();
		Boolean flag = (Boolean)row.getCell("isExecuted")
		.getValue();
		if (!FDCBillStateEnum.AUDITTED_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("定租单不是审核状态!");
			return;
		}
		if (FDCBillStateEnum.INVALID_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("定租单已经作废!");
			return;
		}
		if (flag.booleanValue()) {
			MsgBox.showInfo("定租单已经执行!");
			return;
		}
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		TenancyRentBillFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
		this.refresh(null);
		verifyAuditState();
	}

	/**
	 * 定租单执行事件
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
			MsgBox.showInfo("定租单已经执行!");
			return;
		}
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName == null) {
			MsgBox.showInfo("定租单没有审核!");
			return;
		}
		BizEnumValueInfo tenancyState = (BizEnumValueInfo) row.getCell(
		"state").getValue();
		if(FDCBillStateEnum.INVALID_VALUE.equals(tenancyState.getValue()))
		{
			MsgBox.showInfo("定租单已经作废!");
			return;
		}
		if (MsgBox.showConfirm2New(this, "是否执行?") == MsgBox.YES) {
			String id = (String) row.getCell(this.getKeyFieldName()).getValue();	
			TenancyRentBillInfo tenBill = TenancyClientHelper.getBuildingRentEntrys(id);
			//定租楼栋分录
			BuildigRentEntrysCollection buildingRentEntrysColl = tenBill.getBuildingEntrys();
			//用于记录与定租时房间面积不同的房间名称，用于提示
			StringBuffer stringName = new StringBuffer();
			if(buildingRentEntrysColl.size()>0)
			{
				for(int i=0;i<buildingRentEntrysColl.size();i++)
				{
					BuildigRentEntrysInfo buildingRentInfo = buildingRentEntrysColl.get(i);
					//楼栋房间分录
					BuildingRoomEntrysCollection entrys = TenancyClientHelper.getBuildingRoomEntrys(buildingRentInfo);
					for (int j = 0; j < entrys.size(); j++) {

						BuildingRoomEntrysInfo roomEntrysInfo = entrys.get(j);
						RoomInfo room = roomEntrysInfo.getRooms();
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
						BigDecimal buildingArea = roomEntrysInfo.getBuildingArea();
						BigDecimal roomArea = roomEntrysInfo.getRoomArea();
						/**
						 * 如果在执行时候房间面积和定租时房间面积不相同，说明房间在定租过程中反售前复核或者是实测复核过，那么
						 * 房间面积改变的房间需要重新来定价，执行时也不会把价格写入房间中
						 * */
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
				}
			}else
			{
				BuildingRoomEntrysCollection buildRoomEntryColl = tenBill.getRoomEntrys();
				for(int i=0;i<buildRoomEntryColl.size();i++)
				{
					BuildingRoomEntrysInfo roomEntrysInfo = buildRoomEntryColl.get(i);
					RoomInfo room = roomEntrysInfo.getRooms();
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
					BigDecimal buildingArea = roomEntrysInfo.getBuildingArea();
					BigDecimal roomArea = roomEntrysInfo.getRoomArea();
					if(buildingArea.compareTo(actualBuildingArea)!=0 || roomArea.compareTo(actualRoomArea)!=0)
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
			}
			if(stringName.length()>0)
			{
				MsgBox.showInfo("房间"+stringName.toString()+"定租后面积变化,将自动跳过!需要重新定租!");
			}
			if (TenancyRentBillFactory.getRemoteInstance().execute(id)) {
				MsgBox.showInfo("执行成功!");
				this.refresh(null);
			}

		}
	}

	/**
	 * 定租单作废事件
	 * */
	public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		if(((Boolean)row.getCell("isExecuted").getValue()).booleanValue())
		{
			MsgBox.showInfo("定租单已执行，不能作废!");
			return;
		}
		BizEnumValueInfo tenancyState = (BizEnumValueInfo) row.getCell(
		"state").getValue();
		if(FDCBillStateEnum.INVALID_VALUE.equals(tenancyState.getValue()))
		{
			MsgBox.showInfo("定租单已经作废!");
			return;
		}
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		if (MsgBox.showConfirm2New(this, "作废后不可恢复，请确认是否作废?") == MsgBox.YES) {
			//作废时需要把状态写入单据中，并且停止当前正在运行的工作流
			TenancyRentBillInfo tenancyInfo = TenancyRentBillFactory.getRemoteInstance()
			.getTenancyRentBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
			tenancyInfo.setState(FDCBillStateEnum.INVALID);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("state");
			TenancyRentBillFactory.getRemoteInstance().updatePartial(tenancyInfo, sels);

			ProcessInstInfo instInfo = null;
			ProcessInstInfo[] procInsts = null;
			IEnactmentService service = EnactmentServiceFactory
			.createRemoteEnactService();
			procInsts = service.getProcessInstanceByHoldedObjectId(tenancyInfo.getId()
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
			MsgBox.showInfo("定租单已经审批,不能修改!");
			return;
		}
		FDCClientUtils.checkBillInWorkflow(this, getSelectedKeyValue());//检查单据是否在工作流中
		super.actionEdit_actionPerformed(arg0);
	}
	/**
	 * @author tim_gao
	 * @description 审核反审核判断
	 * @date 2010-12-17
	 */
	protected void verifyAuditState(){
		int rowindex = this.tblMain.getSelectManager().getActiveRowIndex();
		
		if(rowindex <0){
			this.btnAudit.setVisible(true);
			this.actionAudit.setEnabled(true);
			this.actionAudit.setVisible(true);
			this.btnUnAudit.setVisible(true);
			this.actionUnAudit.setEnabled(true);
			this.actionUnAudit.setVisible(true);
		}else{
			IRow row =  this.tblMain.getRow(rowindex);
			BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("state")
			.getValue();
			if(FDCBillStateEnum.AUDITTED_VALUE.equals(state.getValue())){
				this.btnAudit.setEnabled(false);
				this.actionAudit.setEnabled(false);
				
				this.btnUnAudit.setEnabled(true);
				this.actionUnAudit.setEnabled(true);
				
			}else if (FDCBillStateEnum.SUBMITTED_VALUE.equals(state.getValue())||FDCBillStateEnum.SAVED_VALUE.equals(state.getValue())){
				this.btnAudit.setEnabled(true);
				this.actionAudit.setEnabled(true);
				
				this.btnUnAudit.setEnabled(false);
				this.actionUnAudit.setEnabled(false);
			
			}	
			
		}
		
	
	}
	
	public void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
	      super.tblMain_tableClicked(e);
		//write your code here
	  	
			//by tim_gao 审核状态判断
			verifyAuditState();
	}
}