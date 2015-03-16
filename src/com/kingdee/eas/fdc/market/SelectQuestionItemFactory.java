package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SelectQuestionItemFactory
{
    private SelectQuestionItemFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.ISelectQuestionItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISelectQuestionItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("162BAAC6") ,com.kingdee.eas.fdc.market.ISelectQuestionItem.class);
    }
    
    public static com.kingdee.eas.fdc.market.ISelectQuestionItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISelectQuestionItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("162BAAC6") ,com.kingdee.eas.fdc.market.ISelectQuestionItem.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.ISelectQuestionItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISelectQuestionItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("162BAAC6"));
    }
    public static com.kingdee.eas.fdc.market.ISelectQuestionItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISelectQuestionItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("162BAAC6"));
    }
}