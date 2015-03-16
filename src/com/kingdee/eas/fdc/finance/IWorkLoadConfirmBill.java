package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.math.BigDecimal;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface IWorkLoadConfirmBill extends IFDCBill
{
    public WorkLoadConfirmBillInfo getWorkLoadConfirmBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public WorkLoadConfirmBillInfo getWorkLoadConfirmBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public WorkLoadConfirmBillInfo getWorkLoadConfirmBillInfo(String oql) throws BOSException, EASBizException;
    public WorkLoadConfirmBillCollection getWorkLoadConfirmBillCollection() throws BOSException;
    public WorkLoadConfirmBillCollection getWorkLoadConfirmBillCollection(EntityViewInfo view) throws BOSException;
    public WorkLoadConfirmBillCollection getWorkLoadConfirmBillCollection(String oql) throws BOSException;
    public BigDecimal getWorkLoad(String contractId) throws BOSException, EASBizException;
    public Map getWorkLoad(Set contractIds) throws BOSException, EASBizException;
    public BigDecimal getWorkLoadWithoutId(String contractId, String workLoadId) throws BOSException, EASBizException;
    public Map getConPrjFillBill(Set prjFillBillIds) throws BOSException, EASBizException;
    public Map getRefWorkAmount(Map paramMap) throws BOSException, EASBizException;
    public void save(IObjectValue model, List refWorkAmountList, List willRemoveList) throws BOSException, EASBizException;
}