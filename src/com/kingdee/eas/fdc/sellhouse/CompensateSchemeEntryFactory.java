package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompensateSchemeEntryFactory
{
    private CompensateSchemeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICompensateSchemeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICompensateSchemeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BEC8F81B") ,com.kingdee.eas.fdc.sellhouse.ICompensateSchemeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICompensateSchemeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICompensateSchemeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BEC8F81B") ,com.kingdee.eas.fdc.sellhouse.ICompensateSchemeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICompensateSchemeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICompensateSchemeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BEC8F81B"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICompensateSchemeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICompensateSchemeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BEC8F81B"));
    }
}