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

public class MoneyDefine extends FDCDataBase implements IMoneyDefine
{
    public MoneyDefine()
    {
        super();
        registerInterface(IMoneyDefine.class, this);
    }
    public MoneyDefine(Context ctx)
    {
        super(ctx);
        registerInterface(IMoneyDefine.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B8B0A8E0");
    }
    private MoneyDefineController getController() throws BOSException
    {
        return (MoneyDefineController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public MoneyDefineInfo getMoneyDefineInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMoneyDefineInfo(getContext(), pk);
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
    public MoneyDefineInfo getMoneyDefineInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMoneyDefineInfo(getContext(), pk, selector);
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
    public MoneyDefineInfo getMoneyDefineInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMoneyDefineInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MoneyDefineCollection getMoneyDefineCollection() throws BOSException
    {
        try {
            return getController().getMoneyDefineCollection(getContext());
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
    public MoneyDefineCollection getMoneyDefineCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMoneyDefineCollection(getContext(), view);
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
    public MoneyDefineCollection getMoneyDefineCollection(String oql) throws BOSException
    {
        try {
            return getController().getMoneyDefineCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *导入集团款项到组织-User defined method
     *@param col col
     *@return
     */
    public int importToOrg(CoreBaseCollection col) throws BOSException, EASBizException
    {
        try {
            return getController().importToOrg(getContext(), col);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}