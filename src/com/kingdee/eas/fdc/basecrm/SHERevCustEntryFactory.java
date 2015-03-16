package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHERevCustEntryFactory
{
    private SHERevCustEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.ISHERevCustEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevCustEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8B9C0270") ,com.kingdee.eas.fdc.basecrm.ISHERevCustEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.ISHERevCustEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevCustEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8B9C0270") ,com.kingdee.eas.fdc.basecrm.ISHERevCustEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.ISHERevCustEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevCustEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8B9C0270"));
    }
    public static com.kingdee.eas.fdc.basecrm.ISHERevCustEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevCustEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8B9C0270"));
    }
}