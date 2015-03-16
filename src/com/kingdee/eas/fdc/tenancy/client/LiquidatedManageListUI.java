/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.reportone.r1.print.designer.gui.ScriptViewer.Action;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.ILiquidated;
import com.kingdee.eas.fdc.tenancy.LiquidatedFactory;
import com.kingdee.eas.fdc.tenancy.LiquidatedInfo;
import com.kingdee.eas.fdc.tenancy.LiquidatedManageFactory;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.SurrenderReasonFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class LiquidatedManageListUI extends AbstractLiquidatedManageListUI
{
    private static final Logger logger = CoreUIObject.getLogger(LiquidatedManageListUI.class);
    private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    /**
     * output class constructor
     */
    public LiquidatedManageListUI() throws Exception
    {
        super();
    }

    /**
     * output treeMain_valueChanged method
     */
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
    public void onLoad() throws Exception    {
    	super.onLoad();
    	FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.actionQuery.setVisible(true);
		this.actionLocate.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		
		this.actionEdit.setEnabled(true);
		this.actionRemove.setEnabled(true);
		if (!saleOrg.isIsBizUnit())
		{
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAccount.setEnabled(false);
			this.actionBatchAccount.setEnabled(false);
			this.actionGenTenBillOtherPay.setEnabled(false);
		}
	}
	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected String getEditUIModal()
	{
		return UIFactoryName.MODEL;
	}
	protected void refresh(ActionEvent e) throws Exception
	{
		this.tblMain.removeRows();
	}
	protected String getEditUIName() {
		return LiquidatedManageEditUI.class.getName();
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return LiquidatedManageFactory.getRemoteInstance();
	}
	
	protected void initTree() throws Exception
	{
		this.treeMain.setModel(FDCTreeHelper.getSubareaTree(this.actionOnLoad,null));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	protected String getLongNumberFieldName() {
		return "number";
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		this.actionGenTenBillOtherPay.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_post"));
		this.actionAccount.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_alreadycollate"));
		this.actionBatchAccount.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_alreadycollate"));
	}

	public void actionAccount_actionPerformed(ActionEvent e) throws Exception {
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		UIContext uiContext = new UIContext(this);
		if (node != null&&node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			uiContext.put("sellProject", sellProject);
		}else{
			uiContext.put("sellProject", null);
		}
		IUIWindow uiWindow =UIFactory.createUIFactory(UIFactoryName.MODEL).create("com.kingdee.eas.fdc.tenancy.client.LiquidatedTBOPayUI", uiContext, null, OprtState.VIEW);		    
		uiWindow.show();
		refresh(e);
		super.actionAccount_actionPerformed(e);
	}

	public void actionBatchAccount_actionPerformed(ActionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (!(node.getUserObject() instanceof SellProjectInfo)) {
			MsgBox.showWarning("请选择具体租售项目！");
			this.abort();
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			int sus=LiquidatedManageFactory.getRemoteInstance().batchAccount(sellProject.getId());
			MsgBox.showInfo(this, "成功计算"+sus+"条应收费用违约金！");
		}
		refresh(e);
	}

	public void actionGenTenBillOtherPay_actionPerformed(ActionEvent e)
			throws Exception {
		boolean bool=false;
		int row=0;
		if(KDTableUtil.getSelectedRow(tblMain) == null){
			MsgBox.showWarning(this, "请先选中行！");
			return;
		}else{
			row=KDTableUtil.getSelectedRow(tblMain).getRowIndex();
		}
		MoneyDefineCollection mdCollection=MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection("select * where name ='违约金'");
		if(mdCollection.size()==0){
			MsgBox.showWarning(this, "“营销公共资料-款项类型”中必须定义一条名称和类别都为“违约金”的款项！");
			return;
		}
		if(getSelectedIdValues() != null){
			ArrayList id=getSelectedIdValues();
			int sus=0;
			for(int i=0;i<id.size();i++){
				bool=LiquidatedManageFactory.getRemoteInstance().genTenBillOtherPay(BOSUuid.read(id.get(i).toString()));
				if(bool){
					sus=sus+1;
				}
			}
			MsgBox.showInfo(this, "成功生成"+sus+"条应收费用！");
		}
		refresh(e);
		KDTableUtil.setSelectedRow(tblMain, row);
		super.actionGenTenBillOtherPay_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String idStr = this.getSelectedKeyValue();
		if(idStr!=null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isGenerate",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("id",idStr));
			if(LiquidatedManageFactory.getRemoteInstance().exists(filter)){
				MsgBox.showWarning("已经生成应收的违约金计算不允许删除！");
				abort();
			}
		}
		super.actionRemove_actionPerformed(e);
	}


	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		if (saleOrg.isIsBizUnit()){
			if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
				if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isGenerate").getValue() != null) {
					boolean status = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isGenerate").getValue()).booleanValue();
					changeWBTEnabeld(!status);
				}
			} else {
				disabledWBT();
			}
		}
	}
	private void changeWBTEnabeld(boolean isEnabled) {
		this.actionEdit.setEnabled(!isEnabled);
		this.actionEdit.setEnabled(isEnabled);

	}
	
	private void disabledWBT() {
		this.actionEdit.setEnabled(false);
		this.actionEdit.setEnabled(false);

	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		int row=0;
		if(KDTableUtil.getSelectedRow(tblMain) != null){
			row=KDTableUtil.getSelectedRow(tblMain).getRowIndex();
		}
		super.actionEdit_actionPerformed(e);
		refresh(e);
		KDTableUtil.setSelectedRow(tblMain, row);
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		int row=0;
		if(KDTableUtil.getSelectedRow(tblMain) != null){
			row=KDTableUtil.getSelectedRow(tblMain).getRowIndex();
		}
		super.actionView_actionPerformed(e);
		refresh(e);
		KDTableUtil.setSelectedRow(tblMain, row);
	}
	
	
}