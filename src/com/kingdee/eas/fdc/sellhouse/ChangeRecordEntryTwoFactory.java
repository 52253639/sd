package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeRecordEntryTwoFactory
{
    private ChangeRecordEntryTwoFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeRecordEntryTwo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeRecordEntryTwo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("75B31B16") ,com.kingdee.eas.fdc.sellhouse.IChangeRecordEntryTwo.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChangeRecordEntryTwo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeRecordEntryTwo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("75B31B16") ,com.kingdee.eas.fdc.sellhouse.IChangeRecordEntryTwo.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeRecordEntryTwo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeRecordEntryTwo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("75B31B16"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeRecordEntryTwo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeRecordEntryTwo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("75B31B16"));
    }
}