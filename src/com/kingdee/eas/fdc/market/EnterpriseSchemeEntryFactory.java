package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EnterpriseSchemeEntryFactory
{
    private EnterpriseSchemeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IEnterpriseSchemeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterpriseSchemeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6C48F67B") ,com.kingdee.eas.fdc.market.IEnterpriseSchemeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IEnterpriseSchemeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterpriseSchemeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6C48F67B") ,com.kingdee.eas.fdc.market.IEnterpriseSchemeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IEnterpriseSchemeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterpriseSchemeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6C48F67B"));
    }
    public static com.kingdee.eas.fdc.market.IEnterpriseSchemeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterpriseSchemeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6C48F67B"));
    }
}