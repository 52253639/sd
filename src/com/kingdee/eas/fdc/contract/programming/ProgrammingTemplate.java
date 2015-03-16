package com.kingdee.eas.fdc.contract.programming;

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
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;
import com.kingdee.eas.fdc.contract.programming.app.*;

public class ProgrammingTemplate extends FDCDataBase implements IProgrammingTemplate
{
    public ProgrammingTemplate()
    {
        super();
        registerInterface(IProgrammingTemplate.class, this);
    }
    public ProgrammingTemplate(Context ctx)
    {
        super(ctx);
        registerInterface(IProgrammingTemplate.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BFE63543");
    }
    private ProgrammingTemplateController getController() throws BOSException
    {
        return (ProgrammingTemplateController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ProgrammingTemplateInfo getProgrammingTemplateInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingTemplateInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public ProgrammingTemplateInfo getProgrammingTemplateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingTemplateInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public ProgrammingTemplateInfo getProgrammingTemplateInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingTemplateInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ProgrammingTemplateCollection getProgrammingTemplateCollection() throws BOSException
    {
        try {
            return getController().getProgrammingTemplateCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public ProgrammingTemplateCollection getProgrammingTemplateCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProgrammingTemplateCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public ProgrammingTemplateCollection getProgrammingTemplateCollection(String oql) throws BOSException
    {
        try {
            return getController().getProgrammingTemplateCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *复制-User defined method
     */
    public void copy() throws BOSException
    {
        try {
            getController().copy(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}