package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FlatLayerTypeFactory
{
    private FlatLayerTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IFlatLayerType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IFlatLayerType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F71E9681") ,com.kingdee.eas.fdc.market.IFlatLayerType.class);
    }
    
    public static com.kingdee.eas.fdc.market.IFlatLayerType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IFlatLayerType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F71E9681") ,com.kingdee.eas.fdc.market.IFlatLayerType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IFlatLayerType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IFlatLayerType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F71E9681"));
    }
    public static com.kingdee.eas.fdc.market.IFlatLayerType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IFlatLayerType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F71E9681"));
    }
}