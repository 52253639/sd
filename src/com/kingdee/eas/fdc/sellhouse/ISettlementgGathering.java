package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ISettlementgGathering extends IBillBase
{
    public SettlementgGatheringInfo getSettlementgGatheringInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SettlementgGatheringInfo getSettlementgGatheringInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SettlementgGatheringInfo getSettlementgGatheringInfo(String oql) throws BOSException, EASBizException;
    public SettlementgGatheringCollection getSettlementgGatheringCollection() throws BOSException;
    public SettlementgGatheringCollection getSettlementgGatheringCollection(EntityViewInfo view) throws BOSException;
    public SettlementgGatheringCollection getSettlementgGatheringCollection(String oql) throws BOSException;
}