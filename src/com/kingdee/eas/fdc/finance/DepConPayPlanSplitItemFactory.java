package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DepConPayPlanSplitItemFactory
{
    private DepConPayPlanSplitItemFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IDepConPayPlanSplitItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDepConPayPlanSplitItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AF17653E") ,com.kingdee.eas.fdc.finance.IDepConPayPlanSplitItem.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IDepConPayPlanSplitItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDepConPayPlanSplitItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AF17653E") ,com.kingdee.eas.fdc.finance.IDepConPayPlanSplitItem.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IDepConPayPlanSplitItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDepConPayPlanSplitItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AF17653E"));
    }
    public static com.kingdee.eas.fdc.finance.IDepConPayPlanSplitItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDepConPayPlanSplitItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AF17653E"));
    }
}