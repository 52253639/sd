/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIParam;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.monitor.client.ProcessRunningListUI;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CompensateRoomListFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.workflow.IWorkflowUIEnhancement;
import com.kingdee.eas.framework.client.workflow.IWorkflowUISupport;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RoomAreaCompendateNewListUI extends
		AbstractRoomAreaCompendateNewListUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5474724331495338618L;
	private static final Logger logger = CoreUIObject
			.getLogger(RoomAreaCompendateNewListUI.class);

	/**
	 * output class constructor
	 */
	public RoomAreaCompendateNewListUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
	}

	protected void initTree() throws Exception {
		/*
		 * this.treeMain.setModel(FDCTreeHelper.getUnitTreeForSHE(actionOnLoad,
		 * MoneySysTypeEnum.SalehouseSys));
		 * this.treeMain.setShowsRootHandles(true);
		 * this.treeMain.expandAllNodes(true, (TreeNode)
		 * this.treeMain.getModel() .getRoot());
		 */
		if (this.kdtRoomAreaCompensate.getSelectedComponent().getName().equals(
				"kdpRoom")) {
			//this.TreeViewPrice.setModel(FDCTreeHelper.getSellProjectTreeForSHE
			// (this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
			this.roomTree.setModel(FDCTreeHelper.getUnitTreeForSHE(
					actionOnLoad, MoneySysTypeEnum.SalehouseSys));
			this.roomTree.expandAllNodes(true, (TreeNode) this.roomTree
					.getModel().getRoot());
			this.roomTree
					.setSelectionNode((DefaultKingdeeTreeNode) this.roomTree
							.getModel().getRoot());
		} else {
			//this.treeMain.setModel(FDCTreeHelper.getUnitTreeForSHE(actionOnLoad
			// , MoneySysTypeEnum.SalehouseSys));
			this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(
					this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
			this.treeMain.setShowsRootHandles(true);
			this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain
					.getModel().getRoot());
			this.treeMain
					.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain
							.getModel().getRoot());
		}
	}

	protected void tblRoom_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {

		if (e.getType() == KDTStyleConstants.BODY_ROW
				&& e.getButton() == MouseEvent.BUTTON1
				&& e.getClickCount() == 2) {
			IRow row = this.tblRoom.getRow(e.getRowIndex());
			Object idObj = row.getCell("roomId").getValue();
			if (idObj == null) {
				return;
			}
			String idStr = "";
			if (idObj instanceof String) {
				idStr = (String) idObj;
			} else if (idObj instanceof BOSUuid) {
				idStr = ((BOSUuid) idObj).toString();
			}
			if (!idStr.equals("")) {
				try {

					boolean res = CompensateRoomListFactory.getRemoteInstance()
							.exists(
									"select id,name where room.id = '" + idStr
											+ "'");
					if (!res) {
						FDCMsgBox.showWarning(this, "该房间不存在补差信息!");
						this.abort();
					}
					UIContext uiContext = new UIContext(this);
					uiContext.put("selectCompensateId", idStr);
					IUIWindow uiWindow = UIFactory.createUIFactory(
							UIFactoryName.MODEL).create(
							CompensateRoomSelectUI.class.getName(), uiContext,
							null, "ADDNEW");
					uiWindow.show();

					this.execQuery();
				} catch (UIException e1) {
					logger.error(e1.getMessage() + "打开户型界面失败！");
				}

			}
		}

	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);

	}

	private void prepareData(UIContext uiContext) {

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		SellProjectInfo sellProject = null;
		uiContext.put("selectBuilding", null);
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				sellProject = (SellProjectInfo) node.getUserObject();
				uiContext.put("sellProject", sellProject);
			} else if (node.getUserObject() instanceof SubareaInfo) {
				SubareaInfo subArea = (SubareaInfo) node.getUserObject();
				sellProject = subArea.getSellProject();
				uiContext.put("sellProject", sellProject);
				uiContext.put("selectBuilding", null);
			} else if (node.getUserObject() instanceof BuildingInfo) {
				sellProject = ((BuildingInfo) node.getUserObject())
						.getSellProject();
				uiContext.put("sellProject", sellProject);
				uiContext.put("selectBuilding", (BuildingInfo) node
						.getUserObject());
			} else if (node.getUserObject() instanceof BuildingUnitInfo) {
				BuildingUnitInfo unit = (BuildingUnitInfo) node.getUserObject();
				sellProject = unit.getBuilding().getSellProject();
				uiContext.put("selectBuilding", unit.getBuilding());
				uiContext.put("selectUnit", unit);
				uiContext.put("sellProject", sellProject);
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				FDCMsgBox.showWarning(this, "请先选择项目!");
				this.abort();
			}
		}
	}

	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {

		if (e.getType() == KDTStyleConstants.BODY_ROW
				&& e.getButton() == MouseEvent.BUTTON1
				&& e.getClickCount() == 2) {
			IRow row = this.tblMain.getRow(e.getRowIndex());
			Object idObj = row.getCell("id").getValue();
			if (idObj == null) {
				return;
			}
			String idStr = "";
			if (idObj instanceof String) {
				idStr = (String) idObj;
			} else if (idObj instanceof BOSUuid) {
				idStr = ((BOSUuid) idObj).toString();
			}
			if (!idStr.equals("")) {
				String uiClassName = "com.kingdee.eas.fdc.sellhouse.client.RoomAreaCompensateNewUI";
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, idStr);
				try {
					IUIWindow uiWindow = UIFactory.createUIFactory(
							UIFactoryName.MODEL).create(uiClassName, uiContext,
							null, OprtState.VIEW);
					uiWindow.show();
					this.execQuery();
				} catch (UIException e1) {
					logger.error(e1.getMessage() + "打开户型界面失败！");
				}

			}
		}
	}

	public void prepareUIParam(UIParam uiParam) {
		super.prepareUIParam(uiParam);

	}

	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	public void roomTree_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		if (this.kdtRoomAreaCompensate.getSelectedComponent().getName().equals(
				"kdpRoom")) {
			this.tblRoom.removeRows();
			IRowSet rs = RoomAreaCompensateFactory.getRemoteInstance()
					.getRoomInfoList(filterStr());
			if (rs != null) {
				loadRoomInfo(rs);
			}

		} else {
			this.execQuery();
		}

		if (this.isSellOrg()) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
					.getLastSelectedPathComponent();
			if (node != null) {
				if (node.getUserObject() instanceof OrgStructureInfo) {
					this.actionByHand.setEnabled(false);
					this.actionByScheme.setEnabled(false);
					this.actionAudit.setEnabled(false);
					this.actionUnAudit.setEnabled(false);
					this.actionReceipt.setEnabled(false);
					this.actionStop.setEnabled(false);
					this.actionRemove.setEnabled(false);
				} else {
					if (this.kdtRoomAreaCompensate.getSelectedComponent()
							.getName().equals("kdpRoom")) {
						this.actionByHand.setEnabled(false);
						this.actionByScheme.setEnabled(false);
						this.actionAudit.setEnabled(false);
						this.actionUnAudit.setEnabled(false);
						this.actionReceipt.setEnabled(false);
						this.actionStop.setEnabled(false);
						this.actionRemove.setEnabled(false);
					} else {
						this.actionByHand.setEnabled(true);
						this.actionByScheme.setEnabled(true);
						this.actionAudit.setEnabled(true);
						this.actionUnAudit.setEnabled(true);
						this.actionReceipt.setEnabled(true);
						this.actionStop.setEnabled(true);
						this.actionRemove.setEnabled(true);
					}
				}
			}
		}
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		if (this.kdtRoomAreaCompensate.getSelectedComponent().getName().equals(
				"kdpRoom")) {
			this.tblRoom.removeRows();
			IRowSet rs = RoomAreaCompensateFactory.getRemoteInstance()
					.getRoomInfoList(filterStr());
			if (rs != null) {
				loadRoomInfo(rs);
			}

		} else {
			this.execQuery();
		}

		if (this.isSellOrg()) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
					.getLastSelectedPathComponent();
			if (node != null) {
				if (node.getUserObject() instanceof OrgStructureInfo) {
					this.actionByHand.setEnabled(false);
					this.actionByScheme.setEnabled(false);
					this.actionAudit.setEnabled(false);
					this.actionUnAudit.setEnabled(false);
					this.actionReceipt.setEnabled(false);
					this.actionStop.setEnabled(false);
					this.actionViewWorkFlow.setEnabled(false);
					this.actionRemove.setEnabled(false);
				} else {
					if (this.kdtRoomAreaCompensate.getSelectedComponent()
							.getName().equals("kdpRoom")) {
						this.actionByHand.setEnabled(false);
						this.actionByScheme.setEnabled(false);
						this.actionAudit.setEnabled(false);
						this.actionUnAudit.setEnabled(false);
						this.actionReceipt.setEnabled(false);
						this.actionStop.setEnabled(false);
						this.actionViewWorkFlow.setEnabled(false);
						this.actionRemove.setEnabled(false);
					} else {
						this.actionByHand.setEnabled(true);
						this.actionByScheme.setEnabled(true);
						this.actionAudit.setEnabled(true);
						this.actionUnAudit.setEnabled(true);
						this.actionReceipt.setEnabled(true);
						this.actionStop.setEnabled(true);
						this.actionViewWorkFlow.setEnabled(true);
						this.actionRemove.setEnabled(true);
					}

				}
			}

		}

	}

	private void loadRoomInfo(IRowSet rs) {
		try {
			this.tblRoom.checkParsed();
			while (rs.next()) {
				IRow row = tblRoom.addRow();
				if (rs.getString("roomId") != null) {
					row.getCell("roomId").setValue(
							BOSUuid.read(rs.getString("roomId")));
				}

				if (rs.getString("roomName") != null) {
					row.getCell("roomName").setValue(rs.getString("roomName"));
				}

				if (rs.getString("customerName") != null) {
					row.getCell("customer").setValue(
							rs.getString("customerName"));
				}

				if (rs.getString("phone") != null) {
					row.getCell("phone").setValue(rs.getString("phone"));
				}

				if (rs.getString("comNumber") != null) {
					row.getCell("comNumber")
							.setValue(rs.getString("comNumber"));
				}

				if (rs.getDate("comDate") != null) {
					row.getCell("comDate").setValue(rs.getDate("comDate"));
				}

				if (rs.getString("state") != null) {
					if (rs.getString("state").equals(
							RoomCompensateStateEnum.COMAUDITTED_VALUE)) {
						row.getCell("comState").setValue(
								RoomCompensateStateEnum.COMAUDITTED.getAlias());
					} else if (rs.getString("state").equals(
							RoomCompensateStateEnum.COMAUDITTING_VALUE)) {
						row.getCell("comState")
								.setValue(
										RoomCompensateStateEnum.COMAUDITTING
												.getAlias());
					} else if (rs.getString("state").equals(
							RoomCompensateStateEnum.COMRECEIVED_VALUE)) {
						row.getCell("comState").setValue(
								RoomCompensateStateEnum.COMRECEIVED.getAlias());
					} else if (rs.getString("state").equals(
							RoomCompensateStateEnum.COMSUBMIT_VALUE)) {
						row.getCell("comState").setValue(
								RoomCompensateStateEnum.COMSUBMIT.getAlias());
					} else if (rs.getString("state").equals(
							RoomCompensateStateEnum.NOCOMPENSATE_VALUE)) {
						row.getCell("comState")
								.setValue(
										RoomCompensateStateEnum.NOCOMPENSATE
												.getAlias());
					}

				} else {
					row.getCell("comState").setValue(
							RoomCompensateStateEnum.NOCOMPENSATE.getAlias());
				}
				if (rs.getString("schemeName") != null) {
					row.getCell("comScheme").setValue(
							rs.getString("schemeName"));
				}

				if (rs.getBigDecimal("mainAmount") != null) {
					row.getCell("roomAmount").setValue(
							rs.getBigDecimal("mainAmount"));
				}

				if (rs.getBigDecimal("attAmount") != null) {
					row.getCell("attAmount").setValue(
							rs.getBigDecimal("attAmount"));
				}

				if (rs.getBigDecimal("actAmount") != null) {
					row.getCell("actAmount").setValue(
							rs.getBigDecimal("actAmount"));
				}

				if (rs.getBigDecimal("refAmount") != null) {

					row.getCell("refAmount").setValue(
							rs.getBigDecimal("refAmount"));
				}

				if (rs.getBigDecimal("rate") != null) {
					row.getCell("areaRate").setValue(rs.getBigDecimal("rate"));
				}

				if (rs.getBigDecimal("lastAMount") != null) {
					row.getCell("lastestTotal").setValue(
							rs.getBigDecimal("lastAMount"));
				}

				if (rs.getString("handlerName") != null) {
					row.getCell("handler")
							.setValue(rs.getString("handlerName"));
				}

				if (rs.getString("roomDesc") != null) {
					row.getCell("desc").setValue(rs.getString("roomDesc"));
				}

				if (rs.getString("createorName") != null) {
					row.getCell("creator").setValue(
							rs.getString("createorName"));
				}

				if (rs.getDate("createtime") != null) {
					row.getCell("createDate")
							.setValue(rs.getDate("createtime"));
				}

				if (rs.getString("auditorName") != null) {
					row.getCell("auditor")
							.setValue(rs.getString("auditorName"));
				}

				if (rs.getDate("auditTime") != null) {
					row.getCell("auditTime").setValue(rs.getDate("auditTime"));
				}

				if (rs.getString("comId") != null) {
					row.getCell("comId").setValue(
							BOSUuid.read(rs.getString("comId")));
				}

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public String filterStr() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.roomTree
				.getLastSelectedPathComponent();
		if (node == null) {
			return null;
		}
		StringBuffer str = new StringBuffer(" ");

		if (node.getUserObject() instanceof BuildingUnitInfo) {// 单元
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node
					.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			str.append("and building.FID='" + buildingId + "' ");
			str.append("and room.FBuildUnitID='" + buildUnit.getId().toString()
					+ "' ");
		} else if (node.getUserObject() instanceof BuildingInfo) {// 楼栋
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			String buildingId = building.getId().toString();
			str.append("and building.FID='" + buildingId + "' ");
		} else if (node.getUserObject() instanceof SubareaInfo) { // 分区
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			str.append("and subArea.FID='" + subarea.getId().toString() + "' ");
		} else if (node.getUserObject() instanceof SellProjectInfo) { // 销售项目
			String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper
					.getAllObjectIdMap(node, "SellProject").keySet());
			if (allSpIdStr.trim().length() == 0) {
				allSpIdStr = "'nullnull'";
			}
			str.append("and sellProject.FID in (" + allSpIdStr + ") ");
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			str.append("and sellProject.FID in " + "('nullnull')" + " ");
		}
		return str.toString();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionView.setVisible(false);
//		this.actionRemove.setVisible(false);
		this.actionEdit.setVisible(false);

		this.actionByHand.setEnabled(false);
		this.actionByScheme.setEnabled(false);
		this.actionByScheme.setVisible(true);
		this.actionAudit.setEnabled(false);
		this.actionUnAudit.setEnabled(false);
		this.actionReceipt.setEnabled(false);
		this.actionStop.setEnabled(false);
		this.btnHand.setIcon(EASResource.getIcon("imgTbtn_new"));
		this.btnScheme.setIcon(EASResource.getIcon("imgTbtn_edit"));
		this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		this.btnRecepice.setIcon(EASResource.getIcon("imgTbtn_gathering"));
		this.btnStop.setIcon(EASResource.getIcon("imgTbtn_forbid"));
		this.btnWorkFlow.setIcon(EASResource.getIcon("imgTbtn_flowchart"));
		this.actionByHand.setEnabled(false);
		this.actionByScheme.setEnabled(false);
		this.actionAudit.setEnabled(false);
		this.actionUnAudit.setEnabled(false);
		this.actionReceipt.setEnabled(false);
		this.actionStop.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.tblRoom.getStyleAttributes().setLocked(true);
		this.tblMain.getStyleAttributes().setLocked(true);
		this.actionReceipt.setVisible(false);
		
		this.actionStop.setVisible(false);
		
		String[] fields=new String[this.tblMain.getColumnCount()];
		for(int i=0;i<this.tblMain.getColumnCount();i++){
			fields[i]=this.tblMain.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(this.tblMain,fields);
		
		String[] roomfields=new String[this.tblRoom.getColumnCount()];
		for(int i=0;i<this.tblRoom.getColumnCount();i++){
			roomfields[i]=this.tblRoom.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(this.tblRoom,roomfields);
	}

	public void loadFields() {
		super.loadFields();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {

		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			RoomAreaCompensateInfo info = RoomAreaCompensateFactory.getRemoteInstance().getRoomAreaCompensateInfo(
					"select id,name,compensateState where id='"
							+ id.get(i).toString() + "'");
			if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMAUDITTED)) {
				FDCMsgBox.showWarning(this, "单据已审批，不能删除!");
				this.abort();
			} else if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMAUDITTING)) {
				FDCMsgBox.showWarning(this, "单据审批中，不能删除!");
				this.abort();
			} else if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMRECEIVED)) {
				FDCMsgBox.showWarning(this, "单据已收款，不能删除!");
				this.abort();
			} else if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMRECEIVING)) {
				FDCMsgBox.showWarning(this, "单据收款中，不能删除!");
				this.abort();
			}
		
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
		
			if (confirmRemove()) {
				try {
					RoomAreaCompensateFactory.getRemoteInstance().deleteCompensateInfo(BOSUuid.read(id.get(i).toString()));
					this.refresh(null);
				} catch (BOSException ex) {
					logger.error(ex.getMessage() + "删除补差单失败!");
				}
			}
		}
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		ArrayList idList = this.getSelectedIdValues();
		if (idList != null && idList.size() > 0) {
			String id = idList.get(0).toString();
			RoomAreaCompensateInfo info = RoomAreaCompensateFactory
					.getRemoteInstance().getRoomAreaCompensateInfo(
							"select id,name,compensateState where id='" + id
									+ "'");

			if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMAUDITTED)) {
				FDCMsgBox.showWarning(this, "单据已审批，不能再审批!");
				this.abort();
			} else if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMAUDITTING)) {
				FDCMsgBox.showWarning(this, "单据审批中，不能审批!");
				this.abort();
			} else if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMRECEIVED)) {
				FDCMsgBox.showWarning(this, "单据已收款，不能审批!");
				this.abort();
			} else if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMSTOP)) {
				FDCMsgBox.showWarning(this, "单据已作废，不能审批!");
				this.abort();
			} else if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMRECEIVING)) {
				FDCMsgBox.showWarning(this, "单据收款中，不能修改!");
				this.abort();
			}

			FDCClientUtils.checkBillInWorkflow(this, idList.get(0).toString());

			try {
				RoomAreaCompensateFactory.getRemoteInstance()
						.auditAndCalcSellAmount(id);
				MsgBox.showInfo("审批成功!");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		} else {
			FDCMsgBox.showWarning(this, "请先选择记录!");
			this.abort();
		}

		refresh(e);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		ArrayList idList = this.getSelectedIdValues();
		if (idList != null && idList.size() > 0) {
			String id = idList.get(0).toString();
			RoomAreaCompensateInfo info = RoomAreaCompensateFactory
					.getRemoteInstance().getRoomAreaCompensateInfo(
							"select id,name,compensateState where id='" + id
									+ "'");
			if (!info.getCompensateState().equals(
					RoomCompensateStateEnum.COMAUDITTED)) {
				FDCMsgBox.showWarning(this, "单据状态不对，不能进行反审批!");
				this.abort();
			}

			FDCClientUtils.checkBillInWorkflow(this, idList.get(0).toString());

			try {
				RoomAreaCompensateFactory.getRemoteInstance()
						.unAuditAndCalcSellAmount(id);
				MsgBox.showInfo("反审批成功!");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				if (ex instanceof BOSException
						&& ex.getMessage().startsWith("roomAreaCompensate")) {
					MsgBox.showWarning(this, "已收补差款，不能反审批!");
					SysUtil.abort();
				}
			}
		} else {
			FDCMsgBox.showWarning(this, "请先选择记录!");
			this.abort();
		}

		refresh(e);
	}

	public void actionStop_actionPerformed(ActionEvent e) throws Exception {
		super.actionStop_actionPerformed(e);
		ArrayList idList = this.getSelectedIdValues();
		if (idList != null && idList.size() > 0) {
			String id = idList.get(0).toString();
			RoomAreaCompensateInfo info = RoomAreaCompensateFactory
					.getRemoteInstance().getRoomAreaCompensateInfo(
							"select id,name,compensateState where id='" + id
									+ "'");
			if (!info.getCompensateState().equals(
					RoomCompensateStateEnum.COMSUBMIT)) {
				FDCMsgBox.showWarning(this, "单据不是补差提交状态，不能作废!");
				this.abort();
			}

			FDCClientUtils.checkBillInWorkflow(this, idList.get(0).toString());

			try {
				RoomAreaCompensateFactory.getRemoteInstance().setNullify(id);
				MsgBox.showInfo("作废成功!");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		} else {
			FDCMsgBox.showWarning(this, "请先选择记录!");
			this.abort();
		}

		refresh(e);
	}

	public void actionReceipt_actionPerformed(ActionEvent e) throws Exception {
		super.actionReceipt_actionPerformed(e);
		ArrayList idList = this.getSelectedIdValues();
		if (idList != null && idList.size() > 0) {
			String id = idList.get(0).toString();
			RoomAreaCompensateInfo info = RoomAreaCompensateFactory
					.getRemoteInstance().getRoomAreaCompensateInfo(
							"select id,name,compensateState where id='" + id
									+ "'");
			if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMAUDITTING)) {
				FDCMsgBox.showWarning(this, "单据审批中，不能收款!");
				this.abort();
			} else if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMSTOP)) {
				FDCMsgBox.showWarning(this, "单据已作废，不能收款!");
				this.abort();
			} else if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMSUBMIT)) {
				FDCMsgBox.showWarning(this, "未审批单据，不能收款!");
				this.abort();
			} else if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMRECEIVED)) {
				FDCMsgBox.showWarning(this, "收款完成，不能再收款!");
				this.abort();
			} else if (info.getCompensateState().equals(
					RoomCompensateStateEnum.NOCOMPENSATE)) {
				FDCMsgBox.showWarning(this, "未审批单据，不能收款!");
				this.abort();
			}

			FDCClientUtils.checkBillInWorkflow(this, idList.get(0).toString());

			try {

				SellProjectInfo sellProject = null;

				DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
						.getLastSelectedPathComponent();
				if (node != null) {
					if (node.getUserObject() instanceof SellProjectInfo) {
						sellProject = (SellProjectInfo) node.getUserObject();

					} else if (node.getUserObject() instanceof BuildingInfo) {// 楼栋
						BuildingInfo building = (BuildingInfo) node
								.getUserObject();
						sellProject = building.getSellProject();
					} else if (node.getUserObject() instanceof BuildingUnitInfo) {// 单元
						BuildingUnitInfo unit = (BuildingUnitInfo) node
								.getUserObject();
						sellProject = unit.getBuilding().getSellProject();
					} else if (node.getUserObject() instanceof SubareaInfo) { // 分区
						SubareaInfo subarea = (SubareaInfo) node
								.getUserObject();
						sellProject = subarea.getSellProject();
					} else {
						FDCMsgBox.showWarning(this, "请先选择项目!");
						this.abort();
					}
				}

				CRMClientHelper.openTheGatherRevBillWindow(this, null,
						sellProject, null, null, null);
				this.execQuery();
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		} else {
			FDCMsgBox.showWarning(this, "请先选择记录!");
			this.abort();
		}

		refresh(e);
	}

	private int getExistChild() {
		DefaultKingdeeTreeNode node = null;
		int number = 0;
		node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null) {
			number = node.getChildCount();
		}
		return number;
	}

	public void actionByHand_actionPerformed(ActionEvent e) throws Exception {
		if (getExistChild() > 0) {
			FDCMsgBox.showWarning(this, "非末级项目不能新增!");
			this.abort();
		}
		super.actionByHand_actionPerformed(e);
		UIContext uiContext = new UIContext(this);
		uiContext.put("ByHand", Boolean.TRUE);
		this.prepareData(uiContext);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RoomAreaCompensateNewUI.class.getName(), uiContext,
						null, "ADDNEW");
		uiWindow.show();
		this.execQuery();
	}

	public void actionByScheme_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String idStr = "";
		idStr = this.getSelectedKeyValue();
		if (idStr != null && !"".equals(idStr)) {
			RoomAreaCompensateInfo info = RoomAreaCompensateFactory.getRemoteInstance().getRoomAreaCompensateInfo(
					"select id,name,compensateState where id='"
							+ idStr + "'");
			if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMAUDITTED)) {
				FDCMsgBox.showWarning(this, "单据已审批，不能修改!");
				this.abort();
			} else if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMAUDITTING)) {
				FDCMsgBox.showWarning(this, "单据审批中，不能修改!");
				this.abort();
			} else if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMRECEIVED)) {
				FDCMsgBox.showWarning(this, "单据已收款，不能修改!");
				this.abort();
			} else if (info.getCompensateState().equals(
					RoomCompensateStateEnum.COMRECEIVING)) {
				FDCMsgBox.showWarning(this, "单据收款中，不能修改!");
				this.abort();
			}
			FDCClientUtils.checkBillInWorkflow(this, idStr);
			
			String uiClassName = "com.kingdee.eas.fdc.sellhouse.client.RoomAreaCompensateNewUI";
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, idStr);
			try {
				IUIWindow uiWindow = UIFactory.createUIFactory(
						UIFactoryName.MODEL).create(uiClassName, uiContext,
						null, OprtState.EDIT);
				uiWindow.show();
				this.execQuery();
			} catch (UIException e1) {
				logger.error(e1.getMessage() + "打开户型界面失败！");
			}
		} else {
			FDCMsgBox.showWarning(this, "请先选择记录!");
			this.abort();
		}

	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				String allSpIdStr = FDCTreeHelper
						.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node,
								"SellProject").keySet());
				if (allSpIdStr.trim().length() == 0) {
					allSpIdStr = "'nullnull'";
				}

				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", allSpIdStr,
								CompareType.INNER));
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				String orgUnitIdStr = "'nullnull'";
				filter.getFilterItems().add(
						new FilterItemInfo("orgUnit.id", orgUnitIdStr,
								CompareType.INNER));
			} else if (node.getUserObject() instanceof BuildingInfo) {// 楼栋
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				String buildingId = building.getId().toString();
				filter.getFilterItems().add(
						new FilterItemInfo("building.id", buildingId,
								CompareType.EQUALS));
			} else if (node.getUserObject() instanceof BuildingUnitInfo) {// 单元
				BuildingUnitInfo unit = (BuildingUnitInfo) node.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("unit.id", unit.getId().toString(),
								CompareType.EQUALS));
			} else if (node.getUserObject() instanceof SubareaInfo) { // 分区
				SubareaInfo subarea = (SubareaInfo) node.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("subarea.id", subarea.getId()
								.toString(), CompareType.EQUALS));
			}
		}
		// 合并查询条件
		viewInfo = (EntityViewInfo) mainQuery.clone();
		try {
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

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected void refresh(ActionEvent e) throws Exception {
		// super.refresh(e);
		this.execQuery();
	}

	private boolean isSellOrg() {
		boolean res = false;
		try {
			FullOrgUnitInfo fullOrginfo = SysContext.getSysContext()
					.getCurrentOrgUnit().castToFullOrgUnitInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("sellHouseStrut", Boolean.TRUE,
							CompareType.EQUALS));
			if (fullOrginfo != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("unit.id", fullOrginfo.getId()
								.toString(), CompareType.EQUALS));
			}
			filter.getFilterItems().add(
					new FilterItemInfo("tree.id", OrgConstants.SALE_VIEW_ID,
							CompareType.EQUALS));
			res = FDCOrgStructureFactory.getRemoteInstance().exists(filter);
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "获得售楼组织失败!");
		}

		return res;
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		// super.actionRefresh_actionPerformed(e);
		if (this.kdtRoomAreaCompensate.getSelectedComponent().getName().equals(
				"kdpRoom")) {
			this.tblRoom.removeRows();
			IRowSet rs = RoomAreaCompensateFactory.getRemoteInstance()
					.getRoomInfoList(filterStr());
			if (rs != null) {
				loadRoomInfo(rs);
			}

		} else {
			this.execQuery();
		}
	}

	protected void kdtRoomAreaCompensate_stateChanged(ChangeEvent e)
			throws Exception {
		super.kdtRoomAreaCompensate_stateChanged(e);
		if (this.isSellOrg()) {
			boolean result = true;
			if (e.getSource() != null) {
				if (((KDTabbedPane) e.getSource()).getSelectedComponent()
						.getName().equals("kdpRoom")) {
					result = false;

				} else {
					DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
							.getLastSelectedPathComponent();
					if (node != null) {
						if (node.getUserObject() instanceof OrgStructureInfo) {
							result = false;
						} else {
							result = false;
						}
					}
					this.initTree();
					this.execQuery();
				}
			}

			this.actionLocate.setEnabled(result);
			this.actionQuery.setEnabled(result);
			this.actionPrint.setEnabled(result);
			this.actionPrintPreview.setEnabled(result);
			this.actionByHand.setEnabled(result);
			this.actionByScheme.setEnabled(result);
			this.actionAudit.setEnabled(result);
			this.actionUnAudit.setEnabled(result);
			this.actionReceipt.setEnabled(result);
			this.actionStop.setEnabled(result);
			this.actionViewWorkFlow.setEnabled(result);
			// this.actionRefresh.setEnabled(result);

		}
	}

	public void actionViewWorkFlow_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		String fieldName = getQueryFieldNameBindingWf();
		String id = (String) getSelectedFieldValues(fieldName).get(0);
		IEnactmentService service = EnactmentServiceFactory
				.createRemoteEnactService();
		ProcessInstInfo processInstInfo = null;
		ProcessInstInfo[] procInsts = service
				.getProcessInstanceByHoldedObjectId(id);
		for (int i = 0, n = procInsts.length; i < n; i++) {
			if (procInsts[i].getState().startsWith("open")) {
				processInstInfo = procInsts[i];
			}
		}
		if (processInstInfo == null) {
			// 如果没有运行时流程，判断是否有可以展现的流程图并展现
			procInsts = service.getAllProcessInstancesByBizobjId(id);
			if (procInsts == null || procInsts.length <= 0)
				MsgBox.showInfo(this, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Msg_WFHasNotInstance"));
			else if (procInsts.length == 1) {
				showWorkflowDiagram(procInsts[0]);
			} else {
				UIContext uiContext = new UIContext(this);
				uiContext.put("procInsts", procInsts);
				String className = ProcessRunningListUI.class.getName();
				IUIWindow uiWindow = UIFactory.createUIFactory(
						UIFactoryName.MODEL).create(className, uiContext);
				uiWindow.show();
			}
		} else {
			showWorkflowDiagram(processInstInfo);
		}
	}

	public final String getQueryFieldNameBindingWf() {
		if (this instanceof IWorkflowUISupport) {
			IWorkflowUIEnhancement enhancement = ((IWorkflowUISupport) this)
					.getWorkflowUIEnhancement();
			return enhancement.getQueryFieldNameBindingWf(this);
		} else
			return getKeyFieldNameForWF();
	}

	public final ArrayList getSelectedFieldValues(String fieldName) {
		ArrayList list = new ArrayList();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		for (int i = 0; i < selectRows.length; i++) {
			ICell cell = tblMain.getRow(selectRows[i]).getCell(fieldName);
			if (cell == null) {
				MsgBox.showError(EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Error_KeyField_Fail"));
				SysUtil.abort();
			}
			if (cell.getValue() != null) {
				String id = cell.getValue().toString();
				if (!list.contains(id))
					list.add(id);
			}

		}
		return list;
	}

	private void showWorkflowDiagram(ProcessInstInfo processInstInfo)
			throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("id", processInstInfo.getProcInstId());
		uiContext.put("processInstInfo", processInstInfo);
		BasicWorkFlowMonitorPanel.Show(uiContext);
	}

}