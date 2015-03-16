package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class MarktQuaLevel extends FDCDataBase implements IMarktQuaLevel
{
    public MarktQuaLevel()
    {
        super();
        registerInterface(IMarktQuaLevel.class, this);
    }
    public MarktQuaLevel(Context ctx)
    {
        super(ctx);
        registerInterface(IMarktQuaLevel.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5C11D3A1");
    }
    private MarktQuaLevelController getController() throws BOSException
    {
        return (MarktQuaLevelController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MarktQuaLevelInfo getMarktQuaLevelInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarktQuaLevelInfo(getContext(), pk);
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
    public MarktQuaLevelInfo getMarktQuaLevelInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarktQuaLevelInfo(getContext(), pk, selector);
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
    public MarktQuaLevelInfo getMarktQuaLevelInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarktQuaLevelInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MarktQuaLevelCollection getMarktQuaLevelCollection() throws BOSException
    {
        try {
            return getController().getMarktQuaLevelCollection(getContext());
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
    public MarktQuaLevelCollection getMarktQuaLevelCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarktQuaLevelCollection(getContext(), view);
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
    public MarktQuaLevelCollection getMarktQuaLevelCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarktQuaLevelCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}