package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.IFDCDataBase;
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

public interface IHouseAnlysis extends IFDCDataBase
{
    public HouseAnlysisInfo getHouseAnlysisInfo(IObjectPK pk) throws BOSException, EASBizException;
    public HouseAnlysisInfo getHouseAnlysisInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public HouseAnlysisInfo getHouseAnlysisInfo(String oql) throws BOSException, EASBizException;
    public HouseAnlysisCollection getHouseAnlysisCollection() throws BOSException;
    public HouseAnlysisCollection getHouseAnlysisCollection(EntityViewInfo view) throws BOSException;
    public HouseAnlysisCollection getHouseAnlysisCollection(String oql) throws BOSException;
}