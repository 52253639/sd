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
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IContractWithoutText extends IFDCBill
{
    public ContractWithoutTextCollection getContractWithoutTextCollection() throws BOSException;
    public ContractWithoutTextCollection getContractWithoutTextCollection(EntityViewInfo view) throws BOSException;
    public ContractWithoutTextCollection getContractWithoutTextCollection(String oql) throws BOSException;
    public ContractWithoutTextInfo getContractWithoutTextInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractWithoutTextInfo getContractWithoutTextInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractWithoutTextInfo getContractWithoutTextInfo(String oql) throws BOSException, EASBizException;
    public String getContractTypeNumber(IObjectPK id) throws BOSException, EASBizException;
    public String getUseDepartment(String id) throws BOSException, EASBizException;
    public String getPaymentType(BOSUuid id) throws BOSException, EASBizException;
    public void synUpdateProgramming(String billId, ProgrammingContractInfo programming) throws BOSException, EASBizException;
    public void synReUpdateProgramming(String billId, IObjectValue programming) throws BOSException, EASBizException;
    public IObjectValue getNoPValue(IObjectPK pk, SelectorItemCollection sel) throws BOSException;
}