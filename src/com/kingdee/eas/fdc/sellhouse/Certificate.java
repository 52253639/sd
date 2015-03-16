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

public class Certificate extends FDCDataBase implements ICertificate
{
    public Certificate()
    {
        super();
        registerInterface(ICertificate.class, this);
    }
    public Certificate(Context ctx)
    {
        super(ctx);
        registerInterface(ICertificate.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("AFFE93DC");
    }
    private CertificateController getController() throws BOSException
    {
        return (CertificateController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public CertificateInfo getCertificateInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCertificateInfo(getContext(), pk);
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
    public CertificateInfo getCertificateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCertificateInfo(getContext(), pk, selector);
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
    public CertificateInfo getCertificateInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCertificateInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CertificateCollection getCertificateCollection() throws BOSException
    {
        try {
            return getController().getCertificateCollection(getContext());
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
    public CertificateCollection getCertificateCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCertificateCollection(getContext(), view);
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
    public CertificateCollection getCertificateCollection(String oql) throws BOSException
    {
        try {
            return getController().getCertificateCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ÊÇ·ñ¿ÉÒÔÉ¾³ý-User defined method
     *@param id id
     */
    public void isDeleteCertificate(String id) throws BOSException, EASBizException
    {
        try {
            getController().isDeleteCertificate(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}