package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SelectQuestionFactory
{
    private SelectQuestionFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.ISelectQuestion getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISelectQuestion)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6D037D13") ,com.kingdee.eas.fdc.market.ISelectQuestion.class);
    }
    
    public static com.kingdee.eas.fdc.market.ISelectQuestion getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISelectQuestion)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6D037D13") ,com.kingdee.eas.fdc.market.ISelectQuestion.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.ISelectQuestion getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISelectQuestion)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6D037D13"));
    }
    public static com.kingdee.eas.fdc.market.ISelectQuestion getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISelectQuestion)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6D037D13"));
    }
}