package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DocumentAnswerFactory
{
    private DocumentAnswerFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IDocumentAnswer getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocumentAnswer)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B7F1E0CA") ,com.kingdee.eas.fdc.market.IDocumentAnswer.class);
    }
    
    public static com.kingdee.eas.fdc.market.IDocumentAnswer getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocumentAnswer)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B7F1E0CA") ,com.kingdee.eas.fdc.market.IDocumentAnswer.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IDocumentAnswer getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocumentAnswer)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B7F1E0CA"));
    }
    public static com.kingdee.eas.fdc.market.IDocumentAnswer getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocumentAnswer)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B7F1E0CA"));
    }
}