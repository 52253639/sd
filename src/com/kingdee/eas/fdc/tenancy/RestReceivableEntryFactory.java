package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RestReceivableEntryFactory
{
    private RestReceivableEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IRestReceivableEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRestReceivableEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3BC2CCF9") ,com.kingdee.eas.fdc.tenancy.IRestReceivableEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IRestReceivableEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRestReceivableEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3BC2CCF9") ,com.kingdee.eas.fdc.tenancy.IRestReceivableEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IRestReceivableEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRestReceivableEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3BC2CCF9"));
    }
    public static com.kingdee.eas.fdc.tenancy.IRestReceivableEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRestReceivableEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3BC2CCF9"));
    }
}