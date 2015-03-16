package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MoneySubjectContrastFactory
{
    private MoneySubjectContrastFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMoneySubjectContrast getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMoneySubjectContrast)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9BA14AC9") ,com.kingdee.eas.fdc.sellhouse.IMoneySubjectContrast.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMoneySubjectContrast getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMoneySubjectContrast)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9BA14AC9") ,com.kingdee.eas.fdc.sellhouse.IMoneySubjectContrast.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMoneySubjectContrast getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMoneySubjectContrast)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9BA14AC9"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMoneySubjectContrast getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMoneySubjectContrast)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9BA14AC9"));
    }
}