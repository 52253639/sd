package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OverdueCauseFactory
{
    private OverdueCauseFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IOverdueCause getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IOverdueCause)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("08953604") ,com.kingdee.eas.fdc.sellhouse.IOverdueCause.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IOverdueCause getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IOverdueCause)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("08953604") ,com.kingdee.eas.fdc.sellhouse.IOverdueCause.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IOverdueCause getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IOverdueCause)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("08953604"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IOverdueCause getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IOverdueCause)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("08953604"));
    }
}