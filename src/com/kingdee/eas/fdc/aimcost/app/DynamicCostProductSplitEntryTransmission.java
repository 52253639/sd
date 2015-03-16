/**
 * 
 */
package com.kingdee.eas.fdc.aimcost.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.DyCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.ProductDynamicCostMap;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.util.StringUtils;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		
 *		
 * @description 各产品类型动态目标成本<各产品动态成本分摊数据>_导入实现类
 * @author		雍定文
 * @version		EAS7.0		
 * @createDate	2011-6-29	 
 * @see						
 */
public class DynamicCostProductSplitEntryTransmission extends AbstractDataTransmission {
	// 资源文件
	private static String resource = "com.kingdee.eas.fdc.aimcost.AimCostTransmissionResource";
	/** 各产品类型动态模板成本 */
	private DynamicCostProductSplitEntryInfo dynamicCostProductSplitEntryInfo = null;
	
	private DynamicCostInfo dynamicCostInfo = null;
	/** 工程项目 */
	private CurProjectInfo curProject = null;
	/** 工程项目所对应的成本中心<组织> */
	private CostCenterOrgUnitInfo costCenterOrgUnit =null;
	/** 成本科目 */
	private CostAccountInfo costAccount = null;
	/** 产品<类型> */
	private ProductTypeInfo productTypeInfo = null;
	
