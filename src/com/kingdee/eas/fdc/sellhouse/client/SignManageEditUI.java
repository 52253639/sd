/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.format.Colour;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.BankFactory;
import com.kingdee.eas.basedata.assistant.BankInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.MarketUnitSaleManFactory;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.CusClientHelper;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AfterServiceItemEnum;
import com.kingdee.eas.fdc.sellhouse.AfterServiceStateEnum;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.CalcTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeBizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SignAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SinPurSaleMansEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import com.kingdee.eas.fdc.sellhouse.AgioParam;
/**
 * output class name
 */
public class SignManageEditUI extends AbstractSignManageEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SignManageEditUI.class);
    protected final static String AS_SERVICEITEM = "serviceItem";// 服务项目
	protected final static String AS_APPDATE = "appDate";// 申请办理日期
	protected final static String AS_FINISHDATE = "finishDate";// 承诺完成日期
	protected final static String AS_SERVICESTATE = "serviceState";// 承诺完成日期
	public static final String KEY_SUBMITTED_DATA = "submittedData";
	public static final String KEY_DESTORY_WINDOW = "destoryWindowAfterSubmit";
	
    public SignManageEditUI() throws Exception
    {
        super();
    }

	protected ICoreBase getBizInterface() throws BOSException {
		return SignManageFactory.getRemoteInstance();
	}
	
	public void onLoad() throws Exception {
		initTblAfterService();
		super.onLoad();
	}
	
	protected void initTblAfterService(){
		this.tblAfterService.checkParsed();
		this.tblAfterService.getStyleAttributes().setLocked(true);
		KDComboBox comboField = new KDComboBox();
		List list = AfterServiceItemEnum.getEnumList();
		for (int i = 0; i < list.size(); i++) {
			comboField.addItem(list.get(i));
		}
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		this.tblAfterService.getColumn(AS_SERVICEITEM).setEditor(comboEditor);
		this.tblAfterService.getColumn(AS_APPDATE).setEditor(FDCClientHelper.getDateCellEditor());
		this.tblAfterService.getColumn(AS_FINISHDATE).setEditor(FDCClientHelper.getDateCellEditor());
		
		comboField = new KDComboBox();
		list = AfterServiceStateEnum.getEnumList();
		for (int i = 0; i < list.size(); i++) {
			comboField.addItem(list.get(i));
		}
		comboEditor = new KDTDefaultCellEditor(comboField);
		this.tblAfterService.getColumn(AS_SERVICESTATE).setEditor(FDCClientHelper.getDateCellEditor());
		
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
		
		setAuditButtonStatus(this.getOprtState());
		
		loadCommon();
		loadCustomerEntry(editData);
		loadReceiveBill();
		loadPayList(editData);
		loadAfterService(editData);
		loadAttachmentEntry(editData);
		loadBizReview();
		
		this.comboSellType.removeItem(SellTypeEnum.PlanningSell);
		if(srcInfo!=null&&SellTypeEnum.LocaleSell.equals(srcInfo.getSellType())){
			this.comboSellType.removeItem(SellTypeEnum.PreSell);
		}
		if(STATUS_ADDNEW.equals(this.getOprtState())&&editData.getRoom()!=null){
			if(!editData.getRoom().isIsPre()){
    			setSellTypeNull("房间没有预售复核！");
    		}else{
    			this.comboSellType.setSelectedItem(SellTypeEnum.PreSell);
    		}
//			if(srcInfo==null||srcInfo instanceof SincerityPurchaseInfo){
				this.updateRoomArea();
				this.updateRoomInfo();
				this.updataRoomContractAndDealAmount();
//			}
		}
		attachListeners();
	}

	public void storeFields() {
//		已经在选择客户的时候store了
//		storeCustomerEntry();
		storeCommon();
		storePayList();
		storeAttachmentEntry();
		super.storeFields();
		setTxtFormateNumerValue();
	}
