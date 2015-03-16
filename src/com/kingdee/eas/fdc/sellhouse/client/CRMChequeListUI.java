/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.CRMChequeFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class CRMChequeListUI extends AbstractCRMChequeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CRMChequeListUI.class);
    private  SellProjectInfo sellproject = null;
  
    
    protected void initTree() throws Exception
	{
    	this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.treeMain.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
	}
    public CRMChequeListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.actionAttachment.setVisible(false);
    	this.actionAuditResult.setVisible(false);
    	this.actionCopyTo.setVisible(false);
    	this.actionCreateTo.setVisible(false);
    	
    	this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	initTree();
    }
    public void storeFields()
    {
        super.storeFields();
    }

    protected ICoreBase getBizInterface() throws Exception {
    	return CRMChequeFactory.getRemoteInstance();
    }
    
    protected String getEditUIName() {
    	return CRMChequeEditUI.class.getName();
    }

    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
    	if(node != null && node.getUserObject() instanceof OrgStructureInfo){
    		//只有点击项目节点才能登记
			this.btnBookIn.setEnabled(false);
//			this.btnBlankOut.setEnabled(false);
//			this.btnPick.setEnabled(false);
//			this.btn.setEnabled(false);
    	}else if(node != null && node.getUserObject() instanceof SellProjectInfo){
    		this.btnBookIn.setEnabled(true);
//    		this.btnBlankOut.setEnabled(true);
//    		this.btnPick.setEnabled(true);
    		sellproject = (SellProjectInfo)node.getUserObject();
    	}
    	this.execQuery();
    }

    /**
     * 登记
     */
    public void actionBookIn_actionPerformed(ActionEvent e) throws Exception {
    	Map uiContext =getUIContext();
    	uiContext.put(UIContext.OWNER, this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CRMChequeBookInUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		refresh(null);
    }
    
    /**
     * 领用
     */
    public void actionPick_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	Map uiContext = getUIContext();
    	uiContext.put("chequeid",getSelectedKeyValue("id") );
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ChequePickEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		refresh(null);
    }
    
    /**
     * 核销
     */
    public void actionVC_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	Map uiContext = getUIContext();
    	uiContext.put("chequeid",getSelectedKeyValue("id") );
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ChequeVCEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		refresh(null);
    }
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		viewInfo = (EntityViewInfo) this.mainQuery.clone();

		FilterInfo filter = new FilterInfo();
		if (node != null && node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo org = (OrgStructureInfo) node.getUserObject();			
			String orgId = org.getUnit().getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("keepOrgUnit.id", orgId));
			
		} else if(node != null && node.getUserObject() instanceof SellProjectInfo){
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			
			String sqlFilterStr = "select FCRMChequeID from T_SHE_ChequeSellProjectEntry CEEntry " + 
									"left join t_she_sellProject Project on CEEntry.FSellProjectID = Project.Fid " + 
										" where Project.FlongNumber like '"+sellProject.getLongNumber()+"%' ";
			
			filter.getFilterItems().add(new FilterItemInfo("id",sqlFilterStr,CompareType.INNER ));
			filter.getFilterItems().add(new FilterItemInfo("keepOrgUnit.id",SysContext.getSysContext().getCurrentSaleUnit().getId()));
			this.getUIContext().put("sellProject", sellProject);
			
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		}

		if (viewInfo.getFilter() != null) {
			try {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} catch (BOSException e) {
				handleException(e);
			}
		} else {
			viewInfo.setFilter(filter);
		}
	
/*		viewInfo.getSorter().clear();
		viewInfo.getSorter().add(new SorterItemInfo("number"));*/
		//-----------------
		return super.getQueryExecutor(queryPK, viewInfo);
    }
    protected String getEditUIModal()
    {
            return UIFactoryName.MODEL;
    }
    
    private int selectIndex = -1;
    protected String getSelectedKeyValue(String keyFiledName)
    {
        int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
        selectIndex=-1;
        if (selectRows.length > 0)
        {
        	selectIndex=selectRows[0];
        }
        return ListUiHelper.getSelectedKeyValue(selectRows,this.tblMain,keyFiledName);
    }
    
    protected boolean isIgnoreCUFilter() {
    	return true;
    }
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	uiContext.put("sellProject", sellproject);
    }
    
    protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
/*    	 if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
    		 DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent(); 
    		 if(node == null||!(node.getUserObject() instanceof SellProjectInfo)){
    			 FDCMsgBox.showWarning(this,"请先选择左边的项目节点，再查看！");
    			 this.abort();
    		 }
         }*/
    	super.tblMain_tableClicked(e);
    }
}