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
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class BusinessOverView extends CoreBillBase implements IBusinessOverView
{
    public BusinessOverView()
    {
        super();
        registerInterface(IBusinessOverView.class, this);
    }
    public BusinessOverView(Context ctx)
    {
        super(ctx);
        registerInterface(IBusinessOverView.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D0101654");
    }
    private BusinessOverViewController getController() throws BOSException
    {
        return (BusinessOverViewController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public BusinessOverViewInfo getBusinessOverViewInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBusinessOverViewInfo(getContext(), pk);
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
    public BusinessOverViewInfo getBusinessOverViewInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBusinessOverViewInfo(getContext(), pk, selector);
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
    public BusinessOverViewInfo getBusinessOverViewInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBusinessOverViewInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public BusinessOverViewCollection getBusinessOverViewCollection() throws BOSException
    {
        try {
            return getController().getBusinessOverViewCollection(getContext());
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
    public BusinessOverViewCollection getBusinessOverViewCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBusinessOverViewCollection(getContext(), view);
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
    public BusinessOverViewCollection getBusinessOverViewCollection(String oql) throws BOSException
    {
        try {
            return getController().getBusinessOverViewCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}