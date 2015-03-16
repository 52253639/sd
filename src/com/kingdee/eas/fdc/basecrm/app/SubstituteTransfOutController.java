package com.kingdee.eas.fdc.basecrm.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.eas.fdc.basecrm.SubstituteTransfOutInfo;
import com.kingdee.eas.fdc.basecrm.SubstituteTransfOutCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SubstituteTransfOutController extends CoreBillBaseController
{
    public SubstituteTransfOutCollection getSubstituteTransfOutCollection(Context ctx) throws BOSException, RemoteException;
    public SubstituteTransfOutCollection getSubstituteTransfOutCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SubstituteTransfOutCollection getSubstituteTransfOutCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public SubstituteTransfOutInfo getSubstituteTransfOutInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SubstituteTransfOutInfo getSubstituteTransfOutInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SubstituteTransfOutInfo getSubstituteTransfOutInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public void setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
}