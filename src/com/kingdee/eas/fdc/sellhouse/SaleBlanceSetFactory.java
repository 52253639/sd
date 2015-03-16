package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SaleBlanceSetFactory
{
    private SaleBlanceSetFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISaleBlanceSet getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISaleBlanceSet)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EAEBCD87") ,com.kingdee.eas.fdc.sellhouse.ISaleBlanceSet.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISaleBlanceSet getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISaleBlanceSet)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EAEBCD87") ,com.kingdee.eas.fdc.sellhouse.ISaleBlanceSet.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISaleBlanceSet getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISaleBlanceSet)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EAEBCD87"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISaleBlanceSet getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISaleBlanceSet)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EAEBCD87"));
    }
}