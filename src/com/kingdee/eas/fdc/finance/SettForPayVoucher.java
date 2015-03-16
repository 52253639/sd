package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class SettForPayVoucher extends FDCBill implements ISettForPayVoucher
{
    public SettForPayVoucher()
    {
        super();
        registerInterface(ISettForPayVoucher.class, this);
    }
    public SettForPayVoucher(Context ctx)
    {
        super(ctx);
        registerInterface(ISettForPayVoucher.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("CE66FA2E");
    }
    private SettForPayVoucherController getController() throws BOSException
    {
        return (SettForPayVoucherController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SettForPayVoucherInfo getSettForPayVoucherInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSettForPayVoucherInfo(getContext(), pk);
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
    public SettForPayVoucherInfo getSettForPayVoucherInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSettForPayVoucherInfo(getContext(), pk, selector);
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
    public SettForPayVoucherInfo getSettForPayVoucherInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSettForPayVoucherInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SettForPayVoucherCollection getSettForPayVoucherCollection() throws BOSException
    {
        try {
            return getController().getSettForPayVoucherCollection(getContext());
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
    public SettForPayVoucherCollection getSettForPayVoucherCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSettForPayVoucherCollection(getContext(), view);
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
    public SettForPayVoucherCollection getSettForPayVoucherCollection(String oql) throws BOSException
    {
        try {
            return getController().getSettForPayVoucherCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}