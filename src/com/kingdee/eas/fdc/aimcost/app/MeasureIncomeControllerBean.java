package com.kingdee.eas.fdc.aimcost.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.String;

import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.lang.Object;
import java.math.BigDecimal;

import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.eas.fdc.basedata.util.CostAccountHelper;
import com.kingdee.eas.fdc.basedata.util.IncomeAccountHelper;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.aimcost.AIMAimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostException;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.MeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeCollection;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.aimcost.ProjectCostChangeLogFactory;
import com.kingdee.eas.fdc.aimcost.TemplateCustomPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.TemplateCustomPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.TemplatePlanIndexCollection;
import com.kingdee.eas.fdc.aimcost.TemplatePlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.TemplatePlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.TemplatePlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.TemplatePlanIndexInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IncomeAccountCollection;
import com.kingdee.eas.fdc.basedata.IncomeAccountFactory;
import com.kingdee.eas.fdc.basedata.IncomeAccountInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexVerTypeEnum;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeCollection;
import com.kingdee.eas.fdc.basedata.ProjectTypeFactory;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class MeasureIncomeControllerBean extends AbstractMeasureIncomeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.MeasureIncomeControllerBean");
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		FDCBillInfo billInfo = new FDCBillInfo();

		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		billInfo.setAuditTime(new Date());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");

		_updatePartial(ctx, billInfo, selector);
	}

	protected boolean isUseName() {

		return false;
	}

	protected boolean isUseNumber() {
		return false;
	}

	protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		MeasureIncomeInfo info = (MeasureIncomeInfo) model;
		info.setVersionNumber(info.getVersionNumber().replaceAll("\\.", "!"));
		//PlanIndexInfo planIndex = (PlanIndexInfo) info.get("PlanIndex");
		
		//add by david_yang R110407-298 2011.04.19
		if(info.getSrcMeasureCostId()!=null){
			MeasureCostInfo mcinfo=MeasureCostFactory.getLocalInstance(ctx).getMeasureCostInfo(new ObjectUuidPK(info.getSrcMeasureCostId()));
			info.setMeasureStage(mcinfo.getMeasureStage());
			info.setProjectType(mcinfo.getProjectType());
			info.setVersionName(mcinfo.getVersionName());			
		}
		super._save(ctx, pk, model);
		//FilterInfo filter = new FilterInfo();
		//filter.appendFilterItem("headID", pk.toString());
		//PlanIndexFactory.getLocalInstance(ctx).delete(filter);
		//planIndex.setHeadID(pk.toString());
//		PlanIndexFactory.getLocalInstance(ctx).submit(planIndex); by Cassiel_peng  2009-8-18
		//PlanIndexFactory.getLocalInstance(ctx).save(planIndex);
	}

	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		MeasureIncomeInfo info = (MeasureIncomeInfo) model;
		info.setVersionNumber(info.getVersionNumber().replaceAll("\\.", "!"));
		if(info.getVersionNumber()!=null&&info.getVersionNumber().indexOf('!')>-1){
			int index=info.getVersionNumber().indexOf('!');
			info.setMainVerNo(Integer.parseInt(info.getVersionNumber().substring(0,index)));
			info.setSubVerNo(Integer.parseInt(info.getVersionNumber().substring(index+1)));
		}
		//PlanIndexInfo planIndex = (PlanIndexInfo) info.get("PlanIndex");
		
		//add by david_yang R110407-298 2011.04.19
		if(info.getSrcMeasureCostId()!=null){
			MeasureCostInfo mcinfo=MeasureCostFactory.getLocalInstance(ctx).getMeasureCostInfo(new ObjectUuidPK(info.getSrcMeasureCostId()));
			info.setMeasureStage(mcinfo.getMeasureStage());
			info.setProjectType(mcinfo.getProjectType());
			info.setVersionName(mcinfo.getVersionName());			
		}
		
		IObjectPK pk = super._save(ctx, model);
		//FilterInfo filter = new FilterInfo();
		//filter.appendFilterItem("headID", pk.toString());
		//PlanIndexFactory.getLocalInstance(ctx).delete(filter);
		//planIndex.setHeadID(pk.toString());
