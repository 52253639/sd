/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.fdc.aimcost.client.AimCostClientHelper;
import com.kingdee.eas.fdc.basedata.CellBinder;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctDataInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCYearBudgetAcctEntryCollection;
import com.kingdee.eas.fdc.finance.FDCYearBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCYearBudgetAcctFactory;
import com.kingdee.eas.fdc.finance.FDCYearBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCYearBudgetAcctItemInfo;
import com.kingdee.eas.fdc.finance.IFDCBudgetAcct;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class FDCYearBudgetAcctEditUI extends AbstractFDCYearBudgetAcctEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCYearBudgetAcctEditUI.class);
    
    /**
     * output class constructor
     */
    public FDCYearBudgetAcctEditUI() throws Exception
    {
        super();
    }

    public void storeFields(){
    }

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);
	}

	FDCBudgetAcctInfo createNewFDCBudgetAcct() {
		FDCBudgetAcctInfo info=new FDCYearBudgetAcctInfo();
		return info;
	}

	IFDCBudgetAcct getRemoteInterface() throws BOSException {
		return FDCYearBudgetAcctFactory.getRemoteInstance();
	}

	protected void resetTableHead() {
		super.resetTableHead();
		int startIndex=getDetailTable().getColumnIndex("yearPay")+1;
		IRow headRow1=getDetailTable().getHeadRow(0);
		IRow headRow2=getDetailTable().getHeadRow(1);
		for(int i=1;i<=12;i++){
			IColumn column = getDetailTable().addColumn(startIndex);
			column.setKey(getMonthCostKey(i));
			column.setEditor(CellBinder.getCellNumberEdit());
			column=getDetailTable().addColumn(startIndex+1);
			column.setKey(getMonthPayKey(i));
			column.setEditor(CellBinder.getCellNumberEdit());
			
			String title=i+"月";
			headRow1.getCell(startIndex).setValue(title);
			headRow1.getCell(startIndex+1).setValue(title);
			headRow2.getCell(startIndex).setValue("成本");
			headRow2.getCell(startIndex+1).setValue("付款");
			startIndex+=2;
		}
		getDetailTable().getHeadMergeManager().setMergeMode(KDTMergeManager.FREE_MERGE);
		FDCHelper.formatTableNumber(getDetailTable(), "aimCost");
		FDCHelper.formatTableNumber(getDetailTable(), "dynCost");
		FDCHelper.formatTableNumber(getDetailTable(), getDetailTable().getColumnIndex("conAmt"), getDetailTable().getColumnIndex("waitPay"));
	}

	FDCBudgetAcctEntryInfo createNewFDCBudgetAcctEntry() {
		return new FDCYearBudgetAcctEntryInfo();
	}

    protected void storeRows(){
    	FDCYearBudgetAcctInfo yearBudgetAcctInfo = getFDCYearBudgetAcctInfo();
    	if(yearBudgetAcctInfo.getEntrys()==null){
    		yearBudgetAcctInfo.put("entrys", new FDCYearBudgetAcctEntryCollection());
    	}
    	yearBudgetAcctInfo.getEntrys().clear();
    	for(int i=0;i<getDetailTable().getRowCount();i++){;
    		IRow row=getDetailTable().getRow(i);
    		FDCYearBudgetAcctEntryInfo entry = (FDCYearBudgetAcctEntryInfo)storeRow(row);
    		if(entry!=null){
    			yearBudgetAcctInfo.getEntrys().add(entry);
    		}
    	}
    }
    protected FDCBudgetAcctEntryInfo storeRow(IRow row){
    	if(!isEmptyRow(row)&&row.getUserObject() instanceof FDCYearBudgetAcctEntryInfo){
    		FDCYearBudgetAcctEntryInfo entry=(FDCYearBudgetAcctEntryInfo)row.getUserObject();
    		if(isRecension()){
    			entry.setId(null);
    		}
			entry.setCreator((UserInfo)row.getCell("creator").getValue());
			entry.setDesc((String)row.getCell("desc").getValue());
			entry.setDeptment((AdminOrgUnitInfo)row.getCell("deptment").getValue());
			entry.setCost((BigDecimal)row.getCell("yearCost").getValue());
			entry.setAmount((BigDecimal)row.getCell("yearPay").getValue());
			entry.setName((String)row.getCell("conName").getValue());
			entry.setNumber((String)row.getCell("conNumber").getValue());
			entry.setSplitAmt((BigDecimal)row.getCell("splitAmt").getValue());
			entry.setConAmt((BigDecimal)row.getCell("conAmt").getValue());
			entry.getItems().clear();
			entry.setParent(getFDCYearBudgetAcctInfo());
			for(int i=1;i<=12;i++){
				FDCYearBudgetAcctItemInfo item=new FDCYearBudgetAcctItemInfo();
				item.setEntry(entry);
				item.setAmount((BigDecimal)row.getCell(getMonthPayKey(i)).getValue());
				item.setCost((BigDecimal)row.getCell(getMonthCostKey(i)).getValue());
				item.setMonth(i);
				if(item.getAmount()==null&&item.getCost()==null){
					continue;
				}
				entry.getItems().add(item);
			}
			
			return entry;
		}
    	return null;
    }
    
    private FDCYearBudgetAcctInfo getFDCYearBudgetAcctInfo(){
    	return (FDCYearBudgetAcctInfo)this.editData;
    }
    
    
    protected ICoreBase getBizInterface() throws Exception {
    	return FDCYearBudgetAcctFactory.getRemoteInstance();
    }
    
    public void onLoad() throws Exception {
    	this.spMonth.setVisible(false);
    	this.lbMonth.setVisible(false);
    	super.onLoad();
    }

    protected void tblMain_editStopped(KDTEditEvent e){
    	Object newValue=e.getValue();
    	Object oldValue=e.getOldValue();
    	if(newValue==null&&oldValue==null){
    		return;
    	}
    	if(newValue!=null&&oldValue!=null&&newValue.equals(oldValue)){
    		return;
    	}
    	if(isMonthColumn(e.getColIndex())){
    		IRow row=getDetailTable().getRow(e.getRowIndex());
    		BigDecimal tmpAmt=FDCHelper.ZERO;
    		BigDecimal tmpCost=FDCHelper.ZERO;
    		for(int i=1;i<=12;i++){
    			tmpAmt=FDCNumberHelper.add(tmpAmt, row.getCell(getMonthPayKey(i)).getValue());
    			tmpCost=FDCNumberHelper.add(tmpCost, row.getCell(getMonthCostKey(i)).getValue());
    		}
    		row.getCell("yearCost").setValue(tmpCost);
    		row.getCell("yearPay").setValue(tmpAmt);
    		
    	}
    	super.tblMain_editStopped(e);

    }
    
    protected void setEmptRow(KDTEditEvent e){    
    	int index=e.getRowIndex();
    	IRow row=getDetailTable().getRow(index);
    	if(e.getValue()!=null){
    		setEmptyRow(row, false);
    	}else{
    		//判断行是不是空
    		boolean isEmpty=true;
    		for(int i=1;i<=12;i++){
    			if(i==index){
    				continue;
    			}
    			if(row.getCell(getMonthCostKey(i)).getValue()!=null
    					||row.getCell(getMonthPayKey(i)).getValue()!=null){
    				isEmpty=false;
    				break;
    			}
    		}
    		isEmpty=isEmpty?FDCHelper.isEmpty(row.getCell("desc").getValue()):isEmpty;
    		//判断合同是不是可以录入的行
    		if(isEmpty&&!row.getCell("conName").getStyleAttributes().isLocked()){
    			isEmpty=FDCHelper.isEmpty(row.getCell("conName").getValue());
    		}
    		setEmptyRow(row, isEmpty);
    	}
    }
    protected void afterFillTable() {
    	super.afterFillTable();
    }
    
	protected FDCBudgetAcctInfo getFDCBudgetAcctInfo(){
		return this.editData;
	}
	protected void initTable() {
		super.initTable();
		getDetailTable().getColumn("lstAllPaidRate").getStyleAttributes().setNumberFormat("0.00%");
	}
	
    protected void fillCostAccountRow(IRow row) {
    	if(row.getUserObject() instanceof CostAccountInfo){
    		CostAccountInfo info=(CostAccountInfo)row.getUserObject();
    		String key=info.getId().toString();
    		Map map=(Map)getDataMap().get("costMap");
    		if(map!=null&&map.size()>0){
    			FDCBudgetAcctDataInfo dataInfo=(FDCBudgetAcctDataInfo)map.get(key);
    			if(dataInfo==null){
    				return;
    			}
    			BigDecimal aimCost = dataInfo.getAimCost();
    			BigDecimal dynCost = dataInfo.getDynCost();
    			BigDecimal lstCost = dataInfo.getLstCost();
//    			BigDecimal lstPay = dataInfo.getLstPay();
    			row.getCell("aimCost").setValue(aimCost);
				row.getCell("dynCost").setValue(dynCost);
				row.getCell("lstAllCost").setValue(lstCost);
//				row.getCell("lstAllPaid").setValue(FDCNumberHelper.ONE_HUNDRED_MILLION);
//    			row.getCell("lstAllNoPaid").setValue(FDCHelper.subtract(lstCost, lstPay));
//    			row.getCell("lstAllPaidRate").setValue(FDCHelper.divide(lstPay,lstCost,4,BigDecimal.ROUND_HALF_UP));
    			
    		}
    	}
	}
    
    protected void setUnionAll(){
//    	if(true){
//    		return;
//    	}
    	super.setUnionAll();
    	for(int i=0;i<getDetailTable().getRowCount();i++){
    		IRow row=getDetailTable().getRow(i);
    		BigDecimal tmpAmt=FDCHelper.ZERO;
    		BigDecimal tmpCost=FDCHelper.ZERO;
    		for(int j=1;j<=12;j++){
    			tmpAmt=FDCNumberHelper.add(tmpAmt, row.getCell(getMonthPayKey(j)).getValue());
    			tmpCost=FDCNumberHelper.add(tmpCost, row.getCell(getMonthCostKey(j)).getValue());
    		}
    		if(tmpCost.compareTo(FDCHelper.ZERO) > 0){
    			row.getCell("yearCost").setValue(tmpCost);
    		}else{
    			row.getCell("yearCost").setValue(null);
    		}
    		if(tmpAmt.compareTo(FDCHelper.ZERO) > 0){
    			row.getCell("yearPay").setValue(tmpAmt);
    		}else{
    			row.getCell("yearPay").setValue(null);
    		}
    	}
    	for(int i=0;i<getDetailTable().getRowCount();i++){
    		IRow row=getDetailTable().getRow(i);
    		Object obj=row.getUserObject();
			if(obj==null||obj instanceof CostAccountInfo){
				BigDecimal dynCost = (BigDecimal)row.getCell("dynCost").getValue();
				BigDecimal lstCost = (BigDecimal)row.getCell("lstAllCost").getValue();
				BigDecimal lstPay = (BigDecimal)row.getCell("lstAllPaid").getValue();
				BigDecimal yearCost = (BigDecimal)row.getCell("yearCost").getValue();
				BigDecimal yearPay = (BigDecimal)row.getCell("yearPay").getValue();
				
				row.getCell("lstAllNoPaid").setValue(FDCHelper.subtract(lstCost, lstPay));
				row.getCell("lstAllPaidRate").setValue(FDCHelper.divide(lstPay,lstCost,4,BigDecimal.ROUND_HALF_UP));
				row.getCell("waitCost").setValue(FDCHelper.subtract(FDCHelper.subtract(dynCost, lstCost), yearCost));
				row.getCell("waitPay").setValue(FDCHelper.subtract(FDCHelper.subtract(dynCost, lstPay), yearPay));
			}
			if(isSettlecContractRow(row)){
				
			}
			
			//付款大于成本以警告色显示
			BigDecimal yearCost = (BigDecimal)row.getCell("yearCost").getValue();
			BigDecimal yearPay = (BigDecimal)row.getCell("yearPay").getValue();
			Color oldColor=Color.WHITE;
			String costKey="yearCost";
			if(row.getCell(costKey).getUserObject()==null){
				oldColor=row.getCell(costKey).getStyleAttributes().getBackground();
				row.getCell(costKey).setUserObject(oldColor);
			}else{
				oldColor=(Color)row.getCell(costKey).getUserObject();
			}
			if(FDCNumberHelper.subtract(FDCHelper.toBigDecimal(yearCost), FDCHelper.toBigDecimal(yearPay)).signum()<0){
				row.getCell("yearCost").getStyleAttributes().setBackground(FDCTableHelper.warnColor);
				row.getCell("yearPay").getStyleAttributes().setBackground(FDCTableHelper.warnColor);
			}else{
				row.getCell("yearCost").getStyleAttributes().setBackground(oldColor);
				row.getCell("yearPay").getStyleAttributes().setBackground(oldColor);
			}
			for(int j=1;j<=12;j++){
				costKey=getMonthCostKey(j);
				if(row.getCell(costKey).getUserObject()==null){
					oldColor=row.getCell(costKey).getStyleAttributes().getBackground();
					row.getCell(costKey).setUserObject(oldColor);
				}else{
					oldColor=(Color)row.getCell(costKey).getUserObject();
				}
				BigDecimal cost = (BigDecimal)row.getCell(getMonthCostKey(j)).getValue();
				BigDecimal pay = (BigDecimal)row.getCell(getMonthPayKey(j)).getValue();
				if(FDCNumberHelper.subtract(FDCHelper.toBigDecimal(cost), FDCHelper.toBigDecimal(pay)).signum()<0){
					row.getCell(getMonthCostKey(j)).getStyleAttributes().setBackground(FDCTableHelper.warnColor);
					row.getCell(getMonthPayKey(j)).getStyleAttributes().setBackground(FDCTableHelper.warnColor);
				}else{
					row.getCell(getMonthCostKey(j)).getStyleAttributes().setBackground(oldColor);
					row.getCell(getMonthPayKey(j)).getStyleAttributes().setBackground(oldColor);
				}
			}

    	}
    	
    	
    	afterSetUnionAll();

    }
    
    protected List getColumnSumList(){
    	List list=getColumnSumList();
    	for(int i=1;i<=12;i++){
    		list.add(getMonthCostKey(i));
    		list.add(getMonthPayKey(i));
    	}
    	list.add("yearCost");
    	list.add("yearPay");
    	list.add("lstAllCost");
    	list.add("lstAllPaid");
    	return list;
    }
    
    public void loadRow(IRow row) {
    	super.loadRow(row);
		FDCBudgetAcctEntryInfo entry=(FDCBudgetAcctEntryInfo)row.getUserObject();
		row.getCell("lstAllPaid").setValue(entry.getBigDecimal("allPay"));
    }
    
	/**
	 * 科目汇总列
	 * @return
	 */
	protected List getSumColumns(){
		List list=super.getSumColumns();
    	list.add("yearCost");
    	list.add("lstAllCost");
		return list;
	}
	/**
	 * 待签订合同汇总列
	 * @return
	 */
	protected List getUnSettledSumColumns(){
		List list=super.getUnSettledSumColumns();
		return list;
	}
	/**
	 * 已签订合同汇总列
	 * @return
	 */
	protected List getSettledSumColumns(){
		List list=super.getUnSettledSumColumns();
    	list.add("yearPay");
    	list.add("lstAllPaid");
    	list.add("lstAllNoPaid");
		return list;
	}
	
    protected List getColumnTreeCols(){
    	List list=super.getColumnTreeCols();
    	list.add(new String[]{"lstAllPaid","lstAllCost","lstAllNoPaid","lstAllPaidRate"});
    	return list;
    }
    
	public void onShow() throws Exception {
		super.onShow();
		//控制界面状态
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
        Object isFromWorkflow = getUIContext().get("isFromWorkflow");
    	if(isFromWorkflow!=null && isFromWorkflow.toString().equals("true")){
    		actionAddLine.setEnabled(false);
    		actionRemoveLine.setEnabled(false);
    		actionInsertLine.setEnabled(false);
    		if(STATUS_VIEW.equals(getOprtState())){
    			txtNumber.setEnabled(false);
    		}
    	}
	}
	protected void initDataStatus() {
//		super.initDataStatus();
	}
	
	public boolean isModify() {
		return super.isModify();
	}
	
	protected int getStartMonth() {
		return 1;
	}
	
	protected int getEndMonth() {
		return 12;
	}
	
	protected Set getCostColumns(){
		Set list=super.getCostColumns();
		list.add("yearCost");
		list.add("lstAllCost");
		list.add("waitCost");
		return list;
	}
	
}