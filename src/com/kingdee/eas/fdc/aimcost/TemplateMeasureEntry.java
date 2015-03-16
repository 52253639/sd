package com.kingdee.eas.fdc.aimcost;

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
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class TemplateMeasureEntry extends DataBase implements ITemplateMeasureEntry
{
    public TemplateMeasureEntry()
    {
        super();
        registerInterface(ITemplateMeasureEntry.class, this);
    }
    public TemplateMeasureEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITemplateMeasureEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5E698F25");
    }
    private TemplateMeasureEntryController getController() throws BOSException
    {
        return (TemplateMeasureEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public TemplateMeasureEntryInfo getTemplateMeasureEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateMeasureEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public TemplateMeasureEntryInfo getTemplateMeasureEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateMeasureEntryInfo(getContext(), pk, selector);
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
    public TemplateMeasureEntryInfo getTemplateMeasureEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateMeasureEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TemplateMeasureEntryCollection getTemplateMeasureEntryCollection() throws BOSException
    {
        try {
            return getController().getTemplateMeasureEntryCollection(getContext());
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
    public TemplateMeasureEntryCollection getTemplateMeasureEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTemplateMeasureEntryCollection(getContext(), view);
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
    public TemplateMeasureEntryCollection getTemplateMeasureEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTemplateMeasureEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}