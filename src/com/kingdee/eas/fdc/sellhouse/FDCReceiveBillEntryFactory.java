package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCReceiveBillEntryFactory
{
    private FDCReceiveBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("87253BD2") ,com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("87253BD2") ,com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("87253BD2"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("87253BD2"));
    }
}