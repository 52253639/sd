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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class FirstPayProportion extends FDCDataBase implements IFirstPayProportion
{
    public FirstPayProportion()
    {
        super();
        registerInterface(IFirstPayProportion.class, this);
    }
    public FirstPayProportion(Context ctx)
    {
        super(ctx);
        registerInterface(IFirstPayProportion.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("186CA0AD");
    }
    private FirstPayProportionController getController() throws BOSException
    {
        return (FirstPayProportionController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FirstPayProportionInfo getFirstPayProportionInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFirstPayProportionInfo(getContext(), pk);
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
    public FirstPayProportionInfo getFirstPayProportionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFirstPayProportionInfo(getContext(), pk, selector);
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
    public FirstPayProportionInfo getFirstPayProportionInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFirstPayProportionInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FirstPayProportionCollection getFirstPayProportionCollection() throws BOSException
    {
        try {
            return getController().getFirstPayProportionCollection(getContext());
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
    public FirstPayProportionCollection getFirstPayProportionCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFirstPayProportionCollection(getContext(), view);
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
    public FirstPayProportionCollection getFirstPayProportionCollection(String oql) throws BOSException
    {
        try {
            return getController().getFirstPayProportionCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}