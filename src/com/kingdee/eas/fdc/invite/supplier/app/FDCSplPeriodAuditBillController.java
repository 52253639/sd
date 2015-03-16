package com.kingdee.eas.fdc.invite.supplier.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCSplPeriodAuditBillController extends FDCSplAuditBaseBillController
{
    public FDCSplPeriodAuditBillInfo getFDCSplPeriodAuditBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCSplPeriodAuditBillInfo getFDCSplPeriodAuditBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCSplPeriodAuditBillInfo getFDCSplPeriodAuditBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCSplPeriodAuditBillCollection getFDCSplPeriodAuditBillCollection(Context ctx) throws BOSException, RemoteException;
    public FDCSplPeriodAuditBillCollection getFDCSplPeriodAuditBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCSplPeriodAuditBillCollection getFDCSplPeriodAuditBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
}