package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public class OpReportEntryBase extends CoreBillEntryBase implements IOpReportEntryBase
{
    public OpReportEntryBase()
    {
        super();
        registerInterface(IOpReportEntryBase.class, this);
    }
    public OpReportEntryBase(Context ctx)
    {
        super(ctx);
        registerInterface(IOpReportEntryBase.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C966A658");
    }
    private OpReportEntryBaseController getController() throws BOSException
    {
        return (OpReportEntryBaseController)getBizController();
    }
}