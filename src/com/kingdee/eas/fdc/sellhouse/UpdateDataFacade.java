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
import com.kingdee.bos.framework.*;

public class UpdateDataFacade extends AbstractBizCtrl implements IUpdateDataFacade
{
    public UpdateDataFacade()
    {
        super();
        registerInterface(IUpdateDataFacade.class, this);
    }
    public UpdateDataFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IUpdateDataFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7B3A6368");
    }
    private UpdateDataFacadeController getController() throws BOSException
    {
        return (UpdateDataFacadeController)getBizController();
    }
    /**
     *楼层数据是 2009.10.06 后在 EAS 6.0 版本上加上去的-User defined method
     *@return
     */
    public boolean updateBuildingFloorForRoom() throws BOSException, EASBizException
    {
        try {
            return getController().updateBuildingFloorForRoom(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *检测是否要更新房间上的楼层数据-User defined method
     *@return
     */
    public boolean isNeedUpdateBuildingFloorForRoom() throws BOSException, EASBizException
    {
        try {
            return getController().isNeedUpdateBuildingFloorForRoom(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}