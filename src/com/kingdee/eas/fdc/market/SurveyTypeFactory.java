package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SurveyTypeFactory
{
    private SurveyTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.ISurveyType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISurveyType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2E418245") ,com.kingdee.eas.fdc.market.ISurveyType.class);
    }
    
    public static com.kingdee.eas.fdc.market.ISurveyType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISurveyType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2E418245") ,com.kingdee.eas.fdc.market.ISurveyType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.ISurveyType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISurveyType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2E418245"));
    }
    public static com.kingdee.eas.fdc.market.ISurveyType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISurveyType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2E418245"));
    }
}