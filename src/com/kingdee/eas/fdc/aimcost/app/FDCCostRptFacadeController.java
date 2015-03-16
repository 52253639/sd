package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import java.math.BigDecimal;
import com.kingdee.eas.fdc.aimcost.ProductDynamicCostMap;
import java.util.Map;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.aimcost.AimCostMap;
import com.kingdee.eas.fdc.aimcost.DynamicCostMap;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.aimcost.FullDynamicCostMap;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.ProductAimCostMap;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCCostRptFacadeController extends BizController
{
    public AimCostMap getAimCost(Context ctx, String orgOrPrjId, PeriodInfo period) throws BOSException, EASBizException, RemoteException;
    public ProductAimCostMap getProductAimCost(Context ctx, String prjId, ProjectStageEnum indexProjectStage, PeriodInfo period) throws BOSException, EASBizException, RemoteException;
    public FullDynamicCostMap getFullDynamicCost(Context ctx, String prjId, PeriodInfo period) throws BOSException, EASBizException, RemoteException;
    public ProductDynamicCostMap getProductDynamicCost(Context ctx, String prjId, PeriodInfo period) throws BOSException, EASBizException, RemoteException;
    public Map submitAimProductCost(Context ctx, Map costEntryMap) throws BOSException, EASBizException, RemoteException;
    public Map submitAIMAimProductCost(Context ctx, Map costEntryMap) throws BOSException, EASBizException, RemoteException;
    public Map submitDynProductCost(Context ctx, Map dynCostMap) throws BOSException, EASBizException, RemoteException;
    public FullDynamicCostMap getFullPrjDynamicCost(Context ctx, String projOrgId, PeriodInfo period) throws BOSException, EASBizException, RemoteException;
    public void saveSnapShot(Context ctx, String prjId, CostMonthlySaveTypeEnum savedType) throws BOSException, EASBizException, RemoteException;
    public void autoSaveSnapShot(Context ctx) throws BOSException, RemoteException;
    public DynamicCostMap getDynamicCost(Context ctx, String prjId, PeriodInfo period, boolean isLeaf) throws BOSException, EASBizException, RemoteException;
    public void reverseCostMonthSettled(Context ctx, String prjId, PeriodInfo period) throws BOSException, EASBizException, RemoteException;
    public void reverseFinanceMonthSettled(Context ctx, String prjId, PeriodInfo period) throws BOSException, EASBizException, RemoteException;
    public void deleteDynSnapShot(Context ctx, String prjId, PeriodInfo period) throws BOSException, EASBizException, RemoteException;
    public BigDecimal getAimCost(Context ctx, String prjId) throws BOSException, EASBizException, RemoteException;
    public BigDecimal getDynCost(Context ctx, String prjId) throws BOSException, EASBizException, RemoteException;
    public BigDecimal getHappendCost(Context ctx, String prjId) throws BOSException, EASBizException, RemoteException;
    public BigDecimal getAdjustCost(Context ctx, String prjId) throws BOSException, EASBizException, RemoteException;
    public Map getCostAccountAimCost(Context ctx, String prjId) throws BOSException, EASBizException, RemoteException;
    public Map getCostAccountDynCost(Context ctx, String prjId) throws BOSException, EASBizException, RemoteException;
    public Map getCostMap(Context ctx, Map param) throws BOSException, RemoteException;
    public void updatePeriodCostPayedAmt(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
    public Map getPaymentCostStatistics(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
    public Map getNonLeafPaymentCostStatistics(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
    public Map getProjectAnalysisCost(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
    public Map getCostCloseData(Context ctx, boolean isCost, Map param) throws BOSException, EASBizException, RemoteException;
}