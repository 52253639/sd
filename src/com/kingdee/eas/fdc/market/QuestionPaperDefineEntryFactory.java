package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuestionPaperDefineEntryFactory
{
    private QuestionPaperDefineEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IQuestionPaperDefineEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionPaperDefineEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1BB8B922") ,com.kingdee.eas.fdc.market.IQuestionPaperDefineEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IQuestionPaperDefineEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionPaperDefineEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1BB8B922") ,com.kingdee.eas.fdc.market.IQuestionPaperDefineEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IQuestionPaperDefineEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionPaperDefineEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1BB8B922"));
    }
    public static com.kingdee.eas.fdc.market.IQuestionPaperDefineEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IQuestionPaperDefineEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1BB8B922"));
    }
}