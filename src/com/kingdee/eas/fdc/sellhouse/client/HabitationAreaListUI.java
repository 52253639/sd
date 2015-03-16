/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.HabitationAreaFactory;
import com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class HabitationAreaListUI extends AbstractHabitationAreaListUI
{
    private static final Logger logger = CoreUIObject.getLogger(HabitationAreaListUI.class);
    
    /**
     * output class constructor
     */
    public HabitationAreaListUI() throws Exception
    {
        super();
    }



	public void onLoad() throws Exception {
		super.onLoad();
		
		
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		
		
		this.tblMain.getColumn("id").getStyleAttributes().setHided(true);	
		this.setIncludeAllChildren(true);
	}


	protected String getRootName() {
		return "居住区域";
	}



	protected void refresh(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		super.refresh(e);
		
		
/*        ItemAction action = getActionFromActionEvent(e);
        if (action.equals(actionRemove) || action.equals(actionEdit) || action.equals(this.actionAddNew))
        {
			ActionEvent evt = new ActionEvent(this.btnRefresh, 0, "Refresh");
	        refresh(evt);
	        //this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
        }*/
	}

		
	protected boolean isIgnoreCUFilter() {
		return true;
		//return super.isIgnoreCUFilter();
	}
	
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,	EntityViewInfo viewInfo) {
/*		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
		
		FilterInfo filter = new FilterInfo();
		if(thisNode!=null && !thisNode.isRoot())  {			
			HabitationAreaInfo nodeInfo = (HabitationAreaInfo)thisNode.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("parent.id",nodeInfo.getId().toString()));
		}
		viewInfo = (EntityViewInfo)this.mainQuery.clone();
		if(viewInfo.getFilter()==null)
			viewInfo.setFilter(filter);
		else{
			try {
				viewInfo.getFilter().mergeFilter(filter,"AND");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}	*/
//		FilterInfo filter  = viewInfo.getFilter();
//		if(filter!=null) {
//			filter.getFilterItems().get
//			filter.getFilterItems().get(0).get
//			
//		}
		this.tblMain.getColumn("id").getStyleAttributes().setHided(true);	
		
		FilterInfo filter =  viewInfo.getFilter();
		if(filter!=null) {
			FilterItemCollection filterColl = filter.getFilterItems();
			for(int i=0;i<filterColl.size();i++) {
				FilterItemInfo filterItem = filterColl.get(i);
				if(filterItem.getPropertyName().equals("longNumber")){
					filterItem.setCompareValue(((String)filterItem.getCompareValue()).replaceAll("!", "."));
				}
			}
		}		

		return super.getQueryExecutor(queryPK, viewInfo);
	}
	

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		//检查是否已被客户资料引用
		String idStr = this.getSelectedKeyValue();
		if(idStr!=null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("habitationArea.id",idStr));
			if(FDCCustomerFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被客户资料引用，禁止删除!");
				return;
			}
		}
		
		super.actionRemove_actionPerformed(e);
	}

	
	
	protected String getEditUIModal() {	
		return UIFactoryName.MODEL;
	}
	

	protected ITreeBase getTreeInterface() throws Exception {
		return HabitationAreaFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return HabitationAreaEditUI.class.getName();
	}

}