package com.kingdee.eas.fdc.contract;

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
import com.kingdee.eas.fdc.contract.app.*;

public class PayStatusReportFacade extends CommRptBase implements IPayStatusReportFacade
{
    public PayStatusReportFacade()
    {
        super();
        registerInterface(IPayStatusReportFacade.class, this);
    }
    public PayStatusReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IPayStatusReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3200576D");
    }
    private PayStatusReportFacadeController getController() throws BOSException
    {
        return (PayStatusReportFacadeController)getBizController();
    }
}