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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IFDCSplAuditBaseBill extends IFDCBill
{
    public FDCSplAuditBaseBillInfo getFDCSplAuditBaseBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCSplAuditBaseBillInfo getFDCSplAuditBaseBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCSplAuditBaseBillInfo getFDCSplAuditBaseBillInfo(String oql) throws BOSException, EASBizException;
    public FDCSplAuditBaseBillCollection getFDCSplAuditBaseBillCollection() throws BOSException;
    public FDCSplAuditBaseBillCollection getFDCSplAuditBaseBillCollection(EntityViewInfo view) throws BOSException;
    public FDCSplAuditBaseBillCollection getFDCSplAuditBaseBillCollection(String oql) throws BOSException;
}