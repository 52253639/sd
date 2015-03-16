package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSplQualificationAuditTemplateFactory
{
    private FDCSplQualificationAuditTemplateFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditTemplate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditTemplate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1D9975A8") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditTemplate.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditTemplate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditTemplate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1D9975A8") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditTemplate.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditTemplate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditTemplate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1D9975A8"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditTemplate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditTemplate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1D9975A8"));
    }
}