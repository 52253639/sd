package com.kingdee.eas.fdc.invite.supplier.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierStockReportFacadeFactory
{
    private SupplierStockReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7AC205C9") ,com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7AC205C9") ,com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7AC205C9"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7AC205C9"));
    }
}