/**
 * 收款单，选择付款方案界面
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.IBasicRender;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.assistant.SettlementTypeCollection;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.propertymgmt.DepositBillCollection;
import com.kingdee.eas.fdc.propertymgmt.DepositBillFactory;
import com.kingdee.eas.fdc.propertymgmt.DepositBillInfo;
import com.kingdee.eas.fdc.propertymgmt.DepositWithdrawBillCollection;
import com.kingdee.eas.fdc.propertymgmt.DepositWithdrawBillFactory;
import com.kingdee.eas.fdc.propertymgmt.DepositWithdrawBillInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.GatheringEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineReverseEntryCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineReverseEntryFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineSellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineSellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineSellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SettlementTypeEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SettlementTypeEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SettlementTypeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyPayListInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.util.client.MsgBox;


public class SelectPaymentListUI extends AbstractSelectPaymentListUI
{ 
    /**
	 * 
	 */
	private static final long serialVersionUID = -6419194361168984210L;
	private static final Logger logger = CoreUIObject.getLogger(SelectPaymentListUI.class);
	
	private MoneySysTypeEnum currentMoneySysType = null;
	
	private PurchaseInfo purchase = null;
	
	private SincerityPurchaseInfo sinPurchase = null;
	
	private CustomerInfo customer = null;
	
	private RoomInfo room = null;
	
	private TenAttachResourceEntryInfo tenAttach = null;
	
	private ReceiveBillTypeEnum recBillTypeEnum = null;
	
	private TenancyBillInfo tenancyBill = null;
	
	private int activeCounteractRow = 0;
	
	private int activeSheRow = 0;
	
	private int activeTenRow = 0;
	
	private int activeWuyeRow = 0;
	
	private KDCheckBox tenDebug = new KDCheckBox();
	
	private KDCheckBox sheDebug = new KDCheckBox();
	
	private KDCheckBox wuyeDebug = new KDCheckBox();
	
	private GatheringEnum gatheringEnum = null;
	
	//符合当前冲抵的 物业预收款 
	private MoneyDefineInfo wuyePreMoneyDefine = null;
	
	//用来存物业冲抵规则的对应关系
	private Map wuyePreMap = new HashMap();
	
	/**
	 * 物业红冲控制规则里面 款项ID
	 */
	private Set moneyIdSet = new HashSet();
    /**
     * output class constructor
     */
    public SelectPaymentListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception
    {
    	super.onLoad();
    	
    	this.purchase = (PurchaseInfo) this.getUIContext().get("purchase");
    	this.sinPurchase = (SincerityPurchaseInfo) this.getUIContext().get("sinPurchase");
    	this.currentMoneySysType = (MoneySysTypeEnum) this.getUIContext().get(ReceiveBillEidtUI.KEY_MONEYTSYSTYPE);
    	this.room = (RoomInfo) this.getUIContext().get(ReceiveBillEidtUI.Key_Room);
    	this.tenAttach = (TenAttachResourceEntryInfo) this.getUIContext().get(ReceiveBillEidtUI.KEY_TEN_ATTACH);
    	this.tenancyBill = (TenancyBillInfo)this.getUIContext().get(ReceiveBillEidtUI.KEY_TenancyBill);
    	this.recBillTypeEnum = (ReceiveBillTypeEnum) this.getUIContext().get(ReceiveBillEidtUI.KEY_BILLTYPE);
    	this.customer = (CustomerInfo)this.getUIContext().get(ReceiveBillEidtUI.Key_SysCustomer);
    	this.gatheringEnum = (GatheringEnum)this.getUIContext().get(ReceiveBillEidtUI.KEY_GATHERING);
    	
    	this.setPanelShow();
    	
    	this.initCounteractTable();
    	//this.fillCounteractTableByReceiveBillId(null);
    }
    
    public void onShow() throws Exception
    {
    	super.onShow();
   
    	SwingUtilities.getWindowAncestor(this).setSize(660,355);
    }

    protected void btnNo_actionPerformed(ActionEvent e) throws Exception
    {
    	this.getUIContext().put(ReceiveBillEidtUI.KEY_OPT,Boolean.FALSE);
    	this.destroyWindow();
    }
    /**
     * 押金单
     */
    private DepositBillCollection getDepositBillCollection(RoomInfo room,CustomerInfo customer) throws BOSException
    {
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	
    	SelectorItemCollection selColl = new SelectorItemCollection();
    	selColl.add("*");
    	selColl.add("depositSort.*");
    	selColl.add("depositSort.chargeItem.*");
    	
    	view.setSelector(selColl);
    	
    	filter.getFilterItems().add(new FilterItemInfo("room",room.getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("customer.sysCustomer",customer.getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
    	
		return DepositBillFactory.getRemoteInstance().getDepositBillCollection(view);		
    }
    
    private PPMGeneralARCollection getGeneralColl(RoomInfo room, CustomerInfo customer) throws BOSException
    {
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	
    	SelectorItemCollection selColl = new SelectorItemCollection();
    	selColl.add("*");
    	selColl.add("chargeItem.*");
    	
    	view.setSelector(selColl);
    	
    	filter.getFilterItems().add(new FilterItemInfo("room",room.getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("customer.sysCustomer",customer.getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
    	
		return PPMGeneralARFactory.getRemoteInstance().getPPMGeneralARCollection(view);
    }
    
    private PPMMeasureARCollection getMeasureColl(RoomInfo room,CustomerInfo customer) throws BOSException
    {
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	
    	SelectorItemCollection selColl = new SelectorItemCollection();
    	selColl.add("*");
    	selColl.add("chargeItem.*");
    	
    	view.setSelector(selColl);
    	
    	filter.getFilterItems().add(new FilterItemInfo("room",room.getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("customer.sysCustomer",customer.getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
    	
		return PPMMeasureARFactory.getRemoteInstance().getPPMMeasureARCollection(view);
    }
    
    private PPMTemporaryCollection getTemporaryColl(RoomInfo room,CustomerInfo customer) throws BOSException
    {
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	
    	SelectorItemCollection selColl = new SelectorItemCollection();
    	selColl.add("*");
    	selColl.add("chargeItem.*");
    	
    	view.setSelector(selColl);
    	
    	filter.getFilterItems().add(new FilterItemInfo("room",room.getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("customer.sysCustomer",customer.getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));

    	return PPMTemporaryFactory.getRemoteInstance().getPPMTemporaryCollection(view);
    }
    
    private void addPayListInWuYe(RoomInfo room,CustomerInfo customer) throws BOSException
    {
    	this.wuyeTable.checkParsed();
    	
    	//物业预收款
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	view.getSelector().add("moneyType.*");
    	view.getSelector().add("*");
    	
    	filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.PREMONEY_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.MANAGESYS_VALUE));
    	
    	MoneyDefineCollection moneyColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);

    	for(int i = 0; i < moneyColl.size(); i ++)
    	{
    		IRow row = this.wuyeTable.addRow();
    		
    		row.getCell("selectDebug").setValue(Boolean.FALSE);
    		row.getCell("moneyName").setValue(moneyColl.get(i));
    		row.getCell("moneyName").setUserObject(moneyColl.get(i));
    		
    		setSettlementTypeByMoneyDefine(moneyColl.get(i), row);
    	}
    	
//    	PPMGeneralARCollection generalColl = this.getGeneralColl(room, customer);
//    	PPMMeasureARCollection measureColl = this.getMeasureColl(room, customer);
//    	PPMTemporaryCollection temporaryColl = this.getTemporaryColl(room, customer);
//    	DepositBillCollection depositColl = this.getDepositBillCollection(room, customer);
    	
//    	for(int i = 0; i < generalColl.size(); i ++)
//    	{
//    		PPMGeneralARInfo info = generalColl.get(i);
//    		
//    		BigDecimal arAmout = info.getArAmout();
//    		if(arAmout != null)
//    			arAmout = arAmout.setScale(2, BigDecimal.ROUND_HALF_UP);
//    		else
//    			arAmout = FDCHelper.ZERO;
//    		
//    		BigDecimal payedAmount = info.getPayedAmount();
//    		if(payedAmount != null)
//    			payedAmount = payedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
//    		else
//    			payedAmount = FDCHelper.ZERO;
//    		
//    		BigDecimal derateAmount = info.getDerateAmount();
//    		if(derateAmount != null)
//    			derateAmount = derateAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
//    		else
//    			derateAmount = FDCHelper.ZERO;
//    		
//    		if(arAmout.subtract(derateAmount).compareTo(payedAmount) == 0)
//    			continue;
//    		
//    		IRow row = this.wuyeTable.addRow();
//    		
//    		row.setUserObject(info);
//    		
//    		row.getCell("selectDebug").setValue(Boolean.FALSE);
//    		row.getCell("moneyName").setValue(info.getChargeItem());
//    		row.getCell("moneyName").setUserObject(info.getChargeItem());
//    		
//			row.getCell("appAmount").setValue(arAmout);
//			
//    		row.getCell("appDate").setValue(info.getArDate());
//    		
//			row.getCell("actAmount").setValue(payedAmount);
//    		
//			row.getCell("derateAmount").setValue(derateAmount);
//    		row.getCell("id").setValue(info.getId().toString());
//    		
//    		setSettlementTypeByMoneyDefine(info.getChargeItem(), row);
//    	}
    	
//    	for(int i = 0; i < measureColl.size(); i ++)
//    	{
//    		PPMMeasureARInfo info = measureColl.get(i);
//    		
//    		BigDecimal arAmout = info.getArAmout();
//    		if(arAmout != null)
//    			arAmout = arAmout.setScale(2, BigDecimal.ROUND_HALF_UP);
//    		else
//    			arAmout = FDCHelper.ZERO;
//    		
//    		BigDecimal payedAmount = info.getPayedAmount();
//    		if(payedAmount != null)
//    			payedAmount = payedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
//    		else
//    			payedAmount = FDCHelper.ZERO;
//    		
//    		BigDecimal derateAmount = info.getDerateAmount();
//    		if(derateAmount != null)
//    			derateAmount = derateAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
//    		else
//    			derateAmount = FDCHelper.ZERO;
//    		
//    		if(arAmout.subtract(derateAmount).compareTo(payedAmount) == 0)
//    			continue;
//    		
//    		IRow row = this.wuyeTable.addRow();
//    		row.setUserObject(info);
//    		
//    		row.getCell("selectDebug").setValue(Boolean.FALSE);
//    		row.getCell("moneyName").setValue(info.getChargeItem());
//    		row.getCell("moneyName").setUserObject(info.getChargeItem());
//    		
//    		
//			row.getCell("appAmount").setValue(arAmout);
//    		row.getCell("appDate").setValue(info.getArDate());
//			row.getCell("actAmount").setValue(payedAmount);
//			row.getCell("derateAmount").setValue(derateAmount);
//    		row.getCell("id").setValue(info.getId().toString());
//    		
//    		setSettlementTypeByMoneyDefine(info.getChargeItem(), row);
//    	}
    	
//    	for(int i = 0; i < temporaryColl.size(); i ++)
//    	{
//    		PPMTemporaryInfo info = temporaryColl.get(i);
//    		
//
//    		BigDecimal arAmout = info.getArAmout();
//    		if(arAmout != null)
//    			arAmout = arAmout.setScale(2, BigDecimal.ROUND_HALF_UP);
//    		else
//    			arAmout = FDCHelper.ZERO;
//    		
//    		BigDecimal payedAmount = info.getPayedAmount();
//    		if(payedAmount != null)
//    			payedAmount = payedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
//    		else
//    			payedAmount = FDCHelper.ZERO;
//    		
//    		BigDecimal derateAmount = info.getDerateAmount();
//    		if(derateAmount != null)
//    			derateAmount = derateAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
//    		else
//    			derateAmount = FDCHelper.ZERO;
//    		
//    		if(arAmout.subtract(derateAmount).compareTo(payedAmount) == 0)
//    			continue;
//    		
//    		
//    		IRow row = this.wuyeTable.addRow();
//    		
//    		row.setUserObject(info);
//    		
//    		row.getCell("selectDebug").setValue(Boolean.FALSE);
//    		row.getCell("moneyName").setValue(info.getChargeItem());
//    		row.getCell("moneyName").setUserObject(info.getChargeItem());
//    		
//			row.getCell("appAmount").setValue(arAmout);
//			
//    		row.getCell("appDate").setValue(info.getArDate());
//    		
//    	
//			row.getCell("actAmount").setValue(payedAmount);
//			
//			row.getCell("derateAmount").setValue(derateAmount);
//    		row.getCell("id").setValue(info.getId().toString());
//    		
//    		setSettlementTypeByMoneyDefine(info.getChargeItem(), row);
//    	}
    	
//    	for(int i = 0; i < depositColl.size(); i ++)
//    	{
//    		DepositBillInfo info = depositColl.get(i);
//    		
//    		BigDecimal appAmount = info.getDepositAmount();
//    		if(appAmount == null)
//    			appAmount = FDCHelper.ZERO;
//    		else
//    			appAmount = appAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
//    		
//    		BigDecimal actAmount = info.getActGatheringAmo();
//    		if(actAmount == null)
//    			actAmount = FDCHelper.ZERO;
//    		else
//    			actAmount = actAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
//    		
//    		if(appAmount.compareTo(actAmount) == 0)
//    			continue;
//    		
//    		IRow row = this.wuyeTable.addRow();
//    		row.setUserObject(info);
//    		
//    		row.getCell("selectDebug").setValue(Boolean.FALSE);
//    		row.getCell("moneyName").setValue(info.getDepositSort().getChargeItem());
//    		row.getCell("moneyName").setUserObject(info.getDepositSort().getChargeItem());
//    		row.getCell("appAmount").setValue(appAmount);
//    		row.getCell("actAmount").setValue(actAmount);
//    		row.getCell("id").setValue(info.getId().toString());
//    		
//    		setSettlementTypeByMoneyDefine(info.getDepositSort().getChargeItem(), row);
//    	}
    }
    
    private void addPayListInTEN(TenancyBillInfo tenancy, RoomInfo room, TenAttachResourceEntryInfo tenAttach) throws BOSException
    {
    	this.tenTable.checkParsed();
    	
    	IObjectCollection payList = null;
    	if(room != null){
    		payList = this.getTenRoomPayEntryColl(tenancy, room, null);
    	}else{
    		payList = this.getTenAttachPayList(tenancy, tenAttach);
    	}
    	
    	for (int i = 0; i < payList.size(); i++)
		{
    		ITenancyPayListInfo payInfo = (ITenancyPayListInfo) payList.getObject(i);
    		
			IRow row = this.tenTable.addRow();
			row.setUserObject(payInfo);
			row.getCell("selectDebug").setValue(Boolean.FALSE);
			row.getCell("lease").setValue(new Integer(payInfo.getLeaseSeq()));
			row.getCell("moneyName").setValue(payInfo.getMoneyDefine());
			
			//根据款项，带出结算方式
			setSettlementTypeByMoneyDefine(payInfo.getMoneyDefine(), row);
			
			row.getCell("appDate").setValue(payInfo.getAppPayDate());
			
			BigDecimal appAmount = payInfo.getAppAmount();
			if(appAmount != null)
				appAmount = appAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			
			row.getCell("appAmount").setValue(appAmount);
			row.getCell("actDate").setValue(payInfo.getActPayDate());

			BigDecimal actAmount = payInfo.getActAmount();
			if(actAmount != null)
				actAmount = actAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			
			row.getCell("actAmount").setValue(actAmount);
			
			setPayOffRowLocked(appAmount, actAmount, row);
		}
    }
    
    private TenAttachResourcePayListEntryCollection getTenAttachPayList(TenancyBillInfo tenancy, TenAttachResourceEntryInfo tenAttach) throws BOSException{
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	
    	SorterItemCollection sorColl = new SorterItemCollection();
    	sorColl.add(new SorterItemInfo("seq"));
    	
    	view.setSorter(sorColl);
    	view.getSelector().add("*");
    	view.getSelector().add("moneyDefine.*");
    	
    	filter.getFilterItems().add(new FilterItemInfo("tenAttachRes.id",tenAttach.getId().toString()));
    	return TenAttachResourcePayListEntryFactory.getRemoteInstance().getTenAttachResourcePayListEntryCollection(view);
    }

	private PurchasePayListEntryCollection getPurchasePayListEntryColl() throws BOSException
    {
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		SorterItemCollection sorColl = new SorterItemCollection();
		sorColl.add(new SorterItemInfo("seq"));
		
		view.setSorter(sorColl);
		
		view.getSelector().add("*");
		view.getSelector().add("moneyDefine.*");
		view.getSelector().add("currency");
		filter.getFilterItems().add(new FilterItemInfo("head",purchase.getId().toString()));
		
		return PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryCollection(view);
	}
    
	/**
	 * 获得客户对象的某个款项的收款
	 * @param money
	 * @param customer
	 * @return
	 * @throws BOSException
	 */
	private BigDecimal getActAmountByCustomerInSHE(MoneyDefineInfo money,CustomerInfo customer) throws BOSException
	{

		if(money == null || customer == null)
			return FDCHelper.ZERO;
		
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("head");
		view.getSelector().add("head.id");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("customer",customer.getId().toString()));
		
		CustomerEntryCollection customerColl = CustomerEntryFactory.getRemoteInstance().getCustomerEntryCollection(view);
		
		Set fdcBillIdSet = new HashSet();
		
		for(int i = 0; i < customerColl.size(); i ++)
		{
			if(customerColl.get(i) != null && customerColl.get(i).getHead() != null && customerColl.get(i).getHead().getId() != null)
			{
				fdcBillIdSet.add(customerColl.get(i).getHead().getId().toString());
			}
		}
		
		

		//若付款明细里面都没有相应的款项
		view = new EntityViewInfo();
	    filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
		
		if(fdcBillIdSet != null && fdcBillIdSet.size() > 0)
		{
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.id", fdcBillIdSet,CompareType.INCLUDE));
		}
		else
		{
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.id",null));
		}
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.GATHERING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.TRANSFER_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine", money.getId().toString()));
		
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.gathering",GatheringEnum.CUSTOMERREV_VALUE));
		
		filter.setMaskString("#0 and #1 and (#2 or #3) and #4 and #5");
		
		FDCReceiveBillEntryCollection recBillEntryColl = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);

		BigDecimal sumAmount = FDCHelper.ZERO;
		for (int i = 0; i < recBillEntryColl.size(); i++)
		{
			FDCReceiveBillEntryInfo info = recBillEntryColl.get(i);
			BigDecimal amount = info.getAmount();
			if(amount == null)
			{
				amount = FDCHelper.ZERO;
			}
			sumAmount = sumAmount.add(amount);
		}
		if(sumAmount != null)
			sumAmount = sumAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		return sumAmount;
	
	}
	
	
    /**
	 * 获得售楼系统中某个特定款项，它的实收金额
	 */
	private BigDecimal getActAmountByMoneyInSHE(MoneyDefineInfo money,PurchaseInfo purchase) throws BOSException
	{
		if(money == null || purchase == null)
			return FDCHelper.ZERO;
		

		//若付款明细里面都没有相应的款项
		EntityViewInfo view = new EntityViewInfo();
	    FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.purchase.id", purchase.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.GATHERING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.TRANSFER_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine", money.getId().toString()));
		
		filter.setMaskString("#0 and #1 and (#2 or #3) and #4");
		
		FDCReceiveBillEntryCollection recBillEntryColl = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);

		BigDecimal sumAmount = FDCHelper.ZERO;
		for (int i = 0; i < recBillEntryColl.size(); i++)
		{
			FDCReceiveBillEntryInfo info = recBillEntryColl.get(i);
			BigDecimal amount = info.getAmount();
			if(amount == null)
			{
				amount = FDCHelper.ZERO;
			}
			sumAmount = sumAmount.add(amount);
		}
		if(sumAmount != null)
			sumAmount = sumAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		return sumAmount;
	}
    
    private void addPayListInSHE(PurchaseInfo purchase,RoomInfo room) throws BOSException
    {
    	this.sheTable.checkParsed();
    	PurchasePayListEntryCollection payListColl = this.getPurchasePayListEntryColl();
    	
    	//几个特定的款项
    	RoomSellStateEnum roomState = room.getSellState();
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
//		对预定金的判断
		if (!purchase.getState().equals(FDCBillStateEnum.AUDITTED))
		{
			boolean isHavePay = isHavePay(purchase);
			if (isHavePay && (!ReceiveBillTypeEnum.refundment.equals(this.recBillTypeEnum)))
			{
				return;
			}
			else 
			{
				view = new EntityViewInfo();
				view.getSelector().add("*");
				view.getSelector().add("revBillType.*");
				filter = new FilterInfo();
				view.setFilter(filter);
				filter.getFilterItems().add(new FilterItemInfo("moneyType",	MoneyTypeEnum.PRECONCERTMONEY_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SALEHOUSESYS_VALUE));
				
				MoneyDefineCollection moneyColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
				if (ReceiveBillTypeEnum.refundment.equals(this.recBillTypeEnum)
						&& FDCBillStateEnum.AUDITTED.equals(purchase.getState()))
				{
					
				}
				else
				{
					for (int i = 0; i < moneyColl.size(); i++)
					{
						IRow row = this.sheTable.addRow();

						row.getCell("selectDebug").setValue(Boolean.FALSE);
						
						BigDecimal appAmount = purchase.getPrePurchaseAmount();
						if(appAmount != null)
							appAmount = appAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						
						BigDecimal actAmount = this.getActAmountByMoneyInSHE(moneyColl.get(i), purchase);
						
						row.getCell("actAmount").setValue(actAmount);
						
						row.getCell("appAmount").setValue(appAmount);
						row.getCell("moneyName").setValue(moneyColl.get(i));
						
						//根据款项，带出结算方式
	        			setSettlementTypeByMoneyDefine(moneyColl.get(i), row);
						
//						setPayOffRowLocked(appAmount, actAmount, row);//预订金不存在收齐
					}
				}
				if(!ReceiveBillTypeEnum.refundment.equals(this.recBillTypeEnum))
				{
					return;
				}
			}
		} //以下是对计划款项的操作
    
    	if (roomState.equals(RoomSellStateEnum.Sign))
		{
			RoomAreaCompensateInfo roomAreaCompensationInfo = SHEHelper.getRoomAreaCompensation(room);
			
			BigDecimal compensateAmount = FDCHelper.ZERO;
			//如果是签约了，且办理补差的，则需要补差款
			if (roomAreaCompensationInfo != null
					&& roomAreaCompensationInfo.getCompensateState() != null
					&& roomAreaCompensationInfo.getCompensateState().equals(RoomCompensateStateEnum.COMAUDITTED))
			{
				filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.COMPENSATEAMOUNT_VALUE));
				
				compensateAmount = roomAreaCompensationInfo.getCompensateAmount();
			}
			else
			{
				filter.getFilterItems().add(new FilterItemInfo("moneyType","notexists"));
			}
			if (compensateAmount == null)
			{
				compensateAmount = FDCHelper.ZERO;
			}
			filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SalehouseSys));
			
			MoneyDefineCollection moneyColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
			
			for(int j = 0; j < moneyColl.size(); j ++)
			{
			IRow row = this.sheTable.addRow();
			
			row.getCell("moneyName").setValue(moneyColl.get(j));
			
			//根据款项，带出结算方式
			setSettlementTypeByMoneyDefine(moneyColl.get(j), row);
			
			row.getCell("selectDebug").setValue(Boolean.FALSE);
			
			if(compensateAmount != null)
				compensateAmount = compensateAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			
			row.getCell("appAmount").setValue(compensateAmount);
			
			BigDecimal actAmount = this.getActAmountByMoneyInSHE(moneyColl.get(j), purchase);
			
			row.getCell("actAmount").setValue(actAmount);
			//这里是补差的款项，只要不为0都应该显示且可选择
			if(compensateAmount.equals(FDCHelper.ZERO))
				setPayOffRowLocked(compensateAmount, actAmount, row);
			}
		}
    	
    	for(int i = 0; i < payListColl.size(); i ++)
		{
			PurchasePayListEntryInfo payInfo = payListColl.get(i);
			IRow row = this.sheTable.addRow();
				
			row.setUserObject(payInfo);
			row.getCell("selectDebug").setValue(Boolean.FALSE);
			row.getCell("moneyName").setValue(payInfo.getMoneyDefine());
			//根据款项，带出结算方式
			setSettlementTypeByMoneyDefine(payInfo.getMoneyDefine(), row);
			row.getCell("appDate").setValue(payInfo.getAppDate());
			
			BigDecimal appAmount = payInfo.getApAmount();
			if(appAmount != null)
				appAmount = appAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			row.getCell("appAmount").setValue(appAmount);
			row.getCell("actDate").setValue(payInfo.getActRevDate());
			
			BigDecimal actPayAmount = payInfo.getActPayAmount();
			if(actPayAmount != null)
				actPayAmount = actPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			
			row.getCell("actAmount").setValue(actPayAmount);
			
			setPayOffRowLocked(appAmount, actPayAmount, row);
		}
    }
    
    private void addOtherPayListInSHE(PurchaseInfo info) throws BOSException
    {
    	this.sheOtherTable.checkParsed();
    	
    	PurchaseElsePayListEntryCollection elsePayColl = info.getElsePayListEntry();
    	if(elsePayColl == null)
    	{
    		EntityViewInfo view = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		view.setFilter(filter);
    		
    		view.getSelector().add("*");
    		view.getSelector().add("moneyDefine.*");
    		view.getSelector().add("currency");
    		filter.getFilterItems().add(new FilterItemInfo("head",info.getId().toString()));
    		
    		elsePayColl = PurchaseElsePayListEntryFactory.getRemoteInstance().getPurchaseElsePayListEntryCollection(view);
    	}
    	
    	for(int i = 0; i < elsePayColl.size(); i ++)
		{
			PurchaseElsePayListEntryInfo elsePayInfo = elsePayColl.get(i);
			
			IRow row = this.sheOtherTable.addRow();
			
			row.setUserObject(elsePayInfo);
			
			row.getCell("selectDebug").setValue(Boolean.FALSE);
			row.getCell("moneyName").setValue(elsePayInfo.getMoneyDefine());
			//根据款项，带出结算方式
			setSettlementTypeByMoneyDefine(elsePayInfo.getMoneyDefine(), row);
			row.getCell("appDate").setValue(elsePayInfo.getAppDate());
			
			BigDecimal apAmount = elsePayInfo.getApAmount();
			if(apAmount != null)
				apAmount = apAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			row.getCell("appAmount").setValue(apAmount);
			
			row.getCell("actDate").setValue(elsePayInfo.getActRevDate());
			
			BigDecimal actPayAmount = elsePayInfo.getActPayAmount();
			if(actPayAmount != null)
				actPayAmount = actPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			row.getCell("actAmount").setValue(actPayAmount);
			
			setPayOffRowLocked(apAmount, actPayAmount, row);
		}
    }
    
    private void initWuYeTable()
    {
    	this.wuyeTable.checkParsed();
    	
    	ICellEditor cbEditor = new KDTDefaultCellEditor(wuyeDebug);
    	this.wuyeTable.getColumn("selectDebug").setEditor(cbEditor);
    	
    	for(int i = 1; i < this.wuyeTable.getColumnCount(); i ++)
    	{
    		this.wuyeTable.getColumn(i).getStyleAttributes().setLocked(true);
    	}
    	this.wuyeTable.getColumn("settlementType").getStyleAttributes().setLocked(false);
    	
    	KDFormattedTextField field = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
    	field.setPrecision(2);
    	
    	ICellEditor editor = new KDTDefaultCellEditor(field);
    	
    	this.wuyeTable.getColumn("appAmount").setEditor(editor);
    	this.wuyeTable.getColumn("actAmount").setEditor(editor);
    	this.wuyeTable.getColumn("derateAmount").setEditor(editor);
    	
    	this.wuyeTable.getColumn("appAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.wuyeTable.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.wuyeTable.getColumn("actAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.wuyeTable.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.wuyeTable.getColumn("derateAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.wuyeTable.getColumn("derateAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.wuyeTable.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	this.wuyeTable.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	this.wuyeTable.getColumn("derateAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); 
    	
    	// 結算方式
		KDBizPromptBox settlementType = new KDBizPromptBox();
		settlementType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");
		settlementType.setEnabledMultiSelection(true);
		settlementType.setEditFormat("$number$");
		settlementType.setCommitFormat("$number$");
	    settlementType.setDisplayFormat("$name$");
	    
		ICellEditor f7Editor = new KDTDefaultCellEditor(settlementType);
		this.wuyeTable.getColumn("settlementType").setEditor(f7Editor);
		
		this.wuyeTable.getColumn("settlementType").setRenderer(this.getSettlementRender());
		
		this.wuyeTable.getColumn("settlementType").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
    }
    
    private void initSheTable()
    {
    	this.sheTable.checkParsed();

    	ICellEditor cbEditor = new KDTDefaultCellEditor(sheDebug);
    	this.sheTable.getColumn("selectDebug").setEditor(cbEditor);
    	
    	for(int i = 1; i < this.sheTable.getColumnCount(); i ++)
    	{
    		this.sheTable.getColumn(i).getStyleAttributes().setLocked(true);
    	}
    	this.sheTable.getColumn("settlementType").getStyleAttributes().setLocked(false);
    	
    	KDFormattedTextField field = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
    	field.setPrecision(2);
    	
    	ICellEditor editor = new KDTDefaultCellEditor(field);
    	
    	this.sheTable.getColumn("appAmount").setEditor(editor);
    	this.sheTable.getColumn("actAmount").setEditor(editor);
    	
    	this.sheTable.getColumn("appAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.sheTable.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.sheTable.getColumn("actAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.sheTable.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.sheTable.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	this.sheTable.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	// 結算方式
		KDBizPromptBox settlementType = new KDBizPromptBox();
		settlementType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");
		settlementType.setEnabledMultiSelection(true);
		settlementType.setEditFormat("$number$");
		settlementType.setCommitFormat("$number$");
	    settlementType.setDisplayFormat("$name$");
	    
		ICellEditor f7Editor = new KDTDefaultCellEditor(settlementType);
		this.sheTable.getColumn("settlementType").setEditor(f7Editor);
		this.sheTable.getColumn("settlementType").setRenderer(this.getSettlementRender());
		
		this.sheTable.getColumn("settlementType").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
    }
    
    public IDataFormat getSettlementFormatter()
	{
		return new IDataFormat()
		{
			public String format(Object o)
			{
				String temp = "";
				if(o instanceof Object[])
				{
					Object obj[] = (Object[])o;
					for (int j = 0; j < obj.length; j++) 
					{
						if (obj[j] instanceof SettlementTypeInfo)
						{
							SettlementTypeInfo ov = (SettlementTypeInfo) obj[j];

							temp += ov.getName()+";";
						}
					}
				}
				return temp;
			}
		};
	}

	public IBasicRender getSettlementRender()
	{
		ObjectValueRender r = new ObjectValueRender();
		r.setFormat(getSettlementFormatter());
		return r;
	}
    
    private void initTenTable()
    {
    	this.tenTable.checkParsed();
    	
    	ICellEditor cbEditor = new KDTDefaultCellEditor(tenDebug);
    	this.tenTable.getColumn("selectDebug").setEditor(cbEditor);
    	for(int i = 1; i < this.tenTable.getColumnCount(); i ++)
    	{
    		this.tenTable.getColumn(i).getStyleAttributes().setLocked(true);
    	}
    	this.tenTable.getColumn("settlementType").getStyleAttributes().setLocked(false);
    	
    	KDFormattedTextField field = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
    	field.setPrecision(2);
    	
    	ICellEditor editor = new KDTDefaultCellEditor(field);
    	
    	this.tenTable.getColumn("appAmount").setEditor(editor);
    	this.tenTable.getColumn("actAmount").setEditor(editor);
    	
    	this.tenTable.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	this.tenTable.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	
    	this.tenTable.getColumn("appAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.tenTable.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.tenTable.getColumn("actAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.tenTable.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	// 結算方式
		KDBizPromptBox settlementType = new KDBizPromptBox();
		settlementType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");
		settlementType.setEnabledMultiSelection(true);
		settlementType.setEditFormat("$number$");
		settlementType.setCommitFormat("$number$");
	    settlementType.setDisplayFormat("$name$");
	 
		ICellEditor f7Editor = new KDTDefaultCellEditor(settlementType);
		this.tenTable.getColumn("settlementType").setEditor(f7Editor);
		
		this.tenTable.getColumn("settlementType").setRenderer(this.getSettlementRender());
		
		this.tenTable.getColumn("settlementType").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
    }
    
    private void initSheOtherTable()
    {
    	this.sheOtherTable.checkParsed();
    	
    	for(int i = 1; i < this.sheOtherTable.getColumnCount(); i ++)
    	{
    		this.sheOtherTable.getColumn(i).getStyleAttributes().setLocked(true);
    	}
    	this.sheOtherTable.getColumn("settlementType").getStyleAttributes().setLocked(false);
    	
    	KDFormattedTextField field = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
    	field.setPrecision(2);
    	
    	ICellEditor editor = new KDTDefaultCellEditor(field);
    	
    	this.sheOtherTable.getColumn("appAmount").setEditor(editor);
    	this.sheOtherTable.getColumn("actAmount").setEditor(editor);
    	
    	this.sheOtherTable.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	this.sheOtherTable.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	
    	this.sheOtherTable.getColumn("appAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.sheOtherTable.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.sheOtherTable.getColumn("actAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.sheOtherTable.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	// 結算方式
		KDBizPromptBox settlementType = new KDBizPromptBox();
		settlementType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");
		settlementType.setEnabledMultiSelection(true);
		settlementType.setEditFormat("$number$");
		settlementType.setCommitFormat("$number$");
	    settlementType.setDisplayFormat("$name$");
	 	
		ICellEditor f7Editor = new KDTDefaultCellEditor(settlementType);
		this.sheOtherTable.getColumn("settlementType").setEditor(f7Editor);
		this.sheOtherTable.getColumn("settlementType").setRenderer(this.getSettlementRender());
		this.sheOtherTable.getColumn("settlementType").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
    }
    
    private void initCounteractTable()
    {
    	this.counteractTable.checkParsed();
    	
    	final KDCheckBox cb = new KDCheckBox();
    	cb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == 2) {
				} else {
					KDTable table = null;
					if (MoneySysTypeEnum.SalehouseSys.equals(currentMoneySysType)) {
						if(kDTabbedPane1.getSelectedIndex()==0)					
							table = sheTable;
						else if(kDTabbedPane1.getSelectedIndex()==1)
							table = sheOtherTable;
					}else if (MoneySysTypeEnum.TenancySys.equals(currentMoneySysType))
						table = tenTable;
					else if (MoneySysTypeEnum.ManageSys.equals(currentMoneySysType))
						table = wuyeTable;
					
					if (!isTableSelected(table)) {
						MsgBox.showWarning("请先选择收款明细！");
						cb.setSelected(false);
						return;
					}					
					
				}
			}
		});
    	
    	ICellEditor cbEditor = new KDTDefaultCellEditor(cb);
    	this.counteractTable.getColumn("selectDebug").setEditor(cbEditor);
    	
    	for(int i = 0; i < this.counteractTable.getColumnCount(); i ++)
    	{
    		this.counteractTable.getColumn(i).getStyleAttributes().setLocked(true);
    	}
    	this.counteractTable.getColumn("selectDebug").getStyleAttributes().setLocked(false);
    	
    	KDFormattedTextField field = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
    	
    	field.setPrecision(2);
    	
    	ICellEditor editor = new KDTDefaultCellEditor(field);
    	
    	this.counteractTable.getColumn("counteractAmount").setEditor(editor);
    	
    	this.counteractTable.getColumn("counteractAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.counteractTable.getColumn("counteractAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.counteractTable.getColumn("actAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.counteractTable.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.counteractTable.getColumn("canCounteractAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.counteractTable.getColumn("canCounteractAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    }
    
    /**
     * 清空售楼 付款明细 数据   + 其他应收明细
     *
     */
    private void clearShePayTable()
    {
    	isCounteractAccountOK = true;
    	counterDataMapForSHE.clear();
    	for(int i = 0 ; i < this.sheTable.getRowCount(); i ++)
    	{
    		IRow row = this.sheTable.getRow(i);
    		
    		row.getCell("settlementType").setValue(null);
    		row.getCell("gatheringAmount").setValue(null);
    	}
    	for(int i = 0 ; i < this.sheOtherTable.getRowCount(); i ++)
    	{
    		IRow row = this.sheOtherTable.getRow(i);    		
    		row.getCell("settlementType").setValue(null);
    		row.getCell("gatheringAmount").setValue(null);
    	}    	
    	
    }
    /**
     * 将红冲金额带入物业收款明细当中
     * @param eventObj
     */
    private void addCounterDataToWuyeTable()
    {
    	for(int i = 0; i < this.counteractTable.getRowCount(); i ++)
    	{
    		IRow counteractRow = this.counteractTable.getRow(i);
    		BigDecimal remainAmount = (BigDecimal) counteractRow.getCell("counteractAmount").getValue();
    		if(remainAmount == null)
    			remainAmount = FDCHelper.ZERO;
    		
    		if(!((Boolean)counteractRow.getCell("selectDebug").getValue()).booleanValue())
    		{
    			continue;
    		}
    		
    		for(int j = 0; j < this.wuyeTable.getRowCount(); j ++)
    		{
    			IRow wuyeRow = this.wuyeTable.getRow(j);
    			
    			if(!((Boolean)wuyeRow.getCell("selectDebug").getValue()).booleanValue())
    			{
    				continue;
    			}
    			
    			BigDecimal gatheringAmount = FDCHelper.ZERO;
    			if(wuyeRow.getCell("gatheringAmount").getValue() instanceof BigDecimal)
    			{
    				gatheringAmount = (BigDecimal)wuyeRow.getCell("gatheringAmount").getValue();
    			}
    			if(gatheringAmount == null)
    				gatheringAmount = FDCHelper.ZERO;
    			
    			BigDecimal appAmount = FDCHelper.ZERO;
    			if(wuyeRow.getCell("appAmount").getValue() instanceof BigDecimal)
    			{
    				appAmount = (BigDecimal)wuyeRow.getCell("appAmount").getValue();
    			}
    			if(appAmount == null)
    				appAmount = FDCHelper.ZERO;
    			
    			BigDecimal actAmount = FDCHelper.ZERO;
    			if(wuyeRow.getCell("actAmount").getValue() instanceof BigDecimal)
    			{
    				actAmount = (BigDecimal)wuyeRow.getCell("actAmount").getValue();
    			}
    			if(actAmount == null)
    				actAmount = FDCHelper.ZERO;
    			
    			
    			if ( remainAmount.compareTo(FDCHelper.ZERO) > 0)
    			{
    				if (counteractRow.getCell("settlementType").getUserObject() instanceof SettlementTypeInfo)
    				{
    					BigDecimal temp = (BigDecimal) wuyeRow.getCell("gatheringAmount").getValue();
    					if(temp == null)
    						temp = FDCHelper.ZERO;
    					
    					SettlementTypeInfo settlementTypeInfo = (SettlementTypeInfo) counteractRow.getCell("settlementType").getUserObject();
    					Object obj = wuyeRow.getCell("settlementType").getValue();
    					
    					if(obj == null)
    					{
        					wuyeRow.getCell("settlementType").setValue(settlementTypeInfo);
    					}
    					else
    					{
    						if(obj instanceof SettlementTypeInfo)
    						{
    							SettlementTypeInfo oldSettlementType = (SettlementTypeInfo)obj;
    							SettlementTypeInfo[] type = new SettlementTypeInfo[]{settlementTypeInfo,oldSettlementType};
    	    					//wuyeRow.getCell("settlementType").setValue(type);
    						}
    						else if(obj instanceof SettlementTypeInfo[])
    						{
    							SettlementTypeInfo[] oldSettlementType = (SettlementTypeInfo[])obj;
    							int size = 0;
    							if(oldSettlementType != null)
    								size = oldSettlementType.length;
    							
    							SettlementTypeInfo[] type = new SettlementTypeInfo[size + 1];
    							if(oldSettlementType != null)
    							{
    								for(int k = 0; k < oldSettlementType.length; k ++)
    								{
    									type[k] = oldSettlementType[k];
    								}
    							}
    							type[size - 1] = settlementTypeInfo;
    	    					//wuyeRow.getCell("settlementType").setValue(type);
    						}
    					}
    					
    					//BigDecimal setValue = temp.add(remainAmount);
    					BigDecimal setValue = remainAmount;
    					
    					BigDecimal subtract = appAmount.subtract(actAmount);
						if(setValue.compareTo(subtract) <= 0)
    					{
    						if(setValue != null)
    							setValue = setValue.setScale(2, BigDecimal.ROUND_HALF_UP);
    						
    						wuyeRow.getCell("gatheringAmount").setValue(setValue);
    						remainAmount = remainAmount.subtract(setValue);
    					}
    					else
    					{ 	if(appAmount != null)
    						   appAmount = appAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    					
    						wuyeRow.getCell("gatheringAmount").setValue(subtract);
    						remainAmount = remainAmount.subtract(subtract);
    					}
    				}
    			}
    			else
    			{
    				wuyeRow.getCell("gatheringAmount").setValue(FDCHelper.ZERO);
    			}
    		}
    	}
    }
    
    private Map counterDataMapForSHE = new HashMap();		//结构 <RowDes , <结算方式id ,(红冲金额,对方科目s) > >
    
    //表示红冲的对方科目是否一致
    private boolean isCounteractAccountOK = true;
    
    /**
     * 将红冲明细中的相关数据带入  售楼付款明细当中
     */
    private void addCounterDataToSheTable()
    {
    	//将选择的红冲收款单分录的红冲金额，依次的填充到选中的应付款项上
		for (int i = 0; i < this.counteractTable.getRowCount(); i++) {
			IRow counteractRow = this.counteractTable.getRow(i);
			if (!((Boolean) counteractRow.getCell("selectDebug").getValue()).booleanValue()) {
				continue;
			}
			
			if(!(counteractRow.getCell("settlementType").getUserObject() instanceof SettlementTypeInfo))
				continue;
			
			//被红冲的收款分录的结算方式
			SettlementTypeInfo settlementTypeInfo = (SettlementTypeInfo) counteractRow.getCell("settlementType").getUserObject();
			
			BigDecimal remainAmount = (BigDecimal) counteractRow.getCell("counteractAmount").getValue();
			if (remainAmount == null)
				remainAmount = FDCHelper.ZERO;

			FDCReceiveBillEntryInfo fdcRevEntry = (FDCReceiveBillEntryInfo) counteractRow.getUserObject();					
			//被红冲的收款分录的对方科目
			AccountViewInfo oppSubject = fdcRevEntry.getOppSubject();

			remainAmount = dealForCounterSheTables("sheTable",remainAmount,settlementTypeInfo,oppSubject);
			remainAmount = dealForCounterSheTables("sheOtherTable",remainAmount,settlementTypeInfo,oppSubject);
		}
	}
    
    /**处理售楼红冲选择时 应收部分 和其它应收部分 */
    private BigDecimal dealForCounterSheTables(String tableName ,BigDecimal remainAmount,SettlementTypeInfo settlementTypeInfo,AccountViewInfo oppSubject) {
    	KDTable thisTable = null;
    	if(tableName.equals("sheTable"))	thisTable = this.sheTable;
    	else if(tableName.equals("sheOtherTable"))	 thisTable = this.sheOtherTable;
    	
    	for (int j = 0; j < thisTable.getRowCount(); j++) {
			IRow sheRow = thisTable.getRow(j);

			if (!((Boolean) sheRow.getCell("selectDebug").getValue()).booleanValue()) {
				continue;
			}

			BigDecimal gatheringAmount = FDCHelper.ZERO;
			if (sheRow.getCell("gatheringAmount").getValue() instanceof BigDecimal) {
				gatheringAmount = (BigDecimal) sheRow.getCell("gatheringAmount").getValue();
			}
			if (gatheringAmount == null)
				gatheringAmount = FDCHelper.ZERO;

			BigDecimal appAmount = FDCHelper.ZERO;
			if (sheRow.getCell("appAmount").getValue() instanceof BigDecimal) {
				appAmount = (BigDecimal) sheRow.getCell("appAmount").getValue();
			}
			if (appAmount == null)
				appAmount = FDCHelper.ZERO;
			
			BigDecimal actAmount = FDCHelper.ZERO;
			if(sheRow.getCell("actAmount").getValue() instanceof BigDecimal){
				actAmount = (BigDecimal) sheRow.getCell("actAmount").getValue();
			}
			if(actAmount == null)
				actAmount = FDCHelper.ZERO;

			BigDecimal canAppAmount = appAmount.subtract(actAmount);
			//对于预订金，没有应收的概念，可以任意收
			MoneyDefineInfo moneyName = (MoneyDefineInfo) sheRow.getCell("moneyName").getValue();
			if(moneyName != null  &&  MoneyTypeEnum.PreconcertMoney.equals(moneyName.getMoneyType())){
				canAppAmount = FDCHelper.MAX_DECIMAL;
			}
			
			if (gatheringAmount.compareTo(canAppAmount) < 0 && remainAmount.compareTo(FDCHelper.ZERO) > 0) {	
				String settlementTypeId = settlementTypeInfo.getId().toString();
				
				BigDecimal temp = (BigDecimal) sheRow.getCell("gatheringAmount").getValue();
				if (temp == null)
					temp = FDCHelper.ZERO;
				
				//计算该条红冲收款分录可以红冲到该付款明细的金额
				BigDecimal thisCounteractAmount = null;
				BigDecimal setValue = temp.add(remainAmount);
				if (setValue.compareTo(canAppAmount) <= 0) {
					if (setValue != null)
						setValue = setValue.setScale(2, BigDecimal.ROUND_HALF_UP);

					sheRow.getCell("gatheringAmount").setValue(setValue);
					thisCounteractAmount = remainAmount;
					remainAmount = remainAmount.subtract(setValue);
				} else {
					if (canAppAmount != null)
						canAppAmount = canAppAmount.setScale(2, BigDecimal.ROUND_HALF_UP);

					sheRow.getCell("gatheringAmount").setValue(canAppAmount);
					thisCounteractAmount = canAppAmount.subtract(temp);
					remainAmount = remainAmount.subtract(thisCounteractAmount);
				}	
				
				
				//如果同一应付明细上有相同结算方式的红冲，将相同结算方式的红冲金额合并
				RowDes rowDes = new RowDes(tableName, j);
				Map settlementMap = (Map) counterDataMapForSHE.get(rowDes);		//<结算方式id ,(红冲金额,对方科目s) >
				if(settlementMap == null){
					settlementMap = new HashMap();
					Set oppSubjects = new HashSet();
					oppSubjects.add(oppSubject);
					Object[] objs = new Object[]{thisCounteractAmount, oppSubjects};
					
					settlementMap.put(settlementTypeId, objs);							
					counterDataMapForSHE.put(rowDes, settlementMap);
				}else{
					Object[] objs = (Object[]) settlementMap.get(settlementTypeId);
					if(objs == null){
						Set oppSubjects = new HashSet();
						oppSubjects.add(oppSubject);
						objs = new Object[]{thisCounteractAmount, oppSubjects};
						
						settlementMap.put(settlementTypeId, objs);
					}else{//这种表示已有该种结算方式，不用再增加结算方式
						BigDecimal counteractAmount = (BigDecimal) objs[0];
						counteractAmount = counteractAmount.add(thisCounteractAmount);
						objs[0] = counteractAmount;
						
						Set oppSubjects = (Set) objs[1];
						for(Iterator itor = oppSubjects.iterator(); itor.hasNext(); ){
							AccountViewInfo tmp = (AccountViewInfo) itor.next();
							//如果红冲时同一结算方式下有多种对方科目，不允许进行操作
							if(!tmp.getId().toString().equals(oppSubject.getId().toString())){
								isCounteractAccountOK = false;
							}
						}
					}
				}
				
				//需要增加该种结算方式
				addSettlementTypeToPayRow(sheRow, settlementTypeInfo);				
			}				
		
    	}
    	return remainAmount;
    	
    }
    
    
    
    
    

    /**对该行的结算方式列增加一种结算方式	 不重复添加*/
	private void addSettlementTypeToPayRow(IRow sheRow, SettlementTypeInfo settlementTypeInfo) {
		if(settlementTypeInfo==null) return;
		
		Object obj = sheRow.getCell("settlementType").getValue();
		SettlementTypeInfo[] settlementTypes = null;
		if(obj instanceof SettlementTypeInfo){
			SettlementTypeInfo existInfo = (SettlementTypeInfo) obj;
			if(!existInfo.getId().equals(settlementTypeInfo.getId())) 
				settlementTypes = new SettlementTypeInfo[]{existInfo, settlementTypeInfo};
		}else if(obj instanceof SettlementTypeInfo[]){
			SettlementTypeInfo[] oldSettlementTypes = (SettlementTypeInfo[]) obj;
			settlementTypes = new SettlementTypeInfo[oldSettlementTypes.length + 1];
			for(int m=0; m<oldSettlementTypes.length; m++){
				settlementTypes[m] = oldSettlementTypes[m];
				if(oldSettlementTypes[m].getId().equals(settlementTypeInfo.getId())) return;
			}
			settlementTypes[settlementTypes.length - 1] = settlementTypeInfo;
		}else if(obj == null){
			settlementTypes = new SettlementTypeInfo[]{settlementTypeInfo};
		}
		sheRow.getCell("settlementType").setValue(settlementTypes);
	}
    
    private void setSettlementTypeByMoneyDefine(MoneyDefineInfo moneyDefine, IRow row) throws BOSException {
    	if(moneyDefine == null  ||  row == null){
    		return;
    	}
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("head.id", moneyDefine.getId().toString()));
    	
    	view.getSelector().add("*");
    	view.getSelector().add("settlementType.name");
    	view.getSelector().add("settlementType.number");
    	SettlementTypeEntryCollection settlementEntrys = SettlementTypeEntryFactory.getRemoteInstance().getSettlementTypeEntryCollection(view);
    	
    	SettlementTypeCollection tmp = new SettlementTypeCollection();
    	for(int i=0; i<settlementEntrys.size(); i++){
    		SettlementTypeEntryInfo settlementEntry = settlementEntrys.get(i);
    		SettlementTypeInfo settlement = settlementEntry.getSettlementType();
    		boolean isBrought = settlementEntry.isIsBrought();
    		if(isBrought && settlement != null){
    			tmp.add(settlement);
    		}
    	}
    	
    	if(tmp.isEmpty()){
    		return;
    	}else{
    		row.getCell("settlementType").setValue(tmp.toArray());
    	}
    }
    
    
    
    /**
     * 设置呈现哪些系统的付款明细单
     */
    private void setPanelShow() throws EASBizException, BOSException
    {
    	if(MoneySysTypeEnum.SalehouseSys.equals(this.currentMoneySysType))
    	{
    		this.kDTabbedPane1.remove(this.wuyePanel);
    		this.kDTabbedPane1.remove(this.tenPanel);
    		
    		this.initSheTable();
    		this.initSheOtherTable();
    		
    		//如果收款对象是客户的话，特殊处理。  主要是  针对 客户的 退款。  是新加的 暂定款退溃，对原来逻辑没有影响
    		if(GatheringEnum.CustomerRev.equals(this.gatheringEnum))
    		{
    			//暂且只做针对退款的处理
    			if(ReceiveBillTypeEnum.refundment.equals(this.recBillTypeEnum))
    			{
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add("*");
					view.getSelector().add("revBillType.*");
					FilterInfo filter = new FilterInfo();
					view.setFilter(filter);
					filter.getFilterItems().add(new FilterItemInfo("moneyType",	MoneyTypeEnum.PREMONEY_VALUE));
					filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SALEHOUSESYS_VALUE));

					MoneyDefineCollection moneyColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);

					for (int i = 0; i < moneyColl.size(); i++)
					{
						IRow row = this.sheTable.addRow();

						row.getCell("selectDebug").setValue(Boolean.FALSE);

					//	BigDecimal appAmount = purchase.getPrePurchaseAmount();
					//	if (appAmount != null)
						//	appAmount = appAmount.setScale(2,BigDecimal.ROUND_HALF_UP);

					//	BigDecimal actAmount = this.getActAmountByMoneyInSHE(moneyColl.get(i), purchase);
						
						BigDecimal actAmount = this.getActAmountByCustomerInSHE(moneyColl.get(i), this.customer);

						row.getCell("actAmount").setValue(actAmount);

					//	row.getCell("appAmount").setValue(appAmount);
						row.getCell("moneyName").setValue(moneyColl.get(i));

						// 根据款项，带出结算方式
						setSettlementTypeByMoneyDefine(moneyColl.get(i), row);

						// 预订金没有收齐的说法，故不用锁定
						// setPayOffRowLocked(appAmount, actAmount, row);
					}

				}
    		}
    		else
    		{
    		
    		if(sinPurchase != null){
    			this.kDTabbedPane1.remove(this.sheOtherPanel);
    			SincerReceiveEntryCollection sinEntrys = SincerReceiveEntryFactory.getRemoteInstance().getSincerReceiveEntryCollection(
    						"select id,moneyDefine.revBillType,moneyDefine.name,moneyDefine.moneyType,appAmount,actAmount,refundmentAmount,canRefundmentAmount where head.id = '"+sinPurchase.getId()+"'");
    			for(int i=0; i<sinEntrys.size(); i++){
    				SincerReceiveEntryInfo sinEntry = sinEntrys.get(i);
    				IRow row = this.sheTable.addRow();
        			row.setUserObject(sinEntry);
    				
        			row.getCell("moneyName").setValue(sinEntry.getMoneyDefine());
        			
        			//根据款项，带出结算方式
        			setSettlementTypeByMoneyDefine(sinEntry.getMoneyDefine(), row);
        			
        			row.getCell("selectDebug").setValue(Boolean.FALSE);
        			row.getCell("appAmount").setValue(sinEntry.getAppAmount());
//        			BigDecimal actAmount = this.getActAmountByMoneyInSHE(moneyColl.get(j), purchase);
        			row.getCell("actAmount").setValue(sinEntry.getActRevAmount());
        			
        			setPayOffRowLocked(sinEntry.getAppAmount(), sinEntry.getActRevAmount(), row);
    			}
    		}else if(purchase != null){
    			if(ReceiveBillTypeEnum.refundment.equals(this.recBillTypeEnum))
        		{
        			EntityViewInfo view = new EntityViewInfo();
        			FilterInfo filter = new FilterInfo();
        			view.setFilter(filter);
        			
        			filter.getFilterItems().add(new FilterItemInfo("purchase.id",this.purchase.getId().toString()));
        			filter.getFilterItems().add(new FilterItemInfo("room.id",this.room.getId().toString()));
        			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
        			
    				if(QuitRoomFactory.getRemoteInstance().exists(filter)) {
    					this.addPayListInSHE(this.purchase,this.room);
    				}else if(PurchaseChangeFactory.getRemoteInstance().exists("where purchase.id = '"+this.purchase.getId().toString()+"' and state='"+FDCBillStateEnum.AUDITTED_VALUE+"'  ")){  
    					//对于存在审批状态的认购变更单的认购单允许退还多收的金额
    					//PurchaseChangeInfo purInfo = new PurchaseChangeInfo();
    					this.addPayListInSHE(this.purchase,this.room); //×××××××××××××××
    				}else{
//    					对预收款的判断	
    					if (!purchase.getState().equals(FDCBillStateEnum.AUDITTED))
    					{
    						boolean isHavePay = isHavePay(purchase);
    						if (isHavePay && (!ReceiveBillTypeEnum.refundment.equals(this.recBillTypeEnum)))
    						{
    							return;
    						} else
    						{
    							view = new EntityViewInfo();
    							view.getSelector().add("*");
    							view.getSelector().add("revBillType.*");
    							filter = new FilterInfo();
    							view.setFilter(filter);
    							filter.getFilterItems().add(new FilterItemInfo("moneyType",	MoneyTypeEnum.PRECONCERTMONEY_VALUE));
    							filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SALEHOUSESYS_VALUE));
    							
    							MoneyDefineCollection moneyColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);

    							if (ReceiveBillTypeEnum.refundment.equals(this.recBillTypeEnum)
    									&& FDCBillStateEnum.AUDITTED.equals(purchase.getState()))
    							{
    								
    							}
    							else
    							{
    								for (int i = 0; i < moneyColl.size(); i++)
    								{
    									IRow row = this.sheTable.addRow();

    									row.getCell("selectDebug").setValue(Boolean.FALSE);
    									
    									BigDecimal appAmount = purchase.getPrePurchaseAmount();
    									if(appAmount != null)
    										appAmount = appAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    									
    									BigDecimal actAmount = this.getActAmountByMoneyInSHE(moneyColl.get(i), purchase);
    									
    									row.getCell("actAmount").setValue(actAmount);
    									
    									row.getCell("appAmount").setValue(appAmount);
    									row.getCell("moneyName").setValue(moneyColl.get(i));
    									
    									//根据款项，带出结算方式
    				        			setSettlementTypeByMoneyDefine(moneyColl.get(i), row);
    									
    									//预订金没有收齐的说法，故不用锁定
//    									setPayOffRowLocked(appAmount, actAmount, row);
    								}
    							}
    						}
    					} 
    				}
        		}
        		else
        		{
        			this.addPayListInSHE(this.purchase,this.room);
        		}
            	this.addOtherPayListInSHE(this.purchase);
    		}
    		}
    	}
    	else if(MoneySysTypeEnum.TenancySys.equals(this.currentMoneySysType))
    	{
    		this.kDTabbedPane1.remove(this.wuyePanel);
    		this.kDTabbedPane1.remove(this.shePanel);
    		this.kDTabbedPane1.remove(this.sheOtherPanel);
    		
    		this.initTenTable();
    		
    		this.addPayListInTEN(this.tenancyBill,this.room, this.tenAttach);
    	}
    	else if(MoneySysTypeEnum.ManageSys.equals(this.currentMoneySysType))
    	{
    		this.kDTabbedPane1.remove(this.shePanel);
    		this.kDTabbedPane1.remove(this.sheOtherPanel);
    		this.kDTabbedPane1.remove(this.tenPanel);
    		
    		this.initWuYeTable();
    		
    		if(ReceiveBillTypeEnum.refundment.equals(this.recBillTypeEnum))
    		{
    			this.addRefundmentListInWuye(room, customer);
    		}
    		else
    		{
    			this.addPayListInWuYe(room, customer);
    		}
    	}
    	
    	if(ReceiveBillTypeEnum.refundment.equals(this.recBillTypeEnum))
    	{
    		this.cbIsCounteract.setEnabled(false);
    	}
    	else
    	{
    		this.cbIsCounteract.setEnabled(true);
    	}
    }
    
	/**
     * 将付清款的明细行锁住，不能再选择
     * */
    private void setPayOffRowLocked(BigDecimal appAmount, BigDecimal actAmount, IRow row){
    	//退款不做该控制
    	if(ReceiveBillTypeEnum.refundment.equals(recBillTypeEnum)){
    		return;
    	}
    	if(appAmount != null  &&  appAmount.compareTo(FDCHelper.ZERO) != 0){
			if(actAmount != null  &&  actAmount.compareTo(appAmount) >= 0){
				row.getStyleAttributes().setLocked(true);
				row.getStyleAttributes().setBackground(Color.GRAY);
			}
		}
    }
    
    private void addRefundmentListInWuye(RoomInfo room,CustomerInfo customer) throws BOSException
    {
//    	this.wuyeTable.checkParsed();
//    	
//    	EntityViewInfo view = new EntityViewInfo();
//    	FilterInfo filter = new FilterInfo();
//    	view.setFilter(filter);
//    	
//    	SelectorItemCollection selColl = new SelectorItemCollection();
//    	selColl.add("*");
//    	selColl.add("depositBill.*");
//    	selColl.add("depositBill.depositSort.*");
//    	selColl.add("depositBill.depositSort.chargeItem.*");
//    	
//    	view.setSelector(selColl);
//    	
//    	filter.getFilterItems().add(new FilterItemInfo("depositBill.room",room.getId().toString()));
//    	filter.getFilterItems().add(new FilterItemInfo("depositBill.customer.sysCustomer",customer.getId().toString()));
//    	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
//    	
//    	DepositWithdrawBillCollection billColl = DepositWithdrawBillFactory.getRemoteInstance().getDepositWithdrawBillCollection(view);

//		for(int i = 0; i < billColl.size(); i ++)
//		{
//			DepositWithdrawBillInfo info = billColl.get(i);
//    		
//    		IRow row = this.wuyeTable.addRow();
//    		
//    		row.setUserObject(info);
//    		
//    		row.getCell("selectDebug").setValue(Boolean.FALSE);
//    		row.getCell("moneyName").setValue(info.getDepositBill().getDepositSort().getChargeItem());
//    		
//    		BigDecimal arAmout = info.getDepositBill().getDepositAmount();
//    		if(arAmout != null)
//    			arAmout = arAmout.setScale(2, BigDecimal.ROUND_HALF_UP);
//			row.getCell("appAmount").setValue(arAmout);
//    		
//    		BigDecimal payedAmount = info.getDepositBill().getActGatheringAmo();
//    		if(payedAmount != null)
//    			payedAmount = payedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
//			row.getCell("actAmount").setValue(payedAmount);
//			
//    		row.getCell("id").setValue(info.getId().toString());
//    		
//    		setSettlementTypeByMoneyDefine(info.getDepositBill().getDepositSort().getChargeItem(), row);
//		}
    }
    
    /**
     * 取得选中的 售楼 其他付款明细
     * @param settlementMap TODO
     * @param counteractMap 
     * @return
     */
    private PurchaseElsePayListEntryCollection storeElsePayColl(Map settlementMap, Map counteractMap)
    {
    	PurchaseElsePayListEntryCollection elsePayColl = new PurchaseElsePayListEntryCollection();
    	for(int i = 0; i < this.sheOtherTable.getRowCount(); i ++)
    	{
    		IRow row = this.sheOtherTable.getRow(i);
    		Boolean debug = (Boolean)row.getCell("selectDebug").getValue();
    		if(debug.booleanValue())
    		{
    			PurchaseElsePayListEntryInfo info = (PurchaseElsePayListEntryInfo) row.getUserObject();
    			elsePayColl.add(info);
    			
    			//结算方式
			    settlementMap.put(info.getId().toString(),row.getCell("settlementType").getValue());
			    
				ICell cell = this.sheOtherTable.getRow(i).getCell("gatheringAmount");
				if(cell != null && cell.getValue() instanceof BigDecimal)
				{
					RowDes rowDes = new RowDes("sheOtherTable", i);
					Map counSettlementMap = (Map) this.counterDataMapForSHE.get(rowDes);
					if(counSettlementMap == null){
						logger.error("逻辑错误。");
					}else{
						counteractMap.put(info.getId().toString(), counSettlementMap);
					}
				}

    		}
    	}
    	return elsePayColl;
    }
    /**
     * 取得租赁的付款明细单
     * @param tenancyBill
     * @param room
     * @param moneySet 款项名称集合
     * @return
     * @throws BOSException 
     */
    private TenancyRoomPayListEntryCollection getTenRoomPayEntryColl(TenancyBillInfo tenancyBill,RoomInfo room, Set moneySet) throws BOSException
    {
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	
    	SorterItemCollection sorColl = new SorterItemCollection();
    	sorColl.add(new SorterItemInfo("seq"));
    	
    	view.setSorter(sorColl);
    	view.getSelector().add("*");
    	view.getSelector().add("moneyDefine.*");
    	
    	if(tenancyBill.getId() != null)
    		filter.getFilterItems().add(new FilterItemInfo("tenRoom.tenancy",tenancyBill.getId().toString()));
    	if(moneySet != null && moneySet.size() > 0)
    		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType",moneySet,CompareType.INCLUDE));
    	if(room.getId() != null)
    		filter.getFilterItems().add(new FilterItemInfo("tenRoom.room",room.getId().toString()));
    	
		return TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryCollection(view);
    }
    
    /**
     * 取得选中的售楼付款明细
     * @param counteractMap 
     */
    private PurchasePayListEntryCollection storePurchasePayListEntryColl(Map settlementMap, Map counteractMap)
    {
    	PurchasePayListEntryCollection payColl = new PurchasePayListEntryCollection();
		for(int i = 0; i < this.sheTable.getRowCount(); i ++)
		{
			IRow row = this.sheTable.getRow(i);
			Boolean debug = (Boolean)row.getCell("selectDebug").getValue();
			if(debug.booleanValue())
			{
				Object obj = row.getUserObject();
				if(obj instanceof PurchasePayListEntryInfo)
				{
					PurchasePayListEntryInfo info = (PurchasePayListEntryInfo) obj;
    				payColl.add(info);
    				
//    				结算方式
    			    settlementMap.put(info.getId().toString(),row.getCell("settlementType").getValue());
    			    
    				ICell cell = this.sheTable.getRow(i).getCell("gatheringAmount");
    				if(cell != null && cell.getValue() instanceof BigDecimal)
    				{
    					RowDes rowDes = new RowDes("sheTable", i);
    					Map counSettlementMap = (Map) this.counterDataMapForSHE.get(rowDes);
    					if(counSettlementMap == null){
    						logger.error("逻辑错误。");
    					}else{
    						counteractMap.put(info.getId().toString(), counSettlementMap);
    					}
    				}
				}
			}
		}
		return payColl;
    }
    
    private PPMGeneralARCollection storeGeneralColl(Map settlementMap,Map counteractMap)
    {
    	PPMGeneralARCollection generalColl = new PPMGeneralARCollection();
    	for(int i = 0; i < this.wuyeTable.getRowCount(); i ++)
    	{
    		IRow row = this.wuyeTable.getRow(i);
    		
    		Boolean debug = (Boolean)row.getCell("selectDebug").getValue();
    		if(debug.booleanValue())
    		{
    			Object obj = row.getUserObject();
    			if(obj instanceof PPMGeneralARInfo)
    			{
    				PPMGeneralARInfo info = (PPMGeneralARInfo)obj;
    				generalColl.add(info);
    				
    				settlementMap.put(info.getId().toString(),row.getCell("settlementType").getValue());
    				
    				ICell cell = this.wuyeTable.getRow(i).getCell("gatheringAmount");
    				if(cell != null && cell.getValue() instanceof BigDecimal)
    				{
    					BigDecimal amount = (BigDecimal)cell.getValue();
    					counteractMap.put(info.getId().toString(),amount);
    				}
    			}
    		}
    	}
    	return generalColl;
    }
    
    private PPMMeasureARCollection storeMeasureColl(Map settlementMap,Map counteractMap)
    {
    	PPMMeasureARCollection measureARColl = new PPMMeasureARCollection();
    	for(int i = 0; i < this.wuyeTable.getRowCount(); i ++)
    	{ 
    		IRow row = this.wuyeTable.getRow(i);
    		
    		Boolean debug = (Boolean)row.getCell("selectDebug").getValue();
    		if(debug.booleanValue())
    		{
    			Object obj = row.getUserObject();
    			if(obj instanceof PPMMeasureARInfo)
    			{
    				PPMMeasureARInfo info = (PPMMeasureARInfo)obj;
    				measureARColl.add(info);
    				
    				settlementMap.put(info.getId().toString(),row.getCell("settlementType").getValue());
    				
    				ICell cell = this.wuyeTable.getRow(i).getCell("gatheringAmount");
    				if(cell != null && cell.getValue() instanceof BigDecimal)
    				{
    					BigDecimal amount = (BigDecimal)cell.getValue();
    					counteractMap.put(info.getId().toString(),amount);
    				}
    			}
    		}
    	}
    	return measureARColl;
    }
    private DepositWithdrawBillCollection storeDepositWithdrawBillCollection(Map settlementMap,Map counteractMap)
    {

    	DepositWithdrawBillCollection billColl = new DepositWithdrawBillCollection();
    	
    	for(int i = 0; i < this.wuyeTable.getRowCount(); i ++)
    	{
    		IRow row = this.wuyeTable.getRow(i);
    		
    		Boolean debug = (Boolean)row.getCell("selectDebug").getValue();
    		
    		if(debug.booleanValue())
    		{
    			Object obj = row.getUserObject();
    			if(obj instanceof DepositWithdrawBillInfo)
    			{
    				DepositWithdrawBillInfo info = (DepositWithdrawBillInfo)obj;
    				billColl.add(info);
    				
    				settlementMap.put(info.getId().toString(),row.getCell("settlementType").getValue());
    				
    				ICell cell = this.wuyeTable.getRow(i).getCell("gatheringAmount");
    				if(cell != null && cell.getValue() instanceof BigDecimal)
    				{
    					BigDecimal amount = (BigDecimal)cell.getValue();
    					counteractMap.put(info.getId().toString(),amount);
    				}
    			}
    		}
    	}
    	return billColl;
    
    }
    
    private DepositBillCollection storeDepositBillCollection(Map settlementMap,Map counteractMap)
    {
    	DepositBillCollection billColl = new DepositBillCollection();
    	
    	for(int i = 0; i < this.wuyeTable.getRowCount(); i ++)
    	{
    		IRow row = this.wuyeTable.getRow(i);
    		
    		Boolean debug = (Boolean)row.getCell("selectDebug").getValue();
    		
    		if(debug.booleanValue())
    		{
    			Object obj = row.getUserObject();
    			if(obj instanceof DepositBillInfo)
    			{
    				DepositBillInfo info = (DepositBillInfo)obj;
    				billColl.add(info);
    				
    				settlementMap.put(info.getId().toString(),row.getCell("settlementType").getValue());
    				
    				ICell cell = this.wuyeTable.getRow(i).getCell("gatheringAmount");
    				if(cell != null && cell.getValue() instanceof BigDecimal)
    				{
    					BigDecimal amount = (BigDecimal)cell.getValue();
    					counteractMap.put(info.getId().toString(),amount);
    				}
    			}
    		}
    	}
    	return billColl;
    }
    
    
    private PPMTemporaryCollection storeTemporaryColl(Map settlementMap,Map counteractMap)
    {
    	PPMTemporaryCollection temporaryColl = new PPMTemporaryCollection();
    	for(int i = 0; i < this.wuyeTable.getRowCount(); i ++)
    	{ 
    		IRow row = this.wuyeTable.getRow(i);
    		
    		Boolean debug = (Boolean)row.getCell("selectDebug").getValue();
    		if(debug.booleanValue())
    		{
    			Object obj = row.getUserObject();
    			if(obj instanceof PPMTemporaryInfo)
    			{
    				PPMTemporaryInfo info = (PPMTemporaryInfo)obj;
    				temporaryColl.add(info);
    				
    				settlementMap.put(info.getId().toString(),row.getCell("settlementType").getValue());
    				
    				ICell cell = this.wuyeTable.getRow(i).getCell("gatheringAmount");
    				if(cell != null && cell.getValue() instanceof BigDecimal)
    				{
    					BigDecimal amount = (BigDecimal)cell.getValue();
    					counteractMap.put(info.getId().toString(),amount);
    				}
    			}
    		}
    	}
    	return temporaryColl;
    }
    
    private IObjectCollection storeTenRoomOrAttachPayListEntryColl(Map settlementMap)
    {
    	IObjectCollection selectedPayList = null;
    	for(int i = 0; i < this.tenTable.getRowCount(); i ++)
    	{
    		IRow row = this.tenTable.getRow(i);
    		
    		Boolean debug = (Boolean)row.getCell("selectDebug").getValue();
    		if(debug.booleanValue())
    		{
    			Object obj = row.getUserObject();
    			if(obj instanceof TenancyRoomPayListEntryInfo)
    			{
    				if(selectedPayList == null){
    					selectedPayList = new TenancyRoomPayListEntryCollection();
    				}
    				
    				TenancyRoomPayListEntryInfo info = (TenancyRoomPayListEntryInfo)obj;
    				selectedPayList.addObject(info);
    				settlementMap.put(info.getId().toString(),row.getCell("settlementType").getValue());
    			}else if(obj instanceof TenAttachResourcePayListEntryInfo){
    				if(selectedPayList == null){
    					selectedPayList = new TenAttachResourcePayListEntryCollection();
    				}
    				
    				TenAttachResourcePayListEntryInfo info = (TenAttachResourcePayListEntryInfo)obj;
    				selectedPayList.addObject(info);
    				settlementMap.put(info.getId().toString(),row.getCell("settlementType").getValue());
    			}else{
    				logger.error("逻辑错误！！！");
    			}
    		}
    	}
    	return selectedPayList;
    }
    
    /**
     * 返回售楼那边几个特定的付款名称，他们是没有付款明细的
     * @param settlementMap
     * @param counteractMap 
     * @return
     */
    private Collection getSpecifyPayListCollInSHE(Map settlementMap, Map counteractMap)
    {
    	Collection coll = new ArrayList();
    	
    	int count = 0;//流水号
    	for(int i = 0; i < this.sheTable.getRowCount(); i ++)
		{
			IRow row = this.sheTable.getRow(i);
			Boolean debug = (Boolean)row.getCell("selectDebug").getValue();
			if(debug.booleanValue())
			{
				Object obj = row.getUserObject();
				//obj为空的表示特殊款项
				if(obj == null)
				{
					Map tempMap = new HashMap();
					//单独用时间做ID是可能重复的，后面加个流水号
					String tempId = String.valueOf(System.currentTimeMillis()) + count++;
					tempMap.put("id",tempId);
					tempMap.put("moneyName",row.getCell("moneyName").getValue());
					coll.add(tempMap);
//    				结算方式
    			    settlementMap.put(tempId,row.getCell("settlementType").getValue());
    			    
    			    ICell cell = this.sheTable.getRow(i).getCell("gatheringAmount");
    				if(cell != null && cell.getValue() instanceof BigDecimal)
    				{
    					RowDes rowDes = new RowDes("sheTable", i);
    					Map counSettlementMap = (Map) this.counterDataMapForSHE.get(rowDes);
    					if(counSettlementMap == null){
    						logger.error("逻辑错误。");
    					}else{
    						counteractMap.put(tempId, counSettlementMap);
    					}
    				}
				}
			}
		}
    	return coll;
    }
    
    /**
     * 物业那边的像月收款一样的特殊款项
     * @param settlementMap
     * @return
     */
    private Collection getSpecifyPayListCollInWuYe(Map settlementMap)
    {
    	Collection coll = new ArrayList();
    	
    	for(int i = 0; i < this.wuyeTable.getRowCount(); i ++)
		{
			IRow row = this.wuyeTable.getRow(i);
			Boolean debug = (Boolean)row.getCell("selectDebug").getValue();
			if(debug.booleanValue())
			{
				Object obj = row.getUserObject();
				if(obj == null)
				{
					Map tempMap = new HashMap();
					String tempId = String.valueOf(System.currentTimeMillis());
					
					tempMap.put("id",tempId);
					tempMap.put("moneyName",row.getCell("moneyName").getValue());
					coll.add(tempMap);
//    				结算方式
    			    settlementMap.put(tempId,row.getCell("settlementType").getValue());
				}
			}
		}
    	return coll;
    }
    
    private void verifyWuye()
    {
    	for(int i = 0; i < this.wuyeTable.getRowCount(); i ++)
    	{
    		IRow row = this.wuyeTable.getRow(i);
			Boolean debug = (Boolean)row.getCell("selectDebug").getValue();
			
			if(!debug.booleanValue())
				continue;
    		
    		if(row.getCell("settlementType").getValue() == null)
    		{
    			MsgBox.showWarning("第 "+ (i + 1) +" 行，结算方式不能为空！");
    			this.abort();
    		}
    	}
    }
    
    protected void btnYes_actionPerformed(ActionEvent e) throws Exception
    {
    	this.btnYes.setFocusable(true);
    	//结算方式
    	Map settlementMap = new HashMap();    	
    	Map counteractMap = new HashMap();    	
    	
    	if(MoneySysTypeEnum.SalehouseSys.equals(this.currentMoneySysType))
    	{
    		Map counteractMapForSHE = new HashMap();
    		this.verifySHE();
    		
    		PurchaseElsePayListEntryCollection elsePayColl = this.storeElsePayColl(settlementMap, counteractMapForSHE);
    		PurchasePayListEntryCollection payColl = this.storePurchasePayListEntryColl(settlementMap, counteractMapForSHE);
    		Collection specifyPayColl = this.getSpecifyPayListCollInSHE(settlementMap, counteractMapForSHE);
    		
    		this.getUIContext().put(ReceiveBillEidtUI.KEY_PurchaseElsePayListColl,elsePayColl);
    		this.getUIContext().put(ReceiveBillEidtUI.KEY_PurchasePayListColl,payColl);
    		this.getUIContext().put(ReceiveBillEidtUI.Key_SpecifyPayListColl,specifyPayColl);
    		
    		this.getUIContext().put(ReceiveBillEidtUI.KEY_SinPurchasePayListColl, storeSinPurchasePayListEntryColl(settlementMap));
    	
    		//售楼专用的红冲参数，租赁和物业的红冲完善应该也要按售楼这种方式 TODO
    		this.getUIContext().put(ReceiveBillEidtUI.KEY_CounteractAmount_FOR_SHE, counteractMapForSHE);
    	}
    	else if(MoneySysTypeEnum.TenancySys.equals(this.currentMoneySysType))
    	{
    		this.verifyTEN();
    		IObjectCollection tenEntryPayColl = this.storeTenRoomOrAttachPayListEntryColl(settlementMap);
    		this.getUIContext().put(ReceiveBillEidtUI.KEY_TenEntryPayColl,tenEntryPayColl);
    	}
    	else if(MoneySysTypeEnum.ManageSys.equals(this.currentMoneySysType))
    	{
    		this.verifyWuye();
    		
    		PPMGeneralARCollection generalColl = this.storeGeneralColl(settlementMap,counteractMap);
    		PPMMeasureARCollection measureColl = this.storeMeasureColl(settlementMap,counteractMap);
    		PPMTemporaryCollection temporaryColl = this.storeTemporaryColl(settlementMap,counteractMap);
    		DepositBillCollection depositColl = this.storeDepositBillCollection(settlementMap, counteractMap);
    		DepositWithdrawBillCollection withdrawColl = this.storeDepositWithdrawBillCollection(settlementMap, counteractMap);
    		
    		Collection specifyPayColl = this.getSpecifyPayListCollInWuYe(settlementMap);
    		
    		this.getUIContext().put(ReceiveBillEidtUI.Key_generalColl, generalColl);
    		this.getUIContext().put(ReceiveBillEidtUI.Key_measureColl, measureColl);
    		this.getUIContext().put(ReceiveBillEidtUI.Key_temporaryColl, temporaryColl);
    		this.getUIContext().put(ReceiveBillEidtUI.Key_depositBillColl, depositColl);
    		this.getUIContext().put(ReceiveBillEidtUI.Key_WithdrawColl, withdrawColl);
    		
    		this.getUIContext().put(ReceiveBillEidtUI.Key_SpecifyPayListColl,specifyPayColl);
    	}
    	//红冲单标示
    	if(this.cbIsCounteract.isSelected())
    	{
    		this.verifyCounteractTable();
    		this.getUIContext().put(ReceiveBillEidtUI.KEY_BILLTYPE,ReceiveBillTypeEnum.settlement);
    		
    		FDCReceiveBillEntryCollection coll = this.storeCounteractPayList();
    		this.getUIContext().put(ReceiveBillEidtUI.KEY_HongChongRecEntryColl,coll);
    		
    		this.getUIContext().put(ReceiveBillEidtUI.Key_IsSettlement,Boolean.TRUE);
    	}
    	else
    	{
    		this.getUIContext().put(ReceiveBillEidtUI.Key_IsSettlement,Boolean.FALSE);
    	}
    	this.getUIContext().put(ReceiveBillEidtUI.KEY_CounteractAmount, counteractMap);
    	this.getUIContext().put(ReceiveBillEidtUI.KEY_SettlementType,settlementMap);
    	this.getUIContext().put(ReceiveBillEidtUI.KEY_OPT,Boolean.TRUE);
    	this.destroyWindow();
    }
    
    private Object storeSinPurchasePayListEntryColl(Map settlementMap) {
    	SincerReceiveEntryCollection payColl = new SincerReceiveEntryCollection();
		for(int i = 0; i < this.sheTable.getRowCount(); i ++)
		{
			IRow row = this.sheTable.getRow(i);
			Boolean debug = (Boolean)row.getCell("selectDebug").getValue();
			if(debug.booleanValue())
			{
				Object obj = row.getUserObject();
				if(obj instanceof SincerReceiveEntryInfo)
				{
					SincerReceiveEntryInfo info = (SincerReceiveEntryInfo) obj;
    				payColl.add(info);
//    				结算方式
    			    settlementMap.put(info.getId().toString(),row.getCell("settlementType").getValue());
				}
			}
		}
		return payColl;
    }

	private void verifyCounteractTable()
    {
    	this.counteractTable.checkParsed();
    	
    	if(!isCounteractAccountOK){
    		MsgBox.showInfo("红冲明细中的各款项对应的收款科目不一致，无法进行红冲！");
    		this.abort();
    	}
    	
    	BigDecimal sumCounteractAmount = FDCHelper.ZERO;
    	for(int i = 0; i < this.counteractTable.getRowCount(); i ++)
    	{
    		IRow row = this.counteractTable.getRow(i);
    		
    		Boolean debug = (Boolean)row.getCell("selectDebug").getValue();
    		
    		if(!debug.booleanValue())
    			continue;
    		
    		BigDecimal counteractAmount = (BigDecimal)row.getCell("counteractAmount").getValue();
    		if(counteractAmount == null)
    			counteractAmount = FDCHelper.ZERO;
    		else
    			counteractAmount = counteractAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    		
    		sumCounteractAmount = sumCounteractAmount.add(counteractAmount);
    		
    		BigDecimal canCounteractAmount = (BigDecimal)row.getCell("canCounteractAmount").getValue();
    		if(canCounteractAmount == null)
    			canCounteractAmount = FDCHelper.ZERO;
    		else
    			canCounteractAmount = canCounteractAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    		
    		if(counteractAmount.compareTo(canCounteractAmount) > 0)
    		{
    			MsgBox.showWarning("第 "+ (i + 1)+ " 行，红冲金额不能大于可红冲金额！");
    			this.abort();
    		}
    	}
    	
    	if(MoneySysTypeEnum.ManageSys.equals(this.currentMoneySysType))
    	{
    		BigDecimal sum = FDCHelper.ZERO;
    		for(int i = 0; i < this.wuyeTable.getRowCount(); i ++)
    		{
    			IRow row = this.wuyeTable.getRow(i); 
    			
    			BigDecimal gatheringAmount = (BigDecimal)row.getCell("gatheringAmount").getValue();
    			
    			if(gatheringAmount == null)
    				gatheringAmount = FDCHelper.ZERO;
    			else
    				gatheringAmount = gatheringAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    			
    			sum = sum.add(gatheringAmount);
    		}
    		
    		if(sumCounteractAmount.compareTo(sum) != 0)
    		{
    			MsgBox.showWarning("红冲金额与明细中分配金额不相等 ，可能是红冲金额超出分配金额，请修改红冲金额！");
    			this.abort();
    		}
    	}else if(MoneySysTypeEnum.SalehouseSys.equals(this.currentMoneySysType)){
    		BigDecimal sum = FDCHelper.ZERO;
    		for(int i=0; i<this.sheTable.getRowCount(); i++){
    			IRow row = this.sheTable.getRow(i);
    			BigDecimal gatheringAmount = (BigDecimal) row.getCell("gatheringAmount").getValue();
    			
    			if(gatheringAmount == null){
    				gatheringAmount = FDCHelper.ZERO;
    			}else{
    				gatheringAmount = gatheringAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    			}
    			
    			sum = sum.add(gatheringAmount);
    		}
    		for(int i=0; i<this.sheOtherTable.getRowCount(); i++){
    			IRow row = this.sheOtherTable.getRow(i);
    			BigDecimal gatheringAmount = (BigDecimal) row.getCell("gatheringAmount").getValue();    			
    			if(gatheringAmount == null){
    				gatheringAmount = FDCHelper.ZERO;
    			}else{
    				gatheringAmount = gatheringAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    			}    			
    			sum = sum.add(gatheringAmount);
    		}
    		
    		if(sumCounteractAmount.compareTo(sum) != 0){
    			MsgBox.showWarning("红冲金额与明细中分配金额不相等 ，可能是红冲金额超出分配金额，请修改红冲金额！");
    			this.abort();
    		}
    	}
    }
    /**
     * 检验售楼收款
     *
     */
    private void verifySHE()
    {
    	for(int i = 0; i < this.sheTable.getRowCount(); i ++)
    	{
    		IRow row = this.sheTable.getRow(i);
			Boolean debug = (Boolean)row.getCell("selectDebug").getValue();
			
			if(!debug.booleanValue())
				continue;
    		
    		if(row.getCell("settlementType").getValue() == null)
    		{
    			MsgBox.showWarning("第 "+ (i + 1) +" 行，结算方式不能为空！");
    			this.abort();
    		}
    	}
    	
    	for(int i = 0; i < this.sheOtherTable.getRowCount(); i ++)
    	{
    		IRow row = this.sheOtherTable.getRow(i);
			Boolean debug = (Boolean)row.getCell("selectDebug").getValue();
			
			if(!debug.booleanValue())
				continue;
    		
    		if(row.getCell("settlementType").getValue() == null)
    		{
    			MsgBox.showWarning("第 "+ (i + 1) +" 行，结算方式不能为空！");
    			this.abort();
    		}
    	}
    }
    /**
     * 校验租赁收款
     *
     */
    private void verifyTEN()
    {
    	for(int i = 0; i < this.tenTable.getRowCount(); i ++)
    	{
    		IRow row = this.tenTable.getRow(i);
			Boolean debug = (Boolean)row.getCell("selectDebug").getValue();
			
			if(!debug.booleanValue())
				continue;
    		
    		if(row.getCell("settlementType").getValue() == null)
    		{
    			MsgBox.showWarning("第 "+ (i + 1) +" 行，结算方式不能为空！");
    			this.abort();
    		}
    	}
    }
    /**
     * 点击红冲选择框
     */
    protected void cbIsCounteract_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.cbIsCounteract.isSelected())
    	{
    		SwingUtilities.getWindowAncestor(this).setSize(660,600);
			
    		if(MoneySysTypeEnum.SalehouseSys.equals(this.currentMoneySysType))
    		{
    			this.doSheTableAfterCounteractIsSelected();
    			FDCReceiveBillEntryCollection fdcRevEntrys = getToConteractFDCReceiveEntrys();
    			fillCounteractTable(fdcRevEntrys);
    		}
    		else if(MoneySysTypeEnum.ManageSys.equals(this.currentMoneySysType))
    		{
    			this.doWuyeTableAfterCounteractIsSelected();
    			this.fillCounteractTableByMoneyName(MoneySysTypeEnum.ManageSys, MoneyTypeEnum.PreMoney, null);
    		}else if(MoneySysTypeEnum.TenancySys.equals(this.currentMoneySysType)){
    			this.doTenTableAfterCounteractIsSelected();
    			this.fillCounteractTableByMoneyName(MoneySysTypeEnum.TenancySys, MoneyTypeEnum.PreMoney, null);
    		}
    	}
    	else
    	{
    		SwingUtilities.getWindowAncestor(this).setSize(660,355);
		
    		if(MoneySysTypeEnum.SalehouseSys.equals(this.currentMoneySysType))
    		{
    			this.doSheTableAfterCounteractIsNotSelected();
    		}
    		else if(MoneySysTypeEnum.ManageSys.equals(this.currentMoneySysType))
    		{
    			this.doWuyeTableAfterCounteractIsNotSelected();
    		}
    		
    		this.resetValueAsCounterIsNotSelected();
    	}
    }
    
    //这个只是用于售楼系统
    private void fillCounteractTable(FDCReceiveBillEntryCollection fdcRevEntrys) {
    	this.counteractTable.checkParsed();
    	this.counteractTable.removeRows(false);
    	
		if (fdcRevEntrys == null || fdcRevEntrys.isEmpty()) {
			return;
		}
		
		//新需求 需要增加显示 收款日期 ，房间（信息）
		if(this.counteractTable.getColumn("bizDate")==null) {
			IColumn bizDateCol = this.counteractTable.addColumn(1);
			bizDateCol.setKey("bizDate");
			this.counteractTable.getHeadRow(0).getCell("bizDate").setValue("收款日期");
			bizDateCol.getStyleAttributes().setLocked(true);
			bizDateCol.getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		}
		if(this.counteractTable.getColumn("roomDes")==null) { 
			IColumn roomDesCol = this.counteractTable.addColumn(2);
			roomDesCol.setKey("roomDes");
			this.counteractTable.getHeadRow(0).getCell("roomDes").setValue("房间");
			roomDesCol.getStyleAttributes().setLocked(true);
		}
		
		int tempTop = 0;
		String tempReference = "";
		String idStrs = "";
		for (int i = 0; i < fdcRevEntrys.size(); i++) {
			IRow row = this.counteractTable.addRow();
			FDCReceiveBillEntryInfo info = fdcRevEntrys.get(i);

			String tempId = info.getReceivingBill().getId().toString();
			idStrs += ",'" +tempId +"'";
			
			row.setUserObject(info);
			row.getCell("number").setValue(info.getReceivingBill().getNumber());
			
			row.getCell("bizDate").setValue(info.getReceivingBill().getBizDate());
			String roomDes = "";
			if(info.getReceivingBill().getFdcReceiveBill()!=null && info.getReceivingBill().getFdcReceiveBill().getRoom()!=null){
				RoomInfo roomInfo = info.getReceivingBill().getFdcReceiveBill().getRoom();
				if(roomInfo.getBuilding()!=null)
					roomDes += roomInfo.getBuilding().getName()+" " ;
				if(roomInfo.getBuildUnit()!=null)
					roomDes += roomInfo.getBuildUnit().getName()+" " ;
				roomDes += roomInfo.getNumber();
			}
			row.getCell("roomDes").setValue(roomDes);
			
			
			row.getCell("selectDebug").setValue(Boolean.FALSE);
			row.getCell("settlementType").setValue(info.getSettlementType());
			row.getCell("settlementType").setUserObject(info.getSettlementType());
			row.getCell("moneyName").setValue(info.getMoneyDefine());
			row.getCell("canCounteractAmount").setValue(info.getCanCounteractAmount());

			BigDecimal amount = info.getAmount();
			if (amount != null)
				amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
			row.getCell("actAmount").setValue(amount);

			if (!tempReference.equals(tempId)) {
				tempTop = i;
				tempReference = tempId;
			} else {
				// 融合单元格
				this.counteractTable.getMergeManager().mergeBlock(tempTop, 0, i, 0);
			}
		}

		
	}
    
    /**
     * 获得售楼系统中可以被红冲的收款单分录
     * 主要包含对应诚意认购的收款和针对该客户的收款
     * */
	private FDCReceiveBillEntryCollection getToConteractFDCReceiveEntrys() throws BOSException {
		if(this.sinPurchase != null){
			return null;
		}
		if(this.purchase == null){
			return null;
		}
		
		//只有从诚意认购转认购的，才能将原诚意认购的收款结转到当前认购上
		SincerityPurchaseInfo aboutSinPur = purchase.getSincerityPurchase();
		if(aboutSinPur == null  &&  customer == null){
			return null;
		}
		
		EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	
        view.getSorter().add(new SorterItemInfo("receivingBill.id"));
        
    	view.getSelector().add("*");
    	
    	view.getSelector().add("oppSubject.name");
    	view.getSelector().add("oppSubject.number");
    	
    	view.getSelector().add("moneyDefine.name");
    	view.getSelector().add("moneyDefine.number");
    	view.getSelector().add("moneyDefine.moneyType");
    	view.getSelector().add("settlementType.name");
    	view.getSelector().add("settlementType.number");
    	view.getSelector().add("receivingBill.number");
    	
    	view.getSelector().add("receivingBill.bizDate");
    	view.getSelector().add("receivingBill.fdcReceiveBill.room.number");
    	view.getSelector().add("receivingBill.fdcReceiveBill.room.building.name");
    	view.getSelector().add("receivingBill.fdcReceiveBill.room.buildUnit.name");
    	
    	if(aboutSinPur == null){
    		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType", MoneyTypeEnum.PREMONEY_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.moneySysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("canCounteractAmount", FDCHelper.ZERO, CompareType.GREATER));
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.gathering",GatheringEnum.CUSTOMERREV_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.payerID", customer.getId().toString()));
        	
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.TRANSFER_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.isBlankOut",Boolean.FALSE));
        	
