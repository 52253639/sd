package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public class InquiryResult extends FDCBill implements IInquiryResult
{
    public InquiryResult()
    {
        super();
        registerInterface(IInquiryResult.class, this);
    }
    public InquiryResult(Context ctx)
    {
        super(ctx);
        registerInterface(IInquiryResult.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("00224420");
    }
    private InquiryResultController getController() throws BOSException
    {
        return (InquiryResultController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public InquiryResultCollection getInquiryResultCollection() throws BOSException
    {
        try {
            return getController().getInquiryResultCollection(getContext());
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
    public InquiryResultCollection getInquiryResultCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInquiryResultCollection(getContext(), view);
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
    public InquiryResultCollection getInquiryResultCollection(String oql) throws BOSException
    {
        try {
            return getController().getInquiryResultCollection(getContext(), oql);
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
    public InquiryResultInfo getInquiryResultInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInquiryResultInfo(getContext(), pk);
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
    public InquiryResultInfo getInquiryResultInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInquiryResultInfo(getContext(), pk, selector);
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
    public InquiryResultInfo getInquiryResultInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInquiryResultInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批-User defined method
     *@param billId billId
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
     *审批-User defined method
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
     *检查能否提交-User defined method
     *@param id id
     *@return
     */
    public boolean checkCanSubmit(String id) throws BOSException, EASBizException
    {
        try {
            return getController().checkCanSubmit(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取序时簿界面初始化数据-User defined method
     *@param paramMap paramMap
     *@return
     */
    public Map fetchFilterInitData(Map paramMap) throws BOSException, EASBizException
    {
        try {
            return getController().fetchFilterInitData(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取界面初始化数据-User defined method
     *@param paramMap paramMap
     *@return
     */
    public Map fetchInitData(Map paramMap) throws BOSException, EASBizException
    {
        try {
            return getController().fetchInitData(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批-User defined method
     *@param billId billId
     */
    public void unAudit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批-User defined method
     *@param idList idList
     */
    public void unAudit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}