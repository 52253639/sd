package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DecorationStandardFactory
{
    private DecorationStandardFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDecorationStandard getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDecorationStandard)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A72B11A8") ,com.kingdee.eas.fdc.sellhouse.IDecorationStandard.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDecorationStandard getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDecorationStandard)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A72B11A8") ,com.kingdee.eas.fdc.sellhouse.IDecorationStandard.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDecorationStandard getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDecorationStandard)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A72B11A8"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDecorationStandard getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDecorationStandard)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A72B11A8"));
    }
}