package com.kingdee.eas.fdc.sellhouse.client;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;

public class RoomKeepDownBillDataProvider extends FDCBillDataProvider{
private static final Logger logger = Logger.getLogger(RoomKeepDownBillDataProvider.class);
	
	public RoomKeepDownBillDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	}
	
	public IRowSet execute(BOSQueryDataSource ds) {
		// TODO 自动生成方法存根
		Variant paramVal = null;
        ArrayList ps = ds.getParams();
        IRowSet iRowSet = null;
        if (ps.size() > 0)
        {
            DSParam param = (DSParam)ps.get(0); 
            paramVal = param.getValue();	
        }
        
        if ("RoomKeepDownBillPrintQuery".equalsIgnoreCase(ds.getID().toUpperCase()))//假设主数据源名称为mainbill
        {
            //返加主数据源的结果集
        	iRowSet = getMainBillRowSet(ds);
        }
        else if(ds.getID().toUpperCase().startsWith("AUDITINFO")) 
        {
            //返回参数值为paramVal的从数据源1的结果集
        	//iRowSet = super.execute(ds);
        	return getAuditInfoRowSet(ds);
        }
        else{
        	
        	return getOtherSubRowSet(ds);
        }
        return iRowSet;
	}
}
