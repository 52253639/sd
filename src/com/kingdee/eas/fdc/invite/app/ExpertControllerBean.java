package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;

public class ExpertControllerBean extends AbstractExpertControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.ExpertControllerBean");
    
    protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
    		throws BOSException, EASBizException {
    	
    	super._checkNumberDup(ctx, model);
    	//super._checkNameDup(ctx, model);
    	super._submit(ctx, pk, model);
    }
    
    protected IObjectPK _submit(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	super._checkNumberDup(ctx,model);
    	//super._checkNameDup(ctx,model);
    	return super._submit(ctx, model);
    }
}