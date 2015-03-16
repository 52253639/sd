package com.kingdee.eas.fdc.invite.client;

import java.sql.SQLException;
import java.sql.Types;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

public class ViewExpertAppraiseResultDescDataProvider extends
		InvitePrintDataProvider {
	private String desc;

	public ViewExpertAppraiseResultDescDataProvider(String billId,
			IMetaDataPK mainQuery,String desc) {
		super(billId, mainQuery);
		this.desc=desc;
	}

	public IRowSet execute(BOSQueryDataSource ds) {
		if(ds.getID().toUpperCase().startsWith("DESC")) 
        {
        	return getDescRowSet(ds);
        }else{
        	
        	return super.execute(ds);
        }
	}
	
	/**
	 * 
	 * @param ds
	 * @return
	 */
	public IRowSet getDescRowSet(BOSQueryDataSource ds){
		String[] colName=new String[]{"DESC"};
 		try {
 			IRowSet rowset = null;
			DynamicRowSet rs=new DynamicRowSet(colName.length);
			for(int i=0,n=colName.length;i<n;i++){
				ColInfo colInfo=new ColInfo();
				colInfo.colType = Types.VARCHAR;
				colInfo.columnName = colName[i].toString();
				rs.setColInfo(i+1, colInfo);
			}
			rowset = rs;
			rs.beforeFirst();
			rowset.moveToInsertRow();
			rowset.updateString(colName[0], desc);
			rowset.beforeFirst();
			rs.insertRow();
			return rowset;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
