package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.reportone.r1.print.designer.gui.ScriptViewer.Action;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.IRestReceivable;
import com.kingdee.eas.fdc.tenancy.RestReceivableCollection;
import com.kingdee.eas.fdc.tenancy.RestReceivableEntryCollection;
import com.kingdee.eas.fdc.tenancy.RestReceivableFactory;
import com.kingdee.eas.fdc.tenancy.RestReceivableInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent;
import com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

public class RestReceivableEditUI extends AbstractRestReceivableEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RestReceivableEditUI.class);
	// 组织单元
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	public RestReceivableEditUI() throws Exception {
		super();
	}

	public void loadFields() {
		super.loadFields();
	}

	public void storeFields() {
		super.storeFields();
		if (editData != null && editData.getOtherPayList().size() > 0) {
			for (int i = 0; i < editData.getOtherPayList().size(); i++) {
				if (editData.getOtherPayList().get(i).getHead() == null) {
					editData.getOtherPayList().get(i).setHead(
							editData.getTenancyBill());
				}
			}
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		// 是否销售实体的新增、编辑、删除控制
		if (saleOrg.isIsBizUnit()) {
//			this.actionAddNew.setEnabled(true);
//			this.actionRemove.setEnabled(true);
//			this.actionEdit.setEnabled(true);
//			actionAudit.setEnabled(true);
		} else {
			this.actionAddNew.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionEdit.setEnabled(false);
			actionUnAudit.setEnabled(false);
			actionAudit.setEnabled(false);
		}
		initTenancyBillF7Filter();
		initRemarkColLen();
		initMoneyDefineCol();
		kdtEntrys_detailPanel.setTitle("应收明细");
		handleCodingRule();
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
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

	protected ICoreBase getBizInterface() throws Exception {
		return RestReceivableFactory.getRemoteInstance();
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		return null;
	}
	private static final String CANTEDIT = "cantEdit";

	private static final String CANTREMOVE = "cantRemove";
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getId() == null) {
			MsgBox.showInfo(this, "请先保存单据！");
			return;
		}
		if (TenancyBillStateEnum.Audited.equals(editData.getBillState())) {
			MsgBox.showInfo(this, "单据已审核，不能编辑！");
			abort();
		}
		checkBeforeEditOrRemove(CANTEDIT);
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getId() == null) {
			MsgBox.showInfo(this, "请先保存单据！");
			return;
		}
		boolean flag = true;
		if(editData.getOtherPayList() != null){
			for(int i=0;i<editData.getOtherPayList().size();i++){
				BigDecimal actrev = editData.getOtherPayList().get(i).getActRevAmount();
				BigDecimal value = new BigDecimal("0.00");
				if(value.compareTo(actrev)>0 ){
					flag = false;
					break;
				} 
			}
		}
		if (TenancyBillStateEnum.Audited.equals(editData.getBillState())) {
			MsgBox.showInfo(this, "单据已审核，不能删除！");
			abort();
		}
		if(!flag){
			MsgBox.showInfo(this, "存在已经收款的分录，无法删除");
			abort();
		}
		checkBeforeEditOrRemove(CANTREMOVE);
		if(editData.getId()!=null){
			FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		}
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * 描述：审批
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getId() == null) {
			MsgBox.showInfo(this, "请先保存单据！");
			return;
		}
		// 该单据不走工作流，覆盖原反审核方法
		IRestReceivable restRevDao = (IRestReceivable) getBizInterface();
		if (TenancyBillStateEnum.Saved.equals(editData.getBillState())) {
			MsgBox.showInfo(this, "请先提交单据！");
			SysUtil.abort();
		} else if (TenancyBillStateEnum.Submitted.equals(editData
				.getBillState())) {
			restRevDao.passAudit(null, editData);
			MsgBox.showInfo(this, "审批成功！");
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(true);
			this.onLoad();
		} else {
			MsgBox.showInfo(this, "当前单据状态不能审批！");
			SysUtil.abort();
		}
	}

	/**
	 * 描述：反审批
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getId() == null) {
			MsgBox.showInfo(this, "请先保存单据！");
			return;
		}
		// 该单据不走工作流，覆盖原反审核方法
		IRestReceivable restRevDao = (IRestReceivable) getBizInterface();
		
		
		
			//if (TenancyBillStateEnum.Audited.equals(editData.getBillState())) {
			
			boolean flag = true;
			/*if (editData.getOtherPayList().size() > 0) {
				for (int i = 0; i < editData.getOtherPayList().size(); i++) {
					if (editData.getOtherPayList().get(i).getActRevAmount() != null) {
						flag = false;
						break;
					}
				}
			}*/
			/**
			 * 收款后，实收金额就不为0
			 */
			if(editData.getOtherPayList().size() > 0){
				for(int i=0;i<editData.getOtherPayList().size();i++){
					if(editData.getOtherPayList().get(i).getActRevAmount() != null && editData.getOtherPayList().get(i).getActRevAmount().compareTo(FDCHelper.ZERO)>0){
						flag = false;
						break;
					}
				}
			}
			if (flag) {
				/**
				 * 发现直接从界面上取状态，状态可能没有更新
				 * 所以最好是从数据库中查找一次
				 */
				checkBillStats(this.editData.getId().toString());
				restRevDao.unpassAudit(null, editData);
				MsgBox.showInfo(this, "反审批成功！");
				actionUnAudit.setEnabled(false);
				actionAudit.setEnabled(true);
				this.onLoad();
			} else {
				/*checkBillStats(this.editData.getId().toString());
				int i = MsgBox.showConfirm2(this, "已存在收款单，是否确定要反审批");
				if (i == 0) {
					restRevDao.unpassAudit(null, editData);
					MsgBox.showInfo(this, "反审批成功！");
					actionUnAudit.setEnabled(false);
					actionAudit.setEnabled(true);
					this.onLoad();
				}*/
				MsgBox.showInfo(this, "已存在收款单，不能反审批!");
				SysUtil.abort();
			}
	}
	protected void checkBeforeEditOrRemove(String warning) {
		TenancyBillStateEnum state = this.editData.getBillState();
		if (state != null
				&& (state == TenancyBillStateEnum.Auditing || state == TenancyBillStateEnum.Audited )) {
			MsgBox.showWarning(this, ContractClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}
	/**
	 * 单据状态的检查
	 * @param id
	 * @return
	 */
	private boolean checkBillStats(String id){
		boolean result = true;
		try {
			
			IRestReceivable restRevDao = RestReceivableFactory.getRemoteInstance();
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("id", id, CompareType.EQUALS));
			evi.setFilter(filterInfo);

			SelectorItemCollection coll = new SelectorItemCollection();
			coll.add(new SelectorItemInfo("id"));
			coll.add(new SelectorItemInfo("billState"));

			evi.setSelector(coll);

			RestReceivableCollection collection = restRevDao.getRestReceivableCollection(evi);
		
			if (collection != null && collection.size() > 0) {
				for (int i = 0; i < collection.size(); i++) {
					RestReceivableInfo info = collection.get(i);
					if (!info.getBillState().equals(TenancyBillStateEnum.Audited)) {
						MsgBox.showInfo(this, "当前单据状态不能反审批！");
						SysUtil.abort();
					}
				}
			} else {
				MsgBox.showWarning(this, "此记录已不存在！");
				SysUtil.abort();
			}

		} catch (BOSException e) {
			logger.error(e + "获取其他应收单据:" + id + "状态失败！");
		}

		
		return result;
	}

	protected void initWorkButton() {
		super.initWorkButton();

		this.actionCopy.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionWorkFlowG.setVisible(false);

		this.toolBar.remove(this.btnAuditResult);

		this.actionAudit.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_audit"));
		this.actionUnAudit.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_unaudit"));
	}

	/**
	 * 设置分录备注字段输入长度为255
	 */
	private void initRemarkColLen() {
		KDTextField txtRemark = new KDTextField();
		txtRemark.setMaxLength(255);
		txtRemark.setMinLength(0);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(txtRemark);

		kdtEntrys.getColumn("remark").setEditor(editor);

		
		kdtEntrys.getColumn("appAmount").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
	}

	/**
	 * 初始化款项列为F7类型
	 */
	private void initMoneyDefineCol() {
		String queryPK = "com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery";

		KDBizPromptBox prmtMoneyDefine = new KDBizPromptBox();
		prmtMoneyDefine.setQueryInfo(queryPK);
		prmtMoneyDefine.setDisplayFormat("$name$");
		prmtMoneyDefine.setCommitFormat("$number$");
		prmtMoneyDefine.setEditFormat("$number$");

		// 添加过滤：款项类别为“滞纳金、手续费、违约金、其它”
		prmtMoneyDefine.setEntityViewInfo(getMoneyDefineEVI());

		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(prmtMoneyDefine);
		kdtEntrys.getColumn("moneyDefine").setEditor(editor);
		ObjectValueRender ovr = new ObjectValueRender();
		ovr.setFormat(new BizDataFormat("$name$"));
		kdtEntrys.getColumn("moneyDefine").setRenderer(ovr);

		KDDatePicker kdtEntrys_startDate_DatePicker = new KDDatePicker();
		kdtEntrys_startDate_DatePicker.setVisible(true);
		kdtEntrys_startDate_DatePicker.setEditable(true);
		KDTDefaultCellEditor kdtEntrys_startDate_CellEditor = new KDTDefaultCellEditor(
				kdtEntrys_startDate_DatePicker);
		this.kdtEntrys.getColumn("appDate").setEditor(
				kdtEntrys_startDate_CellEditor);
		this.kdtEntrys.getColumn("actRevDate").setEditor(
				kdtEntrys_startDate_CellEditor);
	}

	/**
	 * 获取款项定义的过滤View
	 */
	protected EntityViewInfo getMoneyDefineEVI() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(
						new FilterItemInfo("sysType",
								MoneySysTypeEnum.TENANCYSYS_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType", MoneyTypeEnum.LATEFEE_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType",
						MoneyTypeEnum.COMMISSIONCHARGE_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType",
						MoneyTypeEnum.FITMENTAMOUNT_VALUE));
		// filter.getFilterItems().add(new FilterItemInfo("moneyType",
		// MoneyTypeEnum.TAXFEE_VALUE));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("moneyType",
								MoneyTypeEnum.ELSEAMOUNT_VALUE));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("moneyType",
								MoneyTypeEnum.REPLACEFEE_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType", MoneyTypeEnum.BREACHFEE_VALUE));
		filter.setMaskString("#0 and (#1 or #2 or #3 or #4 or #5 or #6)");
		view.setFilter(filter);
		return view;
	}

	/**
	 * 租赁合同添加过滤：已审核
	 */
	protected void initTenancyBillF7Filter() {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId()
				.toString();
		filter.getFilterItems().add(new FilterItemInfo("CU.id", cuId));
		Set set = new HashSet();
		set.add(TenancyBillStateEnum.SAVED_VALUE);
		set.add(TenancyBillStateEnum.SUBMITTED_VALUE);
		set.add(TenancyBillStateEnum.AUDITING_VALUE);
		filter.getFilterItems()
				.add(
						new FilterItemInfo("tenancyState", set,
								CompareType.NOTINCLUDE));
		evi.setFilter(filter);
		this.prmtTenancyBill.setEntityViewInfo(evi);
	}

	protected IObjectValue createNewData() {
		RestReceivableInfo objectValue = new RestReceivableInfo();
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setBillState(TenancyBillStateEnum.Saved);
		objectValue.setBizDate(new Date());
		return objectValue;
	}

	private void verifyNumber() {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("number", editData.getNumber()));
		if (editData.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", editData.getId().toString(),
							CompareType.NOTINCLUDE));
		}
		evi.setFilter(filter);
		try {
			boolean flag = RestReceivableFactory.getRemoteInstance().exists(
					filter);
			if (flag) {
				MsgBox.showInfo(this, "编码重复，请重新输入！");
				SysUtil.abort();
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		//super.verifyInput(e);
		// 校验单据头
		String number = this.txtNumber.getText();
		if ((number == null || number.trim().length() <= 0)&&this.txtNumber.isEnabled()) {
			showMsg("单据编号");
		}
		if(this.txtNumber.isEnabled()){
			verifyNumber();
		}
		Date bizDate = editData.getBizDate();
		if (bizDate == null) {
			showMsg("业务日期");
		}

		TenancyBillInfo tb = editData.getTenancyBill();
		if (tb == null) {
			showMsg("租赁合同");
		}

		// 校验分录
		RestReceivableEntryCollection colls = editData.getEntrys();
		if (kdtEntrys.getRowCount() < 1) {
			MsgBox.showInfo(this, "请至少录入一条分录信息！");
			SysUtil.abort();
		}
		String pattern = "分录第{0}行中的{1}不能为空！";
		int count = kdtEntrys.getRowCount();
		for (int i = 0; i < count; i++) {
			IRow row = kdtEntrys.getRow(i);
			if (row.getCell("moneyDefine").getValue() == null) {
				showEntryMsg((i + 1), pattern, "款项名称");
			}
			if (row.getCell("appDate").getValue() == null) {
				showEntryMsg((i + 1), pattern, "应收日期");
			}
			if (row.getCell("appAmount").getValue() == null) {
				showEntryMsg((i + 1), pattern, "应收金额");
			}
		}

	}

	public IObjectPK runSubmit() throws Exception {
		editData.setBillState(TenancyBillStateEnum.Submitted);
		return super.runSubmit();
	}

	protected void showMsg(String msg) {
		MsgBox.showInfo(this, msg + "不能为空！");
		SysUtil.abort();
	}

	protected void showEntryMsg(int index, String pattern, String fName) {
		String msg = MessageFormat.format(pattern, new Object[] { "" + index,
				fName });
		MsgBox.showInfo(this, msg);
		SysUtil.abort();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("otherPayList.head.*");
		sic.add("billState");
		return sic;
	}

	/**
	 * 描述：根据是否已经被收款来判断应收金额填写是否正确
	 */
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		if (OprtState.EDIT.equals(getOprtState())) {
			int colIndex = e.getColIndex();
			String colKey = this.kdtEntrys.getColumnKey(colIndex);
			IRow row = KDTableUtil.getSelectedRow(kdtEntrys);
			if (row.getCell("actRevAmount").getValue() != null) {
				if ("appAmount".equals(colKey)) {
					if (row.getCell("appAmount").getValue() != null) {
						BigDecimal app = (BigDecimal)row.getCell("appAmount").getValue();
						BigDecimal act = (BigDecimal) row.getCell(
								"actRevAmount").getValue();
						if (app.compareTo(act) < 0) {
							row.getCell("appAmount").setValue(null);
							MsgBox.showInfo(this, "应收金额不能小于实收金额！");
						}
					}
				}
			}
			if (row.getCell("actRevAmount").getValue() != null) {
				if ("moneyDefine".equals(colKey)) {
					MsgBox.showInfo(this, "该条目已经收款，款项名称不能修改！");
					row.getCell("moneyDefine").setValue(e.getOldValue());
				}
			}
		}
	}

	/**
	 * 描述：主分录的增插删按钮监听事件
	 */
	protected void initListener() {
		super.initListener();
		// 添加分录监听事件
		kdtEntrys_detailPanel.addAddListener(new IDetailPanelListener() {
			// 新增前处理事件
			public void beforeEvent(DetailPanelEvent event) throws Exception {

			}

			// 新增后处理事件
			public void afterEvent(DetailPanelEvent event) throws Exception {

			}
		});
		// 删除分录监听事件
		kdtEntrys_detailPanel.addRemoveListener(new IDetailPanelListener() {

			public void beforeEvent(DetailPanelEvent event) throws Exception {
				IRow row = KDTableUtil.getSelectedRow(kdtEntrys);
				
					if (row.getCell("id").getValue() != null) {
						MsgBox.showInfo("该分录已经对应生成收款单，无法删除！");
						SysUtil.abort();
					}
				
			}

			public void afterEvent(DetailPanelEvent event) throws Exception {

			}
		});
		// 插入行监听事件
		kdtEntrys_detailPanel.addInsertListener(new IDetailPanelListener() {

			public void beforeEvent(DetailPanelEvent event) throws Exception {

			}

			public void afterEvent(DetailPanelEvent event) throws Exception {

			}
		});
		// 选中行监听事件
		kdtEntrys.addKDTSelectListener(new KDTSelectListener() {
			public void tableSelectChanged(KDTSelectEvent e) {

			}
		});
	}
	
	
	  /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
        this.storeFields();
		if(!getOprtState().equals(OprtState.ADDNEW)){
			actionSave.setEnabled(false);
		}else{
			actionSave.setEnabled(true);
			//handleCodingRule();
		}
    }
    
}