package com.kingdee.eas.fdc.aimcost.app;

import java.util.Hashtable;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryFactory;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.IntendingCostEntryInfo;
import com.kingdee.eas.fdc.basedata.AdjustReasonFactory;
import com.kingdee.eas.fdc.basedata.AdjustReasonInfo;
import com.kingdee.eas.fdc.basedata.AdjustTypeFactory;
import com.kingdee.eas.fdc.basedata.AdjustTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.util.StringUtils;

public class DynamicCostImport extends AbstractDataTransmission{
	
	private static String resource = "com.kingdee.eas.fdc.aimcost.AimCostResource";
	
	private static final Logger logger = CoreUIObject.getLogger(DynamicCostImport.class);

	DynamicCostInfo info = null;
	//DynamicCostInfo(待发生成本)-> CostAccountInfo(科目)-> CurProjectInfo(工程) 
	CostAccountInfo maininfo = null;
	CurProjectInfo subinfo = null;
	
	//DynamicCostInfo-> adjustEntrys(调整信息分录)
	AdjustRecordEntryInfo areInfo =  null;
	
	//DynamicCostInfo-> IntendingCostEntryInfo(待发生成本分录)
	IntendingCostEntryInfo iceInfo = new IntendingCostEntryInfo();
	
	// 校验
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
	throws TaskExternalException {
		int seq = 0;
		   
		try {
			info = transmitHead(hsData, ctx);
			if (info == null) {
				return null;
			}
			// (调整信息分录)
			AdjustRecordEntryInfo areEntry = transmitadjustEntrys(hsData, ctx,info );
			seq = info.getAdjustEntrys().size() + 1;
			areEntry.setSeq(seq);
			areEntry.setParent(info);
			info.getAdjustEntrys().add(areEntry);
		} catch (TaskExternalException e) {
			info = null;
			throw e;
		}
		
		return info;
	}
	
	
	
	//提交的时候 查找数据库中是否存在相同的数据，存在就更新数据库 ，不存在就 插入
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		if (coreBaseInfo == null || coreBaseInfo instanceof DynamicCostInfo == false) {
			return;
		}
		
