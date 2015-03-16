package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCReceivingBillEntryFactory
{
    private FDCReceivingBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCReceivingBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCReceivingBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("26EEC414") ,com.kingdee.eas.fdc.basecrm.IFDCReceivingBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.IFDCReceivingBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCReceivingBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("26EEC414") ,com.kingdee.eas.fdc.basecrm.IFDCReceivingBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCReceivingBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCReceivingBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("26EEC414"));
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCReceivingBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCReceivingBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("26EEC414"));
    }
}