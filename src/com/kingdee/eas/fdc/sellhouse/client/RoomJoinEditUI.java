/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.JoinApproachEntryCollection;
import com.kingdee.eas.fdc.sellhouse.JoinApproachEntryInfo;
import com.kingdee.eas.fdc.sellhouse.JoinDataEntryInfo;
import com.kingdee.eas.fdc.sellhouse.JoinDoSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomJoinApproachEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinDataEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinFactory;
import com.kingdee.eas.fdc.sellhouse.RoomJoinInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class RoomJoinEditUI extends AbstractRoomJoinEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomJoinEditUI.class);

	private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	
	/**
	 * output class constructor
	 */
	public RoomJoinEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected IObjectValue createNewData() {
		RoomJoinInfo objectValue = new RoomJoinInfo();
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setTransactor(SysContext.getSysContext().getCurrentUserInfo());
		String roomId = getUIContext().get("roomId").toString();
		try {
			objectValue.setRoom(RoomFactory.getRemoteInstance().getRoomInfo(
					new ObjectUuidPK(BOSUuid.read(roomId))));
		} catch (EASBizException e) {
			logger.error(e);
			this.handleException(e);
		} catch (BOSException e) {
			logger.error(e);
			this.handleException(e);
		} catch (UuidException e) {
			logger.error(e);
			this.handleException(e);
		}
		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		objectValue.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit()
				.castToFullOrgUnitInfo());
		objectValue.setBookedDate(new Date());
		objectValue.setJoinStartDate(new Time(new Date().getTime()));
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomJoinFactory.getRemoteInstance();
	}

	public void loadFields() {
		this.tblAppEntry.checkParsed();
		this.tblDataEntry.checkParsed();
		super.loadFields();

		String roomId = this.editData.getRoom().getId().toString();
		UIContext uiContextRoom = new UIContext(ui);
		uiContextRoom.put(UIContext.ID, roomId);
		
		if(this.editData.getBatchManage() != null){
			uiContextRoom.put("batchNumber", this.editData.getBatchManage().getNumber());
		}
		
		CoreUIObject plUI = null;
		try {
			plUI = (CoreUIObject) UIFactoryHelper.initUIObject(
					RoomBizInfoUI.class.getName(), uiContextRoom, null, "VIEW");
		} catch (UIException e) {
			this.handleException(e);
			logger.error(e);
		}

		sclPanel.setViewportView(plUI);
		sclPanel.setKeyBoardControl(true);
		sclPanel.setAutoscrolls(true);
		
		this.initEntry();
		
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionSave, actionCopy, actionPre, actionNext, actionFirst,
				actionLast, actionCancel, actionCancelCancel, actionRemove,
				actionPrint, actionPrintPreview }, false);
		menuView.setVisible(false);
		menuBiz.setVisible(false);
		menuSubmitOption.setVisible(false);
		pnlRoomInfo.setAutoscrolls(true);
		pnlRoomJoinInfo.setAutoscrolls(true);

		// 修改编辑后变新增状态的问题 20090216 by jeegan
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);

		handleCodingRule();

		if (!FDCSysContext.getInstance().checkIsSHEOrg()) {
			actionEdit.setEnabled(false);
		}
		
		if(this.editData.getActualFinishDate() != null){
			this.ckFinish.setSelected(true);
		}
		else{
			this.ckFinish.setSelected(false);
			this.pkActFinishDate.setEnabled(false);
		}
		
		if (OprtState.VIEW.equals(getOprtState())) {
			actionEdit.setEnabled(true);
		}
		
		initSchem();
	}

	/**
	 * 初始化入伙方案
	 * @throws EASBizException
	 * @throws BOSException
	 * @author liang_ren969
	 * @date 2011-9-9
	 */
	private void initSchem() throws EASBizException, BOSException {
		// 设置方案的过滤条件
		String sellPrjId = String.valueOf(getUIContext().get("sellProject.id"));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		
		Set id=new HashSet();
		SellProjectInfo info = new SellProjectInfo();
		info.setId(BOSUuid.read(sellPrjId));
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
		f7JoinDoScheme.setEntityViewInfo(view);
	}

	protected void initOldData(IObjectValue dataObject) {
		// super.initOldData(dataObject);
	}
	
	protected void tblAppEntry_editStopped(KDTEditEvent e) throws Exception {
		super.tblAppEntry_editStopped(e);
		
		IRow row = this.tblAppEntry.getRow(e.getRowIndex());
		if ("isFinish".equals(this.tblAppEntry.getColumnKey(e.getColIndex()))) {
			Object af = row.getCell("isFinish").getValue();
			if (af.equals(Boolean.TRUE)) {
				row.getCell("actFinishDate").setValue(new Date());
				row.getCell("actFinishDate").getStyleAttributes().setLocked(false);
				row.getCell("transactor").setValue(SysContext.getSysContext().getCurrentUserInfo());
				row.getCell("transactor").getStyleAttributes().setLocked(false);
				
				if(row.getCell("isFinishFlag").getValue()!=null && ((Boolean)row.getCell("isFinishFlag").getValue()).booleanValue()){
					this.ckFinish.setSelected(true);
					this.pkActFinishDate.setValue(new Date());
					this.pkActFinishDate.setEnabled(true);
				}
			} else {
				row.getCell("actFinishDate").setValue(null);
				row.getCell("transactor").setValue(null);
				
				row.getCell("actFinishDate").getStyleAttributes().setLocked(true);
				row.getCell("transactor").getStyleAttributes().setLocked(true);
				
				if(row.getCell("isFinishFlag").getValue()!=null && ((Boolean)row.getCell("isFinishFlag").getValue()).booleanValue()){
					this.ckFinish.setSelected(false);
					this.pkActFinishDate.setValue(null);
					this.pkActFinishDate.setEnabled(false);
				}
			}
		}
		if(row.getUserObject() != null){
			RoomJoinApproachEntryInfo appEntryInfo = (RoomJoinApproachEntryInfo) row.getUserObject();
			if(row.getCell("transactor") != null){
				UserInfo transactor = (UserInfo) row.getCell("transactor").getValue();
				appEntryInfo.setTransactor(transactor);
			}
		}
	}

	protected void f7JoinDoScheme_dataChanged(DataChangeEvent e)
			throws Exception {
		super.f7JoinDoScheme_dataChanged(e);
		
		Object newObj = e.getNewValue();
		Object oldObj = e.getOldValue();

		if (newObj == null && oldObj != null) {
			tblAppEntry.removeRows();
			tblDataEntry.removeRows();
		} else if (newObj != null && oldObj == null) {
			this.loadScheme((JoinDoSchemeInfo) newObj);
		} else if (newObj != null && oldObj != null) {
			// 当选择同一个房间时不做处理
			JoinDoSchemeInfo newScheme = (JoinDoSchemeInfo) newObj;
			JoinDoSchemeInfo oldScheme = (JoinDoSchemeInfo) oldObj;
			if (!newScheme.getId().equals(oldScheme.getId())) {
				this.loadScheme(newScheme);
			}
		}
	}
	
	private void loadScheme(JoinDoSchemeInfo schemeInfo){
		tblAppEntry.removeRows();
		tblDataEntry.removeRows();
		
		for (int i = 0; i < schemeInfo.getApprEntries().size(); i++) {
			IRow row = this.tblAppEntry.addRow(i);
			
			JoinApproachEntryInfo appInfo = schemeInfo.getApprEntries().get(i);
			
			RoomJoinApproachEntryInfo joinAppEntry = new RoomJoinApproachEntryInfo();
			joinAppEntry.setName(appInfo.getName());
			joinAppEntry.setPromiseFinishDate(this.getApproachPromiseDate(schemeInfo.getApprEntries(), appInfo));
			joinAppEntry.setIsFinish(false);
			joinAppEntry.setRemark(appInfo.getRemark());
			
			row.setUserObject(joinAppEntry);
			
			row.getCell("name").setValue(appInfo.getName());
			row.getCell("promFinishDate").setValue(this.getApproachPromiseDate(schemeInfo.getApprEntries(), appInfo));
			row.getCell("isFinish").setValue(Boolean.valueOf(false));
			row.getCell("actFinishDate").setValue(new Date());
			row.getCell("description").setValue(appInfo.getRemark());
			row.getCell("isFinishFlag").setValue(new Boolean(appInfo.isIsFinish()));
		}
		
		for (int i = 0; i < schemeInfo.getDataEntries().size(); i++) {
			IRow row = this.tblDataEntry.addRow(i);
			
			JoinDataEntryInfo dataInfo = schemeInfo.getDataEntries().get(i);
			
			row.getCell("name").setValue(dataInfo.getName());
			row.getCell("isFinish").setValue(Boolean.valueOf(false));
			row.getCell("actFinishDate").setValue(new Date());
			row.getCell("description").setValue(dataInfo.getRemark());
		}
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyUIControlEmpty(this);

		/*EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("room.id", editData.getRoom().getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("isBlankOut", Boolean.TRUE, CompareType.NOTEQUALS));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("number");
		view.getSelector().add("signDate");
		view.getSelector().add("onRecordDate");
		view.getSelector().add("contractNumber");

		RoomSignContractCollection roomColl = RoomSignContractFactory
				.getRemoteInstance().getRoomSignContractCollection(view);
		if (roomColl.size() > 0) {
			RoomSignContractInfo contract = roomColl.get(0);
			if (pkJoinDate.getValue() != null
					&& (DateTimeUtils.dayBefore((Date) pkJoinDate.getValue(),
							contract.getSignDate()))) {
				MsgBox.showInfo(this, contJoinDate.getBoundLabelText()
						+ "不能早于签约日期！");
				SysUtil.abort();
			}
			if (pkApptJoinDate.getValue() != null
					&& (DateTimeUtils.dayBefore((Date) pkApptJoinDate
							.getValue(), contract.getSignDate()))) {
				MsgBox.showInfo(this, contApptJoinDate.getBoundLabelText()
						+ "不能早于签约日期！");
				SysUtil.abort();
			}
		}*/
		super.verifyInput(e);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selector = super.getSelectors();
		selector.add("*");
		selector.add("batchManage.number");
		selector.add("room.*");
		selector.add("sign.*");
		selector.add("approachEntry.*");
		selector.add("dataEntry.*");
		return selector;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getJoinState()!=null && this.editData.getJoinState().equals(AFMortgagedStateEnum.STOPTRANSACT)){
			FDCMsgBox.showInfo("当前状态下的单据不能修改");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
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
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();

		if (STATUS_ADDNEW.equals(this.oprtState)
				&& iCodingRuleManager.isExist(editData, currentOrgId)) {
			// EditUI提供了方法，但没有调用，在onload后调用，以覆盖抽象类loadfields里面的调用（该调用没有处理断号选择）

			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData,
					currentOrgId);
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
			SaleOrgUnitInfo saleUnit = SysContext.getSysContext().getCurrentSaleUnit();

			if (saleUnit != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(
						editData, saleUnit.getId().toString());

				txtNumber.setEnabled(isAllowModify);
				txtNumber.setEditable(isAllowModify);
				txtNumber.setRequired(isAllowModify);
			}
		}
	}

	/*public void shareObject(List customerList, Object[] obj) throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		view.getSorter().add(new SorterItemInfo("number"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", FDCHelper.list2Set(customerList),
						CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("salesman.*");
		view.getSelector().add("shareSellerList.*");
		view.getSelector().add("shareSellerList.seller.*");
		view.getSelector().add("shareSellerList.marketingUnit.*");
		view.getSelector().add("shareSellerList.operationPerson.*");
		view.getSelector().add("shareSellerList.orgUnit.*");
		view.getSelector().add("linkmanList.*");
		FDCCustomerCollection fdcCustomerColl = FDCCustomerFactory
				.getRemoteInstance().getFDCCustomerCollection(view);
		for (int i = 0; i < fdcCustomerColl.size(); i++) {
			FDCCustomerInfo fdcCustomerInfo = fdcCustomerColl.get(i);
			ShareSellerCollection shareSellerColl = fdcCustomerInfo
					.getShareSellerList();
			// 个人共享方式列表
			List oldShareList = new ArrayList();
			// 营销单元共享方式列表
			List marketUnitList = new ArrayList();
			// 组织共享方式列表
			List orgUnitList = new ArrayList();
			// 找出该客户原有的共享销售顾问
			for (int m = 0; m < shareSellerColl.size(); m++) {
				ShareSellerInfo shareSellerInfo = shareSellerColl.get(m);
				if (ShareModelEnum.sharePerson.equals(shareSellerInfo
						.getShareModel())) {
					oldShareList.add(shareSellerInfo);
				} else if (ShareModelEnum.shareMarket.equals(shareSellerInfo
						.getShareModel())) {
					marketUnitList.add(shareSellerInfo);
				} else if (ShareModelEnum.shareOrgUnit.equals(shareSellerInfo
						.getShareModel())) {
					orgUnitList.add(shareSellerInfo);
				}
			}
			shareSellerColl.clear();
			List tblShareList = new ArrayList();
			for (int k = 0; k < obj.length; k++) {
				ShareSellerInfo shareSellerInfo = new ShareSellerInfo();

				if (obj[k] instanceof UserInfo) {
					shareSellerInfo.setSeller((UserInfo) obj[k]);
				} else if (obj[k] instanceof MarketingUnitInfo) {
					shareSellerInfo
							.setMarketingUnit((MarketingUnitInfo) obj[k]);
				} else if (obj[k] instanceof FullOrgUnitInfo) {
					shareSellerInfo.setOrgUnit((FullOrgUnitInfo) obj[k]);
				}
				shareSellerInfo
						.setShareModel((ShareModelEnum) this.comJoinShareModel
								.getSelectedItem());
				shareSellerInfo.setOperationPerson(userInfo);
				tblShareList.add(shareSellerInfo);
			}
			Set set = SHEHelper.differentSet(tblShareList, oldShareList,
					marketUnitList, orgUnitList);
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				ShareSellerInfo shareSellerInfo = (ShareSellerInfo) iterator
						.next();
				fdcCustomerInfo.getShareSellerList().add(shareSellerInfo);
			}
			FDCCustomerFactory.getRemoteInstance().submit(fdcCustomerInfo);
		}
	}*/

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		setOprtState(STATUS_EDIT);
		
		//设置当前状态和进程
		String curApproach = "";
		if (this.ckFinish.isSelected()) {  //办理完成
			curApproach = "办理完成";
			if (editData.getJoinState() == null
					|| editData.getJoinState().equals(AFMortgagedStateEnum.UNTRANSACT)
					|| editData.getJoinState().equals(AFMortgagedStateEnum.TRANSACTING)) {
				this.editData.setJoinState(AFMortgagedStateEnum.TRANSACTED);
			}
			//更新业务总览对应的服务
			SHEManageHelper.updateTransactionOverView(null, this.editData.getRoom(), SHEManageHelper.JOIN,
					this.editData.getPromiseFinishDate(), (Date)this.pkActFinishDate.getValue(), false);
		} 
		else {  //取下一条为完成的进程作为当前进程
			int finishCount = 0;
			for (int i = 0; i < this.tblAppEntry.getRowCount(); i++) {
				if (this.tblAppEntry.getRow(i).getCell("isFinish").getValue().equals(Boolean.TRUE)) {
					curApproach = this.tblAppEntry.getRow(i).getCell("name").getValue().toString();
					finishCount++;
				}
			}
			if(finishCount == 0){  //未办理
				this.editData.setJoinState(AFMortgagedStateEnum.UNTRANSACT);
			}
			else{  //办理中
				this.editData.setJoinState(AFMortgagedStateEnum.TRANSACTING);
			}
			//更新业务总览对应的服务
			SHEManageHelper.updateTransactionOverView(null, this.editData.getRoom(), SHEManageHelper.JOIN,
					this.editData.getPromiseFinishDate(), null, false);
		}
		this.editData.setCurrentApproach(curApproach);
		
		super.actionSubmit_actionPerformed(e);
	}
	
	/**
	 * 加载入伙进程和资料分录
	 */
	private void initEntry(){
		this.tblAppEntry.removeRows();
		this.tblDataEntry.removeRows();
		
		//初始化表格列编辑属性
		this.tblAppEntry.getColumn("actFinishDate").getStyleAttributes().setLocked(true);
		this.tblAppEntry.getColumn("transactor").getStyleAttributes().setLocked(true);
		
		KDDatePicker datePicker = new KDDatePicker();
		this.tblAppEntry.getColumn("promFinishDate").setEditor(new KDTDefaultCellEditor(datePicker));
		
		KDBizPromptBox transactorBox = new KDBizPromptBox();
		transactorBox.setEditable(false);
		transactorBox.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
		this.tblAppEntry.getColumn("transactor").setEditor(new KDTDefaultCellEditor(transactorBox));

		KDTextField txtRemark = new KDTextField();
		txtRemark.setMaxLength(255);
		this.tblAppEntry.getColumn("description").setEditor(new KDTDefaultCellEditor(txtRemark));
		this.tblDataEntry.getColumn("description").setEditor(new KDTDefaultCellEditor(txtRemark));
		
		if(this.editData.getApproachEntry() != null){
			for(int i=0; i<this.editData.getApproachEntry().size(); i++){
				RoomJoinApproachEntryInfo appInfo = this.editData.getApproachEntry().get(i);
				
				IRow row = this.tblAppEntry.addRow();
				row.getCell("id").setValue(appInfo.getId());
				row.getCell("name").setValue(appInfo.getName());
				row.getCell("promFinishDate").setValue(appInfo.getPromiseFinishDate());
				row.getCell("isFinish").setValue(Boolean.valueOf(appInfo.isIsFinish()));
				row.getCell("actFinishDate").setValue(appInfo.getActualFinishDate());
				row.getCell("transactor").setValue(appInfo.getTransactor());
				row.getCell("description").setValue(appInfo.getRemark());
				row.getCell("isFinishFlag").setValue(new Boolean(appInfo.isIsFinishFlag()));
				
				if(appInfo.isIsFinish()){
					row.getCell("actFinishDate").getStyleAttributes().setLocked(false);
					row.getCell("transactor").getStyleAttributes().setLocked(false);
				}
			}
			
			for(int i=0; i<this.editData.getDataEntry().size(); i++){
				RoomJoinDataEntryInfo dataEntry = this.editData.getDataEntry().get(i);
				
				IRow row = tblDataEntry.addRow(i);
				row.getCell("id").setValue(dataEntry.getId());
				row.getCell("name").setValue(dataEntry.getName());
				row.getCell("actFinishDate").setValue(dataEntry.getFinishDate());
				row.getCell("isFinish").setValue(Boolean.valueOf(dataEntry.isIsFinish()));
				row.getCell("description").setValue(dataEntry.getRemark());
			}
		}
	}
	
	/**
	 * 遍历进程，获取当前进程的应完成日期
	 * @param appEntryCol
	 * @param currentAppInfo
	 * @return
	 */
	private Date getApproachPromiseDate(JoinApproachEntryCollection appEntryCol, 
			JoinApproachEntryInfo currentAppInfo){
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
				return this.editData.getSign().getBizDate();
			}
		}
		else{
			for(int i=0; i<appEntryCol.size(); i++){
				JoinApproachEntryInfo appInfo = appEntryCol.get(i);
				if(appInfo.getName().equals(currentAppInfo.getReferenceTime())){
					Date tempDate = getApproachPromiseDate(appEntryCol, appInfo);
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

}