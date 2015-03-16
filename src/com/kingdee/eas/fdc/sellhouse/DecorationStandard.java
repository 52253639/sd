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

public class DecorationStandard extends DataBase implements IDecorationStandard
{
    public DecorationStandard()
    {
        super();
        registerInterface(IDecorationStandard.class, this);
    }
    public DecorationStandard(Context ctx)
    {
        super(ctx);
        registerInterface(IDecorationStandard.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A72B11A8");
    }
    private DecorationStandardController getController() throws BOSException
    {
        return (DecorationStandardController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public DecorationStandardInfo getDecorationStandardInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDecorationStandardInfo(getContext(), pk);
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
    public DecorationStandardInfo getDecorationStandardInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDecorationStandardInfo(getContext(), pk, selector);
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
    public DecorationStandardInfo getDecorationStandardInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDecorationStandardInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DecorationStandardCollection getDecorationStandardCollection() throws BOSException
    {
        try {
            return getController().getDecorationStandardCollection(getContext());
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
    public DecorationStandardCollection getDecorationStandardCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDecorationStandardCollection(getContext(), view);
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
    public DecorationStandardCollection getDecorationStandardCollection(String oql) throws BOSException
    {
        try {
            return getController().getDecorationStandardCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}