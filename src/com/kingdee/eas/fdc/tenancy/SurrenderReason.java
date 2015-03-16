package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class SurrenderReason extends FDCDataBase implements ISurrenderReason
{
    public SurrenderReason()
    {
        super();
        registerInterface(ISurrenderReason.class, this);
    }
    public SurrenderReason(Context ctx)
    {
        super(ctx);
        registerInterface(ISurrenderReason.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("21B32501");
    }
    private SurrenderReasonController getController() throws BOSException
    {
        return (SurrenderReasonController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SurrenderReasonInfo getSurrenderReasonInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSurrenderReasonInfo(getContext(), pk);
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
    public SurrenderReasonInfo getSurrenderReasonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSurrenderReasonInfo(getContext(), pk, selector);
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
    public SurrenderReasonInfo getSurrenderReasonInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSurrenderReasonInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SurrenderReasonCollection getSurrenderReasonCollection() throws BOSException
    {
        try {
            return getController().getSurrenderReasonCollection(getContext());
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
    public SurrenderReasonCollection getSurrenderReasonCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSurrenderReasonCollection(getContext(), view);
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
    public SurrenderReasonCollection getSurrenderReasonCollection(String oql) throws BOSException
    {
        try {
            return getController().getSurrenderReasonCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}