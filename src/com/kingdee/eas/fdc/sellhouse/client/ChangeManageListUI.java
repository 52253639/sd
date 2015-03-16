/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeManageFactory;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerCollection;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerFactory;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerInfo;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlFactory;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntry;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryFactory;
import com.kingdee.eas.fi.arap.client.util.ArApBillUIUtil;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
/**
 * output class name
 */
public class ChangeManageListUI extends AbstractChangeManageListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChangeManageListUI.class);
    protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
    protected SellProjectInfo sellProject = null;
    protected BuildingInfo building = null;
    protected BuildingUnitInfo buildUnit = null;
    protected Map permit=new HashMap();
    protected boolean isControl=SHEManageHelper.isControl(null, SysContext.getSysContext().getCurrentUserInfo());
    /**
     * output class constructor
     */
    public ChangeManageListUI() throws Exception
    {
        super();
    }

    protected boolean isIgnoreCUFilter() {
		return true;
	}
    public boolean isIgnoreRowCount() {
		return false;
	}
    protected String getEditUIName() {
		return ChangeManageEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws BOSException {
		return ChangeManageFactory.getRemoteInstance();
	}
	
	public void onLoad() throws Exception {
		actionQuery.setEnabled(false);
		initTree();
		super.onLoad();
		initControl();
		actionQuery.setEnabled(true);
	}
	protected String[] getLocateNames()
    {
        String[] locateNames = new String[4];
        locateNames[0] = "number";
        locateNames[1] = "srcRoom.number";
        locateNames[2] = "customerNames";
        locateNames[3] = "customerPhone";
        return locateNames;
    }
	protected void initControl(){
		this.treeMain.setSelectionRow(0);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		FDCHelper.formatTableDate(getBillListTable(), "changeDate");
		FDCHelper.formatTableDate(getBillListTable(), "busAdscriptionDate");
		FDCHelper.formatTableDate(getBillListTable(), "lastUpdateTime");
		if (!isSaleHouseOrg)
		{	this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}

		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);

		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);

		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
	}

    
	protected void initTree() throws Exception {
		this.treeMain.setModel(CRMTreeHelper.getSellProjectTree(actionOnLoad, true));
		
		SHEManageHelper.expandAllNodesByBuilding(treeMain, (DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
	}
	protected void refresh(ActionEvent e) throws Exception
	{
		this.tblMain.removeRows();
	}
    protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}
    protected void prepareUIContext(UIContext uiContext, ActionEvent e)
	{
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("sellProject", sellProject);
		uiContext.put("building", building);
		uiContext.put("buildUnit", buildUnit);
	}

	 /**
     * 审批
     */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	FDCClientUtils.checkBillInWorkflow(this, id);
    	
		BizEnumValueInfo billState = (BizEnumValueInfo) row.getCell(getBillStatePropertyName()).getValue();
		if (!FDCBillStateEnum.SUBMITTED_VALUE.equalsIgnoreCase((String) billState.getValue())) {
			FDCMsgBox.showWarning("该单据不是提交状态，不能进行审批操作！");
			return;
		}
		
		ChangeManageFactory.getRemoteInstance().audit(BOSUuid.read(id));
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	/**
	 * 反审批
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	FDCClientUtils.checkBillInWorkflow(this, id);
    	
    	SHEManageHelper.checkCanUnAuditChangeManage(this, id);
    	
		BizEnumValueInfo billState = (BizEnumValueInfo) row.getCell(getBillStatePropertyName()).getValue();
		if (!FDCBillStateEnum.AUDITTED_VALUE.equalsIgnoreCase((String) billState.getValue())) {
			FDCMsgBox.showWarning("该单据不是审批状态，不能进行反审批操作！");
			return;
		}
		
		ChangeManageFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	FDCClientUtils.checkBillInWorkflow(this, id);
    	
		BizEnumValueInfo billState = (BizEnumValueInfo) row.getCell(getBillStatePropertyName()).getValue();
		if (!FDCBillStateEnum.SUBMITTED_VALUE.equalsIgnoreCase((String) billState.getValue())&&!FDCBillStateEnum.SAVED_VALUE.equalsIgnoreCase((String) billState.getValue())) {
			FDCMsgBox.showWarning("该单据不是保存或者提交状态，不能进行修改操作！");
			return;
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	FDCClientUtils.checkBillInWorkflow(this, id);
    	
		BizEnumValueInfo billState = (BizEnumValueInfo) row.getCell(getBillStatePropertyName()).getValue();

		if (!FDCBillStateEnum.SUBMITTED_VALUE.equalsIgnoreCase((String) billState.getValue())&&!FDCBillStateEnum.SAVED_VALUE.equalsIgnoreCase((String) billState.getValue())) {
			FDCMsgBox.showWarning("该单据不是保存或者提交状态，不能进行删除操作！");
			return;
		}
		
		super.actionRemove_actionPerformed(e);
	}
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = this.getSelectedKeyValue();
        checkSelected();
        if (boID == null)
        {
            return;
        }
        acm.showAttachmentListUIByBoID(boID, this,true);
	}
    protected void initWorkButton() {
		super.initWorkButton();
		
        this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
        this.menuItemAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
       
        this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
        this.menuItemUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
	}
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		try	{
			FilterInfo filter = new FilterInfo();
			if(node!=null&&node.getUserObject()!=null){
				if (node.getUserObject() instanceof SellProjectInfo){
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", SHEManageHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet()),CompareType.INNER));	
				}else if (node.getUserObject() instanceof BuildingInfo){ 
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
				}else if (node.getUserObject() instanceof BuildingUnitInfo){ 
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnit.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
				} else if (node.getUserObject() instanceof OrgStructureInfo){
					filter.getFilterItems().add(new FilterItemInfo("id", "'null'"));
				}
				if (!(node.getUserObject() instanceof OrgStructureInfo)&&!isControl){
					Set id=getHeadIdByPermitSaleManView();
					if(id.size()==0){
						filter.getFilterItems().add(new FilterItemInfo("srcId", "'null'",CompareType.INNER));
					}else{
						filter.getFilterItems().add(new FilterItemInfo("srcId",id,CompareType.INNER));
					}
				}
			}else{
				filter.getFilterItems().add(new FilterItemInfo("id", "'null'"));
			}
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
			if(viewInfo.getSorter()!=null&&viewInfo.getSorter().size()<2){
				viewInfo.getSorter().clear();
				viewInfo.getSorter().add(new SorterItemInfo("srcRoom.building.number"));
				viewInfo.getSorter().add(new SorterItemInfo("srcRoom.unit"));
				viewInfo.getSorter().add(new SorterItemInfo("srcRoom.floor"));
				viewInfo.getSorter().add(new SorterItemInfo("srcRoom.number"));
			}
		}catch (Exception e)
		{
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		sellProject=null;
		building=null;
		buildUnit=null;
		if (node.getUserObject() instanceof SellProjectInfo){
			//项目
			if(node.getUserObject() != null){
				sellProject=(SellProjectInfo) node.getUserObject();
			}			
		}else if (node.getUserObject() instanceof BuildingInfo){ 
			// 楼栋
			if(node.getUserObject() != null){
				building=(BuildingInfo)node.getUserObject();
				sellProject = building.getSellProject();
			}
		}else if (node.getUserObject() instanceof BuildingUnitInfo){ 
			// 单元
			if(node.getUserObject() != null){
				buildUnit=(BuildingUnitInfo)node.getUserObject();
				building=buildUnit.getBuilding();
				sellProject = buildUnit.getBuilding().getSellProject();
			}
		} 
		
		if (node.getUserObject() instanceof OrgStructureInfo){
			this.actionAddNew.setEnabled(false);
		}else if(node.getUserObject() instanceof SellProjectInfo){
			if(SHEManageHelper.isSellProjectHasChild((SellProjectInfo) node.getUserObject())){
				this.actionAddNew.setEnabled(false);
			}else if(isSaleHouseOrg){
				this.actionAddNew.setEnabled(true);
			}
		}else{
			if(isSaleHouseOrg){
				this.actionAddNew.setEnabled(true);
			}
		}
		this.refresh(null);
	}
    private Set getHeadIdByPermitSaleManView() throws EASBizException, BOSException{
		Set id=new HashSet();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("user.id",SHEManageHelper.getPermitSaleManSet(sellProject,permit),CompareType.INCLUDE));
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(node!=null){
			if (node.getUserObject() instanceof SellProjectInfo){
				//项目
				if(node.getUserObject() != null){
					filter.getFilterItems().add(new FilterItemInfo("head.sellProject.id", SHEManageHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet()),CompareType.INNER));	
				}
			}else if (node.getUserObject() instanceof BuildingInfo){ 
				// 楼栋
				if(node.getUserObject() != null){
					filter.getFilterItems().add(new FilterItemInfo("head.room.building.id", building.getId().toString()));
				}
			}else if (node.getUserObject() instanceof BuildingUnitInfo){ 
				// 单元
				if(node.getUserObject() != null){
					filter.getFilterItems().add(new FilterItemInfo("head.room.buildUnit.id", buildUnit.getId().toString()));
				}
			} else if (node.getUserObject() instanceof OrgStructureInfo){
				//组织
				if(node.getUserObject() != null){
					filter.getFilterItems().add(new FilterItemInfo("head.sellProject.id", "'null'",CompareType.INNER));
				}
			}
		}
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("head.id");
		view.setFilter(filter);
		view.setSelector(sic);
		
		PrePurchaseSaleManEntryCollection precol=PrePurchaseSaleManEntryFactory.getRemoteInstance().getPrePurchaseSaleManEntryCollection(view);
		for(int i=0;i<precol.size();i++){
			id.add(precol.get(i).getHead().getId());
		}
		PurSaleManEntryCollection purcol=PurSaleManEntryFactory.getRemoteInstance().getPurSaleManEntryCollection(view);
		for(int i=0;i<purcol.size();i++){
			id.add(purcol.get(i).getHead().getId());
		}
		SignSaleManEntryCollection signcol=SignSaleManEntryFactory.getRemoteInstance().getSignSaleManEntryCollection(view);
		for(int i=0;i<signcol.size();i++){
			id.add(signcol.get(i).getHead().getId());
		}
		return id;
	}
    
//	CommonQueryDialog dialog = null;
//	CustomerQueryPanel userPanel = null;
//	protected CommonQueryDialog initCommonQueryDialog(){
//		dialog = super.initCommonQueryDialog();
//		try {
//			dialog.addUserPanel(getUserPanel());
//			dialog.setShowFilter(true);
//			dialog.setShowSorter(true);
//			dialog.setHeight(380);
//			dialog.setWidth(500);
//			dialog.setTitle("客户自定义过滤框");
//		}
//		catch (Exception e) {
//			handUIException(e);
//		}
//		return dialog;
//	}
//	protected CustomerQueryPanel getUserPanel() throws Exception 
//	{
//		if (this.userPanel == null)
//			this.userPanel = new ChangeManageFilterUI();
//		userPanel.onLoad();
//		return this.userPanel;
//	}
    /* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.sellhouse.client.BaseTransactionListUI#afterTableFillData(com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent)
	 */
	protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		//src modified  begin 2012-02-20 13:23
		try {
//			addTableInfo();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//src end
		
//		CRMClientHelper.getFootRow(tblMain, new String[]{"srcRoom.buildingArea","srcRoom.roomArea","srcRoom.standardTotalAmount","room.buildingArea","room.roomArea","room.standardTotalAmount","dealTotalAmount","delayMoney"});
	}
	//增加客户证件号码、联系地址、原认购日期、原签约日期、违约金、置业顾问
    protected void addTableInfo() throws Exception{
    	FilterItemCollection filters = null;
    	Object obj =null;
    	if(this.mainQuery.getFilter()!=null){
    		filters = this.mainQuery.getFilter().getFilterItems();
    		obj = ArApBillUIUtil.getCompareValue(filters, "bizType", CompareType.EQUALS);
    	}
    	int count = this.tblMain.getRowCount();
    	for(int i=0;i<count;i++){
    		IRow row = this.tblMain.getRow(i);
    		String srcId = (String)row.getCell("srcId").getValue();
    		//源单据信息
    		if(row.getCell("srcId").getValue()!=null){
    			ObjectUuidPK pk=new ObjectUuidPK(srcId);
    			IObjectValue objectValue=null;
    			objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk,SHEManageHelper.getBizSelectors(pk.getObjectType()));
				//认购管理单
    			if(objectValue instanceof PurchaseManageInfo){
					PurCustomerEntryCollection customerCol=((PurchaseManageInfo)objectValue).getPurCustomerEntry();
					for(int j=0;j<customerCol.size();j++){
						PurCustomerEntryInfo purInfo = customerCol.get(j);
						if(purInfo.isIsMain()){//主客户
							row.getCell("srcCertificateNumber").setValue(purInfo.getCertificateNumber());
							row.getCell("srcAddress").setValue(purInfo.getAddress());
						}
					}
					row.getCell("saleManNames").setValue(((PurchaseManageInfo)objectValue).getSaleManNames());
					row.getCell("srcPurchaseDate").setValue(((PurchaseManageInfo)objectValue).getBizDate());
				}
    			//签约管理单
				if(objectValue instanceof SignManageInfo){
					SignCustomerEntryCollection customerCol=((SignManageInfo)objectValue).getSignCustomerEntry();
					for(int j=0;j<customerCol.size();j++){
						SignCustomerEntryInfo signInfo = customerCol.get(j);
						if(signInfo.isIsMain()){//主客户
							row.getCell("srcCertificateNumber").setValue(signInfo.getCertificateNumber());
							row.getCell("srcAddress").setValue(signInfo.getAddress());
						}
					}
					row.getCell("saleManNames").setValue(((SignManageInfo)objectValue).getSaleManNames());
					row.getCell("srcSignDate").setValue(((SignManageInfo)objectValue).getBizDate());
				}
    		}
    		
    		//新单据信息
    		String newId = (String)row.getCell("newId").getValue();
    		if(row.getCell("newId").getValue()!=null){
    			ObjectUuidPK pk=new ObjectUuidPK(newId);
    			IObjectValue objectValue=null;
    			objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk,SHEManageHelper.getBizSelectors(pk.getObjectType()));
				if(objectValue instanceof PurchaseManageInfo){
					PurCustomerEntryCollection customerCol=((PurchaseManageInfo)objectValue).getPurCustomerEntry();
					for(int j=0;j<customerCol.size();j++){
						PurCustomerEntryInfo purInfo = customerCol.get(j);
						if(purInfo.isIsMain()){//主客户
							row.getCell("customerNames").setValue(purInfo.getCertificateNumber());
							row.getCell("customerPhone").setValue(purInfo.getCertificateNumber());
							row.getCell("certificateNumber").setValue(purInfo.getCertificateNumber());
							row.getCell("address").setValue(purInfo.getAddress());
						}
					}
				}
				if(objectValue instanceof SignManageInfo){
					SignCustomerEntryCollection customerCol=((SignManageInfo)objectValue).getSignCustomerEntry();
					for(int j=0;j<customerCol.size();j++){
						SignCustomerEntryInfo signInfo = customerCol.get(j);
						if(signInfo.isIsMain()){//主客户
							row.getCell("customerNames").setValue(signInfo.getCertificateNumber());
							row.getCell("customerPhone").setValue(signInfo.getCertificateNumber());
							row.getCell("certificateNumber").setValue(signInfo.getCertificateNumber());
							row.getCell("address").setValue(signInfo.getAddress());
						}
					}
				}
    		}
    		if(obj!=null&&obj.toString().equals("quitRoom")){
        		//违约金
        		if(row.getCell("srcRoom.id").getValue()!=null){
        			String srcRoomId = row.getCell("srcRoom.id").getValue().toString();
            		String oql = " where room.id = '"+srcRoomId+"' ";
            		DefaultAmountMangerCollection coll = DefaultAmountMangerFactory.getRemoteInstance().getDefaultAmountMangerCollection(oql);
            		if(coll.size()>0){
            			DefaultAmountMangerInfo damInfo = coll.get(0);
            			row.getCell("delayMoney").setValue(damInfo.getCarryAmount());
            		}
        		}
    		}

    	}
    	
		if (obj != null) {
			if(obj.toString().equals("ChangeRoom")){
				//原房间、新房间、新客户
				this.tblMain.getColumn("srcRoom.name").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("srcRoom.buildingArea").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("srcRoom.roomArea").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("srcRoom.standardTotalAmount").getStyleAttributes().setHided(false);
			
				this.tblMain.getColumn("room.name").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("room.buildingArea").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("room.roomArea").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("room.standardTotalAmount").getStyleAttributes().setHided(false);
				
				this.tblMain.getColumn("srcCustomerNames").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("srcCustomerPhone").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("srcCertificateNumber").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("srcAddress").getStyleAttributes().setHided(true);
				
				this.tblMain.getColumn("customerNames").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("customerPhone").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("certificateNumber").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("address").getStyleAttributes().setHided(false);
				
				this.tblMain.getColumn("delayMoney").getStyleAttributes().setHided(true);
				this.tblMain.getHeadRow(0).getCell("changeDate").setValue("换房日期");
//				this.tblMain.getHead().getRow2(0).set;//换房日期
			}else if(obj.toString().equals("ChangeName")){
				//原房间、原客户、新客户
				this.tblMain.getColumn("srcRoom.name").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("srcRoom.buildingArea").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("srcRoom.roomArea").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("srcRoom.standardTotalAmount").getStyleAttributes().setHided(false);
			
				this.tblMain.getColumn("room.name").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("room.buildingArea").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("room.roomArea").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("room.standardTotalAmount").getStyleAttributes().setHided(true);
				
				this.tblMain.getColumn("srcCustomerNames").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("srcCustomerPhone").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("srcCertificateNumber").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("srcAddress").getStyleAttributes().setHided(false);
				
				this.tblMain.getColumn("customerNames").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("customerPhone").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("certificateNumber").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("address").getStyleAttributes().setHided(false);
				
				this.tblMain.getColumn("delayMoney").getStyleAttributes().setHided(true);
				this.tblMain.getHeadRow(0).getCell("changeDate").setValue("更名日期");
			}else if(obj.toString().equals("quitRoom")){
				//原房间、原客户、违约金
				this.tblMain.getColumn("srcRoom.name").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("srcRoom.buildingArea").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("srcRoom.roomArea").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("srcRoom.standardTotalAmount").getStyleAttributes().setHided(false);
			
				this.tblMain.getColumn("room.name").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("room.buildingArea").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("room.roomArea").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("room.standardTotalAmount").getStyleAttributes().setHided(true);
				
				this.tblMain.getColumn("srcCustomerNames").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("srcCustomerPhone").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("srcCertificateNumber").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("srcAddress").getStyleAttributes().setHided(false);
				
				this.tblMain.getColumn("customerNames").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("customerPhone").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("certificateNumber").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("address").getStyleAttributes().setHided(true);
				
				this.tblMain.getColumn("delayMoney").getStyleAttributes().setHided(false);
				this.tblMain.getHeadRow(0).getCell("changeDate").setValue("退房日期");
			}
		}else{
			this.tblMain.getColumn("srcRoom.name").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("srcRoom.buildingArea").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("srcRoom.roomArea").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("srcRoom.standardTotalAmount").getStyleAttributes().setHided(true);
		
			this.tblMain.getColumn("room.name").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("room.buildingArea").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("room.roomArea").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("room.standardTotalAmount").getStyleAttributes().setHided(true);
			
			this.tblMain.getColumn("srcCustomerNames").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("srcCustomerPhone").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("srcCertificateNumber").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("srcAddress").getStyleAttributes().setHided(true);
			
			this.tblMain.getColumn("customerNames").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("customerPhone").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("certificateNumber").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("address").getStyleAttributes().setHided(true);
			
			this.tblMain.getColumn("delayMoney").getStyleAttributes().setHided(true);
			this.tblMain.getHeadRow(0).getCell("changeDate").setValue("变更日期");
		}
    }
    protected CommonQueryDialog initCommonQueryDialog() {
		CommonQueryDialog commonQueryDialog = super.initCommonQueryDialog();
		try
		{
			commonQueryDialog.addUserPanel(new ChangeManageFilterUI());
			commonQueryDialog.setHeight(350);
			commonQueryDialog.setWidth(600);
		}
		catch(Exception e)
		{
			super.handUIException(e);
		}
		return commonQueryDialog;
	}
   protected boolean initDefaultFilter() {
	   return true;
   }
}