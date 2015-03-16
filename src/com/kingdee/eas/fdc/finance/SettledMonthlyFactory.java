package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettledMonthlyFactory
{
    private SettledMonthlyFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.ISettledMonthly getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISettledMonthly)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C78A4423") ,com.kingdee.eas.fdc.finance.ISettledMonthly.class);
    }
    
    public static com.kingdee.eas.fdc.finance.ISettledMonthly getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISettledMonthly)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C78A4423") ,com.kingdee.eas.fdc.finance.ISettledMonthly.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.ISettledMonthly getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISettledMonthly)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C78A4423"));
    }
    public static com.kingdee.eas.fdc.finance.ISettledMonthly getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISettledMonthly)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C78A4423"));
    }
}