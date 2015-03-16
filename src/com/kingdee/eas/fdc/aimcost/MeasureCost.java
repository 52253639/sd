package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import java.lang.Object;
import java.math.BigDecimal;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public class MeasureCost extends FDCBill implements IMeasureCost
{
    public MeasureCost()
    {
        super();
        registerInterface(IMeasureCost.class, this);
    }
    public MeasureCost(Context ctx)
    {
        super(ctx);
        registerInterface(IMeasureCost.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("99193494");
    }
    private MeasureCostController getController() throws BOSException
    {
        return (MeasureCostController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MeasureCostInfo getMeasureCostInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureCostInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public MeasureCostInfo getMeasureCostInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureCostInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public MeasureCostCollection getMeasureCostCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMeasureCostCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MeasureCostCollection getMeasureCostCollection() throws BOSException
    {
        try {
            return getController().getMeasureCostCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public MeasureCostCollection getMeasureCostCollection(String oql) throws BOSException
    {
        try {
            return getController().getMeasureCostCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *模板新增-User defined method
     *@param id 模板ID
     *@param orgId 组织ID
     *@return
     */
    public String storeFromTemplate(String id, String orgId) throws BOSException, EASBizException
    {
        try {
            return getController().storeFromTemplate(getContext(), id, orgId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *存为模板-User defined method
     *@param id id
     *@param isTemplate isTemplate
     */
    public void storeToTmplate(String id, boolean isTemplate) throws BOSException, EASBizException
    {
        try {
            getController().storeToTmplate(getContext(), id, isTemplate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *与storeToTmplate同-User defined method
     *@param id id
     *@param isTemplate isTemplate
     */
    public void storeToTemplate(String id, boolean isTemplate) throws BOSException, EASBizException
    {
        try {
            getController().storeToTemplate(getContext(), id, isTemplate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *导出指标-User defined method
     *@param id id
     */
    public void exportIndex(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().exportIndex(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *导出目标成本-User defined method
     *@param measureId 测算ID
     *@param projectId 工程项目名
     */
    public void exportAimCost(String measureId, String projectId) throws BOSException, EASBizException
    {
        try {
            getController().exportAimCost(getContext(), measureId, projectId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *模板取数-User defined method
     *@param templateId 模板ID
     *@param orgId 组织ID
     *@return
     */
    public MeasureCostInfo getMeasureFromTemplate(String templateId, String orgId) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureFromTemplate(getContext(), templateId, orgId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *导入粗粒度获取数据方法-User defined method
     *@param params 参数列表
     *@return
     */
    public Map getImportData(Map params) throws BOSException, EASBizException
    {
        try {
            return getController().getImportData(getContext(), params);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *导入服务端模板数据流-User defined method
     *@return
     */
    public Object getTemplateDataStream() throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateDataStream(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *测算汇总表取数-User defined method
     *@param params 参数
     *@return
     */
    public Map getMeasureRptData(Map params) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureRptData(getContext(), params);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反写工程项目，导出目标成本及保存为最终版本的时候反写工程项目，同时更新分录上的科目信息-User defined method
     *@param measureId 测算
     *@param targetPrjId 目标工程项目
     */
    public void reverseWriteProject(String measureId, String targetPrjId) throws BOSException, EASBizException
    {
        try {
            getController().reverseWriteProject(getContext(), measureId, targetPrjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *保存、提交时同步收入测算-User defined method
     *@param measureId 成本测算Id
     */
    public void costSycMeasureIncome(String measureId) throws BOSException, EASBizException
    {
        try {
            getController().costSycMeasureIncome(getContext(), measureId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步项目产品-User defined method
     *@param param param
     */
    public void sysProduct(Map param) throws BOSException, EASBizException
    {
        try {
            getController().sysProduct(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *若有修订版本，提交到工作流时，需要计算某科目与版本1.0时总计的增长率，eg: 第二次修订，是否超目标成本及超出百分数=（1.2实施成本总额-1.0实施成本总额）÷1.0实施成本总额。-User defined method
     *@param billid 参加工作流的目标成本单据的ID
     *@param costaccountLongNumber 成本科目的长编码
     *@param projectid 工程项目ID
     *@return
     */
    public double getIncreaseRate(String billid, String costaccountLongNumber, String projectid) throws BOSException
    {
        try {
            return getController().getIncreaseRate(getContext(), billid, costaccountLongNumber, projectid);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取目标成本总价-User defined method
     *@param billId billId
     *@return
     */
    public BigDecimal getTotalCostAccount(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            return getController().getTotalCostAccount(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取最新版本目标成本总价-User defined method
     *@param billId billId
     *@return
     */
    public BigDecimal getLastTotalCostAccount(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            return getController().getLastTotalCostAccount(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}