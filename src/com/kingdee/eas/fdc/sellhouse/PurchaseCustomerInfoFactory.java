package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseCustomerInfoFactory
{
    private PurchaseCustomerInfoFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseCustomerInfo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseCustomerInfo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("54C584A8") ,com.kingdee.eas.fdc.sellhouse.IPurchaseCustomerInfo.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseCustomerInfo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseCustomerInfo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("54C584A8") ,com.kingdee.eas.fdc.sellhouse.IPurchaseCustomerInfo.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseCustomerInfo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseCustomerInfo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("54C584A8"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseCustomerInfo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseCustomerInfo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("54C584A8"));
    }
}