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

public class ChangeRoom extends FDCBill implements IChangeRoom
{
    public ChangeRoom()
    {
        super();
        registerInterface(IChangeRoom.class, this);
    }
    public ChangeRoom(Context ctx)
    {
        super(ctx);
        registerInterface(IChangeRoom.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BCA781C6");
    }
    private ChangeRoomController getController() throws BOSException
    {
        return (ChangeRoomController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public ChangeRoomInfo getChangeRoomInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeRoomInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public ChangeRoomInfo getChangeRoomInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeRoomInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public ChangeRoomInfo getChangeRoomInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeRoomInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ChangeRoomCollection getChangeRoomCollection() throws BOSException
    {
        try {
            return getController().getChangeRoomCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view view
     *@return
     */
    public ChangeRoomCollection getChangeRoomCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getChangeRoomCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql oql
     *@return
     */
    public ChangeRoomCollection getChangeRoomCollection(String oql) throws BOSException
    {
        try {
            return getController().getChangeRoomCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *换房结算-User defined method
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