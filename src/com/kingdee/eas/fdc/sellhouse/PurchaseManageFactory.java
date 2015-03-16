package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseManageFactory
{
    private PurchaseManageFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseManage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseManage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A38DD561") ,com.kingdee.eas.fdc.sellhouse.IPurchaseManage.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseManage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseManage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A38DD561") ,com.kingdee.eas.fdc.sellhouse.IPurchaseManage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseManage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseManage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A38DD561"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseManage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseManage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A38DD561"));
    }
}