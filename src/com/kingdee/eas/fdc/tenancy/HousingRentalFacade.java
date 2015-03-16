package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.tenancy.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;

public class HousingRentalFacade extends AbstractBizCtrl implements IHousingRentalFacade
{
    public HousingRentalFacade()
    {
        super();
        registerInterface(IHousingRentalFacade.class, this);
    }
    public HousingRentalFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IHousingRentalFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3BE2EF12");
    }
    private HousingRentalFacadeController getController() throws BOSException
    {
        return (HousingRentalFacadeController)getBizController();
    }
    /**
     *getHousingRentalList-User defined method
     *@param param 参数集合
     *@return
     */
    public Map getHousingRentalList(Map param) throws BOSException
    {
        try {
            return getController().getHousingRentalList(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *修复日单价-User defined method
     *@param param 参数集合
     *@return
     */
    public boolean ReSetDayPrice(Map param) throws BOSException
    {
        try {
            return getController().ReSetDayPrice(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}