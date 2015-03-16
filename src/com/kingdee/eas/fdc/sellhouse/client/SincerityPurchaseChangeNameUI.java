/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.MarketUnitSaleManFactory;
import com.kingdee.eas.fdc.basecrm.client.CusClientHelper;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoCollection;
import com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoInfo;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.ISincerityPurchase;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ProjectProductTypeSet;
import com.kingdee.eas.fdc.sellhouse.ProjectSet;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.ShareOrgUnitInfo;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SinPurSaleMansEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class SincerityPurchaseChangeNameUI extends AbstractSincerityPurchaseChangeNameUI
{
    private static final Logger logger = CoreUIObject.getLogger(SincerityPurchaseChangeNameUI.class);
    
    protected UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
    
    protected List customer=new ArrayList();
	
	private RoomInfo oldRoom = null;
	
	Map sinMap =null;
	  
    protected List saleMan=new ArrayList();
    /**
     * output class constructor
     */
    public SincerityPurchaseChangeNameUI() throws Exception
    {
        super();
    }
    
	public SelectorItemCollection getSelectors()
    {
		
		SelectorItemCollection sels = super.getSelectors();
		sels.add("sincerPriceEntrys.*");
		sels.add("sincerPriceEntrys.moneyDefine.*");
		sels.add("bizState");
		sels.add("orgUnit.*");
		sels.add("CU.*");
		sels.add("sellProject.*");
		sels.add("srcId");
		sels.add("isAutoToInteger");
		sels.add("isBasePriceSell");
		sels.add("toIntegerType");
		sels.add("digit");
//		sels.add("priceAccountType");
		sels.add("isValid");
//		sels.add("isOnRecord");
		sels.add("transactionID");
		sels.add("customer.*");
		sels.add("customer.customer.*");
		sels.add("customer.certificate.*");
		sels.add("customer.customer.mainCustomer.*");
		sels.add("newCommerceChance");
		sels.add("newCommerceChance.customer.*");
		
		sels.add("changeRecordEntryTwo.id");
		sels.add("changeRecordEntryTwo.name");
		sels.add("changeRecordEntryTwo.number");
		sels.add("changeRecordEntryTwo.description");
		sels.add("changeRecordEntryTwo.oldCustomer.id");
		sels.add("changeRecordEntryTwo.oldCustomer.name");
		sels.add("changeRecordEntryTwo.oldCustomer.number");
		sels.add("changeRecordEntryTwo.newCustomer.id");
		sels.add("changeRecordEntryTwo.newCustomer.name");
		sels.add("changeRecordEntryTwo.newCustomer.number");
		sels.add("changeRecordEntryTwo.remark");
		sels.add("changeRecordEntryTwo.changeDate");
		sels.add("changeRecordEntryTwo.operator.id");
		sels.add("changeRecordEntryTwo.operator.name");
		sels.add("changeRecordEntryTwo.operator.number");
		sels.add("changeRecordEntryTwo.oldCusStr");
		sels.add("changeRecordEntryTwo.newCusStr");
			
		sels.add("saleMansEntry.user.*");
		sels.add("saleMansEntry.user.group.name");
		sels.add("saleMansEntry.user.CU.name");
		sels.add("saleMansEntry.*");
		sels.add("saleManStr");
		
		sels.add("customerNames");
		sels.add("customerPhone");
		
		return sels;
    }
	
	
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
      //收款分录存储
        storeSinerPriceEntry();
        this.editData.setIsValid(false);
        setCustomerEntry(this.editData,this.customer);
      //预设初始
//		String cusStr = "";
//		this.editData.setCusStr(cusStr);
//		this.editData.getCustomer().clear();
//		if(null!=customer&&customer.size()>0){
//			
//			Iterator it = this.customer.iterator();
//			while(it.hasNext()){
//				SHECustomerInfo sheCus = (SHECustomerInfo) it.next();
//				SincerityPurchaseCustomerEntryInfo sinPurCusEntry =
//					new SincerityPurchaseCustomerEntryInfo();
//				sinPurCusEntry.setCustomer(sheCus);
//			this.editData.getCustomer().add(sinPurCusEntry);
//			//组合字段
//			cusStr = cusStr + sheCus.getName() + ";";
//			this.editData.setCusStr(cusStr);
//			}
//		}
        
        this.editData.getChangeRecordEntryTwo().clear();
        for (int i = 0; i < this.kdChangeNameRecord.getRowCount(); i++) {
        	IRow row = kdChangeNameRecord.getRow(i);
        	if(row==null){
        		continue;
        	}
        	ChangeRecordEntryTwoInfo item = new ChangeRecordEntryTwoInfo();
    		item.setId(BOSUuid.read(row.getCell("id").getValue().toString()));
    		if(row.getCell("oldCustomer").getValue()!=null){
    			item.setOldCusStr(row.getCell("oldCustomer").getValue().toString());
    		}
    		if(row.getCell("newCustomer").getValue()!=null){
    			item.setNewCusStr(row.getCell("newCustomer").getValue().toString());
    		}
    		if(row.getCell("newCustomer").getUserObject()!=null){
    			item.setOperator((UserInfo)row.getCell("newCustomer").getUserObject());
    		}
    		if(row.getCell("remark").getValue()!=null){
    			item.setRemark(row.getCell("remark").getValue().toString());
    		}
    		
        	this.editData.getChangeRecordEntryTwo().add(item);
		}
        storeSaleMansEntry();
    }
    
    public void storeSaleMansEntry(){
    	String saleManStr="";
		editData.getSaleMansEntry().clear();
		for(int i=0;i<this.saleMan.size();i++){
			if(i==this.saleMan.size()-1){
				saleManStr=saleManStr+((TranSaleManEntryInfo)this.saleMan.get(i)).getUser().getName();
			}else{
				saleManStr=saleManStr+((TranSaleManEntryInfo)this.saleMan.get(i)).getUser().getName()+";";
			}
			SinPurSaleMansEntryInfo entry=new SinPurSaleMansEntryInfo();
			SHEManageHelper.setSaleManEntry(entry,(TranSaleManEntryInfo)this.saleMan.get(i));
			editData.getSaleMansEntry().add(entry);
		}
		editData.setSaleManStr(saleManStr);
    }
    
    public void storeSinerPriceEntry(){
    	SincerReceiveEntryInfo reCeiveEntry =null;
    	SincerReceiveEntryCollection sinColl = this.editData.getSincerPriceEntrys();
    	sinColl.clear();
    	for(int i = 0 ; i <this.kdtSincerPriceEntrys.getRowCount();i++){
    		IRow row = this.kdtSincerPriceEntrys.getRow(i);
    		 reCeiveEntry = new SincerReceiveEntryInfo();
             if(null!=row.getCell("id").getValue()){
            	 reCeiveEntry.setId(BOSUuid.read(row.getCell("id").getValue().toString()));
             }
             if(null!=row.getCell("moneyDefName").getValue()){
            	 reCeiveEntry.setMoneyDefine((MoneyDefineInfo)row.getCell("moneyDefName").getValue());
             }
             if(null!=row.getCell("appDate").getValue()){
            	 reCeiveEntry.setAppDate(new Timestamp(((Date)row.getCell("appDate").getValue()).getTime()));
             }
             if(null!=row.getCell("appAmount").getValue()){
            	 reCeiveEntry.setAppAmount((BigDecimal)row.getCell("appAmount").getValue());
             }
             if(null!=row.getCell("actAmount").getValue()){
            	 reCeiveEntry.setActRevAmount((BigDecimal)row.getCell("actAmount").getValue());
             }
             if(null!=row.getCell("revDate").getValue()){
//            	 reCeiveEntry.setActRevDate(new Timestamp(((Date)row.getCell("revDate").getValue()).getTime()));
             }
             sinColl.add(reCeiveEntry);
         }
    }
    public void setRoomState(){
    	if(!OprtState.ADDNEW.equals(this.getOprtState())&&TransactionStateEnum.WAITTINGFORDEAL.equals(this.editData.getState())){
    		
    	this.contRoom.setEnabled(false);
    	
    	}else{
    		this.contRoom.setEnabled(true);
    	}
    }
    protected void initDataStatus() {
    super.initDataStatus();
    if(OprtState.EDIT.equals(this.getOprtState())){
    	this.kDaddLine.setEnabled(true);
    	this.kDDelLine.setEnabled(true);
		this.btnChangeName.setEnabled(true);
	}
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    	if(OprtState.VIEW.equals(this.getOprtState())){
    		this.prmtSalesman.setEnabled(false);
    	}
    }
   
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.actionPrint.setVisible(true);
    	this.actionPrintPreview.setVisible(true);
    	this.actionPrint.setEnabled(true);
    	this.actionPrintPreview.setEnabled(true);
    	SaleManPromptDialog comBox= new SaleManPromptDialog(this,saleMan,this.oprtState,editData.getId(),editData.getSellProject());
		comBox.setEnabledMultiSelection(true);
		this.prmtSalesman.setSelector(comBox);
		
		this.prmtSalesman.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.prmtSalesman.setEditable(false);
		this.prmtSalesman.getQueryAgent().setEnabledMultiSelection(true);
		
    	initAllF7();
    	   this.btnAddNewCus.setIcon(EASResource.getIcon("imgTbtn_addgroup"));
    	//更名记录页签加载
    	this.btnChangeName.setEnabled(false);
    
    	//单据状态与房间的处理
    	setRoomState();
    	if(OprtState.EDIT.equals(this.getOprtState())){
    		addChangeRoomTable();
    		this.btnChangeName.setEnabled(true);
    		
    	}
    	 this.kdtSincerPriceEntrys.checkParsed();
    	 KDFormattedTextField formattedTextField_CREDIT = new  KDFormattedTextField(); 
	        formattedTextField_CREDIT.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
	        formattedTextField_CREDIT.setPrecision(2);
	        formattedTextField_CREDIT.setSupportedEmpty(true);
	        this.kdtSincerPriceEntrys.getColumn("appAmount").setEditor(new KDTDefaultCellEditor( formattedTextField_CREDIT));
	        this.kdtSincerPriceEntrys.getColumn("actAmount").setEditor(new KDTDefaultCellEditor( formattedTextField_CREDIT));
    	
   
    	
    	//初始化分录格式
	        
		String formatString = "yyyy-MM-dd";
		 this.kdtSincerPriceEntrys.getColumn("appDate").getStyleAttributes()
		 .setNumberFormat(formatString);
		 this.kdtSincerPriceEntrys.getSelectManager().setSelectMode(
				 KDTSelectManager.ROW_SELECT);
		 this.kdtSincerPriceEntrys.getColumn("revDate").getStyleAttributes()
		 	.setNumberFormat(formatString);
		 this.kdtSincerPriceEntrys.getSelectManager().setSelectMode(
		KDTSelectManager.ROW_SELECT);

		 this.kdtSincerPriceEntrys.getColumn("appDate").setEditor(CommerceHelper.getKDDatePickerEditor());
		 this.kdtSincerPriceEntrys.getColumn("revDate").setEditor(CommerceHelper.getKDDatePickerEditor());
    	
		 
		 //合计行
		 
		 KDTFootManager footRowManager = new KDTFootManager(this.kdtSincerPriceEntrys);
			footRowManager.addFootView();
			this.kdtSincerPriceEntrys.setFootManager(footRowManager);
			IRow footRow = footRowManager.addFootRow(0);
			footRow.getCell(0).setValue("合计");
			footRow.getCell(0).getStyleAttributes().setLocked(false);
			footRow.getStyleAttributes().setBackground(
					new Color(0xf6, 0xf6, 0xbf));
			if(!(OprtState.ADDNEW.equals(this.getOprtState()))){//分新增状态下加载
				getTotalDataByRevDetailEntry();
				if((OprtState.VIEW.equals(this.getOprtState()))){//修改时按钮禁止使用
					this.kDaddLine.setEnabled(false);
					this.kDDelLine.setEnabled(false);
					this.btnAddNew.setEnabled(false);
				}

			}
			
			//预约人
			if(null!=this.cusLabel1.getText())
			{
				this.txtAppoinmentPeople.setEnabled(true);
				this.txtAppoinmentPeople.setRequired(true);
			}
			this.btnAuditResult.setVisible(false);
			this.txtDescription.setMaxLength(255);
			this.btnAudit.setVisible(false);
			this.btnAudit.setEnabled(false);
			verfiyCustoemrNull();
			
			
			if(OprtState.ADDNEW.equals(this.getOprtState())){
				
				if(null!=sinMap){
					if(null!=sinMap.get(SHEParamConstant.T1_SIN_PURCHASE_STANDARD)){
						addOneSinPriceRecord();
					}
				}
			}
			this.prmtSalesman.setEntityViewInfo(NewCommerceHelper.getPermitSalemanView(editData.getSellProject(),null));
			if(null!=this.editData.getBizState()){
				if(!(TransactionStateEnum.BAYNUMBER.equals(this.editData.getBizState())
						||TransactionStateEnum.WAITTINGFORDEAL.equals(this.editData.getBizState()))
						){
					this.btnEdit.setEnabled(false);
				}
			}
			if(null!=this.editData.getId()){
				addChangeRoomTable();
			}
