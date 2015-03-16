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

public class PublicCustomerFacade extends AbstractBizCtrl implements IPublicCustomerFacade
{
    public PublicCustomerFacade()
    {
        super();
        registerInterface(IPublicCustomerFacade.class, this);
    }
    public PublicCustomerFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IPublicCustomerFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C6D23C3C");
    }
    private PublicCustomerFacadeController getController() throws BOSException
    {
        return (PublicCustomerFacadeController)getBizController();
    }
    /**
     *个人客户转换成公共客户-User defined method
     */
    public void changeToPublicCustomer() throws BOSException
    {
        try {
            getController().changeToPublicCustomer(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *调用私转公存储过程-User defined method
     */
    public void callChangeToPublicProcedure() throws BOSException, EASBizException
    {
        try {
            getController().callChangeToPublicProcedure(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}