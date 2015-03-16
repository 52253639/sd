package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChequeDetailEntryFactory
{
    private ChequeDetailEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChequeDetailEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChequeDetailEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B7911C45") ,com.kingdee.eas.fdc.sellhouse.IChequeDetailEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChequeDetailEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChequeDetailEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B7911C45") ,com.kingdee.eas.fdc.sellhouse.IChequeDetailEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChequeDetailEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChequeDetailEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B7911C45"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChequeDetailEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChequeDetailEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B7911C45"));
    }
}