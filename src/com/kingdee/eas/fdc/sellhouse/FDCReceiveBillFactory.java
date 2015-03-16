package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCReceiveBillFactory
{
    private FDCReceiveBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IFDCReceiveBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFDCReceiveBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9412AC80") ,com.kingdee.eas.fdc.sellhouse.IFDCReceiveBill.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IFDCReceiveBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFDCReceiveBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9412AC80") ,com.kingdee.eas.fdc.sellhouse.IFDCReceiveBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IFDCReceiveBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFDCReceiveBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9412AC80"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IFDCReceiveBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFDCReceiveBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9412AC80"));
    }
}