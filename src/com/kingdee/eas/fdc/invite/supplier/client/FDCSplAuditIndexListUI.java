/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexGroupFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexGroupInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * 
 * @(#)			FDCSplAuditIndexListUI.java			
 * 版权：		金蝶国际软件集团有限公司版权所有<P>		 	
 * 描述：		基础资料.供方评审指标设置<P>
 *
 * @author		田宝平
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see
 */
public class FDCSplAuditIndexListUI extends AbstractFDCSplAuditIndexListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCSplAuditIndexListUI.class);
    
    /*
     * 定义是否启用列的名称的全局变量
     */
	private final String COL_STATE = "isEnabled";
	
	/*
	 * 获取资源文件
	 */
	private String getResource(String msg) {
		return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
	}
    public void onLoad() throws Exception {
    	
		this.tblMain.checkParsed();
		super.onLoad();
		
		/*
    	 * 初始化样式
    	 */
    	initStyle();
	}

    /**
     * 
    * @description		初始化样式
    * @author			田宝平	
    * @createDate		2010-12-1
    * @param	
    * @return			void		
    *	
    * @version			EAS7.0
    * @see
     */
    public void initStyle(){
    	
		/*
		 * 锁定表格，仅选择一行
		 */
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		/*
		 * 锁向下方向键
		 */
		KDTableHelper.downArrowAutoAddRow(tblMain, false, null);
		/*
		 * 锁回车
		 */
		KDTableHelper.updateEnterWithTab(tblMain, false);
		
		/*
		 * 将树形菜单中移动菜单的按钮设置为隐藏且不可用
		 */
		this.actionGroupMoveTree.setVisible(false);
		this.actionGroupMoveTree.setEnabled(false);
    }
    
    /**
     * 					返回所选择的树的节点路径对象
    * @description		
    * @author			田宝平	
    * @createDate		2010-11-17
    * @param	
    * @return			DefaultKingdeeTreeNode		
    *	
    * @version			EAS7.0
    * @see
     */
	private DefaultKingdeeTreeNode getSelectNode(KDTree tree){
				
		/*
		 * 选择的路径不为空时，返回所选择的树的节点路径对象
		 */
		if(tree.getLastSelectedPathComponent() != null){
			return (DefaultKingdeeTreeNode) tree.getLastSelectedPathComponent();
		}
		/*
		 * 否则返回null
		 */
		else{
			return null;
		}
	}
	
	/**
	 *  
	* @description		获取过滤的信息
	* @author			田宝平	
	* @createDate		2010-12-1
	* @param	
	* @return			FilterInfo		
	*	
	* @version			EAS7.0
	* @see
	 */
	private FilterInfo getMainFilter() throws Exception{
		
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		
		/*
		 * 获取所选择的树的节点路径对象
		 */
		DefaultKingdeeTreeNode projectNode = getSelectNode(this.treeMain);
		FDCSplAuditIndexGroupInfo fdcSplAuditIndexGroupInfo = null;
		
		/*
		 * 判断是否选中树
		 */
		if(projectNode != null && projectNode.getUserObject() instanceof FDCSplAuditIndexGroupInfo){
			fdcSplAuditIndexGroupInfo = (FDCSplAuditIndexGroupInfo)projectNode.getUserObject();
			
			/*
			 * 获取选择树的ID,并放置到SET容器中
			 */
			String id = fdcSplAuditIndexGroupInfo.getId().toString().trim();
			Set set = new HashSet ();
			set.add(id);
			
			/*
			 * 判断如果是叶子节点,则不用循环获取所有子节点
			 */
			if(!projectNode.isLeaf()){
				this.getAllChild(projectNode,set);
			}
			
			/*
			 * 将选择的树的节点的对象放置到容器中,以便于评审维度编辑页面的F7评审维度获取
			 */
			getUIContext().put("indexGroup", fdcSplAuditIndexGroupInfo);
			filterItems.add(new FilterItemInfo("indexGroup.id", set,CompareType.INCLUDE));
		}
		
		return filter ;
	}
	
	/**
	 * 
	* @description		查询
	* @author			田宝平	
	* @createDate		2010-12-1
	*	
	* @version			EAS7.0
	* @see com.kingdee.eas.framework.client.ListUI#getQueryExecutor(com.kingdee.bos.metadata.IMetaDataPK, com.kingdee.bos.metadata.entity.EntityViewInfo)
	 */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
		
		FilterInfo filter = null;
		try{
			if(getMainFilter()!=null){
				filter = getMainFilter();
			}else{
				filter = new FilterInfo();
			}
			
			if (this.getDialog() != null && viewInfo.getFilter() != null ) {
				FilterInfo commFilter = viewInfo.getFilter();
				if (filter != null && commFilter != null)
					filter.mergeFilter(commFilter, "and");
			} else {
				QuerySolutionInfo querySolution = this.getQuerySolutionInfo();
				if (querySolution != null
						&& querySolution.getEntityViewInfo() != null) {
					EntityViewInfo ev = Util.getInnerFilterInfo(querySolution);
					if (ev.getFilter() != null) {
						filter.mergeFilter(ev.getFilter(), "and");
					}
				}
			}
		}catch (Exception e1) {
			handUIException(e1);
		}
		viewInfo.setFilter(filter);
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	/**
	 * 
	* @description		选择树的值改变时触发
	* @author			田宝平	
	* @createDate		2010-12-1
	*	
	* @version			EAS7.0
	* @see com.kingdee.eas.framework.client.TreeDetailListUI#treeMain_valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeMain_valueChanged(e); 
		DefaultKingdeeTreeNode projectNode = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
		FDCSplAuditIndexGroupInfo fdcSplAuditIndexGroupInfo = null;
		
		/*
		 * 判断是否选中树
		 */
		if (projectNode != null&& projectNode.getUserObject() instanceof FDCSplAuditIndexGroupInfo) {
			fdcSplAuditIndexGroupInfo = (FDCSplAuditIndexGroupInfo) projectNode.getUserObject();
			
			/*
			 * 获取选择树的name
			 */
			String titleName = fdcSplAuditIndexGroupInfo.getName().toString().trim();
			
			/*
			 * 改变表头的名称为选择树的名称
			 */
			this.kDContainer1.setTitle(titleName +getResource("dimensionality"));
		} else {
			this.kDContainer1.setTitle(getRootName());
		}
		tblMain.removeRows();
    }
    
    /**
	* @description		递归取得节点下的所有子节点
	* @author			田宝平	
	* @createDate		2010-12-1
	* @param			node:  TreeNode
	* @param			leafNodesIdSet: set对象
	* @return			Set:所有叶子节点的id		
	*	
	* @version			EAS7.0
	* @see
	*/
	protected void getAllChild(TreeNode node, Set leafNodesIdSet)throws Exception {
			
			int count = node.getChildCount();
			
			/*
			 * 如果没有下级项目，说明是叶子节点，直接返回它的id即可
			 */
			if (count == 0) {
				if(((DefaultKingdeeTreeNode) node).getUserObject() instanceof FDCSplAuditIndexGroupInfo){
					FDCSplAuditIndexGroupInfo fdcSplAuditIndexGroupInfo = ((FDCSplAuditIndexGroupInfo) ((DefaultKingdeeTreeNode) node)
							.getUserObject());
					String oid = fdcSplAuditIndexGroupInfo.getId().toString().trim();
					leafNodesIdSet.add(oid);
				}else{}
				return;
			}
			
			DefaultKingdeeTreeNode treeNode = null;
			for (int i = 0; i < count; i++) {
				/*
				 * node为空时退出
				 */
				if (node == null || !( node.getChildAt(i) instanceof DefaultKingdeeTreeNode))
					continue;
				
				treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
				
				/*
				 * 是否叶子节点
				 */
				if (treeNode.isLeaf()) {
					if (!(treeNode.getUserObject() instanceof FDCSplAuditIndexGroupInfo))
						continue;
					String id = ((FDCSplAuditIndexGroupInfo) treeNode.getUserObject()).getId()
							.toString();
					leafNodesIdSet.add(id);
				} else {
					/*
					 * 不是叶子节点回调本方法
					 */
					getAllChild(treeNode, leafNodesIdSet);
				}
			}
	}

    /**
     * output class constructor
     */
    public FDCSplAuditIndexListUI() throws Exception
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
    	/*
    	 * 选中表行时开启部分工具栏
    	 */
    	reSetAuditBtnState();
    	
		super.tblMain_tableClicked(e);
    }

    /**
     * 
    * @description		选择的记录变化时更新按钮状态
    * @author			田宝平	
    * @createDate		2010-12-1
    *	
    * @version			EAS7.0
    * @see com.kingdee.eas.framework.client.TreeDetailListUI#tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent)
     */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
	{
		super.tblMain_tableSelectChanged(e);
		
	    /*
	     * 根据选择的记录是否启用，更新按钮状态
	     */
		reSetAuditBtnState();
	}
	
    /**
     * 
    * @description		根据选择的记录是否启用，更新按钮状态
    * @author			田宝平	
    * @createDate		2010-12-1
    * @param	
    * @return			void		
    *	
    * @version			EAS7.0
    * @see
     */
	private void reSetAuditBtnState()
	{
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex < 0){
			return ;
		}
		
		IRow row = tblMain.getRow(rowIndex);
		String str = row.getCell(COL_STATE).getValue().toString().trim();
		
		/*
		 * 如果是启用状态
		 */
		if (new Boolean(str).booleanValue()) {
			/*
			 * 将禁用按钮设置为启用，启用按钮设置为禁用
			 */
			this.actionCancel.setEnabled(true);
			this.actionCancelCancel.setEnabled(false);
		} 
		/*
		 * 否则为禁用状态
		 */
		else {
			/*
			 * 将禁用按钮设置为禁用，启用按钮设置为启用
			 */
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(true);
		}  
	}
	
    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output chkIncludeChild_itemStateChanged method
     */
    protected void chkIncludeChild_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.chkIncludeChild_itemStateChanged(e);
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
    	/*
    	 * 当是非叶子节点，才可以新增指标
    	 */
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getSupplierResources("notAddBase"));
        	SysUtil.abort();
		}
    	if(getTypeSelectedTreeNode().getUserObject() instanceof FDCSplAuditIndexGroupInfo&&((FDCSplAuditIndexGroupInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
			super.actionAddNew_actionPerformed(e);
		}else{
			FDCMsgBox.showWarning(this,getResource("leafage"));
		}
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
        reSetAuditBtnState();
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	/*
    	 * 判断是否选中
    	 */
    	checkSelected(); 
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getResource("canntedit"));
        	SysUtil.abort();
		}
        super.actionEdit_actionPerformed(e);
        
        /*
         * 根据选择的记录是否启用，更新按钮状态
         */
        reSetAuditBtnState();
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	/*
    	 * 判断是否选中
    	 */
    	checkSelected(); 
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getResource("canntremove"));
        	SysUtil.abort();
		}
    	
    	/*
    	 * 获取选择记录的ID
    	 */
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		Object infoId = tblMain.getRow(rowIndex).getCell("id").getValue();
		
		/*
		 * 将选择记录的ID放置到list中
		 */
		List list = new ArrayList();
		list.add(infoId);
		
		/*
		 * 根据传递的list,调用远程方法,查询评审指标是否被引用,并将结果返回
		 */
		boolean referenceFlag = SupplierStockFactory.getRemoteInstance().getATRFValue(list);

		/*
		 * 未被引用，可以执行此操作
		 */
    	if(!referenceFlag){
    		/*
    		 * 是否确认删除
    		 */
    		if (confirmRemove()){
        		getBizInterface().delete(new ObjectUuidPK(infoId.toString())); 
    		}
            refreshList();
    		this.actionRefresh_actionPerformed(e);
		}else{
			FDCMsgBox.showWarning(this,getResource("excerpted"));
			abort();
		}
    }

    /**
     * 
    * @description		是否确认删除
    * @author			田宝平	
    * @createDate		2010-12-1
    *	
    * @version			EAS7.0
    * @see com.kingdee.eas.framework.client.ListUI#confirmRemove()
     */
	protected boolean confirmRemove()
	{
		return MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
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
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

    /**
     * output actionGroupAddNew_actionPerformed
     */
    public void actionGroupAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getSupplierResources("notAddBase"));
        	SysUtil.abort();
		}
    	/*
    	 * 评审维度下的指标已经有数据,不能在本路径下载新增维度
    	 */
    	if(getTypeSelectedTreeNode().getUserObject() instanceof FDCSplAuditIndexGroupInfo){
    		if(((FDCSplAuditIndexGroupInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
    			FilterInfo filter = new FilterInfo();
    			/*
    			 * 根据评审指标的indexGroup，查询在评审维度中是否已存在数据
    			 */
        		filter.getFilterItems().add(new FilterItemInfo("indexGroup.id",((FDCSplAuditIndexGroupInfo)getTypeSelectedTreeNode().getUserObject()).getId().toString()));
        		if(getBizInterface().exists(filter)){
        			FDCMsgBox.showWarning(this,getResource("haveNumber"));
        			abort();
        		}
    		}
    	}
        super.actionGroupAddNew_actionPerformed(e);
    }

    /**
     * output actionGroupView_actionPerformed
     */
    public void actionGroupView_actionPerformed(ActionEvent e) throws Exception
    {
//    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
//			MsgBox.showWarning(this, getResource("canntview"));
//        	SysUtil.abort();
//		}
        super.actionGroupView_actionPerformed(e);
    }

    /**
     * output actionGroupEdit_actionPerformed
     */
    public void actionGroupEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getResource("canntedit"));
        	SysUtil.abort();
		}
    	if(getTypeSelectedTreeNode().getUserObject() instanceof FDCSplAuditIndexGroupInfo){
    		if(!((FDCSplAuditIndexGroupInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
    			FDCMsgBox.showWarning(this,getSupplierResources("notOne")+getSupplierResources("doNot"));
    			abort();
    		}
    	}
        super.actionGroupEdit_actionPerformed(e);
    }
    
	public DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) this.treeMain
				.getLastSelectedPathComponent();
	}
	
	private String getSupplierResources(String key) {
	    return	EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource",key);

	}
    /**
     * output actionGroupRemove_actionPerformed
     */
    public void actionGroupRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	/*
    	 * 评审维度下的指标已经有数据,不能删除该维度
    	 */
		if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getResource("canntremove"));
        	SysUtil.abort();
		}
    	
    	if(getTypeSelectedTreeNode().getUserObject() instanceof FDCSplAuditIndexGroupInfo){
    		if(((FDCSplAuditIndexGroupInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
    			FilterInfo filter = new FilterInfo();
    			/*
    			 * 根据评审指标的indexGroup，查询在评审维度中是否已存在数据
    			 */
        		filter.getFilterItems().add(new FilterItemInfo("indexGroup.id",((FDCSplAuditIndexGroupInfo)getTypeSelectedTreeNode().getUserObject()).getId().toString()));
        		
        		if(getBizInterface().exists(filter)){
        			FDCMsgBox.showWarning(this,getResource("haveNumber"));
        			abort();
        		}
    		}else{
    			FDCMsgBox.showWarning(this,getResource("junior"));
    			abort();
    		}
    	}
        super.actionGroupRemove_actionPerformed(e);
    }

    /**
     * output actionGroupMoveTree_actionPerformed
     */
    public void actionGroupMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupMoveTree_actionPerformed(e);
    }

    /**
     * output actionMoveTree_actionPerformed
     */
    public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMoveTree_actionPerformed(e);
    }

    /**
	 * output getKeyFieldName method
	 */
	protected String getKeyFieldName()
	{
		return "id";
	}

	protected IObjectValue createNewData()
	{
		FDCSplAuditIndexInfo objectValue = new FDCSplAuditIndexInfo();
		return objectValue;
	}

	protected String getGroupEditUIName() {
		return FDCSplAuditIndexGroupEditUI.class.getName();
	}
	
	protected String getEditUIName()
	{
		return FDCSplAuditIndexEditUI.class.getName();
	}
	
	protected String getQueryFieldName()
	{
		return "indexGroup.id";
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return FDCSplAuditIndexGroupFactory.getRemoteInstance();
	}

	protected String getRootName()
	{
		return getResource("syndicDimensiona");
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCSplAuditIndexFactory.getRemoteInstance();
	}

	protected IObjectPK getSelectedTreeKeyValue() {
		return null;
	}
	
	protected FDCSplAuditIndexInfo getSelectedFDCSplAuditIndex() throws EASBizException, BOSException
	{
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		Object id = tblMain.getRow(rowIndex).getCell("id").getValue();

		if(id != null){
			IObjectPK pk = new ObjectUuidPK(id.toString());
			EntityViewInfo view = new EntityViewInfo();

			view.getSelector().add(new SelectorItemInfo("*")); 

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", id.toString()));

			view.setFilter(filter);

			/*
			 * 根据选择行的ID，查询评审指标中是够存在数据
			 */
			FDCSplAuditIndexCollection prjInfo = FDCSplAuditIndexFactory.getRemoteInstance().getFDCSplAuditIndexCollection(view);

			if(prjInfo.size() > 0){
				return prjInfo.get(0);
			}
		}
		return null ;
	}
	
	/**
	 * 
	* @description		禁用功能
	* @author			田宝平	
	* @createDate		2010-12-1
	*	
	* @version			EAS7.0
	* @see com.kingdee.eas.framework.client.ListUI#actionCancel_actionPerformed(java.awt.event.ActionEvent)
	 */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	/*
    	 * 判断是否选中
    	 */
    	checkSelected();
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getResource("canntedit"));
        	SysUtil.abort();
		}
    	/*
    	 * 获取选择记录的ID
    	 */
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		Object infoId = tblMain.getRow(rowIndex).getCell("id").getValue();
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("splAuditIndex.id", infoId.toString()));
		if(SupplierGuideEntryFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(this,"已经被评审模板引用，不能进行禁用操作！");
			SysUtil.abort();
		}
		
		/*
		 * 将选择记录的ID放置到list中
		 */
		List list = new ArrayList();
		list.add(infoId);
		
