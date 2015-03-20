/**
 * output package name
 */
package com.kingdee.eas.custom.fdcrptcz;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.invite.InviteMonthPlanFactory;
import com.kingdee.eas.fdc.invite.InviteMonthPlanInfo;
import com.kingdee.eas.fdc.invite.client.InviteMonthPlanEditUI;
import com.kingdee.eas.fdc.invite.client.InviteMonthPlanListUI;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class checkRptUI2 extends AbstractCopy_checkRptUI {
	private static final Logger logger = CoreUIObject
	.getLogger(InviteMonthPlanListUI.class);
	protected OrgUnitInfo currentOrg = SysContext.getSysContext()
	.getCurrentCostUnit();
	protected static final String CANTEDIT = "cantEdit";
	protected static final String CANTREMOVE = "cantRemove";
	protected Set authorizedOrgs = null;

	public checkRptUI2() throws Exception {
	}

	protected String getEditUIName() {
		return InviteMonthPlanEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return InviteMonthPlanFactory.getRemoteInstance();
	}

	public KDTable getBillListTable() {
		return this.tblMain;
	}

	@Override
	public void initUIMenuBarLayout() {
		// TODO Auto-generated method stub
		//	super.initUIMenuBarLayout();
	}

	@Override
	public void initUIToolBarLayout() {
		// TODO Auto-generated method stub
		//		super.initUIToolBarLayout();
		//		toolBar.add(btnPrint);
		//		toolBar.add(btnPrintPreview);
	}

	protected void initWorkButton() {
		super.initWorkButton();
		//		this.actionAudit.putValue("SmallIcon", FDCClientHelper.ICON_AUDIT);
		//		this.actionUnAudit.putValue("SmallIcon", FDCClientHelper.ICON_UNAUDIT);
		//
		//		this.actionCreateTo.setVisible(false);
		//		this.actionCopyTo.setVisible(false);
		//
		//		this.actionTraceUp.setVisible(false);
		//		this.actionTraceDown.setVisible(false);
		//
		//		this.btnSet.setIcon(EASResource.getIcon("imgTbtn_duizsetting"));

	}

	protected void refresh(ActionEvent e) throws Exception {
		// this.tblMain.removeRows();
	}

	private void checkCreateNewData(DefaultKingdeeTreeNode selectTreeNode)
	throws EASBizException, BOSException {
		// if ((selectTreeNode == null) || (!(selectTreeNode.getUserObject()
		// instanceof CurProjectInfo)) || (!selectTreeNode.isLeaf())) {
		// this.actionAddNew.setEnabled(false);
		// return;
		// }
		//
		// CurProjectInfo project =
		// (CurProjectInfo)selectTreeNode.getUserObject();
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new FilterItemInfo("project.id",
		// project.getId().toString()));
		//
		// if (InviteMonthPlanFactory.getRemoteInstance().exists(filter)) {
		// this.actionAddNew.setEnabled(false);
		// return;
		// }

		//		this.actionAddNew.setEnabled(false);
	}

	protected FilterInfo getTreeSelectFilter(Object projectNode)
	throws Exception {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();

		if ((projectNode != null) && ((projectNode instanceof CoreBaseInfo))) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			BOSUuid id = null;

			if (((projTreeNodeInfo instanceof OrgStructureInfo))
					|| ((projTreeNodeInfo instanceof FullOrgUnitInfo))) {
				if ((projTreeNodeInfo instanceof OrgStructureInfo))
					id = ((OrgStructureInfo) projTreeNodeInfo).getUnit()
					.getId();
				else {
					id = ((FullOrgUnitInfo) projTreeNodeInfo).getId();
				}

				String orgUnitLongNumber = null;
				if ((this.orgUnit != null)
						&& (id.toString().equals(this.orgUnit.getId()
								.toString()))) {
					orgUnitLongNumber = this.orgUnit.getLongNumber();
				} else {
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory
					.getRemoteInstance().getFullOrgUnitInfo(
							new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}

				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(
						new FilterItemInfo("orgUnit.longNumber",
								orgUnitLongNumber + "%", CompareType.LIKE));

				f.getFilterItems().add(
						new FilterItemInfo("orgUnit.isCostOrgUnit",
								Boolean.TRUE));
				f.getFilterItems().add(
						new FilterItemInfo("orgUnit.id", this.authorizedOrgs,
								CompareType.INCLUDE));

				f.setMaskString("#0 and #1 and #2");

				if (filter != null) {
					filter.mergeFilter(f, "and");
				}

			} else if ((projTreeNodeInfo instanceof CurProjectInfo)) {
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("project.id", idSet,
						CompareType.INCLUDE));
			}
		}

		FilterInfo typefilter = new FilterInfo();
		FilterItemCollection typefilterItems = typefilter.getFilterItems();

		if ((filter != null) && (typefilter != null)) {
			filter.mergeFilter(typefilter, "and");
		}

		return filter;
	}

	// �������ڵ�ѡ���¼�
	protected void treeSelectChange() throws Exception {
		DefaultKingdeeTreeNode projectNode = (DefaultKingdeeTreeNode) this.treeOrg
		.getLastSelectedPathComponent();

		Object project = null;
		if (projectNode != null) {
			project = projectNode.getUserObject();
		}

		if ((project instanceof CurProjectInfo)) {
			String strProjectId = ((CurProjectInfo) project).getId().toString();
			fillTable(strProjectId);
		} else {
			// MsgBox.showInfo("��ѡ��������Ŀ����!");
			// abort();
			cleanTable();
		}

	}

	protected void treeOrg_valueChanged(TreeSelectionEvent e) throws Exception {
		//		if (checkCanOperate()) {
		//			this.actionAddNew.setEnabled(true);
		//			this.actionEdit.setEnabled(true);
		//			this.actionRemove.setEnabled(true);
		//			this.actionAudit.setEnabled(true);
		//			this.actionUnAudit.setEnabled(true);
		//		} else {
		//			this.actionAddNew.setEnabled(false);
		//			this.actionEdit.setEnabled(false);
		//			this.actionRemove.setEnabled(false);
		//			this.actionAudit.setEnabled(false);
		//			this.actionUnAudit.setEnabled(false);
		//		}
		treeSelectChange();
		checkCreateNewData((DefaultKingdeeTreeNode) this.treeOrg
				.getLastSelectedPathComponent());
	}

	protected FilterInfo getOrgFilter() throws Exception {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode orgNode = getSelectedTreeNode(this.treeOrg);
		if ((orgNode != null)
				&& ((orgNode.getUserObject() instanceof OrgStructureInfo))) {
			OrgStructureInfo org = (OrgStructureInfo) orgNode.getUserObject();
			String orgId = org.getUnit().getId().toString();
			filter.getFilterItems()
			.add(new FilterItemInfo("orgUnit.id", orgId));
		}
		return filter;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		// FilterInfo treeFilter = viewInfo.getFilter();
		// FilterInfo filter = null;
		// try {
		// filter = getOrgFilter();
		// if (getDialog() != null) {
		// FilterInfo commFilter = getDialog().getCommonFilter();
		// if ((filter != null) && (commFilter != null))
		// filter.mergeFilter(commFilter, "and");
		// } else {
		// QuerySolutionInfo querySolution = getQuerySolutionInfo();
		// if ((querySolution != null) &&
		// (querySolution.getEntityViewInfo() != null)) {
		// EntityViewInfo ev = Util.getInnerFilterInfo(querySolution);
		// if (ev.getFilter() != null)
		// filter.mergeFilter(ev.getFilter(), "and");
		// }
		// }
		// }
		// catch (BOSException e) {
		// e.printStackTrace();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// try
		// {
		// treeFilter.mergeFilter(filter, "and");
		// } catch (BOSException e) {
		// e.printStackTrace();
		// }
		// viewInfo.setFilter(treeFilter);
		// if ((viewInfo.getSorter() != null) && (viewInfo.getSorter().size() <
		// 2)) {
		// viewInfo.getSorter().clear();
		// viewInfo.getSorter().add(new SorterItemInfo("project.number"));
		// viewInfo.getSorter().add(new SorterItemInfo("isLatest"));
		// SorterItemInfo version = new SorterItemInfo("version");
		// version.setSortType(SortType.DESCEND);
		// viewInfo.getSorter().add(version);
		// }
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree
			.getLastSelectedPathComponent();
			return treeNode;
		}
		return null;
	}

	protected boolean checkCanOperate() {
		boolean flag = false;
		DefaultKingdeeTreeNode orgNode = getSelectedTreeNode(this.treeOrg);
		if (orgNode == null) {
			flag = false;
		}

		if (this.authorizedOrgs.contains(this.currentOrg.getId().toString())) {
			flag = true;
		}
		return flag;
	}

	public void onLoad() throws Exception {
		
//		kDSplitPane2.setFocusable(false);
//		kDSplitPane2.setLastDividerLocation(100);
//		kDSplitPane2.setDividerLocation(100);
		
		
		if ((this.currentOrg == null)
				|| (!this.currentOrg.isIsCompanyOrgUnit())) {
			MsgBox.showInfo("�ǲ�����֯���ܽ���!");
			abort();
		}

//		try {
			super.onLoad();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		
		kDDatePicker1.setValue(null);
		kDDatePicker2.setValue(null);
//		Calendar cal = Calendar.getInstance(); 
//		SimpleDateFormat datef=new SimpleDateFormat("yyyy-MM-dd");
//		//��ǰ�µ����һ��   
//        cal.set( Calendar.DATE, 1 );
//        cal.roll(Calendar.DATE, - 1 );
//        kDDatePicker2.setValue(cal.getTime());
//         //��ǰ�µĵ�һ��          
//        cal.set(GregorianCalendar.DAY_OF_MONTH, 1); 
//        kDDatePicker1.setValue(cal.getTime());

		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		buildOrgTree();
		DefaultKingdeeTreeNode orgRoot = (DefaultKingdeeTreeNode) this.treeOrg
		.getModel().getRoot();
		DefaultKingdeeTreeNode node = findNode(orgRoot, this.currentOrg.getId()
				.toString());
		removeAllBrotherNode(node);
		this.treeOrg.setSelectionNode(node);
		this.treeOrg.expandAllNodes(true, orgRoot);

		this.tHelper = new TablePreferencesHelper(this);

		//
		initTable();

	}

	public void removeAllBrotherNode(DefaultKingdeeTreeNode node) {
		DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode) node
		.getParent();
		if (parent == null) {
			return;
		}
		this.treeOrg.removeAllChildrenFromParent(parent);
		this.treeOrg.addNodeInto(node, parent);
		removeAllBrotherNode(parent);
	}

	protected void setActionState() {
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put("ID", getSelectedKeyValue());
		DefaultKingdeeTreeNode orgNode = getSelectedTreeNode(this.treeOrg);

		if ((orgNode != null) && (orgNode.getUserObject() != null)) {
			if ((orgNode.getUserObject() instanceof OrgStructureInfo))
				uiContext.put("org", ((OrgStructureInfo) orgNode
						.getUserObject()).getUnit());
			else if ((orgNode.getUserObject() instanceof CurProjectInfo)) {
				uiContext.put("curProject", (CurProjectInfo) orgNode
						.getUserObject());
			}
		} else
			uiContext.put("org", null);
	}

	public void buildOrgTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, this.treeOrg, this.actionOnLoad);

		this.authorizedOrgs = ((Set) ActionCache
				.get("FDCBillListUIHandler.authorizedOrgs"));
		if (this.authorizedOrgs == null) {
			this.authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					new ObjectUuidPK(SysContext.getSysContext()
							.getCurrentUserInfo().getId()), OrgType.CostCenter,
							null, null, null);
			if (orgs != null) {
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while (it.hasNext())
					this.authorizedOrgs.add(it.next());
			}
		}
	}

	private DefaultKingdeeTreeNode findNode(DefaultKingdeeTreeNode node,
			String id) {
		if ((node.getUserObject() instanceof CurProjectInfo)) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			if (projectInfo.getId().toString().equals(id))
				return node;
		} else if ((node.getUserObject() instanceof OrgStructureInfo)) {
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

	public boolean isIgnoreRowCount() {
		return false;
	}

	protected void checkBeforeEditOrRemove(String warning, String id)
	throws EASBizException, BOSException, Exception {
		FDCClientUtils.checkBillInWorkflow(this, id);

		SelectorItemCollection sels = super.getSelectors();
		sels.add("state");

		FDCBillInfo info = (FDCBillInfo) getBizInterface().getValue(
				new ObjectUuidPK(id), sels);

		FDCBillStateEnum state = info.getState();

		if ((state != null)
				&& ((state == FDCBillStateEnum.AUDITTING) || (state == FDCBillStateEnum.AUDITTED)))
			if (warning.equals("cantEdit")) {
				FDCMsgBox.showWarning("���ݲ��Ǳ�������ύ״̬�����ܽ����޸Ĳ�����");
				SysUtil.abort();
			} else {
				FDCMsgBox.showWarning("���ݲ��Ǳ�������ύ״̬�����ܽ���ɾ��������");
				SysUtil.abort();
			}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = getSelectedKeyValue();
		checkBeforeEditOrRemove("cantEdit", id);

		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for (int i = 0; i < id.size(); i++) {
			checkBeforeEditOrRemove("cantRemove", id.get(i).toString());
		}
		super.actionRemove_actionPerformed(e);
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for (int i = 0; i < id.size(); i++) {
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());

			if (!FDCBillStateEnum.SUBMITTED.equals(getBizState(id.get(i)
					.toString()))) {
				FDCMsgBox.showWarning("���ݲ����ύ״̬�����ܽ�������������");
				return;
			}
			((IFDCBill) getBizInterface()).audit(BOSUuid.read(id.get(i)
					.toString()));
		}
		FDCClientUtils.showOprtOK(this);
		refresh(null);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for (int i = 0; i < id.size(); i++) {
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
			if (!FDCBillStateEnum.AUDITTED.equals(getBizState(id.get(i)
					.toString()))) {
				FDCMsgBox.showWarning("���ݲ�������״̬�����ܽ��з�����������");
				return;
			}
			((IFDCBill) getBizInterface()).unAudit(BOSUuid.read(id.get(i)
					.toString()));
		}
		FDCClientUtils.showOprtOK(this);
		refresh(null);
	}

	public FDCBillStateEnum getBizState(String id) throws EASBizException,
	BOSException, Exception {
		if (id == null)
			return null;
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("state");
		return ((FDCBillInfo) getBizInterface().getValue(new ObjectUuidPK(id),
				sels)).getState();
	}

	public void actionSet_actionPerformed(ActionEvent e) throws Exception {
		InviteMonthPlanInfo info = getSelectedInfo();
		checkAudited(info);
		checkLastVersion(info);
		checkHasNextMonth(info);
		UIContext uiContext = new UIContext(this);
		uiContext.put("info", info);
		uiContext.put("set", Boolean.TRUE);
		IUIWindow ui = UIFactory.createUIFactory(getEditUIModal()).create(
				getEditUIName(), uiContext, null, OprtState.ADDNEW);
		ui.show();
	}

	private void checkAudited(InviteMonthPlanInfo info) throws BOSException,
	EASBizException {
		if (!FDCUtils.isBillAudited(info)) {
			MsgBox.showWarning(this, "���������ݲ����޶���");
			SysUtil.abort();
		}
	}

	private void checkLastVersion(InviteMonthPlanInfo info)
	throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("project.id", info.getProject().getId()
						.toString()));

		filter.getFilterItems().add(
				new FilterItemInfo("version", info.getVersion(),
						CompareType.GREATER));
		if (InviteMonthPlanFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showWarning(this, "�����°汾�����޶���");
			SysUtil.abort();
		}
	}

	private void checkHasNextMonth(InviteMonthPlanInfo info)
	throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("project.id", info.getProject().getId()
						.toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("planYear", Integer.valueOf(info
						.getPlanYear()), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("planMonth", Integer.valueOf(info
						.getPlanMonth()), CompareType.GREATER));
		if (InviteMonthPlanFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showWarning(this, "�Ѵ������·ݼƻ������޶���");
			SysUtil.abort();
		}
	}

	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) this.treeOrg
		.getLastSelectedPathComponent();
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());

		super.actionAddNew_actionPerformed(e);
	}

	private InviteMonthPlanInfo getSelectedInfo() throws BOSException,
	EASBizException {
		checkSelected();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("orgUnit.*");
		sel.add("CU.*");
		sel.add("programming.*");
		sel.add("measureStage.*");
		sel.add("creator.*");
		sel.add("auditor.*");
		sel.add("project.*");
		sel.add("entry.*");
		sel.add("entry.requiredInviteForm.*");
		return InviteMonthPlanFactory.getRemoteInstance()
		.getInviteMonthPlanInfo(
				new ObjectUuidPK(getSelectedKeyValue()), sel);
	}

	protected void cleanTable() {
		this.tblCheck.removeRows(false);
		this.tblCheck.checkParsed();
	}

	protected void fillTable(String strProjectId) throws BOSException,
	SQLException {

		cleanTable();
		String strBeginDate = "";
		String strEndDate = "";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		if(kDDatePicker1.getValue() != null){
			java.util.Date date1= (Date) kDDatePicker1.getValue(); 
			strBeginDate = sdf.format(date1);
		}
		if(kDDatePicker2.getValue() != null){
			java.util.Date date2= (Date) kDDatePicker2.getValue(); 
			strEndDate = sdf.format(date2);
		}
		
		

		String strSQL = "/*dialect*/ select * from ( select distinct "
			+ " t11.faudittime as contractAudittime," //��ͬ��������
			+ " t13.fname_l2 as oldFormName, "// -- �ƶ�Ҫ��ɹ���ʽ
			+ " t12.fname_l2 as formName, "// --ʵ�ʲɹ���ʽ		
			+ " (case when t12.fname_l2 = '�б�' then 1 else 0 end) as yiwanchengcaigou,"  // ����ɲɹ����б�ĺ�ͬ������ ���ա����˱��С�ʵ�ʲɹ���ʽ���ֶ�ȡ���߼��������ж���������ʵ�ʲɹ���ʽΪ�б�ĸ�����
			+ " (case when t13.fname_l2 = '�б�' and t12.fname_l2 = '�б�' then 1 else 0 end) as zhiduyaoqiushijicaigouyaoqiu," 
			// �ƶ�Ҫ���б���ʵ�ʲ����б�ĺ�ͬ���������ա����˱��С��ƶ�Ҫ��Ĳɹ���ʽ������ʵ�ʲɹ���ʽ��ȡ���߼����ж������������ƶ�Ҫ��ɹ���ʽ��Ϊ���бꡱ�����ҡ�ʵ�ʲɹ���ʽ��ҲΪ���бꡱ�Ĳɹ��ƻ�������
			+ " (case when t13.fname_l2 = '�б�' then 1 else 0 end) as zhiduyaoqiu,"
			// �ƶ�Ҫ���б�ĺ�ͬ���������ա����˱��С��ƶ�Ҫ��Ĳɹ���ʽ��ȡ���߼����ж������������ƶ�Ҫ��ɹ���ʽ��Ϊ���бꡱ�ĸ�����

			+ " t6.fname_l2 as modeName , "// --ʵ�ʲɹ�ģʽ	
			+ " t7.fState as AcceptState," // --�б�����״̬
			+ " t11.fid as contractId," // --��ͬid
			+ " (case when t11.fState = '4AUDITTED' then nvl(t11.FOriginalAmount,0) else 0 end ) as FOriginalAmount, "
			//+ " nvl(t11.FOriginalAmount,0) as FOriginalAmount,"// -- ��ͬ��λ�ҽ�� --> ����ɲɹ��ĺ�ͬ���
			+ " t11.fState as contractState, "// -- ��ͬ״̬
			+ " nvl(t2.FIndicator,0) as FIndicator,"       // -- Ŀ��ɱ�����ָ��
			+ " t1.fversion,"
			+ " t2.fid,"
			+ " t2.flevel,"
			+ " t2.flongnumber,"
			+ " (case when   " +
			" t16.FAuditTime is not null " +
			" and  t2.FRESULTAUDITDATE is not null " +
			//	" and t16.FAuditTime <= t2.FRESULTAUDITDATE " +  ���� �� ���� 7�췶Χ�� Ҳ�㰴ʱ���
			" and to_date(to_char(t16.FAuditTime,'yyyy-MM-dd'),'yyyy-MM-dd') - to_date(to_char(t2.FRESULTAUDITDATE,'yyyy-MM-dd'),'yyyy-MM-dd') <= 7 " +
			" and t11.fState = '4AUDITTED' " +
			" then 1 else " +
			//"0" +
			// 14��	���ɹ��ƻ���:ʵ�ʲɹ���ʽΪ��ֱ��ί�С���.��ʵ���б�����������Ҫȡ ��ֱ��ί���������е���������,�Ҷ��������ͼ��б�ĺ�ͬ���� 20130618
			" (case when  t16.FAuditTime is  null and   t17.faudittime is not null and  t2.FRESULTAUDITDATE is not null and  to_date(to_char(t17.FAuditTime,'yyyy-MM-dd'),'yyyy-MM-dd') - to_date(to_char(t2.FRESULTAUDITDATE,'yyyy-MM-dd'),'yyyy-MM-dd') <= 7  and t11.fState = '4AUDITTED' and   t17.fstate = '4AUDITTED' then 1 else 0 end  )" +
			" end )  as timeCount," //��ʱ��ɵ�����   to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') - to_date('2013-05-29','yyyy-MM-dd')
			+ " t2.fname,"
			+" t17.fstate as t17State,"
			+ " nvl(t2.famount,0) as famount "

			// ��Ӧ�����
			+" ,t8.fid as t8id" +
			" , t8.FSupplierID" +
			" , (select instr(wm_concat(fid),t9.fid,1,1)-1 from T_FDC_SupplierStock where FSysSupplierID = t11.fpartbid) as isSameSuplyId "+
			" ,t9.fname_l2 as winBuildUnit" +  // �б굥λ
			" ,t8.FIsLowest" + //�Ƿ���ͼ��б�
			" ,t8.fhit"  //�Ƿ��б� 
			//				+ ",  (" +
			//						" case when to_char(add_months(t14.FAuditTime,12),'yyyy') <= to_char(sysdate,'yyyy')"
			//				+ "   and t14.FState = '4AUDITTED'"
			//				+ "   and (t15.fname_l2 like '��Լ������' or t15.fname_l2 like '��Լ��������')"
			//				+ "   then 0 else 1 end" +
			//						")  as winBuildCount " // --������Ӧ���б�����
			+", ( select (case when count(*)> 0 then 0 else (" +
					"	case when t8.fhit = 1   " +
					"         and  t6.fname_l2 not like '%��Ŀ���вɹ�%'  " +
					"         and  t6.fname_l2 not like '%����ս�Բɹ�%'  " +
					"         and  t6.fname_l2 not like '%���й�˾���вɹ�%'   then 1 else 0 end) end) as v1  from T_GYS_SupplierReviewGather a1 " 
			+" left join T_GYS_SupplierEvaluationType a2 on a1.FEvaluationTypeId = a2.fid   "
			+" where a1.FSupplierId =  t8.FSupplierID  "
			+" and to_char(add_months(a1.FAuditTime,12),'yyyy') <= to_char(sysdate,'yyyy') " 
			+" and a1.FState = '4AUDITTED' "
			+" and (a2.fname_l2 like '��Լ������' or a2.fname_l2 like '��Լ��������') " 
			+" )   winBuildCount "   // --������Ӧ���б�����


			//+ " , (case when nvl(t2.FIndicator,0) >=  nvl(t8.FCOSTOFCONTRUCTION,0) and t11.fState = '4AUDITTED' then 1 else 0 end) as targetCostCount "+ //Ŀ��ɱ����۴�ɵ�����
			// 20130625  ʵ�ʲɹ���ʽΪ��ֱ��ί�С���������ɲɹ��ĵ�����ۡ�ȡ��ֱ��ί���������ж�Ӧ���ݣ���Ŀ��ɱ�������������ͳ���߼�һ��
			+ " ,  (case when  t12.fname_l2 = 'ֱ��ί��' and t11.fState = '4AUDITTED' and  nvl(t2.FIndicator,0) >=  nvl(t18.FpurchaseDoneAmountEx,0)  then  1 else     " +
					"  (case when   nvl(t2.FIndicator,0) >=  nvl(t8.FCOSTOFCONTRUCTION,0) and t11.fState = '4AUDITTED' then 1 else 0 end ) end) " +
					" as targetCostCount "+ //Ŀ��ɱ����۴�ɵ�����
		
			//" ,nvl(t8.FCOSTOFCONTRUCTION,0) as FCOSTOFCONTRUCTION " //����ɲɹ��ĵ������
			// 20130625  ʵ�ʲɹ���ʽΪ��ֱ��ί�С���������ɲɹ��ĵ�����ۡ�ȡ��ֱ��ί���������ж�Ӧ���ݣ���Ŀ��ɱ�������������ͳ���߼�һ��
			" ,  (case when  t12.fname_l2 = 'ֱ��ί��' then   nvl(t18.FpurchaseDoneAmountEx,0)   else  nvl(t8.FCOSTOFCONTRUCTION,0)  end) as FCOSTOFCONTRUCTION " //����ɲɹ��ĵ������
			// ��Ӧ�����

			+ " from T_INV_InviteMonthPlan t1 "
			+ // �ɹ��ƻ�
			" left join T_INV_InviteMonthPlanEntrys t2 on t1.fid = t2.fparentid "
			+ // �ɹ��ƻ���¼
			" left join T_FDC_CurProject t3 on t1.FProjectID = t3.fid  "
			+ // ������Ŀ
			" left join T_INV_InviteProjectEntries t4 on t4.FPROGRAMMINGCONTRACTID = t2.FProgrammingContractID  "
			+ // �б������¼
			" left join T_INV_InviteProject t5 on t5.fid = t4.fparentid  "
			
			+" left join  T_INV_DirectAccepterResult  t17 on t5.fid = t17.finviteprojectid "  // ���� ֱ��ί������ 20130618
			
			+" left join T_INV_DirectAccepterResultE t18 on t18.fparentid = t17.fid  "  // ���� ֱ��ί��������¼  20130625
			
			+ // �б�����
			" left join T_INV_InvitePurchaseMode t6  on t5.FInvitePurchaseModeID = t6.fid  "

			+ // ��Լ�滮
			" left join T_CON_ProgrammingContract t10 on t10.fid = t2.FProgrammingContractID  "
			+
			" left join T_CON_ContractBill t11 on t11.fid = t10.fbillid  "
			+ // ��ͬ
			" left join T_INV_InviteForm t12 on t12.fid = t5.FInviteFormId "
			+ // �ɹ���ʽ
			" left join T_INV_InviteForm t13 on t13.fid = t2.FREQUIREDINVITEFORMID "				
			+ "left join T_INV_TenderAccepterResult t16 on t16.FInviteProjectID = t5.fid "

			//--��Ӧ��
			+ "left join  T_INV_TenderAccepterResult t7 on t7.FInviteProjectID = t5.fid  "
			+ "left join  T_INV_TenderAccepterResultE t8 on t8.FParentID = t7.fid  "
			+ "left join  T_FDC_SupplierStock t9 on t9.fid = t8.FSupplierID  "
			+ "left join T_GYS_SupplierReviewGather t14 on t14.FSupplierId =  t8.FSupplierID "
			+ "left join T_GYS_SupplierEvaluationType t15 on t14.FEvaluationTypeId = t15.fid "
			//--��Ӧ��


			+ " where t3.fid = '"
			+ strProjectId
			+ "'"
			+ " and (t6.fname_l2 is null or (t6.fname_l2 not like '%����ս�Բɹ�%' and  t6.fname_l2 not like '%���ż��вɹ�%')) "
			+ // ͳ�����ݲ������ɹ�ģʽΪ������ս��/���вɹ���������
			" and t1.fversion = (select max(fversion) from T_INV_InviteMonthPlan where FProjectID =  '"
			+ strProjectId + "')" + // ���µİ汾
			" and (t8.FHit = 1 or t8.FHit is null) " + // �б굥λ �� �б굥λΪ��
			" and (t11.fState = '4AUDITTED' or  t11.fState is null) ";  // ��ͬ״̬Ϊ��������Ϊ��
		
			strSQL += "and (( 1 =1";
			
		    if(!strBeginDate.equals("")){
		    	strSQL += " and  t11.faudittime >= to_date('"+strBeginDate+" 00:00:00','yyyy-MM-dd hh24:mi:ss') " ;
		    }
		    if(!strEndDate.equals("")){
		    	strSQL += " and  t11.faudittime <=to_date('"+strEndDate+" 23:59:59','yyyy-MM-dd hh24:mi:ss') "; // ����ͬ�������ڹ���
		    }
		    strSQL += ") or t11.faudittime is null)";
		    strSQL +=
			//" and (t2.flongnumber = '01.03.04.01' )" +
			" ) " +
		//	"  where formname = 'ֱ��ί��' " +  // test ...........
			" order by  flongnumber , isSameSuplyId desc ";
		//" where  isSameSuplyId = 1 or isSameSuplyId is null"; // ͨ����ͬ�еļ׷������� �ɹ��б��б�Ĺ�Ӧ��.ȥ�����еļ�¼


		System.out
		.println("=====================================================");
		System.out.println(strSQL);
		System.out
		.println("=====================================================");
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.clear();
		builder.appendSql(strSQL);
		IRowSet rs = builder.executeQuery();

		int maxLevel = 0;
		int[] levelArray = new int[rs.size()];
		int i = 0;
		String strPreLongNum = "";
		while (rs.next()) {

			// ���������ظ���,����ǰ  LongNum = strPreLongNum, "-1".equals(strIsSameSuplyId) ��������.  ��� ����ɹ� �����Ӧ���б�����
			String strContractState = rs.getString("contractState");
			if(rs.getString("flongnumber")==null)continue;
			String strCurLongNum = rs.getString("flongnumber").toString();
			String strIsSameSuplyId =rs.getString("isSameSuplyId") == null?"":rs.getString("isSameSuplyId").toString();
			if(strPreLongNum.equals(strCurLongNum) ){
				//				��ǰ  LongNum = strPreLongNum,��������.������������
				System.out.println("---------------------"+strIsSameSuplyId);
				continue;
			}
			else{

				IRow row = this.tblCheck.addRow();
				row.getCell("level").setValue(rs.getString("flevel"));
				row.getCell("longNumber").setValue(rs.getString("flongnumber"));
				strPreLongNum = rs.getString("flongnumber").toString();
				row.getCell("name").setValue(rs.getString("fname"));
				row.getCell("amount").setValue(rs.getBigDecimal("famount"));
				
				
				String strIsZhiJieWeiTuo =  rs.getString("formName");
				
				//������,����ͬ״̬��Ϊ ���״̬,ȫ������ֵ,
				if("4AUDITTED".equals(strContractState)){


					row.getCell("planForm").setValue(rs.getString("oldFormName"));

					//			if(rs.getString("oldFormName") == null || rs.getString("oldFormName").equals("")){//���б귽ʽ��Ϊ Ҷ�ӽڵ�. ��Ҷ�ӽڵ���ɫΪ ��ɫ
					//				
					//			}

					row.getCell("actualForm").setValue(rs.getString("formName"));
					// 14��	���ɹ��ƻ���:ʵ�ʲɹ���ʽΪ��ֱ��ί�С���������,���������ͼ��б�ĺ�ͬ����  20130618 
				
					if(strIsZhiJieWeiTuo != null && "ֱ��ί��".equals(strIsZhiJieWeiTuo)){
						System.out.println(strIsZhiJieWeiTuo);
					}
					String strAuditState =  rs.getString("t17State");
					if (strIsZhiJieWeiTuo != null && "ֱ��ί��".equals(strIsZhiJieWeiTuo) && "4AUDITTED".equals(strAuditState)) {
						row.getCell("minConCount").setValue(1);
					} 
					
					//20130619  ���������ܱ� - ������Ӧ�̱���  - ����ɲɹ����б�ĺ�ͬ������
					// ���������ܱ� - �б���  - �ƶ�Ҫ���б���ʵ�ʲ����б�ĺ�ͬ����
					// ���������ܱ� - �б���  - �ƶ�Ҫ���б�ĺ�ͬ����
					row.getCell("column17").setValue(rs.getBigDecimal("yiwanchengcaigou"));
					row.getCell("column18").setValue(rs.getBigDecimal("zhiduyaoqiushijicaigouyaoqiu"));
					row.getCell("column19").setValue(rs.getBigDecimal("zhiduyaoqiu"));


					row.getCell("totalCon").setValue(0);
					row.getCell("minConCount").setValue(0);

					row.getCell("totalConAmt").setValue(FDCHelper.ZERO);
					row.getCell("costAmt").setValue(FDCHelper.ZERO);

					row.getCell("timeCount").setValue(rs.getInt("timeCount"));


					//�б굥λͨ��������� ���ж��,��Ҫ�ظ�����					
					row.getCell("winBuildUnit").setValue(rs.getString("winBuildUnit"));
					row.getCell("winBuildCount").setValue(rs.getInt("winBuildCount"));

					String strAcceptState = rs.getString("AcceptState");
					if (strAcceptState != null && strAcceptState.equals("4AUDITTED")) {
						// �б�����������
						//����ɲɹ��ĵ������(costAmt)
						row.getCell("costAmt").setValue(rs.getBigDecimal("FCOSTOFCONTRUCTION"));
					}
					
					// ʵ�ʲɹ���ʽΪ��ֱ��ί�С���������ɲɹ��ĵ�����ۡ�ȡ��ֱ��ί���������ж�Ӧ���ݣ���Ŀ��ɱ�������������ͳ���߼�һ��
//						String strIsZhiJieWeiTuo =  rs.getString("formName");
					if(strIsZhiJieWeiTuo != null && "ֱ��ί��".equals(strIsZhiJieWeiTuo)){	//����ɲɹ��ĵ������(costAmt)
						row.getCell("costAmt").setValue(rs.getBigDecimal("FCOSTOFCONTRUCTION"));
					}

					row.getCell("targetCostCount").setValue(rs.getInt("targetCostCount")); // Ŀ��ɱ����۴�ɵ�����


					if (strContractState != null && strContractState.equals("4AUDITTED")) {
						// ��ͬ������

						//				 ����ɲɹ��ĺ�ͬ���(totalConAmt): 
						row.getCell("totalConAmt").setValue(rs.getBigDecimal("FOriginalAmount"));

						// 2�� ����ɲɹ��ĺ�ͬ�������� ��������ɲɹ��ĺ�ͬ�������ݣ����ֶ�ϵͳ�Զ�ͳ������
						if (rs.getString("FOriginalAmount") != null	&& !rs.getString("FOriginalAmount").equals("")) {
							row.getCell("totalCon").setValue(1);
						}

						// ������ͼ��б�ĺ�ͬ��(minConCount): ���б������������������й�ѡ����ͼ��бꡮ���Һ�ͬ��������ϵͳ�Զ�����
						String strIsLowest = rs.getString("FISLOWEST");
						if (strIsLowest != null && strIsLowest.equals("1")) {
							row.getCell("minConCount").setValue(1);
						}
						
					} 
				}
				
				
				// ���  isSameSuplyId == -1  ˵�� ��ͬ�еĹ�Ӧ�� �� �б������еĹ�Ӧ�̶�Ӧ����. ����и� ��Ӧ����ص�����ȫ�����
				if("-1".equals(strIsSameSuplyId)){
					row.getCell("winBuildUnit").setValue(null);
					row.getCell("winBuildCount").setValue(null);
					
					if(!"ֱ��ί��".equals(strIsZhiJieWeiTuo)){ // ʵ�ʲɹ���ʽΪ��ֱ��ί�С���������ɲɹ��ĵ�����ۡ�ȡ��ֱ��ί���������ж�Ӧ���ݣ���Ŀ��ɱ�������������ͳ���߼�һ��
                        // �Ƿ�Ҫ����  ��Ӧ�̶�Ӧ������?????
						// Ҫ����. 20130626 �¾�
//						row.getCell("costAmt").setValue(null);
//						row.getCell("targetCostCount").setValue(null);
					}
					row.getCell("costAmt").setValue(null);
					row.getCell("targetCostCount").setValue(null);
					
					
					row.getCell("minConCount").setValue(null);
//					row.getCell("column17").setValue(null);
//					row.getCell("column18").setValue(null);
//					row.getCell("column19").setValue(null);
				}
				
				// ���  isSameSuplyId == "" 	�� ��Ӧ��id��Ϊ��  ˵�� ��ͬ�еĹ�Ӧ�� �� �б������еĹ�Ӧ�̶�Ӧ����. ����и� ��Ӧ����ص�����ȫ�����
				if("".equals(strIsSameSuplyId) && !"".equals(rs.getString("FSupplierID"))){
					row.getCell("winBuildUnit").setValue(null);
					row.getCell("winBuildCount").setValue(null);
					
					if(!"ֱ��ί��".equals(strIsZhiJieWeiTuo)){  // ʵ�ʲɹ���ʽΪ��ֱ��ί�С���������ɲɹ��ĵ�����ۡ�ȡ��ֱ��ί���������ж�Ӧ���ݣ���Ŀ��ɱ�������������ͳ���߼�һ��
                        // �Ƿ�Ҫ����  ��Ӧ�̶�Ӧ������?????
						// Ҫ����. 20130626 �¾�
//						row.getCell("costAmt").setValue(null);
//						row.getCell("targetCostCount").setValue(null);
					}
					row.getCell("costAmt").setValue(null);
					row.getCell("targetCostCount").setValue(null);
					
					row.getCell("minConCount").setValue(null);
//					row.getCell("column17").setValue(null);
//					row.getCell("column18").setValue(null);
//					row.getCell("column19").setValue(null);
				}
				
				int level = Integer.parseInt(row.getCell("level").getValue()
						.toString());
				levelArray[i++] = level;
				row.setTreeLevel(level - 1);
			}	
		}

		for (int j = 0; j < i; j++) {
			maxLevel = Math.max(levelArray[j], maxLevel);
		}


		this.tblCheck.getTreeColumn().setDepth(maxLevel); 

		//		
		//		row.getStyleAttributes().setBackground(new Color(0xEEEEEE));

		for(int m=1; m<tblCheck.getRowCount(); m++){
			IRow row = tblCheck.getRow(m);
			IRow rowPre = tblCheck.getRow(m-1);

			if(rowPre.getCell("longNumber") != null && rowPre.getCell("longNumber").getValue() != null 
					&& row.getCell("longNumber") != null && row.getCell("longNumber").getValue() != null){
				String longNumPre = rowPre.getCell("longNumber").getValue().toString();
				String longNum = row.getCell("longNumber").getValue().toString();
				if(longNum.indexOf(longNumPre) == 0){
					//					row.getStyleAttributes().setBackground(new Color(0xEEEEEE));
				}
			}


		}


		//�ں�
		//		MergeTbl();





		sumTotal(tblCheck);
		
	}

	protected void initTable() {
		createTbl();
		this.tblCheck.checkParsed();
	}

	protected void setTableTree() {
		int maxLevel = 0;
		int[] levelArray = new int[this.tblCheck.getRowCount()];
		for (int i = 0; i < this.tblCheck.getRowCount(); i++) {
			IRow row = this.tblCheck.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue()
					.toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);


		}
		for (int i = 0; i < this.tblCheck.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
		this.tblCheck.getTreeColumn().setDepth(maxLevel);
	}

	KDTable tblCheck = new KDTable();

	protected void createTbl() {

		//String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"level\" t:width=\"2\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"longNumber\" t:width=\"130\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"totalCon\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"winBuildUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"winBuildCount\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"totalConAmt\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"costAmt\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"targetCostCount\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"planForm\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"actualForm\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"minConCount\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"timeCount\" t:width=\"103\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"id\" t:width=\"2\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"isMerg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{longNumber}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{totalCon}</t:Cell><t:Cell>$Resource{winBuildUnit}</t:Cell><t:Cell>$Resource{winBuildCount}</t:Cell><t:Cell>$Resource{totalConAmt}</t:Cell><t:Cell>$Resource{costAmt}</t:Cell><t:Cell>$Resource{targetCostCount}</t:Cell><t:Cell>$Resource{planForm}</t:Cell><t:Cell>$Resource{actualForm}</t:Cell><t:Cell>$Resource{minConCount}</t:Cell><t:Cell>$Resource{timeCount}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{isMerg}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{level_Row2}</t:Cell><t:Cell>$Resource{longNumber_Row2}</t:Cell><t:Cell>$Resource{name_Row2}</t:Cell><t:Cell>$Resource{amount_Row2}</t:Cell><t:Cell>$Resource{totalCon_Row2}</t:Cell><t:Cell>$Resource{winBuildUnit_Row2}</t:Cell><t:Cell>$Resource{winBuildCount_Row2}</t:Cell><t:Cell>$Resource{totalConAmt_Row2}</t:Cell><t:Cell>$Resource{costAmt_Row2}</t:Cell><t:Cell>$Resource{targetCostCount_Row2}</t:Cell><t:Cell>$Resource{planForm_Row2}</t:Cell><t:Cell>$Resource{actualForm_Row2}</t:Cell><t:Cell>$Resource{minConCount_Row2}</t:Cell><t:Cell>$Resource{timeCount_Row2}</t:Cell><t:Cell>$Resource{id_Row2}</t:Cell><t:Cell>$Resource{isMerg_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"1\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"1\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"1\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"1\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"0\" t:right=\"6\" /><t:Block t:top=\"0\" t:left=\"7\" t:bottom=\"0\" t:right=\"9\" /><t:Block t:top=\"0\" t:left=\"10\" t:bottom=\"0\" t:right=\"11\" /><t:Block t:top=\"0\" t:left=\"12\" t:bottom=\"1\" t:right=\"12\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"level\" t:width=\"2\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"longNumber\" t:width=\"130\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"totalCon\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"winBuildUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"winBuildCount\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"totalConAmt\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"costAmt\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"targetCostCount\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"planForm\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"actualForm\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"minConCount\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"timeCount\" t:width=\"103\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"id\" t:width=\"2\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"isMerg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /><t:Column t:key=\"column17\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"column18\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"column19\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{longNumber}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{totalCon}</t:Cell><t:Cell>$Resource{winBuildUnit}</t:Cell><t:Cell>$Resource{winBuildCount}</t:Cell><t:Cell>$Resource{totalConAmt}</t:Cell><t:Cell>$Resource{costAmt}</t:Cell><t:Cell>$Resource{targetCostCount}</t:Cell><t:Cell>$Resource{planForm}</t:Cell><t:Cell>$Resource{actualForm}</t:Cell><t:Cell>$Resource{minConCount}</t:Cell><t:Cell>$Resource{timeCount}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{isMerg}</t:Cell><t:Cell>$Resource{column17}</t:Cell><t:Cell>$Resource{column18}</t:Cell><t:Cell>$Resource{column19}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{level_Row2}</t:Cell><t:Cell>$Resource{longNumber_Row2}</t:Cell><t:Cell>$Resource{name_Row2}</t:Cell><t:Cell>$Resource{amount_Row2}</t:Cell><t:Cell>$Resource{totalCon_Row2}</t:Cell><t:Cell>$Resource{winBuildUnit_Row2}</t:Cell><t:Cell>$Resource{winBuildCount_Row2}</t:Cell><t:Cell>$Resource{totalConAmt_Row2}</t:Cell><t:Cell>$Resource{costAmt_Row2}</t:Cell><t:Cell>$Resource{targetCostCount_Row2}</t:Cell><t:Cell>$Resource{planForm_Row2}</t:Cell><t:Cell>$Resource{actualForm_Row2}</t:Cell><t:Cell>$Resource{minConCount_Row2}</t:Cell><t:Cell>$Resource{timeCount_Row2}</t:Cell><t:Cell>$Resource{id_Row2}</t:Cell><t:Cell>$Resource{isMerg_Row2}</t:Cell><t:Cell>$Resource{column17_Row2}</t:Cell><t:Cell>$Resource{column18_Row2}</t:Cell><t:Cell>$Resource{column19_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"1\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"1\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"1\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"1\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"0\" t:right=\"6\" /><t:Block t:top=\"0\" t:left=\"7\" t:bottom=\"0\" t:right=\"9\" /><t:Block t:top=\"0\" t:left=\"10\" t:bottom=\"0\" t:right=\"11\" /><t:Block t:top=\"0\" t:left=\"12\" t:bottom=\"1\" t:right=\"12\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		this.tblCheck.setFormatXml(resHelper.translateString("tblMain",
				tblMainStrXML));

		if(tblCheck.getColumn("column17")!=null)tblCheck.getColumn("column17").getStyleAttributes().setHided(true);
		if(tblCheck.getColumn("column18")!=null)tblCheck.getColumn("column18").getStyleAttributes().setHided(true);
		if(tblCheck.getColumn("column19")!=null)tblCheck.getColumn("column19").getStyleAttributes().setHided(true);


