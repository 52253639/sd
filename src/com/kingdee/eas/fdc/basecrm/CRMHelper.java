package com.kingdee.eas.fdc.basecrm;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.service.formula.engine.FormulaEngine;
import com.kingdee.bos.service.formula.engine.RunFormulaException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.EnactmentServiceProxy;
import com.kingdee.bos.workflow.service.IWfDefineService;
import com.kingdee.bos.workflow.service.WfDefineService;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.CalculateTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeManageInfo;
import com.kingdee.eas.fdc.sellhouse.CollectionInfo;
import com.kingdee.eas.fdc.sellhouse.FactorEnum;
import com.kingdee.eas.fdc.sellhouse.HoldEnum;
import com.kingdee.eas.fdc.sellhouse.ITranCustomerEntry;
import com.kingdee.eas.fdc.sellhouse.ITranPayListEntry;
import com.kingdee.eas.fdc.sellhouse.IntegerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.OperateEnum;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerLinkManCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerLinkManInfo;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.IPropertyContainer;
import com.kingdee.util.NumericExceptionSubItem;

public class CRMHelper {
	/**
	 * 对bigDecimal作排空处理
	 * */
	public static BigDecimal getBigDecimal(BigDecimal big){
		return big == null ? FDCHelper.ZERO : big;		
	}
	
	/**
	 * 对集合按某字段进行排序,该字段的值需要是Comparable类型的.
	 * @param cols 要排序的集合
	 * @param sortColName 要排序的字段
	 * @param sortType 是否正序
	 * */
	public static void sortCollection(IObjectCollection cols, final String sortColName, final boolean sortType) {
		Object[] toSortData = cols.toArray();
		
		Arrays.sort(toSortData, new Comparator(){
			public int compare(Object arg0, Object arg1) {
				IObjectValue obj0 = (IObjectValue)arg0;
				IObjectValue obj1 = (IObjectValue)arg1;
				if(obj0 == null  ||  obj1 == null){
					return 0;
				}
				
				Comparable tmp0 = (Comparable)getValue(obj0,sortColName);
				Comparable tmp1 = (Comparable)getValue(obj1,sortColName);
				if(tmp0 == null  ||  tmp1 == null){
					return 0;
				}
				
				return sortType ? tmp0.compareTo(tmp1) : -tmp0.compareTo(tmp1);
			}
		});
		
		cols.clear();
		for(int j=0; j<toSortData.length; j++){
			cols.addObject((IObjectValue) toSortData[j]);
		}
	}
	/**
	 * 对集合按多字段进行排序,该字段的值需要是Comparable类型的.
	 * @param cols 要排序的集合
	 * @param sortColName 要排序的字段
	 * @param sortType 是否正序
	 * */
	public static void sortCollection(IObjectCollection cols, final String[] sortColNames, final boolean sortType) {
		Object[] toSortData = cols.toArray();
		
		Arrays.sort(toSortData, new Comparator(){
			public int compare(Object arg0, Object arg1) {
				IObjectValue obj0 = (IObjectValue)arg0;
				IObjectValue obj1 = (IObjectValue)arg1;
				if(obj0 == null  ||  obj1 == null){
					return 0;
				}
				for(int i=0; i<sortColNames.length; i++){
					Comparable tmp0 = (Comparable)getValue(obj0,sortColNames[i]);
					Comparable tmp1 = (Comparable)getValue(obj1,sortColNames[i]);
					if(tmp0 != null  &&  tmp1 != null){
						if(tmp0.compareTo(tmp1) == 0){
							continue;
						}else{
							return sortType ? tmp0.compareTo(tmp1) : -tmp0.compareTo(tmp1);
						}
					}
					if(tmp0 == null){
						return sortType ? -1 : 1;
					}
					if(tmp1 == null){
						return sortType ? 1 : -1;
					}
				}
				return 0;
			}
		});
		
		cols.clear();
		for(int j=0; j<toSortData.length; j++){
			cols.addObject((IObjectValue) toSortData[j]);
		}
	}
	
