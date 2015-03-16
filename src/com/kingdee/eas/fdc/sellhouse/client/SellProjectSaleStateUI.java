/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OUPartFIInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ISellProject;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SellProjectSaleStateUI extends AbstractSellProjectSaleStateUI {
	private static final Logger logger = CoreUIObject
	.getLogger(SellProjectSaleStateUI.class);

	//这里存的ID
	private List projectList = new ArrayList();
	UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	SaleOrgUnitInfo saleUnit = SysContext.getSysContext().getCurrentSaleUnit();

	CompanyOrgUnitInfo comUnit = SysContext.getSysContext().getCurrentFIUnit();

	public SellProjectSaleStateUI() throws Exception {
		super();
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,
				MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		this.treeMain.setSelectionRow(0);		
		this.actionView.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuEdit.setVisible(false);
		if (saleUnit.isIsBizUnit() || comUnit.isIsBizUnit()) {
			this.actionAllEndInit.setEnabled(true);
			this.actionAllUnInit.setEnabled(true);
		}else
		{
			this.actionAllEndInit.setEnabled(false);
			this.actionAllUnInit.setEnabled(false);
		}
		
		this.setUITitle("销售项目月结状态表");
	}

	public void storeFields() {
		super.storeFields();
	}

	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
	throws Exception {
		if (e.getClickCount() == 2 || e.getType()==0) {
			return ;
		}
		super.tblMain_tableClicked(e);
		String sellProjectID = this.getSelectedKeyValue();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		SellProjectInfo sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(
				new ObjectUuidPK(sellProjectID), sels);
		if (sellProject.isIsEndInit()) {
			this.actionEndInit.setEnabled(false);
			this.actionUnInit.setEnabled(true);
		} else {
			this.actionEndInit.setEnabled(true);
			this.actionUnInit.setEnabled(false);
		}
		
		if(!saleUnit.isIsBizUnit() || !comUnit.isIsBizUnit())
		{
			this.actionEndInit.setEnabled(false);
			this.actionUnInit.setEnabled(false);	
		}
	}

	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}
	
	protected void execQuery()
	{
		super.execQuery();
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo)
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return null;
		}
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
			.getUserObject();
			SelectorItemCollection sels2 = new SelectorItemCollection();
			sels2.add("*");
			try {
				sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(
						new ObjectUuidPK(sellProject.getId().toString()), sels2);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			filter.getFilterItems().add(
					new FilterItemInfo("id", sellProject.getId().toString()));
			//
			//			FullOrgUnitInfo org = sellProject.getOrgUnit();
			//			SelectorItemCollection sels = new SelectorItemCollection();
			//			sels.add("*");
			//			sels.add("parent.*");
			//			sels.add("partFI.*");
			//			org = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(
			//					new ObjectUuidPK(org.getId().toString()), sels);
			//
			//			FullOrgUnitInfo fiOrg = getFIOfOrg(org);
			//
			//			if (fiOrg != null) {
			//				curOrgId = fiOrg.getId().toString();
			//			}
			// OUPartFIInfo comInfo = fiOrg.getPartFI();
			if(saleUnit.isIsBizUnit() || comUnit.isIsBizUnit())
			{
				this.actionAllEndInit.setEnabled(true);
				this.actionAllUnInit.setEnabled(true);
				if (sellProject.isIsEndInit()) {
					this.actionEndInit.setEnabled(false);
					this.actionUnInit.setEnabled(true);
				} else {
					this.actionEndInit.setEnabled(true);
					this.actionUnInit.setEnabled(false);
				}
			}

		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			projectList = new ArrayList();
			getAllSaleIds(node);
//			OrgStructureInfo org = (OrgStructureInfo) node.getUserObject();
//			FullOrgUnitInfo orgUnit = org.getUnit();
//			FullOrgUnitInfo fiOrg = getFIOfOrg(orgUnit);
//			if (fiOrg != null) {
//				curOrgId = fiOrg.getId().toString();
//			}
			if(saleUnit.isIsBizUnit() || comUnit.isIsBizUnit())
			{
				this.actionAllEndInit.setEnabled(true);
				this.actionAllUnInit.setEnabled(true);
			}
			if(projectList.size()!=0)
			{
				filter.getFilterItems().add(
						new FilterItemInfo("id", FDCHelper.list2Set(projectList),
								CompareType.INCLUDE));
			}else
			{
				filter.getFilterItems().add(
						new FilterItemInfo("id",null));
			}

		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("id",null));
		}
		viewInfo = (EntityViewInfo) this.mainQuery.clone();
		if (viewInfo.getFilter() != null)
		{
			try {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		} else
		{
			viewInfo.setFilter(filter);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
	throws Exception {
//		DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeMain
//		.getLastSelectedPathComponent();
//		if (node == null) {
//			return;
//		}
//		FilterInfo filter = new FilterInfo();
//		if (node.getUserObject() instanceof SellProjectInfo) {
//			SellProjectInfo sellProject = (SellProjectInfo) node
//			.getUserObject();
//			SelectorItemCollection sels2 = new SelectorItemCollection();
//			sels2.add("*");
//			sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(
//					new ObjectUuidPK(sellProject.getId().toString()), sels2);
//			filter.getFilterItems().add(
//					new FilterItemInfo("id", sellProject.getId().toString()));
//			//
//			//			FullOrgUnitInfo org = sellProject.getOrgUnit();
//			//			SelectorItemCollection sels = new SelectorItemCollection();
//			//			sels.add("*");
//			//			sels.add("parent.*");
//			//			sels.add("partFI.*");
//			//			org = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(
//			//					new ObjectUuidPK(org.getId().toString()), sels);
//			//
//			//			FullOrgUnitInfo fiOrg = getFIOfOrg(org);
//			//
//			//			if (fiOrg != null) {
//			//				curOrgId = fiOrg.getId().toString();
//			//			}
//			// OUPartFIInfo comInfo = fiOrg.getPartFI();
//			if(saleUnit.isIsBizUnit() || comUnit.isIsBizUnit())
//			{
//				this.actionAllEndInit.setEnabled(true);
//				this.actionAllUnInit.setEnabled(true);
//				if (sellProject.isIsEndInit()) {
//					this.actionEndInit.setEnabled(false);
//					this.actionUnInit.setEnabled(true);
//				} else {
//					this.actionEndInit.setEnabled(true);
//					this.actionUnInit.setEnabled(false);
//				}
//			}
//
//		} else if (node.getUserObject() instanceof OrgStructureInfo) {
//			projectList = new ArrayList();
//			getAllSaleIds(node);
////			OrgStructureInfo org = (OrgStructureInfo) node.getUserObject();
////			FullOrgUnitInfo orgUnit = org.getUnit();
////			FullOrgUnitInfo fiOrg = getFIOfOrg(orgUnit);
////			if (fiOrg != null) {
////				curOrgId = fiOrg.getId().toString();
////			}
//			if(saleUnit.isIsBizUnit() || comUnit.isIsBizUnit())
//			{
//				this.actionAllEndInit.setEnabled(true);
//				this.actionAllUnInit.setEnabled(true);
//			}
//			if(projectList.size()!=0)
//			{
//				filter.getFilterItems().add(
//						new FilterItemInfo("id", FDCHelper.list2Set(projectList),
//								CompareType.INCLUDE));
//			}else
//			{
//				filter.getFilterItems().add(
//						new FilterItemInfo("id",null));
//			}
//
//		} else {
//			filter.getFilterItems().add(
//					new FilterItemInfo("id",null));
//		}
//		this.mainQuery.setFilter(filter);
//		this.tblMain.removeRows();
		execQuery();
	}

	private FullOrgUnitInfo getFIOfOrg(FullOrgUnitInfo org) {
		if (org.isIsCompanyOrgUnit()) {
			return org;
		}

		FullOrgUnitInfo tmpOrg = org.getParent();
		if (tmpOrg == null) {
			return null;
		}
		return getFIOfOrg(tmpOrg);
	}

	/**
	 * 查询节点下所有的销售项目
	 * 
	 * @param treeNode
	 */
	private void getAllSaleIds(DefaultMutableTreeNode treeNode) {
		if (treeNode.isLeaf()) { // DefaultMutableTreeNode
			// DefaultKingdeeTreeNode
			DefaultMutableTreeNode thisNode = (DefaultMutableTreeNode) treeNode;
			if (thisNode.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo project = (SellProjectInfo) thisNode
				.getUserObject();
				projectList.add(project.getId().toString());
			}
		} else {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllSaleIds((DefaultMutableTreeNode) treeNode
						.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}

	public void actionAllEndInit_actionPerformed(ActionEvent e)
	throws Exception {
		PeriodInfo periodInfo = SystemStatusCtrolFactory.getRemoteInstance()
		.getStartPeriod(1818, comUnit.getId().toString());
		if (periodInfo != null) {
			((ISellProject) getBizInterface()).endInit(projectList, comUnit.getId().toString(),
					userInfo);
		} else {
			MsgBox.showInfo("对应财务组织还未设置启用期间，请到系统平台-系统配置-系统状态控制处进行设置");
			this.abort();
		}
		super.actionAllEndInit_actionPerformed(e);
		refresh(null);
	}

	public void actionAllUnInit_actionPerformed(ActionEvent e) throws Exception {
		boolean boo = false;
		List list = new ArrayList();
		List list2 = new ArrayList();
		for (int i = 0; i < projectList.size(); i++) {
			// 对应的项目如果当前期间不等于启用期间说明已经月结过
			String projectID = (String) projectList.get(i);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			SellProjectInfo project = SellProjectFactory.getRemoteInstance()
			.getSellProjectInfo(new ObjectUuidPK(projectID), sels);
			//只有结束初始化的才需要反初始化
			if(project.isIsEndInit())
			{
				list.add(project);
			}			
		}
		for(int j=0;j<list.size();j++)
		{
			SellProjectInfo project = (SellProjectInfo)list.get(j);
			list2.add(project.getId().toString());
			if (project.getSaleTerm().equals(project.getSaleNowTerm()) == false) {
				MsgBox.showInfo("存在已进行月结的销售项目，不能反初始化，"
						+ "请将所有销售项目反月结到启用期间后再进行操作");
				boo = true;
				break;
			}				
		}
		if (boo == false) {
			if(((ISellProject) getBizInterface()).unInit(list2, comUnit.getId().toString(),
					userInfo))
			{
				MsgBox.showInfo("反初始化成功");
			}
		}
		super.actionAllUnInit_actionPerformed(e);
		refresh(null);
	}

	public void actionEndInit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		List projectIds = this.getSelectedIdValues();
		PeriodInfo periodInfo = SystemStatusCtrolFactory.getRemoteInstance()
		.getStartPeriod(1818, comUnit.getId().toString());
		if (periodInfo != null) {
			boolean boo = ((ISellProject) getBizInterface()).endInit(projectIds, comUnit.getId().toString(),
					userInfo);
			if(boo)
			{
				this.actionEndInit.setEnabled(false);
				this.actionUnInit.setEnabled(true);
				MsgBox.showInfo("结束初始化成功");				
			}
		} else {
			MsgBox.showInfo("对应财务组织还未设置启用期间，请到系统平台-系统配置-系统状态控制处进行设置");
			this.abort();
		}

		super.actionEndInit_actionPerformed(e);
		refresh(null);
	}

	public void actionUnInit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		List projectIds = this.getSelectedIdValues();
		boolean boo = false;
		for (int i = 0; i < projectIds.size(); i++) {
			// 对应的项目如果当前期间不等于启用期间说明已经月结过
			String projectID = (String) projectIds.get(i);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			SellProjectInfo project = SellProjectFactory.getRemoteInstance()
			.getSellProjectInfo(new ObjectUuidPK(projectID), sels);
			if (project.getSaleTerm().equals(project.getSaleNowTerm()) == false) {
				MsgBox.showInfo("项目" + project.getName()
						+ "已经进行月结，不能反初始化，请将销售项目反月结到启用期间后再进行操作。");
				boo = true;
				break;
			}
		}
		if (boo == false) {
			if(((ISellProject) getBizInterface()).unInit(projectIds, comUnit.getId().toString(),
					userInfo))
			{
				this.actionEndInit.setEnabled(true);
				this.actionUnInit.setEnabled(false);
				MsgBox.showInfo("反初始化成功!");
			}
		}
		super.actionUnInit_actionPerformed(e);
		refresh(null);
	}

	public void actionInitDataBld_actionPerformed(ActionEvent e)
	throws Exception {
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(DayInitDataBldListUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}


	public void actionInitDataPty_actionPerformed(ActionEvent e)
	throws Exception {
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(DayInitDataPtyListUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SellProjectFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

}