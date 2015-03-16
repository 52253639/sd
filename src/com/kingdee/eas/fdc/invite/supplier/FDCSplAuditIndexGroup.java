package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.IFDCTreeBaseData;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseData;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class FDCSplAuditIndexGroup extends FDCTreeBaseData implements IFDCSplAuditIndexGroup
{
    public FDCSplAuditIndexGroup()
    {
        super();
        registerInterface(IFDCSplAuditIndexGroup.class, this);
    }
    public FDCSplAuditIndexGroup(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSplAuditIndexGroup.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EB452F60");
    }
    private FDCSplAuditIndexGroupController getController() throws BOSException
    {
        return (FDCSplAuditIndexGroupController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public FDCSplAuditIndexGroupInfo getFDCSplAuditIndexGroupInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplAuditIndexGroupInfo(getContext(), oql);
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
    public FDCSplAuditIndexGroupInfo getFDCSplAuditIndexGroupInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplAuditIndexGroupInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCSplAuditIndexGroupInfo getFDCSplAuditIndexGroupInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplAuditIndexGroupInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCSplAuditIndexGroupCollection getFDCSplAuditIndexGroupCollection() throws BOSException
    {
        try {
            return getController().getFDCSplAuditIndexGroupCollection(getContext());
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
    public FDCSplAuditIndexGroupCollection getFDCSplAuditIndexGroupCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCSplAuditIndexGroupCollection(getContext(), view);
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
    public FDCSplAuditIndexGroupCollection getFDCSplAuditIndexGroupCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCSplAuditIndexGroupCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}