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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
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
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryCollection;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryFactory;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectYearPlanDateEntryInfo;
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
public class ProjectMonthPlanEditUI extends AbstractProjectMonthPlanEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectMonthPlanEditUI.class);
    private KDTable contractTable=null;
    private Boolean isLoad=false;
    private int year_old = 0;
	private int month_old =0;
    public ProjectMonthPlanEditUI() throws Exception
    {
        super();
    }
    protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ProjectMonthPlanFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected void checkCreator(){
		if(!this.editData.getCreator().getId().toString().equals(SysContext.getSysContext().getCurrentUserInfo().getId().toString())){
			FDCMsgBox.showWarning("编制人非本人，禁止操作！");
			SysUtil.abort();
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkCreator();
		super.actionEdit_actionPerformed(e);
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkCreator();
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
		checkCreator();
		super.actionAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkCreator();
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
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		
		this.menuBiz.setVisible(false);
		this.txtVersion.setPrecision(1);
		this.actionAddNew.setVisible(false);
		
		this.spYear.setModel(new SpinnerNumberModel(this.spYear.getIntegerVlaue().intValue(),1,10000,1));
		this.spMonth.setModel(new SpinnerNumberModel(this.spMonth.getIntegerVlaue().intValue(),1,12,1));
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionALine.setEnabled(false);
			this.actionILine.setEnabled(false);
			this.actionRLine.setEnabled(false);
			
			this.btnGet.setEnabled(false);
			if(this.contractTable!=null){
				this.contractTable.setEnabled(false);
			}
		} else {
			this.unLockUI();
			this.actionALine.setEnabled(true);
			this.actionILine.setEnabled(true);
			this.actionRLine.setEnabled(true);
			
			this.btnGet.setEnabled(true);
			if(this.contractTable!=null){
				this.contractTable.setEnabled(true);
			}
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
		initControl();
	}
	public void storeFields(){
		this.editData.getEntry().clear();
		for(int i=0;i<this.contractTable.getRowCount();i++){
			ProjectMonthPlanEntryInfo entry=(ProjectMonthPlanEntryInfo) this.contractTable.getRow(i).getUserObject();
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
		this.txtName.setText("项目"+year+"年"+month+"月"+"月度付款计划-合同维度（"+this.editData.getCurProject().getName()+"）（"+SysContext.getSysContext().getCurrentUserInfo().getName()+"）");
		
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
			
			year_old = year;
			month_old = month;
			
			this.spYear.setValue(year);
			this.spMonth.setValue(month);
		}
		loadEntry();
		
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
			
			IColumn remarkColumn=table.addColumn();
			remarkColumn.setEditor(remarkEditor);
			remarkColumn.setKey(key+"remark");
			remarkColumn.getStyleAttributes().setLocked(false);
			
			IColumn bgItemColumn=table.addColumn();
			bgItemColumn.setKey(key+"bgItem");
			bgItemColumn.getStyleAttributes().setLocked(false);
			
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
		headRow.getCell("number").setValue("合同编码");
		headRowName.getCell("number").setValue("合同编码");
		
		KDBizPromptBox prmtcontract = new KDBizPromptBox();
		prmtcontract.setDisplayFormat("$name$");
		prmtcontract.setEditFormat("$number$");
		prmtcontract.setCommitFormat("$number$");
		prmtcontract.setRequired(true);
		prmtcontract.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ContractBillQueryForPayPlanQuery");

		prmtcontract.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				KDBizPromptBox f7 = (KDBizPromptBox) e.getSource();
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				
				filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("curProject.id",editData.getCurProject().getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("contractPropert",ContractPropertyEnum.SUPPLY_VALUE,CompareType.NOTEQUALS));
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
			}
		});
		KDTDefaultCellEditor contractEditor=new KDTDefaultCellEditor(prmtcontract);
		this.contractTable.getColumn("number").setEditor(contractEditor);
		this.contractTable.getColumn("number").getStyleAttributes().setLocked(false);
		ObjectValueRender ovrNum = new ObjectValueRender();
		ovrNum.setFormat(new BizDataFormat("$number$"));
		this.contractTable.getColumn("number").setRenderer(ovrNum);
		
		column=this.contractTable.addColumn();
		column.setKey("name");
		headRow.getCell("name").setValue("合同名称");
		headRowName.getCell("name").setValue("合同名称");
		
		column=this.contractTable.addColumn();
		column.setKey("amount");
		headRow.getCell("amount").setValue("合同金额");
		headRowName.getCell("amount").setValue("合同金额");
		
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
		this.contractTable.getViewManager().setFreezeView(0, 5);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 0, 1, 0);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 1, 1, 1);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 2, 1, 2);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 3, 1, 3);
		this.contractTable.getHeadMergeManager().mergeBlock(0, 4, 1, 4);
		
		initMonthColoum(this.contractTable,year,month,cycle);
		initTableBtn(this.contractTable,"已签合同付款计划");
		
		this.contractTable.addKDTEditListener(new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				table_editStopped(e);
			}
		});
	}
	
	protected void loadEntry(){
		ProjectMonthPlanEntryCollection col=this.editData.getEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.pnlBig.removeAll();
		
		int spYear=this.spYear.getIntegerVlaue().intValue();
		int spMonth=this.spMonth.getIntegerVlaue().intValue();
		int cycle=this.editData.getCycle().getCycle().getValue();
		this.initContractTable(spYear,spMonth,cycle);
		
		Map rowMap=new HashMap();
		IRow row=null;
		for(int i=0;i<col.size();i++){
			ProjectMonthPlanEntryInfo entry=col.get(i);
			String id=null;
			String name=null;
			BigDecimal amount=null;
			BigDecimal lastPrice=null;
			BigDecimal actPayAmount=null;
			if(entry.getContractBill()!=null){
				id=entry.getContractBill().getId().toString();
				
				name=entry.getContractBill().getName();
				amount=entry.getContractBill().getAmount();
				
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
			}
			for(int j=0;j<entry.getDateEntry().size();j++){
				ProjectMonthPlanDateEntryInfo dateEntry=entry.getDateEntry().get(j);
				int month=dateEntry.getMonth();
				int year=dateEntry.getYear();
				
				if(rowMap.containsKey(id)){
					row=(IRow) rowMap.get(id);
				}else{
					row=this.contractTable.addRow();
					row.setUserObject(entry);
					row.getCell("name").setValue(name);
					row.getCell("amount").setValue(amount);
					row.getCell("number").setValue(entry.getContractBill());
					row.getCell("lastPrice").setValue(lastPrice);
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
		CRMClientHelper.getFootRow(this.contractTable, amountColoun);
	}
	private void table_editStopped(KDTEditEvent e) {
		KDTable table = (KDTable) e.getSource();
		if(table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()!=null
				&&table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject() instanceof ProjectMonthPlanDateEntryInfo){
			if(table.getColumnKey(e.getColIndex()).indexOf("remark")>0){
				((ProjectMonthPlanDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setRemark((String)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
			}else if(table.getColumnKey(e.getColIndex()).indexOf("bgItem")>0){
				if(table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue() instanceof BgItemObject){
					BgItemObject bgItem=(BgItemObject) table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue();
					if(bgItem!=null){
						if(!bgItem.getResult().get(0).isIsLeaf()){
							FDCMsgBox.showWarning(this,"请选择明细预算项目！");
							table.getRow(e.getRowIndex()).getCell(e.getColIndex()).setValue(null);
							((ProjectMonthPlanDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setBgItem(null);
						}else{
							table.getRow(e.getRowIndex()).getCell(e.getColIndex()).setValue(bgItem.getResult().get(0));
							((ProjectMonthPlanDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setBgItem(bgItem.getResult().get(0));
						}
					}
				}else if(table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue() instanceof BgItemInfo){
					((ProjectMonthPlanDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setBgItem((BgItemInfo) table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
				}else{
					((ProjectMonthPlanDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setBgItem(null);
				}
			}
		}
		if(table.getColumnKey(e.getColIndex()).equals("number")){
			ProjectMonthPlanEntryInfo entry=(ProjectMonthPlanEntryInfo) table.getRow(e.getRowIndex()).getUserObject();
			
			int spYear=this.spYear.getIntegerVlaue().intValue();
			int spMonth=this.spMonth.getIntegerVlaue().intValue();
			int cycle=this.editData.getCycle().getCycle().getValue();
			
			ContractBillInfo contract=(ContractBillInfo) table.getRow(e.getRowIndex()).getCell("number").getValue();
			String name=null;
			BigDecimal amount=null;
			BigDecimal lastPrice=null;
			BigDecimal actPayAmount=null;
			BgItemInfo bgItem=null;
			if(contract!=null){
				for(int k=0;k<table.getRowCount();k++){
					ContractBillInfo comContract=(ContractBillInfo) table.getRow(k).getCell("number").getValue();
					if(k==e.getRowIndex()||comContract==null) continue;
					if(comContract.getId().toString().equals(contract.getId().toString())){
						FDCMsgBox.showWarning(this,contract.getName()+"已存在，请重新选择！");
						table.getRow(e.getRowIndex()).getCell("number").setValue(null);
						table_editStopped(e);
						SysUtil.abort();
					}
				}
				name=contract.getName();
				amount=contract.getAmount();
				try {
					bgItem=getBgItem(contract.getProgrammingContract());
					
					lastPrice=FDCUtils.getContractLastAmt (null,contract.getId().toString());
					
					FDCSQLBuilder _builder = new FDCSQLBuilder();
					_builder.appendSql("select sum(FAmount) sumCount from t_cas_paymentbill where fbillstatus=15 and fcontractbillid='"+contract.getId().toString()+"'");
					final IRowSet rowSet = _builder.executeQuery();
					if (rowSet.size() == 1) {
						rowSet.next();
						actPayAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("sumCount"));
					}
					for(int k=0;k<cycle;k++){
						if (spMonth > 12) {
							spYear += 1;
							spMonth = 1;
						}
						String key=spYear+"year"+spMonth+"m";
						ContractPayPlanEntryInfo ppEntry = this.getContractPayPlan(contract.getId().toString(), spYear, spMonth);
						ICell amountCell=table.getRow(e.getRowIndex()).getCell(key+"amount");
						ProjectMonthPlanDateEntryInfo ppDateEntry=(ProjectMonthPlanDateEntryInfo) amountCell.getUserObject();
						if(amountCell!=null){
							if(ppDateEntry==null){
								ppDateEntry=new ProjectMonthPlanDateEntryInfo();
								ppDateEntry.setBgItem(bgItem);
								ppDateEntry.setYear(spYear);
								ppDateEntry.setMonth(spMonth);
								entry.getDateEntry().add(ppDateEntry);
							}
							if(ppEntry!=null){
								amountCell.setValue(ppEntry.getPayAmount());
								ppDateEntry.setAmount(ppEntry.getPayAmount());
							}else{
								amountCell.setValue(FDCHelper.ZERO);
								ppDateEntry.setAmount(FDCHelper.ZERO);
							}
							amountCell.setUserObject(ppDateEntry);
						}
						ICell remarkCell=table.getRow(e.getRowIndex()).getCell(key+"remark");
						if(remarkCell!=null){
							if(ppEntry!=null&&ppEntry.getRemark()!=null){
								remarkCell.setValue(ppEntry.getRemark());
								ppDateEntry.setRemark(ppEntry.getRemark());
							}
							remarkCell.setUserObject(ppDateEntry);
						}
						ICell bgItemCell=table.getRow(e.getRowIndex()).getCell(key+"bgItem");
						if(bgItemCell!=null){
							bgItemCell.setValue(bgItem);
							ppDateEntry.setBgItem(bgItem);
							bgItemCell.setUserObject(ppDateEntry);
						}
						spMonth++;
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
			}else{
				for(int k=0;k<cycle;k++){
					if (spMonth > 12) {
						spYear += 1;
						spMonth = 1;
					}
					String key=spYear+"year"+spMonth+"m";
					ICell amountCell=table.getRow(e.getRowIndex()).getCell(key+"amount");
					if(amountCell!=null){
						ProjectMonthPlanDateEntryInfo ppDateEntry=(ProjectMonthPlanDateEntryInfo) amountCell.getUserObject();
						if(ppDateEntry==null){
							ppDateEntry=new ProjectMonthPlanDateEntryInfo();
							ppDateEntry.setYear(spYear);
							ppDateEntry.setMonth(spMonth);
							entry.getDateEntry().add(ppDateEntry);
						}
						amountCell.setValue(null);
						ppDateEntry.setAmount(null);
					}
					ICell remarkCell=table.getRow(e.getRowIndex()).getCell(key+"remark");
					if(remarkCell!=null){
						ProjectMonthPlanDateEntryInfo ppDateEntry=(ProjectMonthPlanDateEntryInfo) amountCell.getUserObject();
						if(ppDateEntry==null){
							ppDateEntry=new ProjectMonthPlanDateEntryInfo();
							ppDateEntry.setYear(spYear);
							ppDateEntry.setMonth(spMonth);
							entry.getDateEntry().add(ppDateEntry);
						}
						remarkCell.setValue(null);
						ppDateEntry.setRemark(null);
					}
					ICell bgItemCell=table.getRow(e.getRowIndex()).getCell(key+"bgItem");
					if(remarkCell!=null){
						ProjectMonthPlanDateEntryInfo ppDateEntry=(ProjectMonthPlanDateEntryInfo) bgItemCell.getUserObject();
						if(ppDateEntry==null){
							ppDateEntry=new ProjectMonthPlanDateEntryInfo();
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
			table.getRow(e.getRowIndex()).getCell("lastPrice").setValue(lastPrice);
			table.getRow(e.getRowIndex()).getCell("actPayAmount").setValue(actPayAmount);
			
			entry.setContractBill(contract);
			
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
		}
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel = super.getSelectors();
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("entry.*");
    	sel.add("entry.contractBill.*");
    	sel.add("entry.contractBill.programmingContract.id");
    	sel.add("entry.dateEntry.*");
    	sel.add("entry.dateEntry.bgItem.*");
    	sel.add("bizDate");
		return sel;
	}
	protected IObjectValue createNewData() {
		ProjectMonthPlanInfo info=(ProjectMonthPlanInfo)this.getUIContext().get("info");
		if(info==null){
			info= new ProjectMonthPlanInfo();
			info.setVersion(1);
			Date now=new Date();
			try {
				now=FDCCommonServerHelper.getServerTimeStamp();
			} catch (BOSException e) {
				logger.error(e.getMessage());
			}
			info.setBizDate(now);
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
		
		if((CurProjectInfo)this.getUIContext().get("curProject")!=null){
			info.setCurProject((CurProjectInfo)this.getUIContext().get("curProject"));
			try {
				FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(info.getCurProject().getCostCenter().getId()));
				info.setOrgUnit(orgUnitInfo);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}else{
			info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		}
		
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
		if(checkBizDate()){
			SysUtil.abort();
		}
		verifyInputForSave();
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
		ProjectMonthPlanEntryInfo entry = new ProjectMonthPlanEntryInfo();
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
			
			ProjectMonthPlanDateEntryInfo dateEntry=new ProjectMonthPlanDateEntryInfo();
			dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
			dateEntry.setYear(year);
			dateEntry.setMonth(month);
			
			if(row.getCell(key+"amount")!=null){
				row.getCell(key+"amount").setUserObject(dateEntry);
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
		ProjectMonthPlanEntryInfo entry = new ProjectMonthPlanEntryInfo();
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
			
			ProjectMonthPlanDateEntryInfo dateEntry=new ProjectMonthPlanDateEntryInfo();
			dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
			dateEntry.setYear(year);
			dateEntry.setMonth(month);
			
			if(row.getCell(key+"amount")!=null){
				row.getCell(key+"amount").setUserObject(dateEntry);
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
				ProjectMonthPlanEntryInfo topValue=(ProjectMonthPlanEntryInfo) table.getRow(top).getUserObject();
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
	protected ContractPayPlanEntryInfo getContractPayPlan(String id,int year,int month) throws BOSException{
		EntityViewInfo view=new EntityViewInfo();
    	FilterInfo filter=new FilterInfo();
    	
    	filter.getFilterItems().add(new FilterItemInfo("head.contractbill.id",id));
    	filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
    	filter.getFilterItems().add(new FilterItemInfo("payAmount",null,CompareType.NOTEQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("month",null,CompareType.NOTEQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("year",null,CompareType.NOTEQUALS));
    	
    	view.setFilter(filter);
    	view.getSelector().add("*");
    	ContractPayPlanEntryCollection col=ContractPayPlanEntryFactory.getRemoteInstance().getContractPayPlanEntryCollection(view);
    	
		for(int i=0;i<col.size();i++){
			ContractPayPlanEntryInfo ppEntry=col.get(i);
			if(year==ppEntry.getYear()&&month==ppEntry.getMonth()){
				return ppEntry;
			}
		}
		return null;
	}
	
	protected boolean checkBizDate() throws EASBizException, BOSException{
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
    	filter.getFilterItems().add(new FilterItemInfo("creator.id",SysContext.getSysContext().getCurrentUserInfo().getId().toString()));
    	if(this.editData.getId()!=null){
    		filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.NOTEQUALS));
    	}
    	SorterItemInfo version=new SorterItemInfo("version");
    	version.setSortType(SortType.DESCEND);
    	view.getSorter().add(version);
    	view.getSelector().add(new SelectorItemInfo("state"));
    	view.getSelector().add(new SelectorItemInfo("version"));
    	view.setFilter(filter);
    	ProjectMonthPlanCollection col=ProjectMonthPlanFactory.getRemoteInstance().getProjectMonthPlanCollection(view);
    	if(col.size()>0&&!col.get(0).getState().equals(FDCBillStateEnum.AUDITTED)){
    		FDCMsgBox.showWarning(this,comYear+"年"+comMonth+"月项目月度付款计划还未审批！");
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
		if (this.contractTable.getRowCount() > 0) {
			result = FDCMsgBox.showConfirm2(this,"改变编制月份将重新获取分录数据！");
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
		for(int i=0;i<this.contractTable.getRowCount();i++){
			ProjectMonthPlanEntryInfo entry=(ProjectMonthPlanEntryInfo) this.contractTable.getRow(i).getUserObject();
			if(entry!=null){
				int year=this.spYear.getIntegerVlaue().intValue();
				int month=this.spMonth.getIntegerVlaue().intValue();
				int cycle=this.editData.getCycle().getCycle().getValue();
				
				for(int j=0;j<cycle;j++){
					if (month > 12) {
						year += 1;
						month = 1;
					}
					
					ProjectMonthPlanDateEntryInfo dateEntry=new ProjectMonthPlanDateEntryInfo();
					dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
					dateEntry.setYear(year);
					dateEntry.setMonth(month);
					
					entry.getDateEntry().add(dateEntry);
					month++;
				}
			}
		}
		this.loadEntry();
		KDTEditEvent contractE=new KDTEditEvent(this.contractTable);
		for(int i=0;i<this.contractTable.getRowCount();i++){
			contractE.setRowIndex(i);
			contractE.setColIndex(this.contractTable.getColumnIndex("number"));
			table_editStopped(contractE);
		}
	}
	protected void spMonth_stateChanged(ChangeEvent e) throws Exception {
		reGetValue();
	}
	protected void spYear_stateChanged(ChangeEvent e) throws Exception {
		reGetValue();
	}
	protected BgItemInfo getBgItem(ProgrammingContractInfo pc) throws EASBizException, BOSException{
		if(pc==null) return null;
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("costEntries.costAccount.longNumber");
		pc=ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(pc.getId()),sel);
		Set costAccount=new HashSet();
		for(int i=0;i<pc.getCostEntries().size();i++){
			costAccount.add(pc.getCostEntries().get(i).getCostAccount().getLongNumber());
		}
		return CostAccountWithBgItemFactory.getRemoteInstance().getBgItem(costAccount);
	}
	protected void btnGet_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getCurProject()==null){
			FDCMsgBox.showWarning(this,"工程项目为空！");
			return;
		}
		boolean isShowWarn=false;
		boolean isUpdate=false;
		if(this.contractTable.getRowCount()>0){
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
        	
        	this.contractTable.removeRows();
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
        	
        	filter.getFilterItems().add(new FilterItemInfo("head.contractbill.curProject.id",this.editData.getCurProject().getId().toString()));
        	if(SysContext.getSysContext().getCurrentUserInfo().getPerson()!=null){
    			filter.getFilterItems().add(new FilterItemInfo("head.contractBill.respPerson.id", SysContext.getSysContext().getCurrentUserInfo().getPerson().getId().toString()));
    		}
        	filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
        	filter.getFilterItems().add(new FilterItemInfo("payAmount",null,CompareType.NOTEQUALS));
        	filter.getFilterItems().add(new FilterItemInfo("month",monthSet,CompareType.INCLUDE));
        	filter.getFilterItems().add(new FilterItemInfo("year",yearSet,CompareType.INCLUDE));
        	
        	view.setFilter(filter);
        	view.getSelector().add("*");
        	view.getSelector().add("head.contractBill.id");
        	view.getSelector().add("head.contractBill.number");
        	view.getSelector().add("head.contractBill.name");
        	view.getSelector().add("head.contractBill.amount");
        	view.getSelector().add("head.contractBill.programmingContract.id");
        	SorterItemCollection sort=new SorterItemCollection();
        	SorterItemInfo number=new SorterItemInfo("head.contractBill.number");
        	number.setSortType(SortType.ASCEND);
        	sort.add(number);
        	
        	view.setSorter(sort);
        	ContractPayPlanEntryCollection col=ContractPayPlanEntryFactory.getRemoteInstance().getContractPayPlanEntryCollection(view);
        	
        	Map bgItemMap=new HashMap();
    		for(int i=0;i<col.size();i++){
    			ContractPayPlanEntryInfo ppEntry=col.get(i);
    			BgItemInfo bgItem=null;
    			if(ppEntry.getHead().getContractBill().getProgrammingContract()!=null){
    				if(bgItemMap.containsKey(ppEntry.getHead().getContractBill().getProgrammingContract().getId().toString())){
    					bgItem=(BgItemInfo)bgItemMap.get(ppEntry.getHead().getContractBill().getProgrammingContract().getId().toString());
    				}else{
    					bgItem=getBgItem(ppEntry.getHead().getContractBill().getProgrammingContract());
    					bgItemMap.put(ppEntry.getHead().getContractBill().getProgrammingContract().getId().toString(), bgItem);
    				}
    			}
        		int year=ppEntry.getYear();
        		int month=ppEntry.getMonth();
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
    					
    					entry.getDateEntry().add(dateEntry);
    					monthMap.put(month, dateEntry);
    					
    					yearMap.put(year, monthMap);
        				
        			}
        		}else{
        			yearMap=new HashMap();
        			entry=new ProjectMonthPlanEntryInfo();
        			entry.setContractBill(contractInfo);
        			entry.setId(BOSUuid.create(entry.getBOSType()));
        			
        			monthMap=new HashMap();
    				
    				dateEntry=new ProjectMonthPlanDateEntryInfo();
    				dateEntry.setBgItem(bgItem);
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
    		KDTEditEvent contractE=new KDTEditEvent(this.contractTable);
    		for(int i=0;i<this.contractTable.getRowCount();i++){
    			contractE.setRowIndex(i);
    			contractE.setColIndex(this.contractTable.getColumnIndex("number"));
    			table_editStopped(contractE);
    		}
		}
	}
	
}