package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Set;

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
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.NewListingColumnInfo;
import com.kingdee.eas.fdc.invite.NewListingEntryInfo;
import com.kingdee.eas.fdc.invite.NewListingValueCollection;
import com.kingdee.eas.fdc.invite.NewListingValueInfo;
import com.kingdee.eas.fdc.invite.NewListingValueCollection;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.invite.NewListingValueInfo;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.jdbc.rowset.IRowSet;

public class NewListingValueControllerBean extends AbstractNewListingValueControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.NewListingValueControllerBean");

	protected IObjectCollection _getCollectionBySQL(Context ctx, String listingId) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		StringBuffer SQL = new StringBuffer();
		SQL.append("select t_inv_newlistingvalue.* ");
		SQL.append(" from t_inv_newlistingvalue inner join t_inv_newlistingentry on t_inv_newlistingvalue.fentryid=t_inv_newlistingentry.fid ");
		SQL.append(" inner join  t_inv_newlistingpage on t_inv_newlistingpage.fid=t_inv_newlistingentry.fheadid");
		SQL.append(" where t_inv_newlistingpage.fheadid = ?");
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder.appendSql(SQL.toString());
		builder.addParam(listingId);
		IRowSet rowSet = builder.executeQuery();
		NewListingValueCollection values = new NewListingValueCollection();
		try {
			NewListingValueInfo info = null;
			NewListingEntryInfo entry =null;
			NewListingColumnInfo column = null;
			NewQuotingPriceInfo quo = null;
			while(rowSet.next()){
				info = new NewListingValueInfo();
				info.setId(BOSUuid.read(rowSet.getString("FID")));
				if(rowSet.getString("FENTRYID")!=null){
					entry = new NewListingEntryInfo();
					entry.setId(BOSUuid.read(rowSet.getString("FENTRYID")));
				}
				info.setEntry(entry);
				
				if(rowSet.getString("FCOLUMNID")!=null){
					column = new NewListingColumnInfo();
					column.setId(BOSUuid.read(rowSet.getString("FCOLUMNID")));
				}
				info.setColumn(column);
				
				if(rowSet.getString("FQUOTINGPRICEID")!=null){
					quo = new NewQuotingPriceInfo();
					quo.setId(BOSUuid.read(rowSet.getString("FQUOTINGPRICEID")));
				}
				info.setQuotingPrice(quo);
				
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