//		//根据传递的list,调用远程方法,查询评审指标是否被引用,并将结果返回
//		boolean referenceFlag = SupplierStockFactory.getRemoteInstance().getATRFValue(list);
//		
//		//未被引用，可以执行此操作
//    	if(!referenceFlag)
//		{
    		/*
    		 * 是否确认禁用
    		 */
    		if (confirmCancel()){
    			SelectorItemCollection sic = new SelectorItemCollection();
        		
        		/*
        		 * 将启用按钮的字段名称isEnabled放置到容器中
        		 */
        		sic.add(new SelectorItemInfo("isEnabled"));	
        		FDCSplAuditIndexInfo info = getSelectedFDCSplAuditIndex();
        		
        		/*
        		 * 设置为禁用
        		 */
        		info.setIsEnabled(false);
        		getBizInterface().updatePartial(info, sic);
        		
        		//super.actionCancel_actionPerformed(e);
        		refreshList();
        		tblMain.refresh();
        		this.actionRefresh_actionPerformed(e);
    		}
//		}
//    	else
//    	{
//			FDCMsgBox.showWarning(this,"该记录已经被引用，不能执行此操作!");
//			abort();
//		}
    }
    
    /**
     * 
    * @description		启用功能
    * @author			田宝平	
    * @createDate		2010-12-1
    *	
    * @version			EAS7.0
    * @see com.kingdee.eas.framework.client.ListUI#actionCancelCancel_actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	/*
    	 * 判断是否选中
    	 */
    	checkSelected();
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getResource("canntedit"));
        	SysUtil.abort();
		}
    	SelectorItemCollection sic = new SelectorItemCollection();
    	
    	/*
    	 * 将启用按钮的字段名称isEnabled放置到容器中
    	 */
		sic.add(new SelectorItemInfo("isEnabled"));	
		FDCSplAuditIndexInfo info = getSelectedFDCSplAuditIndex();
		
		/*
		 * 设置为启用
		 */
		info.setIsEnabled(true);
		getBizInterface().updatePartial(info, sic);
		
		super.actionCancelCancel_actionPerformed(e);
		refreshList();
		tblMain.refresh();
		this.actionRefresh_actionPerformed(e);
    }

    /**
     * 
    * @description		是否确认禁用
    * @author			田宝平	
    * @createDate		2010-12-1
    * @param	
    * @return			boolean		
    *	
    * @version			EAS1.0
    * @see
     */
    protected boolean confirmCancel()
	{
		return MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Cancel")));
	} 
}