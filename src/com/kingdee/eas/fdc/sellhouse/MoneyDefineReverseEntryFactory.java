package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MoneyDefineReverseEntryFactory
{
    private MoneyDefineReverseEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMoneyDefineReverseEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMoneyDefineReverseEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E3A1EFB0") ,com.kingdee.eas.fdc.sellhouse.IMoneyDefineReverseEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMoneyDefineReverseEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMoneyDefineReverseEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E3A1EFB0") ,com.kingdee.eas.fdc.sellhouse.IMoneyDefineReverseEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMoneyDefineReverseEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMoneyDefineReverseEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E3A1EFB0"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMoneyDefineReverseEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMoneyDefineReverseEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E3A1EFB0"));
    }
}