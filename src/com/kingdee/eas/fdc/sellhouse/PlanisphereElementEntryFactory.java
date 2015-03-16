package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PlanisphereElementEntryFactory
{
    private PlanisphereElementEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPlanisphereElementEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPlanisphereElementEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("28567C68") ,com.kingdee.eas.fdc.sellhouse.IPlanisphereElementEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPlanisphereElementEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPlanisphereElementEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("28567C68") ,com.kingdee.eas.fdc.sellhouse.IPlanisphereElementEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPlanisphereElementEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPlanisphereElementEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("28567C68"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPlanisphereElementEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPlanisphereElementEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("28567C68"));
    }
}