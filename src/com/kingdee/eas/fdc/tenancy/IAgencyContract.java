package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IAgencyContract extends ITenBillBase
{
    public AgencyContractInfo getAgencyContractInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AgencyContractInfo getAgencyContractInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AgencyContractInfo getAgencyContractInfo(String oql) throws BOSException, EASBizException;
    public AgencyContractCollection getAgencyContractCollection() throws BOSException;
    public AgencyContractCollection getAgencyContractCollection(EntityViewInfo view) throws BOSException;
    public AgencyContractCollection getAgencyContractCollection(String oql) throws BOSException;
}