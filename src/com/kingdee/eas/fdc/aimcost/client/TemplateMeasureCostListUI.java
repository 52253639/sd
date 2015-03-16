/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.client.AimMeasureCostEditUI.Item;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUInfo;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class TemplateMeasureCostListUI extends AbstractTemplateMeasureCostListUI
{
    private static final Logger logger = CoreUIObject.getLogger(TemplateMeasureCostListUI.class);

	/**
	 * output class constructor
	 */
	public TemplateMeasureCostListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output aimTable_tableClicked method
	 */
	protected void aimTable_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if(e.getClickCount()==2){
			btnOk.doClick();
		}
	}
	
	protected void researchTable_tableClicked(KDTMouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			btnOk.doClick();
		}
	}

	protected ICoreBase getBizInterface() throws Exception {
		return TemplateMeasureCostFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

	public void onLoad() throws Exception {
		KDTreeView treeView = new KDTreeView(this.treeMain);
		treeView.setVisible(true);
		kDScrollPane1.getViewport().add(treeView, null);
		kDSplitPane1.remove(kDScrollPane1);
		kDSplitPane1.add(treeView, "left");
		initTree();
		super.onLoad();
		String orgId=getFilterValue("orgUnit.id");
		if(orgId!=null){
			selectOrgNode(orgId);
		}
		this.toolBar.setVisible(false);
		menuBar.setVisible(false);
	}

	private void selectOrgNode(String orgId){
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
		DefaultKingdeeTreeNode node=root;
		while(node!=null){
			if(node.getUserObject() instanceof OrgStructureInfo){
				OrgStructureInfo info = (OrgStructureInfo)node.getUserObject();
				if(info.getUnit().getId().toString().equals(orgId)){
					treeMain.setSelectionNode(node);
					int row=treeMain.getSelectionRows()[0];
					treeMain.expandRow(row);
					break;
				}
			}
			node=(DefaultKingdeeTreeNode)node.getNextNode();
		}
	}
	protected void initTree() throws Exception {
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
//		treeBuilder.setDevPrjFilter(false);
		if(isContainDevProject()){
			treeBuilder.setDevPrjFilter(false);
		}else{
			treeBuilder.setDevPrjFilter(true, !isContainDevProject());
		}
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
/*		this.treeMain.expandAllNodes(true,
				(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());*/
	}
	
	public boolean isContainDevProject(){
		boolean isContainDevProject = true;
		if(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo() != null){
			Map param = null;
			try {
				param = FDCUtils.getDefaultFDCParam(null,null);
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(param.get(FDCConstants.FDC_PARAM_MEASURECOSTISCONTAINDEVPROJECT)!=null){
				isContainDevProject = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_MEASURECOSTISCONTAINDEVPROJECT).toString()).booleanValue();
			}
		}
		return isContainDevProject;
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		fillTable();
	}
	private void fillTable() throws Exception{
		aimTable.removeRows(false);
		aimTable.checkParsed();
		aimTable.getStyleAttributes().setLocked(true);
		aimTable.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		researchTable.removeRows(false);
		researchTable.checkParsed();
		researchTable.getStyleAttributes().setLocked(true);
		researchTable.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		EntityViewInfo view=new EntityViewInfo();
		view.put("selector", getSelectors());
		view.setFilter(getMainFilter());
		view.getSorter().clear();
		view.getSorter().add(new SorterItemInfo("mainVerNo"));
		view.getSorter().add(new SorterItemInfo("subVerNo"));
		TemplateMeasureCostCollection c = TemplateMeasureCostFactory.getRemoteInstance().getTemplateMeasureCostCollection(view);
		for(Iterator iter=c.iterator();iter.hasNext();){
			TemplateMeasureCostInfo info = (TemplateMeasureCostInfo)iter.next();
			IRow row = null;
			if(info.isIsAimMeasure()){
				row=aimTable.addRow();
			}else{
				row=researchTable.addRow();
			}
			loadRow(row,info);
		}
		
	}
	private void loadRow(IRow row,TemplateMeasureCostInfo info){
		row.setUserObject(info);
		row.getCell("id").setValue(info.getId().toString());
		row.getCell("versionNumber").setValue(info.getVersionNumber().replaceAll("!", "\\."));
		row.getCell("versionName").setValue(info.getVersionName());
		ProjectTypeInfo projectType = info.getProjectType();
		if(projectType!=null){
			row.getCell("projectType.name").setValue(projectType.getName());
		}
		row.getCell("orgUnit.number").setValue(info.getOrgUnit().getNumber());
		row.getCell("orgUnit.name").setValue(info.getOrgUnit().getName());
		row.getCell("creator.name").setValue(info.getCreator().getName());
		row.getCell("createTime").setValue(info.getCreateTime());
		CurProjectInfo prj=info.getProject();
		if(prj!=null){
			row.getCell("project.name").setValue(prj.getName());
			row.getCell("project.name").setValue(prj.getName());
			row.getCell("project.number").setValue(prj.getNumber());
		}
		
		

	}
	protected FilterInfo getMainFilter() throws Exception {
		String projectId = this.getSelectProjectId();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("hasEffected", Boolean.TRUE);//是否是模板
//		filter.appendFilterItem("isAimMeasure", getUIContext().get("isAimMeasure"));
		if (projectId != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("project.id", projectId));
		} else {
			String orgId = getSelectOrgId();
			if (orgId != null) {
				filter.appendFilterItem("orgUnit.id", orgId);
			} else {
				// filter.appendFilterItem("orgUnit", orgId);
			}
		}
