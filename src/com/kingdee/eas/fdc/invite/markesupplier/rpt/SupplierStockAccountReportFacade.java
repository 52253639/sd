package com.kingdee.eas.fdc.invite.markesupplier.rpt;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;
import com.kingdee.eas.fdc.invite.markesupplier.rpt.*;

public class SupplierStockAccountReportFacade extends CommRptBase implements ISupplierStockAccountReportFacade
{
    public SupplierStockAccountReportFacade()
    {
        super();
        registerInterface(ISupplierStockAccountReportFacade.class, this);
    }
    public SupplierStockAccountReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierStockAccountReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5104110A");
    }
    private SupplierStockAccountReportFacadeController getController() throws BOSException
    {
        return (SupplierStockAccountReportFacadeController)getBizController();
    }
}