	public static void sortCollection(Object[] toSortData, final String sortColName, final boolean sortType) {
		if(toSortData==null || toSortData.length==0) return;
		Arrays.sort(toSortData, new Comparator(){
			public int compare(Object arg0, Object arg1) {
				IObjectValue obj0 = (IObjectValue)arg0;
				IObjectValue obj1 = (IObjectValue)arg1;
				if(obj0 == null  ||  obj1 == null){
					return 0;
				}
				
				Comparable tmp0 = (Comparable)getValue(obj0,sortColName);
				Comparable tmp1 = (Comparable)getValue(obj1,sortColName);
				if(tmp0 == null  ||  tmp1 == null){
					return 0;
				}
				
				return sortType ? tmp0.compareTo(tmp1) : -tmp0.compareTo(tmp1);
			}
		});
	}
	
	
	/**
	 * 获取某对象的属性值，支持及联获取.key可以是 room.number 这种格式
	 * */
	public static Object getValue(IObjectValue value, String key){
		int in = key.indexOf(".");
		if(in == -1){
			return value.get(key);
		}else{
			Object tmp = value.get(key.substring(0, in));
			if(tmp != null  &&  tmp instanceof IObjectValue){
				return getValue((IObjectValue) tmp, key.substring(in + 1, key.length()));
			}
		}
		return null;
	}
	
	/**
	 * 判断big是否是空或者0
	 * */
	public static boolean isZeroOrNull(BigDecimal big){
		if(big == null){
			return true;
		}
		return big.compareTo(FDCHelper.ZERO) == 0;
	}

	
	/**
	 * 描述：获取后台日期
	 */
	public static Timestamp getServerDate() throws Exception {
			return FDCSQLFacadeFactory.getRemoteInstance().getServerTime();
	}

