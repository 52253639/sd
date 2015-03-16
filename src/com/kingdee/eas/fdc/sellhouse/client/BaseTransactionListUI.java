/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeManageFactory;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CommercialStageEnum;
import com.kingdee.eas.fdc.sellhouse.IBaseTransaction;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ProjectProductTypeSet;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.app.MarketingUnitControllerBean;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
/**
 * output class name
 */
public abstract class BaseTransactionListUI extends AbstractBaseTransactionListUI implements SHEParamConstant
{
    private static final Logger logger = CoreUIObject.getLogger(BaseTransactionListUI.class);
    
    protected SellProjectInfo sellProject = null;
    protected BuildingInfo building = null;
    protected BuildingUnitInfo buildUnit = null;
    protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
    protected static String IAMPURCHASE = "purchase";
    protected static String IAMPREPURCHASE = "prePurchase";
    protected static String IAMSIGN = "sign";
    protected UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();		
    protected boolean isControl=SHEManageHelper.isControl(null, currentUserInfo);
    protected static final String CANTEDIT = "cantEdit";
    protected static final String CANTREMOVE = "cantRemove";
    protected Map roomDisplay=new HashMap();
    protected Map permit=new HashMap();
    public BaseTransactionListUI() throws Exception
    {
        super();
    }

    protected String[] getLocateNames()
    {
        String[] locateNames = new String[4];
        locateNames[0] = "number";
        locateNames[1] = "room.number";
        locateNames[2] = "customerNames";
        locateNames[3] = "customerPhone";
        return locateNames;
    }
    protected void initTree() throws Exception
	{
    	this.treeMain.setModel(FDCTreeHelper.getUnitTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
    	SHEManageHelper.expandAllNodesByBuilding(treeMain, (DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
    }
    public void onLoad() throws Exception
	{
    	
    	actionQuery.setEnabled(false);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		
		if(this.getUIContext().get("filter")==null){
			initTree();
		}else{
			this.toolBar.setVisible(false);
			this.treeView.setVisible(false);
		}
		initControl();
		
		actionQuery.setEnabled(true);
		
	}
    protected void initControl(){
    	this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    	this.treeMain.setSelectionRow(0);
    	   
		if (!isSaleHouseOrg)
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionInvalid.setEnabled(false);
			this.actionReceiveBill.setEnabled(false);
			
			this.actionChangeName.setEnabled(false);
			this.actionChangeRoom.setEnabled(false);
			this.actionQuitRoom.setEnabled(false);
			this.actionPriceChange.setEnabled(false);
		}

		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);

		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);

		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		
		this.actionReceiveBill.setVisible(false);
		
		FDCHelper.formatTableDate(getBillListTable(), "lastUpdateTime");
		FDCHelper.formatTableDate(getBillListTable(), "bizDate");
		FDCHelper.formatTableDate(getBillListTable(), "busAdscriptionDate");
		
		this.actionPriceChange.setVisible(false);
    }
    protected void initWorkButton() {
		super.initWorkButton();
		
        this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
        this.menuItemAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
       
        this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
        this.menuItemUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
        
        this.btnInvalid.setIcon(EASResource.getIcon("imgTbtn_blankout"));
        this.menuItemInvalid.setIcon(EASResource.getIcon("imgTbtn_blankout"));
        
        this.btnReceiveBill.setIcon(EASResource.getIcon("imgTbtn_monadismpostil"));
        this.menuItemReceiveBill.setIcon(EASResource.getIcon("imgTbtn_monadismpostil"));
        
        this.menuItemChangeName.setIcon(EASResource.getIcon("imgTbtn_recieversetting"));
        
        this.menuItemQuitRoom.setIcon(EASResource.getIcon("imgTbtn_quit"));
        
        this.menuItemChangeRoom.setIcon(EASResource.getIcon("imgTbtn_assetchange"));
        
        this.menuItemPriceChange.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
        
        KDMenuItem menuItem1 = new KDMenuItem();
    	menuItem1.setAction(this.actionChangeName);
    	menuItem1.setText("更名");
    	menuItem1.setIcon(EASResource.getIcon("imgTbtn_recieversetting"));
        
        KDMenuItem menuItem2 = new KDMenuItem();
        menuItem2.setAction(this.actionQuitRoom);
        menuItem2.setText("退房");
    	menuItem2.setIcon(EASResource.getIcon("imgTbtn_quit"));
    	
        KDMenuItem menuItem3 = new KDMenuItem();
        menuItem3.setAction(this.actionChangeRoom);
        menuItem3.setText("换房");
    	menuItem3.setIcon(EASResource.getIcon("imgTbtn_assetchange"));
    	
        KDMenuItem menuItem4 = new KDMenuItem();
        menuItem4.setAction(this.actionPriceChange);
        menuItem4.setText("价格变更");
    	menuItem4.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
    	
		this.btnSpecialBiz.setIcon(EASResource.getIcon("imgTbtn_disassemble"));
		this.btnSpecialBiz.addAssistMenuItem(menuItem1);
		this.btnSpecialBiz.addAssistMenuItem(menuItem2);
		this.btnSpecialBiz.addAssistMenuItem(menuItem3);
		this.btnSpecialBiz.addAssistMenuItem(menuItem4);
		
		this.btnMultiSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
		this.btnImport.setIcon(EASResource.getIcon("imgTbtn_input"));
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
     * 更名
     */
    public void actionChangeName_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	
    	changeNameCheck(id);
    	
    	SHEManageHelper.toChangeManage(ChangeBizTypeEnum.CHANGENAME,BOSUuid.read(id), this, false);
		this.refresh(null);
    }

