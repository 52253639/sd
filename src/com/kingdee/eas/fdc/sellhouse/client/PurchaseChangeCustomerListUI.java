/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.base.uiframe.client.UIModelDialogFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class PurchaseChangeCustomerListUI extends
		AbstractPurchaseChangeCustomerListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PurchaseChangeCustomerListUI.class);

	/**
	 * output class constructor
	 */
	public PurchaseChangeCustomerListUI() throws Exception {
		super();
	}

	protected String getEditUIName() {
		// TODO 自动生成方法存根
		return PurchaseChangeCustomerEditUI.class.getName();
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}

		this.tblMain.getColumn("subarea.name").getStyleAttributes().setHided(
				false);
		this.tblMain.getColumn("building.name").getStyleAttributes().setHided(
				false);

		FilterInfo roomFilter = new FilterInfo();
		if (node.getUserObject() instanceof Integer) { // 已作废
			Integer unit = (Integer) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			roomFilter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));
			roomFilter.getFilterItems().add(new FilterItemInfo("unit", unit));

			this.tblMain.getColumn("subarea.name").getStyleAttributes()
					.setHided(true);
			this.tblMain.getColumn("building.name").getStyleAttributes()
					.setHided(true);
		} else if (node.getUserObject() instanceof BuildingUnitInfo) {
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node
					.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			roomFilter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));
			roomFilter.getFilterItems()
					.add(
							new FilterItemInfo("unit", new Integer(buildUnit
									.getSeq())));

			this.tblMain.getColumn("subarea.name").getStyleAttributes()
					.setHided(true);
			this.tblMain.getColumn("building.name").getStyleAttributes()
					.setHided(true);
		} else if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			String buildingId = building.getId().toString();
			roomFilter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));

			this.tblMain.getColumn("subarea.name").getStyleAttributes()
					.setHided(true);
			this.tblMain.getColumn("building.name").getStyleAttributes()
					.setHided(true);
		} else if (node.getUserObject() instanceof SubareaInfo) {
			SubareaInfo area = (SubareaInfo) node.getUserObject();
			String areaId = area.getId().toString();
			roomFilter.getFilterItems().add(
					new FilterItemInfo("building.subarea.id", areaId));

			this.tblMain.getColumn("subarea.name").getStyleAttributes()
					.setHided(true);
		} else if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
					.getUserObject();
			String sellProjectId = sellProject.getId().toString();
			roomFilter.getFilterItems()
					.add(
							new FilterItemInfo("building.sellProject.id",
									sellProjectId));
		} else {
			roomFilter.getFilterItems().add(new FilterItemInfo("id", null));
		}
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(roomFilter);
		view.getSelector().add("id");
		RoomCollection rooms = RoomFactory.getRemoteInstance()
				.getRoomCollection(view);
		Set idSet = new HashSet();
		idSet.add("null");
		for (int i = 0; i < rooms.size(); i++) {
			idSet.add(rooms.get(i).getId().toString());
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("room.id", idSet, CompareType.INCLUDE));
		filter.getFilterItems().add(
				new FilterItemInfo("purchaseState",
						PurchaseStateEnum.PURCHASEAUDIT_VALUE));

		filter.getFilterItems().add(
				new FilterItemInfo("purchaseState",
						PurchaseStateEnum.PURCHASECHANGE_VALUE));
		// add by yuhuan
		filter.getFilterItems().add(
				new FilterItemInfo("purchaseState",
						PurchaseStateEnum.PREPURCHASECHECK_VALUE));
		filter.setMaskString("#0 and (#1 or #2 or #3)");
		this.mainQuery.setFilter(filter);
		this.tblMain.removeRows();

	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		String purchaseID = getSelectedKeyValue();
		if (purchaseID == null || purchaseID.equals("")) {
			MsgBox.showWarning("请先选择认购单！");
			SysUtil.abort();
		}

		PurchaseInfo purchaseInfo = PurchaseFactory.getRemoteInstance()
				.getPurchaseInfo("where id = '" + purchaseID + "'");
		if (PurchaseStateEnum.PurchaseChange.equals(purchaseInfo
				.getPurchaseState())) {
			MsgBox.showWarning("单据处于变更中，不能再次进行变更！");
			SysUtil.abort();
		}
		getUIContext().put("purchaseID", purchaseID);
		super.actionAddNew_actionPerformed(e);
	}

	public void onLoad() throws Exception {
		// TODO 自动生成方法存根
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		actionLocate.setVisible(false);
		actionAuditResult.setEnabled(true);
		actionAuditResult.setVisible(true);
		actionQuery.setEnabled(false);
		actionQuery.setVisible(false);
		actionRefresh.setEnabled(true);
		actionRefresh.setVisible(true);
		this.btnProofOfPayment.setVisible(false);
		this.menuItemProofOfPayment.setVisible(false);
		this.btnProofOfPayment.setEnabled(false);
		this.menuItemProofOfPayment.setEnabled(false);
		this.actionAuditResult.setVisible(false);
		
		//add by jiyu_guan
		actionChgCheque.setEnabled(true);

		kDTable1.addKDTDataFillListener(new KDTDataFillListener() {

			public void afterDataFill(KDTDataRequestEvent e) {
				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
					IRow row = kDTable1.getRow(i);
					List list = FDCBillStateEnum.getEnumList();
					for (Iterator it = list.iterator(); it.hasNext();) {
						StringEnum enumeration = (StringEnum) it.next();
						if (enumeration.getValue().equals(
								row.getCell("state").getValue().toString())) {
							row.getCell("state").setValue(enumeration);
						}
					}
				}

			}

		});

		kDTable1.addKDTMouseListener(new KDTMouseListener() {

			public void tableClicked(KDTMouseEvent e) {
				if (e.getType() == KDTStyleConstants.BODY_ROW
						&& e.getClickCount() == 2) {
					String id = KDTableUtil.getSelectedRow(kDTable1).getCell(
							"id").getValue().toString();
					UIContext context = new UIContext(ui);
					context.put(UIContext.ID, id);
					context.put(UIContext.OWNER,
							PurchaseChangeCustomerListUI.this);
					try {
						IUIWindow window = UIFactory.createUIFactory(
								UIFactoryName.NEWWIN).create(
								PurchaseChangeCustomerEditUI.class.getName(),
								context, null, OprtState.VIEW);
						window.show();
					} catch (UIException e1) {
						PurchaseChangeCustomerListUI.this.handleException(e1);
					}
				}

			}

		});

		tblMain.addKDTDataFillListener(new KDTDataFillListener() {

			public void afterDataFill(KDTDataRequestEvent e) {
				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
					IRow row = tblMain.getRow(i);
					List list = PurchaseStateEnum.getEnumList();
					for (Iterator it = list.iterator(); it.hasNext();) {
						StringEnum enumeration = (StringEnum) it.next();
						if (enumeration.getValue().equals(
								row.getCell("purchaseState").getValue()
										.toString())) {
							row.getCell("purchaseState").setValue(enumeration);
						}
					}
					if (row.getCell("sellType").getValue() != null) {
						list = SellTypeEnum.getEnumList();
						for (Iterator it = list.iterator(); it.hasNext();) {
							StringEnum enumeration = (StringEnum) it.next();
							if (enumeration.getValue().equals(
									row.getCell("sellType").getValue()
											.toString())) {
								row.getCell("sellType").setValue(enumeration);
								break;
							}
						}
					}
				}

				for (int i = 0; i < kDTable1.getRowCount(); i++) {
					kDTable1.removeRow(0);
					i--;
				}

			}

		});

		tblMain.addKDTSelectListener(new KDTSelectListener() {

			public void tableSelectChanged(KDTSelectEvent e) {
				// 显示变更单
				FilterInfo filter = new FilterInfo();
				if (queryPurchaseChangeCustomerQuery == null) {
					setDataObject("queryPurchaseChangeCustomerQuery",
							new EntityViewInfo());
				}
				filter.appendFilterItem("purchase.id", getSelectedKeyValue());
				queryPurchaseChangeCustomerQuery.setFilter(filter);
				kDTable1.removeRows();
				kDTable1.getSelectManager().setSelectMode(
						KDTSelectManager.ROW_SELECT);
				kDTable1.getStyleAttributes().setLocked(true);
			}

		});

		this.actionUnAudit.setEnabled(false);
		this.actionUnAudit.setVisible(false);

		this.actionPrePurchase.setVisible(false);
	}

	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if (isOrderForClickTableHead()
				&& e.getType() == KDTStyleConstants.HEAD_ROW
				&& e.getButton() == MouseEvent.BUTTON1
				&& e.getClickCount() == 1) {
			super.tblMain_tableClicked(e);
		}
		if (e.getType() == KDTStyleConstants.BODY_ROW
				&& e.getButton() == MouseEvent.BUTTON1
				&& e.getClickCount() == 2) {
			if (e.getType() == KDTStyleConstants.BODY_ROW
					&& e.getButton() == MouseEvent.BUTTON1
					&& e.getClickCount() == 2) {

				String id = KDTableUtil.getSelectedRow(tblMain).getCell("id")
						.getValue().toString();
				UIContext context = new UIContext(ui);
				context.put(UIContext.ID, id);
				try {
					IUIWindow window = UIFactory.createUIFactory(
							UIFactoryName.NEWWIN).create(
							PurchaseEditUI.class.getName(), context, null,
							OprtState.VIEW);
					window.show();
				} catch (UIException e1) {
					PurchaseChangeCustomerListUI.this.handleException(e1);
				}
			}
		}

	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		if (KDTableUtil.getSelectedRow(kDTable1) == null) {
			MsgBox.showWarning("请先选择认购更名单！");
			SysUtil.abort();
		}
		String id = KDTableUtil.getSelectedRow(kDTable1).getCell("id")
				.getValue().toString();

		UIContext context = new UIContext(ui);
		context.put(UIContext.ID, id);
		context.put(UIContext.OWNER, PurchaseChangeCustomerListUI.this);
		try {
			IUIWindow window = UIFactory.createUIFactory(UIFactoryName.NEWWIN)
					.create(PurchaseChangeCustomerEditUI.class.getName(),
							context, null, OprtState.VIEW);
			window.show();
		} catch (UIException e1) {
			this.handleException(e1);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (KDTableUtil.getSelectedRow(kDTable1) == null) {
			MsgBox.showWarning("请先选择认购更名单！");
			SysUtil.abort();
		}
		String id = KDTableUtil.getSelectedRow(kDTable1).getCell("id")
				.getValue().toString();

		PurchaseChangeCustomerInfo customerInfo = PurchaseChangeCustomerFactory
				.getRemoteInstance().getPurchaseChangeCustomerInfo(
						"where id = '" + id + "'");
		if (FDCBillStateEnum.SAVED.equals(customerInfo.getState())
				|| FDCBillStateEnum.SUBMITTED.equals(customerInfo.getState())) {

		} else {
			MsgBox.showWarning("当前单据不允许审批！");
			SysUtil.abort();
		}
		if (EnactmentServiceFactory.createRemoteEnactService()
				.checkAssignmentInfo(
						id,
						SysContext.getSysContext().getCurrentUserInfo().getId()
								.toString()) != null) {
			MsgBox.showWarning("已在工作流处理中，当前任务或执行人不匹配！");
			SysUtil.abort();
		}

		UIContext context = new UIContext(ui);
		context.put(UIContext.ID, id);
		context.put(UIContext.OWNER, PurchaseChangeCustomerListUI.this);
		try {
			IUIWindow window = UIFactory.createUIFactory(UIFactoryName.NEWWIN)
					.create(PurchaseChangeCustomerEditUI.class.getName(),
							context, null, OprtState.EDIT);
			window.show();
		} catch (UIException e1) {
			this.handleException(e1);
		}
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (KDTableUtil.getSelectedRow(kDTable1) == null) {
			MsgBox.showWarning("请先选择认购更名单！");
			SysUtil.abort();
		}
		String id = KDTableUtil.getSelectedRow(kDTable1).getCell("id")
				.getValue().toString();

		PurchaseChangeCustomerInfo customerInfo = PurchaseChangeCustomerFactory
				.getRemoteInstance().getPurchaseChangeCustomerInfo(
						"where id = '" + id + "'");
		if (!FDCBillStateEnum.SAVED.equals(customerInfo.getState())
				&& !FDCBillStateEnum.SUBMITTED.equals(customerInfo.getState())) {
			MsgBox.showWarning("当前单据状态不允许删除！");
		} else {
			int rtn = MsgBox.showConfirm2("是否确定删除?");
			if (MsgBox.OK == rtn) {
				if (EnactmentServiceFactory.createRemoteEnactService()
						.checkAssignmentInfo(
								id,
								SysContext.getSysContext().getCurrentUserInfo()
										.getId().toString()) != null) {
					MsgBox.showWarning("已在工作流处理中，当前任务或执行人不匹配！");
					SysUtil.abort();
				}
				PurchaseChangeCustomerFactory.getRemoteInstance().delete(
						"where id = '" + id + "'");
				kDTable1.removeRows();
			}

		}
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (KDTableUtil.getSelectedRow(kDTable1) == null) {
			MsgBox.showWarning("请先选择认购更名单！");
			SysUtil.abort();
		}
		String id = KDTableUtil.getSelectedRow(kDTable1).getCell("id")
				.getValue().toString();

		PurchaseChangeCustomerInfo customerInfo = PurchaseChangeCustomerFactory
				.getRemoteInstance().getPurchaseChangeCustomerInfo(
						"where id = '" + id + "'");
		if (FDCBillStateEnum.SUBMITTED.equals(customerInfo.getState())) {

		} else {
			MsgBox.showWarning("当前单据不允许审批！");
			SysUtil.abort();
		}

		PurchaseChangeCustomerFactory.getRemoteInstance().audit(
				BOSUuid.read(id));
		kDTable1.removeRows();
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		super.actionUnAudit_actionPerformed(e);
	}

	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO 自动生成方法存根
		if (KDTableUtil.getSelectedRow(kDTable1) == null) {
			MsgBox.showWarning("请先选择认购更名单！");
			SysUtil.abort();
		}
		String boID = KDTableUtil.getSelectedRow(kDTable1).getCell("id")
				.getValue().toString();
		boolean isEdit = false;
		AttachmentClientManager acm = AttachmentManagerFactory
				.getClientManager();

		if (boID == null) {
			return;
		}
		if (getBillStatePropertyName() != null) {
			int rowIdx = kDTable1.getSelectManager().getActiveRowIndex();
			ICell cell = kDTable1.getCell(rowIdx, getBillStatePropertyName());
			Object obj = cell.getValue();
			if (obj != null
					&& (obj.toString()
							.equals(FDCBillStateEnum.SAVED.toString())
							|| obj.toString().equals(
									FDCBillStateEnum.SUBMITTED.toString())
							|| obj.toString().equals(
									FDCBillStateEnum.AUDITTING.toString())
							|| obj.toString().equals(
									BillStatusEnum.SAVE.toString())
							|| obj.toString().equals(
									BillStatusEnum.SUBMIT.toString()) || obj
							.toString().equals(
									BillStatusEnum.AUDITING.toString()))) {
				isEdit = true;
			} else {
				isEdit = false;
			}

		}
		acm.showAttachmentListUIByBoID(boID, this, isEdit); // boID 是 与附件关联的
		// 业务对象的 ID
	}

	public void actionAuditResult_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO 自动生成方法存根
		if (KDTableUtil.getSelectedRow(kDTable1) == null) {
			MsgBox.showWarning("请先选择认购更名单！");
			SysUtil.abort();
		}
		String id = KDTableUtil.getSelectedRow(kDTable1).getCell("id")
				.getValue().toString();
		if (!StringUtils.isEmpty(id)) {
			MultiApproveUtil.showApproveHis(BOSUuid.read(id),
					UIModelDialogFactory.class.getName(), this);
		}
	}

	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		if (KDTableUtil.getSelectedRow(kDTable1) == null) {
			MsgBox.showWarning("请先选择认购更名单！");
			SysUtil.abort();
		}
		String id = KDTableUtil.getSelectedRow(kDTable1).getCell("id")
				.getValue().toString();

		IEnactmentService service2 = EnactmentServiceFactory
				.createRemoteEnactService();
		ProcessInstInfo instInfo = null;
		ProcessInstInfo[] procInsts = service2
				.getProcessInstanceByHoldedObjectId(id);
		for (int i = 0, n = procInsts.length; i < n; i++) {
			// modify by gongyin,流程挂起时也显示流程图
			if ("open.running".equals(procInsts[i].getState())
					|| "open.not_running.suspended".equals(procInsts[i]
							.getState())) {
				instInfo = procInsts[i];
			}
		}
		if (instInfo == null) {
			MsgBox.showInfo(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_WFHasNotInstance"));
			// MessageBox("没有正在运行的对应流程实例");
		} else {
			// 显示UI
			UIContext uiContext = new UIContext(this);
			uiContext.put("id", instInfo.getProcInstId());
			String className = BasicWorkFlowMonitorPanel.class.getName();
			IUIWindow uiWindow = UIFactory.createUIFactory(getEditUIModal())
					.create(className, uiContext);
			uiWindow.show();
		}
	}

	private void showWorkflowDiagram(ProcessInstInfo processInstInfo)
			throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("id", processInstInfo.getProcInstId());
		uiContext.put("processInstInfo", processInstInfo);
		String className = BasicWorkFlowMonitorPanel.class.getName();
		IUIWindow uiWindow = UIFactory.createUIFactory(getEditUIModal())
				.create(className, uiContext);
		uiWindow.show();
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {

		this.kDTable1.removeRows();
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}
	
	public void actionChgCheque_actionPerformed(ActionEvent e) throws Exception {
		IRow row = KDTableUtil.getSelectedRow(kDTable1);
		if (row == null) {
			MsgBox.showWarning(this, "请先选中一张更名单!");
			SysUtil.abort();
		} else if (row.getCell("state").getValue() == null
				|| !((FDCBillStateEnum) row.getCell("state").getValue())
						.getName().equals(FDCBillStateEnum.AUDITTED.getName())) {
			MsgBox.showWarning(this, "请先审批更名单!");
			SysUtil.abort();
		}
		Map ctx = new UIContext(this);
		String purID = (String) row.getCell("id").getValue();
		ctx.put("purID", purID);
		ctx.put(UIContext.OWNER, this);

		IUIWindow uiWindow = null;
		// 弹出模式
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				MakeOutChangeCustomerUI.class.getName(), ctx, null,
				OprtState.ADDNEW);

		// 开始展现UI
		uiWindow.show();
		// 清缓存，重新从数据库查一次
		// CacheServiceFactory.getInstance().discardType(
		// new InvoiceInfo().getBOSType());
		refresh(null);
	}
}