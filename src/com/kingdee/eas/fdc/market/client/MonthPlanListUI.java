/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.market.MonthPlanFactory;
import com.kingdee.eas.fdc.market.ValueBreakCollection;
import com.kingdee.eas.fdc.market.ValueBreakFactory;
import com.kingdee.eas.fdc.market.ValueBreakInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.framework.client.ListUIController;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class MonthPlanListUI extends AbstractMonthPlanListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MonthPlanListUI.class);
    
    /**
     * output class constructor
     */
    public MonthPlanListUI() throws Exception
    {
        super();
    }
    protected String[] getLocateNames()
    {
        String[] locateNames = new String[3];
        locateNames[0] = "number";
        locateNames[1] = "name";
        locateNames[2] = "versoin";
        return locateNames;
    }
    public boolean isIgnoreRowCount() {
		return false;
	}
    public void onLoad() throws Exception {
    	actionQuery.setEnabled(false);
    	initTree();
		super.onLoad();
		actionQuery.setEnabled(true);
		
    	btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
    	btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	
    	this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.menuBiz.setVisible(false);
    }
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = getSelectedIdValues();
		((IFDCBill)getBizInterface()).audit(idList);
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = getSelectedIdValues();
		((IFDCBill)getBizInterface()).unAudit(idList);
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	protected String getEditUIName() {
    	return MonthPlanEditUI.class.getName();
    }
	protected ICoreBase getBizInterface() throws Exception {
		return MonthPlanFactory.getRemoteInstance();
	}
	private void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.treeMain.setSelectionRow(0);
	}
	public DefaultKingdeeTreeNode getSelectedTreeNode()
    {
        return (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
    }
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof OrgStructureInfo){
			this.actionAddNew.setEnabled(false);
		}else{
			this.actionAddNew.setEnabled(true);
		}
		if(getSelectedTreeNode()!=null&&getSelectedTreeNode().getUserObject() instanceof SellProjectInfo ){
			sourceTreeNode = (SellProjectInfo)getSelectedTreeNode().getUserObject();
		}else{
			sourceTreeNode = null;
		}
		this.refresh(null);
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK pk,
			EntityViewInfo viewInfo) {
		
		EntityViewInfo view = (EntityViewInfo)viewInfo.clone();
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		FilterInfo  filter = new FilterInfo();
		if(node!=null){
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo info = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", info.getId().toString()));
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id",MeasurePlanTargetListUI.getAllProjectNodeId(node),CompareType.INCLUDE));
			}
		}
		try {
			if(view.getFilter()!=null){
				view.getFilter().mergeFilter(filter, "and");
			}else{
				view.setFilter(filter);
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return super.getQueryExecutor(pk, view);
	}
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	if(sourceTreeNode!=null){
	        UIContext uiContext = new UIContext(this);
	        uiContext.put("ID", null);
	        uiContext.put("sellProject", sourceTreeNode);
	        prepareUIContext(uiContext, e);
	        getUIContext().putAll(uiContext);
	        IUIWindow uiWindow = null;
	        ((ListUIController)super.getUIController()).actionAddNew();
	        uiWindow = ((ListUIController)super.getUIController()).getNavigator().getUIWindow();
	        Window win = SwingUtilities.getWindowAncestor((Component)uiWindow.getUIObject());
	        if(!win.isActive() && (win instanceof JFrame) && ((JFrame)win).getExtendedState() == 1)
	            ((JFrame)win).setExtendedState(0);
	        if(uiWindow != null && isDoRefresh(uiWindow))
	        {
	            if(UtilRequest.isPrepare("ActionRefresh", this))
	                prepareRefresh(null).callHandler();
	            refresh(e);
	            setPreSelecteRow();
	        }
    	}else{
    		MsgBox.showWarning("请选择一个销售项目新增货值年度分解！");
    	}
    }

	private SellProjectInfo sourceTreeNode = null;
	protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
		MeasurePlanTargetListUI.setBtnState(this.tblMain,getBizInterface(),this.actionAudit,this.actionUnAudit,this.actionEdit,this.actionRemove);
	}
	protected void refresh(ActionEvent e) throws Exception{
    	boolean isSelect=false;
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex==-1){
			isSelect=true;
		}
		super.refresh(e);
        if(this.tblMain.getRowCount()==0){
        	this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
        }
        if(isSelect){
        	tblMain.getSelectManager().select(0, 0);
        }
    }
	protected boolean isIgnoreCUFilter() {
    	return true;
    }
}