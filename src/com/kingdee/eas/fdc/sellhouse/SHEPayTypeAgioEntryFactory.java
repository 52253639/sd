package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHEPayTypeAgioEntryFactory
{
    private SHEPayTypeAgioEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEPayTypeAgioEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEPayTypeAgioEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("14552C39") ,com.kingdee.eas.fdc.sellhouse.ISHEPayTypeAgioEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHEPayTypeAgioEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEPayTypeAgioEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("14552C39") ,com.kingdee.eas.fdc.sellhouse.ISHEPayTypeAgioEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEPayTypeAgioEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEPayTypeAgioEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("14552C39"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEPayTypeAgioEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEPayTypeAgioEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("14552C39"));
    }
}