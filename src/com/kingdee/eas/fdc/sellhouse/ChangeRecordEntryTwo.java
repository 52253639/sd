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

public class ChangeRecordEntryTwo extends DataBase implements IChangeRecordEntryTwo
{
    public ChangeRecordEntryTwo()
    {
        super();
        registerInterface(IChangeRecordEntryTwo.class, this);
    }
    public ChangeRecordEntryTwo(Context ctx)
    {
        super(ctx);
        registerInterface(IChangeRecordEntryTwo.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("75B31B16");
    }
    private ChangeRecordEntryTwoController getController() throws BOSException
    {
        return (ChangeRecordEntryTwoController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public ChangeRecordEntryTwoInfo getChangeRecordEntryTwoInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeRecordEntryTwoInfo(getContext(), pk);
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
    public ChangeRecordEntryTwoInfo getChangeRecordEntryTwoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeRecordEntryTwoInfo(getContext(), pk, selector);
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
    public ChangeRecordEntryTwoInfo getChangeRecordEntryTwoInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeRecordEntryTwoInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ChangeRecordEntryTwoCollection getChangeRecordEntryTwoCollection() throws BOSException
    {
        try {
            return getController().getChangeRecordEntryTwoCollection(getContext());
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
    public ChangeRecordEntryTwoCollection getChangeRecordEntryTwoCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getChangeRecordEntryTwoCollection(getContext(), view);
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
    public ChangeRecordEntryTwoCollection getChangeRecordEntryTwoCollection(String oql) throws BOSException
    {
        try {
            return getController().getChangeRecordEntryTwoCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更名保存-User defined method
     *@param sinPurInfo 预留信息
     *@param appoinmentPeople 预约人
     *@param appoimentPhone 预约电话
     *@param customList 客户
     *@param model 值对象
     *@return
     */
    public boolean changeRecordSave(SincerityPurchaseInfo sinPurInfo, String appoinmentPeople, String appoimentPhone, List customList, IObjectValue model) throws BOSException, EASBizException
    {
        try {
            return getController().changeRecordSave(getContext(), sinPurInfo, appoinmentPeople, appoimentPhone, customList, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}