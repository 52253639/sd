package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSplChangeHistroyFactory
{
    private FDCSplChangeHistroyFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplChangeHistroy getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplChangeHistroy)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("308F4088") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplChangeHistroy.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplChangeHistroy getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplChangeHistroy)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("308F4088") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplChangeHistroy.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplChangeHistroy getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplChangeHistroy)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("308F4088"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplChangeHistroy getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplChangeHistroy)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("308F4088"));
    }
}