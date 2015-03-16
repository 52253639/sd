package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeCollection;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.MsgBox;

public class PropertyDoSchemeListUI extends AbstractPropertyDoSchemeListUI
{
	
    private static final Logger logger = CoreUIObject.getLogger(PropertyDoSchemeListUI.class);

    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    
    public PropertyDoSchemeListUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	actionAddNew.setEnabled(false);
    	actionEdit.setEnabled(true);
    	actionRemove.setEnabled(true);
    	
    	//非售楼组织不能操作
		if (!FDCSysContext.getInstance().checkIsSHEOrg()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
		}
		
		
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
    }
    
    /**
     * 描述：解决因为树节点不为空而引起的刷新中断,treeBuilder为空!
     */
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
    
    protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.treeMain.setSelectionRow(0);
	}
    
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
//		FilterInfo filter=new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			//非售楼组织不能操作
			if (FDCSysContext.getInstance().checkIsSHEOrg()) {
//			filter.getFilterItems().add(new FilterItemInfo("project.id",((SellProjectInfo)node.getUserObject()).getId()));
			actionAddNew.setEnabled(true);
			}
		}
//		else if(node.getUserObject() instanceof OrgStructureInfo){
//			Map sellProMap = FDCTreeHelper.getAllObjectIdMap(node, "SellProject");
//			Iterator iter = sellProMap.keySet().iterator();
//			Set sellProIdSet = new HashSet();
//			while(iter.hasNext())
//				sellProIdSet.add(iter.next());
//			if(sellProIdSet.size()>0)
//				filter.getFilterItems().add(new FilterItemInfo("project.id", sellProIdSet, CompareType.INCLUDE));
//			else
//				filter.getFilterItems().add(new FilterItemInfo("project.id", null));
//			actionAddNew.setEnabled(false);
//		} 
		else {
			actionAddNew.setEnabled(false);
		}
