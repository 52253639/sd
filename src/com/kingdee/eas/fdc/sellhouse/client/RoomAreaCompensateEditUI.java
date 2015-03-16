/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.calc.CalculatorDialog;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class RoomAreaCompensateEditUI extends AbstractRoomAreaCompensateEditUI {
	private static final Logger logger = CoreUIObject.getLogger(RoomAreaCompensateEditUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	String type = null;

	/**
	 * output class constructor
	 */
	public RoomAreaCompensateEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		if ("noscheme".equalsIgnoreCase(type)) {
			editData.setScheme(null);
			editData.setCompensateRate(null);
			editData.setIsCalcByScheme(false);
		} else {
			editData.setIsCalcByScheme(true);
		}

		super.storeFields();
	}

	protected IObjectValue createNewData() {
		RoomAreaCompensateInfo objectValue = new RoomAreaCompensateInfo();
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setTransactor(SysContext.getSysContext().getCurrentUserInfo());

		type = getUIContext().get("type").toString();
		if (type.equals("scheme")) {
			objectValue.setIsCalcByScheme(true);
		} else
			objectValue.setIsCalcByScheme(false);
		String roomId = getUIContext().get("roomId").toString();
		try {
			objectValue.setRoom(RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(roomId))));
		} catch (EASBizException e) {
			logger.error(e);
		} catch (BOSException e) {
			logger.error(e);
		} catch (UuidException e) {
			logger.error(e);
		}
		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		objectValue.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		objectValue.setBookedDate(new Date());
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomAreaCompensateFactory.getRemoteInstance();
	}

	public void loadFields() {
		super.loadFields();

		this.txtCompensateAmount.setValue(editData.getCompensateAmount());

		if ((STATUS_EDIT.equals(getOprtState()) || STATUS_ADDNEW.equals(getOprtState())) && getUIContext().get("type") != null) {
			type = getUIContext().get("type").toString();
			if (type.equals("scheme")) {
				prmtScheme.setRequired(true);
//				txtCompensateAmount.setEnabled(false);
				if (editData == null || editData.getScheme() == null || !editData.isIsCalcByScheme())
					txtCompensateAmount.setValue(null);
			} else {
				prmtScheme.setValue(null);
				prmtScheme.setEnabled(false);
				actionCalc.setEnabled(false);
			}
		}
		String roomId = getUIContext().get("roomId").toString();
		UIContext uiContextRoom = new UIContext(ui);
		uiContextRoom.put(UIContext.ID, roomId);
		CoreUIObject plUI = null;
		try {
			plUI = (CoreUIObject) UIFactoryHelper.initUIObject(RoomBizInfoUI.class.getName(), uiContextRoom, null, "VIEW");
		} catch (UIException e) {
			logger.error(e);
		}
		sclPanel.setViewportView(plUI);
		sclPanel.setKeyBoardControl(true);
		sclPanel.setAutoscrolls(true);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew, this.actionEdit, actionSave, actionCopy, actionPre, actionNext, actionFirst, actionLast, actionCancel, actionCancelCancel,
				actionRemove, actionPrint, actionPrintPreview }, false);
		menuView.setVisible(false);
		menuBiz.setVisible(false);
		pnlRoomInfo.setAutoscrolls(true);
		pnlRoomAreaCompensateInfo.setAutoscrolls(true);
		menuSubmitOption.setVisible(false);
		txtCompensateAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtCompensateAmount.setRemoveingZeroInDispaly(false);
		txtCompensateAmount.setPrecision(2);
		txtCompensateAmount.setSupportedEmpty(true);

		chkMenuItemSubmitAndAddNew.setSelected(false);
		handleCodingRule();

		if (getUIContext().get("isFromWorkFlow") != null && getUIContext().get("isFromWorkFlow").equals("true")) {
			FDCClientHelper.setActionEnable(new ItemAction[] { actionEdit }, false);
		}

		if (!saleOrg.isIsBizUnit()) {
			actionEdit.setEnabled(false);
		}
		if (prmtTransactor.getValue() == null)
			prmtTransactor.setValue(SysContext.getSysContext().getCurrentUserInfo());

		String roomId = getUIContext().get("roomId").toString();

		SelectorItemCollection selColl = new SelectorItemCollection();
		selColl.add("building.sellProject");

		RoomInfo room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(roomId)), selColl);

		String sellProjectId = room.getBuilding().getSellProject().getId().toString();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);

		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));

		prmtScheme.setEntityViewInfo(view);

		this.pkCompensateDate.setDatePattern("yyyy-MM-dd");
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		this.verifyBalance();
		super.actionSave_actionPerformed(e);
		initOldData(editData);
	}

	public void actionCalc_actionPerformed(ActionEvent e) throws Exception {
		this.verifyBalance();
		if (prmtScheme.getValue() == null) {
			MsgBox.showInfo(this, "未选定方案,不能计算补差!");
			SysUtil.abort();
		}
		List idList = new ArrayList();
		String roomId = getUIContext().get("roomId").toString();
		idList.add(roomId);
		String schemeId = ((CompensateSchemeInfo) prmtScheme.getValue()).getId().toString();
		Map values = RoomAreaCompensateFactory.getRemoteInstance().calcAmount(idList, schemeId);
		Map value = (Map) values.get(roomId);
		if (value != null && value.get("compensateAmt") != null)
			txtCompensateAmount.setValue(value.get("compensateAmt"));
		if (value != null && value.get("compensateRate") != null)
			editData.setCompensateRate(FDCHelper.toBigDecimal(value.get("compensateRate")));

	}
	
	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
        CalculatorDialog calc=new CalculatorDialog(this,true);
        calc.showDialog(2,true);        
		//super.actionCalculator_actionPerformed(e);
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		this.verifyBalance();
		FDCClientVerifyHelper.verifyUIControlEmpty(this);
		super.verifyInput(e);
	}

	/**
	 * 对于已结算的期间，不允许进行补差
	 * */
	private void verifyBalance() {
		Date bizDate = (Date) this.pkCompensateDate.getValue();
		if(bizDate==null){
			MsgBox.showInfo("补差日期不能为空。");
			this.abort();
		}
		Date balanceEndDate = null;
		RoomInfo room = null;				
		String roomId = this.editData.getRoom().getId().toString();
		SelectorItemCollection selColl = new SelectorItemCollection();
		selColl.add("building.sellProject.*");
		try {
			room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(roomId)), selColl);
		} catch (EASBizException e) {
			handleException(e);
			e.printStackTrace();
		} catch (BOSException e) {
			handleException(e);
			e.printStackTrace();
		} catch (UuidException e) {
			handleException(e);
			e.printStackTrace();
		}
		SellProjectInfo sellProject = room.getBuilding().getSellProject();
		if (sellProject != null) {
			try {
				balanceEndDate = getLastEndDate(sellProject.getId().toString());
			} catch (Exception e) {
				handleException(e);
				e.printStackTrace();
			}
			SHEHelper.verifyBalance(bizDate, balanceEndDate);
		}
