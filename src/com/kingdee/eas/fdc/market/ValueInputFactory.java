package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ValueInputFactory
{
    private ValueInputFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IValueInput getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueInput)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CEFF296A") ,com.kingdee.eas.fdc.market.IValueInput.class);
    }
    
    public static com.kingdee.eas.fdc.market.IValueInput getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueInput)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CEFF296A") ,com.kingdee.eas.fdc.market.IValueInput.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IValueInput getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueInput)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CEFF296A"));
    }
    public static com.kingdee.eas.fdc.market.IValueInput getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IValueInput)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CEFF296A"));
    }
}