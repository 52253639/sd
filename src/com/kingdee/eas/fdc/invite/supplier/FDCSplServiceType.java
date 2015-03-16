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

public class FDCSplServiceType extends FDCTreeBaseData implements IFDCSplServiceType
{
    public FDCSplServiceType()
    {
        super();
        registerInterface(IFDCSplServiceType.class, this);
    }
    public FDCSplServiceType(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSplServiceType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1A4CD9ED");
    }
    private FDCSplServiceTypeController getController() throws BOSException
    {
        return (FDCSplServiceTypeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCSplServiceTypeInfo getFDCSplServiceTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplServiceTypeInfo(getContext(), pk);
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
    public FDCSplServiceTypeInfo getFDCSplServiceTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplServiceTypeInfo(getContext(), pk, selector);
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
    public FDCSplServiceTypeInfo getFDCSplServiceTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplServiceTypeInfo(getContext(), oql);
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
    public FDCSplServiceTypeCollection getFDCSplServiceTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCSplServiceTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCSplServiceTypeCollection getFDCSplServiceTypeCollection() throws BOSException
    {
        try {
            return getController().getFDCSplServiceTypeCollection(getContext());
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
    public FDCSplServiceTypeCollection getFDCSplServiceTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCSplServiceTypeCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}