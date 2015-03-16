package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DocumentItemFactory
{
    private DocumentItemFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IDocumentItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocumentItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("17A5EF1F") ,com.kingdee.eas.fdc.market.IDocumentItem.class);
    }
    
    public static com.kingdee.eas.fdc.market.IDocumentItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocumentItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("17A5EF1F") ,com.kingdee.eas.fdc.market.IDocumentItem.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IDocumentItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocumentItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("17A5EF1F"));
    }
    public static com.kingdee.eas.fdc.market.IDocumentItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IDocumentItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("17A5EF1F"));
    }
}