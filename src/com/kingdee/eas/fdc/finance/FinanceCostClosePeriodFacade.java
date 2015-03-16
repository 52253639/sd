package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.bos.framework.*;
import java.util.List;

public class FinanceCostClosePeriodFacade extends AbstractBizCtrl implements IFinanceCostClosePeriodFacade
{
    public FinanceCostClosePeriodFacade()
    {
        super();
        registerInterface(IFinanceCostClosePeriodFacade.class, this);
    }
    public FinanceCostClosePeriodFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFinanceCostClosePeriodFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F4FE345B");
    }
    private FinanceCostClosePeriodFacadeController getController() throws BOSException
    {
        return (FinanceCostClosePeriodFacadeController)getBizController();
    }
    /**
     *将财务成本拆分数据及中间表数据（有效的）存入月结数据表-User defined method
     *@param idList 工程项目ID集合
     *@return
     */
    public String traceFinanceCostClose(List idList) throws BOSException, EASBizException
    {
        try {
            return getController().traceFinanceCostClose(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *工程项目冻结-User defined method
     *@param idList 工程项目ID集合
     */
    public void frozenProject(List idList) throws BOSException, EASBizException
    {
        try {
            getController().frozenProject(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反财务月结-User defined method
     *@param idList 工程项目ID集合
     *@return
     */
    public String antiCostClose(List idList) throws BOSException, EASBizException
    {
        try {
            return getController().antiCostClose(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}