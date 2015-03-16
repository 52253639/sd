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

public class SellStatRptFacade extends AbstractBizCtrl implements ISellStatRptFacade
{
    public SellStatRptFacade()
    {
        super();
        registerInterface(ISellStatRptFacade.class, this);
    }
    public SellStatRptFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISellStatRptFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("89089E8F");
    }
    private SellStatRptFacadeController getController() throws BOSException
    {
        return (SellStatRptFacadeController)getBizController();
    }
    /**
     *获取销售统计表数据-User defined method
     *@param map 根据传入参数查询数据
     *@return
     */
    public Map getSellStatRptData(Map map) throws BOSException, EASBizException
    {
        try {
            return getController().getSellStatRptData(getContext(), map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}