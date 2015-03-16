package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCCusBaseDataFactory
{
    private FDCCusBaseDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCCusBaseData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCCusBaseData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C46F3933") ,com.kingdee.eas.fdc.basecrm.IFDCCusBaseData.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.IFDCCusBaseData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCCusBaseData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C46F3933") ,com.kingdee.eas.fdc.basecrm.IFDCCusBaseData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCCusBaseData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCCusBaseData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C46F3933"));
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCCusBaseData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCCusBaseData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C46F3933"));
    }
}