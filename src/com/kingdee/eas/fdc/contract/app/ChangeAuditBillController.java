package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.Object;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditBillCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ChangeAuditBillController extends FDCBillController
{
    public ChangeAuditBillInfo getChangeAuditBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ChangeAuditBillInfo getChangeAuditBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ChangeAuditBillInfo getChangeAuditBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ChangeAuditBillCollection getChangeAuditBillCollection(Context ctx) throws BOSException, RemoteException;
    public ChangeAuditBillCollection getChangeAuditBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ChangeAuditBillCollection getChangeAuditBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void register(Context ctx, Set idSet) throws BOSException, EASBizException, RemoteException;
    public void disPatch(Context ctx, Set idSet) throws BOSException, EASBizException, RemoteException;
    public void aheadDisPatch(Context ctx, Set idSet) throws BOSException, EASBizException, RemoteException;
    public void setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void register4WF(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void disPatch4WF(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void aheadDisPatch4WF(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public Object checkAmount(Context ctx, IObjectPK pk, Map contractMap) throws BOSException, EASBizException, RemoteException;
}