package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EquipmentEntryFactory
{
    private EquipmentEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IEquipmentEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IEquipmentEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("124D916D") ,com.kingdee.eas.fdc.tenancy.IEquipmentEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IEquipmentEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IEquipmentEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("124D916D") ,com.kingdee.eas.fdc.tenancy.IEquipmentEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IEquipmentEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IEquipmentEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("124D916D"));
    }
    public static com.kingdee.eas.fdc.tenancy.IEquipmentEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IEquipmentEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("124D916D"));
    }
}