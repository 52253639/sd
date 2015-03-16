/**
 * 
 */
package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AIMAimCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.AIMAimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		
 *		
 * @description 各产品目标成本导入_导入实现类
 * @author		黄远昌
 * @version		EAS7.0		
 * @createDate	2011-7-14	 
 * @see						
 */
public class AIMProductAimCostImport extends AbstractDataTransmission {
	
	private static String resource = "com.kingdee.eas.fdc.aimcost.AIMProductAimCostImportResource";
	
	/** 各产品类型目标成本 */
	private AIMAimCostProductSplitEntryInfo aimCostProductSplitEntryInfo = null;
	/** 目标成本头 */
	private AimCostInfo aimCostInfo = null;
	/** 目标成本分录 */
	private CostEntryInfo costEntry = null;
	/** 工程项目 */
	private CurProjectInfo curProject = null;
	/** 成本科目 */
	private CostAccountInfo costAccount = null;
	/** 产品类型 */
	private ProductTypeInfo productTypeInfo = null;
	/** 工程项目所对应的成本中心<组织> */
	private CostCenterOrgUnitInfo costCenterOrgUnit =null;
	
	/**
	 * @description		
	 * @author			黄远昌	
	 * @createDate		2011-7-14
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
			factory = AIMAimCostProductSplitEntryFactory.getLocalInstance(context);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	/**
	 * @description		
	 * @author			黄远昌			
	 * @createDate		2011-7-14
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#transmit(java.util.Hashtable, com.kingdee.bos.Context)					
	 */
	public CoreBaseInfo transmit(Hashtable hashtable, Context context) throws TaskExternalException {
		
		aimCostProductSplitEntryInfo = new AIMAimCostProductSplitEntryInfo();
		
		/**
		 * 获取Excel模板中的数据
		 */
		// 组织编码
		String costCenterOrgUnitNumber = ((String) ((DataToken) hashtable.get("orgUnin")).data).trim();
		// 工程项目编码
		String curProjectLongnumber = ((String) ((DataToken) hashtable.get("curPorjectNumber")).data).trim();
		// 工程项目名称
		String curProjectName = ((String) ((DataToken) hashtable.get("curProjectName")).data).trim();
		// 成本科目代码
		String costAccountNumber = ((String) ((DataToken) hashtable.get("costNumber")).data).trim();
		costAccountNumber = costAccountNumber.replace('!', '.');
		// 成本科目名称
		String costAccountName = ((String) ((DataToken) hashtable.get("costName")).data).trim();
		// 金额
		String amount = ((String) ((DataToken) hashtable.get("amount")).data).trim();
		// 选择
		String choice = ((String) ((DataToken) hashtable.get("isChoose")).data).trim();
		String isChoose = null;
		// 产品编码
		String fProductTypeLongnumber = ((String) ((DataToken) hashtable.get("productNumber")).data).trim();
		// 产品_目标
		String fProductTypeAim = ((String) ((DataToken) hashtable.get("productAim")).data).trim();
		// 拆分对象的目标成本
		BigDecimal aimCostAmount = null;
		
		/**
		 * 对获取到的Excel模板中的必录项目进行非空校验
		 */
		// 工程项目编码
		if (StringUtils.isEmpty(curProjectLongnumber)) {
			// throw new TaskExternalException("工程项目编码不能为空！");
			throw new TaskExternalException(getResource(context, "PrjLongNumberNotNull"));
		}
		// 成本科目代码
		if (StringUtils.isEmpty(costAccountNumber)) {
			// throw new TaskExternalException("成本科目代码不能为空！");
			throw new TaskExternalException(getResource(context, "AcctLongNumberNotNull"));
		}
		// 产品编码
		if (StringUtils.isEmpty(fProductTypeLongnumber)) {
			// throw new TaskExternalException("产品编码不能为空！");
			throw new TaskExternalException(getResource(context, "ProductTypeLongNumberNotNull"));
		}
		// 产品_目标
		if (StringUtils.isEmpty(fProductTypeAim)) {
			// throw new TaskExternalException("产品_目标不能为空！");
			throw new TaskExternalException(getResource(context, "ProductTypeAimNotNull"));
		}
		/**
		 * 对获取到的Excel模板中的数值项目进行非数值校验
		 */
		// 金额
		if (!StringUtils.isEmpty(amount)) {
			// 由于金额字段是非必录项，所以在该字段非空的前提下判断是否录入的为合法数字
			if (!amount.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// throw new TaskExternalException("金额录入不合法,应该录入数字型！");
				throw new TaskExternalException(getResource(context, "AmountNotNumber"));
			}
		}
		// 产品_目标
		if (!fProductTypeAim.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
			//throw new TaskExternalException("产品_目标录入不合法,应该录入数字型！");
			throw new TaskExternalException(getResource(context, "ProductTypeAimNotNumber"));
		}
		
		/**
		 * 对获取到的Excel模板中的编码项目进行系统中存在性校验
		 */
		try {
			// 工程项目编码
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("longnumber", curProjectLongnumber.replace('.', '!')));
			EntityViewInfo view1 = new EntityViewInfo();
			view1.setFilter(filter1);
			CurProjectCollection curProjectColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(view1);
			if (curProjectColl.size() > 0) {
				curProject = curProjectColl.get(0);
			} else {
				// throw new TaskExternalException("该工程项目编码在系统中不存在，不能导入！");
				// 导入编码在系统中不存在时，提示：××编码在系统中不存在！
				throw new TaskExternalException(curProjectLongnumber + getResource(context, "NumberNotFound"));
			}
			// 成本科目代码
			FilterInfo filter2 = new FilterInfo();
			filter2.getFilterItems().add(new FilterItemInfo("codingnumber", costAccountNumber, CompareType.EQUALS));
			filter2.getFilterItems().add(new FilterItemInfo("curproject", curProject.getId().toString(), CompareType.EQUALS));
			EntityViewInfo view2 = new EntityViewInfo();
			view2.setFilter(filter2);
			CostAccountCollection costAccountColl = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(view2);
			if (costAccountColl.size() > 0) {
				costAccount = costAccountColl.get(0);
			} else {
				// throw new TaskExternalException("该成本科目代码在系统中不存在，不能导入！");
				// 导入科目编码在系统中不存在时，提示：××科目编码在系统中不存在！
				throw new TaskExternalException(costAccountNumber + getResource(context, "AcctLongNumberNotFound"));
			}
			// 产品编码 fProductTypeLongnumber <TODO 现有产品对象，单需求文档要求为产品长编码，可是在产品对象中不存在长编码字段，有待确定。这里暂时以编码处理>
			FilterInfo filter3 = new FilterInfo();
			filter3.getFilterItems().add(new FilterItemInfo("number", fProductTypeLongnumber));
			EntityViewInfo view3 = new EntityViewInfo();
			view3.setFilter(filter3);
			ProductTypeCollection productTypeColl = ProductTypeFactory.getLocalInstance(context).getProductTypeCollection(view3);
			if (productTypeColl.size() > 0) {
				productTypeInfo = productTypeColl.get(0);
			} else {
				// throw new TaskExternalException("该产品编码在系统中不存在，不能导入！");
				throw new TaskExternalException(fProductTypeLongnumber + getResource(context, "ProductTypeLongNumberNotFound"));
			}
//			// 产品编码
//			AimProductTypeGetter getter = new AimProductTypeGetter(curProject.getId().toString(), ProjectStageEnum.AIMCOST);
//			Map prodcutMap = getter.getSortedProductMap();
//			Set set = prodcutMap.keySet();
//			for (Iterator pIter = set.iterator(); pIter.hasNext();) {
//				if (fProductTypeLongnumber.equals(pIter.next())) {
//					productTypeInfo = (ProductTypeInfo) prodcutMap.get(pIter.next());
//				}
//			}
//			if (productTypeInfo == null) {
//				throw new TaskExternalException(fProductTypeLongnumber + getResource(context, "ProductTypeLongNumberNotFound"));
//			}
			// 选择 choice
			if (choice.equals(getResource(context, "NotChoose"))) { 
				// 如果录入的为"否"，则设置为false
				isChoose = null;
			} else {
				// 如果录入的为"是"，则设置为true，或者录入为空时默认为ture，录入错误是也默认为true
				isChoose = "isChoose";
			}
			
			// 拆分目标成本头存在校验
			EntityViewInfo aimView = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			aimView.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("orgOrProId", curProject.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("isLastVersion", new Integer(1)));
			/********添加过滤：如果启用FDC_PARAM_AIMCOSTAUDIT参数，则加审批过滤条件	-by neo********/
			try {
				if (context != null){
					if(FDCUtils.getDefaultFDCParamByKey(context, 
							ContextUtil.getCurrentFIUnit(context).getId().toString(), FDCConstants.FDC_PARAM_AIMCOSTAUDIT)){
						filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
					}
				}else{
					if(FDCUtils.getDefaultFDCParamByKey(null, 
							SysContext.getSysContext().getCurrentFIUnit().getId().toString(), FDCConstants.FDC_PARAM_AIMCOSTAUDIT))
						filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			}
			aimView.getSelector().add("costEntry.*");
			aimView.getSelector().add("costEntry.costAccount.id");
			aimView.getSelector().add(new SelectorItemInfo("costEntry.product.id"));
			aimView.getSelector().add(new SelectorItemInfo("costEntry.product.name"));
			aimView.getSelector().add(new SelectorItemInfo("costEntry.product.number"));

			AimCostCollection aimCostCollection = AimCostFactory.getLocalInstance(context).getAimCostCollection(aimView);

			if (aimCostCollection.size() >= 1) {
				aimCostInfo = aimCostCollection.get(0);
			}  else {
				// throw new TaskExternalException("该拆分目标成本头不存在，不能导入！");
				throw new TaskExternalException(getResource(context, "AimCostNotFound"));
			}
			
			// 目标成本分录存在校验
			CostEntryCollection costEntrys = aimCostInfo.getCostEntry();
			CostEntryInfo costEntrytemp = null;
			if (costEntrys.size()>0) {
//				ProductTypeInfo productTypeTemp = null;
				CostAccountInfo costAccountTemp = null;
				for (int i = 0; i < costEntrys.size(); i++)
				{
					costEntrytemp = costEntrys.get(i);
//					productTypeTemp = costEntrytemp.getProduct();
					costAccountTemp = costEntrytemp.getCostAccount();
					if (costAccountTemp != null) {
						if (productTypeInfo.getId().toString().equals("") && costAccountTemp.getId().toString().equals(costAccount.getId().toString())) {
							costEntry = costEntrys.get(i);
							break;
						}
//					} else {
//						if (productTypeTemp == null && costAccountTemp != null) {
//							if (costAccountTemp.getId().toString().equals(costAccount.getId().toString())) {
//								costEntry = costEntrys.get(i);
//								break;
//							}
//						}
					}
				}
			}
			if (costEntry == null) {				
				// throw new TaskExternalException("该拆分目标成本分录不存在，不能导入！");
				throw new TaskExternalException(getResource(context, "CostEntryNotFound"));				
			}
			
			// 拆分对象的目标成本
			aimCostAmount = costEntry.getCostAmount();
			
			/**
			 * 将部分参数值存放到各产品目标成本<aimCostProductSplitEntryInfo>对象中
			 */
			aimCostProductSplitEntryInfo.setProduct(productTypeInfo);

			SelectorItemCollection appSelector=new SelectorItemCollection();
			appSelector.add("id");
			appSelector.add("targetType.id");
			ApportionTypeInfo apport = new ApportionTypeInfo();
			// 分摊类型：指定分摊
			apport.setId(BOSUuid.read(ApportionTypeInfo.appointType));
			apport = ApportionTypeFactory.getLocalInstance(context).getApportionTypeInfo(new ObjectUuidPK(apport.getId()),appSelector);
			aimCostProductSplitEntryInfo.setApportionType(apport);
			aimCostProductSplitEntryInfo.setSplitAmount(FDCNumberHelper.toBigDecimal(fProductTypeAim));
			aimCostProductSplitEntryInfo.setDirectAmount(FDCNumberHelper.toBigDecimal(fProductTypeAim));
			aimCostProductSplitEntryInfo.setCostEntryId(costEntry.getId().toString());
			aimCostProductSplitEntryInfo.setDescription(isChoose);	

		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		
		return aimCostProductSplitEntryInfo;
	}

	/**
	 * 得到资源文件
	 * @author 黄远昌
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
