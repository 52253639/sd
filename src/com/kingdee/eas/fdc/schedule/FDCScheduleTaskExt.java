package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class FDCScheduleTaskExt extends CoreBase implements IFDCScheduleTaskExt
{
    public FDCScheduleTaskExt()
    {
        super();
        registerInterface(IFDCScheduleTaskExt.class, this);
    }
    public FDCScheduleTaskExt(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCScheduleTaskExt.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("99CA6BF6");
    }
    private FDCScheduleTaskExtController getController() throws BOSException
    {
        return (FDCScheduleTaskExtController)getBizController();
    }
    /**
     *exists-System defined method
     *@param pk pk
     *@return
     */
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *exists-System defined method
     *@param filter filter
     *@return
     */
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), filter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *exists-System defined method
     *@param oql oql
     *@return
     */
    public boolean exists(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCScheduleTaskExtInfo getFDCScheduleTaskExtInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCScheduleTaskExtInfo(getContext(), pk);
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
    public FDCScheduleTaskExtInfo getFDCScheduleTaskExtInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCScheduleTaskExtInfo(getContext(), pk, selector);
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
    public FDCScheduleTaskExtInfo getFDCScheduleTaskExtInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCScheduleTaskExtInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *addnew-System defined method
     *@param model model
     *@return
     */
    public IObjectPK addnew(FDCScheduleTaskExtInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().addnew(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *addnew-System defined method
     *@param pk pk
     *@param model model
     */
    public void addnew(IObjectPK pk, FDCScheduleTaskExtInfo model) throws BOSException, EASBizException
    {
        try {
            getController().addnew(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *update-System defined method
     *@param pk pk
     *@param model model
     */
    public void update(IObjectPK pk, FDCScheduleTaskExtInfo model) throws BOSException, EASBizException
    {
        try {
            getController().update(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updatePartial-System defined method
     *@param model model
     *@param selector selector
     */
    public void updatePartial(FDCScheduleTaskExtInfo model, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            getController().updatePartial(getContext(), model, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updateBigObject-System defined method
     *@param pk pk
     *@param model model
     */
    public void updateBigObject(IObjectPK pk, FDCScheduleTaskExtInfo model) throws BOSException
    {
        try {
            getController().updateBigObject(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param pk pk
     */
    public void delete(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPKList-System defined method
     *@return
     */
    public IObjectPK[] getPKList() throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPKList-System defined method
     *@param oql oql
     *@return
     */
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPKList-System defined method
     *@param filter filter
     *@param sorter sorter
     *@return
     */
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext(), filter, sorter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCScheduleTaskExtCollection getFDCScheduleTaskExtCollection() throws BOSException
    {
        try {
            return getController().getFDCScheduleTaskExtCollection(getContext());
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
    public FDCScheduleTaskExtCollection getFDCScheduleTaskExtCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCScheduleTaskExtCollection(getContext(), view);
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
    public FDCScheduleTaskExtCollection getFDCScheduleTaskExtCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCScheduleTaskExtCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param filter filter
     *@return
     */
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            return getController().delete(getContext(), filter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param oql oql
     *@return
     */
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().delete(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param arrayPK arrayPK
     */
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), arrayPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除扩展属性-User defined method
     *@param wbsID 删除任务信息的扩展属性
     */
    public void deletePropByWBSID(String wbsID) throws BOSException, EASBizException
    {
        try {
            getController().deletePropByWBSID(getContext(), wbsID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除扩展属性-User defined method
     *@param taskExtID 任务信息扩展属性ID
     */
    public void deletePropByTaskExtID(String taskExtID) throws BOSException, EASBizException
    {
        try {
            getController().deletePropByTaskExtID(getContext(), taskExtID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取任务扩展属性-User defined method
     *@param WBSID 任务ID
     *@return
     */
    public Map getProByWBSID(String WBSID) throws BOSException, EASBizException
    {
        try {
            return getController().getProByWBSID(getContext(), WBSID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取任务扩展属性-User defined method
     *@param taskExtID 任务扩展属性ID
     *@return
     */
    public Map getProByTaskExtID(String taskExtID) throws BOSException, EASBizException
    {
        try {
            return getController().getProByTaskExtID(getContext(), taskExtID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *修改任务执行扩展属性-User defined method
     *@param extProperties 任务执行扩展属性
     */
    public void updateExeProp(Map extProperties) throws BOSException, EASBizException
    {
        try {
            getController().updateExeProp(getContext(), extProperties);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *修改任务编制扩展属性-User defined method
     *@param extProperties 任务编制扩展属性
     */
    public void updateCompleProp(Map extProperties) throws BOSException, EASBizException
    {
        try {
            getController().updateCompleProp(getContext(), extProperties);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *新增任务执行扩展属性-User defined method
     *@param exeExtProperties 任务执行扩展属性
     */
    public void saveExeProp(Map exeExtProperties) throws BOSException, EASBizException
    {
        try {
            getController().saveExeProp(getContext(), exeExtProperties);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *新增任务编制扩展属性-User defined method
     *@param exeCompileProperties 任务编制扩展属性
     */
    public void saveCompileProp(Map exeCompileProperties) throws BOSException, EASBizException
    {
        try {
            getController().saveCompileProp(getContext(), exeCompileProperties);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除扩展属性-User defined method
     *@param wbsIDs WBS编码
     */
    public void deletePropByWBSIDs(Set wbsIDs) throws BOSException, EASBizException
    {
        try {
            getController().deletePropByWBSIDs(getContext(), wbsIDs);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除扩展属性-User defined method
     *@param wbsIDs WBSID
     */
    public void deletePropByTaskExtIDs(Set wbsIDs) throws BOSException, EASBizException
    {
        try {
            getController().deletePropByTaskExtIDs(getContext(), wbsIDs);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *扩展属性从调整单拷贝到项目WBS-User defined method
     *@param oldWBSID 旧WBSID
     *@param newWBSID 新WBSID
     */
    public void copyFromAdjuestBill(String oldWBSID, String newWBSID) throws BOSException, EASBizException
    {
        try {
            getController().copyFromAdjuestBill(getContext(), oldWBSID, newWBSID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}