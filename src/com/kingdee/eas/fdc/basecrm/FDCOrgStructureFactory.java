package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCOrgStructureFactory
{
    private FDCOrgStructureFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCOrgStructure getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCOrgStructure)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6A12CC18") ,com.kingdee.eas.fdc.basecrm.IFDCOrgStructure.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.IFDCOrgStructure getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCOrgStructure)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6A12CC18") ,com.kingdee.eas.fdc.basecrm.IFDCOrgStructure.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCOrgStructure getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCOrgStructure)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6A12CC18"));
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCOrgStructure getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCOrgStructure)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6A12CC18"));
    }
}