package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurCustomerEntryFactory
{
    private PurCustomerEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurCustomerEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurCustomerEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9727BE02") ,com.kingdee.eas.fdc.sellhouse.IPurCustomerEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurCustomerEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurCustomerEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9727BE02") ,com.kingdee.eas.fdc.sellhouse.IPurCustomerEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurCustomerEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurCustomerEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9727BE02"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurCustomerEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurCustomerEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9727BE02"));
    }
}