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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IInvestorHouseTrackRecord extends IDataBase
{
    public InvestorHouseTrackRecordInfo getInvestorHouseTrackRecordInfo(IObjectPK pk) throws BOSException, EASBizException;
    public InvestorHouseTrackRecordInfo getInvestorHouseTrackRecordInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public InvestorHouseTrackRecordInfo getInvestorHouseTrackRecordInfo(String oql) throws BOSException, EASBizException;
    public InvestorHouseTrackRecordCollection getInvestorHouseTrackRecordCollection() throws BOSException;
    public InvestorHouseTrackRecordCollection getInvestorHouseTrackRecordCollection(EntityViewInfo view) throws BOSException;
    public InvestorHouseTrackRecordCollection getInvestorHouseTrackRecordCollection(String oql) throws BOSException;
}