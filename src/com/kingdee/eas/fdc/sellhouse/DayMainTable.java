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

public class DayMainTable extends CoreBase implements IDayMainTable
{
    public DayMainTable()
    {
        super();
        registerInterface(IDayMainTable.class, this);
    }
    public DayMainTable(Context ctx)
    {
        super(ctx);
        registerInterface(IDayMainTable.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("82E5D3B4");
    }
    private DayMainTableController getController() throws BOSException
    {
        return (DayMainTableController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public DayMainTableInfo getDayMainTableInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDayMainTableInfo(getContext(), pk);
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
    public DayMainTableInfo getDayMainTableInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDayMainTableInfo(getContext(), pk, selector);
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
    public DayMainTableInfo getDayMainTableInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDayMainTableInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DayMainTableCollection getDayMainTableCollection() throws BOSException
    {
        try {
            return getController().getDayMainTableCollection(getContext());
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
    public DayMainTableCollection getDayMainTableCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDayMainTableCollection(getContext(), view);
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
    public DayMainTableCollection getDayMainTableCollection(String oql) throws BOSException
    {
        try {
            return getController().getDayMainTableCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}