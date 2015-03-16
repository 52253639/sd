/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BizFlowEnum;
import com.kingdee.eas.fdc.sellhouse.BizListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BizListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.CalcTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CollectionFactory;
import com.kingdee.eas.fdc.sellhouse.CollectionInfo;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SignAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.fdc.sellhouse.AgioParam;
/**
 * output class name
 */
public class BuyingRoomPlanUI extends AbstractBuyingRoomPlanUI
{
    private static final Logger logger = CoreUIObject.getLogger(BuyingRoomPlanUI.class);
    
    private AgioParam currAgioParam = new AgioParam();
    
    private RoomDisplaySetting setting = new RoomDisplaySetting();
    
    private RoomInfo room = null;
    protected int digit=0;
	protected int toIntegerType=0;
	protected boolean isUpdateAmount=true;
    /**
     * output class constructor
     */
    public BuyingRoomPlanUI() throws Exception
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

    public void actionCalc_actionPerformed(ActionEvent e) throws Exception
    {
    	UIContext uiContext = new UIContext(this);
		uiContext.put("ID", "");
		BigDecimal totalAmount =this.txtTotalAmount.getBigDecimalValue();
		BigDecimal aFundAmount = this.txtAFundAmount.getBigDecimalValue();
		BigDecimal loanAmount = this.txtLoanAmount.getBigDecimalValue();
		uiContext.put("totalAmount", totalAmount);
		uiContext.put("aFundAmount", aFundAmount);
		uiContext.put("loanAmount", loanAmount);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(MortgageCalcUI.class.getName(), uiContext,null,OprtState.ADDNEW);
		uiWindow.show();
    }
    
