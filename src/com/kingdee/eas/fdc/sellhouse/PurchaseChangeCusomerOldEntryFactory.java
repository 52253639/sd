package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseChangeCusomerOldEntryFactory
{
    private PurchaseChangeCusomerOldEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerOldEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerOldEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7CA9B62B") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerOldEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerOldEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerOldEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7CA9B62B") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerOldEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerOldEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerOldEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7CA9B62B"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerOldEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerOldEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7CA9B62B"));
    }
}