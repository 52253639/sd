package com.kingdee.eas.fdc.market.report;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.report.*;

public class ProjectAreaSellReportFacade extends AbstractBizCtrl implements IProjectAreaSellReportFacade
{
    public ProjectAreaSellReportFacade()
    {
        super();
        registerInterface(IProjectAreaSellReportFacade.class, this);
    }
    public ProjectAreaSellReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectAreaSellReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C94DD970");
    }
    private ProjectAreaSellReportFacadeController getController() throws BOSException
    {
        return (ProjectAreaSellReportFacadeController)getBizController();
    }
    /**
     *获取报表数值-User defined method
     *@param params 传入参数
     *@return
     */
    public Map getDatas(Map params) throws BOSException, EASBizException
    {
        try {
            return getController().getDatas(getContext(), params);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}