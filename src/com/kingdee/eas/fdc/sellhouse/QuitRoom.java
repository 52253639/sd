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
import com.kingdee.bos.util.BOSUuid;

public class QuitRoom extends FDCBill implements IQuitRoom
{
    public QuitRoom()
    {
        super();
        registerInterface(IQuitRoom.class, this);
    }
    public QuitRoom(Context ctx)
    {
        super(ctx);
        registerInterface(IQuitRoom.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("771FC2CB");
    }
    private QuitRoomController getController() throws BOSException
    {
        return (QuitRoomController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public QuitRoomInfo getQuitRoomInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitRoomInfo(getContext(), pk);
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
    public QuitRoomInfo getQuitRoomInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitRoomInfo(getContext(), pk, selector);
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
    public QuitRoomInfo getQuitRoomInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitRoomInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public QuitRoomCollection getQuitRoomCollection() throws BOSException
    {
        try {
            return getController().getQuitRoomCollection(getContext());
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
    public QuitRoomCollection getQuitRoomCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getQuitRoomCollection(getContext(), view);
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
    public QuitRoomCollection getQuitRoomCollection(String oql) throws BOSException
    {
        try {
            return getController().getQuitRoomCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *执行退房-User defined method
     *@param id ID
     *@return
     */
    public boolean exeQuit(String id) throws BOSException, EASBizException
    {
        try {
            return getController().exeQuit(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *工作流退款节点-User defined method
     *@param billId billId
     */
    public void receiveBill(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().receiveBill(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *退房结算-User defined method
     *@param billId ID
     */
    public void settleMent(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().settleMent(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}