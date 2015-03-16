package com.kingdee.eas.fdc.invite.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class InviteTypeControllerBean extends AbstractInviteTypeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.invite.app.InviteTypeControllerBean");

	
	protected void _submit(Context ctx , IObjectPK pk , IObjectValue model) throws
    BOSException , EASBizException{
		
		super._submit(ctx, pk, model);
//		InviteTypeInfo info = (InviteTypeInfo)model;
//		if(info.getCU().getId().toString().equals(OrgConstants.SYS_CU_ID)){
//			FDCSQLBuilder sqlB = new FDCSQLBuilder(ctx);
//			sqlB.appendSql("update t_inv_inviteType set forgid=fcontrolUnitId where fid=?");
//			sqlB.addParam(pk);
//			sqlB.executeUpdate();
//			sqlB.clear();
//		}
	}
	protected boolean _updateRelateData(Context ctx, String oldID,
			String newID, Object[] tables) throws BOSException {
		if (tables == null) {
			return false;
		}

		for (int i = 0; i < tables.length; i++) {
			String sql = "update " + tables[i] + " set FInviteTypeID='" + newID
					+ "' where FInviteTypeID='" + oldID + "'";
			SQLExecutorFactory.getLocalInstance(ctx, sql).executeSQL();
		}
		return true;
	}

	protected Object[] _getRelateData(Context ctx, String id, String[] tables)
			throws BOSException {
		if (tables == null) {
			return new Object[0];
		}
		HashSet set = new HashSet();
		try {
			String sql = "";
			IRowSet rowSet = null;
			for (int i = 0; i < tables.length; i++) {
				String table = tables[i];
				sql = "select count(*) as count from " + table
						+ " where FInviteTypeID='" + id + "'";
				rowSet = SQLExecutorFactory.getLocalInstance(ctx, sql)
						.executeSQL();
				
				if (rowSet.next()) {
					Collection collection = rowSet.toCollection();
					Iterator iterator = collection.iterator();
					Vector obj = (Vector) iterator.next();
					BigDecimal count = new BigDecimal(obj.get(0).toString());
					if (count.intValue() > 0) {
						set.add(table);
					}
				}
				
			}
			sql = "select count(*) as count from t_inv_invitetype where FparentID='" + id + "'";
			rowSet = SQLExecutorFactory.getLocalInstance(ctx, sql)
				.executeSQL();
			if (rowSet.next()) {
				Collection collection = rowSet.toCollection();
				Iterator iterator = collection.iterator();
				Vector obj = (Vector) iterator.next();
				BigDecimal count = new BigDecimal(obj.get(0).toString());
				if (count.intValue() > 0) {
					set.add("t_inv_invitetype");
				}
			}
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new BOSException(e.getMessage());
		}
		return set.toArray();
	}
	
	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		super._update(ctx, pk, model);
		InviteTypeInfo info = (InviteTypeInfo)model;
		if(info.getCU().getId().toString().equals(OrgConstants.SYS_CU_ID)){
			FDCSQLBuilder sqlB = new FDCSQLBuilder(ctx);
			sqlB.appendSql("update t_inv_inviteType set forgid=fcontrolUnitId where fid= ? ");
			sqlB.addParam(pk.toString());
			sqlB.executeUpdate();
			sqlB.clear();
		}
	}
}