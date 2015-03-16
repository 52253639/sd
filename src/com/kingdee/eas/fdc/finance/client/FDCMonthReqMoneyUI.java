/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
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
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCRenderHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntry;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryCollection;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctFacadeFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryCollection;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.PayRequestAcctPayFactory;
import com.kingdee.eas.fdc.finance.PayRequestAcctPayInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;

/**
 * 描述：重构关联已签定合同及待签定合同付款计划在一个界面操作<p>
 *       维护请参考ContractAssociateAcctPlanUI,PayReqAcctPayUI
 */
public class FDCMonthReqMoneyUI extends AbstractFDCMonthReqMoneyUI
{
	private static final long serialVersionUID = -2196845096299185619L;
	private static final Logger logger = CoreUIObject.getLogger(FDCMonthReqMoneyUI.class);
    
    /**
     * output class constructor
     */
    public FDCMonthReqMoneyUI() throws Exception
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
        super.prmtPeriod_dataChanged(e);
        if(e.getNewValue()!=null){
        	getUIContext().put("period", e.getNewValue());
        	EventListener[] listeners = prmtPeriod.getListeners(DataChangeListener.class);
        	for(int i=0;i<listeners.length;i++){
        		prmtPeriod.removeDataChangeListener((DataChangeListener)listeners[i]);
        	}
        	try{
        		fillTable();
        	}finally{
        		//当付款申请单已在其它月度上存在科目付款申请时fillTable中没有执行合计行计算方法，在此调用
        		setUnionAll();
        		for(int i=0;i<listeners.length;i++){
        			prmtPeriod.addDataChangeListener((DataChangeListener)listeners[i]);
        		}
        	}
        }
    }
    private FDCMonthBudgetAcctEntryCollection getFDCMonthBudgetAcctEntrys(Map budgetEntryMap,String key){
    	return (FDCMonthBudgetAcctEntryCollection)budgetEntryMap.get(key);
    }
    //科目待签定金额之和
    private Object[] getUnSettledContractAcctAmt(IRow row){
    	Object[] unSettledAcctAmt = new Object[2];//0：成本,1:付款
    	for(int i=row.getRowIndex()+1;i<table.getRowCount();i++){
    		IRow tmpRow = table.getRow(i);
    		Object object = tmpRow.getUserObject();
    		if(object instanceof ContractCostSplitEntryInfo){
    			return unSettledAcctAmt;
    		}
    		FDCMonthBudgetAcctEntryInfo entry = (FDCMonthBudgetAcctEntryInfo)object;
    		boolean isSelect=((Boolean)tmpRow.getCell("isSelect").getValue()).booleanValue();
    		if(isSelect){
    			unSettledAcctAmt[0] = FDCHelper.add(unSettledAcctAmt[0], entry.getCost());
    			unSettledAcctAmt[1] = FDCHelper.add(unSettledAcctAmt[1], entry.getAmount());
    		}
    	}
    	return unSettledAcctAmt;
    }
    public static void showFDCMonthReqMoneyUI(CoreUI ui,PayRequestBillInfo editData,FDCBudgetPeriodInfo period,String optState) throws UIException{
		// 创建UI对象并显示
    	UIContext context=new UIContext(ui);
    	
    	context.put("payReqId", editData.getId().toString());
    	context.put("period", period);
    	BigDecimal requestAmount = FDCHelper.toBigDecimal(editData.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP);
    	context.put("requestAmount", requestAmount);
    	context.put("basePeriod", FDCBudgetPeriodInfo.getPeriod(editData.getPayDate(), false));
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(com.kingdee.eas.fdc.finance.client.FDCMonthReqMoneyUI.class.getName(), context, null,
						optState);
		uiWindow.show();
    }

    private void verify(){
    	//TODO
    	boolean select=false;
    	for(int i=0;i<table.getRowCount();i++){
    		IRow row=table.getRow(i);
    		boolean isSelect=row.getCell("isSelect").getValue()==Boolean.TRUE;
    		if(isSelect){
    			select=true;
    			if(row.getUserObject() instanceof ContractCostSplitEntryInfo){
	    			if(row.getCell("thisTimeReq").getValue()==null){
	    				table.getSelectManager().select(i, 0,KDTSelectManager.ROW_SELECT);
	    				FDCMsgBox.showWarning(this, "当前行选择了但没有录入请款金额");
	    	    		SysUtil.abort();
	    			}
    			}
    		}
    	}
    	if(!select){
    		FDCMsgBox.showWarning(this, "没有选择行");
    		SysUtil.abort();
    	}else{
    		BigDecimal requestAmount=(BigDecimal)getUIContext().get("requestAmount");
    		BigDecimal tmpSum=FDCHelper.ZERO;
    		for(int i=0;i<table.getRowCount();i++){
    			tmpSum=tmpSum.add(FDCHelper.toBigDecimal(table.getCell(i, "thisTimeReq").getValue()));
    		}
    		if(FDCHelper.subtract(requestAmount, tmpSum).signum()!=0){
    			FDCMsgBox.showWarning(this, "本次请款金额之和不等于付款申请单金额");
        		SysUtil.abort();
    		}
    	}
    	
    	for(int i=0;i<table.getRowCount();i++){
    		IRow row=table.getRow(i);
    		boolean isSelect=row.getCell("isSelect").getValue()==Boolean.TRUE;
    		if(isSelect){
    			if(row.getUserObject() instanceof ContractCostSplitEntryInfo){
	    			if(FDCHelper.subtract(row.getCell("planExter").getValue(), row.getCell("thisTimeReq").getValue()).signum()<0){
	    				table.getSelectManager().select(i, 0,KDTSelectManager.ROW_SELECT);
	    				int v=FDCMsgBox.showConfirm2New(this, "当前行本次请款金额大于计划余额,是否继续?");
	    				if(v==FDCMsgBox.NO){
	    					SysUtil.abort();
	    				}else{
	    					break;
	    				}
	    			}
    			}
    		}
    	}
    }
    protected void btnOk_actionPerformed(ActionEvent e) throws Exception {
    	//TODO
    	verify();
    	CoreBaseCollection acctPays=new CoreBaseCollection();
    	PayRequestBillInfo payReqInfo=new PayRequestBillInfo();
    	FDCBudgetAcctEntryCollection budgetAcctEntrys=new FDCBudgetAcctEntryCollection();
    	payReqInfo.setId(BOSUuid.read((String)getUIContext().get("payReqId")));
    	String prjId=(String)getUIContext().get("prjId");
    	String contractId=(String)getUIContext().get("contractId");
    	for(int i=0;i<table.getRowCount();i++){
    		IRow row=table.getRow(i);
    		Object object = row.getUserObject();
    		boolean isSelect=row.getCell("isSelect").getValue()==Boolean.TRUE;
    		if(!isSelect){
    			continue;
    		}
    		if(object instanceof ContractCostSplitEntryInfo){
    			ContractCostSplitEntryInfo splitEntry=(ContractCostSplitEntryInfo)row.getUserObject();
    			PayRequestAcctPayInfo acctPay=new PayRequestAcctPayInfo();
    			acctPay.setAmount(FDCHelper.toBigDecimal(row.getCell("thisTimeReq").getValue()));
    			acctPay.setLstAllAmount(FDCHelper.toBigDecimal(row.getCell("hasReq").getValue()));
    			acctPay.setCostAccount(splitEntry.getCostAccount());
    			acctPay.setPayRequestBill(payReqInfo);
    			acctPay.setPeriod((FDCBudgetPeriodInfo)prmtPeriod.getValue());
    			acctPay.setProjectId(prjId);
    			acctPay.setContractId(contractId);
    			acctPays.add(acctPay);
    		}
    		if(object instanceof FDCMonthBudgetAcctEntryInfo){
    			FDCMonthBudgetAcctEntryInfo entry=(FDCMonthBudgetAcctEntryInfo)row.getUserObject();
        		// 已关联过的待签定不保存,避免重复
    			boolean isAssociate = Boolean.valueOf(
						entry.getContractBill() != null).booleanValue()
						&& associateAcctSet.contains(entry.getCostAccount()
								.getId().toString());
//    			if(!isAssociate){
    				budgetAcctEntrys.add((FDCBudgetAcctEntryInfo)row.getUserObject());
//    			}
    		}
    	}
    	if(acctPays.size()>0 || budgetAcctEntrys.size()>0){
    		getUIContext().put("acctPays", acctPays);
    		if(acctPays.size()>0){
    			PayRequestAcctPayFactory.getRemoteInstance().save(acctPays);
    		}
    		if(budgetAcctEntrys.size()>0){
    			FDCBudgetAcctFacadeFactory.getRemoteInstance().bindToContract(contractId, budgetAcctEntrys);
    		}
    		FDCMsgBox.showInfo(this, "保存成功");
    		destroyWindow();
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
		if(getOprtState()!=null&&getOprtState().equals(OprtState.VIEW)){
			return;
    	}
		IRow acctReqRow = null;
		// 是否选择了待签定合同行，true=>拆分科目行自动选择/false=>拆分科目行不选择
		boolean hasSelect = false;
    	if(e.getType()==1&&e.getRowIndex()>-1&&e.getColIndex()>-1){
    		IRow row=table.getRow(e.getRowIndex());
    		if(e.getColIndex()==table.getColumnIndex("isSelect")){
				Boolean isSelect=(Boolean)row.getCell(e.getColIndex()).getValue();
//				isSelect=Boolean.TRUE;
				Object curObject = row.getUserObject();
				int nextIndex = e.getRowIndex()+1<=table.getRowCount()-1?e.getRowIndex()+1:e.getRowIndex();
				Object nextObject = table.getRow(nextIndex).getUserObject();
				//待签定
				if (curObject instanceof FDCBudgetAcctEntryInfo
						|| (curObject instanceof ContractCostSplitEntryInfo && nextObject instanceof FDCBudgetAcctEntryInfo)) {
					if(row.getUserObject() instanceof ContractCostSplitEntryInfo){
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
	    				acctReqRow=row;
	    			}
	    			if(row.getUserObject() instanceof FDCMonthBudgetAcctEntryInfo){
	    				FDCMonthBudgetAcctEntryInfo entry=(FDCMonthBudgetAcctEntryInfo)row.getUserObject();
	    				if(hasAssociateAcctSet.contains(entry.getId().toString())){
	    					return;
	    				}
	    				row.getCell(e.getColIndex()).setValue(Boolean.valueOf(isSelect==Boolean.FALSE));
	    				// 是否拆分科目行
	    				boolean isAcctRow = false;
	    				for(int i=e.getRowIndex()-1;i>=0;i--){
	    					if(isAcctRow){
	    						continue;
	    					}
	    					IRow tmpRow = table.getRow(i);
	    					if(tmpRow.getUserObject() instanceof ContractCostSplitEntryInfo){
	    						isAcctRow = true;
	    					}
	    					acctReqRow = tmpRow;
	    				}
	    			}
	    			Object[] unSettledAmt = getUnSettledContractAcctAmt(acctReqRow);
	    			Object reqAmt = acctReqRow.getCell("hasReq").getValue();
	    			acctReqRow.getCell("cost").setValue(unSettledAmt[0]);
	    			acctReqRow.getCell("pay").setValue(unSettledAmt[1]);
	    			acctReqRow.getCell("planExter").setValue(FDCNumberHelper.subtract(unSettledAmt[1], reqAmt));
				}else{
					//已签定
					acctReqRow = row;
				}
				for(int j=acctReqRow.getRowIndex()+1;j<table.getRowCount()-1;j++){
					IRow tmpRow = table.getRow(j);
					if(tmpRow.getUserObject() instanceof ContractCostSplitEntryInfo){
						continue;
					}else{
						if(((Boolean)tmpRow.getCell(e.getColIndex()).getValue()).booleanValue()){
							hasSelect = true;
						}
					}
				}
				if(hasSelect){
					acctReqRow.getCell(e.getColIndex()).setValue(Boolean.valueOf(hasSelect));
				}else{
					acctReqRow.getCell(e.getColIndex()).setValue(Boolean.valueOf(isSelect==Boolean.FALSE));
				}
				acctReqRow.getCell("thisTimeReq").getStyleAttributes().setLocked(isSelect.booleanValue());
				if(isSelect.booleanValue()){
					acctReqRow.getCell("thisTimeReq").getStyleAttributes().setBackground(Color.WHITE);
					acctReqRow.getCell("thisTimeReq").setValue(null);
					if(hasSelect){
						acctReqRow.getCell("thisTimeReq").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
						acctReqRow.getCell("thisTimeReq").setValue(acctReqRow.getCell("planExter").getValue());
					}
				}else{
					acctReqRow.getCell("thisTimeReq").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
					acctReqRow.getCell("thisTimeReq").setValue(acctReqRow.getCell("planExter").getValue());
				}
				
				setUnionAll();
    		}
    		
    	}
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	initTable();
    	fillTable();
    	initCtrListener();
    	if(getOprtState()!=null&&getOprtState().equals(OprtState.VIEW)){
    		table.getStyleAttributes().setLocked(true);
    		btnOk.setEnabled(false);
    		btnNo.setEnabled(false);
    	}
    	prmtPeriod.setEditable(false);
    }
    private void initCtrListener()throws Exception{
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
    			FDCBudgetPeriodInfo newPeriod=(FDCBudgetPeriodInfo)e.getData();
    			FDCBudgetPeriodInfo basePeriod=(FDCBudgetPeriodInfo)getUIContext().get("basePeriod");
    			if(newPeriod.getYear()<basePeriod.getYear()||(newPeriod.getYear()==basePeriod.getYear()&&newPeriod.getMonth()<basePeriod.getMonth())){
    				FDCMsgBox.showWarning(table, "月份不能早于付款日期");
    				e.setResult(PreChangeEvent.S_FALSE);
    			}
    		}
    	});
    	table.addKDTEditListener(new KDTEditAdapter(){
    		public void editStopped(KDTEditEvent e) {
    			if(e.getColIndex()==table.getColumnIndex("thisTimeReq")){
        			setUnionAll();
        		}
    		}
    	});
    	table.setAfterAction(new BeforeActionListener(){
    		public void beforeAction(BeforeActionEvent e) {
				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
					int activeColumnIndex = table.getSelectManager().getActiveColumnIndex();
					if(activeColumnIndex==table.getColumnIndex("thisTimeReq")){
	        			setUnionAll();
	        		}
				}
    		}
    	});
    }
    //合同,期间,已关联
    private Set associateAcctSet=null;
    //本次，关联
    private Set hasAssociateAcctSet=null;
    private void fillTable() throws EASBizException, BOSException{
    	table.removeRows();
    	String payReqId=(String)getUIContext().get("payReqId");
    	FDCBudgetPeriodInfo period=(FDCBudgetPeriodInfo)getUIContext().get("period");
    	Map dataMap=FDCBudgetAcctFacadeFactory.getRemoteInstance().getAssociateBudget(payReqId, period);

    	if(dataMap==null||dataMap.get("splitEntrys")==null){
    		FDCMsgBox.showWarning(this, "没有该月的项目月度计划数据");
    		SysUtil.abort();
    	}
    	if(dataMap.get("noPlan")!=null){
    		int v=FDCMsgBox.showConfirm2New(this, "该付款申请单对应的合同没有成本科目付款计划,是否继续?\n");
    		if(v!=FDCMsgBox.YES){
    			SysUtil.abort();
    		}
    	}
//    	if(dataMap.get("NoData")!=null){
//    		FDCMsgBox.showWarning(this, "该月没有月度付款计划");
//    		return;
//    	}
    	getUIContext().put("contractId", dataMap.get("contractId"));
    	getUIContext().put("prjId", dataMap.get("prjId"));
    	prmtProject.setValue(dataMap.get("curProject"));
    	prmtContract.setValue(dataMap.get("contractBill"));
    	associateAcctSet=(Set)dataMap.get("associateAcctSet");
    	if(associateAcctSet==null){
    		associateAcctSet = new HashSet();
    	}
    	hasAssociateAcctSet=new HashSet();
    	
    	
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
    	Map budgetEntryMap=(Map)dataMap.get("budgetEntryMap"); 
    	if(budgetEntryMap==null||budgetEntryMap.size()==0){
    		budgetEntryMap=new  HashMap();
    		table.getColumn("name").getStyleAttributes().setHided(true);
    	}
    	ContractCostSplitEntryCollection splitEntrys=(ContractCostSplitEntryCollection)dataMap.get("splitEntrys");
    	for(Iterator iter=splitEntrys.iterator();iter.hasNext();){
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
    			FDCMonthBudgetAcctEntryInfo budgetEntry = (FDCMonthBudgetAcctEntryInfo)iter2.next();
    			budgetEntry.getCostAccount().getLevel();
    			row.setUserObject(budgetEntry);
        		loadRow(row);
    		}
    	}
    	KDTableHelper.autoFitColumnWidth(table, table.getColumnIndex("acctNumber"),2);
    	KDTableHelper.autoFitColumnWidth(table, table.getColumnIndex("acctName"),2);
    	KDTableHelper.autoFitColumnWidth(table, table.getColumnIndex("isSelect"),2);
    	setUnionAll();
    }
    
    /**
     * 添加汇总行进行汇总
     */
    private void setUnionAll(){
    	//设置合计行
    	KDTFootManager footRowManager= table.getFootManager();
    	IRow footRow = null;
    	if(footRowManager==null){
    		String total="合计";
    		if(footRowManager==null){
    			footRowManager = new KDTFootManager(table);
    			footRowManager.addFootView();
    			table.setFootManager(footRowManager);
    			footRow= footRowManager.addFootRow(0);
    			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
    			table.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
    			table.getIndexColumn().setWidth(60);
    			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
    			//设置到第一个可视行
    			footRowManager.addIndexText(0, total);
    		}
    	}else{
    		footRow=table.getFootRow(0);
    	}
    	List colList=Arrays.asList((new String[]{"cost","pay","hasReq","planExter","thisTimeReq"}));
    	Map map=new HashMap();
    	for(int i=0;i<table.getRowCount();i++){
    		IRow row=table.getRow(i);
    		Object object = row.getUserObject();
    		if(object instanceof FDCMonthBudgetAcctEntryInfo){
    			continue;
    		}
	    	for(Iterator iter=colList.iterator();iter.hasNext();){
	    		String colKey=(String)iter.next();
	    		map.put(colKey, FDCNumberHelper.add(map.get(colKey), row.getCell(colKey).getValue()));
	    	}
    	}
    	for(Iterator iter=colList.iterator();iter.hasNext();){
    		String colKey=(String)iter.next();
    		footRow.getCell(colKey).setValue(map.get(colKey));
    	}
    	
    }
    
    private void loadRow(IRow row){
    	if(row.getUserObject() instanceof ContractCostSplitEntryInfo){
			ContractCostSplitEntryInfo entry=(ContractCostSplitEntryInfo)row.getUserObject();
			row.getCell("id").setValue(entry.getId().toString());
			row.getCell("acctNumber").setValue(entry.getCostAccount().getLongNumber());
			row.getCell("acctName").setValue(entry.getCostAccount().getName());
			row.getCell("splitAmt").setValue(entry.getAmount());
			row.getCell("cost").setValue(entry.getBigDecimal("costAmt"));
			row.getCell("pay").setValue(entry.getBigDecimal("planAmt"));
			BigDecimal reqAllAmount = entry.getBigDecimal("reqAllAmt");
			row.getCell("hasReq").setValue(reqAllAmount);
			BigDecimal amount = entry.getBigDecimal("reqAmt");
			row.getCell("thisTimeReq").setValue(amount);
			BigDecimal planExter = FDCNumberHelper.subtract(entry.getBigDecimal("planAmt"), reqAllAmount);
			row.getCell("planExter").setValue(planExter);
			if(entry.getCostAccount()!=null){
				row.getCell("project").setValue(entry.getCostAccount().getCurProject());	
			}
	    	boolean isSelect = FDCNumberHelper.isPositiveBigDecimal(amount);
			row.getCell("isSelect").setValue(Boolean.valueOf(isSelect));
	    	boolean isLock=false;
			if(getOprtState()!=null&&getOprtState().equals(OprtState.VIEW)){
	    		isLock=true;
	    	}
			boolean hasPlanExter = FDCNumberHelper.isPositiveBigDecimal(planExter);
			
			if((isSelect || hasPlanExter)&&!isLock){
				if(hasPlanExter){
					row.getCell("isSelect").setValue(Boolean.valueOf(true));
				}
				row.getCell("thisTimeReq").getStyleAttributes().setLocked(false);
				
				row.getCell("thisTimeReq").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
				if(row.getCell("thisTimeReq").getValue()==null){
					row.getCell("thisTimeReq").setValue(row.getCell("planExter").getValue());
				}
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
    		}
    		if(entry.getContractBill()!=null){
    			hasAssociateAcctSet.add(entry.getId().toString());
    		}
    		if(entry.getCostAccount()!=null){
    			row.getCell("project").setValue(entry.getCostAccount().getCurProject());
    		}
    		row.setUserObject(entry);
    	}
    	//强制已取消
//    	row.getCell("exception").setValue(Boolean.FALSE);
    }
    private void initTable() {
    	table.checkParsed();
    	table.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	table.getColumn("acctNumber").setRenderer(FDCRenderHelper.getLongNumberRender());
//    	table.getColumn("thisTimeReq").setEditor(FDCTableHelper.getCellNumberEditor(FDCNumberHelper.ZERO, FDCNumberHelper.MAX_VALUE, true));
    	table.getStyleAttributes().setLocked(true);
    	
    	FDCHelper.formatTableNumber(table, "splitAmt");
    	FDCHelper.formatTableNumber(table, "cost");
    	FDCHelper.formatTableNumber(table, "pay");
    	
    	FDCHelper.formatTableNumber(table, "hasReq");
    	FDCHelper.formatTableNumber(table, "planExter");
    	FDCHelper.formatTableNumber(table, "thisTimeReq");
    	
    	table.getColumn("exception").getStyleAttributes().setHided(true);
	}
}