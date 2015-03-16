package com.kingdee.eas.fdc.invite.markesupplier.rpt;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierStockReportFacadeFactory
{
    private MarketSupplierStockReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.rpt.IMarketSupplierStockReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.rpt.IMarketSupplierStockReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0E050863") ,com.kingdee.eas.fdc.invite.markesupplier.rpt.IMarketSupplierStockReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.rpt.IMarketSupplierStockReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.rpt.IMarketSupplierStockReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0E050863") ,com.kingdee.eas.fdc.invite.markesupplier.rpt.IMarketSupplierStockReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.rpt.IMarketSupplierStockReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.rpt.IMarketSupplierStockReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0E050863"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.rpt.IMarketSupplierStockReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.rpt.IMarketSupplierStockReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0E050863"));
    }
}