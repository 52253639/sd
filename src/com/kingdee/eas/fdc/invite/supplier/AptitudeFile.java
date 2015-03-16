package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class AptitudeFile extends CoreBillEntryBase implements IAptitudeFile
{
    public AptitudeFile()
    {
        super();
        registerInterface(IAptitudeFile.class, this);
    }
    public AptitudeFile(Context ctx)
    {
        super(ctx);
        registerInterface(IAptitudeFile.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("11DD1A40");
    }
    private AptitudeFileController getController() throws BOSException
    {
        return (AptitudeFileController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public AptitudeFileInfo getAptitudeFileInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAptitudeFileInfo(getContext(), pk);
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
    public AptitudeFileInfo getAptitudeFileInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAptitudeFileInfo(getContext(), pk, selector);
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
    public AptitudeFileInfo getAptitudeFileInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAptitudeFileInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public AptitudeFileCollection getAptitudeFileCollection() throws BOSException
    {
        try {
            return getController().getAptitudeFileCollection(getContext());
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
    public AptitudeFileCollection getAptitudeFileCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAptitudeFileCollection(getContext(), view);
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
    public AptitudeFileCollection getAptitudeFileCollection(String oql) throws BOSException
    {
        try {
            return getController().getAptitudeFileCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}