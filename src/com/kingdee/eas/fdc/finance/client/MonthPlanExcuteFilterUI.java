/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureCollection;
import com.kingdee.eas.basedata.org.OrgStructureFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectListUI;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fi.gl.common.KDSpinnerCellEditor;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MonthPlanExcuteFilterUI extends AbstractMonthPlanExcuteFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(MonthPlanExcuteFilterUI.class);
    private TreeSelectDialog dialog;
    public static boolean getIt = false;
    public MonthPlanExcuteFilterUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		dialog=new TreeSelectDialog(this,buildTree());
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		this.spYear.setValue(year);
		this.spMonth.setValue(month);
		
		this.spYear.setModel(new SpinnerNumberModel(year,1,10000,1));
		this.spMonth.setModel(new SpinnerNumberModel(month,1,12,1));
	}
    public RptParams getCustomCondition() {
    	RptParams pp = new RptParams();
        if(this.spYear.getIntegerVlaue()!=null&&this.spMonth.getIntegerVlaue()!=null){
        	int year=this.spYear.getIntegerVlaue().intValue();
    		int month=this.spMonth.getIntegerVlaue().intValue();
    		Calendar cal = Calendar.getInstance();
    		cal.set(Calendar.YEAR, year);
    		cal.set(Calendar.MONTH, month-1);
    		cal.set(Calendar.DATE, 1);
    		cal.set(Calendar.HOUR_OF_DAY, 0);
    		cal.set(Calendar.MINUTE, 0);
    		cal.set(Calendar.SECOND, 0);
    		cal.set(Calendar.MILLISECOND, 0);
    		
        	pp.setObject("bizDate", cal.getTime());
        }else{
        	pp.setObject("bizDate", null);
        }
        if(this.txtCurProject.getUserObject()!=null){
        	pp.setObject("tree", this.txtCurProject.getUserObject());
        }else{
        	pp.setObject("tree", null);
        }
		return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		if(params.getObject("bizDate")!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime((Date) params.getObject("bizDate"));
			int year=cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;
			
			this.spYear.setValue(year);
			this.spMonth.setValue(month);
		}
		if(params.getObject("tree")!=null){
			try {
				List curProject=new ArrayList();
				getCurProjectName(curProject,((DefaultKingdeeTreeNode) ((TreeModel)params.getObject("tree")).getRoot()));
				String name="";
				for(int i=0;i<curProject.size();i++){
					name=name+curProject.get(i).toString()+";";
				}
				this.txtCurProject.setText(name);
				this.txtCurProject.setUserObject(params.getObject("tree"));
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}
	public void getCurProjectName(List curProject,DefaultKingdeeTreeNode node) throws BOSException{
		if(node.getUserObject() instanceof CurProjectInfo) {
			curProject.add(((CurProjectInfo)node.getUserObject()).getName());
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			getCurProjectName(curProject,(DefaultKingdeeTreeNode) node.getChildAt(i));
		}
	}
	protected void btnSelect_actionPerformed(ActionEvent e) throws Exception {
		dialog.show();
		if(dialog.getSelectTree()!=null){
			List curProject=new ArrayList();
			getCurProjectName(curProject,(DefaultKingdeeTreeNode) dialog.getSelectTree().getRoot());
			String spText="";
			for(int i=0;i<curProject.size();i++){
				spText=spText+curProject.get(i).toString()+";";
			}
			this.txtCurProject.setText(spText);
			this.txtCurProject.setUserObject(dialog.getSelectTree());
		}
	}
	public static OrgStructureInfo getParentOrgInfo(OrgStructureInfo info) throws EASBizException, BOSException{
		if(info.getLevel()==4){
			return info;
		}else{
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("id");
			sel.add("parent.id");
			sel.add("parent.unit.id");
			sel.add("unit.id");
			sel.add("unit.name");
			sel.add("unit.number");
			sel.add("unit.longNumber");
			sel.add("level");
			return getParentOrgInfo(OrgStructureFactory.getRemoteInstance().getOrgStructureInfo(new ObjectUuidPK(info.getParent().getId()), sel));
		}
	}
	public static TreeModel buildOrgTree() throws Exception{
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
	
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,"", orgUnit.getId().toString(), null, null);
		DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode) orgTreeModel.getRoot();
	
		KingdeeTreeModel model=null;
		KDTreeNode root=null; 
		
		if (rootNode.getUserObject() != null) {
			OrgStructureInfo orgStru = (OrgStructureInfo) rootNode.getUserObject();
			if (!orgStru.getUnit().isIsCostOrgUnit()) {
				MsgBox.showWarning(FDCClientUtils.getRes("needCostcenter"));
				SysUtil.abort();
			}
			OrgStructureInfo rootInfo=OrgStructureFactory.getRemoteInstance().getOrgStructureInfo("select id,parent.id,unit.id,unit.name,unit.number,unit.longNumber,level from where id='"+orgStru.getId()+"'");
			if(orgUnit.getLevel()<4){
				root = new KDTreeNode(orgUnit.getName());
				root.setUserObject(rootInfo);
				model=new KingdeeTreeModel(root);
				OrgStructureCollection col=OrgStructureFactory.getRemoteInstance().getOrgStructureCollection("select id,unit.id,unit.name,unit.number,unit.longNumber,level from where level=4 and tree.id='00000000-0000-0000-0000-0000000000024F2827FD' and longnumber like '"+orgUnit.getNumber()+"!%' order by unit.number");
				for(int i=0;i<col.size();i++){
					KDTreeNode child = new KDTreeNode(col.get(i).getUnit().getName());
					child.setUserObject(col.get(i));
					root.add(child);
				}
			}else if(orgUnit.getLevel()==4){
				root = new KDTreeNode(orgUnit.getName());
				root.setUserObject(rootInfo);
				model=new KingdeeTreeModel(root);
			}else if(orgUnit.getLevel()>4){
				OrgStructureInfo info=getParentOrgInfo(rootInfo);
			
				root = new KDTreeNode(info.getUnit().getName());
				root.setUserObject(info);
				model=new KingdeeTreeModel(root);
			}
			model.setRoot(root);
		}
		return model;
	}
	public static TreeModel buildTree() throws Exception {
		KDTree tree = new KDTree();

		TreeModel orgTreeModel = buildOrgTree();
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) orgTreeModel.getRoot();

		if (root.getUserObject() != null) {
			buildByOrgTree(tree,orgTreeModel);
		} else {
			buildByCurOrg(tree);
		}
		return tree.getModel();
	}
	public static void buildByCurOrg(KDTree orgTree) throws Exception {
		FilterInfo projFilter = getProjectFilterByCurOrg();
		TreeNode root = (TreeNode) buildProjTree(projFilter).getModel().getRoot();
		if (root.getChildCount() == 0) {
			MsgBox.showWarning("无法构造工程项目树，请确认\"工程项目与成本中心的对应关系\"设置是否正确");
			SysUtil.abort();
		}
		TreeModel model = new DefaultTreeModel(root.getChildAt(0));
		orgTree.setModel(model);
	}
	public static FilterInfo getProjectFilterByCurOrg() throws BOSException,EASBizException {
		String curCostId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("costCenterOU.id", curCostId));
		view.setFilter(filter);
		ProjectWithCostCenterOUCollection relations = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(view);
		if (relations == null || relations.size() == 0) {
			MsgBox.showWarning(FDCClientUtils.getRes("noRelativeProject"));
			SysUtil.abort();
		}
		String projId = relations.get(0).getCurProject().getId().toString();
		ObjectUuidPK projUuidPK = new ObjectUuidPK(projId);
		if (!CurProjectFactory.getRemoteInstance().exists(projUuidPK)) {
			MsgBox.showWarning(FDCClientUtils.getRes("noRelativeProject"));
			SysUtil.abort();
		}
		CurProjectInfo curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(projUuidPK);
		
		if (!curProjectInfo.isIsEnabled()) {
			MsgBox.showWarning(FDCClientUtils.getRes("curOrgRelProjNotEnab"));
			SysUtil.abort();
		}
		
		String projNumber = curProjectInfo.getLongNumber();
		FilterInfo projFilter = new FilterInfo();
		projFilter.getFilterItems().add(
				new FilterItemInfo("longnumber", projNumber + "%",
						CompareType.LIKE));
		String orgUnitId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		
		projFilter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit", orgUnitId));
		projFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		projFilter.getFilterItems().add(
				new FilterItemInfo("isDevPrj", Boolean.TRUE));
		return projFilter;
	}
	public static  KDTree buildProjTree(FilterInfo filter) throws Exception {
		KDTree treeMain = new KDTree();
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("*");
		selector.add("fullOrgUnit.*");
		selector.add("parent.id");
		ITreeBuilder treeBuilder = TreeBuilderFactory.createTreeBuilder(new DefaultLNTreeNodeCtrl(CurProjectFactory.getRemoteInstance()), 50,5,filter,selector);

		treeBuilder.buildTree(treeMain);

		TreeModel treeModel = null;
		String paramValue = ParamControlFactory.getRemoteInstance().getParamValue(null, FDCConstants.FDC_PARAM_PROJECTTREESORTBYSORTNO);
		if (Boolean.valueOf(paramValue).booleanValue()) {
			treeModel = new KingdeeTreeModel((TreeNode)((DefaultKingdeeTreeNode) treeMain.getModel().getRoot()).clone());
			addNode((DefaultKingdeeTreeNode)treeMain.getModel().getRoot(),(DefaultKingdeeTreeNode)treeModel.getRoot());
		} else {
			treeModel = new KingdeeTreeModel((TreeNode) ((DefaultKingdeeTreeNode) treeMain.getModel().getRoot()));
		}
		treeMain.setModel(treeModel);
		treeMain.setShowPopMenuDefaultItem(false);
		return treeMain;
	}
	public static  void addNode(DefaultKingdeeTreeNode node,DefaultKingdeeTreeNode newNode) {
		TreeMap map = new TreeMap();
		BigDecimal b = new BigDecimal("0.001");
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode childAt = (DefaultKingdeeTreeNode) node.getChildAt(i);
			int index = ((CurProjectInfo) childAt.getUserObject()).getSortNo();
			if (map.containsKey(new BigDecimal(index))) {
				map.put(new BigDecimal(index).add(b), childAt);
				b = b.add(b);
			} else {
				map.put(new BigDecimal(index), childAt);
			}
		}
		Collection collection = map.values();
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			DefaultKingdeeTreeNode element = (DefaultKingdeeTreeNode) iter.next();
			DefaultKingdeeTreeNode newElement = (DefaultKingdeeTreeNode) element.clone();
			newNode.add(newElement);
			addNode(element, newElement);
		}
	}
	public static  void buildByOrgTree(KDTree orgTree,TreeModel orgTreeModel)throws Exception {
		orgTree.setModel(orgTreeModel);
		Set authorizedOrgs = new HashSet();
		Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
				new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.CostCenter,null, null, null);
		if (orgs != null) {
			Set orgSet = orgs.keySet();
			Iterator it = orgSet.iterator();
			while (it.hasNext()) {
				authorizedOrgs.add(it.next());
			}
		}

		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		TreeModel orgTreeModel2 = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,"", orgUnit.getId().toString(), null, null);
		TreeNode root2 = (TreeNode) orgTreeModel2.getRoot();

		Set leafNodesIdSet = new HashSet();
		FDCClientUtils.genLeafNodesIdSet(root2, leafNodesIdSet);
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit", leafNodesIdSet,
						CompareType.INCLUDE));
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("isDevPrj", Boolean.TRUE));
		
		FilterInfo authorizedOrgsFilter = new FilterInfo();
		if (authorizedOrgs == null || authorizedOrgs.size() == 0) {
			// 没有授权则只显示没有做的的数据
			authorizedOrgsFilter.getFilterItems().add(
					new FilterItemInfo("costCenter.id", null));
		} else {
			authorizedOrgsFilter.getFilterItems().add(
					new FilterItemInfo("costCenter.id", authorizedOrgs,
							CompareType.INCLUDE));
			authorizedOrgsFilter.getFilterItems().add(
					new FilterItemInfo("costCenter.id", null));
			authorizedOrgsFilter.setMaskString("#0 or #1");
		}
		filter.mergeFilter(authorizedOrgsFilter, "and");
		
		KDTree projTree=buildProjTree(filter);
		TreeNode root = (TreeNode) projTree.getModel().getRoot();
		
		TreeNode orgRoot = (TreeNode) orgTreeModel.getRoot();
		
		if (orgRoot.getChildCount() == 0) {
			for (int i = 0; i < root.getChildCount(); i++) {
				DefaultKingdeeTreeNode projNode = (DefaultKingdeeTreeNode) root.getChildAt(i);
				orgTree.addNodeInto((MutableTreeNode) projNode,(MutableTreeNode) orgRoot);
				i--;
			}
		} else {
			for (int i = 0; i < root.getChildCount(); i++) {
				
				DefaultKingdeeTreeNode projNode = (DefaultKingdeeTreeNode) root.getChildAt(i);
				CurProjectInfo projectInfo = (CurProjectInfo) projNode.getUserObject();
				String orgId = projectInfo.getFullOrgUnit().getLongNumber();
		
				searchNodeByOrgId(orgRoot, orgId, orgTree, projNode,leafNodesIdSet);
		
				if (getIt) {
					i--;
				}
				getIt = false;
			}
		}
	}
	public static  void searchNodeByOrgId(TreeNode orgNode, String orgId,KDTree orgTree, TreeNode projNode, Set leafNodesIdSet) {
		if (getIt) {
			return;
		}
		if(((DefaultKingdeeTreeNode) orgNode).getUserObject() instanceof OrgStructureInfo){
			OrgStructureInfo obj = (OrgStructureInfo)((DefaultKingdeeTreeNode) orgNode).getUserObject();
			if(obj.getLevel()==4){
				String orgRootId = ((OrgStructureInfo) obj).getUnit().getNumber();
				if (orgId.indexOf(orgRootId)>=0) {
					orgTree.addNodeInto((MutableTreeNode) projNode,(MutableTreeNode) orgNode);
					getIt = true;
					return;
				}
			}
		}else {
			CurProjectInfo projectInfo = (CurProjectInfo) ((DefaultKingdeeTreeNode) projNode).getUserObject();
			if(projectInfo.getParent()!=null){
				CurProjectInfo obj = (CurProjectInfo)((DefaultKingdeeTreeNode) orgNode).getUserObject();
				if(obj.getId().equals(projectInfo.getParent().getId())){
					orgTree.addNodeInto((MutableTreeNode) projNode,(MutableTreeNode) orgNode);
					getIt = true;
					return;
				}
			}
		}
		
		int count = orgNode.getChildCount();
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) orgNode.getChildAt(i);
			Object userObj = treeNode.getUserObject();
			if (userObj instanceof OrgStructureInfo&& leafNodesIdSet.contains(((OrgStructureInfo) userObj).getUnit().getId().toString())) {
				String orgId2 = ((OrgStructureInfo) userObj).getUnit().getNumber();

				if (orgId.indexOf(orgId2)>=0) {
					orgTree.addNodeInto((MutableTreeNode) projNode,(MutableTreeNode) treeNode);
					getIt = true;
					break;
				}
			} else {
				searchNodeByOrgId(treeNode, orgId, orgTree, projNode,leafNodesIdSet);
			}
		}
	}
}