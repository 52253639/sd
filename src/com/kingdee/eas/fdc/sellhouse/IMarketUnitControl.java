package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.framework.IDataBase;

public interface IMarketUnitControl extends IDataBase
{
    public MarketUnitControlInfo getMarketUnitControlInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketUnitControlInfo getMarketUnitControlInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketUnitControlInfo getMarketUnitControlInfo(String oql) throws BOSException, EASBizException;
    public MarketUnitControlCollection getMarketUnitControlCollection() throws BOSException;
    public MarketUnitControlCollection getMarketUnitControlCollection(EntityViewInfo view) throws BOSException;
    public MarketUnitControlCollection getMarketUnitControlCollection(String oql) throws BOSException;
    public void updateMuPermission(FullOrgUnitInfo orgUnitInfo, UserInfo ctrolUserInfo, boolean isAdd) throws BOSException, EASBizException;
}