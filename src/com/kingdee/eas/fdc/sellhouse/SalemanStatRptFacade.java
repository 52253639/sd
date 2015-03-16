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

public class SalemanStatRptFacade extends AbstractBizCtrl implements ISalemanStatRptFacade
{
    public SalemanStatRptFacade()
    {
        super();
        registerInterface(ISalemanStatRptFacade.class, this);
    }
    public SalemanStatRptFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISalemanStatRptFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F849C8A4");
    }
    private SalemanStatRptFacadeController getController() throws BOSException
    {
        return (SalemanStatRptFacadeController)getBizController();
    }
    /**
     *获取置业顾问统计数据-User defined method
     *@param dateMap 过滤数据集
     *@return
     */
    public Map getSalemanStatData(Map dateMap) throws BOSException, EASBizException
    {
        try {
            return getController().getSalemanStatData(getContext(), dateMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}