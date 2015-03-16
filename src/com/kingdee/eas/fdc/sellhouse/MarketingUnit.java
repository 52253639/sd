package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.base.permission.UserInfo;
import java.util.ArrayList;
import com.kingdee.eas.framework.TreeBase;

public class MarketingUnit extends TreeBase implements IMarketingUnit
{
    public MarketingUnit()
    {
        super();
        registerInterface(IMarketingUnit.class, this);
    }
    public MarketingUnit(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketingUnit.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B6B669CF");
    }
    private MarketingUnitController getController() throws BOSException
    {
        return (MarketingUnitController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public MarketingUnitInfo getMarketingUnitInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketingUnitInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public MarketingUnitInfo getMarketingUnitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketingUnitInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public MarketingUnitInfo getMarketingUnitInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketingUnitInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketingUnitCollection getMarketingUnitCollection() throws BOSException
    {
        try {
            return getController().getMarketingUnitCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view view
     *@return
     */
    public MarketingUnitCollection getMarketingUnitCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketingUnitCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql oql
     *@return
     */
    public MarketingUnitCollection getMarketingUnitCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketingUnitCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPermitSaleManIdSet-User defined method
     *@param currSaleMan 营销人员
     *@param sellProInfo 项目
     *@return
     */
    public Set getPermitSaleManIdSet(UserInfo currSaleMan, IObjectValue sellProInfo) throws BOSException, EASBizException
    {
        try {
            return getController().getPermitSaleManIdSet(getContext(), currSaleMan, sellProInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPermitSaleManIdSql-User defined method
     *@param currSaleMan 营销人员
     *@param sellProInfo 项目
     *@return
     */
    public String getPermitSaleManIdSql(UserInfo currSaleMan, IObjectValue sellProInfo) throws BOSException, EASBizException
    {
        try {
            return getController().getPermitSaleManIdSql(getContext(), currSaleMan, sellProInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *指定营销人员所能看到的一级项目集合-User defined method
     *@param saleMan 指定的营销人员
     *@return
     */
    public Set getPermitSellProjectIdSet(UserInfo saleMan) throws BOSException, EASBizException
    {
        try {
            return getController().getPermitSellProjectIdSet(getContext(), saleMan);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *所允许看到的一级项目IdSql-User defined method
     *@param currSaleMan 营销顾问
     *@return
     */
    public String getPermitSellProjectIdSql(UserInfo currSaleMan) throws BOSException, EASBizException
    {
        try {
            return getController().getPermitSellProjectIdSql(getContext(), currSaleMan);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取得一级项目下的所有子项目ID-User defined method
     *@param saleMan 销售人员
     *@return
     */
    public Set getSellProjectIdSetAllChild(UserInfo saleMan) throws BOSException, EASBizException
    {
        try {
            return getController().getSellProjectIdSetAllChild(getContext(), saleMan);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得所有允许查看的客户Id的查询SQL(区分是否包含共享)-User defined method
     *@param saleMan 营销人员
     *@param isInCludeDisEnable 是否包含无效的客户
     *@return
     */
    public Set getPermitFdcCustomerIdSet(UserInfo saleMan, boolean isInCludeDisEnable) throws BOSException, EASBizException
    {
        try {
            return getController().getPermitFdcCustomerIdSet(getContext(), saleMan, isInCludeDisEnable);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得所有允许查看的客户Id的查询SQL(区分是否包含共享)-User defined method
     *@param saleMan 营销人员
     *@param isInCludeDisEnable 是否包含无效的客户
     *@param isIncludeShared 是否包含共享的客户
     *@return
     */
    public Set getPermitFdcCustomerIdSet(UserInfo saleMan, boolean isInCludeDisEnable, boolean isIncludeShared) throws BOSException, EASBizException
    {
        try {
            return getController().getPermitFdcCustomerIdSet(getContext(), saleMan, isInCludeDisEnable, isIncludeShared);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *客户范围是自己有权限查看的，而且必须和名称匹配 (名称不能为空，电话可以为空)-User defined method
     *@param saleMan 营销顾问
     *@param customerName 指定的客户名称,不能为空
     *@param customerPhone 指定的客户电话号码，可以为空(以like方式比较，如果为空则代表所有的)
     *@return
     */
    public Set getCustomerIdSetByNameAndPhone(UserInfo saleMan, String customerName, String customerPhone) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerIdSetByNameAndPhone(getContext(), saleMan, customerName, customerPhone);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *作为负责任所在的营销单元集合-User defined method
     *@param saleMan 指定的营销人员
     *@return
     */
    public MarketingUnitCollection getMarketUnitCollByDutySaleMan(UserInfo saleMan) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketUnitCollByDutySaleMan(getContext(), saleMan);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *作为成员任所在的营销单元集合-User defined method
     *@param saleMan 指定的当前营销人员
     *@return
     */
    public MarketingUnitCollection getMarketUnitCollByMemberSaleMan(UserInfo saleMan) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketUnitCollByMemberSaleMan(getContext(), saleMan);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *指定的OUid集合下的所有营销单元-User defined method
     *@param ouIdSet OU的id集合
     *@return
     */
    public MarketingUnitCollection getMarketUnitCollByOUIdSet(Set ouIdSet) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketUnitCollByOUIdSet(getContext(), ouIdSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *当前人的营销全下下看到的项目节点 包括共享给其它组织的 (多个相同的项目分别隶属于不同的组织下)-User defined method
     *@param detailNodeType SellProject 、SellOrder、Subarea、Building、BuildingUnit可为空，为空代表到项目
     *@param isMuPerm 是否跟营销权限有关
     *@return
     */
    public ArrayList getMuSellProTreeNodes(String detailNodeType, boolean isMuPerm) throws BOSException, EASBizException
    {
        try {
            return getController().getMuSellProTreeNodes(getContext(), detailNodeType, isMuPerm);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *检查项目是否被下级引用-User defined method
     *@param marketingSellProjectSet marketingSellProjectSet
     *@param marketingUnitInfo marketingUnitInfo
     *@return
     */
    public Set checkRefByNext(Set marketingSellProjectSet, MarketingUnitInfo marketingUnitInfo) throws BOSException
    {
        try {
            return getController().checkRefByNext(getContext(), marketingSellProjectSet, marketingUnitInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *相同项目的成员-User defined method
     *@param marketingUnitInfo marketingUnitInfo
     *@return
     */
    public boolean checkMemeberOfSameSellPorject(MarketingUnitInfo marketingUnitInfo) throws BOSException, EASBizException
    {
        try {
            return getController().checkMemeberOfSameSellPorject(getContext(), marketingUnitInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得当前人所在组织下的所有成员(包括下级)-User defined method
     *@param saleMans 指定的营销人员
     *@return
     */
    public MarketingUnitMemberCollection getMarketUnitCollMember(UserInfo saleMans) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketUnitCollMember(getContext(), saleMans);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPermitAllSellProjectIdSet-User defined method
     *@param currSaleMan 营销顾问
     *@return
     */
    public Set getPermitAllSellProjectIdSet(UserInfo currSaleMan) throws BOSException, EASBizException
    {
        try {
            return getController().getPermitAllSellProjectIdSet(getContext(), currSaleMan);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPermitSaleManIdSet-User defined method
     *@param currSaleMan 营销人员
     *@return
     */
    public Set getPermitSaleManIdSet(UserInfo currSaleMan) throws BOSException, EASBizException
    {
        try {
            return getController().getPermitSaleManIdSet(getContext(), currSaleMan);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPermitSaleManIdSql-User defined method
     *@param currSaleMan 营销人员
     *@return
     */
    public String getPermitSaleManIdSql(UserInfo currSaleMan) throws BOSException, EASBizException
    {
        try {
            return getController().getPermitSaleManIdSql(getContext(), currSaleMan);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}