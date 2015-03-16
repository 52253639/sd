package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DocumentFactory
{
    private DocumentFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IDocument getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocument)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6477D5EC") ,com.kingdee.eas.fdc.market.IDocument.class);
    }
    
    public static com.kingdee.eas.fdc.market.IDocument getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocument)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6477D5EC") ,com.kingdee.eas.fdc.market.IDocument.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IDocument getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocument)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6477D5EC"));
    }
    public static com.kingdee.eas.fdc.market.IDocument getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocument)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6477D5EC"));
    }
}