	public static Date getServerDate2() {
		try {
			Timestamp serTime = getServerDate();
			return new Date(serTime.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Timestamp getServerDate(Context ctx) throws Exception {
		return FDCSQLFacadeFactory.getRemoteInstance().getServerTime();
	}
	
	public static Date getServerDate2(Context ctx) {
		try {
			Timestamp serTime = getServerDate(ctx);
			return new Date(serTime.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	

    /**
     * 描述：判断单据是否在工作流中
     * 创建时间：2006-12-27 <p>
     */
    public static boolean isRunningWorkflow(Context ctx, String objId) throws BOSException {
        boolean hasWorkflow = false;
        IWfDefineService service = WfDefineService.getService(ctx);
        String procDefID = service.findSubmitProcDef((BOSUuid.read(objId)).getType(), ContextUtil.getCurrentUserInfo(ctx).getId().toString());
        if (procDefID != null) {
            IEnactmentService service2 = EnactmentServiceProxy.getEnacementService(ctx);
            ProcessInstInfo[] procInsts = service2.getProcessInstanceByHoldedObjectId(objId);
            for (int i = 0, n = procInsts.length; i < n; i++) {
                if ("open.running".equals(procInsts[i].getState())) {
                    hasWorkflow = true;
                    break;
                }
            }
        }
        return hasWorkflow;
    }
    
    /**
     * 描述：判断单据是否在工作流中 client
     * 创建时间：2009-1-8 <p>
     */
    public static boolean isRunningWorkflow(String objId) throws BOSException {
        boolean hasWorkflow = false;
        IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
        ProcessInstInfo[] procInsts = service2.getProcessInstanceByHoldedObjectId(objId);
        for (int i = 0, n = procInsts.length; i < n; i++) {
            if ("open.running".equals(procInsts[i].getState())) {
                hasWorkflow = true;
                break;
            }
        }
        return hasWorkflow;
    }
    
    /**
     * 终止某条单据的工作流
     * @param bizObjId
     * @throws BOSException 
     */
    public static void abortProcessWorkflow(Context ctx,String bizObjId) throws BOSException {
		IEnactmentService service = EnactmentServiceFactory.createEnactService(ctx);
		ProcessInstInfo[] procInsts = service.getProcessInstanceByHoldedObjectId(bizObjId);

		for (int j = 0; j < procInsts.length; j++) {
			if ("open.running".equals(procInsts[j].getState())) {
				ProcessInstInfo instInfo = procInsts[j];
				service.abortProcessInst(instInfo.getProcInstId());
			}

		}
    }
    
    
    public static String getHandleClassNameByRevBizType(RevBizTypeEnum revBizType) throws EASBizException {
		if(revBizType.equals(RevBizTypeEnum.customer) 
				|| revBizType.equals(RevBizTypeEnum.purchase) 
						|| revBizType.equals(RevBizTypeEnum.sincerity) 
								|| revBizType.equals(RevBizTypeEnum.areaCompensate))
			return "com.kingdee.eas.fdc.sellhouse.app.SheRevHandle";
		else if(revBizType.equals(RevBizTypeEnum.obligate) 
					|| revBizType.equals(RevBizTypeEnum.tenancy))
			return "com.kingdee.eas.fdc.sellhouse.app.TenRevHandle";
		else if(revBizType.equals(RevBizTypeEnum.manage))
			return "com.kingdee.eas.fdc.sellhouse.app.PpmRevHandle";
		else	
			throw new EASBizException(new NumericExceptionSubItem("100","收款业务类型异常！"));
    }
    
    
    
    /**按款型类别 分为四大类  
     * 用于收款时的对冲 同一大类中的款项才能互相对冲*/
    public static Map SHERevMoenyTpeMap(){
    	Map typeMap = new HashMap();
    	//1.非按揭类
    	typeMap.put(MoneyTypeEnum.EarnestMoney.getValue(), "A");
    	typeMap.put(MoneyTypeEnum.FisrtAmount.getValue(), "A");
    	typeMap.put(MoneyTypeEnum.HouseAmount.getValue(), "A");
    	typeMap.put(MoneyTypeEnum.PreconcertMoney.getValue(), "A");
    	//2.按揭类
    	typeMap.put(MoneyTypeEnum.LoanAmount.getValue(), "B");
    	typeMap.put(MoneyTypeEnum.AccFundAmount.getValue(), "B");
    	//3.代收费用类
    	typeMap.put(MoneyTypeEnum.ReplaceFee.getValue(), "C");
    	//其它类
    	typeMap.put(MoneyTypeEnum.PreMoney.getValue(), "D");
    	typeMap.put(MoneyTypeEnum.Refundment.getValue(), "D");
    	typeMap.put(MoneyTypeEnum.FitmentAmount.getValue(), "D");
    	typeMap.put(MoneyTypeEnum.LateFee.getValue(), "D");
    	typeMap.put(MoneyTypeEnum.CompensateAmount.getValue(), "D");
    	typeMap.put(MoneyTypeEnum.CommissionCharge.getValue(), "D");
    	typeMap.put(MoneyTypeEnum.ElseAmount.getValue(), "D");
    	typeMap.put(MoneyTypeEnum.SinPur.getValue(), "D");
    	
    	return typeMap;
    }
    
    /**交易单据/业务单据的应收明细的接口   
     * bizType 业务单据类型
     * 主要是为了收款服务的，收款改成针对交易的明细收款后，次处参数bizType不用传递即可       */
	public static ITranPayListEntry getPayListEntryInterFace(Context ctx,RelatBizType bizType) throws BOSException, EASBizException {
		if(bizType!=null)  {
			if(RelatBizType.PreOrder.equals(bizType)) { //预约单
				if(ctx!=null)
					return SincerReceiveEntryFactory.getLocalInstance(ctx);
				else
					return SincerReceiveEntryFactory.getRemoteInstance();
			}else if(RelatBizType.PrePur.equals(bizType)) { //预订单
				if(ctx!=null)
					return PrePurchasePayListEntryFactory.getLocalInstance(ctx);
				else 
					return 
						PrePurchasePayListEntryFactory.getRemoteInstance();
			}else if(RelatBizType.Purchase.equals(bizType)) { //认购单
				if(ctx!=null)
					return PurPayListEntryFactory.getLocalInstance(ctx);
				else
					return PurPayListEntryFactory.getRemoteInstance();
			}else if(RelatBizType.Sign.equals(bizType)) { //签约单
				if(ctx!=null)
					return SignPayListEntryFactory.getLocalInstance(ctx);
				else
					return SignPayListEntryFactory.getRemoteInstance();
			}
		}

		//交易主线
		if(ctx!=null)
			return TranBusinessOverViewFactory.getLocalInstance(ctx);
		else
			return TranBusinessOverViewFactory.getRemoteInstance();
		
	}
	
	/**
	 * 交易单据/业务单据的应收明细的接口
	 * bizBillId 业务单据的应收明细的id
	 * */
	public static ITranCustomerEntry getCustmerEntryInterFace(Context ctx,BOSUuid bizBillId) throws BOSException, EASBizException {
		if(bizBillId==null) return null;
		
		if(bizBillId.getType().equals(new SincerityPurchaseInfo().getBOSType()))
			if(ctx!=null)
				return SincerityPurchaseCustomerEntryFactory.getLocalInstance(ctx);
			else
				return SincerityPurchaseCustomerEntryFactory.getRemoteInstance();
		else if(bizBillId.getType().equals(new PrePurchaseManageInfo().getBOSType()))
			if(ctx!=null)
				return PrePurchaseCustomerEntryFactory.getLocalInstance(ctx);
			else 
				return 
				PrePurchaseCustomerEntryFactory.getRemoteInstance();
		else if(bizBillId.getType().equals(new PurchaseManageInfo().getBOSType()))
			if(ctx!=null)
				return PurCustomerEntryFactory.getLocalInstance(ctx);
			else
				return PurCustomerEntryFactory.getRemoteInstance();	
		else if(bizBillId.getType().equals(new SignManageInfo().getBOSType()))
			if(ctx!=null)
				return SignCustomerEntryFactory.getLocalInstance(ctx);
			else
				return SignCustomerEntryFactory.getRemoteInstance();	

		else return null;
	}
	

	
	//根据bosid返回对应的关联单据业务类型
	public static RelatBizType retRelateBizTypeById(BOSUuid id) {
		if(id==null) return null;
		RelatBizType retType = null;
		
		if(id.getType().equals(new SincerityPurchaseInfo().getBOSType()))
			return RelatBizType.PreOrder;
		else if(id.getType().equals(new PrePurchaseManageInfo().getBOSType()))
			return RelatBizType.PrePur;
		else if(id.getType().equals(new PurchaseManageInfo().getBOSType()))
			return RelatBizType.Purchase;		
		else if(id.getType().equals(new SignManageInfo().getBOSType()))
			return RelatBizType.Sign;		
		else if(id.getType().equals(new ChangeManageInfo().getBOSType()))
			return RelatBizType.Change;		
		
		return retType;
	}
	
	

	/**根据代收费用设置计算代收费用金额
	 *@param collection 代收费设置
	 *@param totalAmount 成交总价
	 *@param standAmount 标准总价
	 *@param buildArea 建筑面积
	 *@param roomArea 套内面积
	 *@param roomInfo 房间对象（要传递房间的所有属性，且包含属性建筑性质的名称）
	 ***/
	public static BigDecimal getSubstituteAmountByCollection(Context ctx,CollectionInfo collection,
					BigDecimal totalAmount,BigDecimal standAmount,BigDecimal buildArea,BigDecimal roomArea,RoomInfo roomInfo) throws BOSException{
		BigDecimal amount = new BigDecimal("0");
			if (CalculateTypeEnum.GENERAL.equals(collection.getCalculateType()) && collection.getComparaValue()!=null) { //常规模式
				if(FactorEnum.DEALTOTALALMOUNT.equals(collection.getFactor())){ //成交总价
					if(totalAmount==null) totalAmount = new BigDecimal("0");
					if(OperateEnum.MULTIPLY.equals(collection.getOperate())){
						amount = totalAmount.multiply(collection.getComparaValue());
					}if(OperateEnum.ADD.equals(collection.getOperate())){
						amount = totalAmount.add(collection.getComparaValue());
					}if(OperateEnum.MINUS.equals(collection.getOperate())){
						amount = totalAmount.subtract(collection.getComparaValue());
					}if(OperateEnum.DIVIDE.equals(collection.getOperate()) && collection.getComparaValue().compareTo(new BigDecimal(0))!=0){
						amount = FDCHelper.divide(totalAmount, collection.getComparaValue());
					}
				}else if(FactorEnum.STANDARTOATALMOUNT.equals(collection.getFactor())){	//标准总价
					if(standAmount==null) standAmount = new BigDecimal("0");
					if(OperateEnum.MULTIPLY.equals(collection.getOperate())){
						amount = standAmount.multiply(collection.getComparaValue());
					}if(OperateEnum.ADD.equals(collection.getOperate())){
						amount = standAmount.add(collection.getComparaValue());
					}if(OperateEnum.MINUS.equals(collection.getOperate())){
						amount = standAmount.subtract(collection.getComparaValue());
					}if(OperateEnum.DIVIDE.equals(collection.getOperate()) && collection.getComparaValue().compareTo(new BigDecimal(0))!=0 ){
						amount = FDCHelper.divide(standAmount, collection.getComparaValue());
					}
				}else if(FactorEnum.BUILDINGAREA.equals(collection.getFactor())){	//建筑面积
					if(buildArea==null) buildArea = new BigDecimal("0");
					if(OperateEnum.MULTIPLY.equals(collection.getOperate())){
						amount = buildArea.multiply(collection.getComparaValue());
					}if(OperateEnum.ADD.equals(collection.getOperate())){
						amount = buildArea.add(collection.getComparaValue());
					}if(OperateEnum.MINUS.equals(collection.getOperate())){
						amount = buildArea.subtract(collection.getComparaValue());
					}if(OperateEnum.DIVIDE.equals(collection.getOperate()) && collection.getComparaValue().compareTo(new BigDecimal(0))!=0){
						amount = FDCHelper.divide(buildArea, collection.getComparaValue());
					}
				}else if(FactorEnum.ROOMAREA.equals(collection.getFactor())){	//套内面积
					if(roomArea==null) roomArea = new BigDecimal("0");
					if(OperateEnum.MULTIPLY.equals(collection.getOperate())){
						amount = roomArea.multiply(collection.getComparaValue());
					}if(OperateEnum.ADD.equals(collection.getOperate())){
						amount = roomArea.add(collection.getComparaValue());
					}if(OperateEnum.MINUS.equals(collection.getOperate())){
						amount = roomArea.subtract(collection.getComparaValue());
					}if(OperateEnum.DIVIDE.equals(collection.getOperate()) && collection.getComparaValue().compareTo(new BigDecimal(0))!=0){
						amount = FDCHelper.divide(roomArea, collection.getComparaValue());
					}
				}
			} else if(CalculateTypeEnum.PARMAMNENT.equals(collection.getCalculateType())){	//固定值
				amount = collection.getFixedAmount();
			}else if(CalculateTypeEnum.PATULOUS.equals(collection.getCalculateType())){		//扩展模式
				Map valuemap = new HashMap();
				valuemap.put("DealTotalAmount", totalAmount);
				valuemap.put("Room", roomInfo);
				String stdFormulaScript = collection.getStdFormulaScript();
				try {
					if(ctx!=null) {
						Object result = FormulaEngine.runFormula(stdFormulaScript, valuemap, ctx);
						amount = new BigDecimal(result!=null?result.toString():"0");
					}else{
						Object result = FormulaEngine.runFormula(stdFormulaScript, valuemap);
						amount = new BigDecimal(result!=null?result.toString():"0");
					}
				} catch (RunFormulaException e) {
					e.printStackTrace();
				}	
			}			
			
			if (amount != null && collection.getHold() != null )     {
		        if (collection.getHold().equals(HoldEnum.YUAN))
		        	if(collection.getIntegerType().equals(IntegerTypeEnum.ROUND)){
		        	    amount = amount.setScale(0, BigDecimal.ROUND_HALF_UP);
		        	}else if(collection.getIntegerType().equals(IntegerTypeEnum.LASTINTERCEPT)){
		        		amount = amount.setScale(0, BigDecimal.ROUND_UP);
		        	}else{
		        		amount = amount.setScale(0, BigDecimal.ROUND_DOWN);
		        	}
		        else if (collection.getHold().equals(HoldEnum.JIAO))
		        	if(collection.getIntegerType().equals(IntegerTypeEnum.ROUND)){
		        	    amount = amount.setScale(1, BigDecimal.ROUND_HALF_UP);
		        	}else if(collection.getIntegerType().equals(IntegerTypeEnum.LASTINTERCEPT)){
		        		amount = amount.setScale(1, BigDecimal.ROUND_UP);
		        	}else{
		        		amount = amount.setScale(1, BigDecimal.ROUND_DOWN);
		        	}
		        else {
		        	if(collection.getIntegerType().equals(IntegerTypeEnum.ROUND)){
		        	    amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
		        	}else if(collection.getIntegerType().equals(IntegerTypeEnum.LASTINTERCEPT)){
		        		amount = amount.setScale(2, BigDecimal.ROUND_UP);
		        	}else{
		        		amount = amount.setScale(2, BigDecimal.ROUND_DOWN);
		        	}
		        }
		    }
			return amount;
	}
	
	/**
	 * base1是有数据的 base2是空的,将base1的值放到base2中
	 * @param isPatchAdd 是否增量添加
	 * */ 
	public static void changeObjectValue(IPropertyContainer base1, IPropertyContainer base2, boolean isPatchAdd, String[] exceptFields) {
		Enumeration enumeration = base1.keys();
		while (enumeration.hasMoreElements()) {
			String obj = enumeration.nextElement().toString();
			if ("id".equals(obj)) {
				continue;
			}
			
			boolean isExcept = false;
			if(exceptFields != null){
				for(int i=0; i<exceptFields.length; i++){
					if(exceptFields[i].toLowerCase().equals(obj)){
						isExcept = true;
						break;
					}
				}
			}
			
			if(isExcept){
				continue;
			}
			
			if(!isPatchAdd){
				base2.put(obj, base1.get(obj));
				continue;
			}
			
			if(base2.get(obj) == null){
				base2.put(obj, base1.get(obj));
				continue;
			}

			if(base1.get(obj) == null){
				continue;
			}
			
			if(base1.get(obj) instanceof String){
				if(!((String)base1.get(obj)).trim().equals("")){
					base2.put(obj, base1.get(obj));
				}
			}else{
				base2.put(obj, base1.get(obj));
			}
		}
	}
	
	public static void changeSheObjectValue(IPropertyContainer base1, IPropertyContainer base2, boolean isPatchAdd, String[] exceptFields) {
		Enumeration enumeration = base1.keys();
		while (enumeration.hasMoreElements()) {
			String obj = enumeration.nextElement().toString();
			if ("id".equals(obj)) {
				continue;
			}
			boolean isExcept = false;
			if(exceptFields != null){
				for(int i=0; i<exceptFields.length; i++){
					if(exceptFields[i].toLowerCase().equals(obj)){
						isExcept = true;
						break;
					}
				}
			}
			
			if(isExcept){
				continue;
			}
			if(!isPatchAdd){
				base2.put(obj, base1.get(obj));
				continue;
			}
			
			if(base2.get(obj) == null){
				base2.put(obj, base1.get(obj));
				continue;
			}

			if(base1.get(obj) == null){
				continue;
			}
			
			if(base1.get(obj) instanceof String){
				if(!((String)base1.get(obj)).trim().equals("")){
					base2.put(obj, base1.get(obj));
				}
			}else if("linkman".equals(obj)){
				OrgCustomerLinkManCollection fdcBaseLinkMan = new OrgCustomerLinkManCollection();
				SHECustomerLinkManCollection sheCLMC = (SHECustomerLinkManCollection)base1.get(obj);
				for(int i=0;i<sheCLMC.size();i++){
					OrgCustomerLinkManInfo oclmi = new OrgCustomerLinkManInfo();
					oclmi.putAll((FDCBaseLinkManInfo)sheCLMC.get(i));
					oclmi.setId(null);
					fdcBaseLinkMan.add(oclmi);
				}
				base2.put(obj, fdcBaseLinkMan);
			}else{
				base2.put(obj, base1.get(obj));
			}
		}
	}

	public static void changeObjectValue(IPropertyContainer base1, IPropertyContainer base2, boolean isPatchAdd) {
		changeObjectValue(base1, base2, isPatchAdd, null);
	}
	
	public static void changeSheObjectValue(IPropertyContainer base1, IPropertyContainer base2, boolean isPatchAdd) {
		changeSheObjectValue(base1, base2, isPatchAdd, null);
	}
	
	public static String getStringFromSet(Set srcSet){
		String retStr = "";
		if(srcSet==null || srcSet.size()==0) return retStr;
		Iterator iter = srcSet.iterator();
		while(iter.hasNext()){
			Object obj = iter.next();
			if(obj instanceof String) retStr += ",'" + (String)obj + "'";
		}
		if(!retStr.equals("")) retStr = retStr.replaceFirst(",", "");
		return retStr;
	}	
	
	
}
