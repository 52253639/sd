package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeNameOldCustomerEntryFactory
{
    private ChangeNameOldCustomerEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeNameOldCustomerEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeNameOldCustomerEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0C3BDFE3") ,com.kingdee.eas.fdc.sellhouse.IChangeNameOldCustomerEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChangeNameOldCustomerEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeNameOldCustomerEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0C3BDFE3") ,com.kingdee.eas.fdc.sellhouse.IChangeNameOldCustomerEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeNameOldCustomerEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeNameOldCustomerEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0C3BDFE3"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeNameOldCustomerEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeNameOldCustomerEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0C3BDFE3"));
    }
}