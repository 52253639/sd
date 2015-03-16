package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.sellhouse.report.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;
import java.util.Set;

public class NewTimeAccountReportFacade extends CommRptBase implements INewTimeAccountReportFacade
{
    public NewTimeAccountReportFacade()
    {
        super();
        registerInterface(INewTimeAccountReportFacade.class, this);
    }
    public NewTimeAccountReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(INewTimeAccountReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("82363B3A");
    }
    private NewTimeAccountReportFacadeController getController() throws BOSException
    {
        return (NewTimeAccountReportFacadeController)getBizController();
    }
    /**
     *获取打印信息-User defined method
     *@param idSet id集合
     *@return
     */
    public IRowSet getPrintData(Set idSet) throws BOSException
    {
        try {
            return getController().getPrintData(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}