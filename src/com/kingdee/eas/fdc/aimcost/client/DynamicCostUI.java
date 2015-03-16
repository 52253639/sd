/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryFactory;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.DyCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DyProductTypeGetter;
import com.kingdee.eas.fdc.aimcost.DynamicCostFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostMap;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.HappenDataInfo;
import com.kingdee.eas.fdc.aimcost.IntendingCostEntryCollection;
import com.kingdee.eas.fdc.aimcost.IntendingCostEntryInfo;
import com.kingdee.eas.fdc.aimcost.VoucherForDynamicFactory;
import com.kingdee.eas.fdc.aimcost.VoucherForDynamicInfo;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.AdjustReasonInfo;
import com.kingdee.eas.fdc.basedata.AdjustTypeCollection;
import com.kingdee.eas.fdc.basedata.AdjustTypeFactory;
import com.kingdee.eas.fdc.basedata.AdjustTypeInfo;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewFactory;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IBeforeAccountView;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.VoucherForProjectFactory;
import com.kingdee.eas.fdc.basedata.VoucherForProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.PaymentSplitVoucherHelper;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class DynamicCostUI extends AbstractDynamicCostUI {

	private CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext()
			.getCurrentCostUnit();

	private AimCostSplitDataGetter aimGetter;

	private DyCostSplitDataGetter dyGetter;

	private HappenDataGetter happenGetter;

	private String objectId = null;

	private CostAccountInfo curAcct = null;

	private DyProductTypeGetter productTypeGetter;
	private BigDecimal nosplitAmt=null;
	private Map acctMap = new HashMap();
	private Map adjustMap =null;
	
	private boolean canAddNew = false;
	/** 是否允许调整 */
	private boolean isAdjust = false;
	
	private boolean isCanDelete = false;
	
	/** 删除的调整记录暂存 */
	private Map adjustDataMap = new HashMap();
	
	public DynamicCostUI() throws Exception {
		super();
	}

	// 数据对象变化时，刷新界面状态
	protected void setActionState() {

	}

	public static final String SETTLE = "Settle";

	public static final String NO_SETTLE = "NoSettle";

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if (e.getClickCount() == 2) {
			int rowIndex = e.getRowIndex();

			// if(getMainTable().getCell(rowIndex, colIndex).getValue() == null)
			// return;

			if(getMainTable().getRow(rowIndex)==null){
				return;
			}
			Object value = getMainTable().getRow(rowIndex).getUserObject();
			if (value == null)
				return;
			CostAccountInfo acctInfo = (CostAccountInfo) value;

			boolean b = acctInfo.isIsLeaf();
			// 显示出来时有错，暂时不显示非明细工程项目
			if (!b)
				return;
			// boolean prjIsLeaf=acctInfo.getCurProject().isIsLeaf();
			DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
					.getLastSelectedPathComponent();
			if (proNode == null || !proNode.isLeaf()) {
				return;
			}

			this.setCursorOfWair();
			Map map = new HashMap();

			String acctId = acctInfo.getId().toString();
			map.put("acctId", acctId);

			String projId = getSelectObjId();

			if (projId == null)
				return;

			this.happenGetter = new HappenDataGetter(projId, true, true,false);
			
			BigDecimal no_set_total = FDCConstants.B0;

			BigDecimal no_set_amt = null;
			HappenDataInfo happenDataInfo = (HappenDataInfo) this.happenGetter.conSplitMap
					.get(acctId + 0);

			if (happenDataInfo != null) {
				no_set_amt = happenDataInfo.getAmount();

				no_set_total = no_set_total.add(no_set_amt);
			}

			BigDecimal set_amt = null;

			happenDataInfo = (HappenDataInfo) this.happenGetter.conSplitMap
					.get(acctId + 1);
			if (happenDataInfo != null) {
				set_amt = happenDataInfo.getAmount();
			}

			//变更
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("isEnabled", Boolean.TRUE));
			view.getSorter().add(new SorterItemInfo("number"));
			view.getSelector().add("id");
			view.getSelector().add("name");
			ChangeTypeCollection changeTypes  = ChangeTypeFactory.getRemoteInstance()
					.getChangeTypeCollection(view);
			BigDecimal noSettleChange=FDCHelper.ZERO;
			BigDecimal settleChange=FDCHelper.ZERO;
			for(Iterator iter=changeTypes.iterator();iter.hasNext();){
				ChangeTypeInfo info=(ChangeTypeInfo)iter.next();
				String changeId=info.getId().toString();
				happenDataInfo = (HappenDataInfo) this.happenGetter.changeSplitMap.get(acctId + changeId + 1);
				
				if (happenDataInfo != null) {
					map.put(changeId+"Settle", happenDataInfo.getAmount());
					settleChange=FDCHelper.add(settleChange, happenDataInfo.getAmount());
				}
				
				happenDataInfo = (HappenDataInfo) this.happenGetter.changeSplitMap.get(acctId + changeId + 0);
				if (happenDataInfo != null) {
					map.put(changeId+"NoSettle", happenDataInfo.getAmount());
					noSettleChange=FDCHelper.add(noSettleChange, happenDataInfo.getAmount());
				}
			}
			no_set_total=FDCHelper.add(no_set_total, noSettleChange);
			BigDecimal set_total = null;
			happenDataInfo = (HappenDataInfo) this.happenGetter.settleSplitMap
					.get(acctId);
			if (happenDataInfo != null) {
				set_total = happenDataInfo.getAmount();
			}

			BigDecimal no_text = null;
			happenDataInfo = (HappenDataInfo) this.happenGetter.noTextSplitMap
					.get(acctId);
			if (happenDataInfo != null) {
				no_text = happenDataInfo.getAmount();
			}

			Object will_happen = getMainTable().getRow(rowIndex).getCell(
					"intendingHappen").getValue();

			map.put("no_set_amt", no_set_amt);
			map.put("no_set_total", no_set_total);

			map.put("set_amt", set_amt);
			map.put("set_total", set_total);

			map.put("no_text", no_text);
			map.put("will_happen", will_happen);
			map.put("FullDyDetailInfoTitle", "动态成本信息");

			FullDyDetailInfoUI.showDialogWindows(this, map);
			this.setCursorOfDefault();
		}
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

	protected void initTree() throws Exception {
		this.initMainTable();
		this.initEntryTable(this.tblAdjustRecord);
		this.initEntryTable(this.tblIntendingCost);
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true,
				(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	private void initEntryTable(KDTable table) throws BOSException {
		table.checkParsed();
		table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		table.getColumn("workload").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("price").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("costAmount").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);

		table.getColumn("workload").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("price").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(8));
		table.getColumn("costAmount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		// table.getColumn("acctName").getStyleAttributes().setBackground(new
		// Color(0xF0EDD9));
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("acctName").setEditor(txtEditor);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		// formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("workload").setEditor(numberEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("unit").setEditor(txtEditor);

		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(8);
		// formattedTextField.setNegatived(false);
		formattedTextField.setSupportedEmpty(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("price").setEditor(numberEditor);
		FDCHelper.formatTableNumber(table, "price");
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		// formattedTextField.setNegatived(false);
		formattedTextField.setRequired(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("costAmount").setEditor(numberEditor);
		table.getColumn("costAmount").getStyleAttributes().setBackground(
				FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);

		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("description").setEditor(txtEditor);
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		if (table.getName().equals("tblAdjustRecord")) {
			((KDFormattedTextField) table.getColumn("costAmount").getEditor()
					.getComponent()).setNegatived(true);
			KDDatePicker datePicker = new KDDatePicker();
			KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(
					datePicker);
			table.getColumn("adjustDate").setEditor(dateEditor);
			String formatString = "yyyy-MM-dd";
			table.getColumn("adjustDate").getStyleAttributes().setNumberFormat(
					formatString);
			table.getColumn("adjustDate").getStyleAttributes().setLocked(true);
			KDComboBox comboAdjustType = new KDComboBox();
			EntityViewInfo adjustTypeView = new EntityViewInfo();
			FilterInfo adjustTypeFilter = new FilterInfo();
			adjustTypeFilter.getFilterItems().add(
					new FilterItemInfo("isEnabled", new Integer(1)));
			adjustTypeView.setFilter(adjustTypeFilter);
			AdjustTypeCollection adjustTypes = AdjustTypeFactory
					.getRemoteInstance()
					.getAdjustTypeCollection(adjustTypeView);
			comboAdjustType.addItem(new AdjustTypeInfo());
			for (int i = 0; i < adjustTypes.size(); i++) {
				comboAdjustType.addItem(adjustTypes.get(i));
			}
			comboAdjustType.setSelectedIndex(0);
			ICellEditor f7AdjustTypeEditor = new KDTDefaultCellEditor(
					comboAdjustType);
			table.getColumn("adjustType").setEditor(f7AdjustTypeEditor);

			KDBizPromptBox f7AdjustReason = new KDBizPromptBox();
			f7AdjustReason
					.setQueryInfo("com.kingdee.eas.fdc.basedata.app.AdjustReasonQuery");
			EntityViewInfo adjustView = new EntityViewInfo();
			FilterInfo adjustFilter = new FilterInfo();
			adjustFilter.getFilterItems().add(
					new FilterItemInfo("isEnabled", new Integer(1)));
			adjustView.setFilter(adjustFilter);
			f7AdjustReason.setEntityViewInfo(adjustView);
			f7AdjustReason.setEditable(true);
			f7AdjustReason.setDisplayFormat("$name$");
			f7AdjustReason.setEditFormat("$number$");
			f7AdjustReason.setCommitFormat("$number$");
			ICellEditor f7AdjustReasonEditor = new KDTDefaultCellEditor(
					f7AdjustReason);
			table.getColumn("adjustReason").setEditor(f7AdjustReasonEditor);
			table.getColumn("objectType").getStyleAttributes().setLocked(true);
		}
		if (this.currentOrg == null || !this.currentOrg.isIsBizUnit()) {
			this.tblAdjustRecord.getStyleAttributes().setLocked(true);
			this.tblIntendingCost.getStyleAttributes().setLocked(true);
		}
	}

	/**
	 * 设置表格属性
	 * 
	 * @throws BOSException
	 */
	private void initMainTable() throws BOSException {
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getViewManager().setFreezeView(-1, 2);
		tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblMain.getColumn("aimCost").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.getColumn("hasHappen").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.getColumn("intendingHappen").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("dynamicCost").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("aimCost").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn("hasHappen").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn("intendingHappen").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("dynamicCost").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));

		tblMain.addHeadRow(1, (IRow) tblMain.getHeadRow(0).clone());
		for (int i = 0; i < tblMain.getColumnCount(); i++) {
			tblMain.getHeadMergeManager().mergeBlock(0, i, 1, i);
		}
	}

	private void setProductTypeColumn() {
		int columnCount = tblMain.getColumnCount();
		for (int i = 0; i < columnCount - 6; i++) {
			tblMain.removeColumn(6);
		}
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (!proNode.isLeaf()) {
			return;
		}
		if(productTypeGetter==null||productTypeGetter.getSortedProductMap().size()==0){
			return;
		}
		
		Map prodcutMap = this.productTypeGetter.getSortedProductMap();
		String[] resName = new String[] { "Aim", "HasHappen", "NoHappen",
				"Dynamic" };
		int i = 0;
		Set keySet = prodcutMap.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String number = (String) iter.next();
			ProductTypeInfo product = (ProductTypeInfo) prodcutMap.get(number);
			for (int j = 0; j < 4; j++) {
				String key = product.getId().toString() + j;
				IColumn col = tblMain.addColumn();
				col.setKey(key);
				col.getStyleAttributes().setNumberFormat(
						FDCHelper.getNumberFtm(2));
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);
				tblMain.getHeadRow(0).getCell(key).setValue(product.getName());
				tblMain.getHeadRow(1).getCell(key).setValue(
						AimCostHandler.getResource(resName[j]));
			}
			tblMain.getHeadMergeManager().mergeBlock(0, 6 + i * 4, 0,
					6 + 3 + i * 4);
			i++;
		}
		KDBizPromptBox f7Product = new KDBizPromptBox();
		f7Product
				.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
		EntityViewInfo productView = new EntityViewInfo();
		FilterInfo productFilter = new FilterInfo();
		productFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		productFilter.getFilterItems().add(
				new FilterItemInfo("id", FDCHelper
						.getSetByArray(this.productTypeGetter
								.getProductTypeIds()), CompareType.INCLUDE));
		productView.setFilter(productFilter);
		f7Product.setEntityViewInfo(productView);
		f7Product.setEditable(true);
		f7Product.setDisplayFormat("$name$");
		f7Product.setEditFormat("$number$");
		f7Product.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Product);
		this.tblAdjustRecord.getColumn("product").setEditor(f7Editor);
		this.tblIntendingCost.getColumn("product").setEditor(f7Editor);
	}

	private boolean isFirstLoad = true;

	private volatile boolean hasFinish = true;

	//R101103-251待发生成本预测表中“目前待发生明细”需要维护，同时目标成本调整使用调整单
    protected void panelDown_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    	IRow newRow = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		if (newRow == null) {
			return;
		}
		CostAccountInfo acct = (CostAccountInfo) newRow.getUserObject();
		if (acct == null && this.curAcct == null) {
			return;
		}
