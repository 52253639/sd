package com.kingdee.eas.fdc.invite.markesupplier.rpt;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierStockAccountReportFacadeFactory
{
    private SupplierStockAccountReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.rpt.ISupplierStockAccountReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.rpt.ISupplierStockAccountReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5104110A") ,com.kingdee.eas.fdc.invite.markesupplier.rpt.ISupplierStockAccountReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.rpt.ISupplierStockAccountReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.rpt.ISupplierStockAccountReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5104110A") ,com.kingdee.eas.fdc.invite.markesupplier.rpt.ISupplierStockAccountReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.rpt.ISupplierStockAccountReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.rpt.ISupplierStockAccountReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5104110A"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.rpt.ISupplierStockAccountReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.rpt.ISupplierStockAccountReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5104110A"));
    }
}