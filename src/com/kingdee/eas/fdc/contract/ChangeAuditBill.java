package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import java.lang.Object;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.contract.app.*;

public class ChangeAuditBill extends FDCBill implements IChangeAuditBill
{
    public ChangeAuditBill()
    {
        super();
        registerInterface(IChangeAuditBill.class, this);
    }
    public ChangeAuditBill(Context ctx)
    {
        super(ctx);
        registerInterface(IChangeAuditBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("70116117");
    }
    private ChangeAuditBillController getController() throws BOSException
    {
        return (ChangeAuditBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ChangeAuditBillInfo getChangeAuditBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeAuditBillInfo(getContext(), pk);
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
    public ChangeAuditBillInfo getChangeAuditBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeAuditBillInfo(getContext(), pk, selector);
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
    public ChangeAuditBillInfo getChangeAuditBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeAuditBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ChangeAuditBillCollection getChangeAuditBillCollection() throws BOSException
    {
        try {
            return getController().getChangeAuditBillCollection(getContext());
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
    public ChangeAuditBillCollection getChangeAuditBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getChangeAuditBillCollection(getContext(), view);
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
    public ChangeAuditBillCollection getChangeAuditBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getChangeAuditBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *登记-User defined method
     *@param idSet id集合
     */
    public void register(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().register(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *下发-User defined method
     *@param idSet id集合
     */
    public void disPatch(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().disPatch(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *提前下发-User defined method
     *@param idSet id集合
     */
    public void aheadDisPatch(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().aheadDisPatch(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置审批中状态-User defined method
     *@param billId billId
     */
    public void setAudittingStatus(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setAudittingStatus(getContext(), billId);
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
     *登记-User defined method
     *@param pk pk
     */
    public void register4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().register4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *下发-User defined method
     *@param pk pk
     */
    public void disPatch4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().disPatch4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *提前下发-User defined method
     *@param pk pk
     */
    public void aheadDisPatch4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().aheadDisPatch4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *合约规划金额检查-User defined method
     *@param pk 当参数“合同签约金额超过与之关联的合约规划金额时是否严格控制”的参数值为“严格控制”时，变更审批单提交时校验：当前变更审批上关联的多个合同中的“测算金额”+“签约金额”+“累计变更”是否大于“框架合约”的“规划余额”，如果大于：提示：“合同XX”的“测算金额”+“签约金额”+“累计变更”大于“框架合约”的“规划余额”，不允许提交。
     *@param contractMap key:合同Id,value:合同对应的测算金额
     *@return
     */
    public Object checkAmount(IObjectPK pk, Map contractMap) throws BOSException, EASBizException
    {
        try {
            return getController().checkAmount(getContext(), pk, contractMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}