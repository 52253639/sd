package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class LinkmanEntryFactory
{
    private LinkmanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ILinkmanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ILinkmanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7F04C78D") ,com.kingdee.eas.fdc.sellhouse.ILinkmanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ILinkmanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ILinkmanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7F04C78D") ,com.kingdee.eas.fdc.sellhouse.ILinkmanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ILinkmanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ILinkmanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7F04C78D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ILinkmanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ILinkmanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7F04C78D"));
    }
}