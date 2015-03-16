package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CloseCompanyPeriodFacadeFactory
{
    private CloseCompanyPeriodFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.ICloseCompanyPeriodFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ICloseCompanyPeriodFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("273B39CF") ,com.kingdee.eas.fdc.finance.ICloseCompanyPeriodFacade.class);
    }
    
    public static com.kingdee.eas.fdc.finance.ICloseCompanyPeriodFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ICloseCompanyPeriodFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("273B39CF") ,com.kingdee.eas.fdc.finance.ICloseCompanyPeriodFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.ICloseCompanyPeriodFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ICloseCompanyPeriodFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("273B39CF"));
    }
    public static com.kingdee.eas.fdc.finance.ICloseCompanyPeriodFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ICloseCompanyPeriodFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("273B39CF"));
    }
}