//		if (acct != null && this.curAcct != null) {
//			if (acct.getId().toString().equals(this.curAcct.getId().toString())) {
//				return;
//			}
//		}
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
    	setBtnStatus();
    }

	private void setBtnStatus() {
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		//非实体业务组织不能修改
		if (!proNode.isLeaf()) {
			this.actionAttachment.setEnabled(true);
			if (!this.currentOrg.isIsBizUnit()) {
				return;
			}
			
		}else{
			return;
		}

		if(canAddNew){
			this.actionSubmit.setEnabled(false);
			this.actionAddRow.setEnabled(false);
			this.actionDeleteRow.setEnabled(false);
			this.actionRecense.setEnabled(false);
			this.actionRevert.setEnabled(false);
			
			this.actionSubmit.setVisible(false);
			this.actionAddRow.setVisible(false);
			this.actionDeleteRow.setVisible(false);
			this.actionRecense.setVisible(false);
			this.actionRevert.setVisible(false);
		}else{//受前面参数控制
			if(panelDown.getSelectedComponent()==null){
				return;
			}
			String ctnName = this.panelDown.getSelectedComponent().getName();
			if("tblAdjustRecord".equals(ctnName)){
				if(!isAdjust){
					this.actionAddRow.setEnabled(false);
					this.actionDeleteRow.setEnabled(false);
					this.actionSubmit.setEnabled(false);
				}else{
					this.actionAddRow.setEnabled(true);
					this.actionDeleteRow.setEnabled(true);
					this.actionSubmit.setEnabled(true);
				}
				if(isCanDelete){
					actionDeleteRow.setEnabled(true);
					this.actionSubmit.setEnabled(true);
				}
			}else{
				this.actionAddRow.setEnabled(true);
				this.actionDeleteRow.setEnabled(true);
				this.actionSubmit.setEnabled(true);
				if(isCanDelete){
					actionDeleteRow.setEnabled(true);
					actionSubmit.setEnabled(true);
				}else{
//					actionDeleteRow.setEnabled(false);
				}
			}
		}
	}
  
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		// if(true) return;
		if (isFirstLoad && (treeMain == null || treeMain.getRowCount() > 1)) {
			isFirstLoad = false;
			return;
		}
		/*
		 * if(treeMain.getUserObject()!=null&&treeMain.getUserObject().equals("old")){
		 * treeMain.setUserObject(null); return; } if(!hasFinish){
		 * MsgBox.showInfo(this,"正在加载数据请稍后再点击"); treeMain.setUserObject("old");
		 * treeMain.setSelectionPath(e.getOldLeadSelectionPath()); return; }
		 * hasFinish=false;
		 */
		final String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		if (selectObjId.equals(this.objectId)) {
			return;
		}

		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if(!proNode.isLeaf()){
			tblMain.removeRows();
			this.productTypeGetter=new DyProductTypeGetter(selectObjId);
			setProductTypeColumn();
			this.objectId=selectObjId;
			return;
		}
		tblAdjustRecord.getSelectManager().select(0, 6);
		tblIntendingCost.getSelectManager().select(0, 6);
		if (isEditEntry()) {
			if (MsgBox.showConfirm2(this, AimCostHandler.getResource("NoSave")) == MsgBox.YES) {
				if (!verify()) {
					treeMain.setSelectionPath(e.getOldLeadSelectionPath());
					return;
				}
				CostAccountInfo oldAcct = (CostAccountInfo) tblMain.getRow(
						tblMain.getSelectManager().getActiveRowIndex())
						.getUserObject();
				if (oldAcct == null) {
					return;
				}
				try {
					submitByAcct(oldAcct);
				} catch (EASBizException e1) {
					handUIExceptionAndAbort(e1);
				} catch (BOSException e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		}
		fetchData(selectObjId);
		setProductTypeColumn();
		adjustDataMap.clear();
		try {
			fillMainTable();
		} catch (Exception e1) {
			hasFinish = true;
			handUIExceptionAndAbort(e1);
		}
		if (tblMain.getRowCount() != 0) {
			tblMain.getSelectManager().select(0, 0);
		}
		hasFinish = true;

		// int acctNameIndex=tblMain.getColumn("acctName").getColumnIndex()+1;
		// tblMain.getViewManager().freeze(0, acctNameIndex);
	}

	public void fillMainTable() throws Exception {
		tblMain.removeRows();
		addTotalRows();

		this.clearEntrys();
		this.objectId = this.getSelectObjId();

		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		FilterInfo acctFilter = new FilterInfo();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			acctFilter.getFilterItems().add(
					new FilterItemInfo("curProject.id", objectId));
		} else {
			acctFilter.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.id", objectId));
		}
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		AcctAccreditHelper.handAcctAccreditFilter(null, objectId, acctFilter);
		TreeModel costAcctTree = FDCClientHelper.createDataTree(
				CostAccountFactory.getRemoteInstance(), acctFilter);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree
				.getRoot();
		int maxLevel=root.getDepth();
		this.tblMain.getTreeColumn().setDepth(maxLevel);
		// 使用新的接口
		for (int i = 0; i < root.getChildCount(); i++) {
			fillNode((DefaultMutableTreeNode) root.getChildAt(i));
		}
		this.setUnionData();
		setTotalData();
		initUserConfig();
	}

	private void addTotalRows() {
		IRow row = tblMain.addRow();
		row.setTreeLevel(0);
		Color totalColor = FDCTableHelper.warnColor;// new Color(0xF0AAA9);
		row.getStyleAttributes().setBackground(totalColor);
		row.getCell("acctNumber").setValue(AimCostHandler.getResource("Total"));
		// AimCostHandler.getResource("ProjectTotal"));
		row = tblMain.addRow();
		row.setTreeLevel(0);
		row.getCell("acctNumber").setValue(
				AimCostHandler.getResource("SubTotal"));
		row.getStyleAttributes().setBackground(totalColor);
		row.getCell("acctName").setValue(
				AimCostHandler.getResource("NoSplitTotal"));
		row = tblMain.addRow();
		row.setTreeLevel(0);
		row.getStyleAttributes().setBackground(totalColor);
		row.getCell("acctName").setValue(
				AimCostHandler.getResource("SplitTotal"));
	}

	public void fillEntryTable() {
		DynamicCostInfo dynamicInfo = this.dyGetter.getDynamicInfo(this.curAcct
				.getId().toString());
		if (dynamicInfo == null) {
			dynamicInfo = new DynamicCostInfo();
			dynamicInfo.setAccount(curAcct);
			this.dyGetter
					.updateDynamic(curAcct.getId().toString(), dynamicInfo);
		}
		AdjustRecordEntryCollection adjustEntrys = dynamicInfo
				.getAdjustEntrys();
		Map adjustMap = new HashMap();
		for (int i = 0; i < adjustEntrys.size(); i++) {
			AdjustRecordEntryInfo adjustEntry = adjustEntrys.get(i);
			if (adjustMap.containsKey(adjustEntry.getAimCostEntryId())) {
				AdjustRecordEntryCollection coll = (AdjustRecordEntryCollection) adjustMap
						.get(adjustEntry.getAimCostEntryId());
				coll.add(adjustEntry);
			} else {
				AdjustRecordEntryCollection newColl = new AdjustRecordEntryCollection();
				newColl.add(adjustEntry);
				adjustMap.put(adjustEntry.getAimCostEntryId(), newColl);
			}
		}
		this.tblAdjustRecord.setUserObject(adjustEntrys);
		// 添加调整汇总
		IRow sumRow = this.tblAdjustRecord.addRow();
		sumRow.getStyleAttributes().setBackground(
				FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		sumRow.getStyleAttributes().setLocked(true);
		sumRow.getCell("objectType").setValue("调整汇总");
		BigDecimal adjustSum = FDCHelper.ZERO;
		CostEntryCollection aimColl = this.aimGetter.getCostEntrys(dynamicInfo
				.getAccount().getId().toString());
		for (int i = 0; i < aimColl.size(); i++) {
			CostEntryInfo aimEntryInfo = aimColl.get(i);
			addAimCostRow(aimEntryInfo);
			AdjustRecordEntryCollection entrys = (AdjustRecordEntryCollection) adjustMap
					.get(aimEntryInfo.getId().toString());
			if (entrys != null) {
				for (int j = 0; j < entrys.size(); j++) {
					AdjustRecordEntryInfo entry = entrys.get(j);
					AdjustObjectTypeEnum adjustObject = AdjustObjectTypeEnum.ADJUST;
					addAdjustRow(entry, adjustObject);
					if (entry.getCostAmount() != null) {
						adjustSum = adjustSum.add(entry.getCostAmount());
					}
				}
				adjustMap.remove(aimEntryInfo.getId().toString());
			}
		}
		Collection nullColl = (Collection) adjustMap.values();
		for (Iterator iter = nullColl.iterator(); iter.hasNext();) {
			AdjustRecordEntryCollection adColl = (AdjustRecordEntryCollection) iter
					.next();
			for (int i = 0; i < adColl.size(); i++) {
				AdjustRecordEntryInfo entry = adColl.get(i);
				AdjustObjectTypeEnum adjustObject = AdjustObjectTypeEnum.NOAIM;
				addAdjustRow(entry, adjustObject);
				if (entry.getCostAmount() != null) {
					adjustSum = adjustSum.add(entry.getCostAmount());
				}
			}
		}
		sumRow.getCell("costAmount").setValue(adjustSum);
		IntendingCostEntryCollection intendingEntrys = dynamicInfo
				.getIntendingCostEntrys();
		this.tblIntendingCost.setUserObject(intendingEntrys);
		for (int i = 0; i < intendingEntrys.size(); i++) {
			IntendingCostEntryInfo intendingEntry = intendingEntrys.get(i);
			addIntendingRow(intendingEntry);
		}
	}

	private void addIntendingRow(IntendingCostEntryInfo intendingEntry) {
		IRow row = this.tblIntendingCost.addRow();
		row.setUserObject(intendingEntry);
		row.getCell("acctName").setValue(intendingEntry.getCostAcctName());
		BigDecimal workload = intendingEntry.getWorkload();
		row.getCell("workload").setValue(workload);
		row.getCell("unit").setValue(intendingEntry.getUnit());
		BigDecimal price = intendingEntry.getPrice();
		row.getCell("price").setValue(price);
		row.getCell("product").setValue(intendingEntry.getProduct());
		row.getCell("costAmount").setValue(intendingEntry.getCostAmount());
		if (workload != null && price != null) {
			row.getCell("costAmount").getStyleAttributes().setLocked(true);
		}
		row.getCell("description").setValue(intendingEntry.getDescription());
	}

	private void addAimCostRow(CostEntryInfo aimEntryInfo) {
		IRow aimRow = this.tblAdjustRecord.addRow();
		aimRow.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		aimRow.getStyleAttributes().setLocked(true);
		aimRow.getCell("objectType").setValue(AdjustObjectTypeEnum.HASAIM);
		aimRow.getCell("acctName").setValue(aimEntryInfo.getEntryName());
		aimRow.getCell("acctName").setUserObject(
				aimEntryInfo.getId().toString());
		aimRow.getCell("workload").setValue(aimEntryInfo.getWorkload());
		aimRow.getCell("unit").setValue(aimEntryInfo.getUnit());
		aimRow.getCell("price").setValue(aimEntryInfo.getPrice());
		aimRow.getCell("product").setValue(aimEntryInfo.getProduct());
		aimRow.getCell("adjusterID").setValue(aimEntryInfo.getCreator());
		aimRow.getCell("adjusterName").setValue(aimEntryInfo.getName());
		aimRow.getCell("costAmount").setValue(aimEntryInfo.getCostAmount());
		aimRow.getCell("description").setValue(aimEntryInfo.getDescription());
	}

	private void addAdjustRow(AdjustRecordEntryInfo entry,
			AdjustObjectTypeEnum adjustObject) {
		IRow row = this.tblAdjustRecord.addRow();
		row.setUserObject(entry);
		if(entry.getId()!=null){
			row.getCell("id").setValue(entry.getId());
			row.getStyleAttributes().setLocked(true);
		}
		row.getCell("objectType").setValue(adjustObject);
		row.getCell("acctName").setValue(entry.getAdjustAcctName());
		row.getCell("adjustType").setValue(entry.getAdjustType());
		row.getCell("adjustReason").setValue(entry.getAdjustReason());
		BigDecimal workload = entry.getWorkload();
		row.getCell("workload").setValue(workload);
		row.getCell("unit").setValue(entry.getUnit());
		BigDecimal price = entry.getPrice();
		row.getCell("price").setValue(price);
		row.getCell("product").setValue(entry.getProduct());
		row.getCell("costAmount").setValue(entry.getCostAmount());
		if (workload != null && price != null) {
			row.getCell("costAmount").getStyleAttributes().setLocked(true);
		}
		row.getCell("adjustDate").setValue(entry.getAdjustDate());
	
		row.getCell("adjusterID").setValue(entry.getAdjuster());
		row.getCell("adjusterName").setValue(entry.getAdjusterName());
		
		row.getCell("description").setValue(entry.getDescription());
		row.getCell("fiVouchered").setValue(
				Boolean.valueOf(entry.isFiVouchered()));
		row.getCell("isBeforeSett").setValue(
				Boolean.valueOf(entry.isIsBeforeSett()));
	}

	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData() {
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		amountColumns.add("aimCost");
		amountColumns.add("hasHappen");
		amountColumns.add("intendingHappen");
		amountColumns.add("dynamicCost");
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (proNode.isLeaf()&&this.productTypeGetter!=null) {
			String[] productTypeIds = this.productTypeGetter
					.getProductTypeIds();
			for (int i = 0; productTypeIds!=null&&i < productTypeIds.length; i++) {
				String id = productTypeIds[i];
				amountColumns.add(id + 0);
				amountColumns.add(id + 1);
				amountColumns.add(id + 2);
				amountColumns.add(id + 3);
			}
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					BigDecimal amount = FMConstants.ZERO;
					boolean isRed = false;
					boolean hasData = false;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						if (rowAdd.getCell(colName).getStyleAttributes()
								.getFontColor().equals(Color.RED)) {
							isRed = true;
						}
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(
										((Integer) value).toString()));
							}
							hasData = true;
						}
					}
					if (isRed) {
						row.getCell(colName).getStyleAttributes().setFontColor(
								Color.RED);
					} else {
						row.getCell(colName).getStyleAttributes().setFontColor(
								Color.BLACK);
					}
					if (hasData) {
						row.getCell(colName).setValue(amount);
					} else {
						row.getCell(colName).setValue(null);
					}
				}
			}
		}
		// IRow projectRow = table.getRow(0);
		// IRow noSplitRow = table.getRow(1);
		// IRow splitRow = table.getRow(2);
		// for (int i = 0; i < amountColumns.size(); i++) {
		// String colName = (String) amountColumns.get(i);
		// BigDecimal amount = FMConstants.ZERO;
		// boolean hasData = false;
		// for (int rowIndex = 0; rowIndex < zeroLeverRowList.size();
		// rowIndex++) {
		// IRow rowAdd = (IRow) zeroLeverRowList.get(rowIndex);
		// Object value = rowAdd.getCell(colName).getValue();
		// if (value != null) {
		// if (value instanceof BigDecimal) {
		// amount = amount.add((BigDecimal) value);
		// } else if (value instanceof Integer) {
		// amount = amount.add(new BigDecimal(((Integer) value)
		// .toString()));
		// }
		// hasData = true;
		// }
		// }
		// if (hasData) {
		// splitRow.getCell(colName).setValue(amount);
		// }
		// }
		// for (int i = 0; i < amountColumns.size(); i++) {
		// String colName = (String) amountColumns.get(i);
		// BigDecimal projectValue = (BigDecimal)
		// projectRow.getCell(colName).getValue();
		// BigDecimal splitValue = (BigDecimal)
		// splitRow.getCell(colName).getValue();
		// if (projectValue == null&&splitValue == null) {
		// continue;
		// }
		// if (projectValue == null) {
		// projectValue=FDCHelper.ZERO;
		// }
		// if (splitValue == null) {
		// splitValue=FDCHelper.ZERO;
		// }
		// BigDecimal noSplitValue = projectValue.subtract(splitValue);
		// noSplitRow.getCell(colName).setValue(noSplitValue);
		// }

	}

	private void fillNode(DefaultMutableTreeNode node) throws BOSException,
			SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}
		IRow row = tblMain.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("acctNumber").setValue(
				costAcct.getLongNumber().replace('!', '.'));
		row.getCell("acctName").setValue(costAcct.getName());
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node.isLeaf()) {
			row.setUserObject(costAcct);
			updateAimData(row, costAcct.getId().toString());

			updateHasHappenData(row, costAcct.getId().toString());
			AdjustRecordEntryCollection adjusts = null;
			IntendingCostEntryCollection intendings = null;
			if (proNode.isLeaf()) {
				DynamicCostInfo dynamicCostInfo = this.dyGetter
						.getDynamicInfo(costAcct.getId().toString());
				if (dynamicCostInfo != null) {
					adjusts = dynamicCostInfo.getAdjustEntrys();
					intendings = dynamicCostInfo.getIntendingCostEntrys();
				}
			} else {
				adjusts = getSumAdjustCol(costAcct);
			}
			updateDynamicData(row, adjusts);
			updateIntendingData(row, intendings);
		}
		if (!node.isLeaf() || !proNode.isLeaf()) {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			this.fillNode((DefaultMutableTreeNode) node.getChildAt(i));
		}
	}

	private AdjustRecordEntryCollection getSumAdjustCol(CostAccountInfo costAcct)
			throws BOSException, SQLException {
		if(true){
			if(this.adjustMap==null||this.adjustMap.size()==0){
				return new AdjustRecordEntryCollection();
			}
			final AdjustRecordEntryCollection adjusts = (AdjustRecordEntryCollection)this.adjustMap.get(costAcct.getId().toString());
			return adjusts==null?new AdjustRecordEntryCollection():adjusts;
		}
		StringBuffer innerSql = new StringBuffer();
		innerSql.append("select fid from " + FDCHelper.getFullAcctSql()
				+ " where ");
		String fullNumber = "";
		CostAccountInfo acct = (CostAccountInfo) this.acctMap.get(costAcct
				.getId().toString());
		if (acct.getCurProject() != null) {
			fullNumber = acct.getCurProject().getFullOrgUnit().getLongNumber()
					+ "!" + acct.getCurProject().getLongNumber();
		} else {
			fullNumber = acct.getFullOrgUnit().getLongNumber();
		}
		
		String longNumber = costAcct.getLongNumber();
		innerSql.append(" (FLongNumber ='").append(longNumber).append("'").append(" or FLongNumber like '").append(longNumber).append("!%' ").append(" or FLongNumber like		 '%!").append(longNumber)
				.append("!%') ");
		innerSql.append("and (FullNumber =				 '").append(fullNumber).append("'").append(" or FullNumber like '").append(fullNumber).append("!%' ").append(" or FullNumber like '%!")
				.append(fullNumber).append("!%') And costAcct.FIsLeaf=1 And costAcct.isleafProject=1 ");

		AdjustRecordEntryCollection adjusts = new AdjustRecordEntryCollection();
		String sql = "select FCostAmount,FProductId, FAdjusterID from T_AIM_AdjustRecordEntry inner join T_AIM_DynamicCost "
				+ "on  T_AIM_AdjustRecordEntry.FParentID= T_AIM_DynamicCost.fid where T_AIM_DynamicCost.FAccountId in ("
				+ innerSql.toString() + ")";
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
		while (rs.next()) {
			AdjustRecordEntryInfo info = new AdjustRecordEntryInfo();
			info.setCostAmount(rs.getBigDecimal("FCostAmount"));
			if (rs.getString("FProductId") != null) {
				ProductTypeInfo product = new ProductTypeInfo();
				product.setId(BOSUuid.read(rs.getString("FProductId")));
				info.setProduct(product);
			}
			adjusts.add(info);
		}
		return adjusts;
	}

	private void updateIntendingData(IRow row,
			IntendingCostEntryCollection intendingCostEntrys) {
		BigDecimal intendingSum = FDCHelper.ZERO;
		Map productDynamic = new HashMap();
		if (intendingCostEntrys != null) {
			for (int i = 0; i < intendingCostEntrys.size(); i++) {
				IntendingCostEntryInfo info = intendingCostEntrys.get(i);
				if (info.getCostAmount() != null) {
					if (info.getProduct() != null) {
						String productId = info.getProduct().getId().toString();
						if (productDynamic.containsKey(productId)) {
							BigDecimal value = (BigDecimal) productDynamic
									.get(productId);
							productDynamic.put(productId, value.add(info
									.getCostAmount()));
						} else {
							productDynamic.put(productId, info.getCostAmount());
						}
					}
					intendingSum = intendingSum.add(info.getCostAmount());
				}
			}
		}

		BigDecimal dynamicCost = (BigDecimal) row.getCell("dynamicCost")
				.getValue();
		BigDecimal hasHappen = (BigDecimal) row.getCell("hasHappen").getValue();
		BigDecimal intendingHappen = dynamicCost.subtract(hasHappen);
		// if (intendingSum.compareTo(intendingHappen) == 0) {
		// row.getCell("intendingHappen").getStyleAttributes().setFontColor(
		// Color.BLACK);
		// } else {
		// row.getCell("intendingHappen").getStyleAttributes().setFontColor(
		// Color.RED);
		// }
		if (FDCHelper.toBigDecimal(intendingHappen,2).compareTo(FDCHelper.ZERO) < 0) {
			row.getCell("intendingHappen").getStyleAttributes().setFontColor(
					Color.RED);
		} else {
			row.getCell("intendingHappen").getStyleAttributes().setFontColor(
					Color.BLACK);
		}
		row.getCell("intendingHappen").setValue(intendingHappen);
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (!proNode.isLeaf()) {
			return;
		}
		String[] productTypeIds = this.productTypeGetter.getProductTypeIds();
		for (int i = 0; i < productTypeIds.length; i++) {
			String productId = productTypeIds[i];

			dynamicCost = (BigDecimal) row.getCell(productId + 3).getValue();
			hasHappen = (BigDecimal) row.getCell(productId + 1).getValue();

			intendingHappen = FDCHelper.ZERO;
			if (dynamicCost != null) {
				intendingHappen = intendingHappen.add(dynamicCost);
			}
			if (hasHappen != null) {
				intendingHappen = intendingHappen.subtract(hasHappen);
			}
			intendingSum = (BigDecimal) productDynamic.get(productId);
			if (dynamicCost != null || hasHappen != null
					|| intendingSum != null) {
				if (intendingSum == null) {
					intendingSum = FDCHelper.ZERO;
				}
				// if (intendingSum.compareTo(intendingHappen) == 0) {
				// row.getCell(productId + 2).getStyleAttributes()
				// .setFontColor(Color.BLACK);
				// } else {
				// row.getCell(productId + 2).getStyleAttributes()
				// .setFontColor(Color.RED);
				// }

				if (FDCHelper.toBigDecimal(intendingHappen,2).compareTo(FDCHelper.ZERO) < 0) {
					row.getCell(productId + 2).getStyleAttributes()
							.setFontColor(Color.RED);
				} else {
					row.getCell(productId + 2).getStyleAttributes()
							.setFontColor(Color.BLACK);
				}
				row.getCell(productId + 2).setValue(intendingHappen);
			} else {
				row.getCell(productId + 2).setValue(null);
			}
		}
	}

	private void updateHasHappenData(IRow row, String acctId) throws BOSException {
		HappenDataInfo happenInfo = this.happenGetter.getHappenInfo(acctId);
		BigDecimal hasHappen = FDCHelper.ZERO;
		if (happenInfo != null) {
			hasHappen = happenInfo.getAmount();
		}
		row.getCell("hasHappen").setValue(hasHappen);
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (!proNode.isLeaf()) {
			return;
		}
		CostAccountInfo acct = (CostAccountInfo) row.getUserObject();
		Map happenCalculateData = this.dyGetter.getHasHappenProductMap(acct
				.getId().toString());
		Set keySet = happenCalculateData.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String prodId = (String) iter.next();
			BigDecimal finalAmount = (BigDecimal) happenCalculateData
					.get(prodId);
			if (row.getCell(prodId + 1) != null) {
				row.getCell(prodId + 1).setValue(finalAmount);
			}
		}
	}

	private void updateAimData(IRow row, String acctId) throws BOSException,
			SQLException {
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		BigDecimal aimCost = FDCHelper.ZERO;
		//findbugs 不知道怎么改，暂时不改
		if (proNode.isLeaf()) {
			aimCost = this.aimGetter.getAimCost(acctId);
		} else {
			aimCost = this.aimGetter.getAimCost(acctId);
		}
		row.getCell("aimCost").setValue(aimCost);
		if (!proNode.isLeaf()) {
			return;
		}
		Map productAim = this.aimGetter.getProductMap(acctId);
		Set keySet = productAim.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			BigDecimal value = (BigDecimal) productAim.get(productId);
			if (row.getCell(productId + 0) != null) {
				row.getCell(productId + 0).setValue(value);
			}
		}
	}

	private void updateDynamicData(IRow row, AdjustRecordEntryCollection entrys) {
		if (entrys == null) {
			entrys = new AdjustRecordEntryCollection();
		}
		CostAccountInfo acct = (CostAccountInfo) row.getUserObject();
		if (acct == null) {
			return;
		}
		BigDecimal aimCost = this.aimGetter.getAimCost(acct.getId().toString());
		BigDecimal dyCost = aimCost.add(FDCHelper.ZERO);
		for (int i = 0; i < entrys.size(); i++) {
			AdjustRecordEntryInfo info = entrys.get(i);
			BigDecimal costAmount = info.getCostAmount();
			if (costAmount != null) {
				dyCost = dyCost.add(costAmount);
			}
		}
		row.getCell("dynamicCost").setValue(dyCost);
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (!proNode.isLeaf()) {
			return;
		}
		try {
//			Map productDynamic = this.dyGetter.getDyProductMap(acct.getId()
//					.toString(), entrys);
			//获取最新的产品动态成本信息，包括新录入还未保存的记录
			Map productDynamic = this.dyGetter.getDyProductMap(acct.getId()
					.toString(), entrys, true);
			Set keySet = productDynamic.keySet();
			for (Iterator iter = keySet.iterator(); iter.hasNext();) {
				String productId = (String) iter.next();
				BigDecimal value = (BigDecimal)productDynamic.get(productId);
				if(adjustDataMap!=null){
					BigDecimal adjustAmt = (BigDecimal)adjustDataMap.get(acct.getId().toString()+productId);
					value = FDCHelper.subtract(value,adjustAmt);
				}
				row.getCell(productId + 3).setValue(value);
			}
		} catch (BOSException e) {
			handUIException(e);
		}
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

	public void onLoad() throws Exception {
		super.onLoad();
		fetchInitParam();
		initControl();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int acctNameIndex = tblMain.getColumn("acctName")
						.getColumnIndex() + 1;
				tblMain.getViewManager().freeze(0, acctNameIndex);
				tblMain.setColumnMoveable(true);
			}
		});
		setBtnStatus();
	}

	private void fetchInitParam() throws EASBizException, BOSException{
		Map paramItem = FDCUtils.getDefaultFDCParam(null, null);
		
		// 待发生成本预测数据通过待发生成本预测单进行处理(二次开发)，待发生成本预测功能只允许查询
		if (paramItem.get(FDCConstants.FDC_PARAM_COSTMEASURE) != null) {
			canAddNew = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_COSTMEASURE)
							.toString()).booleanValue();
		}
		if (paramItem.get(FDCConstants.FDC_PARAM_AIMCOSTADJUSTINPUT) != null) {
			isAdjust = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_AIMCOSTADJUSTINPUT)
							.toString()).booleanValue();
		}	
		if (paramItem.get(FDCConstants.FDC_PARAM_AIMCOSTADJUSTDELETE) != null) {
			isCanDelete = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_AIMCOSTADJUSTDELETE)
							.toString()).booleanValue();
		}	
	}
	private void initControl() {
		this.actionAddNew.setVisible(false);
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionLocate.setVisible(false);
		this.actionLocate.setEnabled(false);
		this.actionRecense.setVisible(false);
		this.actionRecense.setEnabled(false);
		this.actionAttachment.setVisible(true);
		panelDown.removeAll();
		panelDown.add(tblAdjustRecord, resHelper
				.getString("tblAdjustRecord.constraints"));
		panelDown.add(tblIntendingCost, resHelper
				.getString("tblIntendingCost.constraints"));
		
		tblAdjustRecord.getColumn("adjusterName").getStyleAttributes().setLocked(true);
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	}

	public void actionAddRow_actionPerformed(ActionEvent e) throws Exception {
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			return;
		}
		KDTable table = (KDTable) this.panelDown.getSelectedComponent();
		if (table.getName().equals("tblAdjustRecord")) {
			int selectIndex = table.getSelectManager().getActiveRowIndex();
			if (selectIndex == -1) {
				selectIndex = table.getRowCount() - 1;
			}
			if (selectIndex != 0) {
				IRow selectRow = table.getRow(selectIndex);
				IRow row = table.addRow(selectIndex + 1);
				AdjustRecordEntryInfo info = new AdjustRecordEntryInfo();
				String aimEntryId = null;
				if (selectRow.getUserObject() == null) {
					aimEntryId = (String) selectRow.getCell("acctName")
							.getUserObject();
					row.getCell("objectType").setValue(
							AdjustObjectTypeEnum.ADJUST);
				} else {
					aimEntryId = ((AdjustRecordEntryInfo) selectRow
							.getUserObject()).getAimCostEntryId();
					row.getCell("objectType").setValue(
							selectRow.getCell("objectType").getValue());
				}
				info.setAimCostEntryId(aimEntryId);
				row.getCell("acctName").setValue(
						selectRow.getCell("acctName").getValue());
				row.getCell("product").setValue(
						selectRow.getCell("product").getValue());
				row.getCell("adjustDate").setValue(new Date());
				
				//添加调整人
				row.getCell("adjusterID").setValue(SysContext.getSysContext().getCurrentUserInfo());	
				// TODO 冗余字段
				row.getCell("adjusterName").setValue(SysContext.getSysContext().getCurrentUserInfo().getName());	
				
				row.getCell("fiVouchered").setValue(Boolean.FALSE);
				row.getCell("isBeforeSett").setValue(Boolean.TRUE);
				// if (((KDComboBox) tbl.getCell("adjustType").getEditor())
				// .getItemCount() > 0) {
				// row.getCell("adjustType")
				// .setValue(
				// ((KDComboBox) row.getCell("adjustType")
				// .getEditor()).getItemAt(0));
				// }
				info.setId(BOSUuid.create(info.getBOSType()));
				row.setUserObject(info);
			} else {
				IRow row = table.addRow();
				row.getCell("objectType").setValue(AdjustObjectTypeEnum.NOAIM);
				row.getCell("adjustDate").setValue(new Date());
				
				//添加调整人
				row.getCell("adjusterID").setValue(SysContext.getSysContext().getCurrentUserInfo());
				row.getCell("adjusterName").setValue(SysContext.getSysContext().getCurrentUserInfo().getName());
				
				row.getCell("fiVouchered").setValue(Boolean.FALSE);
				row.getCell("isBeforeSett").setValue(Boolean.TRUE);
				AdjustRecordEntryInfo info = new AdjustRecordEntryInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				row.setUserObject(info);
			}
		} else {
			IRow row = table.addRow();
			IntendingCostEntryInfo info = new IntendingCostEntryInfo();
			info.setId(BOSUuid.create(info.getBOSType()));
			row.setUserObject(info);
		}
	}

	public void actionDeleteRow_actionPerformed(ActionEvent e) throws Exception {
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		KDTable table = (KDTable) this.panelDown.getSelectedComponent();
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		if (table.getName().equals("tblAdjustRecord")) {
			IRow row = table.getRow(rowIndex);
			if (row == null) {
				return;
			}
			if (row.getUserObject() == null) {
				return;
			}else if(row.getCell("id").getValue()!=null){
				if(!isCanDelete){
					return;
				}
				String msg = "删除调整记录将改变科目及产品上的成本数据，是否删除？";
				int ok =MsgBox.showConfirm2(this,msg);
				if (ok != MsgBox.OK) {
					SysUtil.abort();
				}
				IRow mainRow = this.tblMain.getRow(this.tblMain.getSelectManager()
						.getActiveRowIndex());
				CostAccountInfo acct = (CostAccountInfo)mainRow.getUserObject();
				ProductTypeInfo product = (ProductTypeInfo)row.getCell("product").getValue();
					
				String productID = null;
				if(product!=null){
					productID = product.getId().toString();
				}else{
					AdjustRecordEntryCollection adjusts = new AdjustRecordEntryCollection();
					AdjustRecordEntryInfo adjust = new AdjustRecordEntryInfo();
					adjust.setCostAmount((BigDecimal)row.getCell("costAmount").getValue());
//					adjusts.add(adjust);
					Map productDynamic = this.dyGetter.getDyProductMap(acct.getId().toString(), adjusts);
					if(productDynamic.size()==1){
						Set keySet = productDynamic.keySet();
						for(Iterator iter=keySet.iterator();iter.hasNext();){
							productID = (String)iter.next();
						}
					}
				}
				String key = acct.getId().toString() + productID; 
				BigDecimal adjustAmt = (BigDecimal)row.getCell("costAmount").getValue();
				if(adjustDataMap.containsKey(key)){
					adjustDataMap.put(key, FDCHelper.add(adjustDataMap.get(key),adjustAmt));
				}else{
					adjustDataMap.put(key, adjustAmt);
				}
			}
		}
		table.removeRow(rowIndex);
		this.detailChange();
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		if (!this.verify()) {
			return;
		}
		IRow selectRow = this.tblMain.getRow(index);
		CostAccountInfo acct = (CostAccountInfo) selectRow.getUserObject();
		if(acct==null){
			return;
		}
		submitByAcct(acct);
		selectRow.getCell(0).setUserObject("hasSaved");
		this.setMessageText(EASResource
				.getString(FrameWorkClientUtils.strResource + "Msg_Save_OK"));
		this.showMessage();
		//应万科要求暂时取消，先留待看下一版本是否需要
//		submitAcctVoucher(acct);
/*		保存后不加载  by sxhong 2008-04-15 15:28:08	
		this.fillMainTable();
		this.tblMain.getSelectManager().select(index, index);
		this.tblMain.scrollToVisible(index,0);
		clearEntrys();*/
		IRow row = this.tblMain.getRow(index);
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (proNode.isLeaf() && row.getUserObject() != null) {
			this.actionAttachment.setEnabled(true);
			if (this.currentOrg.isIsBizUnit()) {
				this.actionAddRow.setEnabled(true);
				this.actionDeleteRow.setEnabled(true);
				this.actionSubmit.setEnabled(true);
				setBtnStatus();
			}
			this.curAcct = (CostAccountInfo) row.getUserObject();
			//不必要的重载　by sxhong　2008-03-66 10:03:22
//			this.fillEntryTable();
		}
		adjustDataMap.clear();
	}

	private void submitAcctVoucher(CostAccountInfo acct) throws BOSException,
			EASBizException, Exception {
		DynamicCostInfo info = this.dyGetter.getDynamicInfo(acct.getId()
				.toString());
		AdjustRecordEntryCollection adjustCol = info.getAdjustEntrys();
		
		CompanyOrgUnitInfo currentCompany = ContextHelperFactory.getRemoteInstance().getCurrentCompany();
		IBeforeAccountView iBefore = BeforeAccountViewFactory.getRemoteInstance();
		
		//动态成本的调整信息分录
		for (int i = 0; i < adjustCol.size(); i++) {
			AdjustRecordEntryInfo adjustInfo = adjustCol.get(i);
			if (adjustInfo.getProduct() != null
					&& adjustInfo.getProduct().getId() != null
					&& (!(adjustInfo.isFiVouchered()))) {
				String prodId = adjustInfo.getProduct().getId().toString();
				String projId = info.getAccount().getCurProject().getId()
						.toString();

				FilterInfo filterExist = new FilterInfo();
				filterExist.getFilterItems().add(
						new FilterItemInfo("curProject.id", projId));
				filterExist.getFilterItems().add(
						new FilterItemInfo("isCompSettle", Boolean.TRUE));
				filterExist.getFilterItems().add(
						new FilterItemInfo("productType.id", prodId));
				
				//当前工程项目产品设置分录
				if (CurProjProductEntriesFactory.getRemoteInstance().exists(
						filterExist)) {
					VoucherForProjectInfo vouPro = new VoucherForProjectInfo();
					
					vouPro.setCurProject(info.getAccount().getCurProject());
					vouPro.setProduct(adjustInfo.getProduct());
					vouPro.setProjectEntry(null);
					EntityViewInfo view = new EntityViewInfo();

					FilterInfo filterSplit = new FilterInfo();
					filterSplit.getFilterItems().add(
							new FilterItemInfo("company.id", currentCompany.getId().toString()));
					view.setFilter(filterSplit);
					BeforeAccountViewInfo account = iBefore
							.getBeforeAccountViewCollection(view).get(0);
					vouPro.setBeAccount(account);
					vouPro.setOrgUnit(info.getAccount().getFullOrgUnit());
					vouPro.setFiVouchered(false);
					vouPro.setAmount(adjustInfo.getCostAmount());
					//工程项目凭证
					IObjectPK pk = VoucherForProjectFactory.getRemoteInstance()
							.addnew(vouPro);
					
					//生成调整凭证
					if (vouPro.getAmount().compareTo(FDCHelper.ZERO) == 1)
						PaymentSplitVoucherHelper.voucherForProject(pk);

					//目标成本调整凭证
					VoucherForDynamicInfo dynPro = new VoucherForDynamicInfo();
					dynPro.setCurProject(info.getAccount().getCurProject());
					dynPro.setProduct(adjustInfo.getProduct());
					dynPro.setBeAccount(account);
					dynPro.setOrgUnit(info.getAccount().getFullOrgUnit());
					dynPro.setFiVouchered(false);
					dynPro.setAmount(adjustInfo.getCostAmount());
					IObjectPK pkDyn = VoucherForDynamicFactory
							.getRemoteInstance().addnew(dynPro);
					if (dynPro.getAmount().compareTo(FDCHelper.ZERO) == 1)
						PaymentSplitVoucherHelper.voucherForDynamic(pkDyn);

					VoucherForProjectInfo vouProj = VoucherForProjectFactory
							.getRemoteInstance().getVoucherForProjectInfo(pk);
					if (vouProj.isFiVouchered()) {
						adjustInfo.setFiVouchered(true);
						adjustInfo.setIsBeforeSett(false);
					}
				}
			} else if (!adjustInfo.isFiVouchered()){
				//是否竣工前调整
				adjustInfo.setIsBeforeSett(true);
			}
			
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("fiVouchered");
			selector.add("isBeforeSett");
			if (adjustInfo.getId() != null){
				AdjustRecordEntryFactory.getRemoteInstance().updatePartial(
						adjustInfo, selector);
			}
		}
	}

	private void submitByAcct(CostAccountInfo acct) throws BOSException,
			EASBizException {
		DynamicCostInfo info = this.dyGetter.getDynamicInfo(acct.getId()
				.toString());
		if(info==null){
			info=new DynamicCostInfo();
			info.setId(BOSUuid.create(info.getBOSType()));
			info.setAccount(acct);
			info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
			this.dyGetter.updateDynamic(acct.getId().toString(), info);
		}
		if(info.getId()==null){
			info.setId(BOSUuid.create(info.getBOSType()));
		}
		info.getAdjustEntrys().clear();
		AdjustRecordEntryCollection adjustCol = getAdjustRecordCollection();
		info.getAdjustEntrys().addCollection(adjustCol);
		BigDecimal adjustAmount = FDCHelper.ZERO;
		for (int i = 0; i < adjustCol.size(); i++) {
			AdjustRecordEntryInfo adjustInfo = adjustCol.get(i);
			adjustAmount = adjustAmount.add(adjustInfo.getCostAmount());
			if (adjustInfo.getId() == null) {
				adjustInfo.setId(BOSUuid.create(adjustInfo.getBOSType()));
			}
			Integer rowNum=(Integer)adjustInfo.get("rows");
			if(rowNum!=null){
				tblAdjustRecord.getRow(rowNum.intValue()).setUserObject(adjustInfo);
			}
		}
		info.setAdjustSumAmount(adjustAmount);

		info.getIntendingCostEntrys().clear();
		IntendingCostEntryCollection intendingCol = getIntendingCollection();
		info.getIntendingCostEntrys().addCollection(intendingCol);
		BigDecimal intendingAmount = FDCHelper.ZERO;
		for (int i = 0; i < intendingCol.size(); i++) {
			IntendingCostEntryInfo intendingInfo = intendingCol.get(i);
			intendingAmount = intendingAmount
					.add(intendingInfo.getCostAmount());
			if (intendingInfo.getId() == null) {
				intendingInfo.setId(BOSUuid.create(intendingInfo.getBOSType()));
			}
			Integer rowNum=(Integer)intendingInfo.get("rows");
			if(rowNum!=null){
				tblIntendingCost.getRow(rowNum.intValue()).setUserObject(intendingInfo);
			}
		}
		info.setIntendingCostSumAmount(intendingAmount);
		DynamicCostFactory.getRemoteInstance().submit(info);

		// tblAdjustRecord.setUserObject(null);
		// tblIntendingCost.setUserObject(null);
	}

	private IntendingCostEntryCollection getIntendingCollection() {
		IntendingCostEntryCollection intendingCol = new IntendingCostEntryCollection();
		for (int i = 0; i < tblIntendingCost.getRowCount(); i++) {
			IRow row = tblIntendingCost.getRow(i);
			IntendingCostEntryInfo intendingInfo = (IntendingCostEntryInfo) ((IntendingCostEntryInfo) row
					.getUserObject()).clone();

			intendingInfo.setCostAcctName((String) row.getCell("acctName")
					.getValue());
			intendingInfo.setWorkload((BigDecimal) row.getCell("workload")
					.getValue());
			intendingInfo.setUnit((String) row.getCell("unit").getValue());
			intendingInfo
					.setPrice((BigDecimal) row.getCell("price").getValue());
			intendingInfo.setProduct((ProductTypeInfo) row.getCell("product")
					.getValue());
			intendingInfo.setDescription((String) row.getCell("description")
					.getValue());
			BigDecimal costAmount = FDCHelper.ZERO;
			if (row.getCell("costAmount").getValue() != null) {
				costAmount = (BigDecimal) row.getCell("costAmount").getValue();
			}
			intendingInfo.setCostAmount(costAmount);
			intendingInfo.put("rows",new Integer(i));
			intendingCol.add(intendingInfo);

		}
		return intendingCol;
	}

	private AdjustRecordEntryCollection getAdjustRecordCollection() {
		AdjustRecordEntryCollection adjustCol = new AdjustRecordEntryCollection();
		for (int i = 0; i < tblAdjustRecord.getRowCount(); i++) {
			IRow row = tblAdjustRecord.getRow(i);
			AdjustRecordEntryInfo adjust = (AdjustRecordEntryInfo) row
					.getUserObject();
			if (adjust != null) {
				AdjustRecordEntryInfo adjustInfo = (AdjustRecordEntryInfo) adjust
						.clone();
				adjustInfo.setAdjustAcctName((String) row.getCell("acctName")
						.getValue());
				adjustInfo.setAdjustType((AdjustTypeInfo) row.getCell(
						"adjustType").getValue());
				adjustInfo.setAdjustReason((AdjustReasonInfo) row.getCell(
						"adjustReason").getValue());
				adjustInfo.setWorkload((BigDecimal) row.getCell("workload")
						.getValue());
				adjustInfo.setUnit((String) row.getCell("unit").getValue());
				adjustInfo.setPrice((BigDecimal) row.getCell("price")
						.getValue());
				adjustInfo.setProduct((ProductTypeInfo) row.getCell("product")
						.getValue());
				Date value = (Date) row.getCell("adjustDate").getValue();
				value=DateTimeUtils.truncateDate(value);

				UserInfo adjuster = (UserInfo)row.getCell("adjusterID").getValue();
				if (adjuster == null) {//k3旧数据无此字段，导入出错,增加默认  by hpw 2010.11.9
					adjuster = SysContext.getSysContext().getCurrentUserInfo();
				}
				//添加调整人
				adjustInfo.setAdjuster(adjuster);
				adjustInfo.setAdjusterName(adjuster.getName());
				
				row.getCell("adjustDate").setValue(value);
				adjustInfo.setAdjustDate(value);
				adjustInfo.setDescription((String) row.getCell("description")
						.getValue());
				adjustInfo.setFiVouchered(((Boolean) row.getCell("fiVouchered")
						.getValue()).booleanValue());
				adjustInfo.setIsBeforeSett(((Boolean) row.getCell(
						"isBeforeSett").getValue()).booleanValue());
				adjustCol.add(adjustInfo);
				BigDecimal costAmount = FDCHelper.ZERO;
				if (row.getCell("costAmount").getValue() != null) {
					costAmount = (BigDecimal) row.getCell("costAmount")
							.getValue();
				}
				adjustInfo.setCostAmount(costAmount.setScale(2,
						BigDecimal.ROUND_HALF_UP));
				adjustInfo.put("rows",new Integer(i));
			}
		}
		return adjustCol;
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.btnRecense.setIcon(EASResource.getIcon("imgTbtn_review"));
		this.btnAddRow.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.btnDeleteRow.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		this.btnExpression.setIcon(EASResource
				.getIcon("imgTbtn_assistantaccountdetail"));
		this.btnRevert.setIcon(EASResource.getIcon("imgTbtn_restore"));
		setButtonDefaultStyl(this.btnSubmit);
		this.menuItemSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.menuItemAddRow.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.menuItemDeleteRow.setIcon(EASResource
				.getIcon("imgTbtn_deleteline"));
		this.menuItemRevert.setIcon(EASResource.getIcon("imgTbtn_restore"));
		this.menuItemExpression.setIcon(EASResource
				.getIcon("imgTbtn_assistantaccountdetail"));
	}

	/**
	 * 获取query中的主键列名称，返回供编辑/删除时获取主键用，默认值为"id"，继承类可以重载
	 * 
	 */
	protected String getKeyFieldName() {
		return "acctNumber";
	}

	private void refresh() throws Exception {
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		String prjId=getSelectObjId();
		if(prjId==null){
			return;
		}
		adjustDataMap.clear();
		// 目前只支持(明细及非明细)工程项目取数 by hpw 2009-11-14
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (!(proNode.getUserObject() instanceof CurProjectInfo)) {
			return;
		}
		fetchData(prjId);
		this.fillMainTable();
		if (index == -1) {
			return;
		}
		this.tblMain.getSelectManager().select(index, 0);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		if (e.getSelectBlock().equals(e.getPrevSelectBlock())) {
			return;
		}
		IRow newRow = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		if (newRow == null) {
			return;
		}
		CostAccountInfo acct = (CostAccountInfo) newRow.getUserObject();
		if (acct == null && this.curAcct == null) {
			return;
		}
		if (acct != null && this.curAcct != null) {
			if (acct.getId().toString().equals(this.curAcct.getId().toString())) {
				return;
			}
		}
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
/*		if(e.getPrevSelectBlock()!=null){
			int pre=e.getPrevSelectBlock().getBeginRow();
			if(pre>0){
				IRow preRow=tblMain.getRow(pre);
				if(preRow!=null&&preRow.getCell(0).getUserObject()!=null&&preRow.getCell(0).getUserObject().equals("hasSaved")){
					return;
				}
			}
		}*/
		if (e.getPrevSelectBlock() != null && this.isEditEntry()) {
			if (MsgBox.showConfirm2(this, AimCostHandler.getResource("NoSave")) == MsgBox.YES) {
				if (!this.verify()) {
					EventListener[] listeners = this.tblMain
							.getListeners(KDTSelectListener.class);
					for (int i = 0; i < listeners.length; i++) {
						this.tblMain.getListenerList().remove(
								KDTSelectListener.class, (KDTSelectListener)listeners[i]);
					}
					this.tblMain.getSelectManager().select(
							e.getPrevSelectBlock());
					for (int i = 0; i < listeners.length; i++) {
						this.tblMain.getListenerList().add(
								KDTSelectListener.class, (KDTSelectListener)listeners[i]);
					}
					return;
				}
				CostAccountInfo oldAcct = (CostAccountInfo) this.tblMain
						.getRow(e.getPrevSelectBlock().getTop())
						.getUserObject();
				if (oldAcct == null) {
					return;
				}
				this.submitByAcct(oldAcct);
			} else {
				this.fillMainTable();
				this.tblMain.getSelectManager().select(index, index);
			}
		}
		clearEntrys();
		IRow row = this.tblMain.getRow(index);
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (proNode.isLeaf() && row.getUserObject() != null) {
			this.actionAttachment.setEnabled(true);
			if (this.currentOrg.isIsBizUnit()) {
				this.actionAddRow.setEnabled(true);
				this.actionDeleteRow.setEnabled(true);
				this.actionSubmit.setEnabled(true);
				setBtnStatus();
			}
			this.curAcct = (CostAccountInfo) row.getUserObject();
			this.fillEntryTable();
		}
	}

	/**
	 * 屏蔽回车键
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {

	}

	private void clearEntrys() {
		this.actionAttachment.setEnabled(false);
		this.actionSubmit.setEnabled(false);
		this.actionAddRow.setEnabled(false);
		this.actionDeleteRow.setEnabled(false);
		this.tblAdjustRecord.removeRows();
		this.tblIntendingCost.removeRows();
		this.tblAdjustRecord.setUserObject(null);
		this.tblIntendingCost.setUserObject(null);
		this.curAcct = null;
	}

	public boolean verify() {
		for (int i = 1; i < this.tblAdjustRecord.getRowCount(); i++) {
			IRow row = this.tblAdjustRecord.getRow(i);
			// if (row.getCell("acctName").getValue() == null) {
			// this.panelDown.setSelectedComponent(tblAdjustRecord);
			// this.setMessageText(AimCostHandler.getResource("AcctNameNull"));
			// this.showMessage();
			// tblAdjustRecord.getSelectManager().select(0, 6);
			// tblAdjustRecord.getSelectManager().select(row.getRowIndex(), 1);
			// return false;
			// }
			if (row.getCell("costAmount").getValue() == null) {
				this.panelDown.setSelectedComponent(tblAdjustRecord);
				this.setMessageText(AimCostHandler.getResource("AmountNull"));
				this.showMessage();
				tblAdjustRecord.getSelectManager().select(0, 6);
				tblAdjustRecord.getSelectManager().select(row.getRowIndex(), 7);
				return false;
			} else {
				BigDecimal value = (BigDecimal) row.getCell("costAmount")
						.getValue();
				if (value.compareTo(FDCHelper.ZERO) == 0) {
					this.panelDown.setSelectedComponent(tblAdjustRecord);
					this.setMessageText(AimCostHandler
							.getResource("AmountNull"));
					this.showMessage();
					tblAdjustRecord.getSelectManager().select(0, 6);
					tblAdjustRecord.getSelectManager().select(
							row.getRowIndex(), 7);
					return false;
				}
				if (value.compareTo(FDCHelper.MAX_VALUE) > 0) {
					this.panelDown.setSelectedComponent(tblAdjustRecord);
					this.setMessageText(AimCostHandler.getResource("MaxValue"));
					this.showMessage();
					tblAdjustRecord.getSelectManager().select(0, 6);
					tblAdjustRecord.getSelectManager().select(
							row.getRowIndex(), 7);
					return false;
				}
			}
		}
		for (int i = 0; i < this.tblIntendingCost.getRowCount(); i++) {
			IRow row = this.tblIntendingCost.getRow(i);
			// if (row.getCell("acctName").getValue() == null) {
			// this.panelDown.setSelectedComponent(tblIntendingCost);
			// this.setMessageText(AimCostHandler.getResource("AcctNameNull"));
			// this.showMessage();
			// tblIntendingCost.getSelectManager().select(0, 6);
			// tblIntendingCost.getSelectManager()
			// .select(row.getRowIndex(), 0);
			// return false;
			// }
			if (row.getCell("costAmount").getValue() == null) {
				this.panelDown.setSelectedComponent(tblIntendingCost);
				this.setMessageText(AimCostHandler.getResource("AmountNull"));
				this.showMessage();
				tblIntendingCost.getSelectManager().select(0, 6);
				tblIntendingCost.getSelectManager()
						.select(row.getRowIndex(), 4);
				return false;
			} else {
				BigDecimal value = (BigDecimal) row.getCell("costAmount")
						.getValue();
				if (value.compareTo(FDCHelper.ZERO) == 0) {
					this.panelDown.setSelectedComponent(tblIntendingCost);
					this.setMessageText(AimCostHandler
							.getResource("AmountNull"));
					this.showMessage();
					tblIntendingCost.getSelectManager().select(0, 6);
					tblIntendingCost.getSelectManager().select(
							row.getRowIndex(), 4);
					return false;
				}
				if (value.compareTo(FDCHelper.MAX_VALUE) > 0) {
					this.panelDown.setSelectedComponent(tblIntendingCost);
					this.setMessageText(AimCostHandler.getResource("MaxValue"));
					this.showMessage();
					tblIntendingCost.getSelectManager().select(0, 6);
					tblIntendingCost.getSelectManager().select(
							row.getRowIndex(), 4);
					return false;
				}
			}
		}
		return true;
	}

	protected void tblAdjustRecord_editStopped(KDTEditEvent e) throws Exception {
		super.tblAdjustRecord_editStopped(e);
		int rowIndex = e.getRowIndex();
		int columnIndex = e.getColIndex();
		IRow row = tblAdjustRecord.getRow(rowIndex);
		if (tblAdjustRecord.getColumnKey(columnIndex).equals("workload")
				|| tblAdjustRecord.getColumnKey(columnIndex).equals("price")) {
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
				row.getCell("costAmount").getStyleAttributes().setLocked(false);
				row.getCell("workload").setValue(null);
				row.getCell("price").setValue(null);
			} else {
				BigDecimal aimCost = workload.multiply(price);
				row.getCell("costAmount").setValue(
						aimCost.setScale(2, BigDecimal.ROUND_HALF_UP));
				row.getCell("costAmount").getStyleAttributes().setLocked(true);
			}
		}
		if (tblAdjustRecord.getColumnKey(columnIndex).equals("workload")
				|| tblAdjustRecord.getColumnKey(columnIndex).equals("price")
				|| tblAdjustRecord.getColumnKey(columnIndex).equals(
						"costAmount")
				|| tblAdjustRecord.getColumnKey(columnIndex).equals("product")) {
			detailChange();
		}
	}

	private void detailChange() {
		IRow mainRow = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		AdjustRecordEntryCollection adjusts = this.getAdjustRecordCollection();
		this.updateDynamicData(mainRow, adjusts);
		BigDecimal adjustSum = FDCHelper.ZERO;
		for (int i = 0; i < adjusts.size(); i++) {
			AdjustRecordEntryInfo adjust = adjusts.get(i);
			BigDecimal costAmount = adjust.getCostAmount();
			if (costAmount != null) {
				adjustSum = adjustSum.add(costAmount);
			}
		}
		this.tblAdjustRecord.getRow(0).getCell("costAmount")
				.setValue(adjustSum);
		this.updateIntendingData(mainRow, this.getIntendingCollection());
		this.setUnionData();
		setTotalData();
	}

	protected void tblIntendingCost_editStopped(KDTEditEvent e)
			throws Exception {
		super.tblIntendingCost_editStopped(e);
		int rowIndex = e.getRowIndex();
		int columnIndex = e.getColIndex();
		IRow row = tblIntendingCost.getRow(rowIndex);
		if (tblIntendingCost.getColumnKey(columnIndex).equals("workload")
				|| tblIntendingCost.getColumnKey(columnIndex).equals("price")) {
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
				row.getCell("costAmount").getStyleAttributes().setLocked(false);
				row.getCell("workload").setValue(null);
				row.getCell("price").setValue(null);
			} else {
				BigDecimal aimCost = workload.multiply(price);
				row.getCell("costAmount").setValue(aimCost);
				row.getCell("costAmount").getStyleAttributes().setLocked(true);
			}
		}
		if (tblIntendingCost.getColumnKey(columnIndex).equals("workload")
				|| tblIntendingCost.getColumnKey(columnIndex).equals("price")
				|| tblIntendingCost.getColumnKey(columnIndex).equals(
						"costAmount")
				|| tblIntendingCost.getColumnKey(columnIndex).equals("product")) {
			this.detailChange();
		}
	}

	public boolean isEditEntry() {
		this.tblAdjustRecord.getSelectManager().select(0, 0);
		this.tblIntendingCost.getSelectManager().select(0, 0);
		AdjustRecordEntryCollection adjCol = (AdjustRecordEntryCollection) this.tblAdjustRecord
				.getUserObject();
		IntendingCostEntryCollection intendCol = (IntendingCostEntryCollection) this.tblIntendingCost
				.getUserObject();
		if (adjCol == null || intendCol == null) {
			return false;
		}
		int count = 0;
		for (int i = 0; i < this.tblAdjustRecord.getRowCount(); i++) {
			if (this.tblAdjustRecord.getRow(i).getUserObject() != null) {
				count++;
			}
		}
		if (adjCol.size() != count) {
			return true;
		}
		for (int i = 0; i < this.tblAdjustRecord.getRowCount(); i++) {
			if (this.tblAdjustRecord.getRow(i).getUserObject() != null) {
				IRow row = this.tblAdjustRecord.getRow(i);
				AdjustRecordEntryInfo info = (AdjustRecordEntryInfo) row
						.getUserObject();
				if (info.getId() == null) {
					return true;
				}
				String[] compareyKeys = new String[] { "adjustAcctName",
						"adjustType", "adjustReason", "workload", "unit",
						"price", "costAmount", "product", "adjustDate",
						"description" };
				String[] columnKeys = new String[] { "acctName", "adjustType",
						"adjustReason", "workload", "unit", "price",
						"costAmount", "product", "adjustDate", "description" };
				for (int j = 0; j < compareyKeys.length; j++) {
					if (!FDCHelper.isEqual(info.get(compareyKeys[j]), row
							.getCell(columnKeys[j]).getValue())) {
						return true;
					}
				}
			}
		}
		if (intendCol.size() != this.tblIntendingCost.getRowCount()) {
			return true;
		}
		for (int i = 0; i < this.tblIntendingCost.getRowCount(); i++) {
			IRow row = this.tblIntendingCost.getRow(i);
			IntendingCostEntryInfo info = (IntendingCostEntryInfo) row
					.getUserObject();
			if (info.getId() == null) {
				return true;
			}
			String[] compareyKeys = new String[] { "costAcctName", "workload",
					"unit", "price", "costAmount", "product", "description" };
			String[] columnKeys = new String[] { "acctName", "workload",
					"unit", "price", "costAmount", "product", "description" };
			for (int j = 0; j < compareyKeys.length; j++) {
				if (!FDCHelper.isEqual(info.get(compareyKeys[j]), row.getCell(
						columnKeys[j]).getValue())) {
					return true;
				}
			}
		}
		return false;
	}

	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		// String selectObjId = getSelectObjId();
		// if (selectObjId == null) {
		// return;
		// }
		//
		// String boID = selectObjId + "dycost";
		// AttachmentClientManager acm = AttachmentManagerFactory
		// .getClientManager();
		// if (boID == null) {
		// return;
		// }
		// acm.showAttachmentListUIByBoID(boID, this);
		DynamicCostInfo dynamicInfo = this.dyGetter.getDynamicInfo(this.curAcct
				.getId().toString());
		if (dynamicInfo == null || dynamicInfo.getId() == null) {
			MsgBox.showInfo(AimCostHandler.getResource("NoSaveNotAttach"));
			return;
		}
		String boID = dynamicInfo.getId().toString();
		AttachmentClientManager acm = AttachmentManagerFactory
				.getClientManager();
		if (boID == null) {
			return;
		}
		acm.showAttachmentListUIByBoID(boID, this);
	}

	public void actionExpression_actionPerformed(ActionEvent e)
			throws Exception {
		KDTable table = (KDTable) this.panelDown.getSelectedComponent();
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		int colIndex = table.getSelectManager().getActiveColumnIndex();
		if (rowIndex == -1) {
			return;
		}
		if (table.getColumn(colIndex).getEditor() != null
				&& table.getColumn(colIndex).getEditor().getComponent() instanceof KDFormattedTextField) {
			ICell cell = table.getRow(rowIndex).getCell(colIndex);
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
			if (result != null) {
				cell.setUserObject(re);
				cell.setValue(result);
				KDTEditEvent editEvent = new KDTEditEvent(cell);
				editEvent.setRowIndex(rowIndex);
				editEvent.setColIndex(colIndex);
				if (table.getName().equals("tblAdjustRecord")) {
					this.tblAdjustRecord_editStopped(editEvent);
				} else {
					this.tblIntendingCost_editStopped(editEvent);
				}
			}
		}
	}

	public void actionRevert_actionPerformed(ActionEvent e) throws Exception {
		super.actionRevert_actionPerformed(e);
		this.refresh();
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		this.refresh();
//		FDCSQLBuilder builder=new FDCSQLBuilder();
//		builder.appendSql("insert into T_AIM_IntendingCostEntry values( dbo.newbosid('25202D93'),null,'xPssmwEYEADgABazwKgSkPXGlvU=','合作款项', 12.0000000000,'分米',23.0000000000,1200.0000000000,'fLSREQEYEADgABq4wKgSkOEgPpc=',null )");
//		builder.execute();
	}

	public boolean destroyWindow() {

		// if (this.isEditEntry()) {
		// if (MsgBox.showConfirm2(this, AimCostHandler.getResource("NoSave"))
		// == MsgBox.YES) {
		// if (!this.verify()) {
		// abort();
		// }
		// btnSubmit.doClick();
		// }
		// }
		// return super.destroyWindow();

		// 按需求改成有三个按钮的提示对话框 Jianxing_zhou 2007-8-20

		if (this.isEditEntry()) {
			int option = MsgBox.showConfirm3(this, AimCostHandler
					.getResource("NoSave"));
			if (option == MsgBox.CANCEL) {
				return false;
			} else if (option == MsgBox.YES) {
				if (!this.verify()) {
					abort();
				}
				btnSubmit.doClick();
			}
		}
		return super.destroyWindow();

	}

	private void setTotalData() {
		/*
		 * 已拆分成本小计行＝开发成本 合计行＝合同成本（已提交状态） 未拆分成本小计＝合计－已拆分小计 //有误 合计=未拆分成本小计－已拆分小计
		 */
		// 已发生
		String id = getSelectObjId();
		if (id == null
				|| !BOSUuid.read(id).getType().equals(
						new CurProjectInfo().getBOSType())) {
			return;
		}
		IRow rowTotal = tblMain.getRow(0);
		IRow rowNoSplit = tblMain.getRow(1);
		IRow rowSplit = tblMain.getRow(2);
		List rootRowList=new ArrayList();
		for(int i=0;i<tblMain.getRowCount();i++){
			final IRow row = tblMain.getRow(i);
			if(row.getTreeLevel()==0){
				rootRowList.add(row);
			}
		}
		//没有成本科目的情况
		if(rootRowList.size()<=0){
			return;
		}
		BigDecimal aimCost=FDCHelper.ZERO;
		BigDecimal dynamicCost=FDCHelper.ZERO;
		BigDecimal hasHappen=FDCHelper.ZERO;
		BigDecimal intendingHappen=FDCHelper.ZERO;
		for(int i=0;i<rootRowList.size();i++){
			final IRow row = (IRow) rootRowList.get(i);
			aimCost=aimCost.add(FDCHelper.toBigDecimal(row.getCell("aimCost").getValue()));
			hasHappen=hasHappen.add(FDCHelper.toBigDecimal(row.getCell("hasHappen").getValue()));
			intendingHappen=intendingHappen.add(FDCHelper.toBigDecimal(row.getCell("intendingHappen").getValue()));
			dynamicCost=dynamicCost.add(FDCHelper.toBigDecimal(row.getCell("dynamicCost").getValue()));
		}
		
		rowSplit.getCell("aimCost").setValue(aimCost);
		rowSplit.getCell("hasHappen").setValue(hasHappen);
		rowSplit.getCell("intendingHappen").setValue(intendingHappen);
		rowSplit.getCell("dynamicCost").setValue(dynamicCost);

		// 合计
		rowTotal.getCell("aimCost").setValue(aimCost);
		rowTotal.getCell("dynamicCost").setValue(dynamicCost);

		BigDecimal happenAmt = nosplitAmt.add(hasHappen);
		rowNoSplit.getCell("hasHappen").setValue(nosplitAmt);
		rowNoSplit.getCell("intendingHappen").setValue(nosplitAmt.negate());
		rowTotal.getCell("hasHappen").setValue(happenAmt);
		BigDecimal tempAmt = intendingHappen.add(nosplitAmt.negate());
		rowTotal.getCell("intendingHappen").setValue(tempAmt);
	}
	private void fetchData(String prjId) throws EASBizException, BOSException{
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if(!(proNode.getUserObject() instanceof CurProjectInfo)){
			return;
		}
		TimeTools.getInstance().msValuePrintln("start fetchData");
		//获取当前期间
		PeriodInfo curPeriod = FDCUtils.getCurrentPeriod(null,prjId,true);
//		final DynamicCostMap dynamicCostMap = FDCCostRptFacadeFactory.getRemoteInstance().getDynamicCost(prjId, null,proNode.isLeaf());
		//取当前期间数据
		final DynamicCostMap dynamicCostMap = FDCCostRptFacadeFactory.getRemoteInstance().getDynamicCost(prjId, curPeriod,proNode.isLeaf());
		if(dynamicCostMap==null){
			return;
		}
		this.productTypeGetter=dynamicCostMap.getDyProductTypeGetter();
		this.dyGetter=dynamicCostMap.getDyCostSplitDataGetter();
		this.aimGetter=dynamicCostMap.getAimCostSplitDataGetter();
		this.happenGetter=dynamicCostMap.getHappenDataGetter();
		this.nosplitAmt=FDCHelper.toBigDecimal(dynamicCostMap.getNoSplitAmt());
		this.acctMap=dynamicCostMap.getAcctMap();
		this.adjustMap=dynamicCostMap.getAdjustMap();
		TimeTools.getInstance().msValuePrintln("end fetchData");
	}

}