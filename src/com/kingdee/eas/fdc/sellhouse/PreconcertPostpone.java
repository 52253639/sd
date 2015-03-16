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

public class PreconcertPostpone extends FDCBill implements IPreconcertPostpone
{
    public PreconcertPostpone()
    {
        super();
        registerInterface(IPreconcertPostpone.class, this);
    }
    public PreconcertPostpone(Context ctx)
    {
        super(ctx);
        registerInterface(IPreconcertPostpone.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("59919274");
    }
    private PreconcertPostponeController getController() throws BOSException
    {
        return (PreconcertPostponeController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PreconcertPostponeCollection getPreconcertPostponeCollection() throws BOSException
    {
        try {
            return getController().getPreconcertPostponeCollection(getContext());
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
    public PreconcertPostponeCollection getPreconcertPostponeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPreconcertPostponeCollection(getContext(), view);
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
    public PreconcertPostponeCollection getPreconcertPostponeCollection(String oql) throws BOSException
    {
        try {
            return getController().getPreconcertPostponeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PreconcertPostponeInfo getPreconcertPostponeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPreconcertPostponeInfo(getContext(), pk);
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
    public PreconcertPostponeInfo getPreconcertPostponeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPreconcertPostponeInfo(getContext(), pk, selector);
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
    public PreconcertPostponeInfo getPreconcertPostponeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPreconcertPostponeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}