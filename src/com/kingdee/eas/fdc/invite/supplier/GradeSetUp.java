package com.kingdee.eas.fdc.invite.supplier;

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
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class GradeSetUp extends FDCDataBase implements IGradeSetUp
{
    public GradeSetUp()
    {
        super();
        registerInterface(IGradeSetUp.class, this);
    }
    public GradeSetUp(Context ctx)
    {
        super(ctx);
        registerInterface(IGradeSetUp.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9A0C7884");
    }
    private GradeSetUpController getController() throws BOSException
    {
        return (GradeSetUpController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public GradeSetUpInfo getGradeSetUpInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getGradeSetUpInfo(getContext(), pk);
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
    public GradeSetUpInfo getGradeSetUpInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getGradeSetUpInfo(getContext(), pk, selector);
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
    public GradeSetUpInfo getGradeSetUpInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getGradeSetUpInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public GradeSetUpCollection getGradeSetUpCollection() throws BOSException
    {
        try {
            return getController().getGradeSetUpCollection(getContext());
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
    public GradeSetUpCollection getGradeSetUpCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getGradeSetUpCollection(getContext(), view);
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
    public GradeSetUpCollection getGradeSetUpCollection(String oql) throws BOSException
    {
        try {
            return getController().getGradeSetUpCollection(getContext(), oql);
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