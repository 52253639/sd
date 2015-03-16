package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectReferedException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomFactory;

public class SwapRoomReasonControllerBean extends AbstractSwapRoomReasonControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.SwapRoomReasonControllerBean");
    
    protected void _isReferenced(Context arg0, IObjectPK arg1)
    		throws BOSException, EASBizException {
    	//被换房单是否引用
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("swapReason.id", arg1.toString()));
    	
    	if(ChangeRoomFactory.getLocalInstance(arg0).exists(filter))
    	{
    		throw new ObjectReferedException(arg1);
    	}
    }
}