package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EffectImageFactory
{
    private EffectImageFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IEffectImage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEffectImage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9D6AA48F") ,com.kingdee.eas.fdc.sellhouse.IEffectImage.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IEffectImage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEffectImage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9D6AA48F") ,com.kingdee.eas.fdc.sellhouse.IEffectImage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IEffectImage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEffectImage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9D6AA48F"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IEffectImage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEffectImage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9D6AA48F"));
    }
}