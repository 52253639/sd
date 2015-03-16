package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class VoucherForDynamicFactory
{
    private VoucherForDynamicFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IVoucherForDynamic getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IVoucherForDynamic)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F5E6400D") ,com.kingdee.eas.fdc.aimcost.IVoucherForDynamic.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IVoucherForDynamic getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IVoucherForDynamic)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F5E6400D") ,com.kingdee.eas.fdc.aimcost.IVoucherForDynamic.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IVoucherForDynamic getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IVoucherForDynamic)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F5E6400D"));
    }
    public static com.kingdee.eas.fdc.aimcost.IVoucherForDynamic getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IVoucherForDynamic)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F5E6400D"));
    }
}