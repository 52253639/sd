/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.List;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
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
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.ChequeCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.VerifyStatusEnum;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class ChequeListUI extends AbstractChequeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChequeListUI.class);
    
    /**
     * output class constructor
     */
    public ChequeListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	FDCClientHelper.addSqlMenu(this, this.menuEdit);
    	super.onLoad();
    	this.treeMain.setModel(SHEHelper.getSaleOrgTree(this.actionOnLoad));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.treeMain.setSelectionRow(0);
		
		this.tblMain.getColumn("amount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("amount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("limitAmount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("limitAmount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		
		//payTime,writtenOffTime,createTime,verifyTime
		String formatString = "yyyy-MM-dd";
		this.tblMain.getColumn("payTime").getStyleAttributes().setNumberFormat(formatString);
		this.tblMain.getColumn("writtenOffTime").getStyleAttributes().setNumberFormat(formatString);
		this.tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat(formatString);
		this.tblMain.getColumn("verifyTime").getStyleAttributes().setNumberFormat(formatString);
		
		this.actionAuditResult.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuWorkFlow.setVisible(false);
    }
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	super.treeMain_valueChanged(e);
    	if (e == null)
			return;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
    	
		execQuery();
    }
    
    protected void comboChequeType_itemStateChanged(ItemEvent e) throws Exception {
    	super.comboChequeType_itemStateChanged(e);
    	execQuery();
    }
    
	protected boolean isIgnoreCUFilter() {
		return true;
	}
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		viewInfo = (EntityViewInfo) this.mainQuery.clone();

		FilterInfo filter = new FilterInfo();
		ChequeTypeEnum cheque = (ChequeTypeEnum) this.comboChequeType.getSelectedItem();
		filter.getFilterItems().add(new FilterItemInfo("chequeType", cheque.getValue()));
		if (node != null
				&& node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo org = (OrgStructureInfo) node.getUserObject();			
			String orgId = org.getUnit().getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("keepOrgUnit.id", orgId));
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
		//---去掉按id排序,修改为按number排序 081222 by zhicheng_jin  ---
		viewInfo.getSorter().clear();
		viewInfo.getSorter().add(new SorterItemInfo("number"));
		//-----------------
		return super.getQueryExecutor(queryPK, viewInfo);
    }
    
	protected String getEditUIName() {
		return ChequeEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ChequeFactory.getRemoteInstance();
	}
	
	/**
	 * 登记
	 * */
	public void actionBookIn_actionPerformed(ActionEvent e) throws Exception {
		super.actionBookIn_actionPerformed(e);
		UIContext uiContext = new UIContext(ui);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ChequeBookInUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	
	/**
	 * 分配
	 * */
	public void actionDistribute_actionPerformed(ActionEvent e) throws Exception {
		super.actionDistribute_actionPerformed(e);
		checkSelected();
		String[] updateIds = (String[]) this.getSelectedIdValues().toArray(new String[]{});
		if (FDCHelper.isEmpty(updateIds)) {
			return;
		}
		
		//已登记状态的票据，才允许分配。
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", FDCHelper.getSetByArray(updateIds),
						CompareType.INCLUDE));
		filter.getFilterItems().add(
				new FilterItemInfo("status", new Integer(
						ChequeStatusEnum.BOOKED_VALUE), CompareType.EQUALS));
		String curUserId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
		filter.getFilterItems().add(
				new FilterItemInfo("keeper.id", curUserId, CompareType.EQUALS));
		
		view.setFilter(filter);
		ChequeCollection coll = ChequeFactory.getRemoteInstance().getChequeCollection(view);
		if (coll != null && coll.size() != updateIds.length) {
			setMessageText("已登记状态，且保管人为自己的票据，才允许分配。");
			showMessage();
			return;
		}
		
		ChequeDistributeUI.showDialogWindows(this, updateIds);
		
		refresh(e);
	}
	
	public void actionVC_actionPerformed(ActionEvent e) throws Exception {
		super.actionVC_actionPerformed(e);
		checkSelected();
		
		List vcIds = this.getSelectedIdValues();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", FDCHelper.getSetByList(vcIds),
						CompareType.INCLUDE));
		filter.getFilterItems().add(
				new FilterItemInfo("verifyStatus", new Integer(
						VerifyStatusEnum.NOTVERIFIED_VALUE), CompareType.EQUALS));
		
		view.setFilter(filter);
		ChequeCollection coll = ChequeFactory.getRemoteInstance().getChequeCollection(view);
		if (coll != null && coll.size() != vcIds.size()) {
			if(!confirmDialog("包含已核销的纪录，是否继续？")){
				return;
			}						
		}else{
			if(!confirmDialog("是否核销?")){
				return;
			}
		}
		ChequeFactory.getRemoteInstance().vc(vcIds);
		refresh(e);
	}
	
	public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception {
		super.actionBlankOut_actionPerformed(e);
		checkSelected();
		
		List blankIds = this.getSelectedIdValues();
		//已登记和已核销的票据才可以作废
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", FDCHelper.getSetByList(blankIds),
						CompareType.INCLUDE));
		filter.getFilterItems().add(
				new FilterItemInfo("status", new Integer(
						ChequeStatusEnum.BOOKED_VALUE), CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("status", new Integer(
						ChequeStatusEnum.VERIFIED_VALUE), CompareType.NOTEQUALS));
		
		view.setFilter(filter);
		ChequeCollection coll = ChequeFactory.getRemoteInstance().getChequeCollection(view);
		if (coll != null && coll.size() > 0) {
			setMessageText("只有已登记和已核销的票据可以作废。");
			showMessage();
			return;
		}
		
		if(confirmDialog("是否作废?")){
			ChequeFactory.getRemoteInstance().abandon(blankIds);
			refresh(e);
		}
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
}