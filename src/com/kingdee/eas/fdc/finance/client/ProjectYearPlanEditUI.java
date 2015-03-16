/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.ActionMap;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
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
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.LineStyle;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.Position;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
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
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPayItem;
import com.kingdee.eas.fdc.contract.ContractPayItemCollection;
import com.kingdee.eas.fdc.contract.ContractPayItemFactory;
import com.kingdee.eas.fdc.contract.ContractPayPlanCollection;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryCollection;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryFactory;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryInfo;
import com.kingdee.eas.fdc.contract.ContractPayPlanFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectYearPlanCollection;
import com.kingdee.eas.fdc.finance.ProjectYearPlanDateEntry;
import com.kingdee.eas.fdc.finance.ProjectYearPlanDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectYearPlanEntry;
import com.kingdee.eas.fdc.finance.ProjectYearPlanEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectYearPlanEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectYearPlanFactory;
import com.kingdee.eas.fdc.finance.ProjectYearPlanInfo;
import com.kingdee.eas.fdc.finance.ProjectYearPlanTotalBgItemEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectYearPlanTotalEntryInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.ma.bg.client.BgItemF7Selector;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.BgItemObject;
import com.kingdee.eas.ma.budget.client.BgItemSelectListUI;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.ma.budget.client.NewBgItemDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class ProjectYearPlanEditUI extends AbstractProjectYearPlanEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectYearPlanEditUI.class);
    private Map kdtEntrys = new HashMap();
    private Boolean isLoad=false;
    private int year_old = 0;
	private int month_old =0;
    public ProjectYearPlanEditUI() throws Exception
    {
        super();
    }
	protected void attachListeners() {
		
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ProjectYearPlanFactory.getRemoteInstance();
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
			this.btnGet.setEnabled(false);
			this.actionALine.setEnabled(false);
			this.actionILine.setEnabled(false);
			this.actionRLine.setEnabled(false);
			
			Object[] key = this.kdtEntrys.keySet().toArray(); 
			for (int k = 0; k < key.length; k++) { 
				KDTable table = (KDTable) this.kdtEntrys.get(key[k]);
				table.setEnabled(false);
			}
		} else {
			this.unLockUI();
			this.btnGet.setEnabled(true);
//			this.actionALine.setEnabled(true);
//			this.actionILine.setEnabled(true);
//			this.actionRLine.setEnabled(true);
			this.actionALine.setEnabled(false);
			this.actionILine.setEnabled(false);
			this.actionRLine.setEnabled(false);
			
			Object[] key = this.kdtEntrys.keySet().toArray(); 
			for (int k = 0; k < key.length; k++) { 
				KDTable table = (KDTable) this.kdtEntrys.get(key[k]);
				table.setEnabled(true);
			}
		}
	}
	public void onLoad() throws Exception {
		initTable();
		super.onLoad();
		initControl();
	}
	public void storeFields(){
		Object[] key = this.kdtEntrys.keySet().toArray(); 
		Arrays.sort(key); 
		
		this.editData.getEntry().clear();
		for (int k = 0; k < key.length; k++) { 
			KDTable table = (KDTable) this.kdtEntrys.get(key[k]);
			for(int i=0;i<table.getRowCount();i++){
				ProjectYearPlanEntryInfo entry=(ProjectYearPlanEntryInfo) table.getRow(i).getUserObject();
				if(entry!=null){
					this.editData.getEntry().add(entry);
				}
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
		this.txtName.setText("项目"+year+"年"+month+"月"+"年度付款规划（"+this.editData.getCurProject().getName()+"）");
		
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
	protected KDTable createTable(){
		KDTable table=new KDTable();
		table.checkParsed();
		IRow headRow=table.addHeadRow();
		IRow headRowName=table.addHeadRow();
		
		table.getStyleAttributes().setLocked(true);
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		amount.setNegatived(false);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		
		IColumn column=table.addColumn();
		column.setKey("number");
		headRow.getCell("number").setValue("编码");
		headRowName.getCell("number").setValue("编码");
		
		column=table.addColumn();
		column.setKey("name");
		headRow.getCell("name").setValue("名称");
		headRowName.getCell("name").setValue("名称");
		
		column=table.addColumn();
		column.setKey("amount");
		headRow.getCell("amount").setValue("金额");
		headRowName.getCell("amount").setValue("金额");
		
		column=table.addColumn();
		column.setKey("lastPrice");
		headRow.getCell("lastPrice").setValue("合同最新造价");
		headRowName.getCell("lastPrice").setValue("合同最新造价");
		
		column=table.addColumn();
		column.setKey("actPayAmount");
		headRow.getCell("actPayAmount").setValue("合同已付金额");
		headRowName.getCell("actPayAmount").setValue("合同已付金额");
		
		table.getColumn("amount").setEditor(amountEditor);
		table.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		table.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		table.getColumn("lastPrice").setEditor(amountEditor);
		table.getColumn("lastPrice").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		table.getColumn("lastPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		table.getColumn("actPayAmount").setEditor(amountEditor);
		table.getColumn("actPayAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		table.getColumn("actPayAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
        
		
		table.setName("");
		table.getViewManager().setFreezeView(0, 5);
		table.getHeadMergeManager().mergeBlock(0, 0, 1, 0);
		table.getHeadMergeManager().mergeBlock(0, 1, 1, 1);
		table.getHeadMergeManager().mergeBlock(0, 2, 1, 2);
		table.getHeadMergeManager().mergeBlock(0, 3, 1, 3);
		table.getHeadMergeManager().mergeBlock(0, 4, 1, 4);
		
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
		
		KDTextField remark = new KDTextField();
		remark.setMaxLength(255);
		KDTDefaultCellEditor remarkEditor = new KDTDefaultCellEditor(remark);
		for(int i=1;i<13;i++){
			IColumn amountColumn=table.addColumn();
			amountColumn.setKey(i+"amount");
			table.getColumn(i+"amount").setEditor(amountEditor);
			table.getColumn(i+"amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			table.getColumn(i+"amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			
			IColumn actAmountColumn=table.addColumn();
			actAmountColumn.setKey(i+"actAmount");
			table.getColumn(i+"actAmount").setEditor(amountEditor);
			table.getColumn(i+"actAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			table.getColumn(i+"actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			
			IColumn remarkColumn=table.addColumn();
			remarkColumn.setEditor(remarkEditor);
			remarkColumn.setKey(i+"remark");
			remarkColumn.getStyleAttributes().setLocked(false);
			
			IColumn bgItemColumn=table.addColumn();
			bgItemColumn.setKey(i+"bgItem");
			bgItemColumn.getStyleAttributes().setLocked(false);
			
			table.getColumn(i+"bgItem").setEditor(f7Editor);
			
			table.getHeadRow(0).getCell(i+"amount").setValue(i+"月");
			table.getHeadRow(0).getCell(i+"actAmount").setValue(i+"月");
			table.getHeadRow(0).getCell(i+"remark").setValue(i+"月");
			table.getHeadRow(0).getCell(i+"bgItem").setValue(i+"月");
			
			table.getHeadRow(1).getCell(i+"amount").setValue("计划支付");
			table.getHeadRow(1).getCell(i+"actAmount").setValue("实际支付");
			table.getHeadRow(1).getCell(i+"remark").setValue("用款说明");
			table.getHeadRow(1).getCell(i+"bgItem").setValue("预算项目");
			
			int merge=(i-1)*4+5;
			table.getHeadMergeManager().mergeBlock(0, merge, 0, merge+3);
		}
		ActionMap actionMap = table.getActionMap();
		actionMap.remove(KDTAction.CUT);
		actionMap.remove(KDTAction.DELETE);
		actionMap.remove(KDTAction.PASTE);
		return table;
	}
	protected void initTable() {
		this.kdtAmount.checkParsed();
		this.kdtAmount.getStyleAttributes().setLocked(true);
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		amount.setNegatived(false);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		this.kdtAmount.getColumn("total").setEditor(amountEditor);
		this.kdtAmount.getColumn("total").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtAmount.getColumn("total").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.kdtDate.checkParsed();
		this.kdtDate.getStyleAttributes().setLocked(true);
		this.kdtDate.getColumn("total").setEditor(amountEditor);
		this.kdtDate.getColumn("total").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtDate.getColumn("total").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
	}
	protected BigDecimal getMonthActPayAmount(String id,int year,int month,boolean isAll) throws BOSException, SQLException{
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(famount) payAmount from t_cas_paymentbill where fbillstatus=15 and fcontractbillid='"+id+"'");
		if(isAll){
			_builder.appendSql(" and famount is not null");
		}else{
			_builder.appendSql(" and famount is not null and year(fpayDate)="+year+" and month(fpayDate)="+month);
		}
		final IRowSet rowSet = _builder.executeQuery();
		while (rowSet.next()) {
			if(rowSet.getBigDecimal("payAmount")!=null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
	protected BigDecimal getMonthActPayAmount(String id,String bgItemId,int year,int month) throws BOSException, SQLException{
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(prbEntry.frequestAmount) payAmount from t_cas_paymentbill pb left join T_CON_PayRequestBill prb on prb.fid=pb.fFdcPayReqID ");
		_builder.appendSql(" left join T_CON_PayRequestBillBgEntry prbEntry on prbEntry.FHeadId = prb.fid ");
		_builder.appendSql(" where pb.fbillstatus=15 and pb.fcontractbillid='"+id+"' and prbEntry.fbgItemId='"+bgItemId+"'");
		_builder.appendSql(" and prbEntry.frequestAmount is not null and year(pb.fpayDate)="+year+" and month(pb.fpayDate)="+month);
		final IRowSet rowSet = _builder.executeQuery();
		while (rowSet.next()) {
			if(rowSet.getBigDecimal("payAmount")!=null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
	protected void loadEntry(){
		ProjectYearPlanEntryCollection col=this.editData.getEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.pnlBig.removeAll();
		
		this.kdtEntrys=new HashMap();
		HashMap rowMap=new HashMap();
		
		IRow row=null;
		for(int i=0;i<col.size();i++){
			ProjectYearPlanEntryInfo entry=col.get(i);
			String id=null;
			String number=null;
			String name=null;
			BigDecimal amount=null;
			if(entry.getContractBill()!=null){
				id=entry.getContractBill().getId().toString();
				number=entry.getContractBill().getNumber();
				name=entry.getContractBill().getName();
				amount=entry.getContractBill().getAmount();
			}else if(entry.getProgrammingContract()!=null){
				id=entry.getProgrammingContract().getId().toString();
				number=entry.getProgrammingContract().getNumber();
				name=entry.getProgrammingContract().getName();
				amount=entry.getProgrammingContract().getAmount();
			}else{
				id=entry.getId().toString();
			}
			for(int j=0;j<entry.getDateEntry().size();j++){
				ProjectYearPlanDateEntryInfo dateEntry=entry.getDateEntry().get(j);
				int month=dateEntry.getMonth();
				int year=dateEntry.getYear();
				
				if(rowMap.containsKey(year+id)){
					row=(IRow) rowMap.get(year+id);
				}else{
					KDTable table=null;
					if(this.kdtEntrys.containsKey(year)){
						table=(KDTable) this.kdtEntrys.get(year);
						row=table.addRow();
					}else{
						table=createTable();
						row=table.addRow();
						table.setName(String.valueOf(year));
						this.kdtEntrys.put(year, table);
						
						table.addKDTEditListener(new KDTEditAdapter() {
							public void editStopped(KDTEditEvent e) {
								table_editStopped(e);
							}
						});
					}
					if(entry.getContractBill()!=null){
						try{
							BigDecimal lastPrice=FDCUtils.getContractLastAmt (null,id);
							BigDecimal actPayAmount=getMonthActPayAmount(id,year,month,true);
							row.getCell("lastPrice").setValue(lastPrice);
							row.getCell("actPayAmount").setValue(actPayAmount);
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
					rowMap.put(year+id, row);
				}
				row.setUserObject(entry);
				row.getCell("number").setValue(number);
				row.getCell("name").setValue(name);
				row.getCell("amount").setValue(amount);
				
				if(entry.getContractBill()==null&&entry.getProgrammingContract()==null){
					for(int mi=1;mi<13;mi++){
						row.getCell(mi+"actAmount").getStyleAttributes().setLocked(false);
						row.getCell(mi+"amount").getStyleAttributes().setLocked(false);
						row.getCell(mi+"remark").getStyleAttributes().setLocked(false);
						row.getCell(mi+"bgItem").getStyleAttributes().setLocked(false);
					}
				}
				row.getCell(month+"amount").setValue(dateEntry.getAmount());
				row.getCell(month+"amount").setUserObject(dateEntry);
				row.getCell(month+"actAmount").setValue(dateEntry.getActAmount());
				row.getCell(month+"actAmount").setUserObject(dateEntry);
				row.getCell(month+"remark").setUserObject(dateEntry);
				row.getCell(month+"remark").setValue(dateEntry.getRemark());
				row.getCell(month+"bgItem").setUserObject(dateEntry);
				row.getCell(month+"bgItem").setValue(dateEntry.getBgItem());
			}
		}
		Object[] key = this.kdtEntrys.keySet().toArray(); 
		Arrays.sort(key); 
		
		for (int k = 0; k < key.length; k++) { 
			KDTable table = (KDTable) this.kdtEntrys.get(key[k]);
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
			
			this.pnlBig.add(contEntry, key[k]+"年度");
			String amountColoun[]=new String[13];
			for(int i=0;i<13;i++){
				if(i==0){
					amountColoun[0]="amount";
				}else{
					amountColoun[i]=i+"amount";
				}
			}
			String actAmountColoun[]=new String[12];
			for(int i=1;i<13;i++){
				actAmountColoun[i-1]=i+"actAmount";
			}
			CRMClientHelper.getFootRow(table, actAmountColoun);
			CRMClientHelper.getFootRow(table, amountColoun);
			CRMClientHelper.getFootRow(table, new String[]{"lastPrice","actPayAmount"});
			int proIndex=0;
			int otherIndex=0;
			for(int i=0;i<table.getRowCount();i++){
				if(((ProjectYearPlanEntryInfo)table.getRow(i).getUserObject()).getProgrammingContract()!=null){
					proIndex=i+1;
					break;
				}
			}
			
			for(int i=0;i<table.getRowCount();i++){
				if(((ProjectYearPlanEntryInfo)table.getRow(i).getUserObject()).getProgrammingContract()==null
						&&((ProjectYearPlanEntryInfo)table.getRow(i).getUserObject()).getContractBill()==null){
					otherIndex=i+2;
					break;
				}
			}
			
			IRow contractRow=table.addRow(0);
			contractRow.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			contractRow.getStyleAttributes().setLocked(true);
			contractRow.getCell("number").setValue("合同信息");
			table.getMergeManager().mergeBlock(0, 0, 0, table.getColumnCount()-1);
			
			IRow proRow=null;
			if(proIndex==0){
				proRow=table.addRow();
				proIndex=proRow.getRowIndex();
			}else{
				proRow=table.addRow(proIndex);
			}
			proRow.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			proRow.getStyleAttributes().setLocked(true);
			proRow.getCell("number").setValue("合约规划");
			table.getMergeManager().mergeBlock(proIndex, 0, proIndex, table.getColumnCount()-1);
			
			IRow otherRow=null;
			if(otherIndex==0){
				otherRow=table.addRow();
				otherIndex=otherRow.getRowIndex();
			}else{
				otherRow=table.addRow(otherIndex);
			}
			otherRow.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			otherRow.getStyleAttributes().setLocked(true);
			otherRow.getCell("number").setValue("其它信息");
			table.getMergeManager().mergeBlock(otherIndex, 0, otherIndex, table.getColumnCount()-1);
		} 
		sortTotalTable();
	}
	private void sortTotalTable(){
		Map sort=new HashMap();
		for(int i=0;i<this.kdtAmount.getRowCount();i++){
			sort.put(this.kdtAmount.getRow(i).getCell("year").getValue().toString(), this.kdtAmount.getRow(i).clone());
		}
		this.kdtAmount.removeRows();
		Object[] sortKey = sort.keySet().toArray(); 
		Arrays.sort(sortKey); 
		for (int k = 0; k < sortKey.length; k++) { 
			IRow oldRow=(IRow) sort.get(sortKey[k]);
			if(oldRow.getCell("total").getValue()!=null&&((BigDecimal)oldRow.getCell("total").getValue()).compareTo(FDCHelper.ZERO)>0){
				IRow newRow=this.kdtAmount.addRow();
				newRow.getCell("year").setValue(oldRow.getCell("year").getValue());
				newRow.getCell("total").setValue(oldRow.getCell("total").getValue());
			}
		}
		sort=new HashMap();
		for(int i=0;i<this.kdtDate.getRowCount();i++){
			String bgItem="";
			if(this.kdtDate.getRow(i).getCell("bgItem").getValue()!=null){
				bgItem=((BgItemInfo)this.kdtDate.getRow(i).getCell("bgItem").getValue()).getId().toString();
			}
			sort.put(this.kdtDate.getRow(i).getCell("year").getValue().toString()+bgItem, this.kdtDate.getRow(i).clone());
		}
		this.kdtDate.removeRows();
		sortKey = sort.keySet().toArray(); 
		Arrays.sort(sortKey); 
		for (int k = 0; k < sortKey.length; k++) { 
			IRow oldRow=(IRow) sort.get(sortKey[k]);
			if(oldRow.getCell("total").getValue()!=null&&((BigDecimal)oldRow.getCell("total").getValue()).compareTo(FDCHelper.ZERO)>0){
				IRow newRow=this.kdtDate.addRow();
				newRow.getCell("year").setValue(oldRow.getCell("year").getValue());
				newRow.getCell("total").setValue(oldRow.getCell("total").getValue());
				newRow.getCell("bgItem").setValue(oldRow.getCell("bgItem").getValue());
			}
		}
		
		CRMClientHelper.getFootRow(this.kdtAmount, new String[]{"total"});
		CRMClientHelper.getFootRow(this.kdtDate, new String[]{"total"});
	}
	private void setTotalTable() throws BOSException, SQLException{
		this.kdtAmount.removeRows();
		this.kdtDate.removeRows();
		
		HashMap amountDateTotal=new HashMap();
		HashMap amountTotal=new HashMap();
		
		ProjectYearPlanEntryCollection col=this.editData.getEntry();
		CRMHelper.sortCollection(col, "seq", true);
		
		int comYear=this.spYear.getIntegerVlaue().intValue();
		int comMonth=this.spMonth.getIntegerVlaue().intValue();
		
		for(int i=0;i<col.size();i++){
			ProjectYearPlanEntryInfo entry=col.get(i);
    		
			for(int j=0;j<entry.getDateEntry().size();j++){
				ProjectYearPlanDateEntryInfo dateEntry=entry.getDateEntry().get(j);
				int year=dateEntry.getYear();
				int month=dateEntry.getMonth();
				BgItemInfo bgItem=dateEntry.getBgItem();
				
				BigDecimal amount=dateEntry.getAmount();
				BigDecimal actAmount=FDCHelper.ZERO;
				if(entry.getContractBill()!=null){
					actAmount=getMonthActPayAmount(entry.getContractBill().getId().toString(),year,month,false);
				}
    			
				if(amountTotal.containsKey(year)){
					BigDecimal totalAmount=(BigDecimal) ((IRow)amountTotal.get(year)).getCell("total").getValue();
        			if(year<comYear||(year==comYear&&month<comMonth)){
        				((IRow)amountTotal.get(year)).getCell("total").setValue(FDCHelper.add(totalAmount, actAmount));
        			}else{
        				((IRow)amountTotal.get(year)).getCell("total").setValue(FDCHelper.add(totalAmount, amount));
        			}
				}else{
					IRow amountRow=this.kdtAmount.addRow();
					amountRow.getCell("year").setValue(year);
					if(year<comYear||(year==comYear&&month<comMonth)){
						amountRow.getCell("total").setValue(actAmount);
        			}else{
        				amountRow.getCell("total").setValue(amount);
        			}
					amountTotal.put(year, amountRow);
				}
				
				String bgItemId="";
    			BigDecimal bgItemAmount=FDCHelper.ZERO;
    			if(bgItem!=null){
    				bgItemId=bgItem.getId().toString();
    				if(entry.getContractBill()!=null){
    					bgItemAmount=getMonthActPayAmount(entry.getContractBill().getId().toString(),bgItemId,year,month);
    				}
    			}
				if(amountDateTotal.containsKey(year+bgItemId)){
					BigDecimal totalAmount=(BigDecimal) ((IRow)amountDateTotal.get(year+bgItemId)).getCell("total").getValue();
        			if(year<comYear||(year==comYear&&month<comMonth)){
        				((IRow)amountDateTotal.get(year+bgItemId)).getCell("total").setValue(FDCHelper.add(totalAmount, bgItemAmount));
        			}else{
        				((IRow)amountDateTotal.get(year+bgItemId)).getCell("total").setValue(FDCHelper.add(totalAmount, amount));
        			}
				}else{
					IRow amountDateRow=this.kdtDate.addRow();
					amountDateRow.getCell("year").setValue(year);
					amountDateRow.getCell("bgItem").setValue(dateEntry.getBgItem());
					if(year<comYear||(year==comYear&&month<comMonth)){
						amountDateRow.getCell("total").setValue(bgItemAmount);
        			}else{
    					amountDateRow.getCell("total").setValue(amount);
        			}
					amountDateTotal.put(year+bgItemId, amountDateRow);
				}
			}
		}
		sortTotalTable();
	}
	private void table_editStopped(KDTEditEvent e) {
		KDTable table = (KDTable) e.getSource();
		if(table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()!=null
				&&table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject() instanceof ProjectYearPlanDateEntryInfo){
			if(table.getColumnKey(e.getColIndex()).indexOf("remark")>0){
				((ProjectYearPlanDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setRemark((String)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
			}else if(table.getColumnKey(e.getColIndex()).indexOf("bgItem")>0||table.getColumnKey(e.getColIndex()).indexOf("amount")>0){
				boolean isChanged = true;
				 if(e.getOldValue() == null && e.getValue() == null) isChanged = false;
				if(table.getColumnKey(e.getColIndex()).indexOf("amount")>0){
					((ProjectYearPlanDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setAmount((BigDecimal)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
					CRMClientHelper.getFootRow(table, new String[]{table.getColumnKey(e.getColIndex())});
					
			        if(e.getOldValue() != null && e.getValue() != null){
			        	BigDecimal oldValue = (BigDecimal)e.getOldValue();
			        	BigDecimal newValue = (BigDecimal)e.getValue();
			            if(oldValue.compareTo(newValue)!=0)
			                isChanged = false;
			        }
				}else{
					if(table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue() instanceof BgItemObject){
						BgItemObject bgItem=(BgItemObject) table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue();
						if(bgItem!=null){
							if(!bgItem.getResult().get(0).isIsLeaf()){
								FDCMsgBox.showWarning(this,"请选择明细预算项目！");
								table.getRow(e.getRowIndex()).getCell(e.getColIndex()).setValue(null);
								((ProjectYearPlanDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setBgItem(null);
							}else{
								table.getRow(e.getRowIndex()).getCell(e.getColIndex()).setValue(bgItem.getResult().get(0));
								((ProjectYearPlanDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setBgItem(bgItem.getResult().get(0));
							}
						}
					}else if(table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue() instanceof BgItemInfo){
						((ProjectYearPlanDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setBgItem((BgItemInfo) table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue());
					}else{
						((ProjectYearPlanDateEntryInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()).setBgItem(null);
					}
					
					if(e.getOldValue() != null && table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue() != null){
						BgItemInfo oldValue = (BgItemInfo)e.getOldValue();
						BgItemInfo newValue = (BgItemInfo)table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue();
			            if(oldValue.getId().toString().equals(newValue.getId().toString())) isChanged = false;
			        }
				}
				try {
					if(isChanged){
						setTotalTable();
					}
				} catch (BOSException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel = super.getSelectors();
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("entry.*");
    	sel.add("entry.contractBill.*");
    	sel.add("entry.programmingContract.*");
    	sel.add("entry.dateEntry.*");
    	sel.add("entry.dateEntry.bgItem.*");
    	sel.add("bizDate");
		return sel;
	}
	protected IObjectValue createNewData() {
		ProjectYearPlanInfo info=(ProjectYearPlanInfo)this.getUIContext().get("info");
		if(info==null){
			info= new ProjectYearPlanInfo();
			info.setVersion(1);
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
			for(int i=0;i<info.getTotalEntry().size();i++){
				info.getTotalEntry().get(i).setId(BOSUuid.create(info.getTotalEntry().get(i).getBOSType()));
			}
			for(int i=0;i<info.getTotalBgEntry().size();i++){
				info.getTotalBgEntry().get(i).setId(BOSUuid.create(info.getTotalBgEntry().get(i).getBOSType()));
			}
		}
		info.setState(FDCBillStateEnum.SAVED);
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info.setBizDate(now);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		
		info.setName(null);
		info.setCreator(null);
		info.setCreateTime(null);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setLastUpdateUser(null);
		info.setLastUpdateTime(null);
		return info;
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
		if(this.kdtEntrys.size()>0){
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
        	
        	Object[] key = this.kdtEntrys.keySet().toArray(); 
    		Arrays.sort(key); 
    		
    		for (int k = 0; k < key.length; k++) { 
    			KDTable table = (KDTable) this.kdtEntrys.get(key[k]);
    			table.removeRows();
    		}
    		this.storeFields();
        	
        	EntityViewInfo view=new EntityViewInfo();
        	FilterInfo filter=new FilterInfo();
        	
        	filter.getFilterItems().add(new FilterItemInfo("head.contractbill.curProject.id",this.editData.getCurProject().getId().toString()));
        	filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
        	filter.getFilterItems().add(new FilterItemInfo("payAmount",null,CompareType.NOTEQUALS));
        	filter.getFilterItems().add(new FilterItemInfo("month",null,CompareType.NOTEQUALS));
        	filter.getFilterItems().add(new FilterItemInfo("year",null,CompareType.NOTEQUALS));
        	
        	view.setFilter(filter);
        	view.getSelector().add("*");
        	view.getSelector().add("head.contractBill.id");
        	view.getSelector().add("head.contractBill.programmingContract.id");
        	view.getSelector().add("head.contractBill.number");
        	view.getSelector().add("head.contractBill.name");
        	view.getSelector().add("head.contractBill.amount");
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
        		BigDecimal actAmount=getMonthActPayAmount(contractId,year,month,false);
        		HashMap yearMap=null;
        		HashMap monthMap=null;
        		ProjectYearPlanDateEntryInfo dateEntry=null;
        		ProjectYearPlanEntryInfo entry=null;
        		
        		if(contract.containsKey(contractId)){
        			yearMap=(HashMap)contract.get(contractId);
        			entry=(ProjectYearPlanEntryInfo) entryMap.get(contractId);
        			
        			if(yearMap.containsKey(year)){
        				monthMap=(HashMap)yearMap.get(year);
        				if(monthMap.containsKey(month)){
        					dateEntry=(ProjectYearPlanDateEntryInfo) monthMap.get(month);
        					dateEntry.setAmount(dateEntry.getAmount().add(amount));
        				}else{
        					dateEntry=new ProjectYearPlanDateEntryInfo();
        					dateEntry.setBgItem(bgItem);
        					dateEntry.setAmount(amount);
        					dateEntry.setYear(year);
        					dateEntry.setMonth(month);
        					dateEntry.setRemark(remark);
        					dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
        					
        					dateEntry.setActAmount(actAmount);
        					entry.getDateEntry().add(dateEntry);
        					monthMap.put(month, dateEntry);
        				}
        			}else{
        				monthMap=new HashMap();
        				
        				dateEntry=new ProjectYearPlanDateEntryInfo();
        				dateEntry.setBgItem(bgItem);
    					dateEntry.setAmount(amount);
    					dateEntry.setYear(year);
    					dateEntry.setMonth(month);
    					dateEntry.setRemark(remark);
    					dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
    					
    					dateEntry.setActAmount(actAmount);
    					entry.getDateEntry().add(dateEntry);
    					monthMap.put(month, dateEntry);
    					
    					yearMap.put(year, monthMap);
        				
        			}
        		}else{
        			yearMap=new HashMap();
        			entry=new ProjectYearPlanEntryInfo();
        			entry.setContractBill(contractInfo);
        			entry.setId(BOSUuid.create(entry.getBOSType()));
        			
        			monthMap=new HashMap();
    				
    				dateEntry=new ProjectYearPlanDateEntryInfo();
    				dateEntry.setBgItem(bgItem);
    				dateEntry.setAmount(amount);
    				dateEntry.setYear(year);
    				dateEntry.setMonth(month);
    				dateEntry.setRemark(remark);
    				dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
    				
    				dateEntry.setActAmount(actAmount);
    				entry.getDateEntry().add(dateEntry);
    				monthMap.put(month, dateEntry);
    				
    				yearMap.put(year, monthMap);
    				
    				contract.put(contractId, yearMap);
    				
    				entryMap.put(contractId, entry);
    				
    				this.editData.getEntry().add(entry);
        		}
        	}
        	view=new EntityViewInfo();
        	filter=new FilterInfo();
        	
        	filter.getFilterItems().add(new FilterItemInfo("head.curProject.id",this.editData.getCurProject().getId().toString()));
        	filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
        	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
        	
        	view.setFilter(filter);
        	view.getSelector().add("dateEntry.*");
        	view.getSelector().add("dateEntry.bgItem.*");
        	view.getSelector().add("programmingContract.id");
        	view.getSelector().add("programmingContract.number");
        	view.getSelector().add("programmingContract.name");
        	view.getSelector().add("programmingContract.amount");
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
        	
        	for(int i=0;i<proCol.size();i++){
    			ProjectMonthPlanProEntryInfo ppEntry=proCol.get(i);
    			String id=null;
    			if(ppEntry.getProgrammingContract()!=null){
    				id=ppEntry.getProgrammingContract().getId().toString();
    			}else{
    				id=ppEntry.getId().toString();
    			}
    			ProjectYearPlanEntryInfo entry=null;
    			if(entryMap.containsKey(id)){
    				entry=(ProjectYearPlanEntryInfo) entryMap.get(id);
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
        					ProjectYearPlanDateEntryInfo gdEntry=new ProjectYearPlanDateEntryInfo();
            				gdEntry.setAmount(dEntry.getAmount());
            				gdEntry.setYear(dEntry.getYear());
            				gdEntry.setMonth(dEntry.getMonth());
            				gdEntry.setRemark(dEntry.getRemark());
            				gdEntry.setBgItem(dEntry.getBgItem());
            				
            				entry.getDateEntry().add(gdEntry);
        				}
        			}
    			}else{
    				entry=new ProjectYearPlanEntryInfo();
        			entry.setProgrammingContract(ppEntry.getProgrammingContract());
        			entry.setId(BOSUuid.create(entry.getBOSType()));
        			for(int j=0;j<ppEntry.getDateEntry().size();j++){
        				ProjectMonthPlanProDateEntryInfo dEntry=ppEntry.getDateEntry().get(j);
        				ProjectYearPlanDateEntryInfo gdEntry=new ProjectYearPlanDateEntryInfo();
        				gdEntry.setAmount(dEntry.getAmount());
        				gdEntry.setYear(dEntry.getYear());
        				gdEntry.setMonth(dEntry.getMonth());
        				gdEntry.setRemark(dEntry.getRemark());
        				gdEntry.setBgItem(dEntry.getBgItem());
        				
        				entry.getDateEntry().add(gdEntry);
        			}
        			this.editData.getEntry().add(entry);
        			
        			entryMap.put(id, entry);
    			}
    		}
        	this.loadEntry();
        	
        	key = this.kdtEntrys.keySet().toArray(); 
    		Arrays.sort(key); 
    		for (int k = 0; k < key.length; k++) { 
    			KDTable table = (KDTable) this.kdtEntrys.get(key[k]);
    			for(int i=0;i<table.getRowCount();i++){
    				IRow row=table.getRow(i);
    				ProjectYearPlanEntryInfo entry=(ProjectYearPlanEntryInfo) row.getUserObject();
    				if(entry==null) continue;
    				for(int month=1;month<13;month++){
    					if(row.getCell(month+"amount").getUserObject()==null&&row.getCell(month+"actAmount").getUserObject()==null
    							&&row.getCell(month+"remark").getUserObject()==null&&row.getCell(month+"bgItem").getUserObject()==null){
    						ProjectYearPlanDateEntryInfo dateEntry=new ProjectYearPlanDateEntryInfo();
    						dateEntry.setYear(Integer.parseInt(key[k].toString()));
    						dateEntry.setMonth(month);
    						dateEntry.setAmount(FDCHelper.ZERO);
    						BigDecimal actAmount=FDCHelper.ZERO;
    						if(entry.getContractBill()!=null){
    							actAmount=getMonthActPayAmount(entry.getContractBill().getId().toString(),Integer.parseInt(key[k].toString()),month,false);
    						}
    						dateEntry.setActAmount(actAmount);
    						
    						row.getCell(month+"amount").setValue(dateEntry.getAmount());
        					row.getCell(month+"amount").setUserObject(dateEntry);
        					row.getCell(month+"actAmount").setValue(dateEntry.getActAmount());
        					row.getCell(month+"actAmount").setUserObject(dateEntry);
        					row.getCell(month+"remark").setUserObject(dateEntry);
        					row.getCell(month+"remark").setValue(dateEntry.getRemark());
        					row.getCell(month+"bgItem").setUserObject(dateEntry);
        					row.getCell(month+"bgItem").setValue(dateEntry.getBgItem());
        					
        					entry.getDateEntry().add(dateEntry);
    					}
    				}
    			}
    		}
    		setTotalTable();
		}
	}
	
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtCurProject);
	}
	protected boolean checkBizDate() throws EASBizException, BOSException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id",this.editData.getCurProject().getId().toString()));
    	if(this.editData.getId()!=null){
    		filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.NOTEQUALS));
    	}
    	SorterItemInfo version=new SorterItemInfo("version");
    	version.setSortType(SortType.DESCEND);
    	view.getSorter().add(version);
    	view.getSelector().add(new SelectorItemInfo("state"));
    	view.getSelector().add(new SelectorItemInfo("version"));
    	view.setFilter(filter);
    	ProjectYearPlanCollection col=ProjectYearPlanFactory.getRemoteInstance().getProjectYearPlanCollection(view);
    	if(col.size()>0&&!col.get(0).getState().equals(FDCBillStateEnum.AUDITTED)){
    		FDCMsgBox.showWarning(this,"最新项目年度付款规划还未审批！");
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
		ProjectYearPlanEntryInfo entry = new ProjectYearPlanEntryInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		row.setUserObject(entry);
		
		for(int i=1;i<13;i++){
			ProjectYearPlanDateEntryInfo dateEntry=new ProjectYearPlanDateEntryInfo();
			dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
			dateEntry.setAmount(FDCHelper.ZERO);
			dateEntry.setYear(Integer.parseInt(table.getName()));
			dateEntry.setMonth(i);
			row.getCell(i+"amount").setValue(FDCHelper.ZERO);
			row.getCell(i+"actAmount").setValue(FDCHelper.ZERO);
			row.getCell(i+"amount").setUserObject(dateEntry);
			row.getCell(i+"actAmount").setUserObject(dateEntry);
			row.getCell(i+"remark").setUserObject(dateEntry);
			row.getCell(i+"bgItem").setUserObject(dateEntry);
			
			row.getCell(i+"amount").getStyleAttributes().setLocked(false);
			row.getCell(i+"actAmount").getStyleAttributes().setLocked(false);
			row.getCell(i+"remark").getStyleAttributes().setLocked(false);
			row.getCell(i+"bgItem").getStyleAttributes().setLocked(false);
			
			entry.getDateEntry().add(dateEntry);
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
				ProjectYearPlanEntryInfo topValue=(ProjectYearPlanEntryInfo) table.getRow(top).getUserObject();
				if(topValue==null||(topValue!=null&&(topValue.getProgrammingContract()!=null||topValue.getContractBill()!=null))){
					FDCMsgBox.showWarning(this,"此行禁止插入行！");
					return;
				}
				row = table.addRow(top);
			}
				
		} else {
			row = table.addRow();
		}
		ProjectYearPlanEntryInfo entry = new ProjectYearPlanEntryInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		row.setUserObject(entry);
		
		for(int i=1;i<13;i++){
			ProjectYearPlanDateEntryInfo dateEntry=new ProjectYearPlanDateEntryInfo();
			dateEntry.setId(BOSUuid.create(dateEntry.getBOSType()));
			dateEntry.setAmount(FDCHelper.ZERO);
			dateEntry.setYear(Integer.parseInt(table.getName()));
			dateEntry.setMonth(i);
			row.getCell(i+"amount").setValue(FDCHelper.ZERO);
			row.getCell(i+"actAmount").setValue(FDCHelper.ZERO);
			row.getCell(i+"amount").setUserObject(dateEntry);
			row.getCell(i+"actAmount").setUserObject(dateEntry);
			row.getCell(i+"remark").setUserObject(dateEntry);
			row.getCell(i+"bgItem").setUserObject(dateEntry);
			
			row.getCell(i+"amount").getStyleAttributes().setLocked(false);
			row.getCell(i+"actAmount").getStyleAttributes().setLocked(false);
			row.getCell(i+"remark").getStyleAttributes().setLocked(false);
			row.getCell(i+"bgItem").getStyleAttributes().setLocked(false);
			
			entry.getDateEntry().add(dateEntry);
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
				ProjectYearPlanEntryInfo topValue=(ProjectYearPlanEntryInfo) table.getRow(top).getUserObject();
				if(topValue==null||(topValue!=null&&(topValue.getProgrammingContract()!=null||topValue.getContractBill()!=null))){
					FDCMsgBox.showWarning(this,"此行禁止删除行！");
					return;
				}
				table.removeRow(top);
				
				this.editData.getEntry().removeObject(topValue);
				
				setTotalTable();
				String amountColoun[]=new String[13];
				for(int mi=0;mi<13;mi++){
					if(mi==0){
						amountColoun[0]="amount";
					}else{
						amountColoun[mi]=mi+"amount";
					}
				}
				String actAmountColoun[]=new String[12];
				for(int mi=1;mi<13;mi++){
					actAmountColoun[mi-1]=mi+"actAmount";
				}
				CRMClientHelper.getFootRow(table, actAmountColoun);
				CRMClientHelper.getFootRow(table, amountColoun);
			}
		}
	}
	protected void spMonth_stateChanged(ChangeEvent e) throws Exception {
		reGetValue();
	}
	protected void spYear_stateChanged(ChangeEvent e) throws Exception {
		reGetValue();
	}
	protected void reGetValue() throws BOSException, SQLException{
		if (isLoad) {
			return;
		}
		int result = MsgBox.OK;
		if (this.kdtAmount.getRowCount() > 0||this.kdtDate.getRowCount()>0) {
			result = FDCMsgBox.showConfirm2(this,"改变编制月份将重新获取汇总分录数据！");
		}
		if (result == MsgBox.OK) {
			year_old = this.spYear.getIntegerVlaue().intValue();
			month_old=this.spMonth.getIntegerVlaue().intValue();
		} else {
			this.spYear.setValue(year_old, false);
			this.spMonth.setValue(month_old,false);
			return;
		}
		setTotalTable();
	}
}