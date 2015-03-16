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

public class ProductTypeSellStateFacade extends AbstractBizCtrl implements IProductTypeSellStateFacade
{
    public ProductTypeSellStateFacade()
    {
        super();
        registerInterface(IProductTypeSellStateFacade.class, this);
    }
    public ProductTypeSellStateFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IProductTypeSellStateFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("51E8C8EB");
    }
    private ProductTypeSellStateFacadeController getController() throws BOSException
    {
        return (ProductTypeSellStateFacadeController)getBizController();
    }
    /**
     *获取产品销售类型统计数据-User defined method
     *@param paramMap 参数集
     *@return
     */
    public Map getProductSellDate(Map paramMap) throws BOSException, EASBizException
    {
        try {
            return getController().getProductSellDate(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}