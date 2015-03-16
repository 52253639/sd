package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SubjectLevelSubjectFactory
{
    private SubjectLevelSubjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.ISubjectLevelSubject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISubjectLevelSubject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AE5D46E5") ,com.kingdee.eas.fdc.finance.ISubjectLevelSubject.class);
    }
    
    public static com.kingdee.eas.fdc.finance.ISubjectLevelSubject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISubjectLevelSubject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AE5D46E5") ,com.kingdee.eas.fdc.finance.ISubjectLevelSubject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.ISubjectLevelSubject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISubjectLevelSubject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AE5D46E5"));
    }
    public static com.kingdee.eas.fdc.finance.ISubjectLevelSubject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.ISubjectLevelSubject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AE5D46E5"));
    }
}