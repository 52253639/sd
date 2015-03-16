package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.assistant.IMeasureUnit;
import com.kingdee.eas.basedata.assistant.MeasureUnitCollection;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.MeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.IProductType;
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
 * 目标成本测算导入导出
 */
public class MeasureCostTrans extends AbstractDataTransmission {
	
	private boolean isFirstRun = true;
	//科目编码
	private final String KEY_ACCT_NUMBER="FAcctNumber";
	//科目名称
	private final String KEY_ACCT_NAME="FAcctName";
	//条目名称
	private final String KEY_ENTRY_NAME="FEntryName";
	//原始指标名称
	private final String KEY_INDEX_NAME="FIndexName";
	//原始指标值
	private final String KEY_INDEX_VALUE="FIndexValue";
	//系数名称
	private final String KEY_COEFFICIENT_NAME="FCoefficientName";
	//系数值
	private final String KEY_COEFFICIENT="FCoefficient";
	//工作量
	private final String KEY_WORKLOAD="FWorkload";
	//单位
	private final String KEY_UNIT="FUnit";
	//单价
	private final String KEY_PRICE="FPrice";
	//调整前合价
	private final String KEY_COSTAMOUNT="FCostAmount";
	//调整系数
	private final String KEY_ADJUST_COEFFICIENT="FAdjustCoefficient";
	//调整金额
	private final String KEY_ADJUST_AMT="FAdjustAmt";
	//合价
	private final String KEY_AMOUNT="FAmount";
	//建筑单方
	private final String KEY_BUILDPART="FBuildPart";
	//可售单方
	private final String KEY_SELLPART="FSellPart";
	//合约规划
	private final String KEY_PROGRAM="FProgram";
	//备注
	private final String KEY_DESC="FDesc";
	//变化原因
	private final String KEY_CHANGE_REASON="FChangeReason";
	//产品
	private final String KEY_PRODUCT_NUMBER="FProductTypeNumber";

	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return MeasureCostFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		MeasureCostInfo info = (MeasureCostInfo)getContextParameter("editData");
		CurProjectInfo curProjectInfo =(CurProjectInfo)getContextParameter("project");
		
		// 分录
		MeasureEntryInfo entryInfo = null;
		MeasureEntryCollection entryCollection = info.getCostEntry();
		entryInfo = new MeasureEntryInfo();
		// 单头对象
		entryInfo.setHead(info);
		
