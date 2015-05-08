package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public class ProjectImageEntry extends CoreBillEntryBase implements IProjectImageEntry
{
    public ProjectImageEntry()
    {
        super();
        registerInterface(IProjectImageEntry.class, this);
    }
    public ProjectImageEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectImageEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("157C26BA");
    }
    private ProjectImageEntryController getController() throws BOSException
    {
        return (ProjectImageEntryController)getBizController();
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public ProjectImageEntryCollection getProjectImageEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectImageEntryCollection(getContext(), oql);
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
    public ProjectImageEntryCollection getProjectImageEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectImageEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProjectImageEntryCollection getProjectImageEntryCollection() throws BOSException
    {
        try {
            return getController().getProjectImageEntryCollection(getContext());
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
    public ProjectImageEntryInfo getProjectImageEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectImageEntryInfo(getContext(), oql);
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
    public ProjectImageEntryInfo getProjectImageEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectImageEntryInfo(getContext(), pk, selector);
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
    public ProjectImageEntryInfo getProjectImageEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectImageEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取原始图像的方法-User defined method
     *@param ID ID
     *@return
     */
    public byte[] getOriginalImageById(BOSUuid ID) throws BOSException, EASBizException
    {
        try {
            return getController().getOriginalImageById(getContext(), ID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}