package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DefaultAmountCreatFactory
{
    private DefaultAmountCreatFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreat getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreat)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5BA83C2B") ,com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreat.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreat getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreat)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5BA83C2B") ,com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreat.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreat getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreat)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5BA83C2B"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreat getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IDefaultAmountCreat)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5BA83C2B"));
    }
}