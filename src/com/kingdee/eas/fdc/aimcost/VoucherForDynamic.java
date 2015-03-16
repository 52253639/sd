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
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class VoucherForDynamic extends FDCBill implements IVoucherForDynamic
{
    public VoucherForDynamic()
    {
        super();
        registerInterface(IVoucherForDynamic.class, this);
    }
    public VoucherForDynamic(Context ctx)
    {
        super(ctx);
        registerInterface(IVoucherForDynamic.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F5E6400D");
    }
    private VoucherForDynamicController getController() throws BOSException
    {
        return (VoucherForDynamicController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public VoucherForDynamicInfo getVoucherForDynamicInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getVoucherForDynamicInfo(getContext(), pk);
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
    public VoucherForDynamicInfo getVoucherForDynamicInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getVoucherForDynamicInfo(getContext(), pk, selector);
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
    public VoucherForDynamicInfo getVoucherForDynamicInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getVoucherForDynamicInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public VoucherForDynamicCollection getVoucherForDynamicCollection() throws BOSException
    {
        try {
            return getController().getVoucherForDynamicCollection(getContext());
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
    public VoucherForDynamicCollection getVoucherForDynamicCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getVoucherForDynamicCollection(getContext(), view);
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
    public VoucherForDynamicCollection getVoucherForDynamicCollection(String oql) throws BOSException
    {
        try {
            return getController().getVoucherForDynamicCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}