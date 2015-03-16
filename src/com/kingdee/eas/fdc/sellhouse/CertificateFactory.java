package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CertificateFactory
{
    private CertificateFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICertificate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICertificate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AFFE93DC") ,com.kingdee.eas.fdc.sellhouse.ICertificate.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICertificate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICertificate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AFFE93DC") ,com.kingdee.eas.fdc.sellhouse.ICertificate.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICertificate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICertificate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AFFE93DC"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICertificate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICertificate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AFFE93DC"));
    }
}