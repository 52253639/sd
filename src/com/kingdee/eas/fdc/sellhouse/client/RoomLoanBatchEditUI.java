/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
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
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.BankFactory;
import com.kingdee.eas.basedata.assistant.BankInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AFMmmTypeEnum;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedDataEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedDataEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedFactory;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.BusinessTypeNameEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanAFMEntrysCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanAFMEntrysInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanFactory;
import com.kingdee.eas.fdc.sellhouse.RoomLoanInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class RoomLoanBatchEditUI extends AbstractRoomLoanBatchEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomLoanBatchEditUI.class);

	/**
	 * output class constructor
	 */
	public RoomLoanBatchEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected IObjectValue createNewData() {
		RoomLoanInfo objectValue = new RoomLoanInfo();
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomLoanFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		this.tblRoomInfo.checkParsed();
		this.tblLoanData.checkParsed();
		
		super.onLoad();
		java.util.List roomLoanIdList = (java.util.List) getUIContext().get("roomLoanIdList");
		
		String sellProjectId = (String) getUIContext().get("sellProjectId");
		SellProjectInfo sellproject = this.getSellProject(sellProjectId);
		this.f7SellProject.setDataNoNotify(sellproject);

		//初始化房间表格
		KDTextField txtNumber = new KDTextField();
    	txtNumber.setHorizontalAlignment(KDTextField.LEFT);
    	txtNumber.setMaxLength(100);
    	this.tblRoomInfo.getColumn("afmNumber").setEditor(new KDTDefaultCellEditor(txtNumber));
    	this.tblRoomInfo.getColumn("afmNumber").getStyleAttributes().setLocked(false);
    	
    	KDBizPromptBox f7LoanBank = new KDBizPromptBox();
    	f7LoanBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7BankQuery");
    	this.tblRoomInfo.getColumn("loanBank").setEditor(new KDTDefaultCellEditor(f7LoanBank));
    	this.tblRoomInfo.getColumn("loanBank").getStyleAttributes().setLocked(false);
    	
    	KDFormattedTextField loanFixYear = new KDFormattedTextField();
    	loanFixYear.setDataType(KDFormattedTextField.INTEGER);
    	loanFixYear.setHorizontalAlignment(KDFormattedTextField.LEFT);
    	loanFixYear.setNegatived(false);
    	this.tblRoomInfo.getColumn("loanFixYear").setEditor(new KDTDefaultCellEditor(loanFixYear));
    	this.tblRoomInfo.getColumn("loanFixYear").getStyleAttributes().setLocked(false);
    	
    	//初始化资料表格
    	KDTextField txtField = new KDTextField();
    	txtField.setHorizontalAlignment(KDTextField.LEFT);
    	txtNumber.setMaxLength(255);
    	this.tblLoanData.getColumn("name").setEditor(new KDTDefaultCellEditor(txtField));
    	this.tblLoanData.getColumn("name").getStyleAttributes().setLocked(false);
    	
    	this.tblLoanData.getColumn("remark").setEditor(new KDTDefaultCellEditor(txtField));
    	this.tblLoanData.getColumn("remark").getStyleAttributes().setLocked(false);
    	
    	KDDatePicker datePicker = new KDDatePicker();
    	this.tblLoanData.getColumn("finishDate").setEditor(new KDTDefaultCellEditor(datePicker));
		
		if(roomLoanIdList!=null && !roomLoanIdList.isEmpty()){
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("id");
			view.getSelector().add("number");
			view.getSelector().add("aFMortgaged.*");
			view.getSelector().add("room.number");
			view.getSelector().add("room.sellState");
			view.getSelector().add("oRSOMortgaged.id");
			view.getSelector().add("oRSOMortgaged.number");
			view.getSelector().add("mmType.name");
			view.getSelector().add("mmType.moneyType");
			view.getSelector().add("actualLoanAmt");
			view.getSelector().add("loanBank.name");
			view.getSelector().add("loanFixedYear");
			view.getSelector().add("purchase.loanAmount");
			view.getSelector().add("purchase.accFundAmount");
			view.getSelector().add("purchase.customerNames");
			view.getSelector().add("purchase.bizNumber");
			view.getSelector().add("purchase.bizDate");
			view.getSelector().add("sign.loanAmount");
			view.getSelector().add("sign.accFundAmount");
			view.getSelector().add("sign.customerNames");
			view.getSelector().add("sign.bizNumber");
			view.getSelector().add("sign.bizDate");
			view.getSelector().add("sign.srcId");
			FilterInfo filter = new FilterInfo();
			
			HashSet roomLoanIdSet = new HashSet();
			for(int i=0; i<roomLoanIdList.size(); i++){
				roomLoanIdSet.add(roomLoanIdList.get(i));
			}
			filter.getFilterItems().add(new FilterItemInfo("id", roomLoanIdSet, CompareType.INCLUDE));

			view.setFilter(filter);
			
			RoomLoanCollection roomLoanCol = RoomLoanFactory.getRemoteInstance().getRoomLoanCollection(view);
			if(roomLoanCol != null && !roomLoanCol.isEmpty()){
				for(int r=0; r<roomLoanCol.size(); r++){
					RoomLoanInfo roomLoanInfo = roomLoanCol.get(r);
					//获取房间信息
					RoomInfo room = this.getRoomInfo(roomLoanInfo.getRoom().getId().toString());
					roomLoanInfo.setRoom(room);
					
					IRow row = tblRoomInfo.addRow();
					row.setUserObject(roomLoanInfo);
					row.getCell("id").setValue(roomLoanInfo.getId());
					
					//用第一个单元来保存按揭的分录，方便更新资料表格的数据
					row.getCell("id").setUserObject(roomLoanInfo.getAFMortgaged());
					
					row.getCell("room").setValue(roomLoanInfo.getRoom().getNumber());
					
					row.getCell("afmNumber").setValue(roomLoanInfo.getNumber());
					
					if(roomLoanInfo.getRoom().getSellState().equals(RoomSellStateEnum.Purchase)
							&& roomLoanInfo.getPurchase() != null){  //认购
						if(roomLoanInfo.getPurchase() != null){
							roomLoanInfo.setPurchase(roomLoanInfo.getPurchase());
							row.getCell("customer").setValue(roomLoanInfo.getPurchase().getCustomerNames());
							row.getCell("contractNumber").setValue(roomLoanInfo.getPurchase().getBizNumber());
							if (roomLoanInfo.getMmType() != null && roomLoanInfo.getMmType().getMoneyType() != null) {
								if (roomLoanInfo.getMmType().getMoneyType().equals(MoneyTypeEnum.LoanAmount)) {  //按揭
									if (roomLoanInfo.getPurchase().getLoanAmount() != null) {
										row.getCell("loanAmount").setValue(roomLoanInfo.getPurchase().getLoanAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
									}
								} else if (roomLoanInfo.getMmType().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)) {  //公积金
									if (roomLoanInfo.getPurchase().getAccFundAmount() != null) {
										row.getCell("loanAmount").setValue(roomLoanInfo.getPurchase().getAccFundAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
									}
								}
							}
							if(row.getCell("loanAmount").getValue()!=null)
								roomLoanInfo.setActualLoanAmt((BigDecimal) row.getCell("loanAmount").getValue());
						}
					}
					else if(roomLoanInfo.getRoom().getSellState().equals(RoomSellStateEnum.Sign)
							&& roomLoanInfo.getSign() != null){  //签约
						roomLoanInfo.setSign(roomLoanInfo.getSign());
						row.getCell("customer").setValue(roomLoanInfo.getSign().getCustomerNames());
						row.getCell("contractNumber").setValue(roomLoanInfo.getSign().getBizNumber());
						
						if (roomLoanInfo.getMmType() != null && roomLoanInfo.getMmType().getMoneyType() != null) {
							if (roomLoanInfo.getMmType().getMoneyType().equals(MoneyTypeEnum.LoanAmount)) {  //按揭
								if (roomLoanInfo.getSign().getLoanAmount() != null) {
									row.getCell("loanAmount").setValue(roomLoanInfo.getSign().getLoanAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
								}
							} else if (roomLoanInfo.getMmType().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)) {  //公积金
								if (roomLoanInfo.getSign().getAccFundAmount() != null) {
									row.getCell("loanAmount").setValue(roomLoanInfo.getSign().getAccFundAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
								}
							}
						}
						if(row.getCell("loanAmount").getValue()!=null)
							roomLoanInfo.setActualLoanAmt((BigDecimal) row.getCell("loanAmount").getValue());
					}
					
					if(roomLoanInfo.getORSOMortgaged() != null){
						AFMortgagedInfo afmInfo = this.getAfmortgaged(roomLoanInfo.getORSOMortgaged().getId().toString());
						roomLoanInfo.setORSOMortgaged(afmInfo);
					}
					if(roomLoanInfo.getMmType() != null){
						row.getCell("mmType").setValue(roomLoanInfo.getMmType().getName());
					}
					if(roomLoanInfo.getLoanBank() != null){
						BankInfo bank = this.getBankInfo(roomLoanInfo.getLoanBank().getId().toString());
						roomLoanInfo.setLoanBank(bank);
						
						row.getCell("loanBank").setValue(roomLoanInfo.getLoanBank());
					}
					row.getCell("loanFixYear").setValue(new Integer(roomLoanInfo.getLoanFixedYear()));
				}
			}
		}
		
		//初始化办理进程下拉框选项
		String afmId = null;
		if(getUIContext().get("afmId") != null){
			afmId = (String)getUIContext().get("afmId");
		}
		AFMortgagedInfo afmInfo = this.getAfmortgaged(afmId);
		
		this.f7Afmortgaged.setValue(afmInfo);
		//填充资料表格
		if(afmInfo != null && afmInfo.getDataEntrys() != null && !afmInfo.getDataEntrys().isEmpty()){
			AFMortgagedDataEntryCollection afmDataCol = afmInfo.getDataEntrys();
			for(int i=0; i<afmDataCol.size(); i++){
				AFMortgagedDataEntryInfo dataInfo = afmDataCol.get(i);
				IRow row = this.tblLoanData.addRow();
				
				row.getCell("name").setValue(dataInfo.getName());
				row.getCell("isFinish").setValue(Boolean.FALSE);
				row.getCell("remark").setValue(dataInfo.getRemark());
			}
		}
		
		if(afmInfo != null && afmInfo.getApproachEntrys() != null){
			KDComboBox combox = (KDComboBox)this.contCurProcess.getBoundEditor();
			combox.removeAllItems();
			for(int i=0; i<afmInfo.getApproachEntrys().size(); i++){
				combox.addItem(afmInfo.getApproachEntrys().get(i).getName());
			}
			/*this.comboCurProcess = combox;
			this.comboCurProcess.setName("comboCurProcess");
			this.comboCurProcess.setRequired(true);
			this.contCurProcess.setBoundEditor(comboCurProcess);*/
		}
		
		this.initF7Afmortgaged();
		this.f7Afmortgaged.setEditable(false);
		
		this.initF7AddRoom();
		
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionSave, actionCopy, actionPre, actionNext, actionFirst,
				actionLast, actionCancel, actionCancelCancel, actionPrint,
				actionPrintPreview,actionEdit,actionRemove }, false);
		menuEdit.setVisible(false);
		menuView.setVisible(false);
		menuBiz.setVisible(false);
		menuSubmitOption.setVisible(false);
		f7Transactor.setValue(SysContext.getSysContext().getCurrentUserInfo());
		pkProcessLoanDate.setValue(null);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		verifyInput(e);
		Date processLoanDate = (Date) pkProcessLoanDate.getValue();
		UserInfo transactor = (UserInfo) f7Transactor.getValue();
		
		Date appDate = null;
		if(this.pkAppDate.getValue() != null){
			appDate = (Date) pkAppDate.getValue();
		}

		Map valueMap = new HashMap();
		valueMap.put("processLoanDate", processLoanDate);
		valueMap.put("transactor", transactor);

		List roomLoanList = new ArrayList();
		for(int i=0; i<this.tblRoomInfo.getRowCount(); i++){
			RoomLoanInfo loanInfo = (RoomLoanInfo)this.tblRoomInfo.getRow(i).getUserObject();
			loanInfo.setTransactor(transactor);
			loanInfo.setProcessLoanDate(processLoanDate);
			loanInfo.setApplicationDate(appDate);
			
			AFMortgagedInfo afmInfo = (AFMortgagedInfo)this.f7Afmortgaged.getValue();
			
			//按揭方案变化，更新进程
			boolean isAppChange = false;
			if(loanInfo.getORSOMortgaged() == null){
				loanInfo.setORSOMortgaged(afmInfo);
				isAppChange = true;
			}
			else if(loanInfo.getORSOMortgaged()!=null 
					&& !loanInfo.getORSOMortgaged().getId().toString().equals(afmInfo.getId().toString())){
				isAppChange = true;
				for(int p=0; p<loanInfo.getAFMortgaged().size(); p++){
					if(loanInfo.getAFMortgaged().get(p).isIsAOrB()){
						loanInfo.getAFMortgaged().removeObject(p);
					}
				}
				loanInfo.setORSOMortgaged(afmInfo);
			}
			//设置按揭进程，状态和当前进程
			RoomLoanAFMEntrysCollection afmAppEntryCol = new RoomLoanAFMEntrysCollection();
			for(int eIndex=0; eIndex<afmInfo.getApproachEntrys().size(); eIndex++){
				AFMortgagedApproachEntryInfo appEntryInfo = afmInfo.getApproachEntrys().get(eIndex);
				
				RoomLoanAFMEntrysInfo afmEntryInfo = null;
				
				//判断新增进程还是修改进程
				if(isAppChange){  //新增进程
					afmEntryInfo = new RoomLoanAFMEntrysInfo();
					afmEntryInfo.setIsAOrB(true);
					afmEntryInfo.setApproach(appEntryInfo.getName());
					afmEntryInfo.setIsFinish(Boolean.FALSE.booleanValue());
					afmEntryInfo.setPromiseFinishDate(getApproachPromiseDate(afmInfo.getApproachEntrys(), appEntryInfo, loanInfo));
					afmEntryInfo.setIsFinishFlag(appEntryInfo.isIsFinish());
					afmEntryInfo.setRemark(appEntryInfo.getRemark());
				}
				else{  //找出对应的进程进行修改
					for(int p=0; p<loanInfo.getAFMortgaged().size(); p++){
						if(loanInfo.getAFMortgaged().get(p).isIsAOrB()
								&& loanInfo.getAFMortgaged().get(p).getApproach().equals(appEntryInfo.getName())){
							afmEntryInfo = loanInfo.getAFMortgaged().get(p);
						}
					}
				}
				if(afmEntryInfo!=null && this.comboCurProcess.getSelectedItem().toString().equals(appEntryInfo.getName())) {
					afmEntryInfo.setIsFinish(true);
					afmEntryInfo.setActualFinishDate(processLoanDate);
					
					loanInfo.setDepositInDate(afmEntryInfo.getPromiseFinishDate());
					loanInfo.setDepositOutDate(processLoanDate);
					
//					BusinessTypeNameEnum bizType = BusinessTypeNameEnum.MORTGAGE;
					String bizType = SHEManageHelper.MORTGAGE;
					if(loanInfo.getMmType()!=null && loanInfo.getMmType().getMoneyType()!=null
							&& loanInfo.getMmType().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
						bizType = SHEManageHelper.ACCFUND;
					}
					if(!isAppChange && afmEntryInfo.isIsFinishFlag()){  //修改进程，使用进程原有的标识做判断是否办理完成
						loanInfo.setApproach("办理完成");
						loanInfo.setAFMortgagedState(AFMortgagedStateEnum.TRANSACTED);
						loanInfo.setActualFinishDate(processLoanDate);
						
						//更新业务总览对应的服务
						SHEManageHelper.updateTransactionOverView(null, loanInfo.getRoom(), bizType,
							loanInfo.getPromiseDate(), loanInfo.getActualFinishDate(), false);
					}
					else if(appEntryInfo.isIsFinish()){  //否则由方案的进程标识判断是否办理完成
						loanInfo.setApproach("办理完成");
						loanInfo.setAFMortgagedState(AFMortgagedStateEnum.TRANSACTED);
						loanInfo.setActualFinishDate(processLoanDate);
						
						//更新业务总览对应的服务
						SHEManageHelper.updateTransactionOverView(null, loanInfo.getRoom(), bizType,
							loanInfo.getPromiseDate(), loanInfo.getActualFinishDate(), false);
					}
					else{
						loanInfo.setApproach(appEntryInfo.getName());
						loanInfo.setAFMortgagedState(AFMortgagedStateEnum.TRANSACTING);
						
						//更新业务总览对应的服务
						SHEManageHelper.updateTransactionOverView(null, loanInfo.getRoom(), bizType,
							loanInfo.getPromiseDate(), null, false);
					}
				}
				
				if(isAppChange){
					afmAppEntryCol.add(afmEntryInfo);
				}
			}
			//新增进程
			if(isAppChange && afmAppEntryCol.size() > 0){
				loanInfo.getAFMortgaged().addCollection(afmAppEntryCol);
			}
			
			//更新按揭房间的信息
			loanInfo.setNumber((String)this.tblRoomInfo.getRow(i).getCell("afmNumber").getValue());
			loanInfo.setLoanBank((BankInfo)this.tblRoomInfo.getRow(i).getCell("loanBank").getValue());
			if(this.tblRoomInfo.getRow(i).getCell("loanBank").getValue() != null){
				loanInfo.setLoanFixedYear(((Integer)this.tblRoomInfo.getRow(i).getCell("loanFixYear").getValue()).intValue());
			}
			
			//批量更新资料
			if(this.checkBatchLoanData.isSelected()){
				for(int p=0; p<loanInfo.getAFMortgaged().size(); p++){
					if(!loanInfo.getAFMortgaged().get(p).isIsAOrB()){
						loanInfo.getAFMortgaged().removeObject(p);
					}
				}
				
				RoomLoanAFMEntrysCollection afmEntryCol = new RoomLoanAFMEntrysCollection();
				for(int a=0; a<this.tblLoanData.getRowCount(); a++ ){
					RoomLoanAFMEntrysInfo afmEntryInfo = new RoomLoanAFMEntrysInfo();
					afmEntryInfo.setIsAOrB(false);
					afmEntryInfo.setApproach((String)this.tblLoanData.getRow(a).getCell("name").getValue());
					afmEntryInfo.setIsFinish(((Boolean)this.tblLoanData.getRow(a).getCell("isFinish").getValue()).booleanValue());
					afmEntryInfo.setActualFinishDate((Date)this.tblLoanData.getRow(a).getCell("finishDate").getValue());
					afmEntryInfo.setRemark((String)this.tblLoanData.getRow(a).getCell("remark").getValue());
					afmEntryCol.add(afmEntryInfo);
				}
				loanInfo.getAFMortgaged().addCollection(afmEntryCol);
			}
			
			roomLoanList.add(loanInfo);
		}
		
		RoomLoanFactory.getRemoteInstance().batchSave(roomLoanList, valueMap);
		this.setMessageText("批量按揭管理 " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Save_OK"));
		this.showMessage();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		actionSave_actionPerformed(e);
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		/*FDCClientVerifyHelper.verifyUIControlEmpty(this);
		IRoomSignContract iRoomSign = RoomSignContractFactory.getRemoteInstance();

		java.util.List roomIds = (java.util.List) getUIContext().get("roomIds");
		for (int i = 0, size = roomIds.size(); i < size; i++) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("room.id", roomIds.get(i).toString()));
			filter.getFilterItems().add(new FilterItemInfo("isBlankOut", Boolean.TRUE, CompareType.NOTEQUALS));
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("number");
			view.getSelector().add("signDate");
			view.getSelector().add("onRecordDate");
			view.getSelector().add("contractNumber");
			view.getSelector().add("room.number");

			RoomSignContractCollection roomColl = iRoomSign.getRoomSignContractCollection(view);
			if (roomColl.size() > 0) {
				RoomSignContractInfo contract = roomColl.get(0);
				
		   if(contract.getSignDate() != null)
		   {
			   if (pkProcessLoanDate.getValue() != null
						&& (DateTimeUtils.dayBefore((Date)pkProcessLoanDate.getValue(),contract.getSignDate()))) {
					MsgBox.showInfo(this, contProcessLoanDate.getBoundLabelText()
							+ "不能早于房间 " + contract.getRoom().getNumber()
							+ "  的签约日期！");
					SysUtil.abort();
				}
		   }
				
				
			}
		}*/
		if(this.f7Transactor.getValue() == null){
			FDCMsgBox.showInfo("经办人不能为空!");
			SysUtil.abort();
		}
		else if(this.f7Afmortgaged.getValue() == null){
			FDCMsgBox.showInfo("按揭方案不能为空!");
			SysUtil.abort();
		}
		else if(this.comboCurProcess.getSelectedItem() == null){
			FDCMsgBox.showInfo("当前进程不能为空!");
			SysUtil.abort();
		}
		else if(this.pkProcessLoanDate.getValue() == null){
			FDCMsgBox.showInfo("办理日期不能为空!");
			SysUtil.abort();
		}
		
		if(this.tblRoomInfo.getRowCount() <= 0){
			FDCMsgBox.showInfo("按揭办理房间不能为空!");
			SysUtil.abort();
		}
		for(int i=0; i<this.tblRoomInfo.getRowCount(); i++){
			IRow row = this.tblRoomInfo.getRow(i);
			if(row.getCell("loanBank").getValue()==null){
				FDCMsgBox.showInfo(this, "按揭银行不能为空！");
				this.tblRoomInfo.getEditManager().editCellAt(row.getRowIndex(), this.tblRoomInfo.getColumnIndex("loanBank"));
				SysUtil.abort();
			}
			if(row.getCell("loanFixYear").getValue()==null){
				FDCMsgBox.showInfo(this, "按揭年限不能为空！");
				this.tblRoomInfo.getEditManager().editCellAt(row.getRowIndex(), this.tblRoomInfo.getColumnIndex("loanFixYear"));
				SysUtil.abort();
			}
		}
		super.verifyInput(e);
	}
	
	protected void checkBatchLoanData_itemStateChanged(ItemEvent e)
			throws Exception {
		super.checkBatchLoanData_itemStateChanged(e);
		
		if(this.checkBatchLoanData.isSelected()){
			this.tblLoanData.removeRows();
			
			AFMortgagedInfo afmInfo = (AFMortgagedInfo)this.f7Afmortgaged.getValue();
			//填充资料表格
			if(afmInfo.getDataEntrys() != null && !afmInfo.getDataEntrys().isEmpty()){
				AFMortgagedDataEntryCollection afmDataCol = afmInfo.getDataEntrys();
				for(int i=0; i<afmDataCol.size(); i++){
					AFMortgagedDataEntryInfo dataInfo = afmDataCol.get(i);
					IRow row = this.tblLoanData.addRow();
					
					row.getCell("name").setValue(dataInfo.getName());
					row.getCell("isFinish").setValue(Boolean.FALSE);
					row.getCell("remark").setValue(dataInfo.getRemark());
				}
			}
		}
		else{
			this.tblLoanData.removeRows();
		}
	}
	
	protected void f7Afmortgaged_dataChanged(DataChangeEvent e)
			throws Exception {
		super.f7Afmortgaged_dataChanged(e);
		
		Object newObj = e.getNewValue();
		Object oldObj = e.getOldValue();
		
		if (newObj == null && oldObj != null) {
			this.tblLoanData.removeRows();
		} 
		else if (newObj != null && oldObj == null) {
			loadAfmortgagedScheme((AFMortgagedInfo) newObj, this.tblLoanData);
		}
		else if (newObj != null && oldObj != null) {
			// 当选择同一个房间时不做处理
			AFMortgagedInfo newScheme = (AFMortgagedInfo) (newObj);
			AFMortgagedInfo oldScheme = (AFMortgagedInfo) (oldObj);
			if (!newScheme.getId().equals(oldScheme.getId())) {
				loadAfmortgagedScheme((AFMortgagedInfo) newObj, this.tblLoanData);
			}
		}
	}
	
	protected void loadAfmortgagedScheme(AFMortgagedInfo schemeInfo, KDTable dataTable)
			throws EASBizException, BOSException {
		AFMortgagedApproachEntryCollection appCols = schemeInfo.getApproachEntrys();
		AFMortgagedDataEntryCollection dataCols = schemeInfo.getDataEntrys();
		
		if(appCols != null && !appCols.isEmpty()){
			//重组当前进程下拉框选项
			KDComboBox comboBox = (KDComboBox)this.contCurProcess.getBoundEditor();
			comboBox.removeAllItems();
			for(int i=0; i<appCols.size(); i++){
				comboBox.addItem(appCols.get(i).getName());
			}
		}
		
		if (dataTable.getRowCount() > 0) {
			dataTable.removeRows();
		}
		
		//填充资料表格
		if(dataCols != null && !dataCols.isEmpty()){
			for(int i=0; i<dataCols.size(); i++){
				AFMortgagedDataEntryInfo dataInfo = dataCols.get(i);
				IRow row = dataTable.addRow();
				row.getCell("name").setValue(dataInfo.getName());
				row.getCell("isFinish").setValue(Boolean.FALSE);
				row.getCell("remark").setValue(dataInfo.getRemark());
			}
		}
	}
	
	protected void tblRoomInfo_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblRoomInfo_tableClicked(e);
		
		//非批量更新，则根据选中的按揭单据，刷新按揭资料表格数据
		if(e.getClickCount() == 1 && !this.checkBatchLoanData.isSelected()){
			IRow row = this.tblRoomInfo.getRow(e.getRowIndex());
			RoomLoanAFMEntrysCollection entryCol = (RoomLoanAFMEntrysCollection)row.getCell("id").getUserObject();
			
			this.tblLoanData.removeRows();
			if(entryCol!=null && !entryCol.isEmpty()){
				for(int i=0; i<entryCol.size(); i++){
					RoomLoanAFMEntrysInfo afmEntryInfo =  entryCol.get(i);
					if(!afmEntryInfo.isIsAOrB()){  //B为资料
						IRow dataRow = this.tblLoanData.addRow();
						
						//保存按揭表的选中行
						dataRow.setUserObject(new Integer(e.getRowIndex()));
						dataRow.getCell("id").setValue(afmEntryInfo.getId());
						dataRow.getCell("name").setValue(afmEntryInfo.getApproach());
						dataRow.getCell("finishDate").setValue(afmEntryInfo.getActualFinishDate());
						dataRow.getCell("isFinish").setValue(new Boolean(afmEntryInfo.isIsFinish()));
						dataRow.getCell("remark").setValue(afmEntryInfo.getRemark());
					}
				}
			}
		}
	}
	
	protected void tblLoanData_editStopped(KDTEditEvent e) throws Exception {
		super.tblLoanData_editStopped(e);
		IRow currentRow = this.tblLoanData.getRow(e.getRowIndex());
		
		if(e.getColIndex() == this.tblLoanData.getColumnIndex("isFinish")){
			boolean isFinish = ((Boolean)e.getValue()).booleanValue();
			if(isFinish){
				this.tblLoanData.getRow(e.getRowIndex()).getCell("finishDate").setValue(new Date());
			}
			else{
				this.tblLoanData.getRow(e.getRowIndex()).getCell("finishDate").setValue(null);
			}
		}
		//保存资料
		if(currentRow.getUserObject() != null && currentRow.getCell("id").getValue()!=null){
			int roomRowIndex = ((Integer)currentRow.getUserObject()).intValue();
			if(this.tblRoomInfo.getRow(roomRowIndex).getCell("id").getUserObject() != null){
				RoomLoanAFMEntrysCollection entryCol = (RoomLoanAFMEntrysCollection)this.tblRoomInfo.getRow(roomRowIndex)
					.getCell("id").getUserObject();
				for(int i=0; i<entryCol.size(); i++){
					RoomLoanAFMEntrysInfo afmEntryInfo =  entryCol.get(i);
					if(afmEntryInfo.getId().toString().equals(currentRow.getCell("id").getValue().toString())){
						afmEntryInfo.setApproach((String)currentRow.getCell("name").getValue());
						afmEntryInfo.setIsFinish(((Boolean)currentRow.getCell("isFinish").getValue()).booleanValue());
						afmEntryInfo.setActualFinishDate((Date)currentRow.getCell("finishDate").getValue());
						afmEntryInfo.setRemark((String)currentRow.getCell("remark").getValue());
					}
				}
			}
			
		}
	}
	
	protected void btnAddRoom_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddRoom_actionPerformed(e);
		
		this.f7AddRoom.setDataBySelector();
	}
	
	protected void btnDeleteRoom_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnDeleteRoom_actionPerformed(e);
		
		if (this.tblRoomInfo.getRowCount() == 0 || this.tblRoomInfo.getSelectManager().size() == 0) {
            MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
            SysUtil.abort();
        }
		if(MsgBox.showConfirm2(this,"确认要删除该房间吗？") == MsgBox.CANCEL){
			return;
		}
		
		IRow row = KDTableUtil.getSelectedRow(this.tblRoomInfo);
		this.tblRoomInfo.removeRow(row.getRowIndex());
	}
	
	protected void f7AddRoom_dataChanged(DataChangeEvent e) throws Exception {
		super.f7AddRoom_dataChanged(e);
		
		RoomLoanInfo loanInfo = (RoomLoanInfo)e.getNewValue();
		for(int i=0; i<this.tblRoomInfo.getRowCount(); i++){
			RoomLoanInfo rowLoanInfo = (RoomLoanInfo)this.tblRoomInfo.getRow(i).getUserObject();
			if(rowLoanInfo.getId().toString().equals(loanInfo.getId().toString())){
				FDCMsgBox.showWarning("房间已存在，请重新选择");
	            SysUtil.abort();
			}
		}
		//获取房间信息
		RoomInfo room = this.getRoomInfo(loanInfo.getRoom().getId().toString());
		loanInfo.setRoom(room);
		
		IRow newRow = this.tblRoomInfo.addRow();
		newRow.setUserObject(loanInfo);
		
		newRow.getCell("id").setValue(loanInfo.getId());
		newRow.getCell("room").setValue(loanInfo.getRoom().getNumber());
		newRow.getCell("afmNumber").setValue(loanInfo.getNumber());
		
		//用第一个单元来保存按揭的分录，方便更新资料表格的数据
		newRow.getCell("id").setUserObject(loanInfo.getAFMortgaged());
		if(loanInfo.getRoom().getSellState().equals(RoomSellStateEnum.Purchase)
				&& loanInfo.getPurchase() != null){  //认购
			if(loanInfo.getPurchase() != null){
				PurchaseManageInfo purInfo = this.getPurchaseInfo(loanInfo.getPurchase().getId().toString());
				loanInfo.setPurchase(purInfo);
				newRow.getCell("customer").setValue(loanInfo.getPurchase().getCustomerNames());
			}
		}
		else if(loanInfo.getRoom().getSellState().equals(RoomSellStateEnum.Sign)
				&& loanInfo.getSign() != null){  //签约
			SignManageInfo signInfo = this.getSignInfo(loanInfo.getSign().getId().toString());
			loanInfo.setSign(signInfo);
			newRow.getCell("customer").setValue(loanInfo.getSign().getCustomerNames());
			newRow.getCell("contractNumber").setValue(loanInfo.getSign().getBizNumber());
		}
		
		if(loanInfo.getORSOMortgaged() != null){
			AFMortgagedInfo afmInfo = this.getAfmortgaged(loanInfo.getORSOMortgaged().getId().toString());
			loanInfo.setORSOMortgaged(afmInfo);
		}
		
		MoneyDefineInfo mmTypeInfo = this.getMoneyType(loanInfo.getMmType().getId().toString());
		loanInfo.setMmType(mmTypeInfo);
		newRow.getCell("mmType").setValue(loanInfo.getMmType().getName());
		
		newRow.getCell("loanAmount").setValue(loanInfo.getActualLoanAmt());
		if(loanInfo.getLoanBank() != null){
			BankInfo bank = this.getBankInfo(loanInfo.getLoanBank().getId().toString());
			loanInfo.setLoanBank(bank);
			
			newRow.getCell("loanBank").setValue(loanInfo.getLoanBank());
		}
		newRow.getCell("loanFixYear").setValue(new Integer(loanInfo.getLoanFixedYear()));
	}
	
	/**
	 * 初始化房间选择f7控件
	 */
	private void initF7AddRoom() {
		String sellProjectId = (String) getUIContext().get("sellProjectId");
		String mmTypeId = (String) getUIContext().get("mmTypeId");
		EntityViewInfo view = new EntityViewInfo();

		// 过滤条件
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("mmType.id", mmTypeId));
		filter.getFilterItems().add(new FilterItemInfo("room.building.sellProject.id", sellProjectId));
		
		HashSet afmStateSet = new HashSet();
		afmStateSet.add(new Integer(AFMortgagedStateEnum.UNTRANSACT_VALUE));
		afmStateSet.add(new Integer(AFMortgagedStateEnum.TRANSACTING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState", afmStateSet, CompareType.INCLUDE));
		
		view.setFilter(filter);

		String queryInfo = "com.kingdee.eas.fdc.sellhouse.app.NewRoomLoanQuery";
		SHEHelper.initF7(this.f7AddRoom, queryInfo, filter);
	}
	
	private void initF7Afmortgaged() throws EASBizException, BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		String sellProjectId = getUIContext().get("sellProjectId").toString();
		
		//从按揭房间信息表的第一行，得到一个按揭对象，读取款项信息做限制条件
		if(this.tblRoomInfo.getRowCount() > 0){
			IRow row = this.tblRoomInfo.getRow(0);
			RoomLoanInfo loanInfo = (RoomLoanInfo)row.getUserObject();
			if(loanInfo.getMmType().getMoneyType() != null){
				if(loanInfo.getMmType().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
					filter.getFilterItems().add(new FilterItemInfo("mmType", AFMmmTypeEnum.AccFundAmount.getValue()));
				}
				else if(loanInfo.getMmType().getMoneyType().equals(MoneyTypeEnum.LoanAmount)){
					filter.getFilterItems().add(new FilterItemInfo("mmType", AFMmmTypeEnum.LoanAmount.getValue()));
				}
			}
		}
		
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		
		Set id=new HashSet();
		SellProjectInfo info = new SellProjectInfo();
		info.setId(BOSUuid.read(sellProjectId));
		id = SHEManageHelper.getAllParentSellProjectCollection(info,id);
	
		if (id != null && id.size() > 0) {
			filter.getFilterItems().add(
				new FilterItemInfo("project.id",FDCTreeHelper.getStringFromSet(id),
						CompareType.INNER));
		}else{
			filter.getFilterItems().add(
					new FilterItemInfo("project.id", null,
							CompareType.EQUALS));
		}
		
		view.setFilter(filter);

		this.f7Afmortgaged.setEntityViewInfo(view);
	}
	
	private Date getApproachPromiseDate(AFMortgagedApproachEntryCollection afmAppCol, 
			AFMortgagedApproachEntryInfo currentAppInfo, RoomLoanInfo roomLoan){
		if(currentAppInfo.getReferenceTime() == null){
			return null;
		}
		else if(currentAppInfo.getReferenceTime().equals("指定日期")){  //参照日期为指定日期
			return currentAppInfo.getScheduledDate();
		}
		else if(currentAppInfo.getReferenceTime().equals("签约日期")){  //参照日期为签约日期
			if(roomLoan.getSign()==null){
				return null;
			}
			else{
				int mon = currentAppInfo.getIntervalMonth();
				int day = currentAppInfo.getIntervalDays();
				return DateTimeUtils.addDuration(roomLoan.getSign().getBizDate(), 0, mon, day);
			}
		}
		else{
			for(int i=0; i<afmAppCol.size(); i++){
				AFMortgagedApproachEntryInfo appInfo = afmAppCol.get(i);
				if(appInfo.getName().equals(currentAppInfo.getReferenceTime())){
					Date tempDate = getApproachPromiseDate(afmAppCol, appInfo, roomLoan);
					if(tempDate != null){
						//根据间隔月和天计算日期
						int mon = currentAppInfo.getIntervalMonth();
						int day = currentAppInfo.getIntervalDays();
						return DateTimeUtils.addDuration(tempDate, 0, mon, day);
					}
				}
			}
			return null;
		}
	}
	
	private SellProjectInfo getSellProject(String sellProjectId) throws EASBizException, BOSException, UuidException{
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		
		SellProjectInfo sellProject = SellProjectFactory.getRemoteInstance()
			.getSellProjectInfo(new ObjectUuidPK(BOSUuid.read(sellProjectId)), sels);
		
		return sellProject;
	}
	
	private RoomInfo getRoomInfo(String roomId) throws EASBizException, BOSException, UuidException{
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("number");
		sels.add("sellState");
		RoomInfo roomInfo = RoomFactory.getRemoteInstance()
			.getRoomInfo(new ObjectUuidPK(BOSUuid.read(roomId)), sels);
		
		return roomInfo;
	}
	
	private PurchaseManageInfo getPurchaseInfo(String purId) throws EASBizException, BOSException{
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("customerNames");
		
		PurchaseManageInfo purInfo = PurchaseManageFactory.getRemoteInstance()
			.getPurchaseManageInfo(new ObjectUuidPK(purId), selCol);
		
		return purInfo;
	}
	
	private SignManageInfo getSignInfo(String signId) throws EASBizException, BOSException{
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("customerNames");
		selCol.add("bizNumber");
		selCol.add("srcId");
		
		SignManageInfo signInfo = SignManageFactory.getRemoteInstance()
			.getSignManageInfo(new ObjectUuidPK(signId), selCol);
		
		return signInfo;
	}
	
	private BankInfo getBankInfo(String bankId) throws EASBizException, BOSException{
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("*");
		
		BankInfo bankInfo = BankFactory.getRemoteInstance()
			.getBankInfo(new ObjectUuidPK(bankId), selCol);
		
		return bankInfo;
	}
	
	private AFMortgagedInfo getAfmortgaged(String afmId) throws EASBizException, BOSException{
		if(null == afmId){
			return null;
		}
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("*");
		selCol.add(new SelectorItemInfo("ApproachEntrys.*"));
		selCol.add(new SelectorItemInfo("DataEntrys.*"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", afmId));

		AFMortgagedInfo afmInfo = AFMortgagedFactory.getRemoteInstance().getAFMortgagedInfo(new ObjectUuidPK(afmId), selCol);
		
		return afmInfo;
	}
	
	private MoneyDefineInfo getMoneyType(String mmTypeId) throws EASBizException, BOSException{
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", mmTypeId));

		MoneyDefineInfo afmInfo = MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(mmTypeId), selCol);
		
		return afmInfo;
	}
}