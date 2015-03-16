/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMenu;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.monitor.client.ProcessRunningListUI;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.log.LogUtil;
import com.kingdee.eas.base.permission.client.util.PermissionHelper;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.AimProductTypeGetter;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectStatusFactory;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.SettledMonthlyHelper;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fm.common.AmountUnitEnum;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AimCostUI extends AbstractAimCostUI {

	private CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext()
			.getCurrentCostUnit();

	private AimCostHandler handler;
	private boolean isFirstLoad=true;
	private DateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
	
	//记录转换后的货币数量单位用于刷新后数据一致
	private AmountUnitEnum Amountunit = AmountUnitEnum.getEnum("yuan") ;
	
	private BigDecimal buildArea = FDCHelper.ZERO;
	private BigDecimal sellArea = FDCHelper.ZERO;
	public AimCostUI() throws Exception {
		super();
	}

	// 数据对象变化时，刷新界面状态
	protected void setActionState() {

	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

	protected void initTree() throws Exception {
		this.initTable();
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true,
				(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		if(isFirstLoad&&(treeMain==null||treeMain.getRowCount()>1)) {
			isFirstLoad=false;
			return;
		}
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		AimProductTypeGetter proGetter = new AimProductTypeGetter(selectObjId,ProjectStageEnum.AIMCOST);
		Set productTypeSet = FDCHelper.getSetByArray(proGetter
				.getProductTypeIds());
		KDBizPromptBox f7Product = new KDBizPromptBox();
		f7Product
				.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		filter.getFilterItems().add(
				new FilterItemInfo("id", productTypeSet, CompareType.INCLUDE));
		view.setFilter(filter);
		f7Product.setEntityViewInfo(view);
		f7Product.setEditable(true);
		f7Product.setDisplayFormat("$name$");
		f7Product.setEditFormat("$number$");
		f7Product.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Product);
		tblMain.getColumn("product").setEditor(f7Editor);
		if (handler != null
				&& selectObjId.equals(this.handler.aimCost.getOrgOrProId())) {
			return;
		}
		if (handler != null) {
			this.verifySaved(e.getOldLeadSelectionPath());
		}
		handler = new AimCostHandler(selectObjId, null);
		Object obj = getUIContext().get(UIContext.UICLASSPARAM);
		if((obj==null||!obj.equals("AimCostRevise"))&&handler.aimCost!=null&&handler.aimCost.getId()!=null&&SettledMonthlyHelper.existObject(null, new AimCostInfo().getBOSType(), handler.aimCost.getId().toString())){
			tblMain.getStyleAttributes().setLocked(true);
			actionSubmit.setEnabled(false);
			actionAddRow.setEnabled(false);
			actionDeleteRow.setEnabled(false);
		}
		this.setVersionButton();
		fillText();
		fillTable();
		int acctNameIndex = tblMain.getColumn("acctName").getColumnIndex() + 1;
		// tblMain.getViewManager().setFreezeView(-1, acctNameIndex);
		tblMain.getViewManager().freeze(0, acctNameIndex);
		actionImportData.setVisible(actionAddRow.isVisible());
		actionImportData.setEnabled(actionAddRow.isEnabled());
	}

	/**
	 * 设置表格属性
	 */
	private void initTable() {
		KDTable table = this.tblMain;
		table.checkParsed();
//		table.getStyleAttributes().setLocked(false);
		// if (currentOrg.isIsBizUnit()) {
		// } else {
		// table.getStyleAttributes().setLocked(true);
		// }
		table.setRefresh(false);
		table.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		// table.getViewManager().setFreezeView(-1, 2);
		table.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_CELL_SELECT);
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		table.getColumn("acctNumber").getStyleAttributes().setLocked(true);
		Color lockColor = FDCTableHelper.cantEditColor;//new Color(0xF0AAD9);
		table.getColumn("acctNumber").getStyleAttributes().setBackground(
				lockColor);
		table.getColumn("sumAimCost").getStyleAttributes().setLocked(true);
		table.getColumn("sumAimCost").getStyleAttributes().setBackground(
				lockColor);
		table.getColumn("offset").getStyleAttributes().setLocked(true);
		table.getColumn("offset").getStyleAttributes().setBackground(lockColor);
		table.getColumn("aimCostCap").getStyleAttributes().setLocked(true);
		table.getColumn("aimCostCap").getStyleAttributes().setBackground(
				lockColor);
		table.getColumn("sumAimCostCap").getStyleAttributes().setLocked(true);
		table.getColumn("sumAimCostCap").getStyleAttributes().setBackground(
				lockColor);
		table.getColumn("buildPart").getStyleAttributes().setLocked(true);
		table.getColumn("buildPart").getStyleAttributes().setBackground(
				lockColor);
		table.getColumn("sellPart").getStyleAttributes().setLocked(true);
		table.getColumn("sellPart").getStyleAttributes().setBackground(
				lockColor);

		table.getColumn("workload").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("price").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("aimCost").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("sumAimCost").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("offset").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("aimCostCap").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("sumAimCostCap").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("buildPart").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("sellPart").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);

		table.getColumn("workload").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("price").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(4));
		table.getColumn("aimCost").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("sumAimCost").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("aimCostCap").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(4));
		table.getColumn("sumAimCostCap").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(4));
		table.getColumn("offset").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("buildPart").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("sellPart").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));

		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("acctName").setEditor(txtEditor);

		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("workload").setEditor(numberEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("unit").setEditor(txtEditor);

		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(4);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("price").setEditor(numberEditor);

		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("aimCost").setEditor(numberEditor);

		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("description").setEditor(txtEditor);
		table.getScriptManager().setAutoRun(true);
		table.setUserObject(null);
		/*
		 * int acctNameIndex=table.getColumn("acctName").getColumnIndex()+1;
		 * table.getViewManager().setFreezeView(-1, acctNameIndex);
		 * table.getViewManager().freeze(rowIndex, colIndex);
		 */

		table.getColumn("sumAimCostCap").getStyleAttributes().setHided(true);
		table.getColumn("sumAimCost").getStyleAttributes().setHided(true);
		table.getColumn("aimCostCap").getStyleAttributes().setHided(true);
	}

	/**
	 * 屏蔽回车键
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {

	}

	private void fillText() throws BOSException, SQLException, EASBizException {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return;
		}
		String selectObjId = this.getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		// String[] projectIds = null;
		// if (node.getUserObject() instanceof CurProjectInfo) {
		// CurProjectInfo project = (CurProjectInfo) node.getUserObject();
		// // projectIds = FDCHelper.getProjectLeafIds(project);
		// projectIds = new String[] { project.getId().toString() };
		// } else {
		// OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
		// if (oui.getUnit() == null) {
		// return;
		// }
		// FullOrgUnitInfo info = oui.getUnit();
		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// view.setFilter(filter);
		// String[] leafIds = FDCHelper.getFullOrgUnitLeafIds(info);
		// if (leafIds != null && leafIds.length != 0) {
		// filter.getFilterItems().add(
		// new FilterItemInfo("fullOrgUnit", FDCHelper
		// .getSetByArray(leafIds), CompareType.INCLUDE));
		// filter.getFilterItems().add(
		// new FilterItemInfo("isLeaf", Boolean.TRUE));
		// CurProjectCollection projects = CurProjectFactory
		// .getRemoteInstance().getCurProjectCollection(view);
		// projectIds = new String[projects.size()];
		// for (int i = 0; i < projectIds.length; i++) {
		// projectIds[i] = projects.get(i).getId().toString();
		// }
		// }
		// }

		BigDecimal buildArea = FDCHelper.getApportionValue(selectObjId,
				ApportionTypeInfo.buildAreaType, ProjectStageEnum.AIMCOST);
		this.txtSumArea.setText(buildArea.toString());
		handler.buildArea = buildArea;
		this.buildArea = buildArea;
		BigDecimal sellArea = FDCHelper.getApportionValue(selectObjId,
				ApportionTypeInfo.sellAreaType, ProjectStageEnum.AIMCOST);
		this.txtSellArea.setText(sellArea.toString());
		handler.sellArea = sellArea;
		this.sellArea = sellArea;
		//编制人
		if (this.handler.aimCost.getCreator() != null) {
			this.txtCreator
					.setText(this.handler.aimCost.getCreator().getName());
		} else {
			this.txtCreator.setText(null);
		}
		//編制日期
		if(this.handler.aimCost.getCreateTime()!=null){
			this.txtCreateDate.setText(formatDay.format(this.handler.aimCost.getCreateTime()));
		}else{
			this.txtCreateDate.setText(null);
		}
		//修订人
		if(this.handler.aimCost.getLastUpdateUser()!=null){
			this.txtUpdatePeople.setText(this.handler.aimCost.getLastUpdateUser().getName());
		}else{
			this.txtUpdatePeople.setText(null);
		}
		//修订日期
		if (this.handler.aimCost.getRecenseDate() != null) {
			this.txtCreateTime.setText(formatDay
					.format(this.handler.aimCost.getRecenseDate()));
		} else {
			this.txtCreateTime.setText(null);
		}
		//审批人
		if (this.handler.aimCost.getAuditor() != null) {
			this.txtAuditor
					.setText(this.handler.aimCost.getAuditor().getName());
		} else {
			this.txtAuditor.setText(null);
		}
		//审批日期
		if (this.handler.aimCost.getAuditDate() != null) {
			this.txtAuditDate.setText(formatDay
					.format(this.handler.aimCost.getAuditDate()));
		} else {
			this.txtAuditDate.setText(null);
		}
		this.txtVersionNumber.setText(this.handler.aimCost.getVersionNumber());
		this.txtVersionName.setText(this.handler.aimCost.getVersionName());
		FDCClientHelper.initComboMeasureStage(comboMeasureStage,false);
		GlUtils.setSelectedItem(comboMeasureStage, this.handler.aimCost.getMeasureStage());
	}

	private String getSelectObjId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo.getId().toString();
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {

			}
			FullOrgUnitInfo info = oui.getUnit();
			return info.getId().toString();
		}
		return null;
	}

	private void setVersionButton() {
		String curShowVersion = handler.aimCost.getVersionNumber();
		if (curShowVersion.equals(handler.versionHandler.getLastVersion())) {
			this.actionNextVersion.setEnabled(false);
			this.actionLastVersion.setEnabled(false);
			// if (currentOrg.isIsBizUnit()) {
			// // this.actionSubmit.setEnabled(true);
			if (handler.aimCost.getAuditor() == null) {
				if (handler.aimCost.getId() != null) {
					this.actionAudit.setEnabled(true);
				}
				this.actionUnAudit.setEnabled(false);
			} else {
				this.actionAudit.setEnabled(false);
				this.actionUnAudit.setEnabled(true);
			}
			//
			// }
		} else {
			this.actionNextVersion.setEnabled(true);
			this.actionLastVersion.setEnabled(true);
			// this.actionSubmit.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}
		this.actionRecense.setEnabled(true);
		this.actionAddRow.setEnabled(true);
		this.actionDeleteRow.setEnabled(true);
		if (curShowVersion.equals(handler.versionHandler.getFirstVersion())) {
			this.actionPreVersion.setEnabled(false);
			this.actionFirstVersion.setEnabled(false);
			if (handler.aimCost.getId() == null) {
				this.actionRecense.setEnabled(false);
				this.actionAddRow.setEnabled(false);
				this.actionDeleteRow.setEnabled(false);
				//没有目标成本
			} else {
				this.actionRecense.setEnabled(true);
				this.actionAddRow.setEnabled(true);
				this.actionDeleteRow.setEnabled(true);
			}
		} else {
			this.actionPreVersion.setEnabled(true);
			this.actionFirstVersion.setEnabled(true);
		}
		((KDMenuItem)btnAmountUnit.getAssitButton(0)).setSelected(true);
//		this.setAmountUnit(AmountUnitEnum.yuan);
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		this.actionAttachment.setVisible(true);
		actionWorkFlowG.setVisible(true);
		actionWorkFlowG.setEnabled(true);
		btnViewWorkFlow.setIcon(EASResource.getIcon("imgTbtn_flowchart"));
        this.menuItemWorkFlowG.setIcon(EASResource.getIcon("imgTbtn_flowchart"));
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		AimCostInfo aimCost = (AimCostInfo) ((Map) this.getUIContext().get(
				"DATAOBJECTS")).get("billInfo");
		if (aimCost != null) {
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) this.treeMain
					.getModel().getRoot();
			DefaultKingdeeTreeNode node = findNode(root, aimCost
					.getOrgOrProId());
			if (node != null) {
				this.treeMain.setSelectionNode(node);
				this.treeMain.setVisible(false);
			}
		}
		// 单价的显示位变成小数后两位by sxhong
		//FDCHelper.formatTableNumber(getMainTable(), "price");
		/** 单价的显示位变成小数后四位by neo**/
		FDCHelper.formatTableNumber(getMainTable(), "price","#,##0.0000;-#,##0.0000");
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int acctNameIndex = tblMain.getColumn("acctName")
						.getColumnIndex() + 1;
				tblMain.getViewManager().freeze(0, acctNameIndex);
				pnlAimCost.setDividerLocation(0.8);
				pnlAimCost.setResizeWeight(0.8);
			}
		});
		
		Object obj = getUIContext().get("MainMenuName");
		if (!FDCHelper.isEmpty(obj)) {
			setUITitle(obj.toString());
		}
		
		//取菜单参数
		obj = getUIContext().get(UIContext.UICLASSPARAM);
		if(obj!=null&&obj.equals("AimCostRevise")){
			//目标成本修订,屏蔽保存按钮
			actionSubmit.setEnabled(false);
			actionSubmit.setVisible(false);
		}
		pnlAimCost.setDividerLocation(500);

	}

	private void initControl() {
		this.actionImportData.setVisible(true);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionLocate.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionApportion.setVisible(false);
		if (currentOrg == null || !currentOrg.isIsBizUnit()) {
			// this.actionSubmit.setEnabled(false);
			// this.actionAddRow.setEnabled(false);
			// this.actionDeleteRow.setEnabled(false);
			// this.actionImportData.setEnabled(false);
			// this.actionRecense.setEnabled(false);
			// this.actionExpression.setEnabled(false);
			// this.actionRevert.setEnabled(false);
			// this.actionAudit.setEnabled(false);
			// this.actionUnAudit.setEnabled(false);
		}
		this.txtVersionName.setEnabled(false);
		List unitList = (List) AmountUnitEnum.getEnumList();
		final KDMenu menu=new KDMenu(btnAmountUnit.getText());
		menu.setIcon(EASResource.getIcon("imgTbtn_unit"));
		for (int i = 0; i < unitList.size(); i++) {
			final AmountUnitEnum unitEnum = (AmountUnitEnum) unitList.get(i);
			KDMenuItem menuItem = new KDMenuItem(unitEnum.getAlias());
			menuItem.setAction(new ItemAction() {
				public void actionPerformed(ActionEvent e) {
					KDMenuItem source=(KDMenuItem)e.getSource();
					for (int i = 0; i < btnAmountUnit.getAssistButtonCount(); i++) {
						KDMenuItem assitButton = (KDMenuItem) btnAmountUnit.getAssitButton(i);
						KDMenuItem menuItem = (KDMenuItem) menu.getMenuComponent(i);
						if (assitButton.getText().equals(source.getText())) {
							assitButton.setIcon(EASResource.getIcon("imgTbtn_affirm"));
							menuItem.setIcon(assitButton.getIcon());
						} else {
							assitButton.setIcon(null);
							menuItem.setIcon(null);
						}
					}
					setAmountUnit(unitEnum);
				}
			});
			menuItem.setText(unitEnum.getAlias());
			menuItem.setToolTipText(unitEnum.getAlias());
			this.btnAmountUnit.addAssistMenuItem(menuItem);
			KDMenuItem menuItem2 = new KDMenuItem(unitEnum.getAlias());
			menuItem2.setAction(menuItem.getAction());
			menuItem2.setText(unitEnum.getAlias());
			menuItem2.setToolTipText(unitEnum.getAlias());
			menu.add(menuItem2);
/*			if(unitEnum==AmountUnitEnum.yuan){
				menuItem.setSelected(true);
				menuItem2.setSelected(true);
			}*/
		}
		menuBiz.add(menu);
	}

	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData() {
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		KDTable table = this.tblMain;
		String[] cols = new String[] { "aimCost", "buildPart", "sellPart" ,"aimCostCap"};
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List aimRowList = new ArrayList();
				List sumAimRowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						aimRowList.add(rowAfter);
					}
					if ((CostAccountInfo) rowAfter.getCell("acctNumber")
							.getUserObject() != null
							&& ((CostAccountInfo) rowAfter
									.getCell("acctNumber").getUserObject())
									.isIsLeaf()) {
						sumAimRowList.add(rowAfter);
					}
				}
