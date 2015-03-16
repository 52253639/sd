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

public class FDCDepConPayPlanItem extends CoreBase implements IFDCDepConPayPlanItem
{
    public FDCDepConPayPlanItem()
    {
        super();
        registerInterface(IFDCDepConPayPlanItem.class, this);
    }
    public FDCDepConPayPlanItem(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCDepConPayPlanItem.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F28BEC5B");
    }
    private FDCDepConPayPlanItemController getController() throws BOSException
    {
        return (FDCDepConPayPlanItemController)getBizController();
    }
}