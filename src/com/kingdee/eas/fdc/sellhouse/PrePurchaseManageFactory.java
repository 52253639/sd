package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PrePurchaseManageFactory
{
    private PrePurchaseManageFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseManage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseManage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("10AC79CE") ,com.kingdee.eas.fdc.sellhouse.IPrePurchaseManage.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseManage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseManage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("10AC79CE") ,com.kingdee.eas.fdc.sellhouse.IPrePurchaseManage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseManage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseManage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("10AC79CE"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPrePurchaseManage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPrePurchaseManage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("10AC79CE"));
    }
}