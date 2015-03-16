package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ShePayTypeHis extends CoreBillEntryBase implements IShePayTypeHis
{
    public ShePayTypeHis()
    {
        super();
        registerInterface(IShePayTypeHis.class, this);
    }
    public ShePayTypeHis(Context ctx)
    {
        super(ctx);
        registerInterface(IShePayTypeHis.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A3043605");
    }
    private ShePayTypeHisController getController() throws BOSException
    {
        return (ShePayTypeHisController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ShePayTypeHisInfo getShePayTypeHisInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getShePayTypeHisInfo(getContext(), pk);
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
    public ShePayTypeHisInfo getShePayTypeHisInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getShePayTypeHisInfo(getContext(), pk, selector);
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
    public ShePayTypeHisInfo getShePayTypeHisInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getShePayTypeHisInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ShePayTypeHisCollection getShePayTypeHisCollection() throws BOSException
    {
        try {
            return getController().getShePayTypeHisCollection(getContext());
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
    public ShePayTypeHisCollection getShePayTypeHisCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getShePayTypeHisCollection(getContext(), view);
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
    public ShePayTypeHisCollection getShePayTypeHisCollection(String oql) throws BOSException
    {
        try {
            return getController().getShePayTypeHisCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}