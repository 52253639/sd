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

public class StandardTaskGuideEntry extends CoreBillEntryBase implements IStandardTaskGuideEntry
{
    public StandardTaskGuideEntry()
    {
        super();
        registerInterface(IStandardTaskGuideEntry.class, this);
    }
    public StandardTaskGuideEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IStandardTaskGuideEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DF52EDAE");
    }
    private StandardTaskGuideEntryController getController() throws BOSException
    {
        return (StandardTaskGuideEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public StandardTaskGuideEntryInfo getStandardTaskGuideEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getStandardTaskGuideEntryInfo(getContext(), pk);
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
    public StandardTaskGuideEntryInfo getStandardTaskGuideEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getStandardTaskGuideEntryInfo(getContext(), pk, selector);
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
    public StandardTaskGuideEntryInfo getStandardTaskGuideEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getStandardTaskGuideEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public StandardTaskGuideEntryCollection getStandardTaskGuideEntryCollection() throws BOSException
    {
        try {
            return getController().getStandardTaskGuideEntryCollection(getContext());
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
    public StandardTaskGuideEntryCollection getStandardTaskGuideEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getStandardTaskGuideEntryCollection(getContext(), view);
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
    public StandardTaskGuideEntryCollection getStandardTaskGuideEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getStandardTaskGuideEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}