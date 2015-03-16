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

public class MarketGradeSetUp extends FDCDataBase implements IMarketGradeSetUp
{
    public MarketGradeSetUp()
    {
        super();
        registerInterface(IMarketGradeSetUp.class, this);
    }
    public MarketGradeSetUp(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketGradeSetUp.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4C48410F");
    }
    private MarketGradeSetUpController getController() throws BOSException
    {
        return (MarketGradeSetUpController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MarketGradeSetUpInfo getMarketGradeSetUpInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketGradeSetUpInfo(getContext(), pk);
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
    public MarketGradeSetUpInfo getMarketGradeSetUpInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketGradeSetUpInfo(getContext(), pk, selector);
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
    public MarketGradeSetUpInfo getMarketGradeSetUpInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketGradeSetUpInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MarketGradeSetUpCollection getMarketGradeSetUpCollection() throws BOSException
    {
        try {
            return getController().getMarketGradeSetUpCollection(getContext());
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
    public MarketGradeSetUpCollection getMarketGradeSetUpCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketGradeSetUpCollection(getContext(), view);
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
    public MarketGradeSetUpCollection getMarketGradeSetUpCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketGradeSetUpCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *是否删除等级别名-User defined method
     *@param gradeName 等级别名
     */
    public void isNdelete(String gradeName) throws BOSException, EASBizException
    {
        try {
            getController().isNdelete(getContext(), gradeName);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}