package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.basedata.IFDCSplitBillEntry;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntry;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;

public class DepConPayPlanSplitEntry extends FDCSplitBillEntry implements IDepConPayPlanSplitEntry
{
    public DepConPayPlanSplitEntry()
    {
        super();
        registerInterface(IDepConPayPlanSplitEntry.class, this);
    }
    public DepConPayPlanSplitEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IDepConPayPlanSplitEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("339A63A7");
    }
    private DepConPayPlanSplitEntryController getController() throws BOSException
    {
        return (DepConPayPlanSplitEntryController)getBizController();
    }
}