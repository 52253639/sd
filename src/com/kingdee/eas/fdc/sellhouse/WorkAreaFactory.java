package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WorkAreaFactory
{
    private WorkAreaFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IWorkArea getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWorkArea)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6CED7719") ,com.kingdee.eas.fdc.sellhouse.IWorkArea.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IWorkArea getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWorkArea)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6CED7719") ,com.kingdee.eas.fdc.sellhouse.IWorkArea.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IWorkArea getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWorkArea)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6CED7719"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IWorkArea getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IWorkArea)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6CED7719"));
    }
}