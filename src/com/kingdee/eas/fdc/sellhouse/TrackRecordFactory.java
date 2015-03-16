package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TrackRecordFactory
{
    private TrackRecordFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ITrackRecord getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ITrackRecord)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("83DD37C1") ,com.kingdee.eas.fdc.sellhouse.ITrackRecord.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ITrackRecord getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ITrackRecord)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("83DD37C1") ,com.kingdee.eas.fdc.sellhouse.ITrackRecord.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ITrackRecord getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ITrackRecord)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("83DD37C1"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ITrackRecord getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ITrackRecord)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("83DD37C1"));
    }
}