/*		String projectTypeId=getFilterValue("projectType.id");
		if(projectTypeId!=null){
			filter.appendFilterItem("projectType.id", projectTypeId);
		}*/
		
		if(!isContainDevProject()){
			filter.appendFilterItem("project.isDevPrj", Boolean.FALSE);
		}
		return filter;
	}

	protected String getSelectProjectId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo.getId().toString();
		}
		return null;
	}

	/**
	 * 所选择结点的实体财务组织
	 * 
	 * @return
	 */
	protected String getSelectOrgId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null ||node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		FullOrgUnitInfo org=null;
		if (node.getUserObject() instanceof OrgStructureInfo){
			final OrgStructureInfo info = (OrgStructureInfo)node.getUserObject();
			org = info.getUnit();

		}
		if(node.getUserObject() instanceof CurProjectInfo){
			CurProjectInfo curPrj=(CurProjectInfo)node.getUserObject();
			EntityViewInfo view=new EntityViewInfo();
			String number=curPrj.getLongNumber();
			
			view.getSelector().add("costCenterOU.id");
			view.getSelector().add("costCenterOU.CU.id");
// view.getSelector().add("costCenterOU.partFI.unit.id");
			view.getSelector().add("curProject.longNumber");
			ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection=null;
			try {
				projectWithCostCenterOUCollection = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(view);
			} catch (BOSException e) {
				handUIException(e);
			}
			if(projectWithCostCenterOUCollection==null||projectWithCostCenterOUCollection.size()==0){
				return null;
			}
			ProjectWithCostCenterOUInfo pwcInfo=null;
			
			CurProjectInfo prj=null;
			CostCenterOrgUnitInfo costCenter=null;
			for(Iterator iter=projectWithCostCenterOUCollection.iterator();iter.hasNext();){
				pwcInfo=(ProjectWithCostCenterOUInfo)iter.next();
				final String longNumber = pwcInfo.getCurProject().getLongNumber();
				if(number.startsWith(longNumber)){
					if(prj==null){
						prj=pwcInfo.getCurProject();
						costCenter=pwcInfo.getCostCenterOU();
					}else{
						if(longNumber.startsWith(prj.getLongNumber())){
							prj=pwcInfo.getCurProject();
							costCenter=pwcInfo.getCostCenterOU();
						}
					}
				}
			}
			org=costCenter.castToFullOrgUnitInfo();
		}
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("isCompanyOrgUnit");
		selector.add("partFI.isBizUnit");
		selector.add("parent.id");
		try{
			for(;org!=null&&org.getId()!=null;org=org.getParent()){
				org=FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(org.getId()), selector);
				if(org.isIsCompanyOrgUnit()){
					if(org.getPartFI().isIsBizUnit()){
						return org.getId().toString();
					}
				}
			};
		}catch (Exception e){
			return null;
		}
		return null;
	}

	public TemplateMeasureCostCollection getData() {
		TemplateMeasureCostCollection infos = new TemplateMeasureCostCollection();
		KDTable table=null;
		if(tabPanel.getSelectedIndex()==0){
			table=researchTable;
		}else{
			table=aimTable;
		}
		int[] selectedRows = KDTableUtil.getSelectedRows(table);
		for (int i = 0; i < selectedRows.length; i++) {
			if(table.getRow(selectedRows[i])==null){
				continue;
			}
			TemplateMeasureCostInfo info = (TemplateMeasureCostInfo)table.getRow(selectedRows[i]).getUserObject();
			infos.add(info);

		}
		return infos;
	}
	
	public SelectorItemCollection getSelectors() {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add("*");
        sic.add(new SelectorItemInfo("projectType.name"));
        sic.add(new SelectorItemInfo("orgUnit.number"));
        sic.add(new SelectorItemInfo("orgUnit.name"));
        sic.add(new SelectorItemInfo("project.name"));
        sic.add(new SelectorItemInfo("project.number"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("creator.name"));
        return sic;
	}
	
	private String getFilterValue(String name){
		Object obj = getUIContext().get(name);
		if(obj!=null){
			return obj.toString();
		}
		
		return null;
	}
	
	private boolean isCancel=false;
	protected void btnOk_actionPerformed(ActionEvent e) throws Exception {
		super.btnOk_actionPerformed(e);
		isCancel=false;
		this.disposeUIWindow();
	}
	
	protected void btnNo_actionPerformed(ActionEvent e) throws Exception {
		super.btnNo_actionPerformed(e);
		isCancel=true;
		this.disposeUIWindow();
	}
	
	public boolean isCancel(){
		return isCancel;
	}
	private Item [] itemsSix=null;
	private Item [] itemsMain=null;
	public Item getIndexName(String key){
		CostAccountTypeEnum type=null;
		String productId=getFilterValue("product.id");
		if(productId==null){
			type=CostAccountTypeEnum.SIX;
		}else{
			type=CostAccountTypeEnum.MAIN;
		}

		if(type==CostAccountTypeEnum.SIX){
			for(int i=0;i<itemsSix.length;i++){
				if(itemsSix[i].key.equals(key)){
					return itemsSix[i];
				}
			}
		}else if (type==CostAccountTypeEnum.MAIN){
			for(int i=0;i<itemsMain.length;i++){
				if(itemsMain[i].key.equals(key)){
					return itemsMain[i];
				}
			}
		}
		return null;
	}



}