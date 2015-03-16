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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class CompensateRoomListHis extends CoreBase implements ICompensateRoomListHis
{
    public CompensateRoomListHis()
    {
        super();
        registerInterface(ICompensateRoomListHis.class, this);
    }
    public CompensateRoomListHis(Context ctx)
    {
        super(ctx);
        registerInterface(ICompensateRoomListHis.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("21EE0F07");
    }
    private CompensateRoomListHisController getController() throws BOSException
    {
        return (CompensateRoomListHisController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public CompensateRoomListHisInfo getCompensateRoomListHisInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensateRoomListHisInfo(getContext(), pk);
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
    public CompensateRoomListHisInfo getCompensateRoomListHisInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensateRoomListHisInfo(getContext(), pk, selector);
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
    public CompensateRoomListHisInfo getCompensateRoomListHisInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensateRoomListHisInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CompensateRoomListHisCollection getCompensateRoomListHisCollection() throws BOSException
    {
        try {
            return getController().getCompensateRoomListHisCollection(getContext());
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
    public CompensateRoomListHisCollection getCompensateRoomListHisCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCompensateRoomListHisCollection(getContext(), view);
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
    public CompensateRoomListHisCollection getCompensateRoomListHisCollection(String oql) throws BOSException
    {
        try {
            return getController().getCompensateRoomListHisCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}