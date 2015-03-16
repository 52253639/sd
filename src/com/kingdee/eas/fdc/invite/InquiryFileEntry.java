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

public class InquiryFileEntry extends CoreBillEntryBase implements IInquiryFileEntry
{
    public InquiryFileEntry()
    {
        super();
        registerInterface(IInquiryFileEntry.class, this);
    }
    public InquiryFileEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IInquiryFileEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B0259173");
    }
    private InquiryFileEntryController getController() throws BOSException
    {
        return (InquiryFileEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public InquiryFileEntryInfo getInquiryFileEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInquiryFileEntryInfo(getContext(), pk);
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
    public InquiryFileEntryInfo getInquiryFileEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInquiryFileEntryInfo(getContext(), pk, selector);
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
    public InquiryFileEntryInfo getInquiryFileEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInquiryFileEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public InquiryFileEntryCollection getInquiryFileEntryCollection() throws BOSException
    {
        try {
            return getController().getInquiryFileEntryCollection(getContext());
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
    public InquiryFileEntryCollection getInquiryFileEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInquiryFileEntryCollection(getContext(), view);
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
    public InquiryFileEntryCollection getInquiryFileEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getInquiryFileEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}