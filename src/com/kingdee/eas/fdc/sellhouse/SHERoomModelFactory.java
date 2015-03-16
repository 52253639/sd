package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHERoomModelFactory
{
    private SHERoomModelFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHERoomModel getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHERoomModel)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9C08FE99") ,com.kingdee.eas.fdc.sellhouse.ISHERoomModel.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHERoomModel getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHERoomModel)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9C08FE99") ,com.kingdee.eas.fdc.sellhouse.ISHERoomModel.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHERoomModel getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHERoomModel)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9C08FE99"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHERoomModel getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHERoomModel)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9C08FE99"));
    }
}