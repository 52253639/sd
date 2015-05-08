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
import java.util.Set;
import com.kingdee.eas.fdc.schedule.framework.IScheduleBase;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.base.permission.UserInfo;

public class ProjectImage extends ScheduleBase implements IProjectImage
{
    public ProjectImage()
    {
        super();
        registerInterface(IProjectImage.class, this);
    }
    public ProjectImage(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectImage.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8A7E9098");
    }
    private ProjectImageController getController() throws BOSException
    {
        return (ProjectImageController)getBizController();
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public ProjectImageCollection getProjectImageCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectImageCollection(getContext(), oql);
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
    public ProjectImageCollection getProjectImageCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectImageCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProjectImageCollection getProjectImageCollection() throws BOSException
    {
        try {
            return getController().getProjectImageCollection(getContext());
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
    public ProjectImageInfo getProjectImageInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectImageInfo(getContext(), oql);
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
    public ProjectImageInfo getProjectImageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectImageInfo(getContext(), pk, selector);
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
    public ProjectImageInfo getProjectImageInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectImageInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *…Û∫À-User defined method
     *@param ids ids
     *@param auditor …Û∫À»À
     *@return
     */
    public Map audit(Set ids, UserInfo auditor) throws BOSException, EASBizException
    {
        try {
            return getController().audit(getContext(), ids, auditor);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *∑¥…Û∫À-User defined method
     *@param ids ids
     *@param unAuditor ∑¥…Û∫À≤Ÿ◊˜»À
     *@return
     */
    public Map unAudit(Set ids, UserInfo unAuditor) throws BOSException, EASBizException
    {
        try {
            return getController().unAudit(getContext(), ids, unAuditor);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}