package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TranBusinessOverViewFactory
{
    private TranBusinessOverViewFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ITranBusinessOverView getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ITranBusinessOverView)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7FB3321F") ,com.kingdee.eas.fdc.sellhouse.ITranBusinessOverView.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ITranBusinessOverView getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ITranBusinessOverView)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7FB3321F") ,com.kingdee.eas.fdc.sellhouse.ITranBusinessOverView.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ITranBusinessOverView getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ITranBusinessOverView)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7FB3321F"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ITranBusinessOverView getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ITranBusinessOverView)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7FB3321F"));
    }
}