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
import com.kingdee.eas.framework.IBillEntryBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IDeductBillEntry extends IBillEntryBase
{
    public DeductBillEntryInfo getDeductBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DeductBillEntryInfo getDeductBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DeductBillEntryInfo getDeductBillEntryInfo(String oql) throws BOSException, EASBizException;
    public DeductBillEntryCollection getDeductBillEntryCollection() throws BOSException;
    public DeductBillEntryCollection getDeductBillEntryCollection(EntityViewInfo view) throws BOSException;
    public DeductBillEntryCollection getDeductBillEntryCollection(String oql) throws BOSException;
}