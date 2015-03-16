/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.VerticalAlignment;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CellBinder;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractPayItemCollection;
import com.kingdee.eas.fdc.contract.ContractPayItemInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractFullInfoUI;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctDataInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctException;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctHelper;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctFactory;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryCollection;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctFactory;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctItemInfo;
import com.kingdee.eas.fdc.finance.IFDCBudgetAcct;
import com.kingdee.eas.fdc.finance.FDCBudgetCtrlStrategy.FDCBudgetParam;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FindDialog;
import com.kingdee.eas.framework.client.FindListEvent;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.IFindListListener;
import com.kingdee.eas.framework.client.ListFind;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.framework.util.StringUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class FDCMonthBudgetAcctEditUI extends AbstractFDCMonthBudgetAcctEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCMonthBudgetAcctEditUI.class);
    private final String COLUMN_AUDITEDPAY="month_auditedPay";
    private Map preVerMap = new HashMap();
    //过滤界面
    private FDCDepMonBudgetAcctFilterUI budgetAcctFilterUI = null;
    /**
     * output class constructor
     */
    public FDCMonthBudgetAcctEditUI() throws Exception
    {
        super();
    }
    
    public void storeFields(){
    }

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);
	}

	FDCBudgetAcctInfo createNewFDCBudgetAcct() {
		FDCBudgetAcctInfo info=new FDCMonthBudgetAcctInfo();
		return info;
	}

	IFDCBudgetAcct getRemoteInterface() throws BOSException {
		return FDCMonthBudgetAcctFactory.getRemoteInstance();
	}

	protected void resetTableHead() {
		super.resetTableHead();
		int startIndex=getDetailTable().getColumnIndex("allPay")+2;
		IRow headRow1=getDetailTable().getHeadRow(0);
		IRow headRow2=getDetailTable().getHeadRow(1);
		int currentMonth=getCurrentMonth();
		for(int i=currentMonth;i<currentMonth+3;i++){
			IColumn column = getDetailTable().addColumn(startIndex);
			column.setKey(getMonthCostKey(i));
			column.setEditor(CellBinder.getCellNumberEdit());
//			System.out.println("column:"+column.getColumnIndex()+"\tkey"+column.getKey());
			column=getDetailTable().addColumn(startIndex+1);
			column.setKey(getMonthPayKey(i));
			column.setEditor(CellBinder.getCellNumberEdit());
//			System.out.println("column:"+column.getColumnIndex()+"\tkey"+column.getKey());
			String title=(i>12?i%12:i)+"月预测";
			if(currentMonth==i){
				column=getDetailTable().addColumn(startIndex+2);
				column.setKey(this.COLUMN_AUDITEDPAY);
				column.setEditor(CellBinder.getCellNumberEdit());
				headRow1.getCell(startIndex).setValue(title);
				headRow1.getCell(startIndex+1).setValue(title);
				headRow1.getCell(startIndex+2).setValue(title);
				headRow2.getCell(startIndex).setValue("成本");
				headRow2.getCell(startIndex+1).setValue("付款申报");
				headRow2.getCell(startIndex+2).setValue("付款审批");
//				System.out.println("column:"+column.getColumnIndex()+"\tkey"+column.getKey());
				startIndex+=3;
			}else{
				headRow1.getCell(startIndex).setValue(title);
				headRow1.getCell(startIndex+1).setValue(title);
				headRow2.getCell(startIndex).setValue("成本");
				headRow2.getCell(startIndex+1).setValue("付款");
				startIndex+=2;
			}
		}
//		getDetailTable().addColumn().setKey(this.COLUMN_AUDITEDPAY);
		getDetailTable().getHeadMergeManager().setMergeMode(KDTMergeManager.FREE_MERGE);
		FDCHelper.formatTableNumber(getDetailTable(), "aimCost");
		FDCHelper.formatTableNumber(getDetailTable(), "dynCost");
		FDCHelper.formatTableNumber(getDetailTable(), getDetailTable().getColumnIndex("conAmt"), getDetailTable().getColumnIndex("desc")-1);
		getDetailTable().getColumn("payItem").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT); 
		getDetailTable().getColumn("process").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		if(disEcoItemProcess()){
			getDetailTable().getColumn("payItem").getStyleAttributes().setWrapText(true); 
			getDetailTable().getColumn("payItem").getStyleAttributes().setVerticalAlign(VerticalAlignment.MIDDLE);
			tblMain.getHeadMergeManager().mergeBlock(0, 4, 1, 11, KDTMergeManager.FREE_MERGE);
		}
	}

	FDCBudgetAcctEntryInfo createNewFDCBudgetAcctEntry() {
		return new FDCMonthBudgetAcctEntryInfo();
	}

    protected void storeRows(){
    	FDCMonthBudgetAcctInfo monthBudgetAcctInfo = getFDCMonthBudgetAcctInfo();
    	if(monthBudgetAcctInfo.getEntrys()==null){
    		monthBudgetAcctInfo.put("entrys", new FDCMonthBudgetAcctEntryCollection());
    	}
    	monthBudgetAcctInfo.getEntrys().clear();
    	for(int i=0;i<getDetailTable().getRowCount();i++){;
    		IRow row=getDetailTable().getRow(i);
    		FDCMonthBudgetAcctEntryInfo entry = (FDCMonthBudgetAcctEntryInfo)storeRow(row);
    		if(entry!=null){
    			monthBudgetAcctInfo.getEntrys().add(entry);
    		}
    	}
    }
    protected FDCBudgetAcctEntryInfo storeRow(IRow row){
    	if(!isEmptyRow(row)&&row.getUserObject() instanceof FDCMonthBudgetAcctEntryInfo){
    		FDCMonthBudgetAcctEntryInfo entry=(FDCMonthBudgetAcctEntryInfo)row.getUserObject();
    		if(isRecension()){
    			entry.setId(null);
    		}
			entry.setCreator((UserInfo)row.getCell("creator").getValue());
			entry.setDesc((String)row.getCell("desc").getValue());
			entry.setDeptment((AdminOrgUnitInfo)row.getCell("deptment").getValue());
			entry.setName((String)row.getCell("conName").getValue());
			entry.setNumber((String)row.getCell("conNumber").getValue());
			entry.setPartB((String)row.getCell("partB").getValue());
			entry.setSplitAmt((BigDecimal)row.getCell("splitAmt").getValue());
			entry.setConAmt((BigDecimal)row.getCell("conAmt").getValue());
			entry.getItems().clear();
			entry.setParent(getFDCMonthBudgetAcctInfo());
			int currentMonth=getCurrentMonth();
			for(int i=currentMonth;i<currentMonth+3;i++){
				FDCMonthBudgetAcctItemInfo item=new FDCMonthBudgetAcctItemInfo();
				item.setEntry(entry);
				item.setAmount((BigDecimal)row.getCell(getMonthPayKey(i)).getValue());
				item.setCost((BigDecimal)row.getCell(getMonthCostKey(i)).getValue());
				item.setMonth(i>12?i%12:i);
				if(item.getAmount()==null&&item.getCost()==null&&row.getCell(COLUMN_AUDITEDPAY).getValue()==null){
					continue;
				}
				if(i==currentMonth){
					entry.setCost(item.getCost());
					entry.setAmount((BigDecimal)row.getCell(COLUMN_AUDITEDPAY).getValue());
				}
				entry.getItems().add(item);
			}
			
			return entry;
		}
    	return null;
    }
    protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
    	Object o = getUIContext().get("isRecension");
    	if(o!=null&&((Boolean)o).booleanValue()){
    		
    	}else{
    		super.getNumberByCodingRule(caller, orgId);
    	}
    
    }
    protected void prepareNumber(IObjectValue caller, String number) {
    	Object o = getUIContext().get("isRecension");
    	if(o!=null&&((Boolean)o).booleanValue()){
    		
    	}else{
    		super.prepareNumber(caller, number);
    	}
    }
    
    private FDCMonthBudgetAcctInfo getFDCMonthBudgetAcctInfo(){
    	return (FDCMonthBudgetAcctInfo)this.editData;
    }
    
    
    protected ICoreBase getBizInterface() throws Exception {
    	return FDCMonthBudgetAcctFactory.getRemoteInstance();
    }
    /**
     * 查看合同执行信息  by  cassiel_peng  2010-01-29
     */
    public void actionConExecInfo_actionPerformed(ActionEvent e)
    		throws Exception {
    	
	    //检查是否选中行
	    if (tblMain.getRowCount() == 0 || tblMain.getSelectManager().size() == 0)
        {
            MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
            SysUtil.abort();
        }
        //检查所选中的行是否是某一个成本科目下的合同行
	    IRow currentRow = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
	    Object userObject = currentRow.getUserObject();
	    FDCMonthBudgetAcctEntryInfo temp = null;
	    if(userObject!=null && userObject instanceof FDCMonthBudgetAcctEntryInfo){
	    	temp = (FDCMonthBudgetAcctEntryInfo)userObject;
	    	if(temp.getItemType()!=null&&FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT_VALUE.equals(temp.getItemType().getValue())){
	    		MsgBox.showWarning("待签订合同无合同执行信息可供查看！");
	    		SysUtil.abort();
	    	}
	    	if(temp.getContractBill()!=null&&temp.getContractBill().getId()!=null){
	    		String contractId = temp.getContractBill().getId().toString();
	    		if(!FDCUtils.isRunningWorkflow(this.editData.getId().toString())){
	    			ContractFullInfoUI.showDialogWindows(this, contractId);
	    		}else{
	    			UIContext uiContext = new UIContext(ui);
	    			uiContext.put(UIContext.ID, contractId);
	    			// 创建UI对象并显示
	    			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
	    					.create(ContractFullInfoUI.class.getName(), uiContext, null,
	    							"VIEW");
	    			uiWindow.show();
	    		}
	    	}
	    }
	}
    public void actionHideBlankRow_actionPerformed(ActionEvent e)
    		throws Exception {
    	// TODO Auto-generated method stub
    	super.actionHideBlankRow_actionPerformed(e);
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();

    	this.tblMain.getSelectManager().select(0,0,0,tblMain.getColumnCount());
    	
//    	editPayAuditColumn();
    	setColorByVersionDiff();
    	//增加监听事件使在点击delete等键值的时候，付款审批的的值改变
    	tblMain.setBeforeAction(new BeforeActionListener(){
    		public void beforeAction(BeforeActionEvent e)
    		{
    			if(BeforeActionEvent.ACTION_DELETE==e.getType() || BeforeActionEvent.ACTION_PASTE==e.getType()){
    				for (int i = 0; i <getDetailTable().getSelectManager().size(); i++)
    				{
    					KDTSelectBlock block = getDetailTable().getSelectManager().get(i);
    					for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++)
    					{
    						for(int colIndex=block.getBeginCol();colIndex<=block.getEndCol();colIndex++){
    							if(colIndex==getDetailTable().getColumnIndex(COLUMN_AUDITEDPAY)-1){
    								KDTEditEvent event = new KDTEditEvent(tblMain, null, null, rowIndex, colIndex, true, 1);
    								try {
    									tblMain_editStopped(event);
    								} catch (Exception e1) {
    									e1.printStackTrace();
    								}
    							}
    						}
    					}
    				}
    			}
    		}
    	});
    }
    
    /**
     * 获取付款事项、形象进度及其他数据  by Cassiel_peng  2009-12-1
     * 
     * 性能优化，移到服务端一次性取数 by hpw 
     */
    private Map fetchOtherData(){
    	
    	/*Map returnValMap=new HashMap();
    	//付款事项
    	EntityViewInfo view=new EntityViewInfo();
    	view.getSelector().add("*");
    	view.getSelector().add("payItemDate");
    	view.getSelector().add("paymentType.name");
    	view.getSelector().add("payCondition");
    	view.getSelector().add("prop");
    	view.getSelector().add("amount");
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractbill",contractId));
		view.setFilter(filter);
		try {
			ContractPayItemCollection payItemColl = ContractPayItemFactory.getRemoteInstance().getContractPayItemCollection(view);
			if(payItemColl!=null&&payItemColl.size()!=0){
				returnValMap.put("payItem", payItemColl);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
    	//形象进度
		EntityViewInfo _view=new EntityViewInfo();
		FilterInfo _filter=new FilterInfo();
		_view.setFilter(_filter);
		String sql="select top 1 fid from T_CON_PayRequestBill where FContractId='"+contractId+"' order by fcreateTime desc";
		_filter.getFilterItems().add(new FilterItemInfo("id",sql,CompareType.INNER));
		_view.getSelector().add("id");
		_view.getSelector().add("process");
		try {
			PayRequestBillCollection payRequestColl= PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(_view);
			if(payRequestColl!=null&&payRequestColl.size()!=0){
				returnValMap.put("process", payRequestColl.get(0));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}*/
    	return (Map)getDataMap().get("otherData");
    }
    
    public void loadRow(IRow row) {
    	super.loadRow(row);
		FDCBudgetAcctEntryInfo entry=(FDCBudgetAcctEntryInfo)row.getUserObject();
		//已完工成本
		if (FDCBudgetAcctItemTypeEnum.CONTRACT.equals(entry.getItemType())) {
			row.getCell("allCost").setValue(entry.getBigDecimal("allCost"));
		}
		row.getCell("allPay").setValue(entry.getBigDecimal("allPay"));
		if(entry.getContractBill()!=null&&entry.getContractBill().getRespPerson()!=null){
			row.getCell("conDeptPerson").setValue(entry.getContractBill().getRespPerson().getName());
		}
		if(entry.getContractBill()!=null&&entry.getContractBill().getCreator()!=null){
			row.getCell("conCreator").setValue(entry.getContractBill().getCreator().getName());
		}
		if (FDCBudgetAcctItemTypeEnum.CONTRACT.equals(entry.getItemType())&&entry.getContractBill()!=null) {
			Object o=entry.getContractBill().getId();
			String contractId=o==null?"":o.toString();
			Map otherDataMap=fetchOtherData();
			if(otherDataMap.size()==0){
				return ;
			}else{
				if(otherDataMap.get("payItem"+contractId)!=null){
					StringBuffer tempStr=new StringBuffer("");
					int tempsequence=0;
					ContractPayItemCollection payItemColl=(ContractPayItemCollection)otherDataMap.get("payItem"+contractId);
					for (java.util.Iterator iter=payItemColl.iterator();iter.hasNext();) {
						tempsequence++;
						ContractPayItemInfo payItem=(ContractPayItemInfo)iter.next();
						String payItemDate=payItem.getPayItemDate()==null?"":FDCDateHelper.DateToString(payItem.getPayItemDate());
						String payItemType=payItem.getPaymentType()==null?"":payItem.getPaymentType().getName();
						String payIteCondition=payItem.getPayCondition();
						String payItemProp=payItem.getProp()==null?"":payItem.getProp().toString();
						String payItemAmt=payItem.getAmount()==null?"":payItem.getAmount().toString();
						tempStr.append(tempsequence).append(".").append("[").append(payItemDate).append("|").append(payIteCondition).append("|").append(payItemType).append("|").append(payItemProp).append("|").append(payItemAmt).append("]").append("\n");
					}
					row.getCell("payItem").setValue(tempStr);
				}
				if(otherDataMap.get("process"+contractId)!=null){
//					PayRequestBillInfo payRequestBill=(PayRequestBillInfo)otherDataMap.get("process"+contractId);
//					Object process=payRequestBill.getProcess();
					row.getCell("process").setValue(otherDataMap.get("process"+contractId));
				}
			}
		}
		//付款事项
		//形象进度
		KDTableHelper.autoFitRowHeight(getDetailTable(), row.getRowIndex());
    }
    
    public void fillItems(IRow row){
    	super.fillItems(row);
    	FDCMonthBudgetAcctEntryInfo entry=(FDCMonthBudgetAcctEntryInfo)row.getUserObject();
    	row.getCell(this.COLUMN_AUDITEDPAY).setValue(entry.getAmount());
    }
    protected void tblMain_editStopped(KDTEditEvent e){
    	Object newValue=e.getValue();
    	Object oldValue=e.getOldValue();
    	//按下delete或是backspace，给付款审批赋值为空
 /*   	if(newValue==null){
  * 
    		if(isMonthColumn(e.getColIndex())){
        		IRow row=getDetailTable().getRow(e.getRowIndex());
        		if(e.getColIndex()==getDetailTable().getColumnIndex(this.COLUMN_AUDITEDPAY)-1){
        			row.getCell(this.COLUMN_AUDITEDPAY).setValue(newValue);
        		}
        	}
    	}*/
    	/*if(newValue==null&&oldValue==null){
    		return;
    	}
    	if(newValue!=null&&oldValue!=null&&newValue.equals(oldValue)){
    		return;
    	}*/
   
    	if(isMonthColumn(e.getColIndex())){
    		IRow row=getDetailTable().getRow(e.getRowIndex());
//    		BigDecimal tmpAmt=FDCHelper.ZERO;
//    		BigDecimal tmpCost=FDCHelper.ZERO;
//			int currentMonth=getCurrentMonth();
//			for(int i=currentMonth;i<currentMonth+3;i++){
//    			tmpAmt=FDCNumberHelper.add(tmpAmt, row.getCell(getMonthPayKey(i)).getValue());
//    			tmpCost=FDCNumberHelper.add(tmpCost, row.getCell(getMonthCostKey(i)).getValue());
//    		}
    		//当调整了列的顺序之后以下注释的硬编码会导致填写的申报列不能同步写到审批列 
    		/*if(e.getColIndex()==getDetailTable().getColumnIndex(this.COLUMN_AUDITEDPAY)-1){
    			row.getCell(this.COLUMN_AUDITEDPAY).setValue(newValue);
    		}改正为：by Cassiel_peng 2009-10-20*/
//    		if(e.getColIndex()==getDetailTable().getColumnIndex("month_pay"+getCurrentMonth())){
//    			row.getCell(this.COLUMN_AUDITEDPAY).setValue(newValue);
//    		}
    		//严格地以columnKey值比较
    		if(getDetailTable().getColumnKey(e.getColIndex()).equals("month_pay"+getCurrentMonth())){
    			row.getCell(this.COLUMN_AUDITEDPAY).setValue(newValue);
    		}
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
    		int currentMonth=getCurrentMonth();
    		for(int i=currentMonth;i<currentMonth+3;i++){
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
    		if(isEmpty&&!row.getCell("conName").getStyleAttributes().isLocked()){
    			isEmpty=FDCHelper.isEmpty(row.getCell("conName").getValue());
    		}
    		setEmptyRow(row, isEmpty);
    	}
    }
    protected void afterFillTable() {
    	super.afterFillTable();
    }
    private int getCurrentMonth() {
		return getSpValue(spMonth);
	}
    
    
    public void setEditable() {
    	super.setEditable();
    	if(getOprtState().equals(STATUS_FINDVIEW)) {
    		getDetailTable().getColumn(this.COLUMN_AUDITEDPAY).getStyleAttributes().setLocked(false);
    	}else{
    		getDetailTable().getColumn(this.COLUMN_AUDITEDPAY).getStyleAttributes().setLocked(true);
    	}
	}
    
	protected FDCBudgetAcctInfo getFDCBudgetAcctInfo(){
		return this.editData;
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
    			BigDecimal lstPay = dataInfo.getLstPay();
    			row.getCell("aimCost").setValue(aimCost);
				row.getCell("dynCost").setValue(dynCost);
				/**
				 * 科目级 已完工成本 显示改为 合同+科目双维度 在合同行显示
				 * by hpw date:2009-07-13
				 */
//				row.getCell("allCost").setValue(lstCost);
//				row.getCell("allPay").setValue(lstPay);
    			
    		}
    	}
	}
    protected List getColumnSumList(){
    	List list=getColumnSumList();
    	int currentMonth = getCurrentMonth();
    	for(int i=currentMonth;i<currentMonth+3;i++){
    		list.add(getMonthCostKey(i));
    		list.add(getMonthPayKey(i));
    	}
    	list.add("allCost");
    	list.add("allPay");
    	return list;
    }
    private boolean disEcoItemProcess(){
    	boolean disEcoItemProcess=true;
    	try {
    		disEcoItemProcess=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_FDC322_DISECOITEMPROCESS);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return disEcoItemProcess;
    }
    protected void initTable() {
    	super.initTable();
    	tblMain.getColumn("creator").setWidth(130);
    	if(disEcoItemProcess()){
    		tblMain.getColumn("payItem").getStyleAttributes().setHided(false);
    		tblMain.getColumn("process").getStyleAttributes().setHided(false);
    		tblMain.getColumn("payItem").setWidth(250);
    		tblMain.getColumn("process").setWidth(130);
    	}else{
    		tblMain.getColumn("payItem").getStyleAttributes().setHided(true);
    		tblMain.getColumn("process").getStyleAttributes().setHided(true);
    	}
     }
    
	/**
	 * 科目汇总列
	 * @return
	 */
	protected List getSumColumns(){
		List list=super.getSumColumns();
//    	list.add("allCost");
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
		list.add("allCost");
		list.add("allPay");
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
    
        SwingUtilities.invokeLater(new Runnable(){
   	  		public void run() {
   	  			editPayAuditColumn();
   	 		}
   	   });
        this.btnConExecInfo.setIcon(EASResource.getIcon("imgTbtn_execute"));
        this.menuItemConExecInfo.setIcon(EASResource.getIcon("imgTbtn_execute"));
	}
	
	/**
	 * 描述：工作流中，修改付款审批列。若界面被抢锁不能修改，可将其放在线程最后执行<p>
	 * @author pengwei_hou sxhong @date: 2008-11-08<p><br>
	 * 举例：<pre>
	 * 	  SwingUtilities.invokeLater(new Runnable(){<p>
	 * 		public void run() {<p>
	 * 			editPayAuditColumn();<p>
	 * 		}<p>
	 * 	  });
	 */
	private void editPayAuditColumn(){
		
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
    	if(isFromWorkflow!=null 
    			&& isFromWorkflow.toString().equals("true") 
    			&& getOprtState().equals(STATUS_FINDVIEW) 
    			&& actionSave.isVisible() 
    			&& (editData.getState()==FDCBillStateEnum.SUBMITTED 
    					|| editData.getState() == FDCBillStateEnum.AUDITTING)){
    	
//    		getDetailTable().getStyleAttributes().setLocked(false);
//    		getDetailTable().setEnabled(true);
    		getDetailTable().setEditable(true);
    		
    		for(int i=0;i<getDetailTable().getRowCount();i++){
    			IRow row = getDetailTable().getRow(i);
    			if(row.getUserObject() instanceof FDCBudgetAcctEntryInfo){
//    				row.getStyleAttributes().setLocked(true);
    				//付款审批列在工作流中查看单据可修改
    				row.getCell(COLUMN_AUDITEDPAY).getStyleAttributes().setLocked(false);
    			}else{
    				row.getCell(COLUMN_AUDITEDPAY).getStyleAttributes().setLocked(true);
    			}
    		}
    		this.actionSave.setEnabled(true);
    	}
    
    	if(isFromWorkflow!=null 
    			&& isFromWorkflow.toString().equals("true") 
    			&& getOprtState().equals(STATUS_EDIT)){
    		actionRemove.setEnabled(false);
    	}
	}
	
	public boolean isModify() {
		return super.isModify();
	}
	
	protected void setUnionAll(){
//    	if(true){
//    		return;
//    	}
    	super.setUnionAll();
    	for(int i=0;i<getDetailTable().getRowCount();i++){
    		IRow row=getDetailTable().getRow(i);
			//付款大于成本以警告色显示
			int currentMonth = getCurrentMonth();
			for(int j=currentMonth;j<currentMonth+3;j++){
				String costKey=getMonthCostKey(j);
				Color oldColor=null;
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
			//付款比例
			Object obj = row.getUserObject();
//			if (obj == null || obj instanceof CostAccountInfo) {
				BigDecimal allCost = (BigDecimal)row.getCell("allCost").getValue();
				BigDecimal allPay = (BigDecimal)row.getCell("allPay").getValue();
				if (FDCNumberHelper.subtract(allCost, FDCHelper.ZERO).signum() > 0) {
					BigDecimal payScale = FDCHelper.divide(allPay, allCost, 4, BigDecimal.ROUND_HALF_UP);
					row.getCell("payScale").setValue(payScale);
				}
//			}
    	}
    	FDCHelper.formatTableNumber(tblMain, "payScale","##0.00%");
    	afterSetUnionAll();
    }
	
	protected int getStartMonth() {
		return getCurrentMonth();
	}
	
	protected int getEndMonth() {
		int currentMonth=getCurrentMonth();
		return currentMonth+2;
	}
	
	protected Set getCostColumns(){
		Set set=super.getCostColumns();
		set.add("allCost");
		return set;
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根\
		String prjId = null;
		String periodId = null;//""; 奥园反馈bug修改：""导致参数为空
		FDCMonthBudgetAcctInfo budgetInfo = null;
		if(this.editData!=null&&this.editData.getId()!=null){
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("curProject.id");
			selector.add("fdcPeriod.id");
			selector.add("sourceBillId");
			budgetInfo = FDCMonthBudgetAcctFactory.getRemoteInstance().getFDCMonthBudgetAcctInfo(new ObjectUuidPK(this.editData.getId()), selector);
			prjId = budgetInfo.getCurProject().getId().toString();
			periodId = budgetInfo.getFdcPeriod().getId().toString();
		}else if(this.editData!=null&&this.editData.getId()==null){
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select FID from T_FNC_FDCBudgetPeriod  where FYear = ? and FMonth =?  ");
			builder.addParam(new Integer(getSpValue(spYear)));
			builder.addParam(new Integer(getSpValue(spMonth)));
			IRowSet rowSet = builder.executeQuery();
			if(rowSet.next()){
				periodId = rowSet.getString("FID");
			}
			prjId = this.editData.getCurProject().getId()==null?null:this.editData.getCurProject().getId().toString();
		}
		if(budgetInfo!=null||(periodId!=null&&prjId!=null)){
			//问题:部门月度申报表提交审批后,数据同步到项目月度申报表中,在项目月度表审批时要如果有当月的部门表未审批,则有提示.此点系统已做到。
			//但是如果是在工作流中审批,是不能出现提示信息的(基础那边不支持在工作流中有信息提示)。故需要将上述逻辑放到项目月度申报表提交之时  by Cassiel_peng 2009-10-20
			FilterInfo filter=new FilterInfo();
			filter.appendFilterItem("curProject.id",prjId );
			filter.appendFilterItem("fdcPeriod.id", periodId );
			filter.appendFilterItem("state", FDCBillStateEnum.SAVED);
			filter.appendFilterItem("state", FDCBillStateEnum.SUBMITTED);
			filter.appendFilterItem("state", FDCBillStateEnum.AUDITTING);
			filter.setMaskString(" #0 and #1 and ( #2 or #3 or #4 )");
			boolean isExist = FDCDepMonBudgetAcctFactory.getRemoteInstance().exists(filter);
			if(isExist){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.EXISTUNAUDITTEDDEPMON);
			}
		}
		
		super.actionSave_actionPerformed(e);
		if(getUIContext().get("isFromWorkflow")!=null 
				&& getUIContext().get("isFromWorkflow").toString().equals("true") 
				&&getOprtState().equals(STATUS_EDIT) 
				&&actionSave.isVisible()){
			actionAddLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			actionInsertLine.setEnabled(false);
			actionRemove.setEnabled(false);
			actionSubmit.setEnabled(false);
		}
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根\
		String prjId = null;
		String periodId = null;
		FDCMonthBudgetAcctInfo budgetInfo = null;
		if(this.editData!=null&&this.editData.getId()!=null){
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("curProject.id");
			selector.add("fdcPeriod.id");
			selector.add("sourceBillId");
			budgetInfo = FDCMonthBudgetAcctFactory.getRemoteInstance().getFDCMonthBudgetAcctInfo(new ObjectUuidPK(this.editData.getId()), selector);
			prjId = budgetInfo.getCurProject().getId().toString();
			periodId = budgetInfo.getFdcPeriod().getId().toString();
		}else if(this.editData!=null&&this.editData.getId()==null){
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select FID from T_FNC_FDCBudgetPeriod  where FYear = ? and FMonth =?  ");
			builder.addParam(new Integer(getSpValue(spYear)));
			builder.addParam(new Integer(getSpValue(spMonth)));
			IRowSet rowSet = builder.executeQuery();
			if(rowSet.next()){
				periodId = rowSet.getString("FID");
			}
			prjId = this.editData.getCurProject().getId()==null?null:this.editData.getCurProject().getId().toString();
		}
		if(budgetInfo!=null||(periodId!=null&&prjId!=null)){
			//问题:部门月度申报表提交审批后,数据同步到项目月度申报表中,在项目月度表审批时要如果有当月的部门表未审批,则有提示.此点系统已做到。
			//但是如果是在工作流中审批,是不能出现提示信息的(基础那边不支持在工作流中有信息提示)。故需要将上述逻辑放到项目月度申报表提交之时  by Cassiel_peng 2009-10-20
			FilterInfo filter=new FilterInfo();
			filter.appendFilterItem("curProject.id",prjId );
			filter.appendFilterItem("fdcPeriod.id", periodId );
			filter.appendFilterItem("state", FDCBillStateEnum.SAVED);
			filter.appendFilterItem("state", FDCBillStateEnum.SUBMITTED);
			filter.appendFilterItem("state", FDCBillStateEnum.AUDITTING);
			filter.setMaskString(" #0 and #1 and ( #2 or #3 or #4 )");
			boolean isExist = FDCDepMonBudgetAcctFactory.getRemoteInstance().exists(filter);
			if(isExist){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.EXISTUNAUDITTEDDEPMON);
			}
		}
		
		super.actionSubmit_actionPerformed(e);
		
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if(isFromWorkflow!=null 
    			&& isFromWorkflow.toString().equals("true") 
    			&& getOprtState().equals(STATUS_EDIT)){
    		actionRemove.setEnabled(false);
    	}
	}
