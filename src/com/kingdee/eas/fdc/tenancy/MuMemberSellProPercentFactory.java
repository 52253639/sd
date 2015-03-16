package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MuMemberSellProPercentFactory
{
    private MuMemberSellProPercentFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IMuMemberSellProPercent getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMuMemberSellProPercent)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BC5E45B5") ,com.kingdee.eas.fdc.tenancy.IMuMemberSellProPercent.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IMuMemberSellProPercent getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMuMemberSellProPercent)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BC5E45B5") ,com.kingdee.eas.fdc.tenancy.IMuMemberSellProPercent.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IMuMemberSellProPercent getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMuMemberSellProPercent)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BC5E45B5"));
    }
    public static com.kingdee.eas.fdc.tenancy.IMuMemberSellProPercent getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IMuMemberSellProPercent)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BC5E45B5"));
    }
}