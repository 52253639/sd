package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHERevBillFactory
{
    private SHERevBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.ISHERevBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DFC518D6") ,com.kingdee.eas.fdc.basecrm.ISHERevBill.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.ISHERevBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DFC518D6") ,com.kingdee.eas.fdc.basecrm.ISHERevBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.ISHERevBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DFC518D6"));
    }
    public static com.kingdee.eas.fdc.basecrm.ISHERevBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DFC518D6"));
    }
}