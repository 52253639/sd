/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.editor.ICellEditor;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeCollection;
import com.kingdee.eas.basedata.assistant.SettlementTypeFactory;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.MoneyAccountContrastEnum;
import com.kingdee.eas.fdc.sellhouse.SettlementgGatheringCollection;
import com.kingdee.eas.fdc.sellhouse.SettlementgGatheringFactory;
import com.kingdee.eas.fdc.sellhouse.SettlementgGatheringInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DeletedStatusEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SettlementgGatheringEditUI extends
		AbstractSettlementgGatheringEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SettlementgGatheringEditUI.class);
	private FullOrgUnitInfo fullOrgUnit = null;
	private SettlementgGatheringCollection mdCol = null;

	public SettlementgGatheringEditUI() throws Exception {
		super();
	}

	public void loadFields() {
		setTable();
	}

	public void storeFields() {
	}

	public void onLoad() throws Exception {
		// super.onLoad();
		
		//2010-11-23 权限问题
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
		PermissionFactory.getRemoteInstance().checkFunctionPermission(new ObjectUuidPK(user.getId()), new ObjectUuidPK(org.getId()), "she_SettlementgGathering_view");

		//财务组织及成本中心可编辑
		//OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
		if(org==null||(!SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit())||(!SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit())){
			actionSubmit.setVisible(false);
		}
		setUITitle("结算方式与收款科目对照表");
		setActionStatus();
		loadFields();
		this.initTree();
		this.tblMain.getColumn("balance").setEditor(
				createComboCellEditor(MoneyAccountContrastEnum.getEnumList()));
	}

	public static KDTDefaultCellEditor createComboCellEditor(List enumList) {
		KDComboBox comboField = new KDComboBox();
		for (int i = 0; i < enumList.size(); i++) {
			comboField.addItem(enumList.get(i));
		}
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		return comboEditor;
	}

	protected OrgUnitInfo getOrgUnitInfo() {
		OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentFIUnit(); // 当前组织
		return crrOu;
	}

	protected void initTree() throws Exception {
		OrgUnitInfo fiOrg = getOrgUnitInfo();
		MetaDataPK dataPK = null;
		if (actionOnLoad != null) {
			String actoinName = actionOnLoad.getClass().getName();
			if (actoinName.indexOf("$") >= 0) {
				actoinName = actoinName.substring(actoinName.indexOf("$") + 1);
			}
			dataPK = new MetaDataPK(actoinName);
		}
		this.treeMain.setModel(NewOrgUtils.getTreeModel(OrgViewType.COMPANY,
				"", fiOrg.getId().toString(), null, dataPK));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		treeMain.setSelectionRow(0);
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		// super.treeMain_valueChanged(e);

		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();

		if (thisNode.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo orgStruct = (OrgStructureInfo) thisNode
					.getUserObject();
			if (orgStruct != null) {
				fullOrgUnit = orgStruct.getUnit();
				if (fullOrgUnit != null) {
					fillData(fullOrgUnit);
				}
			}
		}

		if (fullOrgUnit != null && fullOrgUnit.isIsCompanyOrgUnit()) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("number");
			sic.add("isBizUnit");
			CompanyOrgUnitInfo org = CompanyOrgUnitFactory.getRemoteInstance()
					.getCompanyOrgUnitInfo(
							new ObjectUuidPK(fullOrgUnit.getId()), sic);
			if (org != null && org.isIsBizUnit()) {
				actionSubmit.setEnabled(true);
				tblMain.getStyleAttributes().setLocked(false);
				tblMain.getColumn("balance").getStyleAttributes().setLocked(
						true);
			} else {
				actionSubmit.setEnabled(false);
				tblMain.getStyleAttributes().setLocked(true);
			}
		} else {
			actionSubmit.setEnabled(false);
			tblMain.getStyleAttributes().setLocked(true);
		}
	}

	private void fillData(FullOrgUnitInfo fullOrg) throws Exception {
		this.tblMain.removeRows();
		this.tblMain.checkParsed();
		setTable();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("balance.*");
		sel.add("bankAccount.*");
		sel.add("Gathering.*");
		view.setSelector(sel);
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("orgUnit.id", fullOrg.getId().toString());
		view.setFilter(filter);
		SettlementgGatheringCollection mscCol = new SettlementgGatheringCollection();
		mscCol = SettlementgGatheringFactory.getRemoteInstance()
				.getSettlementgGatheringCollection(view);
		SettlementgGatheringInfo mdInfo;
		IRow row;
		SettlementTypeInfo stInfo;

		for (int i = 0; i < mscCol.size(); i++) {
			mdInfo = mscCol.get(i);
			if (mdInfo != null) {
				String stID = mdInfo.getBalance().getId().toString();
				for (int j = 0; j < tblMain.getRowCount(); j++) {
					row = tblMain.getRow(j);
					stInfo = (SettlementTypeInfo) row.getCell("balance")
							.getValue();
					if (stInfo != null
							&& stID.equals(stInfo.getId().toString())) {
						row.getCell("bankAccount").setValue(
								mdInfo.getBankAccount());
						row.getCell("Gathering")
								.setValue(mdInfo.getGathering());
					}
				}
			}
		}

	}

	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// super.tblMain_tableClicked(e);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(
						new FilterItemInfo("orgUnit.id", fullOrgUnit.getId()
								.toString()));
		SettlementgGatheringFactory.getRemoteInstance().delete(filter);
		SettlementgGatheringCollection colls = new SettlementgGatheringCollection();
		SettlementgGatheringInfo info = null;
		// 结算方式
		SettlementTypeInfo st = null;
		// 银行账户
		AccountBankInfo ab = null;
		// 会计科目
		AccountViewInfo av = null;

		IRow row = null;
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			row = tblMain.getRow(i);
			info = new SettlementgGatheringInfo();
			st = (SettlementTypeInfo) row.getCell("balance").getValue();
			ab = (AccountBankInfo) row.getCell("bankAccount").getValue();
			av = (AccountViewInfo) row.getCell("Gathering").getValue();
			info.setBalance(st);
			info.setBankAccount(ab);
			info.setGathering(av);
			info.setOrgUnit(fullOrgUnit);
			SettlementgGatheringFactory.getRemoteInstance().addnew(info);
		}
		// super.actionSubmit_actionPerformed(e);
	}

	protected IObjectValue createNewData() {
		// TODO Auto-generated method stub
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SettlementgGatheringFactory.getRemoteInstance();
	}

	private void setTable() {
		setTableEditor();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("name");
		sic.add("deletedStatus");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("deletedStatus", new Integer(
						DeletedStatusEnum.NORMAL_VALUE)));
		view.setFilter(filter);
		try {
			SettlementTypeCollection stCol = SettlementTypeFactory
					.getRemoteInstance().getSettlementTypeCollection(view);
			if (stCol != null) {
				IRow row;
				SettlementTypeInfo sti;
				for (int i = 0; i < stCol.size(); i++) {
					row = tblMain.addRow();
					sti = stCol.get(i);
					row.getCell("balance").setValue(sti);
				}
			}
		} catch (BOSException e) {
			handUIException(e);
		}
	}

	private void setTableEditor() {
		tblMain.checkParsed();
		KDBizPromptBox prmt1 = new KDBizPromptBox();
		prmt1
				.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");
		prmt1.setDisplayFormat("$name$");
		prmt1.setEditFormat("$number$");
		prmt1.setCommitFormat("$number$");
		KDTDefaultCellEditor editor1 = new KDTDefaultCellEditor(prmt1);
		tblMain.getColumn("balance").setEditor(editor1);
		tblMain.getColumn("balance").getStyleAttributes().setLocked(true);

		KDBizPromptBox prmt2 = new KDBizPromptBox();
		CtrlUnitInfo curCompany1 = SysContext.getSysContext().getCurrentCtrlUnit();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("CU.id", curCompany1.getId()
						.toString()));
		evi.setFilter(filter);
	    prmt2.setEntityViewInfo(evi);
		prmt2.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7AccountBankQuery");
		prmt2.setDisplayFormat("$name$");
		prmt2.setEditFormat("$number$");
		prmt2.setCommitFormat("$number$");
		KDTDefaultCellEditor editor2 = new KDTDefaultCellEditor(prmt2);
		tblMain.getColumn("bankAccount").setEditor(editor2);

		// 收款科目
		KDBizPromptBox gatheringSubject = new KDBizPromptBox();
		CompanyOrgUnitInfo curCompany = SysContext.getSysContext()
				.getCurrentFIUnit();

		EntityViewInfo view = this.getAccountEvi(curCompany);
		AccountPromptBox opseelect = new AccountPromptBox(this, curCompany,
				view.getFilter(), false, true);
		gatheringSubject.setEntityViewInfo(view);
		gatheringSubject.setSelector(opseelect);
		gatheringSubject
				.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
		gatheringSubject.setEditFormat("$number$");
		gatheringSubject.setCommitFormat("$number$");
		gatheringSubject.setEnableToMaintainBizdata(false);
		opseelect.setEnableToMaintainBizdata(false);
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(
				gatheringSubject);
		tblMain.getColumn("Gathering").setEditor(f7Editor);
	}

	private EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo) {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// -----------------转6.0后修改,科目不按CU隔离,根据财务组织进行隔离
		filter.getFilterItems().add(
				new FilterItemInfo("companyID.id", companyInfo.getId()
						.toString()));
		if (companyInfo.getAccountTable() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("accountTableID.id", companyInfo
							.getAccountTable().getId().toString()));
		}
		filter.getFilterItems().add(
				new FilterItemInfo("isGFreeze", Boolean.FALSE));
		evi.setFilter(filter);
		return evi;
	}

	private void setActionStatus() {
		actionAddNew.setVisible(false);
		actionEdit.setVisible(false);
		actionSave.setVisible(false);
		actionCopy.setVisible(false);
		actionRemove.setVisible(false);
		actionAttachment.setVisible(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		actionFirst.setVisible(false);
		actionPre.setVisible(false);
		actionNext.setVisible(false);
		actionLast.setVisible(false);
		actionCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
		btnSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
	}
}