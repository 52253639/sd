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
import java.lang.Object;
import com.kingdee.bos.framework.*;

public class RoomPropertyBookFacade extends AbstractBizCtrl implements IRoomPropertyBookFacade
{
    public RoomPropertyBookFacade()
    {
        super();
        registerInterface(IRoomPropertyBookFacade.class, this);
    }
    public RoomPropertyBookFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomPropertyBookFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("82CD556E");
    }
    private RoomPropertyBookFacadeController getController() throws BOSException
    {
        return (RoomPropertyBookFacadeController)getBizController();
    }
    /**
     *获取产权管理界面所需的数据-User defined method
     *@param selectedObj selectedObj
     *@param paramMap paramMap
     *@return
     */
    public Map getMutilRoomPropertyCollection(Object selectedObj, Map paramMap) throws BOSException, EASBizException
    {
        try {
            return getController().getMutilRoomPropertyCollection(getContext(), selectedObj, paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新房间产权-User defined method
     *@param paramMap paramMap
     */
    public void updateRoomProperty(Map paramMap) throws BOSException, EASBizException
    {
        try {
            getController().updateRoomProperty(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据房间对应的方案的状态交集显示-User defined method
     *@param paramMap paramMap
     *@return
     */
    public Map getStepAndMatarilState(Map paramMap) throws BOSException, EASBizException
    {
        try {
            return getController().getStepAndMatarilState(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}