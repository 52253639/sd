package com.kingdee.eas.fdc.invite.client;

import java.sql.SQLException;
import java.sql.Types;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

public class InviteProjectDataProvider extends InvitePrintDataProvider {

	public InviteProjectDataProvider(String billId, IMetaDataPK mainQuery,com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry) {
		super(billId, mainQuery);
		this.kdtEntry=kdtEntry;
	}

	public IRowSet execute(BOSQueryDataSource ds) {
        IRowSet iRowSet = null;
        if ("MATERIALENTRY".equals(ds.getID().toUpperCase())){
        	iRowSet = getEntryRowSet(ds);
        } else if (ds.getID().toLowerCase().startsWith("InviteProjectForPrint".toLowerCase())) {
        	// 这种条件下，也视为主数据源
			iRowSet = getMainBillRowSet(ds);
        }
        else{
        	return super.execute(ds);
        }
        return iRowSet;
	}
	
	public IRowSet getEntryRowSet(BOSQueryDataSource ds){
		String[] colName=new String[]{"prjNumber","materialNum","materialName","size","measureUnit","amount","inputDate","description"};
		try {
			DynamicRowSet rs=new DynamicRowSet(colName.length);
			for(int i=0,n=colName.length;i<n;i++){
				ColInfo colInfo=new ColInfo();
				colInfo.colType = Types.VARCHAR;
				colInfo.columnName = colName[i].toString();
				rs.setColInfo(i+1, colInfo);
			}
			for(int i=0,n=kdtEntry.getRowCount();i<n;i++){
				rs.moveToInsertRow();
				for(int j=0,m=colName.length;j<m;j++){
					Object value=kdtEntry.getRow(i).getCell(colName[j]).getValue();
					if("materialNum".equals(colName[j])){
						rs.updateString(colName[j], value==null? "":((MaterialInfo)value).getNumber());
					}else{
						rs.updateString(colName[j], value==null? "":value.toString());
					}
				}
				rs.insertRow();
			}
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
