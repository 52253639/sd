package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.framework.ICoreBase;

public class ProjectPeriodStatus extends CoreBase implements IProjectPeriodStatus
{
    public ProjectPeriodStatus()
    {
        super();
        registerInterface(IProjectPeriodStatus.class, this);
    }
    public ProjectPeriodStatus(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectPeriodStatus.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("30DDC95D");
    }
    private ProjectPeriodStatusController getController() throws BOSException
    {
        return (ProjectPeriodStatusController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ProjectPeriodStatusInfo getProjectPeriodStatusInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectPeriodStatusInfo(getContext(), pk);
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
    public ProjectPeriodStatusInfo getProjectPeriodStatusInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectPeriodStatusInfo(getContext(), pk, selector);
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
    public ProjectPeriodStatusInfo getProjectPeriodStatusInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectPeriodStatusInfo(getContext(), oql);
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
    public ProjectPeriodStatusCollection getProjectPeriodStatusCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectPeriodStatusCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProjectPeriodStatusCollection getProjectPeriodStatusCollection() throws BOSException
    {
        try {
            return getController().getProjectPeriodStatusCollection(getContext());
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
    public ProjectPeriodStatusCollection getProjectPeriodStatusCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectPeriodStatusCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *结束初始化，就是把已经启用的工程项目，生成一条期间状态控制记录，结束初始化。这样，就可以录入合同业务了。-User defined method
     *@param projectIds projectIds
     *@param orgUnitId orgUnitId
     *@param isCompany isCompany
     *@return
     */
    public Map closeInit(List projectIds, String orgUnitId, boolean isCompany) throws BOSException, EASBizException
    {
        try {
            return getController().closeInit(getContext(), projectIds, orgUnitId, isCompany);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *关闭后不需要月结，该工程也不能继续做业务了-User defined method
     *@param projectIds projectIds
     */
    public void closeProject(List projectIds) throws BOSException, EASBizException
    {
        try {
            getController().closeProject(getContext(), projectIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *全部结束初始化。把当前组织的已经启用的全部工程项目，生成期间状态控制记录，结束初始化。这样，就可以录入合同业务了。-User defined method
     *@param orgUnit orgUnit
     *@param isCompany isCompany
     *@return
     */
    public Map closeAll(String orgUnit, boolean isCompany) throws BOSException, EASBizException
    {
        try {
            return getController().closeAll(getContext(), orgUnit, isCompany);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反初始化-User defined method
     *@param projectIds projectIds
     *@param orgUnitId orgUnitId
     *@param isCompany isCompany
     *@return
     */
    public Map closeUnInit(List projectIds, String orgUnitId, boolean isCompany) throws BOSException, EASBizException
    {
        try {
            return getController().closeUnInit(getContext(), projectIds, orgUnitId, isCompany);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反关闭工程-User defined method
     *@param projectIds projectIds
     */
    public void closeUnProject(List projectIds) throws BOSException, EASBizException
    {
        try {
            getController().closeUnProject(getContext(), projectIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *全部反初始化-User defined method
     *@param orgUnit orgUnit
     *@param isCompany isCompany
     *@return
     */
    public Map closeUnAll(String orgUnit, boolean isCompany) throws BOSException, EASBizException
    {
        try {
            return getController().closeUnAll(getContext(), orgUnit, isCompany);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据工程项目，和业务日期，获得一个期间-User defined method
     *@param projectId projectId
     *@param bookedDate bookedDate
     *@param isCost 是否获取成本当前期间
     *@return
     */
    public PeriodInfo fetchPeriod(String projectId, Date bookedDate, boolean isCost) throws BOSException, EASBizException
    {
        try {
            return getController().fetchPeriod(getContext(), projectId, bookedDate, isCost);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取期间-User defined method
     *@param curOrgId curOrgId
     *@param isCompany isCompany
     *@return
     */
    public Map fetchInitData(String curOrgId, boolean isCompany) throws BOSException, EASBizException
    {
        try {
            return getController().fetchInitData(getContext(), curOrgId, isCompany);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *参数改变时发生-User defined method
     *@param comId comId
     */
    public void paramCheck(String comId) throws BOSException, EASBizException
    {
        try {
            getController().paramCheck(getContext(), comId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}