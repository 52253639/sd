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
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class FDCSplQualificationAuditEntry extends CoreBase implements IFDCSplQualificationAuditEntry
{
    public FDCSplQualificationAuditEntry()
    {
        super();
        registerInterface(IFDCSplQualificationAuditEntry.class, this);
    }
    public FDCSplQualificationAuditEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSplQualificationAuditEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A1029E04");
    }
    private FDCSplQualificationAuditEntryController getController() throws BOSException
    {
        return (FDCSplQualificationAuditEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCSplQualificationAuditEntryInfo getFDCSplQualificationAuditEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplQualificationAuditEntryInfo(getContext(), pk);
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
    public FDCSplQualificationAuditEntryInfo getFDCSplQualificationAuditEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplQualificationAuditEntryInfo(getContext(), pk, selector);
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
    public FDCSplQualificationAuditEntryInfo getFDCSplQualificationAuditEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplQualificationAuditEntryInfo(getContext(), oql);
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
    public FDCSplQualificationAuditEntryCollection getFDCSplQualificationAuditEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCSplQualificationAuditEntryCollection(getContext(), oql);
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
    public FDCSplQualificationAuditEntryCollection getFDCSplQualificationAuditEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCSplQualificationAuditEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCSplQualificationAuditEntryCollection getFDCSplQualificationAuditEntryCollection() throws BOSException
    {
        try {
            return getController().getFDCSplQualificationAuditEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}