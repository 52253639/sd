/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;

/**
 * output class name
 */
public class FullDyChangeInfoUI extends AbstractFullDyChangeInfoUI
{
    private static final Logger logger = CoreUIObject.getLogger(FullDyChangeInfoUI.class);
    
    /**
     * output class constructor
     */
    public FullDyChangeInfoUI() throws Exception
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
    	FDCHelper.formatTableNumber(getMainTable(), new String[]{"amt","splitAmt"});
    	
    	Map dataMap = (Map)getUIContext().get("dataMap");
    	String acctId = (String)dataMap.get("acctId");
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select distinct prod.fname_l2 productName,chg.FNumber, chg.FName, cty.FName_l2 typeName, entry.famount FSplitAmt, chg.FAmount, chg.FBalanceAmount,chg.FHasSettled, con.FNumber conNumber, con.FName conName");
    	builder.appendSql(" from T_CON_ConChangeSplit head inner join T_CON_ConChangeSplitEntry entry on head.fid = entry.FParentID inner join T_CON_ContractChangeBill chg on head.FContractChangeID = chg.fid inner join T_FDC_ChangeType cty on chg.FChangeTypeID = cty.fid inner join T_Con_ContractBill con on chg.FContractBillID = con.FID");
    	builder.appendSql(" left outer join T_FDC_ProductType prod on prod.fid=entry.fproductid");
    	builder.appendSql(" where entry.FCostAccountID = ");
    	builder.appendParam(acctId);
//    	builder.appendSql(" And (entry.fproductid Is Not Null Or (entry.fproductid Is Null And (fsplittype Is Null Or fsplittype<>'PRODSPLIT')))");
    	builder.appendSql(" and entry.fisleaf=1 and head.fstate<>'9INVALID' ");
    	builder.appendSql(" order by chg.fnumber");
    	ResultSet set = builder.executeQuery();
    	BigDecimal amtTotal = FDCHelper.ZERO;
    	BigDecimal splitAmtTotal = FDCHelper.ZERO;
    	boolean b = false;
    	int beginRow = 0;
		int endRow = 0;
		String oldNumber = null;
		KDTMergeManager mm = getMainTable().getMergeManager();
		//以下5列按合同进行行融合
		int numberCol = getMainTable().getColumnIndex("number");
		int nameCol = getMainTable().getColumnIndex("name");
		int typeCol = getMainTable().getColumnIndex("type");
		int amtCol = getMainTable().getColumnIndex("amt");
		int conNumCol = getMainTable().getColumnIndex("conNumber");
		int conNameCol = getMainTable().getColumnIndex("conName");
    	while(set.next()) {
    		b = true;
    		String number = set.getString("FNumber");
    		String name = set.getString("FName");
    		String typeName = set.getString("typeName");
    		BigDecimal amount = set.getBigDecimal("FAmount");
    		if(set.getBoolean("FHasSettled")){
    			amount=set.getBigDecimal("FBalanceAmount");
    		}
    		BigDecimal splitAmt = set.getBigDecimal("FSplitAmt");
    		String conNumber = set.getString("conNumber");
    		String conName = set.getString("conName");
    		String productName = set.getString("productName");
    		
    		IRow row = getMainTable().addRow();
    		
    		row.getCell("number").setValue(number);
    		row.getCell("name").setValue(name);
    		row.getCell("type").setValue(typeName);
    		row.getCell("amt").setValue(amount);
    		//增加拆分金额列
    		row.getCell("splitAmt").setValue(splitAmt);
    		row.getCell("conNumber").setValue(conNumber);
    		row.getCell("conName").setValue(conName);
    		row.getCell("product").setValue(productName);
    		
    		if(amount != null) {
    			amtTotal = amtTotal.add(amount);
    		}
    		if(splitAmt != null) {
    			splitAmtTotal = splitAmtTotal.add(splitAmt);
    		}
    		//按变更进行行融合
			if(!number.equals(oldNumber)){
				endRow = getMainTable().getRowCount()-1-1;//索引从0开始，故减1；编码不一样时，已经是另外的变更了，故再减1
				if(endRow > beginRow){
					mm.mergeBlock(beginRow,typeCol,endRow,typeCol,KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(beginRow,numberCol,endRow,numberCol,KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(beginRow,nameCol,endRow,nameCol,KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(beginRow,amtCol,endRow,amtCol,KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(beginRow,conNumCol,endRow,conNumCol,KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(beginRow,conNameCol,endRow,conNameCol,KDTMergeManager.SPECIFY_MERGE);
				}
				
				oldNumber = number;
				beginRow = getMainTable().getRowCount()-1;//索引从0开始，故减1
			}
    	}
    	//如果最后几行需要融合，上面逻辑无法处理，在此处理
		endRow = getMainTable().getRowCount()-1;//索引从0开始，故减1
		if(endRow > beginRow){
			mm.mergeBlock(beginRow,typeCol,endRow,typeCol,KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(beginRow,numberCol,endRow,numberCol,KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(beginRow,nameCol,endRow,nameCol,KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(beginRow,amtCol,endRow,amtCol,KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(beginRow,conNumCol,endRow,conNumCol,KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(beginRow,conNameCol,endRow,conNameCol,KDTMergeManager.SPECIFY_MERGE);
		}
    	if(b) {
    		IRow totalRow = getMainTable().addRow();
    		totalRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
	    	totalRow.getCell("number").setValue("合计");
	    	totalRow.getCell("amt").setValue(amtTotal);
	    	totalRow.getCell("splitAmt").setValue(splitAmtTotal);
    	}
    	getMainTable().getStyleAttributes().setLocked(true);
    }
 }