    public void onLoad() throws Exception 
    {
    	this.actionCalc.setEnabled(true);
    	getRoomInfo();
    	super.onLoad();
    	this.actionAddNew.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionAbout.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionCalc.setVisible(true);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		setTextFormat(this.txtAgio);
		setTextFormat(this.txtTotalAmount);
		setTextFormat(this.txtLoanAmount);
		setTextFormat(this.txtAFundAmount);
		this.addF7PayTypeFilter();
		initPayTable();
		
		HashMap value = SHEManageHelper.getCRMConstants(SysContext.getSysContext().getCurrentOrgUnit().getId());
		digit=SHEManageHelper.setPrecision(value.get(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT).toString()).intValue();
		toIntegerType=SHEManageHelper.setRoundingMode(value.get(CRMConstants.FDCSHE_PARAM_TOL_TOINTEGER_TYPE).toString()).intValue();

		txtTotalAmount.setNegatived(false);
		txtTotalAmount.setPrecision(digit);
		txtTotalAmount.setRoundingMode(toIntegerType);
		txtLoanAmount.setPrecision(digit);
		txtLoanAmount.setRoundingMode(toIntegerType);
		txtAFundAmount.setPrecision(digit);
		txtAFundAmount.setRoundingMode(toIntegerType);
		
		this.payListTable.getColumn("amount").setEditor(SHEManageHelper.getNumberCellEditor(digit,toIntegerType));
		this.payListTable.getColumn("amount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(digit));
    }
    
    private void initPayTable() {
		this.payListTable.checkParsed();
		this.payListTable.getStyleAttributes().setLocked(true);
		this.payListTable.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		this.payListTable.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		String formatString = "yyyy-MM-dd";
		this.payListTable.getColumn("recDate").getStyleAttributes().setNumberFormat(formatString);

		this.payListTable.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.payListTable.getColumn("amount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		this.payListTable.getColumn("recDate").setEditor(dateEditor);
		KDBizPromptBox f7Box = new KDBizPromptBox();
		f7Box.setDisplayFormat("$name$");
		f7Box.setDisplayFormat("$number$");
		f7Box.setDisplayFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.EARNESTMONEY_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.FISRTAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.HOUSEAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.LOANAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.ACCFUNDAMOUNT_VALUE));
		filter.setMaskString("#0 and (#1 or #2 or #3 or #4 or #5)");
		f7Box.setEntityViewInfo(view);
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		this.payListTable.getColumn("moneyName").setEditor(f7Editor);
		f7Box = new KDBizPromptBox();
		f7Box.setDisplayFormat("$name$");
		f7Box.setDisplayFormat("$number$");
		f7Box.setDisplayFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CurrencyQuery");
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.payListTable.getColumn("currency").setEditor(f7Editor);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.INTEGER);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.payListTable.getColumn("amount").setEditor(numberEditor);
		this.payListTable.getColumn("moneyName").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.payListTable.getColumn("recDate").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.payListTable.getColumn("amount").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
	}

    
	private void setTextFormat(KDFormattedTextField text){
		text.setRemoveingZeroInDispaly(false);
		text.setRemoveingZeroInEdit(false);
		text.setPrecision(2);
		text.setHorizontalAlignment(JTextField.RIGHT);
		text.setSupportedEmpty(true);
	}
    public void loadFields()
    {
    	this.f7PayType.setUserObject(null);
    	this.payListTable.removeRows();
    	BigDecimal totalAmount =(BigDecimal) this.getUIContext().get("totalAmount");
    	
    	isUpdateAmount=false;
    	this.txtTotalAmount.setValue(totalAmount);
    	isUpdateAmount=true;
    	
    	this.txtAgio.setValue(FDCHelper.ONE_HUNDRED);
    	this.txtLoanAmount.setValue(FDCHelper.ZERO);
    	this.txtAFundAmount.setValue(FDCHelper.ZERO);
    	AgioEntryCollection agioEntryColl = new AgioEntryCollection(); 
		this.txtAgio.setUserObject(agioEntryColl);
		currAgioParam.setAgios(agioEntryColl);
		if(CalcTypeEnum.PriceByTotalAmount.equals(room.getCalcType())){
			currAgioParam.setPriceAccountType(PriceAccountTypeEnum.StandSetPrice);
		}else{
			currAgioParam.setPriceAccountType(PriceAccountTypeEnum.PriceSetStand);
		}
//		String agioDesString = SHEHelper.getAgioDes(currAgioParam.getAgios(),
//				null,FDCHelper.ZERO,currAgioParam.isToInteger(),currAgioParam.isBasePriceSell(),currAgioParam.getToIntegerType(), currAgioParam.getDigit());
//		this.txtAgioDes.setText(agioDesString);
//    	EventListener[] listeners = this.f7PayType.getListeners(DataChangeListener.class);
//		for (int i = 0; i < listeners.length; i++) {
//			this.f7PayType.removeDataChangeListener((DataChangeListener) listeners[i]);
//		}
    }
    