//        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.GATHERING_VALUE));
//        	filter.setMaskString("#0 and #1 and #2 and #3 and #4 and (#5 or #6)");
    	}else if(customer == null){
    		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType", MoneyTypeEnum.PREMONEY_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.moneySysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("canCounteractAmount", FDCHelper.ZERO, CompareType.GREATER));
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.GATHERING_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.sinPurchase.id", aboutSinPur.getId().toString()));
    	
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.isBlankOut",Boolean.FALSE));
    	}else{
    		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType", MoneyTypeEnum.PREMONEY_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.moneySysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("canCounteractAmount", FDCHelper.ZERO, CompareType.GREATER));
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.isBlankOut",Boolean.FALSE));
        	
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.GATHERING_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.sinPurchase.id", aboutSinPur.getId().toString()));
        	
        	FDCReceiveBillEntryCollection fdcRevEntrys = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);
        	
        	filter = new FilterInfo();
        	view.setFilter(filter);
        	filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType", MoneyTypeEnum.PREMONEY_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.moneySysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("canCounteractAmount", FDCHelper.ZERO, CompareType.GREATER));
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.isBlankOut",Boolean.FALSE));
        	
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.TRANSFER_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.gathering",GatheringEnum.CUSTOMERREV_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("receivingBill.payerID", customer.getId().toString()));
        	//setMaskString对复杂设置不支持,变向实现,将2个查询结果相加
        	fdcRevEntrys.addCollection(FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view));
        	return fdcRevEntrys;
    	}
    	
		return FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);
