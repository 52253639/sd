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

public class TranStateHis extends CoreBase implements ITranStateHis
{
    public TranStateHis()
    {
        super();
        registerInterface(ITranStateHis.class, this);
    }
    public TranStateHis(Context ctx)
    {
        super(ctx);
        registerInterface(ITranStateHis.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9A4933E7");
    }
    private TranStateHisController getController() throws BOSException
    {
        return (TranStateHisController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TranStateHisInfo getTranStateHisInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTranStateHisInfo(getContext(), pk);
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
    public TranStateHisInfo getTranStateHisInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTranStateHisInfo(getContext(), pk, selector);
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
    public TranStateHisInfo getTranStateHisInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTranStateHisInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TranStateHisCollection getTranStateHisCollection() throws BOSException
    {
        try {
            return getController().getTranStateHisCollection(getContext());
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
    public TranStateHisCollection getTranStateHisCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTranStateHisCollection(getContext(), view);
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
    public TranStateHisCollection getTranStateHisCollection(String oql) throws BOSException
    {
        try {
            return getController().getTranStateHisCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}