//			try {
//				OrgUnitInfo currentOrgUnit=SysContext.getSysContext().getCurrentOrgUnit();
//				UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();	
//				String currDate = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()); 
//				boolean isPermit =MarketUnitControlFactory.getRemoteInstance().exists("where orgUnit.id = '"+currentOrgUnit.getId()+"' and controler.id = '"+currentUserInfo.getId()+"' ");
//				if(isPermit){
//					this.prmtSalesman.setEnabled(true);
//				}else{
//					boolean isDuty= MarketUnitSaleManFactory.getRemoteInstance().exists("where orgUnit.id = '"+currentOrgUnit.getId()+"' and " +
//						"dutyPerson.id = '"+currentUserInfo.getId()+"' and member.id = '"+ currentUserInfo.getId() +"' " +
//							"and accessionDate <= {ts '"+currDate+"'} and dimissionDate >= {ts '"+currDate+"'} and sellProject = '"+((BaseTransactionInfo)editData).getSellProject().getId()+"'");
//					if(isDuty){
//						this.prmtSalesman.setEnabled(true);
//					}else{
//						this.prmtSalesman.setEnabled(false);
//					}
//				}
//			} catch (BOSException e) {
//				e.printStackTrace();
//			}catch (EASBizException e) {
//				e.printStackTrace();
//			}
			
			this.actionAddNewCus.setVisible(false);
    }
    
    /**
     * @description 更名记录页签
     * @author tim_gao
     * @throws Exception 
     * @throws BOSException 
     */
    public void addChangeRoomTable() throws BOSException, Exception{
    		this.kdChangeNameRecord.removeRows();
    		this.kdChangeNameRecord.checkParsed();
    		this.kdChangeNameRecord.getStyleAttributes().setLocked(true);
    		SelectorItemCollection selector = new SelectorItemCollection();
        	selector.add("changeRecordEntryTwo.*");
        	selector.add("changeRecordEntryTwo.operator.*");
        	SincerityPurchaseInfo sinPur = ((ISincerityPurchase)this.getBizInterface()).getSincerityPurchaseInfo(new ObjectUuidPK(BOSUuid.read(this.editData.getId().toString())), selector);
        	ChangeRecordEntryTwoCollection changeCol = (ChangeRecordEntryTwoCollection)sinPur.getChangeRecordEntryTwo();
    		for(int i = 0;i<changeCol.size();i++){
    			ChangeRecordEntryTwoInfo changeRecordInfo = (ChangeRecordEntryTwoInfo)changeCol.get(i);
        		IRow row = this.kdChangeNameRecord.addRow();
        		row.getCell("id").setValue(changeRecordInfo.getId().toString());
        		row.getCell("oldCustomer").setValue(changeRecordInfo.getOldCusStr());
        		row.getCell("newCustomer").setValue(changeRecordInfo.getNewCusStr());
        		row.getCell("changeDate").setValue(changeRecordInfo.getChangeDate());
        		row.getCell("operator").setUserObject(changeRecordInfo.getOperator());
        		row.getCell("operator").setValue(changeRecordInfo.getOperator().getName());
        		row.getCell("remark").setValue(changeRecordInfo .getRemark());
//        		String oldCusName = null;
//        		
//        		for(int j =0 ; j<changeRecordInfo.getOldCustomer().size() ; j++){
//        			oldCusName = (changeRecordInfo.getOldCustomer().get(j)).getCustomer().getName()+";";
//        			
//        		}
//        		row.getCell("oldCustomer").setValue(oldCusName);
//        			String newCusName = null;
//        		
//        		for(int j =0 ; j<changeRecordInfo.getNewCustomer().size() ; j++){
//        			newCusName = (changeRecordInfo.getNewCustomer().get(j)).getCustomer().getName()+";";
//        			
//        		}
        		
    		}
    }
    /**
     * @description 同房间排号情况
     * @author tim_gao
     * @throws Exception 
     * @throws BOSException 
     */
    public void addkdRoomSinPurRecordTable() throws BOSException, Exception{
    	if(null!= this.prmtRoom.getData()){
    		this.kdRoomSinPurRecord.removeRows();
    		this.kdRoomSinPurRecord.checkParsed();
        	EntityViewInfo view = new EntityViewInfo();
        	FilterInfo filter = new FilterInfo();
        	filter.getFilterItems().add(new FilterItemInfo("room",((RoomInfo)this.prmtRoom.getData()).getId().toString()));
        	view.setFilter(filter);
        	SorterItemInfo sort = new SorterItemInfo("bizDate");
        	sort.setSortType(SortType.DESCEND);
        	view.getSorter().add(sort);
        	SincerityPurchaseCollection sinPur = ((ISincerityPurchase)this.getBizInterface()).getSincerityPurchaseCollection(view);
     
        	for(int i = 0 ;i<sinPur.size() ;i++)
        	{
        		SincerityPurchaseInfo sinPurInfo = (SincerityPurchaseInfo)sinPur.get(i);
        		IRow row = this.kdRoomSinPurRecord.addRow();
        		
        		row.getCell("number").setValue(sinPurInfo.getNumber());
//        		String cusName = "";
//        		for(int j =0 ; j<sinPurInfo.getCustomer().size() ; j++){
//        			cusName += ((sinPurInfo.getCustomer().get(j)).getCustomer().getName()+";").equals(";")
//        			?cusName:(sinPurInfo.getCustomer().get(j)).getCustomer().getName()+";";
//        			
//        		}
        		row.getCell("cusName").setValue(sinPurInfo.getCustomerNames());
        		row.getCell("sellProNum").setValue(new Integer(sinPurInfo.getProjectNum()));
        		row.getCell("bizDate").setValue(sinPurInfo.getBizDate());
        		row.getCell("id").setValue(sinPurInfo.getId().toString());
        	}
    	}
    }
