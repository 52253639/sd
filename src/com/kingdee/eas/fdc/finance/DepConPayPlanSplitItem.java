package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;

public class DepConPayPlanSplitItem extends CoreBase implements IDepConPayPlanSplitItem
{
    public DepConPayPlanSplitItem()
    {
        super();
        registerInterface(IDepConPayPlanSplitItem.class, this);
    }
    public DepConPayPlanSplitItem(Context ctx)
    {
        super(ctx);
        registerInterface(IDepConPayPlanSplitItem.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("AF17653E");
    }
    private DepConPayPlanSplitItemController getController() throws BOSException
    {
        return (DepConPayPlanSplitItemController)getBizController();
    }
}