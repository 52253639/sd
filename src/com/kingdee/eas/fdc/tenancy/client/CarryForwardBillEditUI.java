/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.ReceiveBillEidtUI;
import com.kingdee.eas.fdc.tenancy.CarryForwardTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillEntryInfo;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 * @deprecated 收款单实现了结转功能，该界面后期作废
 */
public class CarryForwardBillEditUI extends AbstractCarryForwardBillEditUI
{
	private ReceiveBillEidtUI revUI = null;
    
    public static final String KEY_SRC_TENANCY_ID = "srcTenancyId";
    public static final String KEY_TENANCY_ROOM_ID = "tenancyRoomId";
    public static final String KEY_TARGET_TENANCY_ID = "targetTenancyId";
    
    /**
     * output class constructor
     */
    public CarryForwardBillEditUI() throws Exception
    {
        super();
    }

	protected IObjectValue createNewData() {
		return null;
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		this.f7TenancyRoom.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e){
				setF7TenancyRoomFilter();
			}
		});
	}
	
	private void initControl() {
		this.actionEdit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionRemove.setVisible(false);
		
		this.actionLast.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		
		this.actionAttachment.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		
		this.actionSave.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.txtRemainDepositAmount.setEnabled(false);
		
		f7TenancyBillDataChangeListener = new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7TenancyBill_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        };
		this.f7TenancyBill.addDataChangeListener(f7TenancyBillDataChangeListener);
		
		f7TenancyRoomDataChangeListener = new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7TenancyRoom_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        };
		this.f7TenancyRoom.addDataChangeListener(f7TenancyRoomDataChangeListener);
		
		comboCarryForwardTypeItemListener = new ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboCarryForwardType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        };
		
		this.comboCarryForwardType.addItemListener(comboCarryForwardTypeItemListener);
	}

	//在这里定义监听器,而不在界面元收据中定义,是为了取得该监听器的引用,便于在detachListeners()中操作
	private DataChangeListener f7TenancyBillDataChangeListener = null;
	private DataChangeListener f7TenancyRoomDataChangeListener = null;
	private ItemListener comboCarryForwardTypeItemListener = null;
	
	public void loadFields() {
		detachListeners();
		
		super.loadFields();
		
		String tenancyId = (String) this.getUIContext().get(KEY_SRC_TENANCY_ID);
		String tenancyRoomId = (String) this.getUIContext().get(KEY_TENANCY_ROOM_ID);
		
		String revTenancyId = null;//结转操作中待收款的合同ID
		if(tenancyRoomId != null){
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("tenancy.number");
			sels.add("tenancy.tenancyName");
			
			try {
				TenancyRoomEntryInfo tenancyRoom = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryInfo(new ObjectUuidPK(tenancyRoomId), sels);
				TenancyBillInfo tenancy = tenancyRoom.getTenancy();
				
				if(tenancy != null) revTenancyId = tenancy.getId().toString();
				
				BigDecimal remainDepositAmount = tenancyRoom.getRemainDepositAmount();
				this.f7TenancyBill.setValue(tenancy);
				this.f7TenancyRoom.setValue(tenancyRoom);
				setF7TenancyRoomFilter();
				this.txtRemainDepositAmount.setValue(remainDepositAmount);
			} catch (EASBizException e) {
				handleException(e);
			} catch (BOSException e) {
				handleException(e);
			}
		}else if(tenancyId != null){
			revTenancyId = tenancyId;
			try {
				TenancyBillInfo tenancy = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(tenancyId));
				this.f7TenancyBill.setValue(tenancy);
				setF7TenancyRoomFilter();
			} catch (EASBizException e) {
				handleException(e);
			} catch (BOSException e) {
				handleException(e);
			}
		}
		
		this.setFDCReceiveBillTab(revTenancyId);
		
		attachListeners();
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	//基类方法报错，暂重写处理
    protected void checkIsOUSealUp() throws Exception{
    }
	
    /**
     * 根据待收款的租赁合同ID设置引用的收款单新增界面
     * */
	private void setFDCReceiveBillTab(String tenancyId) {
		TenancyBillInfo tenancy = new TenancyBillInfo();//TODO 
		
		this.tabPaneRev.removeAll();
		if (tenancy == null) {
			return;
		}
		KDScrollPane scrollPane = new KDScrollPane();
		this.tabPaneRev.add(scrollPane, "收款单");
		UIContext uiContext = new UIContext();

		uiContext.put(ReceiveBillEidtUI.KEY_MONEYTSYSTYPE, MoneySysTypeEnum.TenancySys);
		if(tenancyId != null)
			uiContext.put(ReceiveBillEidtUI.KEY_TENANCYBILLID, tenancyId);
		
		try {
			revUI = (ReceiveBillEidtUI) UIFactoryHelper.initUIObject(
					ReceiveBillEidtUI.class.getName(), uiContext, null, "ADDNEW");
		} catch (UIException e) {
			this.handleException(e);
		}
		if (revUI != null) {
			scrollPane.setViewportView(revUI);
			scrollPane.setKeyBoardControl(true);
		}
	}

	private void f7TenancyBill_dataChanged(DataChangeEvent e) throws Exception {
		TenancyBillInfo tenancy = (TenancyBillInfo) this.f7TenancyBill.getValue();
		if (tenancy != null){
			this.f7TenancyRoom.setValue(null);
			this.f7TenancyRoom.setEnabled(true);
			
			setF7TenancyRoomFilter();
		}else{
			this.f7TenancyRoom.setValue(null);
			this.f7TenancyRoom.setEnabled(false);
		}
	}

	/**
	 * 根据租赁合同编号设置租赁房间的F7过滤条件
	 * */
	private void setF7TenancyRoomFilter() {
		TenancyBillInfo tenancy = (TenancyBillInfo) this.f7TenancyBill.getValue();
		
		if(tenancy == null){
			return;
		}
		
		this.f7TenancyRoom.getQueryAgent().setDefaultFilterInfo(null);
		this.f7TenancyRoom.getQueryAgent().setHasCUDefaultFilter(false);
		this.f7TenancyRoom.getQueryAgent().resetRuntimeEntityView();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
//		filter.getFilterItems().add(new FilterItemInfo("flagAtTerm", FlagAtTermEnum.Unknown));
		
		filter.getFilterItems().add(new FilterItemInfo("tenancy.id", tenancy.getId().toString(),CompareType.EQUALS));
		view.setFilter(filter);
		this.f7TenancyRoom.setEntityViewInfo(view);
	}
	
	private void f7TenancyRoom_dataChanged(DataChangeEvent e) throws Exception {
		TenancyRoomEntryInfo tenancyRoom = (TenancyRoomEntryInfo) this.f7TenancyRoom.getValue();
		if (tenancyRoom != null){
			BigDecimal remainDepositAmount = tenancyRoom.getRemainDepositAmount();
			this.txtRemainDepositAmount.setValue(remainDepositAmount);
		}else{
			this.txtRemainDepositAmount.setValue(null);
		}
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		TenancyBillInfo srcTen = (TenancyBillInfo) this.f7TenancyBill.getValue();
		if(srcTen == null){
			MsgBox.showInfo("结转的租赁合同不能为空！");
			abort();
		}
		
		TenancyRoomEntryInfo srcTenRoom = (TenancyRoomEntryInfo) this.f7TenancyRoom.getValue();
		if(srcTenRoom == null){
			MsgBox.showInfo("结转的租赁房间不能为空！");
			abort();
		}
		
		BigDecimal srcRemainDepositAmount = this.txtRemainDepositAmount.getBigDecimalValue();
		if(srcRemainDepositAmount == null  ||  srcRemainDepositAmount.compareTo(FDCHelper.ZERO) == 0){
			MsgBox.showInfo("没有剩余押金,不能结转！");
			abort();
		}
		
		//改续租或更名合同存在且审批后,其原合同才能进行押金结转操作
    	String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(srcTen.getId().toString());
    	if(targetTenId == null){
    		MsgBox.showInfo("该合同没有进行改续租或更名,不能结转！");
			abort();
    	}
    	
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(targetTenId));
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	if(TenancyBillStateEnum.Saved.equals(tenState) || TenancyBillStateEnum.Submitted.equals(tenState) ||
    			TenancyBillStateEnum.Auditing.equals(tenState)){
    		MsgBox.showInfo("改续租或者更名的合同还没有审批,不能结转！");
			abort();
    	}
    	
		//对引用的收款单界面进行提交校验,并获得收款单值对象,将收款单和待结转的合同进行校验：
		//结转类型是转租金和水电的,只能结转到原合同上;结转类型是转新租押金或转新租租金时候,只能结转到改续租或者更名的新合同上
		revUI.storeFields();
		ReceivingBillInfo rev = (ReceivingBillInfo) revUI.getEditData();
		revUI.verifyUIData();
		
		//收款单上的租赁合同
		TenancyBillInfo tenancy = rev.getFdcReceiveBill().getTenancyContract();
		if(tenancy == null){
			MsgBox.showInfo("收款单逻辑错误,其租赁合同为空!");
			this.abort();
		}
		
		CarryForwardTypeEnum carryForwardType = (CarryForwardTypeEnum) this.comboCarryForwardType.getSelectedItem();
		if(CarryForwardTypeEnum.ToRent.equals(carryForwardType) || CarryForwardTypeEnum.ToWaterAndElectricityRate.equals(carryForwardType)){
			if(!srcTen.getId().toString().equals(tenancy.getId().toString())){
				MsgBox.showInfo("转押金或转水电时只能结转到当前租赁合同！");
				this.abort();
			}
		}else if(CarryForwardTypeEnum.ToNewBillRent.equals(carryForwardType) || CarryForwardTypeEnum.ToNewBillDeposit.equals(carryForwardType)){
			if(!targetTenId.equals(tenancy.getId().toString())){
				MsgBox.showInfo("该结转类型只能结转到改续租或更名的新合同上！");
				this.abort();
			}
		}
				
		if(srcRemainDepositAmount == null  ||  srcRemainDepositAmount.compareTo(rev.getAmount()) < 0){
			MsgBox.showInfo("剩余押金不够结转！");
			abort();
		}
	}
	
	/**
	 * 提交即执行结转操作
	 * */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyInput(e);
		
		TenancyBillInfo srcTen = (TenancyBillInfo) this.f7TenancyBill.getValue();
		TenancyRoomEntryInfo srcTenRoom = (TenancyRoomEntryInfo) this.f7TenancyRoom.getValue();
		
		ReceivingBillInfo rev = (ReceivingBillInfo) revUI.getEditData();
		rev.setBillStatus(BillStatusEnum.AUDITED);//TODO 对结转的收款单直接设置为已审批状态,后期可能需要修改
		
		//封装红字收款单作为结转类型的退款单
		ReceivingBillInfo rubricRev = buildRubricRevBill(srcTen, srcTenRoom, rev);
		
		ReceivingBillCollection receivingBills = new ReceivingBillCollection();
		receivingBills.add(rev);
		receivingBills.add(rubricRev);
		TenancyBillFactory.getRemoteInstance().carryForward(receivingBills);
		
		MsgBox.showInfo(this, "结转成功!");
		
		this.destroyWindow();
	}
	
	private MoneyDefineInfo carryForwardMoneyName = null;//结转类型的款项名称
	/**
	 * 封装红字收款单.
	 * TODO 目前退款的币别和科目均与收款单相同,需要进行调整;收款单如果没有设置编码规则,红字收款单的编码该如何设置
	 * */
	private ReceivingBillInfo buildRubricRevBill(TenancyBillInfo srcTen, TenancyRoomEntryInfo srcTenRoom, ReceivingBillInfo rev) throws BOSException {
		ReceivingBillInfo rubricRev = (ReceivingBillInfo) rev.clone();
		rubricRev.setId(null);
		if(carryForwardMoneyName == null){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.CarryForwardAmount));
			view.setFilter(filter);
			MoneyDefineCollection moneyNames = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
			if(!moneyNames.isEmpty()){
				carryForwardMoneyName = moneyNames.get(0);
			}
		}
		
		if(carryForwardMoneyName == null){
			MsgBox.showInfo(this, "请先定义结转金类型的款项！");
			this.abort();
		}
		
		BigDecimal rubricAmount = FDCHelper.ZERO.subtract(rev.getAmount());
		//TODO 对于收款单设置启用了编码规则,这里需要对红字收款单的编码进行处理
		String rubricNum = "Rubric-" + rubricRev.getNumber() + System.currentTimeMillis();
		rubricRev.setNumber(rubricNum);
		rubricRev.setAmount(rubricAmount);
		rubricRev.setRecBillType(carryForwardMoneyName == null ? null : carryForwardMoneyName.getRevBillType());
		
		if (rubricRev.getEntries().isEmpty()){
			ReceivingBillEntryInfo entry = new ReceivingBillEntryInfo();
			entry.setAmount(rubricRev.getAmount());
			entry.setActualAmt(rubricRev.getAmount());
			rubricRev.getEntries().add(entry);
		} else{
			ReceivingBillEntryInfo entry = rubricRev.getEntries().get(0);
			entry.setAmount(rubricRev.getAmount());
			entry.setActualAmt(rubricRev.getAmount());
		}
		
		//设置房地产红字收款单
		FDCReceiveBillInfo rubricFDCRev = (FDCReceiveBillInfo) rev.getFdcReceiveBill().clone();
		//注意这里：rev是从界面的editData获得的,他在初始化fdcReceiveBillInfo时候已经设置了ID
		//如果不将该ID清空,会导致结转提交的2张收款单时,第2张房地产收款单与第一张的ID相同而执行更新操作
		rubricFDCRev.setId(null);
		rubricFDCRev.setBillTypeEnum(ReceiveBillTypeEnum.settlement);
		rubricFDCRev.setNumber(rubricNum);
		rubricFDCRev.setTenancyContract(srcTen);
		rubricFDCRev.setRoom(srcTenRoom.getRoom());
		rubricFDCRev.setSeq(1);//结转一定是对押金扣款,而押金序号一定是1
		
