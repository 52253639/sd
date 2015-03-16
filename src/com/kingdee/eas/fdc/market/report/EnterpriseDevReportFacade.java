package com.kingdee.eas.fdc.market.report;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.report.*;

public class EnterpriseDevReportFacade extends AbstractBizCtrl implements IEnterpriseDevReportFacade
{
    public EnterpriseDevReportFacade()
    {
        super();
        registerInterface(IEnterpriseDevReportFacade.class, this);
    }
    public EnterpriseDevReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IEnterpriseDevReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("89E3072C");
    }
    private EnterpriseDevReportFacadeController getController() throws BOSException
    {
        return (EnterpriseDevReportFacadeController)getBizController();
    }
    /**
     *企化费用数据-User defined method
     *@param map 企化费用参数
     *@return
     */
    public Map getTableList(Map map) throws BOSException
    {
        try {
            return getController().getTableList(getContext(), map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}