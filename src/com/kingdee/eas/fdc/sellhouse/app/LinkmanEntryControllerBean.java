package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;

public class LinkmanEntryControllerBean extends AbstractLinkmanEntryControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.LinkmanEntryControllerBean");
    
    //分录数据，不做非空及重复性校验
    protected void _checkNumberBlank(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    }
    
    protected void _checkNameBlank(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    }
    
    protected void _checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    }
    
    protected void _checkNameDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    }
    
    protected void updateCheck(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    }
}