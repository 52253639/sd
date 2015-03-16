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
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public class TemplateMeasureCost extends FDCBill implements ITemplateMeasureCost
{
    public TemplateMeasureCost()
    {
        super();
        registerInterface(ITemplateMeasureCost.class, this);
    }
    public TemplateMeasureCost(Context ctx)
    {
        super(ctx);
        registerInterface(ITemplateMeasureCost.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("241306BA");
    }
    private TemplateMeasureCostController getController() throws BOSException
    {
        return (TemplateMeasureCostController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public TemplateMeasureCostInfo getTemplateMeasureCostInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateMeasureCostInfo(getContext(), pk);
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
    public TemplateMeasureCostInfo getTemplateMeasureCostInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateMeasureCostInfo(getContext(), pk, selector);
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
    public TemplateMeasureCostCollection getTemplateMeasureCostCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTemplateMeasureCostCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TemplateMeasureCostCollection getTemplateMeasureCostCollection() throws BOSException
    {
        try {
            return getController().getTemplateMeasureCostCollection(getContext());
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
    public TemplateMeasureCostCollection getTemplateMeasureCostCollection(String oql) throws BOSException
    {
        try {
            return getController().getTemplateMeasureCostCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批批量-User defined method
     *@param billId 单据ID
     */
    public void audit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批批量-User defined method
     *@param idList idList
     */
    public void audit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批批量-User defined method
     *@param billId 单据ID
     */
    public void unaudit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().unaudit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批批量-User defined method
     *@param idList 单据ID列表
     */
    public void unaudit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().unaudit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}