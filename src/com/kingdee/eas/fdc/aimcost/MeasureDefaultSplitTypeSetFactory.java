package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MeasureDefaultSplitTypeSetFactory
{
    private MeasureDefaultSplitTypeSetFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureDefaultSplitTypeSet getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureDefaultSplitTypeSet)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7703A388") ,com.kingdee.eas.fdc.aimcost.IMeasureDefaultSplitTypeSet.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IMeasureDefaultSplitTypeSet getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureDefaultSplitTypeSet)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7703A388") ,com.kingdee.eas.fdc.aimcost.IMeasureDefaultSplitTypeSet.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureDefaultSplitTypeSet getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureDefaultSplitTypeSet)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7703A388"));
    }
    public static com.kingdee.eas.fdc.aimcost.IMeasureDefaultSplitTypeSet getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IMeasureDefaultSplitTypeSet)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7703A388"));
    }
}