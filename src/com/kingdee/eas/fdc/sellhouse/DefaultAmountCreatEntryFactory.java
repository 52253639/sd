package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DefaultAmountCreatEntryFactory
{
    private DefaultAmountCreatEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreatEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreatEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5D7DBF07") ,com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreatEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreatEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreatEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5D7DBF07") ,com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreatEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreatEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreatEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5D7DBF07"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreatEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreatEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5D7DBF07"));
    }
}