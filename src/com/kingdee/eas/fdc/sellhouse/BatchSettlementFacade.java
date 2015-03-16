package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import java.util.Date;
import com.kingdee.eas.framework.CoreBaseCollection;

public class BatchSettlementFacade extends AbstractBizCtrl implements IBatchSettlementFacade
{
    public BatchSettlementFacade()
    {
        super();
        registerInterface(IBatchSettlementFacade.class, this);
    }
    public BatchSettlementFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IBatchSettlementFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0E7E4A82");
    }
    private BatchSettlementFacadeController getController() throws BOSException
    {
        return (BatchSettlementFacadeController)getBizController();
    }
    /**
     *生成收款单-User defined method
     *@param recBillList 收款单集合
     *@param payList 应收集合
     *@param recDate 收款日期
     */
    public void generateRecBil(CoreBaseCollection recBillList, CoreBaseCollection payList, Date recDate) throws BOSException, EASBizException
    {
        try {
            getController().generateRecBil(getContext(), recBillList, payList, recDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}