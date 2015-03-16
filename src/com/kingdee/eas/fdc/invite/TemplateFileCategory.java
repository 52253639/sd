package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class TemplateFileCategory extends TreeBase implements ITemplateFileCategory
{
    public TemplateFileCategory()
    {
        super();
        registerInterface(ITemplateFileCategory.class, this);
    }
    public TemplateFileCategory(Context ctx)
    {
        super(ctx);
        registerInterface(ITemplateFileCategory.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5D839D58");
    }
    private TemplateFileCategoryController getController() throws BOSException
    {
        return (TemplateFileCategoryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TemplateFileCategoryInfo getTemplateFileCategoryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateFileCategoryInfo(getContext(), pk);
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
    public TemplateFileCategoryInfo getTemplateFileCategoryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateFileCategoryInfo(getContext(), pk, selector);
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
    public TemplateFileCategoryInfo getTemplateFileCategoryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateFileCategoryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TemplateFileCategoryCollection getTemplateFileCategoryCollection() throws BOSException
    {
        try {
            return getController().getTemplateFileCategoryCollection(getContext());
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
    public TemplateFileCategoryCollection getTemplateFileCategoryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTemplateFileCategoryCollection(getContext(), view);
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
    public TemplateFileCategoryCollection getTemplateFileCategoryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTemplateFileCategoryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}