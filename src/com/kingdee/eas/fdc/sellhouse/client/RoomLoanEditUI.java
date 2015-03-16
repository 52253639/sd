/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
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
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.assistant.BankFactory;
import com.kingdee.eas.basedata.assistant.BankInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AFMmmTypeEnum;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedDataEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedDataEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedFactory;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.IAFMortgaged;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.LoanNotarizeEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanAFMEntrysCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanAFMEntrysInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanFactory;
import com.kingdee.eas.fdc.sellhouse.RoomLoanInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class RoomLoanEditUI extends AbstractRoomLoanEditUI {
	private static final Logger logger = CoreUIObject.getLogger(RoomLoanEditUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	
	/**
	 * 是否正在初始化
	 */
	private boolean isOnload = true;
	
	/**
	 * 默认按揭方案，用途暂时未知	
	 */
	private AFMortgagedInfo afmInfo = null;

	public RoomLoanEditUI() throws Exception {
		super();
	}

	public void storeFields() {
		if (txtLoanFixedYear.getIntegerValue() == null)
			editData.setLoanFixedYear(new RoomLoanInfo().getLoanFixedYear());
		if ("toDo".equals(getUIContext().get("toDo"))) {
			this.editData.setIsEditData(isEditDataToDo());
		} else {
			this.editData.setIsEditData(isEditDataDone());
		}
		super.storeFields();
	}

	protected IObjectValue createNewData() {
		RoomLoanInfo objectValue = new RoomLoanInfo();
		String roomId = getUIContext().get("roomId").toString();
		
		if(this.editData.getPurchase() != null){  //认购
			PurchaseManageInfo purchase = this.editData.getPurchase();
			objectValue.setRoom(purchase.getRoom());
			objectValue.setLoanData(purchase.getPayType().getLoanLoanData());
			objectValue.setPurchase(purchase);
			String moneyD = "";
			if (getUIContext().containsKey("mmTypeId")) {  //款项id
				moneyD = getUIContext().get("mmTypeId").toString();
			}
			for (int i = 0; i < purchase.getPurPayListEntry().size(); i++) {  //设置实办按揭金额
				if (purchase.getPurPayListEntry().get(i).getMoneyDefine().getId().toString().equals(moneyD)) {
					objectValue.setActualLoanAmt(purchase.getPurPayListEntry().get(i).getActRevAmount());
				}
			}
			if (purchase.getPayType().getAfLoanData() != null){  //读取付款方案中的按揭资料
				objectValue.setFundBank(purchase.getPayType().getAfLoanData());
			}
			else {
				/*txtFundFixedYear.setEnabled(false);
				txtFundAmt.setEnabled(false);
				txtFundLawyer.setEnabled(false);
				pkFundGrantDate.setEnabled(true);
				pkFundProcessDate.setEnabled(false);
				prmtFundTransactor.setEnabled(false);
				contFundTransactor.setEnabled(false);*/
			}
		}
		else if(this.editData.getSign() != null){  //签约
			SignManageInfo sign = this.editData.getSign();
			objectValue.setRoom(sign.getRoom());
			objectValue.setLoanData(sign.getPayType().getLoanLoanData());
			objectValue.setSign(sign);
			String moneyD = "";
			if (getUIContext().containsKey("mmTypeId")) {  //款项id
				moneyD = getUIContext().get("mmTypeId").toString();
			}
			for (int i = 0; i < sign.getSignPayListEntry().size(); i++) {  //设置实办按揭金额
				if (sign.getSignPayListEntry().get(i).getMoneyDefine().getId().toString().equals(moneyD)) {
					objectValue.setActualLoanAmt(sign.getSignPayListEntry().get(i).getActRevAmount());
				}
			}
			if (sign.getPayType().getAfLoanData() != null){  //读取付款方案中的按揭资料
				objectValue.setFundBank(sign.getPayType().getAfLoanData());
			}
			else {
				/*txtFundFixedYear.setEnabled(false);
				txtFundAmt.setEnabled(false);
				txtFundLawyer.setEnabled(false);
				pkFundGrantDate.setEnabled(true);
				pkFundProcessDate.setEnabled(false);
				prmtFundTransactor.setEnabled(false);
				contFundTransactor.setEnabled(false);*/
			}
		}
		
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setTransactor(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setFundTransactor(SysContext.getSysContext().getCurrentUserInfo());

		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		objectValue.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		objectValue.setBookedDate(new Date());
		objectValue.setLoanIsNotarize(LoanNotarizeEnum.notNotarize);
		String biz = getUIContext().get("mmTypeId").toString();
		try {
			MoneyDefineInfo moneyInfo = MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(biz));
			if (moneyInfo != null) {
				objectValue.setMmType(moneyInfo);
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomLoanFactory.getRemoteInstance();
	}

	public void loadFields() {
		super.loadFields();
		String roomId = getUIContext().get("roomId").toString();
		UIContext uiContextRoom = new UIContext(ui);
		uiContextRoom.put(UIContext.ID, roomId);
		
		//设置房间基础信息
		CoreUIObject plUI = null;
		try {
			plUI = (CoreUIObject) UIFactoryHelper.initUIObject(RoomBizInfoUI.class.getName(), uiContextRoom, null, "VIEW");
		} catch (UIException e) {
			logger.error(e);
		}
		sclPanel.setViewportView(plUI);
		sclPanel.setKeyBoardControl(true);
		
		if (editData == null || new Integer(editData.getLoanFixedYear()) == null || getOprtState().equals(STATUS_ADDNEW))
			txtLoanFixedYear.setValue(null);
		/*if (editData == null || new Integer(editData.getFundFixedYear()) == null || getOprtState().equals(STATUS_ADDNEW))
			txtFundFixedYear.setValue(null);*/
		if (editData != null) {
			if(editData.getORSOMortgaged() != null){
				this.getLoanAFMortgaged(editData.getORSOMortgaged().getId().toString());
			}
			if (editData.getApproach() != null) {
				if (editData.getApproach().equals("办理完成")) {
					kDCheckBox1.setSelected(true);
				}
			}
			else if(editData.getActualFinishDate() != null){
				kDCheckBox1.setSelected(true);
			}
		}

		loadLoanAndAccumulationFundAmount(this.editData);
		
		if(!this.kDCheckBox1.isSelected()){
			this.pkProcessLoanDate.setValue(null);
		}
		
		
	}

	/**
	 * 重新取按揭单据的按揭方案
	 * @param AfmId
	 */
	private void getLoanAFMortgaged(String AfmId){
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add(new SelectorItemInfo("ApproachEntrys.*"));
		view.getSelector().add(new SelectorItemInfo("DataEntrys.*"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", editData.getORSOMortgaged().getId()));

		view.setFilter(filter);
		try {
			AFMortgagedInfo afmInfo = AFMortgagedFactory.getRemoteInstance().getAFMortgagedInfo(new ObjectUuidPK(editData.getORSOMortgaged().getId().toString())); 
			editData.setORSOMortgaged(afmInfo);
		} catch(BOSException e){
			logger.error(e);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据款项和认购单或签约单上的按揭公积金金额，设置实际按揭金额
	 * @param info
	 */
	private void loadLoanAndAccumulationFundAmount(RoomLoanInfo info) {
		if (info.getPurchase() == null && info.getSign() == null) {
			return;
		}
		if (info.getPurchase() != null) {  //认购单
			PurchaseManageInfo purchaseInfo = info.getPurchase();
			if (info.getMmType() != null && info.getMmType().getMoneyType() != null) {
				if (info.getMmType().getMoneyType().equals(MoneyTypeEnum.LoanAmount)) {  //按揭
					if (purchaseInfo.getLoanAmount() != null) {
						this.txtActualLoanAmt.setValue(purchaseInfo.getLoanAmount());
					}

				} else if (info.getMmType().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)) {  //公积金
					if (purchaseInfo.getAccFundAmount() != null) {
						this.txtActualLoanAmt.setValue(purchaseInfo.getAccFundAmount());
					}
				}
			}
		}
		else if(info.getSign() != null){  //签约单
			SignManageInfo signInfo = info.getSign();
			if (info.getMmType() != null && info.getMmType().getMoneyType() != null) {
				if (info.getMmType().getMoneyType().equals(MoneyTypeEnum.LoanAmount)) {  //按揭
					if (signInfo.getLoanAmount() != null) {
						this.txtActualLoanAmt.setValue(signInfo.getLoanAmount());
					}
				} else if (info.getMmType().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)) {  //公积金
					if (signInfo.getAccFundAmount() != null) {
						this.txtActualLoanAmt.setValue(signInfo.getAccFundAmount());
					}
				}
			}			
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew, actionSave, actionCopy, actionPre, actionNext, 
			actionFirst, actionLast, actionCancel, actionCancelCancel, actionRemove, actionPrint, actionPrintPreview }, false);
		menuView.setVisible(false);
		menuBiz.setVisible(false);
		menuSubmitOption.setVisible(false);

		this.f7LoanBank.setEditable(false);
		this.f7Afmortgaged.setEditable(false);

		handleCodingRule();

		if("VIEW".equals(this.oprtState)){
			this.btnAddData.setEnabled(false);
			this.btnRemoveData.setEnabled(false);
		}
		else{
			this.btnAddData.setEnabled(true);
			this.btnRemoveData.setEnabled(true);
		}
		
		if (!FDCSysContext.getInstance().checkIsSHEOrg()) {
			actionEdit.setEnabled(false);
		}
//		pkFundGrantDate.setEnabled(true);
		
//		getAFMortgaged();
		
		this.afmInfo = this.editData.getORSOMortgaged();
		this.f7Afmortgaged.setDataNoNotify(this.editData.getORSOMortgaged());
		
		initEntrys();
		
		this.initF7Afmortgaged();

		kDTabbedPane1_stateChanged(null);
		
		//初始化完毕，按揭方案变化事件可执行清空操作
		this.isOnload = false;
		
		this.actionAttachment.setVisible(true);
	}

	/**
	 * 按揭银行
	 * @param e
	 * @throws Exception
	 */
	protected void f7LoanBank_dataChanged(DataChangeEvent e) throws Exception {
		super.f7LoanBank_dataChanged(e);
		
	}
	
	protected void f7Afmortgaged_dataChanged(DataChangeEvent e)
			throws Exception {
		Object newObj = e.getNewValue();
		Object oldObj = e.getOldValue();

		//正在初始化数据，不清空editData的分录
		if(isOnload){
			return;
		}
		
		if (newObj == null && oldObj != null) {
			this.kDTable1.removeRows();
			this.kDTable2.removeRows();
			this.editData.getAFMortgaged().clear();
			this.kDCheckBox1.setSelected(false);
			this.pkProcessLoanDate.setValue(null);
		} else if (newObj != null && oldObj == null) {
			this.loadScheme();
		} else if (newObj != null && oldObj != null) {
			// 当选择同一个房间时不做处理
			AFMortgagedInfo newScheme = (AFMortgagedInfo) newObj;
			AFMortgagedInfo oldScheme = (AFMortgagedInfo) oldObj;
			if (!newScheme.getId().equals(oldScheme.getId())) {
				this.loadScheme();
			}
		}
	}
	
	private void loadScheme(){
		this.kDTable1.removeRows();
		this.kDTable2.removeRows();
		this.editData.getAFMortgaged().clear();
		this.kDCheckBox1.setSelected(false);
		this.pkProcessLoanDate.setValue(null);
		
		AFMortgagedInfo afmInfo = (AFMortgagedInfo)this.f7Afmortgaged.getValue();
		if(afmInfo != null){
			//填充进程页签
			if(afmInfo.getApproachEntrys() != null && !afmInfo.getApproachEntrys().isEmpty()){
				AFMortgagedApproachEntryCollection afmAppCol = afmInfo.getApproachEntrys();
				for(int i=0; i<afmAppCol.size(); i++){
					AFMortgagedApproachEntryInfo appInfo = afmAppCol.get(i);
					IRow row = this.kDTable1.addRow();
					row.getCell("aApproach").setValue(appInfo.getName());
					row.getCell("aFinish").setValue(Boolean.FALSE);
					row.getCell("aDate").setValue(new Date());
					row.getCell("proFinishDate").setValue(getApproachPromiseDate(afmAppCol, appInfo));
					row.getCell("aRemark").setValue(appInfo.getRemark());
					row.getCell("isFinishFlag").setValue(new Boolean(appInfo.isIsFinish()));
					row.getCell("aOrb").setValue(Boolean.TRUE);
				}
			}
			//填充资料页签
			if(afmInfo.getDataEntrys() != null && !afmInfo.getDataEntrys().isEmpty()){
				AFMortgagedDataEntryCollection afmDataCol = afmInfo.getDataEntrys();
				for(int i=0; i<afmDataCol.size(); i++){
					AFMortgagedDataEntryInfo dataInfo = afmDataCol.get(i);
					IRow row = this.kDTable2.addRow();
					row.getCell("bApproach").setValue(dataInfo.getName());
					row.getCell("bDate").setValue(new Date());
					row.getCell("bFinish").setValue(Boolean.FALSE);
					row.getCell("bRemark").setValue(dataInfo.getRemark());
					row.getCell("aOrb").setValue(Boolean.FALSE);
				}
			}
		}
	}
	
	/**
	 * 初始化按揭方案
	 * @throws EASBizException
	 * @throws BOSException
	 * @author liang_ren969
	 */
	private void initF7Afmortgaged() throws EASBizException, BOSException{
		
		if(this.editData.getSign()==null) return;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		if(this.editData.getMmType().getMoneyType() != null){
			if(this.editData.getMmType().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
				filter.getFilterItems().add(new FilterItemInfo("mmType", AFMmmTypeEnum.AccFundAmount.getValue()));
			}
			else if(this.editData.getMmType().getMoneyType().equals(MoneyTypeEnum.LoanAmount)){
				filter.getFilterItems().add(new FilterItemInfo("mmType", AFMmmTypeEnum.LoanAmount.getValue()));
			}
		}
		
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		
		Set id=new HashSet();
		
		id = SHEManageHelper.getAllParentSellProjectCollection(this.editData.getSign().getSellProject(),id);
	
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
			AFMortgagedApproachEntryInfo currentAppInfo){
		if(currentAppInfo.getReferenceTime() == null){
			return null;
		}
		else if(currentAppInfo.getReferenceTime().equals("指定日期")){  //参照日期为指定日期
			return currentAppInfo.getScheduledDate();
		}
		else if(currentAppInfo.getReferenceTime().equals("签约日期")){  //参照日期为签约日期
			if(this.editData.getSign()==null){
				return null;
			}
			else{
				int mon = currentAppInfo.getIntervalMonth();
				int day = currentAppInfo.getIntervalDays();
				return DateTimeUtils.addDuration(this.editData.getSign().getBizDate(), 0, mon, day);
			}
		}
		else{
			for(int i=0; i<afmAppCol.size(); i++){
				AFMortgagedApproachEntryInfo appInfo = afmAppCol.get(i);
				if(appInfo.getName().equals(currentAppInfo.getReferenceTime())){
					Date tempDate = getApproachPromiseDate(afmAppCol, appInfo);
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

	/**
	 * 设置默认按揭方案：获取指定项目下指定款项的一条按揭方案
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public AFMortgagedInfo getAFMortgaged() throws BOSException,
			EASBizException {
		String roomID = getUIContext().get("roomId").toString();
		
		//获取项目
		IRoom ir = RoomFactory.getRemoteInstance();
		SelectorItemCollection roomSic = new SelectorItemCollection();
		roomSic.add("id");
		roomSic.add("name");
		roomSic.add("number");
		roomSic.add("building.id");
		roomSic.add("building.name");
		roomSic.add("building.number");
		roomSic.add("building.sellProject.id");
		roomSic.add("building.sellProject.name");
		roomSic.add("building.sellProject.number");
		RoomInfo room = ir.getRoomInfo(new ObjectUuidPK(roomID), roomSic);
		SellProjectInfo spInfo = room.getBuilding().getSellProject();
		
		//获取按揭方案
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", spInfo.getId()));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", "1"));

		if (getUIContext().containsKey("mmTypeId")) {
			String biz = getUIContext().get("mmTypeId").toString();
			MoneyDefineInfo moneyInfo = MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(biz));
			if (moneyInfo.getMoneyType().getValue().equals(AFMmmTypeEnum.LOANAMOUNT_VALUE)) {  //按揭
				filter.getFilterItems().add(new FilterItemInfo("mmType", AFMmmTypeEnum.LOANAMOUNT_VALUE));
			} 
			else {  //公积金
				filter.getFilterItems().add(new FilterItemInfo("mmType", AFMmmTypeEnum.ACCFUNDAMOUNT_VALUE));
			}
		}

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("isEnabled");
		sic.add("ApproachEntrys.id");
		sic.add("ApproachEntrys.name");
		sic.add("ApproachEntrys.number");
		sic.add("ApproachEntrys.remark");
		sic.add("DataEntrys.id");
		sic.add("DataEntrys.name");
		sic.add("DataEntrys.number");
		sic.add("DataEntrys.remark");
		view.setFilter(filter);
		view.setSelector(sic);

		IAFMortgaged iafm = AFMortgagedFactory.getRemoteInstance();
		afmInfo = iafm.getAFMortgagedCollection(view).get(0);
		return afmInfo;
	}

	public void initEntrys() throws BOSException, EASBizException {
		kDTable1.checkParsed();
		kDTable1.removeRows(false);
		kDTable2.checkParsed();
		kDTable2.removeRows(false);
		
		//初始化表格列编辑属性
		kDTable1.getColumn("aApproach").getStyleAttributes().setLocked(true);
		kDTable1.getColumn("transactor").getStyleAttributes().setLocked(true);
		kDTable1.getColumn("actFinishDate").getStyleAttributes().setLocked(true);
		
		KDDatePicker datePicker = new KDDatePicker();
		kDTable1.getColumn("proFinishDate").setEditor(new KDTDefaultCellEditor(datePicker));
		
		KDBizPromptBox transactorBox = new KDBizPromptBox();
		transactorBox.setEditable(false);
		transactorBox.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
		kDTable1.getColumn("transactor").setEditor(new KDTDefaultCellEditor(transactorBox));
		
		KDTextField txtApproach = new KDTextField();
		txtApproach.setMaxLength(100);
		kDTable2.getColumn("bApproach").setEditor(new KDTDefaultCellEditor(txtApproach));

		
		/**
		 * 限制按揭资料中说明字段的大小,防止报数据库字段超长的错误
		 */
		KDTextField txtARemark = new KDTextField();
		txtARemark.setMaxLength(255);
		kDTable1.getColumn("aRemark").setEditor(new KDTDefaultCellEditor(txtARemark));
		
		KDTextField txtRemark = new KDTextField();
		txtRemark.setMaxLength(255);
		kDTable2.getColumn("bRemark").setEditor(new KDTDefaultCellEditor(txtRemark));
		
		//根据实体是否已选择进程和资料，进行展示
		if (this.editData.getAFMortgaged() == null || editData.getAFMortgaged().size() == 0) {  //按揭实体尚未选择进程和资料
			/*try {
				if (afmInfo != null) {  //默认按揭方案
					for (int i = 0; i < afmInfo.getApproachEntrys().size(); i++) {
						AFMortgagedApproachEntryInfo aae = afmInfo.getApproachEntrys().get(i);
						IRow row = kDTable1.addRow();
						row.getCell("aApproach").setValue(aae.getName());
						row.getCell("aDate").setValue(new Date());
						row.getCell("proFinishDate").setValue(getApproachPromiseDate(afmInfo.getApproachEntrys(), aae));
						row.getCell("aFinish").setValue(Boolean.FALSE);
						row.getCell("transactor").setValue(SysContext.getSysContext().getCurrentUserInfo());
						row.getCell("aRemark").setValue(aae.getRemark());
						row.getCell("aOrb").setValue(Boolean.TRUE);
					}
					for (int i = 0; i < afmInfo.getDataEntrys().size(); i++) {
						AFMortgagedDataEntryInfo ade = afmInfo.getDataEntrys().get(i);
						IRow row = kDTable2.addRow();
						row.getCell("bDate").setValue(new Date());
						row.getCell("bApproach").setValue(ade.getName());
						row.getCell("bFinish").setValue(Boolean.FALSE);
						row.getCell("bRemark").setValue(ade.getRemark());
						row.getCell("aOrb").setValue(Boolean.FALSE);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		} else {  //按揭实体存在按揭进程和资料
			for (int i = 0; i < this.editData.getAFMortgaged().size(); i++) {
				RoomLoanAFMEntrysInfo rleInfo = this.editData.getAFMortgaged().get(i);
				if (rleInfo.isIsAOrB()) {  //A则为进程
					IRow row = kDTable1.addRow();
					row.getCell("id").setValue(rleInfo.getId());
					row.getCell("aDate").setValue(rleInfo.getDate());
					row.getCell("aApproach").setValue(rleInfo.getApproach());
					row.getCell("proFinishDate").setValue(rleInfo.getPromiseFinishDate());
					row.getCell("aFinish").setValue(Boolean.valueOf(rleInfo.isIsFinish()));
					row.getCell("actFinishDate").setValue(rleInfo.getActualFinishDate());
					row.getCell("transactor").setValue(rleInfo.getTransactor());
					row.getCell("aRemark").setValue(rleInfo.getRemark());
					row.getCell("isFinishFlag").setValue(Boolean.valueOf(rleInfo.isIsFinishFlag()));
					row.getCell("aOrb").setValue(Boolean.valueOf(rleInfo.isIsAOrB()));
					
					if(rleInfo.isIsFinish()){
						row.getCell("actFinishDate").getStyleAttributes().setLocked(false);
						row.getCell("transactor").getStyleAttributes().setLocked(false);
					}
				} else {  //B为资料
					IRow row = kDTable2.addRow();
					row.getCell("id").setValue(rleInfo.getId());
					row.getCell("bDate").setValue(rleInfo.getActualFinishDate());
					row.getCell("bApproach").setValue(rleInfo.getApproach());
					row.getCell("bFinish").setValue(Boolean.valueOf(rleInfo.isIsFinish()));
					row.getCell("bRemark").setValue(rleInfo.getRemark());
					row.getCell("aOrb").setValue(Boolean.valueOf(rleInfo.isIsAOrB()));
				}
			}
		}
	}

    protected void btnAddData_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddData_actionPerformed(e);
		IRow row = kDTable2.addRow(kDTable2.getRowCount());
		row.getCell("bDate").setValue(new Date());
		row.getCell("bFinish").setValue(Boolean.FALSE);
		row.getCell("aOrb").setValue(Boolean.FALSE);
	}
	
	protected void btnRemoveData_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnRemoveData_actionPerformed(e);
		checkSelected();
		if(kDTable2.getRowCount() == 0){
			MsgBox.showInfo("已没有资料可删除！");
			return;
		}
		IRow row = KDTableUtil.getSelectedRow(kDTable2);
		if(((Boolean)row.getCell("bFinish").getValue()).booleanValue()){
			if(MsgBox.showConfirm2(this,"该条资料已完成，您确认要去掉该条资料吗？") == MsgBox.CANCEL){
				return;
			}
		}else{
			if(MsgBox.showConfirm2(this,"你确认要去掉该条资料吗？") == MsgBox.CANCEL){
				return;
			}
		}
		kDTable2.removeRow(row.getRowIndex());
	}
	public void checkSelected() {
		if (kDTable2.getRowCount() == 0 || kDTable2.getSelectManager().size() == 0) {
			MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			SysUtil.abort();
		}
	}

	protected void kDTabbedPane1_stateChanged(ChangeEvent e) throws Exception {
		super.kDTabbedPane1_stateChanged(e);
		if (kDTabbedPane1.getSelectedIndex() == 1 && !getOprtState().equals("VIEW")) {
			btnAddData.setEnabled(true);
			btnRemoveData.setEnabled(true);
		}
		else {
			btnAddData.setEnabled(false);
			btnRemoveData.setEnabled(false);
		}
	}

	/** 
	 * 进程页签
	 */
	protected void kDTable1_editStopped(KDTEditEvent e) throws Exception {
		IRow row = KDTableUtil.getSelectedRow(kDTable1);
		
		if (row != null && "aFinish".equals(this.kDTable1.getColumnKey(e.getColIndex()))) {
			Object af = row.getCell("aFinish").getValue();
			if (af.equals(Boolean.TRUE)) {
				row.getCell("aDate").setValue(new Date());
				row.getCell("actFinishDate").setValue(new Date());
				row.getCell("transactor").setValue(SysContext.getSysContext().getCurrentUserInfo());
				row.getCell("actFinishDate").getStyleAttributes().setLocked(false);
				row.getCell("transactor").getStyleAttributes().setLocked(false);
				
				//当前进程为办理完成进程，则勾选是否完成时，需将单据设为办理完成
				if(row.getCell("isFinishFlag").getValue()!=null && ((Boolean)row.getCell("isFinishFlag").getValue()).booleanValue()){
					kDCheckBox1.setSelected(true);
				}
			} else {
				row.getCell("aDate").setValue(null);
				row.getCell("actFinishDate").setValue(null);
				row.getCell("transactor").setValue(null);
				
				row.getCell("actFinishDate").getStyleAttributes().setLocked(true);
				row.getCell("transactor").getStyleAttributes().setLocked(true);
				
				//当前进程为办理完成进程，则取消勾选是否完成时，需将单据设为未完成
				if(row.getCell("isFinishFlag").getValue()!=null && ((Boolean)row.getCell("isFinishFlag").getValue()).booleanValue()){
					kDCheckBox1.setSelected(false);
				}
			}
		}
	}

	/** 
	 * 资料页签
	 */
	protected void kDTable2_editStopped(KDTEditEvent e) throws Exception {
		IRow row = KDTableUtil.getSelectedRow(kDTable2);
		if (row != null) {
			Object af = row.getCell("bFinish").getValue();
			if (af.equals(Boolean.TRUE)) {
				if(row.getCell("bDate").getValue() == null){
					row.getCell("bDate").setValue(new Date());
				}
			}
			else{
				row.getCell("bDate").setValue(null);
			}
		}
	}

	protected void kDTable2_tableClicked(KDTMouseEvent e) throws Exception {
		// IRow row = KDTableUtil.getSelectedRow(kDTable2);
		// if(((Boolean)row.getCell("bFinish").getValue()).booleanValue()){
		// this.btnRemoveData.setEnabled(false);
		// }else{
		// this.btnRemoveData.setEnabled(true);
		// }
	}

	/**
	 * 办理完成勾选框
	 */
	protected void kDCheckBox1_stateChanged(ChangeEvent e) throws Exception {
		if (kDCheckBox1.isSelected()) {
			/*for (int i = 0; i < kDTable1.getRowCount(); i++) {
				if (kDTable1.getRow(i).getCell("aFinish").getValue() != null) {
					String isFinish = kDTable1.getRow(i).getCell("aFinish")
							.getValue().toString();
					if (isFinish.equals("0") || isFinish.equals("false")) {
						kDCheckBox1.setSelected(false);
						MsgBox.showWarning(this, "步骤未全部完成，无法选择办理完成！");
						return;
					}
				}

			}
			for (int i = 0; i < kDTable2.getRowCount(); i++) {
				if (kDTable2.getRow(i).getCell("bFinish").getValue() != null) {
					String isFinish = kDTable2.getRow(i).getCell("bFinish")
							.getValue().toString();
					if (isFinish.equals("0") || isFinish.equals("false")) {
						kDCheckBox1.setSelected(false);
						MsgBox.showWarning(this, "资料未全部完成，无法选择办理完成！");
						return;
					}
				}
			}*/
			// 打钩了则把当前日期写到办理日期上
			Date date = new Date();
			this.editData.setProcessLoanDate(date);
			this.editData.setActualFinishDate(date);
			this.pkProcessLoanDate.setValue(date);
			this.pkProcessLoanDate.setEnabled(true);
		} else {
			this.editData.setActualFinishDate(null);
			this.editData.setProcessLoanDate(null);
			this.pkProcessLoanDate.setValue(null);
			this.pkProcessLoanDate.setEnabled(false);
		}
	}

	public void onShow() throws Exception {
		super.onShow();
		FDCClientHelper.formatKDFormattedTextField(new KDFormattedTextField[] {txtActualLoanAmt });
		FDCClientHelper.formatKDFormattedINT(new KDFormattedTextField[] {txtLoanFixedYear });
		txtLoanFixedYear.setMaximumValue(new Integer("100"));
		txtActualLoanAmt.setMaximumValue(new BigDecimal("1000000000"));
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyUIControlEmpty(this);
		
		if(this.kDTextArea1.getText()!=null && this.kDTextArea1.getText().length()>80){
			FDCMsgBox.showInfo(this, "说明长度不能超过80个字符");
			this.kDTextArea1.requestFocus();
			SysUtil.abort();
		}
		if (kDTable1.getRowCount() == 0) {
			MsgBox.showInfo(this, "本项目未启用公积金/按揭方案，无法保存！");
			SysUtil.abort();
		}
		//检查进程分录
		for(int i=0; i<kDTable1.getRowCount(); i++){
			IRow row = kDTable1.getRow(i);
			if(row.getCell("proFinishDate").getValue()==null){
				FDCMsgBox.showInfo(this, "请填写第" + (i+1) + "行进程的应完成日期");
				SysUtil.abort();
			}
			if(((Boolean)row.getCell("aFinish").getValue()).booleanValue() && row.getCell("transactor").getValue() == null){
				FDCMsgBox.showInfo(this, "请填写第" + (i+1) + "行进程的经办人");
				SysUtil.abort();
			}
		}
		
		for (int i = 0; i < kDTable2.getRowCount(); i++) {
			if (kDTable2.getRow(i).getCell("bApproach").getValue() == null) {
				MsgBox.showInfo(this, "请填写完资料！");
				abort();
			}
		}
		if (this.kDCheckBox1.isSelected()) {
			if (this.pkProcessLoanDate.getValue() == null) {
				FDCMsgBox.showInfo("处理已完成，请填写处理完成日期！");
				abort();
			}
		}
		super.verifyInput(e);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selector = super.getSelectors();
		selector.add(new SelectorItemInfo("*"));
		selector.add(new SelectorItemInfo("room.*"));
		selector.add(new SelectorItemInfo("sign.*"));
		selector.add(new SelectorItemInfo("sign.sellProject.id"));
		selector.add(new SelectorItemInfo("sign.sellProject.name"));
		selector.add(new SelectorItemInfo("sign.sellProject.number"));
		selector.add(new SelectorItemInfo("approach"));
		selector.add(new SelectorItemInfo("aFMortgaged.*"));
		selector.add(new SelectorItemInfo("aFMortgaged.transactor.*"));

		return selector;
	}

	/**
	 * 处理编码规则
	 * 
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException,
			EASBizException {

		String currentOrgId = FDCClientHelper.getCurrentOrgId();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();

		if (STATUS_ADDNEW.equals(this.oprtState) && iCodingRuleManager.isExist(editData, currentOrgId)) {
			// EditUI提供了方法，但没有调用，在onload后调用，以覆盖抽象类loadfields里面的调用（该调用没有处理断号选择）
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData, currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(editData, currentOrgId);
			} else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData,
						currentOrgId)) { //此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// 启用了断号支持功能,同时启用了用户选择断号功能
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						//要判断是否存在断号,是则弹出,否则不弹///////////////////////////////////
						// ///////
						Object object = null;
						if (iCodingRuleManager
								.isDHExist(editData, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				txtNumber.setText(number);
			}

			setNumberTextEnabled();
		}
	}

	/**
	 * getNumberByCodingRule只提供了获取编码的功能，没有提供设置到控件的功能，实现此方法将根据编码规则的"是否新增显示"
	 * 属性设置编码到控件
	 */
	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);

		txtNumber.setText(number);

	}

	protected void setNumberTextEnabled() {

		if (txtNumber != null) {
			// /CostCenterOrgUnitInfo currentCostUnit =
			// SysContext.getSysContext()
			// .getCurrentCostUnit();
			String currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (currentOrgId != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(editData, currentOrgId);

				txtNumber.setEnabled(isAllowModify);
				txtNumber.setEditable(isAllowModify);
				txtNumber.setRequired(isAllowModify);
			}
		}
	}

	/**
	 * 检查单据状态
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public boolean checkPurState() throws EASBizException, BOSException {
		/*if (getOprtState().equals(this.STATUS_ADDNEW)) {
			String purchaseID = getUIContext().get("purchaseID").toString();
			PurchaseInfo purInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(purchaseID));
			if (purInfo != null) {
				if (!purInfo.getPurchaseState().equals(
						PurchaseStateEnum.PurchaseAudit)) {
					MsgBox.showWarning(this, "认购单未审批，可能处于认购变更流程，无法新增按揭！请审批变更单后再来做按揭!");
					return false;
				}
			}
		}*/
		return true;
	}

	//认购单检验需修改，待改
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getAFMortgagedState()!=null 
				&& (this.editData.getAFMortgagedState().equals(AFMortgagedStateEnum.STOPTRANSACT)
				|| this.editData.getAFMortgagedState().equals(AFMortgagedStateEnum.BANKFUND))){
			FDCMsgBox.showInfo("当前状态下的单据不能修改");
			SysUtil.abort();
		}
		
		this.f7Afmortgaged.setDataNoNotify(this.editData.getORSOMortgaged());
		super.actionEdit_actionPerformed(e);
		
		this.onLoad();
	}

	//认购单检验需修改，待改
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (checkPurState()) {
			setOprtState(STATUS_EDIT);
			
			//设置当前步骤和按揭单据状态
			intercalateApproach();
			
			if (afmInfo != null && getUIContext().containsKey("toDo")) {
				this.editData.setORSOMortgaged(afmInfo);
			}
			if (this.editData.getRoom() == null) {
				String roomID = getUIContext().get("roomId").toString();
				IRoom ir = RoomFactory.getRemoteInstance();
				RoomInfo room = ir.getRoomInfo(new ObjectUuidPK(roomID));
				editData.setRoom(room);
			}
			//检查资料是否填写
			for (int i = 0; i < kDTable2.getRowCount(); i++) {
				if (kDTable2.getRow(i).getCell("bApproach").getValue() == null) {
					MsgBox.showInfo(this, "请填写完资料！");
					abort();
				}
			}
			
			//保存进程和资料
			saveEntrysToEdit();
			
			super.actionSubmit_actionPerformed(e);
			
			this.btnAddData.setEnabled(false);
			this.btnRemoveData.setEnabled(false);
			
			//设置保存后的界面状态
			if (getOprtState().equals(this.STATUS_ADDNEW)) {
				MsgBox.showWarning(this, "保存成功！");
				this.editData = null;
				this.getUIWindow().close();

			} else if (getOprtState().equals(this.STATUS_EDIT)) {
				setOprtState(STATUS_VIEW);
				lockUIForViewStatus();
			}
		}
	}

	public boolean isModify() {
		return super.isModify();
	}

	/**
	 * 设置当前步骤和按揭单据状态
	 */
	public void intercalateApproach() {
		String ApproachStr = "";

//		BusinessTypeNameEnum bizType = BusinessTypeNameEnum.MORTGAGE;  //默认按揭
		String bizType = SHEManageHelper.MORTGAGE;  //默认按揭
		if(this.editData.getMmType()!=null && this.editData.getMmType().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
			bizType = SHEManageHelper.ACCFUND;
		}
		
		if (kDCheckBox1.isSelected()) {  //办理完成
			ApproachStr = "办理完成";
			if (editData.getAFMortgagedState() == null
					|| editData.getAFMortgagedState().equals(AFMortgagedStateEnum.UNTRANSACT)
					|| editData.getAFMortgagedState().equals(AFMortgagedStateEnum.TRANSACTING)) {
				this.editData.setAFMortgagedState(AFMortgagedStateEnum.TRANSACTED);
			}
			//反写业务总览中对应的服务状态
			SHEManageHelper.updateTransactionOverView(null, this.editData.getRoom(), bizType,
				this.editData.getPromiseDate(), this.editData.getActualFinishDate(), false);
		} 
		else {  //取下一条为完成的进程作为当前进程
			int finishCount = 0;
			for (int i = 0; i < kDTable1.getRowCount(); i++) {
				if (kDTable1.getRow(i).getCell("aFinish").getValue().equals(Boolean.TRUE)) {
					ApproachStr = kDTable1.getRow(i).getCell("aApproach").getValue().toString();
					finishCount++;
				}
			}
			if(finishCount == 0){  //未办理
				this.editData.setAFMortgagedState(AFMortgagedStateEnum.UNTRANSACT);
			}
			else{  //办理中
				this.editData.setAFMortgagedState(AFMortgagedStateEnum.TRANSACTING);
			}
			//反写业务总览中对应的服务状态
			SHEManageHelper.updateTransactionOverView(null, this.editData.getRoom(), bizType,
				this.editData.getPromiseDate(), null, false);
		}
		for (int i = 0; i < kDTable1.getRowCount(); i++) {
			if (kDTable1.getRow(i).getCell("aFinish").getValue().equals(Boolean.TRUE)) {
				this.editData.setDepositInDate((Date) kDTable1.getRow(i).getCell("proFinishDate").getValue());
				this.editData.setDepositOutDate((Date) kDTable1.getRow(i).getCell("actFinishDate").getValue());
			}
		}
		
		//设置当前进程
		this.editData.setApproach(ApproachStr);
	}

	boolean isEidt = false;

	/**
	 * 检查按揭资料是否修改
	 * @return
	 */
	public boolean isEditDataDone() {
		boolean isEditData = this.editData.isIsEditData();
		int oldCount = 0;
		if (this.editData.getAFMortgaged() != null) {
			for (int i = 0; i < this.editData.getAFMortgaged().size(); i++) {
				if (!this.editData.getAFMortgaged().get(i).isIsAOrB()) {
					oldCount++;
				}
			}
		}
		if (kDTable2.getRowCount() != oldCount && this.editData.isIsEditData() == false) {
			isEditData = true;
		}
		if (afmInfo!=null && afmInfo.getDataEntrys()!=null && kDTable2.getRowCount() == afmInfo.getDataEntrys().size()) {
			for (int i = 0; i < kDTable2.getRowCount(); i++) {
				if (!kDTable2.getRow(i).getCell("bApproach").getValue().equals(afmInfo.getDataEntrys().get(i).getName())) {
					isEditData = true;
				}
			}
		}
		isEidt = isEditData;
		return isEditData;
	}

	public boolean isEditDataToDo() {
		boolean isEditData = false;
		if(afmInfo == null){
			return false;
		}
		if (afmInfo.getDataEntrys()!=null && kDTable2.getRowCount() != afmInfo.getDataEntrys().size()) {
			isEditData = true;
		}
		if (afmInfo.getDataEntrys()!=null && kDTable2.getRowCount() == afmInfo.getDataEntrys().size()) {
			for (int i = 0; i < kDTable2.getRowCount(); i++) {
				if (!kDTable2.getRow(i).getCell("bApproach").getValue().equals(
						afmInfo.getDataEntrys().get(i).getName())) {
					isEditData = true;
				}
			}
		}
		isEidt = isEditData;
		return isEditData;
	}

	public void saveEntrysToEdit() {
		if (this.editData.getAFMortgaged() == null || editData.getAFMortgaged().size() == 0) {  //新增进程和资料
			for (int i = 0; i < kDTable1.getRowCount(); i++) {  //新增进程
				IRow row = kDTable1.getRow(i);
				RoomLoanAFMEntrysInfo rleInfo = new RoomLoanAFMEntrysInfo();
				rleInfo.setApproach(row.getCell("aApproach").getValue().toString());
				rleInfo.setDate((Date) row.getCell("aDate").getValue());
				rleInfo.setPromiseFinishDate((Date)row.getCell("proFinishDate").getValue());
				rleInfo.setIsFinish(((Boolean) row.getCell("aFinish").getValue()).booleanValue());
				rleInfo.setActualFinishDate((Date)row.getCell("actFinishDate").getValue());
				rleInfo.setTransactor((UserInfo)row.getCell("transactor").getValue());
				if (row.getCell("aRemark").getValue() != null) {
					rleInfo.setRemark(row.getCell("aRemark").getValue().toString());
				}
				rleInfo.setIsFinishFlag(((Boolean) row.getCell("isFinishFlag").getValue()).booleanValue());
				rleInfo.setIsAOrB(((Boolean) row.getCell("aOrb").getValue()).booleanValue());
				this.editData.getAFMortgaged().add(rleInfo);
			}
			if (isEditDataToDo()) {
				for (int i = 0; i < kDTable2.getRowCount(); i++) {
					IRow row = kDTable2.getRow(i);
					RoomLoanAFMEntrysInfo rleInfo = new RoomLoanAFMEntrysInfo();
					rleInfo.setActualFinishDate((Date) row.getCell("bDate").getValue());
					rleInfo.setDate((Date) row.getCell("bDate").getValue());
					rleInfo.setIsFinish(((Boolean) row.getCell("bFinish").getValue()).booleanValue());
					rleInfo.setApproach(row.getCell("bApproach").getValue().toString());
					if (row.getCell("bRemark").getValue() != null) {
						rleInfo.setRemark(row.getCell("bRemark").getValue().toString());
					}
					rleInfo.setIsAOrB(false);
					this.editData.getAFMortgaged().add(rleInfo);
				}
				this.editData.setIsEditData(true);
			} else {
				for (int i = 0; i < kDTable2.getRowCount(); i++) {
					IRow row = kDTable2.getRow(i);
					RoomLoanAFMEntrysInfo rleInfo = new RoomLoanAFMEntrysInfo();
					rleInfo.setDate((Date) row.getCell("bDate").getValue());
					rleInfo.setIsFinish(((Boolean) row.getCell("bFinish").getValue()).booleanValue());
					rleInfo.setApproach(row.getCell("bApproach").getValue().toString());
					if (row.getCell("bRemark").getValue() != null) {
						rleInfo.setRemark(row.getCell("bRemark").getValue().toString());
					}
					rleInfo.setIsAOrB(((Boolean) row.getCell("aOrb").getValue()).booleanValue());
					this.editData.getAFMortgaged().add(rleInfo);
				}
			}
		} else {  //修改进程和资料
			if (this.editData.getAFMortgaged().get(0).getId() != null) {
				RoomLoanAFMEntrysCollection rleC = this.editData.getAFMortgaged();
				for (int i = 0; i < kDTable1.getRowCount(); i++) {
					IRow row = kDTable1.getRow(i);
					String id = null;
					if(row.getCell("id").getValue() != null){
						id = row.getCell("id").getValue().toString();
					}
						
					if(id != null){  //更新进程
						for (int j = 0; j < rleC.size(); j++) {
							RoomLoanAFMEntrysInfo rlInfo = rleC.get(j);
							if (rlInfo != null && rlInfo.getId() != null && id.equals(rlInfo.getId().toString())) {
								rlInfo.setPromiseFinishDate((Date)row.getCell("proFinishDate").getValue());
								rlInfo.setDate((Date) row.getCell("aDate").getValue());
								rlInfo.setIsFinish(((Boolean) row.getCell("aFinish").getValue()).booleanValue());
								rlInfo.setActualFinishDate((Date)row.getCell("actFinishDate").getValue());
								if (row.getCell("aRemark").getValue() != null) {
									rlInfo.setRemark(row.getCell("aRemark").getValue().toString());
								}
								rlInfo.setIsFinishFlag(((Boolean) row.getCell("isFinishFlag").getValue()).booleanValue());
								rlInfo.setTransactor((UserInfo)row.getCell("transactor").getValue());
							}
						}
					}
					else{  //新增进程
						RoomLoanAFMEntrysInfo rleInfo = new RoomLoanAFMEntrysInfo();
						rleInfo.setApproach(row.getCell("aApproach").getValue().toString());
						rleInfo.setDate((Date) row.getCell("aDate").getValue());
						rleInfo.setPromiseFinishDate((Date)row.getCell("proFinishDate").getValue());
						rleInfo.setIsFinish(((Boolean) row.getCell("aFinish").getValue()).booleanValue());
						rleInfo.setActualFinishDate((Date)row.getCell("actFinishDate").getValue());
						rleInfo.setTransactor((UserInfo)row.getCell("transactor").getValue());
						if (row.getCell("aRemark").getValue() != null) {
							rleInfo.setRemark(row.getCell("aRemark").getValue().toString());
						}
						rleInfo.setIsFinishFlag(((Boolean) row.getCell("isFinishFlag").getValue()).booleanValue());
						rleInfo.setIsAOrB(((Boolean) row.getCell("aOrb").getValue()).booleanValue());
						this.editData.getAFMortgaged().add(rleInfo);
					}
				}
				if (isEditDataDone()) {  //处理按揭资料名称被修改的情况
					for (int i = 0; i < rleC.size(); i++) {  //先删除原有的按揭资料
						if (!rleC.get(i).isIsAOrB()) {
							rleC.removeObject(i);
							--i;
						}
					}
					for (int i = 0; i < kDTable2.getRowCount(); i++) {  //再新增资料
						IRow row = kDTable2.getRow(i);
						RoomLoanAFMEntrysInfo rleInfo = new RoomLoanAFMEntrysInfo();
						rleInfo.setDate((Date) row.getCell("bDate").getValue());
						rleInfo.setIsFinish(((Boolean) row.getCell("bFinish").getValue()).booleanValue());
						rleInfo.setApproach(row.getCell("bApproach").getValue().toString());
						if (row.getCell("bRemark").getValue() != null) {
							rleInfo.setRemark(row.getCell("bRemark").getValue().toString());
						}
						rleInfo.setIsAOrB(false);
						this.editData.getAFMortgaged().add(rleInfo);
					}
					this.editData.setIsEditData(true);
				} else {  //按揭资料名称不变的情况
					for (int i = 0; i < kDTable2.getRowCount(); i++) {  //直接更新资料
						IRow row = kDTable2.getRow(i);
						String id = row.getCell("id").getValue().toString();
						for (int j = 0; j < rleC.size(); j++) {
							RoomLoanAFMEntrysInfo rlInfo = rleC.get(j);
							if (id.equals(rlInfo.getId().toString())) {
								rlInfo.setDate((Date) row.getCell("bDate").getValue());
								rlInfo.setIsFinish(((Boolean) row.getCell("bFinish").getValue()).booleanValue());
							}
						}
					}
				}
			}
		}
	}
}