//		mainQuery.setFilter(filter);
		this.execQuery();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo project = (SellProjectInfo) node
						.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", project.getId()
								.toString()));
				/*String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject")
						.keySet());
				if (allSpIdStr.trim().length() == 0) {
					allSpIdStr = "'nullnull'";
				}

				filter.getFilterItems().add(new FilterItemInfo("project.id", allSpIdStr, CompareType.INNER));*/
				//非售楼组织不能操作
				if (FDCSysContext.getInstance().checkIsSHEOrg()) {
					this.actionAddNew.setEnabled(true);
				}
			}else if(node.getUserObject() instanceof OrgStructureInfo){
				
				String orgUnitIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper
						.getAllObjectIdMap(node, "SellProject").keySet());
				if (orgUnitIdStr.trim().length() == 0) {
					orgUnitIdStr = "'nullnull'";
				}
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", orgUnitIdStr,
								CompareType.INNER));
				
			}else {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", null));
				this.actionAddNew.setEnabled(false);
			}
		}
		// 合并查询条件
		viewInfo = (EntityViewInfo) mainQuery.clone();
		try {
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof String) {
			MsgBox.showInfo("请选择具体销售项目！");
			this.abort();
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
					.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
		super.prepareUIContext(uiContext, e);
	}
	
	protected String getEditUIName() {
		return PropertyDoSchemeEditUI.class.getName();
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}

    protected ICoreBase getBizInterface() throws Exception
    {
        return PropertyDoSchemeFactory.getRemoteInstance();
    }

    protected ITreeBase getTreeInterface() throws Exception
    {
        return null;
    }

    protected String getLongNumberFieldName()
    {
        return "number";
    }

    protected String getRootName()
    {
        return "产权办理方案";
    }

    protected IObjectValue createNewData()
    {
        PropertyDoSchemeInfo objectValue = new PropertyDoSchemeInfo();
		
        return objectValue;
    }
    
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList = this.getSelectedIdValues();
		if (idList != null && idList.size()>0) {
			isSchemeEnabled(idList,false);
			PropertyDoSchemeFactory.getRemoteInstance().setEnabled(idList, false);
			MsgBox.showInfo("禁用成功!");
		}else{
			MsgBox.showInfo("请选择记录!");
			return;
		}
			
		String id = "";
		for (int i = 0; i < idList.size(); i++) {
			id = idList.get(i).toString();
			CacheServiceFactory.getInstance().discardType(new ObjectUuidPK(id).getObjectType());
		}
	
		this.refresh(null);
	}
    
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		
		List idList = this.getSelectedIdValues();
		if (idList != null && idList.size()>0) {
			isSchemeEnabled(idList,true);
			PropertyDoSchemeFactory.getRemoteInstance().setEnabled(idList, true);
			MsgBox.showInfo("启用成功!");
		}else{
			MsgBox.showInfo("请选择记录!");
			return;
		}
		String id = "";
		for (int i = 0; i < idList.size(); i++) {
			id = idList.get(i).toString();
			CacheServiceFactory.getInstance().discardType(new ObjectUuidPK(id).getObjectType());
		}
	
		this.refresh(null);
	}
	
	private void isSchemeEnabled(List idList,boolean isCancle) throws BOSException {
		StringBuffer idStr = new StringBuffer();
		for (int i = 0; i < idList.size(); i++) {
			idStr.append("'");
			idStr.append(idList.get(0).toString());
			idStr.append("'");
			if(i!=idList.size()-1){
				idStr.append(",");
			}
		}
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("isEnabled"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",idStr.toString(),CompareType.INNER));
		view.setFilter(filter);
		view.setSelector(coll);
		PropertyDoSchemeCollection priceColl=PropertyDoSchemeFactory.getRemoteInstance().getPropertyDoSchemeCollection(view);
		boolean isEnabled = true;
		for (int i = 0; i < priceColl.size(); i++) {
			PropertyDoSchemeInfo info  = priceColl.get(i);
			if(info!=null){
				if(isCancle){
					if(info.isIsEnabled()){
						isEnabled = false;
					}
				}else{
					if(!info.isIsEnabled()){
						isEnabled = false;
					}
				}
				
			}
		}
		
		if(isCancle){
			if(!isEnabled){
				FDCMsgBox.showWarning(this,"所选记录中已有启用的方案,请检查!");
				this.abort();
			}
		}else{
			if(!isEnabled){
				FDCMsgBox.showWarning(this,"所选记录中已有禁用的方案,请检查!");
				this.abort();
			}
		}
		
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();
//		if (saleOrg.isIsBizUnit() && activeRowIndex != -1) {
		if (activeRowIndex != -1) {
			if (this.tblMain.getRow(activeRowIndex).getCell("isEnabled") != null) {
				Boolean value = (Boolean) this.tblMain.getCell(activeRowIndex,
						"isEnabled").getValue();
				if (value == null) {
					value = Boolean.FALSE;
				}
				boolean status = (value).booleanValue();
				actionCancelCancel.setEnabled(!status);
				actionCancel.setEnabled(status);
			}
		} else {
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] {
					actionCancel, actionCancelCancel }, false);
		}
	}
	
	private boolean checkHasCancel() {
		boolean flag = false;
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("project.id",getSelectProject().getId().toString()));
		try {
			flag = PropertyDoSchemeFactory.getRemoteInstance().exists(filter);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	private SellProjectInfo getSelectProject() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return null;
		}
		SellProjectInfo sellProject = null;
		if (node.getUserObject() instanceof SellProjectInfo) {
			sellProject = (SellProjectInfo) node
					.getUserObject();
		}
		return sellProject;
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkIsEnable();
		super.actionEdit_actionPerformed(e);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkIsEnable();
		checkSchemeReference();
		
		super.actionRemove_actionPerformed(e);
	}
	
	protected void checkSchemeReference() throws EASBizException, BOSException
	{
		if(tblMain.getSelectManager().getActiveRowIndex() >=0)
		{
			IRow tmpRow = tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex());
			String tmpPk = String.valueOf(tmpRow.getCell("id").getValue());
			
			FilterInfo bookFilter = new FilterInfo();
			bookFilter.getFilterItems().add(new FilterItemInfo("propertyDoScheme.id", tmpPk));
			if(RoomPropertyBookFactory.getRemoteInstance().exists(bookFilter))
			{
				MsgBox.showWarning("所选项已经被引用，不能执行此操作！");
				abort();
			}
			
			FilterInfo batchFilter = new FilterInfo();
			batchFilter.getFilterItems().add(new FilterItemInfo("scheme.id", tmpPk));
			if(RoomPropertyBatchFactory.getRemoteInstance().exists(batchFilter))
			{
				MsgBox.showWarning("所选项已经被引用，不能执行此操作！");
				abort();
			}
		}
	}
	
	protected void checkIsEnable()
	{
		if(tblMain.getSelectManager().getActiveRowIndex() >=0)
		{
			IRow tmpRow = tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex());
			Boolean isEnable = (Boolean)(tmpRow.getCell("isEnabled").getValue());
			
			if(isEnable.booleanValue())
			{
				MsgBox.showWarning("所选项为启用状态，不能执行此操作！");
				abort();
			}
		}
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		/*if(getExistChild()>0){
			FDCMsgBox.showWarning(this,"非末级项目不能新增!");
			this.abort();
		}*/
		super.actionAddNew_actionPerformed(e);
	}
	

	private int getExistChild() {
		DefaultKingdeeTreeNode node = null;
		int number = 0;

		node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null) {
			number = node.getChildCount();
		}
		return number;
	}


}