//    	return FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection("select k,dd, ");
    	
    	
	}

	private void doTenTableAfterCounteractIsSelected() {
		// TODO Auto-generated method stub
		
	}

	private void doWuyeTableAfterCounteractIsSelected()
    {
    	for(int i = 0 ; i < this.wuyeTable.getRowCount(); i ++)
    	{
    		IRow row = this.wuyeTable.getRow(i);
    		
    		row.getCell("selectDebug").setValue(Boolean.FALSE);
    		
    		//row.getCell("settlementType").getStyleAttributes().setLocked(true);
    	}
    	
    	if (this.wuyeTable.getColumn("gatheringAmount") == null)
		{
        	
        	KDFormattedTextField field = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
        	field.setPrecision(2);
        	
        	ICellEditor editor = new KDTDefaultCellEditor(field);
    		
			IColumn column = this.wuyeTable.addColumn(this.wuyeTable.getColumnIndex("settlementType") + 1);
			column.setKey("gatheringAmount");
			column.getStyleAttributes().setLocked(false);

			IRow row = this.wuyeTable.getHeadRow(0);
			row.getCell("gatheringAmount").setValue("收款金额");
			
			if(this.wuyeTable.getColumn("gatheringAmount") != null)
			{
				this.wuyeTable.getColumn("gatheringAmount").setEditor(editor);
				this.wuyeTable.getColumn("gatheringAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
				this.wuyeTable.getColumn("gatheringAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
		}
    	
    	SellProjectInfo sellProject = room.getBuilding() == null? null : room.getBuilding().getSellProject();
   
    	if(sellProject == null || sellProject.getId() == null )
    		return;
    	else
    	{
    		this.getMoneyIdSet(sellProject.getId().toString());
    	}
    	
    	
    }
	
	private void getMoneyIdSet(String id)
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		view.getSelector().add("reverseEntry.moneyDefine.id");
		view.getSelector().add("reverseEntry.id");
		view.getSelector().add("*");
		
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",id));
		filter.getFilterItems().add( new FilterItemInfo("isEnabled",Boolean.TRUE));
		
		MoneyDefineSellProjectCollection coll = null;
		try
		{
			coll = MoneyDefineSellProjectFactory.getRemoteInstance().getMoneyDefineSellProjectCollection(view);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}
		if(coll.size() < 1)
			return;
		
		
		for(int j = 0; j < coll.size(); j ++)
		{
			
		MoneyDefineSellProjectInfo moneyDefineSellProjectInfo = coll.get(j);
		MoneyDefineReverseEntryCollection entryColl = moneyDefineSellProjectInfo.getReverseEntry();
		this.wuyePreMoneyDefine = moneyDefineSellProjectInfo.getMoneyDefine();
		String moneyId =  moneyDefineSellProjectInfo.getMoneyDefine().getId().toString();
		
		Set set = new HashSet();
		wuyePreMap.put(moneyId, set);
		
		for(int i = 0; i < entryColl.size(); i ++)
		{
			moneyIdSet.add(entryColl.get(i).getMoneyDefine().getId().toString());
			
			if(wuyePreMap.get(moneyId) != null)
			{
				Set tempSet = (HashSet)wuyePreMap.get(moneyId);
				tempSet.add(entryColl.get(i).getMoneyDefine().getId().toString());
				wuyePreMap.put(moneyId, tempSet);
			}
		}
		}
	}
	
	
    
    private void doSheTableAfterCounteractIsSelected()
    { 
    	for(int i=0; i<this.sheOtherTable.getRowCount(); i++){
    		IRow row = this.sheOtherTable.getRow(i);
    		//row.getStyleAttributes().setLocked(true);
    		row.getCell("selectDebug").setValue(Boolean.FALSE);
    		row.getCell("settlementType").setValue(null);
    		row.getCell("settlementType").getStyleAttributes().setLocked(true);
    	}
    	
    	if(this.sheOtherTable.getColumn("gatheringAmount")==null) {
    		IColumn column = this.sheOtherTable.addColumn(this.sheTable.getColumnIndex("settlementType") + 1);
        	column.setKey("gatheringAmount");
        	column.getStyleAttributes().setLocked(true);
        	
        	IRow row = this.sheOtherTable.getHeadRow(0);
        	row.getCell("gatheringAmount").setValue("收款金额");
			
			if(this.sheOtherTable.getColumn("gatheringAmount") != null)			{
				this.sheOtherTable.getColumn("gatheringAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
				this.sheOtherTable.getColumn("gatheringAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
    	}    	
    	
    	
    	for(int i = 0 ; i < this.sheTable.getRowCount(); i ++)
    	{
    		IRow row = this.sheTable.getRow(i);
    		row.getCell("settlementType").getStyleAttributes().setLocked(true);
    	}
    	
    	if (this.sheTable.getColumn("gatheringAmount") == null)
		{
    		IColumn column = this.sheTable.addColumn(this.sheTable.getColumnIndex("settlementType") + 1);
        	column.setKey("gatheringAmount");
        	column.getStyleAttributes().setLocked(true);
        	
        	IRow row = this.sheTable.getHeadRow(0);
        	row.getCell("gatheringAmount").setValue("收款金额");
			
			if(this.sheTable.getColumn("gatheringAmount") != null)
			{
				this.sheTable.getColumn("gatheringAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
				this.sheTable.getColumn("gatheringAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
		}
    }
    
    private void doWuyeTableAfterCounteractIsNotSelected()
    {
    	for(int i = 0; i < this.wuyeTable.getRowCount(); i ++)
    	{
    		IRow row = this.wuyeTable.getRow(i);
    		
    		row.getCell("settlementType").setValue(null);
    		row.getCell("settlementType").getStyleAttributes().setLocked(false);
    	}
    	this.wuyePreMoneyDefine = null;
    	
    	this.wuyeTable.removeColumn(this.wuyeTable.getColumnIndex("gatheringAmount"));
    }
    
    private void doSheTableAfterCounteractIsNotSelected()
    {
    	for(int i=0; i<this.sheOtherTable.getRowCount(); i++){
    		IRow row = this.sheOtherTable.getRow(i);
    		row.getCell("settlementType").setValue(null);
    		row.getCell("settlementType").getStyleAttributes().setLocked(false);
    	}
    	this.sheOtherTable.removeColumn(this.sheOtherTable.getColumnIndex("gatheringAmount"));
    	
    	for(int i = 0 ; i < this.sheTable.getRowCount(); i ++)   	{
    		IRow row = this.sheTable.getRow(i);    		
    		row.getCell("settlementType").setValue(null);
    		row.getCell("settlementType").getStyleAttributes().setLocked(false);
    	}
    	this.sheTable.removeColumn(this.sheTable.getColumnIndex("gatheringAmount"));
    }
    /**
     * 不选红冲的时候，红冲明细单要做的一些 重置的操作
     *
     */
    private void resetValueAsCounterIsNotSelected()
    {
    	for(int i = 0; i < this.counteractTable.getRowCount(); i ++)
    	{
    		IRow row = this.counteractTable.getRow(i);
    		
    		row.getCell("selectDebug").setValue(Boolean.FALSE);
    		row.getCell("counteractAmount").setValue(null);
    		row.getCell("counteractAmount").getStyleAttributes().setLocked(true);
    		row.getCell("counteractAmount").getStyleAttributes().setBackground(Color.WHITE);
    	}
    	
    	if(this.currentMoneySysType.equals(MoneySysTypeEnum.SalehouseSys))
    	{
    		for(int i = 0; i < this.sheTable.getRowCount(); i ++)
    		{
    			IRow row = this.sheTable.getRow(i);
    			
    			row.getCell("selectDebug").setValue(Boolean.FALSE);
    			row.getCell("settlementType").setValue(null);
    			if(row.getCell("gatheringAmount") != null)
    				row.getCell("gatheringAmount").setValue(null);
    		}
    	}
    }
    
    /**
     * 根据某个系统里面的某个款项去生成待红冲的table
     * @param sys
     * @param money
     * @param moneyIdSet 如果传入了这个参数，就以这个更窄一点的来过滤
     * @throws BOSException 
     */
    private void fillCounteractTableByMoneyName(MoneySysTypeEnum sys, MoneyTypeEnum money, Set moneyIdSet) throws BOSException
    {
    	this.counteractTable.checkParsed();
    	
    	this.counteractTable.removeRows();
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	
    	//MoneyDefineInfo;
    	
        SorterItemCollection sorColl = new SorterItemCollection();
        sorColl.add(new SorterItemInfo("receivingBill.id"));
    	
        view.setSorter(sorColl);
        
    	view.getSelector().add("*");
    	view.getSelector().add("moneyDefine.*");
    	view.getSelector().add("settlementType.*");
    	view.getSelector().add("receivingBill.id");
    	view.getSelector().add("receivingBill.number");
    	
    	if(moneyIdSet != null && moneyIdSet.size() > 0)
    	{
    		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id",moneyIdSet,CompareType.INCLUDE));
    	}
    	else
    	{
    		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType",money));
    	}
    	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.moneySysType",sys));
    	filter.getFilterItems().add(new FilterItemInfo("canCounteractAmount",FDCHelper.ZERO,CompareType.GREATER));
    	filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.gathering));
    	
    	//---gefei 反馈，针对物业系统，需要加房间过滤  zhicheng_jin 090628
    	if(MoneySysTypeEnum.ManageSys.equals(sys))
    	{
    		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.room.id", room.getId().toString()));
    		
    		EntityViewInfo v = new EntityViewInfo();
    		FilterInfo f = new FilterInfo();
    		v.setFilter(f);
    		
    		f.getFilterItems().add(new FilterItemInfo("customer.id",this.customer.getId().toString()));
    		
    		CustomerEntryCollection coll = CustomerEntryFactory.getRemoteInstance().getCustomerEntryCollection(v);
    		
			Set tempSet = new HashSet();
			for(int i = 0; i < coll.size(); i ++)
			{
				tempSet.add(coll.get(i).getHead().getId().toString());
			}
			
			if(tempSet != null && tempSet.size() > 0)
			{
				filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill",tempSet,CompareType.INCLUDE));
			}
			else
			{
				filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill",null));
			}
    	}
    	//--
    	
    	FDCReceiveBillEntryCollection fdcEntryColl  = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);
		
		int tempTop = 0;
		String tempReference = "";
		
		for(int i = 0; i < fdcEntryColl.size(); i ++)
		{
			IRow row = this.counteractTable.addRow();
			
			FDCReceiveBillEntryInfo info = fdcEntryColl.get(i);
			
			String tempId = info.getReceivingBill().getId().toString();
			
			row.setUserObject(info);
			row.getCell("number").setValue(info.getReceivingBill().getNumber());
			row.getCell("selectDebug").setValue(Boolean.FALSE);
			row.getCell("settlementType").setValue(info.getSettlementType());
			row.getCell("settlementType").setUserObject(info.getSettlementType());
			row.getCell("moneyName").setValue(info.getMoneyDefine());
			row.getCell("canCounteractAmount").setValue(info.getCanCounteractAmount());
			
			BigDecimal amount = info.getAmount();
			if(amount != null)
				amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
			row.getCell("actAmount").setValue(amount);
			
			if (!tempReference.equalsIgnoreCase(tempId))
			{
				tempTop = i;
				tempReference = tempId;
			} else
			{
				// 融合单元格
				this.counteractTable.getMergeManager().mergeBlock(tempTop, 0, i, 0);
			}
		}
    }
    
    /**
     * 储存红冲的付款明细
     * @return
     */
    private FDCReceiveBillEntryCollection storeCounteractPayList()
    {
    	FDCReceiveBillEntryCollection fdcRecBillEntryColl = new FDCReceiveBillEntryCollection();
    	for(int i = 0; i < this.counteractTable.getRowCount(); i ++)
    	{
    		IRow row = this.counteractTable.getRow(i);
    		
    		Boolean selectDebug = (Boolean)row.getCell("selectDebug").getValue();
    		
    		if(selectDebug.booleanValue())
    		{
    			FDCReceiveBillEntryInfo info = (FDCReceiveBillEntryInfo) row.getUserObject();
    			
    			BigDecimal counteractAmount = (BigDecimal)row.getCell("counteractAmount").getValue();
    			if(counteractAmount != null && counteractAmount.compareTo(FDCHelper.ZERO) > 0)
    				counteractAmount = FDCHelper.ZERO.subtract(counteractAmount);
    			info.setAmount(counteractAmount);
    			fdcRecBillEntryColl.add(info);
    		}
    	}
    	return fdcRecBillEntryColl;
    }
    protected void tenTable_tableClicked(KDTMouseEvent e) throws Exception
    {
    	if(ReceiveBillTypeEnum.refundment.equals(this.recBillTypeEnum))	
    		return;
    	
    	this.activeTenRow = this.tenTable.getSelectManager().getActiveRowIndex();
    	
		IRow row = this.tenTable.getRow(activeTenRow);
		if(row == null)
			return;
		
		Object obj = row.getUserObject();
		if(obj instanceof TenancyRoomPayListEntryInfo)
		{
			for(int i = 0; i < activeTenRow; i ++)
			{
				IRow tempRow = this.tenTable.getRow(i);
				Object tempObj = tempRow.getUserObject();
				if(tempObj instanceof TenancyRoomPayListEntryInfo)
				{
					BigDecimal appAmount = (BigDecimal)tempRow.getCell("appAmount").getValue();
					BigDecimal actAmount = (BigDecimal)tempRow.getCell("actAmount").getValue();
					if(appAmount == null)
						appAmount = FDCHelper.ZERO;
					if(actAmount == null)
						actAmount = FDCHelper.ZERO;
					
					Boolean debug = (Boolean)tempRow.getCell("selectDebug").getValue();
					
					if(actAmount.compareTo(appAmount) < 0 && !debug.booleanValue())
					{
						MsgBox.showWarning("需先选择该款项之前，未交齐的计划性款项！");
						tenDebug.setSelected(false);
						this.btnNo.requestFocus();
						return;
					}
				}
			}
		}
    }
    protected void wuyeTable_tableClicked(KDTMouseEvent e) throws Exception
    {
    	if(ReceiveBillTypeEnum.refundment.equals(this.recBillTypeEnum))
    		return;
    	
    	if(!this.cbIsCounteract.isSelected())
    		return;
    	
    	this.activeWuyeRow = this.wuyeTable.getSelectManager().getActiveRowIndex();
    	
		IRow row = wuyeTable.getRow(activeWuyeRow);
		if(row == null)
			return;
		
		
		int col = this.wuyeTable.getSelectManager().getActiveColumnIndex();
		if(col != this.wuyeTable.getColumnIndex("selectDebug"))
			return;
		
		
		Object obj = row.getUserObject();
		
		MoneyDefineInfo money = (MoneyDefineInfo)row.getCell("moneyName").getUserObject();
		if(money == null || money.getId() == null)
			return;
		if(moneyIdSet.size() < 1)
			return;
		if(!moneyIdSet.contains(money.getId().toString()))
		{
			MsgBox.showWarning("在预收款冲抵规则当中，不存在此款项！");
			wuyeDebug.setSelected(false);
			this.btnNo.requestFocus();
			return;
		}
		else
		{
			Set set = wuyePreMap.keySet();
			
			Set paraSet = new HashSet();
			if(set != null)
			{
				Iterator it = set.iterator();
				while(it.hasNext())
				{
					String moneyId = (String)it.next();
					Set tempSet = (HashSet)wuyePreMap.get(moneyId);
					if(tempSet.contains(money.getId().toString()))
						paraSet.add(moneyId);
				}
			}
			
			this.fillCounteractTableByMoneyName(MoneySysTypeEnum.ManageSys, MoneyTypeEnum.PreMoney, paraSet);
			for(int i = 0; i < wuyeTable.getRowCount(); i ++)
			{
				if(i == this.activeWuyeRow)
				{
					continue;
				}
				
				this.wuyeTable.getRow(i).getCell("selectDebug").setValue(Boolean.FALSE);
			}
		}
    }
    
    
    protected void sheTable_tableClicked(KDTMouseEvent e) throws Exception
    {
    	if(ReceiveBillTypeEnum.refundment.equals(this.recBillTypeEnum))
    		return;
    	
    	this.activeSheRow = this.sheTable.getSelectManager().getActiveRowIndex();
    	
		IRow row = sheTable.getRow(activeSheRow);
		if(row == null)
			return;
		
		Object obj = row.getUserObject();
		if(obj instanceof PurchasePayListEntryInfo)
		{
			for(int i = 0; i < activeSheRow; i ++)
			{
				IRow tempRow = sheTable.getRow(i);
				Object tempObj = tempRow.getUserObject();
				if(tempObj instanceof PurchasePayListEntryInfo)
				{
					BigDecimal appAmount = (BigDecimal)tempRow.getCell("appAmount").getValue();
					BigDecimal actAmount = (BigDecimal)tempRow.getCell("actAmount").getValue();
					if(appAmount == null)
						appAmount = FDCHelper.ZERO;
					if(actAmount == null)
						actAmount = FDCHelper.ZERO;
					
					Boolean debug = (Boolean)tempRow.getCell("selectDebug").getValue();
					
					if(actAmount.compareTo(appAmount) < 0 && !debug.booleanValue())
					{
						MsgBox.showWarning("需先选择该款项之前，未交齐的计划性款项！");
						sheDebug.setSelected(false);
						this.btnNo.requestFocus();
						return;
					}
				}
			}
		}
    }
    
    //主要就是在选中一条收款单分录时，将红冲金额列设为必录
    protected void counteractTable_tableClicked(KDTMouseEvent e) throws Exception
    {
    	this.activeCounteractRow = this.counteractTable.getSelectManager().getActiveRowIndex();
    	
    	int temp = this.counteractTable.getRowCount();
    	
    	if(temp < 1)
    		return;
    
    	if(this.counteractTable.getSelectManager().getActiveColumnIndex() != this.counteractTable.getColumnIndex("selectDebug"))
    		return;
    	
    	int rowIndex = this.counteractTable.getSelectManager().getActiveRowIndex();
    	IRow row = this.counteractTable.getRow(rowIndex);
    	
    	if(row.getCell("selectDebug").getValue().equals(Boolean.TRUE))  //选中时取消
    	{
    		row.getCell("counteractAmount").setValue(null);
    		row.getCell("counteractAmount").getStyleAttributes().setLocked(true);
    		row.getCell("counteractAmount").getStyleAttributes().setBackground(Color.WHITE);
    	}
    	else													//非选中时被选中
    	{
    		KDTable table = null;
    		if(MoneySysTypeEnum.SalehouseSys.equals(currentMoneySysType)) {
    			if(this.kDTabbedPane1.getSelectedIndex()==0) table = sheTable;
    			else if(this.kDTabbedPane1.getSelectedIndex()==1) table = sheOtherTable;
    		}else if(MoneySysTypeEnum.TenancySys.equals(currentMoneySysType))
    			table = tenTable;
    		else if(MoneySysTypeEnum.ManageSys.equals(currentMoneySysType))
    			table = wuyeTable;
    		
    		if(!this.isTableSelected(table))    		{
    			this.btnNo.requestFocus();
    			row.getCell("selectDebug").setValue(Boolean.FALSE);
    			return;
    		}
    		row.getCell("counteractAmount").getStyleAttributes().setLocked(false);
    		row.getCell("counteractAmount").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
    	}
    	this.btnNo.requestFocus();
    }
    /**
     * 判断这个表格是否至少有一行被选中
     * @param table
     */
    private boolean isTableSelected(KDTable table)
    {
    	if(table == null){
    		return false;
    	}
    	if(table.getRowCount() < 1)
    		return false;
    	
    	for(int i = 0 ; i < table.getRowCount(); i ++)
    	{
    		if(((Boolean)table.getRow(i).getCell("selectDebug").getValue()).booleanValue())
    		{
    			return true;
    		}
    	}
    	return false;
    }
    /**
	 * 判断该认购单是否有付款过
	 * @author laiquan_luo
	 * @param purchase
	 * @return
	 */
	private boolean isHavePay(PurchaseInfo purchase)
	{
		PurchasePayListEntryCollection purchasePayListEntryColl = purchase.getPayListEntry();
		for (int i = 0; i < purchasePayListEntryColl.size(); i++)
		{
			PurchasePayListEntryInfo entry = purchasePayListEntryColl.get(i);
			BigDecimal actAmount = entry.getActPayAmount();
			if (actAmount != null && actAmount.compareTo(FDCHelper.ZERO) > 0)
			{
				return true;
			}
		}
		return false;
	}
	
	protected void counteractTable_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		
		IColumn column = this.counteractTable.getColumn(colIndex);
		IRow row = this.counteractTable.getRow(rowIndex);
		if(column == null  ||  row == null){
			return;
		}
		
		//是否选中该条记录的处理在counteractTable_tableClicked中处理了，这里主要处理金额变化的情况
		//去掉钩选时，也要处理
		if(!"counteractAmount".equals(column.getKey())  &&  !"selectDebug".equals(column.getKey())){
			return;
		}
		
		if(MoneySysTypeEnum.SalehouseSys.equals(currentMoneySysType))
		{
			clearShePayTable();
			addCounterDataToSheTable();
		}
		else if(MoneySysTypeEnum.ManageSys.equals(currentMoneySysType))
		{
			addCounterDataToWuyeTable();
		}
	}
	
	//用来描述1行的类
	private class RowDes{
		private String tblName;
		private int rowIndex;
		
		public RowDes(){
		}
		public RowDes(String tblName, int rowIndex){
			this.tblName = tblName;
			this.rowIndex = rowIndex;
		}
		public boolean equals(Object obj) {
			if(obj instanceof RowDes){
				RowDes other = (RowDes) obj;
				return this.tblName.equals(other.tblName)  &&  this.rowIndex == other.rowIndex;
			}
			return false;
		}
		public int hashCode() {
			return tblName.hashCode() + rowIndex;
		}
	}
}