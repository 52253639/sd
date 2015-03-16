package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InsteadCollectOutBillEntryFactory
{
    private InsteadCollectOutBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8DAFA3C0") ,com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8DAFA3C0") ,com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8DAFA3C0"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IInsteadCollectOutBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8DAFA3C0"));
    }
}