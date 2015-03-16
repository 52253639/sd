package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ShareSellProjectFactory
{
    private ShareSellProjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IShareSellProject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareSellProject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("94CAEAE3") ,com.kingdee.eas.fdc.sellhouse.IShareSellProject.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IShareSellProject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareSellProject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("94CAEAE3") ,com.kingdee.eas.fdc.sellhouse.IShareSellProject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IShareSellProject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareSellProject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("94CAEAE3"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IShareSellProject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IShareSellProject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("94CAEAE3"));
    }
}