package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CommerceChanceDataTypeFactory
{
    private CommerceChanceDataTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChanceDataType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChanceDataType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B7442DAE") ,com.kingdee.eas.fdc.sellhouse.ICommerceChanceDataType.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChanceDataType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChanceDataType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B7442DAE") ,com.kingdee.eas.fdc.sellhouse.ICommerceChanceDataType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChanceDataType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChanceDataType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B7442DAE"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICommerceChanceDataType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICommerceChanceDataType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B7442DAE"));
    }
}