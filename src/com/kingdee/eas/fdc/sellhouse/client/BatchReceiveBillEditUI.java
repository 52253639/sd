/**
 * 批量收款
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IFDCReceivingBill;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastCollection;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastFactory;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fi.cas.client.CasReceivingBillListUI;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * @author laiquan_luo
 * 
 * 
 * 这个批量收款已改成只是针对按揭管理里的批量收款  
 * 只能针对售楼系统，只能收按揭或公积金
 */
public class BatchReceiveBillEditUI extends AbstractBatchReceiveBillEditUI
{
	private static final long serialVersionUID = -7407941483997524711L;
	private static final Logger logger = CoreUIObject.getLogger(BatchReceiveBillEditUI.class);
	private ItemAction[] hiddenAction = {this.actionUnAudit,this.actionSave,this.actionTraceDown,
			this.actionTraceUp,this.actionWorkFlowG,this.actionCreateFrom,this.actionAddLine,
			this.actionRemoveLine,this.actionInsertLine,this.actionViewDoProccess,this.actionRemove,
			this.actionCancel,this.actionCancelCancel,this.actionAddNew,this.actionAddNew,
			this.actionCopy,this.actionCopy,this.actionFirst,this.actionPre,this.actionNext,
			this.actionLast,this.actionVoucher,this.actionAuditResult,this.actionMultiapprove,
			this.actionNextPerson,this.actionAttachment,this.actionEdit};
	//设置认购单
	private KDBizPromptBox f7Purchase = new KDBizPromptBox();	

	private KDBizPromptBox f7MoneyName =  new KDBizPromptBox();
	
	private KDBizPromptBox f7Customer = new KDBizPromptBox();	
	
	
	private FDCReceivingBillCollection fdcRevBillColl = new FDCReceivingBillCollection();

	
	//当前被激活的行
	private int currentActiveRowIndex = 0;
	
	boolean existNumberRule = CommerceHelper.isExistNumberRule(new FDCReceivingBillInfo());
    
    /**
     * output class constructor
     */
    public BatchReceiveBillEditUI() throws Exception
    {
        super();
    }
    public void loadFields()
    {
    	super.loadFields();
    }
  
