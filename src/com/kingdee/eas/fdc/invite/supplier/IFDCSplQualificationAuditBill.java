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

public interface IFDCSplQualificationAuditBill extends IFDCSplAuditBaseBill
{
    public FDCSplQualificationAuditBillInfo getFDCSplQualificationAuditBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCSplQualificationAuditBillInfo getFDCSplQualificationAuditBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCSplQualificationAuditBillInfo getFDCSplQualificationAuditBillInfo(String oql) throws BOSException, EASBizException;
    public FDCSplQualificationAuditBillCollection getFDCSplQualificationAuditBillCollection(String oql) throws BOSException;
    public FDCSplQualificationAuditBillCollection getFDCSplQualificationAuditBillCollection(EntityViewInfo view) throws BOSException;
    public FDCSplQualificationAuditBillCollection getFDCSplQualificationAuditBillCollection() throws BOSException;
}