package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompeteItemFactory
{
    private CompeteItemFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.ICompeteItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("359F4D69") ,com.kingdee.eas.fdc.market.ICompeteItem.class);
    }
    
    public static com.kingdee.eas.fdc.market.ICompeteItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("359F4D69") ,com.kingdee.eas.fdc.market.ICompeteItem.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.ICompeteItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("359F4D69"));
    }
    public static com.kingdee.eas.fdc.market.ICompeteItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeteItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("359F4D69"));
    }
}