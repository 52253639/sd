/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.RESchTemplateFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class RESchTemplateTreeListUI extends AbstractRESchTemplateTreeListUI{
	private static final Logger logger = CoreUIObject
			.getLogger(RESchTemplateTreeListUI.class);

	/**
	 * output class constructor
	 */
	public RESchTemplateTreeListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/*
	 * 初始化按钮
	 */
	protected void initWorkButton() {
		super.initWorkButton();
		this.btnAddNew.setVisible(false);
		this.btnAddNew.setEnabled(false);
		this.btnAttachment.setVisible(false);
		this.btnAttachment.setEnabled(false);
		this.btnCancel.setVisible(false);
		this.btnCancel.setEnabled(false);
		this.btnCancelCancel.setVisible(false);
		this.btnCancelCancel.setEnabled(false);
		this.btnEdit.setVisible(false);
		this.btnEdit.setVisible(false);

		this.btnLocate.setVisible(false);
		this.btnLocate.setEnabled(false);
		this.btnMoveTree.setVisible(false);
		this.btnMoveTree.setEnabled(false);
		this.btnPageSetup.setVisible(false);
		this.btnPageSetup.setEnabled(false);
		this.btnPrint.setVisible(false);
		this.btnPrint.setEnabled(false);
		this.btnPrintPreview.setVisible(false);
		this.btnPrintPreview.setEnabled(false);
		this.btnQuery.setVisible(false);
		this.btnQuery.setEnabled(false);
		this.btnQueryScheme.setVisible(false);
		this.btnQueryScheme.setEnabled(false);
		this.btnRefresh.setVisible(false);
		this.btnPrint.setEnabled(false);
		this.btnRemove.setVisible(false);
		this.btnRemove.setEnabled(false);
		this.btnView.setVisible(false);
		this.setEnabled(false);

	}

	
	/**
	 * 
	 * @description 树状结构设置
	 * @author 车忠伟
	 * @createDate 2011-8-9
	 * @version EAS7.0
	 * @see
	 */
	  public void initDipaly(){
	    	//设置根节点不显示
	    	this.treeMain.setRootVisible(false);
	    	
	    	//设置树节点全展开
	    	this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	    	//屏蔽按钮操作
	    	this.btnGroupAddNew.setVisible(false);
	    	this.btnGroupEdit.setVisible(false);
	    	this.btnGroupMoveTree.setVisible(false);
	    	this.btnGroupRemove.setVisible(false);
	    	this.btnGroupView.setVisible(false);
	    	
	  }
	  
	  /**
		 * 
		 * @description 响应确定按钮
		 * @author 车忠伟
		 * @createDate 2011-8-9
		 * @version EAS7.0
		 * @see
		 */
	public void actionOk_actionPerformed(ActionEvent e) throws Exception {
		  System.out.println();
		  DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		  Object obj = node.getUserObject();
		  RESchTemplateInfo info=(RESchTemplateInfo) obj;
		  if(!info.isIsLeaf()){
			  FDCMsgBox.showWarning("请选择明细");
				SysUtil.abort();
		  }
		  getUIContext().put("template",obj);
		  
		  destroyWindow();
	}
	/**
	 * 
	 * @description 响应取消按钮
	 * @author 车忠伟
	 * @createDate 2011-8-9
	 * @version EAS7.0
	 * @see
	 */
	public void actionNO_actionPerformed(ActionEvent e) throws Exception {
		destroyWindow();
	}
	protected ITreeBase getTreeInterface() throws Exception {

		return RESchTemplateFactory.getRemoteInstance();
	}
   
	
	
	
	public void onLoad() throws Exception {
		super.onLoad();
		initDipaly();
		this.tblMain.setVisible(false);
		actionOk.setEnabled(true);
		actionNO.setEnabled(true);
	}

	protected String getGroupEditUIName() {
		return null;
	}
	
	protected String getQueryFieldName() {
		return "id";
	}

	protected IObjectPK getSelectedTreeKeyValue() {
		RESchTemplateTaskInfo templateTaskInfo = null;
		IObjectPK detail = new ObjectUuidPK(getSelectedKeyValue());
		if (detail == null) {
			return null;
		}
		try {
			templateTaskInfo = (RESchTemplateTaskInfo) getBizInterface().getValue(detail);
		} catch (Exception e) {
			handUIException(e);
		}
		if (templateTaskInfo.getTemplate() == null || templateTaskInfo.getTemplate().getId() == null) {
			this.treeMain.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
			abort();
		}
		return new ObjectUuidPK(templateTaskInfo.getTemplate().getId());
	}


}