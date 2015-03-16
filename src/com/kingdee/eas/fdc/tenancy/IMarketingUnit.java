package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.base.permission.UserInfo;
import java.util.ArrayList;

public interface IMarketingUnit extends ITreeBase
{
    public MarketingUnitInfo getMarketingUnitInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketingUnitInfo getMarketingUnitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketingUnitInfo getMarketingUnitInfo(String oql) throws BOSException, EASBizException;
    public MarketingUnitCollection getMarketingUnitCollection() throws BOSException;
    public MarketingUnitCollection getMarketingUnitCollection(EntityViewInfo view) throws BOSException;
    public MarketingUnitCollection getMarketingUnitCollection(String oql) throws BOSException;
    public Set getPermitSaleManIdSet(UserInfo currSaleMan) throws BOSException, EASBizException;
    public String getPermitSaleManIdSql(UserInfo currSaleMan) throws BOSException, EASBizException;
    public Set getPermitSellProjectIdSet(UserInfo saleMan) throws BOSException, EASBizException;
    public String getPermitSellProjectIdSql(UserInfo currSaleMan) throws BOSException, EASBizException;
    public String getPermitFdcCustomerIdSql(UserInfo saleMan, boolean isInCludeDisEnable) throws BOSException, EASBizException;
    public String getPermitFdcCustomerIdSql(UserInfo saleMan, boolean isInCludeDisEnable, boolean isIncludeShared) throws BOSException, EASBizException;
    public Set getCustomerIdSetByNameAndPhone(UserInfo saleMan, String customerName, String customerPhone) throws BOSException, EASBizException;
    public MarketingUnitCollection getMarketUnitCollByDutySaleMan(UserInfo saleMan) throws BOSException, EASBizException;
    public MarketingUnitCollection getMarketUnitCollByMemberSaleMan(UserInfo saleMan) throws BOSException, EASBizException;
    public MarketingUnitCollection getMarketUnitCollByOUIdSet(Set ouIdSet) throws BOSException, EASBizException;
    public ArrayList getMuSellProTreeNodes(String detailNodeType, boolean isMuPerm) throws BOSException, EASBizException;
}