	/**
	 * @description		
	 * @author			雍定文		
	 * @createDate		2011-6-29
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#getController(com.kingdee.bos.Context)					
	 */
	protected ICoreBase getController(Context context) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = DynamicCostProductSplitEntryFactory.getLocalInstance(context);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	/**
	 * @description		
	 * @author			雍定文		
	 * @createDate		2011-6-29
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#transmit(java.util.Hashtable, com.kingdee.bos.Context)					
	 */
	public CoreBaseInfo transmit(Hashtable hashtable, Context context) throws TaskExternalException {
		
		dynamicCostProductSplitEntryInfo = new DynamicCostProductSplitEntryInfo();
		
		/**
		 * 获取Excel模板中的数据
		 */
		// 1. 组织编码
		String costCenterOrgUnitNumber = ((String) ((DataToken) hashtable.get("CostCenterOrgUnit_number")).data).trim();
		// 2. 工程项目编码
		String curProjectLongnumber = ((String) ((DataToken) hashtable.get("CurProject_longnumber")).data).trim();
		// 3. 工程项目名称
		String curProjectName = ((String) ((DataToken) hashtable.get("CurProject_name")).data).trim();
		// 4. 成本科目代码
		String costAccountNumber = ((String) ((DataToken) hashtable.get("CostAccount_number")).data).trim();
		costAccountNumber = costAccountNumber.replace('!', '.');
		// 5. 成本科目名称
		String costAccountName = ((String) ((DataToken) hashtable.get("CostAccount_name")).data).trim();
		// 6. 动态成本金额
		String trendsCostMoney = ((String) ((DataToken) hashtable.get("TrendsCostMoney")).data).trim();
		// 7. 选择
		String choice = ((String) ((DataToken) hashtable.get("Choice")).data).trim();
		// 8. 产品编码
		String fProductTypeLongnumber = ((String) ((DataToken) hashtable.get("FProductType_longnumber")).data).trim();
		// 9. 产品_动态
		String fProductTypeTrends = ((String) ((DataToken) hashtable.get("FProductType_trends")).data).trim();
		
		/**
		 * 对获取到的Excel模板中的数据进行非空校验
		 */
		if (StringUtils.isEmpty(curProjectLongnumber)) {
			throw new TaskExternalException(getResource(context, "CurProjectNumberNotNull"));
		}
		if (StringUtils.isEmpty(costAccountNumber)) {
			throw new TaskExternalException(getResource(context, "CostAccountNumberNotNull"));
		}
		if (!StringUtils.isEmpty(trendsCostMoney)) {
			// 由于动态成本金额字段是非必录项，所以在改字段非空的前提下判断是否录入的为合法数字
			if (!trendsCostMoney.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				throw new TaskExternalException(getResource(context, "Import_DynamicCostAmount"));
			}
		}
		if (StringUtils.isEmpty(fProductTypeLongnumber)) {
			throw new TaskExternalException(getResource(context, "Import_ProductNumberIsNull"));
		}
		if (StringUtils.isEmpty(fProductTypeTrends)) {
			throw new TaskExternalException(getResource(context, "Import_ProductDynamicIsNull"));
		}
		if (!fProductTypeTrends.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
			throw new TaskExternalException(getResource(context, "Import_ProductDynamicIsError"));
		}
		
		/**
		 * 对获取到的Excel模板中的数据进行数据对比,判断是否在数据库中存在这些数据
		 */
		try {
			// 2. 工程项目编码
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("longnumber", curProjectLongnumber.replace('.', '!')));
			EntityViewInfo view1 = new EntityViewInfo();
			view1.setFilter(filter1);
			CurProjectCollection curProjectColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(view1);
			if (!(curProjectColl.size() > 0)){
				throw new TaskExternalException(getResource(context, "CurProjectNumberNotFound"));
			} else {
				curProject = curProjectColl.get(0);
			}
			// 1. 成本科目代码
			FilterInfo filter4 = new FilterInfo();
			filter4.getFilterItems().add(new FilterItemInfo("codingnumber", costAccountNumber, CompareType.EQUALS));
			filter4.getFilterItems().add(new FilterItemInfo("curproject", curProject.getId().toString(), CompareType.EQUALS));
			EntityViewInfo view4 = new EntityViewInfo();
			view4.setFilter(filter4);
			CostAccountCollection costAccountColl = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(view4);
			if (!(costAccountColl.size() > 0)) {
				throw new TaskExternalException(getResource(context, "Import_CostAccount_NotExist"));
			} else {
				costAccount = costAccountColl.get(0);
			}
			// 6. 动态成本金额 trendsCostMoney <TODO 暂时不能确定该值的存放对象,暂时不做处理>
			// 7. 选择 choice <TODO 暂时不能确定该值的存放对象,暂时不做处理>
//			if (choice.equals("否")) { 
//				// 如果录入的为"否"，则设置为false
//			} else {
//				// 如果录入的为"是"，则设置为true，或者录入为空时默认为ture，录入错误是也默认为true
//				
//			}
			// 8. 产品编码 fProductTypeLongnumber <TODO 现有产品对象，单需求文档要求为产品长编码，可是在产品对象中不存在长编码字段，有待确定。这里暂时以编码处理>
			FilterInfo filter8 = new FilterInfo();
			filter8.getFilterItems().add(new FilterItemInfo("number", fProductTypeLongnumber));
			EntityViewInfo view8 = new EntityViewInfo();
			view8.setFilter(filter8);
			ProductTypeCollection productTypeColl = ProductTypeFactory.getLocalInstance(context).getProductTypeCollection(view8);
			if (!(productTypeColl.size() > 0)) {
				throw new TaskExternalException(getResource(context, "Import_Product_NotExist"));
			} else {
				productTypeInfo = productTypeColl.get(0);
			}
			//查询得到单头
			
			final ProductDynamicCostMap productDynamicCostMap = FDCCostRptFacadeFactory.getLocalInstance(context).getProductDynamicCost(curProject.getId().toString(), null);
			DyCostSplitDataGetter dyGetter= productDynamicCostMap.getDyCostSplitDataGetter();
			if(dyGetter!=null)
			{
				dynamicCostInfo = dyGetter.getDynamicInfo(costAccount.getId().toString());
			}
			
			if(dynamicCostInfo == null)
			{
				throw new TaskExternalException(getResource(context, "Import_Dynamic_NotExist"));
			}
			/**
			 * 将部分参数值存放到各产品类型动态目标成本<aimCostProductSplitEntry>对象中
			 */
			dynamicCostProductSplitEntryInfo.setProduct(productTypeInfo);
			
			SelectorItemCollection appSelector=new SelectorItemCollection();
			appSelector.add("id");
			appSelector.add("targetType.id");
			ApportionTypeInfo apport = new ApportionTypeInfo();
			apport.setId(BOSUuid.read(ApportionTypeInfo.appointType));
			apport = ApportionTypeFactory.getLocalInstance(context).getApportionTypeInfo(new ObjectUuidPK(apport.getId()),appSelector);
			dynamicCostProductSplitEntryInfo.setApportionType(apport);
			dynamicCostProductSplitEntryInfo.setSplitAmount(FDCNumberHelper.toBigDecimal(trendsCostMoney));
			dynamicCostProductSplitEntryInfo.setAimCostAmount(FDCNumberHelper.toBigDecimal(trendsCostMoney));
			dynamicCostProductSplitEntryInfo.setAppointAmount(FDCNumberHelper.toBigDecimal(trendsCostMoney));
			dynamicCostProductSplitEntryInfo.setIntendingDirectAmount(FDCNumberHelper.toBigDecimal(trendsCostMoney));
			dynamicCostProductSplitEntryInfo.setHanppenDirectAmount(FDCNumberHelper.toBigDecimal(trendsCostMoney));
			dynamicCostProductSplitEntryInfo.setDynamicCostId(dynamicCostInfo.getId().toString());
			dynamicCostProductSplitEntryInfo.setIsLatestVer(true);
			dynamicCostProductSplitEntryInfo.setDescription("isChoose");
			
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		
		return dynamicCostProductSplitEntryInfo;
	}

	/**
	 * 得到资源文件
	 */
	public static String getResource(Context context, String key) {
		return ResourceBase.getString(resource, key, context.getLocale());
	}
}
