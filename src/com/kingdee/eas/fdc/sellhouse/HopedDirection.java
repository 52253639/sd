package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class HopedDirection extends FDCDataBase implements IHopedDirection
{
    public HopedDirection()
    {
        super();
        registerInterface(IHopedDirection.class, this);
    }
    public HopedDirection(Context ctx)
    {
        super(ctx);
        registerInterface(IHopedDirection.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4505BD52");
    }
    private HopedDirectionController getController() throws BOSException
    {
        return (HopedDirectionController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public HopedDirectionInfo getHopedDirectionInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getHopedDirectionInfo(getContext(), pk);
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
    public HopedDirectionInfo getHopedDirectionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getHopedDirectionInfo(getContext(), pk, selector);
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
    public HopedDirectionInfo getHopedDirectionInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getHopedDirectionInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public HopedDirectionCollection getHopedDirectionCollection() throws BOSException
    {
        try {
            return getController().getHopedDirectionCollection(getContext());
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
    public HopedDirectionCollection getHopedDirectionCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getHopedDirectionCollection(getContext(), view);
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
    public HopedDirectionCollection getHopedDirectionCollection(String oql) throws BOSException
    {
        try {
            return getController().getHopedDirectionCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}