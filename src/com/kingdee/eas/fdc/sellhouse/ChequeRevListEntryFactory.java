package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChequeRevListEntryFactory
{
    private ChequeRevListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChequeRevListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChequeRevListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("257A2BED") ,com.kingdee.eas.fdc.sellhouse.IChequeRevListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChequeRevListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChequeRevListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("257A2BED") ,com.kingdee.eas.fdc.sellhouse.IChequeRevListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChequeRevListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChequeRevListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("257A2BED"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChequeRevListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChequeRevListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("257A2BED"));
    }
}