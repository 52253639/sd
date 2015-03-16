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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class SinObligateAttachEntry extends CoreBillBase implements ISinObligateAttachEntry
{
    public SinObligateAttachEntry()
    {
        super();
        registerInterface(ISinObligateAttachEntry.class, this);
    }
    public SinObligateAttachEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISinObligateAttachEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B99A6FC3");
    }
    private SinObligateAttachEntryController getController() throws BOSException
    {
        return (SinObligateAttachEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public SinObligateAttachEntryInfo getSinObligateAttachEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSinObligateAttachEntryInfo(getContext(), pk);
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
    public SinObligateAttachEntryInfo getSinObligateAttachEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSinObligateAttachEntryInfo(getContext(), pk, selector);
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
    public SinObligateAttachEntryInfo getSinObligateAttachEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSinObligateAttachEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SinObligateAttachEntryCollection getSinObligateAttachEntryCollection() throws BOSException
    {
        try {
            return getController().getSinObligateAttachEntryCollection(getContext());
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
    public SinObligateAttachEntryCollection getSinObligateAttachEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSinObligateAttachEntryCollection(getContext(), view);
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
    public SinObligateAttachEntryCollection getSinObligateAttachEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSinObligateAttachEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}