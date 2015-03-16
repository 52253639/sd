package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;

public class SHECusAssistantDataGroupControllerBean extends AbstractSHECusAssistantDataGroupControllerBean
 {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.SHECusAssistantDataGroupControllerBean");

	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		this._checkNameDup(ctx, model);
		return super._addnew(ctx, model);
	}

	protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		this._checkNameDup(ctx, model);
		super._addnew(ctx, pk, model);
	}

}