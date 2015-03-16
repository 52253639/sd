package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.framework.bireport.BireportBaseFacade;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;

public class RptRoomFacade extends BireportBaseFacade implements IRptRoomFacade
{
    public RptRoomFacade()
    {
        super();
        registerInterface(IRptRoomFacade.class, this);
    }
    public RptRoomFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IRptRoomFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("739ABCF0");
    }
    private RptRoomFacadeController getController() throws BOSException
    {
        return (RptRoomFacadeController)getBizController();
    }
}