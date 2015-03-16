package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PolicyManageFactory
{
    private PolicyManageFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IPolicyManage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPolicyManage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F13F3588") ,com.kingdee.eas.fdc.market.IPolicyManage.class);
    }
    
    public static com.kingdee.eas.fdc.market.IPolicyManage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPolicyManage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F13F3588") ,com.kingdee.eas.fdc.market.IPolicyManage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IPolicyManage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPolicyManage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F13F3588"));
    }
    public static com.kingdee.eas.fdc.market.IPolicyManage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPolicyManage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F13F3588"));
    }
}