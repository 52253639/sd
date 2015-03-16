package com.kingdee.eas.fdc.market;

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
import com.kingdee.eas.fdc.market.app.*;

public class DynamicTotalValueFacade extends CommRptBase implements IDynamicTotalValueFacade
{
    public DynamicTotalValueFacade()
    {
        super();
        registerInterface(IDynamicTotalValueFacade.class, this);
    }
    public DynamicTotalValueFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IDynamicTotalValueFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8F1A54F5");
    }
    private DynamicTotalValueFacadeController getController() throws BOSException
    {
        return (DynamicTotalValueFacadeController)getBizController();
    }
}