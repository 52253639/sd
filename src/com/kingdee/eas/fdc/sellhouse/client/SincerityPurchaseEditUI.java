/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.PreChangeEvent;
import com.kingdee.bos.ctrl.swing.event.PreChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.message.common.util.Uuid;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryCollection;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryInfo;
import com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.CasSetting;
import com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.FaithAmountSetting;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.GatheringEnum;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.ISincerityPurchase;
import com.kingdee.eas.fdc.sellhouse.IntentionRoomsEntry;
import com.kingdee.eas.fdc.sellhouse.IntentionRoomsEntryCollection;
import com.kingdee.eas.fdc.sellhouse.IntentionRoomsEntryInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellOrderInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordEnum;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class SincerityPurchaseEditUI extends AbstractSincerityPurchaseEditUI {

	private static final Logger logger = CoreUIObject
			.getLogger(SincerityPurchaseEditUI.class);
	// 当前被激活的行
	private int currentActiveRowIndex = 0;
	RoomDisplaySetting setting = new RoomDisplaySetting();
	Map sellProAndBuildingMap = new HashMap();

	protected void setAuditButtonStatus(String oprtType) {

	}

	private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();

	/**
	 * output class constructor
	 */
	public SincerityPurchaseEditUI() throws Exception {
		super();
	}

	public void loadFields() {
		tblChangeRecord.checkParsed();
		SincerityPurchaseInfo info = (SincerityPurchaseInfo) this.editData;
		
		super.loadFields();
		this.actionAttachment.setVisible(false);
		this.txtNumber.setText(info.getNumber());
		this.f7Customer.setValue(info.getCustomer());
		this.f7SellOrder.setValue(info.getSellOrder());
		this.removeDataChangeListener(f7Room);
		this.f7Room.setValue(info.getRoom());
		this.addDataChangeListener(f7Room);
		this.removeDataChangeListener(intentionRooms);
		load_intentionRooms(info);
		this.addDataChangeListener(intentionRooms);
		this.f7Commerce.setValue(info.getCommerceChance());
		// this.spiRoomArrangeNum.setValue(new
		// Integer(info.getRoomArrangeNum()));
		this.dptBookDate.setValue(info.getBookDate());
		this.dptValidDate.setValue(info.getValidDate());
		this.txtSincerityAmount.setValue(info.getSincerityAmount());
		this.f7Currency.setValue(info.getCurrency());
		this.f7RoomModelType.setValue(info.getRoomModelType());
		this.f7Salesman.setValue(info.getSalesman());
		this.txtDescription.setText(info.getDescription());
		this.isReceiveEnterAccount.setSelected(info.isIsReceiveEnterAccount());
		this.setMoneySysValueAsOnload(MoneySysTypeEnum.SalehouseSys, info
				.getSellProject());
		if (info.isIsReceiveEnterAccount() == false) {
			this.pnlSincerPrice.setVisible(false);
		} else {
			this.tblSincerPrice.removeRows();
			SincerReceiveEntryCollection sinRecColl = info
					.getSincerPriceEntrys();
			IRow row = null;
			for (int i = 0; i < sinRecColl.size(); i++) {
				this.tblSincerPrice.checkParsed();
				row = this.tblSincerPrice.addRow();
				SincerReceiveEntryInfo sincerEntryInfo = sinRecColl.get(i);
				row.getCell("moneyTypeName").setValue(
						sincerEntryInfo.getMoneyDefine());
				row.getCell("appAmount").setValue(
						sincerEntryInfo.getAppAmount());
				row.getCell("actAmount").setValue(
						sincerEntryInfo.getActRevAmount());
//				row.getCell("bizDate")
//						.setValue(sincerEntryInfo.getCreateTime());
				row.getCell("refundmentAmount").setValue(
						sincerEntryInfo.getRefundmentAmount());
//				row.getCell("canRefundmentAmount").setValue(
//						sincerEntryInfo.getCanRefundmentAmount());
				//收多少退多少
				//更改了收款分录
//				row.getCell("canRefundmentAmount").setValue(sincerEntryInfo.getAllRemainAmount());				
//				row.setUserObject(sincerEntryInfo);
			}
		}
		if (STATUS_EDIT.equals(this.getOprtState())) {
//			this.txtSincerityAmount.setEnabled(false);
			this.f7Customer.setEnabled(false);
		}
		if (STATUS_ADDNEW.equals(this.getOprtState())) {
//			this.txtSincerityAmount.setEnabled(true);
			this.f7Customer.setEnabled(true);
		}
		
		if (STATUS_VIEW.equals(this.getOprtState())
				|| STATUS_EDIT.equals(this.getOprtState())) {
			String id = editData.getId().toString();
			if (id != null) {
				try {
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("*");
					sic.add("changeRecordEntryTwo.*");
					sic.add("changeRecordEntryTwo.oldCustomer.*");
					sic.add("changeRecordEntryTwo.newCustomer.*");
					sic.add("changeRecordEntryTwo.operator.*");
					SincerityPurchaseInfo spInfo = SincerityPurchaseFactory
							.getRemoteInstance().getSincerityPurchaseInfo(
									new ObjectUuidPK(BOSUuid.read(id)), sic);
					if (spInfo != null) {
						for (int i = 0; i < spInfo.getChangeRecordEntryTwo()
								.size(); i++) {
							ChangeRecordEntryTwoInfo spetInfo = spInfo
									.getChangeRecordEntryTwo().get(i);
							if(spetInfo != null){
								IRow row = tblChangeRecord.addRow(i);
								row.getCell("oldCustomer").setValue(
										spetInfo.getOldCustomer());
								row.getCell("newCustomer").setValue(
										spetInfo.getNewCustomer());
								row.getCell("changeDate").setValue(
										spetInfo.getChangeDate());
								row.getCell("operator").setValue(
										spetInfo.getOperator().getName());
								row.getCell("remark")
										.setValue(spetInfo.getRemark());
							}
						}
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				} catch (UuidException e) {
					e.printStackTrace();
				}
			}

		}
		if(info.getRoom()!=null){
			this.f7Room.setEnabled(true);
			this.intentionRooms.setEnabled(false);
		}
		IntentionRoomsEntryCollection intenRoomsCol = info.getIntentionRooms();
		if(intenRoomsCol!=null && intenRoomsCol.size()>0){
			this.f7Room.setEnabled(false);
			this.intentionRooms.setEnabled(true);
		}
	}
    private void initIntentionRooms(KDBizPromptBox box) {
          box.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
              public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                  try {
                      intentionRooms_dataChanged(e);
                  } catch (Exception exc) {
                      handUIException(exc);
                  } finally {
                  }
              }
          });
		
	}

	/**
     * 描述：加载意向房间信息
     * @param info
     */
	private void load_intentionRooms(SincerityPurchaseInfo info) {
		IntentionRoomsEntryCollection roomEntry = info.getIntentionRooms();
    	if(roomEntry != null  &&  roomEntry.size() > 0){
    		RoomCollection roomColl = new RoomCollection();
    		Object[]  objects = new Object[roomEntry.size()];
//	    		Vector objs = new Vector();
        	for(int i=0; i<roomEntry.size(); i++){
        		IntentionRoomsEntryInfo roomEntryInfo = roomEntry.get(i);
        		RoomInfo roomInfo= roomEntryInfo.getIntentionRoom();

        		roomColl.add(roomInfo);
        		objects[i]=roomInfo;
//	        		objs.add(roomInfo);
        	}
        	this.intentionRooms.setValue(objects);
    	}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		SincerityPurchaseInfo info = (SincerityPurchaseInfo) this.editData;
		info.setSellProject((SellProjectInfo) this.txtProjectNumber
				.getUserObject());
		info.setIsReceiveEnterAccount(this.isReceiveEnterAccount.isSelected());
		info.setNumber(this.txtNumber.getText());
		info.setName(this.txtNumber.getText());
//		info.setCustomer((FDCCustomerInfo) this.f7Customer.getValue());
		info.setSellOrder((SellOrderInfo) this.f7SellOrder.getValue());
		info.setRoom((RoomInfo) this.f7Room.getValue());
//		info.set
		// info.setRoomArrangeNum(((Integer) this.spiRoomArrangeNum.getValue())
		// .intValue());
		info.setBookDate((Date) this.dptBookDate.getValue());
		info.setValidDate((Date) this.dptValidDate.getValue());
		info.setSincerityAmount(this.txtSincerityAmount.getBigDecimalValue());
		info.setCurrency((CurrencyInfo) this.f7Currency.getValue());
		info.setRoomModelType((RoomModelTypeInfo) this.f7RoomModelType
				.getValue());
		info.setSalesman((UserInfo) this.f7Salesman.getValue());
		info.setDescription(this.txtDescription.getText());
		info.setCommerceChance((CommerceChanceInfo) this.f7Commerce.getValue());
		
		//意向房间
		info.getIntentionRooms().clear();
    	Object obj = this.intentionRooms.getValue();
    	StringBuffer intentionRoomsStr = new StringBuffer("");
    	if(obj instanceof Object[]){
    		Object[] os = (Object[]) obj;
    		if (os != null && os.length > 0) {
				for (int i = 0; i < os.length; i++) {
					if (os[i] != null  &&  os[i] instanceof RoomInfo) {
						IntentionRoomsEntryInfo intentionRooms= new IntentionRoomsEntryInfo();
						intentionRooms.setIntentionRoom((RoomInfo) os[i]);
						info.getIntentionRooms().add(intentionRooms);
						intentionRoomsStr.append(((RoomInfo) os[i]).getRoomPropNo()).append(";");
					}
				}
			}
    	}else{
    		RoomInfo room = (RoomInfo) this.intentionRooms.getValue();
    		if(room != null){
    			IntentionRoomsEntryInfo intentionRooms= new IntentionRoomsEntryInfo();
				intentionRooms.setIntentionRoom(room);
				info.getIntentionRooms().add(intentionRooms);
				intentionRoomsStr.append(room.getRoomPropNo());
    		}
    	}
    	if(intentionRoomsStr.toString().length()>255){
    		info.setIntentionRoomsStr(intentionRoomsStr.toString().substring(0, 255));
    	}else{
    		info.setIntentionRoomsStr(intentionRoomsStr.toString());
    	}
    	
		
		/**
		 * 当诚意认购单中的可退金额为0时，请将是否收款复选框设置为FALSE！
		 * by renliang
		 */
		BigDecimal total = new BigDecimal("0.00");
		
		for (int i = 0; i < tblSincerPrice.getRowCount(); i++) {
			IRow row = tblSincerPrice.getRow(i);
			if(row == null){
				continue;
			}
			BigDecimal canRefundmentAmount = new BigDecimal("0.00");
			if(row.getCell("canRefundmentAmount").getValue()!=null){
				canRefundmentAmount = (BigDecimal)row.getCell("canRefundmentAmount").getValue();
				total = FDCHelper.add(total, canRefundmentAmount);
			}
		}
		
		if(total.compareTo(new BigDecimal("0.00"))==0){
			info.setIsRev(false);
		}
		SincerReceiveEntryCollection sinColl = new SincerReceiveEntryCollection();
		for (int i = 0; i < this.tblSincerPrice.getRowCount(); i++) {
			IRow row = tblSincerPrice.getRow(i);
			SincerReceiveEntryInfo sinEntryInfo = new SincerReceiveEntryInfo();
			SincerReceiveEntryInfo oldInfo = (SincerReceiveEntryInfo) row
					.getUserObject();
			if (oldInfo != null) { // 如果只是修改，保留之前的分录id
				sinEntryInfo.setId(oldInfo.getId());
//				if(oldInfo.getId()==null) { //新增情况
//					sinEntryInfo.setRevMoneyType(RevMoneyTypeEnum.PreRev);
//				}
//				if(sinEntryInfo.getRevMoneyType()==null)
//					sinEntryInfo.setRevMoneyType(RevMoneyTypeEnum.PreRev);
			}
//			sinEntryInfo.setIsRemainCanRefundment(true);	//剩余金额默认可退
//			sinEntryInfo.setIsCanRevBeyond(true);
			
			MoneyDefineInfo moneyInfo = (MoneyDefineInfo) row.getCell(
					"moneyTypeName").getValue();
			BigDecimal appAmount = (BigDecimal) row.getCell("appAmount")
					.getValue();
			if (appAmount == null)
				continue;
			sinEntryInfo.setMoneyDefine(moneyInfo);
			sinEntryInfo.setAppAmount(appAmount);
//			try {
//				sinEntryInfo.setCreateTime(FDCSQLFacadeFactory
//						.getRemoteInstance().getServerTime());
//			} catch (BOSException e) {
//				e.printStackTrace();
//			}
			sinColl.add(sinEntryInfo);
		}
		info.getSincerPriceEntrys().clear();
		info.getSincerPriceEntrys().addCollection(sinColl);
	}

	private void initEntryTwo(){
		
	

	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		this.btnSave.setEnabled(true);
		this.txtNumber.setEnabled(true);
		this.f7SellOrder.setEnabled(true);
		super.actionAddNew_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		SincerityPurchaseInfo sin = (SincerityPurchaseInfo) this.editData;
		if (sin.getSincerityState().equals(SincerityPurchaseStateEnum.QuitNum)) {
			MsgBox.showInfo("诚意认购已经退号,不能修改!");
			return;
		}
		if (sin.getSincerityState().equals(
				SincerityPurchaseStateEnum.ToPurchase)) {
			MsgBox.showInfo("已经转认购,不能修改!");
			return;
		}
		if (sin.isIsRev()) {
			MsgBox.showInfo("已经收款,不能修改!");
			return;
		}
		super.actionEdit_actionPerformed(e);
		RoomInfo roomInfo = (RoomInfo) this.f7Room.getValue();
		if(roomInfo == null && this.intentionRooms.getUserObject()==null){
			this.f7Room.setEnabled(true);
			this.intentionRooms.setEnabled(true);
		}else if (roomInfo == null) {
			this.f7Room.setEnabled(false);
			this.intentionRooms.setEnabled(true);
		}else{
			this.f7Room.setEnabled(true);
			this.intentionRooms.setEnabled(false);
		}
//		setSincerAmount(this.isReceiveEnterAccount.isSelected());
		if(this.isReceiveEnterAccount.isSelected())
		{
			this.txtSincerityAmount.setEditable(false);
			this.pnlSincerPrice.setVisible(true);
			this.btnAddRow.setEnabled(true);
			this.btnDelRow.setEnabled(true);
		}else
		{
			this.txtSincerityAmount.setEditable(true);
			this.pnlSincerPrice.setVisible(false);
			this.btnAddRow.setEnabled(false);
			this.btnDelRow.setEnabled(false);
		}
//		this.txtSincerityAmount.setEnabled(false);
		this.f7Customer.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		this.pkCreator.setEnabled(false);
		// this.f7Room.setEnabled(false);
		this.f7SellOrder.setEnabled(false);
		this.txtProjectName.setEnabled(false);
		this.txtProjectNumber.setEnabled(false);
		this.txtNumber.setEnabled(false);
		this.actionAttachment.setVisible(false);
		this.btnSave.setEnabled(true);
//		 this.btnAddRow.setEnabled(true);
//		 this.btnDelRow.setEnabled(true);
		this.setGroupFilter();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		// this.setOprtState("EDIT");
		this.verifyBalance();
		super.actionSubmit_actionPerformed(e);
		this.actionAttachment.setVisible(false);
	}
	public boolean isNullorZero(Object obj ){
		if(obj==null){
			return false;
		}
		if(FDCHelper.ZERO.equals((BigDecimal)obj)){
			return false;
		}
		return true;
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		SincerityPurchaseInfo sin = (SincerityPurchaseInfo) this.editData;
		boolean isRev =false;
		for(Iterator it = sin.getSincerPriceEntrys().iterator(); it.hasNext();){
			SincerReceiveEntryInfo sinInfo = (SincerReceiveEntryInfo) it.next();
			isRev = isNullorZero(SHEManageHelper.getActRevAmount(null,sinInfo.getTanPayListEntryId()));
			if(!isRev){
				isRev = isNullorZero(sinInfo.getActRevAmount());
			}
			if(isRev){
				break;
			}
		}
		if (sin.getBizState().equals(TransactionStateEnum.BAYNUMBER)
				&& !sin.isIsRev()) {
			if (confirmRemove()) {
				super.actionRemove_actionPerformed(e);
			}
		} else {
			MsgBox.showInfo("只能删除排号并未收款的预约单!");
		}

	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		String oldOprt = this.oprtState;
		this.verifyBalance();
		
		
		//已有收款的不准再修改   --  因为 可能存在收款后删除或修改该单据中的明细的情况
		if(this.editData.getId()!=null) {
			if(FDCReceiveBillFactory.getRemoteInstance().exists("where gathering='"+GatheringEnum.SINPURCHASE_VALUE+"' and " +
				"sinPurchase.id = '"+this.editData.getId().toString()+"'")){
				MsgBox.showInfo("该诚意认购单已经收过款,不准再修改!");
				return;
			}	
		}
		RoomInfo roomId=null;
		
//		得到诚意认购单上的房间
		if (oldOprt.equals(OprtState.EDIT)){
			//where条件的添加
	        EntityViewInfo evi = new EntityViewInfo();
	        FilterInfo filterInfo = new FilterInfo();
	        filterInfo.getFilterItems().add(new FilterItemInfo("id", this.editData.getId().toString(), CompareType.EQUALS));
	        evi.setFilter(filterInfo);
	    
	        //查询的字段
	        SelectorItemCollection coll = new SelectorItemCollection();
	      
	        coll.add(new SelectorItemInfo("id"));
	        coll.add(new SelectorItemInfo("room.*"));
	      	        
	        evi.setSelector(coll);
	        ISincerityPurchase restRevDao = SincerityPurchaseFactory.getRemoteInstance();
	        //满足条件的集合
	        SincerityPurchaseCollection collection = restRevDao.getSincerityPurchaseCollection(evi);
	        if (collection != null && collection.size() > 0) {
	            for (int i = 0; i < collection.size(); i++) {
	                    SincerityPurchaseInfo info = collection.get(i);
	                    roomId =info.getRoom();
	            }
	        }
		}
		

		super.actionSave_actionPerformed(e);
//		setSincerAmount(this.isReceiveEnterAccount.isSelected());
		if (this.isReceiveEnterAccount.isSelected()) {
			FDCCustomerInfo customer = (FDCCustomerInfo) this.f7Customer
					.getValue();
			FDCCustomerFactory.getRemoteInstance().addToSysCustomer(
					customer.getId().toString());
		}
		//by tim_gao 2010-10-18
		//修改状态下，房屋状态为诚意认购
		//判断是否排号且待售状态，未推盘状态
		RoomInfo room = (RoomInfo)this.f7Room.getValue();
		if (oldOprt.equals(OprtState.EDIT))
		{
//			xiaoao_liu房间为空时不去修改房间状态
			if(this.f7Room.getValue()!=null){
		
			if(room.getSellState().equals(RoomSellStateEnum.Init)||room.getSellState().equals(RoomSellStateEnum.OnShow)){
				updateRoomStateToSincerPur(room);//修改房屋状态为诚意认购
			}
			}else{
				if(roomId!=null){
					if(roomId.getSellState().equals(RoomSellStateEnum.SincerPurchase)){
						updateRoomStateToSincerPurState(roomId);//修改房屋状态为诚意认购
					}
				}
				
			}
		}
		// 商机关联业务单据包括,诚意认购单 新增保存后增加商机跟进
		if (oldOprt.equals(OprtState.ADDNEW))
			if(this.f7Room.getValue()!=null)
			{
				updateRoomStateToSincerPur(room);//修改房屋状态为诚意认购
			}
			CommerceHelper.addCommerceTrackRecord(this,
					(FDCCustomerInfo) this.f7Customer.getValue(),
					(UserInfo) this.f7Salesman.getValue(),
					TrackRecordEnum.SincerityPur, this.editData.getNumber(),
					this.editData.getId() == null ? null : this.editData
							.getId().toString());
		this.btnSave.setEnabled(true);
		if(this.isReceiveEnterAccount.isSelected())
		{
			this.txtSincerityAmount.setEditable(false);
			this.pnlSincerPrice.setVisible(true);
			this.btnAddRow.setEnabled(true);
			this.btnDelRow.setEnabled(true);
		}else
		{
			this.txtSincerityAmount.setEditable(true);
			this.pnlSincerPrice.setVisible(false);
			this.btnAddRow.setEnabled(false);
			this.btnDelRow.setEnabled(false);
		}
		this.btnRemove.setEnabled(false);
		this.actionAttachment.setVisible(false);
		this.actionSincerPrint.setVisible(true);
		this.actionSincerPrintPreview.setVisible(true);
		
		/**
		 * 如果诚意认购单上修改了房间，则要把原来的房间变成待销售状态
		 * by renliang at 2010-11-19
		 */
		if(STATUS_EDIT.equals(getOprtState())){
			if(this.f7Room.getValue()!=null && roomId!=null)
			{
				if(!roomId.getId().toString().equals(((RoomInfo)this.f7Room.getValue()).getId().toString())){
					FDCSQLBuilder sql =  new FDCSQLBuilder();
					sql.appendSql("update t_she_room set FSellState=? where fid=?");
					sql.addParam("Onshow");
					sql.addParam(roomId.getId().toString());
					sql.execute();
				}
			}
		}
	}
	/**
	 *@author xiaoa_liu
	 *@param RoomInfo
	 *@描述 当删除房间保存的时候反写房间状态为待售
	 *@date 2010-10-18
	 */
	protected void updateRoomStateToSincerPurState(RoomInfo room) throws EASBizException, BOSException{
		room.setSellState(RoomSellStateEnum.OnShow);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("sellState");
		RoomFactory.getRemoteInstance().updatePartial(room, sels);
	}
	/**
	 *@author tim_gao
	 *@param RoomInfo
	 *@描述 修改房屋状态为诚意认购
	 *@date 2010-10-18
	 */
	protected void updateRoomStateToSincerPur(RoomInfo room) throws EASBizException, BOSException{
		room.setSellState(RoomSellStateEnum.SincerPurchase);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("sellState");
		RoomFactory.getRemoteInstance().updatePartial(room, sels);
	}
	public void actionSincerPrint_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		String sincerPurId = this.editData.getId().toString();
		SinerityPrintDataProvider data = new SinerityPrintDataProvider(sincerPurId, new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.SincerityPrintQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print("/bim/fdc/sellhouse/sincerityPrint", data, javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionSincerPrint_actionPerformed(e);
	}
	
	public void actionSincerPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		String sincerPurId = this.editData.getId().toString();
		SinerityPrintDataProvider data = new SinerityPrintDataProvider(sincerPurId, new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.SincerityPrintQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview("/bim/fdc/sellhouse/sincerityPrint", data, javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionSincerPrintPreview_actionPerformed(e);
	}
	
	protected IObjectValue createNewData() {
		this.actionSincerPrint.setVisible(false);
		this.actionSincerPrintPreview.setVisible(false);		
		SincerityPurchaseInfo info = new SincerityPurchaseInfo();
		try {
			CompanyOrgUnitInfo currentCompany = SysContext.getSysContext()
					.getCurrentFIUnit();
			CurrencyInfo baseCurrency = CurrencyFactory.getRemoteInstance()
					.getCurrencyInfo(
							new ObjectUuidPK(BOSUuid.read(currentCompany
									.getBaseCurrency().getId().toString())));
			info.setCurrency(baseCurrency);
		} catch (Exception e) {
			e.printStackTrace();
		}
		info.setCreator(userInfo);
		try {
			info.setCreateTime(FDCSQLFacadeFactory.getRemoteInstance()
					.getServerTime());
		} catch (BOSException e2) {
			e2.printStackTrace();
		}
		if (this.getUIContext().get("commerceChance") != null) {
			this.f7Commerce.setValue(this.getUIContext().get("commerceChance"));
			info.setCommerceChance((CommerceChanceInfo) this.getUIContext()
					.get("commerceChance"));
		}
		if (this.getUIContext().get("customer") != null) {
			this.f7Customer.setValue(this.getUIContext().get("customer"));
//			info.setCustomer((FDCCustomerInfo) this.getUIContext().get(
//					"customer"));
		}
		info.setSalesman(SysContext.getSysContext().getCurrentUserInfo());
		SellOrderInfo sellOrder = (SellOrderInfo) this.getUIContext().get(
				"sellOrder");
		if (sellOrder != null) {
			info.setSellOrder(sellOrder);
		} else {
			this.f7SellOrder.setValue(null);
		}
		RoomInfo room = (RoomInfo) this.getUIContext().get("room");
		if (room != null) {
			verifyAddNewRoom(room,null);

			info.setRoom(room);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("roomModelType.*");
			try {
				RoomModelInfo roomModel = RoomModelFactory.getRemoteInstance()
						.getRoomModelInfo(
								new ObjectUuidPK(BOSUuid.read(room
										.getRoomModel().getId().toString())),
								sels);
				info.setRoomModelType(roomModel.getRoomModelType());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		info.setSincerityState(SincerityPurchaseStateEnum.ArrangeNum);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		try {
			SincerityPurchaseCollection sincerityPurchases = null;
			if (sellOrder != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("sellOrder.id", sellOrder.getId()
								.toString()));
				sincerityPurchases = SincerityPurchaseFactory
						.getRemoteInstance().getSincerityPurchaseCollection(
								view);
				int value = 0;
				int max = 0;
				if (sincerityPurchases.size() > 0) {
					max = sincerityPurchases.get(0).getSellOrderArrangeNum();
				}
				for (int i = 0; i < sincerityPurchases.size(); i++) {
					SincerityPurchaseInfo sinInfo = sincerityPurchases.get(i);
					value = sinInfo.getSellOrderArrangeNum();
					if (value > max) {
						max = value;
					}
				}
				info.setSellOrderArrangeNum(max + 1);
			} else {
				info.setSellOrderArrangeNum(1);
			}
			if (room != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("room.id", room.getId().toString()));
				sincerityPurchases = SincerityPurchaseFactory
						.getRemoteInstance().getSincerityPurchaseCollection(
								view);
				info.setRoomArrangeNum(sincerityPurchases.size() + 1);
			} else {
				info.setRoomArrangeNum(0);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}

		info.setSincerityAmount(FDCHelper.ZERO);
		info.setBookDate(CRMHelper.getServerDate2());
		info.setValidDate(CRMHelper.getServerDate2());

		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit()
				.castToFullOrgUnitInfo());
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SincerityPurchaseFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		initPrmtIntentionRooms(intentionRooms);
		initIntentionRooms(this.intentionRooms);
		super.onLoad();
		initControl();		
		this.setF7CustomerFilter(userInfo);
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		this.txtProjectNumber.setText(sellProject.getNumber());
		this.txtProjectName.setText(sellProject.getName());
		this.txtProjectNumber.setUserObject(sellProject);
		this.txtProjectName.setEnabled(false);
		this.txtProjectNumber.setEnabled(false);
		if (this.getOprtState().equals("EDIT")) {
			this.txtNumber.setEnabled(false);
		} else if (this.getOprtState().equals("VIEW")) {
			 this.btnAddRow.setEnabled(false);
			 this.btnDelRow.setEnabled(false);
			 
		}
		this.setMoneySysValueAsOnload(MoneySysTypeEnum.SalehouseSys,
				sellProject);
		initTable();
		
		
		Map functionSetMap = setting.getFunctionSetMap();
		if (functionSetMap.get(sellProject.getId().toString()) != null
				&& this.getOprtState().equals("ADDNEW")) {
			this.actionSincerPrintPreview.setVisible(false);
			this.actionSincerPrint.setVisible(false);
			FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(sellProject.getId().toString());
			if (funcSet.getIsSincerPrice()!=null && funcSet.getIsSincerPrice().booleanValue()) {
				this.isReceiveEnterAccount.setSelected(true);
				this.isReceiveEnterAccount.setEnabled(false);
				addTable();
			} else {
				this.isReceiveEnterAccount.setSelected(false);
				this.pnlSincerPrice.setVisible(false);
			}
			
//			if (funcSet.getIsSincerSellOrder()!=null && funcSet.getIsSincerSellOrder().booleanValue()) {
//				this.f7SellOrder.setRequired(false);
//			} else {
//				this.f7SellOrder.setRequired(true);
//			}
			
			
			
		}
		
		f7SellOrder.setEditable(false);
		
		this.setGroupFilter();
		this.btnSincerPrint.setEnabled(true);
		this.btnSincerPrintPreview.setEnabled(true);
		setMaxLength();
		this.storeFields();
		this.initOldData(this.editData);
		this.setUITitle("诚意认购");
	}
	
	// 设置更名记录中备注的最大长度
	private void setMaxLength() {
		KDTextField formattedTextField = new KDTextField();
		formattedTextField.setMaxLength(255);
		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(
				formattedTextField);
		tblChangeRecord.getColumn("remark").setEditor(numberEditor);
	}

	/**
	 * 添加集团管控控制过滤。
	 * */
	private void setGroupFilter() {
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		if (sellProject == null) {
			return;
		}
		String spId = sellProject.getId().toString();
		// 设置意向户型类别的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7RoomModelType, spId, "户型类别",
				"RoomArch");
	}

	private void addTable() {
		CasSetting casSet = setting.getCasSetting();
		if(casSet!=null) {
			Object obj = (Object) casSet.getSincerMoneyType();
			if (obj != null) {
				IRow row = this.tblSincerPrice.addRow();
				MoneyDefineInfo moneyDefineInfo = (MoneyDefineInfo) obj;
				row.getCell("moneyTypeName").setValue(moneyDefineInfo);
	
				row.getCell("bizDate").setValue(CRMHelper.getServerDate2());
			}
			
			if (!casSet.getIsSincerUpdate().booleanValue()) {
				this.tblSincerPrice.getColumn("moneyTypeName").getStyleAttributes()
						.setLocked(true);
				 this.btnAddRow.setEnabled(false);
				 this.btnDelRow.setEnabled(false);
			} else {
				this.tblSincerPrice.getColumn("moneyTypeName").getStyleAttributes()
						.setLocked(false);
				 this.btnAddRow.setEnabled(true);
				 this.btnDelRow.setEnabled(true);
			}
		}

	}

	protected void isReceiveEnterAccount_stateChanged(ChangeEvent e)
			throws Exception {
		super.isReceiveEnterAccount_stateChanged(e);
		setSincerAmount(this.isReceiveEnterAccount.isSelected());
	}
	
	private void setSincerAmount(boolean isRecAccount)
	{
		if (isRecAccount) {
			this.txtSincerityAmount.setEditable(false);
			this.pnlSincerPrice.setVisible(true);
			this.btnAddRow.setEnabled(true);
			this.btnDelRow.setEnabled(true);
		} else {
			this.txtSincerityAmount.setEditable(true);
			this.pnlSincerPrice.setVisible(false);
			this.btnAddRow.setEnabled(false);
			this.btnDelRow.setEnabled(false);
			removeAllEntryRow();

		}
	}
	
	private void removeAllEntryRow(){
		int rowCount = this.tblSincerPrice.getRowCount();
		for(int i = 0;i<rowCount;i++){
			 this.tblSincerPrice.removeRow(i);
		}
		this.txtSincerityAmount.setValue(FDCConstants.B0);
	 }
//	protected void btnAddRow_actionPerformed(ActionEvent e) throws Exception {
//		super.btnAddRow_actionPerformed(e);
//		IRow row = this.tblSincerPrice.addRow();
//		this.tblSincerPrice.checkParsed();
//		showTblSinRecEntryRow(row);
//	}
	

	public void actionAddRow_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.tblSincerPrice.addRow();
		super.actionAddRow_actionPerformed(e);
		this.tblSincerPrice.checkParsed();
		showTblSinRecEntryRow(row);
		setTotalArAmount();
	}


	private void setTotalArAmount() {
	// TODO Auto-generated method stub
		int rowCount = this.tblSincerPrice.getRowCount();
		BigDecimal totalAmt = FDCConstants.B0;
		for(int i = 0;i<rowCount;i++){
			IRow	row = tblSincerPrice.getRow(i);
			totalAmt =totalAmt.add(row.getCell("appAmount").getValue()==null?FDCConstants.B0:(BigDecimal)row.getCell("appAmount").getValue());
		}
	    this.txtSincerityAmount.setValue(totalAmt);
    }


	public void actionDelRow_actionPerformed(ActionEvent e) throws Exception {
		super.actionDelRow_actionPerformed(e);
		int activeRowIndex = this.tblSincerPrice.getSelectManager()
		 .getActiveRowIndex();
		 this.tblSincerPrice.removeRow(activeRowIndex);
		 setTotalArAmount();
	}

	protected void showTblSinRecEntryRow(IRow row) {
		SincerReceiveEntryInfo info = new SincerReceiveEntryInfo();
		row.getCell("actAmount").getStyleAttributes().setLocked(true);
		
		//设置默认诚意金额 pu_zhang 
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
		.get("sellProject");
		Map faithAmountSetMap = setting.getFaithAmountSetMap();
		if (faithAmountSetMap.get(sellProject.getId().toString()) != null){
			//修改的时候也需要默认带出设置的诚意金吧
			if(OprtState.ADDNEW.equalsIgnoreCase(this.getOprtState()) || OprtState.EDIT.equalsIgnoreCase(this.getOprtState())){
				FaithAmountSetting amtSet = (FaithAmountSetting)faithAmountSetMap.get(sellProject.getId().toString());
				if (amtSet.getFaithAmount()!=null ) {
					row.getCell("appAmount").setValue(amtSet.getFaithAmount());
				}
			}
		}
		row.setUserObject(info);
	}

	// protected void btnDelRow_actionPerformed(ActionEvent e) throws Exception
	// {
	// super.btnDelRow_actionPerformed(e);
	// int activeRowIndex = this.tblSincerPrice.getSelectManager()
	// .getActiveRowIndex();
	// this.tblSincerPrice.removeRow(activeRowIndex);
	// }

	
	private void initTable() {
		
		this.tblSincerPrice.checkParsed();
		this.tblSincerPrice
				.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.tblSincerPrice.getColumn("bizDate").getStyleAttributes()
				.setLocked(true);
		this.tblSincerPrice.getColumn("refundmentAmount").getStyleAttributes()
				.setLocked(true);
		this.tblSincerPrice.getColumn("canRefundmentAmount")
				.getStyleAttributes().setLocked(true);
		this.tblSincerPrice.getColumn("actAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblSincerPrice.getColumn("actAmount").getStyleAttributes()
				.setLocked(true);
		this.tblSincerPrice.getColumn("appAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);

		KDBizPromptBox f7Customer = new KDBizPromptBox();
		f7Customer
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("sysType",
						MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType", MoneyTypeEnum.PREMONEY_VALUE));
		;
		view.setFilter(filter);
		f7Customer.setEntityViewInfo(view);
		f7Customer.setEditable(true);
		f7Customer.setDisplayFormat("$name$");
		f7Customer.setEditFormat("$number$");
		f7Customer.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Customer);
		this.tblSincerPrice.getColumn("moneyTypeName").setEditor(f7Editor);

		this.tblSincerPrice.getColumn("appAmount").getStyleAttributes()
				.setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblSincerPrice.getColumn("moneyTypeName").getStyleAttributes()
				.setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);

		// 给应收金额加上事件，值改变时改变诚意金金额
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		// formattedTextField.addDataChangeListener(new DataChangeListener() {
		// public void dataChanged(DataChangeEvent eventObj) {
		// tblSincerPrice.getRow(currentActiveRowIndex).getCell("appAmount").setValue(eventObj.getNewValue());
		// //saveAppAmount(tblSincerPrice.getRow(currentActiveRowIndex));
		// updateSincerPrice();
		// //
		// updateStandardRentDataByBuilding(tblRooms.getRow(currentActiveRowIndex));
		// // setStatPrice();
		// }
		// });
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblSincerPrice.getColumn("appAmount").setEditor(numberEditor);
		this.tblSincerPrice.getColumn("appAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblSincerPrice.getColumn("actAmount").setEditor(numberEditor);
		this.tblSincerPrice.getColumn("actAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		// this.tblSincerPrice.getColumn("refundmentAmount").setEditor(numberEditor);
		this.tblSincerPrice.getColumn("refundmentAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblSincerPrice.getColumn("refundmentAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);

		// this.tblSincerPrice.getColumn("canRefundmentAmount").setEditor(numberEditor);
		this.tblSincerPrice.getColumn("canRefundmentAmount")
				.getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblSincerPrice.getColumn("canRefundmentAmount")
				.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

		String formatString = "yyyy-MM-dd";
		this.tblSincerPrice.getColumn("bizDate").getStyleAttributes()
				.setNumberFormat(formatString);
	}

	
	 private void initPrmtIntentionRooms(KDBizPromptBox box) {
	    	box.setQueryInfo("com.kingdee.eas.fdc.propertymgmt.app.RoomPPMQuery");
		    box.setEditable(false);
			box.setDisplayFormat("$number$");
			box.setEditFormat("$number$");
			

			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();	

			SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
			.get("sellProject");
			Set set = new HashSet();
			set.add(RoomSellStateEnum.INIT_VALUE);
			set.add(RoomSellStateEnum.ONSHOW_VALUE);
			set.add(RoomSellStateEnum.KEEPDOWN_VALUE);
			set.add(RoomSellStateEnum.SINCERPURCHASE_VALUE);
			
			filter.getFilterItems().add(new FilterItemInfo("building.sellProject.id", sellProject.getId()));
			filter.getFilterItems().add(new FilterItemInfo("sellState",set,CompareType.INCLUDE));
			

			view.setFilter(filter);	
			SorterItemCollection coll = new SorterItemCollection();
			coll.add(new SorterItemInfo("name"));
			view.setSorter(coll);
		
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			box.setSelectorCollection(sic);

			box.setEnabledMultiSelection(true);
			box.setEntityViewInfo(view);
	}

	protected void updateSincerPrice() {
		BigDecimal amount = new BigDecimal(0);
		for (int i = 0; i < tblSincerPrice.getRowCount(); i++) {
			IRow row = tblSincerPrice.getRow(i);
			BigDecimal appAmount = (BigDecimal) row.getCell("appAmount")
					.getValue();
			if (appAmount == null)
				appAmount = new BigDecimal(0);
			amount = amount.add(appAmount);
		}
		this.txtSincerityAmount.setValue(amount);
	}

	private void setMoneySysValueAsOnload(MoneySysTypeEnum moneySysTypeEnum,
			SellProjectInfo sellProject) {
		f7Room.setSelector(new FDCRoomPromptDialog(Boolean.FALSE, null, null,
				MoneySysTypeEnum.SalehouseSys, null, sellProject));
//		intentionRooms.setSelector(new FDCRoomPromptDialog(Boolean.FALSE, null, null,
//				MoneySysTypeEnum.SalehouseSys, null, sellProject));
	}
	

	private void initControl() throws EASBizException, BOSException {
		this.menuSubmitOption.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuWorkflow.setVisible(false);
		this.menuTable1.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionCopy.setVisible(false);

		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.contRoomArrangeNum.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.txtNumber.setRequired(true);
		// this.f7Room.setRequired(true);
		this.f7Customer.setRequired(true);
		this.f7Currency.setRequired(true);
		this.txtSincerityAmount.setRequired(false);
		this.txtSincerityAmount.setNegatived(false);
		this.dptBookDate.setRequired(true);
		this.dptValidDate.setRequired(true);
		this.txtSincerityAmount.setRemoveingZeroInDispaly(false);
		this.txtSincerityAmount.setRemoveingZeroInEdit(false);
		this.txtSincerityAmount.setPrecision(2);
		this.txtSincerityAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtSincerityAmount.setSupportedEmpty(true);
		this.pkCreator.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		String orgUnitID = SysContext.getSysContext().getCurrentCtrlUnit()
				.getId().toString();
		filter.getFilterItems().add(
				new FilterItemInfo("CU.id", orgUnitID, CompareType.EQUALS));
		// filter.getFilterItems().add(
		// new FilterItemInfo("project.id",
		// sellProject.getId().toString(),CompareType.EQUALS));
		this.f7SellOrder.setEntityViewInfo(view);
		// 客户增加按当前用户是销售顾问过滤
		UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
		EntityViewInfo view1 = new EntityViewInfo();
		// FilterInfo filter1 = new FilterInfo();
		// view1.setFilter(filter1);
		// filter1.getFilterItems().add(
		// new FilterItemInfo("salesman.id", userInfo.getId()
		// .toString()));

		this.f7Customer.setEntityViewInfo(CommerceHelper.getPermitCustomerView(
				userInfo, MoneySysTypeEnum.SalehouseSys));
		// this.spiSellOrderNum.setEnabled(false);
		// this.spiRoomArrangeNum.setEnabled(false);
		this.f7Currency.setEnabled(false);

	}

	protected void f7Room_dataChanged(DataChangeEvent e) throws Exception {
		RoomInfo old_Room = null;
		if(e.getOldValue() instanceof RoomInfo){
			old_Room=(RoomInfo)e.getOldValue();
		}
		RoomInfo room = null;
		if (e.getNewValue() instanceof RoomInfo) {
			room = (RoomInfo) e.getNewValue();
		}
		if (room == null) {
			this.intentionRooms.setEnabled(true);
			return;
		}else{
			this.intentionRooms.setEnabled(false);
		}
//		if (room.getSellState().equals(RoomSellStateEnum.KeepDown)) {
//			MsgBox.showInfo("房间已经被保留!");
//			this.f7Room.setValue(null);
//			return;
//		}
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		Map functionSetMap = setting.getFunctionSetMap();
		if (functionSetMap.get(sellProject.getId().toString()) != null && (this.getOprtState().equals("ADDNEW") 
						|| this.getOprtState().equals("EDIT"))) {
			FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(sellProject.getId().toString());
			if (funcSet.getIsSincerSellOrder()==null || !funcSet.getIsSincerSellOrder().booleanValue()) {
				if (room.getSellState().equals(RoomSellStateEnum.Init)) {
					MsgBox.showInfo("房间没有开盘,要想诚意认购未推盘的房间请到售楼设置里去设置!");
					this.f7Room.setValue(null);
					this.abort();
					// return;
				}else
				{
					SelectorItemCollection sels = new SelectorItemCollection();
					sels.add("roomModelType.*");
					try {
						RoomModelInfo roomModel = RoomModelFactory.getRemoteInstance()
								.getRoomModelInfo(
										new ObjectUuidPK(BOSUuid.read(room
												.getRoomModel().getId().toString())),
										sels);
						if(roomModel!=null && roomModel.getRoomModelType()!=null)
						{
							this.f7RoomModelType.setValue(roomModel.getRoomModelType());
						}else
						{
							this.f7RoomModelType.setValue(null);
						}
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		if (this.getOprtState().equals("EDIT")) {
			if (verifyAddNewRoom(room,old_Room)) {
				this.btnSave.setEnabled(true);
			} //else {
//				this.btnSave.setEnabled(false);
//			}
		} else {
			if (verifyAddNewRoom(room,old_Room) == false) {
				this.actionEdit.setEnabled(false);
			}
		}
		// if (room != null) {
		// SellOrderInfo sellOrder = (SellOrderInfo) this.f7SellOrder
		// .getValue();
		// SelectorItemCollection sels = new SelectorItemCollection();
		// sels.add("*");
		// sels.add("sellOrder.*");
		// RoomInfo roomInfo = RoomFactory.getRemoteInstance()
		// .getRoomInfo(
		// new ObjectUuidPK(BOSUuid.read(room
		// .getId().toString())), sels);
		// SellOrderInfo roomSellOrder = roomInfo.getSellOrder();
		// // if (roomSellOrder == null) {
		// // return;
		// // }
		// if (sellOrder != null) {
		// if (!roomSellOrder.getId().toString().equals(
		// sellOrder.getId().toString())) {
		// MsgBox.showInfo("房间盘次不匹配!");
		// this.abort();
		// }
		// } else {
		// this.f7SellOrder.setValue(null);
		// }
		// this.f7Room.setValue(room);
		// SelectorItemCollection sels2 = new SelectorItemCollection();
		// sels2.add("roomModelType.*");
		// RoomModelInfo roomModel = RoomModelFactory.getRemoteInstance()
		// .getRoomModelInfo(
		// new ObjectUuidPK(BOSUuid.read(room.getRoomModel()
		// .getId().toString())), sels2);
		// this.f7RoomModelType.setValue(roomModel.getRoomModelType());
		//
		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// view.setFilter(filter);
		// filter.getFilterItems().add(
		// new FilterItemInfo("room.id", room.getId().toString()));
		// SincerityPurchaseCollection sincerityPurchases =
		// SincerityPurchaseFactory
		// .getRemoteInstance().getSincerityPurchaseCollection(view);
		// int value = 0;
		// for (int i = 0; i < sincerityPurchases.size(); i++) {
		// SincerityPurchaseInfo info = sincerityPurchases.get(i);
		// if (this.editData.getId() != null
		// && info.getId().toString().equals(
		// this.editData.getId().toString())) {
		// if (info.getRoom() != null
		// && info.getRoom().getId().toString().equals(
		// room.getId().toString())) {
		// value = info.getRoomArrangeNum();
		// }
		// } else {
		// value++;
		// }
		// }
		// this.spiRoomArrangeNum.setValue(new Integer(value + 1));
		// }
		
		//by tim_gao 未推盘房间，设为空且不能修改
		this.f7SellOrder.setEnabled(true);
		if(room.getSellOrder()==null&&!(STATUS_EDIT).equalsIgnoreCase(this.getOprtState())){
			MsgBox.showWarning("未推盘房间，盘次为空！");
			this.f7SellOrder.setValue(null);
			this.f7SellOrder.setEnabled(false);
		}
		
	}
	protected void intentionRooms_dataChanged(DataChangeEvent e) throws Exception {
		Object obj = this.intentionRooms.getValue();
		if(obj!=null && obj instanceof Object[] ){
			Object[] os = (Object[]) obj;
    		if (os != null && os.length > 0 && os[0]!=null ) {
    			this.f7Room.setEnabled(false);
			}else{
				this.f7Room.setEnabled(true);
			}
		}else{
			this.f7Room.setEnabled(true);
		}
	    
		
		
//		RoomInfo room = null;
//		if (e.getNewValue() instanceof RoomInfo) {
//			room = (RoomInfo) e.getNewValue();
//		}
//		if (room == null) {
//			return;
//		}
//		if (room.getSellState().equals(RoomSellStateEnum.KeepDown)) {
//			MsgBox.showInfo("房间已经被保留!");
//			return;
//		}
//		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
//		Map functionSetMap = setting.getFunctionSetMap();
//		if (functionSetMap.get(sellProject.getId().toString()) != null && (this.getOprtState().equals("ADDNEW") 
//						|| this.getOprtState().equals("EDIT"))) {
//			FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(sellProject.getId().toString());
//			if (funcSet.getIsSincerSellOrder()==null || !funcSet.getIsSincerSellOrder().booleanValue()) {
//				if (room.getSellState().equals(RoomSellStateEnum.Init)) {
//					MsgBox.showInfo("房间没有开盘,要想诚意认购未推盘的房间请到售楼设置里去设置!");
//					this.f7Room.setValue(null);
//					this.abort();
//					// return;
//				}else
//				{
//					SelectorItemCollection sels = new SelectorItemCollection();
//					sels.add("roomModelType.*");
//					try {
//						RoomModelInfo roomModel = RoomModelFactory.getRemoteInstance()
//								.getRoomModelInfo(
//										new ObjectUuidPK(BOSUuid.read(room
//												.getRoomModel().getId().toString())),
//										sels);
//						if(roomModel!=null && roomModel.getRoomModelType()!=null)
//						{
//							this.f7RoomModelType.setValue(roomModel.getRoomModelType());
//						}else
//						{
//							this.f7RoomModelType.setValue(null);
//						}
//						
//					} catch (Exception e1) {
//						e1.printStackTrace();
//					}
//				}
//			}
//		}
//		if (this.getOprtState().equals("EDIT")) {
//			if (verifyAddNewRoom(room)) {
//				this.btnSave.setEnabled(true);
//			} else {
//				this.btnSave.setEnabled(false);
//			}
//		} else {
//			if (verifyAddNewRoom(room) == false) {
//				this.actionEdit.setEnabled(false);
//			}
//		}
//		//by tim_gao 未推盘房间，设为空且不能修改
//		this.f7SellOrder.setEnabled(true);
//		if(room.getSellOrder()==null&&!(STATUS_EDIT).equalsIgnoreCase(this.getOprtState())){
//			MsgBox.showWarning("未推盘房间，盘次为空！");
//			this.f7SellOrder.setValue(null);
//			this.f7SellOrder.setEnabled(false);
//		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("*");
		selectors.add("room.*");
		selectors.add("sellOrder.*");
		selectors.add("sellOrder.project.*");
		selectors.add("customer.*");
		selectors.add("customer.customer.*");
		selectors.add("customer.certificate.*");

		selectors.add("salesman.id");
		selectors.add("salesman.name");
		selectors.add("salesman.number");
		selectors.add("currency.id");
		selectors.add("currency.name");
		selectors.add("currency.number");
		selectors.add("roomModelType.id");
		selectors.add("roomModelType.name");
		selectors.add("roomModelType.number");
		selectors.add("commerceChance.*");
		selectors.add("sincerPriceEntrys.*");

		selectors.add("sincerPriceEntrys.moneyDefine.id");
		selectors.add("sincerPriceEntrys.moneyDefine.name");
		selectors.add("sincerPriceEntrys.moneyDefine.number");
		selectors.add("sincerPriceEntrys.moneyDefine.moneyType");
		selectors.add("sincerPriceEntrys.moneyDefine.sysType");
		selectors.add("sincerPriceEntrys.moneyDefine.isEnabled");
		selectors.add("intentionRooms.*");
		selectors.add("intentionRooms.intentionRoom.id");
		selectors.add("intentionRooms.intentionRoom.name");
		selectors.add("intentionRooms.intentionRoom.number");
		

		return selectors;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable()
				&& StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("业务编码必须录入！");
			abort();
		}
		if (this.f7Customer.getValue() == null) {
			MsgBox.showInfo("客户必须录入！");
			abort();
		}
		if (this.f7Currency.getValue() == null) {
			MsgBox.showInfo("币别必须录入！");
			abort();
		}
		if (this.dptBookDate.getValue() == null) {
			MsgBox.showInfo("登记日期必须录入！");
			abort();
		}
//		if (this.f7SellOrder.isRequired()
//				&& this.f7SellOrder.getValue() == null) {
//			MsgBox.showInfo("盘次必须录入");
//			abort();
//		}
		if (this.dptValidDate.getValue() == null) {
			MsgBox.showInfo("有效日期必须录入！");
			abort();
		}
		if (this.isReceiveEnterAccount.isSelected()) {
			if (this.tblSincerPrice.getRowCount() == 0) {
				MsgBox.showInfo("收款明细分录不能为空!");
				this.abort();
			}
			BigDecimal amount = new BigDecimal(0);
			for (int i = 0; i < this.tblSincerPrice.getRowCount(); i++) {
				IRow row = tblSincerPrice.getRow(i);
				BigDecimal appAmount = (BigDecimal) row.getCell("appAmount")
						.getValue();
				if (appAmount == null) {
					appAmount = new BigDecimal(0);
				}
				amount = amount.add(appAmount);
			}
			BigDecimal sincerityAmount = this.txtSincerityAmount
					.getBigDecimalValue();
			if (sincerityAmount.compareTo(new BigDecimal(0)) == 0) {
				MsgBox.showInfo("诚意金不能为空");
				this.abort();
			}
			if (sincerityAmount.compareTo(amount) != 0) {
				MsgBox.showInfo("应付款金额总额跟诚意认购金额不同");
				this.abort();
			}
		}

		FDCCustomerInfo customer = (FDCCustomerInfo) f7Customer.getValue();
		if (customer.getCertificateNumber() == null
				|| customer.getCertificateNumber().length() == 0) {
			MsgBox.showInfo("客户证件号码不能为空,请输入！");
			CustomerCardUI.addTheCustomerAuthority(customer, this);
			this.abort();
		}

		this.verifyBalance();
		super.verifyInput(e);
	}

	/**
	 * 对于已结算的期间，不允许进行收款及修改
	 * */
	private void verifyBalance() {
		Date bizDate = (Date) this.dptBookDate.getValue();
		if (bizDate == null) {
			MsgBox.showInfo("登记日期不能为空。");
			this.abort();
		}
		Date balanceEndDate = null;
		SellProjectInfo sellProject = (SellProjectInfo) this.txtProjectNumber
				.getUserObject();
		if (sellProject != null) {
			try {
				balanceEndDate = getLastEndDate(sellProject.getId().toString());
			} catch (Exception e) {
				handleException(e);
				e.printStackTrace();
			}
			SHEHelper.verifyBalance(bizDate, balanceEndDate);
		}
	}

	/***
	 * 按销售项目取上次结算的截止日期。
	 * **/

	private Date getLastEndDate(String sellProjectId) throws Exception {
		Date lastEndDate = null;
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder
				.appendSql("select FBalanceEndDate from T_SHE_SellProject where FID = ?");
		detailBuilder.addParam(sellProjectId);
		try {
			IRowSet detailSet = detailBuilder.executeQuery();
			if (detailSet.next()) {
				lastEndDate = detailSet.getDate("FBalanceEndDate");
			}
		} catch (Exception e) {
			handleException(e);
			e.printStackTrace();
		}
		return lastEndDate;
	}

	// protected void btnSelectRoom_actionPerformed(ActionEvent e)
	// throws Exception {
	// RoomInfo room = RoomSelectUI.showOneRoomSelectUI(this, null,
	// null,MoneySysTypeEnum.SalehouseSys);
	// if (room == null) {
	// return;
	// }
	// verifyAddNewRoom(room);
	// if (room != null) {
	// SellOrderInfo sellOrder = (SellOrderInfo) this.f7SellOrder
	// .getValue();
	// SellOrderInfo roomSellOrder = room.getSellOrder();
	// if (roomSellOrder == null) {
	// return;
	// }
	// if (sellOrder != null) {
	// if (!roomSellOrder.getId().toString().equals(
	// sellOrder.getId().toString())) {
	// MsgBox.showInfo("房间盘次不匹配!");
	// this.abort();
	// }
	// } else {
	// this.f7SellOrder.setValue(roomSellOrder);
	// }
	// this.f7Room.setUserObject(room);
	// this.f7Room.setText(room.getNumber());
	// // this.txtRoom.setUserObject(room);
	// // this.txtRoom.setText(room.getNumber());
	// SelectorItemCollection sels = new SelectorItemCollection();
	// sels.add("roomModelType.*");
	// RoomModelInfo roomModel = RoomModelFactory.getRemoteInstance()
	// .getRoomModelInfo(
	// new ObjectUuidPK(BOSUuid.read(room.getRoomModel()
	// .getId().toString())), sels);
	// this.f7RoomModelType.setValue(roomModel.getRoomModelType());
	//
	// EntityViewInfo view = new EntityViewInfo();
	// FilterInfo filter = new FilterInfo();
	// view.setFilter(filter);
	// filter.getFilterItems().add(
	// new FilterItemInfo("room.id", room.getId().toString()));
	// SincerityPurchaseCollection sincerityPurchases = SincerityPurchaseFactory
	// .getRemoteInstance().getSincerityPurchaseCollection(view);
	// int value = 0;
	// for (int i = 0; i < sincerityPurchases.size(); i++) {
	// SincerityPurchaseInfo info = sincerityPurchases.get(i);
	// if (this.editData.getId() != null
	// && info.getId().toString().equals(
	// this.editData.getId().toString())) {
	// if (info.getRoom() != null
	// && info.getRoom().getId().toString().equals(
	// room.getId().toString())) {
	// value = info.getRoomArrangeNum();
	// }
	// } else {
	// value++;
	// }
	// }
	// this.spiRoomArrangeNum.setValue(new Integer(value + 1));
	// }
	// }

	private boolean verifyAddNewRoom(RoomInfo room,RoomInfo old_Room) {
		boolean boo = true;
		if (room.getSellState().equals(RoomSellStateEnum.KeepDown)) {
			this.f7Room.setValue(old_Room);
			MsgBox.showInfo("房间已经被保留!");
			boo = false;
		}
		// if (room.getSellState().equals(RoomSellStateEnum.Init)) {
		// MsgBox.showInfo("房间没有开盘!");
		// this.abort();
		// }

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("attachmentEntry.room.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("attachmentEntry.room.id", room.getId()
						.toString()));
		PurchaseRoomAttachmentEntryCollection attachmentEntry = null;
		try {
			attachmentEntry = PurchaseRoomAttachmentEntryFactory
					.getRemoteInstance()
					.getPurchaseRoomAttachmentEntryCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}

		if (attachmentEntry.size() > 0) {
			MsgBox.showInfo("该房间已经被其他房间绑定并且并入合同,不能单独销售!");
			this.f7Room.setValue(old_Room);
			boo = false;
		}

		if (room.getSellState().equals(RoomSellStateEnum.PrePurchase)
				|| room.getSellState().equals(RoomSellStateEnum.Purchase)
				|| room.getSellState().equals(RoomSellStateEnum.Sign)) {
			this.f7Room.setValue(old_Room);
			MsgBox.showInfo("房间已经被认购!");
			boo = false;
		}

		if (HousePropertyEnum.Attachment.equals(room.getHouseProperty())) {
			this.f7Room.setValue(old_Room);
			MsgBox.showInfo("附属房产不能单独销售!");
			boo = false;
		}
		return boo;
	}

	protected void f7SellOrder_dataChanged(DataChangeEvent e) throws Exception {
		super.f7SellOrder_dataChanged(e);
		// if (!(e.getNewValue() instanceof SellOrderInfo)) {
		// return;
		// }
		// SellOrderInfo sellOrder = (SellOrderInfo) e.getNewValue();
		// if (sellOrder == null) {
		// //this.f7Room.setValue(null);
		// //this.spiSellOrderNum.setValue(new Integer(0));
		// } else {
		// if (sellOrder.equals(e.getOldValue())) {
		// return;
		// } else {
		// SincerityPurchaseInfo sinInfo = (SincerityPurchaseInfo)this.editData;
		// this.f7Room.setValue(sinInfo.getRoom());
		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// view.setFilter(filter);
		// filter.getFilterItems().add(
		// new FilterItemInfo("sellOrder.id", sellOrder.getId()
		// .toString()));
		// SincerityPurchaseCollection sincerityPurchases =
		// SincerityPurchaseFactory
		// .getRemoteInstance().getSincerityPurchaseCollection(
		// view);
		// int max = 0;
		// int value = 0;
		// if(sincerityPurchases.size()>0)
		// {
		// max = sincerityPurchases.get(0).getSellOrderArrangeNum();
		// }
		// for (int i = 0; i < sincerityPurchases.size(); i++) {
		// SincerityPurchaseInfo info = sincerityPurchases.get(i);
		// if (this.editData.getId() != null
		// && info.getId().toString().equals(
		// this.editData.getId().toString())) {
		// if (info.getSellOrder() != null
		// && info.getSellOrder().getId().toString()
		// .equals(sellOrder.getId().toString())) {
		// value = info.getSellOrderArrangeNum();
		// if(value>max)
		// {
		// max=value;
		// }
		// }
		// }
		// }
		// this.spiSellOrderNum.setValue(new Integer(max + 1));
		// }
		// }
	}

	private void setF7CustomerFilter(UserInfo info) throws EASBizException,
			BOSException {
		this.f7Customer.setEntityViewInfo(CommerceHelper.getPermitCustomerView(
				info, MoneySysTypeEnum.SalehouseSys));
	}

	protected void f7Customer_dataChanged(DataChangeEvent e) throws Exception {
		super.f7Customer_dataChanged(e);
		FDCCustomerInfo customerInfo = (FDCCustomerInfo) this.f7Customer
				.getValue();
		this.setF7CommerceChance(customerInfo);
	}

	protected void f7Salesman_dataChanged(DataChangeEvent e) throws Exception {
		super.f7Salesman_dataChanged(e);
		UserInfo info = (UserInfo) this.f7Salesman.getValue();
		this.setF7CustomerFilter(info);
	}

	// protected void f7Commerce_stateChanged(ChangeEvent e) throws Exception {
	// super.f7Commerce_stateChanged(e);
	// if(this.f7Customer.getValue()==null)
	// {
	// MsgBox.showInfo("要选择商机请先选择客户");
	// this.abort();
	// }else
	// {
	// FDCCustomerInfo customerInfo =
	// (FDCCustomerInfo)this.f7Customer.getValue();
	// this.setF7CommerceChance(customerInfo);
	// }
	// }

	protected void setF7CommerceChance(FDCCustomerInfo customerInfo) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if (customerInfo != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("fdcCustomer.id", customerInfo.getId()
							.toString()));
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("fdcCustomer.id", null));
		}
		this.f7Commerce.setEntityViewInfo(view);
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected void tblSincerPrice_activeCellChanged(KDTActiveCellEvent e)
			throws Exception {
		super.tblSincerPrice_activeCellChanged(e);
		// 设置激活行全局变量
		currentActiveRowIndex = e.getRowIndex();
	}

	// 随时保存单元格的userObject
	protected void tblSincerPrice_editStopped(KDTEditEvent e) throws Exception {
		super.tblSincerPrice_editStopped(e);
		int rowIndex = e.getRowIndex();
		IRow row = tblSincerPrice.getRow(rowIndex);
		SincerReceiveEntryInfo info = (SincerReceiveEntryInfo) row
				.getUserObject();
		if (info == null) {
			info = new SincerReceiveEntryInfo();
		}
		info.setAppAmount((BigDecimal) row.getCell("appAmount").getValue());
		updateSincerPrice();
	}

	public void onShow() throws Exception {
		if (this.txtNumber.isEnabled())
			this.txtNumber.requestFocus();
		else
			{this.f7Customer.requestFocus();}
	//by tim_gao 判断盘次是否为空
		if(!isRoomSelled()){
			this.f7SellOrder.setValue(null);
			this.f7SellOrder.setEnabled(false);
			
		}
		super.onShow();
	}
	/*
	 * xiaoao_liu
	 * 为查询状态的时候禁用制单人的查询
	 * @see com.kingdee.eas.fdc.sellhouse.client.AbstractSincerityPurchaseEditUI#setOprtState(java.lang.String)
	 */
	public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
		this.pkCreator.setEnabled(false);
    }
	
	/**
	 * @see 判断盘次是否为空,未推盘房间置灰
	 * @author tim_gao
	 * 
	 */
	protected boolean isRoomSelled(){
		
		boolean flag=true;
		SincerityPurchaseInfo sinPurInfo = (SincerityPurchaseInfo) this.editData;
		if(sinPurInfo.getRoom()!=null){//新增的时候房屋本身就为 null所以要加判断
		if(sinPurInfo.getRoom().getSellOrder()==null){
			flag=false;
		}
		}
		return flag;
	}

	protected KDTable getDetailTable() {
		// TODO 自动生成方法存根
		return null;
	}
}