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
import com.kingdee.bos.framework.*;

public class KeyIndexReportFacade extends AbstractBizCtrl implements IKeyIndexReportFacade
{
    public KeyIndexReportFacade()
    {
        super();
        registerInterface(IKeyIndexReportFacade.class, this);
    }
    public KeyIndexReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IKeyIndexReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3629848D");
    }
    private KeyIndexReportFacadeController getController() throws BOSException
    {
        return (KeyIndexReportFacadeController)getBizController();
    }
}