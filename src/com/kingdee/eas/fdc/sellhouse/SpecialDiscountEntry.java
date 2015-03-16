package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class SpecialDiscountEntry extends CoreBillEntryBase implements ISpecialDiscountEntry
{
    public SpecialDiscountEntry()
    {
        super();
        registerInterface(ISpecialDiscountEntry.class, this);
    }
    public SpecialDiscountEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISpecialDiscountEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8D90D2D3");
    }
    private SpecialDiscountEntryController getController() throws BOSException
    {
        return (SpecialDiscountEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SpecialDiscountEntryInfo getSpecialDiscountEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSpecialDiscountEntryInfo(getContext(), pk);
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
    public SpecialDiscountEntryInfo getSpecialDiscountEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSpecialDiscountEntryInfo(getContext(), pk, selector);
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
    public SpecialDiscountEntryInfo getSpecialDiscountEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSpecialDiscountEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SpecialDiscountEntryCollection getSpecialDiscountEntryCollection() throws BOSException
    {
        try {
            return getController().getSpecialDiscountEntryCollection(getContext());
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
    public SpecialDiscountEntryCollection getSpecialDiscountEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSpecialDiscountEntryCollection(getContext(), view);
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
    public SpecialDiscountEntryCollection getSpecialDiscountEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSpecialDiscountEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}