/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.client.AbstractContractWithoutTextListUI;
import com.kingdee.eas.fdc.contract.client.ContractBillListUI;
import com.kingdee.eas.fdc.contract.client.ContractListBaseUI;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.RenewRelateProgSaveFacadeFactory;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RenewRelateProgrammingListUI extends
		AbstractRenewRelateProgrammingListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RenewRelateProgrammingListUI.class);
	private boolean isModify = false;
	private HashSet hs = new HashSet();
	// 框架合约F7
	private KDBizPromptBox kdtCostEntries_programmingContract_PromptBox = null;
	// 最后审批人，最后审批时间
	Map auditMap = new HashMap();

	/**
	 * output class constructor
	 */
	public RenewRelateProgrammingListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected void fetchInitData() throws Exception {
		super.fetchInitData();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		btnSave.setEnabled(true);
		btnSave.setVisible(true);
		btnAuditResult.setVisible(false);
		btnAttachment.setVisible(false);
		menuItemSave.setVisible(true);
		menuItemSave.setEnabled(true);
		MenuItemAttachment.setVisible(false);
		MenuItemAttachment.setEnabled(false);
		kDSplitPane2.add(contContrList, "bottom");
		kDSplitPane2.setDividerLocation(1.0);
		tblMain.getColumn("period.number").getStyleAttributes().setHided(false);
		loadData();
		setProgNotEdit();
		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
			this.btnSave.setEnabled(false);
		}
	}

	/**
	 * 加载数据
	 * 
	 * @throws Exception
	 */
	private void loadData() throws Exception {
		// 设置合约框架F7
		createProgrammingContractF7();
	}

	protected FilterInfo getTreeSelectChangeFilter() {

		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("contractType.isEnabled",
				Boolean.TRUE));
		filterItems
				.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("state", "4AUDITTED",
				com.kingdee.bos.metadata.query.util.CompareType.EQUALS));
		return filter;
	}

	/**
	 * 设置合约构架 F7
	 */
	private void createProgrammingContractF7() throws Exception {
		this.tblMain.getColumn("programmingContract").getStyleAttributes()
				.setLocked(false);
		kdtCostEntries_programmingContract_PromptBox = new KDBizPromptBox();
		ProgrammingContractPromptBox selector = new ProgrammingContractPromptBox(
				this);
		kdtCostEntries_programmingContract_PromptBox
				.setEnabledMultiSelection(false);
		kdtCostEntries_programmingContract_PromptBox
				.setEditFormat("$longNumber$");
		kdtCostEntries_programmingContract_PromptBox.setDisplayFormat("$name$");
		kdtCostEntries_programmingContract_PromptBox
				.setCommitFormat("$longNumber$");
		kdtCostEntries_programmingContract_PromptBox.setSelector(selector);
		kdtCostEntries_programmingContract_PromptBox
				.addSelectorListener(new SelectorListener() {
					public void willShow(SelectorEvent e) {
						boolean flag = isExistProg();
						if (!flag) {
							e.setCanceled(true);
						}
						kdtCostEntries_programmingContract_PromptBox
								.setRefresh(true);
						String id = getSelectedKeyValue();
						ContractBillInfo contractBillInfo = null;
						try {
							SelectorItemCollection sic = new SelectorItemCollection();
							sic.add("*");
							sic.add("curProject.*");
							contractBillInfo = ContractBillFactory
									.getRemoteInstance().getContractBillInfo(
											new ObjectUuidPK(id), sic);
						} catch (Exception e1) {
							logger.error(e1);
						}
						if (contractBillInfo == null) {
							ContractWithoutTextInfo contractWithoutTextInfo = null;
							try {
								contractWithoutTextInfo = ContractWithoutTextFactory
										.getRemoteInstance()
										.getContractWithoutTextInfo(
												new ObjectUuidPK(id));
							} catch (Exception e1) {
								logger.error(e1);
							}
							if (contractWithoutTextInfo.getCurProject() != null) {
								EntityViewInfo entityView = new EntityViewInfo();
								FilterInfo filter = new FilterInfo();
								String pro = contractWithoutTextInfo
										.getCurProject().getId().toString();
								Set set = new HashSet();
								set.add(pro);
								filter.getFilterItems().add(
										new FilterItemInfo("project.id", set,
												CompareType.INCLUDE));
								filter.getFilterItems().add(new FilterItemInfo("programming.isLatest", new Integer(1), CompareType.EQUALS));
								filter.getFilterItems().add(new FilterItemInfo("programming.state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.EQUALS));
								entityView.setFilter(filter);
								kdtCostEntries_programmingContract_PromptBox
										.setEntityViewInfo(entityView);
							}
						} else {
							if (contractBillInfo.getCurProject() != null) {
								EntityViewInfo entityView = new EntityViewInfo();
								FilterInfo filter = new FilterInfo();
								String pro = contractBillInfo.getCurProject()
										.getId().toString();
								Set set = new HashSet();
								set.add(pro);
								filter.getFilterItems().add(
										new FilterItemInfo("project.id", set,
												CompareType.INCLUDE));
								filter.getFilterItems().add(new FilterItemInfo("programming.isLatest", new Integer(1), CompareType.EQUALS));
								filter.getFilterItems().add(new FilterItemInfo("programming.state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.EQUALS));
								entityView.setFilter(filter);
								kdtCostEntries_programmingContract_PromptBox
										.setEntityViewInfo(entityView);
								kdtCostEntries_programmingContract_PromptBox
										.setRefresh(true);
							}
						}
					}
				});
		KDTDefaultCellEditor kdtCostEntries_programmingContract_CellEditor = new KDTDefaultCellEditor(
				kdtCostEntries_programmingContract_PromptBox);
		this.tblMain.getColumn("programmingContract").setEditor(
				kdtCostEntries_programmingContract_CellEditor);

	}

	private boolean isExistProg() {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		Object progValue = this.tblMain
				.getCell(rowIndex, "programmingContract").getValue();
		if (progValue != null) {
			int ret = MsgBox.showConfirm2(this, "该合同已关联框架合约，请确认是否替换?");
			if (ret == MsgBox.YES) {
				return true;
			} else if (ret == MsgBox.CANCEL) {
				return false;
			}
		}
		return true;
	}

	protected void buildContractTypeTree() throws Exception {
		super.buildContractTypeTree();
		KingdeeTreeModel treeModel = (KingdeeTreeModel) treeContractType
				.getModel();
		DefaultKingdeeTreeNode oldRootNode = (DefaultKingdeeTreeNode) treeModel
				.getRoot();
		KDTreeNode node = new KDTreeNode("ContractWithoutText");
		node.setText(PayReqUtils.getRes("contractWithoutText"));
		treeContractType.addNodeInto(node, oldRootNode);// 用无合同类型内的数据来模拟
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if (e.getType() == KDTStyleConstants.HEAD_ROW) {
			super.tblMain_tableClicked(e);
		}
	}
	private boolean isRelateCon(String conId) throws BOSException, SQLException{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select con.fid conId from t_con_contractbillentry entry");
		builder.appendSql(" inner join T_CON_Contractbill con on con.fid=entry.fparentid  and con.fisAmtWithoutCost=1 and con.fcontractPropert='SUPPLY'  ");
		builder.appendSql(" inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and parent.fcurprojectid=con.fcurprojectid	 ");
		builder.appendSql("  where entry.FRowkey='am' and");
		builder.appendParam("  con.fid",conId);
		IRowSet rowSet = builder.executeQuery();
		while(rowSet.next()){
			return true;
		}
		return false;
	}
	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		if (!getTypeSelectedTreeNode().getUserObject().equals(
		"ContractWithoutText")) {
		String selectedKeyValue = getSelectedKeyValue();
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		SelectorItemCollection sic  = new SelectorItemCollection();
		sic.add("*");
		sic.add("programmingContract");
		sic.add("contractType.*");
		sic.add("IsRenewRelateProg");
		ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(selectedKeyValue), sic);
		if(isRelateCon(selectedKeyValue)){
			tblMain.getCell(activeRowIndex, "programmingContract").getStyleAttributes().setLocked(true);
		}else{
			if(contractBillInfo.getIsRenewRelateProg() == 0 && contractBillInfo.getProgrammingContract() != null){
				tblMain.getCell(activeRowIndex, "programmingContract").getStyleAttributes().setLocked(true);
			}else{
				this.btnProgram.setEnabled(true);
			}
		}
		}
	}

	/**
	 * output treeProject_valueChanged method
	 */
	protected void treeProject_valueChanged(
			javax.swing.event.TreeSelectionEvent e) throws Exception {
		if (getTypeSelectedTreeNode() != null
				&& getTypeSelectedTreeNode().getUserObject().equals(
						"ContractWithoutText")) {
			contractWithoutTextSelect();
		} else {
			super.treeProject_valueChanged(e);
			setProgNotEdit();
		}
		isModify = false;
		hs.clear();
	}

	/**
	 * output treeContractType_valueChanged method
	 */
	private boolean flag = false;
	private static ResourceBundleHelper resHelperWithout = new ResourceBundleHelper(
			AbstractContractWithoutTextListUI.class.getName());

	protected void treeContractType_valueChanged(
			javax.swing.event.TreeSelectionEvent e) throws Exception {
		if (getTypeSelectedTreeNode() == null)
			return;
		KDTreeNode oldNode = null;
		if (e.getOldLeadSelectionPath() != null) {
			oldNode = (KDTreeNode) e.getOldLeadSelectionPath()
					.getLastPathComponent();
		}

		// 选择无文本合同
		if (getTypeSelectedTreeNode().getUserObject().equals(
				"ContractWithoutText")) {
			flag = true;
			if (oldNode == null
					|| !oldNode.getUserObject().equals("ContractWithoutText")) {
				mainQuery.getSelector().clear();
				mainQuery.getFilter().getFilterItems().clear();
				mainQuery.getSorter().clear();
				this.tblMain.putBindContents("mainQuery", new String[] { "id",
						"bookedDate", "period.number", "state", "Name",
						"number", "contractType.name", "currency.name",
						"originalAmount", "amount", "signDate",
						"programmingContract.name" });
				mainQueryPK = new MetaDataPK(
						"com.kingdee.eas.fdc.contract.programming.app",
						"ReContractWithoutTextQuery");
				tblMain.checkParsed(true);
			}
			// 设置合约框架F7
			createProgrammingContractF7();
			contractWithoutTextSelect();

		} else {
			flag = false;
			// 测试
			if (oldNode == null
					|| oldNode.getUserObject().equals("ContractWithoutText")) {
				mainQuery.getSelector().clear();
				mainQuery.getFilter().getFilterItems().clear();
				mainQuery.getSorter().clear();
				this.tblMain.setFormatXml(resHelper
						.getString("tblMain.formatXml"));
				this.tblMain.putBindContents("mainQuery", new String[] { "id",
						"bookedDate", "period.number", "state", "number",
						"name", "contractType.name", "currency.name",
						"originalAmount", "amount", "signDate",
						"programmingContract.name" });
				mainQueryPK = new MetaDataPK(
						"com.kingdee.eas.fdc.contract.programming.app",
						"RenewRelateProgrammingBillQuery");
				tblMain.checkParsed(true);
			}
			// 设置合约框架F7
			createProgrammingContractF7();
			tblMain.getDataRequestManager().setDataRequestMode(
					KDTDataRequestManager.VIRTUAL_MODE_PAGE);
			super.treeContractType_valueChanged(e);
			setProgNotEdit();
		}
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		FDCHelper.formatTableNumber(getMainTable(), "originalAmount");
		FDCHelper.formatTableNumber(getMainTable(), "amount");
		isModify = false;
		hs.clear();
	}
	private void setProgNotEdit() throws BOSException, SQLException{
		int rowIndexs = tblMain.getRowCount();
		if(rowIndexs > 0){
			Map allId = new HashMap();
			Set tempAllId = new HashSet();
			//update by david_yang R110415-556 2011.04.22
//			FDCSQLBuilder builder = new FDCSQLBuilder();
			for(int i = 0 ; i < rowIndexs ; i ++){
				allId.put(tblMain.getCell(i, "id").getValue().toString(),new Integer(i).toString());
			}
			tempAllId=RenewRelateProgSaveFacadeFactory.getRemoteInstance().getContractbillID(allId.keySet().toArray());
//			builder.clear();
//			builder.appendSql("select con.fid from t_con_contractbill con where 1 = 1 and con.FisRenewRelateProg = 0 and ");
//			builder.appendParam("con.fid", allId.keySet().toArray());
//			builder.appendSql("  and con.fprogrammingcontract in (SELECT prog.fid  FROM T_CON_ProgrammingContract AS prog");
//			builder.appendSql(" inner JOIN T_CON_Programming AS programming ON prog.FProgrammingID = programming.FID");
//			builder.appendSql(" where programming.fstate = '4AUDITTED')");
//			IRowSet rowSet = builder.executeQuery();
//			while(rowSet.next()){
//				tempAllId.add(rowSet.getString("fid").toString());
//			}
			for(Iterator it = tempAllId.iterator(); it.hasNext();){
				tblMain.getCell(Integer.parseInt(allId.get(it.next()).toString()), "programmingContract").getStyleAttributes().setLocked(true);
			}
		}
	}
	private void contractWithoutTextSelect() throws Exception {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("state",
				FDCBillStateEnum.AUDITTED_VALUE));
		/*
		 * 工程项目树
		 */
		if (getProjSelectedTreeNode() != null
				&& getProjSelectedTreeNode().getUserObject() instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) getProjSelectedTreeNode()
					.getUserObject();

			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo) {
				BOSUuid id = ((OrgStructureInfo) projTreeNodeInfo).getUnit()
						.getId();

				Set idSet = FDCClientUtils.genOrgUnitIdSet(id);
				filterItems.add(new FilterItemInfo("orgUnit.id", idSet,
						CompareType.INCLUDE));
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				BOSUuid id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("curProject.id", idSet,
						CompareType.INCLUDE));
			}

		}

		mainQuery.setFilter(filter);
		// getFieldSumList();
		tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.VIRTUAL_MODE_PAGE);
		execQuery();

		if (isHasBillTable()) {
			getBillListTable().removeRows(false);
		}
		getMainTable().repaint();
		tHelper.init();
		tHelper.setQuerySolutionInfo(null);
		refresh(null);
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		if (isModify) {
			int ret = MsgBox.showConfirm3(this, "当前界面已更新，请确认是否保存？");
			if (ret == MsgBox.YES) {
				btnSave.doClick();
			} else if (ret == MsgBox.CANCEL) {
			}
		}
		super.actionRefresh_actionPerformed(e);
		hs.clear();
		isModify = false;
		setProgNotEdit();
	}

	protected void audit(List ids) throws Exception {

	}

	protected SelectorItemCollection genBillQuerySelector() {
		return null;
	}

	protected boolean isHasBillTable() {
		return false;
	}

	protected KDTable getBillListTable() {
		return getMainTable();
	}

	protected void initTable() {
		super.initTable();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.contract.ContractBillFactory
				.getRemoteInstance();
	}

	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractBillEditUI.class
				.getName();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.contract.ContractBillInfo objectValue = new com.kingdee.eas.fdc.contract.ContractBillInfo();
		objectValue.setCurProject((CurProjectInfo) getProjSelectedTreeNode()
				.getUserObject());
		if (getTypeSelectedTreeNode() != null
				&& getTypeSelectedTreeNode().isLeaf()) {
			objectValue
					.setContractType((ContractTypeInfo) getTypeSelectedTreeNode()
							.getUserObject());
		}
		return objectValue;
	}

	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return (ICoreBillBase) ContractBillFactory.getRemoteInstance();
	}

	protected boolean isFootVisible() {
		return true;
	}

	/**
	 * 判断哪些合同的框架合约需要更改
	 */
	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		if (e.getOldValue() != e.getValue()) {
			isModify = true;
			int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			hs.add(String.valueOf(rowIndex));
		}
	}

	/**
	 * 退出时提示
	 */
	protected boolean checkBeforeWindowClosing() {
		boolean isClose = true;
		if (isModify) {
			int ret = MsgBox.showConfirm3(this, "数据已修改，是否保存？");
			if (ret == MsgBox.YES) {
				btnSave.doClick();
			} else if (ret == MsgBox.CANCEL) {
				isClose = false;
			}
		}
		return isClose;
	}

	/**
	 * 返回修改的合同集合
	 * 
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private IObjectCollection beforeSubmit() throws EASBizException,
			BOSException {
		if (isModify == false)
			return null;
		ContractBillCollection contractBillCollection = new ContractBillCollection();
		ContractWithoutTextCollection contractWithoutTextCollection = new ContractWithoutTextCollection();
		Iterator it = hs.iterator();
		while (it.hasNext()) {
			if (getTypeSelectedTreeNode().getUserObject().equals(
					"ContractWithoutText")) {
				ContractWithoutTextInfo contractWithoutTextInfo = new ContractWithoutTextInfo();
				int i = Integer.parseInt((String) it.next());
				if (this.tblMain.getRow(i).getCell("id").getValue() != null) {
					contractWithoutTextInfo.setId(BOSUuid.read(this.tblMain
							.getRow(i).getCell("id").getValue().toString()));
				}
				if (this.tblMain.getRow(i).getCell("programmingContract")
						.getValue() != null) {
					contractWithoutTextInfo
							.setProgrammingContract((ProgrammingContractInfo) this.tblMain
									.getRow(i).getCell("programmingContract")
									.getValue());
				}
				contractWithoutTextCollection.add(contractWithoutTextInfo);
			} else {
				ContractBillInfo contractBillInfo = new ContractBillInfo();
				int i = Integer.parseInt((String) it.next());
				if (this.tblMain.getRow(i).getCell("id").getValue() != null) {
					contractBillInfo.setId(BOSUuid.read(this.tblMain.getRow(i)
							.getCell("id").getValue().toString()));
				}
				if (this.tblMain.getRow(i).getCell("programmingContract")
						.getValue() != null) {
					contractBillInfo
							.setProgrammingContract((ProgrammingContractInfo) this.tblMain
									.getRow(i).getCell("programmingContract")
									.getValue());
				}
				contractBillCollection.add(contractBillInfo);
			}
		}
		if (getTypeSelectedTreeNode().getUserObject().equals(
				"ContractWithoutText")) {
			return contractWithoutTextCollection;
		} else {
			return contractBillCollection;
		}
	}

	/**
	 * 保存
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		IObjectCollection objectCollection = beforeSubmit();
		if (objectCollection == null)
			return;
		RenewRelateProgSaveFacadeFactory.getRemoteInstance().save(
				objectCollection);
		isModify = false;
		hs.clear();
		kdtCostEntries_programmingContract_PromptBox.setRefresh(true);
		FDCMsgBox.showInfo("保存成功！");
		this.refresh(null);
	}

	protected void unAudit(List ids) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 合同类型树是否有无文本合同
	 * 
	 * @return
	 */
	protected boolean containConWithoutTxt() {
		return true;
	}
	protected void setSortForQuery(SorterItemInfo sortItem,
			SorterItemInfo oldSortItem) throws Exception {
		super.setSortForQuery(sortItem, oldSortItem);
		setProgNotEdit();
	}
	/**
	 * 选择工程项目节点和合同类型节点后的选择事件
	 * @return
	 * @throws Exception
	 */
	protected FilterInfo getTreeSelectFilter(Object projectNode,Object  typeNode,boolean containConWithoutTxt) throws Exception {
		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();
		
		/*
		 * 工程项目树
		 */
		if (projectNode != null 	&& projectNode instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			BOSUuid id = null;
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
				
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					id = ((OrgStructureInfo)projTreeNodeInfo).getUnit().getId();	
				}else{
					id = ((FullOrgUnitInfo)projTreeNodeInfo).getId();
				}				
				
				String orgUnitLongNumber = null;
				if(orgUnit!=null && id.toString().equals(orgUnit.getId().toString())){					
					orgUnitLongNumber = orgUnit.getLongNumber();
				}else{
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance()
					.getFullOrgUnitInfo(new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}
				
				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(
						new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%",CompareType.LIKE));

				f.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
				f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs,CompareType.INCLUDE));
				
				f.setMaskString("#0 and #1 and #2");
				
				if(filter!=null){
					filter.mergeFilter(f,"and");
				}
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("curProject.id", idSet,
						CompareType.INCLUDE));
			}

		}

		FilterInfo typefilter =  new FilterInfo();
		FilterItemCollection typefilterItems = typefilter.getFilterItems();	
		/*
		 * 合同类型树
		 */
		if (typeNode != null&& typeNode instanceof TreeBaseInfo) {
			TreeBaseInfo typeTreeNodeInfo = (TreeBaseInfo)typeNode;
			BOSUuid id = typeTreeNodeInfo.getId();
			Set idSet = FDCClientUtils.genContractTypeIdSet(id);
			typefilterItems.add(new FilterItemInfo("contractType.id", idSet,CompareType.INCLUDE));
		}else if(containConWithoutTxt && typeNode != null &&typeNode.equals("allContract")){
			//如果包含无文本合同，查询所有时，让它查不到合同
			typefilterItems.add(new FilterItemInfo("contractType.id", "allContract"));
		}
		
		//三方合同
//		if(!(this instanceof ContractBillListUI)){
//			typefilter.appendFilterItem("isAmtWithoutCost", String.valueOf(0));
//		}
		
		if(filter!=null && typefilter!=null){
			filter.mergeFilter(typefilter,"and");
		}
		
		return filter;
	}
}