package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class JoinDataEntryFactory
{
    private JoinDataEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IJoinDataEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IJoinDataEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("530FA9E3") ,com.kingdee.eas.fdc.sellhouse.IJoinDataEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IJoinDataEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IJoinDataEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("530FA9E3") ,com.kingdee.eas.fdc.sellhouse.IJoinDataEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IJoinDataEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IJoinDataEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("530FA9E3"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IJoinDataEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IJoinDataEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("530FA9E3"));
    }
}