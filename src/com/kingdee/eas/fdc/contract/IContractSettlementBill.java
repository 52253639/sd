package com.kingdee.eas.fdc.contract;

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

public interface IContractSettlementBill extends IFDCBill
{
    public ContractSettlementBillInfo getContractSettlementBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractSettlementBillInfo getContractSettlementBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractSettlementBillInfo getContractSettlementBillInfo(String oql) throws BOSException, EASBizException;
    public ContractSettlementBillCollection getContractSettlementBillCollection() throws BOSException;
    public ContractSettlementBillCollection getContractSettlementBillCollection(EntityViewInfo view) throws BOSException;
    public ContractSettlementBillCollection getContractSettlementBillCollection(String oql) throws BOSException;
    public void split(IObjectPK pk) throws BOSException, EASBizException;
    public boolean isAllSplit(IObjectPK pk) throws BOSException, EASBizException;
    public boolean autoDelSplit(IObjectPK pk) throws BOSException, EASBizException;
    public void costIndex(IObjectPK pk) throws BOSException, EASBizException;
}