//	 /**
//     * 身份验证，对置业顾问进行设置//TODO xiaominWang
//     */
//    protected void authentication(){
//    	//得到当前登录用户
//    	UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();	
//		String id=currentUserInfo.getId().toString();
//		//当前选中项目
//		 SellProjectInfo spInfo=null;
//		if(null!=this.getUIContext().get("project")){
//			spInfo=(SellProjectInfo)this.getUIContext().get("project");
//		}else{
//			return;
//		}
//		try {
//			String currDate = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()); 
//			OrgUnitInfo orgUnit=SysContext.getSysContext().getCurrentOrgUnit();
//			boolean isPermit = false;
//			isPermit=MarketUnitControlFactory.getRemoteInstance().exists("where orgUnit.id = '"+orgUnit.getId()+"' and controler.id = '"+id+"' ");
//			if(isPermit){
//				this.f7Seller.setEditable(true);
//				this.f7Seller.setValue(currentUserInfo);
//			}else{
//				boolean isDuty=MarketUnitSaleManFactory.getRemoteInstance().exists("where orgUnit.id = '"+orgUnit.getId()+"' and " +
//						"dutyPerson.id = '"+id+"' and member.id = '"+ id +"' " +
//						"and accessionDate <= {ts '"+currDate+"'} and dimissionDate >= {ts '"+currDate+"'} and sellProject = '"+spInfo.getId()+"'");//
//				if(isDuty){
//					this.f7Seller.setEditable(true);
//				}else{
//					this.f7Seller.setEditable(false);
//				}
//				this.f7Seller.setValue(currentUserInfo);
//			}
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//    }
	protected void loadCommon() {
		if(SellTypeEnum.LocaleSell.equals(editData.getSellType())){	
			txtBuildingArea.setValue(editData.getStrdActualBuildingArea());
			txtRoomArea.setValue(editData.getStrdActualRoomArea());
		} else if(SellTypeEnum.PreSell.equals(editData.getSellType())){
			txtBuildingArea.setValue(editData.getBulidingArea());
			txtRoomArea.setValue(editData.getRoomArea());
		}else if(SellTypeEnum.PlanningSell.equals(editData.getSellType())){
			txtBuildingArea.setValue(editData.getStrdPlanBuildingArea());
			txtRoomArea.setValue(editData.getStrdPlanRoomArea());
		}else{
			txtBuildingArea.setValue(FDCHelper.ZERO);
			txtRoomArea.setValue(FDCHelper.ZERO);
		}
		
		this.txtFitmentAmount1.setValue(editData.getFitmentTotalAmount());
		
		if(CalcTypeEnum.PriceByBuildingArea.equals(editData.getValuationType())||CalcTypeEnum.PriceByRoomArea.equals(editData.getValuationType())){
			currAgioParam.setPriceAccountType(PriceAccountTypeEnum.PriceSetStand);
		}else{
			currAgioParam.setPriceAccountType(PriceAccountTypeEnum.StandSetPrice);
		}
		currAgioParam.setBasePriceSell(editData.isIsBasePriceSell());
		currAgioParam.setToIntegerType(SHEHelper.DEFAULT_TO_INTEGER_TYPE);
		currAgioParam.setDigit(SHEHelper.DEFAULT_DIGIT);
		AgioEntryCollection agioEntryColl = new AgioEntryCollection(); 
		for(int i=0;i<editData.getSignAgioEntry().size();i++)
			agioEntryColl.add(editData.getSignAgioEntry().get(i));
		this.txtAgio.setUserObject(agioEntryColl);
		currAgioParam.setAgios(agioEntryColl);
		currAgioParam.setSpecialAgio(editData.getSpecialAgio());
		
		this.saleMan.clear();
		for(int i=0;i<editData.getSignSaleManEntry().size();i++){//his
			this.saleMan.add(editData.getSignSaleManEntry().get(i));
		}
		this.f7Seller.setValue(this.saleMan.toArray());//
		
		if(editData.getSrcId()!=null){
			ObjectUuidPK srcpk=new ObjectUuidPK(editData.getSrcId());
			try {
				IObjectValue objectValue = DynamicObjectFactory.getRemoteInstance().getValue(srcpk.getObjectType(),srcpk,SHEManageHelper.getBizSelectors(srcpk.getObjectType()));
				if(objectValue instanceof PrePurchaseManageInfo||objectValue instanceof PurchaseManageInfo){
					srcInfo=(BaseTransactionInfo)objectValue;
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}
	protected void loadCustomerEntry(SignManageInfo info){
		this.customer=new ArrayList();
		for(int i=0;i<info.getSignCustomerEntry().size();i++){
			SignCustomerEntryInfo entry=info.getSignCustomerEntry().get(i);
			this.customer.add(entry);
		}
		SHEManageHelper.loadCustomer(labelCustomer1, labelCustomer2, labelCustomer3,labelCustomer4,labelCustomer5, this.customer, txtPhoneNumber, info);
	}
	protected void loadPayList(SignManageInfo info) {
		SignPayListEntryCollection payEntrys = info.getSignPayListEntry();
		CRMHelper.sortCollection(payEntrys, "appDate", true);
		this.tblPayList.removeRows();
		for(int i=0;i<payEntrys.size();i++) {
			SignPayListEntryInfo entry = payEntrys.get(i);
			addPayListEntryRow(entry);
		}
	}
	protected void loadAfterService(SignManageInfo info){
//		for(int i=0;i<asEntrys.size();i++) {
//			AfterServiceInfo entry = asEntrys.get(i);
//			IRow row = this.tblAfterService.addRow();
//			row.setUserObject(entry);
//			row.getCell(AS_SERVICEITEM).setValue(entry.getServiceItem());
//			row.getCell(AS_APPDATE).setValue(entry.getAppDate());
//			row.getCell(AS_FINISHDATE).setValue(entry.getFinishDate());
//			row.getCell(AS_SERVICESTATE).setValue(entry.getServiceState());
//		}
	}
	protected void loadAttachmentEntry(SignManageInfo info){
		SignRoomAttachmentEntryCollection entrys = info.getSignRoomAttachmentEntry();
		this.tblAttachProperty.removeRows();
		for(int i=0;i<entrys.size();i++) {
			SignRoomAttachmentEntryInfo entry = entrys.get(i);
			IRow row = this.tblAttachProperty.addRow();
			row.setUserObject(entry);
			row.getCell(AP_ROOMNUMBER).setValue(entry.getRoom());
			row.getCell(AP_BUILDINGAREA).setValue(entry.getBuildingArea());
			row.getCell(AP_ROOMAREA).setValue(entry.getRoomArea());
			row.getCell(AP_STANDARDTOTALAMOUNT).setValue(entry.getStandardTotalAmount());
			row.getCell(AP_BUILDINGPRICE).setValue(entry.getBuildingPrice());
			row.getCell(AP_ROOMPRICE).setValue(entry.getRoomPrice());
			
			row.getCell(AP_AGIO).setValue(entry.getSignAgioEntry().toArray());
			SignAgioEntryCollection signCol=((SignRoomAttachmentEntryInfo)row.getUserObject()).getSignAgioEntry();
			try {
				setEntryAgio(row,signCol,entry.getRoom());
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

	protected void storeCommon() {
		AgioEntryCollection agioEntrys = (AgioEntryCollection)this.txtAgio.getUserObject();
		editData.getSignAgioEntry().clear();
		for (int i = 0; i < agioEntrys.size(); i++) {
			SignAgioEntryInfo entryInfo = (SignAgioEntryInfo)agioEntrys.get(i);
			editData.getSignAgioEntry().add(entryInfo);
		}
		String saleManNames="";
		editData.getSignSaleManEntry().clear();
		for(int i=0;i<this.saleMan.size();i++){
			if(i==this.saleMan.size()-1){
				saleManNames=saleManNames+((TranSaleManEntryInfo)this.saleMan.get(i)).getUser().getName();
			}else{
				saleManNames=saleManNames+((TranSaleManEntryInfo)this.saleMan.get(i)).getUser().getName()+";";
			}
			SignSaleManEntryInfo entry=new SignSaleManEntryInfo();
			SHEManageHelper.setSaleManEntry(entry,(TranSaleManEntryInfo)this.saleMan.get(i));
			editData.getSignSaleManEntry().add(entry);
		}
		editData.setSaleManNames(saleManNames);
	}
	private void setCustomerEntry(SignManageInfo sign,List customerList){
		String customerNames="";
		String customerPhone="";
		String recommendeds="";
		SignSaleManEntryInfo entry=new SignSaleManEntryInfo();
		for(int i=0;i<customerList.size();i++){
			SHECustomerInfo sheCI = new SHECustomerInfo();
			SignCustomerEntryInfo info =new SignCustomerEntryInfo();
			TranCustomerEntryInfo tranInfo=(TranCustomerEntryInfo)customerList.get(i);
			SHEManageHelper.setCustomerListEntry(info, tranInfo);
			//获取客户台帐客户信息
			sheCI = tranInfo.getCustomer();
			String phone="";
			String mob="";
			String tel="";
			String recommended="";
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
			if(sheCI.getRecommended()!=null&&!sheCI.getRecommended().trim().equals("")){
				recommended=sheCI.getRecommended();
			}
			if(i==customerList.size()-1){
				customerNames=customerNames+info.getName();
				customerPhone=customerPhone+phone;
				recommendeds=recommendeds+recommended;
			}else{
				customerNames=customerNames+info.getName()+";";
				customerPhone=customerPhone+phone+";";
				recommendeds=recommendeds+recommended+";";
			}
			sign.getSignCustomerEntry().add(info);
			entry.setUser(info.getCustomer().getPropertyConsultant());
		}
		sign.setCustomerNames(customerNames);
		sign.setCustomerPhone(customerPhone);
		txtRecommended.setText(recommendeds);
		
		if(this.saleMan.size()==0&&entry.getUser()!=null){
			this.saleMan.add(entry);
			this.f7Seller.setValue(this.saleMan.toArray());
		}
	}
	protected void storeCustomerEntry() {
		editData.getSignCustomerEntry().clear();
		setCustomerEntry(editData,this.customer);
	}
	protected void storePayList() {
		SignManageInfo info = this.editData;
		info.getSignPayListEntry().clear();
		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			SignPayListEntryInfo entry = (SignPayListEntryInfo) row.getUserObject();
			entry.setSeq(i);
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell(PL_MONEYNAME).getValue();
			entry.setMoneyDefine(moneyName);
			entry.setAppDate((Date) row.getCell(PL_APPDATE).getValue());
			entry.setCurrency((CurrencyInfo) row.getCell(PL_CURRENCY).getValue());
			entry.setAppAmount(SHEManageHelper.setScale(digit, toIntegerType,(BigDecimal) row.getCell(PL_APPAMOUNT).getValue()));
			entry.setDescription((String) row.getCell(PL_DES).getValue());
			
			info.getSignPayListEntry().add(entry);
		}
	}
	protected void storeAttachmentEntry(){
		String name="附属房产:";
		SignManageInfo info = this.editData;
		info.getSignRoomAttachmentEntry().clear();
		for (int i = 0; i < this.tblAttachProperty.getRowCount(); i++) {
			IRow row = this.tblAttachProperty.getRow(i);
			SignRoomAttachmentEntryInfo entry = (SignRoomAttachmentEntryInfo) row.getUserObject();
			entry.setRoom((RoomInfo)row.getCell(AP_ROOMNUMBER).getValue());
			if(entry.getRoom()!=null) name=name+entry.getRoom()+";";
			entry.setIsAttachcmentToContract(((Boolean)row.getCell(AP_ISMERGETOCONTRACT).getValue()).booleanValue());
			entry.setMergeAmount(SHEManageHelper.setScale(digit, toIntegerType,(BigDecimal)row.getCell(AP_MERGEAMOUNT).getValue()));
			entry.setStandardTotalAmount(SHEManageHelper.setScale(digit, toIntegerType,(BigDecimal)row.getCell(AP_STANDARDTOTALAMOUNT).getValue()));
			entry.setAgioDes(getAgioDes(entry.getSignAgioEntry().toArray()));
			entry.setBuildingPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,(BigDecimal)row.getCell(AP_BUILDINGPRICE).getValue()));
			entry.setRoomPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,(BigDecimal)row.getCell(AP_ROOMPRICE).getValue()));
			
			entry.setBuildingArea((BigDecimal)row.getCell(AP_BUILDINGAREA).getValue());
			entry.setRoomArea((BigDecimal)row.getCell(AP_ROOMAREA).getValue());
			
			info.getSignRoomAttachmentEntry().add(entry);
		}
		if(!name.equals("附属房产:"))
			this.txtDesc.setText(name);
	}
	private void setInfoFromSrcInfo(IObjectValue objectValue,SignManageInfo info){
		info.getSignCustomerEntry().clear();
		info.getSignPayListEntry().clear();
		info.getSignAgioEntry().clear();
		info.getSignRoomAttachmentEntry().clear();
		
		if(objectValue instanceof SincerityPurchaseInfo){
			SincerityPurchaseInfo sincerityPurchaseInfo=(SincerityPurchaseInfo)objectValue;
			SincerityPurchaseCustomerEntryCollection customerCol=sincerityPurchaseInfo.getCustomer();
			SinPurSaleMansEntryCollection saleCol=sincerityPurchaseInfo.getSaleMansEntry();
			
			for(int i=0;i<customerCol.size();i++){
				SignCustomerEntryInfo entry=new SignCustomerEntryInfo();
				SHEManageHelper.setCustomerListEntry(entry,customerCol.get(i));
				
				info.getSignCustomerEntry().add(entry);
			}
			info.setSellProject(sincerityPurchaseInfo.getSellProject());
			info.setCustomerNames(sincerityPurchaseInfo.getCustomerNames());
			info.setCustomerPhone(sincerityPurchaseInfo.getCustomerPhone());
			
//			SincerReceiveEntryCollection payCol=sincerityPurchaseInfo.getSincerPriceEntrys();
//			CRMHelper.sortCollection(payCol, "seq", true);
//			for(int i=0;i<payCol.size();i++){
//				SignPayListEntryInfo entry=new SignPayListEntryInfo();
//				SHEManageHelper.setPayListEntry(null,entry,payCol.get(i));
//				info.getSignPayListEntry().add(entry);
//			}
			
			for(int i=0;i<saleCol.size();i++){
				if(i==0){
					info.setSalesman(saleCol.get(i).getUser());
				}
				SignSaleManEntryInfo entry=new SignSaleManEntryInfo();
				SHEManageHelper.setSaleManEntry(entry,saleCol.get(i));
				
				info.getSignSaleManEntry().add(entry);
			}
			info.setSaleManNames(sincerityPurchaseInfo.getSaleManStr());
			
			info.setNewCommerceChance(sincerityPurchaseInfo.getNewCommerceChance());
			info.setTransactionID(sincerityPurchaseInfo.getTransactionID());
			if(sincerityPurchaseInfo.getRoom()!=null){
				info.setJoinInDate(sincerityPurchaseInfo.getRoom().getBuilding().getJoinInDate());
			}
		}else if(objectValue instanceof PrePurchaseManageInfo){
			PrePurchaseManageInfo prePurchaseManageInfo=(PrePurchaseManageInfo)objectValue;
			SHEManageHelper.setBaseInfo(info,prePurchaseManageInfo,false);
			
			PrePurchaseCustomerEntryCollection customerCol=prePurchaseManageInfo.getPrePurchaseCustomerEntry();
			for(int i=0;i<customerCol.size();i++){
				SignCustomerEntryInfo entry=new SignCustomerEntryInfo();
				SHEManageHelper.setCustomerListEntry(entry,customerCol.get(i));
				
				info.getSignCustomerEntry().add(entry);
			}
			
//			PrePurchasePayListEntryCollection payCol=prePurchaseManageInfo.getPrePurchasePayListEntry();
//			CRMHelper.sortCollection(payCol, "seq", true);
//			for(int i=0;i<payCol.size();i++){
//				SignPayListEntryInfo entry=new SignPayListEntryInfo();
//				if(payCol.get(i).getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.PreconcertMoney)){
//					continue;
//				}
//				SHEManageHelper.setPayListEntry(null,entry,payCol.get(i));
//				info.getSignPayListEntry().add(entry);
//			}
			
			PrePurchaseAgioEntryCollection agioCol=prePurchaseManageInfo.getPrePurchaseAgioEntry();
			for(int i=0;i<agioCol.size();i++){
				SignAgioEntryInfo entry=new SignAgioEntryInfo();
				SHEManageHelper.setAgioListEntry(entry,agioCol.get(i));
				info.getSignAgioEntry().add(entry);
			}
			
			PrePurchaseRoomAttachmentEntryCollection attachCol=prePurchaseManageInfo.getPrePurchaseRoomAttachmentEntry();
			for(int i=0;i<attachCol.size();i++){
				SignRoomAttachmentEntryInfo entry=new SignRoomAttachmentEntryInfo();
				SHEManageHelper.setRoomAttachmentListEntry(entry,attachCol.get(i));
				for(int j=0;j<attachCol.get(i).getPrePurchaseAgioEntry().size();j++){
					SignAgioEntryInfo agioEntry=new SignAgioEntryInfo();
					SHEManageHelper.setAgioListEntry(agioEntry,attachCol.get(i).getPrePurchaseAgioEntry().get(j));
					entry.getSignAgioEntry().add(agioEntry);
				}
				info.getSignRoomAttachmentEntry().add(entry);
			}
			
			PrePurchaseSaleManEntryCollection saleCol=prePurchaseManageInfo.getPrePurchaseSaleManEntry();
			for(int i=0;i<saleCol.size();i++){
				if(i==0){
					info.setSalesman(saleCol.get(i).getUser());
				}
				SignSaleManEntryInfo entry=new SignSaleManEntryInfo();
				SHEManageHelper.setSaleManEntry(entry,saleCol.get(i));
				info.getSignSaleManEntry().add(entry);
			}
			info.setSaleManNames(prePurchaseManageInfo.getSaleManNames());
			info.setJoinInDate(prePurchaseManageInfo.getRoom().getBuilding().getJoinInDate());
		}else if(objectValue instanceof PurchaseManageInfo){
			PurchaseManageInfo purchaseManageInfo=(PurchaseManageInfo)objectValue;
			SHEManageHelper.setBaseInfo(info, (BaseTransactionInfo)objectValue,false);
			info.setPreArea(purchaseManageInfo.getPreArea());
			info.setPlanningArea(purchaseManageInfo.getPlanningArea());
			info.setActualArea(purchaseManageInfo.getActualArea());
			info.setPlanningCompensate(purchaseManageInfo.getPlanningCompensate());
			info.setCashSalesCompensate(purchaseManageInfo.getCashSalesCompensate());
			
			PurCustomerEntryCollection customerCol=purchaseManageInfo.getPurCustomerEntry();
			for(int i=0;i<customerCol.size();i++){
				SignCustomerEntryInfo entry=new SignCustomerEntryInfo();
				SHEManageHelper.setCustomerListEntry(entry,customerCol.get(i));
				
				info.getSignCustomerEntry().add(entry);
			}
			
//			PurPayListEntryCollection payCol=purchaseManageInfo.getPurPayListEntry();
//			CRMHelper.sortCollection(payCol, "seq", true);
//			for(int i=0;i<payCol.size();i++){
//				SignPayListEntryInfo entry=new SignPayListEntryInfo();
//				
//				SHEManageHelper.setPayListEntry(null,entry,payCol.get(i));
//				info.getSignPayListEntry().add(entry);
//			}
			
			PurAgioEntryCollection agioCol=purchaseManageInfo.getPurAgioEntry();
			for(int i=0;i<agioCol.size();i++){
				SignAgioEntryInfo entry=new SignAgioEntryInfo();
				SHEManageHelper.setAgioListEntry(entry,agioCol.get(i));
				info.getSignAgioEntry().add(entry);
			}
			
			PurRoomAttachmentEntryCollection attachCol=purchaseManageInfo.getPurRoomAttachmentEntry();
			for(int i=0;i<attachCol.size();i++){
				SignRoomAttachmentEntryInfo entry=new SignRoomAttachmentEntryInfo();
				SHEManageHelper.setRoomAttachmentListEntry(entry,attachCol.get(i));
				for(int j=0;j<attachCol.get(i).getPurAgioEntry().size();j++){
					SignAgioEntryInfo agioEntry=new SignAgioEntryInfo();
					SHEManageHelper.setAgioListEntry(agioEntry,attachCol.get(i).getPurAgioEntry().get(j));
					entry.getSignAgioEntry().add(agioEntry);
				}
				info.getSignRoomAttachmentEntry().add(entry);
			}
			
			PurSaleManEntryCollection saleCol=purchaseManageInfo.getPurSaleManEntry();
			for(int i=0;i<saleCol.size();i++){
				if(i==0){
					info.setSalesman(saleCol.get(i).getUser());
				}
				SignSaleManEntryInfo entry=new SignSaleManEntryInfo();
				SHEManageHelper.setSaleManEntry(entry,saleCol.get(i));
				info.getSignSaleManEntry().add(entry);
			}
			info.setSaleManNames(purchaseManageInfo.getSaleManNames());
			info.setJoinInDate(purchaseManageInfo.getRoom().getBuilding().getJoinInDate());
		}else if(objectValue instanceof ChooseRoomInfo){
			if(((ChooseRoomInfo)objectValue).getSalesMan()!=null){
				info.setSalesman(((ChooseRoomInfo)objectValue).getSalesMan());
				
				SignSaleManEntryInfo entry=new SignSaleManEntryInfo();
				entry.setUser(((ChooseRoomInfo)objectValue).getSalesMan());
				info.getSignSaleManEntry().add(entry);
				info.setSaleManNames(((ChooseRoomInfo)objectValue).getSalesMan().getName());
			}
			info.setJoinInDate(((ChooseRoomInfo)objectValue).getRoom().getBuilding().getJoinInDate());
		}
		
		BigDecimal contractTotalAmount=info.getContractTotalAmount()==null?FDCHelper.ZERO:info.getContractTotalAmount();
		BigDecimal cashSalesCompensate=info.getCashSalesCompensate()==null?FDCHelper.ZERO:info.getCashSalesCompensate();
		BigDecimal planningCompensate=info.getPlanningCompensate()==null?FDCHelper.ZERO:info.getPlanningCompensate();
		
		info.setSellAmount(contractTotalAmount.add(cashSalesCompensate).add(planningCompensate));
	
	}
	protected IObjectValue createNewData() {
		SignManageInfo info=new SignManageInfo();
		//是放这个组织么
		info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setValuationType(CalcTypeEnum.PriceByTotalAmount);
//		info.setSalesman(SysContext.getSysContext().getCurrentUserInfo());
		try {
			info.setBizDate(FDCCommonServerHelper.getServerTimeStamp());
			info.setBusAdscriptionDate(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info.setIsValid(false);
		info.setIsOnRecord(false);
		info.setIsFitmentToContract(false);
		
		SellProjectInfo sellproject=(SellProjectInfo) this.getUIContext().get("sellProject");
		info.setSellProject(sellproject);
		
		RoomInfo room = (RoomInfo) this.getUIContext().get("room");
		try {
			verifyAddNewRoom(room,info.isIsBasePriceSell());
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		info.setRoom(room);
		setCRMFunction(info);
		
		if(this.getUIContext().get("srcId")!=null){
			info.setSrcId(BOSUuid.read(this.getUIContext().get("srcId").toString()));
			ObjectUuidPK srcpk=new ObjectUuidPK(info.getSrcId());
			try {
				IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(srcpk.getObjectType(),srcpk,SHEManageHelper.getBizSelectors(srcpk.getObjectType()));
				setInfoFromSrcInfo(objectValue,info);
				
				if(SellTypeEnum.PlanningSell.equals(info.getSellType())){
					info.setSellType(null);
				}
			} catch (BOSException e) {
				logger.error(e.getMessage());
			}
		}
		if(this.getUIContext().get("customer")!=null){
			List customer=(ArrayList)this.getUIContext().get("customer");
			setCustomerList(customer);
			setCustomerEntry(info,this.customer);
		}
		if(this.getUIContext().get("changeRoomCustomer")!=null){
			List customer=(ArrayList)this.getUIContext().get("changeRoomCustomer");
			for(int i=0;i<customer.size();i++){
				info.getSignCustomerEntry().add((SignCustomerEntryInfo)customer.get(i));
			}
			info.setCustomerNames((String)this.getUIContext().get("customerNames"));
			info.setCustomerPhone((String)this.getUIContext().get("customerPhone"));
		}
		if(this.getUIContext().get("changeSaleMan")!=null){
			List saleMan=(ArrayList)this.getUIContext().get("changeSaleMan");
			for(int i=0;i<saleMan.size();i++){
				info.getSignSaleManEntry().add((SignSaleManEntryInfo)saleMan.get(i));
			}
			info.setSaleManNames((String)this.getUIContext().get("saleManNames"));
		}
		if(this.getUIContext().get("commerce")!=null){
			CommerceChanceInfo commerce=(CommerceChanceInfo)this.getUIContext().get("commerce");
			info.setNewCommerceChance(commerce);
		}
//		if(info.getSignSaleManEntry().size()==0){
//			SignSaleManEntryInfo entry=new SignSaleManEntryInfo();
//			entry.setUser(SysContext.getSysContext().getCurrentUserInfo());
//			info.getSignSaleManEntry().add(entry);
//			info.setSaleManNames(SysContext.getSysContext().getCurrentUserInfo().getName());
//		}
		return info;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		return super.createNewDetailData(table);
	}

	protected IObjectValue createNewApData() {
		return new SignRoomAttachmentEntryInfo();
	}

	protected IObjectValue createNewPayListData() {
		return new SignPayListEntryInfo();
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("bizState");
		sels.add("state");
		sels.add("orgUnit.*");
		sels.add("CU.*");
		sels.add("sellProject.*");
		sels.add("srcId");
		sels.add("isEarnestInHouseAmount");
		sels.add("isAutoToInteger");
		sels.add("isBasePriceSell");
		sels.add("toIntegerType");
		sels.add("digit");
		sels.add("priceToIntegerType");
		sels.add("priceDigit");
		sels.add("isValid");
		sels.add("isOnRecord");
		sels.add("transactionID");
		sels.add("customerNames");
		sels.add("bulidingArea");
		sels.add("roomArea");
		sels.add("strdPlanBuildingArea");
		sels.add("strdPlanRoomArea");
		sels.add("strdActualBuildingArea");
		sels.add("strdActualRoomArea");
		sels.add("saleManNames");
		sels.add("newCommerceChance");
		sels.add("newCommerceChance.customer.*");
		sels.add("specialAgio.*");
		
		sels.add("signPayListEntry.*");
		sels.add("signPayListEntry.moneyDefine.*");
		sels.add("signPayListEntry.currency.*");
		
		sels.add("signCustomerEntry.*");
		sels.add("signCustomerEntry.customer.*");
		sels.add("signCustomerEntry.certificate.*");
		sels.add("signCustomerEntry.customer.mainCustomer.*");
		
		sels.add("signAgioEntry.*");
		sels.add("signAgioEntry.agio.*");
		
		sels.add("signRoomAttachmentEntry.*");
		sels.add("signRoomAttachmentEntry.room.*");
		sels.add("signRoomAttachmentEntry.signAgioEntry.*");
		sels.add("signRoomAttachmentEntry.signAgioEntry.agio.*");
		
		sels.add("signSaleManEntry.user.*");
		sels.add("signSaleManEntry.user.group.name");
		sels.add("signSaleManEntry.user.CU.name");
		sels.add("signSaleManEntry.*");

		sels.add("room.building.productType.*");
		return sels;
	}
	protected void initWorkButton() {
		super.initWorkButton();
        this.btnRelatePurchase.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
        this.btnRelatePrePurchase.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
	}
	protected TransactionStateEnum getBizStateAuditEnum() {
		return TransactionStateEnum.SIGNAUDIT;
	}

	protected TransactionStateEnum getBizStateSubmitEnum() {
		return TransactionStateEnum.SIGNAPPLE;
	}

	protected TransactionStateEnum getBizStateInvalidEnum() {
		return TransactionStateEnum.SIGNNULLIFY;
	}

	public void actionCustomerSelect_actionPerformed(ActionEvent e) throws Exception {
		customerSelect(this,this.customer,editData.getSellProject(),true);
		this.storeCustomerEntry();
		this.loadCustomerEntry(editData);
	}

	public void actionReceiveBill_actionPerformed(ActionEvent e) throws Exception {
		if(!(TransactionStateEnum.SIGNAPPLE.equals(editData.getBizState())||
				TransactionStateEnum.SIGNAUDIT.equals(editData.getBizState()))){
			FDCMsgBox.showWarning(this,"该单据业务状态不能进行收款操作！");
			SysUtil.abort();
		}
		Object[] custObjs=new Object[editData.getSignCustomerEntry().size()];
		for(int i=0;i<editData.getSignCustomerEntry().size();i++){
			custObjs[i]=editData.getSignCustomerEntry().get(i).getCustomer();
		}
		SignPayListEntryCollection signPayListColl = this.editData.getSignPayListEntry();
		Set transEntryIdsSet = new HashSet();
		for (int i = 0; i < signPayListColl.size(); i++) {
			SignPayListEntryInfo signPayEntryInfo = signPayListColl.get(i);
			if(signPayEntryInfo.getTanPayListEntryId()!=null)
				transEntryIdsSet.add(signPayEntryInfo.getTanPayListEntryId().toString());
		}		
		CRMClientHelper.openTheGatherRevBillWindow(this, null, editData.getSellProject(),editData ,custObjs,transEntryIdsSet);
		this.loadReceiveBill();
		this.updateActRevAmount((BaseTransactionInfo)editData);
	}
	public void actionRelatePrePurchase_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getSrcId()!=null){
			ObjectUuidPK pk=new ObjectUuidPK(editData.getSrcId());
			IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk);
			if(objectValue instanceof PurchaseManageInfo){
				if(((PurchaseManageInfo)objectValue).getSrcId()!=null){
					ObjectUuidPK srcpk=new ObjectUuidPK(((PurchaseManageInfo)objectValue).getSrcId());
					IObjectValue srcobjectValue=DynamicObjectFactory.getRemoteInstance().getValue(srcpk.getObjectType(),srcpk);
					
					if(srcobjectValue instanceof PrePurchaseManageInfo){
						UIContext uiContext = new UIContext(this);
						uiContext.put("ID", ((PurchaseManageInfo)objectValue).getSrcId());
				        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
				        IUIWindow uiWindow = uiFactory.create(PrePurchaseManageEditUI.class.getName(), uiContext,null,OprtState.VIEW);
				        uiWindow.show();
				        return;
					}
				}
			}else if(objectValue instanceof PrePurchaseManageInfo){
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", editData.getSrcId());
		        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		        IUIWindow uiWindow = uiFactory.create(PrePurchaseManageEditUI.class.getName(), uiContext,null,OprtState.VIEW);
		        uiWindow.show();
		        return;
			}
		}
		FDCMsgBox.showWarning(this,"无关联预定单据！");
	}

	public void actionRelatePurchase_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getSrcId()!=null){
			ObjectUuidPK pk=new ObjectUuidPK(editData.getSrcId());
			IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk);
			if(objectValue instanceof PurchaseManageInfo){
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", editData.getSrcId());
		        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		        IUIWindow uiWindow = uiFactory.create(PurchaseManageEditUI.class.getName(), uiContext,null,OprtState.VIEW);
		        uiWindow.show();
		        return;
			}
		}
		FDCMsgBox.showWarning(this,"无关联认购单据！");
	}
	protected void initControl() {
		super.initControl();
		this.txtRecommended.setEnabled(false);
		this.txtDesc.setMaxLength(255);
		this.tabInformation.remove(this.panelAfterService);
		
		if(editData.getSrcId()==null){
			this.btnSetEntry.setEnabled(false);
		}else{
			ObjectUuidPK srcpk=new ObjectUuidPK(editData.getSrcId());
			try {
				IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(srcpk.getObjectType(),srcpk);
				if((objectValue instanceof PrePurchaseManageInfo)||(objectValue instanceof PurchaseManageInfo)){
					this.btnSetEntry.setEnabled(true);
				}else{
					this.btnSetEntry.setEnabled(false);
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		this.contActualArea.setVisible(false);
		
		try {
			this.f7OldSeller.setEntityViewInfo(NewCommerceHelper.getPermitSalemanView(this.editData.getSellProject(),true));
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	protected void setTxtFormate(){
		super.setTxtFormate();
		this.txtPlanningCompensate.setPrecision(digit);
		this.txtPlanningCompensate.setRoundingMode(toIntegerType);
		
		this.txtCashSalesCompensate.setPrecision(digit);
		this.txtCashSalesCompensate.setRoundingMode(toIntegerType);
		
		this.txtAreaCompensate.setPrecision(digit);
		this.txtAreaCompensate.setRoundingMode(toIntegerType);
		
		this.txtSellAmount.setPrecision(digit);
		this.txtSellAmount.setRoundingMode(toIntegerType);
	}
	protected void setTxtFormateNumerValue(){
		super.setTxtFormateNumerValue();
		editData.setSellAmount(SHEManageHelper.setScale(digit, toIntegerType,this.txtSellAmount.getBigDecimalValue()));
		editData.setPlanningCompensate(SHEManageHelper.setScale(digit, toIntegerType,this.txtPlanningCompensate.getBigDecimalValue()));
		editData.setCashSalesCompensate(SHEManageHelper.setScale(digit, toIntegerType,this.txtCashSalesCompensate.getBigDecimalValue()));
		editData.setAreaCompensate(SHEManageHelper.setScale(digit, toIntegerType,this.txtAreaCompensate.getBigDecimalValue()));
	}
	protected void verfiyOther(){
		FDCClientVerifyHelper.verifyEmpty(this, this.f7PayType);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkJoinInDate);
		FDCClientVerifyHelper.verifyEmpty(this, this.f7OldSeller);
		if(editData.getSpecialAgio()!=null){
			boolean isExist=false;
			for(int i=0;i<this.customer.size();i++){
				SignCustomerEntryInfo info=(SignCustomerEntryInfo) this.customer.get(i);
				if(editData.getSpecialAgio().getCustomer().getId().equals(info.getCustomer().getId())){
					isExist=true;
				}
			}
			if(!isExist){
				FDCMsgBox.showWarning(this,"特殊折扣单客户在客户列表中不存在！");
				SysUtil.abort();
			}
			if(((BaseTransactionInfo)editData).isIsBasePriceSell()){
				RoomInfo room = (RoomInfo) this.f7Room.getValue();
				if(room.getBaseStandardPrice()==null){
					FDCMsgBox.showWarning(this,"已启用强制底价控制参数，该房间总价底价不存在，请检查！");
					SysUtil.abort();
				}
				RoomInfo roomInfo = (RoomInfo) this.f7Room.getValue();
				if(roomInfo==null) return;
				AgioEntryCollection agioCol=((AgioEntryCollection)this.txtAgio.getUserObject());
				
				AgioParam oldAgioParam=new AgioParam();
				oldAgioParam.setBasePriceSell(this.currAgioParam.isBasePriceSell());
				oldAgioParam.setDigit(this.currAgioParam.getDigit());
				oldAgioParam.setPriceAccountType(this.currAgioParam.getPriceAccountType());
				oldAgioParam.setToInteger(this.currAgioParam.isToInteger());
				oldAgioParam.setToIntegerType(this.currAgioParam.getToIntegerType());
				oldAgioParam.setSpecialAgio(this.currAgioParam.getSpecialAgio());
				
				AgioEntryCollection oldAgioCol=new AgioEntryCollection();
				oldAgioParam.setAgios(oldAgioCol);
				
				for(int i=0;i<agioCol.size();i++){
					if(agioCol.get(i).getAgio()!=null){
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
				PurchaseParam purParam = SHEManageHelper.getAgioParam(oldAgioParam, roomInfo, 
						 sellType,valuationType,isFitmentToContract,roomArea,buildingArea,roomPrice,buildingPrice,standardAmount,fitmentAmount, attachmentAmount, allCompensate ,SpecialAgioEnum.DaZhe, payTypeAgio,payTypeName);
				
				BigDecimal oldDealTotalAmount=purParam.getDealTotalAmount().setScale(digit, toIntegerType);
				BigDecimal nowDealTotalAmount=this.txtDealTotalAmount.getBigDecimalValue().setScale(digit, toIntegerType);
//				BigDecimal baseStandardPrice=room.getBaseStandardPrice().setScale(digit, toIntegerType);
//				if(oldDealTotalAmount.compareTo(baseStandardPrice)<0){
					if(nowDealTotalAmount.compareTo(oldDealTotalAmount)<0){
						FDCMsgBox.showWarning(this,"成交总价不能低于特殊折扣后的总价！("+nowDealTotalAmount+"<"+oldDealTotalAmount+")");
						SysUtil.abort();
					}
//				}else{
//					if(nowDealTotalAmount.compareTo(oldDealTotalAmount)<0){
//						FDCMsgBox.showWarning(this,"成交总价不能低于房间底价！("+nowDealTotalAmount+"<"+baseStandardPrice+")");
//						SysUtil.abort();
//					}
//				}
			}
		}else{
			if(((BaseTransactionInfo)editData).isIsBasePriceSell()){
				RoomInfo room = (RoomInfo) this.f7Room.getValue();
				if(room.getBaseStandardPrice()==null){
					FDCMsgBox.showWarning(this,"已启用强制底价控制参数，该房间底价不存在，请检查！");
					SysUtil.abort();
				}
				if(this.txtDealTotalAmount.getBigDecimalValue().setScale(digit, toIntegerType).compareTo(room.getBaseStandardPrice().setScale(digit, toIntegerType))<0){
					FDCMsgBox.showWarning(this,"成交总价不能低于房间底价！("+this.txtDealTotalAmount.getBigDecimalValue().setScale(digit, toIntegerType)+"<"+room.getBaseStandardPrice().setScale(digit, toIntegerType)+")");
					SysUtil.abort();
				}
			}
		}
	}
	protected boolean veriftExistsSameRoomBill(RoomInfo room) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId()));
		filter.getFilterItems().add(new FilterItemInfo("bizState", TransactionStateEnum.SIGNAPPLE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState", TransactionStateEnum.SIGNAUDIT_VALUE));
		
		if(editData!=null&&editData.getId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getId(),CompareType.NOTEQUALS));
			filter.setMaskString("#0 and (#1 or #2) and #3");
		}else{
			filter.setMaskString("#0 and (#1 or #2)");
		}
		return getBizInterface().exists(filter);
	}
	protected void verifyAddNewRoom(RoomInfo room,boolean isIsBasePriceSell) throws BOSException, EASBizException {
		super.verifyAddNewRoom(room,isIsBasePriceSell); 
		if(room==null) return;
		
		if (veriftExistsSameRoomBill(room)) {
			setRoomNull("房间已经存在签约单据！");
		}
		IObjectValue objectValue=SHEManageHelper.getCurTransactionBill(room.getId());
		if(objectValue!=null&&((objectValue instanceof PrePurchaseManageInfo)||(objectValue instanceof PurchaseManageInfo))){
			TransactionStateEnum curState=((BaseTransactionInfo)objectValue).getBizState();
			if(!TransactionStateEnum.PREAUDIT.equals(curState)&&!TransactionStateEnum.PURAUDIT.equals(curState)){
				setRoomNull("该房间对应业务单据不是审批状态，不能进行转签约操作！");
			}
			if(editData!=null&&editData.getSrcId()==null){
				srcInfo=(BaseTransactionInfo)objectValue;
				editData.setSrcId(((BaseTransactionInfo)objectValue).getId());
				editData.setRoom(room);
				setInfoFromSrcInfo(objectValue,editData);
				this.loadFields();
			}
		}
	}
	protected void updateAmount(){
		isEditDealAmount=false;
		super.updateAmount();
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
		
		if(srcInfo!=null){
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
		
		BigDecimal contractTotalAmount=this.txtContractTotalAmount.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtContractTotalAmount.getBigDecimalValue();
		BigDecimal cashSalesCompensate=this.txtCashSalesCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtCashSalesCompensate.getBigDecimalValue();
		BigDecimal planningCompensate=this.txtPlanningCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtPlanningCompensate.getBigDecimalValue();
		BigDecimal areaCompensate=this.txtAreaCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtAreaCompensate.getBigDecimalValue();
		BigDecimal allCompensate=cashSalesCompensate.add(planningCompensate).add(areaCompensate);
		
		this.txtSellAmount.setValue(contractTotalAmount.add(allCompensate));
	
		updataRoomContractAndDealAmount();
		updateRoomAttactAndFittmentMD();
		if(this.f7PayType.getValue()!=null){
			updatePayList();
		}
		isEditDealAmount=true;
	}
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
			this.txtSellAmount.setValue(purParam.getContractTotalAmount());
			
		}
		isEditDealAmount=true;
	}
	
    protected void updateRoomArea(){
    	super.updateRoomArea();
    	if(this.comboValuationType.getSelectedItem()!=null&&this.f7Room.getValue()!=null){
    		CalcTypeEnum valuationType=(CalcTypeEnum)this.comboValuationType.getSelectedItem();
    		if(CalcTypeEnum.PriceByBuildingArea.equals(valuationType)||CalcTypeEnum.PriceByTotalAmount.equals(valuationType)){
    			this.txtPlanningArea.setValue(editData.getStrdPlanBuildingArea());
    			this.txtPreArea.setValue(editData.getBulidingArea());
    			this.txtActualArea.setValue(editData.getStrdActualBuildingArea());
    		}
    		if(CalcTypeEnum.PriceByRoomArea.equals(valuationType)){
    			this.txtPlanningArea.setValue(editData.getStrdPlanRoomArea());
    			this.txtPreArea.setValue(editData.getRoomArea());
    			this.txtActualArea.setValue(editData.getStrdActualRoomArea());
    		}
    	}else{
			this.txtPlanningArea.setValue(FDCHelper.ZERO);
			this.txtPreArea.setValue(FDCHelper.ZERO);
			this.txtActualArea.setValue(FDCHelper.ZERO);
			this.txtPlanningCompensate.setValue(FDCHelper.ZERO);
			this.txtCashSalesCompensate.setValue(FDCHelper.ZERO);
		}
    }
	protected void updateRoomInfo(){
		super.updateRoomInfo();
		if(this.f7Room.getValue()!=null){
			RoomInfo room=(RoomInfo)this.f7Room.getValue();
			if(room.getBuilding()!=null){
				this.pkJoinInDate.setValue(room.getBuilding().getJoinInDate());
			}
			if(SellTypeEnum.PlanningSell.equals(room.getSellType())){
				this.comboSellType.setSelectedItem(SellTypeEnum.PreSell);
			}
		}
	}
	protected String getTDFileName() {
		return "/bim/fdc/sellhouse/SignManage";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.SignManagePrintQuery");
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
		if(this.customer.size()>0){
			for(int i=0;i<this.customer.size();i++){
				if(this.customer.get(i)!=null&&((TranCustomerEntryInfo)this.customer.get(i)).isIsMain()){
					value.put("mainAddress", ((TranCustomerEntryInfo)this.customer.get(i)).getAddress());
					value.put("mainPhone", ((TranCustomerEntryInfo)this.customer.get(i)).getPhone());
					value.put("mainTel", ((TranCustomerEntryInfo)this.customer.get(i)).getTel());
					value.put("mainPostalCode", ((TranCustomerEntryInfo)this.customer.get(i)).getCustomer().getPostalCode());
				}
			}
		}
		SignManagePrintDataProvider data = new SignManagePrintDataProvider(
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
		if(this.customer.size()>0){
			for(int i=0;i<this.customer.size();i++){
				if(this.customer.get(i)!=null&&((TranCustomerEntryInfo)this.customer.get(i)).isIsMain()){
					value.put("mainAddress", ((TranCustomerEntryInfo)this.customer.get(i)).getAddress());
					value.put("mainPhone", ((TranCustomerEntryInfo)this.customer.get(i)).getPhone());
					value.put("mainTel", ((TranCustomerEntryInfo)this.customer.get(i)).getTel());
					value.put("mainPostalCode", ((TranCustomerEntryInfo)this.customer.get(i)).getCustomer().getPostalCode());
				}
			}
		}
		SignManagePrintDataProvider data = new SignManagePrintDataProvider(
				editData.getString("id"), getTDQueryPK(),value);
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
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
			oldAgioParam.setSpecialAgio(this.currAgioParam.getSpecialAgio());
			
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
			PurchaseParam purParam = SHEManageHelper.getAgioParam(oldAgioParam, roomInfo, 
					 sellType,valuationType,isFitmentToContract,roomArea,buildingArea,roomPrice,buildingPrice,standardAmount,fitmentAmount, attachmentAmount, allCompensate ,SpecialAgioEnum.DaZhe, payTypeAgio,payTypeName);
			
			BigDecimal amount=SHEManageHelper.setScale(digit, toIntegerType,purParam.getDealTotalAmount()).subtract(this.txtDealTotalAmount.getBigDecimalValue());
			
			if(amount.compareTo(new BigDecimal(0))>=0){
				MsgBox.showInfo("成交价格只能上调!");
				isEditDealAmount=false;
				this.txtDealTotalAmount.setValue(purParam.getDealTotalAmount());
				isEditDealAmount=true;
				return;
			}
			if(!isHasAgio){
				if(!purParam.getDealTotalAmount().equals(this.txtDealTotalAmount.getBigDecimalValue())){
					SignAgioEntryInfo entryInfo = new SignAgioEntryInfo();
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
					 sellType,valuationType,isFitmentToContract,roomArea,buildingArea,roomPrice,buildingPrice,standardAmount,fitmentAmount, attachmentAmount, allCompensate ,SpecialAgioEnum.DaZhe, payTypeAgio,payTypeName);
			if(afterPurParam!=null) {
				this.txtAgioDes.setText(afterPurParam.getAgioDes());
				this.txtAgio.setValue(afterPurParam.getFinalAgio());
				this.txtContractTotalAmount.setValue(afterPurParam.getContractTotalAmount());
				this.txtContractBuildPrice.setValue(afterPurParam.getContractBuildPrice());
				this.txtContractRoomPrice.setValue(afterPurParam.getContractRoomPrice());
				this.txtDealBuildPrice.setValue(afterPurParam.getDealBuildPrice());
				this.txtDealRoomPrice.setValue(afterPurParam.getDealRoomPrice());
				this.txtSellAmount.setValue(afterPurParam.getContractTotalAmount());
			}
			if(this.tblPayList.getRowCount()>0&&FDCMsgBox.showConfirm2(this, "成交总价已改变，是否重新生成付款明细？")== FDCMsgBox.YES){
				this.updatePayList();
			}
    	}
	}

	protected void tblAttachProperty_editStopped(KDTEditEvent e) throws Exception {
		IRow row = this.tblAttachProperty.getRow(e.getRowIndex());
		SignAgioEntryCollection signCol=((SignRoomAttachmentEntryInfo)row.getUserObject()).getSignAgioEntry();
		
		if(e.getColIndex() == tblAttachProperty.getColumnIndex(AP_ROOMNUMBER)){
			if(e.getValue()!=null){
				RoomInfo room = (RoomInfo)row.getCell(AP_ROOMNUMBER).getValue();
				verifyAddNewAttachRoom(room,row);
				updateAttachRoomInfo(room,row);
				setEntryAgio(row,signCol,room);

				updateAmount();
			}else{
				clearTblAttach(row);
			}
		}
		if(e.getColIndex() == tblAttachProperty.getColumnIndex(AP_AGIO)){
			if(e.getValue()==null){
				signCol.clear();
			}else if(!e.getValue().equals(e.getOldValue())){
				Object[] obj=(Object[]) e.getValue();
				signCol.clear();
				for (int i = 0; i < obj.length; i++) {
					SignAgioEntryInfo entryInfo = (SignAgioEntryInfo)obj[i];
					signCol.add(entryInfo);
				}
				setEntryAgio(row,signCol,(RoomInfo)row.getCell(AP_ROOMNUMBER).getValue());
				
				updateAmount();
			}
		}
		if(e.getColIndex() == tblAttachProperty.getColumnIndex(AP_ISMERGETOCONTRACT)||e.getColIndex()==tblAttachProperty.getColumnIndex(AP_MERGEAMOUNT)){
			updateAmount();
		}
	}
	protected void getAttachRoom(RoomInfo room) throws EASBizException, BOSException{
		if(room==null) return;
		RoomAttachmentEntryCollection col=room.getAttachmentEntry();
		this.tblAttachProperty.removeRows();
		for(int i=0;i<col.size();i++){
			
			RoomInfo attRoom=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(col.get(i).getRoom().getId()));
			
			verifyAddNewAttachRoom(attRoom,null);
			
			IRow row=this.tblAttachProperty.addRow();
			row.getCell(AP_ISMERGETOCONTRACT).setValue(new Boolean(false));
			row.setUserObject(this.createNewApData());
			
			row.getCell(AP_ROOMNUMBER).setValue(attRoom);
			row.getCell(AP_MERGEAMOUNT).setValue(attRoom.getStandardTotalAmount());
			updateAttachRoomInfo(attRoom,row);
			
			setEntryAgio(row,new SignAgioEntryCollection(),room);
		}
	}
	protected void setEntryAgio(IRow row,SignAgioEntryCollection signCol,RoomInfo room) throws UIException{
		
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
		for (int i = 0; i < signCol.size(); i++) {
			SignAgioEntryInfo entryInfo = signCol.get(i);
			agioEntrys.add(entryInfo);
		}
		KDBizPromptBox f7AgioBox = new KDBizPromptBox();
		f7AgioBox.setEnabledMultiSelection(true);
		f7AgioBox.setSelector(new AgioPromptDialog(this, room.getId().toString(), agioEntrys, currEntryAgioParam, editData,isAgioListCanEdit));
		f7AgioBox.setEditable(false);
		if(row.getCell(AP_AGIO).getValue()!=null){
			f7AgioBox.setDisplayFormat("");
		}else{
			f7AgioBox.setDisplayFormat(null);
		}
		KDTDefaultCellEditor f7AgioEditor = new KDTDefaultCellEditor(f7AgioBox);
		row.getCell(AP_AGIO).setEditor(f7AgioEditor);
			
		if(signCol.size()>0){
			PurchaseParam purParam = SHEManageHelper.getPurchaseAgioParam(currEntryAgioParam, room, 
					room.getSellType(), false, null, null, null ,null, null,null);
			row.getCell(AP_MERGEAMOUNT).setValue(purParam.getDealTotalAmount());
		}else{
			row.getCell(AP_MERGEAMOUNT).setValue(row.getCell(AP_STANDARDTOTALAMOUNT).getValue());
		}
	}
	private boolean checkIsHasBankPayment() throws EASBizException, BOSException{
		if(editData.getId()==null){
			return false;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("signManager.id", editData.getId()));
		return BankPaymentEntryFactory.getRemoteInstance().exists(filter);
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEditOrRemove("cantEdit");
		if(checkIsHasBankPayment()){
			FDCMsgBox.showWarning(this,"签约单已经产生银行放款单业务，不能进行修改操作！");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
	}
	protected void updatePayListByPayType() {
		try {
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
			List toAddRowEntry = SHEManageHelper.updatePayListByPayType((SHEPayTypeInfo) this.f7PayType.getValue(), this.editData.isIsEarnestInHouseAmount(), this.tblPayList, fittment, roomAttach, null, 
					this.txtContractTotalAmount.getBigDecimalValue(), this.txtDealTotalAmount.getBigDecimalValue(),this.txtStandardTotalAmount.getBigDecimalValue()
					, this.txtBuildingArea.getBigDecimalValue(), this.txtRoomArea.getBigDecimalValue(),(RoomInfo) this.f7Room.getValue(), digit, priceToIntegerType,(Date)this.pkBizDate.getValue(),SHEPayTypeBizTimeEnum.SIGN,isAddFittment,isAddRoomAttach);
			if ( this.f7PayType.getValue() != null) {
				int rowCount=0;
				for (int i = 0; i < toAddRowEntry.size(); i++) {
					IRow row =null;
					if(i==0){
						row = this.tblPayList.addRow(0);
					}else{
						row = this.tblPayList.addRow(rowCount+1);
					}
					SignPayListEntryInfo entry=new SignPayListEntryInfo();
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
	private void addPayListEntryRow(TranPayListEntryInfo entry) {
		IRow row = this.tblPayList.addRow();
		row.setUserObject(entry);
		row.getCell(PL_MONEYNAME).setValue(entry.getMoneyDefine());
		row.getCell(PL_APPDATE).setValue(entry.getAppDate());
		row.getCell(PL_APPAMOUNT).setValue(entry.getAppAmount());
		row.getCell(PL_CURRENCY).setValue(entry.getCurrency());
		row.getCell(PL_DES).setValue(entry.getDescription());
		
		BigDecimal actAmount = FDCHelper.ZERO;
		
		if(TransactionStateEnum.SIGNAPPLE.equals(editData.getBizState())||TransactionStateEnum.SIGNAUDIT.equals(editData.getBizState())){
			actAmount=SHEManageHelper.getActRevAmount(null,entry.getTanPayListEntryId());
			row.getCell(PL_ACTAMOUNT).setValue(actAmount);
		}else{
			actAmount=entry.getActRevAmount();
			row.getCell(PL_ACTAMOUNT).setValue(actAmount);
		}
		BigDecimal apAmount = entry.getAppAmount()==null?FDCHelper.ZERO:entry.getAppAmount();
		
		if (actAmount.compareTo(FDCHelper.ZERO) != 0) {
			if (actAmount.compareTo(apAmount) >= 0) {
				row.getCell(PL_STATE).setValue(new Boolean(true));
			}else{
				row.getCell(PL_STATE).setValue(new Boolean(false));
			}
		}else{
			row.getCell(PL_STATE).setValue(new Boolean(false));
		}
		row.getCell(PL_BALANCE).setValue(apAmount.subtract(actAmount).compareTo(FDCHelper.ZERO)<0?FDCHelper.ZERO:apAmount.subtract(actAmount));
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		if(this.getUIContext()!=null){
			if (Boolean.TRUE.equals(this.getUIContext().get(KEY_DESTORY_WINDOW))) {
				destroyWindow();
			}
			this.getUIContext().put(KEY_SUBMITTED_DATA, Boolean.TRUE);
		}
	}

	protected void btnSetEntry_actionPerformed(ActionEvent e) throws Exception {
		this.updatePayListByPayType();
	}
	protected void f7PayType_dataChanged(DataChangeEvent e) throws Exception {
		super.f7PayType_dataChanged(e);
		if(editData.getSrcId()==null){
			this.updatePayListByPayType();
		}else{
			ObjectUuidPK srcpk=new ObjectUuidPK(editData.getSrcId());
			try {
				IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(srcpk.getObjectType(),srcpk);
				if((objectValue instanceof PrePurchaseManageInfo)||(objectValue instanceof PurchaseManageInfo)){
					this.btnSetEntry.setEnabled(true);
				}else{
					this.updatePayListByPayType();
					this.btnSetEntry.setEnabled(false);
				}
			} catch (BOSException e1) {
				e1.printStackTrace();
			}
		}
		
		if(f7PayType.getValue()!=null){
			String id = ((SHEPayTypeInfo)f7PayType.getValue()).getId().toString();
			SHEPayTypeInfo spt = SHEPayTypeFactory.getRemoteInstance().getSHEPayTypeInfo(new ObjectUuidPK(id)); 
			if(spt.getLoanBank()!=null){
				String loanBankId = spt.getLoanBank().getId().toString();
				BankInfo loanBankInfo = BankFactory.getRemoteInstance().getBankInfo(new ObjectUuidPK(loanBankId));
				prtLoanBank.setValue(loanBankInfo);
			}else{
				prtLoanBank.setValue(null);
			}
			if(spt.getAcfBank()!=null){
				String AcfBankid = spt.getAcfBank().getId().toString();
				BankInfo AcfBank = BankFactory.getRemoteInstance().getBankInfo(new ObjectUuidPK(AcfBankid));				
				prtAfmBank.setValue(AcfBank);
			}else{
				prtAfmBank.setValue(null);
			}
		}
	}
	protected void f7Seller_dataChanged(DataChangeEvent e) throws Exception {
		if(e.getNewValue()==null){
			this.saleMan.clear();
			this.f7OldSeller.setValue(null);
		}
		if(this.saleMan.size()>0&&!"VIEW".equals(this.oprtState)){
			this.f7OldSeller.setValue(((TranSaleManEntryInfo)this.saleMan.get(0)).getUser());
		}
	}
	public void actionAddCustomer_actionPerformed(ActionEvent e) throws Exception {
		SellProjectInfo sellProjectInfo = SHEManageHelper.getParentSellProject(null,((BaseTransactionInfo)editData).getSellProject());
		Map linkedCus = CusClientHelper.addNewCusBegin(this, sellProjectInfo.getId().toString());
		UIContext uiContext = new UIContext(this);
		if (linkedCus != null) {
			uiContext.putAll(linkedCus);
			uiContext.put("sellProject", sellProjectInfo);
			uiContext.put("addnewDerict", "unDerict");
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerRptEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
		}
	}
	protected void verifyPayListTab() throws BOSException {
		super.verifyPayListTab();
		boolean isHasLoan=false;
		boolean isHasAccFund=false;
		boolean isEarnestInHouseAmount = ((BaseTransactionInfo)this.editData).isIsEarnestInHouseAmount();
		BigDecimal contractAmount = this.txtContractTotalAmount.getBigDecimalValue();
		BigDecimal totalAmount = FDCHelper.ZERO;
		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell(PL_MONEYNAME).getValue();
			BigDecimal amount = (BigDecimal)row.getCell(PL_APPAMOUNT).getValue();
			if(SHEManageHelper.isMergerToContractMoneyType(moneyName.getMoneyType(),isEarnestInHouseAmount)){
				totalAmount = totalAmount.add((BigDecimal)amount);
			}
			if(moneyName.getMoneyType().equals(MoneyTypeEnum.LoanAmount)){
				isHasLoan=true;
			}else if(moneyName.getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
				isHasAccFund=true;
			}
		}
		if (contractAmount == null) {
			contractAmount = FDCHelper.ZERO;
		}
		if(!SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("ppl")){
			if (this.f7PayType.getValue()!=null&&SHEManageHelper.setScale(digit, toIntegerType,totalAmount).compareTo(SHEManageHelper.setScale(digit, toIntegerType,contractAmount)) != 0) {
				FDCMsgBox.showWarning("客户付款明细总额不等于合同总价！("+SHEManageHelper.setScale(digit, toIntegerType,totalAmount)+"!="+SHEManageHelper.setScale(digit, toIntegerType,contractAmount)+")");
				SysUtil.abort();
			}
		}
		if(isHasLoan){
			this.prtLoanBank.setRequired(true);
			FDCClientVerifyHelper.verifyEmpty(this, this.prtLoanBank);
		}else{
			this.prtLoanBank.setRequired(false);
		}
		if(isHasAccFund){
			this.prtAfmBank.setRequired(true);
			FDCClientVerifyHelper.verifyEmpty(this, this.prtAfmBank);
		}else{
			this.prtAfmBank.setRequired(false);
		}
	}
	public void actionChooseAgio_actionPerformed(ActionEvent e) throws Exception {
		if (this.f7Room.getValue() == null) {
			FDCMsgBox.showWarning(this,"请先选择房间！");
			return;
		}
		if (this.customer.size()==0) {
			FDCMsgBox.showWarning(this,"请先选择客户！");
			return;
		}
		RoomInfo room = (RoomInfo) this.f7Room.getValue();
		AgioParam agioParam = AgioSelectUI.showUI(this, room.getId().toString(), 
				(AgioEntryCollection)this.txtAgio.getUserObject(), this.currAgioParam ,this.editData,isAgioListCanEdit,false);
		if (agioParam != null&&!this.currAgioParam.equals(agioParam)) {
			this.currAgioParam = agioParam;
			this.txtAgio.setUserObject(agioParam.getAgios());
			this.editData.setSpecialAgio(agioParam.getSpecialAgio());
			this.updateAmount();
		}
	}
	protected void setAuditButtonStatus(String oprtType){
	    super.setAuditButtonStatus(oprtType);
	    
    	this.f7Seller.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
    	BaseTransactionInfo bill = (BaseTransactionInfo)editData;
		if(editData!=null){
			if(getBizStateAuditEnum().equals(bill.getBizState())||getBizStateSubmitEnum().equals(bill.getBizState())){
				this.f7Seller.setEnabled(false);
			}
		}
    }
}