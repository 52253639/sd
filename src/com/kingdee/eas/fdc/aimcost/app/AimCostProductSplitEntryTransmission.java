/**
 * 
 */
package com.kingdee.eas.fdc.aimcost.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccount;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ProductType;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
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
public class AimCostProductSplitEntryTransmission extends AbstractDataTransmission {

	
	/** 各产品类型动态模板成本 */
	private AimCostProductSplitEntryInfo aimCostProductSplitEntry = null;
	
	private CostEntryInfo costEntry = null;
	/** 工程项目 */
	private CurProjectInfo curProject = null;
	private AimCostInfo aimCostInfo = null;
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
			factory = AimCostProductSplitEntryFactory.getLocalInstance(context);
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
		
		aimCostProductSplitEntry = new AimCostProductSplitEntryInfo();
		
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
			throw new TaskExternalException("工程项目编码不能为空！");
		}
		if (StringUtils.isEmpty(costAccountNumber)) {
			throw new TaskExternalException("成本科目代码不能为空！");
		}
		if (!StringUtils.isEmpty(trendsCostMoney)) {
			// 由于动态成本金额字段是非必录项，所以在改字段非空的前提下判断是否录入的为合法数字
			if (!trendsCostMoney.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				throw new TaskExternalException("动态成本金额录入不合法,应该录入数字型！");
			}
		}
//		if (StringUtils.isEmpty(choice)) {
//			throw new TaskExternalException("选择不能为空！"); // 现在改为非必录项
//		}
		if (StringUtils.isEmpty(fProductTypeLongnumber)) {
			throw new TaskExternalException("产品编码不能为空！");
		}
		if (StringUtils.isEmpty(fProductTypeTrends)) {
			throw new TaskExternalException("产品_动态不能为空！");
		}
		if (!fProductTypeTrends.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
			throw new TaskExternalException("产品_动态录入不合法,应该录入数字型！");
		}
		
		/**
		 * 对获取到的Excel模板中的数据进行数据对比,判断是否在数据库中存在这些数据
		 */
		try {
			// 2. 工程项目编码
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("longnumber", curProjectLongnumber));
			EntityViewInfo view1 = new EntityViewInfo();
			view1.setFilter(filter1);
			CurProjectCollection curProjectColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(view1);
			if (!(curProjectColl.size() > 0)){
				throw new TaskExternalException("该工程项目编码在系统中不存在！");
			} else {
				curProject = curProjectColl.get(0);
			}
			// 1. 成本科目代码
			FilterInfo filter4 = new FilterInfo();
			filter4.getFilterItems().add(new FilterItemInfo("longnumber", costAccountNumber, CompareType.EQUALS));
			filter4.getFilterItems().add(new FilterItemInfo("curproject", curProject.getId().toString(), CompareType.EQUALS));
			EntityViewInfo view4 = new EntityViewInfo();
			view4.setFilter(filter4);
			CostAccountCollection costAccountColl = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(view4);
			if (!(costAccountColl.size() > 0)) {
				throw new TaskExternalException("该成本科目代码在系统中不存在！");
			} else {
				costAccount = costAccountColl.get(0);
			}
			
//			<TODO 待确认编写>
//			FilterInfo filter7 = new FilterInfo();
//			filter7.getFilterItems().add(new FilterItemInfo("orgOrProId", curProject.getId().toString()));
//			filter7.getFilterItems().add(new FilterItemInfo("versionNumber", ""));
//			filter7.getFilterItems().add(new FilterItemInfo("versionName", ""));
//			filter7.setMaskString("#0 and #1 and #2");
//			EntityViewInfo view7 = new EntityViewInfo();
//			view7.setFilter(filter7);
//			SorterItemInfo sort7 = new SorterItemInfo("aimcost.versionNumber");
//			sort7.setSortType(SortType.DESCEND); // desc 排序规则
//			view7.getSorter().add(sort7);
			
