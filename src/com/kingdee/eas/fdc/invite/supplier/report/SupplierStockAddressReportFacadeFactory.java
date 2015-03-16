package com.kingdee.eas.fdc.invite.supplier.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierStockAddressReportFacadeFactory
{
    private SupplierStockAddressReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAddressReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAddressReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("48E735C7") ,com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAddressReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAddressReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAddressReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("48E735C7") ,com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAddressReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAddressReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAddressReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("48E735C7"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAddressReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.report.ISupplierStockAddressReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("48E735C7"));
    }
}