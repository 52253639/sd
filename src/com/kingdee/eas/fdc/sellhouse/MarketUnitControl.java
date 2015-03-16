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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.framework.IDataBase;

public class MarketUnitControl extends DataBase implements IMarketUnitControl
{
    public MarketUnitControl()
    {
        super();
        registerInterface(IMarketUnitControl.class, this);
    }
    public MarketUnitControl(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketUnitControl.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("812147E2");
    }
    private MarketUnitControlController getController() throws BOSException
    {
        return (MarketUnitControlController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MarketUnitControlInfo getMarketUnitControlInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketUnitControlInfo(getContext(), pk);
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
    public MarketUnitControlInfo getMarketUnitControlInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketUnitControlInfo(getContext(), pk, selector);
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
    public MarketUnitControlInfo getMarketUnitControlInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketUnitControlInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MarketUnitControlCollection getMarketUnitControlCollection() throws BOSException
    {
        try {
            return getController().getMarketUnitControlCollection(getContext());
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
    public MarketUnitControlCollection getMarketUnitControlCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketUnitControlCollection(getContext(), view);
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
    public MarketUnitControlCollection getMarketUnitControlCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketUnitControlCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新管控权限-User defined method
     *@param orgUnitInfo 组织
     *@param ctrolUserInfo 营销顾问
     *@param isAdd 是否是增加
     */
    public void updateMuPermission(FullOrgUnitInfo orgUnitInfo, UserInfo ctrolUserInfo, boolean isAdd) throws BOSException, EASBizException
    {
        try {
            getController().updateMuPermission(getContext(), orgUnitInfo, ctrolUserInfo, isAdd);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}