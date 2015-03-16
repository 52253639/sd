/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IBlock;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OUException;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.invite.InviteTypeCollection;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public abstract class InviteProjectListBaseUI extends AbstractInviteProjectListBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteProjectListBaseUI.class);
    public OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentCostUnit();
    
    /**
     * output class constructor
     */
    public InviteProjectListBaseUI() throws Exception
    {
        super();
    }

	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(!checkBeforeOprate(new String[]{FDCBillStateEnum.SAVED_VALUE,FDCBillStateEnum.SUBMITTED_VALUE})){
    		MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
    	}
		super.actionEdit_actionPerformed(e);
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(!checkBeforeOprate(new String[]{FDCBillStateEnum.SAVED_VALUE,FDCBillStateEnum.SUBMITTED_VALUE})){
    		MsgBox.showWarning(this, ContractClientUtils.getRes("cantRemove"));
			SysUtil.abort();
    	}
		super.actionRemove_actionPerformed(e);
	}
	protected boolean checkBeforeOprate(String[] states) throws Exception 
	{
		boolean flag = false;
		
		List idList = ContractClientUtils.getSelectedIdValues(this.getBillListTable(), getKeyFieldName());

		if(idList==null || idList.size()==0){
			return flag ;
		}

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBillBaseCollection coll = ((IFDCBill)getBizInterface()).getCoreBillBaseCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			String billState = element.getString(getBillStatePropertyName());
			boolean pass = false;
			for (int i = 0; i < states.length; i++) 
			{
				if(billState.equals(states[i])) 
				{
					pass = true;
				}
			}
			if(!pass)
			{
				flag = false;
				break ;
			}
			else
			{
				flag = pass;
			}
		}

		return flag;
	}

	protected abstract String getEditUIName();

	protected  abstract ICoreBase getBizInterface() throws Exception ;

	/**
	 * 
	 * 描述：根据选择的合同显示单据列表
	 * 
	 * @param e
	 * @throws BOSException
	 * @author:liupd 创建时间：2006-8-2
	 *               <p>
	 */
	protected abstract void displayBillByInvite(EntityViewInfo view) throws BOSException ;
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		if(!e.getActionCommand().endsWith("ActionAddNew"))
			uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("inviteProjectId", this.getInviteProjectId());
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
	protected EntityViewInfo genBillQueryView(String inviteProjectID) {

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("inviteProject.id", inviteProjectID));
		view.setFilter(filter);
		//view.getSorter().add(new SorterItemInfo("createtime"));
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

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		if (tblMain.getRowCount() == 0 || tblMain.getSelectManager().size() == 0)
        {
            MsgBox.showWarning(this, "请先选中招标立项");
            SysUtil.abort();
        }
		super.actionAddNew_actionPerformed(e);
	}

	protected String getSelectedKeyValue() {
		return this.getSelectedKeyValue(this.getBillListTable());
	}
	protected String getBillStatePropertyName() {
    	return "state";
    }
	protected String getStateForAudit() {
		return FDCBillStateEnum.SUBMITTED_VALUE;
	}
	protected String getStateForUnAudit() {
		return FDCBillStateEnum.AUDITTED_VALUE;
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		checkSelected();

		checkBillState(new String[]{getStateForAudit()}, "selectRightRowForAudit");
		List idList =ContractClientUtils.getSelectedIdValues(this.getBillListTable(), getKeyFieldName());
		boolean hasMutex = false;
		try{
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");
			doAudit(idList);
			showOprtOKMsgAndRefresh();
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
		
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		checkSelected();

		checkBillState(new String[]{getStateForUnAudit()}, "selectRightRowForUnAudit");		
				
		List idList =ContractClientUtils.getSelectedIdValues(this.getBillListTable(), getKeyFieldName());
		boolean hasMutex = false;
		try{
			FDCClientUtils.requestDataObjectLock(this, idList, "UnAudit");
			doUnAudit(idList);
			//((IExpertAppraise)getBizInterface()).unAudit(idList);
			showOprtOKMsgAndRefresh();
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
		
	}
	public abstract void doUnAudit(List idList) throws Exception; 
	public abstract void doAudit(List idList) throws Exception; 
	protected void checkBillState(String[] states, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(this.getBillListTable(), getKeyFieldName());

		if(idList==null){
			MsgBox.showWarning(this, "请选中行");
			abort();
			return ;
		}
		
		Set idSet = ContractClientUtils.listToSet(idList);
		Set stateSet = FDCHelper.getSetByArray(states);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBaseCollection coll = getBizInterface().getCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			
//			检查单据是否在工作流中
			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());
			
//			if (!element.getString(getBillStatePropertyName()).equals(states)) {
			if (!stateSet.contains(element.getString(getBillStatePropertyName()))) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}
		}
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

	public String getInviteProjectId() {
		return this.getSelectedKeyValue(tblMain);
	}

	public String getSelectedDetailId() {
		return this.getSelectedKeyValue(tblDetail);
	}
	public ArrayList getSelectedDetailIds()
    {
    	ArrayList selectList = new ArrayList();
        List selectKeyIdFields=null;
        int mode=0;
        int[] selectRows = KDTableUtil.getSelectedRows(this.tblDetail);
        List blockList=this.tblDetail.getSelectManager().getBlocks();
        //判断是整表选取还是分块选取

        if (blockList!=null&&blockList.size()==1)
        {
           mode=((IBlock)this.tblDetail.getSelectManager().getBlocks().get(0)).getMode();
        }
        if (mode==KDTSelectManager.TABLE_SELECT&&selectRows.length>=KDTDataRequestManager.defaultPageRow-1)
        {
        	selectKeyIdFields=this.getQueryPkList();
        }
        return ListUiHelper.getSelectedIdValues(this.tblDetail,this.getKeyFieldName(),selectList,selectKeyIdFields);
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
		if (top > -1) {
			String inviteProjectId = (String) getMainTable().getCell(top,
					getKeyFieldName()).getValue();
			displayBillByInvite(genBillQueryView(inviteProjectId));
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
		this.actionQuery.setVisible(false);
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
		CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
		if (cuInfo == null) {
			throw new OUException(OUException.CU_CAN_NOT_NULL);
		}
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,
				"", this.currentOrg.getId().toString(), null, FDCHelper.getActionPK(this.actionOnLoad));
		this.treeOrg.setModel(orgTreeModel);
		this.treeOrg.expandAllNodes(true, (TreeNode) ((TreeModel) this.treeOrg
				.getModel()).getRoot());
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
		TreePath tr = e.getNewLeadSelectionPath();
		if(tr.getLastPathComponent()!=null&&tr.getLastPathComponent() instanceof DefaultKingdeeTreeNode){
			DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode)tr.getLastPathComponent();
			if (orgNode != null
					&& orgNode.getUserObject() instanceof OrgStructureInfo) {
				OrgStructureInfo org = (OrgStructureInfo) orgNode.getUserObject();
				String orgId = org.getUnit().getId().toString();
				boolean isCan = orgId.equals(this.currentOrg.getId().toString());
				this.actionAddNew.setEnabled(isCan);
				this.actionEdit.setEnabled(isCan);
				this.actionRemove.setEnabled(isCan);
				this.actionAudit.setEnabled(isCan);
				this.actionUnAudit.setEnabled(isCan);
				
			}
		}
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
			Set idSet = this.genLeafInviteTypeIdSet(inviteType.getId()
					.toString());
			filter.getFilterItems().add(
					new FilterItemInfo("inviteType.id", idSet,
							CompareType.INCLUDE));
		}
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
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
	protected String getEditUIModal() {
		// TODO 自动生成方法存根
		return UIFactoryName.NEWTAB;
	}

	protected void Remove() throws Exception {
		// TODO 自动生成方法存根
		int [] selectRows = KDTableUtil.getSelectedRows(getBillListTable());
		IObjectPK[] arrayPK = new ObjectUuidPK[selectRows.length];
		
		boolean canRemove = true ;
		//网络控制
        try
        {
			for (int i = 0; i < selectRows.length; i++) {
				String id = (String)getBillListTable().getCell(selectRows[i], getKeyFieldName()).getValue();
				//checkRef(id);
				arrayPK[i] = new ObjectUuidPK(id);
			
                this.setOprtState("REMOVE");
                this.pubFireVOChangeListener(arrayPK[i].toString());
            }
            
		}catch (Throwable ex)
        {
        	this.handUIException(ex);
        	canRemove = false;
            SysUtil.abort();
        }
		
		if(canRemove){
			getBizInterface().delete(arrayPK);		
			showOprtOKMsgAndRefresh();
		}
	}
	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
		refreshList();
	}

}