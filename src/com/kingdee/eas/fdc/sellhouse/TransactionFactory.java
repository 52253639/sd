package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TransactionFactory
{
    private TransactionFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ITransaction getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ITransaction)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BB402523") ,com.kingdee.eas.fdc.sellhouse.ITransaction.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ITransaction getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ITransaction)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BB402523") ,com.kingdee.eas.fdc.sellhouse.ITransaction.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ITransaction getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ITransaction)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BB402523"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ITransaction getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ITransaction)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BB402523"));
    }
}