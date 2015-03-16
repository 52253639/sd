package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.contract.programming.app.*;
import com.kingdee.eas.fdc.basedata.RetValue;

public class ProgDynaTableFacade extends AbstractBizCtrl implements IProgDynaTableFacade
{
    public ProgDynaTableFacade()
    {
        super();
        registerInterface(IProgDynaTableFacade.class, this);
    }
    public ProgDynaTableFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IProgDynaTableFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4F5771F6");
    }
    private ProgDynaTableFacadeController getController() throws BOSException
    {
        return (ProgDynaTableFacadeController)getBizController();
    }
    /**
     *得到规划动态表数据-User defined method
     *@param paramValue paramValue
     *@return
     */
    public RetValue getCollectionProgDynTab(ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionProgDynTab(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}