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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.fdc.sellhouse.app.*;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.framework.IDataBase;

public class FDCCustomer extends DataBase implements IFDCCustomer
{
    public FDCCustomer()
    {
        super();
        registerInterface(IFDCCustomer.class, this);
    }
    public FDCCustomer(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCCustomer.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("682588A8");
    }
    private FDCCustomerController getController() throws BOSException
    {
        return (FDCCustomerController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public FDCCustomerInfo getFDCCustomerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCustomerInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public FDCCustomerInfo getFDCCustomerInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCustomerInfo(getContext(), pk);
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
    public FDCCustomerInfo getFDCCustomerInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCustomerInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public FDCCustomerCollection getFDCCustomerCollection() throws BOSException
    {
        try {
            return getController().getFDCCustomerCollection(getContext());
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
    public FDCCustomerCollection getFDCCustomerCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCCustomerCollection(getContext(), view);
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
    public FDCCustomerCollection getFDCCustomerCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCCustomerCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步到系统用户-User defined method
     *@param id ID
     */
    public void addToSysCustomer(String id) throws BOSException, EASBizException
    {
        try {
            getController().addToSysCustomer(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *作废-User defined method
     *@param idList 主键IDString形式的集合
     */
    public void blankOut(List idList) throws BOSException
    {
        try {
            getController().blankOut(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *捡回-User defined method
     *@param idList 主键IDStirng形式的集合
     */
    public void pickUp(List idList) throws BOSException
    {
        try {
            getController().pickUp(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *改名-User defined method
     *@param fdccustomer 房地产客户
     */
    public void modifyName(FDCCustomerInfo fdccustomer) throws BOSException
    {
        try {
            getController().modifyName(getContext(), fdccustomer);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *标记为重点跟进-User defined method
     *@param idList 客户String形式PKID的集合
     */
    public void signImportantTrack(List idList) throws BOSException
    {
        try {
            getController().signImportantTrack(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反标记重点跟进记录-User defined method
     *@param idList 客户String形式PKID的集合
     */
    public void cancelImportantTrack(List idList) throws BOSException
    {
        try {
            getController().cancelImportantTrack(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *转接-User defined method
     *@param idList 客户String形式PKID的集合
     *@param salesmanId 接手人String形式的主键ID
     */
    public void switchTo(List idList, String salesmanId) throws BOSException
    {
        try {
            getController().switchTo(getContext(), idList, salesmanId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步到系统用户-User defined method
     *@param id ID
     *@param list 分类集合
     */
    public void addToSysCustomer(String id, List list) throws BOSException, EASBizException
    {
        try {
            getController().addToSysCustomer(getContext(), id, list);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *判断能否保存-User defined method
     *@param model model
     *@return
     */
    public Map verifySave(FDCCustomerInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().verifySave(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置认购状态-User defined method
     *@param idList 用户ID列表
     */
    public void setStatus(List idList) throws BOSException
    {
        try {
            getController().setStatus(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *判断能否保存-User defined method
     *@param model model
     *@param isSingle isSingle
     *@return
     */
    public Map verifySave(IObjectValue model, boolean isSingle) throws BOSException, EASBizException
    {
        try {
            return getController().verifySave(getContext(), model, isSingle);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新租赁合同上的客户信息-User defined method
     *@param fdcCustID 客户ID
     */
    public void updateTenancyBill(String fdcCustID) throws BOSException, EASBizException
    {
        try {
            getController().updateTenancyBill(getContext(), fdcCustID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}