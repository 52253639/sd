package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DocumentSubjectFactory
{
    private DocumentSubjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IDocumentSubject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocumentSubject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0976D5A0") ,com.kingdee.eas.fdc.market.IDocumentSubject.class);
    }
    
    public static com.kingdee.eas.fdc.market.IDocumentSubject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocumentSubject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0976D5A0") ,com.kingdee.eas.fdc.market.IDocumentSubject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IDocumentSubject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocumentSubject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0976D5A0"));
    }
    public static com.kingdee.eas.fdc.market.IDocumentSubject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocumentSubject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0976D5A0"));
    }
}