package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProductSettleBillFactory
{
    private ProductSettleBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProductSettleBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProductSettleBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5CA3F112") ,com.kingdee.eas.fdc.finance.IProductSettleBill.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProductSettleBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProductSettleBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5CA3F112") ,com.kingdee.eas.fdc.finance.IProductSettleBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProductSettleBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProductSettleBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5CA3F112"));
    }
    public static com.kingdee.eas.fdc.finance.IProductSettleBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProductSettleBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5CA3F112"));
    }
}