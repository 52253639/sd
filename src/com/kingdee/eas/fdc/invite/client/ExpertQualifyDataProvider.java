package com.kingdee.eas.fdc.invite.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;
public class ExpertQualifyDataProvider extends FDCBillDataProvider {


	private static final Logger logger = CoreUIObject.getLogger(ExpertQualifyDataProvider.class);
	
	public ExpertQualifyDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	}
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {

		IRowSet mainBillRowSet = getMainBillRowSet(ds);

		return mainBillRowSet;
	}
}
