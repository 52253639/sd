package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;

public class RoomModelControllerBean extends AbstractRoomModelControllerBean
{
 	/**
	 * 
	 */
	private static final long serialVersionUID = -7499664321391633388L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomModelControllerBean");
    
    protected IObjectPK _submit(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	return super._submit(ctx, model);
    }
    protected void _checkNumberDup(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	//super._checkNumberDup(ctx, model);
    }
    
    protected void _checkNameDup(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	//super._checkNameDup(ctx, model);
    }
}