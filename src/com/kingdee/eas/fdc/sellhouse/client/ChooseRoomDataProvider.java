package com.kingdee.eas.fdc.sellhouse.client;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;


public class ChooseRoomDataProvider extends FDCBillDataProvider {


	public ChooseRoomDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	
	}
	public IRowSet execute(BOSQueryDataSource ds) {
		return super.execute(ds);
	}
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds){
		return super.getMainBillRowSet(ds);
	
	}
	
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds){
		return super.getMainBillRowSet(ds);
	}
}
	