//    
//    protected void prmtRoom_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
//    {
//    	if(e.getNewValue()!=(e.getOldValue())){
//    		addkdRoomSinPurRecordTable();
//    		//增加修改单据后对房间状态的转换，排号特殊
//    		if(null!= e.getOldValue()&&OprtState.EDIT.equals(this.getOprtState())){
//    		if(e.getOldValue() instanceof RoomInfo){
//    			RoomInfo room = (RoomInfo) e.getOldValue();
//    			SelectorItemCollection selector = new SelectorItemCollection();
//    			selector.add("sellState");
//    			FilterInfo filter = new FilterInfo();
//    			filter.getFilterItems().add(new FilterItemInfo("isPush",Boolean.TRUE));
//    			IRoom roomFac = (IRoom)RoomFactory.getRemoteInstance();
//    			
//    			if(roomFac.exists(filter)){
//    				
//    				room.setSellState(RoomSellStateEnum.OnShow);
//    			}else{
//    				room.setSellState(RoomSellStateEnum.Init);
//    			}
//    			RoomFactory.getRemoteInstance().updatePartial(room, selector);
//    		}
//    	}
//    	}
//    }
//    	
    
    
	public void initAllF7(){
		//--房屋f7
			
			this.prmtRoom.setSelector(new NewFDCRoomPromptDialog(Boolean.FALSE, null, null,
					MoneySysTypeEnum.SalehouseSys, null,this.editData.getSellProject()));
		//收款明细表 f7
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.SINPUR_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.PREMONEY_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SALEHOUSESYS_VALUE));
			filter.setMaskString("(#0 or #1) and #2");
			view.setFilter(filter);
			this.kdtSincerPriceEntrys.getColumn("moneyDefName").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery", view));
		}
    
    public void loadFields()
    {
        super.loadFields();
        oldRoom = (RoomInfo) this.prmtRoom.getValue();
//        EntityViewInfo view1 = new EntityViewInfo();
//		FilterInfo filter1 = new  FilterInfo();
////		filter.getFilterItems().add(new FilterItemInfo("room.id",room.getId().toString()));
//		filter1.getFilterItems().add(new FilterItemInfo("bizState","BayNumber"));
//		filter1.setMaskString("#0");
//		view1.setFilter(filter1);
//		view1.getSelector().add("bizState");
//		try {
//			SincerityPurchaseCollection col=SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseCollection(view1);
//		} catch (BOSException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
		
		SelectorItemCollection sel = new SelectorItemCollection();
        loadCustomerEntry(editData);
        
        	
        if(null!=this.editData.getSellProject()){
        	this.prmtSellProject.setValue(this.editData.getSellProject());
        }
        //同房屋排号情况加载
        try {
			addkdRoomSinPurRecordTable();
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			logger.warn("同房屋排号页签加载失败！"+e1.getMessage());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logger.warn("同房屋排号页签加载失败！"+e1.getMessage());
		}
        
        //新增时，一些显示
        if(OprtState.ADDNEW.equals(this.getOprtState())){
        	//新增时收款信息插入,要根据
        	//根据项目
        	
        	this.kdtSincerPriceEntrys.checkParsed();
        	
        	//销售顾问为当前登录用户
			UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
			this.prmtSalesman.setValue(user);
			
			
			//根据项目取序号
			EntityViewInfo view = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		SelectorItemCollection selec = new SelectorItemCollection();
    		selec.add("projectNum");
    		filter.getFilterItems().add(new FilterItemInfo("sellProject",((SellProjectInfo)this.getUIContext().get("sellProject")).getId().toString()));
    		view.setFilter(filter);
    		SorterItemInfo sortInfo = new SorterItemInfo("projectNum");
			 sortInfo.setSortType(SortType.DESCEND);
			 view.getSorter().add(sortInfo);
    		
    		SincerityPurchaseCollection sinPurCol=null;
			try {
				sinPurCol = ((ISincerityPurchase)this.getBizInterface()).getSincerityPurchaseCollection(view);
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				logger.warn("项目排号加载失败！"+e.getMessage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.warn("项目排号加载失败！"+e.getMessage());
			}
			
    		if(null!=sinPurCol&&sinPurCol.size()>0 ){
    			if(sinPurCol.get(0).getProjectNum()>=0){
    				if(sinPurCol.get(0).getProjectNum()<10){
    					this.txtSellProNumber.setText("0"+(sinPurCol.get(0).getProjectNum()+1));
    				}else{
    					this.txtSellProNumber.setText((sinPurCol.get(0).getProjectNum()+1)+"");
    				}
    			}else{
    				this.txtSellProNumber.setText("01");
    			}
    		}else{
    			this.txtSellProNumber.setText("01");
    		}
    		
    		if(OprtState.EDIT.equals(this.getOprtState())||OprtState.VIEW.equals(this.getOprtState())){
    			int i = this.editData.getProjectNum();
    			if(i>=0){
    				if(i<10){
    					this.txtSellProNumber.setText("0"+i);
    				}else{
    					this.txtSellProNumber.setText(i+"");
    				}
    				
    			}else{
    				this.txtSellProNumber.setText("01");
    			}
    			
				
				
		
    			
    		}
    		
    		
		    //业务日期为当前日期 
	        this.pkBizDate.setValue(SHEHelper.getCurrentTime());
	        
	        //失效日期 当前日期+失效时限 参数未想好怎么取
	        
	        
		}else{

	        this.kdtSincerPriceEntrys.checkParsed();
			 SincerityPurchaseInfo sinPurInfo = (SincerityPurchaseInfo)this.editData;
		        //载入收款明细分录
			 if(null!=sinPurInfo.getSincerPriceEntrys()){
		     
				 addRevDetailInfo(sinPurInfo.getSincerPriceEntrys());
			 }
		}
        
        
        if(OprtState.EDIT.equals(this.getOprtState())){
        	int i = this.editData.getProjectNum();
        	if(i >=0){
				if(i <10){
				this.txtSellProNumber.setText("0"+i);
				}else{
					this.txtSellProNumber.setText(String.valueOf(i));
				}
			}else{
				this.txtSellProNumber.setText("00");
			}
		
        }
 
//        //加载客户信息
//        if(null!=this.editData.getCustomer()&&this.editData.getCustomer().size()>0){
//        	for(int i =0 ; i<this.editData.getCustomer().size() ; i++){
//        		SHECustomerInfo sheCus = (SHECustomerInfo)this.editData.getCustomer().get(i).getCustomer();
//    			if(0==i){
//    				this.cusLabel1.setText(sheCus.getName());
//    				this.cusLabel1.setUserObject(sheCus);
//    				this.txtAppoinmentPhone.setText(sheCus.getPhone());
//    				this.txtAppoinmentPeople.setText(sheCus.getName());
//    			}else if(1==i){
//    				this.cusLabel2.setText(sheCus.getName());
//    				this.cusLabel2.setUserObject(sheCus);
//    			}else if(2==i){
//    				this.cusLabel3.setText(sheCus.getName());
//    				this.cusLabel3.setUserObject(sheCus);
//    			}
//    			customer.add(sheCus);
//    		}
//        }
        if(null!=this.editData){
        	this.txtAppoinmentPhone.setText(this.editData.getAppointmentPhone());
        }
        
        ChangeRecordEntryTwoCollection twoColl = this.editData.getChangeRecordEntryTwo();
        kdChangeNameRecord.removeRows();
        for (int i = 0; i < twoColl.size(); i++) {
        	ChangeRecordEntryTwoInfo info = twoColl.get(i);
        	if(info!=null){
        		kdChangeNameRecord.checkParsed();
    			IRow row = kdChangeNameRecord.addRow(i);
    			row.getCell("id").setValue(info.getId());
    			row.getCell("oldCustomer").setValue(info.getOldCusStr());
    			row.getCell("newCustomer").setValue(info.getNewCusStr());
    			row.getCell("changeDate").setValue(info.getChangeDate());
    			row.getCell("operator").setValue(info.getOperator().getName());
    			row.getCell("remark").setValue(info.getRemark());
    		}
       }
        loadSalesMan();
    }
   
    
    public void loadSalesMan(){
    	this.saleMan.clear();
		for(int i=0;i<editData.getSaleMansEntry().size();i++){
			this.saleMan.add(editData.getSaleMansEntry().get(i));
		}
		this.prmtSalesman.setValue(this.saleMan.toArray());
    }
    
    /**
     * @decsiription 载入新收款明细分录
     * @author tim_gao
     * 
     */
    public void addSinerityRurchaseInfobyRoom(RoomInfo room){
    	SincerityPurchaseCollection sinPurCol = null;
                if(null!=room){
                	EntityViewInfo view =new  EntityViewInfo();
                	FilterInfo filter = new FilterInfo();
                	filter.getFilterItems().add(new FilterItemInfo("room",room.getId().toString()));
                	SorterItemInfo sort = new SorterItemInfo();
                	sort.setSortType(SortType.DESCEND);
                	view.getSorter().add(sort);
                	view.setFilter(filter);
                	
                	try {
						 sinPurCol = SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseCollection();
					} catch (BOSException e) {
						// TODO Auto-generated catch block
						logger.warn("收款明细分录加载失败！"+e.getMessage());
					} 
                }
                this.kdRoomSinPurRecord.removeRows();
                if(null!=sinPurCol){
                	for(int i =0 ; i<sinPurCol.size() ; i++){
                		SincerityPurchaseInfo sincerityPurchase = (SincerityPurchaseInfo)sinPurCol.get(i);
                	IRow row = this.kdRoomSinPurRecord.addRow();
                	row.getCell("id").setValue(sincerityPurchase.getId());
                	row.getCell("number").setValue(sincerityPurchase.getNumber());
                	String cusName = null;
                	if(null!=sincerityPurchase.getCustomer()){
                		for(int j = 0 ; j<sincerityPurchase.getCustomer().size() ; j++){
                			cusName = sincerityPurchase.getCustomer().get(j).getCustomer().getName()+";";
                		}
                	}
                	row.getCell("cusName").setValue(cusName);
                	row.getCell("sellProNum").setValue(new Integer(sincerityPurchase.getProjectNum()));
                	row.getCell("bizDate").setValue(sincerityPurchase.getBizDate());
                	}
                }
    }
    
    
    
    /**
     * @decsiription 载入收款明细分录
     * @author tim_gao
     * 
     */
    public void addRevDetailInfo(SincerReceiveEntryCollection sinReceiveCol){
    	this.kdtSincerPriceEntrys.checkParsed();
    	this.kdtSincerPriceEntrys.removeRows();
    	CRMHelper.sortCollection(sinReceiveCol, "seq", true);
    	for(int i=0;i<sinReceiveCol.size();i++){
    		SincerReceiveEntryInfo sinInfo = sinReceiveCol.get(i);
    		IRow row = (IRow)this.kdtSincerPriceEntrys.addRow();
    		row.setUserObject(sinInfo);
    		row.getCell("moneyDefName").setValue(sinInfo.getMoneyDefine()!=null?sinInfo.getMoneyDefine():null);
    		row.getCell("appDate").setValue(sinInfo.getAppDate());
    		row.getCell("appAmount").setValue(sinInfo.getAppAmount());
    		BigDecimal actAmount = FDCHelper.ZERO;
    		if(TransactionStateEnum.BAYNUMBER.equals(editData.getBizState())||TransactionStateEnum.QUITNUMBER.equals(editData.getBizState())){
    			actAmount=SHEManageHelper.getActRevAmount(null,sinInfo.getTanPayListEntryId());
    			row.getCell("actAmount").setValue(actAmount);
    		}else{
    			actAmount=sinInfo.getActRevAmount();
    			row.getCell("actAmount").setValue(actAmount);
    		}
    		row.getCell("id").setValue(sinInfo.getId());
    		
    	}
    }
    
    /**
     * output btnAddLine_actionPerformed method
     */
    protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnAddLine_actionPerformed(e);
    }

    /**
     * output menuItemEnterToNextRow_itemStateChanged method
     */
    protected void menuItemEnterToNextRow_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.menuItemEnterToNextRow_itemStateChanged(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    { 
    	verifyInput(e);
        super.actionSave_actionPerformed(e);
        addSinerityRurchaseInfobyRoom((RoomInfo)this.prmtRoom.getData());
    }

    /**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyInput(e);

		// 是否旧房间校验
		if (null != oldRoom) {
			RoomInfo newRoom =  (RoomInfo) this.prmtRoom.getValue();
			
			if (null== this.prmtRoom.getValue()||!(oldRoom.getId().equals(((RoomInfo) this.prmtRoom.getValue()).getId()))) {
				FilterInfo filter = new FilterInfo();
				if(this.editData.getId()!=null){
					filter.getFilterItems().add(new FilterItemInfo("id", this.editData.getId().toString(), CompareType.NOTEQUALS));
				}
				
				filter.getFilterItems().add(new FilterItemInfo("room.id", oldRoom.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("bizState", TransactionStateEnum.BAYNUMBER_VALUE));
				if (!this.getBizInterface().exists(filter)) {// 除了本预约单本房间没有排号单

					SelectorItemCollection sel = new SelectorItemCollection();
					sel.add("id");
					sel.add("isPush");
					sel.add("sellState");
					RoomInfo room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(oldRoom.getId().toString())), sel);
					if (RoomSellStateEnum.SincerPurchase.equals(room.getSellState())) {// 排号状态
						if (room.isIsPush()) {
							room.setSellState(RoomSellStateEnum.OnShow);

						} else if (!room.isIsPush()) {
							room.setSellState(RoomSellStateEnum.Init);

						}
						RoomFactory.getRemoteInstance().updatePartial(room, sel);
					}

				}
			}
		}
		SHEManageHelper.addCommerceChance(this,this.customer,this.saleMan,(BaseTransactionInfo)editData);
		super.actionSubmit_actionPerformed(e);
		addSinerityRurchaseInfobyRoom((RoomInfo) this.prmtRoom.getData());
	}

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    protected String getTDFileName() {
		return "/bim/fdc/sellhouse/SinPur";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.SincerityPurchasePirntQuery");
		
	}
    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {	
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
    	SinPurDataProvider data = new SinPurDataProvider(
    			editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
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
    	SinPurDataProvider data = new SinPurDataProvider(
    			editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
    
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
        this.prmtSalesman.setEnabled(true);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddLine_actionPerformed(e);
    }

    /**
     * output actionCopyLine_actionPerformed
     */
    public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyLine_actionPerformed(e);
    }

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionInsertLine_actionPerformed(e);
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoveLine_actionPerformed(e);
    }

    /**
     * output actionCreateFrom_actionPerformed
     */
    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateFrom_actionPerformed(e);
    }

    /**
     * output actionCopyFrom_actionPerformed
     */
    public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyFrom_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionViewSubmitProccess_actionPerformed
     */
    public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSubmitProccess_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionSendingMessage_actionPerformed
     */
    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendingMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actionViewSignature_actionPerformed
     */
    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSignature_actionPerformed(e);
    }

    /**
     * output actionSendMail_actionPerformed
     */
    public void actionSendMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMail_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }

    /**
     * output actionAttamentCtrl_actionPerformed
     */
    public void actionAttamentCtrl_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttamentCtrl_actionPerformed(e);
    }

	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return SincerityPurchaseFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return null;
	}

	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return this.txtNumber;
	}
	
	protected IObjectValue createNewData() {
		/* TODO 自动生成方法存根 */
		SincerityPurchaseInfo sinPur = new SincerityPurchaseInfo();
		sinPur.setSellProject((SellProjectInfo)this.getUIContext().get("sellProject"));
		
		if(this.getUIContext().get("clues")!=null){
			CluesManageInfo cluesInfo = (CluesManageInfo)this.getUIContext().get("clues");
			sinPur.setCluesCus(cluesInfo);
			txtAppoinmentPeople.setEditable(false);
		    txtAppoinmentPhone.setEditable(false);
			sinPur.setAppointmentPeople(cluesInfo.getName());
			sinPur.setAppointmentPhone(cluesInfo.getPhone());
		}
		RoomInfo room = null;
		if(null!=this.getUIContext().get("roomId")){
			try {
				room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(this.getUIContext().get("roomId").toString())));
				sinPur.setRoom(room);
				this.getUIContext().put("roomId", null);
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				logger.warn("加载房间信息失败！");
				FDCMsgBox.showWarning("加载房间信息失败！");
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				logger.warn("加载房间信息失败！");
				FDCMsgBox.showWarning("加载房间信息失败！");
			} catch (UuidException e) {
				// TODO Auto-generated catch block
				logger.warn("加载房间信息失败！");
				FDCMsgBox.showWarning("加载房间信息失败！");
			}
		}
		
