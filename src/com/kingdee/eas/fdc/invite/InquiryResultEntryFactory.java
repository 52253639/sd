package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InquiryResultEntryFactory
{
    private InquiryResultEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInquiryResultEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInquiryResultEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("09436832") ,com.kingdee.eas.fdc.invite.IInquiryResultEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInquiryResultEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInquiryResultEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("09436832") ,com.kingdee.eas.fdc.invite.IInquiryResultEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInquiryResultEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInquiryResultEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("09436832"));
    }
    public static com.kingdee.eas.fdc.invite.IInquiryResultEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInquiryResultEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("09436832"));
    }
}