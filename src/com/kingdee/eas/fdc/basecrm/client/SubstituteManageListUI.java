/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.SubstituteAdjustFactory;
import com.kingdee.eas.fdc.basecrm.SubstituteAdjustInfo;
import com.kingdee.eas.fdc.basecrm.SubstituteTransfOutFactory;
import com.kingdee.eas.fdc.basecrm.SubstituteTransfOutInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class SubstituteManageListUI extends AbstractSubstituteManageListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SubstituteManageListUI.class);
    protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
	
    public SubstituteManageListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	this.substituteAdjustQuery = new EntityViewInfo();
    	this.tblAdjust.getStyleAttributes().setLocked(true);
    	this.tblAdjust.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    	
    	this.actionTraceUp.setVisible(false);
/*    	this.actionCreateTo.setVisible(false);
    	this.actionTraceDown.setVisible(false);
    	this.actionCopyTo.setVisible(false);*/
    	
    	this.actionAudit.setEnabled(true);
    	this.actionUnAudit.setEnabled(true);
    	
    	
		this.kDTree.setModel(FDCTreeHelper.getBuildingTreeForSHE(actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.kDTree.expandAllNodes(true, (TreeNode) this.kDTree.getModel().getRoot());
		this.kDTree.setSelectionNode((DefaultKingdeeTreeNode)this.kDTree.getModel().getRoot());
		
		//非售楼组织不能操作
		if(!isSaleHouseOrg) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
			this.btnAdAdd.setEnabled(false);
			this.btnAdDelete.setEnabled(false);
			this.btnAdModify.setEnabled(false);
			this.btnAdTransfTo.setEnabled(false);
		}
    }
    
    protected void kDTree_valueChanged(TreeSelectionEvent e) throws Exception {
    	int indexNum = this.kDTabbedPane1.getSelectedIndex();
    	if(indexNum==1){
    		this.tblAdjust.removeRows();
    	}else{ 
    		this.execQuery();
    	}
    }    
    
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
    		EntityViewInfo viewInfo) {
    	int indexNum = this.kDTabbedPane1.getSelectedIndex();
    	FilterInfo filter = new FilterInfo();
    	DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)this.kDTree.getLastSelectedPathComponent();
    	this.actionAddNew.setEnabled(false);
    	this.btnAdAdd.setEnabled(false);
    	if(thisNode!=null) {  
    		if(thisNode.getUserObject() instanceof SellProjectInfo){
	    		SellProjectInfo spInfo = (SellProjectInfo)thisNode.getUserObject();
	    		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",spInfo.getId()));
    			if(isSaleHouseOrg) {
    				if(spInfo.isIsLeaf()) {
	    				if(indexNum==1)
	    					this.btnAdAdd.setEnabled(true);
	    				else
	    					this.actionAddNew.setEnabled(true);
    				}
    			}
	    	}else if(thisNode.getUserObject() instanceof BuildingInfo) {
	    		if(isSaleHouseOrg) {
	    			if(indexNum==1)
	    				this.btnAdAdd.setEnabled(true);
	    			else
	    				this.actionAddNew.setEnabled(true);
	    		}
	    		BuildingInfo build = (BuildingInfo)thisNode.getUserObject();
	    		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",build.getSellProject().getId()));
	    		filter.getFilterItems().add(new FilterItemInfo("building.id",build.getId()));
	    	}else{
	    		Map spIdMap = FDCTreeHelper.getAllObjectIdMap(thisNode, "SellProject");
	    		if(spIdMap.size()>0)
	    			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",FDCTreeHelper.getStringFromSet(spIdMap.keySet()),CompareType.INNER));
	    		else
	    			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",null));
	    	}
    	}else{
    		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",null));
    	}
    	
    	if(indexNum==1){
    		viewInfo.setFilter(filter);
    	}else{
			viewInfo = (EntityViewInfo)this.mainQuery.clone();
			if(viewInfo.getFilter()==null)
				viewInfo.setFilter(filter);
			else{
				try {
					viewInfo.getFilter().mergeFilter(filter,"AND");
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
    	}
    	
    	IQueryExecutor queryExec = super.getQueryExecutor(queryPK, viewInfo);
    	queryExec.option().isAutoTranslateEnum = true;
    	return queryExec;
    }
    
    
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }
    
    protected void tblAdjust_tableClicked(KDTMouseEvent e) throws Exception {
    	if(e.getType()!=1) return;
    	if(e.getClickCount()!=2) return;
    	IRow curRow = KDTableUtil.getSelectedRow(this.tblAdjust);
    	String idStr = (String)curRow.getCell("id").getValue();
    	if(idStr==null) return;    	
    	
    	UIContext uiContext = new UIContext(this); 		
		uiContext.put(UIContext.ID, idStr);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(SubstituteAdjustEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		this.tblAdjust.refresh();
    }

    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)this.kDTree.getLastSelectedPathComponent();
    	if(thisNode!=null) { 
	    	if(thisNode.getUserObject() instanceof SellProjectInfo){
	    		SellProjectInfo spInfo = (SellProjectInfo)thisNode.getUserObject();
	    		uiContext.put("SellProjectInfo", spInfo);
	    	}else if(thisNode.getUserObject() instanceof BuildingInfo) {
	    		BuildingInfo build = (BuildingInfo)thisNode.getUserObject();
	    		uiContext.put("SellProjectInfo", build.getSellProject());
	    		uiContext.put("BuildingInfo", build);
	    	}
    	}
    	
    	super.prepareUIContext(uiContext, e);
    }
    

    protected void btnAdAdd_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
    	DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)this.kDTree.getLastSelectedPathComponent();
    	if(thisNode!=null) { 
	    	if(thisNode.getUserObject() instanceof SellProjectInfo){
	    		SellProjectInfo spInfo = (SellProjectInfo)thisNode.getUserObject();
	    		uiContext.put("SellProjectInfo", spInfo);
	    	}else if(thisNode.getUserObject() instanceof BuildingInfo) {
	    		BuildingInfo build = (BuildingInfo)thisNode.getUserObject();
	    		uiContext.put("SellProjectInfo", build.getSellProject());
	    		uiContext.put("BuildingInfo", build);
	    	}
    	}
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(SubstituteAdjustEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		this.tblAdjust.refresh();
    }
    
    protected void btnAdDelete_actionPerformed(ActionEvent e) throws Exception {
    	int[] selectRowNums = KDTableUtil.getSelectedRows(this.tblAdjust);
    	if(selectRowNums==null || selectRowNums.length==0) return;
    		
    	for (int i = selectRowNums.length-1; i >= 0; i--) {
			IRow curRow = this.tblAdjust.getRow(selectRowNums[i]);
			String idStr = (String)curRow.getCell("id").getValue();
			if(idStr!=null)
				SubstituteAdjustFactory.getRemoteInstance().delete(new ObjectUuidPK(BOSUuid.read(idStr)));
		}
    	this.tblAdjust.removeRows();
    	this.tblAdjust.refresh();
    }
    
    protected void btnAdModify_actionPerformed(ActionEvent e) throws Exception {
    	IRow curRow = KDTableUtil.getSelectedRow(this.tblAdjust);
    	if(curRow==null) return;
    	String idStr = (String)curRow.getCell("id").getValue();
    	if(idStr==null) return;
    	
    	SubstituteAdjustInfo adjustInfo = SubstituteAdjustFactory.getRemoteInstance()
    						.getSubstituteAdjustInfo("select transfAdjustDate where id = '"+idStr+"' ");
    	if(adjustInfo.getTransfAdjustDate()!=null) {
    		FDCMsgBox.showWarning("单据已经传递，禁止修改！");
    		return;
    	}
    	
    	UIContext uiContext = new UIContext(this);
    	uiContext.put(UIContext.ID, idStr);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
			.create(SubstituteAdjustEditUI.class.getName(), uiContext, null, OprtState.EDIT);
		uiWindow.show();
		this.tblAdjust.refresh();
    }
    
    protected void btnAdTransfTo_actionPerformed(ActionEvent e)
    		throws Exception {
    	IRow curRow = KDTableUtil.getSelectedRow(this.tblAdjust);
    	if(curRow==null) return;    	
    	String idStr = (String)curRow.getCell("id").getValue();
    	SubstituteAdjustInfo currInfo = new SubstituteAdjustInfo();
    	currInfo.setId(BOSUuid.read(idStr));
    	SubstituteAdjustFactory.getRemoteInstance().transfTo(currInfo);
    	FDCMsgBox.showInfo("成功传递!");
    	this.tblAdjust.refresh();
    }
    
    protected void kDTabbedPane1_stateChanged(ChangeEvent e) throws Exception {
    	int indexNum = this.kDTabbedPane1.getSelectedIndex();
    	if(indexNum==1){
    		this.actionView.setEnabled(false);
    		this.actionEdit.setEnabled(false);
    		this.actionRemove.setEnabled(false);
    		this.actionQuery.setEnabled(false);
    		this.actionRefresh.setEnabled(false);
    		this.actionLocate.setEnabled(false);
    		this.actionAttachment.setEnabled(false);
    		this.actionWorkFlowG.setEnabled(false);
    		this.actionAuditResult.setEnabled(false);
    		this.actionCreateTo.setEnabled(false);
    		this.actionTraceDown.setEnabled(false);
    		this.actionAudit.setEnabled(false);
    		this.actionUnAudit.setEnabled(false);
    		this.tblAdjust.removeRows();
    		if(isSaleHouseOrg) {
    			this.actionAddNew.setEnabled(false);
	    		this.btnAdDelete.setEnabled(true);
	    		this.btnAdModify.setEnabled(true);
	    		this.btnAdTransfTo.setEnabled(true);
    		}
    	}else{
    		this.actionView.setEnabled(true);
    		this.actionQuery.setEnabled(true);
    		this.actionRefresh.setEnabled(true);
    		this.actionLocate.setEnabled(true);
    		this.actionAttachment.setEnabled(true);    		
    		this.actionWorkFlowG.setEnabled(true);
    		this.actionAuditResult.setEnabled(true);
    		this.actionCreateTo.setEnabled(true);
    		this.actionTraceDown.setEnabled(true);
    		if(isSaleHouseOrg) {
    			this.actionAddNew.setEnabled(true);
	    		this.actionEdit.setEnabled(true);
	    		this.actionRemove.setEnabled(true);
	    		this.actionAudit.setEnabled(true);
	    		this.actionUnAudit.setEnabled(true);
	    		this.btnAdDelete.setEnabled(false);
	    		this.btnAdModify.setEnabled(false);
	    		this.btnAdTransfTo.setEnabled(false);
    		}
    		this.tblMain.removeRows();
    	}
    	
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	IRow curRow = KDTableUtil.getSelectedRow(this.tblMain);
    	if(curRow==null) return;
    	String idStr = (String)curRow.getCell("id").getValue();
    	if(idStr==null) return;
    	
    	SubstituteTransfOutInfo adjustInfo = SubstituteTransfOutFactory.getRemoteInstance()
    						.getSubstituteTransfOutInfo("select state where id = '"+idStr+"' ");
    	if(adjustInfo.getState()!=null && !adjustInfo.getState().equals(FDCBillStateEnum.SAVED) 
    			&& !adjustInfo.getState().equals(FDCBillStateEnum.SUBMITTED)) {
    		FDCMsgBox.showWarning("单据已经审批，禁止修改！");
    		return; 
    	}     
    	
    	super.actionEdit_actionPerformed(e);
    	this.tblMain.refresh();
    }
    
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {  	
    	List idList = this.getSelectedIdValues();
    	if(idList.size()>0) {
    		for(int i=0;i<idList.size();i++) {
    			String idStr = (String)idList.get(i);
    			SubstituteTransfOutInfo subBillInfo = SubstituteTransfOutFactory.getRemoteInstance()
    						.getSubstituteTransfOutInfo("select state where id = '"+idStr+"' ");
        		if(!subBillInfo.getState().equals(FDCBillStateEnum.SUBMITTED) && !subBillInfo.getState().equals(FDCBillStateEnum.AUDITTING)){
        			FDCMsgBox.showInfo("该单据非提交或审批中状态，禁止审批！");
        			return;
        		}
        		SubstituteTransfOutFactory.getRemoteInstance().audit(BOSUuid.read(idStr));
    		}
    		
    		FDCMsgBox.showInfo("审批成功！");
    		this.refreshList();
    	}
    }
    
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {  	
    	List idList = this.getSelectedIdValues();
    	if(idList.size()>0) {
    		for(int i=0;i<idList.size();i++) {
    			String idStr = (String)idList.get(i);
    			SubstituteTransfOutInfo subBillInfo = SubstituteTransfOutFactory.getRemoteInstance()
    						.getSubstituteTransfOutInfo("select state where id = '"+idStr+"' ");
        		if(!subBillInfo.getState().equals(FDCBillStateEnum.AUDITTED)){
        			FDCMsgBox.showInfo("该单据非已审批状态，禁止反审批！");
        			return;
        		}
        		SubstituteTransfOutFactory.getRemoteInstance().unAudit(idList);
    		}
    		FDCMsgBox.showInfo("反审批成功！");
    		this.refreshList();
    	}
    }
    
    protected ICoreBase getBizInterface() throws Exception {
    	return SubstituteTransfOutFactory.getRemoteInstance();
    }
    
    protected String getEditUIName() {
    	return SubstituteTransfOutEditUI.class.getName(); 
    }
    
    protected boolean isIgnoreCUFilter() {
    	return true;
    }
    
    protected FilterInfo getDefaultFilterForQuery() {
    	//FrameWorkUtils.getF7FilterInfoByAuthorizedOrg(com.kingdee.eas.basedata.org.OrgType.getEnum("Sale"),"saleOrgUnit.id",true);
    	//基类的这个方法比较牛
    	return null;
    }       
    
    public void actionPrintPreview_actionPerformed(ActionEvent e)
    		throws Exception {
    	int indexNum = this.kDTabbedPane1.getSelectedIndex();
    	if(indexNum==1){
    		preparePrintPage(tblAdjust);
    		tblAdjust.getPrintManager().printPreview();    		
    	}else{
    		preparePrintPage(tblMain);
    		tblMain.getPrintManager().printPreview();
    	}
    }
    
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
    	int indexNum = this.kDTabbedPane1.getSelectedIndex();
    	if(indexNum==1){
    		preparePrintPage(tblAdjust);
    		tblAdjust.getPrintManager().print();
    	}else{
    		preparePrintPage(tblMain);
        	tblMain.getPrintManager().print();    		
    	}
    }
    
    
    protected void afterTableFillData(KDTDataRequestEvent e) {
/*    	int indexNum = this.kDTabbedPane1.getSelectedIndex();
    	if(indexNum==0){
			for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
				IRow row = this.tblMain.getRow(i);
				List list = FDCBillStateEnum.getEnumList();
				for (Iterator it = list.iterator(); it.hasNext();) {
					StringEnum enumeration = (StringEnum) it.next();
					if (enumeration.getValue().equals(
							row.getCell("state").getValue().toString())) {
						row.getCell("state").setValue(enumeration);
					}
				}
			}
    	}*/
    }
    
}