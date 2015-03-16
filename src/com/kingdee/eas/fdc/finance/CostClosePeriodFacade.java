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

public class CostClosePeriodFacade extends AbstractBizCtrl implements ICostClosePeriodFacade
{
    public CostClosePeriodFacade()
    {
        super();
        registerInterface(ICostClosePeriodFacade.class, this);
    }
    public CostClosePeriodFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ICostClosePeriodFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("99FC09D7");
    }
    private CostClosePeriodFacadeController getController() throws BOSException
    {
        return (CostClosePeriodFacadeController)getBizController();
    }
    /**
     *处理工程项目下没有使用最新拆分的付款拆分（作废进入待处理合同或者某一个拆分不是，直接作废该付款拆分）-User defined method
     *@param idList 工程项目ID集合
     */
    public void tracePayment(List idList) throws BOSException, EASBizException
    {
        try {
            getController().tracePayment(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *检查当前期间的单据是否都已经拆分-User defined method
     *@param idList 工程项目ID集合
     */
    public void checkCostSplit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().checkCostSplit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *将成本拆分数据及中间表数据（有效的）存入月结数据表-User defined method
     *@param idList 工程项目ID集合
     *@return
     */
    public String traceCostClose(List idList) throws BOSException, EASBizException
    {
        try {
            return getController().traceCostClose(getContext(), idList);
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
     *反成本月结-User defined method
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
    /**
     *删除月结数据表中的数据-User defined method
     *@param contractId 合同/无文本合同
     *@param periodId 期间
     */
    public void delete(String contractId, String periodId) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), contractId, periodId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}