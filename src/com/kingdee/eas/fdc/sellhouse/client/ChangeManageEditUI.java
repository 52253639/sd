/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FocusTraversalPolicy;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.JViewport;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBoxMultiColumnItem;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.UIFocusTraversalPolicy;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AgioBillFactory;
import com.kingdee.eas.fdc.sellhouse.AgioBillInfo;
import com.kingdee.eas.fdc.sellhouse.AgioCalTypeEnum;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AgioSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BizDateToEnum;
import com.kingdee.eas.fdc.sellhouse.BizFlowEnum;
import com.kingdee.eas.fdc.sellhouse.BizListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BizListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CalcTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChangeAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChangeCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeManageFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeManageInfo;
import com.kingdee.eas.fdc.sellhouse.ChangePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChangePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomAttachEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomAttachEntryInfo;
import com.kingdee.eas.fdc.sellhouse.CompensateRoomListFactory;
import com.kingdee.eas.fdc.sellhouse.DealStateEnum;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.IChangeManage;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeBizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SignAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import com.kingdee.eas.fdc.sellhouse.AgioParam;
import com.kingdee.jdbc.rowset.IRowSet;
/**
 * output class name
 */
public class ChangeManageEditUI extends AbstractChangeManageEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChangeManageEditUI.class);
    private static final String CHANGENAME="更名";
    private static final String CHANGEROOM="换房";
    private static final String QUITROOM="退房";
    private static final String PRICECHANGE="价格变更";
    private static final String SOURCEBILL="原业务单据";
    private static final String SIGNCHANGEROOM="签约换房";
    private static final String PURCHANGEROOM="认购换房";
    private static final String PRECHANGEROOM="预定换房";
    private static final String SIGNQUITROOM="签约退房";
    private static final String PURQUITROOM="认购退房";
    private static final String PREQUITROOM="预定退房";
    
    protected final static String PL_STATE = "state";// 状态
	protected final static String PL_MONEYNAME = "moneyName";// 款项名称
	protected final static String PL_APPDATE = "appDate";// 应收日期
	protected final static String PL_CURRENCY = "currency";// 币别
	protected final static String PL_APPAMOUNT = "appAmount";// 应收金额
	protected final static String PL_ACTAMOUNT = "actAmount";// 实收金额
	protected final static String PL_BALANCE = "balance";// 应收余额
	protected final static String PL_DES = "des";// 说明
	
	protected final static String AP_ROOMNUMBER = "roomNumber";// 房间
	protected final static String AP_BUILDINGAREA = "buildingArea";// 建筑面积
	protected final static String AP_ROOMAREA = "roomArea";// 套内面积
	protected final static String AP_BUILDINGPRICE = "buildingPrice";// 建筑单价
	protected final static String AP_ROOMPRICE = "roomPrice";// 套内单价
	protected final static String AP_STANDARDTOTALAMOUNT = "standardTotalAmount";// 标准总价
	protected final static String AP_AGIO = "agio";// 折扣
	protected final static String AP_ISMERGETOCONTRACT = "isMergeTocontract";// 是否合并入合同
	protected final static String AP_MERGEAMOUNT = "mergeAmount";// 合并金额
	
	protected IObjectValue objectValue=null;
	protected IObjectValue oldObjectValue=null;
	protected BaseTransactionInfo srcInfo=null;
	protected AgioParam currAgioParam=new AgioParam();
	protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
	protected FullOrgUnitInfo org=SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
	   
	protected boolean isEditDealAmount=false;
	protected boolean isDealAmountEdit=false;
	protected boolean isBizDateEdit=true;
	protected boolean isUseAgioScheme=true;
	protected boolean isAgioListCanEdit=true;
	protected BizDateToEnum cnBizAdscriptionDate=BizDateToEnum.OldBillBizDate;
	protected BizDateToEnum crBizAdscriptionDate=BizDateToEnum.OldBillBizDate;
	protected BizDateToEnum pcBizAdscriptionDate=BizDateToEnum.OldBillBizDate;
	
	protected List customer=new ArrayList();
	protected MoneyDefineInfo fittmentMoneyDefine=SHEManageHelper.getFittmentMoneyDefine();;
	protected MoneyDefineInfo roomAttactMoneyDefine=SHEManageHelper.getRoomAttachMoneyDefine();
	protected CurrencyInfo currency=SHEManageHelper.getCurrencyInfo(null);
	protected int priceDigit=0;
	protected int digit=0;
	protected int toIntegerType=0;
	protected int priceToIntegerType=0;
	protected boolean loadPricePanel=false;
	
	protected MoneyDefineInfo preMoneyDefine=SHEManageHelper.getPreMoneyDefine();
	protected MoneyDefineInfo purMoneyDefine=SHEManageHelper.getPurMoneyDefine();
	
	private static final String CANTEDIT = "cantEdit";
    /**
     * output class constructor
     */
    public ChangeManageEditUI() throws Exception
    {
        super();
    }

    public SelectorItemCollection getSelectors() {
		SelectorItemCollection selColl = super.getSelectors();
		selColl.add("sellProject.*");
		selColl.add("srcId");
		selColl.add("newId");
		selColl.add("newEntryId");
		selColl.add("state");
		selColl.add("orgUnit.*");
		selColl.add("CU.*");
		selColl.add("transactionID");
		
		selColl.add("busAdscriptionDate");
		selColl.add("bizType");
		selColl.add("bizState");
		selColl.add("changeRoomType");
		selColl.add("quitRoomType");
		selColl.add("room.*");
		selColl.add("srcRoom.*");
		selColl.add("quitRoomType");
		selColl.add("moneyDefine.*");
		selColl.add("actAmount");
		selColl.add("feeAmount");
		
		selColl.add("customerNames");
		selColl.add("srcCustomerNames");
		selColl.add("customerPhone");
		selColl.add("srcCustomerPhone");
		
		selColl.add("bulidingArea");
		selColl.add("roomArea");
		selColl.add("strdPlanBuildingArea");
		selColl.add("strdPlanRoomArea");
		selColl.add("strdActualBuildingArea");
		selColl.add("strdActualRoomArea");
		
		
		selColl.add("isEarnestInHouseAmount");
		selColl.add("isAutoToInteger");
		selColl.add("isBasePriceSell");
		selColl.add("toIntegerType");
		selColl.add("digit");
		selColl.add("priceToIntegerType");
		selColl.add("priceDigit");
		
		selColl.add("handler.*");
		
		selColl.add("payListEntry.*");
		selColl.add("payListEntry.moneyDefine.*");
		selColl.add("payListEntry.currency.*");
		
		selColl.add("customerEntry.*");
		selColl.add("customerEntry.customer.*");
		selColl.add("customerEntry.certificate.*");
		selColl.add("customerEntry.customer.mainCustomer.*");
		
		selColl.add("roomAttachEntry.*");
		selColl.add("roomAttachEntry.room.*");
		selColl.add("roomAttachEntry.agioEntry.*");
		selColl.add("roomAttachEntry.agioEntry.agio.*");
		
		selColl.add("agioEntry.*");
		selColl.add("agioEntry.agio.*");
		
		selColl.add("sheRevBill.id");
		selColl.add("dealState");
		selColl.add("creator.person.name");
		
		return selColl;
	}
  
    public void storeFields()
    {
    	super.storeFields();
        if(ChangeBizTypeEnum.CHANGENAME.equals(editData.getBizType())){
    		editData.setMoneyDefine((MoneyDefineInfo)this.f7MoneyDefine.getValue());
        	editData.setActAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtActAmount.getBigDecimalValue()));
        	editData.setFeeAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtFeeAmount.getBigDecimalValue()));

        	editData.setBusAdscriptionDate(this.pkCNBizAdscriptionDate.getTimestamp());
        	editData.setDealState(DealStateEnum.DEAL);
        	
        }
		if(ChangeBizTypeEnum.CHANGEROOM.equals(editData.getBizType())){
			editData.setMoneyDefine((MoneyDefineInfo)this.f7CRMoneyDefine.getValue());
			editData.setActAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtCRActAmount.getBigDecimalValue()));
        	editData.setFeeAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtCRFeeAmount.getBigDecimalValue()));
		}
		if(ChangeBizTypeEnum.QUITROOM.equals(editData.getBizType())){
			editData.setMoneyDefine((MoneyDefineInfo)this.f7QRMoneyDefine.getValue());
			editData.setActAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtQRActAmount.getBigDecimalValue()));
        	editData.setFeeAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtQRFeeAmount.getBigDecimalValue()));
		}
		if(ChangeBizTypeEnum.PRICECHANGE.equals(editData.getBizType())){
			
			editData.setBusAdscriptionDate(this.pkBizAdscriptionDate.getTimestamp());
			
			storePayList(this.tblPCPayList);
			storeAttachmentEntry();
			storeAgioEntry();
			setTxtFormateNumerValue();
		}
		if(this.objectValue!=null){
    		editData.setSrcCustomerNames(((BaseTransactionInfo)objectValue).getCustomerNames());
    		editData.setSrcCustomerPhone(((BaseTransactionInfo)objectValue).getCustomerPhone());
    	}else{
			editData.setSrcCustomerNames(null);
			editData.setSrcCustomerPhone(null);
    	}
		
		/**
		 * 把面积重新set到editData当中，不能保存后面积丢失
		 * 解决融创R111123-0197问题
		 */
		if (SellTypeEnum.LocaleSell.equals(editData.getSellType())) {
			this.editData.setStrdActualBuildingArea(txtBuildingArea.getBigDecimalValue());
			this.editData.setStrdActualRoomArea(txtRoomArea.getBigDecimalValue());
		} else if (SellTypeEnum.PreSell.equals(editData.getSellType())) {
			this.editData.setBulidingArea(txtBuildingArea.getBigDecimalValue());
			this.editData.setRoomArea(txtRoomArea.getBigDecimalValue());
		} else if (SellTypeEnum.PlanningSell.equals(editData.getSellType())) {
			this.editData.setStrdPlanBuildingArea(txtBuildingArea.getBigDecimalValue());
			this.editData.setStrdPlanRoomArea(txtRoomArea.getBigDecimalValue());
		} else {
			txtBuildingArea.setValue(FDCHelper.ZERO);
			txtRoomArea.setValue(FDCHelper.ZERO);
		}
    }
    
	protected ICoreBase getBizInterface() throws Exception {
		return ChangeManageFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected IObjectValue createNewData() {
		ChangeManageInfo info=new ChangeManageInfo();
		if(this.getUIContext().get("srcId")!=null){
			String srcId=this.getUIContext().get("srcId").toString();
			try {
				ObjectUuidPK pk=new ObjectUuidPK(srcId);
				objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk,SHEManageHelper.getBizSelectors(pk.getObjectType()));
				oldObjectValue=objectValue;
				
				if(objectValue instanceof PurchaseManageInfo
						||objectValue instanceof PrePurchaseManageInfo){
					if(((BaseTransactionInfo)objectValue).getSrcId()!=null){
						ObjectUuidPK srcpk=new ObjectUuidPK(((BaseTransactionInfo)objectValue).getSrcId());
						IObjectValue srcobjectValue=DynamicObjectFactory.getRemoteInstance().getValue(srcpk.getObjectType(),srcpk,SHEManageHelper.getBizSelectors(srcpk.getObjectType()));
						if(srcobjectValue instanceof BaseTransactionInfo){
							srcInfo=(BaseTransactionInfo) srcobjectValue;
						}
					}
				}
				
				if(this.getUIContext().get("bizType")!=null){
					info.setBizType((ChangeBizTypeEnum)this.getUIContext().get("bizType"));
				}
				info.setTransactionID(((BaseTransactionInfo)objectValue).getTransactionID());
				info.setSrcId(BOSUuid.read(srcId));
				RoomInfo room=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(((BaseTransactionInfo)objectValue).getRoom().getId()));
				
				info.setSrcRoom(room);
				info.setBizState(((BaseTransactionInfo)objectValue).getBizState());
				
				if(info.getSellProject()==null){
					info.setSellProject(((BaseTransactionInfo)objectValue).getSellProject());
				}
				
				BigDecimal ChangeRoomActAmount=getPayList(info,objectValue);
				info.setActAmount(ChangeRoomActAmount);
				if(ChangeBizTypeEnum.PRICECHANGE.equals(info.getBizType())){
					getAgioList(info,objectValue);
					getRoomAttachmentList(info,objectValue);
				}
				
			} catch (BOSException e) {
				logger.error(e.getMessage());
			} catch (EASBizException e) {
				logger.error(e.getMessage());
			}
		}
		
		info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setHandler(SysContext.getSysContext().getCurrentUserInfo());
		info.setDealState(DealStateEnum.NOTDEAL);
		try {
			info.setChangeDate(FDCCommonServerHelper.getServerTimeStamp());
			info.setBusAdscriptionDate(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info.setIsFitmentToContract(false);
		if(this.getUIContext().get("sellProject")!=null){
			info.setSellProject((SellProjectInfo)this.getUIContext().get("sellProject"));
		}
	
		return info;
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();

        int idx = idList.getCurrentIndex();
        if (idx >= 0) {
            billIndex = "(" + (idx + 1) + ")";
        } else {
        	billIndex = "";
        }
		refreshUITitle();
		
		TransactionStateEnum bizState=editData.getBizState();
		
		if(objectValue==null&&editData.getSrcId()!=null){
			ObjectUuidPK pk=new ObjectUuidPK(editData.getSrcId());
			try {
				objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk,SHEManageHelper.getBizSelectors(pk.getObjectType()));
			} catch (BOSException e) {
				logger.error(e.getMessage());
			}
		}
		oldObjectValue=objectValue;
			
		if(ChangeBizTypeEnum.CHANGENAME.equals(editData.getBizType())){
			this.f7MoneyDefine.setValue(editData.getMoneyDefine());
			this.txtActAmount.setValue(editData.getActAmount());
			this.txtFeeAmount.setValue(editData.getFeeAmount());
			
			this.pkCNBizAdscriptionDate.setValue(editData.getBusAdscriptionDate());
			
			if(STATUS_ADDNEW.equals(this.oprtState)){
				getCustomerList(objectValue,this.customer);
				this.storeCustomerEntry();
			}
			loadCustomerEntry(editData);
			loadSrcCustomerEntry(objectValue);
			loadPayList(editData,this.tblPayList);
		}
		if(ChangeBizTypeEnum.CHANGEROOM.equals(editData.getBizType())){
			this.f7CRMoneyDefine.setValue(editData.getMoneyDefine());
			this.txtCRActAmount.setValue(editData.getActAmount());
			this.txtCRFeeAmount.setValue(editData.getFeeAmount());
			
			loadBizType(bizState,ChangeBizTypeEnum.CHANGEROOM);
		}
		if(ChangeBizTypeEnum.QUITROOM.equals(editData.getBizType())){
			this.f7QRMoneyDefine.setValue(editData.getMoneyDefine());
			this.txtQRActAmount.setValue(editData.getActAmount());
			this.txtQRFeeAmount.setValue(editData.getFeeAmount());
			
			
			loadBizType(bizState,ChangeBizTypeEnum.QUITROOM);
			loadPayList(editData,this.tblQRPayList);
			if(STATUS_ADDNEW.equals(this.oprtState)){
				this.txtQuitAmount.setValue(this.txtQRActAmount.getBigDecimalValue());
			}
		}
		if(ChangeBizTypeEnum.PRICECHANGE.equals(editData.getBizType())){
			
			loadArea(editData);
			
			this.txtFitmentAmount1.setValue(editData.getFitmentTotalAmount());
			this.pkBizAdscriptionDate.setValue(editData.getBusAdscriptionDate());
			
			loadPayList(editData,this.tblPCPayList);
			if(STATUS_ADDNEW.equals(this.oprtState)){
				loadPriceChangePanel();
			}
			loadAttachmentEntry(editData);
			loadAgioEntry(editData);
		}
		if(editData.getBizType()==null){
			objectValue=null;
			loadCustomerEntry(editData);
			loadSrcCustomerEntry(objectValue);
			loadPayList(editData,this.tblPayList);
			loadAttachmentEntry(editData);
			loadAgioEntry(editData);
			
			this.tblPayList.removeRows();
			this.tblPCPayList.removeRows();
			this.tblQRPayList.removeRows();
			
			this.f7MoneyDefine.setValue(null);
			this.txtActAmount.setValue(null);
			this.txtFeeAmount.setValue(null);
			this.f7CRMoneyDefine.setValue(null);
			this.txtCRActAmount.setValue(null);
			this.txtCRFeeAmount.setValue(null);
			this.f7QRMoneyDefine.setValue(null);
			this.txtQRActAmount.setValue(null);
			this.txtQRFeeAmount.setValue(null);
			
			this.panelSourceBill.setViewport(new JViewport());
			this.panelNewBill.setViewport(new JViewport());
			this.comboChangeRoomType.removeAllItems();
			this.comboQuitRoomType.removeAllItems();
			
			this.tabInformation.removeAll();
			this.panelSourceBill.setName(SOURCEBILL);
			this.tabInformation.add(panelSourceBill);
		}
		
		attachListeners();
	}
	private void loadArea(BaseTransactionInfo info){
		if(SellTypeEnum.LocaleSell.equals(info.getSellType())){	
			txtBuildingArea.setValue(info.getStrdActualBuildingArea());
			txtRoomArea.setValue(info.getStrdActualRoomArea());
		} else if(SellTypeEnum.PreSell.equals(info.getSellType())){
			txtBuildingArea.setValue(info.getBulidingArea());
			txtRoomArea.setValue(info.getRoomArea());
		}else if(SellTypeEnum.PlanningSell.equals(info.getSellType())){
			txtBuildingArea.setValue(info.getStrdPlanBuildingArea());
			txtRoomArea.setValue(info.getStrdPlanRoomArea());
		}else{
			txtBuildingArea.setValue(FDCHelper.ZERO);
			txtRoomArea.setValue(FDCHelper.ZERO);
		}
	}
	public void onLoad() throws Exception {
		initTblAttachProperty();
		initTblPayList(this.tblPayList);
		initTblPayList(this.tblQRPayList);
		initTblPayList(this.tblPCPayList);
			
		this.details.setRequired(true);
		this.tblPayList.getStyleAttributes().setLocked(true);
		this.tblQRPayList.getStyleAttributes().setLocked(true);
		super.onLoad();
		initF7Room();
		initMoneyDefine(f7MoneyDefine);
		initMoneyDefine(f7QRMoneyDefine);
		initMoneyDefine(f7CRMoneyDefine);
		initControl();
		initCustomer();
		initF7ChangeReason(editData.getBizType());
		setF7PayTypeFilter();
		setF7AgioSchemeFilter();
		initBizPanel(editData.getBizType());
		initSourceBillPanel();
		initNewBillPanel();
		setSrcBizAdscriptionDate();
		
		this.comboBizType.removeItem(ChangeBizTypeEnum.PRICECHANGE);
		this.comboBizType.removeItem(ChangeBizTypeEnum.CHANGEROOM);
	}
	private void initCustomer() {
		this.labCustomer1.setForeground(Color.BLUE);
		this.labCustomer2.setForeground(Color.BLUE);
		this.labCustomer3.setForeground(Color.BLUE);
		this.labCustomer4.setForeground(Color.BLUE);
		this.labCustomer5.setForeground(Color.BLUE);
		
		this.labSourceCustomer1.setForeground(Color.BLUE);
		this.labSourceCustomer2.setForeground(Color.BLUE);
		this.labSourceCustomer3.setForeground(Color.BLUE);
		this.labSourceCustomer4.setForeground(Color.BLUE);
		this.labSourceCustomer5.setForeground(Color.BLUE);
	}
	protected void initUI(Map uiContextMap,KDScrollPane panel,String UIClassName,String state) throws UIException{
		UIContext uiContext = new UIContext(this);
		Iterator it = uiContextMap.keySet().iterator();
		while (it.hasNext()){
			String key=it.next().toString();
			uiContext.put(key, uiContextMap.get(key));
		}
		BaseTransactionEditUI ui=(BaseTransactionEditUI) UIFactoryHelper.initUIObject(UIClassName, uiContext, null,state);
		ui.contCreator.setVisible(false);
		ui.contCreateTime.setVisible(false);
		ui.contModifier.setVisible(false);
		ui.contModifyDate.setVisible(false);
		ui.contAuditDate.setVisible(false);
		ui.contAuditor.setVisible(false);
		panel.setViewportView(ui);
		panel.setKeyBoardControl(true);
		panel.setEnabled(false);
	}
	protected void initNewBillPanel(){
		if(objectValue!=null&&ChangeBizTypeEnum.CHANGEROOM.equals(comboBizType.getSelectedItem())){
			if(this.getNewBillUI(this.panelNewBill)!=null&&oldObjectValue.getClass().equals(objectValue.getClass())) return;
			try {
				SellProjectInfo sellProject=((BaseTransactionInfo)objectValue).getSellProject();
				String state=null;
				Map uiContextMap=new HashMap();
				if(editData.getNewId()==null){
					getCustomerList(objectValue,this.customer);
					uiContextMap.put("sellProject", SHEManageHelper.getParentSellProject(null, sellProject));
					uiContextMap.put("changeRoomCustomer",this.customer);
					uiContextMap.put("customerNames",((BaseTransactionInfo)objectValue).getCustomerNames());
					uiContextMap.put("customerPhone",((BaseTransactionInfo)objectValue).getCustomerPhone());
					uiContextMap.put("canSelectCustomer",new Boolean(false));
					uiContextMap.put("changeSaleMan",getSaleManList(objectValue));
					state=OprtState.ADDNEW;
				}else{
					uiContextMap.put("ID", editData.getNewId());
					uiContextMap.put("canSelectCustomer",new Boolean(false));
					if(OprtState.EDIT.equals(getOprtState())){
						state=OprtState.EDIT;
					}
				}
				if(objectValue instanceof PrePurchaseManageInfo){
					if(editData.getNewId()==null){
						uiContextMap.put("saleManNames",((PrePurchaseManageInfo)objectValue).getSaleManNames());
					}
					initUI(uiContextMap,this.panelNewBill,PrePurchaseManageEditUI.class.getName(),state);
				}
				if(objectValue instanceof PurchaseManageInfo){
					if(editData.getNewId()==null){
						uiContextMap.put("saleManNames",((PurchaseManageInfo)objectValue).getSaleManNames());
					}
					initUI(uiContextMap,this.panelNewBill,PurchaseManageEditUI.class.getName(),state);
				}
				if(objectValue instanceof SignManageInfo){
					if(editData.getNewId()==null){
						uiContextMap.put("saleManNames",((SignManageInfo)objectValue).getSaleManNames());
					}
					initUI(uiContextMap,this.panelNewBill,SignManageEditUI.class.getName(),state);
				}
			} catch (BOSException e) {
				logger.error(e.getMessage());
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		}
	}
	protected void initSourceBillPanel(){
		if(objectValue!=null){
			try {
				Map uiContextMap=new HashMap();
				uiContextMap.put("ID", editData.getSrcId());
				if(objectValue instanceof PrePurchaseManageInfo){
					initUI(uiContextMap,this.panelSourceBill,PrePurchaseManageEditUI.class.getName(),OprtState.VIEW);
				}
				if(objectValue instanceof PurchaseManageInfo){
					initUI(uiContextMap,this.panelSourceBill,PurchaseManageEditUI.class.getName(),OprtState.VIEW);
				}
				if(objectValue instanceof SignManageInfo){
					initUI(uiContextMap,this.panelSourceBill,SignManageEditUI.class.getName(),OprtState.VIEW);
				}
			} catch (BOSException e) {
				logger.error(e.getMessage());
			}
		}
	}
	protected void initControl(){
		if(!isSaleHouseOrg){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		
		this.txtNumber.setMaxLength(80);
		this.txtRemark.setMaxLength(255);

		this.txtFitmentAmount.setSupportedEmpty(true);
		
		this.txtFeeAmount.setNegatived(false);
		this.txtQRFeeAmount.setNegatived(false);
		this.txtCRFeeAmount.setNegatived(false);
		
		if(editData!=null&&!FDCBillStateEnum.SAVED.equals(editData.getState())){
			if(this.f7Room.getValue()!=null){
				this.f7Room.setEnabled(false);
			}
			if(this.comboBizType.getSelectedItem()!=null){
				this.comboBizType.setEnabled(false);
			}
			if(this.f7MoneyDefine.getValue()!=null){
				this.f7MoneyDefine.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.txtFeeAmount.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.f7MoneyDefine.setEnabled(false);
				this.txtFeeAmount.setEnabled(false);
			}
			if(this.f7QRMoneyDefine.getValue()!=null){
				this.f7QRMoneyDefine.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.txtQRFeeAmount.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.txtQuitAmount.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.f7QRMoneyDefine.setEnabled(false);
				this.txtQRFeeAmount.setEnabled(false);
				this.txtQuitAmount.setEnabled(false);
			}
			if(this.f7CRMoneyDefine.getValue()!=null){
				this.f7CRMoneyDefine.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.txtCRFeeAmount.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.f7CRMoneyDefine.setEnabled(false);
				this.txtCRFeeAmount.setEnabled(false);
			}
		}
		
		//防止房间状态问题
		this.btnAddAPLine.setVisible(false);
		this.btnRemoveAPLine.setVisible(false);
		this.tblAttachProperty.getColumn(AP_ROOMNUMBER).getStyleAttributes().setLocked(true);
		
//		RoomDisplaySetting set = new RoomDisplaySetting(null,SHEParamConstant.PROJECT_SET_MAP);
		Map detailSet = RoomDisplaySetting.getNewProjectSet(null,editData.getSellProject().getId().toString());
		if(detailSet!=null){
			isDealAmountEdit = ((Boolean)detailSet.get(SHEParamConstant.T2_IS_DEAL_AMOUNT_EDIT)).booleanValue();
			isBizDateEdit=((Boolean)detailSet.get(SHEParamConstant.T2_IS_BIZ_DATE_EDITABLE)).booleanValue();
			cnBizAdscriptionDate=(BizDateToEnum) detailSet.get(SHEParamConstant.T2_CHANGE_CUS_DEF_BIZ_DATE);
			pcBizAdscriptionDate=(BizDateToEnum) detailSet.get(SHEParamConstant.T2_PRICE_ADJUST_DEF_BIZ_DATE);
			crBizAdscriptionDate=(BizDateToEnum) detailSet.get(SHEParamConstant.T2_CHANGE_ROOM_DEF_BIZ_DATE);
			isUseAgioScheme=((Boolean)detailSet.get(SHEParamConstant.T2_IS_ENABLE_AGIO)).booleanValue();
			isAgioListCanEdit=((Boolean)detailSet.get(SHEParamConstant.T2_IS_ENABLE_ADJUST_AGIO)).booleanValue();
		}
		SHEFunction();
		setTxtFormate();
		
		this.details.setMaxLength(2000);
		if(this.comboBizType.getSelectedItem()==null&&this.f7Room.getValue()==null){
			this.f7Room.setEnabled(false);
		}
		this.prmtCreator.setDisplayFormat("$person.name$");
	}
	protected void setTxtFormate(){
		HashMap value = SHEManageHelper.getCRMConstants(SysContext.getSysContext().getCurrentOrgUnit().getId());
		BaseTransactionInfo info=(BaseTransactionInfo)editData;
		if(SHEManageHelper.setPrecision(info.getPriceDigit())==null){
			priceDigit=SHEManageHelper.setPrecision(value.get(CRMConstants.FDCSHE_PARAM_PRICE_BIT).toString()).intValue();
		}else{
			priceDigit=SHEManageHelper.setPrecision(info.getPriceDigit()).intValue();
		}
		if(SHEManageHelper.setPrecision(info.getDigit())==null){
			digit=SHEManageHelper.setPrecision(value.get(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT).toString()).intValue();
		}else{
			digit=SHEManageHelper.setPrecision(info.getDigit()).intValue();
		}
		if(SHEManageHelper.setRoundingMode(info.getToIntegerType())==null){
			toIntegerType=SHEManageHelper.setRoundingMode(value.get(CRMConstants.FDCSHE_PARAM_TOL_TOINTEGER_TYPE).toString()).intValue();
		}else{
			toIntegerType=SHEManageHelper.setRoundingMode(info.getToIntegerType()).intValue();
		}
		if(SHEManageHelper.setRoundingMode(info.getPriceToIntegerType())==null){
			priceToIntegerType=SHEManageHelper.setRoundingMode(value.get(CRMConstants.FDCSHE_PARAM_PRICE_TOINTEGER_TYPE).toString()).intValue();
		}else{
			priceToIntegerType=SHEManageHelper.setRoundingMode(info.getPriceToIntegerType()).intValue();
		}
		
		this.txtDealTotalAmount.setPrecision(digit);
		this.txtDealTotalAmount.setRoundingMode(toIntegerType);
		
		this.txtSellAmount.setPrecision(digit);
		this.txtSellAmount.setRoundingMode(toIntegerType);
		
		this.txtContractTotalAmount.setPrecision(digit);
		this.txtContractTotalAmount.setRoundingMode(toIntegerType);
		
		this.txtStandardTotalAmount.setPrecision(digit);
		this.txtStandardTotalAmount.setRoundingMode(toIntegerType);
		
		this.txtAttachPropertyTotalAmount.setPrecision(digit);
		this.txtAttachPropertyTotalAmount.setRoundingMode(toIntegerType);
		
		this.txtFitmentAmount1.setPrecision(digit);
		this.txtFitmentAmount1.setRoundingMode(toIntegerType);
		
		this.txtFitmentAmount.setPrecision(digit);
		this.txtFitmentAmount.setRoundingMode(toIntegerType);
		
		this.txtLoanAmount.setPrecision(digit);
		this.txtLoanAmount.setRoundingMode(toIntegerType);
		
		this.txtAFundAmount.setPrecision(digit);
		this.txtAFundAmount.setRoundingMode(toIntegerType);
		
		
		this.txtFeeAmount.setPrecision(digit);
		this.txtFeeAmount.setRoundingMode(toIntegerType);
		
		this.txtActAmount.setPrecision(digit);
		this.txtActAmount.setRoundingMode(toIntegerType);
		
		this.txtCRFeeAmount.setPrecision(digit);
		this.txtCRFeeAmount.setRoundingMode(toIntegerType);
		
		this.txtCRActAmount.setPrecision(digit);
		this.txtCRActAmount.setRoundingMode(toIntegerType);
		
		this.txtQRFeeAmount.setPrecision(digit);
		this.txtQRFeeAmount.setRoundingMode(toIntegerType);
		
		this.txtQRActAmount.setPrecision(digit);
		this.txtQRActAmount.setRoundingMode(toIntegerType);
		
		this.txtQuitAmount.setPrecision(digit);
		this.txtQuitAmount.setRoundingMode(toIntegerType);
		
		this.txtDealBuildPrice.setPrecision(priceDigit);
		this.txtDealBuildPrice.setRoundingMode(priceToIntegerType);
		
		this.txtDealRoomPrice.setPrecision(priceDigit);
		this.txtDealRoomPrice.setRoundingMode(priceToIntegerType);
		
		this.txtContractBuildPrice.setPrecision(priceDigit);
		this.txtContractBuildPrice.setRoundingMode(priceToIntegerType);
		
		this.txtContractRoomPrice.setPrecision(priceDigit);
		this.txtContractRoomPrice.setRoundingMode(priceToIntegerType);
		
		this.txtRoomPrice.setPrecision(priceDigit);
		this.txtRoomPrice.setRoundingMode(priceToIntegerType);
		
		this.txtBuildingPrice.setPrecision(priceDigit);
		this.txtBuildingPrice.setRoundingMode(priceToIntegerType);
		
		this.txtFitmentPrice.setPrecision(priceDigit);
		this.txtFitmentPrice.setRoundingMode(priceToIntegerType);
		
		this.txtPlanningCompensate.setPrecision(digit);
		this.txtPlanningCompensate.setRoundingMode(toIntegerType);
		
		this.txtCashSalesCompensate.setPrecision(digit);
		this.txtCashSalesCompensate.setRoundingMode(toIntegerType);
		
		this.txtAreaCompensate.setPrecision(digit);
		this.txtAreaCompensate.setRoundingMode(toIntegerType);
		
		this.tblPCPayList.getColumn(PL_APPAMOUNT).setEditor(SHEManageHelper.getNumberCellEditor(2,toIntegerType));
		this.tblPCPayList.getColumn(PL_ACTAMOUNT).setEditor(SHEManageHelper.getNumberCellEditor(2,toIntegerType));
		this.tblPCPayList.getColumn(PL_BALANCE).setEditor(SHEManageHelper.getNumberCellEditor(2,toIntegerType));
		
		this.tblAttachProperty.getColumn(AP_STANDARDTOTALAMOUNT).setEditor(SHEManageHelper.getNumberCellEditor(digit,toIntegerType));
		this.tblAttachProperty.getColumn(AP_MERGEAMOUNT).setEditor(SHEManageHelper.getNumberCellEditor(digit,toIntegerType));
		this.tblAttachProperty.getColumn(AP_ROOMPRICE).setEditor(SHEManageHelper.getNumberCellEditor(priceDigit,priceToIntegerType));
		this.tblAttachProperty.getColumn(AP_BUILDINGPRICE).setEditor(SHEManageHelper.getNumberCellEditor(priceDigit,priceToIntegerType));
		
		this.tblPayList.getColumn(PL_APPAMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblPayList.getColumn(PL_ACTAMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblPayList.getColumn(PL_BALANCE).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblAttachProperty.getColumn(AP_STANDARDTOTALAMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(digit));
		this.tblAttachProperty.getColumn(AP_MERGEAMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(digit));
		this.tblAttachProperty.getColumn(AP_ROOMPRICE).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(priceDigit));
		this.tblAttachProperty.getColumn(AP_BUILDINGPRICE).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(priceDigit));
	}
	protected void setTxtFormateNumerValue(){
		editData.setDealTotalAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtDealTotalAmount.getBigDecimalValue()));
		editData.setContractTotalAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtContractTotalAmount.getBigDecimalValue()));
		editData.setStrdTotalAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtStandardTotalAmount.getBigDecimalValue()));
		editData.setAttachmentAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtAttachPropertyTotalAmount.getBigDecimalValue()));
		editData.setFitmentTotalAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtFitmentAmount.getBigDecimalValue()));
		editData.setLoanAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtLoanAmount.getBigDecimalValue()));
		editData.setAccFundAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtAFundAmount.getBigDecimalValue()));
		
		editData.setDealBuildPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtDealBuildPrice.getBigDecimalValue()));
		editData.setDealRoomPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtDealRoomPrice.getBigDecimalValue()));
		editData.setContractBuildPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtContractBuildPrice.getBigDecimalValue()));
		editData.setContractRoomPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtContractRoomPrice.getBigDecimalValue()));
		editData.setStrdRoomPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtRoomPrice.getBigDecimalValue()));
		editData.setStrdBuildingPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtBuildingPrice.getBigDecimalValue()));
		editData.setFitmentPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtFitmentPrice.getBigDecimalValue()));
		editData.setSellAmount(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtSellAmount.getBigDecimalValue()));
		editData.setPlanningCompensate(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtPlanningCompensate.getBigDecimalValue()));
		editData.setCashSalesCompensate(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtCashSalesCompensate.getBigDecimalValue()));
		editData.setAreaCompensate(SHEManageHelper.setScale(priceDigit, priceToIntegerType,this.txtAreaCompensate.getBigDecimalValue()));
	}
	private void initTblPayList(KDTable table) {
		table.checkParsed();
		
		table.getColumn(PL_APPAMOUNT).setEditor(FDCClientHelper.getNumberCellEditor());
		table.getColumn(PL_APPAMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn(PL_APPAMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		table.getColumn(PL_ACTAMOUNT).setEditor(FDCClientHelper.getNumberCellEditor());
		table.getColumn(PL_ACTAMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn(PL_ACTAMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		table.getColumn(PL_BALANCE).setEditor(FDCClientHelper.getNumberCellEditor());
		table.getColumn(PL_BALANCE).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn(PL_BALANCE).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		KDBizPromptBox f7Box = new KDBizPromptBox(); 
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CurrencyQuery");
		f7Editor = new KDTDefaultCellEditor(f7Box);
		table.getColumn(PL_CURRENCY).setEditor(f7Editor);
		
		KDTextField stateTextField = new KDTextField();
		stateTextField.setMaxLength(80);
		KDTDefaultCellEditor txtStateEditor = new KDTDefaultCellEditor(stateTextField);
		table.getColumn(PL_STATE).setEditor(txtStateEditor);
		table.getColumn(PL_STATE).getStyleAttributes().setHided(true);
		
		String formatString = "yyyy-MM-dd";
		table.getColumn(PL_APPDATE).getStyleAttributes().setNumberFormat(formatString);
		
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		table.getColumn(PL_APPDATE).setEditor(dateEditor);
		
		f7Box = new KDBizPromptBox();
		f7Box.setDisplayFormat("$name$");
		f7Box.setDisplayFormat("$number$");
		f7Box.setDisplayFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("number", SHEManageHelper.RoomAttachMoneyDefineNum,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("number", SHEManageHelper.FittmentMoneyDefineNum,CompareType.NOTEQUALS));
		
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.EARNESTMONEY_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.FISRTAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.HOUSEAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.LOANAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.ACCFUNDAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.ELSEAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.REPLACEFEE_VALUE));
		
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.PRECONCERTMONEY_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.REFUNDMENT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.FITMENTAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.LATEFEE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.COMPENSATEAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.COMMISSIONCHARGE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.SINPUR_VALUE));
		filter.setMaskString("#0 and #1 and #2 or (#3 or #4 or #5 or #6 or #7 or #8 or #9 or #10 or #11 or #12 or #13 or #14 or #15 or # 16)");
		
		view.setFilter(filter);
		f7Box.setEntityViewInfo(view);
		f7Editor = new KDTDefaultCellEditor(f7Box);
		table.getColumn(PL_MONEYNAME).setEditor(f7Editor);
		
		
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn(PL_DES).setEditor(txtEditor);
		
		table.getColumn(PL_MONEYNAME).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		table.getColumn(PL_APPAMOUNT).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		table.getColumn(PL_APPDATE).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		table.getColumn(PL_ACTAMOUNT).getStyleAttributes().setLocked(true);
		table.getColumn(PL_BALANCE).getStyleAttributes().setLocked(true);
		table.getColumn(PL_STATE).getStyleAttributes().setLocked(true);
	}
	private void initTblAttachProperty() {
		this.tblAttachProperty.checkParsed();
	
		//设置是否并入合同金额
		final KDCheckBox isMergeTocontractCheckBox = new KDCheckBox();
		isMergeTocontractCheckBox.setSelected(false);
		tblAttachProperty.getColumn(AP_ISMERGETOCONTRACT).setEditor(new KDTDefaultCellEditor(isMergeTocontractCheckBox));
		tblAttachProperty.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);	
		
		this.tblAttachProperty.getColumn(AP_BUILDINGAREA).setEditor(FDCClientHelper.getNumberCellEditor());
		this.tblAttachProperty.getColumn(AP_BUILDINGAREA).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblAttachProperty.getColumn(AP_BUILDINGAREA).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblAttachProperty.getColumn(AP_ROOMAREA).setEditor(FDCClientHelper.getNumberCellEditor());
		this.tblAttachProperty.getColumn(AP_ROOMAREA).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblAttachProperty.getColumn(AP_ROOMAREA).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblAttachProperty.getColumn(AP_ROOMPRICE).setEditor(FDCClientHelper.getNumberCellEditor());
		this.tblAttachProperty.getColumn(AP_ROOMPRICE).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblAttachProperty.getColumn(AP_ROOMPRICE).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblAttachProperty.getColumn(AP_BUILDINGPRICE).setEditor(FDCClientHelper.getNumberCellEditor());
		this.tblAttachProperty.getColumn(AP_BUILDINGPRICE).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblAttachProperty.getColumn(AP_BUILDINGPRICE).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblAttachProperty.getColumn(AP_STANDARDTOTALAMOUNT).setEditor(FDCClientHelper.getNumberCellEditor());
		this.tblAttachProperty.getColumn(AP_STANDARDTOTALAMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblAttachProperty.getColumn(AP_STANDARDTOTALAMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblAttachProperty.getColumn(AP_MERGEAMOUNT).setEditor(FDCClientHelper.getNumberCellEditor());
		this.tblAttachProperty.getColumn(AP_MERGEAMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblAttachProperty.getColumn(AP_MERGEAMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblAttachProperty.getColumn(AP_AGIO).getStyleAttributes().setLocked(true);
		this.tblAttachProperty.getColumn(AP_AGIO).setRenderer(new ObjectValueRender(){
			public String getText(Object obj)
			{
				if(obj instanceof Object[]){
					
					return getAgioDes((Object[])obj);
				}
				return null;
			}
		});
		this.tblAttachProperty.getColumn(AP_BUILDINGAREA).getStyleAttributes().setLocked(true);
		this.tblAttachProperty.getColumn(AP_ROOMAREA).getStyleAttributes().setLocked(true);
		this.tblAttachProperty.getColumn(AP_ROOMPRICE).getStyleAttributes().setLocked(true);
		this.tblAttachProperty.getColumn(AP_BUILDINGPRICE).getStyleAttributes().setLocked(true);
		this.tblAttachProperty.getColumn(AP_STANDARDTOTALAMOUNT).getStyleAttributes().setLocked(true);
	}
	private BaseTransactionEditUI getNewBillUI(KDScrollPane panel){
		if(panel.getViewport().getComponentCount()==0)
			return null;
		else
			return (BaseTransactionEditUI)panel.getViewport().getComponent(0);		
	}
	protected void labCustomer1_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this,(SHECustomerInfo)labCustomer1.getUserObject());
		}
	}
	protected void labCustomer2_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this,(SHECustomerInfo)labCustomer2.getUserObject());
		}
	}
	protected void labCustomer3_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this,(SHECustomerInfo)labCustomer3.getUserObject());
		}
	}
	
	protected void labCustomer4_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this,(SHECustomerInfo)labCustomer4.getUserObject());
		}
	}

	protected void labCustomer5_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this,(SHECustomerInfo)labCustomer5.getUserObject());
		}
	}
	protected void labSourceCustomer1_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this,(SHECustomerInfo)labSourceCustomer1.getUserObject());
		}
	}
	protected void labSourceCustomer2_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this,(SHECustomerInfo)labSourceCustomer2.getUserObject());
		}
	}
	protected void labSourceCustomer3_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this,(SHECustomerInfo)labSourceCustomer3.getUserObject());
		}
	}
	protected void labSourceCustomer4_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this,(SHECustomerInfo)labSourceCustomer4.getUserObject());
		}
	}

	protected void labSourceCustomer5_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this,(SHECustomerInfo)labSourceCustomer5.getUserObject());
		}
	}
	public void actionSelectCustomer_actionPerformed(ActionEvent e) throws Exception {
		if(this.f7Room.getValue()==null){
			MsgBox.showWarning(this,"请先选择房间！");
			return;
		}
		this.customer =SHEManageHelper.customerSelect(this,this.customer,editData.getSellProject(),true,true);
		this.storeCustomerEntry();
		this.loadCustomerEntry(editData);
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		}
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		}
		super.actionEdit_actionPerformed(e);
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		
		setSaveAction(false);
		if(!checkCanSubmit()){
			MsgBox.showWarning(this,"单据状态已经在审批中或者已审批，不能再提交！");
			SysUtil.abort();
		}
		if(ChangeBizTypeEnum.CHANGEROOM.equals(comboBizType.getSelectedItem())){
			verifyInputForSubmint();
			
			BaseTransactionEditUI ui=this.getNewBillUI(this.panelNewBill);
			BaseTransactionInfo baseInfo=(BaseTransactionInfo)ui.getEditData();
			if(ui!=null&&objectValue!=null){
				BOSUuid newBillId=null;
				if(editData.getNewId()!=null){
					newBillId=editData.getNewId();
				}else{
					newBillId=BOSUuid.create(objectValue.getBOSType());
				}
				baseInfo.setId(newBillId);
				baseInfo.setBizState(TransactionStateEnum.CHANGEROOMAUDITING);
				ui.verifyInputForSubmint();
				ui.storeFields();
				
				if(editData.getNewId()!=null){
					ui.getBillInterface().update(new ObjectUuidPK(newBillId), ui.getEditData());
				}else{
					handleIntermitNumber(ui.getEditData());
					ui.getBillInterface().addnew(ui.getEditData());
				}
				editData.setNewId(newBillId);
				editData.setRoom(baseInfo.getRoom());
			}
        }
		((FDCBillInfo)editData).setState(FDCBillStateEnum.SUBMITTED);
		super.actionSubmit_actionPerformed(e);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if(ChangeBizTypeEnum.CHANGEROOM.equals((ChangeBizTypeEnum)comboBizType.getSelectedItem())){
			FDCMsgBox.showInfo("换房业务请直接进行提交操作！");
			return;
		}
		((FDCBillInfo)editData).setState(FDCBillStateEnum.SAVED);
		super.actionSave_actionPerformed(e);
	}
	protected void verifyInputForSubmint() throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkChangeDate);
		FDCClientVerifyHelper.verifyEmpty(this, this.f7Room);
		FDCClientVerifyHelper.verifyEmpty(this, this.comboBizType);
		FDCClientVerifyHelper.verifyEmpty(this, this.f7ChangeReson);
		FDCClientVerifyHelper.verifyEmpty(this, this.f7ChangeReson);
		FDCClientVerifyHelper.verifyEmpty(this, details);
		
		if(ChangeBizTypeEnum.CHANGENAME.equals(this.comboBizType.getSelectedItem())){
			FDCClientVerifyHelper.verifyEmpty(this, this.pkCNBizAdscriptionDate);
			if(this.f7MoneyDefine.getValue()!=null){
				this.txtFeeAmount.setRequired(true);
				FDCClientVerifyHelper.verifyEmptyAndNoZero(this, this.txtFeeAmount);
			}
			if(this.txtFeeAmount.getBigDecimalValue()!=null&&this.txtFeeAmount.getBigDecimalValue().compareTo(FDCHelper.ZERO)!=0){
				this.f7MoneyDefine.setRequired(true);
				FDCClientVerifyHelper.verifyEmpty(this, this.f7MoneyDefine);
			}
			verifyCustomer();
		}
		if(ChangeBizTypeEnum.CHANGEROOM.equals(this.comboBizType.getSelectedItem())){
			if(this.f7CRMoneyDefine.getValue()!=null){
				this.txtCRFeeAmount.setRequired(true);
				FDCClientVerifyHelper.verifyEmpty(this, this.txtCRFeeAmount);
			}
			if(this.txtCRFeeAmount.getBigDecimalValue()!=null&&this.txtCRFeeAmount.getBigDecimalValue().compareTo(FDCHelper.ZERO)!=0){
				this.f7CRMoneyDefine.setRequired(true);
				FDCClientVerifyHelper.verifyEmpty(this, this.f7CRMoneyDefine);
			}
		}
		if(ChangeBizTypeEnum.QUITROOM.equals(this.comboBizType.getSelectedItem())){
			if(this.f7QRMoneyDefine.getValue()!=null){
				this.txtQRFeeAmount.setRequired(true);
				FDCClientVerifyHelper.verifyEmpty(this, this.txtQRFeeAmount);
			}
			if(this.txtQRFeeAmount.getBigDecimalValue()!=null&&this.txtQRFeeAmount.getBigDecimalValue().compareTo(FDCHelper.ZERO)!=0){
				this.f7QRMoneyDefine.setRequired(true);
				FDCClientVerifyHelper.verifyEmpty(this, this.f7QRMoneyDefine);
			}
		}
		if(ChangeBizTypeEnum.PRICECHANGE.equals(this.comboBizType.getSelectedItem())){
			FDCClientVerifyHelper.verifyEmpty(this, this.pkBizAdscriptionDate);
			if(this.objectValue instanceof SignManageInfo){
				FDCClientVerifyHelper.verifyEmpty(this, this.f7PayType);
			}
			FDCClientVerifyHelper.verifyEmpty(this, this.txtDealTotalAmount);
			if(this.chkIsFitmentToContract.isSelected()){
				FDCClientVerifyHelper.verifyEmptyAndNoZero(this, this.txtFitmentAmount);
			}
			if(editData.isIsBasePriceSell()){
				RoomInfo room = (RoomInfo) this.f7Room.getValue();
				if(room.getBaseStandardPrice()==null){
					FDCMsgBox.showWarning(this," 已启用强制底价控制参数，该房间总价底价不存在，请检查！");
					SysUtil.abort();
				}
				if(this.txtDealTotalAmount.getBigDecimalValue().setScale(digit, toIntegerType).compareTo(room.getBaseStandardPrice().setScale(digit, toIntegerType))<0){
					FDCMsgBox.showWarning(this,"成交总价不能低于房间总价底价！("+this.txtDealTotalAmount.getBigDecimalValue().setScale(digit, toIntegerType)+"<"+room.getBaseStandardPrice().setScale(digit, toIntegerType)+")");
					SysUtil.abort();
				}
			}
			verifyAttachPropertyTab();
			verifyPayListTab();
		}
		
		if(STATUS_ADDNEW.equals(this.getOprtState())){
			this.verifyAddNewRoom((RoomInfo)this.f7Room.getValue(), null);
		}
		if(veriftExistsSameRoomBill((RoomInfo)this.f7Room.getValue())){
			FDCMsgBox.showWarning(this,"已存在该房间变更单据！");
			SysUtil.abort();
		}
	}
	protected boolean veriftExistsSameRoomBill(RoomInfo room) throws Exception{
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId()));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED_VALUE));
		
		if(editData!=null&&editData.getId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getId(),CompareType.NOTEQUALS));
		}
		return getBizInterface().exists(filter);
	}
	protected void verifyCustomer(){
		if(editData.getCustomerEntry().size()==0){
			FDCMsgBox.showWarning(this,"客户不能为空！");
			SysUtil.abort();
		}
	}
	protected void verifyAttachPropertyTab() throws BOSException {
    	for (int i = 0; i < this.tblAttachProperty.getRowCount(); i++) {
    		IRow row = this.tblAttachProperty.getRow(i);
    		if (row.getCell(AP_ROOMNUMBER).getValue()==null) 
    		{
    			FDCMsgBox.showWarning(this,"附属房产信息房间不能为空！");
				this.tblAttachProperty.getEditManager().editCellAt(row.getRowIndex(), this.tblPayList.getColumnIndex(PL_MONEYNAME));
				SysUtil.abort();
    		}
    		Boolean isOk = new Boolean(row.getCell(AP_ISMERGETOCONTRACT).getValue().toString()); 
    		if(isOk.booleanValue()){
    			BigDecimal amount=(BigDecimal)row.getCell(AP_MERGEAMOUNT).getValue();
				if (amount==null){
					FDCMsgBox.showWarning(this,"附属房产信息并入金额不能为空！");
					this.tblAttachProperty.getEditManager().editCellAt(row.getRowIndex(), this.tblPayList.getColumnIndex(AP_MERGEAMOUNT));
					SysUtil.abort();
				}
				if (amount.compareTo(FDCHelper.ZERO)==0){
					FDCMsgBox.showWarning(this,"附属房产信息并入金额不能为0！");
					this.tblAttachProperty.getEditManager().editCellAt(row.getRowIndex(), this.tblPayList.getColumnIndex(AP_MERGEAMOUNT));
					SysUtil.abort();
				}
    		}
    	}
    }
    protected void verifyPayListTab() throws BOSException {
    	if(this.tblPCPayList.getRowCount()==0){
			FDCMsgBox.showWarning(this,"客户付款明细款不能为空！");
			SysUtil.abort();
		}
		boolean isEarnestInHouseAmount = ((BaseTransactionInfo)this.editData).isIsEarnestInHouseAmount();
		BigDecimal contractAmount = this.txtContractTotalAmount.getBigDecimalValue().setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal totalAmount = FDCHelper.ZERO;
		for (int i = 0; i < this.tblPCPayList.getRowCount(); i++) {
			IRow row = this.tblPCPayList.getRow(i);
				
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell(PL_MONEYNAME).getValue();
			if (moneyName == null) {
				FDCMsgBox.showWarning(this,"客户付款明细款项名称不能为空！");
				this.tblPCPayList.getEditManager().editCellAt(row.getRowIndex(), this.tblPCPayList.getColumnIndex(PL_MONEYNAME));
				SysUtil.abort();
			} 
			BigDecimal amount = (BigDecimal)row.getCell(PL_APPAMOUNT).getValue();
			if (amount == null){
				FDCMsgBox.showWarning(this,"客户付款明细应收金额不能为空！");
				this.tblPCPayList.getEditManager().editCellAt(row.getRowIndex(), this.tblPCPayList.getColumnIndex(PL_APPAMOUNT));
				SysUtil.abort();
			}
			if (amount.compareTo(FDCHelper.ZERO)==0){
				FDCMsgBox.showWarning(this,"客户付款明细应收金额不能为0！");
				this.tblPCPayList.getEditManager().editCellAt(row.getRowIndex(), this.tblPCPayList.getColumnIndex(PL_APPAMOUNT));
				SysUtil.abort();
			}
			Date date = (Date) row.getCell(PL_APPDATE).getValue();
			if (date == null) {
				if(!MoneyTypeEnum.BreachFee.equals(moneyName.getMoneyType())){
					FDCMsgBox.showWarning(this,"客户付款明细应收日期不能为空！");
					this.tblPCPayList.getEditManager().editCellAt(row.getRowIndex(), this.tblPCPayList.getColumnIndex(PL_APPDATE));
					SysUtil.abort();	
				}
			}
			if(SHEManageHelper.isMergerToContractMoneyType(moneyName.getMoneyType(),isEarnestInHouseAmount)){
				totalAmount = totalAmount.add((BigDecimal)amount);
			}
		}
		if (contractAmount == null) {
			contractAmount = FDCHelper.ZERO;
		}
		if (this.f7PayType.getValue()!=null&&totalAmount.setScale(digit, toIntegerType).compareTo(contractAmount.setScale(digit, toIntegerType)) != 0) {
			FDCMsgBox.showWarning("客户付款明细总额不等于合同总价！("+totalAmount.setScale(digit, toIntegerType)+"!="+contractAmount.setScale(digit, toIntegerType)+")");
			SysUtil.abort();
		}
	}
	private boolean checkIsHasBankPayment(String id) throws EASBizException, BOSException{
		if(id==null){
			return false;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("signManager.id", id));
		return BankPaymentEntryFactory.getRemoteInstance().exists(filter);
	}
	protected void setRoomNull(RoomInfo oldRoom,String warning){
		FDCMsgBox.showWarning(this,warning);
		this.f7Room.setValue(oldRoom);
		objectValue=oldObjectValue;
		SysUtil.abort();
	}
	protected void verifyAddNewAttachRoom(RoomInfo room,IRow row) throws EASBizException, BOSException{
		if (room.getStandardTotalAmount() == null ) {
			FDCMsgBox.showWarning(this,"房间尚未定价！");
			clearTblAttach(row);
			SysUtil.abort();
		}
		if (HousePropertyEnum.NoAttachment.equals(room.getHouseProperty())) {
			FDCMsgBox.showWarning(this,"不能绑定非附属房产！");
			clearTblAttach(row);
			SysUtil.abort();
		}
		if(!room.isIsForSHE()){
			FDCMsgBox.showWarning(this,"不能绑定非售楼属性的附属房产！");
			clearTblAttach(row);
			SysUtil.abort();
		}
		if(!RoomSellStateEnum.OnShow.equals(room.getSellState())){
			FDCMsgBox.showWarning(this,"不能绑定非待售的附属房产！");
			clearTblAttach(row);
			SysUtil.abort();
		}
		for (int i = 0; i < this.tblAttachProperty.getRowCount(); i++) {
			if(i==row.getRowIndex()) continue;
			if(this.tblAttachProperty.getRow(i).getCell(AP_ROOMNUMBER).getValue()==null) continue;
			RoomInfo aRoom = (RoomInfo) tblAttachProperty.getRow(i).getCell(AP_ROOMNUMBER).getValue();
			if (aRoom.getId().toString().equals(room.getId().toString())) {
				FDCMsgBox.showWarning(this,"该房间已经绑定！");
				clearTblAttach(row);
				SysUtil.abort();
			}
		}
		FilterInfo filter = new FilterInfo();
		
		if(editData.getSrcId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("head.id", editData.getSrcId(),CompareType.NOTEQUALS));
		}
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId()));
		filter.getFilterItems().add(new FilterItemInfo("head.isValid", new Boolean(false)));
		filter.getFilterItems().add(new FilterItemInfo("head.bizState", TransactionStateEnum.PCNULLIFY,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("head.bizState", TransactionStateEnum.CNNULLIFY,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("head.bizState", TransactionStateEnum.CRNULLIFY,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("head.bizState", TransactionStateEnum.QRNULLIFY,CompareType.NOTEQUALS));
		
		if (SignRoomAttachmentEntryFactory.getRemoteInstance().exists(filter)
				||PurRoomAttachmentEntryFactory.getRemoteInstance().exists(filter)||
					PrePurchaseRoomAttachmentEntryFactory.getRemoteInstance().exists(filter)) {
			FDCMsgBox.showWarning(this,"该房间已经被其他房间绑定！");
			clearTblAttach(row);
			SysUtil.abort();
		}
		
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId()));
		filter.getFilterItems().add(new FilterItemInfo("head.id", ((RoomInfo)this.f7Room.getValue()).getId(),CompareType.NOTEQUALS));
		
		if(RoomAttachmentEntryFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(this,"该房间已经被其他房间绑定！");
			clearTblAttach(row);
			SysUtil.abort();
		}
	}
	protected void verifyAddNewRoom(RoomInfo room,RoomInfo oldRoom) throws BOSException, EASBizException{
		objectValue=SHEManageHelper.getCurTransactionBill(room.getId());
		if(objectValue!=null){
			if(objectValue instanceof SignManageInfo){
				if(ChangeBizTypeEnum.CHANGENAME.equals(comboBizType.getSelectedItem())){
					setRoomNull(oldRoom,"该房间已经签约，不适合做更名业务！");
				}
				if(checkIsHasBankPayment(((BaseTransactionInfo)objectValue).getId().toString())){
					setRoomNull(oldRoom,"该房间存在银行放款，不适合做变更业务！");
				}
				if(comboBizType.getSelectedItem()==null){
					setRoomNull(oldRoom,"请先选择业务类型！");
				}
				if(((SignManageInfo)objectValue).getAreaCompensate()!=null&&
						((SignManageInfo)objectValue).getAreaCompensate().compareTo(FDCHelper.ZERO)>0&&!ChangeBizTypeEnum.QUITROOM.equals(comboBizType.getSelectedItem())){
					setRoomNull(oldRoom,"该房间存在面积补差，不适合做变更业务！");
				}
				if(((SignManageInfo)objectValue).getSpecialAgio()!=null&&!ChangeBizTypeEnum.QUITROOM.equals(comboBizType.getSelectedItem())){
					setRoomNull(oldRoom,"该房间存在特殊折扣，不适合做变更业务！");
				}
			}
			TransactionStateEnum state=((BaseTransactionInfo)objectValue).getBizState();
			if(!(TransactionStateEnum.PURAUDIT.equals(state)||TransactionStateEnum.PREAUDIT.equals(state)||TransactionStateEnum.SIGNAUDIT.equals(state))){
				setRoomNull(oldRoom,"该房间业务单据不是审批状态，不适合做变更业务！");
			}
		}else{
			setRoomNull(oldRoom,"该房间无业务单据，不适合做变更业务！");
		}
	}
	protected void txtDealTotalAmount_dataChanged(DataChangeEvent e) throws Exception {
		if(isDealAmountEdit&&isEditDealAmount){
			RoomInfo roomInfo = (RoomInfo) this.f7Room.getValue();
			if(roomInfo==null) return;
			if(e.getOldValue().equals(e.getNewValue())) return;
			boolean isHasAgio=false;
			AgioEntryInfo agioEntryInfo=null;
			AgioEntryCollection agioCol=((AgioEntryCollection)this.txtAgio.getUserObject());
			
			AgioParam oldAgioParam=new AgioParam();
			oldAgioParam.setBasePriceSell(this.currAgioParam.isBasePriceSell());
			oldAgioParam.setDigit(this.currAgioParam.getDigit());
			oldAgioParam.setPriceAccountType(this.currAgioParam.getPriceAccountType());
			oldAgioParam.setToInteger(this.currAgioParam.isToInteger());
			oldAgioParam.setToIntegerType(this.currAgioParam.getToIntegerType());
			
			AgioEntryCollection oldAgioCol=new AgioEntryCollection();
			oldAgioParam.setAgios(oldAgioCol);
			
			for(int i=0;i<agioCol.size();i++){
				if(agioCol.get(i).getAgio()==null){
					isHasAgio=true;
					agioEntryInfo=agioCol.get(i);
				}else{
					oldAgioCol.add(agioCol.get(i));
				}
			}
			SellTypeEnum sellType = (SellTypeEnum)this.comboSellType.getSelectedItem();
			CalcTypeEnum valuationType = (CalcTypeEnum)this.comboValuationType.getSelectedItem();
			
			BigDecimal roomArea=this.txtRoomArea.getBigDecimalValue();
			BigDecimal buildingArea=this.txtBuildingArea.getBigDecimalValue();
			BigDecimal roomPrice=this.txtRoomPrice.getBigDecimalValue();
			BigDecimal buildingPrice=this.txtBuildingPrice.getBigDecimalValue();
			BigDecimal standardAmount=this.txtStandardTotalAmount.getBigDecimalValue();
			
			
			BigDecimal fitmentAmount = this.txtFitmentAmount.getBigDecimalValue();
			BigDecimal attachmentAmount =getMergeContractAttachmentTotalAmount(true);
			
			BigDecimal cashSalesCompensate=this.txtCashSalesCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtCashSalesCompensate.getBigDecimalValue();
			BigDecimal planningCompensate=this.txtPlanningCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtPlanningCompensate.getBigDecimalValue();
			
			BigDecimal areaCompensateAmount =cashSalesCompensate.add(planningCompensate);
			
			boolean isFitmentToContract = this.chkIsFitmentToContract.isSelected();
			SHEPayTypeInfo payType = (SHEPayTypeInfo) this.f7PayType.getValue();
			String payTypeName = null;
			BigDecimal payTypeAgio = FDCHelper.ONE_HUNDRED;
			if (payType != null) {
				payTypeName = payType.getName();
				payTypeAgio = payType.getAgio();
				if(payTypeAgio == null){
					payTypeAgio = FDCHelper.ONE_HUNDRED;
				}
			}
			PurchaseParam purParam = SHEManageHelper.getAgioParam(oldAgioParam, roomInfo, 
					 sellType,valuationType,isFitmentToContract,roomArea,buildingArea,roomPrice,buildingPrice,standardAmount,fitmentAmount, attachmentAmount, areaCompensateAmount ,SpecialAgioEnum.DaZhe, payTypeAgio,payTypeName);
			
			BigDecimal amount=purParam.getDealTotalAmount().subtract(this.txtDealTotalAmount.getBigDecimalValue());
			amount=SHEManageHelper.setScale(digit, toIntegerType,amount);
			if(!isHasAgio){
				if(!purParam.getDealTotalAmount().equals(this.txtDealTotalAmount.getBigDecimalValue())){
					ChangeAgioEntryInfo entryInfo = new ChangeAgioEntryInfo();
					entryInfo.setAmount(amount);
					entryInfo.setSeq(this.currAgioParam.getAgios().size()+1);
					this.currAgioParam.getAgios().add(entryInfo);
					this.txtAgio.setUserObject(this.currAgioParam.getAgios());
				}
			}else{
				if(!purParam.getDealTotalAmount().equals(this.txtDealTotalAmount.getBigDecimalValue().add(agioEntryInfo.getAmount()))){
					if(amount.compareTo(FDCHelper.ZERO)==0){
						this.currAgioParam.getAgios().remove(agioEntryInfo);
					}else{
						agioEntryInfo.setAmount(amount);
						agioEntryInfo.setSeq(this.currAgioParam.getAgios().size()+1);
					}
				}
			}
			PurchaseParam afterPurParam = SHEManageHelper.getAgioParam(this.currAgioParam, roomInfo, 
					 sellType,valuationType,isFitmentToContract,roomArea,buildingArea,roomPrice,buildingPrice,standardAmount,fitmentAmount, attachmentAmount, areaCompensateAmount ,SpecialAgioEnum.DaZhe, payTypeAgio,payTypeName);
			if(afterPurParam!=null) {
				this.txtAgioDes.setText(afterPurParam.getAgioDes());
				this.txtAgio.setValue(afterPurParam.getFinalAgio());
				this.txtContractTotalAmount.setValue(afterPurParam.getContractTotalAmount());
				this.txtContractBuildPrice.setValue(afterPurParam.getContractBuildPrice());
				this.txtContractRoomPrice.setValue(afterPurParam.getContractRoomPrice());
				this.txtDealBuildPrice.setValue(afterPurParam.getDealBuildPrice());
				this.txtDealRoomPrice.setValue(afterPurParam.getDealRoomPrice());
			}
			calcSellAmount();
			
			if(this.tblPCPayList.getRowCount()>0&&FDCMsgBox.showConfirm2(this, "成交总价已改变，是否重新生成付款明细？")== FDCMsgBox.YES){
				this.updatePayList();
			}
    	}
	}
	protected void txtQRFeeAmount_dataChanged(DataChangeEvent e) throws Exception {
		if(e.getNewValue()!=null&&this.txtQRActAmount.getBigDecimalValue()!=null){
			if(this.txtQRActAmount.getBigDecimalValue().compareTo((BigDecimal)e.getNewValue())<0){
				this.txtQuitAmount.setValue(FDCHelper.ZERO);
			}else{
				this.txtQuitAmount.setValue(this.txtQRActAmount.getBigDecimalValue().subtract((BigDecimal)e.getNewValue()));
			}
		}
	}

	protected void f7Room_dataChanged(DataChangeEvent e) throws Exception {
		if(e.getNewValue()==null) return;
		RoomInfo room=(RoomInfo)e.getNewValue();
		verifyAddNewRoom(room,(RoomInfo)e.getOldValue());
		
		TransactionStateEnum state=((BaseTransactionInfo)objectValue).getBizState();
		editData.setSrcId(((BaseTransactionInfo)objectValue).getId());
		editData.setTransactionID(((BaseTransactionInfo)objectValue).getTransactionID());
		editData.setBizState(state);
		loadBizType(state,(ChangeBizTypeEnum)comboBizType.getSelectedItem());
		
		initSourceBillPanel();
		initNewBillPanel();
		setSrcBizAdscriptionDate();
		
		BigDecimal ChangeRoomActAmount=getPayList(editData,objectValue);
		if(ChangeBizTypeEnum.CHANGENAME.equals((ChangeBizTypeEnum)comboBizType.getSelectedItem())){
			getCustomerList(objectValue,this.customer);
			this.storeCustomerEntry();
			loadCustomerEntry(editData);
			loadSrcCustomerEntry(objectValue);
			loadPayList(editData,this.tblPayList);
			this.txtActAmount.setValue(ChangeRoomActAmount);
		}
		if(ChangeBizTypeEnum.QUITROOM.equals((ChangeBizTypeEnum)comboBizType.getSelectedItem())){
			loadPayList(editData,this.tblQRPayList);
			this.txtQRActAmount.setValue(ChangeRoomActAmount);
			this.txtQuitAmount.setValue(this.txtQRActAmount.getBigDecimalValue());
		}
		if(ChangeBizTypeEnum.PRICECHANGE.equals((ChangeBizTypeEnum)comboBizType.getSelectedItem())){
			loadPriceChangePanel();
			loadPayList(editData,this.tblPCPayList);
			
			getAgioList(editData,objectValue);
			getRoomAttachmentList(editData,objectValue);
			loadAttachmentEntry(editData);
			loadAgioEntry(editData);
		}
		if(ChangeBizTypeEnum.CHANGEROOM.equals((ChangeBizTypeEnum)comboBizType.getSelectedItem())){
			this.txtCRActAmount.setValue(ChangeRoomActAmount);
		}
		oldObjectValue=objectValue;
		
		if(room!=null){
			room=RoomFactory.getRemoteInstance().getRoomInfo("select building.sellProject.* from where id='"+room.getId()+"'");
			((BaseTransactionInfo)editData).setSellProject(room.getBuilding().getSellProject());
		}
	}
	protected void f7AgioScheme_dataChanged(DataChangeEvent e) throws Exception {
		if(this.f7AgioScheme.getValue()!=null){
			AgioSchemeInfo agioScheme=(AgioSchemeInfo)this.f7AgioScheme.getValue();
			boolean existDJ=false;
			boolean existZJ=false;
			AgioEntryCollection agioEntryColl = currAgioParam.getAgios(); 
			Set id=new HashSet();
			for(int i=0;i<agioEntryColl.size();i++){
				id.add(agioEntryColl.get(i).getAgio());
				if(AgioCalTypeEnum.DanJia.equals(agioEntryColl.get(i).getAgio().getCalType())){
					existDJ=true;
				}
				if(AgioCalTypeEnum.ZongJia.equals(agioEntryColl.get(i).getAgio().getCalType())){
					existZJ=true;
				}
			}
			for(int i=0;i<agioScheme.getAgioSchemeEntry().size();i++){
				AgioBillInfo agioBill=AgioBillFactory.getRemoteInstance().getAgioBillInfo(new ObjectUuidPK(agioScheme.getAgioSchemeEntry().get(i).getAgioBill().getId()));
				
				if(AgioCalTypeEnum.DanJia.equals(agioBill.getCalType())){
					existDJ=true;
				}
				if(AgioCalTypeEnum.ZongJia.equals(agioBill.getCalType())){
					existZJ=true;
				}
			}
			if(existDJ&&existZJ){
				FDCMsgBox.showWarning(this,"总价优惠与单价优惠折扣类型不能同时选择!");
				this.f7AgioScheme.setValue(e.getOldValue());
				SysUtil.abort();
			}
			int count=agioEntryColl.size();
			
			for(int i=0;i<agioScheme.getAgioSchemeEntry().size();i++){
				AgioBillInfo agioBill=AgioBillFactory.getRemoteInstance().getAgioBillInfo(new ObjectUuidPK(agioScheme.getAgioSchemeEntry().get(i).getAgioBill().getId()));		
				
				if(id.contains(agioBill)){
					continue;
				}
				if (agioBill.getCalType().equals(AgioCalTypeEnum.DanJia)) {
					if(PriceAccountTypeEnum.StandSetPrice.equals(this.currAgioParam.getPriceAccountType()))
					{
						FDCMsgBox.showWarning(this,"当计价方式是按标准总价时，不能选择单价优惠折扣类型!");
						this.f7AgioScheme.setValue(null);
						SysUtil.abort();
					}
				}
				AgioEntryInfo entry=new ChangeAgioEntryInfo();
				entry.setAgio(agioBill);
				entry.setPro(agioBill.getPro());
				entry.setAmount(agioBill.getAmount());
				entry.setSeq(count);
				entry.setOperate(agioScheme.getAgioSchemeEntry().get(i).getOperate());
				agioEntryColl.add(entry);
				
				count++;
			}
			this.txtAgio.setUserObject(agioEntryColl);
			this.updateAmount();
		}
	}
	protected void comboBizType_itemStateChanged(ItemEvent e) throws Exception {
		editData.setBizType((ChangeBizTypeEnum) comboBizType.getSelectedItem());
		initBizPanel((ChangeBizTypeEnum) comboBizType.getSelectedItem());
		initF7ChangeReason((ChangeBizTypeEnum) comboBizType.getSelectedItem());
		this.f7ChangeReson.setValue(null);
		if(objectValue!=null){
			loadBizType(((BaseTransactionInfo)objectValue).getBizState(),(ChangeBizTypeEnum)comboBizType.getSelectedItem());
		}
		initNewBillPanel();
		
		BigDecimal actAmount=getPayList(editData,objectValue);
		if(ChangeBizTypeEnum.CHANGENAME.equals((ChangeBizTypeEnum)comboBizType.getSelectedItem())){
			getCustomerList(objectValue,this.customer);
			this.storeCustomerEntry();
			loadCustomerEntry(editData);
			loadSrcCustomerEntry(objectValue);
			loadPayList(editData,this.tblPayList);
			this.txtActAmount.setValue(actAmount);
		}
		if(ChangeBizTypeEnum.QUITROOM.equals((ChangeBizTypeEnum)comboBizType.getSelectedItem())){
			loadPayList(editData,this.tblQRPayList);
			this.txtQRActAmount.setValue(actAmount);
			this.txtQuitAmount.setValue(this.txtQRActAmount.getBigDecimalValue());
		}
		if(ChangeBizTypeEnum.PRICECHANGE.equals((ChangeBizTypeEnum)comboBizType.getSelectedItem())&&this.tblPCPayList.getRowCount()==0){
			loadPayList(editData,this.tblPCPayList);
			loadPriceChangePanel();
		}
		if(ChangeBizTypeEnum.CHANGEROOM.equals((ChangeBizTypeEnum)comboBizType.getSelectedItem())){
			this.txtCRActAmount.setValue(actAmount);
		}
		if(this.comboBizType.getSelectedItem()==null){
			this.f7Room.setEnabled(false);
		}else{
			this.f7Room.setEnabled(true);
		}
	}
	private void initF7ChangeReason(ChangeBizTypeEnum bizType){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(bizType==null){
			filter.getFilterItems().add(new FilterItemInfo("bizType", "null"));	
		}else{
			filter.getFilterItems().add(new FilterItemInfo("bizType", bizType.getValue()));	
		}
		
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",org.getId()));	
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",new Boolean(true)));	
		view.setFilter(filter);
		this.f7ChangeReson.setEntityViewInfo(view);
	}
	/**
	 * 初始化F7房间
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void initF7Room() throws EASBizException, BOSException {
		SellProjectInfo sellproject=((BaseTransactionInfo)editData).getSellProject();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellproject.getId().toString()));
		
		String per=NewCommerceHelper.getPermitSaleManIdSql(sellproject,SysContext.getSysContext().getCurrentUserInfo());
		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from ( ");
		sql.append("select distinct bill.froomId from t_she_prePurchaseManage bill ");
		sql.append("left join T_SHE_PrePurchaseSaleManEntry saleMan on saleMan.fheadid=bill.fid where saleMan.fuserid in ("+per+") and bill.fsellProjectId='"+sellproject.getId()+"' and bill.fbizState in('SignAudit','PreAudit','PurAudit') ");
		sql.append("union ");
		sql.append("select distinct bill.froomId from t_she_purchaseManage bill ");
		sql.append("left join T_SHE_PurSaleManEntry saleMan on saleMan.fheadid=bill.fid where saleMan.fuserid in ("+per+") and bill.fsellProjectId='"+sellproject.getId()+"' and bill.fbizState in('SignAudit','PreAudit','PurAudit') ");
		sql.append("union ");
		sql.append("select distinct bill.froomId from t_she_signManage bill ");
		sql.append("left join T_SHE_SignSaleManEntry saleMan on saleMan.fheadid=bill.fid where saleMan.fuserid in ("+per+") and bill.fsellProjectId='"+sellproject.getId()+"' and bill.fbizState in('SignAudit','PreAudit','PurAudit'))");
	
		filter.getFilterItems().add(new FilterItemInfo("id", sql,CompareType.INNER));
		
		view.setFilter(filter);
		this.f7Room.setEntityViewInfo(view);
		
		KDBizPromptBox f7RoomBox = new KDBizPromptBox();
		f7RoomBox.setDisplayFormat("$name$");
		f7RoomBox.setEditFormat("$name$");
		f7RoomBox.setCommitFormat("$name$");
		f7RoomBox.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomSelectQuery");
		f7RoomBox.setEntityViewInfo(view);
		KDTDefaultCellEditor f7RoomEditor = new KDTDefaultCellEditor(f7RoomBox);
		this.tblAttachProperty.getColumn(AP_ROOMNUMBER).setEditor(f7RoomEditor);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (!FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
			FDCMsgBox.showWarning(this,"该单据不是提交状态，不能进行审批操作！");
			return;
		}
		if (isModify()) {
			FDCMsgBox.showWarning(this,"单据已被修改，请先提交。");
			this.abort();
		}
		
		String id = getSelectBOID();
		FDCClientUtils.checkBillInWorkflow(this, id);
		if (id != null) {
			((IChangeManage) getBizInterface()).audit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();

		setAuditButtonStatus(STATUS_VIEW);
		this.actionSave.setEnabled(false);
	}
	private void setSrcBizAdscriptionDate(){
		if(objectValue!=null){
			if(BizDateToEnum.OldBillBizDate.equals(cnBizAdscriptionDate)){
				this.pkCNBizAdscriptionDate.setValue(((BaseTransactionInfo)objectValue).getBusAdscriptionDate());
			}
			if(BizDateToEnum.OldBillBizDate.equals(pcBizAdscriptionDate)){
				this.pkBizAdscriptionDate.setValue(((BaseTransactionInfo)objectValue).getBusAdscriptionDate());
			}
			if(BizDateToEnum.OldBillBizDate.equals(crBizAdscriptionDate)){
				if(this.getNewBillUI(this.panelNewBill)!=null){
					this.getNewBillUI(this.panelNewBill).pkBizAdscriptionDate.setValue(((BaseTransactionInfo)objectValue).getBusAdscriptionDate());
					this.getNewBillUI(this.panelNewBill).pkBizDate.setValue(((BaseTransactionInfo)objectValue).getBizDate());
				}
			}
		}
	}
	private void SHEFunction(){
		if((STATUS_EDIT.equals(this.getOprtState())||STATUS_ADDNEW.equals(this.getOprtState()))){
			this.txtDealTotalAmount.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.pkBizAdscriptionDate.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.f7AgioScheme.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			if(isDealAmountEdit){
				//if(!STATUS_ADDNEW.equals(this.getOprtState())){
					this.txtDealTotalAmount.setEnabled(true);
				//}
			}else{
				this.txtDealTotalAmount.setEnabled(false);
			}
//			if(isBizDateEdit){
//				this.pkBizAdscriptionDate.setEnabled(true);
//				this.pkCNBizAdscriptionDate.setEnabled(true);
//			}else{
				this.pkBizAdscriptionDate.setEnabled(false);
				this.pkCNBizAdscriptionDate.setEnabled(false);
//			}
			if(isUseAgioScheme){
				this.f7AgioScheme.setEnabled(true);
			}else{
				this.f7AgioScheme.setEnabled(false);
			}
		}
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		setAfterSubmitEnable(oprtType);
		SHEFunction();
	}
	
	/**
	 * 重新计算销售总价
	 * add by renliang at 2011-11-19
	 */
	private void calcSellAmount(){
		BigDecimal contractTotalAmount=this.txtContractTotalAmount.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtContractTotalAmount.getBigDecimalValue();
		BigDecimal cashSalesCompensate=this.txtCashSalesCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtCashSalesCompensate.getBigDecimalValue();
		BigDecimal planningCompensate=this.txtPlanningCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtPlanningCompensate.getBigDecimalValue();
		BigDecimal areaCompensate=this.txtAreaCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtAreaCompensate.getBigDecimalValue();
		BigDecimal allCompensate=cashSalesCompensate.add(planningCompensate).add(areaCompensate);
		
		this.txtSellAmount.setValue(contractTotalAmount.add(allCompensate));
	}
	
	protected void setAfterSubmitEnable(String oprtType){
		if(STATUS_ADDNEW.equals(oprtType)){
			this.f7Room.setEnabled(true);
			this.comboBizType.setEnabled(true);
			
			this.f7MoneyDefine.setEnabled(true);
			this.txtFeeAmount.setEnabled(true);
			this.f7QRMoneyDefine.setEnabled(true);
			this.txtQRFeeAmount.setEnabled(true);
			this.f7CRMoneyDefine.setEnabled(true);
			this.txtCRFeeAmount.setEnabled(true);

	    }else{
	    	if(editData!=null&&!FDCBillStateEnum.SAVED.equals(editData.getState())){
	    		this.f7Room.setEnabled(false);
				this.comboBizType.setEnabled(false);
				
				if(this.f7MoneyDefine.getValue()!=null){
					this.f7MoneyDefine.setEnabled(false);
					this.txtFeeAmount.setEnabled(false);
				}
				if(this.f7QRMoneyDefine.getValue()!=null){
					this.f7QRMoneyDefine.setEnabled(false);
					this.txtQRFeeAmount.setEnabled(false);
				}
				if(this.f7CRMoneyDefine.getValue()!=null){
					this.f7CRMoneyDefine.setEnabled(false);
					this.txtCRFeeAmount.setEnabled(false);
				}
	    	}
	    }
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if (!FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
			FDCMsgBox.showWarning(this,"该单据不是审批状态，不能进行反审批操作！");
			return;
		}
		String id = getSelectBOID();
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		SHEManageHelper.checkCanUnAuditChangeManage(this, id);
		
		if (id != null) {
			((IChangeManage) getBizInterface()).unAudit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		
		setAuditButtonStatus(this.getOprtState());
	}
	protected void initBizPanel(ChangeBizTypeEnum bizType){
		tabInformation.removeAll();
		if(ChangeBizTypeEnum.CHANGENAME.equals(bizType)){
			panelChangeName.setName(CHANGENAME);
			tabInformation.add(panelChangeName);
		}
		if(ChangeBizTypeEnum.CHANGEROOM.equals(bizType)){
			panelChangeRoom.setName(CHANGEROOM);
			tabInformation.add(panelChangeRoom);
		}
		if(ChangeBizTypeEnum.QUITROOM.equals(bizType)){
			panelQuitRoom.setName(QUITROOM);
			tabInformation.add(panelQuitRoom);
		}
		if(ChangeBizTypeEnum.PRICECHANGE.equals(bizType)){
			panelPriceChange.setName(PRICECHANGE);
			tabInformation.add(panelPriceChange);
		}
		panelSourceBill.setName(SOURCEBILL);
		tabInformation.add(panelSourceBill);
	}
	
	/**
	 *插入行，请实现createNewPayListData()；否则中断
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		insertLine(tblPCPayList);
		appendFootRow(tblPCPayList);
	}
	
	/**
	 * 覆写父类方法
	 */
	protected void insertLine(KDTable table) {
		if (table == null) {
			return;
		}
		IRow row = null;
		if (table.getSelectManager().size() > 0) {
			int top = table.getSelectManager().get().getTop();
			if (isTableColumnSelected(table)) {
				row = table.addRow();
				row.setUserObject(createNewPayListData());
				row.getCell(PL_STATE).setValue(new Boolean(false));
				row.getCell(PL_CURRENCY).setValue(currency);
			} else {
				row = table.addRow(top);
				row.setUserObject(createNewPayListData());
				row.getCell(PL_STATE).setValue(new Boolean(false));
				row.getCell(PL_CURRENCY).setValue(currency);
			}
		} else {
			row = table.addRow();
			row.setUserObject(createNewPayListData());
			row.getCell(PL_STATE).setValue(new Boolean(false));
			row.getCell(PL_CURRENCY).setValue(currency);
		}
	}
	/**
	 * 删除行
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		if (this.btnRemoveAPLine.equals(e.getSource())) {
			removeLine(tblAttachProperty);
			afterRemoveLineFocusPolicy(tblAttachProperty);
			
			this.updateAmount();
		} else if (this.btnPCRemovePayLine.equals(e.getSource())) {
			removeLine(tblPCPayList);
			afterRemoveLineFocusPolicy(tblPCPayList);
		}
	}

	/**
	 * 删除行后的焦点策略
	 * 
	 * @return
	 */
	private void afterRemoveLineFocusPolicy(KDTable table) {
		if (table.getRowCount() == 0) {
			FocusTraversalPolicy policy = null;
			Container container = null;
			Component initComponent = null;
			if (this.getFocusTraversalPolicy() != null
					&& this.getFocusTraversalPolicy() instanceof UIFocusTraversalPolicy) {
				policy = this.getFocusTraversalPolicy();
				container = this;
				Component[] traverComponent = ((UIFocusTraversalPolicy) policy).getComponents();
				for (int i = 0; i < traverComponent.length; i++) {
					if (traverComponent[i] == table) {
						initComponent = traverComponent[i];
						break;
					}
				}
				if (initComponent == null) {
					initComponent = policy.getLastComponent(container);
					initComponent.requestFocusInWindow();
				} else if (initComponent != null) {
					Component component = policy.getComponentBefore(container, initComponent);
					while ((component instanceof IKDTextComponent) == false || component.isEnabled() == false) {
						component = policy.getComponentBefore(container, component);
					}
					component.requestFocusInWindow();
				}
			} else if (policy == null) {
				if (this.getUIWindow() instanceof Dialog) {
					policy = ((Dialog) uiWindow).getFocusTraversalPolicy();
					container = (Dialog) uiWindow;
				} else if (this.getUIWindow() instanceof Window) {
					policy = ((Window) uiWindow).getFocusTraversalPolicy();
					container = (Window) uiWindow;
				}

				if (policy != null) {
					try {
						Component component = policy.getComponentBefore(container, table);
						while ((component instanceof IKDTextComponent) == false || component.isEnabled() == false) {
							component = policy.getComponentBefore(container, component);
						}
						component.requestFocusInWindow();
					} catch (Exception ex) {
					}
				}
			}
		}
	}

	protected void removeLine(KDTable table) {
		if (table == null) {
			return;
		}
		if ((table.getSelectManager().size() == 0)){
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
			return;
		}
		if (confirmRemove()) {
			KDTSelectManager selectManager = table.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (table.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null){
				return;
			}
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				table.removeRow(rowIndex);
			}
			if (table.getRow(0) != null){
				table.getSelectManager().select(0, 0);
			}
		}
	}
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		if (this.btnAddAPLine.equals(e.getSource())) {
			IRow row = tblAttachProperty.addRow();
			row.setUserObject(createNewApData());
			row.getCell(AP_ISMERGETOCONTRACT).setValue(new Boolean(false));
		} else if (this.btnPCAddPayline.equals(e.getSource())) {
			IRow row = tblPCPayList.addRow();
			row.setUserObject(createNewPayListData());
			row.getCell(PL_STATE).setValue(new Boolean(false));
			row.getCell(PL_CURRENCY).setValue(currency);
		}
	}

	/**
    * 
    */
	protected IObjectValue createNewDetailData(KDTable table) {
		if (tblAttachProperty.equals(table)) {
			return createNewApData();
		} else if (tblPCPayList.equals(table)) {
			return createNewPayListData();
		}
		return null;
	}

	/**
	 * 新增付款明细行时，需要New一个付款明细值对象,否则新增出中断
	 * 
	 * @return
	 */
	protected IObjectValue createNewPayListData(){
		return new ChangePayListEntryInfo();
		
	}

	/**
	 * 新增附属房产行时，需要New一个附属房产值对象,否则新增出中断
	 * 
	 * @return
	 */
	protected IObjectValue createNewApData(){
		return new ChangeRoomAttachEntryInfo();
	}
	protected void initMoneyDefine(KDBizPromptBox f7){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.COMMISSIONCHARGE_VALUE));
		view.setFilter(filter);
		f7.setEntityViewInfo(view);
	}
	
	protected void loadCustomerEntry(ChangeManageInfo info){
		ChangeCustomerEntryCollection customerCol=info.getCustomerEntry();
		customer=new ArrayList();
		for(int i=0;i<customerCol.size();i++){
			this.customer.add(customerCol.get(i));
		}
		SHEManageHelper.loadCustomer(labCustomer1, labCustomer2, labCustomer3,labCustomer4,labCustomer5,this.customer, txtTel, info);
		
	}
	protected void getCustomerList(IObjectValue objectValue,List customerentry){
		customerentry.clear();
		if(objectValue instanceof PrePurchaseManageInfo){
			PrePurchaseCustomerEntryCollection customerCol=((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry();
			for(int i=0;i<customerCol.size();i++){
				customerCol.get(i).setId(null);
				customerentry.add(customerCol.get(i));
			}
		}
		if(objectValue instanceof PurchaseManageInfo){
			PurCustomerEntryCollection customerCol=((PurchaseManageInfo)objectValue).getPurCustomerEntry();
			for(int i=0;i<customerCol.size();i++){
				customerCol.get(i).setId(null);
				customerentry.add(customerCol.get(i));
			}
		}
		if(objectValue instanceof SignManageInfo){
			SignCustomerEntryCollection customerCol=((SignManageInfo)objectValue).getSignCustomerEntry();
			for(int i=0;i<customerCol.size();i++){
				customerCol.get(i).setId(null);
				customerentry.add(customerCol.get(i));
			}
		}
	}
	protected List getSaleManList(IObjectValue objectValue){
		List saleManEntry=new ArrayList();
		if(objectValue instanceof PrePurchaseManageInfo){
			PrePurchaseSaleManEntryCollection saleManCol=((PrePurchaseManageInfo)objectValue).getPrePurchaseSaleManEntry();
			for(int i=0;i<saleManCol.size();i++){
				saleManCol.get(i).setId(null);
				saleManEntry.add(saleManCol.get(i));
			}
		}
		if(objectValue instanceof PurchaseManageInfo){
			PurSaleManEntryCollection saleManCol=((PurchaseManageInfo)objectValue).getPurSaleManEntry();
			for(int i=0;i<saleManCol.size();i++){
				saleManCol.get(i).setId(null);
				saleManEntry.add(saleManCol.get(i));
			}
		}
		if(objectValue instanceof SignManageInfo){
			SignSaleManEntryCollection saleManCol=((SignManageInfo)objectValue).getSignSaleManEntry();
			for(int i=0;i<saleManCol.size();i++){
				saleManCol.get(i).setId(null);
				saleManEntry.add(saleManCol.get(i));
			}
		}
		return saleManEntry;
	}
	protected void loadSrcCustomerEntry(IObjectValue objectValue){
		List customerentry=new ArrayList();
		getCustomerList(objectValue,customerentry);
		SHEManageHelper.loadCustomer(labSourceCustomer1, labSourceCustomer2, labSourceCustomer3,labSourceCustomer4,labSourceCustomer5,customerentry, txtSourceTel, (BaseTransactionInfo)objectValue);
	}
	protected void loadBizType(TransactionStateEnum bizState,ChangeBizTypeEnum bizType){
		this.comboChangeRoomType.removeAllItems();
		this.comboQuitRoomType.removeAllItems();
		
		this.comboChangeRoomType.addItem(new KDComboBoxMultiColumnItem(new String[] {PRECHANGEROOM}));
		this.comboChangeRoomType.addItem(new KDComboBoxMultiColumnItem(new String[] {PURCHANGEROOM}));
		this.comboChangeRoomType.addItem(new KDComboBoxMultiColumnItem(new String[] {SIGNCHANGEROOM}));
		
		this.comboQuitRoomType.addItem(new KDComboBoxMultiColumnItem(new String[] {PREQUITROOM}));
		this.comboQuitRoomType.addItem(new KDComboBoxMultiColumnItem(new String[] {PURQUITROOM}));
		this.comboQuitRoomType.addItem(new KDComboBoxMultiColumnItem(new String[] {SIGNQUITROOM}));
	
		if(ChangeBizTypeEnum.CHANGEROOM.equals(bizType)){
			if(TransactionStateEnum.PREAUDIT.equals(bizState)) this.comboChangeRoomType.setSelectedIndex(0);
			if(TransactionStateEnum.PURAUDIT.equals(bizState)) this.comboChangeRoomType.setSelectedIndex(1);
			if(TransactionStateEnum.SIGNAUDIT.equals(bizState)) this.comboChangeRoomType.setSelectedIndex(2);
		}
		if(ChangeBizTypeEnum.QUITROOM.equals(bizType)){
			if(TransactionStateEnum.PREAUDIT.equals(bizState)) this.comboQuitRoomType.setSelectedIndex(0);
			if(TransactionStateEnum.PURAUDIT.equals(bizState)) this.comboQuitRoomType.setSelectedIndex(1);
			if(TransactionStateEnum.SIGNAUDIT.equals(bizState)) this.comboQuitRoomType.setSelectedIndex(2);
		}
	}
	protected void storePayList(KDTable table) {
		ChangeManageInfo info = this.editData;
		info.getPayListEntry().clear();
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			ChangePayListEntryInfo entry = (ChangePayListEntryInfo) row.getUserObject();
			entry.setSeq(i);
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell(PL_MONEYNAME).getValue();
			entry.setMoneyDefine(moneyName);
			entry.setAppDate((Date) row.getCell(PL_APPDATE).getValue());
			entry.setCurrency((CurrencyInfo) row.getCell(PL_CURRENCY).getValue());
			entry.setAppAmount(SHEManageHelper.setScale(2, toIntegerType,(BigDecimal) row.getCell(PL_APPAMOUNT).getValue()));
			entry.setDescription((String) row.getCell(PL_DES).getValue());
			
			info.getPayListEntry().add(entry);
		}
	}
	
	protected void getAgioList(ChangeManageInfo info,IObjectValue objectValue){
		if(objectValue==null) return;
		info.getAgioEntry().clear();
		if(objectValue instanceof PrePurchaseManageInfo){
			PrePurchaseAgioEntryCollection col=((PrePurchaseManageInfo)objectValue).getPrePurchaseAgioEntry();
			for(int i=0;i<col.size();i++){
				ChangeAgioEntryInfo entry=new ChangeAgioEntryInfo();
				SHEManageHelper.setAgioListEntry(entry,col.get(i));
				info.getAgioEntry().add(entry);
			}
		}
		if(objectValue instanceof PurchaseManageInfo){
			PurAgioEntryCollection col=((PurchaseManageInfo)objectValue).getPurAgioEntry();
			for(int i=0;i<col.size();i++){
				ChangeAgioEntryInfo entry=new ChangeAgioEntryInfo();
				SHEManageHelper.setAgioListEntry(entry,col.get(i));
				info.getAgioEntry().add(entry);
			}
		}
		if(objectValue instanceof SignManageInfo){
			SignAgioEntryCollection col=((SignManageInfo)objectValue).getSignAgioEntry();
			for(int i=0;i<col.size();i++){
				ChangeAgioEntryInfo entry=new ChangeAgioEntryInfo();
				SHEManageHelper.setAgioListEntry(entry,col.get(i));
				info.getAgioEntry().add(entry);
			}
		}
	}
	protected void getRoomAttachmentList(ChangeManageInfo info,IObjectValue objectValue){
		if(objectValue==null) return;
		info.getRoomAttachEntry().clear();
		if(objectValue instanceof PrePurchaseManageInfo){
			PrePurchaseRoomAttachmentEntryCollection col=((PrePurchaseManageInfo)objectValue).getPrePurchaseRoomAttachmentEntry();
			for(int i=0;i<col.size();i++){
				ChangeRoomAttachEntryInfo entry=new ChangeRoomAttachEntryInfo();
				SHEManageHelper.setRoomAttachmentListEntry(entry,col.get(i));
				for(int j=0;j<col.get(i).getPrePurchaseAgioEntry().size();j++){
					ChangeAgioEntryInfo agioEntry=new ChangeAgioEntryInfo();
					SHEManageHelper.setAgioListEntry(agioEntry,col.get(i).getPrePurchaseAgioEntry().get(j));
					entry.getAgioEntry().add(agioEntry);
				}
				info.getRoomAttachEntry().add(entry);
			}
		}
		if(objectValue instanceof PurchaseManageInfo){
			PurRoomAttachmentEntryCollection col=((PurchaseManageInfo)objectValue).getPurRoomAttachmentEntry();
			for(int i=0;i<col.size();i++){
				ChangeRoomAttachEntryInfo entry=new ChangeRoomAttachEntryInfo();
				SHEManageHelper.setRoomAttachmentListEntry(entry,col.get(i));
				for(int j=0;j<col.get(i).getPurAgioEntry().size();j++){
					ChangeAgioEntryInfo agioEntry=new ChangeAgioEntryInfo();
					SHEManageHelper.setAgioListEntry(agioEntry,col.get(i).getPurAgioEntry().get(j));
					entry.getAgioEntry().add(agioEntry);
				}
				info.getRoomAttachEntry().add(entry);
			}
		}
		if(objectValue instanceof SignManageInfo){
			SignRoomAttachmentEntryCollection col=((SignManageInfo)objectValue).getSignRoomAttachmentEntry();
			for(int i=0;i<col.size();i++){
				ChangeRoomAttachEntryInfo entry=new ChangeRoomAttachEntryInfo();
				SHEManageHelper.setRoomAttachmentListEntry(entry,col.get(i));
				for(int j=0;j<col.get(i).getSignAgioEntry().size();j++){
					ChangeAgioEntryInfo agioEntry=new ChangeAgioEntryInfo();
					SHEManageHelper.setAgioListEntry(agioEntry,col.get(i).getSignAgioEntry().get(j));
					entry.getAgioEntry().add(agioEntry);
				}
				info.getRoomAttachEntry().add(entry);
			}
		}
	}
	protected BigDecimal getPayList(ChangeManageInfo info,IObjectValue objectValue){
		if(objectValue==null) return FDCHelper.ZERO;
		info.getPayListEntry().clear();
		BigDecimal actAmount=FDCHelper.ZERO;
		if(objectValue instanceof PrePurchaseManageInfo){
			PrePurchasePayListEntryCollection col=((PrePurchaseManageInfo)objectValue).getPrePurchasePayListEntry();
			CRMHelper.sortCollection(col, "seq", true);
			for(int i=0;i<col.size();i++){
				ChangePayListEntryInfo entry=new ChangePayListEntryInfo();
				SHEManageHelper.setPayListEntry(null,entry,col.get(i));
				actAmount=actAmount.add(entry.getActRevAmount());
				info.getPayListEntry().add(entry);
			}
		}
		if(objectValue instanceof PurchaseManageInfo){
			PurPayListEntryCollection col=((PurchaseManageInfo)objectValue).getPurPayListEntry();
			CRMHelper.sortCollection(col, "seq", true);
			for(int i=0;i<col.size();i++){
				ChangePayListEntryInfo entry=new ChangePayListEntryInfo();
				SHEManageHelper.setPayListEntry(null,entry,col.get(i));
				actAmount=actAmount.add(entry.getActRevAmount());
				info.getPayListEntry().add(entry);
			}
		}
		if(objectValue instanceof SignManageInfo){
			SignPayListEntryCollection col=((SignManageInfo)objectValue).getSignPayListEntry();
			CRMHelper.sortCollection(col, "seq", true);
			for(int i=0;i<col.size();i++){
				ChangePayListEntryInfo entry=new ChangePayListEntryInfo();
				SHEManageHelper.setPayListEntry(null,entry,col.get(i));
				actAmount=actAmount.add(entry.getActRevAmount());
				info.getPayListEntry().add(entry);
			}
		}
		return actAmount;
	}

	protected void loadPayList(ChangeManageInfo info,KDTable table) {
		ChangePayListEntryCollection payEntrys = info.getPayListEntry();
		CRMHelper.sortCollection(payEntrys, "seq", true);
		table.removeRows();
		for(int i=0;i<payEntrys.size();i++) {
			ChangePayListEntryInfo entry = payEntrys.get(i);
			addPayListEntryRow(entry,table);
		}
	}
	protected void loadPriceChangePanel(){
		loadPricePanel=false;
		if(objectValue==null) return;
		BaseTransactionInfo baseInfo=(BaseTransactionInfo)objectValue;
		this.comboSellType.setSelectedItem(baseInfo.getSellType());
		if(baseInfo.getRoom().getRoomModel()!=null)
			this.txtRoomModel.setText(baseInfo.getRoom().getRoomModel().getName());
		this.txtStandardTotalAmount.setValue(baseInfo.getStrdTotalAmount());
	
		loadArea(baseInfo);
		
		this.txtAttachPropertyTotalAmount.setValue(baseInfo.getAttachmentAmount());
		this.txtBuildingPrice.setValue(baseInfo.getStrdBuildingPrice());
		this.txtRoomPrice.setValue(baseInfo.getStrdRoomPrice());
		this.txtFitmentAmount1.setValue(baseInfo.getFitmentTotalAmount());
		
		this.f7FitmentStandard.setValue(baseInfo.getFitmentStandard());
		this.txtFitmentPrice.setValue(baseInfo.getFitmentPrice());
		this.txtFitmentAmount.setValue(baseInfo.getFitmentTotalAmount());
		this.chkIsFitmentToContract.setSelected(baseInfo.isIsFitmentToContract());
		
		//根据参数取业务归属日期
//		this.pkBizAdscriptionDate.setValue(baseInfo.getBusAdscriptionDate());
		
		this.f7PayType.setValue(baseInfo.getPayType());
		this.f7AgioScheme.setValue(baseInfo.getAgioScheme());
		this.txtAgioDes.setText(baseInfo.getAgioDesc());
		this.txtAgio.setValue(baseInfo.getLastAgio());
		this.txtDealTotalAmount.setValue(baseInfo.getDealTotalAmount());
		this.txtContractTotalAmount.setValue(baseInfo.getContractTotalAmount());
		this.comboValuationType.setSelectedItem(baseInfo.getValuationType());
	
		
		this.txtDealBuildPrice.setValue(baseInfo.getDealBuildPrice());
		this.txtDealRoomPrice.setValue(baseInfo.getDealRoomPrice());
		this.txtContractBuildPrice.setValue(baseInfo.getContractBuildPrice());
		this.txtContractRoomPrice.setValue(baseInfo.getContractRoomPrice());
		this.pkBizDate.setValue(baseInfo.getBizDate());
		
		this.txtLoanAmount.setValue(baseInfo.getLoanAmount());
		this.txtAFundAmount.setValue(baseInfo.getAccFundAmount());
		
		editData.setValuationType(baseInfo.getValuationType());
		editData.setPayType(baseInfo.getPayType());
		
		//参数针对交易
		editData.setIsEarnestInHouseAmount(baseInfo.isIsEarnestInHouseAmount());
		editData.setToIntegerType(baseInfo.getToIntegerType());
		editData.setDigit(baseInfo.getDigit());
		editData.setIsBasePriceSell(baseInfo.isIsBasePriceSell());
		editData.setPriceDigit(baseInfo.getPriceDigit());
		editData.setPriceToIntegerType(baseInfo.getPriceToIntegerType());
		
		if(objectValue instanceof PurchaseManageInfo){
			PurchaseManageInfo info=(PurchaseManageInfo)objectValue;
			this.txtPlanningCompensate.setValue(info.getPlanningCompensate());
			this.txtCashSalesCompensate.setValue(info.getCashSalesCompensate());
			
			this.txtPlanningArea.setValue(info.getPlanningArea());
			this.txtPreArea.setValue(info.getPreArea());
			this.txtActualArea.setValue(info.getActualArea());
			
		}
		if(objectValue instanceof SignManageInfo){
			SignManageInfo info=(SignManageInfo)objectValue;
			this.txtSellAmount.setValue(info.getSellAmount());
			this.txtPlanningCompensate.setValue(info.getPlanningCompensate());
			this.txtCashSalesCompensate.setValue(info.getCashSalesCompensate());
			this.txtAreaCompensate.setValue(info.getAreaCompensate());
			
			this.txtPlanningArea.setValue(info.getPlanningArea());
			this.txtPreArea.setValue(info.getPreArea());
			this.txtActualArea.setValue(info.getActualArea());
		}
		setTxtFormate();
		loadPricePanel=true;
	}
	protected void loadAgioEntry(ChangeManageInfo info){
		currAgioParam.setBasePriceSell(editData.isIsBasePriceSell());
		currAgioParam.setToIntegerType(SHEHelper.DEFAULT_TO_INTEGER_TYPE);
		currAgioParam.setDigit(SHEHelper.DEFAULT_DIGIT);
		if(CalcTypeEnum.PriceByBuildingArea.equals(editData.getValuationType())||CalcTypeEnum.PriceByRoomArea.equals(editData.getValuationType())){
			currAgioParam.setPriceAccountType(PriceAccountTypeEnum.PriceSetStand);
		}else{
			currAgioParam.setPriceAccountType(PriceAccountTypeEnum.StandSetPrice);
		}
		AgioEntryCollection agioEntryColl = new AgioEntryCollection(); 
		for(int i=0;i<editData.getAgioEntry().size();i++)
			agioEntryColl.add(editData.getAgioEntry().get(i));
		this.txtAgio.setUserObject(agioEntryColl);
		currAgioParam.setAgios(agioEntryColl);
	}
	protected void loadAttachmentEntry(ChangeManageInfo info){
		ChangeRoomAttachEntryCollection entrys = info.getRoomAttachEntry();
		this.tblAttachProperty.removeRows();
		for(int i=0;i<entrys.size();i++) {
			ChangeRoomAttachEntryInfo entry = entrys.get(i);
			IRow row = this.tblAttachProperty.addRow();
			row.setUserObject(entry);
			row.getCell(AP_ROOMNUMBER).setValue(entry.getRoom());
			row.getCell(AP_BUILDINGAREA).setValue(entry.getBuildingArea());
			row.getCell(AP_ROOMAREA).setValue(entry.getRoomArea());
			row.getCell(AP_STANDARDTOTALAMOUNT).setValue(entry.getStandardTotalAmount());
			row.getCell(AP_BUILDINGPRICE).setValue(entry.getBuildingPrice());
			row.getCell(AP_ROOMPRICE).setValue(entry.getRoomPrice());
			
			row.getCell(AP_AGIO).setValue(entry.getAgioEntry().toArray());
			ChangeAgioEntryCollection ChangeCol=((ChangeRoomAttachEntryInfo)row.getUserObject()).getAgioEntry();
			try {
				setEntryAgio(row,ChangeCol,(RoomInfo)row.getCell(AP_ROOMNUMBER).getValue());
			} catch (UIException e) {
				e.printStackTrace();
			}
			if(entry.getRoom()!=null){
				row.getCell(AP_AGIO).getStyleAttributes().setLocked(false);
			}else{
				row.getCell(AP_AGIO).getStyleAttributes().setLocked(true);
			}
			
			row.getCell(AP_ISMERGETOCONTRACT).setValue(new Boolean(entry.isIsAttachcmentToContract()));
			row.getCell(AP_MERGEAMOUNT).setValue(entry.getMergeAmount());
		}
	}
	protected void storeAgioEntry(){
		AgioEntryCollection agioEntrys = (AgioEntryCollection)this.txtAgio.getUserObject();
		if(agioEntrys==null){
			agioEntrys=new AgioEntryCollection();
		}
		editData.getAgioEntry().clear();
		for (int i = 0; i < agioEntrys.size(); i++) {
			ChangeAgioEntryInfo entryInfo = (ChangeAgioEntryInfo)agioEntrys.get(i);
			editData.getAgioEntry().add(entryInfo);
		}
	}
	protected void storeAttachmentEntry(){
		ChangeManageInfo info = this.editData;
		info.getRoomAttachEntry().clear();
		for (int i = 0; i < this.tblAttachProperty.getRowCount(); i++) {
			IRow row = this.tblAttachProperty.getRow(i);
			ChangeRoomAttachEntryInfo entry = (ChangeRoomAttachEntryInfo) row.getUserObject();
			entry.setRoom((RoomInfo)row.getCell(AP_ROOMNUMBER).getValue());
			entry.setIsAttachcmentToContract(((Boolean)row.getCell(AP_ISMERGETOCONTRACT).getValue()).booleanValue());
			entry.setMergeAmount(SHEManageHelper.setScale(digit, toIntegerType,(BigDecimal)row.getCell(AP_MERGEAMOUNT).getValue()));
			entry.setStandardTotalAmount(SHEManageHelper.setScale(digit, toIntegerType,(BigDecimal)row.getCell(AP_STANDARDTOTALAMOUNT).getValue()));
			entry.setBuildingPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,(BigDecimal)row.getCell(AP_BUILDINGPRICE).getValue()));
			entry.setRoomPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,(BigDecimal)row.getCell(AP_ROOMPRICE).getValue()));
			
			entry.setAgioDes(getAgioDes(entry.getAgioEntry().toArray()));
			
			entry.setBuildingArea((BigDecimal)row.getCell(AP_BUILDINGAREA).getValue());
			entry.setRoomArea((BigDecimal)row.getCell(AP_ROOMAREA).getValue());
			
			info.getRoomAttachEntry().add(entry);
		}
	}
	protected void storeCustomerEntry(){
		editData.getCustomerEntry().clear();
		String customerNames="";
		String customerPhone="";
		for(int i=0;i<this.customer.size();i++){
			ChangeCustomerEntryInfo info =new ChangeCustomerEntryInfo();
			TranCustomerEntryInfo tranInfo=(TranCustomerEntryInfo)customer.get(i);
			SHEManageHelper.setCustomerListEntry(info, tranInfo);
			String phone="";
			String mob="";
			String tel="";
			if(info.getPhone()!=null&&!info.getPhone().trim().equals("")){
				mob=info.getPhone()+"(M)";
			}
			if(info.getTel()!=null&&!info.getTel().trim().equals("")){
				tel=info.getTel()+"(T)";
			}
			if(!mob.equals("")&&!tel.equals("")){
				phone=mob+","+tel;
			}else if(!mob.equals("")){
				phone=mob;
			}else if(!tel.equals("")){
				phone=tel;
			}
			if(i==this.customer.size()-1){
				customerNames=customerNames+info.getName();
				customerPhone=customerPhone+phone;
			}else{
				customerNames=customerNames+info.getName()+";";
				customerPhone=customerPhone+phone+";";
			}
			editData.getCustomerEntry().add(info);
		}
		editData.setCustomerNames(customerNames);
		editData.setCustomerPhone(customerPhone);
	}
	protected void addActionListener(AbstractButton com) {
		EventListener[] listeners = (EventListener[]) listenersMap.get(com);
		if (listeners != null && listeners.length > 0) {
			for (int i = 0; i < listeners.length; i++) {
				com.addActionListener((ActionListener) listeners[i]);
			}
		}
	}
	protected void removeActionListener(AbstractButton com) {
		EventListener[] listeners = com.getListeners(ActionListener.class);
		if (listeners == null) {
			return;
		}
		for (int i = 0; i < listeners.length; i++) {
			com.removeActionListener((ActionListener) listeners[i]);
		}
		if (listeners.length > 0) {
			listenersMap.put(com, listeners);
		}
	}
	protected void attachListeners() {
		addDataChangeListener(this.f7Room);
		addDataChangeListener(this.f7PayType);
		addDataChangeListener(this.f7AgioScheme);
		addDataChangeListener(this.comboBizType);
		addDataChangeListener(this.txtQRFeeAmount);
		addDataChangeListener(this.txtFitmentAmount);
		addActionListener(this.chkIsFitmentToContract);
		addDataChangeListener(this.txtDealTotalAmount);
		
	}

	protected void detachListeners() {
		removeDataChangeListener(this.f7Room);
		removeDataChangeListener(this.f7PayType);
		removeDataChangeListener(this.f7AgioScheme);
		removeDataChangeListener(this.comboBizType);
		removeDataChangeListener(this.txtQRFeeAmount);
		removeDataChangeListener(this.txtFitmentAmount);
		removeActionListener(this.chkIsFitmentToContract);
		removeDataChangeListener(this.txtDealTotalAmount);
	}
	protected void setAuditButtonStatus(String oprtType){
		if(getNewBillUI(this.panelNewBill)!=null){
			getNewBillUI(this.panelNewBill).setAuditButtonStatus(oprtType);
		}
		if(STATUS_VIEW.equals(oprtType)) {
    		actionAudit.setVisible(true);
    		actionUnAudit.setVisible(true);
    		actionAudit.setEnabled(true);
    		actionUnAudit.setEnabled(true);
    		
    		this.actionAddLine.setEnabled(false);
    		this.actionRemoveLine.setEnabled(false);
    		this.actionInsertLine.setEnabled(false);
    		this.actionSelectCustomer.setEnabled(false);
    		this.actionChooseAgio.setEnabled(false);
    		
    		FDCBillInfo bill = (FDCBillInfo)editData;
    		if(editData!=null){
    			if(FDCBillStateEnum.AUDITTED.equals(bill.getState())){
    	    		actionUnAudit.setVisible(true);
    	    		actionUnAudit.setEnabled(true);   
    	    		actionAudit.setVisible(false);
    	    		actionAudit.setEnabled(false);
    			}else{
    	    		actionUnAudit.setVisible(false);
    	    		actionUnAudit.setEnabled(false);   
    	    		actionAudit.setVisible(true);
    	    		actionAudit.setEnabled(true);
    			}
    		}
    	}else {
    		actionAudit.setVisible(false);
    		actionUnAudit.setVisible(false);
    		actionAudit.setEnabled(false);
    		actionUnAudit.setEnabled(false);
    		
    		this.actionAddLine.setEnabled(true);
    		this.actionRemoveLine.setEnabled(true);
    		this.actionInsertLine.setEnabled(true);
    		this.actionSelectCustomer.setEnabled(true);
    		this.actionChooseAgio.setEnabled(true);
    	}
    }
	protected void setF7PayTypeFilter() throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("validDate", FDCDateHelper.addDays(new Date(), 1), CompareType.LESS_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", new Date(), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", null, CompareType.IS));
		
		filter.getFilterItems().add(new FilterItemInfo("project.id", SHEManageHelper.getAllParentSellProjectCollection(((BaseTransactionInfo)editData).getSellProject(),new HashSet()),CompareType.INCLUDE));
		filter.setMaskString("#0 and #1 and (#2 or #3 )and #4 )");
		view.setFilter(filter);
		this.f7PayType.setEntityViewInfo(view);
	}
	protected EntityViewInfo setF7AgioSchemeFilter() throws BOSException, EASBizException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("validDate", FDCDateHelper.addDays(new Date(), 1), CompareType.LESS_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", new Date(), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", null, CompareType.IS));

		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", SHEManageHelper.getAllParentSellProjectCollection(((BaseTransactionInfo)editData).getSellProject(),new HashSet()),CompareType.INCLUDE));
		filter.setMaskString("#0 and #1 and (#2 or #3 )and #4 )");
		view.setFilter(filter);
		this.f7AgioScheme.setEntityViewInfo(view);
		
		return view;
	}
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = getSelectBOID();
        if(boID == null)
        {
            return;
        } 
        acm.showAttachmentListUIByBoID(boID,this,true);
	}

	public void actionChooseAgio_actionPerformed(ActionEvent e) throws Exception {
		if (this.f7Room.getValue() == null) {
			FDCMsgBox.showWarning(this,"请先选择房间！");
			return;
		}
		RoomInfo room = (RoomInfo) this.f7Room.getValue();
		AgioParam agioParam = AgioSelectUI.showUI(this, room.getId().toString(), 
				(AgioEntryCollection)this.txtAgio.getUserObject(), this.currAgioParam ,this.editData,isAgioListCanEdit,false);
		if (agioParam != null&&!this.currAgioParam.equals(agioParam)) {
			this.currAgioParam = agioParam;
			this.txtAgio.setUserObject(agioParam.getAgios());
			this.updateAmount();
		}
	}
	protected String getAgioDes(Object[] values) {
		if(values==null) return null;
		AgioEntryCollection agioEntrys = new AgioEntryCollection();
		for (int i = 0; i < values.length; i++) {
			AgioEntryInfo entryInfo = (AgioEntryInfo)values[i];
			agioEntrys.add(entryInfo);
		}
		return SHEHelper.getAgioDes(agioEntrys,null,null,null, false,false,SHEHelper.DEFAULT_TO_INTEGER_TYPE, SHEHelper.DEFAULT_DIGIT);
	}
	protected void clearTblAttach(IRow row){
		row.getCell(AP_ROOMNUMBER).setValue(null);
		row.getCell(AP_BUILDINGAREA).setValue(null);
		row.getCell(AP_ROOMAREA).setValue(null);
		row.getCell(AP_STANDARDTOTALAMOUNT).setValue(null);
		row.getCell(AP_MERGEAMOUNT).setValue(null);
		row.getCell(AP_AGIO).setValue(null);
		row.getCell(AP_AGIO).getStyleAttributes().setLocked(true);
	}
	protected void tblAttachProperty_editStopped(KDTEditEvent e) throws Exception {
		IRow row = this.tblAttachProperty.getRow(e.getRowIndex());
		ChangeAgioEntryCollection changeCol=((ChangeRoomAttachEntryInfo)row.getUserObject()).getAgioEntry();
		if(e.getColIndex() == tblAttachProperty.getColumnIndex(AP_ROOMNUMBER)){
			if(e.getValue()!=null){
				RoomInfo room = (RoomInfo)row.getCell(AP_ROOMNUMBER).getValue();
				verifyAddNewAttachRoom(room,row);
				updateAttachRoomInfo(room,row);
				setEntryAgio(row,changeCol,room);

				updateAmount();
			}else{
				clearTblAttach(row);
			}
		}
		if(e.getColIndex() == tblAttachProperty.getColumnIndex(AP_AGIO)){
			if(e.getValue()==null){
				changeCol.clear();
			}else if(!e.getValue().equals(e.getOldValue())){
				Object[] obj=(Object[]) e.getValue();
				changeCol.clear();
				for (int i = 0; i < obj.length; i++) {
					ChangeAgioEntryInfo entryInfo = (ChangeAgioEntryInfo)obj[i];
					changeCol.add(entryInfo);
				}
				setEntryAgio(row,changeCol,(RoomInfo)row.getCell(AP_ROOMNUMBER).getValue());
				
				updateAmount();
			}
		}
		if(e.getColIndex() == tblAttachProperty.getColumnIndex(AP_ISMERGETOCONTRACT)||e.getColIndex()==tblAttachProperty.getColumnIndex(AP_MERGEAMOUNT)){
			updateAmount();
		}
	}
	protected void setEntryAgio(IRow row,ChangeAgioEntryCollection changeCol,RoomInfo room) throws UIException{
		AgioParam currEntryAgioParam=new AgioParam();
		currEntryAgioParam.setBasePriceSell(editData.isIsBasePriceSell());
		currEntryAgioParam.setToIntegerType(SHEHelper.DEFAULT_TO_INTEGER_TYPE);
		currEntryAgioParam.setDigit(SHEHelper.DEFAULT_DIGIT);
		if(CalcTypeEnum.PriceByBuildingArea.equals(room.getCalcType())||CalcTypeEnum.PriceByRoomArea.equals(room.getCalcType())){
			currEntryAgioParam.setPriceAccountType(PriceAccountTypeEnum.PriceSetStand);
		}else{
			currEntryAgioParam.setPriceAccountType(PriceAccountTypeEnum.StandSetPrice);
		}
		AgioEntryCollection agioEntrys = currEntryAgioParam.getAgios();
		for (int i = 0; i < changeCol.size(); i++) {
			ChangeAgioEntryInfo entryInfo = changeCol.get(i);
			agioEntrys.add(entryInfo);
		}
		KDBizPromptBox f7AgioBox = new KDBizPromptBox();
		f7AgioBox.setEnabledMultiSelection(true);
		f7AgioBox.setSelector(new AgioPromptDialog(this, ((RoomInfo)row.getCell(AP_ROOMNUMBER).getValue()).getId().toString(), agioEntrys, currEntryAgioParam, editData,isAgioListCanEdit));
		f7AgioBox.setEditable(false);
		if(row.getCell(AP_AGIO).getValue()!=null){
			f7AgioBox.setDisplayFormat("");
		}else{
			f7AgioBox.setDisplayFormat(null);
		}
		KDTDefaultCellEditor f7AgioEditor = new KDTDefaultCellEditor(f7AgioBox);
		row.getCell(AP_AGIO).setEditor(f7AgioEditor);
		PurchaseParam purParam = SHEManageHelper.getPurchaseAgioParam(currEntryAgioParam, (RoomInfo)row.getCell(AP_ROOMNUMBER).getValue(), 
				room.getSellType(), false, null, null, null ,null, null,null);
		
		if(changeCol.size()>0){
			row.getCell(AP_MERGEAMOUNT).setValue(purParam.getDealTotalAmount());
		}else{
			row.getCell(AP_MERGEAMOUNT).setValue(row.getCell(AP_STANDARDTOTALAMOUNT).getValue());
		}
	}
	protected void chkIsFitmentToContract_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		updateAmount();
	}
	protected void txtFitmentAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		updateAmount();
	}
	protected void updateAttachRoomInfo(RoomInfo room,IRow row){
    	BigDecimal buildingArea = FDCHelper.ZERO;
		BigDecimal roomArea = FDCHelper.ZERO;
		if(SellTypeEnum.LocaleSell.equals(room.getSellType())){	
			buildingArea = room.getActualBuildingArea();
			roomArea = room.getActualRoomArea();
		}else if(SellTypeEnum.PreSell.equals(room.getSellType())){	
			buildingArea = room.getBuildingArea();
			roomArea = room.getRoomArea();
		}else {
			buildingArea = room.getPlanBuildingArea();
			roomArea = room.getPlanRoomArea();
		}
		row.getCell(AP_BUILDINGAREA).setValue(buildingArea);
		row.getCell(AP_ROOMAREA).setValue(roomArea);
		row.getCell(AP_BUILDINGPRICE).setValue(room.getBuildPrice());
		row.getCell(AP_ROOMPRICE).setValue(room.getRoomPrice());
		row.getCell(AP_STANDARDTOTALAMOUNT).setValue(room.getStandardTotalAmount());
		
		row.getCell(AP_AGIO).getStyleAttributes().setLocked(false);
    }
	protected void updateAmount(){
		isEditDealAmount=false;
		if(!loadPricePanel){
    		return;
    	}
		BigDecimal standardTotalAmount=this.txtStandardTotalAmount.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtStandardTotalAmount.getBigDecimalValue();
		BigDecimal mergeAttachmentTotalAmount=this.getMergeContractAttachmentTotalAmount(true);
		BigDecimal totalattachmentTotalAmount=FDCHelper.ZERO;
		for(int i=0;i<tblAttachProperty.getRowCount();i++){
			IRow row=tblAttachProperty.getRow(i);
			if(row.getCell(AP_STANDARDTOTALAMOUNT).getValue()!=null){
				totalattachmentTotalAmount=totalattachmentTotalAmount.add((BigDecimal)row.getCell(AP_STANDARDTOTALAMOUNT).getValue());
			}
		}
		this.txtAttachPropertyTotalAmount.setValue(totalattachmentTotalAmount);
		this.txtDealTotalAmount.setValue(standardTotalAmount);
		
		this.txtDealBuildPrice.setValue(this.txtBuildingArea.getBigDecimalValue()==null||this.txtBuildingArea.getBigDecimalValue().compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:this.txtDealTotalAmount.getBigDecimalValue().divide(this.txtBuildingArea.getBigDecimalValue(),2,BigDecimal.ROUND_HALF_UP));
		this.txtDealRoomPrice.setValue(this.txtRoomArea.getBigDecimalValue()==null||this.txtRoomArea.getBigDecimalValue().compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:this.txtDealTotalAmount.getBigDecimalValue().divide(this.txtRoomArea.getBigDecimalValue(),2,BigDecimal.ROUND_HALF_UP));
		
		BigDecimal fitmentAmount=this.txtFitmentAmount.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtFitmentAmount.getBigDecimalValue();
		this.txtFitmentPrice.setValue(this.txtBuildingArea.getBigDecimalValue()==null||this.txtBuildingArea.getBigDecimalValue().compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:fitmentAmount.divide(this.txtBuildingArea.getBigDecimalValue(),2,BigDecimal.ROUND_HALF_UP));
		
		if(this.chkIsFitmentToContract.isSelected()){
			this.txtContractTotalAmount.setValue(this.txtDealTotalAmount.getBigDecimalValue().add(fitmentAmount).add(mergeAttachmentTotalAmount));
		} else {
			this.txtContractTotalAmount.setValue(this.txtDealTotalAmount.getBigDecimalValue().add(mergeAttachmentTotalAmount));
		}
		
		this.txtContractBuildPrice.setValue(this.txtBuildingArea.getBigDecimalValue()==null||this.txtBuildingArea.getBigDecimalValue().compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:this.txtContractTotalAmount.getBigDecimalValue().divide(this.txtBuildingArea.getBigDecimalValue(),2,BigDecimal.ROUND_HALF_UP));
		this.txtContractRoomPrice.setValue(this.txtRoomArea.getBigDecimalValue()==null||this.txtRoomArea.getBigDecimalValue().compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:this.txtContractTotalAmount.getBigDecimalValue().divide(this.txtRoomArea.getBigDecimalValue(),2,BigDecimal.ROUND_HALF_UP));
   
		
		
		BigDecimal dealPrice=FDCHelper.ZERO;
		if(CalcTypeEnum.PriceByBuildingArea.equals(this.comboValuationType.getSelectedItem())){
			dealPrice=this.txtDealBuildPrice.getBigDecimalValue();
		}
		if(CalcTypeEnum.PriceByRoomArea.equals(this.comboValuationType.getSelectedItem())){
			dealPrice=this.txtDealRoomPrice.getBigDecimalValue();
		}
		BigDecimal preArea=this.txtPreArea.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtPreArea.getBigDecimalValue();
		BigDecimal planningArea=this.txtPlanningArea.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtPlanningArea.getBigDecimalValue();
		BigDecimal actualArea=this.txtActualArea.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtActualArea.getBigDecimalValue();
		
		if(srcInfo!=null&&objectValue instanceof SignManageInfo){
			if(SellTypeEnum.LocaleSell.equals((SellTypeEnum)this.comboSellType.getSelectedItem())){
				if(SellTypeEnum.LocaleSell.equals(srcInfo.getSellType())&&srcInfo instanceof PurchaseManageInfo){
					this.txtPlanningCompensate.setValue(((PurchaseManageInfo)srcInfo).getPlanningCompensate());
					this.txtCashSalesCompensate.setValue(((PurchaseManageInfo)srcInfo).getCashSalesCompensate());
				}else if(SellTypeEnum.PreSell.equals(srcInfo.getSellType())&&srcInfo instanceof PurchaseManageInfo){
					this.txtPlanningCompensate.setValue(FDCHelper.ZERO);
					this.txtCashSalesCompensate.setValue((actualArea.subtract(preArea)).multiply(dealPrice));
				}else if((SellTypeEnum.PlanningSell.equals(srcInfo.getSellType())||SellTypeEnum.PreSell.equals(srcInfo.getSellType()))
						&&srcInfo instanceof PrePurchaseManageInfo){
					this.txtPlanningCompensate.setValue(FDCHelper.ZERO);
					this.txtCashSalesCompensate.setValue((actualArea.subtract(planningArea)).multiply(dealPrice));
				}else{
					this.txtPlanningCompensate.setValue(FDCHelper.ZERO);
					this.txtCashSalesCompensate.setValue(FDCHelper.ZERO);
				}
			}
			if(SellTypeEnum.PreSell.equals((SellTypeEnum)this.comboSellType.getSelectedItem())){
				if(SellTypeEnum.PreSell.equals(srcInfo.getSellType())&&srcInfo instanceof PurchaseManageInfo){
					this.txtPlanningCompensate.setValue(((PurchaseManageInfo)srcInfo).getPlanningCompensate());
					this.txtCashSalesCompensate.setValue(FDCHelper.ZERO);
				}else if(SellTypeEnum.PlanningSell.equals(srcInfo.getSellType())&&srcInfo instanceof PrePurchaseManageInfo){
					this.txtPlanningCompensate.setValue((preArea.subtract(planningArea)).multiply(dealPrice));
					this.txtCashSalesCompensate.setValue(FDCHelper.ZERO);
				}else{
					this.txtPlanningCompensate.setValue(FDCHelper.ZERO);
					this.txtCashSalesCompensate.setValue(FDCHelper.ZERO);
				}
			}
		}
		if(srcInfo!=null&&objectValue instanceof PurchaseManageInfo){
			if(SellTypeEnum.LocaleSell.equals((SellTypeEnum)this.comboSellType.getSelectedItem())){
				if(SellTypeEnum.PreSell.equals(srcInfo.getSellType())&&srcInfo instanceof PrePurchaseManageInfo){
					this.txtPlanningCompensate.setValue(FDCHelper.ZERO);
					this.txtCashSalesCompensate.setValue((actualArea.subtract(preArea)).multiply(dealPrice));
				}else if(SellTypeEnum.PlanningSell.equals(srcInfo.getSellType())&&srcInfo instanceof PrePurchaseManageInfo){
					this.txtPlanningCompensate.setValue(FDCHelper.ZERO);
					this.txtCashSalesCompensate.setValue((actualArea.subtract(planningArea)).multiply(dealPrice));
				}else{
					this.txtPlanningCompensate.setValue(FDCHelper.ZERO);
					this.txtCashSalesCompensate.setValue(FDCHelper.ZERO);
				}
			}
			if(SellTypeEnum.PreSell.equals((SellTypeEnum)this.comboSellType.getSelectedItem())){
				if(SellTypeEnum.PlanningSell.equals(srcInfo.getSellType())&&srcInfo instanceof PrePurchaseManageInfo){
					this.txtPlanningCompensate.setValue((preArea.subtract(planningArea)).multiply(dealPrice));
					this.txtCashSalesCompensate.setValue(FDCHelper.ZERO);
				}else{
					this.txtPlanningCompensate.setValue(FDCHelper.ZERO);
					this.txtCashSalesCompensate.setValue(FDCHelper.ZERO);
				}
			}
		}
		BigDecimal contractTotalAmount=this.txtContractTotalAmount.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtContractTotalAmount.getBigDecimalValue();
		BigDecimal cashSalesCompensate=this.txtCashSalesCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtCashSalesCompensate.getBigDecimalValue();
		BigDecimal planningCompensate=this.txtPlanningCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtPlanningCompensate.getBigDecimalValue();
		BigDecimal areaCompensate=this.txtAreaCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtAreaCompensate.getBigDecimalValue();
		
		BigDecimal allCompensate=cashSalesCompensate.add(planningCompensate).add(areaCompensate);
		
		this.txtSellAmount.setValue(contractTotalAmount.add(allCompensate));
	
		updataRoomContractAndDealAmount();
		updateRoomAttactAndFittmentMD();
		updatePayList();
		isEditDealAmount=true;
    }
	protected void updatePayList(){
		BigDecimal contractTotalAmount=this.txtContractTotalAmount.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtContractTotalAmount.getBigDecimalValue();
		SHEManageHelper.updatePayList(contractTotalAmount, this.tblPCPayList, ((BaseTransactionInfo)this.editData).isIsEarnestInHouseAmount());
	}
	protected BigDecimal getMergeContractAttachmentTotalAmount(boolean isMerger){
		BigDecimal attachmentTotalAmount=FDCHelper.ZERO;
		for(int i=0;i<tblAttachProperty.getRowCount();i++){
			IRow row=tblAttachProperty.getRow(i);
			Boolean isOk = new Boolean(row.getCell(AP_ISMERGETOCONTRACT).getValue().toString()); 
			BigDecimal amount=row.getCell(AP_MERGEAMOUNT).getValue()==null?FDCHelper.ZERO:(BigDecimal)row.getCell(AP_MERGEAMOUNT).getValue();
			if(isMerger){
				if(isOk.booleanValue()){
					attachmentTotalAmount=attachmentTotalAmount.add(amount);
				}
			}else{
				if(!isOk.booleanValue()){
					attachmentTotalAmount=attachmentTotalAmount.add(amount);
				}
			}
		}
		return attachmentTotalAmount;
	}
	protected IRow getRoomAttactMoneyDefineRow(){
		for(int i=0;i<this.tblPCPayList.getRowCount();i++){
			IRow row=this.tblPCPayList.getRow(i);
			if(this.roomAttactMoneyDefine.equals(row.getCell(PL_MONEYNAME).getValue())){
				return row;
			}
		}
		return null;
	}
	protected IRow getFittmentMoneyDefineRow(){
		for(int i=0;i<this.tblPCPayList.getRowCount();i++){
			IRow row=this.tblPCPayList.getRow(i);
			if(this.fittmentMoneyDefine.equals(row.getCell(PL_MONEYNAME).getValue())){
				return row;
			}
		}
		return null;
	}
	protected void updateRoomAttactAndFittmentMD(){
		IRow row=getFittmentMoneyDefineRow();
		if(!this.chkIsFitmentToContract.isSelected()&&this.txtFitmentAmount.getBigDecimalValue()!=null&&this.txtFitmentAmount.getBigDecimalValue().compareTo(FDCHelper.ZERO)>0){
			if(row!=null){
				row.getCell(PL_APPAMOUNT).setValue(this.txtFitmentAmount.getBigDecimalValue());
				row.getCell(PL_BALANCE).setValue(this.txtFitmentAmount.getBigDecimalValue());
			}else{
				row=this.tblPCPayList.addRow();
				row.setUserObject(this.createNewPayListData());
				row.getCell(PL_MONEYNAME).setValue(this.fittmentMoneyDefine);
				row.getCell(PL_APPAMOUNT).setValue(this.txtFitmentAmount.getBigDecimalValue());
				row.getCell(PL_BALANCE).setValue(this.txtFitmentAmount.getBigDecimalValue());
				row.getCell(PL_STATE).setValue(new Boolean(false));
				row.getCell(PL_CURRENCY).setValue(currency);
			}
		}else{
			if(row!=null){
				this.tblPCPayList.removeRow(row.getRowIndex());
			}
		}
		IRow raRow=getRoomAttactMoneyDefineRow();
		BigDecimal attachmentTotalAmount=getMergeContractAttachmentTotalAmount(false);
		
		if(attachmentTotalAmount.compareTo(FDCHelper.ZERO)>0){
			if(raRow!=null){
				raRow.getCell(PL_APPAMOUNT).setValue(attachmentTotalAmount);
				raRow.getCell(PL_BALANCE).setValue(attachmentTotalAmount);
			}else{
				row=this.tblPCPayList.addRow();
				row.setUserObject(this.createNewPayListData());
				row.getCell(PL_MONEYNAME).setValue(this.roomAttactMoneyDefine);
				row.getCell(PL_APPAMOUNT).setValue(attachmentTotalAmount);
				row.getCell(PL_BALANCE).setValue(attachmentTotalAmount);
				row.getCell(PL_STATE).setValue(new Boolean(false));
				row.getCell(PL_CURRENCY).setValue(currency);
			}
		}else{
			if(raRow!=null){
				this.tblPCPayList.removeRow(raRow.getRowIndex());
			}
		}
	}
    //选择折扣后更新
    protected void updataRoomContractAndDealAmount(){
    	RoomInfo roomInfo = (RoomInfo) this.f7Room.getValue();
		if(roomInfo==null) return;
		
		isEditDealAmount=false;
		
		SellTypeEnum sellType = (SellTypeEnum)this.comboSellType.getSelectedItem();
		CalcTypeEnum valuationType = (CalcTypeEnum)this.comboValuationType.getSelectedItem();
		
		BigDecimal roomArea=this.txtRoomArea.getBigDecimalValue();
		BigDecimal buildingArea=this.txtBuildingArea.getBigDecimalValue();
		BigDecimal roomPrice=this.txtRoomPrice.getBigDecimalValue();
		BigDecimal buildingPrice=this.txtBuildingPrice.getBigDecimalValue();
		BigDecimal standardAmount=this.txtStandardTotalAmount.getBigDecimalValue();
		
		
		BigDecimal fitmentAmount = this.txtFitmentAmount.getBigDecimalValue();
		BigDecimal attachmentAmount =getMergeContractAttachmentTotalAmount(true);
		
		BigDecimal cashSalesCompensate=this.txtCashSalesCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtCashSalesCompensate.getBigDecimalValue();
		BigDecimal planningCompensate=this.txtPlanningCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtPlanningCompensate.getBigDecimalValue();
		BigDecimal areaCompensate=this.txtAreaCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtAreaCompensate.getBigDecimalValue();
		BigDecimal allCompensate=cashSalesCompensate.add(planningCompensate).add(areaCompensate);
		
		boolean isFitmentToContract = this.chkIsFitmentToContract.isSelected();
		SHEPayTypeInfo payType = (SHEPayTypeInfo) this.f7PayType.getValue();
		String payTypeName = null;
		BigDecimal payTypeAgio = FDCHelper.ONE_HUNDRED;
		if (payType != null) {
			payTypeName = payType.getName();
			payTypeAgio = payType.getAgio();
			if(payTypeAgio == null){
				payTypeAgio = FDCHelper.ONE_HUNDRED;
			}
		}
		PurchaseParam purParam = SHEManageHelper.getAgioParam(this.currAgioParam, roomInfo, 
				 sellType,valuationType,isFitmentToContract,roomArea,buildingArea,roomPrice,buildingPrice,standardAmount,fitmentAmount, attachmentAmount, allCompensate ,SpecialAgioEnum.DaZhe, payTypeAgio,payTypeName);
		if(purParam!=null) {
			this.txtAgioDes.setText(purParam.getAgioDes());
			this.txtAgio.setValue(purParam.getFinalAgio());
			this.txtDealTotalAmount.setValue(purParam.getDealTotalAmount());
			this.txtContractTotalAmount.setValue(purParam.getContractTotalAmount());
			this.txtContractBuildPrice.setValue(purParam.getContractBuildPrice());
			this.txtContractRoomPrice.setValue(purParam.getContractRoomPrice());
			this.txtDealBuildPrice.setValue(purParam.getDealBuildPrice());
			this.txtDealRoomPrice.setValue(purParam.getDealRoomPrice());
			this.txtSellAmount.setValue(purParam.getContractTotalAmount().add(allCompensate));
		}
		isEditDealAmount=true;
    }
    protected void f7PayType_dataChanged(DataChangeEvent e) throws Exception {
    	if (e.getNewValue() == null) {
			editData.setIsEarnestInHouseAmount(true);
			this.updateAmount();
			return;
		}
    	if (this.f7Room.getValue() == null) {
			FDCMsgBox.showWarning(this,"请先选择房间！");
			this.f7PayType.setValue(null);
			return;
		}
		SHEPayTypeInfo payType = (SHEPayTypeInfo) this.f7PayType.getValue();
		
		editData.setIsEarnestInHouseAmount(payType.isIsTotal());
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		view.getSelector().add("*");
		view.getSelector().add("moneyDefine.*");
		view.getSelector().add("currency.*");
		filter.getFilterItems().add(new FilterItemInfo("head.id", payType.getId().toString()));
		
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("seq"));
		
		PayListEntryCollection payList = PayListEntryFactory.getRemoteInstance().getPayListEntryCollection(view);
		payType.getPayLists().clear();
		payType.getPayLists().addCollection(payList);
		
		//计算约定签约日期
		EntityViewInfo viewBiz = new EntityViewInfo();
		FilterInfo filterbiz = new FilterInfo();
		
		filterbiz.getFilterItems().add(new FilterItemInfo("head.id", payType.getId().toString()));
		viewBiz.setFilter(filterbiz);
		viewBiz.getSorter().add(new SorterItemInfo("seq"));
		
		BizListEntryCollection bizList = BizListEntryFactory.getRemoteInstance().getBizListEntryCollection(viewBiz);

		Date date = (Date)this.pkBizDate.getValue();
		if(date==null){
			date = new Date();
		}
		if(bizList.size()>0){
			BizListEntryInfo bizListInfo = null;
			for(int i = 0; i < bizList.size(); i++){
				bizListInfo  = bizList.get(i);
				if(BizFlowEnum.Sign.equals(bizListInfo.getBizFlow()) ){
					if(BizTimeEnum.Purchase.equals(bizListInfo.getBizTime())){
						int monthLimit = bizListInfo.getMonthLimit();
						int dayLimit = bizListInfo.getDayLimit();
						date = addMonthsDays(date,monthLimit,dayLimit);
						break;
					}else if(BizTimeEnum.AppTime.equals(bizListInfo.getBizTime())){
						date = bizListInfo.getAppDate();
						break;
					}
					
				}
			}
			this.pkBizDate.setValue(date);
		}
		this.updataRoomContractAndDealAmount();
		this.updatePayListByPayType();
	}
    protected Date addMonthsDays(Date date,int month,int day)
    {
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(date);
    	calendar.add(Calendar.MONTH,month);
    	calendar.add(Calendar.DATE,day);
    	return calendar.getTime();
    }
    protected void updatePayListByPayType() {
    	try {
    		MoneyDefineInfo moneyDefine=null;
    		if(preMoneyDefine==null){
    			moneyDefine=purMoneyDefine;
    		}else{
    			moneyDefine=preMoneyDefine;
    		}
    		SHEPayTypeBizTimeEnum billState=null;
    		if(objectValue instanceof PrePurchaseManageInfo){
    			billState=SHEPayTypeBizTimeEnum.PREREGISTER;
			}
			if(objectValue instanceof PurchaseManageInfo){
				billState=SHEPayTypeBizTimeEnum.PURCHASE;
			}
			if(objectValue instanceof SignManageInfo){
				billState=SHEPayTypeBizTimeEnum.SIGN;
			}
			boolean isAddFittment=true;
			boolean isAddRoomAttach=true;
			if(!this.chkIsFitmentToContract.isSelected()&&this.txtFitmentAmount.getBigDecimalValue()!=null&&this.txtFitmentAmount.getBigDecimalValue().compareTo(FDCHelper.ZERO)>0){
				isAddFittment=false;
			}
			BigDecimal attachmentTotalAmount=getMergeContractAttachmentTotalAmount(false);
			
			if(attachmentTotalAmount.compareTo(FDCHelper.ZERO)>0){
				isAddRoomAttach=false;
			}
			IRow fittment=null;
			IRow roomAttach=null;
			if(!isAddFittment){
				fittment=getFittmentMoneyDefineRow();
			}
			if(!isAddRoomAttach){
				roomAttach=getRoomAttactMoneyDefineRow();
			}
			List toAddRowEntry = SHEManageHelper.updatePayListByPayType((SHEPayTypeInfo) this.f7PayType.getValue(), this.editData.isIsEarnestInHouseAmount(), this.tblPCPayList, fittment, roomAttach, moneyDefine, 
					this.txtContractTotalAmount.getBigDecimalValue(), this.txtDealTotalAmount.getBigDecimalValue(),this.txtStandardTotalAmount.getBigDecimalValue()
					, this.txtBuildingArea.getBigDecimalValue(), this.txtRoomArea.getBigDecimalValue(),(RoomInfo) this.f7Room.getValue(), digit, priceToIntegerType,(Date)this.pkBizDate.getValue(),billState,isAddFittment,isAddRoomAttach);
			if ( this.f7PayType.getValue() != null) {
				int rowCount=0;
				for (int i = 0; i < toAddRowEntry.size(); i++) {
					IRow row =null;
					if(i==0){
						row = this.tblPCPayList.addRow(0);
					}else{
						row = this.tblPCPayList.addRow(rowCount+1);
					}
					ChangePayListEntryInfo entry=new ChangePayListEntryInfo();
					SHEManageHelper.setPayListEntry(entry, (TranPayListEntryInfo) toAddRowEntry.get(i),true);
					row.setUserObject(entry);
					row.getCell(PL_MONEYNAME).setValue(entry.getMoneyDefine());
					row.getCell(PL_APPDATE).setValue(entry.getAppDate());
					row.getCell(PL_APPAMOUNT).setValue(entry.getAppAmount());
					row.getCell(PL_BALANCE).setValue(entry.getAppAmount());
					row.getCell(PL_CURRENCY).setValue(entry.getCurrency());
					row.getCell(PL_STATE).setValue(new Boolean(false));
					
					rowCount=row.getRowIndex();
				}
				updateLoanAndAFAmount();
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	protected void tblPCPayList_editStopped(KDTEditEvent e) throws Exception {
		boolean isEarnestInHouseAmount = ((BaseTransactionInfo)this.editData).isIsEarnestInHouseAmount();
		BigDecimal contractAmount = this.txtContractTotalAmount.getBigDecimalValue();
		if (contractAmount != null && e.getRowIndex() >= 0) {
			contractAmount=contractAmount.setScale(digit, toIntegerType);
			IRow nextRow = this.tblPCPayList.getRow(e.getRowIndex() + 1);
			if (nextRow != null) {
				BigDecimal nextAmount = contractAmount;
				for (int i = 0; i < this.tblPCPayList.getRowCount(); i++) {
					if (i != (e.getRowIndex() + 1)) {
						IRow perRow = this.tblPCPayList.getRow(i);
						BigDecimal purAmount = (BigDecimal) perRow.getCell(PL_APPAMOUNT).getValue();
						if (purAmount == null)
							purAmount = FDCHelper.ZERO;
						
						MoneyDefineInfo moneyDefine = (MoneyDefineInfo) perRow.getCell(PL_MONEYNAME).getValue();
						
						if(moneyDefine != null) {
							if(!SHEManageHelper.isMergerToContractMoneyType(moneyDefine.getMoneyType(),isEarnestInHouseAmount)){
								continue;
							}
						}
						nextAmount = nextAmount.subtract(purAmount);
					}
				}
				MoneyDefineInfo nextMoneyDefine= (MoneyDefineInfo) nextRow.getCell(PL_MONEYNAME).getValue();
				
				if(nextMoneyDefine != null) {
					if(SHEManageHelper.isMergerToContractMoneyType(nextMoneyDefine.getMoneyType(),isEarnestInHouseAmount)){
						nextRow.getCell(PL_APPAMOUNT).setValue(nextAmount);
					}
				}
			}
		}
		this.updateLoanAndAFAmount();
	}
	protected void updateLoanAndAFAmount() {
		BigDecimal loanAmount = FDCHelper.ZERO;
		BigDecimal afAmount = FDCHelper.ZERO;

		for (int i = 0; i < this.tblPCPayList.getRowCount(); i++) {
			IRow row = this.tblPCPayList.getRow(i);
			if (!(row.getCell(PL_MONEYNAME).getValue() instanceof MoneyDefineInfo)) {
				continue;
			}
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell(PL_MONEYNAME).getValue();
			BigDecimal amount = (BigDecimal) row.getCell(PL_APPAMOUNT).getValue();
			if (amount == null) {
				amount = FDCHelper.ZERO;
			}
			if (moneyName != null) {
				if (moneyName.getMoneyType().equals(MoneyTypeEnum.LoanAmount)) {
					loanAmount = loanAmount.add(amount);
				} else if (moneyName.getMoneyType().equals(MoneyTypeEnum.AccFundAmount)) {
					afAmount = afAmount.add(amount);
				}
			}
		}
		this.txtLoanAmount.setValue(loanAmount);
		this.txtAFundAmount.setValue(afAmount);
	}
	private void addPayListEntryRow(TranPayListEntryInfo entry,KDTable table) {
		IRow row = table.addRow();
		row.setUserObject(entry);
		row.getCell(PL_MONEYNAME).setValue(entry.getMoneyDefine());
		row.getCell(PL_APPDATE).setValue(entry.getAppDate());
		row.getCell(PL_APPAMOUNT).setValue(entry.getAppAmount());
		row.getCell(PL_CURRENCY).setValue(entry.getCurrency());
		row.getCell(PL_DES).setValue(entry.getDescription());
		row.getCell(PL_ACTAMOUNT).setValue(entry.getActRevAmount());

		BigDecimal apAmount = entry.getAppAmount()==null?FDCHelper.ZERO:entry.getAppAmount();
		BigDecimal actAmount = entry.getActRevAmount()==null?FDCHelper.ZERO:entry.getActRevAmount();
		
		if (actAmount.compareTo(FDCHelper.ZERO) != 0) {
			row.getCell(PL_MONEYNAME).getStyleAttributes().setLocked(true);
			row.getCell(PL_APPDATE).getStyleAttributes().setLocked(true);
			row.getCell(PL_CURRENCY).getStyleAttributes().setLocked(true);
			if (actAmount.compareTo(apAmount) == 0) {
				row.getCell(PL_STATE).setValue(new Boolean(true));
			}else{
				row.getCell(PL_STATE).setValue(new Boolean(false));
			}
		}else{
			row.getCell(PL_STATE).setValue(new Boolean(false));
		}
		row.getCell(PL_BALANCE).setValue(apAmount.subtract(actAmount).compareTo(FDCHelper.ZERO)<0?FDCHelper.ZERO:apAmount.subtract(actAmount));
	}
	
	protected String getTDFileName() {
		return "/bim/fdc/sellhouse/ChangeManage";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.ChangeManagePrintQuery");
		
	}
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
			"cantPrint"));
			return;
		}
		HashMap value=new HashMap();
		value.put("roomArea", this.txtRoomArea.getBigDecimalValue());
		value.put("buildingArea", this.txtBuildingArea.getBigDecimalValue());
		ChangeManagePrintDataProvider data = new ChangeManagePrintDataProvider(
				editData.getString("id"), getTDQueryPK(),value);
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
			"cantPrint"));
			return;

		}
		HashMap value=new HashMap();
		value.put("roomArea", this.txtRoomArea.getBigDecimalValue());
		value.put("buildingArea", this.txtBuildingArea.getBigDecimalValue());
		ChangeManagePrintDataProvider data = new ChangeManagePrintDataProvider(
				editData.getString("id"), getTDQueryPK(),value);
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	protected void checkBeforeEditOrRemove(String warning) {
		FDCBillStateEnum state = editData.getState();
		if (state != null
				&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED || state == FDCBillStateEnum.CANCEL )) {
			if(warning.equals(CANTEDIT)){
				FDCMsgBox.showWarning("该单据不是保存或者提交状态，不能进行修改操作！");
				SysUtil.abort();
			}else{
				FDCMsgBox.showWarning("该单据不是保存或者提交状态，不能进行删除操作！");
				SysUtil.abort();
			}
		}
	}
	
	
	
	private void handleIntermitNumber(CoreBillBaseInfo info)	throws BOSException, CodingRuleException, EASBizException {
		if (info.getNumber() != null && info.getNumber().length() > 0)
			return;
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		boolean isExist = true;
		String costUnitId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		if (StringUtils.isEmpty(costUnitId)) {
			return;
		}
		
		if (!iCodingRuleManager.isExist(info, costUnitId)) {
			isExist = false;
		}
		
		if (isExist) {
			// 选择了断号支持或者没有选择新增显示,获取并设置编号
			if (iCodingRuleManager.isUseIntermitNumber(info, costUnitId)
					|| !iCodingRuleManager.isAddView(info, costUnitId))
			// 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
			{
				// 启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
				String number = iCodingRuleManager.getNumber(info, costUnitId);
				info.setNumber(number);
			}
		}
	}
	
}