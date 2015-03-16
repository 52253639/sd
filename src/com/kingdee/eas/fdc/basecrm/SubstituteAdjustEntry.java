package com.kingdee.eas.fdc.basecrm;

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
import com.kingdee.eas.fdc.basecrm.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class SubstituteAdjustEntry extends CoreBillEntryBase implements ISubstituteAdjustEntry
{
    public SubstituteAdjustEntry()
    {
        super();
        registerInterface(ISubstituteAdjustEntry.class, this);
    }
    public SubstituteAdjustEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISubstituteAdjustEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C112974D");
    }
    private SubstituteAdjustEntryController getController() throws BOSException
    {
        return (SubstituteAdjustEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SubstituteAdjustEntryInfo getSubstituteAdjustEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSubstituteAdjustEntryInfo(getContext(), pk);
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
    public SubstituteAdjustEntryInfo getSubstituteAdjustEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSubstituteAdjustEntryInfo(getContext(), pk, selector);
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
    public SubstituteAdjustEntryInfo getSubstituteAdjustEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSubstituteAdjustEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SubstituteAdjustEntryCollection getSubstituteAdjustEntryCollection() throws BOSException
    {
        try {
            return getController().getSubstituteAdjustEntryCollection(getContext());
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
    public SubstituteAdjustEntryCollection getSubstituteAdjustEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSubstituteAdjustEntryCollection(getContext(), view);
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
    public SubstituteAdjustEntryCollection getSubstituteAdjustEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSubstituteAdjustEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}