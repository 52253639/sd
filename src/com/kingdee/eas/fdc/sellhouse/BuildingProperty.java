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

public class BuildingProperty extends FDCDataBase implements IBuildingProperty
{
    public BuildingProperty()
    {
        super();
        registerInterface(IBuildingProperty.class, this);
    }
    public BuildingProperty(Context ctx)
    {
        super(ctx);
        registerInterface(IBuildingProperty.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BC30B184");
    }
    private BuildingPropertyController getController() throws BOSException
    {
        return (BuildingPropertyController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public BuildingPropertyInfo getBuildingPropertyInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingPropertyInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public BuildingPropertyInfo getBuildingPropertyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingPropertyInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public BuildingPropertyInfo getBuildingPropertyInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingPropertyInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BuildingPropertyCollection getBuildingPropertyCollection() throws BOSException
    {
        try {
            return getController().getBuildingPropertyCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public BuildingPropertyCollection getBuildingPropertyCollection(String oql) throws BOSException
    {
        try {
            return getController().getBuildingPropertyCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public BuildingPropertyCollection getBuildingPropertyCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBuildingPropertyCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}