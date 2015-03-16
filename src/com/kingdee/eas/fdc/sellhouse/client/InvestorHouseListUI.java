/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.InvestorHouseFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class InvestorHouseListUI extends AbstractInvestorHouseListUI
{
    private static final Logger logger = CoreUIObject.getLogger(InvestorHouseListUI.class);
    
    private String saleUserIds = "null";
    
    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    
    public InvestorHouseListUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    protected String getEditUIModal() {
    	return UIFactoryName.MODEL;
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
    	this.btnTrackRecord.setVisible(false);
    	this.btnDelTrackRecord.setVisible(false);
    	
    	this.kDTreeUnit.setModel(FDCTreeHelper.getMarketTree(this.actionOnLoad));
    	this.kDTreeUnit.expandAllNodes(true, (TreeNode)this.kDTreeUnit.getModel().getRoot());    	
    	this.kDTreeUnit.setSelectionRow(0);
    }

	protected String getEditUIName() {
		return InvestorHouseEditUI.class.getName();
	}
	
	public void actionTrackRecord_actionPerformed(ActionEvent e) throws Exception {
		String investorHouseID = this.getSelectedKeyValue();
		InvestorTrackRecordEditUI.showEditUI(this,investorHouseID);
		super.actionTrackRecord_actionPerformed(e);
		
	}
	protected ICoreBase getBizInterface() throws Exception {
		return InvestorHouseFactory.getRemoteInstance();
	}
	
	
	
	protected void kDTreeUnit_valueChanged(TreeSelectionEvent e)
			throws Exception {
		super.kDTreeUnit_valueChanged(e);
		
		this.execQuery();
	}
	
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		try {
			//过滤面板上的条件加上销售顾问的过滤
			viewInfo = (EntityViewInfo)this.mainQuery.clone();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.kDTreeUnit.getLastSelectedPathComponent();
			FilterInfo filter = new FilterInfo();
			saleUserIds = "null";
			if(node!=null)  getAllSaleIds(node);
			filter.getFilterItems().add(new FilterItemInfo("salesman.id",saleUserIds,CompareType.INCLUDE));			
			if(node==null || node.isRoot()) {
				FilterInfo filterNull = new FilterInfo();
				filterNull.getFilterItems().add(new FilterItemInfo("salesman.id",null,CompareType.EQUALS));
				filter.mergeFilter(filterNull, "or");
			}
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
				
		} catch (Exception e) {
			this.handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	
	/**
	 * 查询节点下所有的营销人员
	 * @param treeNode
	 */
	private void getAllSaleIds(DefaultMutableTreeNode treeNode){
		if(treeNode.isLeaf()){ //DefaultMutableTreeNode   DefaultKingdeeTreeNode
			DefaultMutableTreeNode thisNode = (DefaultMutableTreeNode)treeNode;
			if(thisNode.getUserObject() instanceof UserInfo){
				UserInfo user = (UserInfo)thisNode.getUserObject();
				saleUserIds += "," + user.getId().toString();
			}
		}else{
			int childCount = treeNode.getChildCount();
			while(childCount>0) {				
				getAllSaleIds((DefaultMutableTreeNode)treeNode.getChildAt(childCount-1));		
				childCount --;
			}	
		}
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		InvestorHouseListUI.checkHasRelatedByFDCCustomer("investorHouse.id", getSelectedIdValues());
		super.actionRemove_actionPerformed(e);
	}
    
    private static boolean hasRelatedByFDCCustomer(String key, List selectedIds) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(key, FDCHelper.getSetByList(selectedIds),CompareType.INCLUDE));
		return SellProjectFactory.getRemoteInstance().exists(filter);
	}
    
    public static void checkHasRelatedByFDCCustomer(String key, List selectedIds) throws EASBizException, BOSException{
		if(hasRelatedByFDCCustomer(key, selectedIds)){
			MsgBox.showInfo("已经被项目引用不可删除");
			SysUtil.abort();
		}
	}
    
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) kDTreeUnit
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof UserInfo) {
			UserInfo user = (UserInfo) node.getUserObject();
			uiContext.put("user", user);
		}else
		{
			uiContext.put("user",null);
		}
		super.prepareUIContext(uiContext, e);
	}
	
	
	
	

}