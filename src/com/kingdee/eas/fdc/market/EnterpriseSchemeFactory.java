package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EnterpriseSchemeFactory
{
    private EnterpriseSchemeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IEnterpriseScheme getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterpriseScheme)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("56ED2C37") ,com.kingdee.eas.fdc.market.IEnterpriseScheme.class);
    }
    
    public static com.kingdee.eas.fdc.market.IEnterpriseScheme getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterpriseScheme)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("56ED2C37") ,com.kingdee.eas.fdc.market.IEnterpriseScheme.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IEnterpriseScheme getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterpriseScheme)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("56ED2C37"));
    }
    public static com.kingdee.eas.fdc.market.IEnterpriseScheme getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterpriseScheme)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("56ED2C37"));
    }
}