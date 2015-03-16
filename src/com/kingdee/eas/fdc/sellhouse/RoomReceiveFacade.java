package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.sellhouse.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;

public class RoomReceiveFacade extends AbstractBizCtrl implements IRoomReceiveFacade
{
    public RoomReceiveFacade()
    {
        super();
        registerInterface(IRoomReceiveFacade.class, this);
    }
    public RoomReceiveFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomReceiveFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("17010EC7");
    }
    private RoomReceiveFacadeController getController() throws BOSException
    {
        return (RoomReceiveFacadeController)getBizController();
    }
    /**
     *获取房间收款情况表数据-User defined method
     *@param map 过滤条件
     *@return
     */
    public Map getRoomReceiveData(Map map) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomReceiveData(getContext(), map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}