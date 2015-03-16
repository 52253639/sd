package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCReceiveBillRecordFactory
{
    private FDCReceiveBillRecordFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillRecord getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillRecord)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("732AA4F1") ,com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillRecord.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillRecord getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillRecord)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("732AA4F1") ,com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillRecord.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillRecord getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillRecord)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("732AA4F1"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillRecord getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFDCReceiveBillRecord)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("732AA4F1"));
    }
}