package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AskQuestionFactory
{
    private AskQuestionFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IAskQuestion getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IAskQuestion)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("55D20F0E") ,com.kingdee.eas.fdc.market.IAskQuestion.class);
    }
    
    public static com.kingdee.eas.fdc.market.IAskQuestion getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IAskQuestion)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("55D20F0E") ,com.kingdee.eas.fdc.market.IAskQuestion.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IAskQuestion getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IAskQuestion)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("55D20F0E"));
    }
    public static com.kingdee.eas.fdc.market.IAskQuestion getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IAskQuestion)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("55D20F0E"));
    }
}