/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class QuitRoomListUI extends AbstractQuitRoomListUI {
	public void actionBalance_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		} 
					
		if (MsgBox.showConfirm2("您确定要结算吗？")!= MsgBox.YES) return;
		
		String quitRoomID = this.getSelectedKeyValue();
		QuitRoomFactory.getRemoteInstance().settleMent(BOSUuid.read(quitRoomID));
		MsgBox.showInfo("结算成功！");
	}

	
	private static final Logger logger = CoreUIObject
			.getLogger(QuitRoomListUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	/**
	 * output class constructor
	 */
	public QuitRoomListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		// actionQuery.setVisible(false);
		super.onLoad();
		initTree();
		this.treeMain.setSelectionRow(0);
		// this.actionAddNew.setVisible(false);
		// this.actionAddNew.setEnabled(false);
		//this.actionRemove.setVisible(false);
		//this.actionRemove.setEnabled(false);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionAuditResult.setVisible(false);

		this.actionQuitRoomChange.setVisible(true);
		this.actionQuitRoomChange.setEnabled(true);
		this.btnBalance.setVisible(true);
		this.btnBalance.setEnabled(true);
		if (!saleOrg.isIsBizUnit()) {
			this.actionEdit.setEnabled(false);
			this.actionAddNew.setEnabled(false);
		}
		this.actionAudit.setEnabled(true);
		
		//add by jiyu_guan
		actionRetakeCheque.setEnabled(true);
	
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,
				MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
	}


	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String idStr = this.getSelectedKeyValue();
		if(idStr==null) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		QuitRoomInfo quitInfo = QuitRoomFactory.getRemoteInstance().getQuitRoomInfo("select state where id='"+idStr+"'");
		if(quitInfo!=null) {
			if(quitInfo.getState().equals(FDCBillStateEnum.AUDITTED)) {
				MsgBox.showInfo("退房单已经审核不能修改!");
				return;
			}else if(quitInfo.getState().equals(FDCBillStateEnum.INVALID)) {
				MsgBox.showInfo("退房单已经作废不能修改!");
				return;
			}
		}
		super.actionEdit_actionPerformed(e);
	}



	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		this.execQuery();
	}
	
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		
		FilterInfo filter = new FilterInfo();
		if (node != null) {
			///tblMain.getColumn("subarea").getStyleAttributes().setHided(false);
			///tblMain.getColumn("building").getStyleAttributes().setHided(false);
			///tblMain.getColumn("unit").getStyleAttributes().setHided(false);
			
			if (node.getUserObject() instanceof BuildingUnitInfo) {
				BuildingUnitInfo buildUnit = (BuildingUnitInfo) node
						.getUserObject();
				BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
						.getParent()).getUserObject();
				String buildingId = building.getId().toString();
				filter.getFilterItems().add(
						new FilterItemInfo("building.id", buildingId));
				filter.getFilterItems().add(
						new FilterItemInfo("room.buildUnit.id", buildUnit.getId()
								.toString()));
			
				///tblMain.getColumn("subarea").getStyleAttributes().setHided(true);
				///tblMain.getColumn("building").getStyleAttributes().setHided(true);
				///tblMain.getColumn("unit").getStyleAttributes().setHided(true);
			} else if (node.getUserObject() instanceof BuildingInfo) {
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				String buildingId = building.getId().toString();
				filter.getFilterItems().add(
						new FilterItemInfo("building.id", buildingId));
				if (building.getUnitCount() == 0) {
					tblMain.getColumn("unit").getStyleAttributes().setHided(true);
				}
			
				///tblMain.getColumn("subarea").getStyleAttributes().setHided(true);
				//tblMain.getColumn("building").getStyleAttributes().setHided(true);
			} else if (node.getUserObject() instanceof SubareaInfo) {
				SubareaInfo subarea = (SubareaInfo) node.getUserObject();
				filter.getFilterItems()
						.add(
								new FilterItemInfo("subarea.id", subarea.getId()
										.toString()));
			
				//tblMain.getColumn("subarea").getStyleAttributes().setHided(true);
			} else if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo sellProject = (SellProjectInfo) node
						.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sellProject.getId()
								.toString()));
				if (sellProject.getSubarea() == null
						|| sellProject.getSubarea().isEmpty()) {
					//tblMain.getColumn("subarea").getStyleAttributes()
							//.setHided(true);
				}
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}
		}

		viewInfo = (EntityViewInfo) this.mainQuery.clone();
		if (viewInfo.getFilter() == null)
			viewInfo.setFilter(filter);
		else {
			try {
				viewInfo.getFilter().mergeFilter(filter, "AND");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}		
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return QuitRoomFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return QuitRoomEditUI.class.getName();
	}

	protected void refresh(ActionEvent e) throws Exception {
		super.refresh(e);
		//treeMain_valueChanged(null);
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName != null) {
			MsgBox.showInfo("退房单已经审批!");
			return;
		}
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		QuitRoomFactory.getRemoteInstance().audit(BOSUuid.read(id));
		this.refresh(null);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		QuitRoomFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
	}

	public void actionRefundment_actionPerformed(ActionEvent e)
			throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		
		String quitId = this.getSelectedKeyValue();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("room.*");
		sels.add("room.building.*");
		sels.add("room.building.sellProject.id");
		sels.add("room.building.sellProject.name");
		sels.add("room.building.sellProject.number");
		sels.add("purchase.*");
		sels.add("sellOrder.*");
		QuitRoomInfo quitRoomInfo = QuitRoomFactory.getRemoteInstance()
				.getQuitRoomInfo(new ObjectUuidPK(BOSUuid.read(quitId)), sels);
		
		if(quitRoomInfo.getAuditor()==null){
			MsgBox.showInfo("退房单没有审批！");
			return;
		}
		
		RoomInfo room = quitRoomInfo.getRoom();
		if (room == null) {
			return;
		}

		UIContext uiContext = new UIContext(this);
		uiContext.put(SHEReceivingBillEditUI.KEY_SELL_PROJECT, room.getBuilding().getSellProject());
		uiContext.put(SHEReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.refundment);
		uiContext.put(SHEReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, new Boolean(true));
		uiContext.put(SHEReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.purchase);
		uiContext.put(SHEReceivingBillEditUI.KEY_SELL_PURCHASE, quitRoomInfo.getPurchase());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(SHEReceivingBillEditUI.class.getName(), uiContext, null,"ADDNEW");
		uiWindow.show();		
		
	}
	
	public void actionQuitRoomChange_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionQuitRoomChange_actionPerformed(e);
		this.checkSelected();
		String quitRoomID = this.getSelectedKeyValue();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("number");
		sels.add("room.*");
    	sels.add("room.id");
    	sels.add("room.name");
    	sels.add("state");
    	sels.add("isBlance");
		QuitRoomInfo quitRoomInfo = QuitRoomFactory.getRemoteInstance()
				.getQuitRoomInfo(new ObjectUuidPK(quitRoomID),sels);
		if (!FDCBillStateEnum.AUDITTED.equals(quitRoomInfo
				.getState())) {
			MsgBox.showWarning("只有已审批的退房单才能进行变更！");
			SysUtil.abort();
		}
		if (quitRoomInfo.getIsBlance()==1) {
			MsgBox.showWarning("已经退房结算，不能再进行变更！");
			SysUtil.abort();
		}		
		
		
		UIContext context = new UIContext(ui);
		context.put("quitRoomInfo", quitRoomInfo);
		try {
			IUIWindow window = UIFactory.createUIFactory(
					UIFactoryName.NEWWIN).create(
					QuitRoomChangeEditUI.class.getName(),
					context, null, OprtState.ADDNEW);
			window.show();
		} catch (UIException e1) {
			QuitRoomListUI.this.handleException(e1);
		}	
	}

	protected String[] getLocateNames() {
		String[] locateNames = new String[4];
		locateNames[0] = "number";
		locateNames[1] = "state";
		locateNames[2] = "room.number";
		locateNames[3] = "customer";
		return locateNames;
	}
	
	/**
	 * 回收票据 add by jiyu_guan
	 */
	public void actionRetakeCheque_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row == null) {
			MsgBox.showWarning(this, "请先选中一张退房单!");
			SysUtil.abort();
		}
		String stateName = ((BizEnumValueDTO) row.getCell("state").getValue())
				.getName();
		if (!stateName.equals(FDCBillStateEnum.AUDITTED.getName())) {
			MsgBox.showWarning(this, "当前单据非审批状态，不能做回收票据操作!");
			return;
		}
		Map ctx = new UIContext(this);
		String qtrID = (String) row.getCell("id").getValue();
		ctx.put("sourceID", qtrID);
		ctx.put("sourceType", "退房");
		ctx.put(UIContext.OWNER, this);

		IUIWindow uiWindow = null;
		// 弹出模式
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				MackOutRetakeChequeUI.class.getName(), ctx, null,
				OprtState.ADDNEW);

		// 开始展现UI
		uiWindow.show();
		// 清缓存，重新从数据库查一次
		CacheServiceFactory.getInstance().discardType(
				new InvoiceInfo().getBOSType());
		refresh(null);
	}
	
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		//保存状态的 单据可以删除
		String idStr = this.getSelectedKeyValue();
		if(idStr!=null) {
			if(QuitRoomFactory.getRemoteInstance().exists("where state='"+FDCBillStateEnum.SAVED_VALUE+"' and id='"+idStr+"'")){
				if(MsgBox.showConfirm2("确认要删除该记录吗？")==MsgBox.OK) {
					super.actionRemove_actionPerformed(e);
					this.refresh(null);
				}
			}else{
				MsgBox.showInfo("只有保存状态的单据才能够被删除！");
			}
		}
	}
	
	
	public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception {
		//只有保存和提交状态的单据可以作废
		String idStr = this.getSelectedKeyValue();
		if(idStr!=null) {
			QuitRoomInfo quitInfo = QuitRoomFactory.getRemoteInstance().getQuitRoomInfo("select state where id='"+idStr+"'");
			if(quitInfo!=null && (quitInfo.getState().equals(FDCBillStateEnum.SAVED) || quitInfo.getState().equals(FDCBillStateEnum.SUBMITTED))) {
				if(MsgBox.showConfirm2("确认要作废该记录吗？")==MsgBox.OK) {					
					SelectorItemCollection selecotr = new SelectorItemCollection();
					selecotr.add(new SelectorItemInfo("state"));
					quitInfo.setState(FDCBillStateEnum.INVALID);
					QuitRoomFactory.getRemoteInstance().updatePartial(quitInfo, selecotr);
				}
			}else{
				MsgBox.showInfo("只有保存或提交状态的单据才能够被作废！");
			}
		}
	}
	
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		super.actionView_actionPerformed(e);
	}
	
}