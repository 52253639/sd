package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DefaultAmountMangerEntryFactory
{
    private DefaultAmountMangerEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C0FFB2FA") ,com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C0FFB2FA") ,com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C0FFB2FA"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountMangerEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C0FFB2FA"));
    }
}