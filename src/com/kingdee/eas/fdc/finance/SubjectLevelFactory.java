package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SubjectLevelFactory
{
    private SubjectLevelFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.ISubjectLevel getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISubjectLevel)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("596E1847") ,com.kingdee.eas.fdc.finance.ISubjectLevel.class);
    }
    
    public static com.kingdee.eas.fdc.finance.ISubjectLevel getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISubjectLevel)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("596E1847") ,com.kingdee.eas.fdc.finance.ISubjectLevel.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.ISubjectLevel getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISubjectLevel)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("596E1847"));
    }
    public static com.kingdee.eas.fdc.finance.ISubjectLevel getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISubjectLevel)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("596E1847"));
    }
}