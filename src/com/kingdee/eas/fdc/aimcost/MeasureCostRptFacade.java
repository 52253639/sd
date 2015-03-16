package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.eas.fdc.basedata.RetValue;

public class MeasureCostRptFacade extends AbstractBizCtrl implements IMeasureCostRptFacade
{
    public MeasureCostRptFacade()
    {
        super();
        registerInterface(IMeasureCostRptFacade.class, this);
    }
    public MeasureCostRptFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IMeasureCostRptFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1E3157FC");
    }
    private MeasureCostRptFacadeController getController() throws BOSException
    {
        return (MeasureCostRptFacadeController)getBizController();
    }
    /**
     *项目各阶段目标成本对比表-User defined method
     *@param param param
     *@return
     */
    public RetValue getMeasureCosts(ParamValue param) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureCosts(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}