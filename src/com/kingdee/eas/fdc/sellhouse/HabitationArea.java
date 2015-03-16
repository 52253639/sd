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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class HabitationArea extends TreeBase implements IHabitationArea
{
    public HabitationArea()
    {
        super();
        registerInterface(IHabitationArea.class, this);
    }
    public HabitationArea(Context ctx)
    {
        super(ctx);
        registerInterface(IHabitationArea.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DE3809C9");
    }
    private HabitationAreaController getController() throws BOSException
    {
        return (HabitationAreaController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public HabitationAreaInfo getHabitationAreaInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getHabitationAreaInfo(getContext(), pk);
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
    public HabitationAreaInfo getHabitationAreaInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getHabitationAreaInfo(getContext(), pk, selector);
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
    public HabitationAreaInfo getHabitationAreaInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getHabitationAreaInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public HabitationAreaCollection getHabitationAreaCollection() throws BOSException
    {
        try {
            return getController().getHabitationAreaCollection(getContext());
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
    public HabitationAreaCollection getHabitationAreaCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getHabitationAreaCollection(getContext(), view);
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
    public HabitationAreaCollection getHabitationAreaCollection(String oql) throws BOSException
    {
        try {
            return getController().getHabitationAreaCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}