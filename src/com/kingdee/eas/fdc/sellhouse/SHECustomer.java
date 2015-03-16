package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import java.util.Map;
import com.kingdee.eas.fdc.basecrm.FDCBaseCustomer;
import java.util.HashMap;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basecrm.IFDCBaseCustomer;
import com.kingdee.bos.framework.*;
import java.util.List;

public class SHECustomer extends FDCBaseCustomer implements ISHECustomer
{
    public SHECustomer()
    {
        super();
        registerInterface(ISHECustomer.class, this);
    }
    public SHECustomer(Context ctx)
    {
        super(ctx);
        registerInterface(ISHECustomer.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F1713EF3");
    }
    private SHECustomerController getController() throws BOSException
    {
        return (SHECustomerController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SHECustomerInfo getSHECustomerInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSHECustomerInfo(getContext(), pk);
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
    public SHECustomerInfo getSHECustomerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSHECustomerInfo(getContext(), pk, selector);
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
    public SHECustomerInfo getSHECustomerInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSHECustomerInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SHECustomerCollection getSHECustomerCollection() throws BOSException
    {
        try {
            return getController().getSHECustomerCollection(getContext());
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
    public SHECustomerCollection getSHECustomerCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSHECustomerCollection(getContext(), view);
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
    public SHECustomerCollection getSHECustomerCollection(String oql) throws BOSException
    {
        try {
            return getController().getSHECustomerCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *客户更新-User defined method
     *@param ids ids
     */
    public void updateData(List ids) throws BOSException, EASBizException
    {
        try {
            getController().updateData(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *客户合并-User defined method
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
     *客户更名-User defined method
     *@param model model
     *@param map map
     */
    public void changeName(SHECustomerInfo model, Map map) throws BOSException, EASBizException
    {
        try {
            getController().changeName(getContext(), model, map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *客户共享-User defined method
     *@param objectColl objectColl
     *@param map map
     */
    public void shareCustomer(SHECustomerCollection objectColl, Map map) throws BOSException, EASBizException
    {
        try {
            getController().shareCustomer(getContext(), objectColl, map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *客户转交-User defined method
     *@param model model
     *@param map map
     */
    public void deliverCustomer(SHECustomerInfo model, Map map) throws BOSException, EASBizException
    {
        try {
            getController().deliverCustomer(getContext(), model, map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *客户导入-User defined method
     *@param res res
     *@param sellProject 项目
     */
    public void importCustomer(SHECustomerCollection res, SellProjectInfo sellProject) throws BOSException, EASBizException
    {
        try {
            getController().importCustomer(getContext(), res, sellProject);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *企业客户保存-User defined method
     *@param model model
     *@param name name
     *@param phone phone
     */
    public void submitEnterpriceCustomer(SHECustomerInfo model, String name, String phone) throws BOSException, EASBizException
    {
        try {
            getController().submitEnterpriceCustomer(getContext(), model, name, phone);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *新增保存-User defined method
     *@param hashMap 商机等参数
     *@param editData editData
     *@return
     */
    public IObjectPK submitAll(HashMap hashMap, IObjectValue editData) throws BOSException, EASBizException
    {
        try {
            return getController().submitAll(getContext(), hashMap, editData);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}