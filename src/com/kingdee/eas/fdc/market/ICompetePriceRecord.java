package com.kingdee.eas.fdc.market;

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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface ICompetePriceRecord extends ICoreBillEntryBase
{
    public CompetePriceRecordInfo getCompetePriceRecordInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CompetePriceRecordInfo getCompetePriceRecordInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CompetePriceRecordInfo getCompetePriceRecordInfo(String oql) throws BOSException, EASBizException;
    public CompetePriceRecordCollection getCompetePriceRecordCollection() throws BOSException;
    public CompetePriceRecordCollection getCompetePriceRecordCollection(EntityViewInfo view) throws BOSException;
    public CompetePriceRecordCollection getCompetePriceRecordCollection(String oql) throws BOSException;
}