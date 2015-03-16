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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class SupplierAppraiseTemplate extends FDCBill implements ISupplierAppraiseTemplate
{
    public SupplierAppraiseTemplate()
    {
        super();
        registerInterface(ISupplierAppraiseTemplate.class, this);
    }
    public SupplierAppraiseTemplate(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierAppraiseTemplate.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("218F44EF");
    }
    private SupplierAppraiseTemplateController getController() throws BOSException
    {
        return (SupplierAppraiseTemplateController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SupplierAppraiseTemplateInfo getSupplierAppraiseTemplateInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierAppraiseTemplateInfo(getContext(), pk);
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
    public SupplierAppraiseTemplateInfo getSupplierAppraiseTemplateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierAppraiseTemplateInfo(getContext(), pk, selector);
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
    public SupplierAppraiseTemplateInfo getSupplierAppraiseTemplateInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierAppraiseTemplateInfo(getContext(), oql);
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
    public SupplierAppraiseTemplateCollection getSupplierAppraiseTemplateCollection(String oql) throws BOSException
    {
        try {
            return getController().getSupplierAppraiseTemplateCollection(getContext(), oql);
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
    public SupplierAppraiseTemplateCollection getSupplierAppraiseTemplateCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSupplierAppraiseTemplateCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SupplierAppraiseTemplateCollection getSupplierAppraiseTemplateCollection() throws BOSException
    {
        try {
            return getController().getSupplierAppraiseTemplateCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}