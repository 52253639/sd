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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ObjectBase;
import com.kingdee.eas.framework.IObjectBase;

public class FDCProjectCostDetail extends ObjectBase implements IFDCProjectCostDetail
{
    public FDCProjectCostDetail()
    {
        super();
        registerInterface(IFDCProjectCostDetail.class, this);
    }
    public FDCProjectCostDetail(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCProjectCostDetail.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("10ACC969");
    }
    private FDCProjectCostDetailController getController() throws BOSException
    {
        return (FDCProjectCostDetailController)getBizController();
    }
}