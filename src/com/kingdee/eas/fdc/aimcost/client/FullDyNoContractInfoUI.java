/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;

/**
 * output class name
 */
public class FullDyNoContractInfoUI extends AbstractFullDyNoContractInfoUI
{
    private static final Logger logger = CoreUIObject.getLogger(FullDyNoContractInfoUI.class);
    
    /**
     * output class constructor
     */
    public FullDyNoContractInfoUI() throws Exception
    {
        super();
    }
    private KDTable getMainTable() {
    	return kDTable1;
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	getMainTable().checkParsed();
    	FDCTableHelper.setColumnMoveable(getMainTable(), true);
    	FDCHelper.formatTableNumber(getMainTable(), new String[]{"amt", "splitAmt"});
    	
    	
    	Map dataMap = (Map)getUIContext().get("dataMap");
    	String acctId = (String)dataMap.get("acctId");
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select wt.FNumber, wt.FName, payBill.FActualPayAmount payAmt, payBill.FPayDate, sum(entry.FAmount) splitAmt, head.FCreateTime splitDate, u.FName_l2 splitCreator");
    	builder.appendSql(" from T_FNC_PaymentSplit head inner join T_FNC_PaymentSplitEntry entry on head.fid = entry.FParentID inner join T_CAS_PaymentBill payBill on head.FPaymentBillID = payBill.FID inner join T_CON_ContractWithoutText wt on payBill.FContractBillID = wt.fid left outer join t_pm_user u on head.FCreatorID = u.FID");
    	builder.appendSql(" where entry.FIsLeaf = 1 and head.fstate<>'9INVALID' and entry.FCostAccountID = " );
    	builder.appendParam(acctId);
    	builder.appendSql(" group by wt.FNumber, wt.FName, payBill.FActualPayAmount, payBill.FPayDate, head.FCreateTime, u.FName_l2 order by wt.fnumber");
    	
    	ResultSet set = builder.executeQuery();
    	
    	BigDecimal amtTotal = FDCHelper.ZERO;
    	BigDecimal splitAmtTotal = FDCHelper.ZERO;
    	boolean b = false;
    	
    	while(set.next()) {
    		b = true;
    		String number = set.getString("FNumber");
    		String name = set.getString("FName");
    		BigDecimal amount = set.getBigDecimal("payAmt");
    		Date payDate = set.getDate("FPayDate");
    		String strPayDate = null;
    		if(payDate != null) {
    			strPayDate = FDCClientHelper.formateDate(payDate);
    		}
    		BigDecimal splitAmt = set.getBigDecimal("splitAmt");
    		Date splitDate = set.getDate("splitDate");
    		String strSplitDate = null;
    		if(splitDate != null) {
    			strSplitDate = FDCClientHelper.formateDate(splitDate);
    		}
    		String splitCreator = set.getString("splitCreator");
    		
    		IRow row = getMainTable().addRow();
    		
    		row.getCell("number").setValue(number);
    		row.getCell("name").setValue(name);
    		row.getCell("amt").setValue(amount);
    		row.getCell("payDate").setValue(strPayDate);
    		row.getCell("splitAmt").setValue(splitAmt);
    		row.getCell("createDate").setValue(strSplitDate);
    		row.getCell("creator").setValue(splitCreator);
    		
    		if(amount != null) {
    			amtTotal = amtTotal.add(amount);
    		}
    		
    		if(splitAmt != null)  {
    			splitAmtTotal = splitAmtTotal.add(splitAmt);
    		}
    			
    		
    	}
    	if(b) {
	    	IRow totalRow = getMainTable().addRow();
	    	totalRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
	    	totalRow.getCell("number").setValue("ºÏ¼Æ");
	    	totalRow.getCell("amt").setValue(amtTotal);
	    	totalRow.getCell("splitAmt").setValue(splitAmtTotal);
    	}
    	getMainTable().getStyleAttributes().setLocked(true);
    }
    

}