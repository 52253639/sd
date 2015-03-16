package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.common.EASBizException;

public class BuildingStructureControllerBean extends
		AbstractBuildingStructureControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.BuildingStructureControllerBean");

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		this.isReferenced(ctx, pk);
		super._delete(ctx, pk);
	}
}