//		对于方案不差的时候校验 系统 默认抛出异常的提示不友好
		type = getUIContext().get("type").toString();
		if (type.equals("scheme")) {
			if (prmtScheme.getValue() == null) {
				MsgBox.showInfo(this, "未选定方案,不能计算补差!");
				SysUtil .abort();
			}
		}
	}

	/***
	 * 按销售项目取上次结算的截止日期。
	 * **/

	private Date getLastEndDate(String sellProjectId) throws Exception {
		Date lastEndDate = null;
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql("select FBalanceEndDate from T_SHE_SellProject where FID = ?");
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

	protected void txtCompensateAmount_dataChanged(DataChangeEvent e) throws Exception {
		super.txtCompensateAmount_dataChanged(e);
		if (type != null && type.equals("noscheme") && editData != null && !editData.isIsCalcByScheme())
			if (!((e.getOldValue() == null) || (FDCHelper.toBigDecimal(e.getOldValue()).compareTo(FDCHelper.toBigDecimal(e.getOldValue())) == 0))) {
				prmtScheme.setValue(null);
			}
	}

	protected void prmtScheme_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtScheme_dataChanged(e);
		if (e.getOldValue() == null || e.getNewValue() == null || ((type != null && type.equals("scheme") && !(e.getOldValue().equals(e.getNewValue())))))
			txtCompensateAmount.setValue(null);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selector = super.getSelectors();
		selector.add("*");
		selector.add("room.*");
		return selector;
	}

	/**
	 * 处理编码规则
	 * 
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {

		String currentOrgId = FDCClientHelper.getCurrentOrgId();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();

		if (STATUS_ADDNEW.equals(this.oprtState) && iCodingRuleManager.isExist(editData, currentOrgId)) {
			// EditUI提供了方法，但没有调用，在onload后调用，以覆盖抽象类loadfields里面的调用（该调用没有处理断号选择）

			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData, currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(editData, currentOrgId);
			} else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData, currentOrgId)) { // 此处的orgId与步骤1
																						// ）
																						// 的orgId时一致的
																						// ，
																						// 判断用户是否启用断号支持功能
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// 启用了断号支持功能,同时启用了用户选择断号功能
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						//要判断是否存在断号,是则弹出,否则不弹///////////////////////////////////
						// ///////
						Object object = null;
						if (iCodingRuleManager.isDHExist(editData, currentOrgId)) {
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
			// CostCenterOrgUnitInfo currentCostUnit =
			// SysContext.getSysContext()
			// .getCurrentCostUnit();

			OrgUnitInfo currentCostUnit = SysContext.getSysContext().getCurrentOrgUnit();

			if (currentCostUnit != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(editData, currentCostUnit.getId().toString());

				txtNumber.setEnabled(isAllowModify);
				txtNumber.setEditable(isAllowModify);
				txtNumber.setRequired(isAllowModify);
			}
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyBalance();
		setOprtState(STATUS_EDIT);
		super.actionSubmit_actionPerformed(e);
		// initOldData(editData);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		if (type == null) {
			type = "noscheme";
			prmtScheme.setEnabled(false);
			actionCalc.setEnabled(false);
		}
		/*
		 * if(this.prmtScheme.getValue() == null) {
		 * prmtScheme.setEnabled(false); actionCalc.setEnabled(false); } else {
		 * prmtScheme.setEnabled(true); txtCompensateAmount.setEnabled(false); }
		 */
	}
}