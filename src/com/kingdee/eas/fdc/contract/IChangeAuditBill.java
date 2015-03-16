package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import java.lang.Object;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IChangeAuditBill extends IFDCBill
{
    public ChangeAuditBillInfo getChangeAuditBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ChangeAuditBillInfo getChangeAuditBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ChangeAuditBillInfo getChangeAuditBillInfo(String oql) throws BOSException, EASBizException;
    public ChangeAuditBillCollection getChangeAuditBillCollection() throws BOSException;
    public ChangeAuditBillCollection getChangeAuditBillCollection(EntityViewInfo view) throws BOSException;
    public ChangeAuditBillCollection getChangeAuditBillCollection(String oql) throws BOSException;
    public void register(Set idSet) throws BOSException, EASBizException;
    public void disPatch(Set idSet) throws BOSException, EASBizException;
    public void aheadDisPatch(Set idSet) throws BOSException, EASBizException;
    public void setAudittingStatus(BOSUuid billId) throws BOSException, EASBizException;
    public void setSubmitStatus(BOSUuid billId) throws BOSException, EASBizException;
    public void register4WF(IObjectPK pk) throws BOSException, EASBizException;
    public void disPatch4WF(IObjectPK pk) throws BOSException, EASBizException;
    public void aheadDisPatch4WF(IObjectPK pk) throws BOSException, EASBizException;
    public Object checkAmount(IObjectPK pk, Map contractMap) throws BOSException, EASBizException;
}