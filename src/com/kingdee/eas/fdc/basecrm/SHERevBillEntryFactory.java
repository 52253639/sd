package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHERevBillEntryFactory
{
    private SHERevBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.ISHERevBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("98D7153C") ,com.kingdee.eas.fdc.basecrm.ISHERevBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.ISHERevBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("98D7153C") ,com.kingdee.eas.fdc.basecrm.ISHERevBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.ISHERevBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("98D7153C"));
    }
    public static com.kingdee.eas.fdc.basecrm.ISHERevBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("98D7153C"));
    }
}