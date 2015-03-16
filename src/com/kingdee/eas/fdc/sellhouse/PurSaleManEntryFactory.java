package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurSaleManEntryFactory
{
    private PurSaleManEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurSaleManEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurSaleManEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1D0F2C11") ,com.kingdee.eas.fdc.sellhouse.IPurSaleManEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurSaleManEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurSaleManEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1D0F2C11") ,com.kingdee.eas.fdc.sellhouse.IPurSaleManEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurSaleManEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurSaleManEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1D0F2C11"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurSaleManEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurSaleManEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1D0F2C11"));
    }
}