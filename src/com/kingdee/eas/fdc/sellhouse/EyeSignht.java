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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class EyeSignht extends DataBase implements IEyeSignht
{
    public EyeSignht()
    {
        super();
        registerInterface(IEyeSignht.class, this);
    }
    public EyeSignht(Context ctx)
    {
        super(ctx);
        registerInterface(IEyeSignht.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FC46DC9F");
    }
    private EyeSignhtController getController() throws BOSException
    {
        return (EyeSignhtController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public EyeSignhtInfo getEyeSignhtInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getEyeSignhtInfo(getContext(), pk);
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
    public EyeSignhtInfo getEyeSignhtInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getEyeSignhtInfo(getContext(), pk, selector);
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
    public EyeSignhtInfo getEyeSignhtInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getEyeSignhtInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public EyeSignhtCollection getEyeSignhtCollection() throws BOSException
    {
        try {
            return getController().getEyeSignhtCollection(getContext());
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
    public EyeSignhtCollection getEyeSignhtCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getEyeSignhtCollection(getContext(), view);
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
    public EyeSignhtCollection getEyeSignhtCollection(String oql) throws BOSException
    {
        try {
            return getController().getEyeSignhtCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}