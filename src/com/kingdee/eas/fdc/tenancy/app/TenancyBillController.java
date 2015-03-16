package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import java.util.Date;
import com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.tenancy.HandleRoomEntrysCollection;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TenancyBillController extends TenBillBaseController
{
    public TenancyBillInfo getTenancyBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TenancyBillInfo getTenancyBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TenancyBillInfo getTenancyBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TenancyBillCollection getTenancyBillCollection(Context ctx) throws BOSException, RemoteException;
    public TenancyBillCollection getTenancyBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TenancyBillCollection getTenancyBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void handleTenancyRoom(Context ctx, IObjectCollection tenAttachEntryColl, TenancyRoomEntryCollection tenancyRoomEntryColl, TenancyBillInfo tenancyBillInfo, HandleRoomEntrysCollection handleRoomEntryColl) throws BOSException, RemoteException;
    public void carryForward(Context ctx, ReceivingBillCollection receivingBills) throws BOSException, EASBizException, RemoteException;
    public void blankOut(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void repairStartDate(Context ctx, TenancyBillInfo tenancyBillInfo, Date repairStartDate, FirstLeaseTypeEnum firstLease, Date firstLeaseDate) throws BOSException, EASBizException, RemoteException;
    public void antiAudit(Context ctx, BOSUuid billID) throws BOSException, EASBizException, RemoteException;
}