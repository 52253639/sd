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

public class SellStatRoomRptNewFacade extends AbstractBizCtrl implements ISellStatRoomRptNewFacade
{
    public SellStatRoomRptNewFacade()
    {
        super();
        registerInterface(ISellStatRoomRptNewFacade.class, this);
    }
    public SellStatRoomRptNewFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISellStatRoomRptNewFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E4FBBC40");
    }
    private SellStatRoomRptNewFacadeController getController() throws BOSException
    {
        return (SellStatRoomRptNewFacadeController)getBizController();
    }
    /**
     *获取房间认购单销售明细数据-User defined method
     *@param paramMap 参数集合
     *@return
     */
    public Map getSellStatRoomData(Map paramMap) throws BOSException, EASBizException
    {
        try {
            return getController().getSellStatRoomData(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}