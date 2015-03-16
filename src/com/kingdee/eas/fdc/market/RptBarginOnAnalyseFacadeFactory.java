package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RptBarginOnAnalyseFacadeFactory
{
    private RptBarginOnAnalyseFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IRptBarginOnAnalyseFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IRptBarginOnAnalyseFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AC781DB0") ,com.kingdee.eas.fdc.market.IRptBarginOnAnalyseFacade.class);
    }
    
    public static com.kingdee.eas.fdc.market.IRptBarginOnAnalyseFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IRptBarginOnAnalyseFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AC781DB0") ,com.kingdee.eas.fdc.market.IRptBarginOnAnalyseFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IRptBarginOnAnalyseFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IRptBarginOnAnalyseFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AC781DB0"));
    }
    public static com.kingdee.eas.fdc.market.IRptBarginOnAnalyseFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IRptBarginOnAnalyseFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AC781DB0"));
    }
}