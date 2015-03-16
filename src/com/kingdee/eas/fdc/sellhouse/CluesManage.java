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
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class CluesManage extends FDCDataBase implements ICluesManage
{
    public CluesManage()
    {
        super();
        registerInterface(ICluesManage.class, this);
    }
    public CluesManage(Context ctx)
    {
        super(ctx);
        registerInterface(ICluesManage.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EA85C324");
    }
    private CluesManageController getController() throws BOSException
    {
        return (CluesManageController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public CluesManageInfo getCluesManageInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCluesManageInfo(getContext(), pk);
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
    public CluesManageInfo getCluesManageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCluesManageInfo(getContext(), pk, selector);
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
    public CluesManageInfo getCluesManageInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCluesManageInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CluesManageCollection getCluesManageCollection() throws BOSException
    {
        try {
            return getController().getCluesManageCollection(getContext());
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
    public CluesManageCollection getCluesManageCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCluesManageCollection(getContext(), view);
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
    public CluesManageCollection getCluesManageCollection(String oql) throws BOSException
    {
        try {
            return getController().getCluesManageCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *线索客户共享-User defined method
     *@param objectColl objectColl
     *@param map map
     */
    public void shareClues(CluesManageCollection objectColl, Map map) throws BOSException, EASBizException
    {
        try {
            getController().shareClues(getContext(), objectColl, map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *线索客户转交-User defined method
     *@param model model
     *@param map map
     */
    public void deliverClues(CluesManageInfo model, Map map) throws BOSException, EASBizException
    {
        try {
            getController().deliverClues(getContext(), model, map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *线索客户导入-User defined method
     *@param res res
     */
    public void importClues(CluesManageCollection res) throws BOSException, EASBizException
    {
        try {
            getController().importClues(getContext(), res);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *转交易改变线索状态-User defined method
     *@param model model
     *@param firstLinkMan firstLinkMan
     *@return
     */
    public SHECustomerInfo updateCluesStatus(CluesManageInfo model, String firstLinkMan) throws BOSException, EASBizException
    {
        try {
            return getController().updateCluesStatus(getContext(), model, firstLinkMan);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}