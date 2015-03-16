package com.kingdee.eas.fdc.finance;

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

public interface IDeductBill extends IFDCBill
{
    public DeductBillInfo getDeductBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DeductBillInfo getDeductBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DeductBillInfo getDeductBillInfo(String oql) throws BOSException, EASBizException;
    public DeductBillCollection getDeductBillCollection() throws BOSException;
    public DeductBillCollection getDeductBillCollection(EntityViewInfo view) throws BOSException;
    public DeductBillCollection getDeductBillCollection(String oql) throws BOSException;
    public void createPartADeductBill(String paymentId) throws BOSException, EASBizException;
}