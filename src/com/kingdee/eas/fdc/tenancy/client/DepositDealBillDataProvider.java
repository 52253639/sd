package com.kingdee.eas.fdc.tenancy.client;

import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;

public class DepositDealBillDataProvider extends FDCBillDataProvider{
	private String tenId=null;
	public DepositDealBillDataProvider(String billId,String tenId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
		this.tenId=tenId;
	}
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		
		if (ds.getID().equalsIgnoreCase("AttachmentQuery")) {
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.basedata.app.AttachmentQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("boAttchAsso.boID",billId));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return iRowSet;
		}else if (ds.getID().equalsIgnoreCase("TenancyBillTotalPrintQuery")) {
			return getTotalEntryRowSet();
		}
		return getMainBillRowSet(ds);
	}
	private IRowSet getTotalEntryRowSet() {
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select t.moneyDefine,isnull(sum(t.appAmount),0) appAmount,isnull(sum(t.actRevAmount),0) actRevAmount from(");
		_builder.appendSql(" select md.fnumber mdNumber,md.fname_l2 moneyDefine,entry.fappAmount appAmount,isnull(entry.factRevAmount,0) actRevAmount from T_TEN_TenBillOtherPay entry left join T_SHE_MoneyDefine md on md.fid=entry.fmoneyDefineId");
		_builder.appendSql(" where entry.fheadId='"+tenId+"'");
		_builder.appendSql(" union all select md.fnumber mdNumber,md.fname_l2 moneyDefine,entry.fappAmount appAmount,isnull(entry.factRevAmount,0) actRevAmount from T_TEN_TenancyRoomPayListEntry entry left join T_SHE_MoneyDefine md on md.fid=entry.fmoneyDefineId");
		_builder.appendSql(" where entry.ftenBillId='"+tenId+"'");
		_builder.appendSql(" )t group by t.moneyDefine");
		IRowSet rowSet=null;
		try {
			rowSet = _builder.executeQuery();
			rowSet.beforeFirst();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowSet;
	}
}
