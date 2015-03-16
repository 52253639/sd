package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.propertymgmt.IPPMProjectDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.propertymgmt.PPMProjectDataBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class MoneyDefineSellProject extends PPMProjectDataBase implements IMoneyDefineSellProject
{
    public MoneyDefineSellProject()
    {
        super();
        registerInterface(IMoneyDefineSellProject.class, this);
    }
    public MoneyDefineSellProject(Context ctx)
    {
        super(ctx);
        registerInterface(IMoneyDefineSellProject.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A10D4667");
    }
    private MoneyDefineSellProjectController getController() throws BOSException
    {
        return (MoneyDefineSellProjectController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MoneyDefineSellProjectInfo getMoneyDefineSellProjectInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMoneyDefineSellProjectInfo(getContext(), pk);
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
    public MoneyDefineSellProjectInfo getMoneyDefineSellProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMoneyDefineSellProjectInfo(getContext(), pk, selector);
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
    public MoneyDefineSellProjectInfo getMoneyDefineSellProjectInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMoneyDefineSellProjectInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MoneyDefineSellProjectCollection getMoneyDefineSellProjectCollection() throws BOSException
    {
        try {
            return getController().getMoneyDefineSellProjectCollection(getContext());
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
    public MoneyDefineSellProjectCollection getMoneyDefineSellProjectCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMoneyDefineSellProjectCollection(getContext(), view);
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
    public MoneyDefineSellProjectCollection getMoneyDefineSellProjectCollection(String oql) throws BOSException
    {
        try {
            return getController().getMoneyDefineSellProjectCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}