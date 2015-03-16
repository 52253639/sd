package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SalemanStatRptFacadeFactory
{
    private SalemanStatRptFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISalemanStatRptFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISalemanStatRptFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F849C8A4") ,com.kingdee.eas.fdc.sellhouse.ISalemanStatRptFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISalemanStatRptFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISalemanStatRptFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F849C8A4") ,com.kingdee.eas.fdc.sellhouse.ISalemanStatRptFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISalemanStatRptFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISalemanStatRptFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F849C8A4"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISalemanStatRptFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISalemanStatRptFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F849C8A4"));
    }
}