//		if(this.getUIContext().get("customer")!=null){
//			List customer=(ArrayList)this.getUIContext().get("customer");
//			for(int i=0;i<customer.size();i++){
//				SHECustomerInfo entry=(SHECustomerInfo)customer.get(i);
//				TranCustomerEntryInfo tranInfo=new TranCustomerEntryInfo();
//				tranInfo.setCustomer(entry);
//				tranInfo.setName(entry.getName());
//				tranInfo.setAddress(entry.getMailAddress());
//				tranInfo.setCertificate(entry.getCertificateType());
//				tranInfo.setCertificateNumber(entry.getCertificateNumber());
//				tranInfo.setPhone(entry.getPhone());
//				tranInfo.setTel(entry.getTel());
//				this.customer.add(tranInfo);
//			}
//			setCustomerEntry(sinPur,this.customer);
//		}
		if(room!=null){
			sinMap = RoomDisplaySetting.getNewProjectProductTypeSet(null,sinPur.getSellProject().getId().toString(),room.getProductType()==null?room.getBuilding().getProductType().getId().toString():room.getProductType().getId().toString());
		}else{
			sinMap = RoomDisplaySetting.getNewProjectProductTypeSet(null,sinPur.getSellProject().getId().toString(),null);
		}
//		if(sinPur.getSaleMansEntry().size()==0){
//		 	SinPurSaleMansEntryInfo entry=new SinPurSaleMansEntryInfo();
//			entry.setUser(SysContext.getSysContext().getCurrentUserInfo());
//			sinPur.getSaleMansEntry().add(entry);
//			sinPur.setSaleManStr(SysContext.getSysContext().getCurrentUserInfo().getName());
//		}
		sinPur.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return sinPur;
	}
	
	public void actionChangeName_actionPerformed(ActionEvent e) throws Exception
    {
	

//		if(row.getCell("sincerityState").getValue().toString().equals(SincerityPurchaseStateEnum.ArrangeNum.toString())){
		if(!(TransactionStateEnum.BAYNUMBER.equals(this.editData.getBizState()))){	
			FDCMsgBox.showWarning("请在排号状态下的单据做更名操作！");
			SysUtil.abort();
		}
//		Map map = new UIContext(this);
		if(OprtState.ADDNEW.equals(this.getOprtState())||null==this.editData){
			if(this.editData.getId()==null){
				FDCMsgBox.showWarning("请提交后再做更名操作！");
				SysUtil.abort();
			}
			
		}
			UIContext uiContext = new UIContext(this);
		uiContext.put("selectedID",this.editData.getId().toString());
//		uiContext.put("sinPurID", row.getCell("id").getValue().toString());
//			uiContext.put(UIContext.ID, getSelectedKeyValue());

			IUIWindow uiWindow = null ; 
			// UIFactoryName.MODEL 为弹出模式
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
			create("com.kingdee.eas.fdc.sellhouse.client.SincerityChangeNameEditUI",uiContext, null,
							OprtState.ADDNEW);
			uiWindow.show();
		
		
		//更名记录签
		addChangeRoomTable();
		
		this.actionSubmit.setEnabled(false);
		this.actionEdit.setEnabled(true);
		
    }
	
	protected void initOldData(IObjectValue dataObject) {
		/*if (OprtState.ADDNEW.equals(this.getOprtState())) {
			// super.initOldData(dataObject);
		} else {
			if (!this.isOld) {
				super.initOldData(dataObject);
			}
		}*/
	}
	
	
	
	  /**
     * output kDaddLine_actionPerformed method
     */
    protected void kDaddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	super.kDaddLine_actionPerformed(e);
    	IRow row =this.kdtSincerPriceEntrys.addRow();
    	SincerReceiveEntryInfo sinInfo = new SincerReceiveEntryInfo();
    	row.setUserObject(sinInfo);
