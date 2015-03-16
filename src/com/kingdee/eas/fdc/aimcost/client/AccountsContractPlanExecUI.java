/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractSourceEnum;
import com.kingdee.eas.fdc.contract.IContractCostSplitEntry;
import com.kingdee.eas.fdc.contract.client.ContractFullInfoUI;
import com.kingdee.eas.fdc.finance.ConPayPlanSplitFactory;
import com.kingdee.eas.fdc.finance.IPaymentSplitEntry;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ITreeBase;

/**
 * 描述:科目合同明细表
 * 
 * @author jackwang date:2007-5-18
 *         <p>
 * @version EAS5.3
 */
public class AccountsContractPlanExecUI extends AbstractAccountsContractPlanExecUI {
	private static final Logger logger = CoreUIObject.getLogger(AccountsContractPlanExecUI.class);

	private CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentCostUnit();

	/**
	 * output class constructor
	 */
	public AccountsContractPlanExecUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		// this.actionAttachment.setVisible(true);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		AimCostInfo aimCost = (AimCostInfo) ((Map) this.getUIContext().get("DATAOBJECTS")).get("billInfo");
		if (aimCost != null) {
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot();
			DefaultKingdeeTreeNode node = findNode(root, aimCost.getOrgOrProId());
			if (node != null) {
				this.treeMain.setSelectionNode(node);
				this.pnlMain.setDividerLocation(0);
				this.treeMain.setVisible(false);
			}
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int acctNameIndex=tblMain.getColumn("acctName").getColumnIndex()+1;
				tblMain.getViewManager().freeze(0, acctNameIndex);
		}});
		FDCClientHelper.setUIMainMenuAsTitle(this);
	}

	protected void initTree() throws Exception {
		this.initTable();
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true, (TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	/**
	 * 设置表格属性
	 */
	private void initTable() {
		KDTable table = this.tblMain;
		table.checkParsed();
		// table.getStyleAttributes().setLocked(false);
		table.setRefresh(false);
		table.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		table.getViewManager().setFreezeView(-1, 2);
		// table.getSelectManager().setSelectMode(
		// KDTSelectManager.MULTIPLE_CELL_SELECT);
		// table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		// table.getColumn("acctNumber").getStyleAttributes().setLocked(true);
		// Color lockColor = new Color(0xF0AAD9);
		// table.getColumn("acctNumber").getStyleAttributes().setBackground(
		// lockColor);

		table.setColumnMoveable(true);
		table.getColumn("unit").getStyleAttributes().setHided(true);
		table.getColumn("date").getStyleAttributes().setHided(true);
		table.getColumn("isInvite").getStyleAttributes().setHided(true);
		table.getColumn("hasPayAmt").getStyleAttributes().setHided(true);
//		FDCTableHelper.setColumnMoveable(table, true);
		FDCHelper.formatTableNumber(table, new String[]{"amt","splitAmt","payPlan","hasPay","execDiff","allPayPlan","allHasPay","allexecDif"});
		table.getColumn("execProgess").getStyleAttributes().setNumberFormat("0.00%");
		table.getColumn("execProgess").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("allexecProgess").getStyleAttributes().setNumberFormat("0.00%");
		table.getColumn("allexecProgess").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
	}

	private void initControl() {
		this.actionImportData.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuBiz.setEnabled(false);
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
		this.menuBiz.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		this.menuItemSubmit.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.menuItemRecense.setVisible(false);
		
    	Calendar calc=Calendar.getInstance();
    	int year=calc.get(Calendar.YEAR);
    	int month=calc.get(Calendar.MONTH)+1;
    	SpinnerNumberModel model=new SpinnerNumberModel(year,1900,3000,1);
    	spYear.setModel(model);
    	model=new SpinnerNumberModel(month,1,12,1);
    	spMonth.setModel(model);
    	this.kDPanel2.setPreferredSize(new Dimension(10,30));
	}

	private DefaultKingdeeTreeNode findNode(DefaultKingdeeTreeNode node, String id) {
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
			DefaultKingdeeTreeNode findNode = findNode((DefaultKingdeeTreeNode) node.getChildAt(i), id);
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

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
//		super.tblMain_tableClicked(e);
        if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
        	setCursorOfWair();
        	IRow row=KDTableUtil.getSelectedRow(tblMain);
			if(row.getUserObject() instanceof ContractBillInfo){
				ContractBillInfo info = (ContractBillInfo) row.getUserObject();
				ContractFullInfoUI.showDialogWindows(this, info.getId().toString());
			}
			setCursorOfDefault();
        }
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	/**
	 * output tblMain_editStopping method
	 */
	protected void tblMain_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
		super.tblMain_editStopping(e);
	}

	/**
	 * output tblMain_editStopped method
	 */
	protected void tblMain_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
		super.tblMain_editStopped(e);
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	private CurProjectInfo getSelectProject() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			return null;
		}
		return null;
	}

	private String getSelectObjId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
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

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		fillTable();

	}


	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		fillTable();
	}


	private void refresh() throws Exception, BOSException, SQLException {
		fillTable();
	}

	private void initCbMap(String selectObjId) throws BOSException {
		// 获取当前工程项目下的科目map
		HashSet accountUps = new HashSet();
		ICostAccount ica = CostAccountFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", selectObjId));
		evi.getSelector().add(new SelectorItemInfo("id"));
		evi.getSelector().add(new SelectorItemInfo("curProject.id"));
		evi.setFilter(filter);
		CostAccountCollection cac = ica.getCostAccountCollection(evi);
		if (cac != null) {
			for (int i = 0; i < cac.size(); i++) {
				accountUps.add(cac.get(i).getId().toString());
			}
		}
		if(accountUps.size()==0){
			return;
		}
		//
		// IContractBill icb = ContractBillFactory.getRemoteInstance();
		IContractCostSplitEntry icse = ContractCostSplitEntryFactory.getRemoteInstance();
		evi = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("costAccount.id", accountUps, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
		evi.getSelector().add(new SelectorItemInfo("id"));
		evi.getSelector().add(new SelectorItemInfo("costAccount.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.id"));
		evi.setFilter(filter);
		ContractCostSplitEntryCollection ccsec = icse.getContractCostSplitEntryCollection(evi);
		if (ccsec != null) {
			for (int i = 0; i < ccsec.size(); i++) {
				if (!lnUps.contains(ccsec.get(i).getParent().getContractBill().getId().toString())) {
					lnUps.add(ccsec.get(i).getParent().getContractBill().getId().toString());
				}
			}
		}

		// ContractBillCollection cbc = icb.getContractBillCollection(evi);
		// if (cbc.size() != 0) {
		// for (int i = 0; i < cbc.size(); i++) {
		// // cbMap.put(cbc.get(i).getId().toString(),cbc.get(i));
		// lnUps.add(cbc.get(i).getId().toString());
		// }
		// }
	}

	private void initRealMap() throws BOSException {
		IContractCostSplitEntry icse = ContractCostSplitEntryFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.getSelector().add(new SelectorItemInfo("*"));
		evi.getSelector().add(new SelectorItemInfo("costAccount.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.name"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.number"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.signDate"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.contractSource"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.amount"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.partB.name"));
		if (lnUps.size() != 0) {// 当前工程项目下有合同
			filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id", lnUps, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
			evi.setFilter(filter);
			ContractCostSplitEntryCollection ccsec = icse.getContractCostSplitEntryCollection(evi);
			if (ccsec.size() != 0) {
				ContractBillInfo cbi;
				for (int i = 0; i < ccsec.size(); i++) {
					if (!realMap.containsKey(ccsec.get(i).getCostAccount().getId().toString())) {
						ContractBillCollection cbc = new ContractBillCollection();
						cbc.add(ccsec.get(i).getParent().getContractBill());
						realMap.put(ccsec.get(i).getCostAccount().getId().toString(), cbc);
					} else {
						ContractBillCollection cbc = (ContractBillCollection) realMap.get(ccsec.get(i).getCostAccount().getId().toString());
						if (!cbc.contains(ccsec.get(i).getParent().getContractBill())) {
							cbc.add(ccsec.get(i).getParent().getContractBill());
							realMap.put(ccsec.get(i).getCostAccount().getId().toString(), cbc);
						}
					}
					if (((!ccsec.get(i).isIsLeaf())
							&& ccsec.get(i).getSplitType() != null && ccsec
							.get(i).getSplitType().equals(
									CostSplitTypeEnum.PRODSPLIT))
							|| (ccsec.get(i).getSplitType() != null && !ccsec
									.get(i).getSplitType().equals(
											CostSplitTypeEnum.PRODSPLIT))
							|| (ccsec.get(i).getSplitType() == null)) {
						if (splitAmtMap.containsKey(ccsec.get(i).getCostAccount().getId().toString())) {
							Map map = (Map) splitAmtMap.get(ccsec.get(i).getCostAccount().getId().toString());
							if (map.containsKey(ccsec.get(i).getParent().getContractBill().getId().toString())) {
								BigDecimal t = (BigDecimal) map.get(ccsec.get(i).getParent().getContractBill().getId().toString());
								t = t.add(ccsec.get(i).getAmount());
								map.put(ccsec.get(i).getParent().getContractBill().getId().toString(), t);
							} else {
								map.put(ccsec.get(i).getParent().getContractBill().getId().toString(), ccsec.get(i).getAmount());
							}
							splitAmtMap.put(ccsec.get(i).getCostAccount().getId().toString(), map);
						} else {
							Map map = new HashMap();
							map.put(ccsec.get(i).getParent().getContractBill().getId().toString(), ccsec.get(i).getAmount());
							splitAmtMap.put(ccsec.get(i).getCostAccount().getId().toString(), map);
						}
						// Map map = new HashMap();
						// map.put(ccsec.get(i).getParent().getContractBill().getId().toString(),
						// ccsec.get(i).getAmount());
						// if
						// (!splitAmtMap.containsKey(ccsec.get(i).getCostAccount().getId().toString()))
						// {
						// ArrayList al = new ArrayList();
						// al.add(map);
						// splitAmtMap.put(ccsec.get(i).getCostAccount().getId().toString(),
						// al);
						// } else {
						// ArrayList al = (ArrayList)
						// splitAmtMap.get(ccsec.get(i).getCostAccount().getId().toString());
						// ArrayList al1 = new ArrayList();
						// for(int j=0;j<al.size();j++){
						// Map m= (Map) al.get(j);
						// if(m.containsKey(ccsec.get(i).getParent().getContractBill().getId().toString())){
						// // al.remove(m);
						// BigDecimal t = (BigDecimal)
						// m.get(ccsec.get(i).getParent().getContractBill().getId().toString());
						// t = t.add(ccsec.get(i).getAmount());
						// m.put(ccsec.get(i).getParent().getContractBill().getId().toString(),t);
						// al1.add(m);
						// }else{
						// al1.add(m);
						// }
						// }
						// // if (!al.contains(map)) {
						// // al.add(map);
						// //
						// splitAmtMap.put(ccsec.get(i).getCostAccount().getId().toString(),
						// al);
						// // }
						// splitAmtMap.put(ccsec.get(i).getCostAccount().getId().toString(),
						// al);
						// }
					}
					// this.splitAmtMap.put(ccsec.get(i).getCostAccount().getId().toString(),map);
				}
			}
		}
	}

	Map pbAmtMap = new HashMap();

	private void initPayBillEntryMap() throws BOSException {
		IPaymentSplitEntry ipse = PaymentSplitEntryFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.getSelector().add(new SelectorItemInfo("*"));
		evi.getSelector().add(new SelectorItemInfo("costAccount.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.paymentBill.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.paymentBill.contractBillId"));
		evi.getSelector().add(new SelectorItemInfo("parent.paymentBill.billStatus"));

		if (pBlnUps.size() != 0) {// 当前工程项目下有合同
			filter.getFilterItems().add(new FilterItemInfo("parent.paymentBill.id", pBlnUps, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("parent.paymentBill.billStatus", BillStatusEnum.PAYED));
			evi.setFilter(filter);
			PaymentSplitEntryCollection psec = ipse.getPaymentSplitEntryCollection(evi);
			if (psec.size() != 0) {
				ContractBillInfo cbi;
				for (int i = 0; i < psec.size(); i++) {
					// if(!realMap.containsKey(ccsec.get(i).getCostAccount().getId().toString())){
					// ContractBillCollection cbc = new
					// ContractBillCollection();
					// cbc.add(ccsec.get(i).getParent().getContractBill());
					// realMap.put(ccsec.get(i).getCostAccount().getId().toString(),cbc);
					// }else{
					// ContractBillCollection cbc = (ContractBillCollection)
					// realMap.get(ccsec.get(i).getCostAccount().getId().toString());
					// if(!cbc.contains(ccsec.get(i).getParent().getContractBill())){
					// cbc.add(ccsec.get(i).getParent().getContractBill());
					// realMap.put(ccsec.get(i).getCostAccount().getId().toString(),cbc);
					// }
					// }
					Map map = new HashMap();
					map.put(psec.get(i).getParent().getPaymentBill().getContractBillId(), psec.get(i).getAmount());
					if (psec.get(i).getCostAccount() != null) {
						if (!paySplitAmtMap.containsKey(psec.get(i).getCostAccount().getId().toString())) {
							ArrayList al = new ArrayList();
							al.add(map);
							paySplitAmtMap.put(psec.get(i).getCostAccount().getId().toString(), al);

						} else {
							ArrayList al = (ArrayList) paySplitAmtMap.get(psec.get(i).getCostAccount().getId().toString());
							if (!al.contains(map)) {
								al.add(map);
								paySplitAmtMap.put(psec.get(i).getCostAccount().getId().toString(), al);
							}
						}
					}
					// this.splitAmtMap.put(ccsec.get(i).getCostAccount().getId().toString(),map);
				}
			}
		}
	}

	private void initPayAmtMap() throws BOSException {
		// 获取当前工程项目节点下合同关联的所有已付款付款单的集合
		// 获取每个合同关联的已付款付款单金额的集合
		IPaymentBill ipb = PaymentBillFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.getSelector().add(new SelectorItemInfo("id"));
		evi.getSelector().add(new SelectorItemInfo("actPayAmt"));
		evi.getSelector().add(new SelectorItemInfo("contractBillId"));
		evi.getSelector().add(new SelectorItemInfo("billStatus"));
		filter.getFilterItems().add(new FilterItemInfo("contractBillId", lnUps, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("billStatus", BillStatusEnum.PAYED));
		evi.setFilter(filter);
		PaymentBillCollection pbc = ipb.getPaymentBillCollection(evi);
		if (pbc.size() != 0) {
			for (int i = 0; i < pbc.size(); i++) {
				pBlnUps.add(pbc.get(i).getId().toString());
			}
			for (int i = 0; i < pbc.size(); i++) {
				String key = pbc.get(i).getContractBillId();
				if (pbAmtMap.containsKey(key)) {
					BigDecimal tmp = (BigDecimal) pbAmtMap.get(key);
					tmp = tmp.add(pbc.get(i).getActPayAmt());
					pbAmtMap.put(key, tmp);
				} else {
					pbAmtMap.put(key, pbc.get(i).getActPayAmt());
				}
			}
		}
	}

	Map cbMap = new HashMap();

	HashSet lnUps = new HashSet();

	HashSet pBlnUps = new HashSet();

	Map splitAmtMap = new HashMap();

	Map paySplitAmtMap = new HashMap();

	ArrayList cbList = new ArrayList();

	Map realMap = new HashMap();
	private Map planSplitMap=new HashMap();
	private Map reqSplitMap=new HashMap();
	private Map allPlanSplitMap=new HashMap();
	private Map allReqSplitMap=new HashMap();
	public void fillTable() throws Exception {
		tblMain.removeRows();
		this.realMap.clear();
		this.splitAmtMap.clear();
		this.cbMap.clear();
		this.pbAmtMap.clear();
		this.lnUps.clear();
		this.pBlnUps.clear();
		// this.paySplitAmtMap.clear();
		tblMain.setUserObject(null);
		String selectObjId = getSelectObjId();
		Calendar calc=Calendar.getInstance();
		calc.set(((Integer)spYear.getValue()).intValue(), ((Integer)spMonth.getValue()).intValue()-1, 1);
		CurProjectInfo project = getSelectProject();
		if (project != null && project.isIsLeaf()) {
			Map costAcctPlan = ConPayPlanSplitFactory.getRemoteInstance().getCostAcctPlan(project.getId().toString(), calc.getTime());
			if(costAcctPlan==null){
				costAcctPlan=new HashMap();
			}
			planSplitMap=(Map)costAcctPlan.get("planSplitMap");
			reqSplitMap=(Map)costAcctPlan.get("reqSplitMap");
			allPlanSplitMap=(Map)costAcctPlan.get("allPlanSplitMap");
			allReqSplitMap=(Map)costAcctPlan.get("allReqSplitMap");
			if(planSplitMap==null){
				planSplitMap=new HashMap();
			}
			if(reqSplitMap==null){
				reqSplitMap=new HashMap();
			}
			if(allPlanSplitMap==null){
				allPlanSplitMap=new HashMap();
			}
			if(allReqSplitMap==null){
				allReqSplitMap=new HashMap();
			}
			initCbMap(selectObjId);// 初始化合同map
			if (lnUps.size() != 0) {// 当前工程项目下有合同
				initRealMap();// 初始化科目对应合同map
				initPayAmtMap();// 初始化已付款付款单map
				// initPayBillEntryMap();//初始化已付款关联科目与合同的拆分金额
				BOSObjectType bosType = project.getId().getType();
				FilterInfo acctFilter = new FilterInfo();
				if (new CurProjectInfo().getBOSType().equals(bosType)) {
					acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString()));
				}
				acctFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
				TreeModel costAcctTree;
				costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory.getRemoteInstance(), acctFilter);
				DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree.getRoot();
				Enumeration childrens = root.depthFirstEnumeration();
				int maxLevel = 0;
				while (childrens.hasMoreElements()) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens.nextElement();
					if (node.getUserObject() != null && node.getLevel() > maxLevel) {
						maxLevel = node.getLevel();
					}
				}
				tblMain.getTreeColumn().setDepth(maxLevel + 1);
				for (int i = 0; i < root.getChildCount(); i++) {
					fillNode((DefaultMutableTreeNode) root.getChildAt(i));
				}
				gatherDatas();
			}
		}
	}

	private void fillNode(DefaultMutableTreeNode node) throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}
		String acctId = costAcct.getId().toString();
		IRow row = tblMain.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("acctNumber").setValue(costAcct.getLongNumber().replace('!', '.'));
		row.getCell("acctNumber").setUserObject(acctId);
		row.getCell("acctName").setValue(costAcct.getName());
		if (node.isLeaf()) {
			ContractBillCollection cbc = (ContractBillCollection) this.realMap.get(acctId);
			if (cbc != null) {
				for (int i = 0; i < cbc.size(); i++) {
					ContractBillInfo info = cbc.get(i);
					IRow eRow = this.tblMain.addRow();
					eRow.getCell("acctNumber").setUserObject(acctId);
					eRow.setTreeLevel(node.getLevel());
					eRow.setUserObject(info);
					loadRow(eRow);// 合同数据填充
					// ArrayList al = (ArrayList) splitAmtMap.get(acctId);
					// if (al != null && al.size() != 0) {
					// for (int j = 0; j < al.size(); j++) {
					// Map map = (Map) al.get(j);
					// if (map.get(info.getId().toString()) != null) {
					// eRow.getCell("splitAmt").setValue(map.get(info.getId().toString()));//拆分金额
					// }
					// }
					// }
					Map map = (Map) splitAmtMap.get(acctId);
					if (map != null) {
						eRow.getCell("splitAmt").setValue(map.get(info.getId().toString()));// 已付款金额
					}
					// al = (ArrayList) paySplitAmtMap.get(acctId);
					// if (al != null && al.size() != 0) {
					// for (int j = 0; j < al.size(); j++) {
					// Map map = (Map) al.get(j);
					// if (map.get(info.getId().toString()) != null) {
					// eRow.getCell("hasPayAmt").setValue(map.get(info.getId().toString()));
					// }
					// }
					// }
					eRow.getCell("hasPayAmt").setValue(pbAmtMap.get(info.getId().toString()));// 已付款金额
				}
			}
		} else {
		}

		//
		for (int i = 0; i < node.getChildCount(); i++) {
			this.fillNode((DefaultMutableTreeNode) node.getChildAt(i));
		}
	}

	/*
	 * 汇总数据
	 */
	private void gatherDatas() {
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		for (int j = 8; j < table.getColumnCount(); j++) {
			amountColumns.add(tblMain.getColumn(j).getKey());
		}

		for (int i = 0; i < table.getRowCount(); i++) {

			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {// 非叶结点
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
					boolean hasData = false;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						if (rowAdd.getCell(colName).getStyleAttributes().getFontColor().equals(Color.RED)) {
						}
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(((Integer) value).toString()));
							}
							hasData = true;
						}
					}
					if (hasData) {
						row.getCell(colName).setValue(amount);
					}
				}
			} else {

			}

		}
	}

	// /////////////////////////
	public void loadRow(IRow row) {
		ContractBillInfo info = (ContractBillInfo) row.getUserObject();
		row.getCell("contract").setValue(info.getName());
		row.getCell("conNumber").setValue(info.getNumber());
		if(info.getPartB()!=null){
			row.getCell("unit").setValue(info.getPartB().getName());
		}
		row.getCell("date").setValue(info.getSignDate());
		if (info.getContractSource() != null && info.getContractSource().getValue().equals(ContractSourceEnum.INVITE_VALUE)) {
			row.getCell("isInvite").setValue(Boolean.valueOf(true));
		} else {
			row.getCell("isInvite").setValue(Boolean.valueOf(false));
		}
		row.getCell("amt").setValue(info.getAmount());
		String acctId=(String)row.getCell("acctNumber").getUserObject();
		if(acctId!=null){
			String key=acctId+info.getId().toString();
			BigDecimal planAmt=(BigDecimal)this.planSplitMap.get(key);
			BigDecimal reqAmt=(BigDecimal)this.reqSplitMap.get(key);
			BigDecimal diff=FDCNumberHelper.subtract(planAmt, reqAmt);
			BigDecimal execProgess=FDCNumberHelper.divide(reqAmt, planAmt);
			row.getCell("payPlan").setValue(planAmt);
			row.getCell("hasPay").setValue(reqAmt);
			row.getCell("execDiff").setValue(diff);
			row.getCell("execProgess").setValue(execProgess);
			
			BigDecimal allplanAmt=(BigDecimal)this.allPlanSplitMap.get(key);
			BigDecimal allreqAmt=(BigDecimal)this.allReqSplitMap.get(key);
			BigDecimal alldiff=FDCNumberHelper.subtract(allplanAmt, allreqAmt);
			BigDecimal allexecProgess=FDCNumberHelper.divide(allreqAmt, allplanAmt);
			row.getCell("allPayPlan").setValue(allplanAmt);
			row.getCell("allHasPay").setValue(allreqAmt);
			row.getCell("allexecDif").setValue(alldiff);
			row.getCell("allexecProgess").setValue(allexecProgess);
			
		}
		// BigDecimal workload = info.getWorkload();
		// if (workload != null && workload.compareTo(FDCHelper.ZERO) == 0) {
		// workload = null;
		// }
		// row.getCell("workload").setValue(workload);
		// row.getCell("unit").setValue(info.getUnit());
		// BigDecimal price = info.getPrice();
		// if (price != null && price.compareTo(FDCHelper.ZERO) == 0) {
		// price = null;
		// }
		// row.getCell("price").setValue(price);
		// row.getCell("aimCost").setValue(info.getCostAmount());
		// if (workload != null && price != null) {
		// row.getCell("aimCost").getStyleAttributes().setLocked(true);
		// }
		// if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
		// BigDecimal buildPart = info.getCostAmount().divide(this.buildArea,
		// 2, BigDecimal.ROUND_HALF_UP).setScale(2,
		// BigDecimal.ROUND_HALF_UP);
		// row.getCell("buildPart").setValue(buildPart);
		// }
		// if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
		// BigDecimal sellPart = info.getCostAmount().divide(this.sellArea, 2,
		// BigDecimal.ROUND_HALF_UP).setScale(2,
		// BigDecimal.ROUND_HALF_UP);
		// row.getCell("sellPart").setValue(sellPart);
		// }
		// row.getCell("product").setValue(info.getProduct());
		// row.getCell("description").setValue(info.getDescription());
	}


	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}


	/**
	 * 获取query中的主键列名称，返回供编辑/删除时获取主键用，默认值为"id"，继承类可以重载
	 * 
	 */
	protected String getKeyFieldName() {
		return "acctNumber";
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

	protected boolean isCanOrderTable() {
		return false;
	}
	protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
		actionRefresh_actionPerformed(e);
	}
}