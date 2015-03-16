package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class XQEntryFactory
{
    private XQEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IXQEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IXQEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A7E9A5DE") ,com.kingdee.eas.fdc.sellhouse.IXQEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IXQEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IXQEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A7E9A5DE") ,com.kingdee.eas.fdc.sellhouse.IXQEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IXQEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IXQEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A7E9A5DE"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IXQEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IXQEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A7E9A5DE"));
    }
}