		// 成本科目
		CostAccountInfo costAccountInfo = null;
		try {
			Object data = ((DataToken) hsData.get(KEY_ACCT_NUMBER)).data;
			if (data != null && data.toString().length() > 0) {
				String str = data.toString();
				if (str != null && str.length() > 0) {
					ICostAccount ica = CostAccountFactory.getLocalInstance(ctx);
					CostAccountCollection collection = ica.getCostAccountCollection(getLNFilter(str, info.getOrgUnit(), info.getProject()));
					if (collection != null && collection.size() > 0) {
						costAccountInfo = collection.get(0);
						if (costAccountInfo != null) {
							if (!costAccountInfo.isIsEnabled()) {
								// 成本科目已被禁用
								throw new TaskExternalException(getResource(ctx, "Import_CostAccount_DisEnabled"));
							} else if (!costAccountInfo.isIsLeaf()) {
								// 为了不破坏汇总关系,只能在明细科目下导入
								throw new TaskExternalException(getResource(ctx, "Import_CostAccount_IsNotLeaf"));
							}else if(costAccountInfo.getType()== CostAccountTypeEnum.MAIN){
								if(hsData.get(KEY_PRODUCT_NUMBER)!=null&&StringUtils.isEmpty(hsData.get(KEY_PRODUCT_NUMBER).toString())){
									throw new TaskExternalException("产品类型不能为空!");
								}
							}
							entryInfo.setCostAccount(costAccountInfo);
						} else {
							// 成本科目不存在
							throw new TaskExternalException(getResource(ctx, "Import_CostAccount_NotExist"));
						}
					}
				} else {
					// 成本科目没有指定编码
					throw new TaskExternalException(getResource(ctx, "Import_CostAccount_Null"));
				}
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}

		Object acctName = ((DataToken) hsData.get(KEY_ACCT_NAME)).data;
		if (acctName != null && acctName.toString().length() > 0) {
			if(acctName.toString().length()>80){
				throw new TaskExternalException("科目名称超长!");
			}
		}else{
			throw new TaskExternalException("科目名称不能为空!");
		}
		if (curProjectInfo != null) {// 只有对工程项目节点引入时需要产品类型
			// 归属产品(产品类型)
			ProductTypeInfo productTypeInfo = null;
			try {
				Object data = ((DataToken) hsData.get(KEY_PRODUCT_NUMBER)).data;
				if (data != null && data.toString().length() > 0) {
					String str = data.toString();
					if (str != null && str.length() > 0) {
						IProductType ipt = ProductTypeFactory.getLocalInstance(ctx);
						ProductTypeCollection collection = ipt.getProductTypeCollection(getNFilter(str));
						if (collection != null && collection.size() > 0) {
							productTypeInfo = collection.get(0);
							if (productTypeInfo != null) {
								if (!productTypeInfo.isIsEnabled()) {
									// 产品类型已被禁用
									throw new TaskExternalException(getResource(ctx, "Import_ProductType_DisEnabled"));
								} else {
									if (curProjectInfo.getCurProjProductEntries() != null) {
										CurProjProductEntriesCollection cppec = curProjectInfo.getCurProjProductEntries();
										boolean isAccObj = true;//分录中是否勾选了"是否核算对象"
										boolean flag = false;
										
										for(int i = 0 ;i<cppec.size();i++){											
											if(productTypeInfo.getId().toString().equals(cppec.get(i).getProductType().getId().toString())){
												if(cppec.get(i).isIsAccObj()){													
													flag = true;
													entryInfo.setProduct(productTypeInfo);
													break;
												}else{
													isAccObj = false;
													break;
												}
											}											
										}
										if(!isAccObj){//工程项目中该产品类型对应的分录为非核算对象,不能导入
											throw new TaskExternalException(getResource(ctx, "Import_ProductType_IsAccObj"));
										}else{
											if(!flag){//指定工程项目产品分录不包含导入指定产品类型,不能导入
												throw new TaskExternalException(getResource(ctx, "Import_CostAmount_ProjectHasNotProductType"));
											}
										}

									} else {
										// 指定工程项目没有定义任何产品类型,不能导入数据
									}

								}
							}
						} else {
							// 产品类型不存在
							throw new TaskExternalException("工程项目+"+curProjectInfo.getName()+"不存在此产品");
						}
					}
				} else if(entryInfo.getCostAccount().getType()==CostAccountTypeEnum.MAIN){
					// 产品类型没有指定
					throw new TaskExternalException(getResource(ctx, "Import_ProductType_Null"));
				}
			} catch (BOSException e) {
				throw new TaskExternalException(e.getMessage(), e.getCause());
			}
		}
		
		entryCollection.add(entryInfo);
		createEntry(ctx, entryInfo, hsData);

		return info;
	}

