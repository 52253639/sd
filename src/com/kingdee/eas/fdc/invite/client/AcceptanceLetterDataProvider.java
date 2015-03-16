package com.kingdee.eas.fdc.invite.client;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.jdbc.rowset.IRowSet;

public class AcceptanceLetterDataProvider extends InvitePrintDataProvider {

	public AcceptanceLetterDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	}

	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds){
		return this.getMainBillRowSet(ds);
	}
}
