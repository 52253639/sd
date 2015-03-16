package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RoomLoanAFMEntrysFactory
{
    private RoomLoanAFMEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomLoanAFMEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomLoanAFMEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9E35F7A3") ,com.kingdee.eas.fdc.sellhouse.IRoomLoanAFMEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IRoomLoanAFMEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomLoanAFMEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9E35F7A3") ,com.kingdee.eas.fdc.sellhouse.IRoomLoanAFMEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomLoanAFMEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomLoanAFMEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9E35F7A3"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IRoomLoanAFMEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IRoomLoanAFMEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9E35F7A3"));
    }
}