package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.basedata.assistant.PeriodInfo;
import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.math.BigDecimal;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;

public interface IFDCCostRptFacade extends IBizCtrl
{
    public AimCostMap getAimCost(String orgOrPrjId, PeriodInfo period) throws BOSException, EASBizException;
    public ProductAimCostMap getProductAimCost(String prjId, ProjectStageEnum indexProjectStage, PeriodInfo period) throws BOSException, EASBizException;
    public FullDynamicCostMap getFullDynamicCost(String prjId, PeriodInfo period) throws BOSException, EASBizException;
    public ProductDynamicCostMap getProductDynamicCost(String prjId, PeriodInfo period) throws BOSException, EASBizException;
    public Map submitAimProductCost(Map costEntryMap) throws BOSException, EASBizException;
    public Map submitAIMAimProductCost(Map costEntryMap) throws BOSException, EASBizException;
    public Map submitDynProductCost(Map dynCostMap) throws BOSException, EASBizException;
    public FullDynamicCostMap getFullPrjDynamicCost(String projOrgId, PeriodInfo period) throws BOSException, EASBizException;
    public void saveSnapShot(String prjId, CostMonthlySaveTypeEnum savedType) throws BOSException, EASBizException;
    public void autoSaveSnapShot() throws BOSException;
    public DynamicCostMap getDynamicCost(String prjId, PeriodInfo period, boolean isLeaf) throws BOSException, EASBizException;
    public void reverseCostMonthSettled(String prjId, PeriodInfo period) throws BOSException, EASBizException;
    public void reverseFinanceMonthSettled(String prjId, PeriodInfo period) throws BOSException, EASBizException;
    public void deleteDynSnapShot(String prjId, PeriodInfo period) throws BOSException, EASBizException;
    public BigDecimal getAimCost(String prjId) throws BOSException, EASBizException;
    public BigDecimal getDynCost(String prjId) throws BOSException, EASBizException;
    public BigDecimal getHappendCost(String prjId) throws BOSException, EASBizException;
    public BigDecimal getAdjustCost(String prjId) throws BOSException, EASBizException;
    public Map getCostAccountAimCost(String prjId) throws BOSException, EASBizException;
    public Map getCostAccountDynCost(String prjId) throws BOSException, EASBizException;
    public Map getCostMap(Map param) throws BOSException;
    public void updatePeriodCostPayedAmt(Map param) throws BOSException, EASBizException;
    public Map getPaymentCostStatistics(Map param) throws BOSException, EASBizException;
    public Map getNonLeafPaymentCostStatistics(Map param) throws BOSException, EASBizException;
    public Map getProjectAnalysisCost(Map param) throws BOSException, EASBizException;
    public Map getCostCloseData(boolean isCost, Map param) throws BOSException, EASBizException;
}