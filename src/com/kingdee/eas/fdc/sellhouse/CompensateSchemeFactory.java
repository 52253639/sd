package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompensateSchemeFactory
{
    private CompensateSchemeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICompensateScheme getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICompensateScheme)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BB9AA697") ,com.kingdee.eas.fdc.sellhouse.ICompensateScheme.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICompensateScheme getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICompensateScheme)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BB9AA697") ,com.kingdee.eas.fdc.sellhouse.ICompensateScheme.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICompensateScheme getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICompensateScheme)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BB9AA697"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICompensateScheme getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICompensateScheme)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BB9AA697"));
    }
}