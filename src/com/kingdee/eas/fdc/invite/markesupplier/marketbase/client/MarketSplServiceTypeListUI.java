/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.marketbase.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplServiceTypeFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplServiceTypeInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MarketSplServiceTypeListUI extends AbstractMarketSplServiceTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSplServiceTypeListUI.class);
    
    /**
     * output class constructor
     */
    public MarketSplServiceTypeListUI() throws Exception
    {
        super();
    }
    private Object TypeInfo = null;
    
    public Object getTypeInfo() {
		return TypeInfo;
	}

	public void setTypeInfo(Object typeInfo) {
		TypeInfo = typeInfo;
	}
	protected FDCTreeBaseDataInfo getBaseDataInfo() {
		
		return new MarketSplServiceTypeInfo();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		
		return MarketSplServiceTypeFactory.getRemoteInstance();
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
	    	
	    	if(fdcsplserviceTypeInfo instanceof MarketSplServiceTypeInfo){
	    		
	    		longNumber = ((MarketSplServiceTypeInfo)fdcsplserviceTypeInfo).getLongNumber().toString().trim();	
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
	    	if(MarketSplServiceTypeFactory.getRemoteInstance().exists(filter)){
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
	    	MarketSplServiceTypeFactory.getRemoteInstance().delete(new ObjectUuidPK(id.toString()));
			
	    	refresh(e);
	    	super.actionRemove_actionPerformed(e);
	    	
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
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplServiceTypeFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplServiceTypeInfo objectValue = new com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplServiceTypeInfo();
		
        return objectValue;
    }


}