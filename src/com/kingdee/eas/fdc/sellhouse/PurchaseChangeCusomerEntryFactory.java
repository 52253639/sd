package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseChangeCusomerEntryFactory
{
    private PurchaseChangeCusomerEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B5B620D2") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B5B620D2") ,com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B5B620D2"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPurchaseChangeCusomerEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B5B620D2"));
    }
}