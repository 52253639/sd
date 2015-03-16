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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class AgioBill extends FDCBill implements IAgioBill
{
    public AgioBill()
    {
        super();
        registerInterface(IAgioBill.class, this);
    }
    public AgioBill(Context ctx)
    {
        super(ctx);
        registerInterface(IAgioBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C93ED5CE");
    }
    private AgioBillController getController() throws BOSException
    {
        return (AgioBillController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public AgioBillInfo getAgioBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAgioBillInfo(getContext(), pk);
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
    public AgioBillInfo getAgioBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAgioBillInfo(getContext(), pk, selector);
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
    public AgioBillInfo getAgioBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAgioBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public AgioBillCollection getAgioBillCollection() throws BOSException
    {
        try {
            return getController().getAgioBillCollection(getContext());
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
    public AgioBillCollection getAgioBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAgioBillCollection(getContext(), view);
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
    public AgioBillCollection getAgioBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getAgioBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *∆Ù”√-User defined method
     *@param pk pk
     */
    public void enable(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().enable(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ω˚”√-User defined method
     *@param pk pk
     */
    public void disEnable(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().disEnable(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}