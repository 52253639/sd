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
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.finance.client.PaymentBillEditUI;

/**
 * output class name
 */
public class FullDyPayInfoUI extends AbstractFullDyPayInfoUI
{
    private static final Logger logger = CoreUIObject.getLogger(FullDyPayInfoUI.class);
    
    /**
     * output class constructor
     */
    public FullDyPayInfoUI() throws Exception
    {
        super();
    }
    private KDTable getMainTable() {
    	return kDTable1;
    }
    protected void kDTable1_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	// 单击
		if (e.getClickCount() == 1) {
			// ...do nothing
		}
		// 双击
		else if (e.getClickCount() == 2) {
			viewPay();
		}
    }
    
    private void viewPay() throws Exception {
		
		int selectRows[] = KDTableUtil.getSelectedRows(getMainTable());
		if (selectRows.length ==1) {

			Object obj = getMainTable().getCell(selectRows[0], "id").getValue();
			if (obj != null) {
				String objId = obj.toString();
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, objId);
				IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL)
						.create(PaymentBillEditUI.class.getName(), uiContext,
								null, OprtState.VIEW);
				ui.show();
			}
		}
	}

    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	getMainTable().checkParsed();
    	getMainTable().getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    	FDCTableHelper.setColumnMoveable(getMainTable(), true);
    	FDCHelper.formatTableNumber(getMainTable(), new String[]{"payAmt","splitAmt"});
    	getMainTable().getColumn("id").getStyleAttributes().setHided(true);
    	
    	Map dataMap = (Map)getUIContext().get("dataMap");
    	String acctId = (String)dataMap.get("acctId");
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql(" select distinct(pay.FID) payId, pay.fnumber payNumber, con.FNumber number, con.FName name, pay.FLocalAmount amount, pay.FPayDate payDate, entry.FPayedAmt splitAmt  from T_FNC_PaymentSplitEntry entry \n");
    	builder.appendSql(" inner join T_FNC_PaymentSplit head on head.FID=entry.FParentID \n");
    	builder.appendSql(" inner join T_CAS_PaymentBill pay on pay.fid=head.FPaymentBillID \n");
    	builder.appendSql(" inner join T_Con_ContractBill con on con.FID = pay.FContractBillID \n");
    	builder.appendSql(" where entry.fisleaf=1 and head.fstate<>'9INVALID' and entry.FCostAccountID = \n");
    	builder.appendParam(acctId);
    	builder.appendSql(" order by con.fnumber,pay.fnumber ");
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
		int payNumberCol = getMainTable().getColumnIndex("name");
		int payDateCol = getMainTable().getColumnIndex("payDate");
//		int amountCol = getMainTable().getColumnIndex("payAmt");
//		int splitAmtCol = getMainTable().getColumnIndex("splitAmt");
		int idCol = getMainTable().getColumnIndex("id");
    	while(set.next()) {
    		b = true;
    		String number = set.getString("number");
    		String name = set.getString("name");
    		String payNumber = set.getString("payNumber");
    		String id = set.getString("payId");
    		String payDate = set.getString("payDate");
    		BigDecimal amount = set.getBigDecimal("amount");
    		BigDecimal splitAmt = set.getBigDecimal("splitAmt");
    		
    		IRow row = getMainTable().addRow();
    		row.getCell("number").setValue(number);
    		row.getCell("name").setValue(name);
    		row.getCell("payNumber").setValue(payNumber);
    		row.getCell("id").setValue(id);
    		if(payDate!=null){
    			payDate = payDate.substring(0, 10);
    		}
    		row.getCell("payDate").setValue(payDate);
    		row.getCell("payAmt").setValue(amount);
    		row.getCell("splitAmt").setValue(splitAmt);
    		
    		if(amount != null) {
    			amtTotal = amtTotal.add(amount);
    		}
    		if(splitAmt != null) {
    			splitAmtTotal = splitAmtTotal.add(splitAmt);
    		}
    		//融合
			if(!number.equals(oldNumber)){
				endRow = getMainTable().getRowCount()-1-1;//索引从0开始，故减1；编码不一样时，已经是另外的单据了，故再减1
				if(endRow > beginRow){
					mm.mergeBlock(beginRow,numberCol,endRow,numberCol,KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(beginRow,nameCol,endRow,nameCol,KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(beginRow,payNumberCol,endRow,payNumberCol,KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(beginRow,payDateCol,endRow,payDateCol,KDTMergeManager.SPECIFY_MERGE);
//					mm.mergeBlock(beginRow,amountCol,endRow,amountCol,KDTMergeManager.SPECIFY_MERGE);
//					mm.mergeBlock(beginRow,splitAmtCol,endRow,splitAmtCol,KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(beginRow,idCol,endRow,idCol,KDTMergeManager.SPECIFY_MERGE);
				}
				
				oldNumber = number;
				beginRow = getMainTable().getRowCount()-1;//索引从0开始，故减1
			}
    	}
    	//如果最后几行需要融合，上面逻辑无法处理，在此处理
		endRow = getMainTable().getRowCount()-1;//索引从0开始，故减1
		if(endRow > beginRow){
			mm.mergeBlock(beginRow,numberCol,endRow,numberCol,KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(beginRow,nameCol,endRow,nameCol,KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(beginRow,payNumberCol,endRow,payNumberCol,KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(beginRow,payDateCol,endRow,payDateCol,KDTMergeManager.SPECIFY_MERGE);
//			mm.mergeBlock(beginRow,amountCol,endRow,amountCol,KDTMergeManager.SPECIFY_MERGE);
//			mm.mergeBlock(beginRow,splitAmtCol,endRow,splitAmtCol,KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(beginRow,idCol,endRow,idCol,KDTMergeManager.SPECIFY_MERGE);
		}
    	if(b) {
    		IRow totalRow = getMainTable().addRow();
    		totalRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
	    	totalRow.getCell("number").setValue("合计");
	    	totalRow.getCell("payAmt").setValue(amtTotal);
	    	totalRow.getCell("splitAmt").setValue(splitAmtTotal);
    	}
    	getMainTable().getStyleAttributes().setLocked(true);
    }
}