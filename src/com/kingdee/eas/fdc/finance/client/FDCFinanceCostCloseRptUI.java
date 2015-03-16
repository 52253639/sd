/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.finance.FDCCostCloseSortEnum;

/**
 * output class name
 */
public class FDCFinanceCostCloseRptUI extends AbstractFDCFinanceCostCloseRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCFinanceCostCloseRptUI.class);
    
    /**
     * output class constructor
     */
    public FDCFinanceCostCloseRptUI() throws Exception
    {
        super();
    }
    
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }
    
    public void onLoad() throws Exception {
    	isCost=false;
    	super.onLoad();
    }
    
    protected void loadRow(IRow row) {
    	Object[] statictisics = getStatisticsData(row);
		if (statictisics == null)
			return;
		if (isClose()) {
			if(getItem(row).toString().startsWith("invalid")){
				row.getCell("thisCount").setValue(statictisics[1]);
				return;
			}
			row.getCell("itemCount").setValue(statictisics[0]);
			row.getCell("thisCount").setValue(statictisics[1]);
			row.getCell("allSplit").setValue(statictisics[2]);
		} else {
			if(getItem(row).toString().startsWith("invalid")){
				row.getCell("thisCount").setValue(statictisics[1]);
				return;
			}
			row.getCell("save").setValue(statictisics[0]);
			row.getCell("submit").setValue(statictisics[1]);
			row.getCell("audit").setValue(statictisics[2]);
			row.getCell("noSplit").setValue(statictisics[3]);
			row.getCell("partSplit").setValue(statictisics[4]);
			row.getCell("allSplit").setValue(statictisics[5]);
		}
    }
    
    private void printMothed(ActionEvent e, boolean b) throws Exception {
		
		IRow row = tblMain.getHeadRow(0);// 获得表头的第一行
		int headColCount = tblMain.getColumnCount() - 1;// 取列数，不过要减去id列
		row.setHeight(19);// 设置行高 ，让第一行行可见
		// 拿到第一个单元格 并且设置
		ICell cell = row.getCell(0);
		cell.setValue("分类统计");
		// 拿到第2个单元格 并且设置
		ICell cell1 = row.getCell(1);
		String str = "";
		if (comboCostCloseSort.getSelectedIndex() == 0) {
			str = FDCCostCloseSortEnum.COSTCLOSERPT.getAlias();
		} else {
			str = FDCCostCloseSortEnum.COSTUNCLOSERPT.getAlias();
		}
		cell1.setValue(str);
		row.setCell(1, cell1);
		// 拿到第3个单元格 并且设置
		ICell cell2 = row.getCell(2);
		cell2.setValue("月度");
		row.setCell(2, cell2);
		// 拿到第4个单元格 并且设置
		String year = spiYear.getValue().toString();
		String month = spiMonth.getValue().toString();
		String monthDate = year + "\u5e74" + month +"\u6708" ;
		ICell cell3 = row.getCell(3);
		cell3.setValue(monthDate);
		row.setCell(3, cell3);
		// 合并单元格
		KDTMergeManager mm = tblMain.getHeadMergeManager();
		mm.mergeBlock(0, 4, 0, (headColCount - 1),KDTMergeManager.SPECIFY_MERGE);
		// 查看是不是有数据，如果没有数据，系统会提示不打印，这个时候需要将表头的高度设置为0不让用户看见。
		if (tblMain.getBody().size() == 0) {
			row.setHeight(0);
		}
		if (b) {// 打印 预览
			super.actionPrintPreview_actionPerformed(e);
		} else {// 打印
			super.actionPrint_actionPerformed(e);
		}
		// 关闭打印预览以后，将表头第一行的高度设置成0 不让用户看见
		tblMain.getHeadRow(0).setHeight(0);
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		this.printMothed(e, true);
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		this.printMothed(e, false);
	}  

}