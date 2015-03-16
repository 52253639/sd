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

public interface IInquiryFile extends IFDCBill
{
    public InquiryFileCollection getInquiryFileCollection() throws BOSException;
    public InquiryFileCollection getInquiryFileCollection(EntityViewInfo view) throws BOSException;
    public InquiryFileCollection getInquiryFileCollection(String oql) throws BOSException;
    public InquiryFileInfo getInquiryFileInfo(IObjectPK pk) throws BOSException, EASBizException;
    public InquiryFileInfo getInquiryFileInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public InquiryFileInfo getInquiryFileInfo(String oql) throws BOSException, EASBizException;
    public void audit(BOSUuid billId) throws BOSException, EASBizException;
    public void audit(List idList) throws BOSException, EASBizException;
    public void unAudit(BOSUuid billId) throws BOSException, EASBizException;
    public void unAudit(List idList) throws BOSException, EASBizException;
}