//		PlanIndexFactory.getLocalInstance(ctx).submit(planIndex);  by Cassiel_peng  2009-8-18
		//PlanIndexFactory.getLocalInstance(ctx).save(planIndex);
		return pk;
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		MeasureIncomeInfo info = (MeasureIncomeInfo) model;
		info.setVersionNumber(info.getVersionNumber().replaceAll("\\.", "!"));
		if(info.getVersionNumber()!=null&&info.getVersionNumber().indexOf('!')>-1){
			int index=info.getVersionNumber().indexOf('!');
			info.setMainVerNo(Integer.parseInt(info.getVersionNumber().substring(0,index)));
			info.setSubVerNo(Integer.parseInt(info.getVersionNumber().substring(index+1)));
		}
		//PlanIndexInfo planIndex = (PlanIndexInfo) info.get("PlanIndex");
//		IObjectPK pk = super._save(ctx, model);    by Cassiel_peng  2009-8-18
		
		//add by david_yang R110407-298 2011.04.19
		if(info.getSrcMeasureCostId()!=null){
			MeasureCostInfo mcinfo=MeasureCostFactory.getLocalInstance(ctx).getMeasureCostInfo(new ObjectUuidPK(info.getSrcMeasureCostId()));
			info.setMeasureStage(mcinfo.getMeasureStage());
			info.setProjectType(mcinfo.getProjectType());
			info.setVersionName(mcinfo.getVersionName());			
		}
		
		IObjectPK pk = super._submit(ctx, model);
		//FilterInfo filter = new FilterInfo();
		//filter.appendFilterItem("headID", pk.toString());
		//PlanIndexFactory.getLocalInstance(ctx).delete(filter);
//		if(planIndex==null){
//			return pk;
//		}
		//planIndex.setHeadID(pk.toString());
//		for (Iterator iter = planIndex.getEntrys().iterator(); iter.hasNext();) {
//			PlanIndexEntryInfo entry = (PlanIndexEntryInfo) iter.next();
//			// 将null进行转换
//			entry.setIsSplit(entry.isIsSplit());
//		}
		//PlanIndexFactory.getLocalInstance(ctx).submit(planIndex);
		return pk;
	}

	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		MeasureIncomeInfo info = (MeasureIncomeInfo) model;
		info.setVersionNumber(info.getVersionNumber().replaceAll("\\.", "!"));