//	public String getAgioDes() {
//		return SHEHelper.getAgioDes((AgioEntryCollection)this.txtAgio.getUserObject(),
//				SpecialAgioEnum.DaZhe, FDCHelper.ZERO, 
//				this.currAgioParam.isToInteger(),false,this.currAgioParam.getToIntegerType(), this.currAgioParam.getDigit());
//	}
    private void getRoomInfo()throws Exception{
		String roomId =this.getUIContext().get(UIContext.ID).toString();
		EntityViewInfo roomView = new EntityViewInfo();
		FilterInfo roomFilter = new FilterInfo();
		roomView.setFilter(roomFilter);
		roomView.getSelector().add("*");
		roomView.getSelector().add("buildingProperty.name");
		roomView.getSelector().add("building");
		roomView.getSelector().add("building.sellProject");
		roomFilter.getFilterItems().add(new FilterItemInfo("id", roomId));
		RoomCollection roomCol = (RoomCollection) RoomFactory.getRemoteInstance().getRoomCollection(roomView);
		if(roomCol != null && roomCol.size()>0 ){
			room = roomCol.get(0);
		}
    }
    public void addF7PayTypeFilter() throws Exception{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("validDate", new Date(), CompareType.LESS_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", new Date(), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", null, CompareType.IS));

		String projectId = "null";
		if (room!=null && room.getBuilding().getSellProject() != null) {
			projectId = room.getBuilding().getSellProject().getId().toString();
		}
		filter.getFilterItems().add(new FilterItemInfo("project.id", projectId));
		filter.setMaskString("#0 and #1 and (#2 or #3 )and #4 )");
		view.setFilter(filter);
		this.f7PayType.setEntityViewInfo(view);
	}
    private void updateAmount(BigDecimal amount) throws BOSException{
    	String roomId =this.getUIContext().get(UIContext.ID).toString();
		if (roomId == null || "".equals(roomId)) {
			MsgBox.showInfo("请先选择房间!");
			this.f7PayType.setValue(null);
			return;
		}
		SHEPayTypeInfo payType = (SHEPayTypeInfo) this.f7PayType.getValue();
		boolean isEarnestInHouseAmount = true; //默认定金隶属于房款
		if (payType == null) {
			this.txtLoanAmount.setValue(null);
			this.txtAFundAmount.setValue(null);
			this.payListTable.removeRows();
		} else {
			isEarnestInHouseAmount = payType.isIsTotal();
			EntityViewInfo view = new EntityViewInfo();
			view.getSorter().add(new SorterItemInfo("seq"));
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("seq");
			view.getSelector().add("moneyDefine.*");
			view.getSelector().add("currency.*");
			filter.getFilterItems().add(new FilterItemInfo("head.id", payType.getId().toString()));
			PayListEntryCollection payList = new PayListEntryCollection();
			payList = PayListEntryFactory.getRemoteInstance().getPayListEntryCollection(view);
			payType.getPayLists().clear();
			payType.getPayLists().addCollection(payList);
		}
		String payTypeName = null;
		BigDecimal payTypeAgio = FDCHelper.ONE_HUNDRED;
		if (payType != null) {
			payTypeName = payType.getName();
			payTypeAgio = payType.getAgio();
			if(payTypeAgio == null){
				payTypeAgio = FDCHelper.ONE_HUNDRED;
			}
		}
		CalcTypeEnum type=null;
		if(room.getCalcType()==null){
			return;
		}else{
			type=room.getCalcType();
		}
		PurchaseParam purParam = SHEManageHelper.getAgioParam(this.currAgioParam, room, 
				room.getSellType(), type, false,room.getRoomArea(), room.getBuildingArea(), room.getRoomPrice(),room.getBuildPrice(),amount,
				null,null,null,SpecialAgioEnum.DaZhe, payTypeAgio,payTypeName);
		if(purParam!=null) {
			this.txtAgioDes.setText(purParam.getAgioDes());
			this.txtAgio.setValue(purParam.getFinalAgio());
			
			isUpdateAmount=false;
			this.txtTotalAmount.setValue(purParam.getDealTotalAmount());
			isUpdateAmount=true;
		}
		this.updatePayList(isEarnestInHouseAmount);
    }
    protected void f7PayType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	if(room.getCalcType()==null){
			FDCMsgBox.showWarning(this,"房间未定价，不能进行置业计划操作！");
    		return;
		}
    	if((e.getOldValue()!=null&&e.getNewValue()==null)
    			||(e.getNewValue()!=null&&e.getOldValue()==null)
    			||(e.getOldValue()!=null&&e.getNewValue()!=null&&!((SHEPayTypeInfo)e.getOldValue()).getId().equals(((SHEPayTypeInfo)e.getNewValue()).getId()))){
    		updateAmount(this.txtTotalAmount.getBigDecimalValue());
    	}
    }
    
	public boolean setIsEarnestInHouseAmount() throws BOSException {
		String projectId = "null";
		RoomInfo room = null;
		String roomId =this.getUIContext().get(UIContext.ID).toString();
		EntityViewInfo roomView = new EntityViewInfo();
		FilterInfo roomFilter = new FilterInfo();
		roomView.setFilter(roomFilter);
		roomView.getSelector().add("*");
		roomView.getSelector().add("building");
		roomView.getSelector().add("building.sellProject");
		roomFilter.getFilterItems().add(new FilterItemInfo("id", roomId));
		RoomCollection roomCol = (RoomCollection) RoomFactory.getRemoteInstance().getRoomCollection(roomView);
		if(roomCol != null && roomCol.size()>0 ){
			room = roomCol.get(0);
		}
		if (room!=null && room.getBuilding().getSellProject() != null) {
			projectId = room.getBuilding().getSellProject().getId().toString();
		}
		if(projectId != null){
			//从setting中获得参数：定金是否计入楼款
			Boolean isHouseMoney = new Boolean(true);
			Map functionSetMap = setting.getFunctionSetMap();
			FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(projectId);
			if(funcSet == null){
				MsgBox.showInfo(this, "售楼设置中未设置该项目的定金是否隶属房款，此处默认隶属于房款！");
			}
			if(funcSet!=null) {
				isHouseMoney = funcSet.getIsHouseMoney();
				if(isHouseMoney == null){
					return true;
				}	
			}
			return isHouseMoney.booleanValue();
		}else{
			MsgBox.showInfo(this, "房间没有关联销售项目！");
			this.abort();
			return true;
		}
	}
    
    private void updatePayList(boolean isEarnestInHouseAmount) throws BOSException {
//		boolean isEarnestInHouseAmount = setIsEarnestInHouseAmount();
//    	boolean isEarnestInHouseAmount = false;

			
		SHEPayTypeInfo payType = (SHEPayTypeInfo) this.f7PayType.getValue();
		if (payType != null) {
			PayListEntryCollection payList = payType.getPayLists();
			BigDecimal eareatMoney = FDCHelper.ZERO;
			BigDecimal remain = this.txtTotalAmount.getBigDecimalValue();
			BigDecimal contractTotalAmount= this.txtTotalAmount.getBigDecimalValue();
			if (remain == null) 	remain = FDCHelper.ZERO;
			
			CRMHelper.sortCollection(payList, "seq", true);
			List toAddRowPurEntry = new ArrayList();
				
			for (int i = 0; i < payList.size(); i++) {
				PayListEntryInfo entry = payList.get(i);
				PurchasePayListEntryInfo purEntry = new PurchasePayListEntryInfo();
				Date curDate = new Date();
				if (entry.getBizTime().equals(BizTimeEnum.AppTime)) {
					curDate = entry.getAppDate();
				} else {
					curDate = new Date();
					int monthLimit = entry.getMonthLimit();
					int dayLimit = entry.getDayLimit();
					Calendar cal = Calendar.getInstance();
					cal.setTime(curDate);
					cal.add(Calendar.MONTH, monthLimit);
					cal.add(Calendar.DATE, dayLimit);
					curDate = cal.getTime();
				}
				purEntry.setAppDate(curDate);
				BigDecimal amount = FDCHelper.ZERO;
		    	BigDecimal buildingArea = (BigDecimal)this.getUIContext().get("buildingArea");
		    	BigDecimal roomArea = (BigDecimal)this.getUIContext().get("roomArea");
		    	
				MoneyDefineInfo moneyDefine = entry.getMoneyDefine();
				if(moneyDefine.getMoneyType().equals(MoneyTypeEnum.ReplaceFee)&&entry.getCollection()!=null){
					try {
						CollectionInfo collection=CollectionFactory.getRemoteInstance().getCollectionInfo(new ObjectUuidPK(entry.getCollection().getId()));
						amount=CRMHelper.getSubstituteAmountByCollection(null, collection,contractTotalAmount,contractTotalAmount,buildingArea, roomArea,room);
					} catch (BOSException e) {
						e.printStackTrace();
					} catch (EASBizException e) {
						e.printStackTrace();
					}
				}else{
					if (entry.getValue() != null) {
						amount = entry.getValue();
					} else {
						BigDecimal proportion = entry.getProportion();
						amount = contractTotalAmount;
						if (amount == null) {
							amount = FDCHelper.ZERO;
						}
						amount = amount.multiply(proportion).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
					}
				}
				
				if (moneyDefine.getMoneyType().equals(MoneyTypeEnum.EarnestMoney)) {
					eareatMoney = eareatMoney.add(amount);
				} else {
					//定金计入楼款时
					if(isEarnestInHouseAmount){
						amount = amount.subtract(eareatMoney);
					}else{
						
					}
					eareatMoney = FDCHelper.ZERO;
					if (amount.compareTo(FDCHelper.ZERO) < 0) {
						amount = FDCHelper.ZERO;
					}
				}
				if(SHEManageHelper.isMergerToContractMoneyType(moneyDefine.getMoneyType(),isEarnestInHouseAmount)){
					if (amount.compareTo(remain) > 0) {
						amount = remain;
						remain = FDCHelper.ZERO;
					} else {
						remain = remain.subtract(amount);
					}
				}
				if (i == payList.size() - 1) {
					amount = amount.add(remain);
				}
				purEntry.setAppAmount(amount);
				purEntry.setMoneyDefine(moneyDefine);
				purEntry.setCurrency(entry.getCurrency());
				toAddRowPurEntry.add(purEntry);
			}
			this.payListTable.removeRows();
			for (int i = 0; i < toAddRowPurEntry.size(); i++) {
				addPayListEntryRow((PurchasePayListEntryInfo) toAddRowPurEntry.get(i));
			}
			updateLoanAndAFAmount();
		}
	}
    
	

	private void updateLoanAndAFAmount() {
		BigDecimal loanAmount = FDCHelper.ZERO;
		BigDecimal afAmount = FDCHelper.ZERO;

		for (int i = 0; i < this.payListTable.getRowCount(); i++) {
			IRow row = this.payListTable.getRow(i);
			if (!(row.getCell("moneyName").getValue() instanceof MoneyDefineInfo)) {
				continue;
			}
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell("moneyName").getValue();
			BigDecimal amount = (BigDecimal) row.getCell("amount").getValue();
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
    
    private void addPayListEntryRow(PurchasePayListEntryInfo entry) throws BOSException {
		IRow row = this.payListTable.addRow();
		row.setUserObject(entry);
		row.getCell("moneyName").setValue(entry.getMoneyDefine());
		row.getCell("recDate").setValue(entry.getAppDate());
		row.getCell("amount").setValue(entry.getApAmount());
		row.getCell("currency").setValue(entry.getCurrency());
	}

	private boolean checkIsChange(AgioParam currAgioParam,AgioParam agioParam){
		Set id=new HashSet();
		if(currAgioParam.getAgios()!=null&&agioParam.getAgios()!=null){
			for(int i=0;i<currAgioParam.getAgios().size();i++){
				if(currAgioParam.getAgios().get(i).getAgio()==null) continue;
				id.add(currAgioParam.getAgios().get(i).getAgio().getId().toString());
			}
			if(id.size()!=0&&agioParam.getAgios().size()==0){
				return true;
			}
			for(int i=0;i<agioParam.getAgios().size();i++){
				if(agioParam.getAgios().get(i).getAgio()==null) continue;
				if(!id.contains(agioParam.getAgios().get(i).getAgio().getId().toString())){
					return true;
				}
			}
		}
		return false;
	}
    
	protected void btnChooseAgio_actionPerformed(ActionEvent e) throws Exception {
		String roomId =this.getUIContext().get(UIContext.ID).toString();
		if (roomId == null || "".equals(roomId)) {
			MsgBox.showInfo("请先选择房间!");
			return;
		}
		if(room.getCalcType()==null){
			FDCMsgBox.showWarning(this,"房间未定价，不能进行置业计划操作！");
    		return;
		}
		if(this.f7PayType.getValue()==null){
    		FDCMsgBox.showWarning(this,"请先选择付款方案！");
    		return;
    	}
		
		AgioParam agioParam = AgioSelectUI.showUI(this, roomId, 
				this.currAgioParam.getAgios(), this.currAgioParam ,this.editData,false,true);
		if (agioParam != null&&!this.currAgioParam.equals(agioParam)&&checkIsChange(this.currAgioParam,agioParam)) {
			this.currAgioParam = agioParam;
			updateAmount(room.getStandardTotalAmount());
		}
	}
	
	/**
	成交总价、成交总价、房间建筑销售底价、房间套内销售底价、房间建筑销售底价、房间套内销售底价
	 */
	private void updataRoomContractAndDealAmount() throws Exception{
//		RoomInfo roomInfo = (RoomInfo) this.txtRoomNumber.getUserObject();
//		if(roomInfo==null) roomInfo = this.editData.getRoom();
//		
//		SellTypeEnum sellType = (SellTypeEnum)this.comboSellType.getSelectedItem();
//		
//		BigDecimal fitmentAmount = (BigDecimal)this.txtFitmentAmount.getNumberValue();
//		BigDecimal attachmentAmount = (BigDecimal)this.txtAttachmentAmount.getNumberValue();
//		BigDecimal areaCompensateAmount = (BigDecimal)this.txtAreaCompensateAmount.getNumberValue();
//		SpecialAgioEnum splType = (SpecialAgioEnum)this.comboSpecialAgioType.getSelectedItem();
//		boolean isFitmentToContract = this.chkIsFitmentToContract.isSelected();
//		BigDecimal splAgio = (BigDecimal)this.txtSpecialAgio.getNumberValue();
//		
		RoomInfo room = null;
		String roomId =this.getUIContext().get(UIContext.ID).toString();
		EntityViewInfo roomView = new EntityViewInfo();
		FilterInfo roomFilter = new FilterInfo();
		roomView.setFilter(roomFilter);
		roomView.getSelector().add("*");
		roomView.getSelector().add("building");
		roomView.getSelector().add("building.sellProject");
		roomFilter.getFilterItems().add(new FilterItemInfo("id", roomId));
		RoomCollection roomCol = (RoomCollection) RoomFactory.getRemoteInstance().getRoomCollection(roomView);
		if(roomCol != null && roomCol.size()>0 ){
			room = roomCol.get(0);
		}
		SellTypeEnum sellType = room.getSellType();
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
		CalcTypeEnum type=null;
		if(room.getCalcType()==null){
			type=CalcTypeEnum.PriceByTotalAmount;
			currAgioParam.setPriceAccountType(PriceAccountTypeEnum.StandSetPrice);
		}else{
			type=room.getCalcType();
		}
		PurchaseParam purParam = SHEManageHelper.getAgioParam(this.currAgioParam, room, 
				room.getSellType(), type, false,room.getRoomArea(), room.getBuildingArea(), room.getRoomPrice(),room.getBuildPrice(),txtTotalAmount.getBigDecimalValue(),
				null,null,null,SpecialAgioEnum.DaZhe, payTypeAgio,payTypeName);
		if(purParam!=null) {
			this.txtAgioDes.setText(purParam.getAgioDes());
			this.txtAgio.setValue(purParam.getFinalAgio());
			this.txtTotalAmount.setValue(purParam.getDealTotalAmount());
		}
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
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
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

    protected void btnPrintPlan_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	if(room.getCalcType()==null){
    		FDCMsgBox.showWarning(this,"房间未定价，不能进行置业计划操作！");
    		return;
		}
    	Object isVirtual = this.getUIContext().get("isVirtual");
    	Object sellState = this.getUIContext().get("sellState");
    	RoomSellStateEnum roomSellState = (RoomSellStateEnum)sellState;
		Boolean virtual = (Boolean)isVirtual;
		if(isVirtual !=null && virtual.booleanValue() && !RoomSellStateEnum.OnShow.equals(roomSellState)&& !RoomSellStateEnum.SincerPurchase.equals(roomSellState)){
			MsgBox.showInfo("该房间已售，不可进行置业计划查询!");
			this.abort();
		}
		verifyPayListTable();
    	this.actionPrintPreview_actionPerformed(e);
    }
    
    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    	HashMap parmterMap = this.checkPurchase();
		BuyingRoomPlanPrintDataProvider data = new BuyingRoomPlanPrintDataProvider(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.BuyingRoomPlanQuery"),
				parmterMap);
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print("/bim/fdc/sellhouse/buyingRoomPlan", data,
				javax.swing.SwingUtilities.getWindowAncestor(this));
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
		HashMap parmterMap = this.checkPurchase();
		BuyingRoomPlanPrintDataProvider data = new BuyingRoomPlanPrintDataProvider(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.BuyingRoomPlanQuery"),
				parmterMap);
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print("/bim/fdc/sellhouse/buyingRoomPlan", data,
				javax.swing.SwingUtilities.getWindowAncestor(this));
        super.actionPrintPreview_actionPerformed(e);
    }
    
    protected void verifyPayListTable(){
    	for (int i = 0; i < this.payListTable.getRowCount(); i++) {
			IRow row = this.payListTable.getRow(i);
			if(row.getCell("moneyName").getValue() == null){
				FDCMsgBox.showWarning(this,"第"+(i+1)+"行的款项名称不能为空!");
				this.abort();
			}
			if(row.getCell("recDate").getValue() == null){
				FDCMsgBox.showWarning(this,"第"+(i+1)+"行的应收时间不能为空!");
				this.abort();
			}
			if(row.getCell("currency").getValue() == null){
				FDCMsgBox.showWarning(this,"第"+(i+1)+"行的币别不能为空!");
				this.abort();
			}
			if(row.getCell("amount").getValue() == null){
				FDCMsgBox.showWarning(this,"第"+(i+1)+"行的金额不能为空!");
				this.abort();
			}
    	}
    }

    public HashMap checkPurchase() throws Exception
	{
    	HashMap parmterMap = new HashMap();
    	if (this.f7PayType.getValue() == null ) {
    		MsgBox.showInfo("付款方案不能为空!");
			this.abort();
		}
    	String str_payType = this.f7PayType.getValue().toString();
    	String str_agioDes = this.txtAgioDes.getText();
    	String str_totalAmount = this.txtTotalAmount.getText();
    	String str_agio = this.txtAgio.getText();
    	String str_loanAmount = this.txtLoanAmount.getText();
    	String str_aFundAmount = this.txtAFundAmount.getText();
    	parmterMap.put("payType", str_payType);
    	parmterMap.put("agioDes", str_agioDes);
    	parmterMap.put("totalAmount", str_totalAmount);
    	parmterMap.put("agio", str_agio);
    	parmterMap.put("loanAmount", str_loanAmount);
    	parmterMap.put("aFundAmount", str_aFundAmount);
    	ArrayList list = new ArrayList();
    	for (int i = 0; i < this.payListTable.getRowCount(); i++) {
			IRow row = this.payListTable.getRow(i);
			ArrayList payList = new ArrayList();
			MoneyDefineInfo moneyName = (MoneyDefineInfo)row.getCell("moneyName").getValue();
			String str_moneyName = moneyName.getName();
			Date date = (Date)row.getCell("recDate").getValue();
			String str_recDate = getStringFromDate(date);
			String str_currency =(row.getCell("currency").getValue()).toString();
			String str_amount =(row.getCell("amount").getValue()).toString();
			payList.add(str_moneyName);
			payList.add(str_recDate);
			payList.add(str_currency);
			payList.add(str_amount);
			list.add(payList);
		}
    	parmterMap.put("payList", list);
    	return parmterMap;
	}
    
    private String getStringFromDate(Date date){
    	String temp = "";
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    	temp = sf.format(date);
    	return temp;
    }
    
    protected void txtTotalAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	if(isUpdateAmount){
    		if(e.getNewValue()==null){
    			FDCMsgBox.showWarning(this,"房款总额不能为空！");
    			isUpdateAmount=false;
        		this.txtTotalAmount.setValue(e.getOldValue());
        		isUpdateAmount=true;
        		return;
    		}
    		if(((BigDecimal)e.getNewValue()).compareTo(FDCHelper.ZERO)==0){
    			FDCMsgBox.showWarning(this,"房款总额不能为0！");
    			isUpdateAmount=false;
        		this.txtTotalAmount.setValue(e.getOldValue());
        		isUpdateAmount=true;
        		return;
    		}
    		if(this.f7PayType.getValue()==null){
        		FDCMsgBox.showWarning(this,"请先选择付款方案！");
        		isUpdateAmount=false;
        		this.txtTotalAmount.setValue(e.getOldValue());
        		isUpdateAmount=true;
        		return;
        	}
        	if(e.getOldValue().equals(e.getNewValue())) return;
        	
    		boolean isHasAgio=false;
    		AgioEntryInfo agioEntryInfo=null;
    		AgioEntryCollection agioCol=this.currAgioParam.getAgios();
    		
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
    		CalcTypeEnum type=null;
    		if(room.getCalcType()==null){
    			return;
    		}else{
    			type=room.getCalcType();
    		}
    		PurchaseParam purParam = SHEManageHelper.getAgioParam(oldAgioParam, room, 
    				room.getSellType(), type, false,room.getRoomArea(), room.getBuildingArea(), room.getRoomPrice(),room.getBuildPrice(),room.getStandardTotalAmount(),
    				null,null,null,SpecialAgioEnum.DaZhe, payTypeAgio,payTypeName);
    		
    		BigDecimal amount=purParam.getDealTotalAmount().subtract(this.txtTotalAmount.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtTotalAmount.getBigDecimalValue());
    		amount=SHEManageHelper.setScale(digit, toIntegerType,amount);
    		if(!isHasAgio){
    			if(!purParam.getDealTotalAmount().equals(this.txtTotalAmount.getBigDecimalValue())){
    				SignAgioEntryInfo entryInfo = new SignAgioEntryInfo();
    				entryInfo.setAmount(amount);
    				entryInfo.setSeq(this.currAgioParam.getAgios().size()+1);
    				this.currAgioParam.getAgios().add(entryInfo);
    				this.txtAgio.setUserObject(this.currAgioParam.getAgios());
    			}
    		}else{
    			if(!purParam.getDealTotalAmount().equals(this.txtTotalAmount.getBigDecimalValue().add(agioEntryInfo.getAmount()))){
    				if(amount.compareTo(FDCHelper.ZERO)==0){
    					this.currAgioParam.getAgios().remove(agioEntryInfo);
    				}else{
    					agioEntryInfo.setAmount(amount);
    					agioEntryInfo.setSeq(this.currAgioParam.getAgios().size()+1);
    				}
    			}
    		}
    		PurchaseParam afterPurParam = SHEManageHelper.getAgioParam(this.currAgioParam, room, 
    				room.getSellType(), type, false,room.getRoomArea(), room.getBuildingArea(), room.getRoomPrice(),room.getBuildPrice(),room.getStandardTotalAmount(),
    				null,null,null,SpecialAgioEnum.DaZhe, payTypeAgio,payTypeName);
    		if(afterPurParam!=null) {
    			this.txtAgioDes.setText(afterPurParam.getAgioDes());
    			this.txtAgio.setValue(afterPurParam.getFinalAgio());
    			
    			isUpdateAmount=false;
    			this.txtTotalAmount.setValue(afterPurParam.getDealTotalAmount());
    			isUpdateAmount=true;
    			this.updatePayList(payType.isIsTotal());
    		}
    	}
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

	protected IObjectValue createNewData() {
		// TODO Auto-generated method stub
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return RoomFactory.getRemoteInstance();
	}

}