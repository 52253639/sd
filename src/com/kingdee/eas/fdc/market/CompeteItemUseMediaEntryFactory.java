package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompeteItemUseMediaEntryFactory
{
    private CompeteItemUseMediaEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.ICompeteItemUseMediaEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItemUseMediaEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CBE750EC") ,com.kingdee.eas.fdc.market.ICompeteItemUseMediaEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.ICompeteItemUseMediaEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItemUseMediaEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CBE750EC") ,com.kingdee.eas.fdc.market.ICompeteItemUseMediaEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.ICompeteItemUseMediaEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItemUseMediaEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CBE750EC"));
    }
    public static com.kingdee.eas.fdc.market.ICompeteItemUseMediaEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItemUseMediaEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CBE750EC"));
    }
}