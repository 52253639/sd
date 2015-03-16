package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RptQuestionPaperFacadeFactory
{
    private RptQuestionPaperFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IRptQuestionPaperFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IRptQuestionPaperFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F1AEA6BB") ,com.kingdee.eas.fdc.market.IRptQuestionPaperFacade.class);
    }
    
    public static com.kingdee.eas.fdc.market.IRptQuestionPaperFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IRptQuestionPaperFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F1AEA6BB") ,com.kingdee.eas.fdc.market.IRptQuestionPaperFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IRptQuestionPaperFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IRptQuestionPaperFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F1AEA6BB"));
    }
    public static com.kingdee.eas.fdc.market.IRptQuestionPaperFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IRptQuestionPaperFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F1AEA6BB"));
    }
}