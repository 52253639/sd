package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SpecialDiscountEntryFactory
{
    private SpecialDiscountEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISpecialDiscountEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISpecialDiscountEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8D90D2D3") ,com.kingdee.eas.fdc.sellhouse.ISpecialDiscountEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISpecialDiscountEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISpecialDiscountEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8D90D2D3") ,com.kingdee.eas.fdc.sellhouse.ISpecialDiscountEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISpecialDiscountEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISpecialDiscountEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8D90D2D3"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISpecialDiscountEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISpecialDiscountEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8D90D2D3"));
    }
}