/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.ContractPayPlanCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.ContractPayPlanInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetCtrlStrategy.FDCBudgetParam;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * 合同付款计划 编辑界面
 */
public class ContractPayPlanEditUI extends AbstractContractPayPlanEditUI {
	private ContractBillInfo contract;
	private BigDecimal exRate;
	private BigDecimal lastAmount;
	private Date time=new Date();
	private int year = 0;
	private int month = 0;
	private Map tableDataMap = null;
	private Map payDataMap = null;
	/**
	 * output class constructor
	 */
	public ContractPayPlanEditUI() throws Exception {
		super();
	}

	public boolean destroyWindow() {
		if (this.isEdited()) {
			int option = MsgBox.showConfirm3(this, "数据已修改，是否保存并退出");
			if (option == MsgBox.YES) {
				try {
					this.verify();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.btnSave.doClick();
			}else if(option == MsgBox.CANCEL){
				this.abort();
			}
		}
		return super.destroyWindow();
	}

	public boolean isEdited() {
		this.tblPayPlan.getSelectManager().select(0, 0);
		this.tblPayPlan.getSelectManager().select(0, 1);
		if (this.tblPayPlan.getUserObject() != null) {
			return true;
		}
		for (int i = 0; i < this.tblPayPlan.getRowCount(); i++) {
			IRow row = this.tblPayPlan.getRow(i);
			ContractPayPlanInfo payPlanInfo = (ContractPayPlanInfo) row
					.getUserObject();
			// if(payPlanInfo==null){
			// payPlanInfo=new ContractPayPlanInfo();
			// }
			if (!FDCHelper.isEqual(payPlanInfo.getPayDate(), row.getCell(
					"payDate").getValue())) {
				return true;
			}
			if (!FDCHelper.isEqual(payPlanInfo.getPayProportion(), row.getCell(
					"payProportion").getValue())) {
				return true;
			}
			if (!FDCHelper.isEqual(payPlanInfo.getPayAmount(), row.getCell(
					"payAmount").getValue())) {
				return true;
			}
			if (!FDCHelper.isEqual(payPlanInfo.getDescription(), row.getCell(
					"description").getValue())) {
				return true;
			}
		}
		return false;
	}

	private void verify() throws SQLException {
		BigDecimal sum = FDCHelper.ZERO;
		Map monthMap = new HashMap();
		Date curDate = DateTimeUtils.truncateDate(new Date());

		//取系统参数
		FDCBudgetParam params=null;
		try {
			params=FDCBudgetParam.getInstance(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString());
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		BigDecimal totalPayProportion = FDCHelper.ZERO;
		for (int i = 0; i < this.tblPayPlan.getRowCount(); i++) {
			IRow row = this.tblPayPlan.getRow(i);
			Date payDate = (Date) row.getCell("payDate").getValue();
			if (payDate == null) {
				MsgBox.showError(ContractPayPlanHandler
						.getResource("PayDateNull"));
				this.tblPayPlan.getSelectManager().select(i, 0);
				this.abort();
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(payDate);
			int year = new Integer(cal.get(Calendar.YEAR)).intValue();
			int month = new Integer(cal.get(Calendar.MONTH)).intValue();
			String key = year + "_" + (month+1);
			if (monthMap.containsKey(key)) {
				MsgBox.showError(ContractPayPlanHandler
						.getResource("OneMonthOnePlan"));
				this.tblPayPlan.getSelectManager().select(i, 1);
				this.abort();
			}
			monthMap.put(key, payDate);
			BigDecimal payScale = (BigDecimal) row.getCell("payProportion").getValue();
			totalPayProportion =FDCHelper.add(totalPayProportion, payScale);
//			totalPayProportion = totalPayProportion.add(payScale);
			if (payScale == null || payScale.compareTo(FDCHelper.ZERO) <= 0) {
				MsgBox.showWarning(this, "付款比例不能为空");
				this.tblPayPlan.getSelectManager().select(i, 2);
				this.abort();
			}
			
			BigDecimal amount = (BigDecimal) row.getCell("payAmount")
					.getValue();
			if (amount == null || amount.compareTo(FDCHelper.ZERO) <= 0) {
				MsgBox.showError(ContractPayPlanHandler
						.getResource("PayAmountNull"));
				this.tblPayPlan.getSelectManager().select(i, 3);
				this.abort();
			}
			Date date = DateTimeUtils.truncateDate((Date) row
					.getCell("payDate").getValue());
			//计划金额都只取“返还履约保证金”值为否的当前月和之后月的分录。 by Cassiel_peng 2008-8-27
			boolean isBailAmount=row.getCell("isBailAmt").getValue()==null?false:((Boolean)row.getCell("isBailAmt").getValue()).booleanValue();
			if (!date.before(curDate)&&!isBailAmount) {
				sum = sum.add(amount);
			}

			String tmpInfo = "第" + (i+1) + "行 已付款金额已大于修改后的当月付款计划";
			BigDecimal hasPayAmount = (BigDecimal)row.getCell("hasPayAmount").getValue();
			if(hasPayAmount!=null&&amount!=null&&hasPayAmount.compareTo(amount)>0)
			{
				//严格控制
				if (params!=null && params.isStrictCtrl()) {
						MsgBox.showWarning(this, tmpInfo);
						this.abort();
				}else{
					if (MsgBox.showConfirm2(this,tmpInfo) == MsgBox.CANCEL) {
						this.abort();
					}
				}
			}
		}
		// PBG065635 启用参数->合同付款计划控制当月付款。合同付款计划，修改合同付款计划，月度累计付款计划超过合同最新造价也可以保存成功。
		// 请需求确认这里是否要进行超出合同最新造价的控制。 Added By Owen_wen 2010-11-09
		if(totalPayProportion.compareTo(FDCHelper.ONE_HUNDRED) > 0){
			int confirm = MsgBox.showConfirm2(this, "计划付款金额累计数中已超过合同最新造价，是否继续？");
			if (confirm == MsgBox.CANCEL)
				this.abort();
		}
		
		BigDecimal contractAmount = lastAmount;
		if (contractAmount == null) {
			contractAmount = FDCHelper.ZERO;
		}
		BigDecimal paymentAmount = null;
		try {
			paymentAmount = ContractPayPlanHandler.getPaymentAmount(contract.getId().toString(), null, curDate,true);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if (paymentAmount == null) {
			paymentAmount = FDCHelper.ZERO;
		}
		sum = sum.add(paymentAmount);
		if (sum.compareTo(contractAmount) > 0) {
			if (MsgBox.showConfirm2(this, "当前月之前的合同内工程款实付金额加本月及之后月份的计划金额大于合同最新造价，是否继续保存？") == MsgBox.CANCEL) {
				this.abort();
			}
		}
	}

	protected KDTable getDetailTable() {
		return this.tblPayPlan;
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		this.verify();
		Map map = this.getUIContext();
		String contractId = (String) map.get(UIContext.ID);

		//构建集合，后台保存
		ContractPayPlanCollection col = new ContractPayPlanCollection();
		BigDecimal proportion = GlUtils.zero;
		
		BigDecimal exechange = FDCHelper.toBigDecimal(exRate);
		CurrencyInfo currency = (CurrencyInfo)cbCurrency.getValue();
		CompanyOrgUnitInfo company = (CompanyOrgUnitInfo)contract.getCurProject().getFullOrgUnit().cast(CompanyOrgUnitInfo.class);
		
		for (int i = 0; i < this.tblPayPlan.getRowCount(); i++) {
			IRow row = this.tblPayPlan.getRow(i);
			ContractPayPlanInfo payPlan = (ContractPayPlanInfo) row
					.getUserObject();
			payPlan.setContractId(contract);
			payPlan.setPayDate((Date) row.getCell("payDate").getValue());
			
			BigDecimal payProportion = (BigDecimal) row.getCell("payProportion").getValue();
			payPlan.setPayProportion(payProportion);
			proportion = payProportion.add(proportion);
			
			payPlan.setPayOriAmount((BigDecimal) row.getCell("payOriAmount")
					.getValue());
			payPlan.setPayAmount((BigDecimal) row.getCell("payAmount")
					.getValue());
			payPlan.setDescription((String) row.getCell("description")
					.getValue());
			boolean isBailAmount=row.getCell("isBailAmt").getValue()==null?false:((Boolean)row.getCell("isBailAmt").getValue()).booleanValue();
			payPlan.setIsBailAmt(isBailAmount);
			payPlan.setCurrecy(currency);
			payPlan.setCurProject(contract.getCurProject());
			payPlan.setCompany(company);
			payPlan.setExchangeRate(exechange);
			
			payPlan.setSeq(i+1);
			
			col.add(payPlan);

		}
		
		if(proportion.compareTo(FDCConstants.ONE_HUNDRED)<0){
			int result  = MsgBox.showConfirm2(this,EASResource.getString(FDCConstants.FinanceResource,"ContractPayPlayProtectionNotEnagh"));
			if(result != MsgBox.OK){
				return ;
			}
		}
		
		ContractPayPlanFactory.getRemoteInstance().submitCol(contractId,col);
		
		showSaveSuccess();
		ContractPayPlanHandler.fillTable(contract.getId().toString(),
				this.tblPayPlan);
//		ContractPayPlanHandler.fillTable(contract.getId().toString(),this.tblPayPlan);
		//提单：R090104-108 合同付款计划录入后无法修改或者删除
		//允许修改/删除当月及以后月份的付款计划，但不允许修改/删除当月之前的付款计划
		setRowLock();
	}

	protected IObjectValue createNewData() {
		ContractPayPlanInfo objectValue = new ContractPayPlanInfo();
		try {
			objectValue.setCreateTime(FDCDateHelper.getServerTimeStamp());
//			objectValue.setSignDate(new Date(FDCDateHelper.getServerTimeStamp().getTime()));
		} catch (BOSException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
		}
		return objectValue;
	}
	
	//增加分录,默认当天
    protected IObjectValue createNewDetailData(KDTable table) {
    	ContractPayPlanInfo editdata = new ContractPayPlanInfo();
//    	editdata.setPayDate(new Date());
    	try {
    		editdata.setPayDate(new Date(FDCDateHelper.getServerTimeStamp().getTime()));
    		editdata.setIsBailAmt(false);
    	} catch (BOSException e1) {
		// TODO 自动生成 catch 块
		e1.printStackTrace();
	}
        return editdata;
    }

	protected void loadLineFields(KDTable table, IRow row, IObjectValue obj) {
		if(obj==null){
			obj = new ContractPayPlanInfo();
		}else{
			ContractPayPlanInfo contractPayPlanInfo =(ContractPayPlanInfo)obj;
			row.getCell("payDate").setValue(contractPayPlanInfo.getPayDate());
			row.getCell("isBailAmt").setValue(Boolean.valueOf(contractPayPlanInfo.isIsBailAmt()));
		}
		row.setUserObject(obj);
		this.tblPayPlan.setUserObject("AddRow");
	}

	public void setDataObject(IObjectValue dataObject) {

	}

	/**
	 * 根据id显示窗体
	 */
	public static void showEditUI(IUIObject ui, String contractId, String oprt)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put(UIContext.ID, contractId);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(ContractPayPlanEditUI.class.getName(), uiContext, null,
						oprt);
		uiWindow.show();
	}

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		return null;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		Map map = this.getUIContext();
		String contractId = (String) map.get(UIContext.ID);
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("amount");
		selector.add("settleAmt");
		selector.add("currency.number");
		selector.add("currency.name");
		selector.add("curProject.name");
		selector.add("curProject.fullOrgUnit.id");
		selector.add("exRate");
		selector.add("state");
		//hasSettled
		selector.add("hasSettled");
		
		contract = ContractBillFactory.getRemoteInstance().getContractBillInfo(
				new ObjectUuidPK(BOSUuid.read(contractId)),selector);
		
		this.cbCurrency.setValue(contract.getCurrency());
		
		exRate = contract.getExRate();
		this.txtEchangeRate.setText(String.valueOf(exRate.setScale(3,BigDecimal.ROUND_HALF_UP)));		
		
		//使用新的接口
		Map contractMap = FDCUtils.getLastAmt_Batch(null,new String[]{contractId});
		lastAmount = (BigDecimal)contractMap.get(contractId);
		//setScale方法使用参数BigDecimal.ROUND_HALF_UP，防止ArithMeticException
		this.txtContractAmount.setText(String.valueOf(lastAmount.setScale(2,BigDecimal.ROUND_HALF_UP)));
		ContractPayPlanHandler.fillTable(contract.getId().toString(),
				this.tblPayPlan);
		initControl();

		//提单：R090104-108 合同付款计划录入后无法修改或者删除
		//允许修改/删除当月及以后月份的付款计划，但不允许修改/删除当月之前的付款计划
		setRowLock();
	}

	private void initControl() {

		String state = this.getOprtState();
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setEnabled(false);
		this.actionAuditResult.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionViewSubmitProccess.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionWorkFlowG.setVisible(false);

		if (state.equals("VIEW") || state.equals(STATUS_FINDVIEW)) {
			this.tblPayPlan.getStyleAttributes().setLocked(true);
			this.actionAddLine.setVisible(false);
			this.actionAddLine.setEnabled(false);
			this.actionRemoveLine.setVisible(false);
			this.actionRemoveLine.setEnabled(false);
			this.actionInsertLine.setVisible(false);
			this.actionInsertLine.setEnabled(false);
			this.actionSubmit.setVisible(false);
			this.actionSubmit.setEnabled(false);
			this.menuBiz.setVisible(false);
			this.menuView.setVisible(false);
			this.menuWorkflow.setVisible(false);
			this.menuTool.setVisible(false);
			this.menuTable1.setVisible(false);
			this.menuEdit.setVisible(false);
			this.actionSubmitOption.setVisible(false);
		}
		if (contract.getState().equals(FDCBillStateEnum.CANCEL)) {
			this.actionAddLine.setVisible(false);
			this.actionAddLine.setEnabled(false);
			this.actionRemoveLine.setVisible(false);
			this.actionRemoveLine.setEnabled(false);
			this.actionInsertLine.setVisible(false);
			this.actionInsertLine.setEnabled(false);
			this.actionSubmit.setVisible(false);
			this.actionSubmit.setEnabled(false);
			this.actionSave.setEnabled(false);
			this.actionSave.setVisible(false);
			
			tblPayPlan.getStyleAttributes().setLocked(true);
			tblPayPlan.setEditable(false);
			tblPayPlan.setEnabled(false);
		}
		
		actionNextPerson.setVisible(false);
	}
	/**
	 * 如果同一个月份的月度付款计划已经是审批过了，合同的付款计划便不允许再被修改  by cassiel 2010-10-27 (私包,测试未介入) 
	 */
	//@Deprecated
	 protected void tblPayPlan_tableSelectChanged(KDTSelectEvent e)
				throws Exception {
		 	int rowIndex =  e.getSelectBlock().getBeginRow();
		 	if(rowIndex==-1){
		 		return;
		 	}
		 	Object o = tblPayPlan.getCell(rowIndex, "id").getValue();
		 	if(o==null){
		 		return;
		 	}
		 	String conPayPlanId = o.toString();
		 	SelectorItemCollection selector = new SelectorItemCollection();
		 	selector.add("auditor.id");
		 	ContractPayPlanInfo conPayPlan = ContractPayPlanFactory.getRemoteInstance().getContractPayPlanInfo(new ObjectUuidPK(conPayPlanId),selector);
		 	UserInfo auditor = conPayPlan.getAuditor();
		 	if(auditor != null){
		 		tblPayPlan.getRow(rowIndex).getStyleAttributes().setLocked(true);
		 		SysUtil.abort();
		 	}
		}
	
	protected void tblPayPlan_editStopped(KDTEditEvent e) throws Exception {
		super.tblPayPlan_editStopped(e);
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		IRow row = this.tblPayPlan.getRow(rowIndex);
		
        IColumn col = tblPayPlan.getColumn(colIndex);
        String colKey = col.getKey();
        
		// 修改日期列
        // 亿达补丁，取消日期的判断,可以录入本月之前的数据
		if ("payDate".endsWith(colKey)) {
			Date date = DateTimeUtils.truncateDate((Date) row
					.getCell("payDate").getValue());
//			Date firstDay=new Date(FDCSQLFacadeFactory.getRemoteInstance().getServerTime().getTime());
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(firstDay);
//			cal.set(Calendar.DATE, 1);
//			 firstDay = DateTimeUtils.truncateDate(cal.getTime());
//			if (date != null && date.before(firstDay)) {
//				MsgBox.showInfo(ContractPayPlanHandler
//						.getResource("DateBeforeCurMonth"));
//				row.getCell("payDate").setValue(e.getOldValue());
//				this.abort();
//			}
			Date oldDate = DateTimeUtils.truncateDate((Date)e.getOldValue());
			BigDecimal hasPayAmount = (BigDecimal)row.getCell("hasPayAmount").getValue();
			if(hasPayAmount!=null&&!(date.getYear()==oldDate.getYear()&&date.getMonth()==oldDate.getMonth())){
				MsgBox.showInfo(this,"已存在当月已付金额，不能修改月份");
				row.getCell("payDate").setValue(e.getOldValue());
				this.abort();
			}
		}
		//BT251401修改比例后，计算金额
		if ("payProportion".endsWith(colKey)) {
			BigDecimal payProportion = (BigDecimal) row
					.getCell("payProportion").getValue();
			if (payProportion != null && lastAmount != null) {
				BigDecimal payAmount = lastAmount.multiply(payProportion)
						.divide(FDCConstants.ONE_HUNDRED, 2,
								BigDecimal.ROUND_HALF_UP);
				if (payAmount.compareTo(FDCHelper.MAX_DECIMAL) >= 0) {
					
					this.abort();
				}
				BigDecimal hasPayAmount = (BigDecimal)row.getCell("hasPayAmount").getValue();
				if(hasPayAmount!=null&&payAmount!=null&&payAmount.compareTo(hasPayAmount)<0)
				{
					MsgBox.showInfo(this,"修改后的付款金额小于当月已付金额");
					row.getCell("payProportion").setValue(e.getOldValue());
					this.abort();
				}
				row.getCell("payAmount").setValue(payAmount);
				
				BigDecimal originalAmt = FDCHelper.toBigDecimal(exRate);
				row.getCell("payOriAmount").setValue(payAmount.divide(originalAmt,2,BigDecimal.ROUND_HALF_DOWN));
			}
		}
		//BT251401修改金额后，计算比例
		if ("payAmount".endsWith(colKey)) {
			BigDecimal payAmount = (BigDecimal) row.getCell("payAmount")
					.getValue();
			if (payAmount != null && lastAmount != null
					&& lastAmount.compareTo(FDCHelper.ZERO) != 0) {
				BigDecimal payProportion = payAmount.multiply(
						new BigDecimal(100)).divide(lastAmount, 2,
						BigDecimal.ROUND_HALF_UP);
				BigDecimal hasPayAmount = (BigDecimal)row.getCell("hasPayAmount").getValue();
				
				if(hasPayAmount!=null&&payAmount!=null&&payAmount.compareTo(hasPayAmount)<0)
				{
					MsgBox.showInfo(this,"修改后的付款金额小于当月已付金额");
					row.getCell("payAmount").setValue(e.getOldValue());
					this.abort();
				}
				row.getCell("payProportion").setValue(payProportion);
				
				//计算原币
				BigDecimal originalAmt = FDCHelper.toBigDecimal(exRate);
				row.getCell("payOriAmount").setValue(payAmount.divide(originalAmt,2,BigDecimal.ROUND_HALF_DOWN));
			}
		}
	}

	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		KDTSelectManager selectManager = tblPayPlan.getSelectManager();
		if (selectManager == null || selectManager.size() == 0) {
			MsgBox.showWarning(this, "请先选中行");
			SysUtil.abort();
		}
		for (int i = 0; i < selectManager.size(); i++) {
			KDTSelectBlock selectBlock = selectManager.get(i);
			for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
				IRow selectRow = this.tblPayPlan.getRow(j);
				
				if(selectRow == null || selectRow.getRowType() == KDTStyleConstants.HEAD_ROW) continue;
				
				//已经有当月已付，不允许删除
				if(selectRow.getCell("hasPayAmount").getValue()!=null){
					return;
				}
				String description = (String) selectRow.getCell("description").getValue();
				if(description != null && description.indexOf("月计划审批汇总生成")>0){
					return;
				}
				if (selectRow.getStyleAttributes().isLocked()) {
					if(MsgBox.CANCEL==MsgBox.showConfirm2(this, "请确认要删除之前的付款计划吗？")){
						return;
					}
				}
				if (selectRow.getUserObject() != null) {
					selectRow.getCell("payDate").setUserObject("delete");
				}
				
				Object o = selectRow.getCell("id").getValue();
			 	if(o!=null){
			 		String conPayPlanId = o.toString();
				 	SelectorItemCollection selector = new SelectorItemCollection();
				 	selector.add("auditor.id");
				 	ContractPayPlanInfo conPayPlan = ContractPayPlanFactory.getRemoteInstance().getContractPayPlanInfo(new ObjectUuidPK(conPayPlanId),selector);
				 	UserInfo auditor = conPayPlan.getAuditor();
				 	if(auditor != null){
				 		FDCMsgBox.showInfo("本月的月度付款计划已经审批，不允许再删除该月的合同付款计划！");
				 		SysUtil.abort();
				 	}
			 	}
			}
		}
		for (int i = 0; i < tblPayPlan.getRowCount(); i++) {
			IRow row = (IRow) tblPayPlan.getRow(i);
			if (row.getCell("payDate").getUserObject() != null) {
				this.tblPayPlan.removeRow(row.getRowIndex());
				i--;
			}
		}
		this.tblPayPlan.setUserObject("DeleteRow");
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractPayPlanFactory.getRemoteInstance();
	}
	

