package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DocumentOptionFactory
{
    private DocumentOptionFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IDocumentOption getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocumentOption)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CFF22981") ,com.kingdee.eas.fdc.market.IDocumentOption.class);
    }
    
    public static com.kingdee.eas.fdc.market.IDocumentOption getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocumentOption)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CFF22981") ,com.kingdee.eas.fdc.market.IDocumentOption.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IDocumentOption getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocumentOption)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CFF22981"));
    }
    public static com.kingdee.eas.fdc.market.IDocumentOption getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocumentOption)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CFF22981"));
    }
}