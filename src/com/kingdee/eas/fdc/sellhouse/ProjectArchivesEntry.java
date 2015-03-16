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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class ProjectArchivesEntry extends DataBase implements IProjectArchivesEntry
{
    public ProjectArchivesEntry()
    {
        super();
        registerInterface(IProjectArchivesEntry.class, this);
    }
    public ProjectArchivesEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectArchivesEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("92090FC3");
    }
    private ProjectArchivesEntryController getController() throws BOSException
    {
        return (ProjectArchivesEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public ProjectArchivesEntryInfo getProjectArchivesEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectArchivesEntryInfo(getContext(), pk);
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
    public ProjectArchivesEntryInfo getProjectArchivesEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectArchivesEntryInfo(getContext(), pk, selector);
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
    public ProjectArchivesEntryInfo getProjectArchivesEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectArchivesEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ProjectArchivesEntryCollection getProjectArchivesEntryCollection() throws BOSException
    {
        try {
            return getController().getProjectArchivesEntryCollection(getContext());
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
    public ProjectArchivesEntryCollection getProjectArchivesEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectArchivesEntryCollection(getContext(), view);
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
    public ProjectArchivesEntryCollection getProjectArchivesEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectArchivesEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}