		try {  
	        DynamicCostInfo billBase = (DynamicCostInfo) coreBaseInfo;
			CostAccountInfo costInfo = billBase.getAccount();//拿DynamicCost表中成本科目的id
			String idName = costInfo.getId().toString();
	        //查询DynamicCost表 确认是否有  上述成本科目
			DynamicCostInfo syCostin = DynamicCostFactory.getLocalInstance(ctx).getDynamicCostCollection(
					this.getView("account", idName)).get(0);
			
			String id = "";
			AdjustRecordEntryInfo  adReEntry = null;
			if (syCostin != null) {
				id = syCostin.getId().toString();
				adReEntry = billBase.getAdjustEntrys().get(0);//只有一条记录
			}
			
			if (StringUtil.isEmptyString(id)) {
				getController(ctx).addnew(coreBaseInfo);
			} else {
				adReEntry.setParent(syCostin);
				adReEntry.setSeq(syCostin.getAdjustEntrys().size()+1);
				AdjustRecordEntryFactory.getLocalInstance(ctx).addnew(adReEntry);
			}

		} catch (Exception ex) {
			throw new TaskExternalException(ex.getMessage(), ex.getCause());
		}

	}
	
	
	
	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		
		ICoreBase factory = null;
		try {
			factory = DynamicCostFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	//DynamicCostInfo-> CostAccountInfo(科目)-> CurProjectInfo(工程) 
	private DynamicCostInfo transmitHead(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		
		info = new DynamicCostInfo();
		//DynamicCostInfo-> CostAccountInfo(科目)-> CurProjectInfo(工程) 
		maininfo = new CostAccountInfo();
		subinfo = new CurProjectInfo();

		//取值
		String costOrg_number = FDCTransmissionHelper.getFieldValue(hsData, "FAccount$curProject$costOrg_number");//组织编码
		String curProject_longNumber = FDCTransmissionHelper.getFieldValue(hsData, "FAccount$curProject_longNumber");//*工程编码
		String curProject_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FAccount$curProject_name_l2");//工程项目名称
		String FAccount_number = FDCTransmissionHelper.getFieldValue(hsData, "FAccount_number");//*科目编码
		FAccount_number = FAccount_number.replace('!', '.');
		String FAccount_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FAccount_name_l2");//成名科目名称
		//验证格式是否正确  是否为空   长度是否超出
		FDCTransmissionHelper.valueFormat("组织编码", costOrg_number, "String", false, 80);
		FDCTransmissionHelper.valueFormat("工程编码", curProject_longNumber, "String", true, 80);
		FDCTransmissionHelper.valueFormat("工程项目名称", curProject_name_l2, "String", false, 200);
		FDCTransmissionHelper.valueFormat("科目编码", FAccount_number, "String", true, 80);
		FDCTransmissionHelper.valueFormat("成本科目名称", FAccount_name_l2, "String", false, 200);
		//数据库校验  
		//DynamicCostInfo-> CostAccountInfo(科目)-> CurProjectInfo(工程) 
		FullOrgUnitInfo finfo = null;//组织
		CurProjectInfo cpinfo = null;//工程项目
		CostAccountInfo cainfo = null;//科目
		CostCenterOrgUnitInfo ojkk = null;//成本中心
		
		try {
			//工程项目
			cpinfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(this.getView("longnumber", curProject_longNumber.replace('.', '!'))).get(0);
			if (cpinfo == null) { // 工程项目在系统中不存在
				FDCTransmissionHelper.isThrow(getResource(ctx, "CurProjectNumberNotFound"), "");
			}
			String id = cpinfo.getCostCenter().getId().toString();
			ojkk = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(this.getView("id", id)).get(0);
			String costCenterName = ojkk.getLongNumber();//成本中心长编码
			
			//成本中心名字   为了取出组织编码  取工程项目长编码对应成本中心名字
			if(costOrg_number.trim().equals("") || costOrg_number==null){//为空的时候
				finfo = cpinfo.getCostCenter().castToFullOrgUnitInfo();//强转工程对象-》组织编码
			}else{//不为空的时候
				if (!costOrg_number.replace('.', '!').equals(costCenterName)) {// 填写有误
					// 工程项目所对应的成本中心不存在！
					FDCTransmissionHelper.isThrow(getResource(ctx, "FullOrgUnitNotFound"), "");
				}else{//没有填写错误
					finfo = cpinfo.getCostCenter().castToFullOrgUnitInfo();//强转工程对象-》组织编码
				}
			}
			
			// 科目
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("codingnumber", FAccount_number, CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("curproject", cpinfo.getId().toString(), CompareType.EQUALS));
			view.setFilter(filter);// 拿到工程中的科目
			cainfo = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view).get(0);
			if (cainfo == null) {// 成本科目编码在系统中不存在！
				FDCTransmissionHelper.isThrow(getResource(ctx, "kmbmzxtzbcz"), "");
			}
		
			
			//设置值
			//DynamicCostInfo-> CostAccountInfo(科目)-> CurProjectInfo(工程) 
			subinfo.setFullOrgUnit(finfo);//将成本中心放入 工程项目
			subinfo.setNumber(cpinfo.getLongNumber());//工程项目编码
			subinfo.setName(cpinfo.getName());//工程项目名称
			//将组织编码  工程项目编码 工程项目名称  设置到 成本对象CostAccountInfo中
			maininfo.setCurProject(subinfo);
			maininfo.setNumber(cainfo.getNumber());//成本科目编码
			maininfo.setName(cainfo.getName());//成本科目名称
			maininfo.setId(cainfo.getId());
			//将成本科目 组织编码  工程项目编码 工程项目名称  设置到待发生成本实体 DynamicCostInfo中
			info.setAccount(maininfo);
			
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return info;
	}
	
    //adjustEntrys(调整信息分录)
	private AdjustRecordEntryInfo transmitadjustEntrys(Hashtable hsData, Context ctx ,DynamicCostInfo dcMainInfo)
	throws TaskExternalException {
		//DynamicCostInfo-> adjustEntrys(调整信息分录)
		areInfo = new AdjustRecordEntryInfo();
		
		//取值
		String costAmount = FDCTransmissionHelper.getFieldValue(hsData, "FAdjustEntrys_costAmount");//*调整金额
		String product_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FAdjustEntrys$product_name_l2");//归属产品
		String adjustType_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FAdjustEntrys$adjustType_name_l2");//调整类型
		String adjustReason_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FAdjustEntrys$adjustReason_name_l2");//调整原因
		String adjuster_number = FDCTransmissionHelper.getFieldValue(hsData, "FAdjustEntrys$adjuster_number");//*调整人编码
		String adjustDate = FDCTransmissionHelper.getFieldValue(hsData, "FAdjustEntrys_adjustDate");//*调整时间
		String description = FDCTransmissionHelper.getFieldValue(hsData, "FAdjustEntrys_description");//说明
		String workload = FDCTransmissionHelper.getFieldValue(hsData, "FIntendingCostEntrys_workload");// 工作量
		String unit = FDCTransmissionHelper.getFieldValue(hsData, "FIntendingCostEntrys_unit");// 单位
		String price = FDCTransmissionHelper.getFieldValue(hsData, "FIntendingCostEntrys_price");// 单价
		
		//格式校验   
		FDCTransmissionHelper.bdValueFormat("调整金额", costAmount,  true, 13,2);
		FDCTransmissionHelper.valueFormat("归属产品", product_name_l2, "String", false, 80);
		FDCTransmissionHelper.valueFormat("调整类型", adjustType_name_l2, "String", false, 80);
		FDCTransmissionHelper.valueFormat("调整原因", adjustReason_name_l2, "String", false, 200);
		FDCTransmissionHelper.valueFormat("调整人编码", adjuster_number, "String", true, 80);
		FDCTransmissionHelper.valueFormat("调整时间", adjustDate, "Date", true, 80);
		FDCTransmissionHelper.valueFormat("说明", description, "String", false, 200);
		FDCTransmissionHelper.valueFormat("工作量", workload, "Double", false, -1);
		// 单位在需求上写的是非比录项，又需要判断空
		FDCTransmissionHelper.valueFormat("单位", unit, "String", false, 200);
		this.bdValueFormat("单价", price, false, 13, 4);
		
		//数据库校验//DynamicCostInfo-> adjustEntrys(调整信息分录)
		ProductTypeInfo prinfo = null;// 归属产品
		AdjustTypeInfo atinfo = null;//调整类型
		AdjustReasonInfo arinfo = null;//调整原因
		UserInfo userinfo = null;//调整人
		MeasureUnitInfo muinfo = null;// 单位

		try {
			//归属产品
			prinfo = ProductTypeFactory.getLocalInstance(ctx).getProductTypeCollection(this.getView("name", product_name_l2)).get(0);
		    
			if(!StringUtils.isEmpty(adjustType_name_l2))
			{//调整类型
				atinfo = AdjustTypeFactory.getLocalInstance(ctx).getAdjustTypeCollection(this.getView("name", adjustType_name_l2)).get(0);
				if(atinfo==null){
					// 调整类型在系统中不存在
					FDCTransmissionHelper.isThrow(getResource(ctx, "tzlxzxtzbcz"), "");
				}else
				{
					areInfo.setAdjustType(atinfo);//调整类型 FAdjustEntrys$adjustType_name_l2
				}
			}
			
			//调整原因
			arinfo = AdjustReasonFactory.getLocalInstance(ctx).getAdjustReasonCollection(this.getView("name", adjustReason_name_l2)).get(0);
			
			//调整人
			userinfo = UserFactory.getLocalInstance(ctx).getUserCollection("where number='"+adjuster_number+"'").get(0);
			if (userinfo == null) {// 调整人在系统中不存在
				FDCTransmissionHelper.isThrow(getResource(ctx, "tzrzxtzbcuz"), "");
			}
			
			if (!StringUtils.isEmpty(unit.trim())) {
				// 从计量单位中拿到 单位信息
				muinfo = MeasureUnitFactory.getLocalInstance(ctx).getMeasureUnitCollection(this.getView("name", unit)).get(0);
				if (muinfo == null) {// 单位在系统中不存在
					FDCTransmissionHelper.isThrow(getResource(ctx, "MeasureUnitNotFound"), "");
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		//待发生成本对象
		CostAccountInfo costAccObj =  dcMainInfo.getAccount();//拿到成本信息
		String costAccName = "";
		if(costAccObj!=null){
			 costAccName = costAccObj.getName();//成本科目名称
		}
		//数据库中有，但是导入的表格中没有的字段
		areInfo.setAdjustAcctName(costAccName);
		areInfo.setAdjusterName(userinfo.getName());
		
		
		//DynamicCostInfo-> AdjustRecordEntryInfo(调整信息分录)
		areInfo.setCostAmount(FDCTransmissionHelper.strToBigDecimal(costAmount));//调整金额
		areInfo.setProduct(prinfo);//归属产品
		areInfo.setAdjustReason(arinfo);//调整原因FAdjustEntrys$adjustReason_name_l2
		areInfo.setAdjuster(userinfo);//*调整人编码FAdjustEntrys$adjuster_number
		areInfo.setAdjustDate(FDCTransmissionHelper.strToDate(adjustDate));//*调整时间FAdjustEntrys_adjustDate
		areInfo.setDescription(description);//说明FAdjustEntrys_description
		areInfo.setWorkload(FDCTransmissionHelper.strToBigDecimal(workload));// 工作量
		areInfo.setUnit(unit);// 设置单位
		areInfo.setPrice(FDCTransmissionHelper.strToBigDecimal(price));// 单价

		return areInfo;
	}
	

	//返回视图  
	private EntityViewInfo getView(String sqlcolnum,Object item){
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(sqlcolnum,item,CompareType.EQUALS));
        view.setFilter(filter);
		return view;	
	}
	/**
	 * 得到资源文件
	 * @author 朱俊
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
	
	/**
	 * 判断 数字型的数据是否合法  必填项是否为空、 数据的长度是不是过长   的方法  
	 * name 中文名字 value 值  b是否必填 iv整数位 fv小数位
	 */
	public  void bdValueFormat(String name,String value,boolean b,int iv,int fv) throws TaskExternalException{
		if(null != value && !"".equals(value.trim()) ){
			if(!value.matches("(-{0,1})([1-9]\\d{0,"+(iv-1)+"}(.)\\d{0,"+fv+"})|(0(.)\\d{0,"+fv+"})||(--{0,1})([1-9]\\d{0,"+(iv-1)+"})")){
				FDCTransmissionHelper.isThrow(name,"必须以 1－"+iv+"位整数或加 1－"+fv+"位小数构成！" );
    		}
		}else{
			if(b){//为空的情况  但是是必填的字段
				FDCTransmissionHelper.isThrow(name,"不能为空！");	
			}
		}
	}
}
