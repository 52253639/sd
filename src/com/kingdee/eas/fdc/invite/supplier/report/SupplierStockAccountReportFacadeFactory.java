package com.kingdee.eas.fdc.invite.supplier.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierStockAccountReportFacadeFactory
{
    private SupplierStockAccountReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAccountReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAccountReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B1DA9680") ,com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAccountReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAccountReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAccountReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B1DA9680") ,com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAccountReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAccountReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAccountReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B1DA9680"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAccountReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAccountReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B1DA9680"));
    }
}