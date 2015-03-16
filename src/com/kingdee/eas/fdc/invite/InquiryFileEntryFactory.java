package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InquiryFileEntryFactory
{
    private InquiryFileEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInquiryFileEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInquiryFileEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B0259173") ,com.kingdee.eas.fdc.invite.IInquiryFileEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInquiryFileEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInquiryFileEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B0259173") ,com.kingdee.eas.fdc.invite.IInquiryFileEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInquiryFileEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInquiryFileEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B0259173"));
    }
    public static com.kingdee.eas.fdc.invite.IInquiryFileEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInquiryFileEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B0259173"));
    }
}