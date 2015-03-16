package com.kingdee.eas.fdc.invite.supplier.report;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.supplier.report.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;

public class SupplierStockReportFacade extends CommRptBase implements ISupplierStockReportFacade
{
    public SupplierStockReportFacade()
    {
        super();
        registerInterface(ISupplierStockReportFacade.class, this);
    }
    public SupplierStockReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierStockReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7AC205C9");
    }
    private SupplierStockReportFacadeController getController() throws BOSException
    {
        return (SupplierStockReportFacadeController)getBizController();
    }
}