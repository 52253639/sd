package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.basedata.assistant.PeriodInfo;
import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.math.BigDecimal;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;

public class FDCCostRptFacade extends AbstractBizCtrl implements IFDCCostRptFacade
{
    public FDCCostRptFacade()
    {
        super();
        registerInterface(IFDCCostRptFacade.class, this);
    }
    public FDCCostRptFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCCostRptFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B118BCD5");
    }
    private FDCCostRptFacadeController getController() throws BOSException
    {
        return (FDCCostRptFacadeController)getBizController();
    }
    /**
     *取得项目的目标成本-User defined method
     *@param orgOrPrjId 项目或组织名
     *@param period 期间
     *@return
     */
    public AimCostMap getAimCost(String orgOrPrjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCost(getContext(), orgOrPrjId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到项目上所有产品的目标成本-User defined method
     *@param prjId 项目ID
     *@param indexProjectStage 指标的版本阶段
     *@param period period
     *@return
     */
    public ProductAimCostMap getProductAimCost(String prjId, ProjectStageEnum indexProjectStage, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            return getController().getProductAimCost(getContext(), prjId, indexProjectStage, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *全项目动态成本-User defined method
     *@param prjId 项目ID
     *@param period 期间
     *@return
     */
    public FullDynamicCostMap getFullDynamicCost(String prjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            return getController().getFullDynamicCost(getContext(), prjId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *产品动态成本-User defined method
     *@param prjId 项目ID
     *@param period 期间
     *@return
     */
    public ProductDynamicCostMap getProductDynamicCost(String prjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            return getController().getProductDynamicCost(getContext(), prjId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *各产品类型动态目标成本提交-User defined method
     *@param costEntryMap costEntryMap
     *@return
     */
    public Map submitAimProductCost(Map costEntryMap) throws BOSException, EASBizException
    {
        try {
            return getController().submitAimProductCost(getContext(), costEntryMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *各产品类型目标成本-User defined method
     *@param costEntryMap costEntryMap
     *@return
     */
    public Map submitAIMAimProductCost(Map costEntryMap) throws BOSException, EASBizException
    {
        try {
            return getController().submitAIMAimProductCost(getContext(), costEntryMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *各产品动态成本保存-User defined method
     *@param dynCostMap dynCostMap
     *@return
     */
    public Map submitDynProductCost(Map dynCostMap) throws BOSException, EASBizException
    {
        try {
            return getController().submitDynProductCost(getContext(), dynCostMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *全项目动态成本-User defined method
     *@param projOrgId 组织编码
     *@param period 期间
     *@return
     */
    public FullDynamicCostMap getFullPrjDynamicCost(String projOrgId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            return getController().getFullPrjDynamicCost(getContext(), projOrgId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *全项目保存-User defined method
     *@param prjId 项目ID
     *@param savedType 保存类型
     */
    public void saveSnapShot(String prjId, CostMonthlySaveTypeEnum savedType) throws BOSException, EASBizException
    {
        try {
            getController().saveSnapShot(getContext(), prjId, savedType);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *0点月结保存-User defined method
     */
    public void autoSaveSnapShot() throws BOSException
    {
        try {
            getController().autoSaveSnapShot(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *待发生处数据准备-User defined method
     *@param prjId 项目名
     *@param period 期间
     *@param isLeaf 是否叶结点
     *@return
     */
    public DynamicCostMap getDynamicCost(String prjId, PeriodInfo period, boolean isLeaf) throws BOSException, EASBizException
    {
        try {
            return getController().getDynamicCost(getContext(), prjId, period, isLeaf);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *成本反月结-User defined method
     *@param prjId 项目ID
     *@param period 期间
     */
    public void reverseCostMonthSettled(String prjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            getController().reverseCostMonthSettled(getContext(), prjId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *财务反月结-User defined method
     *@param prjId 项目Id
     *@param period 期间
     */
    public void reverseFinanceMonthSettled(String prjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            getController().reverseFinanceMonthSettled(getContext(), prjId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除快照数据-User defined method
     *@param prjId 项目Id
     *@param period 期间
     */
    public void deleteDynSnapShot(String prjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            getController().deleteDynSnapShot(getContext(), prjId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取得项目的目标成本-User defined method
     *@param prjId 项目Id
     *@return
     */
    public BigDecimal getAimCost(String prjId) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCost(getContext(), prjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到动态成本-User defined method
     *@param prjId 项目Id
     *@return
     */
    public BigDecimal getDynCost(String prjId) throws BOSException, EASBizException
    {
        try {
            return getController().getDynCost(getContext(), prjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到已发生成本-User defined method
     *@param prjId 项目Id
     *@return
     */
    public BigDecimal getHappendCost(String prjId) throws BOSException, EASBizException
    {
        try {
            return getController().getHappendCost(getContext(), prjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到待发生调整成本-User defined method
     *@param prjId 项目ID
     *@return
     */
    public BigDecimal getAdjustCost(String prjId) throws BOSException, EASBizException
    {
        try {
            return getController().getAdjustCost(getContext(), prjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到所有明细科目的目标成本-User defined method
     *@param prjId 工程项目
     *@return
     */
    public Map getCostAccountAimCost(String prjId) throws BOSException, EASBizException
    {
        try {
            return getController().getCostAccountAimCost(getContext(), prjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *各明细科目的动态成本-User defined method
     *@param prjId 项目ID
     *@return
     */
    public Map getCostAccountDynCost(String prjId) throws BOSException, EASBizException
    {
        try {
            return getController().getCostAccountDynCost(getContext(), prjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量取成本科目的方法-User defined method
     *@param param 参数
     *@return
     */
    public Map getCostMap(Map param) throws BOSException
    {
        try {
            return getController().getCostMap(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新快照已实现分摊数据-User defined method
     *@param param 工程项目id，分摊方案id等参数
     */
    public void updatePeriodCostPayedAmt(Map param) throws BOSException, EASBizException
    {
        try {
            getController().updatePeriodCostPayedAmt(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取付款情況表的结果集-User defined method
     *@param param 参数
     *@return
     */
    public Map getPaymentCostStatistics(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentCostStatistics(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取非明细工程项目付款情況表的结果集-User defined method
     *@param param 参数
     *@return
     */
    public Map getNonLeafPaymentCostStatistics(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getNonLeafPaymentCostStatistics(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取当前组织所有工程项目成本数据-User defined method
     *@param param 参数
     *@return
     */
    public Map getProjectAnalysisCost(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectAnalysisCost(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取成本月结或财务成本月结数据-User defined method
     *@param isCost true成本月结,false财务成本月结
     *@param param 参数
     *@return
     */
    public Map getCostCloseData(boolean isCost, Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getCostCloseData(getContext(), isCost, param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}