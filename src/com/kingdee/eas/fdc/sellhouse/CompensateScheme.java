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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class CompensateScheme extends FDCBill implements ICompensateScheme
{
    public CompensateScheme()
    {
        super();
        registerInterface(ICompensateScheme.class, this);
    }
    public CompensateScheme(Context ctx)
    {
        super(ctx);
        registerInterface(ICompensateScheme.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BB9AA697");
    }
    private CompensateSchemeController getController() throws BOSException
    {
        return (CompensateSchemeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public CompensateSchemeInfo getCompensateSchemeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensateSchemeInfo(getContext(), pk);
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
    public CompensateSchemeInfo getCompensateSchemeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensateSchemeInfo(getContext(), pk, selector);
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
    public CompensateSchemeInfo getCompensateSchemeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensateSchemeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CompensateSchemeCollection getCompensateSchemeCollection() throws BOSException
    {
        try {
            return getController().getCompensateSchemeCollection(getContext());
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
    public CompensateSchemeCollection getCompensateSchemeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCompensateSchemeCollection(getContext(), view);
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
    public CompensateSchemeCollection getCompensateSchemeCollection(String oql) throws BOSException
    {
        try {
            return getController().getCompensateSchemeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}