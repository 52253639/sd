package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CRMChequeFactory
{
    private CRMChequeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICRMCheque getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICRMCheque)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EF7E1D24") ,com.kingdee.eas.fdc.sellhouse.ICRMCheque.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICRMCheque getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICRMCheque)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EF7E1D24") ,com.kingdee.eas.fdc.sellhouse.ICRMCheque.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICRMCheque getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICRMCheque)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EF7E1D24"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICRMCheque getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICRMCheque)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EF7E1D24"));
    }
}