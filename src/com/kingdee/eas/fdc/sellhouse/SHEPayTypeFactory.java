package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHEPayTypeFactory
{
    private SHEPayTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEPayType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEPayType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DFF2AA4D") ,com.kingdee.eas.fdc.sellhouse.ISHEPayType.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHEPayType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEPayType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DFF2AA4D") ,com.kingdee.eas.fdc.sellhouse.ISHEPayType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEPayType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEPayType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DFF2AA4D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEPayType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEPayType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DFF2AA4D"));
    }
}