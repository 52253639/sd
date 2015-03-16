package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.math.BigDecimal;
import java.lang.Object;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.aimcost.MeasureCostCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MeasureCostController extends FDCBillController
{
    public MeasureCostInfo getMeasureCostInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public MeasureCostInfo getMeasureCostInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public MeasureCostCollection getMeasureCostCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public MeasureCostCollection getMeasureCostCollection(Context ctx) throws BOSException, RemoteException;
    public MeasureCostCollection getMeasureCostCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public String storeFromTemplate(Context ctx, String id, String orgId) throws BOSException, EASBizException, RemoteException;
    public void storeToTmplate(Context ctx, String id, boolean isTemplate) throws BOSException, EASBizException, RemoteException;
    public void storeToTemplate(Context ctx, String id, boolean isTemplate) throws BOSException, EASBizException, RemoteException;
    public void exportIndex(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public void exportAimCost(Context ctx, String measureId, String projectId) throws BOSException, EASBizException, RemoteException;
    public MeasureCostInfo getMeasureFromTemplate(Context ctx, String templateId, String orgId) throws BOSException, EASBizException, RemoteException;
    public Map getImportData(Context ctx, Map params) throws BOSException, EASBizException, RemoteException;
    public Object getTemplateDataStream(Context ctx) throws BOSException, EASBizException, RemoteException;
    public Map getMeasureRptData(Context ctx, Map params) throws BOSException, EASBizException, RemoteException;
    public void reverseWriteProject(Context ctx, String measureId, String targetPrjId) throws BOSException, EASBizException, RemoteException;
    public void costSycMeasureIncome(Context ctx, String measureId) throws BOSException, EASBizException, RemoteException;
    public void sysProduct(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
    public double getIncreaseRate(Context ctx, String billid, String costaccountLongNumber, String projectid) throws BOSException, RemoteException;
    public BigDecimal getTotalCostAccount(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public BigDecimal getLastTotalCostAccount(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
}