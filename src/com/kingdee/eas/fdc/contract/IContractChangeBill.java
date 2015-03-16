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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IContractChangeBill extends IFDCBill
{
    public ContractChangeBillCollection getContractChangeBillCollection() throws BOSException;
    public ContractChangeBillCollection getContractChangeBillCollection(EntityViewInfo view) throws BOSException;
    public ContractChangeBillCollection getContractChangeBillCollection(String oql) throws BOSException;
    public ContractChangeBillInfo getContractChangeBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractChangeBillInfo getContractChangeBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractChangeBillInfo getContractChangeBillInfo(String oql) throws BOSException, EASBizException;
    public void disPatch(IObjectPK[] idSet) throws BOSException, EASBizException;
    public boolean visa(IObjectPK[] idSet, IObjectCollection cols) throws BOSException, EASBizException;
    public void settle(IObjectPK pk, IObjectValue changeBill) throws BOSException, EASBizException;
    public void unDispatch(IObjectPK pk) throws BOSException, EASBizException;
    public void unVisa(IObjectPK pk) throws BOSException, EASBizException;
    public void submitForWF(ContractChangeBillInfo model) throws BOSException, EASBizException;
    public void setSettAuttingForWF(BOSUuid pk) throws BOSException, EASBizException;
    public void setSettAuditedForWF(BOSUuid pk) throws BOSException, EASBizException;
    public void confirmExecute(BOSUuid billId) throws BOSException, EASBizException;
    public void changeSettle(BOSUuid billId) throws BOSException, EASBizException;
    public void costChangeSplit(BOSUuid billId) throws BOSException, EASBizException;
    public void nonCostChangeSplit(BOSUuid billId) throws BOSException, EASBizException;
}