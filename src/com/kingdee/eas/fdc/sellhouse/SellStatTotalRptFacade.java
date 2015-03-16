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
import java.util.Map;
import com.kingdee.bos.framework.*;

public class SellStatTotalRptFacade extends AbstractBizCtrl implements ISellStatTotalRptFacade
{
    public SellStatTotalRptFacade()
    {
        super();
        registerInterface(ISellStatTotalRptFacade.class, this);
    }
    public SellStatTotalRptFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISellStatTotalRptFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D2D7824D");
    }
    private SellStatTotalRptFacadeController getController() throws BOSException
    {
        return (SellStatTotalRptFacadeController)getBizController();
    }
    /**
     *获取销售汇总数据-User defined method
     *@param paramMap 参数集合
     *@return
     */
    public Map getSellStatData(Map paramMap) throws BOSException, EASBizException
    {
        try {
            return getController().getSellStatData(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}