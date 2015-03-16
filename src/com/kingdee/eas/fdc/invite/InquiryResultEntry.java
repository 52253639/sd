package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class InquiryResultEntry extends CoreBillEntryBase implements IInquiryResultEntry
{
    public InquiryResultEntry()
    {
        super();
        registerInterface(IInquiryResultEntry.class, this);
    }
    public InquiryResultEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IInquiryResultEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("09436832");
    }
    private InquiryResultEntryController getController() throws BOSException
    {
        return (InquiryResultEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public InquiryResultEntryInfo getInquiryResultEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInquiryResultEntryInfo(getContext(), pk);
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
    public InquiryResultEntryInfo getInquiryResultEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInquiryResultEntryInfo(getContext(), pk, selector);
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
    public InquiryResultEntryInfo getInquiryResultEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInquiryResultEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public InquiryResultEntryCollection getInquiryResultEntryCollection() throws BOSException
    {
        try {
            return getController().getInquiryResultEntryCollection(getContext());
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
    public InquiryResultEntryCollection getInquiryResultEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInquiryResultEntryCollection(getContext(), view);
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
    public InquiryResultEntryCollection getInquiryResultEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getInquiryResultEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}