//    	row.getCell("revDate").setValue(new Date());
    	//诚意金标准金
    	BigDecimal appAmount =FDCHelper.ZERO;
    	
    	if(null!= sinMap){
    		if(null!= sinMap.get(SHEParamConstant.T1_SIN_PURCHASE_STANDARD)){
    			appAmount = ((BigDecimal) sinMap.get(SHEParamConstant.T1_SIN_PURCHASE_STANDARD));
    		}
    	}
    	row.getCell("appAmount").setValue(appAmount);
    	row.getCell("actAmount").getStyleAttributes().setLocked(true);
    	row.getCell("revDate").getStyleAttributes().setLocked(true);
    }
    
    /**
     * output kDDelLine_actionPerformed method
     */
    protected void kDDelLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	
    	super.kDDelLine_actionPerformed(e);
    	int index = this.kdtSincerPriceEntrys.getSelectManager().getActiveRowIndex();
    	if(index>=0){
    	this.kdtSincerPriceEntrys.removeRow(this.kdtSincerPriceEntrys.getSelectManager().getActiveRowIndex());
    	}
    	else{
    		FDCMsgBox.showWarning("请选择需要删除的收款明细行！");
    		SysUtil.abort();
    	}
    
    }
	
//    public void clearCus(){
//    	this.customer = new ArrayList();
//    	this.cusLabel1.setText(null);
//    	this.cusLabel1.setUserObject(null);
//    	this.cusLabel2.setText(null);
//    	this.cusLabel2.setUserObject(null);
//    	this.cusLabel3.setText(null);
//    	this.cusLabel3.setUserObject(null);
//    	
//    }
    /**
     * output btnSelecCus_actionPerformed method
     */
    protected void btnSelecCus_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {	
    	this.customer =SHEManageHelper.customerSelect(this,this.customer,editData.getSellProject(),false,true);
    	this.storeCustomerEntry();
		this.loadCustomerEntry(editData);
		
//		if(this.customer.size()>0){
//			if(this.customer.get(0)!=null){
//				SincerityPurchaseCustomerEntryInfo entryInfo=(SincerityPurchaseCustomerEntryInfo)this.customer.get(0);
//				if(entryInfo.getCustomer()!=null){
//					this.txtAppoinmentPeople.setText(entryInfo.getCustomer().getName());
//				}
//				
//			}
//		}
	}
    protected void storeCustomerEntry() {
		editData.getCustomer().clear();
		setCustomerEntry(editData,this.customer);
	}
    protected void loadCustomerEntry(SincerityPurchaseInfo info){
		this.customer=new ArrayList();
		for(int i=0;i<info.getCustomer().size();i++){
			SincerityPurchaseCustomerEntryInfo entry=info.getCustomer().get(i);
			this.customer.add(entry);
		}
		SHEManageHelper.loadCustomer(cusLabel1, cusLabel2, cusLabel3,cusLabel4,cusLabel5 ,this.customer, null, info);
		for(int i=0;i<this.customer.size();i++){
			TranCustomerEntryInfo entry=(TranCustomerEntryInfo)this.customer.get(i);
			if(entry.isIsMain()){
				this.txtAppoinmentPeople.setText(entry.getName());
				this.txtAppoinmentPhone.setText(entry.getPhone());
				this.txtCertificateNumber.setText(entry.getCustomer().getCertificateNumber());
//				this.txtAppoinmentPeople.setEnabled(false);
//				this.txtAppoinmentPhone.setEnabled(false);
			}
		}
	}
    private void setCustomerEntry(SincerityPurchaseInfo sign,List customerList){
    	if(customerList==null){
    		return;
    	}
		String customerNames="";
		String customerPhone="";
		sign.getCustomer().clear();
		
		SinPurSaleMansEntryInfo entry=new SinPurSaleMansEntryInfo();
		for(int i=0;i<customerList.size();i++){
			SincerityPurchaseCustomerEntryInfo info =new SincerityPurchaseCustomerEntryInfo();
			TranCustomerEntryInfo tranInfo=(TranCustomerEntryInfo)customerList.get(i);
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
			if(i==customerList.size()-1){
				customerNames=customerNames+info.getName();
				customerPhone=customerPhone+phone;
			}else{
				customerNames=customerNames+info.getName()+";";
				customerPhone=customerPhone+phone+";";
			}
			sign.getCustomer().add(info);
			entry.setUser(info.getCustomer().getPropertyConsultant());
		}
		sign.setCustomerNames(customerNames);
		sign.setCusStr(customerNames);
		sign.setCustomerPhone(customerPhone);
		
		if(this.saleMan.size()==0&&entry.getUser()!=null){
			this.saleMan.add(entry);
			this.prmtSalesman.setValue(this.saleMan.toArray());
		}
	}
    /**
     * output cusLabel1_mouseClicked method
     */
    protected void cusLabel1_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {	if(null==this.cusLabel1.getText()||("").equals(this.cusLabel1.getText())){
    		return;
    	}
    	UIContext uiContext = new UIContext(this);
    	
		uiContext.put(UIContext.INIT_DATAOBJECT, new CustomerRptEditUI());
		
		EntityViewInfo view=CommerceHelper.getPermitCustomerView(user, MoneySysTypeEnum.SalehouseSys);
	
		uiContext.put("view", view);
		
		uiContext.put(UIContext.ID,((SHECustomerInfo)this.cusLabel1.getUserObject()).getId());
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				CustomerRptEditUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
    }
    /**
     * output cusLabel4_mouseClicked method
     */
   protected void cusLabel4_mouseClicked(java.awt.event.MouseEvent e) throws Exception
   {
	   if(null==this.cusLabel4.getText()||("").equals(this.cusLabel4.getText())){
   		return;
   	}
   	UIContext uiContext = new UIContext(this);
   	
		uiContext.put(UIContext.INIT_DATAOBJECT, new CustomerRptEditUI());
		
		EntityViewInfo view=CommerceHelper.getPermitCustomerView(user, MoneySysTypeEnum.SalehouseSys);
	
		uiContext.put("view", view);
		
		uiContext.put(UIContext.ID,((SHECustomerInfo)this.cusLabel4.getUserObject()).getId());
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				CustomerRptEditUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
   }

   /**
    * output cusLabel5_mouseClicked method
    */
   protected void cusLabel5_mouseClicked(java.awt.event.MouseEvent e) throws Exception
   {if(null==this.cusLabel5.getText()||("").equals(this.cusLabel5.getText())){
		return;
	}
	UIContext uiContext = new UIContext(this);
	
	uiContext.put(UIContext.INIT_DATAOBJECT, new CustomerRptEditUI());
	
	EntityViewInfo view=CommerceHelper.getPermitCustomerView(user, MoneySysTypeEnum.SalehouseSys);

	uiContext.put("view", view);
	
	uiContext.put(UIContext.ID,((SHECustomerInfo)this.cusLabel5.getUserObject()).getId());
	
	IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
			CustomerRptEditUI.class.getName(), uiContext, null, "VIEW");
	uiWindow.show();
   }
    /**
     * output cusLabel2_mouseClicked method
     */
    protected void cusLabel2_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    	if(null==this.cusLabel2.getText()||("").equals(this.cusLabel2.getText())){
    		return;
    	}
    	UIContext uiContext = new UIContext(this);
    	
		uiContext.put(UIContext.INIT_DATAOBJECT, new CustomerRptEditUI());
		
		EntityViewInfo view=CommerceHelper.getPermitCustomerView(user, MoneySysTypeEnum.SalehouseSys);
	
		uiContext.put("view", view);
		
		uiContext.put(UIContext.ID,((SHECustomerInfo)this.cusLabel2.getUserObject()).getId());
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				CustomerRptEditUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
    }

    /**
     * output cusLabel3_mouseClicked method
     */
    protected void cusLabel3_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    	if(null==this.cusLabel3.getText()||("").equals(this.cusLabel3.getText())){
    		return;
    	}
    	UIContext uiContext = new UIContext(this);
    	
		uiContext.put(UIContext.INIT_DATAOBJECT, new CustomerRptEditUI());
		
		EntityViewInfo view=CommerceHelper.getPermitCustomerView(user, MoneySysTypeEnum.SalehouseSys);
	
		uiContext.put("view", view);
		
		uiContext.put(UIContext.ID,((SHECustomerInfo)this.cusLabel3.getUserObject()).getId());
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				CustomerRptEditUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
    }
    
    /**
     * output kdtSincerPriceEntrys_editStopped method
     */
    protected void kdtSincerPriceEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    	getTotalDataByRevDetailEntry();
    	
    }
    
    /**
     * @description  计算收款分录合计行
     * @author tim_gao
     */
    public void getTotalDataByRevDetailEntry(){
    	KDTFootManager footManager = this.kdtSincerPriceEntrys.getFootManager();
    	BigDecimal amount = FDCHelper.ZERO;
    	
    	IRow footRow = footManager.getFootRow(0);
    	for(int j=0 ; j<this.kdtSincerPriceEntrys.getColumnCount();j++){
    		for(int i = 0 ; i<this.kdtSincerPriceEntrys.getRowCount() ; i ++){
    			if(this.kdtSincerPriceEntrys.getColumnKey(j).equals("actAmount")||this.kdtSincerPriceEntrys.getColumnKey(j).equals("appAmount")){
    				if(null != this.kdtSincerPriceEntrys.getCell(i, j).getValue()){
    					amount = amount.add((BigDecimal) this.kdtSincerPriceEntrys.getCell(i, j).getValue());
            		}else{
    					amount = amount.add(FDCHelper.ZERO);
    				}
    				footRow.getCell(j).setValue(amount);
        		}
        	}
    	
    		amount = FDCHelper.ZERO;
    	}
    }
    
  
    	protected void verifyInput(ActionEvent e) throws Exception {
    	//排号不能为空
    	if(null==this.txtSellProNumber.getText()||("").equals(this.txtSellProNumber.getText())){
    		FDCMsgBox.showWarning("排号不能为空!");
    		SysUtil.abort();
    	}
    	//必录项校验
    	if(this.txtNumber.isEnabled()==true){
    		if(null==this.txtNumber.getText()||("").equals(this.txtNumber.getText())){
        		FDCMsgBox.showWarning("单据编码不能为空");
        		SysUtil.abort();
        	}
    	}
    	
    	
    	
    	if(customer==null||customer.size()<=0){
    		//-- 预约人 
        	if(null==this.txtAppoinmentPeople.getText()||("").equals(this.txtAppoinmentPeople.getText())){
        		FDCMsgBox.showWarning("预约人不能为空!");
        		SysUtil.abort();
        	}
        	//-- 联系电话 
        	if(null==this.txtAppoinmentPhone.getText()||("").equals(this.txtAppoinmentPhone.getText())){
        		FDCMsgBox.showWarning("联系电话不能为空!");
        		SysUtil.abort();
        	}
    	}
    	
    	//-- 销售顾问 
    	if(null==this.prmtSalesman.getValue()){
    		FDCMsgBox.showWarning("销售顾问不能为空!");
    		SysUtil.abort();
    	}
    	//-- 业务日期 
    	if(null==this.pkBizDate.getValue()){
    		FDCMsgBox.showWarning("业务日期不能为空!");
    		SysUtil.abort();
    	}
    	//-- 失效日期
    	if(null==this.pkInvalidationDate.getValue()){
    		FDCMsgBox.showWarning("失效日期不能为空!");
    		SysUtil.abort();
    	}
    	//日期比较
    	if(((Date)this.pkBizDate.getValue()).after((Date)this.pkInvalidationDate.getValue())){
    		FDCMsgBox.showWarning("业务日期需设置早于失效日期!");
    		SysUtil.abort();
    	}
    	if(("00").equals(this.txtSellProNumber.getText())||("0").equals(this.txtSellProNumber.getText())){
			FDCMsgBox.showWarning("预约排号不能为00！");
			SysUtil.abort();
		}
    	
    	
    		if(null!=this.txtSellProNumber.getText()){
        		//排号重复
    			FilterInfo filter = new FilterInfo();
    			filter.getFilterItems().add(new FilterItemInfo("sellProject",this.editData.getSellProject().getId().toString()));
            	filter.getFilterItems().add(new FilterItemInfo("projectNum",new Integer(this.txtSellProNumber.getText())));
    			
    			if(OprtState.ADDNEW.equals(this.getOprtState())){
            
            	}else{
    				filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.NOTEQUALS));
    			}
            	if(((ISincerityPurchase)this.getBizInterface()).exists(filter)){
            		FDCMsgBox.showWarning("排号重复！");
            		SysUtil.abort();
            	}
        	
    		}else{
    			FDCMsgBox.showWarning("项目排号不能为空！");
    			SysUtil.abort();
    		}
    	//房间未推盘参数校验
    		RoomInfo room = (RoomInfo) this.prmtRoom.getValue();
    		
    		if(room!=null){
    			// 对房间状态的判断
			if (!RoomSellStateEnum.Init.equals(room.getSellState())
					&& !RoomSellStateEnum.OnShow.equals(room.getSellState())
					&& !RoomSellStateEnum.SincerPurchase.equals(room
							.getSellState())
					&& !RoomSellStateEnum.KeepDown.equals(room.getSellState())) {
				FDCMsgBox.showWarning("此房间已有交易，请选择其他房间排号!");
				SysUtil.abort();

			}
			if (null != room
					&& RoomSellStateEnum.Init.equals(room.getSellState())) {

				if (room.getHouseProperty()
						.equals(HousePropertyEnum.Attachment)) {
					FDCMsgBox.showWarning("附属房产不能单独做销售！");
					SysUtil.abort();
				}
//				RoomDisplaySetting set = new RoomDisplaySetting(
//						new String[] { RoomDisplaySetting.PROJECT_SET_MAP });
				Map proSet = RoomDisplaySetting.getNewProjectSet(null,this.editData.getSellProject().getId()
						.toString());
				if (null != proSet) {
					Boolean isEnabled = (Boolean) proSet
							.get(SHEParamConstant.T2_IS_NO_SELL_CAN_SIN);
					if (Boolean.FALSE.equals(isEnabled)) {
						FDCMsgBox.showWarning("【非可售房源允许预约排号】参数未启用，不能进行预约排号操作!");
						SysUtil.abort();
					}
				} else {
					FDCMsgBox.showWarning("【非可售房源允许预约排号】参数未启用，不能进行预约排号操作!");
					SysUtil.abort();
				}
			}
    		}
    	   
    		   //校验当有诚意金时客户不能为空
    		   if(this.kdtSincerPriceEntrys.getRowCount()>0){
    			  if(customer==null||customer.size()<=0){
    				  FDCMsgBox.showWarning("客户不能为空！");
    				  SysUtil.abort();
    			  }
    		   }
    		   //诚意金等不能为空
    		   for(int s = 0 ; s<this.kdtSincerPriceEntrys.getRowCount() ; s++){
    			   IRow row = this.kdtSincerPriceEntrys.getRow(s);
    			   if(null==row.getCell("moneyDefName").getValue()){
    				   FDCMsgBox.showWarning("诚意金不能为空！");
    				   SysUtil.abort();
    				   break;
    			   }
    			   
    		   }
    		   
    		   //置业顾问
    			FDCClientVerifyHelper.verifyEmpty(this, this.prmtSalesman);
    }
    	
    	/**
	 * output prmtSalesman_dataChanged method
	 */
	protected void prmtSalesman_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
