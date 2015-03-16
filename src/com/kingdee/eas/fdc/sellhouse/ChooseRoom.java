package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ChooseRoom extends FDCBill implements IChooseRoom
{
    public ChooseRoom()
    {
        super();
        registerInterface(IChooseRoom.class, this);
    }
    public ChooseRoom(Context ctx)
    {
        super(ctx);
        registerInterface(IChooseRoom.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B45858AD");
    }
    private ChooseRoomController getController() throws BOSException
    {
        return (ChooseRoomController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ChooseRoomInfo getChooseRoomInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getChooseRoomInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public ChooseRoomInfo getChooseRoomInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getChooseRoomInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public ChooseRoomInfo getChooseRoomInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getChooseRoomInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ChooseRoomCollection getChooseRoomCollection() throws BOSException
    {
        try {
            return getController().getChooseRoomCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public ChooseRoomCollection getChooseRoomCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getChooseRoomCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public ChooseRoomCollection getChooseRoomCollection(String oql) throws BOSException
    {
        try {
            return getController().getChooseRoomCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *是否失效-User defined method
     *@param billID 单据ID
     *@return
     */
    public boolean isValid(String billID) throws BOSException, EASBizException
    {
        try {
            return getController().isValid(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取消选房-User defined method
     *@param billID 单据ID
     */
    public void cancelChooseRoom(String billID) throws BOSException, EASBizException
    {
        try {
            getController().cancelChooseRoom(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *转预定(转签约，转认购)-User defined method
     *@param billID 单据ID
     *@param chooseRoomState 选房单据状态
     */
    public void updateTrans(String billID, ChooseRoomStateEnum chooseRoomState) throws BOSException, EASBizException
    {
        try {
            getController().updateTrans(getContext(), billID, chooseRoomState);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}