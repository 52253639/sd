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

public class AFMortgaged extends FDCDataBase implements IAFMortgaged
{
    public AFMortgaged()
    {
        super();
        registerInterface(IAFMortgaged.class, this);
    }
    public AFMortgaged(Context ctx)
    {
        super(ctx);
        registerInterface(IAFMortgaged.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6FE57B08");
    }
    private AFMortgagedController getController() throws BOSException
    {
        return (AFMortgagedController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public AFMortgagedInfo getAFMortgagedInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAFMortgagedInfo(getContext(), pk);
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
    public AFMortgagedInfo getAFMortgagedInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAFMortgagedInfo(getContext(), pk, selector);
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
    public AFMortgagedInfo getAFMortgagedInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAFMortgagedInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public AFMortgagedCollection getAFMortgagedCollection() throws BOSException
    {
        try {
            return getController().getAFMortgagedCollection(getContext());
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
    public AFMortgagedCollection getAFMortgagedCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAFMortgagedCollection(getContext(), view);
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
    public AFMortgagedCollection getAFMortgagedCollection(String oql) throws BOSException
    {
        try {
            return getController().getAFMortgagedCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *方案的启用和禁用-User defined method
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