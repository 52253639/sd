package com.kingdee.eas.fdc.schedule.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;

public class AchievementProfessionControllerBean extends AbstractAchievementProfessionControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.AchievementProfessionControllerBean");
    
    public CoreBaseCollection getCollection(Context ctx, EntityViewInfo view) throws BOSException {
		return super.getCollection(ctx, view);
	}
}