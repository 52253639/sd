package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHERevbillTwoEntryFactory
{
    private SHERevbillTwoEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.ISHERevbillTwoEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevbillTwoEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0AE8D85C") ,com.kingdee.eas.fdc.basecrm.ISHERevbillTwoEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.ISHERevbillTwoEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevbillTwoEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0AE8D85C") ,com.kingdee.eas.fdc.basecrm.ISHERevbillTwoEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.ISHERevbillTwoEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevbillTwoEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0AE8D85C"));
    }
    public static com.kingdee.eas.fdc.basecrm.ISHERevbillTwoEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.ISHERevbillTwoEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0AE8D85C"));
    }
}