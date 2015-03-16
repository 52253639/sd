package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCReceivingBillFactory
{
    private FDCReceivingBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCReceivingBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCReceivingBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F12182FE") ,com.kingdee.eas.fdc.basecrm.IFDCReceivingBill.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.IFDCReceivingBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCReceivingBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F12182FE") ,com.kingdee.eas.fdc.basecrm.IFDCReceivingBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCReceivingBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCReceivingBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F12182FE"));
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCReceivingBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCReceivingBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F12182FE"));
    }
}