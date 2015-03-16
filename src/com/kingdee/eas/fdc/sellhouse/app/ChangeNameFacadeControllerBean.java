package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ChangeNameFacadeControllerBean extends
		AbstractChangeNameFacadeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.ChangeNameFacadeControllerBean");

	protected void _changeName(Context ctx, String id, String newCustomerID)
			throws BOSException, EASBizException {
		StringBuffer sql = new StringBuffer();
		List params = new ArrayList();

		sql.append("update T_SHE_SincerityPurchase set FCustomerID = ? ");
		sql.append("where FID = ? ");

		params.add(newCustomerID);
		params.add(id);

		DbUtil.execute(ctx, sql.toString(), params.toArray());

	}
}