package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EffectImageElementEntryFactory
{
    private EffectImageElementEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IEffectImageElementEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEffectImageElementEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DFF59105") ,com.kingdee.eas.fdc.sellhouse.IEffectImageElementEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IEffectImageElementEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEffectImageElementEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DFF59105") ,com.kingdee.eas.fdc.sellhouse.IEffectImageElementEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IEffectImageElementEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEffectImageElementEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DFF59105"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IEffectImageElementEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEffectImageElementEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DFF59105"));
    }
}