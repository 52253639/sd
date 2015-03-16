package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ShareSaleManFactory
{
    private ShareSaleManFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IShareSaleMan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareSaleMan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8E71290F") ,com.kingdee.eas.fdc.sellhouse.IShareSaleMan.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IShareSaleMan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareSaleMan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8E71290F") ,com.kingdee.eas.fdc.sellhouse.IShareSaleMan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IShareSaleMan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareSaleMan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8E71290F"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IShareSaleMan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareSaleMan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8E71290F"));
    }
}