    /**
     * 退房
     */
    public void actionQuitRoom_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	
    	quitRoomCheck(id);

    	SHEManageHelper.toChangeManage(ChangeBizTypeEnum.QUITROOM,BOSUuid.read(id), this, false);
		this.refresh(null);
    }

    /**
     * 换房
     */
    public void actionChangeRoom_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	
    	changeRoomCheck(id);
    	
    	SHEManageHelper.toChangeManage(ChangeBizTypeEnum.CHANGEROOM,BOSUuid.read(id), this, false);
		this.refresh(null);
    }

    /**
     * 价格变更
     */
    public void actionPriceChange_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	
    	priceChangeCheck(id);
    	
    	SHEManageHelper.toChangeManage(ChangeBizTypeEnum.PRICECHANGE,BOSUuid.read(id), this, false);
		this.refresh(null);
    }
    public TransactionStateEnum getBizState(String id) throws EASBizException, BOSException, Exception{
    	if(id==null) return null;
    	SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("bizState");
    	return ((BaseTransactionInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels)).getBizState();
    }
    /**
     * 审批
     */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	checkRef(id.get(i).toString());
	    	auditCheck(id.get(i).toString());
	    	
			if (!getBizStateSubmitEnum().equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				return;
			}
			((IBaseTransaction)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	/**
	 * 反审批
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	checkRef(id.get(i).toString());
	    	unAuditCheck(id.get(i).toString());
	    	
			if (!getBizStateAuditEnum().equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				return;
			}
			((IBaseTransaction)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	 /**
     * 作废
     */
    public void actionInvalid_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	checkRef(id.get(i).toString());
	    	invalidCheck(id.get(i).toString());
	    	
			if (!getBizStateSubmitEnum().equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行作废操作！");
				return;
			}
			((IBaseTransaction)getBizInterface()).invalid(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
    protected void checkBeforeEditOrRemove(String warning,String id) throws EASBizException, BOSException, Exception {
    	//检查是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		TransactionStateEnum bizState = getBizState(id);
		
		if (!getBizStateSubmitEnum().equals(bizState)&&!getBizStateSavedEnum().equals(bizState)) {
			if(warning.equals(CANTEDIT)){
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行修改操作！");
				SysUtil.abort();
			}else{
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行删除操作！");
				SysUtil.abort();
			}
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	checkRef(id);
    	editCheck(id);
    	
    	checkBeforeEditOrRemove(CANTEDIT,id);
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
	    	checkRef(id.get(i).toString());
	    	removeCheck(id.get(i).toString());
	    	checkBeforeEditOrRemove(CANTREMOVE,id.get(i).toString());
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
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		try	{
			FilterInfo filter = new FilterInfo();
			if(this.getUIContext().get("filter")==null){
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
						filter.getFilterItems().add(new FilterItemInfo("sellProject.id", "'null'",CompareType.INNER));
					}
					if (!(node.getUserObject() instanceof OrgStructureInfo)&&!isControl){
						if(IAMPREPURCHASE.equals(whoAmI())){
							filter.getFilterItems().add(new FilterItemInfo("prePurchaseSaleManEntry.user.id", SHEManageHelper.getPermitSaleManSet(sellProject,permit),CompareType.INCLUDE));
						}else if(IAMPURCHASE.equals(whoAmI())){
							filter.getFilterItems().add(new FilterItemInfo("purSaleManEntry.user.id", SHEManageHelper.getPermitSaleManSet(sellProject,permit),CompareType.INCLUDE));
						}else if(IAMSIGN.equals(whoAmI())){
							filter.getFilterItems().add(new FilterItemInfo("signSaleManEntry.user.id", SHEManageHelper.getPermitSaleManSet(sellProject,permit),CompareType.INCLUDE));
						}
					}
				}else{
					filter.getFilterItems().add(new FilterItemInfo("id", "'null'"));
				}
			}else{
				filter.mergeFilter((FilterInfo) this.getUIContext().get("filter"), "and");
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
				viewInfo.getSorter().add(new SorterItemInfo("building.number"));
				viewInfo.getSorter().add(new SorterItemInfo("room.unit"));
				viewInfo.getSorter().add(new SorterItemInfo("room.floor"));
				viewInfo.getSorter().add(new SorterItemInfo("room.number"));
			}
		}catch (Exception e)
		{
			handleException(e);
		}
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
	
	/**
	 * 审批事件扩充状态校验
	 */
	protected void auditCheck(String id)throws EASBizException, BOSException{
	}
	
	/**
	 * 反审批事件扩充状态校验
	 */
	protected void unAuditCheck(String id)throws EASBizException, BOSException{
	}
	
	/**
	 * 作废事件扩充状态校验
	 */
    protected void invalidCheck(String id)throws EASBizException, BOSException{
    }
	
    /**
	 * 修改事件扩充状态校验
	 */
	protected void editCheck(String id)throws EASBizException, BOSException{
	}
	
	/**
	 * 删除事件扩充状态校验
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	protected void removeCheck(String id) throws EASBizException, BOSException{
	}
	
	/**
	 * 更名事件扩充状态校验
	 */
	protected void changeNameCheck(String id)throws EASBizException, BOSException{
		
	}
	
	/**
	 * 换房事件扩充状态校验
	 */
	protected void changeRoomCheck(String id)throws EASBizException, BOSException{
		
	}
	
	/**
	 * 退房扩充状态校验
	 */
	protected void quitRoomCheck(String id)throws EASBizException, BOSException{
		
	}
	
	/**
	 * 价格变更扩充状态校验
	 */
	protected void priceChangeCheck(String id)throws EASBizException, BOSException{
		
	}
	
	/**
    *
    * 检查是否有关联对象
    *
    */
   protected void checkRef(String id) throws Exception {

   }
   
   /**
    * 屏蔽掉FDCBILLLIST里的这个方法，因为这个这是考虑到了成本模块，CRM这边无用
    */
   protected void fetchInitData() throws Exception {
	   
   }
   /**
    * 返回业务状态枚举审批通过状态枚举值
    */
   protected abstract TransactionStateEnum getBizStateAuditEnum();
   
   /**
    * 返回业务状态枚举提交状态枚举值
    */
   protected abstract TransactionStateEnum getBizStateSubmitEnum();
   
   protected abstract TransactionStateEnum getBizStateSavedEnum();
   
   /**
    * 返回业务状态枚举作废状态枚举值
    */
   protected abstract TransactionStateEnum getBizStateInvalidEnum();
   
   protected void afterTableFillData(KDTDataRequestEvent e) {
	   super.afterTableFillData(e);
	   setInvalidColor(whoAmI());
   }
   protected Map getRoomDisplay(String sellProjectId,String projectTypeId){
	   if(roomDisplay.containsKey(sellProjectId)){
		   Map projectType=(Map) roomDisplay.get(sellProjectId);
		   if(projectType.containsKey(projectTypeId)){
			   return (Map) projectType.get(projectTypeId);
		   }else{
			   Map map=RoomDisplaySetting.getNewProjectProductTypeSet(null,sellProjectId, projectTypeId);
			   projectType.put(projectTypeId, map);
			   return map;
		   }
	   }else{
		   Map map=RoomDisplaySetting.getNewProjectProductTypeSet(null,sellProjectId, projectTypeId);
		   Map sellProject=new HashMap();
		   sellProject.put(projectTypeId, map);
		   roomDisplay.put(sellProjectId, sellProject);
		   return map;
	   }
   }
   abstract protected String whoAmI();
   
   protected void setInvalidColor(String who){
	   long dayTime = 3600*24*1000;
	  Date curDate =  new Date();
	   if(IAMPREPURCHASE.equals(who)){
		   for(int i = 0 ; i < tblMain.getRowCount();i++){
			    IRow row = tblMain.getRow(i);
			    String sellProjectId = (String)getSelectedKeyValue(row,"sellProject.id");
			    String projectTypeId =(String)getSelectedKeyValue(row,"productType.id");
			    Map typeSet = getRoomDisplay(sellProjectId, projectTypeId);
			    Date bizDate = (Date)getSelectedKeyValue(row,"bizDate");
			    if(bizDate==null) continue;
			    long  diffd =  FDCDateHelper.dateDiff(bizDate,curDate )/dayTime ;
			    if(typeSet == null)continue;
			    int in =  ((Integer)typeSet.get(T1_PRE_PURCHASE_LIMIT_TIME)).intValue();
			    if(diffd > in){
			    	row.getStyleAttributes().setBackground(Color.pink);
			    }
		   }
	   }else if(IAMPURCHASE.equals(who)){
		   for(int i = 0 ; i < tblMain.getRowCount();i++){
			    IRow row = tblMain.getRow(i);
			    String sellProjectId = (String)getSelectedKeyValue(row,"sellProject.id");
			    String projectTypeId =(String)getSelectedKeyValue(row,"productType.id");
			    Map typeSet = getRoomDisplay(sellProjectId, projectTypeId);
			    Date bizDate = (Date)getSelectedKeyValue(row,"bizDate");
			    if(bizDate==null)continue;
			    long  diffd =  FDCDateHelper.dateDiff(bizDate,curDate )/dayTime ;
			    if(typeSet == null)continue;
			    int in =  ((Integer)typeSet.get(T1_PURCHASE_LIMIT_TIME)).intValue();
			    if(diffd > in){
			    	row.getStyleAttributes().setBackground(Color.pink);
			    }
//			    int in2 =  ((Integer)typeSet.get(T1_TO_SIGN_LIMIT_TIME)).intValue();
//			    Date planSignDate = (Date)getSelectedKeyValue(row,"planSignDate");
//			    if(planSignDate==null) continue;
//			    long  diffd2 =  FDCDateHelper.dateDiff(planSignDate,curDate )/dayTime ;
//			    if(diffd2 > in2){
//			    	row.getStyleAttributes().setBackground(Color.pink);
//			    }
		   }
	   }else if(IAMSIGN.equals(who)){
		   for(int i = 0 ; i < tblMain.getRowCount();i++){
			    IRow row = tblMain.getRow(i);
			    String sellProjectId = (String)getSelectedKeyValue(row,"sellProject.id");
			    String projectTypeId =(String)getSelectedKeyValue(row,"productType.id");
			    Map typeSet = getRoomDisplay(sellProjectId, projectTypeId);
			    Date bizDate = (Date)getSelectedKeyValue(row,"bizDate");
			    if(bizDate==null) continue;
			    long  diffd =  FDCDateHelper.dateDiff(bizDate,curDate )/dayTime ;
			    if(typeSet == null) continue;
			    int in =  ((Integer)typeSet.get(T1_SIGN_LIMIT_TIME)).intValue();
			    if(diffd > in){
			    	row.getStyleAttributes().setBackground(Color.pink);
			    }
		   }
	   }
   }
   
   protected Object getSelectedKeyValue(IRow row,String feildName) {
       if (row == null){
    	   return null;
       }
       ICell cell = row.getCell(feildName);
       return cell.getValue();
   }
   public void actionMultiSubmit_actionPerformed(ActionEvent e) throws Exception {
	   checkSelected();
	   Window win = SwingUtilities.getWindowAncestor(this);
       LongTimeDialog dialog = null;
       if(win instanceof Frame)
           dialog = new LongTimeDialog((Frame)win);
       else
       if(win instanceof Dialog)
           dialog = new LongTimeDialog((Dialog)win);
       
       dialog.setLongTimeTask(new ILongTimeTask() {
           public Object exec()
               throws Exception
           {
        	   ArrayList id = getSelectedIdValues();
        	   if(IAMPREPURCHASE.equals(whoAmI())){
        		   for(int i = 0; i < id.size(); i++){
        			   UIContext uiContext = new UIContext(this);
        			   uiContext.put("ID", id.get(i).toString());
        			   PrePurchaseManageEditUI ui=(PrePurchaseManageEditUI) UIFactoryHelper.initUIObject(PrePurchaseManageEditUI.class.getName(), uiContext, null,OprtState.EDIT);
        			   TransactionStateEnum state = ((BaseTransactionInfo)ui.getEditData()).getBizState();
        			   FDCClientUtils.checkBillInWorkflow(ui, ui.getEditData().getId().toString());
        				
        			   if(state==null||!(TransactionStateEnum.PREAPPLE.equals(state)||TransactionStateEnum.PRESAVED.equals(state))){
        					MsgBox.showWarning("单据不是保存或者提交状态，不能进行提交操作！");
        					SysUtil.abort();
        			   }
        			   ui.verifyInputForSubmint();
        			   ui.runSubmit();
        			   ui.destroyWindow();
        		   }
        	   }else if(IAMPURCHASE.equals(whoAmI())){
        		   for(int i = 0; i < id.size(); i++){
        			   UIContext uiContext = new UIContext(this);
        			   uiContext.put("ID", id.get(i).toString());
        			   PurchaseManageEditUI ui=(PurchaseManageEditUI) UIFactoryHelper.initUIObject(PurchaseManageEditUI.class.getName(), uiContext, null,OprtState.EDIT);
        			   TransactionStateEnum state = ((BaseTransactionInfo)ui.getEditData()).getBizState();
        			   FDCClientUtils.checkBillInWorkflow(ui, ui.getEditData().getId().toString());
        				
        			   if(state==null||!(TransactionStateEnum.PURAPPLE.equals(state)||TransactionStateEnum.PURSAVED.equals(state))){
        					MsgBox.showWarning("单据不是保存或者提交状态，不能进行提交操作！");
        					SysUtil.abort();
        			   }
	       			   ui.verifyInputForSubmint();
	       			   ui.runSubmit();
	       			   ui.destroyWindow();
        		   }
        	   }else if(IAMSIGN.equals(whoAmI())){
        		   for(int i = 0; i < id.size(); i++){
        			   UIContext uiContext = new UIContext(this);
        			   uiContext.put("ID", id.get(i).toString());
        			   SignManageEditUI ui=(SignManageEditUI) UIFactoryHelper.initUIObject(SignManageEditUI.class.getName(), uiContext, null,OprtState.EDIT);
        			   TransactionStateEnum state = ((BaseTransactionInfo)ui.getEditData()).getBizState();
        			   FDCClientUtils.checkBillInWorkflow(ui, ui.getEditData().getId().toString());
        				
        			   if(state==null||!(TransactionStateEnum.SIGNAPPLE.equals(state)||TransactionStateEnum.SIGNSAVED.equals(state))){
        					MsgBox.showWarning("单据不是保存或者提交状态，不能进行提交操作！");
        					SysUtil.abort();
        			   }
	       			   ui.verifyInputForSubmint();
	       			   ui.runSubmit();
	       			   ui.destroyWindow();
        		   }
        	   }
               return new Boolean(true);
           }
           public void afterExec(Object result)
               throws Exception
           {
        	   FDCMsgBox.showWarning("操作成功！");
           }
       }
);
       dialog.show();
	   this.refresh(null);
   }
   public void actionImport_actionPerformed(ActionEvent e) throws Exception {
	   String strSolutionName = "";
	   if(IAMPREPURCHASE.equals(whoAmI())){
		   strSolutionName = "eas.fdc.sellhouse.prePurchaseManage";
	   }else if(IAMPURCHASE.equals(whoAmI())){
		   strSolutionName = "eas.fdc.sellhouse.purchaseManage";
	   }else if(IAMSIGN.equals(whoAmI())){
		   strSolutionName = "eas.fdc.sellhouse.signManage";
	   }
       DatataskCaller task = new DatataskCaller();
       task.setParentComponent(this);
       DatataskParameter param = new DatataskParameter();
       String solutionName = strSolutionName;
       param.solutionName = solutionName;
       ArrayList paramList = new ArrayList();
       paramList.add(param);
       task.invoke(paramList, DatataskMode.UPDATE, true);
       this.refresh(null);
   }
   protected CommonQueryDialog initCommonQueryDialog() {
		CommonQueryDialog commonQueryDialog = super.initCommonQueryDialog();
		try
		{
			if(IAMSIGN.equals(whoAmI())){
				commonQueryDialog.addUserPanel(new SignManageFilterUI(whoAmI()));
				commonQueryDialog.setHeight(400);
			}else{
				commonQueryDialog.addUserPanel(new BaseTransactionFilterUI(whoAmI()));
				commonQueryDialog.setHeight(350);
			}
			commonQueryDialog.setWidth(600);
		}
		catch(Exception e)
		{
			super.handUIException(e);
		}
		return commonQueryDialog;
	}
   protected boolean initDefaultFilter() {
	   if(this.getUIContext().get("filter")==null){
		   return true;
	   }else{
		   return false;
	   }
   }
}