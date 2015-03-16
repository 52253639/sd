package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectPeriodStatusFactory
{
    private ProjectPeriodStatusFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectPeriodStatus getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectPeriodStatus)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("30DDC95D") ,com.kingdee.eas.fdc.finance.IProjectPeriodStatus.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectPeriodStatus getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectPeriodStatus)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("30DDC95D") ,com.kingdee.eas.fdc.finance.IProjectPeriodStatus.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectPeriodStatus getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectPeriodStatus)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("30DDC95D"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectPeriodStatus getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectPeriodStatus)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("30DDC95D"));
    }
}