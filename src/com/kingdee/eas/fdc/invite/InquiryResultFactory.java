package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InquiryResultFactory
{
    private InquiryResultFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInquiryResult getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInquiryResult)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("00224420") ,com.kingdee.eas.fdc.invite.IInquiryResult.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInquiryResult getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInquiryResult)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("00224420") ,com.kingdee.eas.fdc.invite.IInquiryResult.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInquiryResult getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInquiryResult)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("00224420"));
    }
    public static com.kingdee.eas.fdc.invite.IInquiryResult getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInquiryResult)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("00224420"));
    }
}