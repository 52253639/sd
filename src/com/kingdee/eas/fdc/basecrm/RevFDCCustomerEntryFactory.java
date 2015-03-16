package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RevFDCCustomerEntryFactory
{
    private RevFDCCustomerEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.IRevFDCCustomerEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IRevFDCCustomerEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0F394DB6") ,com.kingdee.eas.fdc.basecrm.IRevFDCCustomerEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.IRevFDCCustomerEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IRevFDCCustomerEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0F394DB6") ,com.kingdee.eas.fdc.basecrm.IRevFDCCustomerEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.IRevFDCCustomerEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IRevFDCCustomerEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0F394DB6"));
    }
    public static com.kingdee.eas.fdc.basecrm.IRevFDCCustomerEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IRevFDCCustomerEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0F394DB6"));
    }
}