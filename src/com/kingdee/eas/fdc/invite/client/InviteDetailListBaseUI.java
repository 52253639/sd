/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OUException;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.invite.InviteTypeCollection;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.NewListingFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 招标清单列表界面基类
 */
public abstract class InviteDetailListBaseUI extends
		AbstractInviteDetailListBaseUI {
    public OrgUnitInfo currentOrg = SysContext.getSysContext()
			.getCurrentCostUnit();

	/**
	 * output class constructor
	 */
	public InviteDetailListBaseUI() throws Exception {
		super();
	}

	protected String getEditUIName() {
		// TODO 自动生成方法存根
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return NewListingFactory.getRemoteInstance();
	}

	/**
	 *
	 * 描述：根据选择的合同显示单据列表
	 *
	 * @param e
	 * @throws BOSException
	 * @author:liupd 创建时间：2006-8-2
	 *               <p>
	 */
	protected void displayBillByInvite(EntityViewInfo view) throws BOSException {
	}

	/**
	 *
	 * 描述：生成查询单据的EntityViewInfo
	 *
	 * @param e
	 * @return
	 * @author:liupd 创建时间：2006-8-2
	 *               <p>
	 */
	protected EntityViewInfo genBillQueryView(String contractId) {

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("listing.id", contractId));
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("number"));
		SelectorItemCollection selectors = genBillQuerySelector();
		if (selectors != null && selectors.size() > 0) {
			for (Iterator iter = selectors.iterator(); iter.hasNext();) {
				SelectorItemInfo element = (SelectorItemInfo) iter.next();
				view.getSelector().add(element);

			}
		}
		return view;
	}

	/**
	 *
	 * 描述：获取当前单据的Table（子类必须实现）
	 *
	 * @return
	 * @author:liupd 创建时间：2006-8-2
	 *               <p>
	 */
	protected KDTable getBillListTable() {
		return this.tblDetail;
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		// TODO 自动生成方法存根
		 super.tblMain_tableClicked(e);
	}

	protected String getSelectedKeyValue() {
		return this.getSelectedKeyValue(this.getBillListTable());
	}

	/**
	 * 获取当前选择行的主键
	 *
	 */
	protected String getSelectedKeyValue(KDTable table) {
		KDTSelectBlock selectBlock = table.getSelectManager().get();

		if (selectBlock != null) {
			int rowIndex = selectBlock.getTop();
			IRow row = table.getRow(rowIndex);
			if (row == null) {
				return null;
			}

			ICell cell = row.getCell(getKeyFieldName());

			if (cell == null) {
				MsgBox.showError(EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Error_KeyField_Fail"));
				SysUtil.abort();
			}

			Object keyValue = cell.getValue();

			if (keyValue != null) {
				return keyValue.toString();
			}
		}

		return null;
	}
	protected String getSelectedNameValue(KDTable table) {
		KDTSelectBlock selectBlock = table.getSelectManager().get();

		if (selectBlock != null) {
			int rowIndex = selectBlock.getTop();
			IRow row = table.getRow(rowIndex);
			if (row == null) {
				return null;
			}

			ICell cell = row.getCell("name");

			if (cell == null) {
				MsgBox.showError(EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Error_KeyField_Fail"));
				SysUtil.abort();
			}

			Object keyValue = cell.getValue();

			if (keyValue != null) {
				return keyValue.toString();
			}
		}

		return null;
	}

	public String getInviteListingId() {
		return this.getSelectedKeyValue(tblMain);
	}
	public String getSelectedDetailId(){
		return this.getSelectedKeyValue(tblDetail);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// if(!isHasBillTable()) return;
		if (e.getSelectBlock() == null)
			return;
		getBillListTable().removeRows(false);
		KDTSelectBlock selectBlock = e.getSelectBlock();
		int top = selectBlock.getTop();
		if(top>-1){
			String contractId = (String) getMainTable().getCell(top,
					getKeyFieldName()).getValue();
			displayBillByInvite(genBillQueryView(contractId));
		}
		super.tblMain_tableSelectChanged(e);
	}

	/**
	 *
	 * 描述：生成获取单据的Selector
	 *
	 * @return
	 * @author:liupd 创建时间：2006-8-3
	 *               <p>
	 */
	protected SelectorItemCollection genBillQuerySelector() {
		return null;
	}

	public void onLoad() throws Exception {
		if (currentOrg == null || !this.currentOrg.isIsCompanyOrgUnit()) {
			MsgBox.showInfo("非财务组织不能进入!");
			this.abort();
		}
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		initTable();
		buildOrgTree();
		buildInviteTypeTree();
		DefaultKingdeeTreeNode orgRoot = (DefaultKingdeeTreeNode) ((TreeModel) this.treeOrg
				.getModel()).getRoot();
		DefaultKingdeeTreeNode node = this.findNode(orgRoot, SysContext
				.getSysContext().getCurrentCostUnit().getId().toString());
		this.removeAllBrotherNode(node);
		this.treeOrg.setSelectionNode(node);
		this.treeInviteType.setSelectionRow(0);
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
	}

	public void removeAllBrotherNode(DefaultKingdeeTreeNode node) {
		DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode) node
				.getParent();
		if (parent == null) {
			return;
		}
		this.treeOrg.removeAllChildrenFromParent(parent);
		this.treeOrg.addNodeInto(node, parent);
		this.removeAllBrotherNode(parent);
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

	protected void initTable() {

	}

	private void buildOrgTree() throws Exception {
//		CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
//		if (cuInfo == null) {
//			throw new OUException(OUException.CU_CAN_NOT_NULL);
//		}
//		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,
//				"", null, null, FDCHelper.getActionPK(this.actionOnLoad));
//		this.treeOrg.setModel(orgTreeModel);
//		this.treeOrg.expandAllNodes(true, (TreeNode) ((TreeModel) this.treeOrg
//				.getModel()).getRoot());
		
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,
				"", SysContext.getSysContext().getCurrentFIUnit().getId().toString(), null, FDCHelper.getActionPK(this.actionOnLoad));
		this.treeOrg.setModel(orgTreeModel);
	}

	private void buildInviteTypeTree() throws Exception {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		TreeModel model = FDCClientHelper.createDataTree(InviteTypeFactory
				.getRemoteInstance(), filter, "所有类型");
		this.treeInviteType.setModel(model);
	}

	protected void fillTable() throws Exception {
		this.mainQuery.setFilter(this.getMainFilter());
		this.tblMain.removeRows();
		this.tblDetail.removeRows();
	}

	protected void treeInviteType_valueChanged(TreeSelectionEvent e)
			throws Exception {
		super.treeInviteType_valueChanged(e);
		fillTable();
	}

	protected void treeOrg_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeOrg_valueChanged(e);
		fillTable();
	}

	protected FilterInfo getMainFilter() throws Exception {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeOrg
				.getLastSelectedPathComponent();
		if (orgNode != null
				&& orgNode.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo org = (OrgStructureInfo) orgNode.getUserObject();
			String orgId = org.getUnit().getId().toString();
			filter.getFilterItems()
					.add(new FilterItemInfo("orgUnit.id", orgId));
		}
		DefaultKingdeeTreeNode inviteNode = (DefaultKingdeeTreeNode) treeInviteType
				.getLastSelectedPathComponent();
		if (inviteNode != null
				&& inviteNode.getUserObject() instanceof InviteTypeInfo) {
			InviteTypeInfo inviteType = (InviteTypeInfo) inviteNode
					.getUserObject();
			// filter.getFilterItems().add(
			// new FilterItemInfo("inviteType.id", inviteType.getId()
			// .toString()));
			Set idSet = this.genLeafInviteTypeIdSet(inviteType.getId()
					.toString());
			filter.getFilterItems().add(
					new FilterItemInfo("inviteType.id", idSet,
							CompareType.INCLUDE));
		}
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		return filter;
	}

	public Set genLeafInviteTypeIdSet(String id) throws Exception {

		Set idSet = new HashSet();
		InviteTypeInfo typeInfo = InviteTypeFactory.getRemoteInstance()
				.getInviteTypeInfo(new ObjectUuidPK(BOSUuid.read(id)));
		String longNumber = typeInfo.getLongNumber();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber + "%",
						CompareType.LIKE));
		view.setFilter(filter);

		InviteTypeCollection contractTypeCollection = InviteTypeFactory
				.getRemoteInstance().getInviteTypeCollection(view);

		for (Iterator iter = contractTypeCollection.iterator(); iter.hasNext();) {
			InviteTypeInfo element = (InviteTypeInfo) iter.next();
			idSet.add(element.getId().toString());
		}
		return idSet;
	}
	
	
	 public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
			// TODO Auto-generated method stub
			super.actionQuery_actionPerformed(e);
		}
}