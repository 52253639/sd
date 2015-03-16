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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.base.permission.UserInfo;
import java.util.ArrayList;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.framework.TreeBase;

public class SellProject extends TreeBase implements ISellProject
{
    public SellProject()
    {
        super();
        registerInterface(ISellProject.class, this);
    }
    public SellProject(Context ctx)
    {
        super(ctx);
        registerInterface(ISellProject.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2FFBE5AC");
    }
    private SellProjectController getController() throws BOSException
    {
        return (SellProjectController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public SellProjectInfo getSellProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSellProjectInfo(getContext(), pk, selector);
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
    public SellProjectInfo getSellProjectInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSellProjectInfo(getContext(), pk);
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
    public SellProjectInfo getSellProjectInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSellProjectInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SellProjectCollection getSellProjectCollection() throws BOSException
    {
        try {
            return getController().getSellProjectCollection(getContext());
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
    public SellProjectCollection getSellProjectCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSellProjectCollection(getContext(), view);
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
    public SellProjectCollection getSellProjectCollection(String oql) throws BOSException
    {
        try {
            return getController().getSellProjectCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *不会关联查出分录-User defined method
     *@param idStr ID
     *@return
     */
    public SellProjectInfo getBaseValue(String idStr) throws BOSException
    {
        try {
            return getController().getBaseValue(getContext(), idStr);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *无分录信息-User defined method
     *@param idSet id集合
     *@return
     */
    public SellProjectCollection getBaseCollection(Set idSet) throws BOSException
    {
        try {
            return getController().getBaseCollection(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *结束初始化-User defined method
     *@param projectIds 项目ID
     *@param orgUnitId 组织ID
     *@param userInfo 用户
     *@return
     */
    public boolean endInit(List projectIds, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException
    {
        try {
            return getController().endInit(getContext(), projectIds, orgUnitId, userInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反初始化-User defined method
     *@param projectIds 项目ID
     *@param orgUnitId 组织ID
     *@param userInfo 用户
     *@return
     */
    public boolean unInit(List projectIds, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException
    {
        try {
            return getController().unInit(getContext(), projectIds, orgUnitId, userInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *全部结束初始化-User defined method
     *@param orgUnitId 组织ID
     *@param userInfo 当前用户
     *@return
     */
    public boolean allEndInit(String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException
    {
        try {
            return getController().allEndInit(getContext(), orgUnitId, userInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *全部反初始化-User defined method
     *@param orgUnitId 组织ID
     *@param userInfo 当前用户
     *@return
     */
    public boolean allUnInit(String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException
    {
        try {
            return getController().allUnInit(getContext(), orgUnitId, userInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *公司结账成功到下个系统期间-User defined method
     *@param comId 公司ID
     *@param userInfo 当前用户
     */
    public void nextSystem(String comId, UserInfo userInfo) throws BOSException, EASBizException
    {
        try {
            getController().nextSystem(getContext(), comId, userInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反结账到上一个期间-User defined method
     *@param comId 公司ID
     */
    public void preSystem(String comId) throws BOSException, EASBizException
    {
        try {
            getController().preSystem(getContext(), comId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *单个项目档案管控升级-User defined method
     *@param model 项目
     */
    public void projectDataUpdate(IObjectValue model) throws BOSException, EASBizException
    {
        try {
            getController().projectDataUpdate(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *所有项目管控档案升级-User defined method
     */
    public void allProjectDataUpdate() throws BOSException, EASBizException
    {
        try {
            getController().allProjectDataUpdate(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *将项目资料更新成项目-User defined method
     *@param id id
     *@param orgUnitID orgUnitID
     *@param longNumber longNumber
     */
    public void updateToSHEProject(BOSUuid id, BOSUuid orgUnitID, String longNumber) throws BOSException, EASBizException
    {
        try {
            getController().updateToSHEProject(getContext(), id, orgUnitID, longNumber);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *将项目同步到项目资料-User defined method
     *@param id id
     *@param number number
     *@param name name
     */
    public void updateToSellProject(BOSUuid id, String number, String name) throws BOSException, EASBizException
    {
        try {
            getController().updateToSellProject(getContext(), id, number, name);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到项目资料-User defined method
     *@param type type
     *@return
     */
    public ArrayList getSellProTreeNodes(String type) throws BOSException, EASBizException
    {
        try {
            return getController().getSellProTreeNodes(getContext(), type);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新是否删除的状态-User defined method
     *@param billId 单据id
     */
    public void updateDeleteStatus(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().updateDeleteStatus(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除项目建立-User defined method
     *@param billId billId
     */
    public void deleteSellProject(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().deleteSellProject(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除项目资料-User defined method
     *@param billId billId
     */
    public void deleteProjectInSystem(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().deleteProjectInSystem(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *把父类的户型同步到子项目中-User defined method
     *@param billId billId
     *@param roomModelList roomModelList
     */
    public void updateRoomModelForChild(BOSUuid billId, List roomModelList) throws BOSException, EASBizException
    {
        try {
            getController().updateRoomModelForChild(getContext(), billId, roomModelList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}