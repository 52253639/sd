package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.NewListTempletColumnInfo;
import com.kingdee.eas.fdc.invite.NewListTempletEntryInfo;
import com.kingdee.eas.fdc.invite.NewListTempletValueCollection;
import com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.fdc.invite.NewListTempletValueInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class NewListTempletValueControllerBean extends AbstractNewListTempletValueControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.NewListTempletValueControllerBean");

	protected IObjectCollection _getCollectionBySQL(Context ctx, String listingId) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		StringBuffer SQL = new StringBuffer();
		SQL.append("select t_inv_newlisttempletvalue.* ");
		SQL.append(" from t_inv_newlisttempletvalue inner join t_inv_newlisttempletentry on t_inv_newlisttempletvalue.fentryid=t_inv_newlisttempletentry.fid ");
		SQL.append(" inner join  t_inv_newlisttempletpage on t_inv_newlisttempletpage.fid=t_inv_newlisttempletentry.fheadid");
		SQL.append(" where t_inv_newlisttempletpage.fheadid = ?");

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder
				.appendSql(SQL.toString());
		builder.addParam(listingId);
		IRowSet rowSet = builder.executeQuery();
		NewListTempletValueCollection values = new NewListTempletValueCollection();
		try {
			NewListTempletValueInfo info = null;
			NewListTempletEntryInfo entry =null;
			NewListTempletColumnInfo column = null;
			while(rowSet.next()){
				info = new NewListTempletValueInfo();
				info.setId(BOSUuid.read(rowSet.getString("FID")));
				entry = new NewListTempletEntryInfo();
				entry.setId(BOSUuid.read(rowSet.getString("FENTRYID")));
				info.setEntry(entry);
				column = new NewListTempletColumnInfo();
				column.setId(BOSUuid.read(rowSet.getString("FCOLUMNID")));
				info.setColumn(column);
				info.setSeq(rowSet.getInt("FSEQ"));
				info.setAmount(rowSet.getBigDecimal("FAMOUNT"));
				info.setText(rowSet.getString("FTEXT"));
				info.setType(QuotingPriceTypeEnum.getEnum(rowSet.getString("FTYPE")));
				info.setDateValue(rowSet.getDate("FDATEVALUE"));
				values.add(info);
			}
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			logger.error(e);
		}
		return values;
	}
}