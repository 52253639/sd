package com.kingdee.eas.fdc.sellhouse;

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

public interface IBankPayment extends IFDCBill
{
    public BankPaymentInfo getBankPaymentInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BankPaymentInfo getBankPaymentInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BankPaymentInfo getBankPaymentInfo(String oql) throws BOSException, EASBizException;
    public BankPaymentCollection getBankPaymentCollection() throws BOSException;
    public BankPaymentCollection getBankPaymentCollection(EntityViewInfo view) throws BOSException;
    public BankPaymentCollection getBankPaymentCollection(String oql) throws BOSException;
    public void setAudittingStatus(BOSUuid billId) throws BOSException, EASBizException;
    public void setSubmitStatus(BOSUuid billId) throws BOSException, EASBizException;
}