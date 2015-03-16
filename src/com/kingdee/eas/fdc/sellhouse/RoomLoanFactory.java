package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomLoanFactory
{
    private RoomLoanFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomLoan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomLoan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("608A3046") ,com.kingdee.eas.fdc.sellhouse.IRoomLoan.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomLoan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomLoan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("608A3046") ,com.kingdee.eas.fdc.sellhouse.IRoomLoan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomLoan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomLoan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("608A3046"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomLoan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomLoan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("608A3046"));
    }
}