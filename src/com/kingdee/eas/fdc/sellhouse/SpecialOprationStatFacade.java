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

public class SpecialOprationStatFacade extends AbstractBizCtrl implements ISpecialOprationStatFacade
{
    public SpecialOprationStatFacade()
    {
        super();
        registerInterface(ISpecialOprationStatFacade.class, this);
    }
    public SpecialOprationStatFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISpecialOprationStatFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3D8B4C30");
    }
    private SpecialOprationStatFacadeController getController() throws BOSException
    {
        return (SpecialOprationStatFacadeController)getBizController();
    }
    /**
     *获取特殊业务数据集-User defined method
     *@param paramMap 参数集
     *@return
     */
    public Map getSpecialStatData(Map paramMap) throws BOSException, EASBizException
    {
        try {
            return getController().getSpecialStatData(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}