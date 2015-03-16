package com.kingdee.eas.fdc.market;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;
import com.kingdee.eas.fdc.market.app.*;

public class DynamicValueReportFacade extends CommRptBase implements IDynamicValueReportFacade
{
    public DynamicValueReportFacade()
    {
        super();
        registerInterface(IDynamicValueReportFacade.class, this);
    }
    public DynamicValueReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IDynamicValueReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("16644751");
    }
    private DynamicValueReportFacadeController getController() throws BOSException
    {
        return (DynamicValueReportFacadeController)getBizController();
    }
    /**
     *Пьее-User defined method
     */
    public void photo() throws BOSException, EASBizException
    {
        try {
            getController().photo(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}