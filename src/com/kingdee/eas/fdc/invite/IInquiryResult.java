package com.kingdee.eas.fdc.invite;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public interface IInquiryResult extends IFDCBill
{
    public InquiryResultCollection getInquiryResultCollection() throws BOSException;
    public InquiryResultCollection getInquiryResultCollection(EntityViewInfo view) throws BOSException;
    public InquiryResultCollection getInquiryResultCollection(String oql) throws BOSException;
    public InquiryResultInfo getInquiryResultInfo(IObjectPK pk) throws BOSException, EASBizException;
    public InquiryResultInfo getInquiryResultInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public InquiryResultInfo getInquiryResultInfo(String oql) throws BOSException, EASBizException;
    public void audit(BOSUuid billId) throws BOSException, EASBizException;
    public void audit(List idList) throws BOSException, EASBizException;
    public boolean checkCanSubmit(String id) throws BOSException, EASBizException;
    public Map fetchFilterInitData(Map paramMap) throws BOSException, EASBizException;
    public Map fetchInitData(Map paramMap) throws BOSException, EASBizException;
    public void unAudit(BOSUuid billId) throws BOSException, EASBizException;
    public void unAudit(List idList) throws BOSException, EASBizException;
}