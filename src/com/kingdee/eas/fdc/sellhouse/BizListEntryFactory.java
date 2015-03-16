package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BizListEntryFactory
{
    private BizListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBizListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBizListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D424B8FC") ,com.kingdee.eas.fdc.sellhouse.IBizListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBizListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBizListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D424B8FC") ,com.kingdee.eas.fdc.sellhouse.IBizListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBizListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBizListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D424B8FC"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBizListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBizListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D424B8FC"));
    }
}