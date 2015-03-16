package com.kingdee.eas.fdc.aimcost.client;

import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

public class MeasureCostAttachmentUtil {

	public static void tableDataFillHandle(KDTable tblMain, int startRow, int endRow, String idColumn, String attachColumn)throws Exception {
		Set ids = new HashSet();
		for(int i = startRow; i <= endRow; ++i){
			ids.add(tblMain.getRow(i).getCell(idColumn).getValue().toString());
			tblMain.getRow(i).getCell(attachColumn).setValue(Boolean.FALSE);
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select distinct fboid from T_BAS_BoAttchAsso where ");
		builder.appendParam("fboid", ids.toArray());
		IRowSet rowSet = builder.executeQuery();
		while(rowSet.next()){
			IRow row = getRow(tblMain, startRow, endRow, rowSet.getString(1), idColumn);
			if(row != null){
				row.getCell(attachColumn).setValue(Boolean.TRUE);
			}
		}
	}
	
	
	private static IRow getRow(KDTable tblMain, int startRow, int endRow, String id, String idColumn){
		for(int i = startRow; i <= endRow; ++i){
			IRow row = tblMain.getRow(i);
			if(id.equals(row.getCell(idColumn).getValue().toString())){
				return row;
			}
		}
		return null;
	}
	
	public static boolean isHasAttachement(String id) throws Exception{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select count(fid) from T_BAS_BoAttchAsso where fboid = ?");
		builder.addParam(id);
		IRowSet rowSet = builder.executeQuery();
		if(rowSet.next()){
			return rowSet.getInt(1)>0;
		}
		return false;
		
	}
}
