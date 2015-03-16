package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basecrm.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import java.util.Set;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import java.util.List;

public class FDCOrgCustomer extends FDCBaseCustomer implements IFDCOrgCustomer
{
    public FDCOrgCustomer()
    {
        super();
        registerInterface(IFDCOrgCustomer.class, this);
    }
    public FDCOrgCustomer(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCOrgCustomer.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("79DAF899");
    }
    private FDCOrgCustomerController getController() throws BOSException
    {
        return (FDCOrgCustomerController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCOrgCustomerInfo getFDCOrgCustomerInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCOrgCustomerInfo(getContext(), pk);
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
    public FDCOrgCustomerInfo getFDCOrgCustomerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCOrgCustomerInfo(getContext(), pk, selector);
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
    public FDCOrgCustomerInfo getFDCOrgCustomerInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCOrgCustomerInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCOrgCustomerCollection getFDCOrgCustomerCollection() throws BOSException
    {
        try {
            return getController().getFDCOrgCustomerCollection(getContext());
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
    public FDCOrgCustomerCollection getFDCOrgCustomerCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCOrgCustomerCollection(getContext(), view);
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
    public FDCOrgCustomerCollection getFDCOrgCustomerCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCOrgCustomerCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *客户更名-User defined method
     *@param cusId cusId
     *@param newName newName
     */
    public void changeCusName(String cusId, String newName) throws BOSException, EASBizException
    {
        try {
            getController().changeCusName(getContext(), cusId, newName);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *客户资料更新-User defined method
     *@param ids ids
     */
    public void updateCustomerInfo(List ids) throws BOSException, EASBizException
    {
        try {
            getController().updateCustomerInfo(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *shareCustomer-User defined method
     *@param cusIds cusIds
     *@param shareOrgId shareOrgId
     */
    public void shareCustomer(List cusIds, String shareOrgId) throws BOSException, EASBizException
    {
        try {
            getController().shareCustomer(getContext(), cusIds, shareOrgId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *可并客户-User defined method
     *@param srcIds srcIds
     *@param toId toId
     */
    public void mergeCustomer(List srcIds, String toId) throws BOSException, EASBizException
    {
        try {
            getController().mergeCustomer(getContext(), srcIds, toId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *importCustomer-User defined method
     *@param res res
     */
    public void importCustomer(IObjectCollection res) throws BOSException, EASBizException
    {
        try {
            getController().importCustomer(getContext(), res);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *企业客户保存-User defined method
     *@param model model
     *@param name 姓名
     *@param phone 联系电话
     */
    public void submitEnterpriceCustomer(FDCOrgCustomerInfo model, String name, String phone) throws BOSException, EASBizException
    {
        try {
            getController().submitEnterpriceCustomer(getContext(), model, name, phone);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步到企业客户-User defined method
     *@param set set
     *@param str1 str1
     *@param str2 str2
     *@param isLinkMan isLinkMan
     */
    public void updateEnterpriceCustomer(Set set, String str1, String str2, boolean isLinkMan) throws BOSException, EASBizException
    {
        try {
            getController().updateEnterpriceCustomer(getContext(), set, str1, str2, isLinkMan);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}