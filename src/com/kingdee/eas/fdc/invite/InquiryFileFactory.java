package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InquiryFileFactory
{
    private InquiryFileFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInquiryFile getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInquiryFile)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3FE9963F") ,com.kingdee.eas.fdc.invite.IInquiryFile.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInquiryFile getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInquiryFile)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3FE9963F") ,com.kingdee.eas.fdc.invite.IInquiryFile.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInquiryFile getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInquiryFile)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3FE9963F"));
    }
    public static com.kingdee.eas.fdc.invite.IInquiryFile getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInquiryFile)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3FE9963F"));
    }
}