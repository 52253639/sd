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

public class SupplierStockAddressReportFacade extends CommRptBase implements ISupplierStockAddressReportFacade
{
    public SupplierStockAddressReportFacade()
    {
        super();
        registerInterface(ISupplierStockAddressReportFacade.class, this);
    }
    public SupplierStockAddressReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierStockAddressReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("48E735C7");
    }
    private SupplierStockAddressReportFacadeController getController() throws BOSException
    {
        return (SupplierStockAddressReportFacadeController)getBizController();
    }
}