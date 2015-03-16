/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.PreChangeEvent;
import com.kingdee.bos.ctrl.swing.event.PreChangeListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCRenderHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryCollection;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctFacadeFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryCollection;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class ContractAssociateAcctPlanUI extends AbstractContractAssociateAcctPlanUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractAssociateAcctPlanUI.class);
    
    /**
     * output class constructor
     */
    public ContractAssociateAcctPlanUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output prmtPeriod_dataChanged method
     */
    protected void prmtPeriod_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	FDCBudgetPeriodInfo period = (FDCBudgetPeriodInfo)prmtPeriod.getValue();
    	if(period!=null){
    		getUIContext().put("period", period);
    		fillTable();
    	}
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	initTable();
    	fillTable();
    	EntityViewInfo view=prmtPeriod.getEntityViewInfo();
    	if(view==null){
    		view=new EntityViewInfo();
    	}
    	if(view.getFilter()==null){
    		view.setFilter(new FilterInfo());
    	}
    	FilterInfo filter=new FilterInfo();
    	filter.appendFilterItem("isYear", Boolean.FALSE);
    	view.getSorter().add(new SorterItemInfo("year"));
    	view.getSorter().add(new SorterItemInfo("month"));
    	view.getFilter().mergeFilter(filter, "and");
    	prmtPeriod.setEntityViewInfo(view);
    	prmtPeriod.addPreChangeListener(new PreChangeListener(){
    		public void preChange(PreChangeEvent e) {
//    			FDCBudgetPeriodInfo newPeriod=(FDCBudgetPeriodInfo)e.getData();
//    			FDCBudgetPeriodInfo basePeriod=(FDCBudgetPeriodInfo)getUIContext().get("basePeriod");
//    			if(newPeriod.getYear()<basePeriod.getYear()||(newPeriod.getYear()==basePeriod.getYear()&&newPeriod.getMonth()<basePeriod.getMonth())){
//    				FDCMsgBox.showWarning(table, "月份不能早于付款日期");
//    				e.setResult(PreChangeEvent.S_FALSE);
//    			}
    		}
    	});
    }
    private Set associateAcctSet=null;
    private Set hasAssociateAcctSet=null;
    private void fillTable() throws EASBizException, BOSException{
    	table.removeRows();
    	table.getStyleAttributes().setLocked(true);
    	table.getColumn("isSelect").getStyleAttributes().setLocked(false);
    	String prjId=(String)getUIContext().get("prjId");
    	String contractId=(String)getUIContext().get("contractId");
    	FDCBudgetPeriodInfo period=(FDCBudgetPeriodInfo)getUIContext().get("period");
    	Map dataMap=FDCBudgetAcctFacadeFactory.getRemoteInstance().getAssociateAcctPlan(prjId, contractId, period);
    	String budgetId=(String)dataMap.get("budgetId");
    	boolean isNoData=false;
    	if(budgetId==null){
    		FDCMsgBox.showWarning(this, "该月不存在合同拆分科目下的待签订合同月度付款计划");
//    		SysUtil.abort();
    		isNoData=true;
    	}
    	associateAcctSet=(Set)dataMap.get("associateAcctSet");
    	hasAssociateAcctSet=new HashSet();
    	table.setUserObject(budgetId);
    	prmtProject.setValue(dataMap.get("curProject"));
    	prmtContract.setValue(dataMap.get("contractBill"));
    	EventListener[] listeners = prmtPeriod.getListeners(DataChangeListener.class);
    	for(int i=0;i<listeners.length;i++){
    		prmtPeriod.removeDataChangeListener((DataChangeListener)listeners[i]);
    	}
    	try{
    		prmtPeriod.setValue(dataMap.get("period"));
    	}finally{
    		for(int i=0;i<listeners.length;i++){
    			prmtPeriod.addDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}
    	if(isNoData){
    		return;
    	}
    	Map budgetEntryMap=(Map)dataMap.get("budgetEntryMap"); 
    	if(budgetEntryMap==null){
    		budgetEntryMap=new  HashMap();
    	}

    	ContractCostSplitEntryCollection costSplitEntrys=(ContractCostSplitEntryCollection)dataMap.get("costSplitEntrys");
    	for(Iterator iter=costSplitEntrys.iterator();iter.hasNext();){
    		ContractCostSplitEntryInfo entry=(ContractCostSplitEntryInfo)iter.next();
    		IRow row=table.addRow();
    		row.setUserObject(entry);
    		loadRow(row);
    		String key=entry.getCostAccount().getId().toString();
    		FDCMonthBudgetAcctEntryCollection monthBudgetAcctEntrys = getFDCMonthBudgetAcctEntrys(budgetEntryMap, key);
    		if(monthBudgetAcctEntrys==null||monthBudgetAcctEntrys.size()==0){
    			continue;
    		}
    		for(Iterator iter2=monthBudgetAcctEntrys.iterator();iter2.hasNext();){
    			row=table.addRow();
        		row.setUserObject(iter2.next());
        		loadRow(row);
    		}
    	}
    }
    
    private void loadRow(IRow row){
    	
    	if(row.getUserObject() instanceof ContractCostSplitEntryInfo){
    		ContractCostSplitEntryInfo entry=(ContractCostSplitEntryInfo)row.getUserObject();
    		row.getCell("id").setValue(entry.getId().toString());
    		row.getCell("acctNumber").setValue(entry.getCostAccount().getLongNumber());
    		row.getCell("acctName").setValue(entry.getCostAccount().getName());
    		row.getCell("splitAmt").setValue(entry.getAmount());
    		row.setUserObject(entry);
    		row.getCell("isSelect").setValue(Boolean.FALSE);
    		row.getCell("isSelect").getStyleAttributes().setLocked(true);
//    		row.getCell("isSelect").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    		if(entry.getCostAccount()!=null){
    			row.getCell("project").setValue(entry.getCostAccount().getCurProject());
    		}
    	}else{
    		FDCMonthBudgetAcctEntryInfo entry=(FDCMonthBudgetAcctEntryInfo)row.getUserObject();
    		row.getCell("id").setValue(entry.getId().toString());
    		row.getCell("name").setValue(entry.getName());
    		row.getCell("cost").setValue(entry.getCost());
    		row.getCell("pay").setValue(entry.getAmount());
    		Boolean isSelect = Boolean.valueOf(entry.getContractBill()!=null);
			row.getCell("isSelect").setValue(isSelect);
    		if(isSelect.booleanValue() && associateAcctSet.contains(entry.getCostAccount().getId().toString())){
    			row.getCell("isSelect").getStyleAttributes().setLocked(true);
//    			row.getCell("isSelect").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    			
    		}
    		if(entry.getContractBill()!=null){
    			hasAssociateAcctSet.add(entry.getId().toString());
    		}
    		if(entry.getCostAccount()!=null){
    			row.getCell("project").setValue(entry.getCostAccount().getCurProject());
    		}
    		row.setUserObject(entry);
    	}
    	row.getCell("exception").setValue(Boolean.FALSE);
    }
    private void initTable() {
    	table.checkParsed();
    	table.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	table.getColumn("acctNumber").setRenderer(FDCRenderHelper.getLongNumberRender());
    	FDCHelper.formatTableNumber(table, "cost");
    	FDCHelper.formatTableNumber(table, "pay");
    	FDCHelper.formatTableNumber(table, "splitAmt");
    	table.getColumn("exception").getStyleAttributes().setHided(true);
//    	table.getColumn("isSelect").getStyleAttributes().setLocked(true);

	}
    private FDCMonthBudgetAcctEntryCollection getFDCMonthBudgetAcctEntrys(Map budgetEntryMap,String key){
    	return (FDCMonthBudgetAcctEntryCollection)budgetEntryMap.get(key);
    }
    public static void showContractAssociateAcctPlanUI(CoreUI ui,String prjId,String contractId,FDCBudgetPeriodInfo period,String optState) throws UIException{
		// 创建UI对象并显示
    	UIContext context=new UIContext(ui);
    	context.put("prjId", prjId);
    	context.put("contractId", contractId);
    	context.put("period", period);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(ContractAssociateAcctPlanUI.class.getName(), context, null,
						optState);
		uiWindow.show();
    }
    
    private void storeRows(){
    	
    }
    private void verify(){
    	boolean isSelect=false;
    	for(int i=0;i<table.getRowCount();i++){
    		IRow row=table.getRow(i);
    		isSelect=row.getCell("isSelect").getValue()==Boolean.TRUE;
//    		boolean isException=row.getCell("exception").getValue()==Boolean.TRUE;
    		if(isSelect){
    			break;
    		}
    	}
    	if(!isSelect){
    		FDCMsgBox.showWarning(this, "没有选择行,不允许保存");
    		SysUtil.abort();
    	}
    }
    protected void btnOk_actionPerformed(ActionEvent e) throws Exception {
    	verify();
    	storeRows();
    	String contractId=FDCHelper.getF7Id(prmtContract);
    	FDCBudgetAcctEntryCollection budgetAcctEntrys=new FDCBudgetAcctEntryCollection();
    	for(int i=0;i<table.getRowCount();i++){
    		IRow row=table.getRow(i);
    		boolean isSelect=row.getCell("isSelect").getValue()==Boolean.TRUE;
    		boolean isException=row.getCell("exception").getValue()==Boolean.TRUE;
    		if(row.getUserObject() instanceof FDCBudgetAcctEntryInfo&&isSelect){
    			budgetAcctEntrys.add((FDCBudgetAcctEntryInfo)row.getUserObject());
    		}
//    		if(row.getUserObject() instanceof ContractCostSplitEntryInfo&&isException){
//    			FDCBudgetAcctEntryInfo entry=new FDCBudgetAcctEntryInfo();
//    			ContractCostSplitEntryInfo splitEntry=(ContractCostSplitEntryInfo)row.getUserObject();
//    			entry.setCostAccount(splitEntry.getCostAccount());
//    			budgetAcctEntrys.add((FDCBudgetAcctEntryInfo)row.getUserObject());
//    		}
    	}
    	if(budgetAcctEntrys.size()>0){
    		FDCBudgetAcctFacadeFactory.getRemoteInstance().bindToContract(contractId, budgetAcctEntrys);
    		FDCMsgBox.showInfo(this, "保存成功");
    		this.destroyWindow();
    	}else{
    		FDCMsgBox.showWarning(this, "不存在符合保存条件的行");
    		SysUtil.abort();
    	}
    }
    protected void btnNo_actionPerformed(ActionEvent e) throws Exception {
    	super.btnNo_actionPerformed(e);
    	this.destroyWindow();
    }
    
    protected void table_tableClicked(KDTMouseEvent e) throws Exception {
    	super.table_tableClicked(e);
    	if(e.getType()==1&&e.getRowIndex()>-1&&e.getColIndex()>-1){
    		IRow row=table.getRow(e.getRowIndex());
    		if(e.getColIndex()==table.getColumnIndex("isSelect")){
    			if(row.getUserObject() instanceof ContractCostSplitEntryInfo){
    				Boolean isSelect=(Boolean)row.getCell(e.getColIndex()).getValue();
    				ContractCostSplitEntryInfo entry=(ContractCostSplitEntryInfo)row.getUserObject();
    				if(associateAcctSet!=null&&associateAcctSet.contains(entry.getCostAccount().getId().toString())){
    					return;
    				}
    				for(int i=e.getRowIndex()+1;i<table.getRowCount();i++){
    					IRow tmpRow=table.getRow(i);
    					if(tmpRow.getUserObject() instanceof ContractCostSplitEntryInfo){
    						break;
    					}
        				FDCMonthBudgetAcctEntryInfo entry2=(FDCMonthBudgetAcctEntryInfo)tmpRow.getUserObject();
        				if(hasAssociateAcctSet.contains(entry2.getId().toString())){
        					continue;
        				}
    					tmpRow.getCell(e.getColIndex()).setValue(Boolean.valueOf(isSelect==Boolean.FALSE));
    				}
    				row.getCell(e.getColIndex()).setValue(Boolean.valueOf(isSelect==Boolean.FALSE));
    			}
    			if(row.getUserObject() instanceof FDCMonthBudgetAcctEntryInfo){
    				Boolean isSelect=(Boolean)row.getCell(e.getColIndex()).getValue();
    				FDCMonthBudgetAcctEntryInfo entry=(FDCMonthBudgetAcctEntryInfo)row.getUserObject();
    				if(hasAssociateAcctSet.contains(entry.getId().toString())){
    					return;
    				}
//    				if(isSelect.booleanValue()&&associateAcctSet!=null&&associateAcctSet.contains(entry.getCostAccount().getId().toString())){
//    					return;
//    				}
    				row.getCell(e.getColIndex()).setValue(Boolean.valueOf(isSelect==Boolean.FALSE));
    			}
    		}
    		
    		if(e.getColIndex()==table.getColumnIndex("exception")){
    			if(row.getUserObject() instanceof ContractCostSplitEntryInfo){
    				Boolean exception=(Boolean)row.getCell(e.getColIndex()).getValue();
    				row.getCell(e.getColIndex()).setValue(Boolean.valueOf(exception==Boolean.FALSE));
    			}
    			if(row.getUserObject() instanceof FDCMonthBudgetAcctEntryInfo){
    			}
    		}
    	}
    }

}