//		if (null != e.getOldValue()) {
//			if (!e.getOldValue().equals(e.getNewValue())) {
//				this.cusLabel1.setUserObject(null);
//				this.cusLabel1.setText(null);
//				this.cusLabel2.setUserObject(null);
//				this.cusLabel2.setText(null);
//				this.cusLabel3.setUserObject(null);
//				this.cusLabel3.setText(null);
//				this.cusLabel4.setUserObject(null);
//				this.cusLabel4.setText(null);
//				this.cusLabel5.setUserObject(null);
//				this.cusLabel5.setText(null);
//				customer = null;
//			}
//		}
		if(e.getNewValue()==null){
			this.saleMan.clear();
		}
	}
        protected void verfiyCustoemrNull(){
        	if(null!=customer&&customer.size()>0){
        		this.txtAppoinmentPeople.setRequired(false);
        		this.txtAppoinmentPhone.setRequired(false);
        		this.txtAppoinmentPeople.setEnabled(false);
        		this.txtAppoinmentPhone.setEnabled(false);
        	}else{
        		this.txtAppoinmentPeople.setRequired(true);
        		this.txtAppoinmentPhone.setRequired(true);
        		this.txtAppoinmentPeople.setEnabled(true);
        		this.txtAppoinmentPhone.setEnabled(true);
        	}
        }
        
        /**
         * output pkBizDate_dataChanged method
         */
        protected void pkBizDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
        {
        	if(null!=e.getNewValue()){
        		int vaildDate = 0;
        		Calendar calTime = Calendar.getInstance();
				calTime.setTime((Date)this.pkBizDate.getValue());
				
					if(null!=sinMap){
						if(null!=sinMap.get(SHEParamConstant.T1_KEEP_DOWN_LIMIT_TIME)){
							if(getParamSet()==null) return;
							vaildDate=((Integer)getParamSet().get(SHEParamConstant.T1_KEEP_DOWN_LIMIT_TIME)).intValue();
						}
						calTime.add(Calendar.DATE,vaildDate);
		        		this.pkInvalidationDate.setValue(calTime.getTime());
					}
					
				
				
        	}
        
        }
        
        public Map getParamSet(){

      	  RoomInfo room = (RoomInfo) this.prmtRoom.getValue();
      	  ProductTypeInfo productType =null;
      	  if(null!=room){
      	  productType =  room.getProductType();
      	  }
    
      	Map paramSetValues =null;
      
//  		Map paramSetValues =RoomDisplaySetting.getNewProjectProductTypeSet(this.sellProject.getId().toString(), null);
      	
      		//获得参数
      		if(null!=productType){
      			paramSetValues =RoomDisplaySetting.getNewProjectProductTypeSet(null,this.editData.getSellProject().getId().toString(),productType.getId().toString());
      		}else{
      			paramSetValues =RoomDisplaySetting.getNewProjectProductTypeSet(null,this.editData.getSellProject().getId().toString(),null);
      		
      		}
      		
      	 
        	
//        	if(null==paramSetValues){
//        		FDCMsgBox.showWarning("请在售楼功能设置中设置系统默认值！");
//        	}
        	return paramSetValues;
        }
        
        public void addOneSinPriceRecord() throws EASBizException, BOSException{
        	this.kdtSincerPriceEntrys.checkParsed();
        	IRow row =this.kdtSincerPriceEntrys.addRow();
	    	SincerReceiveEntryInfo sinInfo = new SincerReceiveEntryInfo();
	    	row.setUserObject(sinInfo);
//	    	row.getCell("revDate").setValue(new Date());
	    	//诚意金标准金
	    	BigDecimal appAmount =FDCHelper.ZERO;
	    	//固定的诚意金
	    	MoneyDefineInfo money =SHEManageHelper.getSinMoneyDefine();
    		row.getCell("moneyDefName").setValue(money);
			appAmount = ((BigDecimal)getParamSet().get(SHEParamConstant.T1_SIN_PURCHASE_STANDARD));
	    	row.getCell("appAmount").setValue(appAmount);
	    	row.getCell("actAmount").getStyleAttributes().setLocked(true);
	    	row.getCell("revDate").getStyleAttributes().setLocked(true);
        }
        
        /**
         * @description 对比有参数的值是否变化过，变化了不该，没有变化就改成新的到参数中的值
         * @author tim_gao
         * @throws BOSException 
         * @throws EASBizException 
         */
        protected void parmValueChange() throws EASBizException, BOSException{
        	//对比的参数
//        	Date date = (Date)this.pkInvalidationDate.getValue();
        	//新时间
        	//新金额
        	if(this.pkBizDate.getValue()==null) return;
			BigDecimal appAmount =FDCHelper.ZERO;
        	int vaildDate = 0;
    		Calendar calTimeNew = Calendar.getInstance();
			calTimeNew.setTime((Date)this.pkBizDate.getValue());
			Map tempProProduct = getParamSet();
			
				if(null!=tempProProduct){
					if(null!=tempProProduct.get(SHEParamConstant.T1_KEEP_DOWN_LIMIT_TIME)){
						vaildDate=((Integer)getParamSet().get(SHEParamConstant.T1_KEEP_DOWN_LIMIT_TIME)).intValue();
					}
					calTimeNew.add(Calendar.DATE,vaildDate);
					if(null!= tempProProduct.get(SHEParamConstant.T1_SIN_PURCHASE_STANDARD)){
		    			appAmount = ((BigDecimal) tempProProduct.get(SHEParamConstant.T1_SIN_PURCHASE_STANDARD));
		    		}
				}else{//为空跳出
					return;
				}
				//------------------------处理时间部分
				//得到旧参数时间
		    	Calendar calTimeOld = Calendar.getInstance();
	    		calTimeOld.setTime((Date)this.pkBizDate.getValue());
				
					if(null!=sinMap){
						if(null!=sinMap.get(SHEParamConstant.T1_KEEP_DOWN_LIMIT_TIME)){
							vaildDate=((Integer)sinMap.get(SHEParamConstant.T1_KEEP_DOWN_LIMIT_TIME)).intValue();
						}
						  calTimeOld.add(Calendar.DATE,vaildDate);
					}
					//不为空的时候做对比是否修改，等于空直接填入
			if(null!=sinMap){
				//没有变取新的填入,变化则不填入
				Calendar calTime = Calendar.getInstance();
	    		calTime.setTime((Date)this.pkInvalidationDate.getValue());
    			if(calTimeOld.YEAR==calTime.YEAR&&calTimeOld.MONTH==calTime.MONTH&&calTimeOld.DATE==calTime.DATE){
    				
					this.pkInvalidationDate.setValue(calTimeNew.getTime());
					
    			}	
			}else{
				 
					this.pkInvalidationDate.setValue(calTimeNew.getTime());
			}
					
			//------------------------处理收款信息部分	
					if(this.kdtSincerPriceEntrys.getRowCount()>0){
						
						//得到旧的诚意金
						BigDecimal appAmountOld =FDCHelper.ZERO;
						if(null!=sinMap){
							if(null!= sinMap.get(SHEParamConstant.T1_SIN_PURCHASE_STANDARD)){
								appAmountOld = ((BigDecimal) sinMap.get(SHEParamConstant.T1_SIN_PURCHASE_STANDARD));
				    		}
						}
						
						//金额是否填入
			        	for(int i = 0 ; i < this.kdtSincerPriceEntrys.getRowCount() ; i++){
			        		IRow row = this.kdtSincerPriceEntrys.getRow(i);
			        		MoneyDefineInfo money = (MoneyDefineInfo)row.getCell("moneyDefName").getValue();
			        		if(null!=money){
			        			if(MoneyTypeEnum.SinPur.equals(money.getMoneyType())){
			        				//没有变取新的填入
					        		if(0==	appAmountOld .compareTo((BigDecimal)(row.getCell("appAmount").getValue()))){
					        		
					        			row.getCell("appAmount").setValue(appAmount);
					        		}
			        			}
			        		}
			        	
			        		
			        	}
					}else{
						//增加诚意金的一条记录
						addOneSinPriceRecord();
					}
					
					//得到新的参数
					sinMap = this.getParamSet();
		
        
        }
        
        
        /**
         * @description 修改房间的操作
         * @author tim_gao
         */
        protected void prmtRoom_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
        	parmValueChange();
        	addkdRoomSinPurRecordTable();
        }
