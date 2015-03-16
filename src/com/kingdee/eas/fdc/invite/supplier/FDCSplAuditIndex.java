package com.kingdee.eas.fdc.invite.supplier;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class FDCSplAuditIndex extends FDCDataBase implements IFDCSplAuditIndex
{
    public FDCSplAuditIndex()
    {
        super();
        registerInterface(IFDCSplAuditIndex.class, this);
    }
    public FDCSplAuditIndex(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSplAuditIndex.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("44D17A5F");
    }
    private FDCSplAuditIndexController getController() throws BOSException
    {
        return (FDCSplAuditIndexController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCSplAuditIndexInfo getFDCSplAuditIndexInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplAuditIndexInfo(getContext(), pk);
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
    public FDCSplAuditIndexInfo getFDCSplAuditIndexInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplAuditIndexInfo(getContext(), pk, selector);
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
    public FDCSplAuditIndexInfo getFDCSplAuditIndexInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplAuditIndexInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCSplAuditIndexCollection getFDCSplAuditIndexCollection() throws BOSException
    {
        try {
            return getController().getFDCSplAuditIndexCollection(getContext());
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
    public FDCSplAuditIndexCollection getFDCSplAuditIndexCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCSplAuditIndexCollection(getContext(), view);
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
    public FDCSplAuditIndexCollection getFDCSplAuditIndexCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCSplAuditIndexCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}