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

public class FDCSplArea extends FDCDataBase implements IFDCSplArea
{
    public FDCSplArea()
    {
        super();
        registerInterface(IFDCSplArea.class, this);
    }
    public FDCSplArea(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSplArea.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DDF72BEF");
    }
    private FDCSplAreaController getController() throws BOSException
    {
        return (FDCSplAreaController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public FDCSplAreaInfo getFDCSplAreaInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplAreaInfo(getContext(), oql);
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
    public FDCSplAreaInfo getFDCSplAreaInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplAreaInfo(getContext(), pk, selector);
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
    public FDCSplAreaInfo getFDCSplAreaInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplAreaInfo(getContext(), pk);
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
    public FDCSplAreaCollection getFDCSplAreaCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCSplAreaCollection(getContext(), oql);
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
    public FDCSplAreaCollection getFDCSplAreaCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCSplAreaCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCSplAreaCollection getFDCSplAreaCollection() throws BOSException
    {
        try {
            return getController().getFDCSplAreaCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *是否删除区域名称-User defined method
     *@param areaName 区域名称
     */
    public void IsNdelete(String areaName) throws BOSException, EASBizException
    {
        try {
            getController().IsNdelete(getContext(), areaName);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}