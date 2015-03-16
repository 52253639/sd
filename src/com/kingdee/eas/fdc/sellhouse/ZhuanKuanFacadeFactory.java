package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ZhuanKuanFacadeFactory
{
    private ZhuanKuanFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IZhuanKuanFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IZhuanKuanFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3F9696EA") ,com.kingdee.eas.fdc.sellhouse.IZhuanKuanFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IZhuanKuanFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IZhuanKuanFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3F9696EA") ,com.kingdee.eas.fdc.sellhouse.IZhuanKuanFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IZhuanKuanFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IZhuanKuanFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3F9696EA"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IZhuanKuanFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IZhuanKuanFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3F9696EA"));
    }
}