//		   kDSplitPane2.add(tblMain, "bottom");
		kDSplitPane2.remove(tblMain);
		kDSplitPane2.add(tblCheck, "bottom");
		tblCheck.getStyleAttributes().setLocked(true);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int freezeIndex=3;
				tblCheck.getViewManager().freeze(5, freezeIndex);  //���� �� ��
			}});
	}

	// �������ϻ���
	protected void sumTotal(KDTable table) {
		List unionColumns = getUnionColumns();
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);

			IRow rowNext = table.getRow(i+1);
			if(rowNext != null && rowNext.getCell("longNumber") != null && rowNext.getCell("longNumber").getValue() != null 
					&& row.getCell("longNumber") != null && row.getCell("longNumber").getValue() != null){
				String longNumNext = rowNext.getCell("longNumber").getValue().toString();
				try{
					longNumNext = longNumNext.substring(0,longNumNext.lastIndexOf("."));
				}catch (Exception e) {
					System.out.println(e.getMessage());
					return;
					// TODO: handle exception
				}
				

				String longNum = row.getCell("longNumber").getValue().toString();
				if(longNumNext.equals(longNum)){
					row.getStyleAttributes().setBackground(new Color(0xEEEEEE));
				}
			}

			if (row.getUserObject() == null) {// ��Ҷ�ӽڵ� 

				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					rowList.add(rowAfter);// ��Ҷ�ӽڵ㡢Ҷ�ӽڵ���� ���������ϼ�����
				}

				for (int k = 0; k < unionColumns.size(); k++) {
					String colName = (String) unionColumns.get(k);
					Object destValue = row.getCell(colName).getValue();// ��Ҷ�ӽڵ㱾����ֵ   ��ֵ�ᱻ�ۼӶ����ᱻ����
					//					Object destValue = null;// ��Ҷ�ӽڵ㱾����ֵ   ��ֵ�ᱻ����
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								if (destValue == null) {
									destValue = FDCHelper.ZERO;
								}
								destValue = FDCHelper
								.toBigDecimal(destValue, 2)
								.add(FDCHelper.toBigDecimal(value, 2));
							} else if (value instanceof Integer) {
								if (destValue == null) {
									destValue = new Integer(0);
								}
								destValue = (Integer) destValue
								+ (Integer) value;
							}
						}
					}
					row.getCell(colName).setValue(destValue);
				}
			}
		}
	}

	protected List getUnionColumns() {// ��Ҫ�¼����ϻ��ܵ���
		List columns = new ArrayList();
		columns.add("totalCon");      // ����ɲɹ��ĺ�ͬ������():
		columns.add("totalConAmt");   // ����ɲɹ��ĺ�ͬ���():
		columns.add("costAmt");       // ����ɲɹ��ĵ������():
		columns.add("targetCostCount");   // Ŀ��ɱ����۴�ɵ�����()
		columns.add("minConCount");       // ������ͼ��б�ĺ�ͬ��():
		columns.add("timeCount");         // ��ʱ��ɵ�����():
		columns.add("winBuildCount");         //  ������Ӧ��
		
		columns.add("column17");         //  ����ɲɹ����б�ĺ�ͬ������ 
		columns.add("column18");         //  �ƶ�Ҫ���б���ʵ�ʲ����б�ĺ�ͬ����
		columns.add("column19");         //  �ƶ�Ҫ���б�ĺ�ͬ����

		return columns;
	}



	// �ں�
	protected void MergeTbl(){ 
		String strFirstValue="";
		List<Integer> listMerIndex = new ArrayList<Integer>();
		int continueCount =0;
		for(int k=0; k<tblCheck.getRowCount(); k++){
			IRow row = tblCheck.getRow(k);
			String strCurValue =(String) row.getCell("longNumber").getValue();
			if(k == 0){
				strFirstValue = strCurValue;
			}else{
				strFirstValue = (String) tblCheck.getRow(k-1).getCell("longNumber").getValue();
				if(strCurValue.equals(strFirstValue)){
					continueCount++;
				}else{
					if(continueCount > 0){
						listMerIndex.add(k-1-continueCount);
						listMerIndex.add(k-1);
					} 
					continueCount = 0;
				}
			}
		} 

		int merCol[] = {1,2,3,4,7,10,11,12,13}; // Ҫ�ںϵ���

		//�ںϵĵ�Ԫ��ֵ��һ��,���ۼ�. �Զ����ֵ��Ϊ0
		for(int m=0; m<listMerIndex.size(); m+=2){
			int firstRow = listMerIndex.get(m);
			int lastRow = listMerIndex.get(m+1); 
			for(int i=firstRow+1; i<=lastRow; i++){
				IRow row = tblCheck.getRow(i);
				row.getCell("totalCon").setValue(0);
				row.getCell("totalConAmt").setValue(FDCHelper.ZERO);
				row.getCell("minConCount").setValue(0);
				row.getCell("timeCount").setValue(0);
			}
		}

		KDTMergeManager mm = tblCheck.getMergeManager();

		for(int m=0; m<listMerIndex.size(); m+=2){
			int firstRow = listMerIndex.get(m);
			int lastRow = listMerIndex.get(m+1);

			for(int n=0; n<merCol.length; n++){
				mm.mergeBlock(firstRow, merCol[n], lastRow, merCol[n], KDTMergeManager.SPECIFY_MERGE);
			} 
		}



		System.out.println(listMerIndex.toString());
	}


	@Override
	protected void fillFirstData(RequestRowSetEvent e) {
		// TODO Auto-generated method stub
		// super.fillFirstData(e);
		return;
	}
	
	// ���ϲ�ѯ����
	@Override
	protected void kDButton1_mouseClicked(MouseEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.kDButton1_mouseClicked(e);
//		MsgBox.showInfo("���ϲ�ѯ����");
		treeSelectChange();
	}
}
