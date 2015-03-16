package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SellStatRoomRptNewFacadeFactory
{
    private SellStatRoomRptNewFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellStatRoomRptNewFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellStatRoomRptNewFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E4FBBC40") ,com.kingdee.eas.fdc.sellhouse.ISellStatRoomRptNewFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISellStatRoomRptNewFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellStatRoomRptNewFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E4FBBC40") ,com.kingdee.eas.fdc.sellhouse.ISellStatRoomRptNewFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellStatRoomRptNewFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellStatRoomRptNewFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E4FBBC40"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISellStatRoomRptNewFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISellStatRoomRptNewFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E4FBBC40"));
    }
}