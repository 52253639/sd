package com.kingdee.eas.fdc.finance.client;

import java.sql.SQLException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.eas.base.report.VirtualRowSet;
import com.kingdee.jdbc.rowset.IRowSet;

public class FDCCostCloseDataProvider implements BOSQueryDelegate {
	private KDTable tblMain;
	
	public FDCCostCloseDataProvider(KDTable tblMain){
		this.tblMain = tblMain;
	}

	public IRowSet execute(BOSQueryDataSource ds) {
		if(tblMain == null){
			return null;
		}
		IRowSet rs = new  VirtualRowSet();
		
		String[] colName = new String[] { "company", "project", "item",
				"standard", "itemCount", "thisCount", "allSplit", "save",
				"submit", "audits","noSplit","partSplit","allSplit" };

		for(int i = 0,n = tblMain.getRowCount();i < n ; i++){
			IRow row = tblMain.getRow(i);
			try {
				rs.insertRow();
				for(int j = 0; j < colName.length;j ++){
					rs.updateString(j,row.getCell(colName[j]).getValue().toString());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}
}
