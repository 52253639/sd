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

public class SHEShareOrgUnit extends FDCDataBase implements ISHEShareOrgUnit
{
    public SHEShareOrgUnit()
    {
        super();
        registerInterface(ISHEShareOrgUnit.class, this);
    }
    public SHEShareOrgUnit(Context ctx)
    {
        super(ctx);
        registerInterface(ISHEShareOrgUnit.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B1C2963E");
    }
    private SHEShareOrgUnitController getController() throws BOSException
    {
        return (SHEShareOrgUnitController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SHEShareOrgUnitInfo getSHEShareOrgUnitInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEShareOrgUnitInfo(getContext(), pk);
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
    public SHEShareOrgUnitInfo getSHEShareOrgUnitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEShareOrgUnitInfo(getContext(), pk, selector);
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
    public SHEShareOrgUnitInfo getSHEShareOrgUnitInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEShareOrgUnitInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SHEShareOrgUnitCollection getSHEShareOrgUnitCollection() throws BOSException
    {
        try {
            return getController().getSHEShareOrgUnitCollection(getContext());
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
    public SHEShareOrgUnitCollection getSHEShareOrgUnitCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSHEShareOrgUnitCollection(getContext(), view);
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
    public SHEShareOrgUnitCollection getSHEShareOrgUnitCollection(String oql) throws BOSException
    {
        try {
            return getController().getSHEShareOrgUnitCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}