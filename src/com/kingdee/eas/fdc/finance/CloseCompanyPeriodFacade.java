package com.kingdee.eas.fdc.finance;

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
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.bos.framework.*;

public class CloseCompanyPeriodFacade extends AbstractBizCtrl implements ICloseCompanyPeriodFacade
{
    public CloseCompanyPeriodFacade()
    {
        super();
        registerInterface(ICloseCompanyPeriodFacade.class, this);
    }
    public CloseCompanyPeriodFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ICloseCompanyPeriodFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("273B39CF");
    }
    private CloseCompanyPeriodFacadeController getController() throws BOSException
    {
        return (CloseCompanyPeriodFacadeController)getBizController();
    }
    /**
     *财务组织成本期间到下一期-User defined method
     *@param companyId 财务组织ID
     */
    public void closeCompany(String companyId) throws BOSException, EASBizException
    {
        try {
            getController().closeCompany(getContext(), companyId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *公司期间反结帐回到上一期间-User defined method
     *@param companyId 财务组织ID
     */
    public void antiCloseCompany(String companyId) throws BOSException, EASBizException
    {
        try {
            getController().antiCloseCompany(getContext(), companyId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}