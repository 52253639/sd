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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCSplQualificationAuditBillController extends FDCSplAuditBaseBillController
{
    public FDCSplQualificationAuditBillInfo getFDCSplQualificationAuditBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCSplQualificationAuditBillInfo getFDCSplQualificationAuditBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCSplQualificationAuditBillInfo getFDCSplQualificationAuditBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCSplQualificationAuditBillCollection getFDCSplQualificationAuditBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public FDCSplQualificationAuditBillCollection getFDCSplQualificationAuditBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCSplQualificationAuditBillCollection getFDCSplQualificationAuditBillCollection(Context ctx) throws BOSException, RemoteException;
}