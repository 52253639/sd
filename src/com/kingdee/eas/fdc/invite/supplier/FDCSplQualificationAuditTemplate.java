package com.kingdee.eas.fdc.invite.supplier;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class FDCSplQualificationAuditTemplate extends CoreBillEntryBase implements IFDCSplQualificationAuditTemplate
{
    public FDCSplQualificationAuditTemplate()
    {
        super();
        registerInterface(IFDCSplQualificationAuditTemplate.class, this);
    }
    public FDCSplQualificationAuditTemplate(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSplQualificationAuditTemplate.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1D9975A8");
    }
    private FDCSplQualificationAuditTemplateController getController() throws BOSException
    {
        return (FDCSplQualificationAuditTemplateController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCSplQualificationAuditTemplateInfo getFDCSplQualificationAuditTemplateInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplQualificationAuditTemplateInfo(getContext(), pk);
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
    public FDCSplQualificationAuditTemplateInfo getFDCSplQualificationAuditTemplateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplQualificationAuditTemplateInfo(getContext(), pk, selector);
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
    public FDCSplQualificationAuditTemplateInfo getFDCSplQualificationAuditTemplateInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplQualificationAuditTemplateInfo(getContext(), oql);
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
    public FDCSplQualificationAuditTemplateCollection getFDCSplQualificationAuditTemplateCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCSplQualificationAuditTemplateCollection(getContext(), oql);
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
    public FDCSplQualificationAuditTemplateCollection getFDCSplQualificationAuditTemplateCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCSplQualificationAuditTemplateCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCSplQualificationAuditTemplateCollection getFDCSplQualificationAuditTemplateCollection() throws BOSException
    {
        try {
            return getController().getFDCSplQualificationAuditTemplateCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}