//	在“提交”新版本时，需要按“行”检查修改后的本月计划数是否小于关联本计划的付款申请单金额
	protected boolean checkCanSubmit() throws Exception {
		boolean canSubmit = super.checkCanSubmit();
		String tmpInfo = null;
		String rowNumbers = null;
		if(canSubmit){
			//取系统参数
			FDCBudgetParam params=null;
			try {
				params=FDCBudgetParam.getInstance(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString());
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			
			Map curPeriodPayMap=null;
			String projectId = this.editData.getCurProject().getId().toString();
			FDCBudgetPeriodInfo CurPeriod = getPeriod();
			try {
				curPeriodPayMap=FDCBudgetAcctHelper.getCurPeriodRequestedAmt(null, projectId, CurPeriod);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			
			for(int i=0;i<getDetailTable().getRowCount();i++){
	    		IRow row=getDetailTable().getRow(i);
	    		//在明细行上比较本月计划数、付款申请单金额
	    		if(row.getUserObject() instanceof FDCBudgetAcctEntryInfo){
	    			FDCBudgetAcctEntryInfo entry=(FDCBudgetAcctEntryInfo)row.getUserObject();
	    			if(entry.getContractBill() != null){	
	    				String rowKey = entry.getCostAccount().getId().toString() + entry.getContractBill().getId().toString();
	    				String colKey = getMonthPayKey(getCurrentMonth());
	    				tmpInfo = "第 " + (i+1) + " 行的本月计划已小于付款申请单金额";
	    				BigDecimal monthPay = (BigDecimal)row.getCell(colKey).getValue();
	    				if(monthPay == null){
	    					monthPay = FDCHelper.ZERO;
	    				}
	    				BigDecimal reqPay = (BigDecimal)curPeriodPayMap.get(rowKey);
	    				if(reqPay == null){
	    					reqPay = FDCHelper.ZERO;
	    				}
	    				if(reqPay.compareTo(monthPay)>0)
	    				{
	    					//严格控制
	    					if (params!=null && params.isStrictCtrl()) {
	    							canSubmit = false;
	    							MsgBox.showWarning(this, tmpInfo);
	    							this.abort();
	    					}else{
	    						if(rowNumbers == null){
	    							rowNumbers = String.valueOf(i+1);
	    						}else{
	    							rowNumbers = rowNumbers.concat("、".concat(String.valueOf(i+1)));	    							
	    						}
	    					}
	    				}
	    			}
	    		}
			}
			if(rowNumbers != null){
				tmpInfo = "第 " + rowNumbers + " 行的本月计划已小于付款申请单金额";
				if (MsgBox.showConfirm2(this,tmpInfo) == MsgBox.CANCEL) {
					canSubmit = false;
					this.abort();
				}
			}
		}
		return canSubmit;
	}
	
	private void setColorByVersionDiff(){
		BigDecimal preVer = FDCHelper.toBigDecimal(txtVerNumber.getText());
		if(preVer.compareTo(FDCHelper.toBigDecimal("1.0")) == 0){
			return;
		}
		try {
			getPreVerMap(preVer);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int currentMonth = getCurrentMonth();
		String costAccountId=null;
		for(int i=0;i<getDetailTable().getRowCount();i++){
    		IRow row=getDetailTable().getRow(i);
    		String contractBillId=null;
    		//明细行上才会有科目实体
    		if(row.getUserObject() instanceof CostAccountInfo){
    			CostAccountInfo ca = (CostAccountInfo)row.getUserObject();
    			costAccountId = ca.getId().toString();
    			continue;
    		}else if(row.getUserObject() instanceof FDCBudgetAcctEntryInfo){
    			FDCBudgetAcctEntryInfo entry=(FDCBudgetAcctEntryInfo)row.getUserObject();
    			if(entry.getContractBill() == null){
    				costAccountId = null;
    				continue;
    			}else{
    				contractBillId=entry.getContractBill().getId().toString();
    			}
    		}else if(isUnSettlecContractRow(row)){
    			contractBillId="nullid";
			}else{
				continue;
			}
    		if(costAccountId == null || contractBillId == null)	continue;
    		
			String sKey=costAccountId.concat("|".concat(contractBillId.concat("|")));
			for(int j=currentMonth;j<currentMonth+3;j++){
				String monthCostKey=getMonthCostKey(j);
				String monthPayKey=getMonthPayKey(j);
				String costKey=sKey.concat(monthCostKey);
				String payKey=sKey.concat(monthPayKey);
				BigDecimal cost = (BigDecimal)row.getCell(monthCostKey).getValue();
				BigDecimal pay = (BigDecimal)row.getCell(monthPayKey).getValue();
				BigDecimal preCost = FDCHelper.ZERO;
				BigDecimal prePay = FDCHelper.ZERO;
				if(preVerMap.size()>0 && preVerMap.containsKey(costKey)){
					preCost = (BigDecimal)preVerMap.get(costKey);
				}
				if(preVerMap.size()>0 && preVerMap.containsKey(payKey)){
					prePay = (BigDecimal)preVerMap.get(payKey);
				}
				
				if(preVerMap.size()<1 || (cost != null && cost.compareTo(preCost)!=0)){
					row.getCell(monthCostKey).getStyleAttributes().setFontColor(Color.RED);
				}
				if(preVerMap.size()<1 || (pay != null && pay.compareTo(prePay)!=0)){
					row.getCell(monthPayKey).getStyleAttributes().setFontColor(Color.RED);
				}
			}
    	}
	}
	private void getPreVerMap(BigDecimal preVer)throws BOSException, SQLException {
		String projectId = this.editData.getCurProject().getId().toString();
		String periodId = this.editData.getFdcPeriod().getId().toString();
		String sKey = null;
		String sMonth = null;
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select t1.FCostAccountId,t1.FContractBillId,t0.FMonth,t0.FCost,t0.FAmount from T_FNC_FDCMonthBudgetAcctItem t0 ");
		builder.appendSql("inner join t_fnc_fdcmonthbudgetacctentry t1 on t1.fid=t0.fentryid ");
		builder.appendSql("inner join t_fnc_fdcmonthbudgetacct t2 on t2.fid=t1.fparentid ");
		builder.appendSql("where t2.FProjectId=? and t2.FfdcPeriodId=? and t2.fvernumber=?-0.1");
		builder.appendSql("and t1.fitemtype='1CONTRACT' ");
		builder.appendSql("union ");
		builder.appendSql("select t1.FCostAccountId,'nullid' as FContractBillId,t0.FMonth,sum(t0.FCost) as FCost,sum(t0.FAmount) as FAmount from T_FNC_FDCMonthBudgetAcctItem t0 ");
		builder.appendSql("inner join t_fnc_fdcmonthbudgetacctentry t1 on t1.fid=t0.fentryid ");
		builder.appendSql("inner join t_fnc_fdcmonthbudgetacct t2 on t2.fid=t1.fparentid ");
		builder.appendSql("where t2.FProjectId=? and t2.FfdcPeriodId=? and t2.fvernumber=?-0.1 ");
		builder.appendSql("and t1.fitemtype='9UNSETTLEDCONTRACT' ");
		builder.appendSql("group by t1.FCostAccountId,t0.FMonth ");
		
    	builder.addParam(projectId);
    	builder.addParam(periodId);
    	builder.addParam(preVer);
    	builder.addParam(projectId);
    	builder.addParam(periodId);
    	builder.addParam(preVer);
    	IRowSet rowSet=builder.executeQuery();
    	preVerMap.clear();
    	try {
    		while (rowSet.next()) {
				sKey = rowSet.getString("FCostAccountId").concat("|".concat(rowSet.getString("FContractBillId").concat("|")));
				sMonth = String.valueOf(rowSet.getInt("FMonth"));
				BigDecimal preValue = rowSet.getBigDecimal("FCost");
				preVerMap.put(sKey.concat("month_cost".concat(sMonth)),preValue == null?FDCHelper.ZERO:preValue);
				preValue = rowSet.getBigDecimal("FAmount");
				preVerMap.put(sKey.concat("month_pay".concat(sMonth)),preValue == null?FDCHelper.ZERO:preValue);
			}
		}catch(SQLException e){
    		throw new BOSException(e);
    	}    	
	}
	
	/*--------月度付款计划申报表中增加定位查找功能,可快速查到'合同名称"和科目等   by ling_peng 2009-6-18--------*/
	
	protected void initWorkButton() {
		super.initWorkButton();
		btnQuery.setText("过滤");
		menuItemQuery.setText("过滤");
		if(menuItemCopyLine!=null){
			menuItemCopyLine.setVisible(false);
			menuItemCopyLine.setEnabled(false);
		}
		this.menuItemLocate.setIcon(EASResource.getIcon("imgTbtn_speedgoto"));
	}
	
	
	    private boolean hasQyeryPK = false;
	    boolean isFirstFind = true;
	    private int searcheRowCount=0;
	    private  String searchText=null;
	    private  boolean isMatch=false;
	    private  FindListEvent preFindListEvent=null;
	    private  String  propertyName=null;
	    //定位框
	    private FindDialog findDialog = null;
	    private static String locateFirst = "Msg_LocateFirst";
	    private static String locateLast = "Msg_LocateLast";
	    //添加等待提示框
	    KDDialog kddialog=null;
	    protected String[] keyFieldNames;
	    //分录id
	    protected String   subKeyFieldName;
	    //初始化辅助类
	    ListUiHelper listUiHelper=null;
	    
		 public void actionLocate_actionPerformed(ActionEvent e) throws Exception
		    {
			 	/*if(tblMain.getSelectManager().get()==null){
			 		MsgBox.showWarning("请先选中一行");
			 		return;
			 	}*/
			 
		        searcheRowCount=0;
		        searchText=null;
		        preFindListEvent=null;
		        Window win = SwingUtilities.getWindowAncestor(this);
		        List FindPropertyName = new ArrayList();
		        for (int i = 0; i < getLocateNames().length; i++)
		        {
		            if (tblMain.getColumn(getLocateNames()[i]) != null)
		            {
		            	//在序时簿中应该是tblMain.getHeadRow(0)，不过在此处应用场景之下应该是tblMain.getHeadRow(1)。
		                ListFind cEnum = new ListFind(getLocateNames()[i], tblMain.getHeadRow(1).getCell(getLocateNames()[i])
		                        .getValue().toString());
		                FindPropertyName.add(cEnum);
		            }
		        }

		        if (FindPropertyName.size() == 0)
		        {
		            return;
		        }

		            if (win instanceof Frame)
		            {
		                findDialog = new FindDialog((Frame) win, "", FindPropertyName, true);
		            }
		            else
		            {
		                findDialog = new FindDialog((Dialog) win, "", FindPropertyName, true);
		            }
		            findDialog.addFindListListener(new IFindListListener()
		            {
		                public void FindNext(FindListEvent e)
		                {
		                    locate(e);
		                }

		                /**
		                 * FindClose
		                 *
		                 * @param e
		                 *            FindListEvent
		                 */
		                public void FindClose(FindListEvent e)
		                {
		                    findDialog.dispose();
		                    findDialog = null;
		                    searcheRowCount=0;
		                    searchText=null;
		                    isMatch=false;
		                }
		            }

		            );
		        findDialog.setLocation(600, 100);
		        findDialog.show();
		    }
		     /**
			 * 快速模糊定位
			 */
			protected String[] getLocateNames() {
				String[] locateNames = new String[5];
				locateNames[0]="conNumber";
				locateNames[1]="conName";
				locateNames[2]="partB";
				locateNames[3]="acctNumber";
				locateNames[4]="acctName";
				return locateNames;
			} 
	
    protected void locateForNotIdList(FindListEvent e)
    {
        int currentRow = 0;
        if (this.tblMain.getSelectManager().get() != null)
        {
            currentRow = this.tblMain.getSelectManager().get().getBeginRow();
        }
        int i = 0;
            if (e.getFindDeration() == FindListEvent.Down_Find)
            {
                i = currentRow + 1;
            }
            else
            {
                i = currentRow;
            }

        IRow row = tblMain.getRow(i);
        showMsgNoIdList(row,e);
        while (row != null)
        {
        	row = tblMain.getRow(i);
            // 如果碰到融合单据，则不处理。
            if (i!=-1&&i!=tblMain.getRowCount()&&row.getCell(e.getPropertyName()).getValue() == null)
            {
            	if (e.getFindDeration() == FindListEvent.Down_Find)
            		i++;
            	else
            		i--;
                continue;
            }
            int curSelectIndex=this.tblMain.getSelectManager().getActiveRowIndex();
            if (curSelectIndex==i)
            {
            	if (e.getFindDeration() == FindListEvent.Down_Find)
            		i++;
            	else
            		i--;
            	 continue;
            }
            row = tblMain.getRow(i);
            if (row==null)
            {
            	showMsgNoIdList(row,e);
            	break;
            }
            ICell cell=tblMain.getRow(i).getCell(e.getPropertyName());
            Object cellValue = cell==null?null:cell.getValue();
            String Search =cellValue==null?null:cellValue.toString().replace('!', '.');
            
            if (Search != null
                    && StringUtility.isMatch(Search, e.getSearch(), e.isIsMatch(), Pattern.CASE_INSENSITIVE))
            {
                isFirstFind = false;
                searcheRowCount++;
                this.tblMain.getSelectManager().select(i, 0, i, tblMain.getColumnCount());
                //tblMain.getRow(i).getStyleAttributes().setBackground(new Color(223,239,245));
                tblMain.getLayoutManager().scrollRowToShow(i);
                break;
            }
            if (e.getFindDeration() == FindListEvent.Down_Find){
            	i++;
            }
        	else{
        		i--;
        	}
        }

    }
    /**
     * 找不到匹配记录时给予提示
     */
    private void showMsgNoIdList(IRow row,FindListEvent e)
    {
    	if (row == null)
        {
        	String hint="";
        	if (e.getFindDeration() == FindListEvent.Down_Find)
        	{
        		if (searcheRowCount==0)
        		{
        		  hint=locateLast;
        		}else
        		{
        		  hint="Msg_LocateFirst_end";
        		}
        	}else
        	{
        		if (searcheRowCount==0)
        		{
        			hint=locateFirst;
        		}else
        		{
        			hint="Msg_LocateFirst_end";
        		}
        	}
        	String msg=EASResource.getString(FrameWorkClientUtils.strResource + hint);
        	if (searcheRowCount!=0)
        	{
        		Object[] objs = new Object[]{ new Integer(searcheRowCount)};
        		msg = MessageFormat.format(msg, objs);
        	}
            MsgBox.showInfo(this, msg);
            this.findDialog.show();
        }
    }
	

    //是否使用虚模式预取,在定位的时候不需要预取
	private boolean bPreFetch = true;
	
	  public boolean isHasQyeryPK()
	    {
	        return hasQyeryPK;
	    }
	
    // 定位List表格位置。
    protected void locate(FindListEvent e)
    {
        boolean searchResult = false;
        int currentRow = 0;
        // 总行数
        int RowCount = 0;

        // 如果是虚模式则处理。
        // if (this.isHasQyeryPK() && !this.isLessData()) {
        int curSelectIndex=this.tblMain.getSelectManager().getActiveRowIndex();
        if (curSelectIndex<0&&(this.tblMain.getRowCount()>0||this.tblMain.getRowCount3()>0))
        {
        	this.tblMain.getSelectManager().select(0,0);
        	curSelectIndex=0;
        }
        if (curSelectIndex<0||this.tblMain.getRowCount()==0)
        {
        	MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateLast));
        	return;
        }
        if ((preFindListEvent!=null&&e.getFindDeration()!=preFindListEvent.getFindDeration())
        	||(searchText!=null&&!searchText.equals(e.getSearch()))
        	||(propertyName!=null&&!e.getPropertyName().equals(propertyName))
        	||isMatch!=e.isIsMatch())
        {
        	searcheRowCount=0;
        	Object cellValue= null;
        	try {
        		bPreFetch = false;
        		cellValue = tblMain.getRow(curSelectIndex).getCell(e.getPropertyName()).getValue();
        	}
        	finally {
        		bPreFetch = true;
        	}
          String selectText = cellValue==null?null:cellValue.toString();
          propertyName=e.getPropertyName();
          if (selectText != null&&StringUtility.isMatch(selectText, e.getSearch(), e.isIsMatch(), Pattern.CASE_INSENSITIVE))
          {
              searcheRowCount=searcheRowCount+1;
          }
        }
        isMatch=e.isIsMatch();
        preFindListEvent=e;
        searchText=e.getSearch();
        propertyName=e.getPropertyName();
        if (this.isHasQyeryPK())
        {
            RowCount = tblMain.getBody().size();
        }
        else
        {
            locateForNotIdList(e);
        }

        if (this.tblMain.getSelectManager().get() != null)
        {
            currentRow = this.tblMain.getSelectManager().get().getBeginRow();
        }
        int i = 0;
        if (e.getFindDeration() == FindListEvent.Down_Find)
        {
           i = currentRow + 1;
        }
        else
        {
            i = currentRow;
        }


        // 如果没数据则不进行处理。
        if (RowCount == 0)
        {
            return;
        }

        if (e.getFindDeration() == FindListEvent.Down_Find)
        {
            if (i == RowCount)
            {
            	if (searcheRowCount==0)
            	{
                  MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateLast));
            	}
                else
                {
            		String msg=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_LocateLast_end");
            		Object[] objs = new Object[]{ new Integer(searcheRowCount)};
            		msg = MessageFormat.format(msg, objs);
            		MsgBox.showInfo(this, msg);
            		//searcheRowCount=0;
                }
                this.findDialog.show();
            }
            else
            {
                for (; i < RowCount; i++)
                {
                	IRow row = null;
                	try {
                		bPreFetch = false;
                		row = tblMain.getRow2(i);
                	}
                	finally {
                		bPreFetch = true;
                	}
                	ICell cell=(null == row ) ? null : row.getCell(e.getPropertyName());
                    Object o = cell==null?null:cell.getValue();
                    String Search = o == null ? null : o.toString();
                    // 如果碰到融合单据，则不处理。
                    if (Search == null)
                    {
                        if (i == RowCount - 1)
                        {
                            MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateLast));
                            this.findDialog.show();
                        }
                        continue;
                    }

                    if (Search != null
                            && StringUtility.isMatch(Search, e.getSearch(), e.isIsMatch(), Pattern.CASE_INSENSITIVE))
                    {
                        searchResult = true;
                        isFirstFind = false;
                        searcheRowCount++;
                        this.tblMain.getSelectManager().select(i, 0);
                        tblMain.getLayoutManager().scrollRowToShow(i);
                        break;
                    }
                    // 没有找到就不会选中。
                    if (i == RowCount - 1)
                    {
                    	if (searcheRowCount==0)
                    	{
                    		MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateLast));
                    	}else
                    	{
                    		String msg=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_LocateLast_end");
                    		Object[] objs = new Object[]{ new Integer(searcheRowCount)};
                    		msg = MessageFormat.format(msg, objs);
                    		MsgBox.showInfo(this, msg);
                    		//searcheRowCount=0;
                    	}
                        this.findDialog.show();
                    }
                }
            }
        }
        else
        {
            if (i == 0)
            {
            	if (searcheRowCount==0)
            	{
            		MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateFirst));
            	}else
            	{
            		String msg=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_LocateFirst_end");
            		Object[] objs = new Object[]{ new Integer(searcheRowCount)};
            		msg = MessageFormat.format(msg, objs);
            		MsgBox.showInfo(this, msg);
            		//searcheRowCount=0;
            	}
                this.findDialog.show();
            }

            for (; i > 0; i--)
            {
            	ICell cell=(null == tblMain.getRow2(i - 1)) ? null : tblMain.getRow2(i - 1).getCell(e.getPropertyName());
            	Object o = cell==null?null:cell.getValue();
                String Search = o == null ? null : o.toString();
                // 如果碰到融合单据，则不处理。
                if (Search == null)
                {
                    if (i == 1)
                    {
                        MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateFirst));
                        this.findDialog.show();
                    }
                    continue;
                }


                if (Search != null
                        && StringUtility.isMatch(Search, e.getSearch(), e.isIsMatch(), Pattern.CASE_INSENSITIVE))
                {
                    searchResult = true;
                    isFirstFind = false;
                    searcheRowCount++;
                    this.tblMain.getSelectManager().select(i - 1, 0);
                    tblMain.getLayoutManager().scrollRowToShow(i - 1);
                    break;
                }
                // 没有找到就不会选中。
                if (i == 1)
                {
                	if (searcheRowCount==0)
                	{
                		MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateFirst));
                	}else
                	{
                		String msg=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_LocateFirst_end");
                		Object[] objs = new Object[]{ new Integer(searcheRowCount)};
                		msg = MessageFormat.format(msg, objs);
                		MsgBox.showInfo(this, msg);
                		//searcheRowCount=0;
                	}

                    this.findDialog.show();
                }
            }
        }
    }
    /**
	 * 描述：过滤界面
	 * @throws Exception 
	 */
    protected void showFDCBudgetAcctDeptFilterUI() throws Exception{
    	if(budgetAcctFilterUI == null){
    		budgetAcctFilterUI = new FDCDepMonBudgetAcctFilterUI();
    	}
    	Map context=new UIContext(this);
    	Map param = budgetAcctFilterUI.showUI(context);
    	getUIContext().putAll(param);
    	fillTable();
    }
    
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
    	showFDCBudgetAcctDeptFilterUI();
    	this.btnShowBlankRow.doClick();
    }
    public boolean destroyWindow() {
    	if(budgetAcctFilterUI!=null){
    		budgetAcctFilterUI.clear();
    	}
    	return super.destroyWindow();
    }
    
	protected void verifyInputForSave() throws Exception {
		if(this.txtNumber.isEnabled()){
			if(this.txtNumber.getText() == null || this.txtNumber.getText().length() == 0){
				FDCMsgBox.showWarning("编码不能为空！");
				abort();
			}
		}
	}
    
    
}

