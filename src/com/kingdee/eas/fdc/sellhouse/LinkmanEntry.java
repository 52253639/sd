package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class LinkmanEntry extends FDCDataBase implements ILinkmanEntry
{
    public LinkmanEntry()
    {
        super();
        registerInterface(ILinkmanEntry.class, this);
    }
    public LinkmanEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ILinkmanEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7F04C78D");
    }
    private LinkmanEntryController getController() throws BOSException
    {
        return (LinkmanEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public LinkmanEntryInfo getLinkmanEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getLinkmanEntryInfo(getContext(), pk);
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
    public LinkmanEntryInfo getLinkmanEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getLinkmanEntryInfo(getContext(), pk, selector);
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
    public LinkmanEntryInfo getLinkmanEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getLinkmanEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public LinkmanEntryCollection getLinkmanEntryCollection() throws BOSException
    {
        try {
            return getController().getLinkmanEntryCollection(getContext());
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
    public LinkmanEntryCollection getLinkmanEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getLinkmanEntryCollection(getContext(), view);
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
    public LinkmanEntryCollection getLinkmanEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getLinkmanEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}