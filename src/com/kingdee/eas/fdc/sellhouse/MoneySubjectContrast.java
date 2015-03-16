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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ObjectBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IObjectBase;

public class MoneySubjectContrast extends ObjectBase implements IMoneySubjectContrast
{
    public MoneySubjectContrast()
    {
        super();
        registerInterface(IMoneySubjectContrast.class, this);
    }
    public MoneySubjectContrast(Context ctx)
    {
        super(ctx);
        registerInterface(IMoneySubjectContrast.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9BA14AC9");
    }
    private MoneySubjectContrastController getController() throws BOSException
    {
        return (MoneySubjectContrastController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MoneySubjectContrastInfo getMoneySubjectContrastInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMoneySubjectContrastInfo(getContext(), pk);
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
    public MoneySubjectContrastInfo getMoneySubjectContrastInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMoneySubjectContrastInfo(getContext(), pk, selector);
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
    public MoneySubjectContrastInfo getMoneySubjectContrastInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMoneySubjectContrastInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MoneySubjectContrastCollection getMoneySubjectContrastCollection() throws BOSException
    {
        try {
            return getController().getMoneySubjectContrastCollection(getContext());
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
    public MoneySubjectContrastCollection getMoneySubjectContrastCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMoneySubjectContrastCollection(getContext(), view);
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
    public MoneySubjectContrastCollection getMoneySubjectContrastCollection(String oql) throws BOSException
    {
        try {
            return getController().getMoneySubjectContrastCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}