package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;

public class RoomPropertyBatchBooksControllerBean extends AbstractRoomPropertyBatchBooksControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomPropertyBatchBooksControllerBean");
    
    protected IObjectPK _save(Context arg0, IObjectValue arg1)
    		throws BOSException, EASBizException {
    	return super._save(arg0, arg1);
    }
}