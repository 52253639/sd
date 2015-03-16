package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public class ConstructPlanIndexEntry extends CoreBillEntryBase implements IConstructPlanIndexEntry
{
    public ConstructPlanIndexEntry()
    {
        super();
        registerInterface(IConstructPlanIndexEntry.class, this);
    }
    public ConstructPlanIndexEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IConstructPlanIndexEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9D443B49");
    }
    private ConstructPlanIndexEntryController getController() throws BOSException
    {
        return (ConstructPlanIndexEntryController)getBizController();
    }
}