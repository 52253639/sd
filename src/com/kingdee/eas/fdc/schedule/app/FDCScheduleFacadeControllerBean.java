package com.kingdee.eas.fdc.schedule.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;

public class FDCScheduleFacadeControllerBean extends AbstractFDCScheduleFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.FDCScheduleFacadeControllerBean");
    protected EntityViewInfo _getView(Context ctx, String billID)throws BOSException
    {
		return new EntityViewInfo();
    }
    
   
}