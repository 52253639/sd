package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class AttachResourceRentEntrys extends DataBase implements IAttachResourceRentEntrys
{
    public AttachResourceRentEntrys()
    {
        super();
        registerInterface(IAttachResourceRentEntrys.class, this);
    }
    public AttachResourceRentEntrys(Context ctx)
    {
        super(ctx);
        registerInterface(IAttachResourceRentEntrys.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0063AF56");
    }
    private AttachResourceRentEntrysController getController() throws BOSException
    {
        return (AttachResourceRentEntrysController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public AttachResourceRentEntrysInfo getAttachResourceRentEntrysInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachResourceRentEntrysInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public AttachResourceRentEntrysInfo getAttachResourceRentEntrysInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachResourceRentEntrysInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public AttachResourceRentEntrysInfo getAttachResourceRentEntrysInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachResourceRentEntrysInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public AttachResourceRentEntrysCollection getAttachResourceRentEntrysCollection() throws BOSException
    {
        try {
            return getController().getAttachResourceRentEntrysCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view view
     *@return
     */
    public AttachResourceRentEntrysCollection getAttachResourceRentEntrysCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAttachResourceRentEntrysCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql oql
     *@return
     */
    public AttachResourceRentEntrysCollection getAttachResourceRentEntrysCollection(String oql) throws BOSException
    {
        try {
            return getController().getAttachResourceRentEntrysCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}