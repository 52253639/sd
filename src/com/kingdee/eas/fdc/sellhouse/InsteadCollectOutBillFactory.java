package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InsteadCollectOutBillFactory
{
    private InsteadCollectOutBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F2A687D2") ,com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBill.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F2A687D2") ,com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F2A687D2"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F2A687D2"));
    }
}