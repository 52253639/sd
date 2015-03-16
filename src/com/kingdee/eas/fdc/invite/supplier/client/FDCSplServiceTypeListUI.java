/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FDCSplServiceTypeListUI extends AbstractFDCSplServiceTypeListUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 65674377625340339L;
	private static final Logger logger = CoreUIObject.getLogger(FDCSplServiceTypeListUI.class);
    private Object TypeInfo = null;
    
    public Object getTypeInfo() {
		return TypeInfo;
	}

	public void setTypeInfo(Object typeInfo) {
		TypeInfo = typeInfo;
	}

	public static Logger getLogger() {
		return logger;
	}

	protected FDCTreeBaseDataInfo getBaseDataInfo() {
		
		return new FDCSplServiceTypeInfo();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		
		return FDCSplServiceTypeFactory.getRemoteInstance();
	}
	
    protected String getEditUIName() {
    	
    	return FDCSplServiceTypeEditUI.class.getName();
    }
    
	
    private DefaultKingdeeTreeNode getSelectNode(KDTree tree){
    	if(tree.getLastSelectedPathComponent() != null){
    		return (DefaultKingdeeTreeNode) tree.getLastSelectedPathComponent();
    	}else{
    		return null;
    	}
    }
	
    /**
	 * @description		节点查询(窗体加载时调用)
	 * @author			GeneZhou	
	 * @createDate		2010-11-19
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		
		FilterInfo filter = null;
		try{
			filter = getServiceTypeFilter();
			
			if (this.getDialog() != null && viewInfo.getFilter() != null ) {
				FilterInfo commFilter = viewInfo.getFilter();
				if (commFilter != null)
					filter.mergeFilter(commFilter, "and");
			} 
		}catch (Exception e1) {
			handUIException(e1);
		}
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	/**
	 * @description		过滤查询：根据longNumber!进行过滤
	 * @author			GeneZhou	
	 * @createDate		2010-11-19
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private FilterInfo getServiceTypeFilter() throws Exception{
		 
	    	FilterInfo filter = new FilterInfo();
	    	DefaultKingdeeTreeNode serviceTypeNode = getSelectNode(this.treeMain);
	    	
	    	Object fdcsplserviceTypeInfo = null;
	    	String longNumber = null;
	    	
	    	if(serviceTypeNode != null && serviceTypeNode.getUserObject() != null){
	    		fdcsplserviceTypeInfo = serviceTypeNode.getUserObject();
	    	}
	    	
	    	if(fdcsplserviceTypeInfo instanceof FDCSplServiceTypeInfo){
	    		
	    		longNumber = ((FDCSplServiceTypeInfo)fdcsplserviceTypeInfo).getLongNumber().toString().trim();	
			}
	    	if(null == longNumber || "".equals(longNumber)){
	    		filter.getFilterItems().add(new FilterItemInfo("Fid","",CompareType.NOTEQUALS));
	    	}else{
	    		filter.getFilterItems().add(new FilterItemInfo("flongnumber",longNumber+"\\!%",CompareType.LIKE));
	    	}
	       if(filter != null && filter != null){
	    	   filter.mergeFilter(filter,"and");
	       }
	    	return filter;
	    }
	 
	/**
	 * @description		查询：值改变事件
	 * @author			GeneZhou	
	 * @createDate		2010-11-19
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
			if(node == null) return;
			FilterInfo filter = new FilterInfo();
			if (node.getUserObject() instanceof FDCTreeBaseDataInfo) {
				FDCTreeBaseDataInfo info = (FDCTreeBaseDataInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo(getLongNumberFieldName(),info.getLongNumber()));
				filter.getFilterItems().add(new FilterItemInfo(getLongNumberFieldName(),info.getLongNumber()+"!%" ,CompareType.LIKE));
				mainQuery.setFilter(filter);
				mainQuery.getFilter().setMaskString("#0 or #1");
			} else	mainQuery.setFilter(filter);
			this.tblServiceType.setTitle(node.toString());
			/**
			 *  触发新查询
			 */
			this.tblMain.removeRows();
	 }
	    
    /**
	 * @description		获取资源文件设置Tree根节点名称
	 * @author			GeneZhou	
	 * @createDate		2010-11-19
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	 private String getResourceNode(String str)
	 {
		 return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", str);
	 }
	    
 	/**
	 * @description		设置Tree根节点名称
	 * @author			GeneZhou	
	 * @createDate		2010-11-19
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	 protected String getRootName() {
		
		return getResourceNode("serviceType");
	 }
	 
	 /**
	  * Window load
	  * (non-Javadoc)
	  * @see com.kingdee.eas.fdc.basedata.client.FDCTreeBaseDataListUI#onLoad()
	  */
	 public void onLoad() throws Exception {
			
		 	this.tblMain.checkParsed();
			super.onLoad();
			this.initSetUp();

			
	}
	 
	/**
	 * @description		初始化设置
	 * @author			GeneZhou	
	 * @createDate		2010-11-19
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	 public void initSetUp()
	 {
		 	/**
			 * 设置容器空间收缩属性
			 */
			this.tblServiceType.setEnableActive(false);
			/**
			 * Tree‘s Title
			 */
			this.treeView.setTitle(getResourceNode("serviceType"));
			/**
			 * 锁定表格，仅选择一行
			 */
			this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	 }
	 
	 
	 /**
     * output class constructor
     */
    public FDCSplServiceTypeListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

  

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getResourceNode("canntedit"));
        	SysUtil.abort();
		}
    	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
    	String id = this.tblMain.getRow(rowIndex).getCell("id").getValue().toString();
    	FilterInfo filter = new FilterInfo();
    	
    	filter.getFilterItems().add(new FilterItemInfo("parent.id", id));
    	filter.getFilterItems().add(new FilterItemInfo("id", id,CompareType.NOTEQUALS));
    	if(FDCSplServiceTypeFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(this,getResourceNode("notOne")+getResourceNode("doNot"));
			abort();
    	}
        super.actionEdit_actionPerformed(e);
    }
    
	public DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
	} 
	
	
	
	/**
	 * @description		删除
	 * @author			GeneZhou	
	 * @createDate		2010-11-19
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this,getResourceNode("canntremove"));
        	SysUtil.abort();
		}
    	int yes=MsgBox.showConfirm2New(this, getResourceNode("deleteS"));
		if(yes > 0){
			SysUtil.abort();
		}
    	Object id = this.tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();  
    	FDCSplServiceTypeFactory.getRemoteInstance().delete(new ObjectUuidPK(id.toString()));
		
    	refresh(e);
    	super.actionRemove_actionPerformed(e);
    	
    }
    

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	int yes=MsgBox.showConfirm2New(this, getResourceNode("isProhibit"));
		if(yes>0){
			/**
			 * 终止操作
			 */
			SysUtil.abort();
		}
		else{
	        super.actionCancel_actionPerformed(e);
	        this.onShow();
		}	 
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	/**
    	 * 判断是否选择数据行
    	 */
    	checkSelected();
        super.actionCancelCancel_actionPerformed(e);
        this.onShow();
    }
    
    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

    /**
     * output actionMoveTree_actionPerformed
     */
    public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMoveTree_actionPerformed(e);
    }
	
	

}