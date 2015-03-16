/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;

/**
 * output class name
 */
public class FullDyCostInfoUI extends AbstractFullDyCostInfoUI
{
    private static final Logger logger = CoreUIObject.getLogger(FullDyCostInfoUI.class);
    
    /**
     * output class constructor
     */
    public FullDyCostInfoUI() throws Exception
    {
        super();
    }

    private KDTable getMainTable() {
    	return kDTable1;
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	//取变更类型集合
    	ChangeTypeCollection changeTypes = null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.getSorter().add(new SorterItemInfo("number"));
		view.getSelector().add("id");
		view.getSelector().add("name");
		changeTypes = ChangeTypeFactory.getRemoteInstance()
				.getChangeTypeCollection(view);
    	getMainTable().checkParsed();
    	FDCTableHelper.setColumnMoveable(getMainTable(), true);
    	FDCHelper.formatTableNumber(getMainTable(), new String[]{"amt"});
    	
    	Map map = (Map)getUIContext().get("dataMap");
    	
    	BigDecimal no_set_amt = (BigDecimal)map.get("no_set_amt");
//    	BigDecimal no_set_design = (BigDecimal)map.get("no_set_design" );
//    	BigDecimal no_set_sign = (BigDecimal)map.get("no_set_sign");
//    	BigDecimal no_set_other = (BigDecimal)map.get("no_set_other");
    	BigDecimal no_set_total = (BigDecimal)map.get("no_set_total");
		
    	BigDecimal set_amt = (BigDecimal)map.get("set_amt");
//    	BigDecimal set_design = (BigDecimal)map.get("set_design");
//    	BigDecimal set_sign = (BigDecimal)map.get("set_sign");
//    	BigDecimal set_other = (BigDecimal)map.get("set_other");
    	BigDecimal set_total = (BigDecimal)map.get("set_total");
		
    	BigDecimal no_text = (BigDecimal)map.get("no_text");
    	BigDecimal will_happen = (BigDecimal)map.get("will_happen");
		
    	
    	IRow row = getMainTable().addRow();
    	
    	row.getCell("cost").setValue("已结算合同");
    	row.getCell("type").setValue("合同金额");
    	row.getCell("amt").setValue(set_amt);
    	//根据变更类型动态增加列 已结算
    	BigDecimal settChange=FDCHelper.ZERO;
    	for(int i =0;i<changeTypes.size();i++){
    		row = getMainTable().addRow();
    		BigDecimal set_type = (BigDecimal)map.get(changeTypes.get(i).getId().toString()+"Settle");
        	row.getCell("cost").setValue("已结算合同");
        	row.getCell("type").setValue(changeTypes.get(i).getName());
        	row.getCell("amt").setValue(set_type);
        	settChange=FDCHelper.add(settChange, set_type);
    	}
    	
    	row = getMainTable().addRow();
    	row.getCell("cost").setValue("已结算合同");
    	row.getCell("type").setValue("结算调整");
    	BigDecimal settleAdjust = FDCHelper.subtract(FDCHelper.subtract(set_total, settChange), set_amt);
    	if(settleAdjust!=null&&settleAdjust.signum()==0){
    		settleAdjust=null;
    	}
		row.getCell("amt").setValue(settleAdjust);
    	
    	row = getMainTable().addRow();
    	row.getCell("cost").setValue("已结算合同");
    	row.getCell("type").setValue("合同小计");
    	row.getCell("amt").setValue(set_total);
    	

    	row = getMainTable().addRow();
    	row.getCell("cost").setValue("未结算合同");
    	row.getCell("type").setValue("合同金额");
    	row.getCell("amt").setValue(no_set_amt);
//    	根据变更类型动态增加列 未结算
    	for(int i =0;i<changeTypes.size();i++){
    		row = getMainTable().addRow();
    		BigDecimal no_set_type = (BigDecimal)map.get(changeTypes.get(i).getId().toString()+"NoSettle");
        	row.getCell("cost").setValue("未结算合同");
        	row.getCell("type").setValue(changeTypes.get(i).getName());
        	row.getCell("amt").setValue(no_set_type);
    	}
    	
    	row = getMainTable().addRow();
    	row.getCell("cost").setValue("未结算合同");
    	row.getCell("type").setValue("合同小计");
    	row.getCell("amt").setValue(no_set_total);
    	
    	row = getMainTable().addRow();
    	row.getCell("cost").setValue("非合同性成本");
    	row.getCell("amt").setValue(no_text);
    	
    	row = getMainTable().addRow();
    	row.getCell("cost").setValue("待发生成本");
    	row.getCell("amt").setValue(will_happen);
    	
    	BigDecimal total = FDCHelper.ZERO;
    	if(set_total != null) {
    		total = total.add(set_total);
    	}
    	if(no_set_total != null) {
    		total = total.add(no_set_total);
    	}
    	if(no_text != null) {
    		total = total.add(no_text);
    	}
    	if(will_happen != null) {
    		total = total.add(will_happen);
    	}
    	row = getMainTable().addRow();
    	row.getCell("cost").setValue("合计");
    	row.getCell("amt").setValue(total);
    	
    	getMainTable().getMergeManager().mergeBlock(0, 0, changeTypes.size()+2, 0);
    	getMainTable().getMergeManager().mergeBlock(changeTypes.size()+3, 0, (changeTypes.size()+3+changeTypes.size()+1), 0);
    	
    	getMainTable().getStyleAttributes().setLocked(true);
    }
}