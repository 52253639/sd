package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BusinessOverViewFactory
{
    private BusinessOverViewFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBusinessOverView getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBusinessOverView)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D0101654") ,com.kingdee.eas.fdc.sellhouse.IBusinessOverView.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBusinessOverView getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBusinessOverView)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D0101654") ,com.kingdee.eas.fdc.sellhouse.IBusinessOverView.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBusinessOverView getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBusinessOverView)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D0101654"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBusinessOverView getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBusinessOverView)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D0101654"));
    }
}