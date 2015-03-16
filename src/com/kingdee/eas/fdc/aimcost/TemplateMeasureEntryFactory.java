package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TemplateMeasureEntryFactory
{
    private TemplateMeasureEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplateMeasureEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateMeasureEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5E698F25") ,com.kingdee.eas.fdc.aimcost.ITemplateMeasureEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ITemplateMeasureEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateMeasureEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5E698F25") ,com.kingdee.eas.fdc.aimcost.ITemplateMeasureEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplateMeasureEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateMeasureEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5E698F25"));
    }
    public static com.kingdee.eas.fdc.aimcost.ITemplateMeasureEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITemplateMeasureEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5E698F25"));
    }
}