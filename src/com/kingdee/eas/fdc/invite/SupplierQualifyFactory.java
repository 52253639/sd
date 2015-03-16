package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierQualifyFactory
{
    private SupplierQualifyFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.ISupplierQualify getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ISupplierQualify)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8A82503D") ,com.kingdee.eas.fdc.invite.ISupplierQualify.class);
    }
    
    public static com.kingdee.eas.fdc.invite.ISupplierQualify getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ISupplierQualify)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8A82503D") ,com.kingdee.eas.fdc.invite.ISupplierQualify.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.ISupplierQualify getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ISupplierQualify)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8A82503D"));
    }
    public static com.kingdee.eas.fdc.invite.ISupplierQualify getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ISupplierQualify)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8A82503D"));
    }
}