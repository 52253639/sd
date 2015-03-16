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
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public class BasePriceControl extends FDCBill implements IBasePriceControl
{
    public BasePriceControl()
    {
        super();
        registerInterface(IBasePriceControl.class, this);
    }
    public BasePriceControl(Context ctx)
    {
        super(ctx);
        registerInterface(IBasePriceControl.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5EE7F220");
    }
    private BasePriceControlController getController() throws BOSException
    {
        return (BasePriceControlController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public BasePriceControlInfo getBasePriceControlInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBasePriceControlInfo(getContext(), pk);
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
    public BasePriceControlInfo getBasePriceControlInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBasePriceControlInfo(getContext(), pk, selector);
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
    public BasePriceControlInfo getBasePriceControlInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBasePriceControlInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public BasePriceControlCollection getBasePriceControlCollection() throws BOSException
    {
        try {
            return getController().getBasePriceControlCollection(getContext());
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
    public BasePriceControlCollection getBasePriceControlCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBasePriceControlCollection(getContext(), view);
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
    public BasePriceControlCollection getBasePriceControlCollection(String oql) throws BOSException
    {
        try {
            return getController().getBasePriceControlCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updateRoom-User defined method
     *@param roomList roomList
     */
    public void updateRoom(List roomList) throws BOSException, EASBizException
    {
        try {
            getController().updateRoom(getContext(), roomList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getRoomInfoList-User defined method
     *@param filterString filterString
     *@return
     */
    public List getRoomInfoList(String filterString) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomInfoList(getContext(), filterString);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批-User defined method
     *@param billId 单据id
     */
    public void auditBasePrice(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().auditBasePrice(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批-User defined method
     *@param billId billId
     */
    public void unAuditBasePrice(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().unAuditBasePrice(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *置提交-User defined method
     *@param billId 单据id
     */
    public void setSubmit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setSubmit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *置审批中状态-User defined method
     *@param billId 单据id
     */
    public void setAuditing(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setAuditing(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}