package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.framework.app.TreeBaseController;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.base.permission.UserInfo;
import java.util.ArrayList;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MarketingUnitController extends TreeBaseController
{
    public MarketingUnitInfo getMarketingUnitInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public MarketingUnitInfo getMarketingUnitInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public MarketingUnitInfo getMarketingUnitInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public MarketingUnitCollection getMarketingUnitCollection(Context ctx) throws BOSException, RemoteException;
    public MarketingUnitCollection getMarketingUnitCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public MarketingUnitCollection getMarketingUnitCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public Set getPermitSaleManIdSet(Context ctx, UserInfo currSaleMan, IObjectValue sellProInfo) throws BOSException, EASBizException, RemoteException;
    public String getPermitSaleManIdSql(Context ctx, UserInfo currSaleMan, IObjectValue sellProInfo) throws BOSException, EASBizException, RemoteException;
    public Set getPermitSellProjectIdSet(Context ctx, UserInfo saleMan) throws BOSException, EASBizException, RemoteException;
    public String getPermitSellProjectIdSql(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException, RemoteException;
    public Set getSellProjectIdSetAllChild(Context ctx, UserInfo saleMan) throws BOSException, EASBizException, RemoteException;
    public Set getPermitFdcCustomerIdSet(Context ctx, UserInfo saleMan, boolean isInCludeDisEnable) throws BOSException, EASBizException, RemoteException;
    public Set getPermitFdcCustomerIdSet(Context ctx, UserInfo saleMan, boolean isInCludeDisEnable, boolean isIncludeShared) throws BOSException, EASBizException, RemoteException;
    public Set getCustomerIdSetByNameAndPhone(Context ctx, UserInfo saleMan, String customerName, String customerPhone) throws BOSException, EASBizException, RemoteException;
    public MarketingUnitCollection getMarketUnitCollByDutySaleMan(Context ctx, UserInfo saleMan) throws BOSException, EASBizException, RemoteException;
    public MarketingUnitCollection getMarketUnitCollByMemberSaleMan(Context ctx, UserInfo saleMan) throws BOSException, EASBizException, RemoteException;
    public MarketingUnitCollection getMarketUnitCollByOUIdSet(Context ctx, Set ouIdSet) throws BOSException, EASBizException, RemoteException;
    public ArrayList getMuSellProTreeNodes(Context ctx, String detailNodeType, boolean isMuPerm) throws BOSException, EASBizException, RemoteException;
    public Set checkRefByNext(Context ctx, Set marketingSellProjectSet, MarketingUnitInfo marketingUnitInfo) throws BOSException, RemoteException;
    public boolean checkMemeberOfSameSellPorject(Context ctx, MarketingUnitInfo marketingUnitInfo) throws BOSException, EASBizException, RemoteException;
    public MarketingUnitMemberCollection getMarketUnitCollMember(Context ctx, UserInfo saleMans) throws BOSException, EASBizException, RemoteException;
    public Set getPermitAllSellProjectIdSet(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException, RemoteException;
    public Set getPermitSaleManIdSet(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException, RemoteException;
    public String getPermitSaleManIdSql(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException, RemoteException;
}