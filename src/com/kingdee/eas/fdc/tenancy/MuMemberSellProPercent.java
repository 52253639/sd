package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class MuMemberSellProPercent extends DataBase implements IMuMemberSellProPercent
{
    public MuMemberSellProPercent()
    {
        super();
        registerInterface(IMuMemberSellProPercent.class, this);
    }
    public MuMemberSellProPercent(Context ctx)
    {
        super(ctx);
        registerInterface(IMuMemberSellProPercent.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BC5E45B5");
    }
    private MuMemberSellProPercentController getController() throws BOSException
    {
        return (MuMemberSellProPercentController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MuMemberSellProPercentInfo getMuMemberSellProPercentInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMuMemberSellProPercentInfo(getContext(), pk);
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
    public MuMemberSellProPercentInfo getMuMemberSellProPercentInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMuMemberSellProPercentInfo(getContext(), pk, selector);
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
    public MuMemberSellProPercentInfo getMuMemberSellProPercentInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMuMemberSellProPercentInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MuMemberSellProPercentCollection getMuMemberSellProPercentCollection() throws BOSException
    {
        try {
            return getController().getMuMemberSellProPercentCollection(getContext());
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
    public MuMemberSellProPercentCollection getMuMemberSellProPercentCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMuMemberSellProPercentCollection(getContext(), view);
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
    public MuMemberSellProPercentCollection getMuMemberSellProPercentCollection(String oql) throws BOSException
    {
        try {
            return getController().getMuMemberSellProPercentCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}