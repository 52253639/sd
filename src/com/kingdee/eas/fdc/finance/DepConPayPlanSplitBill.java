package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCSplitBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.IFDCSplitBill;

public class DepConPayPlanSplitBill extends FDCSplitBill implements IDepConPayPlanSplitBill
{
    public DepConPayPlanSplitBill()
    {
        super();
        registerInterface(IDepConPayPlanSplitBill.class, this);
    }
    public DepConPayPlanSplitBill(Context ctx)
    {
        super(ctx);
        registerInterface(IDepConPayPlanSplitBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("AF140E32");
    }
    private DepConPayPlanSplitBillController getController() throws BOSException
    {
        return (DepConPayPlanSplitBillController)getBizController();
    }
    /**
     *批量取数-User defined method
     *@param param param
     *@return
     */
    public Map fetchData(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().fetchData(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}