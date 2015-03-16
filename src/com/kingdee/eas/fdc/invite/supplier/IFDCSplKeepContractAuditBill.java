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

public interface IFDCSplKeepContractAuditBill extends IFDCSplAuditBaseBill
{
    public FDCSplKeepContractAuditBillInfo getFDCSplKeepContractAuditBillInfo(String oql) throws BOSException, EASBizException;
    public FDCSplKeepContractAuditBillInfo getFDCSplKeepContractAuditBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCSplKeepContractAuditBillInfo getFDCSplKeepContractAuditBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCSplKeepContractAuditBillCollection getFDCSplKeepContractAuditBillCollection(String oql) throws BOSException;
    public FDCSplKeepContractAuditBillCollection getFDCSplKeepContractAuditBillCollection(EntityViewInfo view) throws BOSException;
    public FDCSplKeepContractAuditBillCollection getFDCSplKeepContractAuditBillCollection() throws BOSException;
}