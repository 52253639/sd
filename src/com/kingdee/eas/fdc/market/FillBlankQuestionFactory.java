package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FillBlankQuestionFactory
{
    private FillBlankQuestionFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IFillBlankQuestion getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IFillBlankQuestion)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2389FAE6") ,com.kingdee.eas.fdc.market.IFillBlankQuestion.class);
    }
    
    public static com.kingdee.eas.fdc.market.IFillBlankQuestion getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IFillBlankQuestion)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2389FAE6") ,com.kingdee.eas.fdc.market.IFillBlankQuestion.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IFillBlankQuestion getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IFillBlankQuestion)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2389FAE6"));
    }
    public static com.kingdee.eas.fdc.market.IFillBlankQuestion getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IFillBlankQuestion)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2389FAE6"));
    }
}