package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.CollectionInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.ICoreBillBase;

public interface ISubstituteAdjust extends ICoreBillBase
{
    public SubstituteAdjustCollection getSubstituteAdjustCollection() throws BOSException;
    public SubstituteAdjustCollection getSubstituteAdjustCollection(EntityViewInfo view) throws BOSException;
    public SubstituteAdjustCollection getSubstituteAdjustCollection(String oql) throws BOSException;
    public SubstituteAdjustInfo getSubstituteAdjustInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SubstituteAdjustInfo getSubstituteAdjustInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SubstituteAdjustInfo getSubstituteAdjustInfo(String oql) throws BOSException, EASBizException;
    public void transfTo(SubstituteAdjustInfo adjustInfo) throws BOSException, EASBizException;
    public SubstituteAdjustEntryCollection getCalculateResult(BOSUuid moneyDefineId, BOSUuid sellPorjectId, BOSUuid buildingId, CollectionInfo collInfo) throws BOSException, EASBizException;
}