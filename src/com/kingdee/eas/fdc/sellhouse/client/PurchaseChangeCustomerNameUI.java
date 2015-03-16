/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ISellProject;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class PurchaseChangeCustomerNameUI extends AbstractPurchaseChangeCustomerNameUI
{
    private static final Logger logger = CoreUIObject.getLogger(PurchaseChangeCustomerNameUI.class);
    
    /**
     * output class constructor
     */
    public PurchaseChangeCustomerNameUI() throws Exception
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
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	this.execQuery();
    }
    
    public FilterInfo getTreeFilter() throws BOSException{
		return null;
    }

    public SelectorItemCollection getSelectors() {
    	// TODO Auto-generated method stub
    	SelectorItemCollection sic=super.getSelectors();
    	sic.add("purchase.id");
    	sic.add("purchase.room.id");    	
    	return sic;
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }


	protected ICoreBase getBizInterface() throws Exception {
		
		return PurchaseChangeCustomerFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return PurchaseChangeCustomerEditUI.class.getName();
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
	public void onLoad() throws Exception {
		
		if(!SysContext.getSysContext().getCurrentOrgUnit().isIsSaleOrgUnit()){
			MsgBox.showWarning("当前组织不是销售组织，不能进入！");
    		abort();
		}
		if(!SHEHelper.getCurrentSaleOrg().isIsBizUnit()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		super.onLoad();
		this.btnAttachment.setVisible(true);
		this.btnRelationPurchase.setVisible(false);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.setUITitle("更名管理");
		//this.MenuService.setVisible(false);
		initTree();
		this.treeMain.setSelectionRow(0);
	}
	
	protected boolean initDefaultFilter() {
		if (this.getUIContext().get("DefaultQueryInfo") == null) 		
			return true;
		else
			return false;
	}
	
	public void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,null,true));
	}
	
	private CommonQueryDialog commonQueryDialog = null;
	private PurchaseChangeCustomerNameFilterUI filterUI=null;
	
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		return commonQueryDialog;
	}
	
	private PurchaseChangeCustomerNameFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new PurchaseChangeCustomerNameFilterUI(this, this.actionOnLoad);
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(node==null) node = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
		
		String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet());
		if(allSpIdStr.trim().length()==0)
			allSpIdStr = "'nullnull'"; 
		
		try	{
			FilterInfo filter = new FilterInfo();
			if(node!=null){
				if (node.getUserObject() instanceof SellProjectInfo)		{
					SellProjectInfo spInfo = (SellProjectInfo)node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", spInfo.getId().toString()));
				}else if (node.getUserObject() instanceof SubareaInfo)		{
					SubareaInfo subInfo = (SubareaInfo) node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("subarea.id", subInfo.getId().toString()));
				}else if (node.getUserObject() instanceof BuildingInfo)		{
					BuildingInfo bdInfo = (BuildingInfo)node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("building.id", bdInfo.getId().toString()));
				}else if (node.getUserObject() instanceof BuildingUnitInfo)		{
					BuildingInfo bdInfo = (BuildingInfo)((DefaultKingdeeTreeNode)node.getParent()).getUserObject();
					BuildingUnitInfo buInfo = (BuildingUnitInfo)node.getUserObject();
//					filter.getFilterItems().add(new FilterItemInfo("building.id", bdInfo.getId().toString()));
//					filter.getFilterItems().add(new FilterItemInfo("room.unit", new Integer(buInfo.getSeq())));
//					现在已近改为buildUnit这个字段 ，这里的过滤条件也改掉 xiaoao_liu
					filter.getFilterItems().add(new FilterItemInfo("room.buildUnit.id", buInfo.getId().toString()));
				}
			}
			if(filter.getFilterItems().size()==0)
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", allSpIdStr ,CompareType.INNER));
			
			
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (Exception e)
		{
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (KDTableUtil.getSelectedRow(tblMain) == null) {
			MsgBox.showWarning("请先选择认购更名单！");
			SysUtil.abort();
		}
		String id = KDTableUtil.getSelectedRow(tblMain).getCell("id")
				.getValue().toString();

		PurchaseChangeCustomerInfo customerInfo = PurchaseChangeCustomerFactory
				.getRemoteInstance().getPurchaseChangeCustomerInfo(
						"where id = '" + id + "'");
		if (FDCBillStateEnum.SUBMITTED.equals(customerInfo.getState())) {

		} else {
			MsgBox.showWarning("当前单据不允许审批！");
			SysUtil.abort();
		}

		PurchaseChangeCustomerFactory.getRemoteInstance().audit(
				BOSUuid.read(id));
		tblMain.removeRows();
	}
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		
//		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
//		.getLastSelectedPathComponent();
	}
	public void actionChgCheque_actionPerformed(ActionEvent e) throws Exception {
		
		/**
		 * 不知道为什么权限不起作用，老是返回的是true
		 * 结果手动调用后正确，奇怪的问题
		 * by renliang at 2010-11-20
		 * 
		 */
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
		PermissionFactory.getRemoteInstance().checkFunctionPermission(new ObjectUuidPK(user.getId()), new ObjectUuidPK(org.getId()), "she_purchaseChangeCustomer_change");
		
		
		IRow row = KDTableUtil.getSelectedRow(this.tblMain);
		if (row == null) {
			MsgBox.showWarning(this, "请先选中一张更名单!");
			SysUtil.abort();
		} else if (row.getCell("state").getValue() == null
				|| !("已审批").equals(row.getCell("state").getValue().toString())) {
			MsgBox.showWarning(this, "请先审批更名单!");
			SysUtil.abort();
		}
		Map ctx = new com.kingdee.eas.common.client.UIContext(this);
		String purID = (String) row.getCell("id").getValue();
		ctx.put("purID", purID);
		ctx.put(com.kingdee.eas.common.client.UIContext.OWNER, this);

		IUIWindow uiWindow = null;
		// 弹出模式
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				MakeOutChangeCustomerUI.class.getName(), ctx, null,
				OprtState.ADDNEW);

		// 开始展现UI
		uiWindow.show();
		refresh(null);
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		
		super.actionEdit_actionPerformed(e);
	}
	protected void prepareUIContext(
			com.kingdee.eas.common.client.UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		setUIContextAboutTreeNode(uiContext);
		
	}
	
	private void setUIContextAboutTreeNode(com.kingdee.eas.common.client.UIContext uiContext)
	{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof Integer)
		{ // 已作废
			Integer unit = (Integer) node.getUserObject();
			uiContext.put("unit", unit);
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			uiContext.put("building", building);
			uiContext.put("sellProject", building.getSellProject());
		}
		if (node.getUserObject() instanceof BuildingUnitInfo)
		{
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node
					.getUserObject();
			uiContext.put("buildUnit", buildUnit);
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			uiContext.put("building", building);
			uiContext.put("sellProject", building.getSellProject());
		} else if (node.getUserObject() instanceof BuildingInfo)	{
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum))
			{
				uiContext.put("building", building);
				uiContext.put("sellProject", building.getSellProject());
				uiContext.put("unit", new Integer(0));
			}
		}
		else if(node.getUserObject() instanceof SellProjectInfo){
			SellProjectInfo sellProjectInfo = (SellProjectInfo) node.getUserObject();
			if(sellProjectInfo!=null){
				uiContext.put("sellProject", sellProjectInfo);
			}
		}
	}
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		String state =FDCClientHelper.getSelectedKeyValue(tblMain, "state");
		if(!SHEHelper.getCurrentSaleOrg().isIsBizUnit()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}else{
			if("已审批".equals(state)){
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.btnAudit.setEnabled(true);
			}else{
				this.btnEdit.setEnabled(true);
				this.btnRemove.setEnabled(true);
				this.btnAudit.setEnabled(true);
			}
		}
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
}