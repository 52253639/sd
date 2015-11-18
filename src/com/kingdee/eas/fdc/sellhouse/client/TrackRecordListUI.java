/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TrackRecordListUI extends AbstractTrackRecordListUI
{
    private static final Logger logger = CoreUIObject.getLogger(TrackRecordListUI.class);
    
    /**
     * output class constructor
     */
    public TrackRecordListUI() throws Exception
    {
        super();
    }

    
	public void onLoad() throws Exception {
		super.onLoad();
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		this.actionExcelBatchImport.setEnabled(true);

		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionExcelBatchImport.setVisible(false);
		this.actionView.setVisible(false);
		this.actionLocate.setVisible(false);
		
		this.menuBiz.setVisible(false);
		this.menuWorkFlow.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.btnAuditResult.setVisible(false);
		this.btnWorkFlowG.setVisible(false);

		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		
		this.treeSellProject.setModel(CRMTreeHelper.getSellProjectTree(actionOnLoad,false));
		this.treeSellProject.expandAllNodes(true, (TreeNode) this.treeSellProject.getModel().getRoot());
		if(this.treeSellProject.getRowCount()>0){
			treeSellProject.setSelectionRow(1); 
		}else{
			treeSellProject.setSelectionRow(0); 
		}
//		this.treeSellProject.setModel(FDCTreeHelper.getSellProjectTree(actionOnLoad, null));   //SHEHelper.getSellProjectTree(this.actionOnLoad)
//		this.treeSellProject.expandAllNodes(true,(TreeNode) this.treeSellProject.getModel().getRoot());
	}
    

	protected void treeSellProject_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeSellProject_valueChanged(e);
		
		//this.tblMain.removeRows();
		this.execQuery();		
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		String proId = "nullnull";
		DefaultKingdeeTreeNode projectNode = (DefaultKingdeeTreeNode)this.treeSellProject.getLastSelectedPathComponent();
		if(projectNode !=null && projectNode.getUserObject() instanceof SellProjectInfo){
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)projectNode;
			SellProjectInfo proInfo = (SellProjectInfo)thisNode.getUserObject();
			if(proInfo!=null) proId = proInfo.getId().toString();
		}
		
		if(proId.equals("nullnull")) {
			//从跟进统计表中传递来的销售项目
			String proIdStr = (String)this.getUIContext().get("sellProjectId");
			if(proIdStr!=null) {
				proId = proIdStr;
				//定位到项目树中的该项目
				DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode)this.treeSellProject.getModel().getRoot();
				Enumeration enumeration = rootNode.breadthFirstEnumeration();
				while (enumeration.hasMoreElements()) {
					DefaultKingdeeTreeNode element = (DefaultKingdeeTreeNode) enumeration.nextElement();
					if (element.getUserObject() instanceof SellProjectInfo) {
						SellProjectInfo proInfo = (SellProjectInfo)element.getUserObject();
						if(proInfo.getId().toString().equals(proId))
							this.treeSellProject.setSelectionNode(element);
					}
				}				
				
				this.getUIContext().remove("sellProjectId");  //清除掉
			}
		}
		
		viewInfo = (EntityViewInfo)this.mainQuery.clone();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",proId));
		
		//	从跟进统计表中传递来的 开始和结束时间
		Date beginDate = (Date)this.getUIContext().get("beginDate");
		if(beginDate!=null)  {
			filter.getFilterItems().add(new FilterItemInfo("eventDate",beginDate,CompareType.GREATER_EQUALS));
			this.getUIContext().remove("beginDate");  //清除掉
		}
			
		
		Date endDate = (Date)this.getUIContext().get("endDate");
		if(endDate!=null) {
			filter.getFilterItems().add(new FilterItemInfo("eventDate",endDate,CompareType.LESS_EQUALS));
			this.getUIContext().remove("endDate");  //清除掉
		}	
		
		if(viewInfo.getFilter()==null)
			viewInfo.setFilter(filter);
		else{
			try {
				viewInfo.getFilter().mergeFilter(filter,"AND");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
				
		return super.getQueryExecutor(queryPK, viewInfo);		
	}

	
	
	
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}


	protected String getEditUIName() {
		return TrackRecordEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return TrackRecordFactory.getRemoteInstance();
	}
	
	
	
	public static void showUI(IUIObject ui, String sellProjectId, Date beginDate,Date endDate)	throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("sellProjectId", sellProjectId);
		uiContext.put("beginDate", beginDate);
		uiContext.put("endDate", endDate);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(TrackRecordListUI.class.getName(), uiContext, null,	"VIEW");
		uiWindow.show();
	}
	
	
	
	public void actionExcelBatchImport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExcelBatchImport_actionPerformed(e);
		
		UIContext uiContext = new UIContext(this);
		
		DefaultKingdeeTreeNode projectNode = (DefaultKingdeeTreeNode)this.treeSellProject.getLastSelectedPathComponent();
		if(projectNode !=null && projectNode.getUserObject() instanceof SellProjectInfo){
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)projectNode;
			SellProjectInfo proInfo = (SellProjectInfo)thisNode.getUserObject();
			uiContext.put("sellProject", proInfo);
		}
		
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(TrackRecordImportUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();

	}
	
	
	
	
	

}