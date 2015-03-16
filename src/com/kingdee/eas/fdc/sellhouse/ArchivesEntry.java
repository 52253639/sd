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

public class ArchivesEntry extends DataBase implements IArchivesEntry
{
    public ArchivesEntry()
    {
        super();
        registerInterface(IArchivesEntry.class, this);
    }
    public ArchivesEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IArchivesEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C90B0406");
    }
    private ArchivesEntryController getController() throws BOSException
    {
        return (ArchivesEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public ArchivesEntryInfo getArchivesEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getArchivesEntryInfo(getContext(), pk);
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
    public ArchivesEntryInfo getArchivesEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getArchivesEntryInfo(getContext(), pk, selector);
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
    public ArchivesEntryInfo getArchivesEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getArchivesEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ArchivesEntryCollection getArchivesEntryCollection() throws BOSException
    {
        try {
            return getController().getArchivesEntryCollection(getContext());
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
    public ArchivesEntryCollection getArchivesEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getArchivesEntryCollection(getContext(), view);
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
    public ArchivesEntryCollection getArchivesEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getArchivesEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}