	private void createEntry(Context ctx, MeasureEntryInfo info, Hashtable hsData) throws TaskExternalException {
		MeasureCostInfo costInfo = (MeasureCostInfo)getContextParameter("editData");
		PlanIndexInfo indexInfo = (PlanIndexInfo)costInfo.get("PlanIndex");
		//条目名称
		if(hsData.get(KEY_ENTRY_NAME)!=null){
			String entryName = (String) ((DataToken) hsData.get(KEY_ENTRY_NAME)).data;
			info.setEntryName(entryName);
		}
		//原始指标
		if(hsData.get(KEY_INDEX_NAME)!=null){
			Object data = ((DataToken) hsData.get("FIndexName")).data;
			Item item = getItemByValue(data.toString().trim(),info.getCostAccount().getType());
			if(item==null){
				throw new TaskExternalException("原始指标为空或不正确!");
			}
			info.setIndexName(item.name);
			info.setSimpleName(item.key);
			
			//原始指标值
			if(info.getProduct()!=null&&indexInfo.getEntrys()!=null){
				boolean isProduct = false;
				for(int i=0;i<indexInfo.getEntrys().size();i++){
					if(isProduct){
						continue;
					}
					PlanIndexEntryInfo indexEntry = indexInfo.getEntrys().get(i);
					if(indexEntry.getProduct().getId().toString().equals(info.getProduct().getId().toString())){
						info.setIndexValue(indexEntry.getBigDecimal(item.key));
						isProduct=true;
					}
				}
			}else{
				info.setIndexValue(indexInfo.getBigDecimal(item.key));
			}
		}else{
			throw new TaskExternalException("原始指标为空或不正确!");
		}
		
		//系数
		if(hsData.get(KEY_COEFFICIENT_NAME)!=null){
			String coefficientName = (String) ((DataToken) hsData.get(KEY_COEFFICIENT_NAME)).data;
			if(coefficientName.length()>80){
				throw new TaskExternalException(coefficientName+" 系数名称超过长度!");
			}
			info.setCoefficientName(coefficientName);
		}
		
		//系数值
		if(hsData.get(KEY_COEFFICIENT)!=null){
			info.setCoefficient(getBigDecimal(ctx, hsData, KEY_COEFFICIENT, false));
		}
		
		//工作量
		info.setWorkload(getBigDecimal(ctx, hsData, KEY_WORKLOAD, false));
		
		//单价
		info.setPrice(getBigDecimal(ctx, hsData, KEY_PRICE, false));
		
		//计量单位
		try {
			Object data = ((DataToken) hsData.get("FUnit")).data;
			if (data != null && data.toString().length() > 0) {
				String str = data.toString();
				if (str != null && str.length() > 0) {
					IMeasureUnit imu = MeasureUnitFactory.getLocalInstance(ctx);
					MeasureUnitCollection collection = imu.getMeasureUnitCollection(getMUFilter(str));
					
					if (collection != null && collection.size() > 0) {
						MeasureUnitInfo measureUnitInfo = collection.get(0);
						if (measureUnitInfo != null) {
							if (measureUnitInfo.isIsDisabled()) {
								throw new TaskExternalException(getResource(ctx, "计量单位  "+measureUnitInfo.getName()+"已被禁用！"));
							} else {
								info.setUnit(measureUnitInfo);
							}
						}
					} else {
						//不存在
						throw new TaskExternalException("计量单位 "+str+" 不存在");
					}
				}
			} else {
				//没有指定
			}
			
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		
		//目标成本金额(调整前合价)
		info.setCostAmount(getBigDecimal(ctx, hsData, KEY_COSTAMOUNT,true));
		
		//与编辑界面保持相同处理逻辑
		BigDecimal price = info.getPrice();
		BigDecimal workload = info.getWorkload();
		if (price == null) {
			price = FDCHelper.ZERO;
		}
		if (workload == null) {
			workload = FDCHelper.ZERO;
		}

		if (price.compareTo(FDCHelper.ZERO) == 0
				&& workload.compareTo(FDCHelper.ZERO) == 0) {
			info.setPrice(null);
			info.setWorkload(null);
		} else {

			BigDecimal sumPrice = price.multiply(workload).setScale(2,
					BigDecimal.ROUND_HALF_UP);
			info.setCostAmount(sumPrice);
		}
		
		//调整系
		info.setAdjustCoefficient(getBigDecimal(ctx, hsData, KEY_ADJUST_COEFFICIENT, false));

		//调整值
		info.setAdjustAmt(getBigDecimal(ctx, hsData, KEY_ADJUST_AMT, false));
		
		//合价
		info.setAmount(getBigDecimal(ctx, hsData, KEY_AMOUNT, true));
		
		//建筑单方
		//可售单方
		//合约规划
		if(hsData.get(KEY_PROGRAM)!=null){
			String program = (String) ((DataToken) hsData.get(KEY_PROGRAM)).data;
			if(program.length()>255){
				throw new TaskExternalException("合约规划超长!");
			}
			info.setProgram(program);
		}
		//备注
		if(hsData.get(KEY_DESC)!=null){
			String desc = (String) ((DataToken) hsData.get(KEY_DESC)).data;
			if(desc.length()>1200){
				throw new TaskExternalException("备注超长!");
			}
			info.setDesc(desc);
		}
		//变化原因
		if(hsData.get(KEY_CHANGE_REASON)!=null){
			String changeReason = (String) ((DataToken) hsData.get("FChangeReason")).data;
			if(changeReason.length()>1200){
				throw new TaskExternalException("变化原因超长!");
			}
			info.setChangeReason(changeReason);
		}
		
		/*//工作量\单价\目标成本金额控制逻辑
		//1		如果工作量、单价、目标成本都有数据，且满足 工作量×单价＝目标成本 的等式，则导入三者均导入。
		//2、   如果工作量、单价、目标成本都有数据，但不满足 工作量×单价＝目标成本 的等式，则导入目标成本，工作量、单价不导入。
		//3、   如果只有目标成本有数据，则导入目标成本。
		//4、   如果工作量、单价都有数据或有一个没有数据，但目标成本没有数据，系统给提示，目标成本不允许为空。该数据不能导入。
		//5、   如果工作量、单价有一个没有数据，目标成本有数据，则导入目标成本，工作量、单价不导入。

		if(info.getWorkload()!=null){
			if(info.getPrice()!=null){
				if(info.getCostAmount()!=null){
					if(round(info.getWorkload().multiply(info.getPrice()),2).compareTo(info.getCostAmount())==0){//相等
						//符合1
					}else{//不等
						info.setPrice(null);
						info.setWorkload(null);
						//符合2
					}
				}
			}else{
				//符合5
				info.setWorkload(null);
			}
		}else{
			if(info.getPrice()!=null){
				//符合5
				info.setPrice(null);
			}else{
				//符合3
			}
		}*/
		
	}

	private BigDecimal getBigDecimal(Context ctx, Hashtable hsData, String key, boolean isRequered) throws TaskExternalException {
		BigDecimal value;
		try {
			Object o = ((DataToken) hsData.get(key)).data;
			if (o != null && o.toString().trim().length() > 0) {
				value = new BigDecimal(o.toString());
				if (value != null) {
					return value;
				}
			}else {
				// 必录项,不能为空
				if(isRequered){
					throw new TaskExternalException(getResource(ctx, "Import_CostAmount_Null"));
				}
			}
		} catch (NumberFormatException nex) {
			// 格式错误，只能是数字
			throw new TaskExternalException(getResource(ctx, "Import_CostAmount_PriceFormatError"));
		}
		return null;
	}
	
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx)
			throws TaskExternalException {
		//TODO 设置数据
		
		
		super.submit(coreBaseInfo, ctx);
	}
	
	
	private static String resource = "com.kingdee.eas.fdc.costdb.CostDBResource";
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}	
	/**   
     *   提供精确的小数位四舍五入处理。   
     *   @param   v   需要四舍五入的数字   
     *   @param   scale   小数点后保留几位   
     *   @return   四舍五入后的结果   
     */   
    public static BigDecimal round(BigDecimal   v,int   scale){   
           if(scale<0){   
                   throw   new   IllegalArgumentException(   
                           "The   scale   must   be   a   positive   integer   or   zero");   
           }   
           BigDecimal   b   =   v;
           BigDecimal   one   =   new   BigDecimal("1");   

           return   b.divide(one,scale,BigDecimal.ROUND_HALF_UP);  
    }
    // 计量单位EntityViewInfo
	private EntityViewInfo getATFilter(String number) {
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", number, CompareType.EQUALS));
		viewInfo.setFilter(filter);
		return viewInfo;
	}
    // 计量单位EntityViewInfo
	private EntityViewInfo getMUFilter(String name) {
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", name, CompareType.EQUALS));
		viewInfo.setFilter(filter);
		return viewInfo;
	}
   
    // 获取编码类型的EntityViewInfo
    private EntityViewInfo getNFilter(String name) {

		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", name, CompareType.EQUALS));
		viewInfo.setFilter(filter);
		return viewInfo;
	}
   
    // 获取长编码类型的EntityViewInfo
    private EntityViewInfo getLNFilter(String longNumber, FullOrgUnitInfo fullOrgUnitInfo, CurProjectInfo curProjectInfo) {
	   
	   EntityViewInfo viewInfo = new EntityViewInfo();
	   FilterInfo filter = new FilterInfo();
	   filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber.replace('.', '!'), CompareType.EQUALS));
	   if (curProjectInfo != null) {
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectInfo.getId().toString(), CompareType.EQUALS));
	   }
	   viewInfo.setFilter(filter);
	   return viewInfo;
	}
    
    public Item getItemByValue(String value,CostAccountTypeEnum enumType){
    	if(CostAccountTypeEnum.MAIN==enumType){
    		for(int i=0;i<Item.PRODUCTITEMS.length;i++){
    			Item item = Item.PRODUCTITEMS[i];
    			if(value.equals(item.name)){
    				return item;
    			}
    		}
    	}else{
    		for(int i=0;i<Item.SIXITEMS.length;i++){
    			Item item = Item.SIXITEMS[i];
    			if(value.equals(item.name)){
    				return item;
    			}
    		}
    	}
    	
    	return null;
    }
    public static class Item{
		String key=null;
		String name=null;
		String productId=null;
		BigDecimal sellArea=null;
		boolean isCustom=false;
		Item(String key,String name) {
			this.key=key;
			this.name=name;
		}
		private Item(ApportionTypeInfo info) {
			this.key=info.getId().toString();
			this.name=info.getName();
			isCustom=true;
		}
		private static Map hashMap=null;
		public static Item getCustomItem(ApportionTypeInfo info){
			if(hashMap==null){
				hashMap=new HashMap();
			}
			Item item=(Item)hashMap.get(info.getId());
			if(item==null){
				item=new Item(info);
			}
			hashMap.put(info.getId(), item);
			return item;
			
		}
		public boolean isCustomIndex(){
			return isCustom;
		}
		public String toString() {
			return name;
		}
		
		public int hashCode() {
			return super.hashCode();
		}
		
		/**
		 * 六类公摊使用指标
		 * TODO 后续需要优化
		 */
		public static Item [] SIXITEMS=new Item[]{
			new Item("empty",	""),	
			new Item("totalContainArea",	"总占地面积"),	
			new Item("buildArea",	"建筑用地面积"),	
			new Item("totalBuildArea",	"总建筑面积"),	
			new Item("buildContainArea",	"建筑物占地面积"),
//			new Item("buildDensity",	"建筑密度	"),
//			new Item("greenAreaRate",	"绿地率"),	
			new Item("cubageRateArea",	"计容积率面积"),	
//			new Item("cubageRate",	"容积率"),	
			new Item("totalRoadArea",	"道路用地合计"),	
			new Item("totalGreenArea",	"绿化用地合计	"),
			new Item("pitchRoad",	"沥青路面车行道"),
			new Item("concreteRoad",	"砼路面车行道（停车场）"),	
			new Item("hardRoad",	"硬质铺装车行道"),
			new Item("hardSquare",	"硬质铺装广场	"),
			new Item("hardManRoad",	"硬质铺装人行道"),
			new Item("importPubGreenArea",	"重要公共绿地	"),
			new Item("houseGreenArea",	"组团宅间绿化	"),
			new Item("privateGarden",	"底层私家花园	"),
			new Item("warterViewArea",	"水景面积"),
			new Item("viewArea",	"景观面积")//,
//			new Item("doors",	"户数")
			
		};
		
		/**
		 * 产品使用指标
		 */
		public static Item [] PRODUCTITEMS=new Item[]{
				new Item("empty",	""),
				new Item("containArea",	"占地面积"),	
				new Item("buildArea",	"建筑面积"),	
				new Item("sellArea",	"可售面积"),	
				new Item("cubageRate",	"容积率"),	
				new Item("productRate",	"产品比例"),	
				new Item("unitArea",	"平均每户面积"),	
				new Item("units",	"单元数"),	
				new Item("doors",	"户数"),
		};
		
		/**
		 * 汇总表产品分摊使用指标
		 */
		public static Item [] SPLITITEMS = new Item[] { 
				new Item("man", "指定分摊"), 
				new Item("buildArea", "建筑面积"), 
				new Item("sellArea", "可售面积"),
				new Item("containArea", "占地面积"),
				new Item("cubageRate", "容积率"), 
				new Item("productRate", "产品比例"), 
				new Item("unitArea", "平均每户面积"), 
				new Item("units", "单元数"),
				new Item("doors", "户数	"),

		};

	}
	
}
