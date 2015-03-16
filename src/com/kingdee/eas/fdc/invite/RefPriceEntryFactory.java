package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RefPriceEntryFactory
{
    private RefPriceEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IRefPriceEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IRefPriceEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8B68BCF8") ,com.kingdee.eas.fdc.invite.IRefPriceEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IRefPriceEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IRefPriceEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8B68BCF8") ,com.kingdee.eas.fdc.invite.IRefPriceEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IRefPriceEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IRefPriceEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8B68BCF8"));
    }
    public static com.kingdee.eas.fdc.invite.IRefPriceEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IRefPriceEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8B68BCF8"));
    }
}