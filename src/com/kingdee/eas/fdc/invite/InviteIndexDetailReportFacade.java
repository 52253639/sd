package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;

public class InviteIndexDetailReportFacade extends CommRptBase implements IInviteIndexDetailReportFacade
{
    public InviteIndexDetailReportFacade()
    {
        super();
        registerInterface(IInviteIndexDetailReportFacade.class, this);
    }
    public InviteIndexDetailReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IInviteIndexDetailReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("44D61864");
    }
    private InviteIndexDetailReportFacadeController getController() throws BOSException
    {
        return (InviteIndexDetailReportFacadeController)getBizController();
    }
}