//				for (int j = 0; j < cols.length; j++) {
//					BigDecimal aimCost = FMConstants.ZERO;
//					for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
//						IRow rowAdd = (IRow) aimRowList.get(rowIndex);
//						Object value = rowAdd.getCell(cols[j]).getValue();
//						if (value != null) {
//							if (value instanceof BigDecimal) {
//								aimCost = aimCost.add((BigDecimal) value);
//							} else if (value instanceof Integer) {
//								aimCost = aimCost.add(new BigDecimal(
//										((Integer) value).toString()));
//							}
//						}
//					}
//					row.getCell(cols[j]).setValue(aimCost);
//				}
/*				*//**
				 * 所有报表的所有单方不存在上下级汇总关系，而应该是各个级次的都等于各自的对应成本除以对应的面积，而非下级的单方汇总
				 * @author pengwei_hou Date: 2009-01-04 22:23:23
				 */
				for (int j = 0; j < cols.length; j++) {
					if(aimRowList.size()==0){
						continue;
					}
					if(cols[j].equals("aimCost") || cols[j].equals("aimCostCap")){
						BigDecimal amt = FMConstants.ZERO;
						for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
							IRow rowAdd = (IRow) aimRowList.get(rowIndex);
							Object value = rowAdd.getCell(cols[j]).getValue();
							if (value != null) {
								if (value instanceof BigDecimal) {
									amt = amt.add((BigDecimal) value);
								} else if (value instanceof Integer) {
									amt = amt.add(new BigDecimal(
											((Integer) value).toString()));
								}
							}
						}
						row.getCell(cols[j]).setValue(amt);
					}
					if(cols[j].equals("buildPart")){
						BigDecimal rowAimCost = FDCHelper.toBigDecimal(row.getCell("aimCost").getValue());
						BigDecimal amt = FDCNumberHelper.divide(rowAimCost, this.buildArea, 2, BigDecimal.ROUND_HALF_UP);
						row.getCell(cols[j]).setValue(amt);
					}
					if(cols[j].equals("sellPart")){
						BigDecimal rowAimCost = FDCHelper.toBigDecimal(row.getCell("aimCost").getValue());
						BigDecimal  amt = FDCNumberHelper.divide(rowAimCost, this.sellArea, 2, BigDecimal.ROUND_HALF_UP);
						row.getCell(cols[j]).setValue(amt);
					}
					
				}
				BigDecimal aimCost = (BigDecimal) row.getCell("aimCost")
						.getValue();
				if (proNode.isLeaf()) {
					row.getCell("sumAimCost").setValue(aimCost);
				} else {
					BigDecimal sumAimCost = FDCHelper.ZERO;
					if (sumAimRowList.size() > 0) {
						for (int rowIndex = 0; rowIndex < sumAimRowList.size(); rowIndex++) {
							IRow rowAdd = (IRow) sumAimRowList.get(rowIndex);
							Object value = rowAdd.getCell("sumAimCost")
									.getValue();
							if (value != null) {
								if (value instanceof BigDecimal) {
									sumAimCost = sumAimCost
											.add((BigDecimal) value);
								} else if (value instanceof Integer) {
									sumAimCost = sumAimCost.add(new BigDecimal(
											((Integer) value).toString()));
								}
							}
						}
						row.getCell("sumAimCost").setValue(sumAimCost);
					}
				}
				BigDecimal sumAimCost = (BigDecimal) row.getCell("sumAimCost")
						.getValue();
				// 设置差额项
				row.getCell("offset").setValue(FDCHelper.subtract(aimCost,sumAimCost));
			}
		}
		try {
			AimCostClientHelper.setTotalCostRow(table, cols);
		} catch (Exception e) {
			handUIException(e);
		}
	}

	private void setAmountUnit(AmountUnitEnum unit) {
		this.Amountunit = unit ; 
		if (unit.equals(AmountUnitEnum.yuan)) {
			this.tblMain.getColumn("price").getStyleAttributes()
					.setHided(false);
			this.tblMain.getColumn("unit").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("workload").getStyleAttributes().setHided(
					false);
			this.tblMain.getColumn("aimCost").getStyleAttributes().setHided(
					false);
			this.tblMain.getColumn("sumAimCost").getStyleAttributes().setHided(
					false);
			this.tblMain.getColumn("aimCostCap").getStyleAttributes().setHided(
					true);
			this.tblMain.getColumn("sumAimCostCap").getStyleAttributes()
					.setHided(true);
		} else {
			this.tblMain.getColumn("price").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("unit").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("workload").getStyleAttributes().setHided(
					true);
			this.tblMain.getColumn("aimCost").getStyleAttributes().setHided(
					true);
			this.tblMain.getColumn("sumAimCost").getStyleAttributes().setHided(
					true);
			this.tblMain.getColumn("aimCostCap").getStyleAttributes().setHided(
					false);
			ICell cell = this.tblMain.getHeadRow(0).getCell("aimCostCap");
			String text = (String) cell.getValue();
			if (text.indexOf("(") != -1) {
				text = text.substring(0, text.indexOf("("));
			}
			cell.setValue(text + "(" + unit.getAlias() + ")");
			this.tblMain.getColumn("sumAimCostCap").getStyleAttributes()
					.setHided(false);
			cell = this.tblMain.getHeadRow(0).getCell("sumAimCostCap");
			text = (String) cell.getValue();
			if (text.indexOf("(") != -1) {
				text = text.substring(0, text.indexOf("("));
			}
			cell.setValue(text + "(" + unit.getAlias() + ")");
			for (int i = 0; i < tblMain.getRowCount(); i++) {
				IRow row = tblMain.getRow(i);
				Object value = row.getCell("aimCost").getValue();
				if (value != null) {
					row.getCell("aimCostCap").setValue(
							((BigDecimal) value).divide(new BigDecimal(unit
									.getValue()), 4, BigDecimal.ROUND_HALF_UP));
				}
				value = row.getCell("sumAimCost").getValue();
				if (value != null) {
					row.getCell("sumAimCostCap").setValue(
							((BigDecimal) value).divide(new BigDecimal(unit
									.getValue()), 4, BigDecimal.ROUND_HALF_UP));
				}

			}
			
		}
		this.tblMain.getColumn("sumAimCost").getStyleAttributes().setHided(
				true);
		this.tblMain.getColumn("sumAimCostCap").getStyleAttributes()
		.setHided(true);
		
		this.setUnionData();

	}

	private DefaultKingdeeTreeNode findNode(DefaultKingdeeTreeNode node,
			String id) {
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			if (projectInfo.getId().toString().equals(id)) {
				return node;
			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			FullOrgUnitInfo info = oui.getUnit();
			if (info.getId().toString().equals(id)) {
				return node;
			}
		}

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode findNode = findNode(
					(DefaultKingdeeTreeNode) node.getChildAt(i), id);
			if (findNode != null) {
				return findNode;
			}

		}
		return null;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	}

	public void actionAddRow_actionPerformed(ActionEvent e) throws Exception {
		handleNoData();
		super.actionAddRow_actionPerformed(e);
		if (this.tblMain.getRowCount() == 0) {
			return;
		}
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			this.tblMain.getSelectManager().set(this.tblMain.getRowCount() - 1,
					0);
			index = this.tblMain.getRowCount() - 1;
		}
		IRow selectRow = this.tblMain.getRow(index);
		if (selectRow.getUserObject() == null) {
			CostAccountInfo acct = (CostAccountInfo) selectRow.getCell(
					"acctNumber").getUserObject();
			// 格外校验
			if (tblMain.getRow(index + 1) != null
					&& tblMain.getRow(index + 1).getUserObject() == null) {
				if (tblMain.getRow(index + 1).getTreeLevel() > selectRow
						.getTreeLevel()) {
					this.setMessageText(AimCostHandler
							.getResource("NotLeafAcct"));
					this.showMessage();
					return;
				}
			}
			if (acct.isIsLeaf()) {
				IRow row = this.tblMain.addRow(index + 1);
				row.setTreeLevel(selectRow.getTreeLevel() + 1);
				CostEntryInfo info = new CostEntryInfo();
				info.setCostAccount(acct);
				row.setUserObject(info);
				this.tblMain.setUserObject("addRow");
			} else {
				this.setMessageText(AimCostHandler.getResource("NotLeafAcct"));
				this.showMessage();
			}
		} else {
			CostEntryInfo infoUp = (CostEntryInfo) selectRow.getUserObject();
			if(this.handler.isDetailAcctRow(selectRow)){
				//明细行录入
				this.handler.storeRow(selectRow);
				IRow tempRow=tblMain.addRow(index+1);
				tempRow.setUserObject(infoUp);
				tempRow.setTreeLevel(selectRow.getTreeLevel()+1);
				this.handler.loadRow(tempRow);
				selectRow.setUserObject(null);
				this.handler.clearDetailAcctRow(tblMain, selectRow);
				this.handler.setDetailAcctRowNull(selectRow);
				selectRow.getStyleAttributes().setLocked(true);
				selectRow.getStyleAttributes().setBackground(new Color(0xF0EDD9));
				index++;
				setUnionData();
				IRow row = this.tblMain.addRow(index + 1);
				row.setTreeLevel(selectRow.getTreeLevel()+1);
				CostEntryInfo info = new CostEntryInfo();
				info.setCostAccount(infoUp.getCostAccount());
				row.setUserObject(info);
				this.tblMain.setUserObject("addRow");
			}else{
				IRow row = this.tblMain.addRow(index + 1);
				row.setTreeLevel(selectRow.getTreeLevel());
				CostEntryInfo info = new CostEntryInfo();
				info.setCostAccount(infoUp.getCostAccount());
				row.setUserObject(info);
				this.tblMain.setUserObject("addRow");
			}
		}

	}

	public void actionDeleteRow_actionPerformed(ActionEvent e) throws Exception {
		handleNoData();
		super.actionDeleteRow_actionPerformed(e);
		KDTSelectManager selectManager = tblMain.getSelectManager();
		if (selectManager == null || selectManager.size() == 0) {
			return;
		}
		for (int i = 0; i < selectManager.size(); i++) {
			KDTSelectBlock selectBlock = selectManager.get(i);
			for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
				IRow selectRow = this.tblMain.getRow(j);
				if (selectRow.getUserObject() != null) {
					selectRow.getCell("price").setUserObject("delete");
				}
			}
		}
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = (IRow) tblMain.getRow(i);
			if (row.getCell("price").getUserObject() != null) {
				if(this.handler.isDetailAcctRow(row)){
					this.handler.clearDetailAcctRow(tblMain, row);
					row.getCell("price").setUserObject(null);
				}else{
					int j=row.getRowIndex()-1;
					int k=row.getRowIndex()+1;
					if(j>0){
						IRow parentRow = tblMain.getRow(j);
						if(parentRow.getUserObject()==null){
							if(k==tblMain.getRowCount()||this.handler.isDetailAcctRow(tblMain.getRow(k))
									//非明细行
									||tblMain.getRow(k).getUserObject()==null){
								this.handler.clearDetailAcctRow(tblMain, parentRow);
								CostEntryInfo info = new CostEntryInfo();
								info.setCostAccount((CostAccountInfo)parentRow.getCell("acctNumber").getUserObject());
								parentRow.setUserObject(info);
								this.handler.setDetailAcctRow(parentRow);
								parentRow.getStyleAttributes().setBackground(Color.WHITE);
								parentRow.getStyleAttributes().setLocked(false);
								parentRow.getCell("acctNumber").getStyleAttributes().setLocked(true);
								parentRow.getCell("acctNumber").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
								parentRow.getCell("acctName").getStyleAttributes().setLocked(true);
								parentRow.getCell("acctName").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
								parentRow.getCell("buildPart").getStyleAttributes().setLocked(true);
								parentRow.getCell("buildPart").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
								parentRow.getCell("sellPart").getStyleAttributes().setLocked(true);
								parentRow.getCell("sellPart").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
							}
						}
					}
					this.tblMain.removeRow(row.getRowIndex());
					i--;
				}
				this.tblMain.setUserObject("delteRow");
				setUnionData();
			}
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		 
		handleNoData();
		if (handler.aimCost.getAuditor() != null) {
			MsgBox.showError(AimCostHandler.getResource("HasAudit"));
			return;
		}
		// 校验不通过,直接返回
		if (!this.verify()) {
			return;
		}
		// 没有修改直接返回
		if (!handler.isEditedTable(this.tblMain)) {
			this.setMessageText(AimCostHandler.getResource("NoChangeNoSubmit"));
			this.showMessage();
			return;
		}
		// 修改老版本数据,提示
		if (handler.aimCost.getVersionNumber().equals(
				handler.versionHandler.getLastVersion())) {
			this.handler.submitTableData(this.tblMain);
			this
					.setMessageText(EASResource
							.getString(FrameWorkClientUtils.strResource
									+ "Msg_Save_OK"));
			this.showMessage();

			refresh();
		} else {
			if (MsgBox.showConfirm2(this, AimCostHandler
					.getResource("OldVersionNoSubmit")) == MsgBox.YES) {
				this.actionRecense_actionPerformed(null);
			}
		}
		//保存时 清空缓存 修改 工程项目是否有 目标成本 "hasAimCost" true
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
		TreeBaseInfo info = (TreeBaseInfo)node.getUserObject();
		info.put("hasAimCost", Boolean.TRUE);
		node.setTextColor(Color.BLACK);
		CacheServiceFactory.getInstance().discardType(new AimCostInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new CurProjectInfo().getBOSType());
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.btnRecense.setIcon(EASResource.getIcon("imgTbtn_review"));
		this.btnFirstVersion.setIcon(EASResource.getIcon("imgTbtn_first"));
		this.btnPreVersion.setIcon(EASResource.getIcon("imgTbtn_previous"));
		this.btnNextVersion.setIcon(EASResource.getIcon("imgTbtn_next"));
		this.btnLastVersion.setIcon(EASResource.getIcon("imgTbtn_last"));
		this.btnAddRow.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.btnDeleteRow.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		this.btnExpression.setIcon(EASResource
				.getIcon("imgTbtn_assistantaccountdetail"));
		this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		this.btnVersionInfo.setIcon(EASResource.getIcon("imgTbtn_particular"));
		this.btnApportion.setIcon(EASResource.getIcon("imgTbtn_split"));
		this.btnRevert.setIcon(EASResource.getIcon("imgTbtn_restore"));
		this.btnAmountUnit.setIcon(EASResource.getIcon("imgTbtn_unit"));
		setButtonDefaultStyl(this.btnFirstVersion);
		setButtonDefaultStyl(this.btnPreVersion);
		setButtonDefaultStyl(this.btnNextVersion);
		setButtonDefaultStyl(this.btnLastVersion);
		this.menuItemFirstVersion.setIcon(EASResource.getIcon("imgTbtn_first"));
		this.menuItemPreVersion
				.setIcon(EASResource.getIcon("imgTbtn_previous"));
		this.menuItemNextVersion.setIcon(EASResource.getIcon("imgTbtn_next"));
		this.menuItemLastVersion.setIcon(EASResource.getIcon("imgTbtn_last"));
		this.menuItemAddRow.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.menuItemDeleteRow.setIcon(EASResource
				.getIcon("imgTbtn_deleteline"));
		this.menuItemAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.menuItemUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		this.menuItemApportion.setIcon(EASResource.getIcon("imgTbtn_split"));
		this.menuItemRevert.setIcon(EASResource.getIcon("imgTbtn_restore"));
		this.menuItemSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.menuItemRecense.setIcon(EASResource.getIcon("imgTbtn_review"));
		this.menuItemExpression.setIcon(EASResource
				.getIcon("imgTbtn_assistantaccountdetail"));
		this.menuItemAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.menuItemUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		this.menuItemVersionInfo.setIcon(EASResource
				.getIcon("imgTbtn_particular"));
		
		final Object obj = getUIContext().get(UIContext.UICLASSPARAM);
		if(obj!=null&&obj.equals("AimCostRevise")){
			//目标成本修订,提示修订
			actionCloseInit.setVisible(false);
			actionCloseInit.setEnabled(false);
		}else{
			actionRecense.setVisible(false);
			actionRecense.setEnabled(false);
			if(isForbidInput()){				
				this.getMainTable().setEditable(false); //没有启用目标成本查询录入功能，禁止用户在table中输入值 Added by Owen_wen 2010-09-09
				actionSubmit.setEnabled(false);
				actionSubmit.setVisible(false);
				actionAddRow.setEnabled(false);
				actionAddRow.setVisible(false);
				actionDeleteRow.setEnabled(false);
				actionDeleteRow.setVisible(false);
				actionRevert.setVisible(false);
				actionRevert.setEnabled(false);
				actionExpression.setVisible(false);
				actionExpression.setEnabled(false);
			}
		}
		actionImportData.setVisible(actionAddRow.isVisible());
		actionImportData.setEnabled(actionAddRow.isEnabled());
		comboMeasureStage.setEnabled(false);
	}

	public boolean verify() {
		int count = 0;
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if(this.handler.isDetailAcctRow(row)){
				if(!this.handler.isDetailAcctHasInput(row)){
					continue;
				}
/*				this.handler.storeRow(row);
				CostEntryInfo info=(CostEntryInfo)row.getUserObject();
				if(FDCHelper.toBigDecimal(info.getCostAmount()).signum()==0){
					if(FDCHelper.isEmpty(info.getWorkload())
							&&FDCHelper.isEmpty(info.getPrice())
							&&FDCHelper.isEmpty(info.getUnit())
							&&info.getProduct()==null
							&&FDCHelper.isEmpty(info.getPrice())){
						continue;
					}
				}*/
			}
			if (row.getUserObject() != null) {
				// if (row.getCell("acctName").getValue() == null) {
				// this.setMessageText(AimCostHandler
				// .getResource("AcctNameNull"));
				// this.showMessage();
				// tblMain.getSelectManager().select(0, 0);
				// tblMain.getSelectManager().select(row.getRowIndex(), 1);
				// return false;
				// }
				if (row.getCell("aimCost").getValue() == null) {
					this.setMessageText(AimCostHandler
							.getResource("CostAmountNull"));
					this.showMessage();
					tblMain.getSelectManager().select(0, 0);
					tblMain.getSelectManager().select(row.getRowIndex(), 5);
					return false;
				} else {
					BigDecimal value = (BigDecimal) row.getCell("aimCost")
							.getValue();
					if (value.compareTo(FDCHelper.ZERO) == 0) {
						this.setMessageText(AimCostHandler
								.getResource("CostAmountNull"));
						this.showMessage();
						tblMain.getSelectManager().select(0, 0);
						tblMain.getSelectManager().select(row.getRowIndex(), 5);
						return false;
					}
					if (value.compareTo(FDCHelper.MAX_VALUE) > 0) {
						this.setMessageText(AimCostHandler
								.getResource("MaxValue"));
						this.showMessage();
						tblMain.getSelectManager().select(0, 0);
						tblMain.getSelectManager().select(row.getRowIndex(), 5);
						return false;
					}
				}
				count++;
			}
		}
		// if (count == 0) {
		// this.setMessageText(AimCostHandler.getResource("NoDetailData"));
		// this.showMessage();
		// return false;
		// }
		return true;
	}

	/**
	 * 获取query中的主键列名称，返回供编辑/删除时获取主键用，默认值为"id"，继承类可以重载
	 * 
	 */
	protected String getKeyFieldName() {
		return "acctNumber";
	}

	public void actionNextVersion_actionPerformed(ActionEvent e)
			throws Exception {
		verifySaved(null);
		String selectObjId = this.getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		String nextVersion = AimCostVersionHandler
				.getNextVersion(this.handler.aimCost.getVersionNumber());
		handler = new AimCostHandler(selectObjId, nextVersion);
		this.setVersionButton();
		fillText();
		fillTable();
	}

	public void actionPreVersion_actionPerformed(ActionEvent e)
			throws Exception {
		verifySaved(null);
		String selectObjId = this.getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		String preVersion = AimCostVersionHandler
				.getPreVersion(this.handler.aimCost.getVersionNumber());
		handler = new AimCostHandler(selectObjId, preVersion);
		this.setVersionButton();
		fillText();
		fillTable();
	}

	public void actionRecense_actionPerformed(ActionEvent e) throws Exception {
		// 当前版本没有审批 或没有修改，不必修订
		if (handler == null
				|| (handler.aimCost != null && !FDCBillStateEnum.AUDITTED
						.equals(handler.aimCost.getState()))
				|| !handler.isEditedTable(this.tblMain)) {
			MsgBox.showInfo(AimCostHandler
					.getResource("NoAuditNoChangeNoRecense"));
			return;
		}
		if (!this.verify()) {
			this.abort();
		}
		if (this.handler.recenseTableDate(this.tblMain)) {
			//R101123-225目标成本修订支持工作审批流. by zhiyuan_tang
			if (e != null) {
				AimCostFactory.getRemoteInstance().recense(this.handler.aimCost);
			}
			refresh();
		}
	}

	private void refresh() throws Exception, BOSException, SQLException {
		String selectObjId = getSelectObjId();
		handler = new AimCostHandler(selectObjId, null);
		this.setVersionButton();
		fillText();
		fillTable();
	}

	private void fillTable() throws Exception {
		// 非明细不能修改 BT484556 by hpw 
		boolean isLeafPrj = isLeafPrj();
		if(!isLeafPrj){
			tblMain.getStyleAttributes().setLocked(true);
			actionSubmit.setEnabled(false);
			actionAddRow.setEnabled(false);
			actionDeleteRow.setEnabled(false);
		}else{
			tblMain.getStyleAttributes().setLocked(false);
			actionSubmit.setEnabled(true);
			actionAddRow.setEnabled(true);
			actionDeleteRow.setEnabled(true);
		}
		
		this.tblMain.getColumn("sumAimCostCap").getStyleAttributes().setHided(
				true);
		this.tblMain.getColumn("sumAimCost").getStyleAttributes()
				.setHided(true);
		handler.fillTable(this.tblMain);
		//取菜单参数
		Object obj = getUIContext().get(UIContext.UICLASSPARAM);
		if(obj!=null&&obj.equals("AimCostRevise")){
			//目标成本修订,屏蔽保存按钮
			if(!actionRecense.isEnabled()){
				for(int i=0;i<tblMain.getRowCount();i++){
					tblMain.getRow(i).getStyleAttributes().setLocked(true);
				}
//				this.tblMain.getStyleAttributes().setLocked(true);
			}
		}else{
			tblMain.getStyleAttributes().setLocked(isForbidInput());
		}
		
		if(!isLeafPrj){
			tblMain.getStyleAttributes().setLocked(true);
		}
		this.setUnionData();
		
		//刷新后货币数量单位和刷新前一致
		this.setAmountUnit(Amountunit);
	}

	public void actionExpression_actionPerformed(ActionEvent e)
			throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		int colIndex = this.tblMain.getSelectManager().getActiveColumnIndex();
		if (rowIndex == -1) {
			return;
		}
		if (this.tblMain.getColumn(colIndex).getEditor() != null
				&& this.tblMain.getColumn(colIndex).getEditor().getComponent() instanceof KDFormattedTextField) {
			ICell cell = this.tblMain.getRow(rowIndex).getCell(colIndex);
			if (cell.getStyleAttributes().isLocked()) {
				return;
			}
			String expression = (String) cell.getUserObject();
			if (expression == null) {
				if (cell.getValue() != null
						&& cell.getValue() instanceof BigDecimal) {
					expression = cell.getValue().toString();
				}
			}
			String re = ExpressionUI.showExpressionUI(expression);
			if (re == null || re.length() == 0) {
				return;
			}
			BigDecimal result = null;
			try {
				result = new BigDecimal(FDCHelper.getScriptResult(re)
						.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
				MsgBox.showError(AimCostHandler.getResource("ExpressionErrer"));
			}
			if (result != null && result.compareTo(FDCHelper.ZERO) < 0) {
				// this.setMessageText(AimCostHandler.getResource("NotNegetive"));
				return;
			}
			if (result != null) {
				cell.setUserObject(re);
				cell.setValue(result);
				KDTEditEvent editEvent = new KDTEditEvent(cell);
				editEvent.setRowIndex(rowIndex);
				editEvent.setColIndex(colIndex);
				this.tblMain_editStopped(editEvent);
			}
		}
	}

	protected void tblMain_editStopping(KDTEditEvent e) throws Exception {
		super.tblMain_editStopping(e);

	}

	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		// TODO 自动生成方法存根
		super.tblMain_editStopped(e);
		Object oldValue = e.getOldValue();
		Object newValue=e.getValue();
		if(oldValue==null&&newValue==null){
			return;
		}
		if(oldValue!=null&&newValue!=null){
			if(oldValue instanceof BigDecimal&&newValue instanceof BigDecimal&&FDCHelper.subtract(oldValue, newValue).signum()==0){
				return;
			}else if(oldValue.equals(newValue)){
				return;
			}
		}
		int rowIndex = e.getRowIndex();
		int columnIndex = e.getColIndex();
		
		IRow row = tblMain.getRow(rowIndex);
		if(row.getUserObject()==null){
			//处理删除的行
			CostEntryInfo entry=new CostEntryInfo();
			CostAccountInfo acct=(CostAccountInfo)row.getCell("acctNumber").getUserObject();
			entry.setCostAccount(acct);
			row.setUserObject(entry);
			handler.setDetailAcctRow(row);
		}
		if (tblMain.getColumnKey(columnIndex).equals("workload")
				|| tblMain.getColumnKey(columnIndex).equals("price")) {
			BigDecimal workload = (BigDecimal) row.getCell("workload")
					.getValue();
			BigDecimal price = (BigDecimal) row.getCell("price").getValue();
			if (workload == null) {
				workload = FDCHelper.ZERO;
			}
			if (price == null) {
				price = FDCHelper.ZERO;
			}
			if (workload.compareTo(FDCHelper.ZERO) == 0
					&& price.compareTo(FDCHelper.ZERO) == 0) {
				row.getCell("aimCost").getStyleAttributes().setLocked(false);
				row.getCell("workload").setValue(null);
				row.getCell("price").setValue(null);
			} else {
				BigDecimal aimCost = workload.multiply(price);
				row.getCell("aimCost").setValue(aimCost);
				row.getCell("aimCost").getStyleAttributes().setLocked(true);
				if (aimCost != null) {
					if (handler.buildArea != null
							&& handler.buildArea.compareTo(FDCHelper.ZERO) != 0) {
						BigDecimal buildPart = aimCost.divide(
								handler.buildArea, BigDecimal.ROUND_HALF_UP);
						row.getCell("buildPart").setValue(buildPart);
					}
					if (handler.sellArea != null
							&& handler.sellArea.compareTo(FDCHelper.ZERO) != 0) {
						BigDecimal sellPart = aimCost.divide(handler.sellArea,
								BigDecimal.ROUND_HALF_UP);
						row.getCell("sellPart").setValue(sellPart);
					}
				} else {
					row.getCell("buildPart").setValue(null);
					row.getCell("sellPart").setValue(null);
				}
				setUnionData();
			}
		} else if (tblMain.getColumnKey(columnIndex).equals("aimCost")) {
			BigDecimal aimCost = (BigDecimal) row.getCell("aimCost").getValue();
			if (aimCost != null) {
				if (handler.buildArea != null
						&& handler.buildArea.compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal buildPart = aimCost.divide(handler.buildArea,
							BigDecimal.ROUND_HALF_UP);
					row.getCell("buildPart").setValue(buildPart);
				}
				if (handler.sellArea != null
						&& handler.sellArea.compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal sellPart = aimCost.divide(handler.sellArea,
							BigDecimal.ROUND_HALF_UP);
					row.getCell("sellPart").setValue(sellPart);
				}
			} else {
				row.getCell("buildPart").setValue(null);
				row.getCell("sellPart").setValue(null);
			}
			setUnionData();
		}
		if(this.handler.isDetailAcctRow(row)){
			if(e.getValue()!=null){
				this.handler.setDetailAcctHasInput(row);
			}else{
				boolean isEmpty=true;
				for(int i=2;i<tblMain.getColumnCount();i++){
					if(tblMain.getColumnIndex("aimCost")==i
							||tblMain.getColumnIndex("buildPart")==i||tblMain.getColumnIndex("sellPart")==i){
						if(!FDCHelper.isNullZero((BigDecimal)row.getCell("aimCost").getValue())){
							isEmpty=false;
							break;
						}
						continue;
					}
					if(!FDCHelper.isEmpty(row.getCell(i).getValue())){
						isEmpty=false;
						break;
					}
				}
				if(isEmpty ){
//					this.handler.setDetailAcctHasNotInput(row);
//					this.handler.clearDetailAcctRow(tblMain, row);
//					row.getCell("price").setUserObject(null);
//					this.tblMain.setUserObject("delteRow");
					btnDeleteRow.doClick();
				}else{
					this.handler.setDetailAcctHasInput(row);
				}
				
			}
		}
	}

	public void actionFirstVersion_actionPerformed(ActionEvent e)
			throws Exception {
		verifySaved(null);
		String selectObjId = this.getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		handler = new AimCostHandler(selectObjId, handler.versionHandler
				.getFirstVersion());
		this.setVersionButton();
		fillText();
		fillTable();
	}

	private void verifySaved(TreePath treePath) {
		handleNoData();
		this.tblMain.getSelectManager().select(0, 0);
		if (handler == null) {
			return;
		}
		if (handler.isEditedTable(this.tblMain)) {
			if (MsgBox.showConfirm2New(this, AimCostHandler.getResource("NoSave")) == MsgBox.YES) {
				if (!this.verify()) {
					if (treePath != null) {
						this.treeMain.setSelectionPath(treePath);
					}
					this.abort();
				}
				if (handler.aimCost.getAuditor() != null) {
					if (treePath != null) {
						this.treeMain.setSelectionPath(treePath);
					}
					MsgBox.showError(AimCostHandler.getResource("HasAudit"));
					this.abort();
				}
				final Object obj = getUIContext().get(UIContext.UICLASSPARAM);
				if(obj!=null&&obj.equals("AimCostRevise")){
					//目标成本修订,提示修订
					this.btnRecense.doClick();
				}else{
					this.btnSubmit.doClick();
				}
			}
		}
	}

	public void actionLastVersion_actionPerformed(ActionEvent e)
			throws Exception {
		if(handler==null){
			return;
		}
		verifySaved(null);
		String selectObjId = this.getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		handler = new AimCostHandler(selectObjId, null);
		this.setVersionButton();
		fillText();
		fillTable();
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		handleNoData();
		if (handler.aimCost.getAuditor() != null) {
			MsgBox.showInfo(AimCostHandler.getResource("HasAudit"));
			return;
		}
		verifySaved(null);
		if (this.handler.aimCost.getId() != null) {
			if(tblMain.getRowCount()<=0){
				MsgBox.showInfo(AimCostHandler.getResource("NoDetailRow"));
				return;
			}
			AimCostFactory.getRemoteInstance().audit(
					this.handler.aimCost.getId());
			MsgBox.showInfo(AimCostHandler.getResource("AuditSucces"));
			refresh();
		} else {
			MsgBox.showInfo(AimCostHandler.getResource("NoSaveNotAudit"));
		}
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		verifySaved(null);
		AimCostFactory.getRemoteInstance()
				.unaudit(this.handler.aimCost.getId());
		MsgBox.showInfo(AimCostHandler.getResource("UnauditSucces"));
		refresh();
	}

	public void actionApportion_actionPerformed(ActionEvent e) throws Exception {
		super.actionApportion_actionPerformed(e);
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		String selectObjId = getSelectObjId();
		if(handler==null){
			handler = new AimCostHandler(selectObjId, null);
		}
		else
		{
			handler = new AimCostHandler(selectObjId, handler.aimCost
					.getVersionNumber());
		}
		this.setVersionButton();
		fillText();
		fillTable();
	}

	public void actionRevert_actionPerformed(ActionEvent e) throws Exception {
		handleNoData();
		super.actionRevert_actionPerformed(e);
		// 没有修改直接返回
		if (!handler.isEditedTable(this.tblMain)) {
			return;
		}
		fillTable();
/*		//回到以元为单位的界面
		setAmountUnit(AmountUnitEnum.getEnum("yuan"));
		for(int i = 0; i < this.menuBiz.getMenuComponentCount()-1; ++i)
		{
			KDMenuItem assitButton = (KDMenuItem) btnAmountUnit.getAssitButton(i);
			KDMenuItem menuItem = (KDMenuItem) this.menuBiz.getMenuComponent(i);
			
			assitButton.setIcon(null);
			menuItem.setIcon(null);
		}
		
		KDMenuItem assitButton = (KDMenuItem) btnAmountUnit.getAssitButton(0);
		KDMenuItem menuItem = (KDMenuItem) this.menuBiz.getMenuComponent(0);
		
		assitButton.setIcon(EASResource.getIcon("imgTbtn_affirm"));
		menuItem.setIcon(assitButton.getIcon());*/
	}

	public void actionVersionInfo_actionPerformed(ActionEvent e)
			throws Exception {
		handleNoData();
		super.actionVersionInfo_actionPerformed(e);
		String curShowVersion = handler.aimCost.getVersionNumber();
		if (!curShowVersion.equals(handler.versionHandler.getLastVersion())) {
			VersionInputUI
					.showViewUI(handler.aimCost.getVersionNumber(),
							handler.aimCost.getVersionName(), handler.aimCost
									.getDescription(), handler.aimCost
									.getRecenseDate(),handler.aimCost.getMeasureStage());
		} else {
			String id = null;
			if (handler.aimCost.getId() != null) {
				id = handler.aimCost.getId().toString();
			}
			Map map = VersionInputUI.showEditUI(
					handler.aimCost.getOrgOrProId(), id, handler.aimCost
							.getVersionNumber(), handler.aimCost
							.getVersionName(),
					handler.aimCost.getDescription(), handler.aimCost
							.getRecenseDate(),handler.aimCost.getMeasureStage());
			if (map.get("isConfirm") == null) {
				return;
			}
			handler.submitVersionData((String) map.get("versionName"),
					(String) map.get("description"), (Date) map
							.get("recenseDate"));
			this.fillText();
		}
	}

	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		if (handler == null) {
			return;
		}
		if (this.handler.aimCost.getId() == null) {
			MsgBox.showInfo(AimCostHandler.getResource("NoSaveNotAttach"));
			return;
		}
		String boID = this.handler.aimCost.getId().toString();
		AttachmentClientManager acm = AttachmentManagerFactory
				.getClientManager();
		if (boID == null) {
			return;
		}
		boolean isEditable=false;
		if(this.handler.aimCost.getAuditor() == null&&this.handler.aimCost.isIsLastVersion()){
			isEditable=true;
		}
		acm.showAttachmentListUIByBoID(boID, this,isEditable);

	}

	public boolean destroyWindow() {
		if(handler!=null){
			verifySaved(null);
		}
		return super.destroyWindow();
	}

	/*
	 * 导入参数设置 jackwang 2006/11/16
	 */
	protected ArrayList getImportParam() {
		DatataskParameter param = new DatataskParameter();
		// 当前节点
		Hashtable hs = new Hashtable();
		String orgOrProId = this.getSelectObjId();
		hs.put("orgOrProId", orgOrProId);
		//
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		//find bugs审计发现	NP类问题 	可能存在空指针问题
		if (node != null && node.getUserObject() != null) {
			if (node.getUserObject() instanceof CurProjectInfo) {
				CurProjectInfo curProjectInfo = (CurProjectInfo) node
						.getUserObject();
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("*"));
				sic.add(new SelectorItemInfo("curProjProductEntries.*"));
				sic.add(new SelectorItemInfo(
						"curProjProductEntries.curProjProEntrApporData"));
				sic.add(new SelectorItemInfo("curProjCostEntries.*"));
				CurProjectInfo paramProject = new CurProjectInfo();
				try {
					paramProject = CurProjectFactory.getRemoteInstance()
							.getCurProjectInfo(
									new ObjectUuidPK(curProjectInfo.getId()
											.toString()), sic);
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
				hs.put("curProjectInfo", paramProject);
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
				if (oui.getUnit() == null) {

				}
				FullOrgUnitInfo fullOrgUnitInfo = oui.getUnit();
				hs.put("fullOrgUnitInfo", fullOrgUnitInfo);
			}
		}
		// 当前最新版本号
		AimCostVersionHandler versionHandler;
		String lastVerisonNumber = "1.0";
		try {
			versionHandler = new AimCostVersionHandler(orgOrProId);
			lastVerisonNumber = versionHandler.getLastVersion();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		hs.put("lastVerisonNumber", lastVerisonNumber);
		// 最新headID
		String lastFid = null;
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("orgOrProId", orgOrProId));
		filter.getFilterItems().add(
				new FilterItemInfo("isLastVersion", Boolean.valueOf(true)));
		// String sql = "select top 1 FID from T_Aim_AimCost " + "where
		// FOrgOrProId='" + orgOrProId + " and FIsLastVersion = 1 '" ;//+ "order
		// by to_number(FVersionNumber) desc";
		evi.setFilter(filter);
		try {
			AimCostCollection acc;
			acc = AimCostFactory.getRemoteInstance().getAimCostCollection(evi);
			if (acc != null && acc.size() != 0) {
				lastFid = acc.get(0).getId().toString();// .getString("FID");
				hs.put("lastFid", lastFid);
			}
		} catch (BOSException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

		// IRowSet rs;
		// try {
		// rs = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
		// if (rs.next()) {
		// lastFid = rs.getString("FID");
		// hs.put("lastFid", lastFid);
		// }
		// } catch (BOSException e) {
		// e.printStackTrace();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		param.setContextParam(hs);//
		String solutionName = "eas.fdc.costmanager.costdb.aimcost";
		param.solutionName = solutionName;
		param.alias = EASResource.getString(resourcePath, "AimCost");
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		return paramList;
	}

	public void actionImportData_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionImportData_actionPerformed(e);
		handleNoData();
		DatataskCaller task = new DatataskCaller();
		task.setParentComponent(this);
		if (getImportParam() != null) {
			task.invoke(getImportParam(), DatataskMode.ImpMode, true, true);
		}
	}

	public static final String resourcePath = "com.kingdee.eas.fdc.costdb.CostDBResource";

	public void actionCloseInit_actionPerformed(ActionEvent e) throws Exception {
		handleNoData();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("projectStatus.id"));
			sic.add(new SelectorItemInfo("isLeaf"));
			CurProjectInfo info = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(projectInfo.getId()), sic);
			
			if (info.isIsLeaf()) {
				if (!info.getProjectStatus().getId().toString().equals(
						ProjectStatusInfo.notIntiID)) {
					this.setMessageText("工程项目状态不适合结束初始化！");
					this.showMessage();
					return;
				} else {
					info
							.setProjectStatus(ProjectStatusFactory
									.getRemoteInstance()
									.getProjectStatusInfo(
											new ObjectUuidPK(
													BOSUuid
															.read(ProjectStatusInfo.proceedingID))));
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("projectStatus");
					// CurProjectFactory.getRemoteInstance().save(projectInfo);
					CurProjectFactory.getRemoteInstance().updatePartial(
							info, selector);
					//增加日志
					IObjectPK pk = LogUtil.beginLog(null, "1",info.getBOSType(), null, selector.get(0).toString(), "AimCost_init" );
					LogUtil.afterLog(null, pk );
					
					this.setMessageText("成功结束初始化！");
					this.showMessage();
				}
			} else {
				this.setMessageText("非明细工程项目不能结束初始化！");
				this.showMessage();
				return;
			}
		} else {
			this.setMessageText("非明细工程项目不能结束初始化！");
			this.showMessage();
			return;
		}
	}
	
	private void handleNoData(){
		if(handler==null){
			SysUtil.abort();
		}
	}
	
	private boolean isForbid=false;
	private boolean hasinitForbid=false;
	private boolean isForbidInput(){
		if(!hasinitForbid){
			final Object obj = getUIContext().get(UIContext.UICLASSPARAM);
			if(obj==null||!obj.equals("AimCostRevise")){
				try {
					isForbid = !FDCUtils.getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_AIMCOSTINPUT);
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
			hasinitForbid=true;
		}
		return isForbid;
	}
	private boolean isLeafPrj() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null && node.getUserObject() != null && node.getUserObject() instanceof CurProjectInfo && ((CurProjectInfo)node.getUserObject()).isIsLeaf()) {
			return true;
		}else{
			return false;			
		}
	}
	
	public void beforeActionPerformed(ActionEvent e) {
		super.beforeActionPerformed(e);
		String action = e.getActionCommand();
		if(action == null || action.length()==0 || action.indexOf('$') == -1){
			return;
		}
		//确保每次操作每个action都调用
		handlePermissionForEachItemAction(action);
	}
    
    //同一实体，相同界面，相同方法的权限处理 by hpw 2010.11.18
    private String getPermItemNameByAction(String actionName){
    	String permItemName="ActionOnLoad";
    	boolean isAimCost = true;//查询
    	Object obj = getUIContext().get(UIContext.UICLASSPARAM);
		if(obj!=null&&obj.equals("AimCostRevise")){
			isAimCost=false;//修订
		}
    	if("ActionAddRow".endsWith(actionName)){
    		if(isAimCost){
    			permItemName="aim_cost_cb_addline";
    		}else{
    			permItemName="aim_cost_xj_addline";
    		}
    	}else if("ActionDeleteRow".endsWith(actionName)){
    		if(isAimCost){
    			permItemName="aim_cost_cb_delline";
    		}else{
    			permItemName="aim_cost_xj_delete";
    		}
    	}else if("ActionVersionInfo".endsWith(actionName)){
    		if(isAimCost){
    			permItemName="aim_cost_cb_version";
    		}else{
    			permItemName="aim_cost_xj_version";
    		}
    	}else if("ActionAudit".endsWith(actionName)){
    		if(isAimCost){
    			permItemName="aim_cost_cb_audit";
    		}else{
    			permItemName="aim_cost_xj_audit";
    		}
    	}else if("ActionUnAudit".endsWith(actionName)){
    		if(isAimCost){
    			permItemName="aim_cost_cb_unudit";
    		}else{
    			permItemName="aim_cost_xj_unaudit";
    		}
    	}else if("ActionPrint".endsWith(actionName)||"ActionPrintView".endsWith(actionName)){
    		if(isAimCost){
    			permItemName="aim_cost_cb_print";
    		}else{
    			permItemName="aim_cost_xj_print";
    		}
    	}else if("ActionCloseInit".endsWith(actionName)){
    		if(isAimCost){
    			permItemName="aim_cost_cb_closeinit";
    		}
    	}else if("aim_cost_xj_recense".endsWith(actionName)){
    		if(!isAimCost){
    			permItemName="actionRecense";
    		}
    	} else {
			// ActionOnLoad,ActionSubmit,ActionRefresh
			if (isAimCost) {
				permItemName = "aim_cost_cb_view";
			} else {
				permItemName = "aim_cost_xj_view";
			}
		}
    	return permItemName;
    }
    private void handlePermissionForEachItemAction(String actionName) {
		if(actionName == null || actionName.length()==0 || actionName.indexOf('$') == -1){
			return;
		}
		int index = actionName.indexOf('$');
		actionName = actionName.substring(index+1,actionName.length());
    	try {
			PermissionHelper.checkFunctionPermission(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()), new ObjectUuidPK(SysContext.getSysContext()
					.getCurrentOrgUnit().getId().toString()), getPermItemNameByAction(actionName));
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}

    /**
     * 同一实体，同一菜单，相同方法权限问题处理
     * by hpw 2010.11.17
     */
    
	protected void handlePermissionForItemAction(ItemAction action) {
//    	super.handlePermissionForItemAction(action);
    	String actionName = action.getActionName();
    	handlePermissionForEachItemAction(actionName);
    }
	
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		String selectId = null;
		if (this.handler.aimCost.getId() != null) {
			selectId = this.handler.aimCost.getId().toString();
		}
		FDCClientHelper.viewWorkFlowForEditUI(this, selectId);
	}
}