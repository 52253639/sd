package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SubareaFactory
{
    private SubareaFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISubarea getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISubarea)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DE6BAB92") ,com.kingdee.eas.fdc.sellhouse.ISubarea.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISubarea getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISubarea)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DE6BAB92") ,com.kingdee.eas.fdc.sellhouse.ISubarea.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISubarea getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISubarea)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DE6BAB92"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISubarea getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISubarea)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DE6BAB92"));
    }
}