//		rubricFDCRev.setMoneyDefine(carryForwardMoneyName);//TODO  作废
		rubricFDCRev.setCheque(null);
		rubricFDCRev.setReceiptNumber(null);
		
		FDCReceiveBillEntryCollection fdcReceiveBillEntryColl = new FDCReceiveBillEntryCollection();
		for (int i = 0; i < rubricRev.getFdcReceiveBillEntry().size(); i++){
			FDCReceiveBillEntryInfo receiveBillEntryInfo = rubricRev.getFdcReceiveBillEntry().get(i);
			receiveBillEntryInfo.setAmount(FDCHelper.ZERO.subtract(receiveBillEntryInfo.getAmount()));
			fdcReceiveBillEntryColl.add(receiveBillEntryInfo);
		}
		rubricRev.getFdcReceiveBillEntry().clear();
		rubricRev.getFdcReceiveBillEntry().addCollection(fdcReceiveBillEntryColl);
		
		rubricRev.setFdcReceiveBill(rubricFDCRev);
		return rubricRev;
	}
	
	private void comboCarryForwardType_itemStateChanged(ItemEvent e) throws Exception {
		CarryForwardTypeEnum carryForwardType = (CarryForwardTypeEnum) this.comboCarryForwardType.getSelectedItem();
		
		TenancyBillInfo ten = (TenancyBillInfo) this.f7TenancyBill.getValue();
		if(ten == null){
			return;
		}
		
		if(CarryForwardTypeEnum.ToRent.equals(carryForwardType) || CarryForwardTypeEnum.ToWaterAndElectricityRate.equals(carryForwardType)){
			setFDCReceiveBillTab(ten.getId().toString());
		}else if(CarryForwardTypeEnum.ToNewBillRent.equals(carryForwardType) || CarryForwardTypeEnum.ToNewBillDeposit.equals(carryForwardType)){
			String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(ten.getId().toString());
			if(targetTenId == null){
				MsgBox.showInfo(this, "该合同没有改续租或者更名的新合同,不能进行该类型的结转!");
				abort();
			}
			
			TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(targetTenId));
	    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
	    	if(TenancyBillStateEnum.Saved.equals(tenState) || TenancyBillStateEnum.Submitted.equals(tenState) ||
	    			TenancyBillStateEnum.Auditing.equals(tenState)){
	    		MsgBox.showInfo("改续租或者更名的新合同还没有审批,不能结转！");
				abort();
	    	}
			setFDCReceiveBillTab(targetTenId);
		}
	}
	
	protected void attachListeners() {
		this.f7TenancyBill.addDataChangeListener(f7TenancyBillDataChangeListener);
		this.f7TenancyRoom.addDataChangeListener(f7TenancyRoomDataChangeListener);
		this.comboCarryForwardType.addItemListener(comboCarryForwardTypeItemListener);
	}
	
	protected void detachListeners() {
		this.f7TenancyBill.removeDataChangeListener(f7TenancyBillDataChangeListener);
		this.f7TenancyRoom.removeDataChangeListener(f7TenancyRoomDataChangeListener);
		this.comboCarryForwardType.removeItemListener(comboCarryForwardTypeItemListener);
	}
	
}