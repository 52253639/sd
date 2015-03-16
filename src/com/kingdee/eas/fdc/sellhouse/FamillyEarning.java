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

public class FamillyEarning extends FDCDataBase implements IFamillyEarning
{
    public FamillyEarning()
    {
        super();
        registerInterface(IFamillyEarning.class, this);
    }
    public FamillyEarning(Context ctx)
    {
        super(ctx);
        registerInterface(IFamillyEarning.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("AB323243");
    }
    private FamillyEarningController getController() throws BOSException
    {
        return (FamillyEarningController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public FamillyEarningInfo getFamillyEarningInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFamillyEarningInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FamillyEarningInfo getFamillyEarningInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFamillyEarningInfo(getContext(), pk);
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
    public FamillyEarningInfo getFamillyEarningInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFamillyEarningInfo(getContext(), oql);
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
    public FamillyEarningCollection getFamillyEarningCollection(String oql) throws BOSException
    {
        try {
            return getController().getFamillyEarningCollection(getContext(), oql);
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
    public FamillyEarningCollection getFamillyEarningCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFamillyEarningCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FamillyEarningCollection getFamillyEarningCollection() throws BOSException
    {
        try {
            return getController().getFamillyEarningCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}