//		info.setState(FDCBillStateEnum.SAVED);
		info.setState(FDCBillStateEnum.SUBMITTED);
		//PlanIndexInfo planIndex = (PlanIndexInfo) info.get("PlanIndex");
		
		//add by david_yang R110407-298 2011.04.19
		if(info.getSrcMeasureCostId()!=null){
			MeasureCostInfo mcinfo=MeasureCostFactory.getLocalInstance(ctx).getMeasureCostInfo(new ObjectUuidPK(info.getSrcMeasureCostId()));
			info.setMeasureStage(mcinfo.getMeasureStage());
			info.setProjectType(mcinfo.getProjectType());
			info.setVersionName(mcinfo.getVersionName());			
		}
		
		super._submit(ctx, pk, model);
		//FilterInfo filter = new FilterInfo();
		//filter.appendFilterItem("headID", pk.toString());
		//PlanIndexFactory.getLocalInstance(ctx).delete(filter);
		//planIndex.setHeadID(pk.toString());
		//PlanIndexFactory.getLocalInstance(ctx).submit(planIndex);
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		//FilterInfo filter = new FilterInfo();
		//filter.appendFilterItem("headID", pk.toString());
		//PlanIndexFactory.getLocalInstance(ctx).delete(filter);
		super._delete(ctx, pk);
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
//		Set set = new HashSet();
//		for (int i = 0; i < arrayPK.length; i++) {
//			set.add(arrayPK[i].toString());
//		}
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("headID", set, CompareType.INCLUDE));
//		PlanIndexFactory.getLocalInstance(ctx).delete(filter);
		super._delete(ctx, arrayPK);
	}
	
	private Map getAcctMap(Context ctx, String objectId) throws BOSException {
		//create Acct Map
		Map map = new TreeMap();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		if(new CurProjectInfo().getBOSType().equals(BOSUuid.read(objectId).getType())){
			view.getFilter().appendFilterItem("curProject.id", objectId);
		}else{
			view.getFilter().appendFilterItem("fullOrgUnit.id", objectId);
		}
//		view.getFilter().appendFilterItem("isLeaf", Boolean.TRUE);
		view.getFilter().appendFilterItem("isEnabled", Boolean.TRUE);
		IncomeAccountCollection c = IncomeAccountFactory.getLocalInstance(ctx).getIncomeAccountCollection(view);
		for (Iterator iter = c.iterator(); iter.hasNext();) {
			IncomeAccountInfo info = (IncomeAccountInfo) iter.next();
			map.put(info.getLongNumber(), info);
		}
		return map;
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		FDCBillInfo billInfo = new FDCBillInfo();

		billInfo.setId(billId);
		/**
		 * 之前"可研成本测算编辑界面"并没有提供"提交"功能,所以"保存"状态的单据可直接"审批".反审批之后也就将单据状态设置为了"保存"
		 * 现提供"提交"功能，就必须控制反审批之后单据状态为"提交"而非"保存"   by Cassiel_peng 2009-8-18
		 */
//		billInfo.setState(FDCBillStateEnum.SAVED);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");

		_updatePartial(ctx, billInfo, selector);
	}
	
	
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		return new HashMap();
	}

	protected Map _getImportData(Context ctx, Map params) throws BOSException, EASBizException {
		if(params==null||params.size()<=0){
			return new HashMap();
		}
		Map dataMap=new HashMap();
		String projectTypeNumber=(String)params.get("ProjectTypeNumber");
		String projectNumber=(String)params.get("ProjectNumber");
		String orgId=(String)params.get("OrgId");
		Set productSet=(Set)params.get("ProductNumberSet");
		if(projectTypeNumber!=null){
			EntityViewInfo view=new  EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			view.setFilter(filter);
			filter.appendFilterItem("number", projectTypeNumber);
			view.getSelector().add("number");
			view.getSelector().add("id");
			view.getSelector().add("name");
			ProjectTypeCollection projectTypeCol=ProjectTypeFactory.getLocalInstance(ctx).getProjectTypeCollection(view);
			if(projectTypeCol.size()>0){
				dataMap.put("projectType", projectTypeCol.get(0));
			}
		}
		if(projectNumber!=null){
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new  FilterInfo();
			view.setFilter(filter);
			filter.appendFilterItem("longNumber", projectNumber.replace('.','!'));
			view.getSelector().add("id");
			view.getSelector().add("longNumber");
			view.getSelector().add("number");
			view.getSelector().add("name");
			view.getSelector().add("projectType.id");
			CurProjectCollection prjs=CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(view);
			if(prjs.size()>0){
				dataMap.put("project", prjs.get(0));
			}
		}
		if(orgId!=null){
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new  FilterInfo();
			view.setFilter(filter);
			filter.appendFilterItem("fullOrgUnit.id", orgId);
			view.getSelector().add("number");
			view.getSelector().add("name");
			view.getSelector().add("id");
			view.getSelector().add("isLeaf");
			view.getSelector().add("longNumber");
			IncomeAccountCollection accts=IncomeAccountFactory.getLocalInstance(ctx).getIncomeAccountCollection(view);
			if(accts.size()>0){
				HashMap acctMap=new HashMap();
				for(Iterator iter=accts.iterator();iter.hasNext();){
					IncomeAccountInfo info=(IncomeAccountInfo)iter.next();
					acctMap.put(info.getLongNumber().replace('!', '.'), info);
				}
				dataMap.put("acctMap", acctMap);
			}
			
		}
		if(productSet!=null&&productSet.size()>0){
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new  FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("number",productSet,CompareType.INCLUDE));
			ProductTypeCollection productCol=ProductTypeFactory.getLocalInstance(ctx).getProductTypeCollection(view);
			if(productCol.size()>0){
				Map productMap=new HashMap();
				for(Iterator iter=productCol.iterator();iter.hasNext();){
					ProductTypeInfo info=(ProductTypeInfo)iter.next();
					productMap.put(info.getNumber(), info);
				}
				dataMap.put("productMap", productMap);
			}
		}
		return dataMap;
	}
	
	
	private IncomeAccountInfo getMapIncomeAccount(Map acctMap,String longNumber){
		return IncomeAccountHelper.getIncomeAccount(acctMap, longNumber);
	}

	protected void _reverseWriteProject(Context ctx, String measureId, String targetPrjId) throws BOSException, EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("project.id");
		sels.add("incomeEntry.id");
		sels.add("incomeEntry.incomeAccount.id");
		sels.add("incomeEntry.incomeAccount.longNumber");
		sels.add("incomeEntry.incomeAccount.curProject.id");
		MeasureIncomeInfo measure = MeasureIncomeFactory.getLocalInstance(ctx).getMeasureIncomeInfo(new ObjectUuidPK(BOSUuid.read(measureId)), sels);
		
		sels=new SelectorItemCollection();
		sels.add("project.id");
		MeasureIncomeFactory.getLocalInstance(ctx).updatePartial(measure, sels);
		String projectId=targetPrjId;
		//如果测算对应的科目的工程项目与测算的项目不同则要更新测算科目为测算项目的科目
		boolean mustSyn=true;
		if(measure.getIncomeEntry().size()>0){
			if(measure.getIncomeEntry().get(0).getIncomeAccount().getCurProject()!=null){
				String acctPrjId=measure.getIncomeEntry().get(0).getIncomeAccount().getCurProject().getId().toString();
				if(acctPrjId.equals(projectId)){
					mustSyn=false;
				}
			}
		}
		
		if(mustSyn){
			//同步
			Map acctMap=getAcctMap(ctx, projectId);
			List list=new ArrayList();
			String insertSql="update T_Aim_MeasureIncomeEntry set fincomeaccountid=? where fid=?";
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			for(Iterator iter=measure.getIncomeEntry().iterator();iter.hasNext();){
				MeasureIncomeEntryInfo entry=(MeasureIncomeEntryInfo)iter.next();
				IncomeAccountInfo acct=getMapIncomeAccount(acctMap, entry.getIncomeAccount().getLongNumber());
				
				list.add(Arrays.asList(new String[]{acct.getId().toString(),entry.getId().toString()}));
				if(list.size()>1000){
					builder.executeBatch(insertSql, list);
					list.clear();
				}
			}
			
			if(list.size()>0){
				builder.executeBatch(insertSql, list);
			}
		}
		
	}	
	
	/**
	 * 设私有属性params(HashMap),可采取以下方式取值
	 * <pre>if (params==null) {
			try {
				params = FDCUtils.getDefaultFDCParam(null, null);
			} catch (Exception e) {
				handUIException(e);
			}
		}
        Object theValue = params.get(FDCConstants.FDC_PARAM_MEASUREQUALITY);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	 * @param ctx 
	 */
	private HashMap getParams(Context ctx) {
	        HashMap hmParamIn = new HashMap();
	        HashMap hmAllParam= new HashMap();
	        hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREADJUST, null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREQUALITY, null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_USECOSTOMINDEX, null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_PLANINDEXLOGIC, null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_BUILDPARTLOGIC, null);
	        try{
	        	hmAllParam = ParamControlFactory.getLocalInstance(ctx).getParamHashMap(hmParamIn);
	        }catch(Exception e){
	        	//TODO
	        	e.printStackTrace();
	        }
        return hmAllParam;
	}
	
	/**
	 * 启用自定义指标
	 * @return
	 */
	protected boolean isUseCustomIndex(Map hmAllParam) {
		Object theValue = hmAllParam.get(FDCConstants.FDC_PARAM_USECOSTOMINDEX);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	/**
	 * 可研成本测算及目标成本测算时，规划指标表上：人行道及广场计算在“绿化用地合计”内
	 * @return
	 */
	protected boolean isPlanIndexLogic(Map hmAllParam) {
		Object theValue = hmAllParam.get(FDCConstants.FDC_PARAM_PLANINDEXLOGIC);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	/**
	 * 可研成本测算、目标成本测算上项目建筑单方计算，目标成本测算导出项目建筑面积指标都使用参与分摊的产品建筑面积之和，而不是总建筑面积
	 * @return
	 */
	protected boolean isBuildPartLogic(Map hmAllParam) {
		Object theValue = hmAllParam.get(FDCConstants.FDC_PARAM_BUILDPARTLOGIC);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
}