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

public class ShareOrgUnit extends FDCDataBase implements IShareOrgUnit
{
    public ShareOrgUnit()
    {
        super();
        registerInterface(IShareOrgUnit.class, this);
    }
    public ShareOrgUnit(Context ctx)
    {
        super(ctx);
        registerInterface(IShareOrgUnit.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D78D6004");
    }
    private ShareOrgUnitController getController() throws BOSException
    {
        return (ShareOrgUnitController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public ShareOrgUnitInfo getShareOrgUnitInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getShareOrgUnitInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public ShareOrgUnitInfo getShareOrgUnitInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getShareOrgUnitInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public ShareOrgUnitInfo getShareOrgUnitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getShareOrgUnitInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ShareOrgUnitCollection getShareOrgUnitCollection() throws BOSException
    {
        try {
            return getController().getShareOrgUnitCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view view
     *@return
     */
    public ShareOrgUnitCollection getShareOrgUnitCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getShareOrgUnitCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql oql
     *@return
     */
    public ShareOrgUnitCollection getShareOrgUnitCollection(String oql) throws BOSException
    {
        try {
            return getController().getShareOrgUnitCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}