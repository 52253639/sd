package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IFDCSplPeriodAuditBill extends IFDCSplAuditBaseBill
{
    public FDCSplPeriodAuditBillInfo getFDCSplPeriodAuditBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCSplPeriodAuditBillInfo getFDCSplPeriodAuditBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCSplPeriodAuditBillInfo getFDCSplPeriodAuditBillInfo(String oql) throws BOSException, EASBizException;
    public FDCSplPeriodAuditBillCollection getFDCSplPeriodAuditBillCollection() throws BOSException;
    public FDCSplPeriodAuditBillCollection getFDCSplPeriodAuditBillCollection(EntityViewInfo view) throws BOSException;
    public FDCSplPeriodAuditBillCollection getFDCSplPeriodAuditBillCollection(String oql) throws BOSException;
}