	private void getMonthBudgetAcctData()throws BOSException, SQLException {
		tableDataMap = new HashMap();
		String contractId = contract.getId().toString();
		
		StringBuffer mySql = new StringBuffer();		
		mySql.append("select a.fprojectid,b.fyear,b.fmonth from T_FNC_FDCMonthBudgetAcct a 		\r\n");
		mySql.append("inner join T_FNC_FDCBudgetPeriod b on a.FfdcPeriodId=b.fid		\r\n");
		mySql.append("inner join T_CON_ContractBill c on a.fprojectid=c.fcurprojectid		\r\n");
		mySql.append("where b.fisyear=0 and a.fstate='4AUDITTED' and c.fid='" + contractId + "' 		\r\n");
		
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(mySql.toString()).executeSQL();
		
		String myKey = null;
		while (rs.next()) {
			myKey = rs.getString("fyear") + "_" + rs.getString("fmonth");
			this.tableDataMap.put(myKey,rs.getString("fprojectid"));
		}
	}
	
	//提单：R090104-108 合同付款计划录入后无法修改或者删除
	//允许修改/删除当月及以后月份的付款计划，但不允许修改/删除当月之前的付款计划
	private void setRowLock() throws EASBizException, BOSException{
		if(ContractPayPlanListUI.beingEdit() || PayPlanUI.payPlanEdit()){
			//取当前工程项目的月度计划申请数据
			try {
				getMonthBudgetAcctData();
			} catch (SQLException err) {
				err.printStackTrace();
			} catch (BOSException err) {
				err.printStackTrace();
			}
			for (int i = 0; i < this.tblPayPlan.getRowCount(); i++) {
				IRow row = this.tblPayPlan.getRow(i);
				year = ((Date) row.getCell("payDate").getValue()).getYear();
				month = ((Date) row.getCell("payDate").getValue()).getMonth();
				String key = (year+1900) + "_" + (month+1);
				boolean isLock = false;
				
				//如果系统当前月份的月度付款计划未审批，不能锁定，需要支持修改 by cassiel
			 	Object o =row.getCell("id").getValue();
				String conPayPlanId = o.toString();
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("auditor.id");
				ContractPayPlanInfo conPayPlan = ContractPayPlanFactory.getRemoteInstance().getContractPayPlanInfo(new ObjectUuidPK(conPayPlanId),selector);
				UserInfo auditor = conPayPlan.getAuditor();
				if(year>time.getYear()){
					if(tableDataMap.containsKey(key)){
						isLock = (year-time.getYear()==1 && month-time.getMonth()<3) ? true:false;
					}else{
						isLock = false;
					}									
				}else if((year==time.getYear())){
					if(month>=time.getMonth()){
						if(tableDataMap.containsKey(key)){
							isLock = month-time.getMonth()<3? true:false;
							if(auditor == null){
						 		isLock = false;
						 	}
						}else{
							isLock = false;
						}							
					}else{
						isLock = true;
					}					
				}else if(year < time.getYear()){
					isLock = true;
				}
				if(auditor != null){
			 		isLock=true;
			 	}
				String description = (String) row.getCell("description").getValue();
				if(description != null && description.indexOf("月计划审批汇总生成")>0){
					isLock=true;
				}
				row.getStyleAttributes().setLocked(isLock);
			}
		}
	}
}