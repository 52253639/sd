/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;

/**
 * output class name
 */
public class FullDyWillHappenInfoUI extends AbstractFullDyWillHappenInfoUI
{
    private static final Logger logger = CoreUIObject.getLogger(FullDyWillHappenInfoUI.class);
    
    /**
     * output class constructor
     */
    public FullDyWillHappenInfoUI() throws Exception
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
    	FDCHelper.formatTableNumber(getMainTable(), new String[]{"workload", "price", "amt"});
    	//候艳的bug要求显示２位 2008-02-51 20:42:42
//    	getMainTable().getColumn("price").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	Map dataMap = (Map)getUIContext().get("dataMap");
    	String acctId = (String)dataMap.get("acctId");
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select entry.FCostAcctName, entry.FWorkload, entry.FUnit, entry.FPrice, entry.FCostAmount, prod.FName_l2 product");
    	builder.appendSql(" from T_AIM_IntendingCostEntry entry inner join T_AIM_DynamicCost head on head.FID = entry.FParentID left outer join T_FDC_ProductType prod on entry.FProductID = prod.fid");
    	builder.appendSql(" where head.FAccountID = ");
    	builder.appendParam(acctId);
    	builder.appendSql(" order by entry.fcostacctName");
    	ResultSet set = builder.executeQuery();
    	
    	BigDecimal wlTotal = FDCHelper.ZERO;
    	BigDecimal amtTotal = FDCHelper.ZERO;
    	
    	boolean b = false;
    	
    	while(set.next()) {
    		b = true;
    		String name = set.getString("FCostAcctName");
    		BigDecimal workload = set.getBigDecimal("FWorkload");
    		String unit = set.getString("FUnit");
    		BigDecimal price = set.getBigDecimal("FPrice");
    		BigDecimal amt = set.getBigDecimal("FCostAmount");
    		String product = set.getString("product");
    		
    		IRow row = getMainTable().addRow();
    		
    		row.getCell("name").setValue(name);
    		row.getCell("workload").setValue(workload);
    		row.getCell("unit").setValue(unit);
    		row.getCell("price").setValue(price);
    		row.getCell("amt").setValue(amt);
    		row.getCell("product").setValue(product);
    		
    		if(workload != null) {
    			wlTotal = wlTotal.add(workload);
    		}
    		
    		if(amt != null) {
    			amtTotal = amtTotal.add(amt);
    		}
    	}

    	if(b) {
    		
	    	IRow totalRow = getMainTable().addRow();
	    	totalRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
	    	totalRow.getCell("name").setValue("合计");
	    	totalRow.getCell("workload").setValue(wlTotal);
	    	totalRow.getCell("amt").setValue(amtTotal);
    	}
    	
    	getMainTable().getStyleAttributes().setLocked(true);
    	
    }
    
}