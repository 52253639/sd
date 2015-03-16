package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ActionEvaluateFactory
{
    private ActionEvaluateFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IActionEvaluate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IActionEvaluate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("887E5D00") ,com.kingdee.eas.fdc.market.IActionEvaluate.class);
    }
    
    public static com.kingdee.eas.fdc.market.IActionEvaluate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IActionEvaluate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("887E5D00") ,com.kingdee.eas.fdc.market.IActionEvaluate.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IActionEvaluate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IActionEvaluate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("887E5D00"));
    }
    public static com.kingdee.eas.fdc.market.IActionEvaluate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IActionEvaluate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("887E5D00"));
    }
}