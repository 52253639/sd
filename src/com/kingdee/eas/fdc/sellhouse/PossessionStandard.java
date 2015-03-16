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

public class PossessionStandard extends DataBase implements IPossessionStandard
{
    public PossessionStandard()
    {
        super();
        registerInterface(IPossessionStandard.class, this);
    }
    public PossessionStandard(Context ctx)
    {
        super(ctx);
        registerInterface(IPossessionStandard.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("CA7170BA");
    }
    private PossessionStandardController getController() throws BOSException
    {
        return (PossessionStandardController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PossessionStandardInfo getPossessionStandardInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPossessionStandardInfo(getContext(), pk);
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
    public PossessionStandardInfo getPossessionStandardInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPossessionStandardInfo(getContext(), pk, selector);
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
    public PossessionStandardInfo getPossessionStandardInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPossessionStandardInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PossessionStandardCollection getPossessionStandardCollection() throws BOSException
    {
        try {
            return getController().getPossessionStandardCollection(getContext());
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
    public PossessionStandardCollection getPossessionStandardCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPossessionStandardCollection(getContext(), view);
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
    public PossessionStandardCollection getPossessionStandardCollection(String oql) throws BOSException
    {
        try {
            return getController().getPossessionStandardCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}