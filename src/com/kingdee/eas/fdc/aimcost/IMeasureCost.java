package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import java.lang.Object;
import java.math.BigDecimal;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IMeasureCost extends IFDCBill
{
    public MeasureCostInfo getMeasureCostInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MeasureCostInfo getMeasureCostInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MeasureCostCollection getMeasureCostCollection(EntityViewInfo view) throws BOSException;
    public MeasureCostCollection getMeasureCostCollection() throws BOSException;
    public MeasureCostCollection getMeasureCostCollection(String oql) throws BOSException;
    public String storeFromTemplate(String id, String orgId) throws BOSException, EASBizException;
    public void storeToTmplate(String id, boolean isTemplate) throws BOSException, EASBizException;
    public void storeToTemplate(String id, boolean isTemplate) throws BOSException, EASBizException;
    public void exportIndex(BOSUuid id) throws BOSException, EASBizException;
    public void exportAimCost(String measureId, String projectId) throws BOSException, EASBizException;
    public MeasureCostInfo getMeasureFromTemplate(String templateId, String orgId) throws BOSException, EASBizException;
    public Map getImportData(Map params) throws BOSException, EASBizException;
    public Object getTemplateDataStream() throws BOSException, EASBizException;
    public Map getMeasureRptData(Map params) throws BOSException, EASBizException;
    public void reverseWriteProject(String measureId, String targetPrjId) throws BOSException, EASBizException;
    public void costSycMeasureIncome(String measureId) throws BOSException, EASBizException;
    public void sysProduct(Map param) throws BOSException, EASBizException;
    public double getIncreaseRate(String billid, String costaccountLongNumber, String projectid) throws BOSException;
    public BigDecimal getTotalCostAccount(BOSUuid billId) throws BOSException, EASBizException;
    public BigDecimal getLastTotalCostAccount(BOSUuid billId) throws BOSException, EASBizException;
}