package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.math.BigDecimal;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.programming.app.*;
import com.kingdee.bos.util.BOSUuid;

public class Programming extends FDCBill implements IProgramming
{
    public Programming()
    {
        super();
        registerInterface(IProgramming.class, this);
    }
    public Programming(Context ctx)
    {
        super(ctx);
        registerInterface(IProgramming.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("53B0BDA9");
    }
    private ProgrammingController getController() throws BOSException
    {
        return (ProgrammingController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ProgrammingInfo getProgrammingInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingInfo(getContext(), pk);
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
    public ProgrammingInfo getProgrammingInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingInfo(getContext(), pk, selector);
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
    public ProgrammingInfo getProgrammingInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProgrammingCollection getProgrammingCollection() throws BOSException
    {
        try {
            return getController().getProgrammingCollection(getContext());
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
    public ProgrammingCollection getProgrammingCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProgrammingCollection(getContext(), view);
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
    public ProgrammingCollection getProgrammingCollection(String oql) throws BOSException
    {
        try {
            return getController().getProgrammingCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *最新版本-User defined method
     *@param pk pk
     *@return
     */
    public ProgrammingInfo getLastVersion(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getLastVersion(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *是否最新版本-User defined method
     *@param pk pk
     *@return
     */
    public boolean isLastVersion(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().isLastVersion(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *最新版本-User defined method
     *@param versionGroup 版本组
     *@return
     */
    public ProgrammingInfo getLastVersion(String versionGroup) throws BOSException, EASBizException
    {
        try {
            return getController().getLastVersion(getContext(), versionGroup);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批中-User defined method
     *@param billId billId
     */
    public void setAuttingForWF(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setAuttingForWF(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *修订-User defined method
     *@param versionGroup 版本组
     *@param curVersion 当前最新版本
     *@return
     */
    public IObjectPK billModify(String versionGroup, String curVersion) throws BOSException, EASBizException
    {
        try {
            return getController().billModify(getContext(), versionGroup, curVersion);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置提交状态-User defined method
     *@param billId billId
     */
    public void setSubmitStatus(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setSubmitStatus(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *新增合约规划是否超过金额-User defined method
     *@param billId billId
     *@param amount amount
     *@return
     */
    public boolean isAddPCPass(BOSUuid billId, BigDecimal amount) throws BOSException, EASBizException
    {
        try {
            return getController().isAddPCPass(getContext(), billId, amount);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *修改合约规划是否超过金额-User defined method
     *@param billId billId
     *@param amount amount
     *@return
     */
    public boolean isEditPCPass(BOSUuid billId, BigDecimal amount) throws BOSException, EASBizException
    {
        try {
            return getController().isEditPCPass(getContext(), billId, amount);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}