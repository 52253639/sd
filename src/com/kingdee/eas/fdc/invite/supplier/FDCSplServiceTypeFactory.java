package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSplServiceTypeFactory
{
    private FDCSplServiceTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplServiceType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplServiceType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1A4CD9ED") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplServiceType.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplServiceType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplServiceType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1A4CD9ED") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplServiceType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplServiceType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplServiceType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1A4CD9ED"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplServiceType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplServiceType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1A4CD9ED"));
    }
}