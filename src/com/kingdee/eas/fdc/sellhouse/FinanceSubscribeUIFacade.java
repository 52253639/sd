package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;

public class FinanceSubscribeUIFacade extends AbstractBizCtrl implements IFinanceSubscribeUIFacade
{
    public FinanceSubscribeUIFacade()
    {
        super();
        registerInterface(IFinanceSubscribeUIFacade.class, this);
    }
    public FinanceSubscribeUIFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFinanceSubscribeUIFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BCEE00B9");
    }
    private FinanceSubscribeUIFacadeController getController() throws BOSException
    {
        return (FinanceSubscribeUIFacadeController)getBizController();
    }
    /**
     *查询收款纪录-User defined method
     *@param saleLongNumber 销售组织长编码
     *@param sellProjectID 工程项目ID
     *@param beginQueryDate 起始时间
     *@param endQueryDate 结束时间
     *@return
     */
    public IRowSet getSubscribe(String saleLongNumber, String sellProjectID, String beginQueryDate, String endQueryDate) throws BOSException, EASBizException
    {
        try {
            return getController().getSubscribe(getContext(), saleLongNumber, sellProjectID, beginQueryDate, endQueryDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}