package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;

public interface ITenancyBill extends ITenBillBase
{
    public TenancyBillInfo getTenancyBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TenancyBillInfo getTenancyBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TenancyBillInfo getTenancyBillInfo(String oql) throws BOSException, EASBizException;
    public TenancyBillCollection getTenancyBillCollection() throws BOSException;
    public TenancyBillCollection getTenancyBillCollection(EntityViewInfo view) throws BOSException;
    public TenancyBillCollection getTenancyBillCollection(String oql) throws BOSException;
    public void handleTenancyRoom(IObjectCollection tenAttachEntryColl, TenancyRoomEntryCollection tenancyRoomEntryColl, TenancyBillInfo tenancyBillInfo, HandleRoomEntrysCollection handleRoomEntryColl) throws BOSException;
    public void carryForward(ReceivingBillCollection receivingBills) throws BOSException, EASBizException;
    public void blankOut(IObjectPK pk) throws BOSException, EASBizException;
    public void repairStartDate(TenancyBillInfo tenancyBillInfo, Date repairStartDate, FirstLeaseTypeEnum firstLease, Date firstLeaseDate) throws BOSException, EASBizException;
    public void antiAudit(BOSUuid billID) throws BOSException, EASBizException;
}