package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeCustomerEntryFactory
{
    private ChangeCustomerEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeCustomerEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeCustomerEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E202AAA9") ,com.kingdee.eas.fdc.sellhouse.IChangeCustomerEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChangeCustomerEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeCustomerEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E202AAA9") ,com.kingdee.eas.fdc.sellhouse.IChangeCustomerEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeCustomerEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeCustomerEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E202AAA9"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeCustomerEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeCustomerEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E202AAA9"));
    }
}