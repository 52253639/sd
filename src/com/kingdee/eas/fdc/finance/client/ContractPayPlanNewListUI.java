/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.ContractPayPlanNewCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanNewFactory;
import com.kingdee.eas.fdc.finance.ContractPayPlanNewInfo;
import com.kingdee.eas.fdc.finance.IContractPayPlanNew;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ContractPayPlanNewListUI extends AbstractContractPayPlanNewListUI{
	private static final Logger logger = CoreUIObject.getLogger(ContractPayPlanNewListUI.class);
	// 增加变量判断是否为修改合同的编辑
	private static boolean beingEdit = false;

	public static boolean beingEdit() {
		return beingEdit;
	}

	 /**
     * 检查合同是否已经生成了合同计划
     * @param contractId 合约id
     * @return
     */
    private boolean isExistPl(String contractId){
    	try {
			IContractPayPlanNew ip = ContractPayPlanNewFactory.getRemoteInstance();
			ContractPayPlanNewCollection pc = ip.getContractPayPlanNewCollection("where ContractCd='"+contractId+"'");
			if(UIRuleUtil.isNull(pc) || pc.size()==0){
				return true;
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
    	return false;
    }
    
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
//		super.actionAddNew_actionPerformed(e);
		if (getSelectContractId() == null) {
			MsgBox.showWarning(this, "没有选中合同，无法新增合同付款计划");
			SysUtil.abort();
		}
		
		if(!isExistPl(getSelectContractId())){
			FDCMsgBox.showInfo("新增失败,该合同已经有一条合同付款计划了.");
    		return;
		}
		
		beingEdit = true;
		boolean hasMutex = false;
		List list = new ArrayList();
		String id = this.getSelectContractId();
		list.add(id + "PayPlanNew");
		try {
			FDCClientUtils.requestDataObjectLock(this, list, "edit");
			AddEditUI(getSelectContractId(),OprtState.ADDNEW);
			this.displayBillByContract(null);
		} catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		} finally {
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, list);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}
		}
	}

	/**
	 * output class constructor
	 */
	public ContractPayPlanNewListUI() throws Exception {
		super();
	}

	public void actionEditPayPlan_actionPerformed(ActionEvent e)
			throws Exception {
		if (getSelectContractId() == null) {
			MsgBox.showWarning(this, "没有选中合同，无法修改合同付款计划");
			SysUtil.abort();
		}
		beingEdit = true;
		boolean hasMutex = false;
		List list = new ArrayList();
		String id = this.getSelectContractId();
		list.add(id + "PayPlanNew");
		try {
			FDCClientUtils.requestDataObjectLock(this, list, "edit");
			EditEditUI(getSelectContractId(),OprtState.EDIT);
			this.displayBillByContract(null);
		} catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		} finally {
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, list);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}
		}
	}

	 /**
     * output tblPayPlan_tableClicked method
     */
    protected void tblPayPlan_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	if(e.getClickCount()>1){
    		EditEditUI(getSelectContractId(),OprtState.VIEW);
    	}
    }

   protected void tblPayPlan_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		super.tblPayPlan_tableSelectChanged(e);
		this.btn_Audit.setEnabled(false);
    	this.btn_Modify.setEnabled(false);
    	this.btn_unAudit.setEnabled(false);
    	this.btn_Submit.setEnabled(false);
    	String status = String.valueOf(tblPayPlan.getCell(e.getSelectBlock().getTop(), "status").getValue());
    	if("保存".equals(status)){
    		this.btn_Submit.setEnabled(true);
    	}else if("提交".equals(status)){
    		this.btn_Audit.setEnabled(true);
    	}else if("已审批".equals(status)){
    		this.btn_Modify.setEnabled(true);
    		this.btn_unAudit.setEnabled(true);
    	}
	}

