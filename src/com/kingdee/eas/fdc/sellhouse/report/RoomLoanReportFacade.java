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
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;

public class RoomLoanReportFacade extends CommRptBase implements IRoomLoanReportFacade
{
    public RoomLoanReportFacade()
    {
        super();
        registerInterface(IRoomLoanReportFacade.class, this);
    }
    public RoomLoanReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomLoanReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4CE74745");
    }
    private RoomLoanReportFacadeController getController() throws BOSException
    {
        return (RoomLoanReportFacadeController)getBizController();
    }
}