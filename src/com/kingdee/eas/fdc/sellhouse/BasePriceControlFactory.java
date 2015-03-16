package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BasePriceControlFactory
{
    private BasePriceControlFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBasePriceControl getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBasePriceControl)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5EE7F220") ,com.kingdee.eas.fdc.sellhouse.IBasePriceControl.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBasePriceControl getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBasePriceControl)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5EE7F220") ,com.kingdee.eas.fdc.sellhouse.IBasePriceControl.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBasePriceControl getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBasePriceControl)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5EE7F220"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBasePriceControl getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBasePriceControl)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5EE7F220"));
    }
}