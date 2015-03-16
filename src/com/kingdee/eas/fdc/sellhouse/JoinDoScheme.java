package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;
import java.util.List;

public class JoinDoScheme extends FDCDataBase implements IJoinDoScheme
{
    public JoinDoScheme()
    {
        super();
        registerInterface(IJoinDoScheme.class, this);
    }
    public JoinDoScheme(Context ctx)
    {
        super(ctx);
        registerInterface(IJoinDoScheme.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F973C7B5");
    }
    private JoinDoSchemeController getController() throws BOSException
    {
        return (JoinDoSchemeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public JoinDoSchemeInfo getJoinDoSchemeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getJoinDoSchemeInfo(getContext(), pk);
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
    public JoinDoSchemeInfo getJoinDoSchemeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getJoinDoSchemeInfo(getContext(), pk, selector);
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
    public JoinDoSchemeInfo getJoinDoSchemeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getJoinDoSchemeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPKList-System defined method
     *@return
     */
    public IObjectPK[] getPKList() throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPKList-System defined method
     *@param oql oql
     *@return
     */
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPKList-System defined method
     *@param filter filter
     *@param sorter sorter
     *@return
     */
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext(), filter, sorter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public JoinDoSchemeCollection getJoinDoSchemeCollection() throws BOSException
    {
        try {
            return getController().getJoinDoSchemeCollection(getContext());
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
    public JoinDoSchemeCollection getJoinDoSchemeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getJoinDoSchemeCollection(getContext(), view);
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
    public JoinDoSchemeCollection getJoinDoSchemeCollection(String oql) throws BOSException
    {
        try {
            return getController().getJoinDoSchemeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *方案批量的禁用和启用-User defined method
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