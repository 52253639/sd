package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHEFunctionChooseRoomSetFactory
{
    private SHEFunctionChooseRoomSetFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEFunctionChooseRoomSet getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEFunctionChooseRoomSet)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("39F9A183") ,com.kingdee.eas.fdc.sellhouse.ISHEFunctionChooseRoomSet.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHEFunctionChooseRoomSet getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEFunctionChooseRoomSet)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("39F9A183") ,com.kingdee.eas.fdc.sellhouse.ISHEFunctionChooseRoomSet.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEFunctionChooseRoomSet getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEFunctionChooseRoomSet)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("39F9A183"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEFunctionChooseRoomSet getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEFunctionChooseRoomSet)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("39F9A183"));
    }
}