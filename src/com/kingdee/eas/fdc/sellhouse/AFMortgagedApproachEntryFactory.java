package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AFMortgagedApproachEntryFactory
{
    private AFMortgagedApproachEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IAFMortgagedApproachEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAFMortgagedApproachEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("95476B82") ,com.kingdee.eas.fdc.sellhouse.IAFMortgagedApproachEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IAFMortgagedApproachEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAFMortgagedApproachEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("95476B82") ,com.kingdee.eas.fdc.sellhouse.IAFMortgagedApproachEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IAFMortgagedApproachEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAFMortgagedApproachEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("95476B82"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IAFMortgagedApproachEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAFMortgagedApproachEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("95476B82"));
    }
}