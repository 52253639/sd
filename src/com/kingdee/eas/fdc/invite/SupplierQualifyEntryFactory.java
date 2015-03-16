package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierQualifyEntryFactory
{
    private SupplierQualifyEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.ISupplierQualifyEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ISupplierQualifyEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A6876635") ,com.kingdee.eas.fdc.invite.ISupplierQualifyEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.ISupplierQualifyEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ISupplierQualifyEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A6876635") ,com.kingdee.eas.fdc.invite.ISupplierQualifyEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.ISupplierQualifyEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ISupplierQualifyEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A6876635"));
    }
    public static com.kingdee.eas.fdc.invite.ISupplierQualifyEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ISupplierQualifyEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A6876635"));
    }
}