//		if (e.getNewValue() != (e.getOldValue())) {
//			addkdRoomSinPurRecordTable();
//
//			if (OprtState.EDIT.equals(this.getOprtState())) {
//
//				if (null != e.getOldValue()) {
//
//					if (null != e.getNewValue()) {
//						if (!e.getNewValue().equals(e.getOldValue())) {
//							RoomInfo oldRoom = ((RoomInfo) e.getOldValue());
//
//							FilterInfo filter = new FilterInfo();
//							filter.getFilterItems().add(new FilterItemInfo("id", this.editData.getId().toString(), CompareType.NOTEQUALS));
//							filter.getFilterItems().add(new FilterItemInfo("room.id", oldRoom.getId().toString()));
//							filter.getFilterItems().add(new FilterItemInfo("bizState", TransactionStateEnum.BAYNUMBER_VALUE));
//							if (!this.getBizInterface().exists(filter)) {// 除了本预约单本房间没有排号单
//
//								SelectorItemCollection sel = new SelectorItemCollection();
//								sel.add("id");
//								sel.add("isPush");
//								sel.add("sellState");
//								RoomInfo room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(oldRoom.getId().toString())), sel);
//								if (RoomSellStateEnum.SincerPurchase.equals(room.getSellState())) {// 排号状态
//									if (room.isIsPush()) {
//										room.setSellState(RoomSellStateEnum.OnShow);
//
//									} else if (!room.isIsPush()) {
//										room.setSellState(RoomSellStateEnum.Init);
//
//									}
//									RoomFactory.getRemoteInstance().updatePartial(room, sel);
//								}
//							}
//						}
//					}
//				}
//			}
//
//		}
//
//	}

        /**
         * output prmtRoom_willShow method
         */
        protected void prmtRoom_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
        {
        	if(null!=this.prmtRoom.getValue()){
        	if(OprtState.EDIT.equals(this.getOprtState())){
        	FDCMsgBox.showWarning("房间修改后，之前房间根据是否有其他预约,回复之前销售状态！");
        	}
        	}
        }
        
        /**
         * output actionAddNewCus_actionPerformed method
         */
        public void actionAddNewCus_actionPerformed(ActionEvent e) throws Exception
        {
        	if(null==this.editData.getSellProject()){
        		return;
        	}
    		Map linkedCus = CusClientHelper.addNewCusBegin(this, this.editData.getSellProject().getId().toString());
    		UIContext uiContext = new UIContext(this);
    		if (linkedCus != null) {
    			uiContext.putAll(linkedCus);
    			uiContext.put("sellProject",this.editData.getSellProject());
    			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerRptEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
    			uiWindow.show();
    		}
        }
}