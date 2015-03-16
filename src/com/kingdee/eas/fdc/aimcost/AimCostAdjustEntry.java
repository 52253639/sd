package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.eas.framework.IBillEntryBase;
import com.kingdee.eas.framework.CoreBaseCollection;

public class AimCostAdjustEntry extends BillEntryBase implements IAimCostAdjustEntry
{
    public AimCostAdjustEntry()
    {
        super();
        registerInterface(IAimCostAdjustEntry.class, this);
    }
    public AimCostAdjustEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IAimCostAdjustEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("663575A8");
    }
    private AimCostAdjustEntryController getController() throws BOSException
    {
        return (AimCostAdjustEntryController)getBizController();
    }
}