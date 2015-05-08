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
import java.lang.Object;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import java.util.Set;
import com.kingdee.eas.fdc.basedata.IFDCTreeBaseData;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseData;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class FDCWBS extends FDCTreeBaseData implements IFDCWBS
{
    public FDCWBS()
    {
        super();
        registerInterface(IFDCWBS.class, this);
    }
    public FDCWBS(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCWBS.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("786DC4B9");
    }
    private FDCWBSController getController() throws BOSException
    {
        return (FDCWBSController)getBizController();
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
    public FDCWBSInfo getFDCWBSInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCWBSInfo(getContext(), pk);
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
    public FDCWBSInfo getFDCWBSInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCWBSInfo(getContext(), pk, selector);
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
    public FDCWBSInfo getFDCWBSInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCWBSInfo(getContext(), oql);
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
    public IObjectPK addnew(FDCWBSInfo model) throws BOSException, EASBizException
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
    public void addnew(IObjectPK pk, FDCWBSInfo model) throws BOSException, EASBizException
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
    public void update(IObjectPK pk, FDCWBSInfo model) throws BOSException, EASBizException
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
    public void updatePartial(FDCWBSInfo model, SelectorItemCollection selector) throws BOSException, EASBizException
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
    public void updateBigObject(IObjectPK pk, FDCWBSInfo model) throws BOSException
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
    public FDCWBSCollection getFDCWBSCollection() throws BOSException
    {
        try {
            return getController().getFDCWBSCollection(getContext());
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
    public FDCWBSCollection getFDCWBSCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCWBSCollection(getContext(), view);
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
    public FDCWBSCollection getFDCWBSCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCWBSCollection(getContext(), oql);
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
     *导入WBS模板-User defined method
     *@param param 参数
     */
    public void importWBSTemplate(Map param) throws BOSException, ScheduleException, EASBizException
    {
        try {
            getController().importWBSTemplate(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量设置专业属性-User defined method
     *@param wbsIDs wbsID
     *@param taskProID 任务属性ID
     */
    public void batChangeTaskPro(Set wbsIDs, String taskProID) throws BOSException, EASBizException
    {
        try {
            getController().batChangeTaskPro(getContext(), wbsIDs, taskProID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量修改归口部门-User defined method
     *@param wbsIDs wbsIDs
     *@param adminDeptID 归口部门ID
     */
    public void batChangeAdminDept(Set wbsIDs, String adminDeptID) throws BOSException, EASBizException
    {
        try {
            getController().batChangeAdminDept(getContext(), wbsIDs, adminDeptID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量修改责任人-User defined method
     *@param wbsIds wbsID集合
     *@param adminPersonID 责任人ID
     */
    public void batChangeAdminPerson(Set wbsIds, String adminPersonID) throws BOSException, EASBizException
    {
        try {
            getController().batChangeAdminPerson(getContext(), wbsIds, adminPersonID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量修改责任部门-User defined method
     *@param wbsIds wbsIds
     *@param respDeptId 责任部门ID
     */
    public void batChangeRespDept(Set wbsIds, String respDeptId) throws BOSException, EASBizException
    {
        try {
            getController().batChangeRespDept(getContext(), wbsIds, respDeptId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *从WBS获取模板内容-User defined method
     *@param prjId 工程项目Id~
     *@return
     */
    public Map getTemplateFromFDCWBS(String prjId) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateFromFDCWBS(getContext(), prjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据工程项目ID获取WBS及对应最新版本任务-User defined method
     *@param projectId 工程项目ID
     *@return
     */
    public Map getTaskWBSByProjectId(String projectId) throws BOSException, EASBizException
    {
        try {
            return getController().getTaskWBSByProjectId(getContext(), projectId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *WBS编码重算-User defined method
     *@param wbsAdjustManager WBS调整管理
     */
    public void reCalculateNumber(Object wbsAdjustManager) throws BOSException, EASBizException
    {
        try {
            getController().reCalculateNumber(getContext(), wbsAdjustManager);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *优化性能，移到服务端处理启用时候需要的逻辑-User defined method
     *@param paramMap paramMap
     */
    public void handleCancleCancle(Map paramMap) throws BOSException, EASBizException
    {
        try {
            getController().handleCancleCancle(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *保存经过升降级，上下移后的WBS-User defined method
     *@param tree 排序后的WBS树
     *@param isRetMap 是否返回id2WBSMap
     *@return
     */
    public Map saveOrderWBS(FDCWBSTree tree, boolean isRetMap) throws BOSException, EASBizException
    {
        try {
            return getController().saveOrderWBS(getContext(), tree, isRetMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}