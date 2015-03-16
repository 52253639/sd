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
import java.util.List;

public class PropertyDoScheme extends FDCDataBase implements IPropertyDoScheme
{
    public PropertyDoScheme()
    {
        super();
        registerInterface(IPropertyDoScheme.class, this);
    }
    public PropertyDoScheme(Context ctx)
    {
        super(ctx);
        registerInterface(IPropertyDoScheme.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C65D47E0");
    }
    private PropertyDoSchemeController getController() throws BOSException
    {
        return (PropertyDoSchemeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PropertyDoSchemeInfo getPropertyDoSchemeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPropertyDoSchemeInfo(getContext(), pk);
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
    public PropertyDoSchemeInfo getPropertyDoSchemeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPropertyDoSchemeInfo(getContext(), pk, selector);
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
    public PropertyDoSchemeInfo getPropertyDoSchemeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPropertyDoSchemeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PropertyDoSchemeCollection getPropertyDoSchemeCollection() throws BOSException
    {
        try {
            return getController().getPropertyDoSchemeCollection(getContext());
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
    public PropertyDoSchemeCollection getPropertyDoSchemeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPropertyDoSchemeCollection(getContext(), view);
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
    public PropertyDoSchemeCollection getPropertyDoSchemeCollection(String oql) throws BOSException
    {
        try {
            return getController().getPropertyDoSchemeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *启用-User defined method
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
     *禁用-User defined method
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
     *方案的批量的启用和禁用-User defined method
     *@param idList idList
     *@param isEnabled isEnabled
     */
    public void setEnabled(List idList, boolean isEnabled) throws BOSException, EASBizException
    {
        try {
            getController().setEnabled(getContext(), idList, isEnabled);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}