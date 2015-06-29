/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.FeesWarrantFactory;
import com.kingdee.eas.fdc.tenancy.FeesWarrantInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FeesVoucherListUI extends AbstractFeesVoucherListUI
{

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
//    	if(KDTableUtil.getSelectedRow(tblMain) == null){
//			MsgBox.showWarning(this, "请先选中行！");
//			return;
//		}
//		 int currRow = -1;
//		    String chcek = null;
//		    int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
//		    for (int i = 0; i < selectRows.length; i++) {
//		      currRow = selectRows[i];
//		      String ObjectPK = this.tblMain.getRow(currRow).getCell("id").getValue().toString();
//		      if (!ObjectPK.equals(chcek)) {
//		    	  FeesWarrantInfo fwinfo= FeesWarrantFactory.getRemoteInstance().getFeesWarrantInfo(new ObjectUuidPK(ObjectPK));
//		        if (FDCBillStateEnum.SUBMITTED.equals(fwinfo.getState()))
//		        {
//		        }else{
//		        	  if (fwinfo.isFiVouchered()){
//		        		  MsgBox.showError(" 第" + (selectRows[i] + 1) + "行 生成了凭证，不能删除!");
//			        	  this.abort();
//		        	  }else if(!FDCBillStateEnum.SUBMITTED.equals(fwinfo.getState())){
//		        		  MsgBox.showError(" 第" + (selectRows[i] + 1) + "行 单据状态不是已提交，不能删除!");
//			        	  this.abort();
//		        	  }
//		        	 
//		        }
//		        chcek = ObjectPK;
//		      }
//		    }
        super.actionRemove_actionPerformed(e);
    }
	protected String getEditUIName() {
		return FeesWarrantEditUI.class.getName();
	}
	protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}
	private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) 
	{
//		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
//		.getLastSelectedPathComponent();
//		if (node == null) {
//			MsgBox.showWarning("请选择具体租售项目！");
//			this.abort();
//		}
//		if (node.getUserObject() instanceof String) {
//			MsgBox.showWarning("请选择具体租售项目！");
//			this.abort();
//		}
//		if (node.getUserObject() instanceof SellProjectInfo) {
//			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
//			uiContext.put("sellProject", sellProject);
//		} 
		super.prepareUIContext(uiContext, e);
	}
	protected void initTree() throws Exception
	{
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.treeMain.setSelectionRow(0);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return FeesWarrantFactory.getRemoteInstance();
	}
	  protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
	    {
	    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();

	    	if (node == null)
	    	{
	    		return;
	    	}
	    	FilterInfo filter = new FilterInfo();
	    	if (node != null && node.getUserObject() instanceof SellProjectInfo) 
	    	{
	    		SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
	    		String sellProjectId = sellProject.getId().toString();
	    		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));
	    		if (saleOrg.isIsBizUnit()) 
	    		{
	    			this.actionAddNew.setEnabled(true);
	    		}
	    	}
	    	else
	    	{
	    		filter.getFilterItems().add(new FilterItemInfo("id", null));
	    		this.actionAddNew.setEnabled(false);
	    	}
	    	this.mainQuery.setFilter(filter);
	    	this.tblMain.removeRows();
	    }
	public void initUIContentLayout()
	{
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        pnlMain.setBounds(new Rectangle(10, 10, 996, 611));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 996, 611, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(tblMain, "right");
        pnlMain.add(kDTreeView1, "left");
        //treeView
        this.kDTreeView1.setTree(treeMain);

	 }
	 public void onLoad() throws Exception    {
		if(this.getUIContext().get("filter")!=null){
			EntityViewInfo ev=new EntityViewInfo();
			ev.setFilter((FilterInfo) this.getUIContext().get("filter"));
			this.mainQuery=ev;
		}
    	super.onLoad();
    	tblMain.getColumn("id").getStyleAttributes().setHided(true);
    	this.tblMain.getIndexColumn().setWidthAdjustMode(
				KDTIndexColumn.WIDTH_MANUAL);
		tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
		if(this.getUIContext().get("filter")==null){
			initTree();
		}else{
			this.kDTreeView1.setVisible(false);
			this.actionQuery.setVisible(false);
		}
    	FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.actionLocate.setVisible(true);
		this.actionEdit.setEnabled(true);
		this.actionRemove.setEnabled(true);
		this.actionCreateTo.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.tblMain.getColumn("id").getStyleAttributes().setHided(true);
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);
	    this.kDWorkButton1.setIcon(EASResource.getIcon("imgTbtn_audit"));
	    this.kDWorkButton2.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		if (saleOrg.isIsBizUnit()) 
		{
			this.actionEdit.setEnabled(true);
			this.actionAddNew.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionSetAudit.setEnabled(true);
			this.actionSetUnaudit.setEnabled(true);
		}else{
			this.actionEdit.setEnabled(false);
			this.actionAddNew.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionSetAudit.setEnabled(false);
			this.actionSetUnaudit.setEnabled(false);
		}
		
		this.actionSetAudit.setVisible(false);
		this.actionSetUnaudit.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.menuWorkFlow.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionEdit.setVisible(false);
    }
	 public void actionSetAudit_actionPerformed(ActionEvent e) throws Exception {
			// TODO Auto-generated method stub
			super.actionSetAudit_actionPerformed(e);
			if(KDTableUtil.getSelectedRow(tblMain) == null){
				MsgBox.showWarning(this, "请先选中行！");
				return;
			}
			 int currRow = -1;
			    String chcek = null;
			    int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
			    for (int i = 0; i < selectRows.length; i++) {
			      currRow = selectRows[i];
			      String ObjectPK = this.tblMain.getRow(currRow).getCell("id").getValue().toString();
			      if (!ObjectPK.equals(chcek)) {
			    	  FeesWarrantInfo fwinfo= FeesWarrantFactory.getRemoteInstance().getFeesWarrantInfo(new ObjectUuidPK(ObjectPK));
			        if (FDCBillStateEnum.SUBMITTED.equals(fwinfo.getState()))
			        {
			        	fwinfo.setState(FDCBillStateEnum.AUDITTED);
			        	FeesWarrantFactory.getRemoteInstance().update(new ObjectUuidPK(ObjectPK),fwinfo);
			        }else{
			        	  MsgBox.showError(" 第" + (selectRows[i] + 1) + "行 单据状态已提交,不能审批!");
			        }
			        chcek = ObjectPK;
			      }
			    }
			    actionRefresh_actionPerformed(e);
			    if (chcek == null) {
			      MsgBox.showInfo(" 请选中一行！");
			      SysUtil.abort();
			    }
		}

		public void actionSetUnaudit_actionPerformed(ActionEvent e)
				throws Exception {
			super.actionSetUnaudit_actionPerformed(e);
			if(KDTableUtil.getSelectedRow(tblMain) == null){
				MsgBox.showWarning(this, "请先选中行！");
				return;
			}
			 int currRow = -1;
			    String chcek = null;
			    int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
			    for (int i = 0; i < selectRows.length; i++) {
			      currRow = selectRows[i];
			      String ObjectPK = this.tblMain.getRow(currRow).getCell("id").getValue().toString();
			      if (!ObjectPK.equals(chcek)) {
			    	  FeesWarrantInfo fwinfo= FeesWarrantFactory.getRemoteInstance().getFeesWarrantInfo(new ObjectUuidPK(ObjectPK));
			        if (FDCBillStateEnum.AUDITTED.equals(fwinfo.getState())&&!fwinfo.isFiVouchered())
			        {
			        	fwinfo.setState(FDCBillStateEnum.SUBMITTED);
			        	FeesWarrantFactory.getRemoteInstance().update(new ObjectUuidPK(ObjectPK),fwinfo);
			        }else{
			        	if(fwinfo.isFiVouchered()){
			        		MsgBox.showError(" 第" + (selectRows[i] + 1) + "行已生成凭证，请先删除凭证。");
			        	}else{
			        		MsgBox.showError(" 第" + (selectRows[i] + 1) + "行 单据状态不是审批,不能反审批!");	
			        	}
			        	  
			        }
			        chcek = ObjectPK;
			      }
			    }
			    actionRefresh_actionPerformed(e);
			    if (chcek == null) {
			      MsgBox.showInfo(" 请选中一行！");
			      SysUtil.abort();
			    }
		}
	    /**
	     * output actionVoucher_actionPerformed
	     */
	    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
	    {
//	    	if(KDTableUtil.getSelectedRow(tblMain) == null){
//				MsgBox.showWarning(this, "请先选中行！");
//				return;
//			}
//			 int currRow = -1;
//			    String chcek = null;
//			    int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
//			    for (int i = 0; i < selectRows.length; i++) {
//			      currRow = selectRows[i];
//			      String ObjectPK = this.tblMain.getRow(currRow).getCell("id").getValue().toString();
//			      if (!ObjectPK.equals(chcek)) {
//			    	  FeesWarrantInfo fwinfo= FeesWarrantFactory.getRemoteInstance().getFeesWarrantInfo(new ObjectUuidPK(ObjectPK));
//			        if (FDCBillStateEnum.AUDITTED.equals(fwinfo.getState()))
//			        {
//			        }else{
//			        	  MsgBox.showError(" 第" + (selectRows[i] + 1) + "行 单据状态不是审批状态,不能生成凭证!");
//			        	  this.abort();
//			        }
//			        chcek = ObjectPK;
//			      }
//			    }
	        super.actionVoucher_actionPerformed(e);
	    }

	    /**
	     * output actionDelVoucher_actionPerformed
	     */
	    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
	    {
//	    	if(KDTableUtil.getSelectedRow(tblMain) == null){
//				MsgBox.showWarning(this, "请先选中行！");
//				return;
//			}
//			 int currRow = -1;
//			    String chcek = null;
//			    int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
//			    for (int i = 0; i < selectRows.length; i++) {
//			      currRow = selectRows[i];
//			      String ObjectPK = this.tblMain.getRow(currRow).getCell("id").getValue().toString();
//			      if (!ObjectPK.equals(chcek)) {
//			    	  FeesWarrantInfo fwinfo= FeesWarrantFactory.getRemoteInstance().getFeesWarrantInfo(new ObjectUuidPK(ObjectPK));
//			        if (FDCBillStateEnum.AUDITTED.equals(fwinfo.getState()))
//			        {
//			        }else{
//			        	  MsgBox.showError(" 第" + (selectRows[i] + 1) + "行 单据状态不是审批状态,不能删凭证!");
//			        	  this.abort();
//			        }
//			        chcek = ObjectPK;
//			      }
//			    }
	        super.actionDelVoucher_actionPerformed(e);
	    }
    protected void refresh(ActionEvent e) throws Exception
	{
		this.tblMain.removeRows();
	}
    private static final Logger logger = CoreUIObject.getLogger(FeesVoucherListUI.class);
    
    public FeesVoucherListUI() throws Exception
    {
        super();
    }

    
}