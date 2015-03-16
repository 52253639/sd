package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SharePPMFactory
{
    private SharePPMFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISharePPM getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISharePPM)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3E8D41E9") ,com.kingdee.eas.fdc.sellhouse.ISharePPM.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISharePPM getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISharePPM)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3E8D41E9") ,com.kingdee.eas.fdc.sellhouse.ISharePPM.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISharePPM getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISharePPM)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3E8D41E9"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISharePPM getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISharePPM)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3E8D41E9"));
    }
}