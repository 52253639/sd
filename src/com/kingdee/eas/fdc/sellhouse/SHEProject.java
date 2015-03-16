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
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.framework.TreeBase;

public class SHEProject extends TreeBase implements ISHEProject
{
    public SHEProject()
    {
        super();
        registerInterface(ISHEProject.class, this);
    }
    public SHEProject(Context ctx)
    {
        super(ctx);
        registerInterface(ISHEProject.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8BD71CA5");
    }
    private SHEProjectController getController() throws BOSException
    {
        return (SHEProjectController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SHEProjectInfo getSHEProjectInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEProjectInfo(getContext(), pk);
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
    public SHEProjectInfo getSHEProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEProjectInfo(getContext(), pk, selector);
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
    public SHEProjectInfo getSHEProjectInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEProjectInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SHEProjectCollection getSHEProjectCollection() throws BOSException
    {
        try {
            return getController().getSHEProjectCollection(getContext());
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
    public SHEProjectCollection getSHEProjectCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSHEProjectCollection(getContext(), view);
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
    public SHEProjectCollection getSHEProjectCollection(String oql) throws BOSException
    {
        try {
            return getController().getSHEProjectCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updateRoomModel-User defined method
     *@param idList idList
     *@param id id
     */
    public void updateRoomModel(List idList, String id) throws BOSException, EASBizException
    {
        try {
            getController().updateRoomModel(getContext(), idList, id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除户型-User defined method
     *@param idList idList
     */
    public void deleteRoomModel(List idList) throws BOSException, EASBizException
    {
        try {
            getController().deleteRoomModel(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置启用或者禁用-User defined method
     *@param id id
     *@param isEnabled 是启用还是禁用
     */
    public void updateEnable(BOSUuid id, boolean isEnabled) throws BOSException, EASBizException
    {
        try {
            getController().updateEnable(getContext(), id, isEnabled);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步售楼项目到集团项目-User defined method
     *@param id id
     */
    public void updateSHEProjectToSellProject(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().updateSHEProjectToSellProject(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步集团项目到售楼项目-User defined method
     *@param orgId orgId
     */
    public void updateSellProjectToSHEProject(String orgId) throws BOSException, EASBizException
    {
        try {
            getController().updateSellProjectToSHEProject(getContext(), orgId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}