/**
    * 新增付款计划
    * @param contractCd
    * @param status
    * @throws UIException
    */
	private void AddEditUI(String contractCd,String status) throws UIException{
		ContractPayPlanNewInfo info = new ContractPayPlanNewInfo();
		try {
			ContractBillInfo cbInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(contractCd));
			info.setSignAmount(cbInfo.getOriginalAmount());//合同金额
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    	info.setContractCd(contractCd);
    	String projectId = tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
    	logger.info("-----------projectId:"+projectId);
    	UIContext uiContext = new UIContext(this);
    	uiContext.put("UI", this);
    	uiContext.put("projectId", projectId);
    	uiContext.put(UIContext.INIT_DATAOBJECT, info);
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		IUIWindow curDialog = uiFactory.create(ContractPayPlanNewEditUI.class
				.getName(), uiContext, null, status);
		curDialog.show();
	}
	/**
	 * 修改或查看付款计划
	 * @param contractCd
	 * @param status
	 * @throws UIException
	 */
	private void EditEditUI(String contractCd,String status) throws UIException{
		ContractPayPlanNewInfo info = new ContractPayPlanNewInfo();
    	info.setContractCd(contractCd);
//    	info.setPlanAmount(amount);//合同金额
    	IRow row = tblPayPlan.getRow(this.tblPayPlan.getSelectManager().getActiveRowIndex());
    	if(UIRuleUtil.isNotNull(row) && UIRuleUtil.isNotNull(row.getCell("id").getValue())){
	    	UIContext uiContext = new UIContext(this);
	    	String projectId = tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
	    	uiContext.put("UI", this);
	    	uiContext.put("projectId", projectId);
	    	uiContext.put(UIContext.ID, String.valueOf(row.getCell("id").getValue()).trim());
			IUIFactory uiFactory = null;
			uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
			IUIWindow curDialog = uiFactory.create(ContractPayPlanNewEditUI.class
					.getName(), uiContext, null, status);
			curDialog.show();
    	}
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		this.btnEditPayPlan.setIcon(EASResource.getIcon("imgTbtn_edit"));
		this.menuItemEditPayPlan.setIcon(EASResource.getIcon("imgTbtn_edit"));
		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setEnabled(false);

		this.actionView.setEnabled(false);
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		// super.actionView_actionPerformed(e);
	}

	protected void refresh(ActionEvent e) throws Exception {
		super.refresh(e);
		beingEdit = false;
	}

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return ContractPayPlanEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {

		return ContractPayPlanFactory.getRemoteInstance();
	}

	/**
	 * 
	 * 描述：获取当前单据的Table（子类必须实现）
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-2
	 *               <p>
	 */
	protected KDTable getBillListTable() {
		return this.tblPayPlan;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected boolean isHasBillTable() {
		return true;
	}

	protected void displayBillByContract(EntityViewInfo view)
			throws BOSException {
		String contractId = getSelectContractId();
		if (contractId == null) {
			return;
		}
		try {
			fillTable(contractId, getBillListTable());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.tblPayPlan.getStyleAttributes().setLocked(true);
	}

	private String getSelectContractId() {
		int rowIndex = this.getMainTable().getSelectManager()
				.getActiveRowIndex();
		if (rowIndex == -1) {
			return null;
		}
		String contractId = (String) getMainTable().getCell(rowIndex,
				getKeyFieldName()).getValue();
		return contractId;
	}

	public static void fillTable(String contractId, KDTable table)
			throws BOSException, SQLException {
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		table.removeColumns();
		table.removeRows();
		table.setUserObject(null);
		IColumn iCol = table.addColumn();
		iCol.setKey("id");
		iCol.getStyleAttributes().setHided(true);
		
		iCol = table.addColumn();
		iCol.setKey("number");
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		txtEditor.setClickCountToStart(1);
		iCol.setEditor(txtEditor);
		
		iCol = table.addColumn();
		iCol.setKey("CopyName");
		textField = new KDTextField();
		textField.setMaxLength(100);
		txtEditor = new KDTDefaultCellEditor(textField);
		txtEditor.setClickCountToStart(1);
		iCol.setEditor(txtEditor);
		
		iCol = table.addColumn();
		iCol.setKey("status");
		textField = new KDTextField();
		textField.setMaxLength(10);
		txtEditor = new KDTDefaultCellEditor(textField);
		txtEditor.setClickCountToStart(1);
		iCol.setEditor(txtEditor);
		
		iCol = table.addColumn();
		iCol.setKey("planAmount");
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		formattedTextField.setRequired(true);
		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		numberEditor.setClickCountToStart(1);
		iCol.setEditor(numberEditor);
		iCol.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		iCol.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		iCol.getStyleAttributes().setBackground(
				FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);
		iCol.getStyleAttributes().setLocked(true);
		
		iCol = table.addColumn();
		iCol.setKey("auditPerson");
		textField = new KDTextField();
		textField.setMaxLength(200);
		txtEditor = new KDTDefaultCellEditor(textField);
		txtEditor.setClickCountToStart(1);
		iCol.setEditor(txtEditor);
		
		iCol = table.addColumn();
		iCol.setKey("auditTime");
		KDDatePicker datePicker = new KDDatePicker();
		datePicker.setRequired(true);
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(datePicker);
		dateEditor.setClickCountToStart(1);
		iCol.setEditor(dateEditor);
		String formatString = "yyyy-MM-dd";
		iCol.getStyleAttributes().setNumberFormat(formatString);
		iCol.getStyleAttributes().setBackground(
				FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);

		IRow headRow = table.addHeadRow();
		headRow.getCell("id").setValue("id");
		headRow.getCell("number").setValue("版本号");
		headRow.getCell("CopyName").setValue("版本名称");
		headRow.getCell("status").setValue("状态");
		headRow.getCell("planAmount").setValue("计划总金额");
		headRow.getCell("auditPerson").setValue("审批人");
		headRow.getCell("auditTime").setValue("审批时间");

		EntityViewInfo view = new EntityViewInfo();
//		view.getSorter().add(new SorterItemInfo("seq"));

		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(
				new FilterItemInfo("ContractCd", contractId));
		view.setFilter(filter);
		// SorterItemCollection sorter = view.getSorter();
		// sorter.add(new SorterItemInfo("seq"));

		ContractPayPlanNewCollection payPlans = ContractPayPlanNewFactory
				.getRemoteInstance().getContractPayPlanNewCollection(view);

		for (int i = 0; i < payPlans.size(); i++) {
			ContractPayPlanNewInfo info = payPlans.get(i);
			IRow row = table.addRow();
			row.setUserObject(info);
			row.getCell("id").setValue(info.getId().toString());
			row.getCell("number").setValue(info.getNumber());
			row.getCell("CopyName").setValue(info.getCopyName());
			row.getCell("status").setValue(info.getStatus());
			row.getCell("planAmount").setValue(info.getContractPrice());
			row.getCell("auditPerson").setValue(info.getAuditPerson());
			row.getCell("auditTime").setValue(info.getAuditTime());
		}
	}

	protected String getKeyFieldName() {
		return "id";
	}

	protected void tblBill_tableClicked(KDTMouseEvent e) throws Exception {

	}

	// 数据对象变化时，刷新界面状态
	protected void setActionState() {

	}

	public void onLoad() throws Exception {
		super.onLoad();
		FDCClientHelper.setActionEnable(new ItemAction[] { actionLocate,
				actionAttachment, actionPrint, actionPrintPreview,
				actionWorkFlowG, actionAudit, actionUnAudit }, false);
//		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuWorkFlow.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionEditPayPlan.setVisible(false);
		actionAuditResult.setEnabled(false);
		actionAuditResult.setVisible(false);
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionEdit, actionAttachment, actionPrint, actionPrintPreview,
				actionWorkFlowG, actionAudit, actionUnAudit, actionRemove,
				actionView, actionCancel, actionCancelCancel }, false);
		CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext()
				.getCurrentCostUnit();
		if (currentOrg == null || !currentOrg.isIsBizUnit()) {
			// this.actionEditPayPlan.setVisible(false);
			actionEditPayPlan.setEnabled(false);
			// menuEdit.setVisible(false);
		}
		this.btnAddNew.setVisible(true);
		this.btnAddNew.setEnabled(true);
		this.actionAddNew.setVisible(true);
		this.actionAddNew.setEnabled(true);
		actionEditPayPlan.setEnabled(true);
	}
	
	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return null;
	}

	protected void audit(List ids) throws Exception {

	}

	protected void unAudit(List ids) throws Exception {

	}

	/**
	 * 要显示的合同的状态集合,用于过滤合同
	 * 
	 * @return
	 */
	protected Set getContractBillStateSet() {
		Set set = new HashSet();
		set.add(FDCBillStateEnum.AUDITTED_VALUE);
		set.add(FDCBillStateEnum.SAVED_VALUE);
		set.add(FDCBillStateEnum.SUBMITTED_VALUE);
		return set;
	}

	protected boolean isFootVisible() {
		return false;
	}

	protected void fetchInitData() throws Exception {
		// Map param = new HashMap();
		// Map initData =
		// ((IFDCBill)fdcbill.getRemoteInstance()).fetchInitData(param);
		//		
		// //获得当前组织
		// orgUnit =
		// (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);

	}

	protected SelectorItemCollection genBillQuerySelector() {
		return null;
	}

	// 默认附件不可见
	protected boolean isShowAttachmentAction() {
		return false;
	}
	/**
	 * 审核
	 */
	protected void btn_Audit_actionPerformed(ActionEvent e) throws Exception {
		super.btn_Audit_actionPerformed(e);
		int activeRow = tblPayPlan.getSelectManager().getActiveRowIndex();
		if(activeRow>-1){
			BOSUuid bosid = BOSUuid.read(String.valueOf(tblPayPlan.getCell(activeRow, "id").getValue()));
			ContractPayPlanNewInfo model = ContractPayPlanNewFactory
					.getRemoteInstance().getContractPayPlanNewInfo(
							new ObjectUuidPK(bosid));
			UserInfo ui = SysContext.getSysContext().getCurrentUserInfo();
			model.setAuditPerson(ui.getName());
			model.setAuditTime(new Date());
			if(ContractPayPlanNewFactory.getRemoteInstance().auditBill(model)){
				btn_Submit.setEnabled(false);
				btn_Audit.setEnabled(false);
				btn_unAudit.setEnabled(true);
				btn_Modify.setEnabled(true);
				this.refreshList();
			}
		}
	}
	/**
	 * 提交
	 */
	protected void btn_Submit_actionPerformed(ActionEvent e) throws Exception {
		super.btn_Submit_actionPerformed(e);
		int activeRow = tblPayPlan.getSelectManager().getActiveRowIndex();
		if(activeRow>-1){
			BOSUuid bosid = BOSUuid.read(String.valueOf(tblPayPlan.getCell(activeRow, "id").getValue()));
			ContractPayPlanNewInfo model = ContractPayPlanNewFactory
					.getRemoteInstance().getContractPayPlanNewInfo(
							new ObjectUuidPK(bosid));
			if(ContractPayPlanNewFactory.getRemoteInstance().submitBill(model)){
				btn_Submit.setEnabled(false);
				btn_Audit.setEnabled(true);
				this.refreshList();
			}
		}
	}
	/**
	 * 反审核
	 */
	protected void btn_unAudit_actionPerformed(ActionEvent e) throws Exception {
		super.btn_unAudit_actionPerformed(e);
		int activeRow = tblPayPlan.getSelectManager().getActiveRowIndex();
		if(activeRow>-1){
			BOSUuid bosid = BOSUuid.read(String.valueOf(tblPayPlan.getCell(activeRow, "id").getValue()));
			ContractPayPlanNewInfo model = ContractPayPlanNewFactory
					.getRemoteInstance().getContractPayPlanNewInfo(
							new ObjectUuidPK(bosid));
			model.setAuditPerson("");
			model.setAuditTime(null);
			if(ContractPayPlanNewFactory.getRemoteInstance().unauditBill(model)){
				btn_Audit.setEnabled(true);
				btn_unAudit.setEnabled(false);
				this.refreshList();
			}else{
				MsgBox.showWarning(this, "选中合同付款计划已经修订过，不能反审核");
				SysUtil.abort();
			}
		}
	}
	/**
	 * 修订
	 */
	protected void btn_Modify_actionPerformed(ActionEvent e) throws Exception {
		super.btn_Modify_actionPerformed(e);
		int activeRow = tblPayPlan.getSelectManager().getActiveRowIndex();
		if(activeRow<0){
			MsgBox.showWarning(this, "没有选中合同付款计划，无法修订合同付款计划");
			SysUtil.abort();
		}else if(tblPayPlan.getCell(activeRow, "status").getValue()==null 
				|| !"已审批".equals(tblPayPlan.getCell(activeRow, "status").getValue().toString())){
			MsgBox.showWarning(this, "选中合同付款计划还未审批，无法修订合同付款计划");
			SysUtil.abort();
		}
		if(UIRuleUtil.isNotNull(tblPayPlan.getCell(activeRow, "id").getValue())){
			String id = tblPayPlan.getCell(activeRow, "id").getValue().toString();
			BOSUuid bosid = BOSUuid.read(id);

			if(!ContractPayPlanNewFactory.getRemoteInstance().isFinal(bosid)){
				MsgBox.showWarning(this, "选中的合同付款计划不是最后一个版本，无法修订合同付款计划");
				SysUtil.abort();
			}
			ContractPayPlanNewInfo model = ContractPayPlanNewFactory
					.getRemoteInstance().getContractPayPlanNewInfo(
							new ObjectUuidPK(bosid));
			model.setId(null);
			for(int i=0; i<model.getPlanMingxi().size(); i++){
				model.getPlanMingxi().get(i).setId(null);
				if(UIRuleUtil.isNotNull(model.getPlanMingxi().get(i).getShixiangmx())){
					model.getPlanMingxi().get(i).setShixiangmx(
							FDCScheduleTaskFactory.getRemoteInstance()
									.getFDCScheduleTaskInfo(
											new ObjectUuidPK(model
													.getPlanMingxi().get(i)
													.getShixiangmx().getId())));
				}
			}
			for(int i=0; i<model.getPlam().size(); i++){
				model.getPlam().get(i).setId(null);
			}
			for(int i=0; i<model.getPayByStage().size(); i++){
				model.getPayByStage().get(i).setId(null);
			}
			String projectId = tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
	    	UIContext uiContext = new UIContext(this);
	    	uiContext.put("UI", this);
	    	uiContext.put("modify", "1");//处于修订状态
	    	uiContext.put("projectId", projectId);
	    	uiContext.put(UIContext.INIT_DATAOBJECT, model);
			IUIFactory uiFactory = null;
			uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
			IUIWindow curDialog = uiFactory.create(ContractPayPlanNewEditUI.class
					.getName(), uiContext, null, OprtState.ADDNEW);
			curDialog.show();
		}
	}

}