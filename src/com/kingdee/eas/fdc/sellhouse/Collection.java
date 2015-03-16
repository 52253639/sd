package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class Collection extends FDCDataBase implements ICollection
{
    public Collection()
    {
        super();
        registerInterface(ICollection.class, this);
    }
    public Collection(Context ctx)
    {
        super(ctx);
        registerInterface(ICollection.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D3F07FB9");
    }
    private CollectionController getController() throws BOSException
    {
        return (CollectionController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public CollectionInfo getCollectionInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionInfo(getContext(), pk);
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
    public CollectionInfo getCollectionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionInfo(getContext(), pk, selector);
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
    public CollectionInfo getCollectionInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CollectionCollection getCollectionCollection() throws BOSException
    {
        try {
            return getController().getCollectionCollection(getContext());
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
    public CollectionCollection getCollectionCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCollectionCollection(getContext(), view);
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
    public CollectionCollection getCollectionCollection(String oql) throws BOSException
    {
        try {
            return getController().getCollectionCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *∆Ù”√-User defined method
     *@param pk pk
     */
    public void enable(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().enable(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ω˚”√-User defined method
     *@param pk pk
     */
    public void disEnable(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().disEnable(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getArAmout-User defined method
     *@param map map
     *@return
     */
    public Map getArAmout(Map map) throws BOSException
    {
        try {
            return getController().getArAmout(getContext(), map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}