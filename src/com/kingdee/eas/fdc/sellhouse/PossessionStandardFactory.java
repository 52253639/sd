package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PossessionStandardFactory
{
    private PossessionStandardFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPossessionStandard getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPossessionStandard)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CA7170BA") ,com.kingdee.eas.fdc.sellhouse.IPossessionStandard.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPossessionStandard getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPossessionStandard)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CA7170BA") ,com.kingdee.eas.fdc.sellhouse.IPossessionStandard.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPossessionStandard getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPossessionStandard)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CA7170BA"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPossessionStandard getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPossessionStandard)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CA7170BA"));
    }
}