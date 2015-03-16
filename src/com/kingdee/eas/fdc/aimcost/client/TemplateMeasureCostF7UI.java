/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Action;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.client.AimMeasureCostEditUI.Item;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUInfo;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.invite.client.ListingItemPriceQueryUI;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class TemplateMeasureCostF7UI extends AbstractTemplateMeasureCostF7UI {
	private static final Logger logger = CoreUIObject
			.getLogger(TemplateMeasureCostF7UI.class);
	private IUIObject uiObj = null;
	/**
	 * output class constructor
	 */
	public TemplateMeasureCostF7UI() throws Exception {
		super();
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
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if(e.getClickCount()==2){
			btnOk.doClick();
		}
//		super.tblMain_tableClicked(e);
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
//		kDScrollPane1.getViewport().add(treeView, null);
		kDSplitPane1.add(treeView, "left");
		initTree();
		super.onLoad();
		this.setPreferredSize(new Dimension(810,629));
//		this.tabPane.setPreferredSize(new Dimension(850,629));
		String orgId=getFilterValue("orgUnit.id");
		if(orgId!=null){
			selectOrgNode(orgId);
		}
		
		this.toolBar.setVisible(true);
		actionQuery.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_filter"));
		actionQuery.setEnabled(true);
		menuBar.setVisible(false);
		tabPane.remove(pnlMeasure);
		loadDBpanel(this.pnlDB);
//		tabPane.setSelectedIndex(0);
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
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
/*		this.treeMain.expandAllNodes(true,
				(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());*/
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
//		this.mainQuery.setFilter(this.getMainFilter());
//		this.mainQuery.put("selector", getSelectors());// .getSelector().
//		this.tblMain.removeRows();
////		execQuery();
		fillTable();
	}
	private void fillTable() throws Exception{
		tblMain.removeRows(false);
		tblMain.checkParsed();
		if(getFilterValue("product.id")==null){
			this.tblMain.getColumn("product.name").getStyleAttributes().setHided(true);
		}
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		FDCHelper.formatTableNumber(tblMain, new String[]{"costEntry.price","costEntry.coefficient"});
		
		StringBuffer sql=new StringBuffer("SELECT DISTINCT ");
		sql.append("\"COSTACCOUNT\".FLongNumber AS \"COSTACCOUNT.LONGNUMBER\",  ");
		sql.append("\"COSTACCOUNT\".FName_l2 AS \"COSTACCOUNT.NAME\",  ");
		sql.append("\"COSTENTRY\".FEntryName AS \"COSTENTRY.ENTRYNAME\",  ");
		sql.append("\"COSTENTRY\".FSimpleName AS \"COSTENTRY.SIMPLENAME\",  ");
		sql.append("\"COSTENTRY\".FPrice AS \"COSTENTRY.PRICE\",  ");
		sql.append("\"COSTENTRY\".FCoefficient AS \"COSTENTRY.COEFFICIENT\",  ");
		sql.append("\"PROJECTTYPE\".FName_l2 AS \"PROJECTTYPE.NAME\",  ");
		sql.append("\"ORGUNIT\".FNumber AS \"ORGUNIT.NUMBER\",  ");
		sql.append("\"ORGUNIT\".FName_l2 AS \"ORGUNIT.NAME\",  ");
		sql.append("\"TEMPLATEMEASURECOST\".FProjectTypeID AS \"PROJECTTYPE.ID\",  ");
		sql.append("\"PRODUCT\".FName_l2 AS \"PRODUCT.NAME\" ");

		sql.append("FROM T_AIM_TemplateMeasureCost AS \"TEMPLATEMEASURECOST\" ");

		sql.append("LEFT OUTER JOIN T_ORG_BaseUnit AS \"ORGUNIT\" ");
		sql.append("ON \"TEMPLATEMEASURECOST\".FOrgUnitID = \"ORGUNIT\".FID ");

		sql.append("LEFT OUTER JOIN T_FDC_ProjectType AS \"PROJECTTYPE\" ");
		sql.append("ON \"TEMPLATEMEASURECOST\".FProjectTypeID = \"PROJECTTYPE\".FID ");

		sql.append("LEFT OUTER JOIN T_AIM_TemplateMeasureEntry AS \"COSTENTRY\" ");
		sql.append("ON \"TEMPLATEMEASURECOST\".FID = \"COSTENTRY\".FHeadID ");

		sql.append("INNER JOIN T_FDC_CurProject AS \"PROJECT\" ");
		sql.append("ON \"TEMPLATEMEASURECOST\".FProjectID = \"PROJECT\".FID ");

		sql.append("LEFT OUTER JOIN T_FDC_CostAccount AS \"COSTACCOUNT\" ");
		sql.append("ON \"COSTENTRY\".FCostAccountID = \"COSTACCOUNT\".FID ");

		sql.append("LEFT OUTER JOIN T_FDC_ProductType AS \"PRODUCT\" ");
		sql.append("ON \"COSTENTRY\".FProductID = \"PRODUCT\".FID ");
		
		sql.append("WHERE ");
		if(getUIContext().get("isAimMeasure").equals(Boolean.TRUE)){
			sql.append(" \"TEMPLATEMEASURECOST\".fisAimMeasure=1 and ");
		}else{
			sql.append(" \"TEMPLATEMEASURECOST\".fisAimMeasure=0 and ");
		}
		String acctLongNumber=getFilterValue("costAccount.longNumber");
		if(acctLongNumber!=null){
			sql.append(" \"COSTACCOUNT\".FLongNumber='"+acctLongNumber+"'");
		}else{
			MsgBox.showError("成本科目编码为空");
			SysUtil.abort();
		}
		String projectId = this.getSelectProjectId();
		if (projectId != null) {
			sql.append(" and \"TEMPLATEMEASURECOST\".FProjectID='"+projectId+"'");
		} else {
			String orgId = getSelectOrgId();
			if (orgId != null) {
				sql.append(" and \"TEMPLATEMEASURECOST\".FOrgUnitID='"+orgId+"'");
			} 
		}
		
		String productId=getFilterValue("product.id");
		if(productId!=null){
			sql.append(" and \"COSTENTRY\".FProductID='"+productId+"'");
		}
		String index=getFilterValue("index");
		if(index!=null){
			sql.append(" and \"COSTENTRY\".fsimpleName='"+index+"'");
		}
		String projectTypeId=getFilterValue("projectType.id");
		if(projectTypeId!=null){
			sql.append(" and \"PROJECTTYPE\".FID='"+projectTypeId+"'");
		}
		
		IRowSet rowSet = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(sql.toString(), null);
		if(rowSet.size()>0){
			while(rowSet.next()){
				IRow row = tblMain.addRow();
				loadRow(row, rowSet);
			}
		}
		
		
/*		EntityViewInfo view=new EntityViewInfo();
		view.put("selector", getSelectors());
		view.setFilter(getMainFilter());
		view.getSorter().add(new SorterItemInfo("createTime"));
		TemplateMeasureEntryCollection c = TemplateMeasureEntryFactory.getRemoteInstance().getTemplateMeasureEntryCollection(view);
		for(Iterator iter=c.iterator();iter.hasNext();){
			TemplateMeasureEntryInfo entry = (TemplateMeasureEntryInfo)iter.next();
			IRow row = tblMain.addRow();
			loadRow(row,entry);
		}*/
		
	}
	private void loadRow(IRow row,TemplateMeasureEntryInfo entry){
		row.setUserObject(entry);
		row.getCell("costAccount.longNumber").setValue(entry.getCostAccount().getLongNumber().replaceAll("!", "\\."));
		row.getCell("costAccount.name").setValue(entry.getCostAccount().getName());
		row.getCell("costEntry.entryName").setValue(entry.getEntryName());
		row.getCell("costEntry.simpleName").setValue(getIndexName(entry.getSimpleName()));
		row.getCell("costEntry.price").setValue(entry.getPrice());
		row.getCell("costEntry.coefficient").setValue(entry.getCoefficient());
		ProjectTypeInfo projectType = entry.getHead().getProjectType();
		if(projectType!=null){
			row.getCell("projectType.name").setValue(projectType.getName());
		}
		row.getCell("orgUnit.number").setValue(entry.getHead().getOrgUnit().getNumber());
		row.getCell("orgUnit.name").setValue(entry.getHead().getOrgUnit().getName());
		if(entry.getProduct()!=null){
			row.getCell("product.name").setValue(entry.getProduct().getName());
		}
		
	}
	
	private void loadRow(IRow row,IRowSet rowSet) throws Exception{
		if(rowSet.getString("COSTACCOUNT.LONGNUMBER")!=null){
			row.getCell("costAccount.longNumber").setValue(rowSet.getString("COSTACCOUNT.LONGNUMBER").replaceAll("!", "\\."));
		}
		row.getCell("costAccount.name").setValue(rowSet.getString("COSTACCOUNT.NAME"));
		row.getCell("costEntry.entryName").setValue(rowSet.getString("COSTENTRY.ENTRYNAME"));
		row.getCell("costEntry.simpleName").setValue(getIndexName(rowSet.getString("COSTENTRY.SIMPLENAME")));
		row.getCell("costEntry.price").setValue(rowSet.getString("COSTENTRY.PRICE"));
		row.getCell("costEntry.coefficient").setValue(rowSet.getString("COSTENTRY.COEFFICIENT"));
		String projectType = rowSet.getString("PROJECTTYPE.NAME");
		if(projectType!=null){
			row.getCell("projectType.name").setValue(projectType);
		}
		row.getCell("orgUnit.number").setValue(rowSet.getString("ORGUNIT.NUMBER"));
		row.getCell("orgUnit.name").setValue(rowSet.getString("ORGUNIT.NAME"));
		row.getCell("product.name").setValue(rowSet.getString("PRODUCT.NAME"));
		
	}
	protected FilterInfo getMainFilter() throws Exception {
		String projectId = this.getSelectProjectId();
		FilterInfo filter = new FilterInfo();
		if (projectId != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("head.project.id", projectId));
		} else {
			String orgId = getSelectOrgId();
			if (orgId != null) {
				filter.appendFilterItem("head.orgUnit.id", orgId);
			} else {
				// filter.appendFilterItem("orgUnit", orgId);
			}
		}
		
		String productId=getFilterValue("product.id");
		if(productId!=null){
			filter.appendFilterItem("product.id", productId);
		}
		String acctLongNumber=getFilterValue("costAccount.longNumber");
		if(acctLongNumber!=null){
			filter.appendFilterItem("costAccount.longNumber", acctLongNumber);
		}
		String index=getFilterValue("index");
		if(index!=null){
			filter.appendFilterItem("simpleName", index);
		}
		String projectTypeId=getFilterValue("projectType.id");
		if(projectTypeId!=null){
			filter.appendFilterItem("head.projectType.id", projectTypeId);
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

	public Object getData() {
		//是测算数据返回测算的分录，否则返回一个数组
		if(this.tabPane.getSelectedIndex()==1){
			TemplateMeasureEntryCollection entrys = new TemplateMeasureEntryCollection();
			int[] selectedRows = KDTableUtil.getSelectedRows(tblMain);
			for (int i = 0; i < selectedRows.length; i++) {
				TemplateMeasureEntryInfo entry = new TemplateMeasureEntryInfo();//(TemplateMeasureEntryInfo)tblMain.getRow(selectedRows[i]).getUserObject();
				Object obj = tblMain.getRow(selectedRows[i]).getCell("costEntry.price")
						.getValue();
				if (obj != null) {
					entry.setPrice(FDCHelper.toBigDecimal(obj));
				}
				obj = tblMain.getRow(selectedRows[i]).getCell("costEntry.coefficient").getValue();
				if (obj != null) {
					entry.setCoefficient(FDCHelper.toBigDecimal(obj));
				}
				
				entrys.add(entry);
	
			}
			return entrys;
		}else{
			Boolean isPrice=(Boolean)getUIContext().get("isPrice");
			if(isPrice==null) return null;
			if(isPrice.booleanValue()){
				ListingItemPriceQueryUI ui=(ListingItemPriceQueryUI) uiObj;
				if(ui==null){
					return null;
				}
				return ui.getSelectSumValue();
			}else{
				//系数
				ProjectIndexQueryUI ui=(ProjectIndexQueryUI) uiObj;
				if(ui==null||ui.getMainTable()==null){
					return null;
				}
				IRow selectedRow = KDTableUtil.getSelectedRow(ui.getMainTable());
				if(selectedRow==null){
					return null;
				}
//				ProjectIndexDataEntryInfo info = new ProjectIndexDataEntryInfo();
//				Map resMap = new HashMap();
				Object value = selectedRow.getCell("entries.indexValue").getValue();
				Object value1 = selectedRow.getCell("apportionType.name").getValue();
				AimMeasureCostEditUI.indexName = value1.toString();
//				resMap.put("entries.indexValue", FDCHelper.toBigDecimal(value,4));
//				resMap.put("apportionType.name", value1);
//				info.setIndexValue(FDCHelper.toBigDecimal(value,4));
//				info.setRemark(value1.toString());
				return value !=null?new Object[]{FDCHelper.toBigDecimal(value,4)}:null;
			}
		}
	}
	
	public SelectorItemCollection getSelectors() {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add("*");
        sic.add(new SelectorItemInfo("costAccount.longNumber"));
        sic.add(new SelectorItemInfo("costAccount.name"));
        sic.add(new SelectorItemInfo("entryName"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("price"));
        sic.add(new SelectorItemInfo("coefficient"));
        sic.add(new SelectorItemInfo("head.projectType.name"));
        sic.add(new SelectorItemInfo("head.orgUnit.number"));
        sic.add(new SelectorItemInfo("head.orgUnit.name"));
        sic.add(new SelectorItemInfo("product.name"));
        sic.add(new SelectorItemInfo("id"));
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
		if(itemsSix==null){
			itemsSix=Item.SIXITEMS;
		}
		
		if(itemsMain==null){
			itemsMain=Item.PRODUCTITEMS;
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

	private void loadDBpanel(KDPanel panel){
		Map context = getUIContext();
		Boolean isPrice=(Boolean)context.get("isPrice");
		String uiName="com.kingdee.eas.fdc.basedata.client.ProjectIndexDataMntUI";
		uiName="com.kingdee.eas.fdc.aimcost.client.ProjectIndexQueryUI";
		if(isPrice!=null&&isPrice.booleanValue()){
			uiName="com.kingdee.eas.fdc.invite.client.ListingItemPriceQueryUI";
//			tabPane.remove(1);
//			return;
		}
		
		try {
			context.put("Owner", this);
			uiObj = UIFactoryHelper.initUIObject(uiName, context, null, OprtState.VIEW);
			try {
				Method method = uiObj.getClass().getMethod("getMainTable", new Class[]{});
				com.kingdee.bos.ctrl.kdf.table.KDTable table = (com.kingdee.bos.ctrl.kdf.table.KDTable)method.invoke(uiObj, new Object[]{});
				table.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
		            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
		                try {
		                    tblMain_tableClicked(e);
		                } catch (Exception exc) {
		                    handUIException(exc);
		                } finally {
		                }
		            }
		        });
			} catch (Exception e) {
				e.printStackTrace();
			} 
			panel.setLayout(new BorderLayout());
			final CoreUIObject obj = (CoreUIObject)uiObj;
//			obj.setPreferredSize(new Dimension(300,40));
			panel.add(new KDScrollPane(obj),BorderLayout.CENTER);
			hasLoadDBPanel=true;
		} catch (UIException e) {
			handUIException(e);
			hasLoadDBPanel=false;
		}
	}
	
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
//		super.actionQuery_actionPerformed(e);fprojectperiod
		boolean init=false;
		if(uiObj==null){
			loadDBpanel(this.pnlDB);
			init=true;
		}
		if(!init&&uiObj instanceof ListUI){
			((ListUI)uiObj).actionQuery_actionPerformed(e);
		}
	}
	
	private boolean hasLoadDBPanel=false;
	protected void tabPane_stateChanged(ChangeEvent e) throws Exception {
//		if(tabPane.getSelectedIndex()==0&&!hasLoadDBPanel){
//			loadDBpanel(this.pnlDB);
//		}
//		Boolean isPrice=(Boolean)getUIContext().get("isPrice");
//		if(isPrice!=null&&isPrice.booleanValue()){
//			actionQuery.setEnabled(false);
//			return;
//		}

//		if(tabPane.getSelectedIndex()==0){
//			actionQuery.setEnabled(true);
//		}
//		if(tabPane.getSelectedIndex()==1){
//			actionQuery.setEnabled(true);
//		}
		
	}
	public boolean destroyWindow() {
		isCancel=true;
		return super.destroyWindow();
	}
}