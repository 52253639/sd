/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ActionMap;
import javax.swing.JMenuItem;
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
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizComboBoxMultiColumnItem;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBook;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBookVO;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDComboBoxMultiColumnItem;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.CostAccountWithBgItemFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PayPlanCycleFactory;
import com.kingdee.eas.fdc.basedata.PayPlanCycleInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryCollection;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryFactory;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryInfo;
import com.kingdee.eas.fdc.contract.ContractPayPlanInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractWithoutText;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.client.ContractPayPlanEditUI;
import com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI;
import com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.IProjectMonthPlanGather;
import com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherDateEntryCollection;
import com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherDateEntryFactory;
import com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherDateEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherPayEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProInfo;
import com.kingdee.eas.fdc.finance.ProjectYearPlanEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectYearPlanTotalEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectYearPlanTotalEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectYearPlanTotalEntryInfo;
import com.kingdee.eas.fdc.finance.VersionTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BankPaymentInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeManageFactory;
import com.kingdee.eas.fdc.sellhouse.IBaseTransaction;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.fdc.sellhouse.client.BankPaymentEditUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.UIPopupManager;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.BgItemObject;
import com.kingdee.eas.ma.budget.client.NewBgItemDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class ProjectMonthPlanGatherEditUI extends AbstractProjectMonthPlanGatherEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectMonthPlanGatherEditUI.class);
    private KDTable bgTable=null;
    private KDTable contractTable=null;
    private KDTable payTable=null;
    private Boolean isLoad=false;
	private KDComboBox cbRespPerson=null;
	private KDComboBox cbRespDep=null;
	private List reportColoum=null;
	private List executeColoum=null;
    public ProjectMonthPlanGatherEditUI() throws Exception
    {
        super();
    }
    protected void attachListeners() {
    	addDataChangeListener(this.cbVersionType);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.cbVersionType);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ProjectMonthPlanGatherFactory.getRemoteInstance();
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
	protected void initControl() {
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
		
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPre.setVisible(false);
		this.menuView.setVisible(false);
		
		this.menuBiz.setVisible(false);
		this.actionAddNew.setVisible(false);
		
		this.txtVersion.setPrecision(1);
		
		this.spYear.setModel(new SpinnerNumberModel(this.spYear.getIntegerVlaue().intValue(),1,10000,1));
		this.spMonth.setModel(new SpinnerNumberModel(this.spMonth.getIntegerVlaue().intValue(),1,12,1));
		
		this.contExecuteAmount.getBoundLabel().setForeground(Color.RED);
		
		
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.btnGet.setEnabled(false);
			if(this.contractTable!=null){
				this.contractTable.setEnabled(false);
			}
			if(this.payTable!=null){
				this.payTable.setEnabled(false);
			}
			this.actionALine.setEnabled(false);
			this.actionILine.setEnabled(false);
			this.actionRLine.setEnabled(false);
		} else {
			this.unLockUI();
//			if(this.txtVersion.getIntegerValue()!=null&&
//					this.txtVersion.getIntegerValue()==1){
//				this.btnGet.setEnabled(true);
//			}else{
//				this.btnGet.setEnabled(false);
//			}
			if(this.contractTable!=null){
				this.contractTable.setEnabled(true);
			}
			if(this.payTable!=null){
				this.payTable.setEnabled(true);
			}
			if(VersionTypeEnum.EXECUTE.equals(this.cbVersionType.getSelectedItem())){
				this.actionALine.setEnabled(true);
				this.actionILine.setEnabled(true);
				this.actionRLine.setEnabled(true);
			}else{
				this.actionALine.setEnabled(false);
				this.actionILine.setEnabled(false);
				this.actionRLine.setEnabled(false);
			}
			this.actionRLine.setEnabled(true);
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
		initControl();
	}
	public void storeFields(){
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
		this.txtName.setText(year+"年"+month+"月"+"项目月度资金计划（"+this.editData.getCurProject().getName()+"）");
		
		super.storeFields();
	}
	public void loadFields() {
		isLoad=true;
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		if(this.editData.getBizDate()!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.editData.getBizDate());
			int year=cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;
			
			this.spYear.setValue(year);
			this.spMonth.setValue(month);
		}
		loadEntry();
		
		if(this.txtVersion.getIntegerValue()>1){
			this.cbVersionType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.cbVersionType.setEnabled(false);
			this.spYear.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.spYear.setEnabled(false);
			this.spMonth.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.spMonth.setEnabled(false);
		}
		this.cbVersionType.setEnabled(false);
		
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
		
		reportColoum=new ArrayList();
		executeColoum=new ArrayList();
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
			table.getColumn(key+"amount").getStyleAttributes().setFontColor(Color.BLUE);
			
			amountColumn=table.addColumn();
			reportColoum.add(amountColumn);
			amountColumn.setKey(key+"reportAmount");
			table.getColumn(key+"reportAmount").setEditor(amountEditor);
			table.getColumn(key+"reportAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			table.getColumn(key+"reportAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			
			amountColumn=table.addColumn();
			amountColumn.setKey(key+"amt");
			table.getColumn(key+"amt").setEditor(amountEditor);
			table.getColumn(key+"amt").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			table.getColumn(key+"amt").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			
			amountColumn=table.addColumn();
			executeColoum.add(amountColumn);
			amountColumn.setKey(key+"executeAmount");
			table.getColumn(key+"executeAmount").setEditor(amountEditor);
			table.getColumn(key+"executeAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			table.getColumn(key+"executeAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			
			IColumn remarkColumn=table.addColumn();
			remarkColumn.setEditor(remarkEditor);
			remarkColumn.setKey(key+"remark");
			
			IColumn bgItemColumn=table.addColumn();
			bgItemColumn.setKey(key+"bgItem");
			
			table.getColumn(key+"bgItem").setEditor(f7Editor);
			
			table.getHeadRow(0).getCell(key+"amount").setValue(monthStr);
			table.getHeadRow(0).getCell(key+"reportAmount").setValue(monthStr);
			table.getHeadRow(0).getCell(key+"amt").setValue(monthStr);
			table.getHeadRow(0).getCell(key+"executeAmount").setValue(monthStr);
			table.getHeadRow(0).getCell(key+"remark").setValue(monthStr);
			table.getHeadRow(0).getCell(key+"bgItem").setValue(monthStr);
			
			table.getHeadRow(1).getCell(key+"amount").setValue("计划支付");
			table.getHeadRow(1).getCell(key+"reportAmount").setValue("上报金额");
			table.getHeadRow(1).getCell(key+"amt").setValue("完成工程量");
			table.getHeadRow(1).getCell(key+"executeAmount").setValue("核定金额");
			table.getHeadRow(1).getCell(key+"remark").setValue("用款说明");
			table.getHeadRow(1).getCell(key+"bgItem").setValue("预算项目");
			
			int merge=table.getHeadRow(0).getCell(key+"amount").getColumnIndex();
			table.getHeadMergeManager().mergeBlock(0, merge, 0, merge+5);
			
			month++;
		}
	}
	protected void initContractTable(int year,int month,int cycle) {
		this.contractTable=new KDTable();
		this.contractTable.checkParsed();
		IRow headRow=this.contractTable.addHeadRow();
		IRow headRowName=this.contractTable.addHeadRow();
		
		this.contractTable.getStyleAttributes().setLocked(true);
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		amount.setNegatived(false);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		
		IColumn column=this.contractTable.addColumn();
		column.setKey("number");
		column.setWidth(200);
		headRow.getCell("number").setValue("编码");
		headRowName.getCell("number").setValue("编码");
		
		column=this.contractTable.addColumn();
		column.setKey("name");
		column.setWidth(200);
		headRow.getCell("name").setValue("名称");
		headRowName.getCell("name").setValue("名称");

		column=this.contractTable.addColumn();
		column.setKey("partB");
		headRow.getCell("partB").setValue("乙方");
		headRowName.getCell("partB").setValue("乙方");
		
		column=this.contractTable.addColumn();
		column.setKey("respPerson");
		headRow.getCell("respPerson").setValue("责任人");
		headRowName.getCell("respPerson").setValue("责任人");
		
		column=this.contractTable.addColumn();
		column.setKey("respDept");
		headRow.getCell("respDept").setValue("责任部门");
		headRowName.getCell("respDept").setValue("责任部门");
		
		column=this.contractTable.addColumn();
		column.setKey("amount");
		headRow.getCell("amount").setValue("金额");
		headRowName.getCell("amount").setValue("金额");
		
		column=this.contractTable.addColumn();
		column.setKey("lastPrice");
		headRow.getCell("lastPrice").setValue("合同最新造价");
		headRowName.getCell("lastPrice").setValue("合同最新造价");
		
		column=this.contractTable.addColumn();
		column.setKey("actPayAmount");
		headRow.getCell("actPayAmount").setValue("合同已付金额");
		headRowName.getCell("actPayAmount").setValue("合同已付金额");
		
		this.contractTable.getColumn("amount").setEditor(amountEditor);
		this.contractTable.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.contractTable.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
        
		this.contractTable.getColumn("lastPrice").setEditor(amountEditor);
		this.contractTable.getColumn("lastPrice").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.contractTable.getColumn("lastPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.contractTable.getColumn("actPayAmount").setEditor(amountEditor);
		this.contractTable.getColumn("actPayAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.contractTable.getColumn("actPayAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.contractTable.setName("contractTable");
//		this.contractTable.getViewManager().setFreezeView(0, 7);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 0, 1, 0);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 1, 1, 1);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 2, 1, 2);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 3, 1, 3);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 4, 1, 4);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 5, 1, 5);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 6, 1, 6);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 7, 1, 7);
		
		initMonthColoum(this.contractTable,year,month,cycle);
		
		ActionMap actionMap = this.contractTable.getActionMap();
		actionMap.remove(KDTAction.CUT);
		actionMap.remove(KDTAction.DELETE);
		actionMap.remove(KDTAction.PASTE);
		
		this.contractTable.addKDTMouseListener(new KDTMouseListener() {
			public void tableClicked(KDTMouseEvent e) {
				table_Clicked(e);
			}
		});
		
		KDContainer contEntry = new KDContainer();
		contEntry.setName(this.contractTable.getName());
		contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        
		contEntry.getContentPane().add(this.contractTable, BorderLayout.CENTER);
		
		cbRespPerson=new KDComboBox();
		cbRespDep=new KDComboBox();
		
		KDLabelContainer contRespPerson = new KDLabelContainer();
		contRespPerson.setBoundLabelText("责任人");		
		contRespPerson.setBoundLabelLength(100);		
		contRespPerson.setBoundLabelUnderline(true);		
		contRespPerson.setBoundEditor(cbRespPerson);
		contRespPerson.setBounds(new Rectangle(250, 1, 270, 19));
        contEntry.add(contRespPerson);
        
		KDLabelContainer contRespDep = new KDLabelContainer();
		contRespDep.setBoundLabelText("责任部门");		
		contRespDep.setBoundLabelLength(100);		
		contRespDep.setBoundLabelUnderline(true);	
		contRespDep.setBoundEditor(cbRespDep);
		contRespDep.setBounds(new Rectangle(550, 1, 270, 19));
        contEntry.add(contRespDep);
		
//        KDWorkButton btnAddRowinfo = new KDWorkButton();
//		KDWorkButton btnInsertRowinfo = new KDWorkButton();
//		KDWorkButton btnDeleteRowinfo = new KDWorkButton();
//
//		this.actionALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
//		btnAddRowinfo = (KDWorkButton)contEntry.add(this.actionALine);
//		btnAddRowinfo.setText("新增行");
//		btnAddRowinfo.setSize(new Dimension(140, 19));
//
//		this.actionILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
//		btnInsertRowinfo = (KDWorkButton) contEntry.add(this.actionILine);
//		btnInsertRowinfo.setText("插入行");
//		btnInsertRowinfo.setSize(new Dimension(140, 19));
//
//		this.actionRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
//		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRLine);
//		btnDeleteRowinfo.setText("删除行");
//		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
        this.pnlBig.add(contEntry, "付款计划明细");
        
		this.contractTable.addKDTEditListener(new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				table_editStopped(e);
			}
		});
	}
	protected void initPayTable() {
		this.payTable=new KDTable();
		this.payTable.checkParsed();
		IRow headRow=this.payTable.addHeadRow();
		
		this.payTable.getStyleAttributes().setLocked(true);
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		amount.setNegatived(false);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		
		IColumn column=this.payTable.addColumn();
		column.setKey("number");
		column.setWidth(200);
		headRow.getCell("number").setValue("编码");
		column.getStyleAttributes().setFontColor(Color.BLUE);
		
		column=this.payTable.addColumn();
		column.setKey("name");
		column.setWidth(200);
		headRow.getCell("name").setValue("名称");

		column=this.payTable.addColumn();
		column.setKey("amount");
		headRow.getCell("amount").setValue("申请金额");
		
		column=this.payTable.addColumn();
		column.setKey("bizDate");
		headRow.getCell("bizDate").setValue("业务日期");
		
		column=this.payTable.addColumn();
		column.setKey("supplier");
		column.setWidth(250);
		headRow.getCell("supplier").setValue("供应商");
		
		column=this.payTable.addColumn();
		column.setKey("actPayAmount");
		headRow.getCell("actPayAmount").setValue("已付金额");
		
		column=this.payTable.addColumn();
		
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
		
		column.setEditor(f7Editor);
		column.setRequired(true);
		column.getStyleAttributes().setLocked(false);
		column.setKey("bgItem");
		headRow.getCell("bgItem").setValue("预算项目");
		
		this.payTable.addKDTMouseListener(new KDTMouseListener() {
			public void tableClicked(KDTMouseEvent e) {
				paytable_Clicked(e);
			}
		});
		
		this.payTable.addKDTEditListener(new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				paytable_editStopped(e);
			}
		});
		
		this.payTable.getColumn("amount").setEditor(amountEditor);
		this.payTable.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.payTable.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
        
		this.payTable.getColumn("actPayAmount").setEditor(amountEditor);
		this.payTable.getColumn("actPayAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.payTable.getColumn("actPayAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.payTable.setName("payTable");
		
		ActionMap actionMap = this.contractTable.getActionMap();
		actionMap.remove(KDTAction.CUT);
		actionMap.remove(KDTAction.DELETE);
		actionMap.remove(KDTAction.PASTE);
		
		
		KDContainer contEntry = new KDContainer();
		contEntry.setName(this.payTable.getName());
		contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        
		contEntry.getContentPane().add(this.payTable, BorderLayout.CENTER);
		
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();
		this.actionRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
        this.pnlBig.add(contEntry, "付款申请未完成支付数据汇总");
	}
	private void table_editStopped(KDTEditEvent e) {
		KDTable table = (KDTable) e.getSource();
		if(table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()!=null
				&&table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject() instanceof ProjectMonthPlanGatherDateEntryInfo){
			if(table.getColumnKey(e.getColIndex()).indexOf("reportAmount")>0){
				((ProjectMonthPlanGatherDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setReportAmount((BigDecimal)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
			}else if(table.getColumnKey(e.getColIndex()).indexOf("executeAmount")>0){
				((ProjectMonthPlanGatherDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setExecuteAmount((BigDecimal)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
			}
			CRMClientHelper.getFootRow(table, new String[]{table.getColumnKey(e.getColIndex())});
		}
	}
	private void paytable_editStopped(KDTEditEvent e) {
		KDTable table = (KDTable) e.getSource();
		if(table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue() instanceof BgItemObject){
			BgItemObject bgItem=(BgItemObject) table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue();
			if(bgItem!=null){
				if(!bgItem.getResult().get(0).isIsLeaf()){
					FDCMsgBox.showWarning(this,"请选择明细预算项目！");
					((ProjectMonthPlanGatherPayEntryInfo)table.getRow(e.getRowIndex()).getUserObject()).setBgItem((BgItemInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
				}else{
					((ProjectMonthPlanGatherPayEntryInfo)table.getRow(e.getRowIndex()).getUserObject()).setBgItem(bgItem.getResult().get(0));
				}
			}
		}
	}
	private void setRowHide(){
		PersonInfo person=null;
		AdminOrgUnitInfo admin=null;
		if(cbRespPerson.getSelectedItem()!=null&&cbRespPerson.getSelectedItem() instanceof KDComboBoxMultiColumnItem){
			KDComboBoxMultiColumnItem personItem=(KDComboBoxMultiColumnItem) cbRespPerson.getSelectedItem();
			person=(PersonInfo) personItem.getUserObject();
		}
		if(cbRespDep.getSelectedItem()!=null&&cbRespDep.getSelectedItem() instanceof KDComboBoxMultiColumnItem){
			KDComboBoxMultiColumnItem adminItem=(KDComboBoxMultiColumnItem) cbRespDep.getSelectedItem();
			admin=(AdminOrgUnitInfo) adminItem.getUserObject();
		}
		for(int i=0;i<this.contractTable.getRowCount();i++){
			IRow row=this.contractTable.getRow(i);
			if(row.getStyleAttributes().getBackground().equals(FDCTableHelper.cantEditColor)){
				continue;
			}
			PersonInfo rowPeson=(PersonInfo) row.getCell("respPerson").getUserObject();
			AdminOrgUnitInfo rowAdmin=(AdminOrgUnitInfo) row.getCell("respDept").getUserObject();
			if(person!=null&&rowPeson!=null&&admin!=null&&rowAdmin!=null){
				if(person.getId().equals(rowPeson.getId())&&admin.getId().equals(rowAdmin.getId())){
					row.getStyleAttributes().setHided(false);
				}else{
					row.getStyleAttributes().setHided(true);
				}
			}else if(person!=null&&rowPeson!=null&&!person.getId().equals(rowPeson.getId())&&admin==null&&rowAdmin!=null){
				row.getStyleAttributes().setHided(true);
			}else if(admin!=null&&rowAdmin!=null&&!admin.getId().equals(rowAdmin.getId())&&person==null&&rowPeson!=null){
				row.getStyleAttributes().setHided(true);
			}else{
				row.getStyleAttributes().setHided(false);
			}
		}
	}
	protected void cbRespPerson_itemStateChanged(java.awt.event.ItemEvent e) throws Exception{
		setRowHide();
	}
	protected void cbRespDep_itemStateChanged(java.awt.event.ItemEvent e) throws Exception{
		setRowHide();
	}
	protected void table_Clicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e){
		KDTable table = (KDTable) e.getSource();
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			if(table.getColumnKey(e.getColIndex()).indexOf("amount")>0
					&&table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue()!=null){
				ProjectMonthPlanGatherEntryInfo entry=(ProjectMonthPlanGatherEntryInfo) table.getRow(e.getRowIndex()).getUserObject();
				if(entry!=null&&entry.getSrcId()!=null){
					UIContext uiContext = new UIContext(this);
					uiContext.put("ID", entry.getSrcId());
					String uiClass=null;
					if(entry.getSrcId().getType().equals(new ContractPayPlanInfo().getBOSType())){
						uiClass=ContractPayPlanEditUI.class.getName();
					}else if(entry.getSrcId().getType().equals(new ProjectMonthPlanProInfo().getBOSType())){
						uiClass=ProjectMonthPlanProEditUI.class.getName();
					}else if(entry.getSrcId().getType().equals(new ProjectMonthPlanGatherInfo().getBOSType())){
						uiClass=ProjectMonthPlanGatherEditUI.class.getName();
					}
					try {
						if(uiClass!=null){
							IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiClass, uiContext, null, OprtState.VIEW);
							uiWindow.show();
						}
					} catch (UIException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
	protected void paytable_Clicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e){
		KDTable table = (KDTable) e.getSource();
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			if(table.getColumnKey(e.getColIndex()).equals("number")){
				ProjectMonthPlanGatherPayEntryInfo entry=(ProjectMonthPlanGatherPayEntryInfo) table.getRow(e.getRowIndex()).getUserObject();
				if(entry.getPayRequestBill()!=null){
					UIContext uiContext = new UIContext(this);
					uiContext.put("ID", entry.getPayRequestBill().getId());
					try {
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PayRequestBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
						uiWindow.show();
					} catch (UIException e1) {
						e1.printStackTrace();
					}
				}else{
					UIContext uiContext = new UIContext(this);
					uiContext.put("ID", entry.getContractWithoutText().getId());
					try {
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractWithoutTextEditUI.class.getName(), uiContext, null, OprtState.VIEW);
						uiWindow.show();
					} catch (UIException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
	protected void initBgTable(int year,int month,int cycle) {
		this.bgTable=new KDTable();
		enableExportExcel(this.bgTable);
		this.bgTable.checkParsed();
		IRow headRow=this.bgTable.addHeadRow();
		IRow headRowName=this.bgTable.addHeadRow();
		
		this.bgTable.getStyleAttributes().setLocked(true);
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		amount.setNegatived(false);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		
		IColumn column=this.bgTable.addColumn();
		column.setKey("bgItem");
		headRow.getCell("bgItem").setValue("预算项目");
		headRowName.getCell("bgItem").setValue("预算项目");
		
		for(int i=0;i<cycle;i++){
			if (month > 12) {
				year += 1;
				month = 1;
			}
			String monthStr= year + "年" + month + "月";
			String key=year+"year"+month+"m";
			
			IColumn amountColumn=this.bgTable.addColumn();
			amountColumn.setKey(key+"amount");
			this.bgTable.getColumn(key+"amount").setEditor(amountEditor);
			this.bgTable.getColumn(key+"amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			this.bgTable.getColumn(key+"amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			
			
			this.bgTable.getHeadRow(0).getCell(key+"amount").setValue(monthStr);
			
			this.bgTable.getHeadRow(1).getCell(key+"amount").setValue("计划支付");
			
			int merge=this.bgTable.getHeadRow(0).getCell(key+"amount").getColumnIndex();
			this.bgTable.getHeadMergeManager().mergeBlock(0, merge, 0, merge);
			
			month++;
		}
        
		this.bgTable.setName("contractTable");
		this.bgTable.getViewManager().setFreezeView(0, 1);
		this.bgTable.getHeadMergeManager().mergeBlock(0, 0, 1, 0);
		
		KDContainer contEntry = new KDContainer();
		contEntry.setName(this.bgTable.getName());
		contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        
		contEntry.getContentPane().add(this.bgTable, BorderLayout.CENTER);

		this.pnlBig.add(contEntry, "预算明细");
	}
	protected void loadEntry(){
		ProjectMonthPlanGatherEntryCollection col=this.editData.getEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.pnlBig.removeAll();
		
		int spYear=this.spYear.getIntegerVlaue().intValue();
		int spMonth=this.spMonth.getIntegerVlaue().intValue();
		int cycle=this.editData.getCycle().getCycle().getValue();
	
		this.initContractTable(spYear,spMonth,cycle);
		this.initPayTable();
		this.initBgTable(spYear,spMonth,cycle);
		
		Map rowMap=new HashMap();
		ProjectMonthPlanGatherDateEntryCollection sortCol=new ProjectMonthPlanGatherDateEntryCollection();
		IRow row=null;
		
		this.cbRespPerson.removeAllItems();
		this.cbRespDep.removeAllItems();
		
		KDComboBoxMultiColumnItem defaultValue=new KDComboBoxMultiColumnItem(new String[] { "","" });
		this.cbRespPerson.addItem(defaultValue);
		this.cbRespDep.addItem(defaultValue);
		
		Set respPersonSetId=new HashSet();
		Set respDepSetId=new HashSet();
		for(int i=0;i<col.size();i++){
			ProjectMonthPlanGatherEntryInfo entry=col.get(i);
			String id=null;
			String number=null;
			PersonInfo respPerson=null;
			AdminOrgUnitInfo respDept=null;
			BigDecimal lastPrice=null;
			BigDecimal actPayAmount=null;
			String partB=null;
			if(entry.getRespPerson()!=null){
				respPerson=entry.getRespPerson();
				if(!respPersonSetId.contains(respPerson.getId().toString())){
					KDComboBoxMultiColumnItem person=new KDComboBoxMultiColumnItem(new String[] { respPerson.getNumber(),respPerson.getName() });
					person.setUserObject(respPerson);
					this.cbRespPerson.addItem(person);
					respPersonSetId.add(respPerson.getId().toString());
				}
			}
				
			if(entry.getRespDept()!=null){
				respDept=entry.getRespDept();
				if(!respDepSetId.contains(respDept.getId().toString())){
					KDComboBoxMultiColumnItem dept=new KDComboBoxMultiColumnItem(new String[] { respDept.getNumber(),respDept.getName() });
					dept.setUserObject(respDept);
					this.cbRespDep.addItem(dept);
					respDepSetId.add(respDept.getId().toString());
				}
			}
				
			if(entry.getContractBill()!=null){
				id=entry.getContractBill().getId().toString();
				
				number=entry.getContractBill().getNumber();
				partB=entry.getContractBill().getPartB().getName();
				try{
					lastPrice=FDCUtils.getContractLastAmt (null,id);
					
					FDCSQLBuilder _builder = new FDCSQLBuilder();
					_builder.appendSql("select sum(FAmount) sumCount from t_cas_paymentbill where fbillstatus=15 and fcontractbillid='"+id+"'");
					final IRowSet rowSet = _builder.executeQuery();
					if (rowSet.size() == 1) {
						rowSet.next();
						actPayAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("sumCount"));
					}
				} catch (EASBizException e1) {
					e1.printStackTrace();
				} catch (BOSException e1) {
					e1.printStackTrace();
				} catch (UuidException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}else if(entry.getProgrammingContract()!=null){
				id=entry.getProgrammingContract().getId().toString();
				number=entry.getProgrammingContract().getNumber();
			}else{
				id=entry.getId().toString();
			}
			for(int j=0;j<entry.getDateEntry().size();j++){
				ProjectMonthPlanGatherDateEntryInfo dateEntry=entry.getDateEntry().get(j);
				
				sortCol.add(dateEntry);
				
				int month=dateEntry.getMonth();
				int year=dateEntry.getYear();
				
				if(rowMap.containsKey(id)){
					row=(IRow) rowMap.get(id);
				}else{
					row=this.contractTable.addRow();
					row.setUserObject(entry);
					row.getCell("number").setValue(number);
					row.getCell("name").setValue(entry.getName());
					row.getCell("partB").setValue(partB);
					if(respPerson!=null){
						row.getCell("respPerson").setValue(respPerson.getName());
						row.getCell("respPerson").setUserObject(respPerson);
					}
					if(respDept!=null){
						row.getCell("respDept").setValue(respDept.getName());
						row.getCell("respDept").setUserObject(respDept);
					}
					row.getCell("amount").setValue(entry.getAmount());
					row.getCell("lastPrice").setValue(lastPrice);
					row.getCell("actPayAmount").setValue(actPayAmount);
					rowMap.put(id, row);
				}
				
				String key=year+"year"+month+"m";
				
				if(row.getCell(key+"amount")!=null){
					row.getCell(key+"amount").setUserObject(dateEntry);
					row.getCell(key+"amount").setValue(dateEntry.getAmount());
				}
				if(row.getCell(key+"reportAmount")!=null){
					row.getCell(key+"reportAmount").setUserObject(dateEntry);
					row.getCell(key+"reportAmount").setValue(dateEntry.getReportAmount());
				}
				if(row.getCell(key+"amt")!=null){
					row.getCell(key+"amt").setUserObject(dateEntry);
					row.getCell(key+"amt").setValue(dateEntry.getAmt());
				}
				if(row.getCell(key+"executeAmount")!=null){
					row.getCell(key+"executeAmount").setUserObject(dateEntry);
					row.getCell(key+"executeAmount").setValue(dateEntry.getExecuteAmount());
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
		cbRespPerson.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                	cbRespPerson_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
		cbRespDep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                	cbRespDep_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
		if(this.contractTable.getRowCount()>0){
			IRow contractRow=this.contractTable.addRow(0);
			contractRow.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			contractRow.getStyleAttributes().setLocked(true);
			contractRow.getCell("name").setValue("合同付款计划明细");
			this.contractTable.getMergeManager().mergeBlock(0, 0, 0, this.contractTable.getColumnCount()-1);
			
			int proIndex=0;
			for(int i=0;i<this.contractTable.getRowCount();i++){
				if((ProjectMonthPlanGatherEntryInfo)this.contractTable.getRow(i).getUserObject()!=null&&((ProjectMonthPlanGatherEntryInfo)this.contractTable.getRow(i).getUserObject()).getContractBill()!=null){
					proIndex=i+1;
				}
			}
			
			IRow proRow=null;
			if(proIndex==0){
				proRow=this.contractTable.addRow();
				proIndex=proRow.getRowIndex();
			}else{
				proRow=this.contractTable.addRow(proIndex);
			}
			proRow.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			proRow.getStyleAttributes().setLocked(true);
			proRow.getCell("name").setValue("无合同费用付款计划明细");
			this.contractTable.getMergeManager().mergeBlock(proIndex, 0, proIndex, this.contractTable.getColumnCount()-1);
		}
		
		try {
			getExecuteAmount();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		setColoumHide();
		
		for(int i=0;i<this.contractTable.getRowCount();i++){
			IRow zeroRow=this.contractTable.getRow(i);
			ProjectMonthPlanGatherEntryInfo entry=(ProjectMonthPlanGatherEntryInfo) zeroRow.getUserObject();
			if(zeroRow.getStyleAttributes().getBackground().equals(FDCTableHelper.cantEditColor)){
				continue;
			}
			for(int j=0;j<this.contractTable.getColumnCount();j++){
				if(this.contractTable.getColumn(j).isRequired()&&this.contractTable.getColumnKey(j).indexOf("Amount")>0&&zeroRow.getCell(j).getUserObject()==null){
					ProjectMonthPlanGatherDateEntryInfo dateEntry=new ProjectMonthPlanGatherDateEntryInfo();
					String key=this.contractTable.getColumnKey(j);
					int year=Integer.valueOf(this.contractTable.getColumnKey(j).substring(0, key.indexOf("year")));
					int month=Integer.valueOf(this.contractTable.getColumnKey(j).substring(key.indexOf("year")+4, key.indexOf("m")));
					
					dateEntry.setYear(year);
					dateEntry.setMonth(month);
					dateEntry.setAmount(FDCHelper.ZERO);
					VersionTypeEnum versionType=(VersionTypeEnum)this.cbVersionType.getSelectedItem();
					if(versionType.equals(VersionTypeEnum.REPORT)){
						dateEntry.setReportAmount(FDCHelper.ZERO);
					}else{
						dateEntry.setExecuteAmount(FDCHelper.ZERO);
					}
					dateEntry.setAmt(FDCHelper.ZERO);
					
					key=year+"year"+month+"m"; 
					if(zeroRow.getCell(key+"amount")!=null){
						zeroRow.getCell(key+"amount").setUserObject(dateEntry);
						zeroRow.getCell(key+"amount").setValue(dateEntry.getAmount());
					}
					if(row.getCell(key+"reportAmount")!=null){
						zeroRow.getCell(key+"reportAmount").setUserObject(dateEntry);
						zeroRow.getCell(key+"reportAmount").setValue(dateEntry.getReportAmount());
					}
					if(row.getCell(key+"amt")!=null){
						zeroRow.getCell(key+"amt").setUserObject(dateEntry);
						zeroRow.getCell(key+"amt").setValue(dateEntry.getAmt());
					}
					if(row.getCell(key+"executeAmount")!=null){
						zeroRow.getCell(key+"executeAmount").setUserObject(dateEntry);
						zeroRow.getCell(key+"executeAmount").setValue(dateEntry.getExecuteAmount());
					}
					entry.getDateEntry().add(dateEntry);
				}
			}
		}
		
		Map bgRowMap=new HashMap();
		CRMHelper.sortCollection(sortCol, "bgItem.number", true);
		for(int i=0;i<sortCol.size();i++){
			ProjectMonthPlanGatherDateEntryInfo dateEntry=sortCol.get(i);
			if(dateEntry.getBgItem()==null) continue;
			String bgItemId=dateEntry.getBgItem().getId().toString();
			int month=dateEntry.getMonth();
			int year=dateEntry.getYear();
			if(bgRowMap.containsKey(bgItemId)){
				row=(IRow) bgRowMap.get(bgItemId);
			}else{
				row=this.bgTable.addRow();
				row.getCell("bgItem").setValue(dateEntry.getBgItem().getName());
				
				for(int j=0;j<this.bgTable.getColumnCount();j++){
					if(this.bgTable.getColumnKey(j).indexOf("amount")>0){
						row.getCell(j).setValue(FDCHelper.ZERO);
					}
				}
				bgRowMap.put(bgItemId, row);
			}
			String key=year+"year"+month+"m";
			
			if(row.getCell(key+"amount")!=null){
				BigDecimal amount=FDCHelper.ZERO;
				if(row.getCell(key+"amount").getValue()!=null){
					amount=(BigDecimal) row.getCell(key+"amount").getValue();
				}
				row.getCell(key+"amount").setValue(dateEntry.getAmount().add(amount));
			}
		}
		for(int i=0;i<this.editData.getPayEntry().size();i++){
			ProjectMonthPlanGatherPayEntryInfo entry=this.editData.getPayEntry().get(i);
			IRow addrow=this.payTable.addRow();
			addrow.setUserObject(entry);
			String number=entry.getPayRequestBill().getNumber();
			String name=entry.getPayRequestBill().getName();
			Date bizDate=entry.getPayRequestBill().getBookedDate();
			SupplierInfo supplier=entry.getPayRequestBill().getRealSupplier();
			
			if(entry.getContractWithoutText()!=null){
				number=entry.getContractWithoutText().getNumber();
				name=entry.getContractWithoutText().getName();
				bizDate=entry.getContractWithoutText().getBookedDate();
				supplier=entry.getContractWithoutText().getReceiveUnit();
			}
			addrow.getCell("number").setValue(number);
			addrow.getCell("name").setValue(number);
			addrow.getCell("amount").setValue(entry.getAmount());
			addrow.getCell("bizDate").setValue(bizDate);
			if(supplier!=null)
				addrow.getCell("supplier").setValue(supplier.getName());
			
			addrow.getCell("actPayAmount").setValue(entry.getActPayAmount());
			addrow.getCell("bgItem").setValue(entry.getBgItem());
			if(entry.getBgItem()!=null&&bgRowMap.containsKey(entry.getBgItem().getId().toString())){
				String key=spYear+"year"+spMonth+"m";
				
				if(row.getCell(key+"amount")!=null){
					BigDecimal amount=FDCHelper.ZERO;
					if(row.getCell(key+"amount").getValue()!=null){
						amount=(BigDecimal) row.getCell(key+"amount").getValue();
					}
					row.getCell(key+"amount").setValue(FDCHelper.add(amount, FDCHelper.subtract(entry.getAmount(), entry.getActPayAmount())));
				}
			}
		}
		
		String amountColoun[]=new String[cycle*4+1];
		for(int i=0;i<cycle*4+1;i++){
			if(i==0){
				amountColoun[0]="amount";
			}else{
				if (spMonth > 12) {
					spYear += 1;
					spMonth = 1;
				}
				String key=spYear+"year"+spMonth+"m";
				amountColoun[i]=key+"amount";
				amountColoun[i+1]=key+"reportAmount";
				amountColoun[i+2]=key+"executeAmount";
				amountColoun[i+3]=key+"amt";
				
				i=i+3;
				spMonth++;
			}
		}
		CRMClientHelper.getFootRow(this.payTable, new String[]{"amount","actPayAmount"});
		CRMClientHelper.getFootRow(this.contractTable, amountColoun);
		CRMClientHelper.getFootRow(this.contractTable, new String[]{"lastPrice","actPayAmount"});
		CRMClientHelper.getFootRow(this.bgTable, amountColoun);
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel = super.getSelectors();
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("entry.*");
    	sel.add("entry.contractBill.*");
    	sel.add("entry.contractBill.partB.*");
    	sel.add("entry.respDept.*");
    	sel.add("entry.respPerson.*");
    	sel.add("entry.programmingContract.*");
    	sel.add("entry.dateEntry.*");
    	sel.add("entry.dateEntry.bgItem.*");
    	sel.add("bizDate");
    	sel.add("amount");
    	sel.add("curProject.fullOrgUnit.id");
    	
    	sel.add("payEntry.payRequestBill.realSupplier.name");
    	sel.add("payEntry.contractWithoutText.receiveUnit.name");
    	sel.add("payEntry.payRequestBill.*");
    	sel.add("payEntry.contractWithoutText.*");
    	sel.add("payEntry.bgItem.*");
    	sel.add("payEntry.*");
		return sel;
	}
	protected IObjectValue createNewData() {
		ProjectMonthPlanGatherInfo info=(ProjectMonthPlanGatherInfo)this.getUIContext().get("info");
		if(info==null){
			info= new ProjectMonthPlanGatherInfo();
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
			FDCDataBaseCollection col = PayPlanCycleFactory.getRemoteInstance()
					.getFDCDataBaseCollection(view);
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
		info.setVersionType(VersionTypeEnum.REPORT);
		info.setName(null);
		info.setCreator(null);
		info.setCreateTime(null);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setLastUpdateUser(null);
		info.setLastUpdateTime(null);
		info.setVersionType(null);
		return info;
	}
	
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtCurProject);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbVersionType);
	}
	protected void verifyInputForSubmint() throws Exception {
		if(checkBizDate()){
			SysUtil.abort();
		}
		verifyInputForSave();
		IRow headRow=this.contractTable.getHeadRow(0);
		IRow headRowTwo=this.contractTable.getHeadRow(1);
		for(int i=0;i<this.contractTable.getRowCount();i++){
			IRow row=this.contractTable.getRow(i);
			if(row.getStyleAttributes().getBackground().equals(FDCTableHelper.cantEditColor)){
				continue;
			}
			for(int j=0;j<this.contractTable.getColumnCount();j++){
				if(this.contractTable.getColumn(j).isRequired()){
					if(row.getCell(j).getValue()!=null&&row.getCell(j).getValue() instanceof String){
						if("".equals(row.getCell(j).getValue().toString().trim())){
							FDCMsgBox.showWarning(this,headRow.getCell(j).getValue().toString()+headRowTwo.getCell(j).getValue().toString()+"不能为空！");
							this.contractTable.getEditManager().editCellAt(i, j);
							SysUtil.abort();
						}
					}else if(row.getCell(j).getValue()==null){
						FDCMsgBox.showWarning(this,headRow.getCell(j).getValue().toString()+headRowTwo.getCell(j).getValue().toString()+"不能为空！");
						this.contractTable.getEditManager().editCellAt(i, j);
						SysUtil.abort();
					}
				}
			}
		}
		checkExecuteAmount();
		checkYearAmount();
	}
	protected BigDecimal getMonthActPayAmount(String curProjectId,int year,int month) throws BOSException, SQLException{
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(pb.famount) payAmount from t_cas_paymentbill pb left join T_CON_PayRequestBill prb on prb.fid=pb.fFdcPayReqID ");
		_builder.appendSql(" where pb.fbillstatus=15 and prb.fCurProjectid='"+curProjectId+"'");
		_builder.appendSql(" and pb.famount is not null and year(pb.fpayDate)="+year+" and month(pb.fpayDate)<"+month);
		final IRowSet rowSet = _builder.executeQuery();
		while (rowSet.next()) {
			if(rowSet.getBigDecimal("payAmount")!=null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
	protected void checkExecuteAmount() throws BOSException, EASBizException, SQLException{
		this.editData.setAmount(null);
		String key=this.spYear.getIntegerVlaue()+"year"+this.spMonth.getIntegerVlaue()+"m";
		for(int i=0;i<this.contractTable.getRowCount();i++){
			ProjectMonthPlanGatherEntryInfo info=(ProjectMonthPlanGatherEntryInfo) this.contractTable.getRow(i).getUserObject();
			if(info!=null){
				this.editData.setAmount(FDCHelper.add(this.editData.getAmount(), this.contractTable.getRow(i).getCell(key+"executeAmount").getValue()));
			}
		}
		if(VersionTypeEnum.EXECUTE.equals(this.cbVersionType.getSelectedItem())
				&&this.txtExecuteAmount.getBigDecimalValue()!=null){
			if(this.editData.getAmount()==null||
					this.editData.getAmount().compareTo(this.txtExecuteAmount.getBigDecimalValue())>0){
				FDCMsgBox.showWarning(this,"项目月度资金计划超过事业部月度资金计划核定金额！");
	    		SysUtil.abort();
			}
		}
	}
	protected void checkYearAmount() throws BOSException, EASBizException, SQLException{
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("CIFI_YEARPLAN", editData.getCurProject().getFullOrgUnit().getId().toString());
		HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
		boolean isCheck=false;
		if(hmAllParam.get("CIFI_YEARPLAN")!=null){
			isCheck=Boolean.valueOf(hmAllParam.get("CIFI_YEARPLAN").toString()).booleanValue();
		}
		if(isCheck){
			int comYear=this.spYear.getIntegerVlaue().intValue();
			int comMonth=this.spMonth.getIntegerVlaue().intValue();
			
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("head.curProject.id",this.editData.getCurProject().getId().toString()));
	    	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
	    	filter.getFilterItems().add(new FilterItemInfo("year",comYear));
	    	filter.getFilterItems().add(new FilterItemInfo("amount",null,CompareType.NOTEQUALS));
	    	view.getSelector().add(new SelectorItemInfo("amount"));
	    	
	    	view.setFilter(filter);
	    	ProjectYearPlanTotalEntryCollection col=ProjectYearPlanTotalEntryFactory.getRemoteInstance().getProjectYearPlanTotalEntryCollection(view);
	    	if(col.size()>0&&this.editData.getAmount()!=null){
	    		BigDecimal actAmount=getMonthActPayAmount(this.editData.getCurProject().getId().toString(),comYear,comMonth);
	    		if(col.get(0).getAmount().compareTo(this.editData.getAmount().add(actAmount))>=0){
	    			return;
	    		}
	    	}
	    	FDCMsgBox.showWarning(this,"项目月度资金计划超过项目年度付款规划控制！");
    		SysUtil.abort();
		}
	}
	protected void checkInitVersionAmount() throws BOSException, EASBizException{
		if(this.txtVersion.getBigDecimalValue().compareTo(new BigDecimal(1))>0){
			HashMap hmParamIn = new HashMap();
			hmParamIn.put("CIFI_MONTHPLANGATHER", editData.getCurProject().getFullOrgUnit().getId().toString());
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			boolean isCheck=false;
			if(hmAllParam.get("CIFI_MONTHPLANGATHER")!=null){
				isCheck=Boolean.valueOf(hmAllParam.get("CIFI_MONTHPLANGATHER").toString()).booleanValue();
			}
			if(isCheck){
				int comYear=this.spYear.getIntegerVlaue().intValue();
				int comMonth=this.spMonth.getIntegerVlaue().intValue();
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, comYear);
				cal.set(Calendar.MONTH, comMonth-1);
				cal.set(Calendar.DATE, 1);
				Date begin=FDCDateHelper.getDayBegin(cal.getTime());
				
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("curProject.id",this.editData.getCurProject().getId().toString()));
		    	filter.getFilterItems().add(new FilterItemInfo("bizDate",begin));
	    		filter.getFilterItems().add(new FilterItemInfo("version",1));
	    		filter.getFilterItems().add(new FilterItemInfo("amount",null,CompareType.NOTEQUALS));
	    		
		    	view.getSelector().add(new SelectorItemInfo("amount"));
		    	
		    	view.setFilter(filter);
		    	ProjectMonthPlanGatherCollection col=ProjectMonthPlanGatherFactory.getRemoteInstance().getProjectMonthPlanGatherCollection(view);
		    	if(col.size()>0&&this.editData.getAmount()!=null){
		    		if(col.get(0).getAmount().compareTo(this.editData.getAmount())>=0){
		    			return;
		    		}
		    	}
		    	FDCMsgBox.showWarning(this,"当月修订的月度总金额必须“小于等于”确认通过的初始版本的月度总金额！");
	    		SysUtil.abort();
			}
		}
	}
	protected boolean checkBizDate() throws EASBizException, BOSException{
		if(this.cbVersionType.getSelectedItem()==null){
			FDCMsgBox.showWarning(this,"版本类型不能为空！");
			return true;
		}
		int comYear=this.spYear.getIntegerVlaue().intValue();
		int comMonth=this.spMonth.getIntegerVlaue().intValue();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, comYear);
		cal.set(Calendar.MONTH, comMonth-1);
		cal.set(Calendar.DATE, 1);
		Date begin=FDCDateHelper.getDayBegin(cal.getTime());

		VersionTypeEnum versionType=(VersionTypeEnum)this.cbVersionType.getSelectedItem();
		if(versionType.equals(VersionTypeEnum.REPORT)){
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id",this.editData.getCurProject().getId().toString()));
	    	filter.getFilterItems().add(new FilterItemInfo("bizDate",begin));
			filter.getFilterItems().add(new FilterItemInfo("versionType",VersionTypeEnum.EXECUTE_VALUE));
			if(ProjectMonthPlanGatherFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this,comYear+"年"+comMonth+"月项目月度资金计划已存在执行版！");
				return true;
			}
		}else{
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id",this.editData.getCurProject().getId().toString()));
	    	filter.getFilterItems().add(new FilterItemInfo("bizDate",begin));
			filter.getFilterItems().add(new FilterItemInfo("versionType",VersionTypeEnum.REPORT_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("isLatest",Boolean.TRUE));
	    	
			if(!ProjectMonthPlanGatherFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this,"请先新增"+comYear+"年"+comMonth+"月项目月度资金计划上报版！");
				return true;
			}
		}
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id",this.editData.getCurProject().getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("bizDate",begin));
    	filter.getFilterItems().add(new FilterItemInfo("versionType",versionType.getValue()));
    	if(this.editData.getId()!=null){
    		filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.NOTEQUALS));
    	}
    	SorterItemInfo version=new SorterItemInfo("version");
    	version.setSortType(SortType.DESCEND);
    	view.getSorter().add(version);
    	view.getSelector().add(new SelectorItemInfo("state"));
    	view.getSelector().add(new SelectorItemInfo("version"));
    	
    	view.setFilter(filter);
    	ProjectMonthPlanGatherCollection col=ProjectMonthPlanGatherFactory.getRemoteInstance().getProjectMonthPlanGatherCollection(view);
    	if(col.size()>0&&!col.get(0).getState().equals(FDCBillStateEnum.AUDITTED)){
    		FDCMsgBox.showWarning(this,comYear+"年"+comMonth+"月项目月度资金计划-"+((VersionTypeEnum)this.cbVersionType.getSelectedItem()).getAlias()+",版本号"+col.get(0).getVersion()+"还未审批！");
    		return true;
    	}else{
    		if(col.size()==0){
    			return false;
    		}else if(this.cbVersionType.isEnabled()){
    			FDCMsgBox.showWarning(this,"请对"+comYear+"年"+comMonth+"月项目月度资金计划-"+((VersionTypeEnum)this.cbVersionType.getSelectedItem()).getAlias()+",版本号"+col.get(0).getVersion()+"进行修订操作！");
        		return true;
    		}else if(col.get(0).getVersion()==this.txtVersion.getIntegerValue()){
    			FDCMsgBox.showWarning(this,comYear+"年"+comMonth+"月项目月度资金计划-"+((VersionTypeEnum)this.cbVersionType.getSelectedItem()).getAlias()+",版本号"+col.get(0).getVersion()+"已存在！");
        		return true;
    		}
    	}
		return false;
	}
	protected void reGetValue() throws Exception{
		if (!isLoad&&this.cbVersionType.getSelectedItem()!=null) {
			if(checkBizDate()){
				this.cbVersionType.setSelectedItem(null);
				return;
			}
			int result = MsgBox.OK;
			if (this.contractTable.getRowCount() > 0 || this.bgTable.getRowCount() > 0) {
				result = FDCMsgBox.showConfirm2(this,"改变编制月份将根据编制周期重新调整编制表格！");
			}
			if (result == MsgBox.OK) {
				this.bgTable.removeRows();
				this.contractTable.removeRows();
				this.payTable.removeRows();
				this.btnGet_actionPerformed(null);
			} else {
				this.cbVersionType.setSelectedItem(null);
				return;
			}
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
		FDCClientVerifyHelper.verifyEmpty(this, this.cbVersionType);
		boolean isShowWarn=false;
		boolean isUpdate=false;
		if(this.contractTable.getRowCount()>0||this.bgTable.getRowCount()>0){
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
        	this.editData.getEntry().clear();
        	this.editData.getPayEntry().clear();
        	this.storeFields();
        	
        	Map bgItemMap=new HashMap();
        	
        	int comYear=this.spYear.getIntegerVlaue().intValue();
    		int comMonth=this.spMonth.getIntegerVlaue().intValue();
    		Calendar cal = Calendar.getInstance();
    		cal.set(Calendar.YEAR, comYear);
    		cal.set(Calendar.MONTH, comMonth-1);
    		cal.set(Calendar.DATE, 1);
    		
        	Date begin=FDCDateHelper.getDayBegin(cal.getTime());
        	VersionTypeEnum versionType=(VersionTypeEnum)this.cbVersionType.getSelectedItem();
    		if(versionType.equals(VersionTypeEnum.REPORT)){
    			ProjectMonthPlanEntryCollection monthplancol=new ProjectMonthPlanEntryCollection();
            	Map contract=new HashMap();
            	Map entryMap=new HashMap();
            	
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
        			String key=spYear+"year"+spMonth+"m";
        			monthSet.add(key);
        			spMonth++;
            	}
            	
            	EntityViewInfo view=new EntityViewInfo();
            	FilterInfo filter=new FilterInfo();
            	
            	filter.getFilterItems().add(new FilterItemInfo("head.contractbill.curProject.id",this.editData.getCurProject().getId().toString()));
//            	if(SysContext.getSysContext().getCurrentUserInfo().getPerson()!=null){
//        			filter.getFilterItems().add(new FilterItemInfo("head.contractBill.respPerson.id", SysContext.getSysContext().getCurrentUserInfo().getPerson().getId().toString()));
//        		}
            	filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
            	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
            	filter.getFilterItems().add(new FilterItemInfo("payAmount",null,CompareType.NOTEQUALS));
            	filter.getFilterItems().add(new FilterItemInfo("year",yearSet,CompareType.INCLUDE));
            	
            	view.setFilter(filter);
            	view.getSelector().add("*");
            	view.getSelector().add("bgItem.*");
            	view.getSelector().add("head.contractBill.id");
            	view.getSelector().add("head.contractBill.number");
            	view.getSelector().add("head.contractBill.name");
            	view.getSelector().add("head.contractBill.amount");
            	view.getSelector().add("head.contractBill.partB.name");
            	view.getSelector().add("head.contractBill.programmingContract.id");
            	view.getSelector().add("head.respDept.*");
            	view.getSelector().add("head.respPerson.*");
            	SorterItemCollection sort=new SorterItemCollection();
            	SorterItemInfo number=new SorterItemInfo("head.contractBill.number");
            	number.setSortType(SortType.ASCEND);
            	sort.add(number);
            	
            	view.setSorter(sort);
            	ContractPayPlanEntryCollection col=ContractPayPlanEntryFactory.getRemoteInstance().getContractPayPlanEntryCollection(view);
            	
//            	Map bgItemMap=new HashMap();
        		for(int i=0;i<col.size();i++){
        			ContractPayPlanEntryInfo ppEntry=col.get(i);
        			BgItemInfo bgItem=ppEntry.getBgItem();
//        			if(ppEntry.getHead().getContractBill().getProgrammingContract()!=null){
//        				if(bgItemMap.containsKey(ppEntry.getHead().getContractBill().getProgrammingContract().getId().toString())){
//        					bgItem=(BgItemInfo)bgItemMap.get(ppEntry.getHead().getContractBill().getProgrammingContract().getId().toString());
//        				}else{
//        					bgItem=getBgItem(ppEntry.getHead().getContractBill().getProgrammingContract());
//        					bgItemMap.put(ppEntry.getHead().getContractBill().getProgrammingContract().getId().toString(), bgItem);
//        				}
//        			}
            		int year=ppEntry.getYear();
            		int month=ppEntry.getMonth();
            		
            		String key=year+"year"+month+"m";
            		if(!monthSet.contains(key)){
        				continue;
        			}
            		String remark=ppEntry.getRemark();
            		ContractBillInfo contractInfo=ppEntry.getHead().getContractBill();
            		String contractId=contractInfo.getId().toString();
            		BigDecimal amount=ppEntry.getPayAmount();
            		HashMap yearMap=null;
            		HashMap monthMap=null;
            		ProjectMonthPlanDateEntryInfo dateEntry=null;
            		ProjectMonthPlanEntryInfo entry=null;
            		
            		if(contract.containsKey(contractId)){
            			yearMap=(HashMap)contract.get(contractId);
            			entry=(ProjectMonthPlanEntryInfo) entryMap.get(contractId);
            			
            			if(yearMap.containsKey(year)){
            				monthMap=(HashMap)yearMap.get(year);
            				if(monthMap.containsKey(month)){
            					dateEntry=(ProjectMonthPlanDateEntryInfo) monthMap.get(month);
            					dateEntry.setAmount(dateEntry.getAmount().add(amount));
            				}else{
            					dateEntry=new ProjectMonthPlanDateEntryInfo();
            					dateEntry.setBgItem(bgItem);
            					dateEntry.setAmount(amount);
            					dateEntry.setYear(year);
            					dateEntry.setMonth(month);
            					dateEntry.setRemark(remark);
            					dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
            					dateEntry.setAmt(ppEntry.getAmt());
            					
            					entry.getDateEntry().add(dateEntry);
            					monthMap.put(month, dateEntry);
            				}
            			}else{
            				monthMap=new HashMap();
            				
            				dateEntry=new ProjectMonthPlanDateEntryInfo();
            				dateEntry.setBgItem(bgItem);
        					dateEntry.setAmount(amount);
        					dateEntry.setYear(year);
        					dateEntry.setMonth(month);
        					dateEntry.setRemark(remark);
        					dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
        					dateEntry.setAmt(ppEntry.getAmt());
        					
        					entry.getDateEntry().add(dateEntry);
        					monthMap.put(month, dateEntry);
        					
        					yearMap.put(year, monthMap);
            				
            			}
            		}else{
            			yearMap=new HashMap();
            			entry=new ProjectMonthPlanEntryInfo();
            			entry.setContractBill(contractInfo);
            			entry.setId(ppEntry.getHead().getId());
            			entry.put("respDept", ppEntry.getHead().getRespDept());
            			entry.put("respPerson", ppEntry.getHead().getRespPerson());
            			monthMap=new HashMap();
        				
        				dateEntry=new ProjectMonthPlanDateEntryInfo();
        				dateEntry.setBgItem(bgItem);
    					dateEntry.setAmount(amount);
    					dateEntry.setYear(year);
    					dateEntry.setMonth(month);
    					dateEntry.setRemark(remark);
    					dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
    					dateEntry.setAmt(ppEntry.getAmt());
    					
    					entry.getDateEntry().add(dateEntry);
    					monthMap.put(month, dateEntry);
    					
    					yearMap.put(year, monthMap);
    					
    					contract.put(contractId, yearMap);
    					
    					entryMap.put(contractId, entry);
    					
    					monthplancol.add(entry);
            		}
            	}
        		
//            	EntityViewInfo view=new EntityViewInfo();
//            	FilterInfo filter=new FilterInfo();
//            	
//            	filter.getFilterItems().add(new FilterItemInfo("head.curProject.id",this.editData.getCurProject().getId().toString()));
//            	filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
//            	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
//            	filter.getFilterItems().add(new FilterItemInfo("head.bizDate",begin));
//            	
//            	view.setFilter(filter);
//            	view.getSelector().add("dateEntry.*");
//            	view.getSelector().add("dateEntry.bgItem.*");
//            	view.getSelector().add("contractBill.id");
//            	view.getSelector().add("contractBill.number");
//            	view.getSelector().add("contractBill.name");
//            	view.getSelector().add("contractBill.amount");
//            	SorterItemCollection sort=new SorterItemCollection();
//            	SorterItemInfo seq=new SorterItemInfo("seq");
//            	seq.setSortType(SortType.ASCEND);
//            	SorterItemInfo dateSeq=new SorterItemInfo("dateEntry.seq");
//            	dateSeq.setSortType(SortType.ASCEND);
//            	sort.add(dateSeq);
//            	
//            	view.setSorter(sort);
//            	ProjectMonthPlanEntryCollection col=ProjectMonthPlanEntryFactory.getRemoteInstance().getProjectMonthPlanEntryCollection(view);
            	
            	entryMap=new HashMap();
            	
        		for(int i=0;i<monthplancol.size();i++){
        			ProjectMonthPlanEntryInfo ppEntry=monthplancol.get(i);
        			String id=null;
        			if(ppEntry.getContractBill()!=null){
        				id=ppEntry.getContractBill().getId().toString();
        			}else{
        				continue;
        			}
        			ProjectMonthPlanGatherEntryInfo entry=null;
        			if(entryMap.containsKey(id)){
        				entry=(ProjectMonthPlanGatherEntryInfo) entryMap.get(id);
        				for(int j=0;j<ppEntry.getDateEntry().size();j++){
            				ProjectMonthPlanDateEntryInfo dEntry=ppEntry.getDateEntry().get(j);
            				boolean isAddNew=true;
            				for(int k=0;k<entry.getDateEntry().size();k++){
            					if(entry.getDateEntry().get(k).getYear()==dEntry.getYear()&&entry.getDateEntry().get(k).getMonth()==dEntry.getMonth()){
            						BigDecimal amount=FDCHelper.ZERO;
            						BigDecimal addAmount=FDCHelper.ZERO;
            						if(entry.getDateEntry().get(k).getAmount()!=null) amount=entry.getDateEntry().get(k).getAmount();
            						if(dEntry.getAmount()!=null) addAmount=dEntry.getAmount();
            						entry.getDateEntry().get(k).setAmount(amount.add(addAmount));
            						
            						isAddNew=false;
            						break;
            					}
            				}
            				if(isAddNew){
            					ProjectMonthPlanGatherDateEntryInfo gdEntry=new ProjectMonthPlanGatherDateEntryInfo();
                				gdEntry.setAmount(dEntry.getAmount());
                				gdEntry.setReportAmount(dEntry.getAmount());
                				gdEntry.setYear(dEntry.getYear());
                				gdEntry.setMonth(dEntry.getMonth());
                				gdEntry.setRemark(dEntry.getRemark());
                				gdEntry.setBgItem(dEntry.getBgItem());
                				gdEntry.setId(BOSUuid.create(gdEntry.getBOSType()));
                				gdEntry.setAmt(dEntry.getAmt());
                				
                				entry.getDateEntry().add(gdEntry);
                				
                				if(dEntry.getYear()==spYear&&dEntry.getMonth()==spMonth){
                					bgItemMap.put(id, dEntry.getBgItem());
                				}
            				}
            			}
        			}else{
        				entry=new ProjectMonthPlanGatherEntryInfo();
        				entry.setContractBill(ppEntry.getContractBill());
        				entry.setName(ppEntry.getContractBill().getName());
        				entry.setAmount(ppEntry.getContractBill().getAmount());
        				entry.setId(BOSUuid.create(entry.getBOSType()));
        				entry.setSrcId(ppEntry.getId());
        				if(ppEntry.get("respDept")!=null)
        					entry.setRespDept((AdminOrgUnitInfo) ppEntry.get("respDept"));
        				if(ppEntry.get("respPerson")!=null)
        					entry.setRespPerson((PersonInfo) ppEntry.get("respPerson"));
            			for(int j=0;j<ppEntry.getDateEntry().size();j++){
            				ProjectMonthPlanDateEntryInfo dEntry=ppEntry.getDateEntry().get(j);
            				ProjectMonthPlanGatherDateEntryInfo gdEntry=new ProjectMonthPlanGatherDateEntryInfo();
            				gdEntry.setAmount(dEntry.getAmount());
            				gdEntry.setReportAmount(dEntry.getAmount());
            				gdEntry.setYear(dEntry.getYear());
            				gdEntry.setMonth(dEntry.getMonth());
            				gdEntry.setRemark(dEntry.getRemark());
            				gdEntry.setBgItem(dEntry.getBgItem());
            				gdEntry.setId(BOSUuid.create(gdEntry.getBOSType()));
            				gdEntry.setAmt(dEntry.getAmt());
            				
            				entry.getDateEntry().add(gdEntry);
            			}
            			this.editData.getEntry().add(entry);
            			
            			entryMap.put(ppEntry.getContractBill().getId().toString(), entry);
        			}
        		}
        		
        		view=new EntityViewInfo();
            	filter=new FilterInfo();
            	
            	filter.getFilterItems().add(new FilterItemInfo("head.curProject.id",this.editData.getCurProject().getId().toString()));
            	filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
            	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
            	filter.getFilterItems().add(new FilterItemInfo("head.bizDate",begin));
            	
            	view.setFilter(filter);
            	view.getSelector().add("dateEntry.*");
            	view.getSelector().add("dateEntry.bgItem.*");
            	view.getSelector().add("programmingContract.id");
            	view.getSelector().add("programmingContract.number");
            	view.getSelector().add("name");
            	view.getSelector().add("amount");
            	view.getSelector().add("head.respDept.*");
            	view.getSelector().add("head.respPerson.*");
            	sort=new SorterItemCollection();
            	SorterItemInfo seq=new SorterItemInfo("seq");
            	seq.setSortType(SortType.ASCEND);
            	SorterItemInfo dateSeq=new SorterItemInfo("dateEntry.seq");
            	dateSeq.setSortType(SortType.ASCEND);
            	sort.add(dateSeq);
            	
            	view.setSorter(sort);
            	ProjectMonthPlanProEntryCollection proCol=ProjectMonthPlanProEntryFactory.getRemoteInstance().getProjectMonthPlanProEntryCollection(view);
            	
            	entryMap=new HashMap();
            	
        		for(int i=0;i<proCol.size();i++){
        			ProjectMonthPlanProEntryInfo ppEntry=proCol.get(i);
        			String id=null;
        			if(ppEntry.getProgrammingContract()!=null){
        				id=ppEntry.getProgrammingContract().getId().toString();
        			}else{
        				id=ppEntry.getId().toString();
        			}
        			ProjectMonthPlanGatherEntryInfo entry=null;
        			if(entryMap.containsKey(id)){
        				entry=(ProjectMonthPlanGatherEntryInfo) entryMap.get(id);
        				for(int j=0;j<ppEntry.getDateEntry().size();j++){
            				ProjectMonthPlanProDateEntryInfo dEntry=ppEntry.getDateEntry().get(j);
            				boolean isAddNew=true;
            				for(int k=0;k<entry.getDateEntry().size();k++){
            					if(entry.getDateEntry().get(k).getYear()==dEntry.getYear()&&entry.getDateEntry().get(k).getMonth()==dEntry.getMonth()){
            						BigDecimal amount=FDCHelper.ZERO;
            						BigDecimal addAmount=FDCHelper.ZERO;
            						if(entry.getDateEntry().get(k).getAmount()!=null) amount=entry.getDateEntry().get(k).getAmount();
            						if(dEntry.getAmount()!=null) addAmount=dEntry.getAmount();
            						entry.getDateEntry().get(k).setAmount(amount.add(addAmount));
            						
            						isAddNew=false;
            						break;
            					}
            				}
            				if(isAddNew){
            					ProjectMonthPlanGatherDateEntryInfo gdEntry=new ProjectMonthPlanGatherDateEntryInfo();
                				gdEntry.setAmount(dEntry.getAmount());
                				gdEntry.setReportAmount(dEntry.getAmount());
                				gdEntry.setYear(dEntry.getYear());
                				gdEntry.setMonth(dEntry.getMonth());
                				gdEntry.setRemark(dEntry.getRemark());
                				gdEntry.setBgItem(dEntry.getBgItem());
                				gdEntry.setId(BOSUuid.create(gdEntry.getBOSType()));
                				
                				entry.getDateEntry().add(gdEntry);
            				}
            			}
        			}else{
        				entry=new ProjectMonthPlanGatherEntryInfo();
            			entry.setProgrammingContract(ppEntry.getProgrammingContract());
            			entry.setName(ppEntry.getName());
            			entry.setAmount(ppEntry.getAmount());
            			entry.setRespDept(ppEntry.getHead().getRespDept());
            			entry.setRespPerson(ppEntry.getHead().getRespPerson());
            			entry.setId(BOSUuid.create(entry.getBOSType()));
            			entry.setSrcId(ppEntry.getHead().getId());
            			for(int j=0;j<ppEntry.getDateEntry().size();j++){
            				ProjectMonthPlanProDateEntryInfo dEntry=ppEntry.getDateEntry().get(j);
            				ProjectMonthPlanGatherDateEntryInfo gdEntry=new ProjectMonthPlanGatherDateEntryInfo();
            				gdEntry.setAmount(dEntry.getAmount());
            				gdEntry.setReportAmount(dEntry.getAmount());
            				gdEntry.setYear(dEntry.getYear());
            				gdEntry.setMonth(dEntry.getMonth());
            				gdEntry.setRemark(dEntry.getRemark());
            				gdEntry.setBgItem(dEntry.getBgItem());
            				gdEntry.setId(BOSUuid.create(gdEntry.getBOSType()));
            				
            				entry.getDateEntry().add(gdEntry);
            			}
            			this.editData.getEntry().add(entry);
            			
            			entryMap.put(id, entry);
        			}
        		}
    		}else{
    			EntityViewInfo view=new EntityViewInfo();
    			FilterInfo filter=new FilterInfo();
            	
            	filter.getFilterItems().add(new FilterItemInfo("curProject.id",this.editData.getCurProject().getId().toString()));
            	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
            	filter.getFilterItems().add(new FilterItemInfo("isLatest",Boolean.TRUE));
            	filter.getFilterItems().add(new FilterItemInfo("bizDate",begin));
            	filter.getFilterItems().add(new FilterItemInfo("versionType",VersionTypeEnum.REPORT_VALUE));
            	
            	view.setFilter(filter);
            	SelectorItemCollection sel=new SelectorItemCollection();
             	sel.add("entry.*");
            	sel.add("entry.contractBill.*");
            	sel.add("entry.contractBill.partB.name");
            	sel.add("entry.respDept.*");
            	sel.add("entry.respPerson.*");
            	sel.add("entry.programmingContract.*");
            	sel.add("entry.dateEntry.*");
            	sel.add("entry.dateEntry.bgItem.*");
            	view.setSelector(sel);
            	ProjectMonthPlanGatherCollection col=ProjectMonthPlanGatherFactory.getRemoteInstance().getProjectMonthPlanGatherCollection(view);
            	if(col.size()>1){
            		FDCMsgBox.showWarning(this,"存在多个上报版本！");
            	}else if(col.size()==0){
            		FDCMsgBox.showWarning(this,"不存在上报版本！");
            	}else{
            		for(int i=0;i<col.get(0).getEntry().size();i++){
            			ProjectMonthPlanGatherEntryInfo entry=col.get(0).getEntry().get(i);
            			entry.setId(BOSUuid.create(entry.getBOSType()));
            			entry.setSrcId(col.get(0).getId());
            			
            			for(int j=0;j<entry.getDateEntry().size();j++){
            				ProjectMonthPlanGatherDateEntryInfo dateEntry=entry.getDateEntry().get(j);
            				dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
            				dateEntry.setExecuteAmount(dateEntry.getReportAmount());
            			}
            			this.editData.getEntry().add(entry);
            		}
            	}
    		}
    		
    		FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql("select bill.fid billId,pay.fid id,pay.famount amount,isnull(t.amount,0) actPayAmount,pay.fcontractId contractId from t_con_payRequestBill pay left join t_con_contractWithoutText bill on bill.fid=pay.fcontractId ");
			_builder.appendSql("left join (select ffdcPayReqId,sum(FAmount) amount from t_cas_paymentbill where fbillstatus=15 group by ffdcPayReqId) t on t.ffdcPayReqId=pay.fid where pay.famount!=t.amount and bill.fid is null and pay.fcurProjectId='"+this.editData.getCurProject().getId().toString()+"' ");
			_builder.appendSql("union all select bill.fid billId,bill.fid id,bill.famount amount,isnull(t.amount,0) actPayAmount,pay.fcontractId contractId from t_con_payRequestBill pay left join t_con_contractWithoutText bill on bill.fid=pay.fcontractId ");
			_builder.appendSql("left join (select ffdcPayReqId,sum(FAmount) amount from t_cas_paymentbill where fbillstatus=15 group by ffdcPayReqId) t on t.ffdcPayReqId=pay.fid where bill.famount!=t.amount and bill.fid is not null and pay.fcurProjectId='"+this.editData.getCurProject().getId().toString()+"' ");
			
			final IRowSet rowSet = _builder.executeQuery();
			while(rowSet.next()) {
				ProjectMonthPlanGatherPayEntryInfo entry=new ProjectMonthPlanGatherPayEntryInfo();
				if(rowSet.getString("billId")==null){
					SelectorItemCollection sic=new SelectorItemCollection();
					sic.add("realSupplier.name");
					sic.add("number");
					sic.add("name");
					sic.add("bookedDate");
					entry.setPayRequestBill(PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(rowSet.getString("id")), sic));
				}else{
					SelectorItemCollection sic=new SelectorItemCollection();
					sic.add("receiveUnit.name");
					sic.add("number");
					sic.add("name");
					sic.add("bookedDate");
					entry.setContractWithoutText(ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(rowSet.getString("id")), sic));
				}
				entry.setAmount(rowSet.getBigDecimal("amount"));
				entry.setActPayAmount(rowSet.getBigDecimal("actPayAmount"));
				if(bgItemMap.containsKey(bgItemMap))
					entry.setBgItem((BgItemInfo) bgItemMap.get(rowSet.getString("contractId")));
				this.editData.getPayEntry().add(entry);
			}
			
			this.loadEntry();
		}
	}
	protected void getExecuteAmount() throws BOSException{
    	EntityViewInfo view=new EntityViewInfo();
    	FilterInfo filter=new FilterInfo();
    	
    	filter.getFilterItems().add(new FilterItemInfo("headEntry.head.versionType",VersionTypeEnum.EXECUTE_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("headEntry.head.state",FDCBillStateEnum.AUDITTED_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("headEntry.head.isLatest",Boolean.TRUE));
    	filter.getFilterItems().add(new FilterItemInfo("headEntry.curProject.id",this.editData.getCurProject().getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("year",this.spYear.getIntegerVlaue().intValue()));
    	filter.getFilterItems().add(new FilterItemInfo("month",this.spMonth.getIntegerVlaue().intValue()));
    	view.setFilter(filter);
    	view.getSelector().add(new SelectorItemInfo("executeAmount"));
    	
    	OrgUnitMonthPlanGatherDateEntryCollection col=OrgUnitMonthPlanGatherDateEntryFactory.getRemoteInstance().getOrgUnitMonthPlanGatherDateEntryCollection(view);
    	if(col.size()>0){
    		this.txtExecuteAmount.setValue(col.get(0).getExecuteAmount());
    	}else{
    		this.txtExecuteAmount.setValue(FDCHelper.ZERO);
    	}
	}
	private void setColoumHide(){
		if(VersionTypeEnum.EXECUTE.equals(this.cbVersionType.getSelectedItem())){
			this.contExecuteAmount.setVisible(true);
		}else{
			this.contExecuteAmount.setVisible(false);
		}
		if(this.reportColoum!=null){
			for(int i=0;i<this.reportColoum.size();i++){
				IColumn coloum=(IColumn) this.reportColoum.get(i);
				if(VersionTypeEnum.REPORT.equals(this.cbVersionType.getSelectedItem())){
					coloum.getStyleAttributes().setHided(false);
					coloum.getStyleAttributes().setLocked(true);
					coloum.setRequired(true);
				}else if(VersionTypeEnum.EXECUTE.equals(this.cbVersionType.getSelectedItem())){
					coloum.getStyleAttributes().setHided(false);
					coloum.getStyleAttributes().setLocked(true);
					coloum.setRequired(false);
				}else{
					coloum.getStyleAttributes().setHided(true);
				}
			}
		}
		if(this.executeColoum!=null){
			for(int i=0;i<this.executeColoum.size();i++){
				IColumn coloum=(IColumn) this.executeColoum.get(i);
				if(VersionTypeEnum.REPORT.equals(this.cbVersionType.getSelectedItem())){
					coloum.getStyleAttributes().setHided(true);
					coloum.getStyleAttributes().setLocked(true);
					coloum.setRequired(false);
				}else if(VersionTypeEnum.EXECUTE.equals(this.cbVersionType.getSelectedItem())){
					coloum.getStyleAttributes().setHided(false);
					coloum.getStyleAttributes().setLocked(false);
					coloum.setRequired(true);
				}else{
					coloum.getStyleAttributes().setHided(true);
				}
			}
		}
	}
	protected void cbVersionType_itemStateChanged(ItemEvent e) throws Exception {
		if(VersionTypeEnum.EXECUTE.equals(this.cbVersionType.getSelectedItem())){
			this.actionALine.setEnabled(true);
			this.actionILine.setEnabled(true);
			this.actionRLine.setEnabled(true);
		}else{
			this.actionALine.setEnabled(false);
			this.actionILine.setEnabled(false);
			this.actionRLine.setEnabled(false);
		}
		if(!isLoad&&this.cbVersionType.getSelectedItem()!=null){
			if(checkBizDate()){
				this.cbVersionType.setSelectedItem(null);
				return;
			}
			int result = MsgBox.OK;
			if (this.contractTable.getRowCount() > 0 || this.bgTable.getRowCount() > 0) {
				result = FDCMsgBox.showConfirm2(this,"改变版本类型将根据编制周期重新调整编制表格！");
			}
			if (result == MsgBox.OK) {
				this.bgTable.removeRows();
				this.contractTable.removeRows();
				this.payTable.removeRows();
				this.btnGet_actionPerformed(null);
			} else {
				this.cbVersionType.setSelectedItem(null);
				return;
			}
		}
	}
	public void actionALine_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=this.contractTable;
		IRow row = table.addRow();
		ProjectMonthPlanGatherEntryInfo entry = new ProjectMonthPlanGatherEntryInfo();
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
			
			ProjectMonthPlanGatherDateEntryInfo dateEntry=new ProjectMonthPlanGatherDateEntryInfo();
			dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
			dateEntry.setYear(year);
			dateEntry.setMonth(month);
			dateEntry.setExecuteAmount(FDCHelper.ZERO);

			if(row.getCell(key+"executeAmount")!=null){
				row.getCell(key+"executeAmount").setUserObject(dateEntry);
				row.getCell(key+"executeAmount").setValue(FDCHelper.ZERO);
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
		KDTable table=this.contractTable;
		IRow row = null;
		if (table.getSelectManager().size() > 0) {
			int top = table.getSelectManager().get().getTop();
			if (isTableColumnSelected(table)){
				row = table.addRow();
			}else{
				if(table.getRow(top).getStyleAttributes().getBackground().equals(FDCTableHelper.cantEditColor)){
					FDCMsgBox.showInfo(this,"没有选中分录，无法插入！");
					return;
				}
				row = table.addRow(top);
			}
		} else {
			row = table.addRow();
		}
		ProjectMonthPlanGatherEntryInfo entry = new ProjectMonthPlanGatherEntryInfo();
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
			
			ProjectMonthPlanGatherDateEntryInfo dateEntry=new ProjectMonthPlanGatherDateEntryInfo();
			dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
			dateEntry.setYear(year);
			dateEntry.setMonth(month);
			dateEntry.setExecuteAmount(FDCHelper.ZERO);
			
			if(row.getCell(key+"executeAmount")!=null){
				row.getCell(key+"executeAmount").setUserObject(dateEntry);
				row.getCell(key+"executeAmount").setValue(FDCHelper.ZERO);
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
		KDTable table=this.payTable;
		if (table.getSelectManager().size() == 0 || isTableColumnSelected(table)) {
			FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
			return;
		}
		if (confirmRemove(this)) {
			int top = table.getSelectManager().get().getBeginRow();
			int bottom = table.getSelectManager().get().getEndRow();
			for (int i = top; i <= bottom; i++) {
				if (table.getRow(top) == null||table.getRow(top).getStyleAttributes().getBackground().equals(FDCTableHelper.cantEditColor)) {
					FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
					return;
				}
				ProjectMonthPlanGatherPayEntryInfo topValue=(ProjectMonthPlanGatherPayEntryInfo) table.getRow(top).getUserObject();
				table.removeRow(top);
				
				if(this.editData.getPayEntry().contains(topValue)){
					this.editData.getPayEntry().removeObject(topValue);
				}
				
				CRMClientHelper.getFootRow(this.payTable, new String[]{"amount","actPayAmount"});
			}
		}
	}
	protected void btnExport_actionPerformed(ActionEvent e) throws Exception {
		 ExportManager exportM = new ExportManager();
	        String path = null;
	        File tempFile = File.createTempFile("eastemp",".xls");
	        path = tempFile.getCanonicalPath();



	        KDTables2KDSBookVO[] tablesVO = new KDTables2KDSBookVO[1];
            tablesVO[0] = new KDTables2KDSBookVO(this.contractTable);
            String title ="付款计划明细";
            title=title.replaceAll("[{\\\\}{\\*}{\\?}{\\[}{\\]}{\\/}]", "|");
			tablesVO[0].setTableName(title);
	        KDSBook book = null;
	        book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO,true,true);

	        exportM.exportToExcel(book, path);
	        
			// 尝试用excel打开
			try
			{
				KDTMenuManager.openFileInExcel(path);
				tempFile.deleteOnExit();
			}
			catch (IOException e2)
			{
				// excel打开失败，保存到指定位置
				KDFileChooser fileChooser = new KDFileChooser();
				int result = fileChooser.showSaveDialog(this);
				if (result == KDFileChooser.APPROVE_OPTION)
				{
					// File dest = this.getFileChooser().getSelectedFile();
					File dest = fileChooser.getSelectedFile();
					try
					{
						// 重命名临时文件到指定目标
						File src = new File(path);
						if (dest.exists())
							dest.delete();
						src.renameTo(dest);
					}
					catch (Exception e3)
					{
						handUIException(e3);
					}
				}
			}
	}
	
}