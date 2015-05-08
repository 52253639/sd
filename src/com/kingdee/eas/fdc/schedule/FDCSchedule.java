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
import com.kingdee.eas.fdc.schedule.framework.ScheduleBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import java.util.Set;
import com.kingdee.eas.fdc.schedule.framework.IScheduleBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public class FDCSchedule extends ScheduleBase implements IFDCSchedule
{
    public FDCSchedule()
    {
        super();
        registerInterface(IFDCSchedule.class, this);
    }
    public FDCSchedule(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSchedule.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D0FA7B86");
    }
    private FDCScheduleController getController() throws BOSException
    {
        return (FDCScheduleController)getBizController();
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
    public FDCScheduleInfo getFDCScheduleInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCScheduleInfo(getContext(), pk);
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
    public FDCScheduleInfo getFDCScheduleInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCScheduleInfo(getContext(), pk, selector);
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
    public FDCScheduleInfo getFDCScheduleInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCScheduleInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *新增-System defined method
     *@param model model
     *@return
     */
    public IObjectPK addnew(FDCScheduleInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().addnew(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *新增-System defined method
     *@param pk pk
     *@param model model
     */
    public void addnew(IObjectPK pk, FDCScheduleInfo model) throws BOSException, EASBizException
    {
        try {
            getController().addnew(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新-System defined method
     *@param pk pk
     *@param model model
     */
    public void update(IObjectPK pk, FDCScheduleInfo model) throws BOSException, EASBizException
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
    public void updatePartial(FDCScheduleInfo model, SelectorItemCollection selector) throws BOSException, EASBizException
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
    public void updateBigObject(IObjectPK pk, FDCScheduleInfo model) throws BOSException
    {
        try {
            getController().updateBigObject(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除-System defined method
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
    public FDCScheduleCollection getFDCScheduleCollection() throws BOSException
    {
        try {
            return getController().getFDCScheduleCollection(getContext());
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
    public FDCScheduleCollection getFDCScheduleCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCScheduleCollection(getContext(), view);
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
    public FDCScheduleCollection getFDCScheduleCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCScheduleCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除-System defined method
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
     *删除-System defined method
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
     *删除-System defined method
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
     *禁用-User defined method
     *@param ids ID集合
     *@return
     */
    public Map cancel(Set ids) throws BOSException, EASBizException
    {
        try {
            return getController().cancel(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *启用-User defined method
     *@param ids ID集合
     *@return
     */
    public Map cancelCancel(Set ids) throws BOSException, EASBizException
    {
        try {
            return getController().cancelCancel(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批 -User defined method
     *@param ids 审批
     *@return
     */
    public Map audit(Set ids) throws BOSException, EASBizException
    {
        try {
            return getController().audit(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批 -User defined method
     *@param ids ID集合
     *@return
     */
    public Map unAudit(Set ids) throws BOSException, EASBizException
    {
        try {
            return getController().unAudit(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *关闭-User defined method
     *@param ids ID集合
     *@return
     */
    public Map close(Set ids) throws BOSException, EASBizException
    {
        try {
            return getController().close(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取得新增数据-User defined method
     *@param param 参数
     *@return
     */
    public FDCScheduleInfo getNewData(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getNewData(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置计划执行-User defined method
     *@param days 提前X天执行,默认不提前
     */
    public void setExecuting(int days) throws BOSException, EASBizException
    {
        try {
            getController().setExecuting(getContext(), days);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getScheduleInfo-User defined method
     *@param pk 计划ID
     *@return
     */
    public FDCScheduleInfo getScheduleInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置计划启动-User defined method
     *@param days 提前X天启动
     */
    public void setStart(int days) throws BOSException, EASBizException
    {
        try {
            getController().setStart(getContext(), days);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置发送消息-User defined method
     *@param days 提前X天通知
     */
    public void setSendMsg(int days) throws BOSException, EASBizException
    {
        try {
            getController().setSendMsg(getContext(), days);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *保存新任务-User defined method
     *@param schedule 进度计划
     */
    public void saveNewTask(FDCScheduleInfo schedule) throws BOSException, EASBizException
    {
        try {
            getController().saveNewTask(getContext(), schedule);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置提交状态-User defined method
     *@param billID 单据编号
     */
    public void setSubmitStatus(BOSUuid billID) throws BOSException, EASBizException
    {
        try {
            getController().setSubmitStatus(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置审批中状态-User defined method
     *@param billID 单据编号
     */
    public void setAudittingStatus(BOSUuid billID) throws BOSException, EASBizException
    {
        try {
            getController().setAudittingStatus(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批 -User defined method
     *@param billID 单据编号
     */
    public void audit(BOSUuid billID) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批 -User defined method
     *@param billID 单据编号
     */
    public void unAudit(BOSUuid billID) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反关闭-User defined method
     *@param idSet ID集合
     */
    public void unClose(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().unClose(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *检查计划是否为可执行状态-User defined method
     *@param ids ID集合
     */
    public void checkScheduleState(Set ids) throws BOSException, EASBizException
    {
        try {
            getController().checkScheduleState(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getScheduleInfo-User defined method
     *@param isMainSchedule 是否主项计划
     *@param isAdjust 是否调整。点击调整按钮时获取数据此参数才为true
     *@param project 工程项目，可为null
     *@param projectSpecial 项目专项，可为null
     *@param param 其他参数
     *@return
     */
    public FDCScheduleInfo getScheduleInfo(boolean isMainSchedule, boolean isAdjust, CurProjectInfo project, ProjectSpecialInfo projectSpecial, Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleInfo(getContext(), isMainSchedule, isAdjust, project, projectSpecial, param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *版本对比-User defined method
     *@param baseVerID 基准版本id，为空时去比较版本的上一版本计划id。点击计划编制界面的版本对比时，此值为空
     *@param compareVerID 比较版本id，不能为空
     *@return
     */
    public FDCScheduleInfo getSchedule4Compare(String baseVerID, String compareVerID) throws BOSException
    {
        try {
            return getController().getSchedule4Compare(getContext(), baseVerID, compareVerID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得最新版本的进度计划-User defined method
     *@param curProject 工程项目
     *@param projectSpecial 项目专项
     *@return
     */
    public FDCScheduleInfo getNewestVerSchedule(CurProjectInfo curProject, ProjectSpecialInfo projectSpecial) throws BOSException, EASBizException
    {
        try {
            return getController().getNewestVerSchedule(getContext(), curProject, projectSpecial);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量修改任务属性（责任人、责任部门、业务类型以及任务类别等）-User defined method
     *@param param 修改参数
     *@return
     */
    public Map batchChangeTaskProperties(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().batchChangeTaskProperties(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *主项计划编制-User defined method
     *@param mainSchedule mainSchedule
     */
    public void submitMainSchedule(FDCScheduleInfo mainSchedule) throws BOSException, EASBizException
    {
        try {
            getController().submitMainSchedule(getContext(), mainSchedule);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *专项计划编制-User defined method
     *@param specialSchedule 专项计划
     */
    public void submitSpecialSchedule(FDCScheduleInfo specialSchedule) throws BOSException, EASBizException
    {
        try {
            getController().submitSpecialSchedule(getContext(), specialSchedule);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置考核-User defined method
     *@param billId ID
     */
    public void setCheckVersion(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setCheckVersion(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}