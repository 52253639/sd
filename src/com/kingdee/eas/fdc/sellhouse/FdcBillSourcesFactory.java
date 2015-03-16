package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FdcBillSourcesFactory
{
    private FdcBillSourcesFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IFdcBillSources getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFdcBillSources)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("544F10A7") ,com.kingdee.eas.fdc.sellhouse.IFdcBillSources.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IFdcBillSources getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFdcBillSources)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("544F10A7") ,com.kingdee.eas.fdc.sellhouse.IFdcBillSources.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IFdcBillSources getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFdcBillSources)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("544F10A7"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IFdcBillSources getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFdcBillSources)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("544F10A7"));
    }
}