//			// 1. 组织编码
//			costCenterOrgUnit = curProject.getCostCenter();
//			// 如果从Excel模板中得到的组织编码对象为空时,那么将从该工程项目对应的成本中心组织获取的编码赋给组织编码
//			// 如过从Excel模板中得到的组织编码对象不为空,那么将该组织编码与从工程项目对应的成本中心组织所获取到的编码对比是否相同
//			if (!StringUtils.isEmpty(costCenterOrgUnitNumber)) {
//				if (!costCenterOrgUnitNumber.equals(costCenterOrgUnit.getLongNumber())) {
//					throw new TaskExternalException("此组织编码在该工程项目所对应的成本中心组织中不存在！");
//				}
//			}
//			// 3. 工程项目名称
//			// 如果从Excel模板中得到的工程项目名称为空时,那么将从该工程项目中获取名称赋值
//			// 如果从Excel模板中得到的工程项目名称不为空时,那么将该工程项目名称与从工程项目中获取到的名称对比是否相同
//			if (!StringUtils.isEmpty(curProjectName)) {
//				if (!curProjectName.equals(curProject.getName())) {
//					throw new TaskExternalException("此工程项目名称与该工程项目编码所对应的名称不相同！");
//				}
//			}
			// 6. 动态成本金额 trendsCostMoney <TODO 暂时不能确定该值的存放对象,暂时不做处理>
			// 7. 选择 choice <TODO 暂时不能确定该值的存放对象,暂时不做处理>
			if (choice.equals("否")) { 
				// 如果录入的为"否"，则设置为false
				
			} else {
				// 如果录入的为"是"，则设置为true，或者录入为空时默认为ture，录入错误是也默认为true
				
			}
			// 8. 产品编码 fProductTypeLongnumber <TODO 现有产品对象，单需求文档要求为产品长编码，可是在产品对象中不存在长编码字段，有待确定。这里暂时以编码处理>
			FilterInfo filter8 = new FilterInfo();
			filter8.getFilterItems().add(new FilterItemInfo("number", fProductTypeLongnumber));
			EntityViewInfo view8 = new EntityViewInfo();
			view8.setFilter(filter8);
			ProductTypeCollection productTypeColl = ProductTypeFactory.getLocalInstance(context).getProductTypeCollection(view8);
			if (!(productTypeColl.size() > 0)) {
				throw new TaskExternalException("该产品编码在系统中不存在，不能导入！");
			} else {
				productTypeInfo = productTypeColl.get(0);
			}
			
//			EntityViewInfo view = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("costAccount.id", costAccount.getId().toString(), CompareType.EQUALS));
////			filter.getFilterItems().add(new FilterItemInfo("product.id", productTypeInfo.getId().toString(), CompareType.EQUALS));
//			view.setFilter(filter);
//			CostEntryCollection costEntrys = null;
//			costEntrys = CostEntryFactory.getLocalInstance(context).getCostEntryCollection(view);
//			if(costEntrys.size() > 0) {
//				costEntry = costEntrys.get(0);
//			} else {
//				throw new TaskExternalException("该拆分目标成本分录不存在，不能导入！");
//			}
			
			EntityViewInfo aimView = new EntityViewInfo();
			FilterInfo aimFilter = new FilterInfo();
			aimFilter.getFilterItems().add(new FilterItemInfo("orgOrProId", curProject.getId().toString(), CompareType.EQUALS));
			aimFilter.getFilterItems().add(new FilterItemInfo("state", "4AUDITTED", CompareType.EQUALS));
			aimView.setFilter(aimFilter);
			
			SorterItemCollection sorters = aimView.getSorter();
			SorterItemInfo sort = new SorterItemInfo("versionNumber");
			sort.setSortType(SortType.DESCEND);
			sorters.add(sort);
			aimView.setSorter(sorters);
			
			AimCostCollection aimCosts = null;
			aimCosts = AimCostFactory.getLocalInstance(context).getAimCostCollection(aimView);
			if(aimCosts.size() > 0) {
				aimCostInfo = aimCosts.get(0);
			} else {
				throw new TaskExternalException("该拆分目标成本不存在，不能导入！");
			}

			//先查询目标成本头
			CostEntryCollection costEntrys = aimCostInfo.getCostEntry();
			CostEntryInfo tempCostEntry = new CostEntryInfo();
			if(costEntrys.size()>0)
			{
				ProductTypeInfo typeTemp = null;
				CostAccountInfo accountTemp = null;
				for(int i=0;i<costEntrys.size();i++)
				{
					tempCostEntry = costEntrys.get(i);
					typeTemp = tempCostEntry.getProduct();
					accountTemp = tempCostEntry.getCostAccount();
					if(typeTemp!=null&&accountTemp!=null)
					{
						if(tempCostEntry.getProduct().getId().toString().equals(productTypeInfo.getId().toString())&&tempCostEntry.getCostAccount().getId().toString().equals(costAccount.getId().toString()))
						{
							costEntry = costEntrys.get(i);
							break;
						}
					}
				}
			}
			if(costEntry==null)
			{
				
				throw new TaskExternalException("该拆分目标成本分录不存在，不能导入！");
				
			}
			
			
			
			/**
			 * 将部分参数值存放到各产品类型动态目标成本<aimCostProductSplitEntry>对象中
			 */
			aimCostProductSplitEntry.setProduct(productTypeInfo);
			
			SelectorItemCollection appSelector=new SelectorItemCollection();
			appSelector.add("id");
			appSelector.add("targetType.id");
			ApportionTypeInfo apport = new ApportionTypeInfo();
			apport.setId(BOSUuid.read(ApportionTypeInfo.appointType));
			apport = ApportionTypeFactory.getLocalInstance(context).getApportionTypeInfo(new ObjectUuidPK(apport.getId()),appSelector);
			aimCostProductSplitEntry.setApportionType(apport);
			aimCostProductSplitEntry.setSplitAmount(FDCNumberHelper.toBigDecimal(trendsCostMoney));
			aimCostProductSplitEntry.setDirectAmount(FDCNumberHelper.toBigDecimal(trendsCostMoney));
			aimCostProductSplitEntry.setCostEntryId(costEntry.getId().toString());
			aimCostProductSplitEntry.setDescription("isChoose");
			
			
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		
		return aimCostProductSplitEntry;
	}

}
