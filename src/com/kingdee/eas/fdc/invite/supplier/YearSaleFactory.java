package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class YearSaleFactory
{
    private YearSaleFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IYearSale getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IYearSale)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A790C122") ,com.kingdee.eas.fdc.invite.supplier.IYearSale.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IYearSale getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IYearSale)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A790C122") ,com.kingdee.eas.fdc.invite.supplier.IYearSale.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IYearSale getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IYearSale)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A790C122"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IYearSale getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IYearSale)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A790C122"));
    }
}