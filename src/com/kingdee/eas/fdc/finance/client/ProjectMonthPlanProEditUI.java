/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.ActionMap;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.data.SortType;
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
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.CostAccountWithBgItemFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PayPlanCycleFactory;
import com.kingdee.eas.fdc.basedata.PayPlanCycleInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryCollection;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryFactory;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.BgItemObject;
import com.kingdee.eas.ma.budget.client.NewBgItemDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class ProjectMonthPlanProEditUI extends AbstractProjectMonthPlanProEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectMonthPlanProEditUI.class);
    private boolean canSelectOtherOrgPerson = false;
    private KDTable proTable=null;
    private Boolean isLoad=false;
    private int year_old = 0;
	private int month_old =0;
	private Boolean isPro=true;
    public ProjectMonthPlanProEditUI() throws Exception
    {
        super();
    }

    protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ProjectMonthPlanProFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
		
		actionAudit_actionPerformed(e);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	protected void initControl() throws EASBizException, BOSException {
		this.txtNumber.setMaxLength(255);

		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		this.actionWorkFlowG.setVisible(false);
		this.actionAuditResult.setVisible(false);
		
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPre.setVisible(false);
		this.menuView.setVisible(false);
		
		this.menuBiz.setVisible(false);
		this.menuWorkflow.setVisible(false);
		this.actionAddNew.setVisible(false);
		
		this.btnSubmit.setText("提交&审批");
		this.btnSubmit.setToolTipText("提交&审批");
		this.txtVersion.setPrecision(1);
		
		this.spYear.setModel(new SpinnerNumberModel(this.spYear.getIntegerVlaue().intValue(),1,10000,1));
		this.spMonth.setModel(new SpinnerNumberModel(this.spMonth.getIntegerVlaue().intValue(),1,12,1));
		
		this.btnGet.setVisible(false);
		
		String cuId=SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
    	Map param = (Map) ActionCache.get("FDCBillEditUIHandler.orgParamItem");
		if (param == null) {
			param = FDCUtils.getDefaultFDCParam(null, SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getId().toString());
		}
		if (param.get(FDCConstants.FDC_PARAM_SELECTPERSON) != null) {
			canSelectOtherOrgPerson = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SELECTPERSON).toString()).booleanValue();
		}
		FDCClientUtils.setRespDeptF7(prmtRespDept, this,canSelectOtherOrgPerson ? null : cuId);
		FDCClientUtils.setPersonF7(prmtRespPerson, this,canSelectOtherOrgPerson ? null : cuId);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionALine.setEnabled(false);
			this.actionILine.setEnabled(false);
			this.actionRLine.setEnabled(false);
			
			this.btnGet.setEnabled(false);
			if(this.proTable!=null){
				this.proTable.setEnabled(false);
			}
		} else {
			this.unLockUI();
			this.actionALine.setEnabled(true);
			this.actionILine.setEnabled(true);
			this.actionRLine.setEnabled(true);
			
			this.btnGet.setEnabled(true);
			if(this.proTable!=null){
				this.proTable.setEnabled(true);
			}
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
		initControl();
	}
	public void storeFields(){
		this.editData.getEntry().clear();
		for(int i=0;i<this.proTable.getRowCount();i++){
			ProjectMonthPlanProEntryInfo entry=(ProjectMonthPlanProEntryInfo) this.proTable.getRow(i).getUserObject();
			if(entry!=null){
				this.editData.getEntry().add(entry);
			}
		}
		int year=this.spYear.getIntegerVlaue().intValue();
		int month=this.spMonth.getIntegerVlaue().intValue();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		this.editData.setBizDate(cal.getTime());
		this.txtName.setText(year+"年"+month+"月"+"无合同费用付款计划（"+this.editData.getCurProject().getName()+"）（"+SysContext.getSysContext().getCurrentUserInfo().getName()+"）");
		
		super.storeFields();
	}
	public void loadFields() {
		if(isPro==null){
			HashMap hmParamIn = new HashMap();
			hmParamIn.put("CIFI_PROJECTPLANPRO", this.editData.getOrgUnit().getId().toString());
			try {
				HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
				if(hmAllParam.get("CIFI_PROJECTPLANPRO")!=null){
					isPro=Boolean.parseBoolean(hmAllParam.get("CIFI_PROJECTPLANPRO").toString());
				}else{
					isPro=false;
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		isLoad=true;
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		if(this.editData.getBizDate()!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.editData.getBizDate());
			int year=cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;
			
			year_old = year;
			month_old = month;
			
			this.spYear.setValue(year);
			this.spMonth.setValue(month);
		}
		loadEntry();
		if(this.txtVersion.getIntegerValue().intValue()>1){
			this.prmtRespPerson.setEnabled(false);
			this.prmtRespDept.setEnabled(false);
		}
		attachListeners();
		setOprtState(this.getOprtState());
		isLoad=false;
	}
	protected void initMonthColoum(KDTable table,int year,int month,int cycle){
		KDBizPromptBox f7Box = new KDBizPromptBox();
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$name$");
		f7Box.setCommitFormat("$name$");
		NewBgItemDialog bgItemDialog=new NewBgItemDialog(this);
		bgItemDialog.setMulSelect(false);
		bgItemDialog.setSelectCombinItem(false);
		f7Box.setSelector(bgItemDialog);
		f7Editor = new KDTDefaultCellEditor(f7Box);
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		amount.setNegatived(false);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		
		KDTextField remark = new KDTextField();
		remark.setMaxLength(255);
		KDTDefaultCellEditor remarkEditor = new KDTDefaultCellEditor(remark);
		for(int i=0;i<cycle;i++){
			if (month > 12) {
				year += 1;
				month = 1;
			}
			String monthStr= year + "年" + month + "月";
			String key=year+"year"+month+"m";
			
			IColumn amountColumn=table.addColumn();
			amountColumn.setKey(key+"amount");
			table.getColumn(key+"amount").setEditor(amountEditor);
			table.getColumn(key+"amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			table.getColumn(key+"amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			amountColumn.getStyleAttributes().setLocked(false);
			amountColumn.setRequired(true);
			
			IColumn remarkColumn=table.addColumn();
			remarkColumn.setEditor(remarkEditor);
			remarkColumn.setKey(key+"remark");
			remarkColumn.getStyleAttributes().setLocked(false);
			remarkColumn.setRequired(true);
			
			IColumn bgItemColumn=table.addColumn();
			bgItemColumn.setKey(key+"bgItem");
			bgItemColumn.getStyleAttributes().setLocked(false);
			bgItemColumn.setRequired(true);
			
			table.getColumn(key+"bgItem").setEditor(f7Editor);
			
			table.getHeadRow(0).getCell(key+"amount").setValue(monthStr);
			table.getHeadRow(0).getCell(key+"remark").setValue(monthStr);
			table.getHeadRow(0).getCell(key+"bgItem").setValue(monthStr);
			
			table.getHeadRow(1).getCell(key+"amount").setValue("计划支付");
			table.getHeadRow(1).getCell(key+"remark").setValue("用款说明");
			table.getHeadRow(1).getCell(key+"bgItem").setValue("预算项目");
			
			int merge=table.getHeadRow(0).getCell(key+"amount").getColumnIndex();
			table.getHeadMergeManager().mergeBlock(0, merge, 0, merge+2);
			
			month++;
		}
	}
	protected void initTableBtn(KDTable table,String title){
		KDContainer contEntry = new KDContainer();
		contEntry.setName(table.getName());
		contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        
		contEntry.getContentPane().add(table, BorderLayout.CENTER);
		
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnInsertRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)contEntry.add(this.actionALine);
		btnAddRowinfo.setText("新增行");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton) contEntry.add(this.actionILine);
		btnInsertRowinfo.setText("插入行");
		btnInsertRowinfo.setSize(new Dimension(140, 19));

		this.actionRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.pnlBig.add(contEntry, title);
	}
	protected void initProTable(int year,int month,int cycle) {
		this.proTable=new KDTable();
		this.proTable.checkParsed();
		IRow headRow=this.proTable.addHeadRow();
		IRow headRowName=this.proTable.addHeadRow();
		
		this.proTable.getStyleAttributes().setLocked(true);
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		amount.setNegatived(false);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		
		IColumn column=this.proTable.addColumn();
		column.setKey("number");
		headRow.getCell("number").setValue("合约规划编码");
		headRowName.getCell("number").setValue("合约规划编码");
		
		KDBizPromptBox prmtcontract = new KDBizPromptBox();
		prmtcontract.setDisplayFormat("$name$");
		prmtcontract.setEditFormat("$number$");
		prmtcontract.setCommitFormat("$number$");
		prmtcontract.setQueryInfo("com.kingdee.eas.fdc.contract.programming.app.ProgrammingContractF7Query");

		prmtcontract.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				KDBizPromptBox f7 = (KDBizPromptBox) e.getSource();
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				
				filter.getFilterItems().add(new FilterItemInfo("programming.state",FDCBillStateEnum.AUDITTED_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("programming.project.id",editData.getCurProject().getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("programming.isLatest",Boolean.TRUE));
				filter.getFilterItems().add(new FilterItemInfo("programming.isCiting",Boolean.FALSE));
				filter.getFilterItems().add(new FilterItemInfo("id","select fparentid from T_CON_ProgrammingContract where fparentid is not null",CompareType.NOTINNER));
				
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
			}
		});
		KDTDefaultCellEditor contractEditor=new KDTDefaultCellEditor(prmtcontract);
		column.setEditor(contractEditor);
		column.getStyleAttributes().setLocked(false);
		ObjectValueRender ovrNum = new ObjectValueRender();
		ovrNum.setFormat(new BizDataFormat("$number$"));
		column.setRenderer(ovrNum);
		if(this.isPro){
			column.getStyleAttributes().setHided(true);
		}else{
			column.setRequired(true);
		}
		
		column=this.proTable.addColumn();
		column.setKey("name");
		if(this.isPro){
			headRow.getCell("name").setValue("事项");
			headRowName.getCell("name").setValue("事项");
			column.getStyleAttributes().setLocked(false);
			column.setRequired(true);
		}else{
			headRow.getCell("name").setValue("合约规划名称");
			headRowName.getCell("name").setValue("合约规划名称");
		}
		
		column=this.proTable.addColumn();
		column.setKey("amount");
		if(this.isPro){
			headRow.getCell("amount").setValue("金额");
			headRowName.getCell("amount").setValue("金额");
		}else{
			headRow.getCell("amount").setValue("规划金额");
			headRowName.getCell("amount").setValue("规划金额");
		}
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		column=this.proTable.addColumn();
		column.setKey("actPayAmount");
		headRow.getCell("actPayAmount").setValue("累计实付金额");
		headRowName.getCell("actPayAmount").setValue("累计实付金额");
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
        
		this.proTable.setName("proTable");
		this.proTable.getViewManager().setFreezeView(0, 4);
		this.proTable.getHeadMergeManager().mergeBlock(0, 0, 1, 0);
		this.proTable.getHeadMergeManager().mergeBlock(0, 1, 1, 1);
		this.proTable.getHeadMergeManager().mergeBlock(0, 2, 1, 2);
		this.proTable.getHeadMergeManager().mergeBlock(0, 3, 1, 3);
		
		initMonthColoum(this.proTable,year,month,cycle);
		initTableBtn(this.proTable,"无合同费用付款计划");
		
		ActionMap actionMap = this.proTable.getActionMap();
		actionMap.remove(KDTAction.CUT);
		actionMap.remove(KDTAction.DELETE);
		actionMap.remove(KDTAction.PASTE);
		
		this.proTable.addKDTEditListener(new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				table_editStopped(e);
			}
		});
	}
	private BigDecimal getActPayAmount(String id) throws BOSException, SQLException{
		BigDecimal actPayAmount=null;
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql("select sum(sumCount) sumCount from (select sum(pay.FAmount) sumCount from t_cas_paymentbill pay left join t_con_contractBill bill on bill.fid=pay.fcontractbillid where pay.fbillstatus=15 and bill.FProgrammingContract='"+id+"'");
		_builder.appendSql("union all select sum(pay.FAmount) sumCount from t_cas_paymentbill pay left join t_con_contractWithoutText bill on bill.fid=pay.fcontractbillid where pay.fbillstatus=15 and bill.FProgrammingContract='"+id+"')");
		final IRowSet rowSet = _builder.executeQuery();
		if (rowSet.size() == 1) {
			rowSet.next();
			actPayAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("sumCount"));
		}
		return actPayAmount;
	}
	protected void loadEntry(){
		ProjectMonthPlanProEntryCollection col=this.editData.getEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.pnlBig.removeAll();
		
		int spYear=this.spYear.getIntegerVlaue().intValue();
		int spMonth=this.spMonth.getIntegerVlaue().intValue();
		int cycle=this.editData.getCycle().getCycle().getValue();
		this.initProTable(spYear,spMonth,cycle);
		
		Map rowMap=new HashMap();
		IRow row=null;
		for(int i=0;i<col.size();i++){
			ProjectMonthPlanProEntryInfo entry=col.get(i);
			String id=null;
			BigDecimal actPayAmount=null;
			if(entry.getProgrammingContract()!=null){
				id=entry.getProgrammingContract().getId().toString();
				try{
					actPayAmount=getActPayAmount(id);
				} catch (BOSException e1) {
					e1.printStackTrace();
				} catch (UuidException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}else{
				id=entry.getId().toString();
			}
			for(int j=0;j<entry.getDateEntry().size();j++){
				ProjectMonthPlanProDateEntryInfo dateEntry=entry.getDateEntry().get(j);
				int month=dateEntry.getMonth();
				int year=dateEntry.getYear();
				
				if(rowMap.containsKey(id)){
					row=(IRow) rowMap.get(id);
				}else{
					row=this.proTable.addRow();
					row.setUserObject(entry);
					row.getCell("name").setValue(entry.getName());
					row.getCell("amount").setValue(entry.getAmount());
					row.getCell("number").setValue(entry.getProgrammingContract());
					row.getCell("actPayAmount").setValue(actPayAmount);
					rowMap.put(id, row);
				}
				
				String key=year+"year"+month+"m";
				
				if(row.getCell(key+"amount")!=null){
					row.getCell(key+"amount").setUserObject(dateEntry);
					row.getCell(key+"amount").setValue(dateEntry.getAmount());
				}
				if(row.getCell(key+"remark")!=null){
					row.getCell(key+"remark").setUserObject(dateEntry);
					row.getCell(key+"remark").setValue(dateEntry.getRemark());
				}
				if(row.getCell(key+"bgItem")!=null){
					row.getCell(key+"bgItem").setUserObject(dateEntry);
					row.getCell(key+"bgItem").setValue(dateEntry.getBgItem());
				}
			}
		}
		String amountColoun[]=new String[cycle+1];
		for(int i=0;i<cycle+1;i++){
			if(i==0){
				amountColoun[0]="amount";
			}else{
				if (spMonth > 12) {
					spYear += 1;
					spMonth = 1;
				}
				String key=spYear+"year"+spMonth+"m";
				amountColoun[i]=key+"amount";
				
				spMonth++;
			}
		}
		CRMClientHelper.getFootRow(this.proTable, amountColoun);
		CRMClientHelper.getFootRow(this.proTable, new String[]{"actPayAmount"});
	}
	private void table_editStopped(KDTEditEvent e) {
		KDTable table = (KDTable) e.getSource();
		if(table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()!=null
				&&table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject() instanceof ProjectMonthPlanProDateEntryInfo){
			if(table.getColumnKey(e.getColIndex()).indexOf("remark")>0){
				((ProjectMonthPlanProDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setRemark((String)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
			}else if(table.getColumnKey(e.getColIndex()).indexOf("amount")>0){
				((ProjectMonthPlanProDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setAmount((BigDecimal)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
				CRMClientHelper.getFootRow(table, new String[]{table.getColumnKey(e.getColIndex())});
				
				if(this.isPro){
					int spYear=this.spYear.getIntegerVlaue().intValue();
					int spMonth=this.spMonth.getIntegerVlaue().intValue();
					int cycle=this.editData.getCycle().getCycle().getValue();
					
					BigDecimal amount=FDCHelper.ZERO;
					for(int i=0;i<cycle;i++){
						if (spMonth > 12) {
							spYear += 1;
							spMonth = 1;
						}
						String key=spYear+"year"+spMonth+"m";
						amount=FDCHelper.add(amount, (BigDecimal)table.getRow(e.getRowIndex()).getCell(key+"amount").getValue());
						spMonth++;
					}
					table.getRow(e.getRowIndex()).getCell("amount").setValue(amount);
					((ProjectMonthPlanProEntryInfo)table.getRow(e.getRowIndex()).getUserObject()).setAmount(amount);
				}
			}else if(table.getColumnKey(e.getColIndex()).indexOf("bgItem")>0){
				if(table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue() instanceof BgItemObject){
					BgItemObject bgItem=(BgItemObject) table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue();
					if(bgItem!=null){
						if(!bgItem.getResult().get(0).isIsLeaf()){
							FDCMsgBox.showWarning(this,"请选择明细预算项目！");
							table.getRow(e.getRowIndex()).getCell(e.getColIndex()).setValue(null);
							((ProjectMonthPlanProDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setBgItem(null);
						}else{
							table.getRow(e.getRowIndex()).getCell(e.getColIndex()).setValue(bgItem.getResult().get(0));
							((ProjectMonthPlanProDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setBgItem(bgItem.getResult().get(0));
							
							int spYear=this.spYear.getIntegerVlaue().intValue();
							int spMonth=this.spMonth.getIntegerVlaue().intValue();
							int cycle=this.editData.getCycle().getCycle().getValue();
							
							String key=spYear+"year"+spMonth+"m";
							if((key+"bgItem").equals(table.getColumnKey(e.getColIndex()))){
								for(int i=0;i<cycle;i++){
									if (spMonth > 12) {
										spYear += 1;
										spMonth = 1;
									}
									key=spYear+"year"+spMonth+"m";
									table.getRow(e.getRowIndex()).getCell(key+"bgItem").setValue(bgItem.getResult().get(0));
									((ProjectMonthPlanProDateEntryInfo)table.getRow(e.getRowIndex()).getCell(key+"bgItem").getUserObject()).setBgItem(bgItem.getResult().get(0));
									
									spMonth++;
								}
							}
							
						}
					}
				}else if(table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue() instanceof BgItemInfo){
					((ProjectMonthPlanProDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setBgItem((BgItemInfo) table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
				}else{
					((ProjectMonthPlanProDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setBgItem(null);
				}
			}
		}
		if(table.getColumnKey(e.getColIndex()).equals("number")){
			ProjectMonthPlanProEntryInfo entry=(ProjectMonthPlanProEntryInfo) table.getRow(e.getRowIndex()).getUserObject();
			
			int spYear=this.spYear.getIntegerVlaue().intValue();
			int spMonth=this.spMonth.getIntegerVlaue().intValue();
			int cycle=this.editData.getCycle().getCycle().getValue();
			
			ProgrammingContractInfo pro=(ProgrammingContractInfo) table.getRow(e.getRowIndex()).getCell("number").getValue();
			String name=null;
			BigDecimal amount=null;
			if(pro!=null){
				name=pro.getName();
				amount=pro.getAmount();
			}else{
				for(int k=0;k<cycle;k++){
					if (spMonth > 12) {
						spYear += 1;
						spMonth = 1;
					}
					String key=spYear+"year"+spMonth+"m";
					ICell amountCell=table.getRow(e.getRowIndex()).getCell(key+"amount");
					if(amountCell!=null){
						ProjectMonthPlanProDateEntryInfo ppDateEntry=(ProjectMonthPlanProDateEntryInfo) amountCell.getUserObject();
						if(ppDateEntry==null){
							ppDateEntry=new ProjectMonthPlanProDateEntryInfo();
							ppDateEntry.setYear(spYear);
							ppDateEntry.setMonth(spMonth);
							entry.getDateEntry().add(ppDateEntry);
						}
						amountCell.setValue(null);
						ppDateEntry.setAmount(null);
					}
					ICell remarkCell=table.getRow(e.getRowIndex()).getCell(key+"remark");
					if(remarkCell!=null){
						ProjectMonthPlanProDateEntryInfo ppDateEntry=(ProjectMonthPlanProDateEntryInfo) amountCell.getUserObject();
						if(ppDateEntry==null){
							ppDateEntry=new ProjectMonthPlanProDateEntryInfo();
							ppDateEntry.setYear(spYear);
							ppDateEntry.setMonth(spMonth);
							entry.getDateEntry().add(ppDateEntry);
						}
						remarkCell.setValue(null);
						ppDateEntry.setRemark(null);
					}
					ICell bgItemCell=table.getRow(e.getRowIndex()).getCell(key+"bgItem");
					if(remarkCell!=null){
						ProjectMonthPlanProDateEntryInfo ppDateEntry=(ProjectMonthPlanProDateEntryInfo) bgItemCell.getUserObject();
						if(ppDateEntry==null){
							ppDateEntry=new ProjectMonthPlanProDateEntryInfo();
							ppDateEntry.setYear(spYear);
							ppDateEntry.setMonth(spMonth);
							entry.getDateEntry().add(ppDateEntry);
						}
						bgItemCell.setValue(null);
						ppDateEntry.setBgItem(null);
					}
					spMonth++;
				}
			}
			table.getRow(e.getRowIndex()).getCell("name").setValue(name);
			table.getRow(e.getRowIndex()).getCell("amount").setValue(amount);
			try {
				if(pro!=null){
					table.getRow(e.getRowIndex()).getCell("actPayAmount").setValue(getActPayAmount(pro.getId().toString()));
				}else{
					table.getRow(e.getRowIndex()).getCell("actPayAmount").setValue(null);
				}
			} catch (BOSException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			entry.setProgrammingContract(pro);
			entry.setName(name);
			entry.setAmount(amount);
			
			int totalYear=this.spYear.getIntegerVlaue().intValue();
			int totalMonth=this.spMonth.getIntegerVlaue().intValue();
			String amountColoun[]=new String[cycle+1];
			for(int k=0;k<cycle+1;k++){
				if(k==0){
					amountColoun[0]="amount";
				}else{
					if (totalMonth > 12) {
						totalYear += 1;
						totalMonth = 1;
					}
					String key=totalYear+"year"+totalMonth+"m";
					amountColoun[k]=key+"amount";
					
					totalMonth++;
				}
			}
			CRMClientHelper.getFootRow(table, amountColoun);
		}else if(table.getColumnKey(e.getColIndex()).equals("name")){
			((ProjectMonthPlanProEntryInfo)table.getRow(e.getRowIndex()).getUserObject()).setName((String)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
		}
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel = super.getSelectors();
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("entry.*");
    	sel.add("entry.programmingContract.*");
    	sel.add("entry.dateEntry.*");
    	sel.add("entry.dateEntry.bgItem.*");
    	sel.add("bizDate");
		return sel;
	}
	protected IObjectValue createNewData() {
		ProjectMonthPlanProInfo info=(ProjectMonthPlanProInfo)this.getUIContext().get("info");
		if(info==null){
			info= new ProjectMonthPlanProInfo();
			info.setVersion(1);
			Date now=new Date();
			try {
				now=FDCCommonServerHelper.getServerTimeStamp();
			} catch (BOSException e) {
				logger.error(e.getMessage());
			}
			info.setBizDate(FDCDateHelper.getNextMonth(now));
			if((CurProjectInfo)this.getUIContext().get("curProject")!=null){
				info.setCurProject((CurProjectInfo)this.getUIContext().get("curProject"));
				try {
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(info.getCurProject().getFullOrgUnit().getId()));
					info.setOrgUnit(orgUnitInfo);
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}else{
				FDCMsgBox.showWarning(this,"项目为空！");
	    		SysUtil.abort();
			}
		}else{
			info.setVersion(info.getVersion()+1);
			info.setId(null);
			for(int i=0;i<info.getEntry().size();i++){
				info.getEntry().get(i).setId(BOSUuid.create(info.getEntry().get(i).getBOSType()));
				for(int j=0;j<info.getEntry().get(i).getDateEntry().size();j++){
					info.getEntry().get(i).getDateEntry().get(j).setId(BOSUuid.create(info.getEntry().get(i).getDateEntry().get(j).getBOSType()));
				}
			}
		}
		info.setState(FDCBillStateEnum.SAVED);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("cycle");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		try {
			FDCDataBaseCollection col = PayPlanCycleFactory.getRemoteInstance().getFDCDataBaseCollection(view);
			if (col != null && col.size() > 0) {
				PayPlanCycleInfo cycle = (PayPlanCycleInfo) col.get(0);
				info.setCycle(cycle);
			} else {
				FDCMsgBox.showWarning(this, "必须启用一套付款计划周期!");
				SysUtil.abort();
			}
		} catch (BOSException e) {
			handUIException(e);
		}
		if(SysContext.getSysContext().getCurrentUserInfo().getPerson()!=null){
			try {
				info.setRespDept(getDepByPerson(SysContext.getSysContext().getCurrentUserInfo().getPerson()));
				info.setRespPerson(SysContext.getSysContext().getCurrentUserInfo().getPerson());
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		info.setName(null);
		info.setCreator(null);
		info.setCreateTime(null);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setLastUpdateUser(null);
		info.setLastUpdateTime(null);
		return info;
	}
	
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtCurProject);
	}
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtRespPerson);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtRespDept);
		if(checkBizDate()){
			SysUtil.abort();
		}
		IRow headRow=this.proTable.getHeadRow(0);
		IRow headRowTwo=this.proTable.getHeadRow(1);
		for(int i=0;i<this.proTable.getRowCount();i++){
			IRow row=this.proTable.getRow(i);
			BigDecimal amount=FDCHelper.ZERO;
			for(int j=0;j<this.proTable.getColumnCount();j++){
				if(this.proTable.getColumn(j).isRequired()){
					if(row.getCell(j).getValue()!=null&&row.getCell(j).getValue() instanceof String){
						if("".equals(row.getCell(j).getValue().toString().trim())){
							FDCMsgBox.showWarning(this,headRow.getCell(j).getValue().toString()+headRowTwo.getCell(j).getValue().toString()+"不能为空！");
							this.proTable.getEditManager().editCellAt(i, j);
							SysUtil.abort();
						}
					}else if(row.getCell(j).getValue()==null){
						FDCMsgBox.showWarning(this,headRow.getCell(j).getValue().toString()+headRowTwo.getCell(j).getValue().toString()+"不能为空！");
						this.proTable.getEditManager().editCellAt(i, j);
						SysUtil.abort();
					}
				}
				if(this.proTable.getColumnKey(j).indexOf("amount")>0){
					amount=FDCHelper.add(amount, row.getCell(j).getValue());
				}
			}
			ProjectMonthPlanProEntryInfo entry=(ProjectMonthPlanProEntryInfo) row.getUserObject();
			if(entry.getProgrammingContract()!=null){
				BigDecimal totalAmount=(BigDecimal) (row.getCell("amount").getValue()==null?FDCHelper.ZERO:row.getCell("amount").getValue());
				BigDecimal actPayAmount=(BigDecimal) (row.getCell("actPayAmount").getValue()==null?FDCHelper.ZERO:row.getCell("actPayAmount").getValue());
				if(totalAmount.subtract(actPayAmount).compareTo(amount)<0){
					FDCMsgBox.showWarning(this,"第"+(i+1)+"行 计划支付汇总不能超过 规划金额-累计实付金额！");
					SysUtil.abort();
				}
			}
		}
	}
	protected KDTable getSelectTable(){
		for(int i=0;i<((KDContainer)this.pnlBig.getSelectedComponent()).getContentPane().getComponentCount();i++){
			if(((KDContainer)this.pnlBig.getSelectedComponent()).getContentPane().getComponent(i) instanceof KDTable){
				return (KDTable) ((KDContainer)this.pnlBig.getSelectedComponent()).getContentPane().getComponent(i);
			}
		}
		return null;
	}
	public void actionALine_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=getSelectTable();
		IRow row = table.addRow();
		ProjectMonthPlanProEntryInfo entry = new ProjectMonthPlanProEntryInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		row.setUserObject(entry);
		
		int year=this.spYear.getIntegerVlaue().intValue();
		int month=this.spMonth.getIntegerVlaue().intValue();
		int cycle=this.editData.getCycle().getCycle().getValue();
		
		for(int i=0;i<cycle;i++){
			if (month > 12) {
				year += 1;
				month = 1;
			}
			String key=year+"year"+month+"m";
			
			ProjectMonthPlanProDateEntryInfo dateEntry=new ProjectMonthPlanProDateEntryInfo();
			dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
			dateEntry.setYear(year);
			dateEntry.setMonth(month);
			dateEntry.setAmount(FDCHelper.ZERO);

			if(row.getCell(key+"amount")!=null){
				row.getCell(key+"amount").setUserObject(dateEntry);
				row.getCell(key+"amount").setValue(FDCHelper.ZERO);
			}
			if(row.getCell(key+"remark")!=null){
				row.getCell(key+"remark").setUserObject(dateEntry);
			}
			if(row.getCell(key+"bgItem")!=null){
				row.getCell(key+"bgItem").setUserObject(dateEntry);
			}
			entry.getDateEntry().add(dateEntry);
			month++;
		}
		this.editData.getEntry().add(entry);
	}
	public void actionILine_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=getSelectTable();
		IRow row = null;
		if (table.getSelectManager().size() > 0) {
			int top = table.getSelectManager().get().getTop();
			if (isTableColumnSelected(table)){
				row = table.addRow();
			}else{
				row = table.addRow(top);
			}
		} else {
			row = table.addRow();
		}
		ProjectMonthPlanProEntryInfo entry = new ProjectMonthPlanProEntryInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		row.setUserObject(entry);
		
		int year=this.spYear.getIntegerVlaue().intValue();
		int month=this.spMonth.getIntegerVlaue().intValue();
		int cycle=this.editData.getCycle().getCycle().getValue();
		
		for(int i=0;i<cycle;i++){
			if (month > 12) {
				year += 1;
				month = 1;
			}
			String key=year+"year"+month+"m";
			
			ProjectMonthPlanProDateEntryInfo dateEntry=new ProjectMonthPlanProDateEntryInfo();
			dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
			dateEntry.setYear(year);
			dateEntry.setMonth(month);
			dateEntry.setAmount(FDCHelper.ZERO);
			
			if(row.getCell(key+"amount")!=null){
				row.getCell(key+"amount").setUserObject(dateEntry);
				row.getCell(key+"amount").setValue(FDCHelper.ZERO);
			}
			if(row.getCell(key+"remark")!=null){
				row.getCell(key+"remark").setUserObject(dateEntry);
			}
			if(row.getCell(key+"bgItem")!=null){
				row.getCell(key+"bgItem").setUserObject(dateEntry);
			}
			entry.getDateEntry().add(dateEntry);
			month++;
		}
		
		this.editData.getEntry().add(entry);
	}
	public boolean confirmRemove(Component comp) {
		return FDCMsgBox.isYes(FDCMsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}
	public void actionRLine_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=getSelectTable();
		if (table.getSelectManager().size() == 0 || isTableColumnSelected(table)) {
			FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
			return;
		}
		if (confirmRemove(this)) {
			int top = table.getSelectManager().get().getBeginRow();
			int bottom = table.getSelectManager().get().getEndRow();
			for (int i = top; i <= bottom; i++) {
				if (table.getRow(top) == null) {
					FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
					return;
				}
				ProjectMonthPlanProEntryInfo topValue=(ProjectMonthPlanProEntryInfo) table.getRow(top).getUserObject();
				table.removeRow(top);
				
				if(this.editData.getEntry().contains(topValue)){
					this.editData.getEntry().removeObject(topValue);
				}
				
				int spYear=this.spYear.getIntegerVlaue().intValue();
				int spMonth=this.spMonth.getIntegerVlaue().intValue();
				int cycle=this.editData.getCycle().getCycle().getValue();
				String amountColoun[]=new String[cycle+1];
				for(int k=0;k<cycle+1;k++){
					if(k==0){
						amountColoun[0]="amount";
					}else{
						if (spMonth > 12) {
							spYear += 1;
							spMonth = 1;
						}
						String key=spYear+"year"+spMonth+"m";
						amountColoun[k]=key+"amount";
						
						spMonth++;
					}
				}
				CRMClientHelper.getFootRow(table, amountColoun);
			}
		}
	}
//	protected ProgrammingContractEconomyInfo getProPayPlan(String id,int year,int month) throws BOSException, EASBizException{
//    	SelectorItemCollection sel=new SelectorItemCollection();
//    	sel.add("economyEntries.*");
//    	ProgrammingContractInfo pc=ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(id),sel);
//    	
//    	ProgrammingContractEconomyCollection ecol=pc.getEconomyEntries();
//    	for(int i=0;i<ecol.size();i++){
//    		Calendar cal = Calendar.getInstance();
//    		cal.setTime(ecol.get(i).getPaymentDate());
//    		int comYear=cal.get(Calendar.YEAR);
//    		int comMonth=cal.get(Calendar.MONTH)+1;
//    		if(year==comYear&&month==comMonth){
//				return ecol.get(i);
//			}
//    	}
//    	return null;
//	}
	protected boolean checkBizDate() throws EASBizException, BOSException{
		int comYear=this.spYear.getIntegerVlaue().intValue();
		int comMonth=this.spMonth.getIntegerVlaue().intValue();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, comYear);
		cal.set(Calendar.MONTH, comMonth-1);
		cal.set(Calendar.DATE, 1);
		Date begin=FDCDateHelper.getDayBegin(cal.getTime());
		
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtRespPerson);
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id",this.editData.getCurProject().getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("respPerson.id",((PersonInfo)this.prmtRespPerson.getValue()).getId().toString()));
    	
    	filter.getFilterItems().add(new FilterItemInfo("bizDate",begin));
    	if(this.editData.getId()!=null){
    		filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.NOTEQUALS));
    	}
    	SorterItemInfo version=new SorterItemInfo("version");
    	version.setSortType(SortType.DESCEND);
    	view.getSorter().add(version);
    	view.getSelector().add(new SelectorItemInfo("state"));
    	view.getSelector().add(new SelectorItemInfo("version"));
    	view.setFilter(filter);
    	ProjectMonthPlanProCollection col=ProjectMonthPlanProFactory.getRemoteInstance().getProjectMonthPlanProCollection(view);
    	if(col.size()>0&&!col.get(0).getState().equals(FDCBillStateEnum.AUDITTED)){
    		FDCMsgBox.showWarning(this,comYear+"年"+comMonth+"月无合同费用付款计划还未审批！");
    		return true;
    	}else{
    		if(col.size()==0){
    			this.txtVersion.setValue(1);
    		}else{
    			this.txtVersion.setValue(col.get(0).getVersion()+1);
    		}
    		return false;
    	}
	}
	protected void reGetValue() throws EASBizException, BOSException{
		if (isLoad) {
			return;
		}
    	if(checkBizDate()){
    		this.spYear.setValue(year_old, false);
			this.spMonth.setValue(month_old,false);
			return;
    	}
    	
		int result = MsgBox.OK;
		if (this.proTable.getRowCount() > 0) {
			result = FDCMsgBox.showConfirm2(this,"改变编制月份将根据编制周期重新调整编制表格！");
		}
		if (result == MsgBox.OK) {
			year_old = this.spYear.getIntegerVlaue().intValue();
			month_old=this.spMonth.getIntegerVlaue().intValue();
		} else {
			this.spYear.setValue(year_old, false);
			this.spMonth.setValue(month_old,false);
			return;
		}
		for(int i=0;i<this.editData.getEntry().size();i++){
			this.editData.getEntry().get(i).getDateEntry().clear();
		}
		this.storeFields();
		for(int i=0;i<this.proTable.getRowCount();i++){
			ProjectMonthPlanProEntryInfo entry=(ProjectMonthPlanProEntryInfo) this.proTable.getRow(i).getUserObject();
			if(entry!=null){
				int year=this.spYear.getIntegerVlaue().intValue();
				int month=this.spMonth.getIntegerVlaue().intValue();
				int cycle=this.editData.getCycle().getCycle().getValue();
				
				for(int j=0;j<cycle;j++){
					if (month > 12) {
						year += 1;
						month = 1;
					}
					
					ProjectMonthPlanProDateEntryInfo dateEntry=new ProjectMonthPlanProDateEntryInfo();
					dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
					dateEntry.setYear(year);
					dateEntry.setMonth(month);
					
					entry.getDateEntry().add(dateEntry);
					month++;
				}
			}
		}
		this.loadEntry();
		KDTEditEvent proE=new KDTEditEvent(this.proTable);
		for(int i=0;i<this.proTable.getRowCount();i++){
			proE.setRowIndex(i);
			proE.setColIndex(this.proTable.getColumnIndex("number"));
			table_editStopped(proE);
		}
	}
	protected void spMonth_stateChanged(ChangeEvent e) throws Exception {
		reGetValue();
	}
	protected void spYear_stateChanged(ChangeEvent e) throws Exception {
		reGetValue();
	}
//	protected BgItemInfo getBgItem(ProgrammingContractInfo pc) throws EASBizException, BOSException{
//		if(pc==null) return null;
//		SelectorItemCollection sel=new SelectorItemCollection();
//		sel.add("costEntries.costAccount.longNumber");
//		pc=ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(pc.getId()),sel);
//		Set costAccount=new HashSet();
//		for(int i=0;i<pc.getCostEntries().size();i++){
//			costAccount.add(pc.getCostEntries().get(i).getCostAccount().getLongNumber());
//		}
//		return CostAccountWithBgItemFactory.getRemoteInstance().getBgItem(costAccount);
//	}
	protected void btnGet_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getCurProject()==null){
			FDCMsgBox.showWarning(this,"工程项目为空！");
			return;
		}
		boolean isShowWarn=false;
		boolean isUpdate=false;
		if(this.proTable.getRowCount()>0){
       	 	isShowWarn=true;
        }
        if(isShowWarn){
       	 	if(FDCMsgBox.showConfirm2(this, "重新提取数据将会覆盖之前数据，是否继续？")== FDCMsgBox.YES){
       	 		isUpdate=true;
       	 	}
        }else{
       	 	isUpdate=true;
        }
        if(isUpdate){
        	Map contract=new HashMap();
        	Map entryMap=new HashMap();
        	
        	this.proTable.removeRows();
        	this.storeFields();
        	
        	Set yearSet=new HashSet();
        	Set monthSet=new HashSet();
        	int spYear=this.spYear.getIntegerVlaue().intValue();
    		int spMonth=this.spMonth.getIntegerVlaue().intValue();
    		int cycle=this.editData.getCycle().getCycle().getValue();
        	for(int i=0;i<cycle;i++){
    			if (spMonth > 12) {
    				spYear += 1;
    				spMonth = 1;
    			}
    			yearSet.add(spYear);
    			monthSet.add(spMonth);
    			spMonth++;
        	}
        	
        	EntityViewInfo view=new EntityViewInfo();
        	FilterInfo filter=new FilterInfo();
        	
        	filter.getFilterItems().add(new FilterItemInfo("contract.programming.project.id",this.editData.getCurProject().getId().toString()));
        	filter.getFilterItems().add(new FilterItemInfo("contract.programming.state",FDCBillStateEnum.AUDITTED_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("contract.programming.isLatest",Boolean.TRUE));
        	filter.getFilterItems().add(new FilterItemInfo("contract.isCiting",Boolean.FALSE));
        	filter.getFilterItems().add(new FilterItemInfo("paymentDate",null,CompareType.NOTEQUALS));
        	filter.getFilterItems().add(new FilterItemInfo("amount",null,CompareType.NOTEQUALS));
        	
        	view.setFilter(filter);
        	view.getSelector().add("*");
        	view.getSelector().add("contract.number");
        	view.getSelector().add("contract.name");
        	view.getSelector().add("contract.amount");
        	
        	SorterItemCollection sort=new SorterItemCollection();
        	SorterItemInfo number=new SorterItemInfo("contract.number");
        	number.setSortType(SortType.ASCEND);
        	sort.add(number);
        	
        	view.setSorter(sort);
        	ProgrammingContractEconomyCollection ecol=ProgrammingContractEconomyFactory.getRemoteInstance().getProgrammingContractEconomyCollection(view);
        	
//        	Map bgItemMap=new HashMap();
        	for(int i=0;i<ecol.size();i++){
        		Calendar cal = Calendar.getInstance();
        		cal.setTime(ecol.get(i).getPaymentDate());
        		int year=cal.get(Calendar.YEAR);
        		int month=cal.get(Calendar.MONTH)+1;
        		if(!(yearSet.contains(year)&&monthSet.contains(month))){
        			continue;
        		}
        		String remark=ecol.get(i).getCondition();
        		String contractId=ecol.get(i).getContract().getId().toString();
        		BigDecimal amount=ecol.get(i).getAmount();
        		HashMap yearMap=null;
        		HashMap monthMap=null;
        		ProjectMonthPlanProDateEntryInfo dateEntry=null;
        		ProjectMonthPlanProEntryInfo entry=null;
        		
//        		BgItemInfo bgItem=null;
//        		if(bgItemMap.containsKey(contractId)){
//					bgItem=(BgItemInfo)bgItemMap.get(contractId);
//				}else{
//					bgItem=getBgItem(ecol.get(i).getContract());
//					bgItemMap.put(contractId, bgItem);
//				}
        		if(contract.containsKey(contractId)){
        			yearMap=(HashMap)contract.get(contractId);
        			entry=(ProjectMonthPlanProEntryInfo) entryMap.get(contractId);
        			
        			if(yearMap.containsKey(year)){
        				monthMap=(HashMap)yearMap.get(year);
        				if(monthMap.containsKey(month)){
        					dateEntry=(ProjectMonthPlanProDateEntryInfo) monthMap.get(month);
        					dateEntry.setAmount(dateEntry.getAmount().add(amount));
        				}else{
        					dateEntry=new ProjectMonthPlanProDateEntryInfo();
//        					dateEntry.setBgItem(bgItem);
        					dateEntry.setAmount(amount);
        					dateEntry.setYear(year);
        					dateEntry.setMonth(month);
        					dateEntry.setRemark(remark);
        					dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
        					
        					entry.getDateEntry().add(dateEntry);
        					monthMap.put(month, dateEntry);
        				}
        			}else{
        				monthMap=new HashMap();
        				
        				dateEntry=new ProjectMonthPlanProDateEntryInfo();
//        				dateEntry.setBgItem(bgItem);
    					dateEntry.setAmount(amount);
    					dateEntry.setYear(year);
    					dateEntry.setMonth(month);
    					dateEntry.setRemark(remark);
    					dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
    					
    					entry.getDateEntry().add(dateEntry);
    					monthMap.put(month, dateEntry);
    					
    					yearMap.put(year, monthMap);
        				
        			}
        		}else{
        			yearMap=new HashMap();
        			entry=new ProjectMonthPlanProEntryInfo();
        			entry.setProgrammingContract(ecol.get(i).getContract());
        			entry.setId(BOSUuid.create(entry.getBOSType()));
        			
        			monthMap=new HashMap();
    				
    				dateEntry=new ProjectMonthPlanProDateEntryInfo();
//    				dateEntry.setBgItem(bgItem);
					dateEntry.setAmount(amount);
					dateEntry.setYear(year);
					dateEntry.setMonth(month);
					dateEntry.setRemark(remark);
					dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
					
					entry.getDateEntry().add(dateEntry);
					monthMap.put(month, dateEntry);
					
					yearMap.put(year, monthMap);
					
					contract.put(contractId, yearMap);
					
					entryMap.put(contractId, entry);
					
					this.editData.getEntry().add(entry);
        		}
        	}
        	this.loadEntry();
        	KDTEditEvent proE=new KDTEditEvent(this.proTable);
    		for(int i=0;i<this.proTable.getRowCount();i++){
    			proE.setRowIndex(i);
    			proE.setColIndex(this.proTable.getColumnIndex("number"));
    			table_editStopped(proE);
    		}
		}
	}
	protected AdminOrgUnitInfo getDepByPerson(PersonInfo person) throws BOSException{
		SelectorItemCollection personsel=new SelectorItemCollection();
		personsel.add("position.adminOrgUnit.*");
		if(person!=null){
			EntityViewInfo view = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("person.id",person.getId()));
    		filter.getFilterItems().add(new FilterItemInfo("isPrimary",Boolean.TRUE));
    		view.setFilter(filter);
    		view.setSelector(personsel);
			
			PositionMemberCollection pmcol=PositionMemberFactory.getRemoteInstance().getPositionMemberCollection(view);
			if(pmcol.size()>0){
				return pmcol.get(0).getPosition().getAdminOrgUnit();
			}
		}
		return null;
	}
	protected void prmtRespPerson_dataChanged(DataChangeEvent e) throws Exception {
		PersonInfo person=(PersonInfo) prmtRespPerson.getValue();
		if(person!=null){
			this.prmtRespDept.setValue(getDepByPerson(person));
		}else{
			this.prmtRespDept.setValue(null);
		}
	}
}