    /**
     * 隐藏按钮
     *
     */
    private void hideButton()   {
		if (getUIContext().get(UIContext.OWNER) instanceof CasReceivingBillListUI) {
			if (this.getOprtState().equals("EDIT")) {
				MsgBox.showInfo("出纳系统不能修改来自售楼系统的收款单!");
				this.abort();
			} else {
				this.actionEdit.setVisible(false);
			}
		}
		
		
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(false);
		

		this.actionRec.setVisible(true);
		this.actionRec.setEnabled(false);
		
    }
    private void initTable()
    {
    	this.tblRooms.checkParsed();
    	/*
    	 * 设定 table中一些列属性
    	 */

    	f7Purchase.setEditable(true);
    	f7Purchase.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.PurchaseQuery");
    	f7Purchase.setDisplayFormat("$number$");
    	f7Purchase.setCommitFormat("$number$"); 
    	f7Purchase.setEditFormat("$number$");
    	
    	//设置fuPurchase 的显示格式
		ObjectValueRender objectValueRender = new ObjectValueRender();
		objectValueRender.setFormat(new IDataFormat()
				{
			public String format(Object o)
			{
				if(o instanceof IObjectValue)
				{
					return ((IObjectValue)o).getString("number");
					
				}
				return null;
			}
		});
		this.tblRooms.getColumn("f7Purchase").setRenderer(objectValueRender);
    	
    	ICellEditor f7PurchaseEditor = new KDTDefaultCellEditor(f7Purchase);
    	this.tblRooms.getColumn("f7Purchase").setEditor(f7PurchaseEditor);

    	//对方科目
    	this.f7PayeeAccount.setEditable(true);
    	this.f7PayeeAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
    	this.f7PayeeAccount.setDisplayFormat("$number$$name$");
    	this.f7PayeeAccount.setCommitFormat("$number");
    	this.f7PayeeAccount.setEditFormat("$number");
    	

    	//设置款项名称
    	f7MoneyName.setEditable(true);
    	f7MoneyName.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
    	f7MoneyName.setDisplayFormat("$name$");
    	f7MoneyName.setCommitFormat("$number");
    	f7MoneyName.setEditFormat("$number");
    	
    	f7MoneyName.addDataChangeListener(new DataChangeListener()
    			{
					public void dataChanged(DataChangeEvent eventObj)
					{
						try
						{
							//之所以这样写，是因为当f7改变之后，单元格的内容还是原来的值
							IRow row = tblRooms.getRow(currentActiveRowIndex);
							if(row == null)
								return;
							row.getCell("f7MoneyName").setValue(eventObj.getNewValue());
							updateAmountByMoneyName(row);
						} catch (BOSException e)
						{
							handUIException(e);
						} catch (Exception e)
						{
							handUIException(e);
						}
					}
    		
    			});
    	
    	ICellEditor f7MoneyNameEditor = new KDTDefaultCellEditor(f7MoneyName);
    	this.tblRooms.getColumn("f7MoneyName").setEditor(f7MoneyNameEditor);
    	
    	
    	
//    	金额格式
    	KDFormattedTextField amountField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
    	amountField.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    	amountField.setSupportedEmpty(true);
    	amountField.setPrecision(2);
    	
    	//设置顾客
    	f7Customer.setEditable(true);
    	f7Customer.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7CustomerQuery");
    	f7Customer.setDisplayFormat("$name$");
    	f7Customer.setCommitFormat("$number$");
    	f7Customer.setEditFormat("$number$");
    	
    	ICellEditor f7CustomerEditor = new KDTDefaultCellEditor(f7Customer);
    	this.tblRooms.getColumn("f7Customer").setEditor(f7CustomerEditor);
    	
    
    	/*
    	 * 设置表格里面的 应收金额 列为数字型
    	 */
    	this.tblRooms.getColumn("appReceiveAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	this.tblRooms.getColumn("appReceiveAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	ICellEditor appReceiveAmountFieldEditor = new KDTDefaultCellEditor(amountField);
    	this.tblRooms.getColumn("appReceiveAmount").setEditor(appReceiveAmountFieldEditor);
    	
    	/*
    	 * 设置表格里面的 已收金额 列为数字型
    	 */
    	this.tblRooms.getColumn("actReceiveAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	this.tblRooms.getColumn("actReceiveAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	ICellEditor actReceiveAmountFieldEditor = new KDTDefaultCellEditor(amountField);
    	this.tblRooms.getColumn("actReceiveAmount").setEditor(actReceiveAmountFieldEditor);
    	
    	/*
    	 * 设置表格里面的 收款金额 列为数字型
    	 */
    	KDFormattedTextField receiveAmount = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
    	receiveAmount.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    	receiveAmount.setNegatived(false);
    	receiveAmount.setSupportedEmpty(true);
    	receiveAmount.setPrecision(2);
    	
    	//给收款金额栏添加事件
    	receiveAmount.addDataChangeListener(new DataChangeListener()
    			{

					public void dataChanged(DataChangeEvent eventObj)
					{
						tblRooms.getRow(currentActiveRowIndex).getCell("receiveAmount").setValue(eventObj.getNewValue());
						updateAmountTotal();
					}
    		
    			});
    	
    	
    	this.tblRooms.getColumn("receiveAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	this.tblRooms.getColumn("receiveAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	ICellEditor receiveAmountFieldEditor = new KDTDefaultCellEditor(receiveAmount);
    	this.tblRooms.getColumn("receiveAmount").setEditor(receiveAmountFieldEditor);
    	
    	
    	/*
    	 *  设置关联属性取值
    	 */
    	SelectorItemCollection f7MoneyNameSels = new SelectorItemCollection();
    	f7MoneyNameSels.add("*");
    	f7MoneyNameSels.add("revBillType.*");
    	f7MoneyNameSels.add("moneyType.*");
		this.f7MoneyName.setSelectorCollection(f7MoneyNameSels);
		
		
		
		
		SelectorItemCollection f7PurchaseSels = getPurchaseSelectors();
		this.f7Purchase.setSelectorCollection(f7PurchaseSels);
		
		/*
		 * 设置相关列的一些属性
		 */
		this.tblRooms.getColumn("appReceiveAmount").getStyleAttributes().setLocked(true);
		this.tblRooms.getColumn("actReceiveAmount").getStyleAttributes().setLocked(true);
		this.tblRooms.getColumn("building").getStyleAttributes().setLocked(true);
		this.tblRooms.getColumn("room").getStyleAttributes().setLocked(true);

		
		
		this.tblRooms.getColumn("receiveAmount").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblRooms.getColumn("number").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblRooms.getColumn("f7Purchase").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblRooms.getColumn("f7Purchase").getStyleAttributes().setLocked(true);
		this.tblRooms.getColumn("f7MoneyName").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblRooms.getColumn("f7MoneyName").getStyleAttributes().setLocked(true);
		this.tblRooms.getColumn("f7Customer").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);

    }
    
    /**
	 * 设置收款科目的过滤信息  界面上对应着对方科目
	 * 供onload事件中使用
	 * @author laiquan_luo
	 */
	private void setF7PayeeAccountFilterAsOnload()
	{
		CompanyOrgUnitInfo companyOrgUnitInfo = SysContext.getSysContext().getCurrentFIUnit();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// -----------------转6.0后修改,科目不按CU隔离,根据财务组织进行隔离
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", companyOrgUnitInfo.getId().toString()));
		if (companyOrgUnitInfo.getAccountTable() != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", companyOrgUnitInfo.getAccountTable().getId().toString()));
		}
		filter.getFilterItems().add(new FilterItemInfo("isGFreeze", Boolean.FALSE));
		view.setFilter(filter);
		
		AccountPromptBox opseelect = new AccountPromptBox(this, companyOrgUnitInfo,view.getFilter(), false, false);
		
		this.f7ReceiveSubject.setEntityViewInfo(view);
		this.f7ReceiveSubject.setSelector(opseelect);
		
		
		this.f7PayeeAccount.setEntityViewInfo(view);
		this.f7PayeeAccount.setSelector(opseelect);
		
		//自动带出对方科目
    	MoneyTypeEnum mTypeEnum = (MoneyTypeEnum)this.getUIContext().get("MoneyTypeEnum");
    	if(mTypeEnum!=null) {
    		CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit();
    		String kSql = "select accountView.*,isChanged where moneyDefine.moneyType='"+mTypeEnum.getValue()+"'";
    		if(company != null){
    			kSql += " and fullOrgUnit.id='"+ company.getId().toString() +"'";
    		}else{
    			kSql += " and fullOrgUnit.id='idnull'";
    		}
    		try{
	    		MoneySubjectContrastCollection monContractInfoColl = MoneySubjectContrastFactory.getRemoteInstance().getMoneySubjectContrastCollection(kSql);
	    		if(monContractInfoColl.size()>0) {
	    			MoneySubjectContrastInfo msInfo = monContractInfoColl.get(0);
	    			this.f7PayeeAccount.setValue(msInfo.getAccountView());
	    			if(!msInfo.isIsChanged()) this.f7PayeeAccount.setEnabled(false);
	    		}
    		}catch(Exception e){e.printStackTrace();}
    	
    	}
	}
    
    public void onLoad() throws Exception
    {
    	super.onLoad();	
    	this.tblRooms.checkParsed();
    	this.initTable();
    	FDCClientHelper.setActionVisible(this.hiddenAction,false);
    	this.hideButton();
	
		/*
		 * 设置一些F7的过滤条件
		 */
		CompanyOrgUnitInfo curCompany = SysContext.getSysContext().getCurrentFIUnit();
		//收款帐户过滤条件
		EntityViewInfo view = this.getAccountBankEvi(curCompany);
		this.f7ReceiveAccount.setEntityViewInfo(view);
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("account.*");
		this.f7ReceiveAccount.setSelectorCollection(sels);

		this.f7PayeeAccount.setRequired(true);
		
        //收款和对方科目过滤条件    
	    this.setF7PayeeAccountFilterAsOnload();
		
	    java.util.List roomIds = (java.util.List) getUIContext().get("roomIds");
		if (roomIds != null) {
			Set idSet = new HashSet();
			for (int i = 0, size = roomIds.size(); i < size; i++)
			{
				idSet.add(roomIds.get(i).toString());
			}
			
			if(idSet.size()>0)	{
				EntityViewInfo  roomView = new EntityViewInfo();
				roomView.getSelector().add(new SelectorItemInfo("*"));
				roomView.getSelector().add(new SelectorItemInfo("building.*"));
				roomView.getSelector().add(new SelectorItemInfo("building.sellProject.*"));
				
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id",idSet, CompareType.INCLUDE ));
				roomView.setFilter(filter);
				
				RoomCollection roomColl = RoomFactory.getRemoteInstance().getRoomCollection(roomView);
				this.addRoomByUI(roomColl);
			}			
		}else{
			Object treeNodeInfo = getUIContext().get("TreeNodeInfo");
			if(treeNodeInfo!=null) {
				if(treeNodeInfo instanceof BuildingInfo) {				
					RoomCollection roomColl = RoomFactory.getRemoteInstance().getRoomCollection(
							"select *,building.*,building.sellProject.* where building.id = '"+((CoreBaseInfo)treeNodeInfo).getId()+"' ");
					this.addRoomByUI(roomColl);
				}else if(treeNodeInfo instanceof BuildingUnitInfo){
					RoomCollection roomColl = RoomFactory.getRemoteInstance().getRoomCollection(
							"select *,building.*,building.sellProject.* where buildUnit.id = '"+((CoreBaseInfo)treeNodeInfo).getId()+"' ");
					this.addRoomByUI(roomColl);					
				}
			}	
		}
    	
		//设置输入框的基本属性
		this.f7ReceiveSubject.setRequired(true);
		
		SellProjectInfo sellProInfo = (SellProjectInfo)this.getUIContext().get("sellProject");
		if(sellProInfo!=null)
			this.prmtSellProject.setValue(sellProInfo);
		
		this.f7Currency.setEnabled(false);
		this.menuEdit.setVisible(false);
		this.actionVoucher.setVisible(false);
		this.btnVoucher.setVisible(true);
		this.btnVoucher.setEnabled(false);

    }
    
    /**
     * 让编辑界面上的所有相变为不可编辑项
     *
     */
    private void setButtonDisable()
    {
        this.f7ReceiveSubject.setEnabled(false);
        this.txtBalanceNum.setEditable(false);
        this.f7ReceiveAccount.setEnabled(false);
        this.txtReceiveBank.setEditable(false);
        this.f7BalanceType.setEnabled(false);
        this.txtSummary.setEditable(false);
        this.txtPayBank.setEditable(false);
        this.txtPayAccountNum.setEditable(false);
        this.wbAddRoom.setEnabled(false);
        this.wbDeleteRoom.setEnabled(false);
        this.dpBillDate.setEnabled(false);
        this.receiveAmountTotal.setEditable(false);  	
        
        for(int i=0;i<this.tblRooms.getColumnCount();i++)
        {
        	this.tblRooms.getColumn(i).getStyleAttributes().setLocked(true);
        }
        
    }
    
    
    /**
     * 根据f7Purchase的值来刷新f7MoneyName的值
     * @throws BOSException 
     *
     */
    protected  void upDateMoneyByPurchase(IRow row) throws BOSException
    {}

  
    /**
	 * 根据款项名称来给金额赋值
	 * 
	 * @throws BOSException
	 */
	protected void updateAmountByMoneyName(IRow row) throws BOSException
	{
		MoneyDefineInfo money = (MoneyDefineInfo) row.getCell("f7MoneyName").getValue();
		if (money != null)
		{
			this.setApAmountByMoneyInSHE(money,row);
			this.setActAmountByMoneyInSHE(money,row);
			BigDecimal apAmount = null;
			if (row.getCell("appReceiveAmount").getValue() == null)
			{
				apAmount = FDCHelper.ZERO;
			} else
			{
				apAmount = new BigDecimal(row.getCell("appReceiveAmount").getValue().toString());
			}
			BigDecimal actAmount = null;
			if (row.getCell("actReceiveAmount").getValue() == null)
			{
				actAmount = FDCHelper.ZERO;
			} else
			{
				actAmount = new BigDecimal(row.getCell("actReceiveAmount").getValue().toString());
			}
			BigDecimal temp = apAmount.subtract(actAmount);
			if(temp.compareTo(FDCHelper.ZERO) >= 0)
			{
				row.getCell("receiveAmount").setValue(temp);
			}
			else
			{
				row.getCell("receiveAmount").setValue(FDCHelper.ZERO);
			}
		} else
		{
			row.getCell("receiveAmount").setValue(null);
			row.getCell("actReceiveAmount").setValue(null);
		}
	}


	
    /**
	 * output storeFields method
	 */
    public void storeFields()
	{
		super.storeFields();
		
		FDCReceivingBillInfo rev = (FDCReceivingBillInfo) this.editData;
		fdcRevBillColl.clear();
		
		/*
		 * 根据每个房间来生成每个单据保存
		 */
		for(int i = 0; i < this.tblRooms.getRowCount(); i++)  {
			FDCReceivingBillInfo fdc = (FDCReceivingBillInfo) rev.clone();
			IRow row = this.tblRooms.getRow(i);

			fdc.setBizDate((Date) this.dpBillDate.getValue());

			// 注意编码
			fdc.setNumber(row.getCell("number").getValue() == null ? null : row.getCell("number").getValue().toString());

			fdc.setSellProject((SellProjectInfo)this.prmtSellProject.getValue());
			fdc.setRoom((RoomInfo)row.getCell("room").getUserObject());

			fdc.setPurchaseObj((PurchaseInfo)row.getCell("f7Purchase").getValue());

			CustomerInfo customer = (CustomerInfo) row.getCell("f7Customer").getValue();
			if (customer != null)			{
				fdc.setCustomer(customer);
			}
			
			BigDecimal receiveAmount = row.getCell("receiveAmount").getValue() == null ? FDCHelper.ZERO : (BigDecimal)row.getCell("receiveAmount").getValue();
			fdc.setAmount(receiveAmount);
			fdc.setDescription(this.txtSummary.getText());

	
			// 房地产收款单分录
			FDCReceivingBillEntryCollection receiveBillEntryCollection = new FDCReceivingBillEntryCollection();

			FDCReceivingBillEntryInfo receiveBillEntryInfo = new FDCReceivingBillEntryInfo();
			receiveBillEntryInfo.setRevListType(RevListTypeEnum.purchaseRev);
			
			receiveBillEntryInfo.setRevAmount(receiveAmount);
			receiveBillEntryInfo.setSettlementType((SettlementTypeInfo)this.f7BalanceType.getValue());
			
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell("f7MoneyName").getValue();
			receiveBillEntryInfo.setMoneyDefine(moneyName);
			
			receiveBillEntryInfo.setRevAccount((AccountViewInfo) this.f7ReceiveSubject.getValue());
			receiveBillEntryInfo.setOppAccount((AccountViewInfo)this.f7PayeeAccount.getValue());
			AccountBankInfo acountBankInfo = (AccountBankInfo)this.f7ReceiveAccount.getValue();
			if(acountBankInfo!=null) {
				receiveBillEntryInfo.setRevAccountBank(acountBankInfo);
				if(acountBankInfo.getBank()!=null){
					receiveBillEntryInfo.setBankNumber(acountBankInfo.getBank().getNumber());					
				}
			}
			receiveBillEntryInfo.setBankNumber((String)this.txtPayAccountNum.getText());
			receiveBillEntryInfo.setSettlementNumber(this.txtBalanceNum.getText());
			receiveBillEntryInfo.setRevListId((String)row.getCell("seq").getValue());
			
			receiveBillEntryCollection.add(receiveBillEntryInfo);

			fdc.getEntries().clear();
			fdc.getEntries().addCollection(receiveBillEntryCollection);
			
			fdc.setBillStatus(RevBillStatusEnum.SUBMIT);
			fdcRevBillColl.add(fdc);
		}

	}

 
	protected void attachListeners()	{
	}

	protected void detachListeners()	{
	}

	protected KDTextField getNumberCtrl()
	{
		return null;
	}
	protected void fetchInitData() throws Exception
	{
		
	}
	protected ICoreBase getBizInterface() throws Exception
	{
		return FDCReceivingBillFactory.getRemoteInstance();
	}
	
	protected IObjectValue createNewData()
	{
		FDCReceivingBillInfo fdcRevBill = new FDCReceivingBillInfo();
		try{			
			CompanyOrgUnitInfo currentCompany = SysContext.getSysContext().getCurrentFIUnit();
			CurrencyInfo baseCurrency = CurrencyFactory.getRemoteInstance()
					.getCurrencyInfo(new ObjectUuidPK(BOSUuid.read(currentCompany.getBaseCurrency().getId().toString())));
			this.f7Currency.setValue(baseCurrency);
			fdcRevBill.setCurrency(baseCurrency);
			fdcRevBill.setCreator(SysContext.getSysContext().getCurrentUserInfo());
			fdcRevBill.setCreateTime(FDCCommonServerHelper.getServerTimeStamp());
			fdcRevBill.setCompany(currentCompany);
			fdcRevBill.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
			
			fdcRevBill.setExchangeRate(FDCHelper.ONE);
			
			fdcRevBill.setRevBillType(RevBillTypeEnum.gathering);
			fdcRevBill.setRevBizType(RevBizTypeEnum.purchase);
			
			//默认收款日期为当前服务器日期
			fdcRevBill.setBizDate(FDCCommonServerHelper.getServerTimeStamp());
			
		} catch (Exception e)
		{
			super.handUIException(e);
		}
		
		return fdcRevBill;

	}

	/**
	 * 从其他UI界面上传过来的房间信息
	 */
	protected void addRoomByUI (RoomCollection roomColl) throws Exception
	{	
		for (int i = 0; i < roomColl.size(); i++) 
		{
			RoomInfo room = roomColl.get(i);			

			MoneyTypeEnum mTypeEnum = (MoneyTypeEnum)this.getUIContext().get("MoneyTypeEnum");
			String ksqlStr = "select head.name,head.number,moneyDefine.name,moneyDefine.revBillType.*,appAmount,actRevAmount,refundmentAmount " +
				"where head.room.id ='"+room.getId().toString()+"' and head.purchaseState = '"+PurchaseStateEnum.PURCHASEAUDIT_VALUE+"' ";
				ksqlStr += " and moneyDefine.moneyType = '"+(mTypeEnum==null?"":mTypeEnum.getValue())+"' " ;
				ksqlStr +=" order by head.name";
				
			PurchasePayListEntryCollection payListColl = PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryCollection(ksqlStr); 	
			this.addRowByRoom(room,payListColl);
		}
		
			this.updateAmountTotal();
	}
	
	
	
	private SelectorItemCollection getPurchaseSelectors()
	{
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("payType.*");
		sels.add("payType.loanLoanData.*");
		sels.add("dealCurrency.*");
		sels.add("customerInfo.*");
		sels.add("customerInfo.customer.sysCustomer.*");
		sels.add("sincerityPurchase.*");
		sels.add("purchaseCurrency.*");
		sels.add("salesman.name");
		sels.add("room.*");
		sels.add("payListEntry.*");
		sels.add("payListEntry.moneyDefine.*");
		sels.add("payListEntry.moneyDefine.revBillType.*");
		sels.add("payListEntry.currency.*");
		
		sels.add("elsePayListEntry.*");
		sels.add("elsePayListEntry.moneyDefine.*");
		sels.add("elsePayListEntry.moneyDefine.revBillType.*");
		sels.add("elsePayListEntry.currency.*");
		
		return sels;
	}
	/**
	 * 增加每一行房间的记录，这里的每一行不能叫做分录。
	 * @param 
	 * @throws BOSException 
	 * @throws UuidException 
	 * @throws EASBizException 
	 */
	private void addRowByRoom(RoomInfo room,PurchasePayListEntryCollection payListColl) throws BOSException, EASBizException, UuidException
	{
		Map purMap = new HashMap();		//认购单对应行的集合
		String purIdStr = "'nullnull'";
		for(int i=0;i<payListColl.size();i++) {
				PurchasePayListEntryInfo payEntryInfo = payListColl.get(i);
				BigDecimal appReceiveAmount = payEntryInfo.getAppAmount()==null?new BigDecimal(0):payEntryInfo.getAppAmount();
				BigDecimal actReceiveAmount = payEntryInfo.getActRevAmount()==null?new BigDecimal(0):payEntryInfo.getActRevAmount();
				if(appReceiveAmount.compareTo(actReceiveAmount)<0) continue;
				
				PurchaseInfo purchase = payEntryInfo.getHead();
				
				IRow row = this.tblRooms.addRow();
				row.setUserObject(room);			
				row.getCell("f7Purchase").setValue(purchase);
				row.getCell("building").setValue(room.getBuilding().getName());
				row.getCell("room").setValue(room.getNumber());
				row.getCell("room").setUserObject(room);
				
				row.getCell("f7MoneyName").setValue(payEntryInfo.getMoneyDefine());
				row.getCell("appReceiveAmount").setValue(appReceiveAmount);
				row.getCell("actReceiveAmount").setValue(actReceiveAmount);
				BigDecimal receiveAmount = (appReceiveAmount).subtract(actReceiveAmount);
				row.getCell("receiveAmount").setValue(receiveAmount);
				row.getCell("seq").setValue(payEntryInfo.getId().toString());
				
				if(!purMap.containsKey(purchase.getId().toString())) {
					Set rowSet = new HashSet();
					rowSet.add(row);
					purMap.put(purchase.getId().toString(), rowSet);
				}else{
					Set rowSet = (Set)purMap.get(purchase.getId().toString());
					rowSet.add(row);
					purMap.put(purchase.getId().toString(), rowSet);
				}
				
				purIdStr += ",'"+purchase.getId().toString()+"'";
				
				this.handleCodingRule(row);				
			}
			
			if(purMap.size()>0) {	//设置收款客户
				PurchaseCollection purColl = PurchaseFactory.getRemoteInstance().getPurchaseCollection(
					"select id,customerInfo.*,customerInfo.customer.*,customerInfo.customer.sysCustomer.* " +
					"where id in ("+purIdStr+") order by customerInfo.seq");
				for(int i=0;i<purColl.size();i++) {
					Set rowSet = (Set)purMap.get(purColl.get(i).getId().toString());
					CustomerInfo sysCustomer = null;
					if( purColl.get(i).getCustomerInfo().size()>0) 
						sysCustomer = purColl.get(i).getCustomerInfo().get(0).getCustomer().getSysCustomer();
					Iterator it =rowSet.iterator();
					while(it.hasNext())
					 ((IRow)it.next()).getCell("f7Customer").setValue(sysCustomer);
				}
			}
			
			
			this.updateAmountTotal();
	}
	/**
	 * 删除一行“分录”
	 */
	protected void wbDeleteRoom_actionPerformed(ActionEvent e) throws Exception
	{
		super.wbDeleteRoom_actionPerformed(e);
		int activeRowIndex = this.tblRooms.getSelectManager().getActiveRowIndex();
		this.tblRooms.removeRow(activeRowIndex);
		
		this.updateAmountTotal();
	}



	protected void tblRooms_tableClicked(KDTMouseEvent e) throws Exception
	{
		if(this.tblRooms.getRowCount()<1)
			return;
		int clickedRowIndex = e.getRowIndex();
		if(clickedRowIndex == -1)
			return;
		IRow row = this.tblRooms.getRow(clickedRowIndex);
		//this.doAsTableClickedInSHE(row);
		this.setF7SysCustomerFilterForSHE(row);

	}

	/**
	 * 售楼系统 设置客户的过滤条件
	 * 主要是取得特定认购单下的所有客户信息
	 *
	 */
	private void setF7SysCustomerFilterForSHE(IRow row)
	{
		if(row == null)
			return;
		RoomInfo room = (RoomInfo)row.getUserObject();
		PurchaseInfo purchaseInfo = (PurchaseInfo)row.getCell("f7Purchase").getValue();
		if (room == null)
		{
			return;
		}
		EntityViewInfo queryView = new EntityViewInfo();
		queryView.getSelector().add("customer.sysCustomer.*");
		FilterInfo queryFilter = new FilterInfo();
		//这里不明白为什么要加这个条件，下面直接有认购单的ID就可以了啊
		queryFilter.getFilterItems().add(
				new FilterItemInfo("head.room.id", room.getId().toString()));
		if (purchaseInfo != null)
		{
			queryFilter.getFilterItems().add(
					new FilterItemInfo("head.id", purchaseInfo.getId().toString()));
		}
		queryView.setFilter(queryFilter);
		PurchaseCustomerInfoCollection purchaseCustomerColl = null;
		try
		{
			purchaseCustomerColl = PurchaseCustomerInfoFactory.getRemoteInstance()
					.getPurchaseCustomerInfoCollection(queryView);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}

		Set set = new HashSet();
		for (int i = 0; i < purchaseCustomerColl.size(); i++)
		{
			PurchaseCustomerInfoInfo purchaseCustomerInfo = purchaseCustomerColl.get(i);
			FDCCustomerInfo fdcCustomerInfo = purchaseCustomerInfo.getCustomer();
			CustomerInfo sysCustomerInfo = fdcCustomerInfo.getSysCustomer();
			if (sysCustomerInfo == null)
			{
				logger.warn("认购的房地产客户未同步,请仔细检查. fdcCustomerId:"
						+ fdcCustomerInfo.getId().toString());
				continue;
			}
			set.add(sysCustomerInfo.getId().toString());
		}
		if (set.isEmpty())
		{
			this.f7Customer.setEntityViewInfo(null);
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id", set, CompareType.INCLUDE));
		this.f7Customer.setEntityViewInfo(view);
	}


	/**
	 * 设置应付金额
	 * @param money
	 * @param row
	 * @throws BOSException
	 */
	private void setApAmountByMoneyInSHE(MoneyDefineInfo moneyDefineInfo,IRow row) throws BOSException
	{
		PurchaseInfo purchase = (PurchaseInfo)row.getCell("f7Purchase").getValue();
		if (purchase == null)
		{
			return ;
		}
		PurchasePayListEntryCollection purchasePayListEntryColl = purchase.getPayListEntry();
		
		List elsePayList = new ArrayList();
		PurchaseElsePayListEntryCollection elsePayColl = purchase.getElsePayListEntry();
		if(elsePayColl != null)
		{
			for(int i = 0; i < elsePayColl.size(); i ++ )
			{
				PurchaseElsePayListEntryInfo info = elsePayColl.get(i);
				if(info.getMoneyDefine() != null && info.getMoneyDefine().getId() != null)
				{
					elsePayList.add(info.getMoneyDefine().getId().toString());
				}
			}
		}
		
		
		
		
		if (moneyDefineInfo.getMoneyType().equals(MoneyTypeEnum.Refundment))
		{
			row.getCell("appReceiveAmount").setValue(null);
		} else if (moneyDefineInfo.getMoneyType().equals(MoneyTypeEnum.PreconcertMoney))
		{
			BigDecimal prePurchaseAmount = purchase.getPrePurchaseAmount();
			row.getCell("appReceiveAmount").setValue(prePurchaseAmount);
		} else if (moneyDefineInfo.getMoneyType().equals(MoneyTypeEnum.CompensateAmount))
		{
			//房间的补差信息
			RoomAreaCompensateInfo roomAreaCompensateInfo = SHEHelper.getRoomAreaCompensation(purchase.getRoom());
			//必须是补差审核了的
			if (roomAreaCompensateInfo != null && (roomAreaCompensateInfo.getCompensateState().equals(RoomCompensateStateEnum.COMAUDITTED)
					|| roomAreaCompensateInfo.getCompensateState().equals(RoomCompensateStateEnum.COMRECEIVED)))
			{
				BigDecimal compensateAmount = roomAreaCompensateInfo.getCompensateAmount();
				if (compensateAmount == null)
				{
					compensateAmount = FDCHelper.ZERO;
				}
				row.getCell("appReceiveAmount").setValue(compensateAmount);
				if (compensateAmount.compareTo(FDCHelper.ZERO) > 0)
				{
					//this.txtReceiveAmount.setNegatived(false);
				} else
				{
					//this.txtReceiveAmount.setNegatived(true);
				}
			}
		} /*else if (moneyDefineInfo.getMoneyType().equals(MoneyTypeEnum.FitmentAmount))
		{
			if (purchase.isIsFitmentToContract())
			{
				row.getCell("appReceiveAmount").setValue(purchase.getFitmentAmount());
			} else
			{
				row.getCell("appReceiveAmount").setValue(FDCHelper.ZERO);
			}
		} else if (moneyDefineInfo.getMoneyType().equals(MoneyTypeEnum.CommissionCharge))
		{
			row.getCell("appReceiveAmount").setValue(null);
		} else if (moneyDefineInfo.getMoneyType().equals(MoneyTypeEnum.LateFee))
		{
			row.getCell("appReceiveAmount").setValue(null);
		} else if (moneyDefineInfo.getMoneyType().equals(MoneyTypeEnum.ElseAmount))
		{
			row.getCell("appReceiveAmount").setValue(null);
		}*/ 
//		如果是定义在非计划性款项里面的
		else if(elsePayList.contains(moneyDefineInfo.getId().toString()))
		{
			FilterInfo filter = new FilterInfo();
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("apAmount");
			view.getSelector().add("currency.*");
			
			filter.getFilterItems().add(new FilterItemInfo("head.id",purchase.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id",moneyDefineInfo.getId().toString()));
			
			PurchaseElsePayListEntryCollection payColl = PurchaseElsePayListEntryFactory.getRemoteInstance().getPurchaseElsePayListEntryCollection(view);
			
			if(payColl.size() < 1)
			{
				row.getCell("appReceiveAmount").setValue(null);
				return;
			}
			else if(payColl.size() > 1)
			{
				logger.error("认购单"+purchase.toString()+"所对应的非计划性款项定义为"+moneyDefineInfo.toString()+" 查找多条相对应的付款明细");
				throw new BOSException("系统逻辑错误，已记录日志！");
			}
			else
			{
				PurchaseElsePayListEntryInfo purchaseElsePayListEntryInfo = payColl.get(0);
				
				row.getCell("appReceiveAmount").setValue(purchaseElsePayListEntryInfo.getApAmount());
			}
		}
		else
		{
			Map moneyMap = new HashMap();
			for (int i = 0; i < purchasePayListEntryColl.size(); i++)
			{
				PurchasePayListEntryInfo purchasePayListEntryInfo = purchasePayListEntryColl.get(i);
				moneyMap.put(purchasePayListEntryInfo.getMoneyDefine().getId().toString(), purchasePayListEntryInfo);
			}
			if (moneyMap.containsKey(moneyDefineInfo.getId().toString()))
			{
				PurchasePayListEntryInfo purchasePayListEntryInfo = (PurchasePayListEntryInfo) moneyMap.get(moneyDefineInfo.getId().toString());
				row.getCell("appReceiveAmount").setValue(purchasePayListEntryInfo.getApAmount());
			} else
			{
				this.f7MoneyName.setValue(null);
				MsgBox.showInfo("不支持的收款类型!");
			}
			
			FilterInfo filter = new FilterInfo();
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("apAmount");
			view.getSelector().add("currency.*");
			
			filter.getFilterItems().add(new FilterItemInfo("head.id",purchase.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id",moneyDefineInfo.getId().toString()));
			
			PurchasePayListEntryCollection payColl = PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryCollection(view);
			
			if(payColl.size() < 1)
			{
				logger.error("认购单"+purchase.toString()+"所对应的款项定义为"+moneyDefineInfo.toString()+" 查不到找相对应的付款明细");
				row.getCell("appReceiveAmount").setValue(null);
			}
			else if(payColl.size() > 1)
			{
				logger.error("认购单"+purchase.toString()+"所对应款项定义为"+moneyDefineInfo.toString()+" 查找多条相对应的付款明细");
				throw new BOSException("系统逻辑错误，已记录日志！");
			}
			else
			{
				PurchasePayListEntryInfo purchasePayListEntryInfo = payColl.get(0);
				row.getCell("appReceiveAmount").setValue(purchasePayListEntryInfo.getApAmount());
			}
		}
	}
	
	/**
	 * 设置实付金额
	 * @param money
	 * @throws BOSException
	 */
	private void setActAmountByMoneyInSHE(MoneyDefineInfo moneyDefineInfo,IRow row) throws BOSException	{
		PurchaseInfo purchaseInfo = (PurchaseInfo)row.getCell("f7Purchase").getValue();
		
		if (purchaseInfo == null)	{
			return;
		}
		
		PurchasePayListEntryCollection payColl = PurchasePayListEntryFactory.getRemoteInstance()
				.getPurchasePayListEntryCollection("select actPayAmount where head.id='"+purchaseInfo.getId()+"' and moneyDefine.id='"+moneyDefineInfo.getId()+"'  ");
		
		if(payColl.size() > 1)	{
			logger.error("认购单"+purchaseInfo.toString()+"所对应的款项定义为"+moneyDefineInfo.toString()+" 查找多条相对应的付款明细");
			throw new BOSException("系统逻辑错误，已记录日志！");
		}else if(payColl.size()==1)	{
			PurchasePayListEntryInfo purchasePayListEntryInfo = payColl.get(0);
			row.getCell("actReceiveAmount").setValue(purchasePayListEntryInfo.getActPayAmount());
			return;
		}
		
		//非计划性款项分录里面		
		List elsePayList = new ArrayList();
		PurchaseElsePayListEntryCollection elsePayColl = purchaseInfo.getElsePayListEntry();
		if(elsePayColl != null)	{
			for(int i = 0; i < elsePayColl.size(); i ++ )		{
				PurchaseElsePayListEntryInfo info = elsePayColl.get(i);
				if(info.getMoneyDefine() != null && info.getMoneyDefine().getId() != null)	{
					elsePayList.add(info.getMoneyDefine().getId().toString());
				}
			}
		}
		
		if(elsePayList.contains(moneyDefineInfo.getId().toString()))	{
			PurchaseElsePayListEntryCollection elsePayListColl = PurchaseElsePayListEntryFactory.getRemoteInstance()
					.getPurchaseElsePayListEntryCollection("select actPayAmount,currency.* where head.id='"+purchaseInfo.getId()+"' and moneyDefine.id='"+moneyDefineInfo.getId()+"' ");
			if(elsePayListColl.size() > 1)		{
				logger.error("认购单"+purchaseInfo.toString()+"所对应的非计划性款项定义为"+moneyDefineInfo.toString()+" 查找多条相对应的付款明细");
				throw new BOSException("系统逻辑错误，已记录日志！");
			}else if(elsePayListColl.size() == 1)	{
				PurchaseElsePayListEntryInfo purchaseElsePayListEntryInfo = elsePayListColl.get(0);
				row.getCell("actReceiveAmount").setValue(purchaseElsePayListEntryInfo.getActPayAmount());
			}
		}
	}
	
	protected void tblRooms_activeCellChanged(KDTActiveCellEvent e) throws Exception
	{
		super.tblRooms_activeCellChanged(e);
		//设置激活行全局变量
		currentActiveRowIndex = e.getRowIndex();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
	{
		verifySubmit();
		
		this.storeFields();

		this.setOprtState("EDIT");

		for(int i=0;i<fdcRevBillColl.size();i++)	{
			String idStr = FDCReceivingBillFactory.getRemoteInstance().submitRev(fdcRevBillColl.get(i),"com.kingdee.eas.fdc.sellhouse.app.SheRevHandle");
			fdcRevBillColl.get(i).setId(BOSUuid.read(idStr));
		}
		showSubmitSuccess();
		this.actionSave.setEnabled(false);
		
		//this.actionSubmit.setVisible(false);
		this.actionSubmit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
		
		
		this.actionAttachment.setVisible(false);
		
		this.setButtonDisable();
		//this.onLoad();
		//	this.storeFields();
		//this.initOldData(this.editData);
		setCellAfterSubmit();
	}
	
	private void setCellAfterSubmit(){
		for(int i=0; i<tblRooms.getRowCount(); i++){
			BigDecimal receiveAmount = FDCHelper.ZERO;
			BigDecimal actReceiveAmount = FDCHelper.ZERO;
			if(tblRooms.getRow(i).getCell("receiveAmount").getValue()!=null){
				receiveAmount = (BigDecimal)tblRooms.getRow(i).getCell("receiveAmount").getValue();
			}
			if(tblRooms.getRow(i).getCell("receiveAmount").getValue()!=null){
				actReceiveAmount = (BigDecimal)tblRooms.getRow(i).getCell("actReceiveAmount").getValue();
			}
			tblRooms.getRow(i).getCell("receiveAmount").setValue(new BigDecimal(0));
			tblRooms.getRow(i).getCell("actReceiveAmount").setValue(receiveAmount.add(actReceiveAmount));
		}
	}
	
	public SelectorItemCollection getSelectors()
	{
		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
		sels.add("creator.*");
		sels.add("auditor.*");
		sels.add("currency.*");

		sels.add("oppAccount.*");
		sels.add("project.*");
		sels.add("payeeBank.*");
		sels.add("payeeAccountBank.*");
		sels.add("payeeAccount.*");
		sels.add("settlementType.*");
		sels.add("fdcReceiveBill.*");
		sels.add("fdcReceiveBill.cheque.*");
		sels.add("fdcReceiveBill.moneyDefine.*");
		sels.add("fdcReceiveBill.moneyDefine.revBillType.*");
		sels.add("fdcReceiveBill.room.*");
		sels.add("fdcReceiveBill.room.sellOrder.*");
		sels.add("fdcReceiveBill.sellProject.*");
		sels.add("fdcReceiveBill.sellProject.project.*");
		
		sels.add("fdcReceiveBill.entry.*");
		sels.add("fdcReceiveBill.entry.settlementType.*");
		sels.add("fdcReceiveBill.entry.account.*");
		sels.add("fdcReceiveBill.customerEntrys.customer.*");
		sels.add("fdcReceiveBill.tenancyContract.*");
		// sels.add("fdcReceiveBill.purchase.*");
		// sels.add("fdcReceiveBill.purchase.payType.loanLoanData.*");
		// sels.add("fdcReceiveBill.purchase.salesman.*");
		// sels.add("fdcReceiveBill.purchase.payListEntry.*");
		// sels.add("fdcReceiveBill.purchase.payListEntry.moneyDefine.*");
		// sels.add("fdcReceiveBill.purchase.payListEntry.currency.*");
		return sels;
	}
	
	protected void setNumberTextEnabled()
	{

		if (getNumberCtrl() != null)
		{
			CostCenterOrgUnitInfo currentCostUnit = SysContext.getSysContext()
					.getCurrentCostUnit();

			if (currentCostUnit != null)
			{
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(
						this.editData, currentCostUnit.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
			}
		}
	}

	/**
	 * 重写编码规则，不做任何操作
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException,
			EASBizException
	{
	}
	
	
	public void setNumberEnabled(IRow row)
	{
		if (editData == null)
		{
			return;
		}
		// 是否自动编码
		boolean isAutoNumber = true;
		try
		{
			if (isRequireGenNewNumber())
			{
				FDCReceivingBillInfo rev = (FDCReceivingBillInfo) this.editData;
				row.getCell("number").setValue(this.getNumberFromCodeRule(rev));
				rev.setNumber(row.getCell("number").getValue().toString());
				initOldData(editData);
			}
			isAutoNumber = isAutoNumber();
		} catch (Exception e)
		{
			handUIException(e);
		}
		// 能不能编辑只看是否定义过编码规则
		//txtNumber.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
	
		//txtNumber.setEditable(!isAutoNumber);
		row.getCell("number").getStyleAttributes().setLocked(isAutoNumber);
	}
	
	/**
	 * 是否需要生成单据编号
	 * 
	 * @return
	 * @throws CodingRuleException
	 * @throws EASBizException
	 * @throws BOSException
	 *             2006-11-24 wangyb
	 */
	private boolean isRequireGenNewNumber() throws CodingRuleException,
			EASBizException, BOSException
	{
		
		
		
		if (!getOprtState().equals(STATUS_ADDNEW)
				 || !isAutoNumber()
				|| !isAddView())
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @return 要自动生成时返回(true)否则返回(false)
	 * @throws CodingRuleException
	 * @throws EASBizException
	 * @throws BOSException
	 *             2006-2-22 wangyb
	 */
	protected boolean isAutoNumber() throws CodingRuleException,
			EASBizException, BOSException
	{
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		CompanyOrgUnitInfo company = SysContext.getSysContext()
				.getCurrentFIUnit();
		if (iCodingRuleManager.isExist(editData, company.getId().toString()))
		{
			// 定义过编码规则则视为自动生成编码
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 描述：判断是否新增即显示
	 * 
	 * @return boolean
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws CodingRuleException
	 * @author:daij 创建时间：2005-8-22
	 *              <p>
	 */
	public boolean isAddView() throws CodingRuleException, EASBizException,
			BOSException
	{
		CompanyOrgUnitInfo company = SysContext.getSysContext()
				.getCurrentFIUnit();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		return iCodingRuleManager.isAddView(editData, company.getId()
				.toString());
	}
	
	public String getNumberFromCodeRule(IObjectValue billInfo)
			throws CodingRuleException, EASBizException, BOSException
	{
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		String sysNumber = null;
		String companyID = iCodingRuleManager.getCurrentAppOUID(billInfo);

		if (iCodingRuleManager.isExist(billInfo, companyID))
		{
			// 没有启用断号支持功能，此时获取了编码规则产生的编码
			sysNumber = iCodingRuleManager.getNumber(billInfo, companyID);
		}

		return sysNumber;
	}
	
	/**
	 * @param curCompany
	 * @return
	 */
	private EntityViewInfo getAccountBankEvi(CompanyOrgUnitInfo curCompany)
	{
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(
						new FilterItemInfo("company.id", curCompany.getId()
								.toString()));
		evi.setFilter(filter);
		return evi;
	}
	
	/**
	 * 
	 * @param companyInfo
	 * @return
	 */
	public EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo)
	{
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		//-----------------转6.0后修改,科目不按CU隔离,根据财务组织进行隔离
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", companyInfo.getId().toString()));
//		filter.getFilterItems().add(
//				new FilterItemInfo("CU.id", companyInfo.getCU().getId()
//						.toString()));
		//------------------------
		

		if (companyInfo.getAccountTable() != null)
			filter.getFilterItems().add(
					new FilterItemInfo("accountTableID.id", companyInfo
							.getAccountTable().getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("isGFreeze", Boolean.FALSE));
		filter.getFilterItems().add(
				new FilterItemInfo("isCFreeze", Boolean.FALSE));
		filter.getFilterItems().add(
				new FilterItemInfo("isLeaf", FMConstants.TRUE));

		evi.setFilter(filter);
		return evi;
	}
	
	/**
	 * 
	 * @param cuId
	 * @param accountTable
	 * @return
	 */
	public EntityViewInfo getCustomAcctEvi(String comId, String accountTable)
	{

		FilterInfo filter = new FilterInfo();
//		-----------------转6.0后修改,科目不按CU隔离,根据财务组织进行隔离
//		filter.getFilterItems().add(new FilterItemInfo("CU.id", cuId));		
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", comId));
		//---------------
		
		filter.getFilterItems().add(
				new FilterItemInfo("accountTableID.id", accountTable));
		filter.getFilterItems().add(
				new FilterItemInfo("isGFreeze", FMConstants.FALSE));
		filter.getFilterItems().add(
				new FilterItemInfo("isCFreeze", FMConstants.FALSE));
		filter.getFilterItems().add(
				new FilterItemInfo("isLeaf", FMConstants.TRUE));

		EntityViewInfo customEvi = new EntityViewInfo();
		customEvi.setFilter(filter);
		return customEvi;
	}
	
	/**
	 * 校验
	 * @throws BOSException
	 */
	private void verifySubmit() throws BOSException
	{	
		this.verifyRequiredItemInHead();
		for(int i = 0;i < this.tblRooms.getRowCount(); i++)
		{
			IRow row = this.tblRooms.getRow(i);
			RoomInfo room = (RoomInfo)row.getUserObject();
			
			this.verifyRequiredItemInEntry(row,room);
		    this.verifyBizInEntry(row,room);	
		    this.verifyMoney(row,room);
	  }
	}
	
	/**
	 * 校验分录上的业务
	 * @param row
	 * @param room
	 */
	private void verifyBizInEntry(IRow row,RoomInfo room)
	{
//		 变更中的认购单不允许收款
		if (row.getCell("f7Purchase").getValue() instanceof PurchaseInfo)
		{
			if (PurchaseStateEnum.PurchaseChange.equals(((PurchaseInfo) row.getCell("f7Purchase").getValue()).getPurchaseState()))
			{
				MsgBox.showInfo("房间 "+room.getNumber()+" 所选择的认购单在变更流程中,不能收款!");
				this.abort();
			}
		}
	}
	/**
	 * 校验金额
	 * @param row
	 * @param room
	 */
	private void verifyMoney(IRow row,RoomInfo room) throws BOSException
	{
		BigDecimal receiveAmount = FDCHelper.ZERO;
		BigDecimal appAmount = FDCHelper.ZERO;
		BigDecimal actAmount = FDCHelper.ZERO;
			
		if(row.getCell("receiveAmount").getValue()!=null)
			receiveAmount = new BigDecimal(row.getCell("receiveAmount").getValue().toString());
		if(row.getCell("appReceiveAmount").getValue()!=null)
			appAmount = new BigDecimal(row.getCell("appReceiveAmount").getValue().toString());
		if(row.getCell("actReceiveAmount").getValue()!=null)
			actAmount = new BigDecimal(row.getCell("actReceiveAmount").getValue().toString());
		
		MoneyDefineInfo moneyName = (MoneyDefineInfo)row.getCell("f7MoneyName").getValue();
		
	
		if (moneyName.getMoneyType().equals(MoneyTypeEnum.CompensateAmount))
		{
			if (appAmount.compareTo(FDCHelper.ZERO) < 0)
			{
				if (actAmount.compareTo(FDCHelper.ZERO) > 0)
				{
					MsgBox.showInfo("房间 "+room.getNumber()+" 应补差款为负数,收款金额也必须为负数!");
					this.abort();
				}
				actAmount = actAmount.add(receiveAmount);
				if (actAmount.compareTo(appAmount) < 0)
				{
					MsgBox.showInfo("房间 "+room.getNumber()+" 累计退补差款大于应退补差款!");
					this.abort();
				}
			} 
			else
			{
				if (actAmount.compareTo(appAmount) > 0)
				{
					MsgBox.showInfo("房间 "+room.getNumber()+" 累计收补差款大于应收补差款!");
					this.abort();
				}
			}
		}
		else if (moneyName.getMoneyType().equals(MoneyTypeEnum.PreconcertMoney))
		{
			// 预收不能大于当前认购的定金标准
		//	this.verifyPreReceiveAmountAndEarnestAmount(row);
		}
		else if(moneyName.getMoneyType().equals(MoneyTypeEnum.DepositAmount))
		{
			//押金不用校验
		}
		else
		{
			actAmount = actAmount.add(receiveAmount);
			if (actAmount.compareTo(appAmount) > 0)
			{
				MsgBox.showInfo("房间 "+room.getNumber()+" 累计收款额大于应收标准!");
				this.abort();
			}
		}	
	}
	
	
	
	/**
	 * 校验单据体上必录项
	 *
	 */
	private void verifyRequiredItemInHead()
	{
		if (this.dpBillDate.getValue() == null)
		{
			MsgBox.showInfo("单据日期不能为空！");
			this.abort();
		}
		
		if(this.tblRooms.getRowCount()<1)
		{
			MsgBox.showInfo("请选择房间！");
			this.abort();
		}
		
		if(this.f7ReceiveSubject.getValue() == null)
		{
			MsgBox.showInfo("收款科目不能为空！");
			this.abort();
		}
		if(this.f7BalanceType.getValue() == null)
		{
			MsgBox.showInfo("结算方式不能为空！	");
			this.abort();
		}
		if(this.f7PayeeAccount.getValue() == null)
		{
			MsgBox.showInfo("对方科目不能为空！");
			this.abort();
		}		
	}

	/**
	 * 校验必填项
	 * @param row
	 */
	private void verifyRequiredItemInEntry(IRow row,RoomInfo room)
	{
		if (!row.getCell("number").getStyleAttributes().isLocked())
		{
			if (row.getCell("number").getValue() == null
					|| row.getCell("number").getValue().toString().trim()
							.length() < 1)
			{
				MsgBox.showInfo("请输入单据编码！");
				this.abort();
			}
		}
		BigDecimal actReceiveAmount = (BigDecimal)row.getCell("actReceiveAmount").getValue();
		if(actReceiveAmount==null) actReceiveAmount = new BigDecimal(0);
		BigDecimal appReceiveAmount = (BigDecimal)row.getCell("appReceiveAmount").getValue();
		if(appReceiveAmount==null) appReceiveAmount = new BigDecimal(0);
		if(actReceiveAmount.equals(appReceiveAmount)){
			MsgBox.showInfo("房间 "+room.getNumber()+" 的金额已收齐！");
			this.abort();
		}
		BigDecimal receiveAmount = row.getCell("receiveAmount").getValue() == null? null:new BigDecimal(row.getCell("receiveAmount").getValue().toString()); 
		if (receiveAmount == null|| receiveAmount.compareTo(FDCHelper.ZERO) == 0)
		{
			MsgBox.showInfo("房间 "+room.getNumber()+" 收款金额不能为空！");
			this.abort();
		}
		if(receiveAmount.compareTo(appReceiveAmount.subtract(actReceiveAmount))>0) {
			MsgBox.showInfo("本次收款金额+已收金额不能超过应收金额！");
			this.abort();
		}
		
		if(row.getCell("f7Customer").getValue() == null)
		{
			MsgBox.showInfo("房间 "+room.getNumber()+" 客户不能为空！");
			this.abort();
		}
		

		if (row.getCell("f7Purchase").getValue() == null)
		{
			MsgBox.showInfo("房间 " + room.getNumber() + " 认购单不能为空！");
			this.abort();
		}

		
		if(row.getCell("f7MoneyName").getValue() == null)
		{
			MsgBox.showInfo("房间 "+ room.getNumber()+" 款项名称不能为空！");
			this.abort();
		}
		
	}
	
	/**
	 * 重载  处理编码规则
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule(IRow row) throws BOSException,
			CodingRuleException, EASBizException
	{
		String number = null;
		if(existNumberRule) {
			number = CommerceHelper.getNumberByRule(new FDCReceivingBillInfo());
			row.getCell("number").setValue(number);
			row.getCell("number").getStyleAttributes().setLocked(true);
		}		
		
	}
	
	/**
	 * 重写
	 */
	protected void prepareNumber(IObjectValue caller, String number)
	{
		//super.prepareNumber(caller, number);
	}
	
	/**
	 * 重写控制按钮方法
	 */
	 protected void setAuditButtonStatus(String oprtType)
	{
		 this.actionAudit.setVisible(true);
		 this.actionAudit.setEnabled(true);
	}
	 
	protected void checkBeforeEditOrRemove(String warning)
	{
		FDCReceivingBillInfo rev = (FDCReceivingBillInfo) this.editData;
		RevBillStatusEnum state = rev.getBillStatus();
		if (state != null && (state == RevBillStatusEnum.AUDITING || state == RevBillStatusEnum.AUDITED)) {
			MsgBox.showWarning(this, "已审核,不能修改!");
			SysUtil.abort();
		}
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception
	{
		for (int i = 0; i < fdcRevBillColl.size(); i++)
		{
			//将状态设置为 审批 状态
			fdcRevBillColl.get(i).setBillStatus(RevBillStatusEnum.AUDITED);
			((IFDCReceivingBill)getBizInterface()).audit(fdcRevBillColl.get(i).getId());
		}
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
		
		this.actionAudit.setEnabled(false);
		this.actionRec.setEnabled(true);
	//	this.onLoad();
	}
	
	/**
	 * 统计收款
	 *
	 */
	private void updateAmountTotal()
	{
		if(this.tblRooms.getRowCount()<1)
			return;
		BigDecimal receiveAmount = FDCHelper.ZERO;
		
		for(int i=0;i<this.tblRooms.getRowCount();i++)
		{
			IRow row = this.tblRooms.getRow(i);
			receiveAmount = receiveAmount.add(row.getCell("receiveAmount").getValue()==null? FDCHelper.ZERO:new BigDecimal(row.getCell("receiveAmount").getValue().toString()));
		}
		this.receiveAmountTotal.setValue(receiveAmount);
	}
	
	/**
	 * 收款处理
	 * @param e
	 * @throws Exception
	 */
	public void actionRec_actionPerformed(ActionEvent e) throws Exception
	{
		ArrayList list = new ArrayList(); 
		for (int i = 0; i < fdcRevBillColl.size(); i++)
		{
			if (!RevBillStatusEnum.AUDITED.equals(fdcRevBillColl.get(i).getBillStatus()))
			{
				MsgBox.showInfo("未审批不能收款!");
				return;
			}
			//设置收款状态
			fdcRevBillColl.get(i).setBillStatus(RevBillStatusEnum.RECED);
			list.add(fdcRevBillColl.get(i).getId().toString());
		}
		((IFDCReceivingBill) getBizInterface()).receive(list);
		
		this.actionAudit.setEnabled(false);
		this.actionRec.setEnabled(false);
		this.btnVoucher.setEnabled(true);
		
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
		
	}
	
	/**
	 * 生成凭证
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
	{
		
		for (int i = 0; i < fdcRevBillColl.size(); i++)
		{
			if (!RevBillStatusEnum.RECED.equals(fdcRevBillColl.get(i).getBillStatus()))
			{
				MsgBox.showInfo("未收款的单据不能生成凭证!");
				return;
			}
		}
		this.btnVoucher.setEnabled(false);
		super.actionVoucher_actionPerformed(e);
		//this.onLoad();
		
	}
	
	/**
	 * 重写生成凭证 取得集合的方法
	 */
	protected CoreBillBaseCollection getBillCollection() throws Exception
    {
		//缓存中的分录无id值，因而此处要重新查询一次
		String idStrs = "'nullnull'";
        for(int i=0;i<fdcRevBillColl.size();i++)      {
        	idStrs += ",'"+ fdcRevBillColl.get(i).getId() + "'";
        }
        
        CoreBillBaseCollection sourceBillCollection = new CoreBillBaseCollection();
        FDCReceivingBillCollection fdcBillColl = FDCReceivingBillFactory.getRemoteInstance()
        			.getFDCReceivingBillCollection("select *,entries.* where id in ("+idStrs+")");
        for(int i=0;i<fdcBillColl.size();i++)
        	sourceBillCollection.add(fdcBillColl.get(i));
        
         return sourceBillCollection;
    }
	protected KDTable getDetailTable()
	{
		return null;
	}
	
	protected void f7ReceiveAccount_dataChanged(DataChangeEvent e) throws Exception {
		AccountBankInfo revInfo = (AccountBankInfo)e.getNewValue();
		if(revInfo ==null)
			this.txtReceiveBank.setText("");
		else
			this.txtReceiveBank.setText(revInfo.getBankAccountNumber());
	}
	

	/**
	 * 是否新增显示
	 * 
	 * @param caller
	 * @param orgId
	 * @return
	 */
	protected   boolean isCodingRuleAddView(IObjectValue caller, String orgId) {
		ICodingRuleManager iCodingRuleManager;
		boolean isAddView = false;
		try {
			iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			if(SysContext.getSysContext().getCurrentFIUnit() != null){
				isAddView = iCodingRuleManager.isAddView(caller, SysContext
						.getSysContext().getCurrentFIUnit().getId().toString());	
			}
		} catch (Exception e) {
			ExceptionHandler.handle(e);
		}
		return isAddView;
	}	
	
	
}