package com.kingdee.eas.fdc.tenancy.app;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.fdc.tenancy.AttachDealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.AttachResourceFactory;
import com.kingdee.eas.fdc.tenancy.AttachResourceInfo;
import com.kingdee.eas.fdc.tenancy.BizStateEnum;
import com.kingdee.eas.fdc.tenancy.ChargeDateTypeEnum;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryFactory;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum;
import com.kingdee.eas.fdc.tenancy.FlagAtTermEnum;
import com.kingdee.eas.fdc.tenancy.HandleRoomEntrysInfo;
import com.kingdee.eas.fdc.tenancy.HandleStateEnum;
import com.kingdee.eas.fdc.tenancy.ITenancyEntryInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyPayListInfo;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.RentCountTypeEnum;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenBillBaseInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyException;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class TenancyBillControllerBean extends AbstractTenancyBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.TenancyBillControllerBean");
    private MoneyDefineInfo rentMoneyName = null;// 租金款项类型
	private MoneyDefineInfo depositMoneyName = null;// 押金款项类型
    
    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	TenancyBillInfo tenancyBill = (TenancyBillInfo) model;
    	tenancyBill.setTenancyState(TenancyBillStateEnum.Saved);
    	
    	IObjectPK pk = super._save(ctx, model);
    	//转认租时，返写诚意预留单的业务状态
    	if(tenancyBill.getSincerObligate() != null){
    		String sql = "update T_TEN_SincerObligate set FBizState = ? where fid = ?";
    		DbUtil.execute(ctx, sql,new Object []{BizStateEnum.LEASE_VALUE,tenancyBill.getSincerObligate().getId().toString()});
    	}
    	return pk;
    }
    
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("tenancyType");
    	sels.add("oldTenancyBill.tenancyState");
    	sels.add("sincerObligate.*");
    	TenancyBillInfo tenancy = (TenancyBillInfo) _getValue(ctx, pk, sels);
    	//如果删除的合同是改租，续租或者更名类型的，需要将原合同从改租中更新为执行中.
    	//该操作的前提是 “只有执行中的合同才能进行改租，续租，转名”
    	TenancyContractTypeEnum tenType = tenancy.getTenancyType();
    	if(TenancyContractTypeEnum.ContinueTenancy.equals(tenType)
    			|| TenancyContractTypeEnum.RejiggerTenancy.equals(tenType)
    			|| TenancyContractTypeEnum.ChangeName.equals(tenType)){
    		TenancyBillInfo oldTen = tenancy.getOldTenancyBill();
    		if(oldTen == null){
    			logger.error("改续转名合同的原合同为空，合同ID：" + pk.toString());
    		}else{
    			TenancyBillStateEnum oldBillState = oldTen.getTenancyState();    			
    			if((TenancyContractTypeEnum.ContinueTenancy.equals(tenType) && TenancyBillStateEnum.ContinueTenancying.equals(oldBillState))
    					|| (TenancyContractTypeEnum.RejiggerTenancy.equals(tenType) && TenancyBillStateEnum.RejiggerTenancying.equals(oldBillState))
    					|| (TenancyContractTypeEnum.ChangeName.equals(tenType) && TenancyBillStateEnum.ChangeNaming.equals(oldBillState))){
    				oldTen.setTenancyState(TenancyBillStateEnum.Executing);
    				SelectorItemCollection updateOldStateSels = new SelectorItemCollection();
    				updateOldStateSels.add("tenancyState");
    				_updatePartial(ctx, oldTen, updateOldStateSels);
    			}else{
    				logger.error("状态逻辑有问题哈。tenType:" + tenType + "  oldTenState:" + oldBillState);
    			}
    		}
    	}
    	//转认租时，返写诚意预留单的业务状态
    	if(tenancy.getSincerObligate() != null){
    		String sql = "update T_TEN_SincerObligate set FBizState = ? where fid = ?";
    		DbUtil.execute(ctx, sql,new Object []{BizStateEnum.SINCEROBLIGATED_VALUE,tenancy.getSincerObligate().getId().toString()});
    	}
    	super._delete(ctx, pk);
    }
    
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		TenancyBillInfo tenancyBill = (TenancyBillInfo) model;
		TenancyContractTypeEnum tenancyBillType = tenancyBill.getTenancyType();

		// 改租,续租或更名 需要判断原合同剩余押金是否够结算
		if (!TenancyContractTypeEnum.NewTenancy.equals(tenancyBillType)) {
			TenancyBillInfo oldTen = tenancyBill.getOldTenancyBill();
			if (oldTen == null) {
				throw new BOSException("没有原合同!");
			}

			//设置原合同的状态为 改租中/续租中/更名中
			TenancyBillStateEnum oldBillState = null;
			if(TenancyContractTypeEnum.ContinueTenancy.equals(tenancyBillType)){
				oldBillState = TenancyBillStateEnum.ContinueTenancying;
			}else if(TenancyContractTypeEnum.RejiggerTenancy.equals(tenancyBillType)){
				oldBillState = TenancyBillStateEnum.RejiggerTenancying;
			}else if(TenancyContractTypeEnum.ChangeName.equals(tenancyBillType)){
				oldBillState = TenancyBillStateEnum.ChangeNaming;
			}else if(TenancyContractTypeEnum.PriceChange.equals(tenancyBillType)){
				if(FDCBillStateEnum.AUDITTED.equals(oldTen.getState())){
					oldBillState = TenancyBillStateEnum.Expiration;
				}else{
					oldBillState = TenancyBillStateEnum.PriceChanging;
				}
			}
			setBillState(ctx, oldTen, oldBillState);
			
			oldTen = TenancyHelper.getTenancyBillInfo(ctx, oldTen.getId().toString());
			// 这里只进行验证,为不影响新增单据的正常提交,使用拷贝对象进行调整验证
			TenancyBillInfo newBillCopy = (TenancyBillInfo) tenancyBill.clone();
			TenancyBillInfo oldBillCopy = (TenancyBillInfo) oldTen.clone();

			adjustDepositeAndPayList(ctx, newBillCopy, oldBillCopy, false);
			checkRemainDepositAmountEnough(newBillCopy, oldBillCopy);
		}

		TenancyCustomerEntryCollection customerInfos = tenancyBill.getTenCustomerList();
		for (int i = 0; i < customerInfos.size(); i++) {
			TenancyCustomerEntryInfo customerInfoInfo = customerInfos.get(i);
			FDCCustomerInfo customer = customerInfoInfo.getFdcCustomer();
			if (customer != null) {
				//---这里只更新租赁时可能改动的信息，避免使用update(model)
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("postalcode");
				sels.add("phone");
				sels.add("certificateName");
				sels.add("certificateNumber");
				sels.add("mailAddress");
				sels.add("description");
				FDCCustomerFactory.getLocalInstance(ctx).updatePartial(customer, sels);
//				FDCCustomerFactory.getLocalInstance(ctx).update(new ObjectUuidPK(customer.getId()), customer);
				//-------
				FDCCustomerFactory.getLocalInstance(ctx).addToSysCustomer(customer.getId().toString());
			}
		}

		tenancyBill.setTenancyState(TenancyBillStateEnum.Submitted);
		
		IObjectPK pk = super._submit(ctx, model);
		//更新合同ID到收款明细
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("tenancyRoomList.roomPayList.*");
		sels.add("tenancyRoomList.roomPayList.currency.name");
		sels.add("tenancyRoomList.roomPayList.currency.number");
		
		sels.add("tenancyRoomList.roomPayList.moneyDefine.name");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.number");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.moneyType");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.sysType");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.isEnabled");
		
		TenancyBillInfo tenancyInfo = TenancyBillFactory.getLocalInstance(ctx).getTenancyBillInfo(pk, sels);
		TenancyRoomEntryCollection  roomPaylistColl = tenancyInfo.getTenancyRoomList();
		for(int i = 0;i <roomPaylistColl.size();i++){
			TenancyRoomEntryInfo roomEntryInfo = (TenancyRoomEntryInfo)roomPaylistColl.get(i);
			TenancyRoomPayListEntryCollection roomPaylist = roomEntryInfo.getRoomPayList();
			for(int j= 0 ; j<roomPaylist.size();j++ ){
				TenancyRoomPayListEntryInfo paylistInfo =  (TenancyRoomPayListEntryInfo)roomPaylist.get(j);
				paylistInfo.setTenBill(tenancyBill);
				TenancyRoomPayListEntryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(paylistInfo.getId()),paylistInfo);
			}
		}
		
		//转认租时，返写诚意预留单的业务状态
    	if(tenancyBill.getSincerObligate() != null){
    		String sql = "update T_TEN_SincerObligate set FBizState = ? where fid = ?";
    		DbUtil.execute(ctx, sql,new Object []{BizStateEnum.LEASE_VALUE,tenancyBill.getSincerObligate().getId().toString()});
    	}
    	return  pk;
	}
    protected void checkNameDup(Context ctx, TenBillBaseInfo tenBillBaseInfo)
    		throws BOSException, EASBizException {
//    	super.checkNameDup(ctx, tenBillBaseInfo); //这里不再检查 名称重复 2010.4.9 by jian_wen 需求变更
    }
    private void setBillState(Context ctx, TenancyBillInfo oldTen, TenancyBillStateEnum state) throws EASBizException, BOSException {
    	if(oldTen != null){
    		oldTen.setTenancyState(state);
    		SelectorItemCollection sels = new SelectorItemCollection();
    		sels.add("tenancyState");
    		updatePartial(ctx, oldTen, sels);
    	}
	}

	/**
	 * 转续租时进行资金结转 TODO 这些逻辑转到界面上去做
	 */
    private void financingCarryForward(TenancyBillInfo tenBill){
    	TenancyBillInfo oldTenBill = tenBill.getOldTenancyBill();
    	//改租时进行资金结转
    	BigDecimal deductAmount = tenBill.getDeductAmount();//获得扣款金额
    	
    	BigDecimal oldRemainDepositAmount = oldTenBill.getRemainDepositAmount();//获得原合同剩余押金
    	Date rejiggerDate = tenBill.getStartDate();//改租日期
    	
    	BigDecimal totalMissingRentAmount = FDCHelper.ZERO;//缺少的房间租金
    	TenancyRoomEntryCollection tenRooms = oldTenBill.getTenancyRoomList();
    	for(int i=0; i<tenRooms.size(); i++){
    		TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
    		TenancyRoomPayListEntryCollection roomPayList = tenRoom.getRoomPayList();
    		for(int j=0; j<roomPayList.size(); j++){
    			TenancyRoomPayListEntryInfo roomPay = roomPayList.get(j);
    			Date payStartDate = roomPay.getStartDate();
    			Date payEndDate = roomPay.getEndDate();
    			
    			BigDecimal appAmount = roomPay.getAppAmount();
    			BigDecimal actAmount = roomPay.getActAmount();
    			
    			if(!payStartDate.before(rejiggerDate)){
    				//如果是改租日期后的租期,不再计算租金
    			}else if(!payEndDate.after(rejiggerDate)){
    				//如果是改租日期之前的租期,则计算完整租期的租金
    				BigDecimal missingAmount = appAmount.subtract(appAmount);
    				totalMissingRentAmount = totalMissingRentAmount.add(missingAmount);
    				//TODO 按需求文档是需要删除该付款明细的
    			}else{
    				//如果改租日期在该租期之间,则计算该租期有效日期的租金
    				
    				BigDecimal actAppAmount = FDCHelper.ZERO;//实际应付金额
    				BigDecimal missingAmount = actAppAmount.subtract(appAmount);
    				totalMissingRentAmount = totalMissingRentAmount.add(missingAmount);
    			}
    		}
    	}
    	
    	//
    	BigDecimal remainAmount = oldRemainDepositAmount.subtract(deductAmount).subtract(totalMissingRentAmount);
    	if(remainAmount.compareTo(FDCHelper.ZERO) == 0){
    		//正好抵平,仅将原合同中的剩余押金和未交房租进行结转
    	}else if(remainAmount.compareTo(FDCHelper.ZERO) > 0){
    		//除原合同未交租金结转外,还需要与新合同进行结转
    	}else{
    		//钱不够呀,抛出异常。其实这个判断应该是改租提交的时候进行的,在审批的时候就不会再有钱不够的情况了
    	}
    	
    	
    	TenancyContractTypeEnum tenancyBillType = oldTenBill.getTenancyType();
    	if(TenancyContractTypeEnum.RejiggerTenancy.equals(tenancyBillType)){//改租
    		carryForward1(tenBill, oldTenBill);
    	}else if(TenancyContractTypeEnum.ContinueTenancy.equals(tenancyBillType)){
//    		carryForward2();
    	}
    	
    }
    
    protected void _setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	super._setSubmitStatus(ctx, billId);
    	TenancyBillInfo tenancy = (TenancyBillInfo) this.getValue(ctx, new ObjectUuidPK(billId));
    	updateTenancyState(ctx, tenancy, TenancyBillStateEnum.Submitted);
    }

    /**
	 * 更新租赁合同的状态
	 * */
	private void updateTenancyState(Context ctx, TenancyBillInfo tenancy, TenancyBillStateEnum state) throws EASBizException, BOSException{
		tenancy.setTenancyState(state);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("tenancyState");
		_updatePartial(ctx, tenancy, sels);
	}
    
    protected void _setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	super._setAudittingStatus(ctx, billId);
    	TenancyBillInfo tenancy = (TenancyBillInfo) this.getValue(ctx, new ObjectUuidPK(billId));
    	updateTenancyState(ctx, tenancy, TenancyBillStateEnum.Auditing);
    }
    
    /** 审批 */
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	super._audit(ctx, billId);
    	
    	TenancyBillInfo tenBill = TenancyHelper.getTenancyBillInfo(ctx, billId.toString());
    	
    	tenBill.setTenancyState(TenancyBillStateEnum.Audited);
		SelectorItemCollection updateSels = new SelectorItemCollection();
		updateSels.add("tenancyState");
		_updatePartial(ctx, tenBill, updateSels);
    	
    	TenancyContractTypeEnum tenContractType = tenBill.getTenancyType();
    	
    	TenancyBillInfo oldTenBill = tenBill.getOldTenancyBill();
    	if(oldTenBill != null){
    		oldTenBill = TenancyHelper.getTenancyBillInfo(ctx, oldTenBill.getId().toString());
    		tenBill.setOldTenancyBill(oldTenBill);
    	}
    	
    	if(TenancyContractTypeEnum.RejiggerTenancy.equals(tenContractType)){//改租审批
    		setOldTenancyFlagAtTerm(ctx, tenBill, oldTenBill, FlagAtTermEnum.RejiggerNotAtTerm, FlagAtTermEnum.RejiggerNotAtTerm, FlagAtTermEnum.QuitNotAtTerm);    		
    	}else if(TenancyContractTypeEnum.ContinueTenancy.equals(tenContractType)){//续租审批
    		setOldTenancyFlagAtTerm(ctx, tenBill, oldTenBill, FlagAtTermEnum.ReletAtTerm, FlagAtTermEnum.ReletAtTerm, FlagAtTermEnum.QuitAtTerm);    		    	
    	}else if(TenancyContractTypeEnum.ChangeName.equals(tenContractType)){//转名审批
    		setOldTenancyFlagAtTerm(ctx, tenBill, oldTenBill, FlagAtTermEnum.ChangeNameNotAtTerm, FlagAtTermEnum.ChangeNameNotAtTerm, FlagAtTermEnum.ChangeNameNotAtTerm);
    	}else if(TenancyContractTypeEnum.PriceChange.equals(tenContractType)){//价格变更
    		setOldTenancyFlagAtTerm(ctx, tenBill, oldTenBill, FlagAtTermEnum.PriceChangeNotAtTerm, FlagAtTermEnum.PriceChangeNotAtTerm, FlagAtTermEnum.PriceChangeNotAtTerm);
    	}
    	
    	if(!TenancyContractTypeEnum.NewTenancy.equals(tenContractType)){
    		adjustDepositeAndPayList(ctx, tenBill, oldTenBill, true);
        	checkRemainDepositAmountEnough(tenBill, oldTenBill);
        	deductAmountOfRemainDepositAmount(ctx, tenBill, oldTenBill);	
    	}
    }
    
    /**
     * 原合同剩余押金减去扣款,并分摊到各个房间的剩余押金上,调用该函数前一定要先检查原合同剩余押金是否够计算所有扣款和未付租金,即checkRemainDepositAmountEnough();
     * @throws BOSException 
     * @throws EASBizException 
     * */
    private void deductAmountOfRemainDepositAmount(Context ctx, TenancyBillInfo tenBill, TenancyBillInfo oldTenBill) throws EASBizException, BOSException {
    	BigDecimal deductAmount = tenBill.getDeductAmount();
    	//不用扣了。TODO 后续会修改为针对房间扣款
//    	TenancyHelper.deductAmountOfRemainDepositAmount(ctx, deductAmount, oldTenBill);
	}

	/**
     * 判断剩余押金是否够结算合同应付金额
     * @param tenBill
     * @param oldTenBill
     * @throws BOSException 
	 * @throws TenancyException 
     * */
    private void checkRemainDepositAmountEnough(TenancyBillInfo tenBill, TenancyBillInfo oldTenBill) throws BOSException, TenancyException {
    	BigDecimal deductAmount = tenBill.getDeductAmount()==null ? FDCHelper.ZERO : tenBill.getDeductAmount();
    	//现在没有扣款金额，暂不处理. TODO
    	BigDecimal oldTenToPayAmount = FDCHelper.ZERO;//deductAmount;
    	TenancyRoomEntryCollection oldTenRooms = oldTenBill.getTenancyRoomList();
    	TenAttachResourceEntryCollection oldTenAttachs = oldTenBill.getTenAttachResourceList();
    	BigDecimal oldTenRemainDepositAmount = oldTenBill.getRemainDepositAmount()==null ? FDCHelper.ZERO : oldTenBill.getRemainDepositAmount();
    	
    	oldTenToPayAmount = addToPayAmount(oldTenRooms, oldTenToPayAmount);
    	oldTenToPayAmount = addToPayAmount(oldTenAttachs, oldTenToPayAmount);
    	
    	if(oldTenRemainDepositAmount.compareTo(oldTenToPayAmount) < 0){//原合同押金不够结算未交租金和扣款,则不允许审批/提交
    		throw new TenancyException(TenancyException.DEPOSIT_NOT_ENOUGH);
    	}
	}
    
    private BigDecimal addToPayAmount(IObjectCollection tenObjs, BigDecimal srcAmount){
    	for(int i=0; i<tenObjs.size(); i++){
    		ITenancyEntryInfo tenObj = (ITenancyEntryInfo) tenObjs.getObject(i);
    		
    		IObjectCollection payList = tenObj.getPayList();
    		
    		// 对总应付和实付进行比较来确定未付的应付金额
    		BigDecimal totalAppAmount = FDCHelper.ZERO;
    		BigDecimal totalActAmount = FDCHelper.ZERO;
    		for(int j=0; j<payList.size(); j++){
    			ITenancyPayListInfo pay = (ITenancyPayListInfo) payList.getObject(j);
    			MoneyDefineInfo moneyName = pay.getMoneyDefine();
    			//押金不用计算应付
    			if(moneyName == null  ||  MoneyTypeEnum.DepositAmount.equals(moneyName.getMoneyType())){
    				continue;
    			}    			
    			
    			BigDecimal appAmount = pay.getAppAmount()==null ? FDCHelper.ZERO : pay.getAppAmount();
    			BigDecimal actAmount = pay.getActAmount()==null ? FDCHelper.ZERO : pay.getActAmount();
    			
    			totalAppAmount = totalAppAmount.add(appAmount);
    			totalActAmount = totalActAmount.add(actAmount);
    		}
    		
    		//只有应付比实付大时,才计入 未付的应付金额;(当应付比实付小时,已在adjustDepositeAndPayList()中将多收的实付金额加入到剩余押金中,这里不再处理)
    		BigDecimal tmpToPay = totalAppAmount.subtract(totalActAmount);
    		if(tmpToPay.compareTo(FDCHelper.ZERO) > 0){
    			srcAmount = srcAmount.add(tmpToPay);
    		}
    	}
    	return srcAmount;
    }
    
    /**
     * 调整剩余押金和付款明细的应付金额
     * 对于改租和更名,根据改租的日期,需要更新原合同的应付金额,并将多交的实付金额加到房间押金上;续租不存在应付日期的改动,所以原合同应付不会变动
     * @param ctx
     * @param tenBill
     * @param oldTenBill
     * @param isExcuteToDB
     * 			金额调整是否更新到数据库.考虑到改租等合同提交时,需要对原合同是否足够结算进行验证,而不会将调整的金额入库(审批时候才会),提供该参数
     * */
    private void adjustDepositeAndPayList(Context ctx, TenancyBillInfo tenBill, TenancyBillInfo oldTenBill, boolean isExecuteToDB) throws EASBizException, BOSException{
    	Date bizDate = tenBill.getStartDate();//改租续租的实际日期,以新合同起始日期为准
    	
    	TenancyHelper.adjustDepositeAndPayList(ctx, bizDate, oldTenBill, isExecuteToDB);
    	
    	/*
    	TenancyRoomEntryCollection oldTenRooms = oldTenBill.getTenancyRoomList();
    	
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("remainDepositAmount");
    	
		BigDecimal oldTenRemainDepositAmount = FDCHelper.ZERO;
		for(int i=0; i<oldTenRooms.size(); i++){
			TenancyRoomEntryInfo oldTenRoom = oldTenRooms.get(i);
			
			BigDecimal oldRoomRemainDepositAmount = oldTenRoom.getRemainDepositAmount();
			if(oldRoomRemainDepositAmount == null){
				oldRoomRemainDepositAmount = FDCHelper.ZERO;
			}
			
			TenancyRoomPayListEntryCollection tenRoomPayList = oldTenRoom.getRoomPayList();
			
			int interruptSeq = -1;//表示改租,转名时被变动日期截断的那期收款明细的序列号
			BigDecimal reducedAppPayAmount = null;//被截断的那期减少的应付金额,用原应付金额减去该金额作为新的应付金额,如果原应付金额小于该金额,从原上一期的应付金额中减,依此类推
			for(int j=0; j<tenRoomPayList.size(); j++){
				TenancyRoomPayListEntryInfo tenRoomPay = tenRoomPayList.get(j);
				
				BigDecimal appPayAmount = tenRoomPay.getActAmount();
				
				Date payStartDate = tenRoomPay.getStartDate();
				Date payEndDate = tenRoomPay.getEndDate();
				
				BigDecimal actPayAmount = tenRoomPay.getActAmount();
				
				//如果是变更日期后的付款明细,应付更新为0,将实付的租金全部加到押金上面
				if(!payStartDate.before(bizDate)){
					if(actPayAmount != null  &&  actPayAmount.compareTo(FDCHelper.ZERO) != 0){
						oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actPayAmount);
					}
					tenRoomPay.setAppAmount(FDCHelper.ZERO);
					if(isExecuteToDB)updateAppAmount(ctx, tenRoomPay);
				}else if(!payEndDate.after(bizDate)){//在变更日期之前的完整付款明细,不涉及修改
					
				}else if(payStartDate.before(bizDate) && payEndDate.after(bizDate)){//被截断的付款明细记录,计算应扣掉的金额,并将多收的实付金额加到剩余押金上
					if(tenRoomPay.getSeq() == 1){//序列号为1的表示押金
						continue;
					}
					interruptSeq = tenRoomPay.getSeq();
					
					//计算要扣掉的应付金额,也就是变动日期到该租期结束日期之间的租金金额
					reducedAppPayAmount = TenancyHelper.getRentBetweenDate(bizDate, payEndDate, oldTenRoom.getDealRentType(), oldTenRoom.getDealRoomRent());
					
					if(appPayAmount == null)appPayAmount = FDCHelper.ZERO;
					
					BigDecimal tmp = appPayAmount.subtract(reducedAppPayAmount);//计算原应付金额与扣掉的金额的差
					
					if(tmp.compareTo(FDCHelper.ZERO) >= 0){//该期原应付金额大于等于要扣掉的金额
						tenRoomPay.setAppAmount(tmp);
						reducedAppPayAmount = FDCHelper.ZERO;
					}else{//该期原应付金额不够扣掉的金额
						tenRoomPay.setAppAmount(FDCHelper.ZERO);
						reducedAppPayAmount = reducedAppPayAmount.subtract(appPayAmount);
					}
					
					if(actPayAmount != null  &&  actPayAmount.compareTo(FDCHelper.ZERO) != 0){
						oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actPayAmount.subtract(tenRoomPay.getAppAmount()));
					}
					
					if(isExecuteToDB)updateAppAmount(ctx, tenRoomPay);
				}else{
					logger.warn("脏数据或者逻辑错误.tenRoomPay ID:" + tenRoomPay.getId().toString());
					throw new BOSException("脏数据或者逻辑错误.");
				}
			}
			
			//被截断那期原应付金额时不够扣除的应付金额时,应该在被截断租期前面的租期中继续扣除
			for(int j=interruptSeq - 1; j>0 && reducedAppPayAmount != null && reducedAppPayAmount.compareTo(FDCHelper.ZERO) > 0; j--){
				TenancyRoomPayListEntryInfo tenRoomPayOfSeq = getTenRoomPayOfSeq(tenRoomPayList, j);
				if(tenRoomPayOfSeq == null){
					logger.warn("脏数据或者逻辑错误.tenRoom ID:" + oldTenRoom.getId().toString());
					continue;
				}
				BigDecimal appAmount = tenRoomPayOfSeq.getAppAmount();
				BigDecimal actAmount = tenRoomPayOfSeq.getActAmount();
				
				if(appAmount == null)appAmount = FDCHelper.ZERO;
				
				BigDecimal tmp = appAmount.subtract(reducedAppPayAmount);//计算原应付金额与扣掉的金额的差
				if(tmp.compareTo(FDCHelper.ZERO) >= 0){
					tenRoomPayOfSeq.setAppAmount(tmp);
					reducedAppPayAmount = FDCHelper.ZERO;
				}else{
					tenRoomPayOfSeq.setAppAmount(FDCHelper.ZERO);
					reducedAppPayAmount = reducedAppPayAmount.subtract(appAmount);
				}
				if(actAmount != null  &&  actAmount.compareTo(FDCHelper.ZERO) != 0){
					oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actAmount.subtract(tenRoomPayOfSeq.getAppAmount()));
				}
				
				if(isExecuteToDB)updateAppAmount(ctx, tenRoomPayOfSeq);
			}
			
			if(reducedAppPayAmount != null  &&  reducedAppPayAmount.compareTo(FDCHelper.ZERO) > 0){
				throw new BOSException("逻辑错误啦啦啦.");
			}
			
			//更新原租赁房间的剩余押金
			if(oldTenRoom.getRemainDepositAmount() == null  ||  oldRoomRemainDepositAmount.compareTo(oldTenRoom.getRemainDepositAmount()) != 0){
				oldTenRoom.setRemainDepositAmount(oldRoomRemainDepositAmount);
				if(isExecuteToDB)TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(oldTenRoom, sels);
			}
			oldTenRemainDepositAmount = oldTenRemainDepositAmount.add(oldRoomRemainDepositAmount);
		}
		
		if(oldTenBill.getRemainDepositAmount() == null  ||  oldTenRemainDepositAmount.compareTo(oldTenBill.getRemainDepositAmount()) != 0){
			oldTenBill.setRemainDepositAmount(oldTenRemainDepositAmount);
			if(isExecuteToDB)updatePartial(ctx, oldTenBill, sels);
		}
		*/
    }
    
	/**
     * 反写原合同及原合同租赁房间的到期标记
     * TODO 配套资源的反写
     * @param ctx
     * @param tenBill
     * @param oldTenBill
     * @param targetOldBillFlag
     * 			原合同的目标到期标记
     * @param oldRoomFlag
     * 			原合同的房间在新合同存在时,原合同房间的目标到期标记
     * @param oldRoomFlag2
     * 			原合同的房间在新合同不存在时,原合同房间的目标到期标记
     * */
    private void setOldTenancyFlagAtTerm(Context ctx, TenancyBillInfo tenBill, TenancyBillInfo oldTenBill, FlagAtTermEnum targetOldBillFlag, FlagAtTermEnum oldRoomFlag, FlagAtTermEnum oldRoomFlag2) throws EASBizException, BOSException{
    	TenancyRoomEntryCollection tenRooms = tenBill.getTenancyRoomList();
    	TenAttachResourceEntryCollection tenAttachs = tenBill.getTenAttachResourceList();
    	oldTenBill.setFlagAtTerm(targetOldBillFlag);
    	SelectorItemCollection updateFlagAtTermSels = new SelectorItemCollection();
    	updateFlagAtTermSels.add("flagAtTerm");
    	updatePartial(ctx, oldTenBill, updateFlagAtTermSels);
		TenancyRoomEntryCollection oldTenRooms = oldTenBill.getTenancyRoomList();
    	TenAttachResourceEntryCollection oldTenAttachs = oldTenBill.getTenAttachResourceList();
		
		for(int i=0; i<oldTenRooms.size(); i++){
			TenancyRoomEntryInfo oldTenRoom = oldTenRooms.get(i);
			String oldRoomId = oldTenRoom.getRoom().getId().toString();
			
			boolean existInNewBill = false;//原合同的房间在新合同中是否存在
			for(int j=0; j<tenRooms.size(); j++){
				TenancyRoomEntryInfo tenRoom = tenRooms.get(j);
				String roomId = tenRoom.getRoom().getId().toString();
    			if(roomId.equals(oldRoomId)){
    				existInNewBill = true;
    				break;
    			}
			}
			if(existInNewBill){
				oldTenRoom.setFlagAtTerm(oldRoomFlag);
			}else{
				oldTenRoom.setFlagAtTerm(oldRoomFlag2);
			}
			TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(oldTenRoom, updateFlagAtTermSels);
		}
		
		for(int i=0; i<oldTenAttachs.size(); i++){
			TenAttachResourceEntryInfo oldTenAttach = oldTenAttachs.get(i);
			String oldAttachId = oldTenAttach.getAttachResource().getId().toString();
			
			boolean existInNewBill = false;//原合同的配套资源在新合同中是否存在
			for(int j=0; j<tenAttachs.size(); j++){
				TenAttachResourceEntryInfo tenAttach = tenAttachs.get(j);
				String attachId = tenAttach.getAttachResource().getId().toString();
    			if(attachId.equals(oldAttachId)){
    				existInNewBill = true;
    				break;
    			}
			}
			if(existInNewBill){
				oldTenAttach.setFlagAtTerm(oldRoomFlag);
			}else{
				oldTenAttach.setFlagAtTerm(oldRoomFlag2);
			}
			TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(oldTenAttach, updateFlagAtTermSels);
		}
    }

    /**
     * 更新应付金额字段
     * */
	private void updateAppAmount(Context ctx, TenancyRoomPayListEntryInfo tenRoomPay) throws BOSException, EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("appAmount");
		TenancyRoomPayListEntryFactory.getLocalInstance(ctx).updatePartial(tenRoomPay, sels);
	}
    
    /**
     * 根据序号获得付款明细集合中对应的明细记录
     * */
    private TenancyRoomPayListEntryInfo getTenRoomPayOfSeq(TenancyRoomPayListEntryCollection tenRoomPayList, int seq) {
    	for(int i=0; i<tenRoomPayList.size(); i++){
			TenancyRoomPayListEntryInfo tenRoomPay = tenRoomPayList.get(i);
			if(tenRoomPay.getSeq() == seq){
				return tenRoomPay;
			}
		}
    	return null;
	}

	/**
     * 续租审批时自动进行资金结转
     * TODO 先不实现自动结转,和改租一样在界面手动结转
     * */
	private void carryForward1(TenancyBillInfo newTenBill, TenancyBillInfo oldTenBill) {
		
		
		
		
		
		
	}
	
	/**
	 * 进行资金结转,其实就是把收款单和红字收款单以事物方式提交
	 * */
	protected void _carryForward(Context ctx, IObjectCollection receivingBills) throws BOSException, EASBizException {
		FDCReceiveBillFactory.getLocalInstance(ctx).submitByCasRevColl((ReceivingBillCollection) receivingBills);
	}
	
	/** 租赁合同作废 */
	protected void _blankOut(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		TenancyBillInfo ten = this.getTenancyBillInfo(ctx, pk);
		
		TenancyBillStateEnum srcState = ten.getTenancyState();
		
		if(!TenancyBillStateEnum.Saved.equals(srcState) && !TenancyBillStateEnum.Submitted.equals(srcState) && 
				!TenancyBillStateEnum.Auditing.equals(srcState) && !TenancyBillStateEnum.Audited.equals(srcState) /*&& 
				!TenancyBillStateEnum.DepositReved.equals(srcState)*/ ){
			throw new BOSException("已保存,已提交,审批中,已审批,已收首租和押金状态的合同才可以作废");//TODO 定义业务异常
		}
		
		updateTenancyState(ctx, ten, TenancyBillStateEnum.BlankOut);
		
		//将该租赁合同的相关工作流停止
		ProcessInstInfo instInfo = null;
		ProcessInstInfo[] procInsts = null;
		IEnactmentService service = EnactmentServiceFactory.createEnactService(ctx);
		procInsts = service.getProcessInstanceByHoldedObjectId(ten.getId().toString());
		for (int j = 0; j < procInsts.length; j++) {
			if ("open.running".equals(procInsts[j].getState())) {
				instInfo = procInsts[j];
				service.abortProcessInst(instInfo.getProcInstId());
			}
		}
	}
	

	/**
	 * 接口方法
	 * 从租赁交接界面传过来的参数，做最终的交接操作,因为是需要对每个房间分录来做处理，避免在客户端进行循环远程调用操作
	 * @author laiquan_luo
	 */
	protected void _handleTenancyRoom(Context ctx,IObjectCollection tenAttachEntryColl, IObjectCollection tenancyRoomEntryColl, IObjectValue billInfo,IObjectCollection handleRoomEntryColl) throws BOSException
	{

		if (tenancyRoomEntryColl == null || billInfo == null)
		{
			logger.warn("传过来的参数有为空的！请检查....");
			return;
		}
		
		TenancyBillInfo tenancyBillInfo = (TenancyBillInfo) billInfo;

		/*这里对每个租赁配套资源分录进行处理，逻辑基本上和房间分录的处理方式一样*/
		for(int i=0;i< tenAttachEntryColl.size();i++)
		{
			TenAttachResourceEntryInfo tenAttachEntryInfo = (TenAttachResourceEntryInfo)tenAttachEntryColl.getObject(i);			
			BigDecimal depositAmount = tenAttachEntryInfo.getDepositAmount();
			depositAmount = depositAmount==null?new BigDecimal(0):depositAmount;
        	BigDecimal firstPayRent = tenAttachEntryInfo.getFirstPayAmount();
        	firstPayRent = firstPayRent==null?new BigDecimal(0):firstPayRent;
        	String attachid = tenAttachEntryInfo.getAttachResource().getId().toString();
        	BigDecimal rent = depositAmount.add(firstPayRent);
        	BigDecimal recRent  = this.getAttachAmount(ctx, attachid, tenancyBillInfo.getId().toString());
        	String handleType = "";
        	Date finishDate = new Date();
        	for(int j=0;j<handleRoomEntryColl.size();j++)
        	{
        		HandleRoomEntrysInfo handleRoomInfo = (HandleRoomEntrysInfo)handleRoomEntryColl.getObject(j);
        		if(handleRoomInfo.getAttach()!=null)
        		{
        			if(tenAttachEntryInfo.getId().toString().equals(handleRoomInfo.getAttach().getId().toString()))
            		{
            			tenAttachEntryInfo.setHandleState(handleRoomInfo.getNewHandleState());
            			handleType = handleRoomInfo.getHandleType();
            			finishDate = handleRoomInfo.getFinishDate();
            		}
        		}        		
        	}
        	// 当前处理的合同是 已收押金首租 或 半执行状态 则说明现在这个操作是 交房
//			if ((TenancyBillStateEnum.Audited.equals(tenancyBillInfo.getTenancyState()) && recRent.compareTo(rent)>=0)
//					|| TenancyBillStateEnum.PartExecuted.equals(tenancyBillInfo.getTenancyState()))
        	if (TenancyBillStateEnum.Audited.equals(tenancyBillInfo.getTenancyState()) 
        			|| TenancyBillStateEnum.PartExecuted.equals(tenancyBillInfo.getTenancyState()))
			{
				/*
				 * 分合同类型处理
				 */
				// 如果是新租的合同,这种情况就处理单一的当前合同即可。
				if (TenancyContractTypeEnum.NewTenancy.equals(tenancyBillInfo
						.getTenancyType()) || handleType.equals("交房"))
				{
					tenAttachEntryInfo.setActDeliveryAttachResDate(finishDate);
				}//如果是续租的
				else if(TenancyContractTypeEnum.ContinueTenancy.equals(tenancyBillInfo.getTenancyType()) && handleType.equals("续租交接"))
				{
					TenancyBillInfo oldTenancyBillInfo = tenancyBillInfo.getOldTenancyBill();
					if(oldTenancyBillInfo == null)
					{
						logger.warn("此续租类型的合同，居然没有找到原合同，请先查看是否脏数据，再查找合同录入那边逻辑控制！");
						throw new BOSException("出现程序错误，已记录日志！");
					}
					//房间分录交房日期置为合同的开始日期
					tenAttachEntryInfo.setActDeliveryAttachResDate(tenancyBillInfo.getStartDate());
					//原合同房间分录的收房日期置为原合同的结束日期
					this.updateOldTenancyAttachEntryInfo(ctx,oldTenancyBillInfo,tenAttachEntryInfo,oldTenancyBillInfo.getEndDate(),"shoufang");
				}//如果是改租
				else if(TenancyContractTypeEnum.RejiggerTenancy.equals(tenancyBillInfo.getTenancyType()) && handleType.equals("改租交接"))
				{
					TenancyBillInfo oldTenancyBillInfo = tenancyBillInfo.getOldTenancyBill();
					if(oldTenancyBillInfo == null)
					{
						logger.warn("此续租类型的合同，居然没有找到原合同，请先查看是否脏数据，再查找合同录入那边逻辑控制！");
						throw new BOSException("出现程序错误，已记录日志！");
					}
					//房间分录交房日期置为当前日期
					tenAttachEntryInfo.setActDeliveryAttachResDate(finishDate);
					//原合同房间分录的收房日期置为当前日期
					this.updateOldTenancyAttachEntryInfo(ctx,oldTenancyBillInfo,tenAttachEntryInfo,new Date(),"shoufang");
				}//如果是转名 跟改租一样的处理
				else if(TenancyContractTypeEnum.ChangeName.equals(tenancyBillInfo.getTenancyType()))
				{
					TenancyBillInfo oldTenancyBillInfo = tenancyBillInfo.getOldTenancyBill();
					if(oldTenancyBillInfo == null)
					{
						logger.warn("此续租类型的合同，居然没有找到原合同，请先查看是否脏数据，再查找合同录入那边逻辑控制！");
						throw new BOSException("出现程序错误，已记录日志！");
					}
					//房间分录交房日期置为当前日期
					tenAttachEntryInfo.setActDeliveryAttachResDate(finishDate);
					//原合同房间分录的收房日期置为当前日期
					this.updateOldTenancyAttachEntryInfo(ctx,oldTenancyBillInfo,tenAttachEntryInfo,new Date(),"shoufang");
				}
				else
				{
					logger.warn("没有处理该类型的交接...");
					throw new BOSException("没有处理该类型的交接");
				}
				//更新时间
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryAttachResDate");
				selector.add("handleState");
				try
				{
					TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(tenAttachEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
				this.setAttachTenState(ctx,tenAttachEntryInfo.getAttachResource(),tenAttachEntryInfo.getTenAttachState());
			}//如果是改租中状态肯定是退房，而且如果房间在新合同里是没有办法进行交接动作的。所以在这也不需要验证了
			else if(TenancyBillStateEnum.RejiggerTenancying.equals(tenancyBillInfo.getTenancyState()))
			{
				//房间分录收房日期置为当前日期
				tenAttachEntryInfo.setActQuitTenDate(finishDate);
				//房间分录的交房日期置为当前日期
				this.setAttachTenState(ctx,tenAttachEntryInfo.getAttachResource(),TenancyStateEnum.waitTenancy);
				//更新房源状态
				//this.updateRoomStateByNewTenancyRoomEntryInfo(ctx,tenancyBillInfo,tenancyRoomEntryInfo);
				//更新时间
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryAttachResDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(tenAttachEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
			}else if(TenancyBillStateEnum.ContinueTenancying.equals(tenancyBillInfo.getTenancyState()))
			{
				//房间分录收房日期设置为合同的结束日期
				tenAttachEntryInfo.setActQuitTenDate(finishDate);
				//原合同房间分录的收房日期置为原合同的结束日期
				this.setAttachTenState(ctx,tenAttachEntryInfo.getAttachResource(),TenancyStateEnum.waitTenancy);
				//this.updateRoomStateByNewTenancyRoomEntryInfo(ctx,tenancyBillInfo,tenancyRoomEntryInfo);
				//更新时间
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryAttachResDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(tenAttachEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
				//改名和改租操作一样
			}else if(TenancyBillStateEnum.ChangeNaming.equals(tenancyBillInfo.getTenancyState()))
			{
				//房间分录收房日期置为当前日期
				tenAttachEntryInfo.setActQuitTenDate(finishDate);
				//房间分录的交房日期置为当前日期
				this.setAttachTenState(ctx,tenAttachEntryInfo.getAttachResource(),TenancyStateEnum.waitTenancy);
				//this.updateRoomStateByNewTenancyRoomEntryInfo(ctx,tenancyBillInfo,tenancyRoomEntryInfo);
				//更新时间
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryAttachResDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(tenAttachEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
			}else if(TenancyBillStateEnum.QuitTenancying.equals(tenancyBillInfo.getTenancyState()))
			{
				//房间分录收房日期置为当前日期
				tenAttachEntryInfo.setActQuitTenDate(finishDate);
				//房间分录的交房日期置为当前日期
				this.setAttachTenState(ctx,tenAttachEntryInfo.getAttachResource(),TenancyStateEnum.waitTenancy);
				//更新时间
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryRoomDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(tenAttachEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
			}
		}
		/*
		 * 分别去对每个房间分录进行处理，这里做的是一些 房间状态，和时间的一些设置，
		 * 至于那些自动退房的，改合同状态等乱七八糟的操作，等这里处理完成之后在客户端进行验证，再操作，崩溃...
		 */
		for (int i = 0; i < tenancyRoomEntryColl.size(); i++)
		{
			TenancyRoomEntryInfo tenancyRoomEntryInfo = (TenancyRoomEntryInfo) tenancyRoomEntryColl.getObject(i);
			DealAmountEntryCollection dealColl = tenancyRoomEntryInfo.getDealAmounts();
			BigDecimal depositAmount = new BigDecimal(0);
			for(int j=0;j<dealColl.size();j++)
			{
				DealAmountEntryInfo dealInfo = dealColl.get(j);
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("*");
				sel.add("moneyDefine.*");
				try {
					dealInfo = DealAmountEntryFactory.getLocalInstance(ctx).getDealAmountEntryInfo(new ObjectUuidPK(dealInfo.getId()), sel);
				} catch (EASBizException e) {
					e.printStackTrace();
				}
				if(MoneyTypeEnum.DepositAmount.equals(dealInfo.getMoneyDefine().getMoneyType()))
				{
					BigDecimal amount = dealInfo.getAmount();
					amount = amount==null?new BigDecimal(0):amount;
					depositAmount = depositAmount.add(amount);
				}
			}
//			BigDecimal depositAmount = tenancyRoomEntryInfo.getDepositAmount();
//			depositAmount = depositAmount==null?new BigDecimal(0):depositAmount;
        	BigDecimal firstPayRent = tenancyRoomEntryInfo.getFirstPayAmount();
        	firstPayRent = firstPayRent==null?new BigDecimal(0):firstPayRent;
        	String roomid = tenancyRoomEntryInfo.getRoom().getId().toString();
        	BigDecimal rent = depositAmount.add(firstPayRent);
        	BigDecimal recRent = this.getAmount(ctx,roomid,tenancyBillInfo.getId().toString());
        	String handleType = new String();
        	Date finishDate = new Date();
        	for(int j=0;j<handleRoomEntryColl.size();j++)
        	{
        		HandleRoomEntrysInfo handleRoomInfo = (HandleRoomEntrysInfo)handleRoomEntryColl.getObject(j);
        		if(handleRoomInfo.getTenancyRoom()!=null)
        		{
        			if(tenancyRoomEntryInfo.getId().toString().equals(handleRoomInfo.getTenancyRoom().getId().toString()))
            		{
            			tenancyRoomEntryInfo.setHandleState(handleRoomInfo.getNewHandleState());
            			handleType = handleRoomInfo.getHandleType();
            			finishDate = handleRoomInfo.getFinishDate();
            		}
        		}      		
        	}
			// 当前处理的合同是 已收押金首租 或 半执行状态 则说明现在这个操作是 交房
//			if ((TenancyBillStateEnum.Audited.equals(tenancyBillInfo.getTenancyState()) && recRent.compareTo(rent)>=0)
//					|| TenancyBillStateEnum.PartExecuted.equals(tenancyBillInfo.getTenancyState()))
				if (TenancyBillStateEnum.Audited.equals(tenancyBillInfo.getTenancyState())
						|| TenancyBillStateEnum.PartExecuted.equals(tenancyBillInfo.getTenancyState()))
			{
				/*
				 * 分合同类型处理
				 */
				// 如果是新租的合同,这种情况就处理单一的当前合同即可。
				if (TenancyContractTypeEnum.NewTenancy.equals(tenancyBillInfo
						.getTenancyType()) || handleType.equals("交房"))
				{
					tenancyRoomEntryInfo.setActDeliveryRoomDate(finishDate);
				}//如果是续租的
				else if(TenancyContractTypeEnum.ContinueTenancy.equals(tenancyBillInfo.getTenancyType()) && handleType.equals("续租交接"))
				{
					TenancyBillInfo oldTenancyBillInfo = tenancyBillInfo.getOldTenancyBill();
					if(oldTenancyBillInfo == null)
					{
						logger.warn("此续租类型的合同，居然没有找到原合同，请先查看是否脏数据，再查找合同录入那边逻辑控制！");
						throw new BOSException("出现程序错误，已记录日志！");
					}
					//房间分录交房日期置为合同的开始日期
					tenancyRoomEntryInfo.setActDeliveryRoomDate(tenancyBillInfo.getStartDate());
					//原合同房间分录的收房日期置为原合同的结束日期
					this.updateOldTenancyRoomEntryInfo(ctx,oldTenancyBillInfo,tenancyRoomEntryInfo,oldTenancyBillInfo.getEndDate(),"shoufang");
				}//如果是改租
				else if(TenancyContractTypeEnum.RejiggerTenancy.equals(tenancyBillInfo.getTenancyType()) && handleType.equals("改租交接"))
				{
					TenancyBillInfo oldTenancyBillInfo = tenancyBillInfo.getOldTenancyBill();
					if(oldTenancyBillInfo == null)
					{
						logger.warn("此续租类型的合同，居然没有找到原合同，请先查看是否脏数据，再查找合同录入那边逻辑控制！");
						throw new BOSException("出现程序错误，已记录日志！");
					}
					//房间分录交房日期置为当前日期
					tenancyRoomEntryInfo.setActDeliveryRoomDate(finishDate);
					//原合同房间分录的收房日期置为当前日期
					this.updateOldTenancyRoomEntryInfo(ctx,oldTenancyBillInfo,tenancyRoomEntryInfo,new Date(),"shoufang");
				}//如果是转名 跟改租一样的处理
				else if(TenancyContractTypeEnum.ChangeName.equals(tenancyBillInfo.getTenancyType()))
				{
					TenancyBillInfo oldTenancyBillInfo = tenancyBillInfo.getOldTenancyBill();
					if(oldTenancyBillInfo == null)
					{
						logger.warn("此续租类型的合同，居然没有找到原合同，请先查看是否脏数据，再查找合同录入那边逻辑控制！");
						throw new BOSException("出现程序错误，已记录日志！");
					}
					//房间分录交房日期置为当前日期
					tenancyRoomEntryInfo.setActDeliveryRoomDate(finishDate);
					//原合同房间分录的收房日期置为当前日期
					this.updateOldTenancyRoomEntryInfo(ctx,oldTenancyBillInfo,tenancyRoomEntryInfo,new Date(),"shoufang");
				}//如果是价格变更 跟转名一样的处理
				else if(TenancyContractTypeEnum.PriceChange.equals(tenancyBillInfo.getTenancyType()))
				{
					TenancyBillInfo oldTenancyBillInfo = tenancyBillInfo.getOldTenancyBill();
					if(oldTenancyBillInfo == null)
					{
						logger.warn("此续租类型的合同，居然没有找到原合同，请先查看是否脏数据，再查找合同录入那边逻辑控制！");
						throw new BOSException("出现程序错误，已记录日志！");
					}
					//房间分录交房日期置为当前日期
					tenancyRoomEntryInfo.setActDeliveryRoomDate(finishDate);
					//原合同房间分录的收房日期置为当前日期
					this.updateOldTenancyRoomEntryInfo(ctx,oldTenancyBillInfo,tenancyRoomEntryInfo,new Date(),"shoufang");
				}
				else
				{
					logger.warn("没有处理该类型的交接...");
					throw new BOSException("没有处理该类型的交接");
				}
				//更新时间
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryRoomDate");
				selector.add("handleState");
				try
				{
					TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenancyRoomEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
				this.setRoomTenancyState(ctx,tenancyRoomEntryInfo.getRoom(),tenancyRoomEntryInfo.getTenRoomState(), tenancyBillInfo);
			}
			//如果是改租中状态肯定是退房，而且如果房间在新合同里是没有办法进行交接动作的。所以在这也不需要验证了
			else if(TenancyBillStateEnum.RejiggerTenancying.equals(tenancyBillInfo.getTenancyState()))
			{
				//房间分录收房日期置为当前日期
				tenancyRoomEntryInfo.setActQuitTenDate(finishDate);
				//房间分录的交房日期置为当前日期
				this.setRoomTenancyState(ctx,tenancyRoomEntryInfo.getRoom(),TenancyStateEnum.waitTenancy, null);
				//更新房源状态
				//this.updateRoomStateByNewTenancyRoomEntryInfo(ctx,tenancyBillInfo,tenancyRoomEntryInfo);
				//更新时间
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryRoomDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenancyRoomEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
			}else if(TenancyBillStateEnum.ContinueTenancying.equals(tenancyBillInfo.getTenancyState()))
			{
				//房间分录收房日期设置为合同的结束日期
				tenancyRoomEntryInfo.setActQuitTenDate(finishDate);
				//原合同房间分录的收房日期置为原合同的结束日期
				this.setRoomTenancyState(ctx,tenancyRoomEntryInfo.getRoom(),TenancyStateEnum.waitTenancy, null);
				//this.updateRoomStateByNewTenancyRoomEntryInfo(ctx,tenancyBillInfo,tenancyRoomEntryInfo);
				//更新时间
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryRoomDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenancyRoomEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
				//改名和改租操作一样
			}else if(TenancyBillStateEnum.ChangeNaming.equals(tenancyBillInfo.getTenancyState()))
			{
				//房间分录收房日期置为当前日期
				tenancyRoomEntryInfo.setActQuitTenDate(finishDate);
				//房间分录的交房日期置为当前日期
				this.setRoomTenancyState(ctx,tenancyRoomEntryInfo.getRoom(),TenancyStateEnum.waitTenancy, null);
				//this.updateRoomStateByNewTenancyRoomEntryInfo(ctx,tenancyBillInfo,tenancyRoomEntryInfo);
				//更新时间
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryRoomDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenancyRoomEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
			}else if(TenancyBillStateEnum.PriceChanging.equals(tenancyBillInfo.getTenancyState()))
			{
				//房间分录收房日期置为当前日期
				tenancyRoomEntryInfo.setActQuitTenDate(finishDate);
				//房间分录的交房日期置为当前日期
				this.setRoomTenancyState(ctx,tenancyRoomEntryInfo.getRoom(),TenancyStateEnum.waitTenancy, null);
				//this.updateRoomStateByNewTenancyRoomEntryInfo(ctx,tenancyBillInfo,tenancyRoomEntryInfo);
				//更新时间
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryRoomDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenancyRoomEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
			}else if(TenancyBillStateEnum.QuitTenancying.equals(tenancyBillInfo.getTenancyState()))
			{
				//房间分录收房日期置为当前日期
				tenancyRoomEntryInfo.setActQuitTenDate(finishDate);
				//房间分录的交房日期置为当前日期
				this.setRoomTenancyState(ctx,tenancyRoomEntryInfo.getRoom(),TenancyStateEnum.waitTenancy, null);
				//更新时间
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("actDeliveryRoomDate");
				selector.add("actQuitTenDate");
				selector.add("handleState");
				try
				{
					TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenancyRoomEntryInfo,selector);
				} catch (EASBizException e)
				{
					throw new BOSException(e);
				}
			}
		}
	}
	
	/**
	 * 修改房源上面房间的状态
	 * @param ctx
	 * @param room
	 * @param tenancyState
	 * @param lastTen
	 * @author laiquan_luo
	 * @throws BOSException 
	 * 增加对房间lastTenancy的反写   modified by zhicheng_jin
	 */
	public void setRoomTenancyState(Context ctx,RoomInfo room,TenancyStateEnum tenancyState, TenancyBillInfo lastTen) throws BOSException
	{
		SelectorItemCollection selColl = new SelectorItemCollection();
		
		selColl.add("tenancyState");
		selColl.add("lastTenancy");
		room.setTenancyState(tenancyState);
		room.setLastTenancy(lastTen);
		try
		{
			RoomFactory.getLocalInstance(ctx).updatePartial(room,selColl);
		} catch (EASBizException e)
		{
			throw new BOSException(e);
		} catch (BOSException e)
		{
			throw new BOSException(e);
		}
	}
	
	/**
	 * 修改房源上面配套资源的状态
	 * @param ctx
	 * @param room
	 * @param tenancyState
	 * @param lastTen
	 * @author laiquan_luo
	 * @throws BOSException 
	 * 增加对房间lastTenancy的反写   modified by zhicheng_jin
	 */
	public void setAttachTenState(Context ctx,AttachResourceInfo attachInfo,TenancyStateEnum tenancyState) throws BOSException
	{
		SelectorItemCollection selColl = new SelectorItemCollection();
		
		selColl.add("tenancyState");
		attachInfo.setTenancyState(tenancyState);
		try
		{
			AttachResourceFactory.getLocalInstance(ctx).updatePartial(attachInfo,selColl);
		} catch (EASBizException e)
		{
			throw new BOSException(e);
		} catch (BOSException e)
		{
			throw new BOSException(e);
		}
	}
	
	/**
	 * 更新该房间的它所对应的原合同上的房间分录的交房或者收房日期 
	 * @param oldTenancyBillInfo
	 * @param tenancyRoomEntryInfo
	 * @param date
	 * @param type  限定为字符串  jiaofang   shoufang
	 * @throws BOSException 
	 * @author laiquan_luo
	 */
	private void updateOldTenancyAttachEntryInfo(Context ctx,TenancyBillInfo oldTenancyBillInfo,TenAttachResourceEntryInfo tenAttachEntryInfo,Date date,String type) throws BOSException
	{
		if(oldTenancyBillInfo == null || tenAttachEntryInfo == null || date == null || type == null)
		{
			logger.warn("传过来的参数有为空的！请检查....");
			return;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id",oldTenancyBillInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("attachResource.id",tenAttachEntryInfo.getAttachResource().getId().toString()));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		//这里做容错吧
		TenAttachResourceEntryCollection oldTenAttachEntryColl = new TenAttachResourceEntryCollection();
		
		oldTenAttachEntryColl = TenAttachResourceEntryFactory.getLocalInstance(ctx).getTenAttachResourceEntryCollection(view);
		//不会出现大于一个的现象，但是会有为0的现象
		if(oldTenAttachEntryColl.size() > 1)
		{
			logger.warn("该配套资源在源合同里面找到了多个配套资源分录信息...");
			throw new BOSException("程序逻辑错误，已记录日志！");
		}
		TenAttachResourceEntryInfo oldTenAttachEntryInfo = oldTenAttachEntryColl.get(0);
		SelectorItemCollection selectorItemColl = new SelectorItemCollection();
		//收房时间
		if("shoufang".equalsIgnoreCase(type))
		{
			oldTenAttachEntryInfo.setActQuitTenDate(date);
			selectorItemColl.add("actQuitTenDate");
			oldTenAttachEntryInfo.setHandleState(HandleStateEnum.AlreadyCallBack);
			selectorItemColl.add("handleState");
		}//交房
		else if("jiaofang".equalsIgnoreCase(type))
		{
			oldTenAttachEntryInfo.setActDeliveryAttachResDate(date);
			selectorItemColl.add("actDeliveryRoomDate");
			oldTenAttachEntryInfo.setHandleState(HandleStateEnum.livingHouse);
			selectorItemColl.add("handleState");
		}
		try
		{
			TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(oldTenAttachEntryInfo,selectorItemColl);
		} catch (EASBizException e)
		{	
			throw new BOSException(e);
		} 
		
	}
	
	/**
	 * 更新该房间的它所对应的原合同上的房间分录的交房或者收房日期 
	 * @param oldTenancyBillInfo
	 * @param tenancyRoomEntryInfo
	 * @param date
	 * @param type  限定为字符串  jiaofang   shoufang
	 * @throws BOSException 
	 * @author laiquan_luo
	 */
	private void updateOldTenancyRoomEntryInfo(Context ctx,TenancyBillInfo oldTenancyBillInfo,TenancyRoomEntryInfo tenancyRoomEntryInfo,Date date,String type) throws BOSException
	{
		if(oldTenancyBillInfo == null || tenancyRoomEntryInfo == null || date == null || type == null)
		{
			logger.warn("传过来的参数有为空的！请检查....");
			return;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancy.id",oldTenancyBillInfo.getId().toString()));
//		filter.getFilterItems().add(new FilterItemInfo("room.id",tenancyRoomEntryInfo.getRoom().getId().toString()));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		//这里做容错吧
		TenancyRoomEntryCollection oldTenancyRoomEntryColl = new TenancyRoomEntryCollection();
		
		oldTenancyRoomEntryColl = TenancyRoomEntryFactory.getLocalInstance(ctx).getTenancyRoomEntryCollection(view);
		//不会出现大于一个的现象，但是会有为0的现象
//		if(oldTenancyRoomEntryColl.size() > 1)
//		{
//			logger.warn("该房间在源合同里面找到了多个房间分录信息...");
//			throw new BOSException("程序逻辑错误，已记录日志！");
//		}
		//当新合同做房间交接时，强制性的把老合同的房间做收房处理 eric_wang 2010.08.31
		for(int i=0;i<oldTenancyRoomEntryColl.size();i++){
			TenancyRoomEntryInfo oldTenancyRoomEntryInfo = oldTenancyRoomEntryColl.get(i);
			SelectorItemCollection selectorItemColl = new SelectorItemCollection();
			//收房时间
			if("shoufang".equalsIgnoreCase(type))
			{
				oldTenancyRoomEntryInfo.setActQuitTenDate(date);
				selectorItemColl.add("actQuitTenDate");
				oldTenancyRoomEntryInfo.setHandleState(HandleStateEnum.AlreadyCallBack);
				selectorItemColl.add("handleState");
			}//交房
			else if("jiaofang".equalsIgnoreCase(type))
			{
				oldTenancyRoomEntryInfo.setActDeliveryRoomDate(date);
				selectorItemColl.add("actDeliveryRoomDate");
				oldTenancyRoomEntryInfo.setHandleState(HandleStateEnum.livingHouse);
				selectorItemColl.add("handleState");
			}
			try
			{
				TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(oldTenancyRoomEntryInfo,selectorItemColl);
			} catch (EASBizException e)
			{	
				throw new BOSException(e);
			} 
		}
		
		
	}
	
	/**
	 * 更新该房间的它所对应的新合同上的房间分录的交房或者收房日期
	 * @param ctx
	 * @param newTenancyBillInfo
	 * @param tenancyRoomEntryInfo
	 * @param date
	 * @param type
	 * @throws BOSException
	 * @author laiquan_luo
	 */
	private void updateNewTenancyRoomEntryInfo(Context ctx,TenancyBillInfo newTenancyBillInfo,TenancyRoomEntryInfo tenancyRoomEntryInfo,Date date,String type) throws BOSException
	{
		if(newTenancyBillInfo == null || tenancyRoomEntryInfo == null || date == null || type == null)
		{
			logger.warn("传过来的参数有为空的！请检查....");
			throw new BOSException("参数为空！");
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancy.id",newTenancyBillInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("room.id",tenancyRoomEntryInfo.getRoom().getId().toString()));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		
		TenancyRoomEntryCollection newTenancyRoomEntryColl = null;
		
		newTenancyRoomEntryColl = TenancyRoomEntryFactory.getLocalInstance(ctx).getTenancyRoomEntryCollection(view);
		//不会出现大于一个的现象，但是会有为0的现象
		if(newTenancyRoomEntryColl.size() > 1)
		{
			logger.warn("该房间在源合同里面找到了多个房间分录信息...");
			throw new BOSException("程序逻辑错误，已记录日志！");
		}
		//为0的时候证明在原合同有的时间，在新合同不一定有，就不可以不做下面的操作了。
		if(newTenancyRoomEntryColl.get(0) == null)
			return;
		
		TenancyRoomEntryInfo newTenancyRoomEntryInfo = newTenancyRoomEntryColl.get(0);
		SelectorItemCollection selectorItemColl = new SelectorItemCollection();
		//收房时间
		if("shoufang".equalsIgnoreCase(type))
		{
			newTenancyRoomEntryInfo.setActQuitTenDate(date);
			selectorItemColl.add("actQuitTenDate");
		}//交房
		else if("jiaofang".equalsIgnoreCase(type))
		{
			newTenancyRoomEntryInfo.setActDeliveryRoomDate(date);
			selectorItemColl.add("actDeliveryRoomDate");
		}
		try
		{
			TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(newTenancyRoomEntryInfo,selectorItemColl);
		} catch (EASBizException e)
		{	
			throw new BOSException(e);
		} 
	}
	
	/**
	 * 修改房源那边房间的状态，这个状态的修改是根据新合同的这个房间分录的状态
	 * @param ctx
	 * @param newTenancyBillInfo
	 * @param tenancyRoomEntryInfo
	 * @throws BOSException
	 * @author laiquan_luo
	 */
	private void updateRoomStateByNewTenancyRoomEntryInfo(Context ctx,TenancyBillInfo newTenancyBillInfo,TenancyRoomEntryInfo tenancyRoomEntryInfo) throws BOSException
	{
		if(newTenancyBillInfo == null || tenancyRoomEntryInfo == null)
		{
			logger.warn("传过来的参数有为空的！请检查....");
			throw new BOSException("参数为空！");
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancy.id",newTenancyBillInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("room.id",tenancyRoomEntryInfo.getRoom().getId().toString()));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("room.tenancyState");
		view.setFilter(filter);
		//这里做容错吧
		TenancyRoomEntryCollection newTenancyRoomEntryColl = null;
		
		newTenancyRoomEntryColl = TenancyRoomEntryFactory.getLocalInstance(ctx).getTenancyRoomEntryCollection(view);
		//不会出现大于一个的现象，但是会有为0的现象
		if(newTenancyRoomEntryColl.size() > 1)
		{
			logger.warn("该房间在源合同里面找到了多个房间分录信息...");
			throw new BOSException("程序逻辑错误，已记录日志！");
		}
		//新合同里面可能没有这个房间
		if(newTenancyRoomEntryColl.get(0) == null)
			return;
		TenancyRoomEntryInfo newTenancyRoomEntryInfo = newTenancyRoomEntryColl.get(0);
		
		this.setRoomTenancyState(ctx,newTenancyRoomEntryInfo.getRoom(),newTenancyRoomEntryInfo.getTenRoomState(), newTenancyBillInfo);
	}
	
	/**
	 * 根据旧的合同去查找一个新的合同，新合同的状态时已交押金首租或执行状态的
	 * @param oldTenancyBillInfo
	 * @return
	 * @throws BOSException 
	 * @author laiquan_luo
	 * 改租合同审批后,原合同的一个房间在改租合同中不存在,就已经可以退房了.所以这里也查询出 审批状态的合同,再根据合同状态进行控制  by zhicheng_jin
	 */
	private TenancyBillInfo getNewTenancyBillByOldTenancyBill(Context ctx,TenancyBillInfo oldTenancyBillInfo) throws BOSException
	{
		if(oldTenancyBillInfo == null)
		{
			logger.warn("参数为空...");
			throw new BOSException("传入参数为空，已记录日志");
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("oldTenancyBill.id",oldTenancyBillInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.AUDITED_VALUE));
		//filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.DEPOSITREVED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.PARTEXECUTED_VALUE));
		//filter.setMaskString("#0 and (#1 or #2 or #3))");
		filter.setMaskString("#0 and (#1 or #2))");
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("tenancyRoomList.room.id");
		view.setFilter(filter);
		
		TenancyBillCollection tenancyBillColl = TenancyBillFactory.getLocalInstance(ctx).getTenancyBillCollection(view);
		
		if(tenancyBillColl.size() > 1)
		{
			logger.warn("找到了是半执行状态或押金首租状态的多个合同...");
			throw new BOSException("程序逻辑错误，已记录日志！");
		}
		return tenancyBillColl.get(0);
	}
	
	/**
	 * 判断该合同是否存在审批状态的退租单
	 * @param ctx
	 * @param bill
	 * @return
	 * @throws BOSException
	 * @author laiquan_luo
	 */
	private boolean isExistQuitTenancyBill(Context ctx,TenancyBillInfo bill) throws BOSException
	{
		if(bill == null)
		{
			logger.warn("传入参数为空....");
			throw new BOSException("传入参数为空！");
		}
		boolean debug = false;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id",bill.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		
		try
		{
			if(QuitTenancyFactory.getLocalInstance(ctx).exists(filter))
				debug = true;
			else
				debug = false;
		} catch (EASBizException e)
		{
			throw new BOSException(e);
		} 
		return debug;
	}
	
	private BigDecimal getAmount(Context ctx,String roomid,String tenancyBillID)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select room.fid roomID,sum(fdcEntry.famount) sumRent from t_she_fdcreceivebillentry fdcEntry ");
    	builder.appendSql(" left join t_cas_receivingbill cas on cas.fid=fdcentry.FReceivingBillID ");
    	builder.appendSql(" left join T_SHE_FDCReceiveBill fdc on cas.FFDCReceivebillid=fdc.fid ");
    	builder.appendSql(" left join t_she_room room on fdc.froomid=room.fid ");
    	builder.appendSql(" left join t_ten_tenancybill tenbill on fdc.FTenancyContractID = tenbill.fid ");
    	builder.appendSql(" left join t_she_moneyDefine money on fdcentry.FMoneyDefineId=money.fid ");
    	builder.appendSql(" where  tenbill.fid = '"+tenancyBillID+"' and room.fid ='"+roomid+"'");
    	builder.appendSql(" and (money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount') and money.FSysType ='TenancySys'");
    	builder.appendSql(" group by room.fid ");
    	IRowSet set = null;
    	BigDecimal sumRent =  new BigDecimal(0);
		try {
			set = SQLExecutorFactory.getLocalInstance(ctx, builder.getSql())
			.executeSQL();
			while(set.next())
			{
				sumRent = set.getBigDecimal("sumRent");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return sumRent;
	}

	private BigDecimal getAttachAmount(Context ctx,String attachid,String tenancyBillID)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select attach.fid attachid,sum(fdcEntry.famount) sumRent from t_she_fdcreceivebillentry fdcEntry ");
    	builder.appendSql(" left join t_cas_receivingbill cas on cas.fid=fdcentry.FReceivingBillID ");
    	builder.appendSql(" left join T_SHE_FDCReceiveBill fdc on cas.FFDCReceivebillid=fdc.fid ");
    	builder.appendSql(" left join t_ten_TenAttachResourceEntry tenEntry on fdc.ftenAttachID=tenEntry.fid ");
    	builder.appendSql(" left join t_ten_attachResource attach on tenEntry.FAttachResourceID=attach.fid ");
    	builder.appendSql(" left join t_ten_tenancybill tenbill on fdc.FTenancyContractID = tenbill.fid ");
    	builder.appendSql(" left join t_she_moneyDefine money on fdcentry.FMoneyDefineId=money.fid ");
    	builder.appendSql(" where  tenbill.fid = '"+tenancyBillID+"' and attach.fid ='"+attachid+"'");
    	builder.appendSql(" and (money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount') and money.FSysType ='TenancySys'");
    	builder.appendSql(" group by attach.fid ");
    	IRowSet set = null;
    	BigDecimal sumRent =  new BigDecimal(0);
		try {
			set = SQLExecutorFactory.getLocalInstance(ctx, builder.getSql())
			.executeSQL();
			while(set.next())
			{
				sumRent = set.getBigDecimal("sumRent");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return sumRent;
	}

	protected void _repairStartDate(Context ctx, IObjectValue tenancyBillInfo,
			Date repairStartDate, FirstLeaseTypeEnum firstLease,
			Date firstLeaseDate) throws BOSException, EASBizException {
		//反写租赁开始日期结束日期，首租类型和首租结束日期
		TenancyBillInfo tenancyBill = (TenancyBillInfo) tenancyBillInfo;
		tenancyBill = TenancyHelper.getTenancyBillInfo(ctx, tenancyBill.getId().toString());
		BigDecimal leaseCount = tenancyBill.getLeaseCount();
		int leaseTime = tenancyBill.getLeaseTime();
		int totalMonthes = leaseTime*leaseCount.intValue();
		Date tmpDate = TenancyHelper.addCalendar(repairStartDate, Calendar.MONTH, totalMonthes);
		Date endDate = TenancyHelper.addCalendar(tmpDate, Calendar.DATE, -1);
		tenancyBill.setStartDate(repairStartDate);
		tenancyBill.setEndDate(endDate);
		tenancyBill.setFirstLeaseType(firstLease);
		tenancyBill.setFirstLeaseEndDate(firstLeaseDate);
		
		//反写房间成交租金分录
		TenancyRoomEntryCollection tenRooms = tenancyBill.getTenancyRoomList();
		TenAttachResourceEntryCollection tenAttaches = tenancyBill.getTenAttachResourceList();
		for(int i=0;i<tenRooms.size();i++)
		{
			TenancyRoomEntryInfo tenRoomInfo = tenRooms.get(i);
			DealAmountEntryCollection dealColl = tenRoomInfo.getDealAmounts();
			for(int j=0;j<dealColl.size();j++)
			{
				DealAmountEntryInfo dealInfo = dealColl.get(j);
				dealInfo.setStartDate(repairStartDate);
				dealInfo.setEndDate(endDate);
				//TODO 目前补录起始日期时不需要进行租金递增，免租的设置。后续可能需要修改
			}
		}
		
		// 反写配套资源的新租金明细.配套资源的目前似乎没用
		for (int i = 0; i < tenAttaches.size(); i++) {
			// 得到配套资源
			TenAttachResourceEntryInfo tenAttaInfo = (TenAttachResourceEntryInfo) tenAttaches.get(i);
			for (int j = 0; j < tenAttaInfo.getDealAmounts().size(); j++) {
				// 得到配套资源的租金明细
				AttachDealAmountEntryInfo attaDealInfo = (AttachDealAmountEntryInfo) tenAttaInfo.getDealAmounts().get(j);
				attaDealInfo.setStartDate(repairStartDate);
				attaDealInfo.setEndDate(endDate);
			}
		}
		
		//反写房间的付款明细
		FirstLeaseTypeEnum firstLeaseType = tenancyBill.getFirstLeaseType();
		Date firstLeaseEndDate = tenancyBill.getFirstLeaseEndDate();
		
		List leaseList = TenancyHelper.getLeaseList(repairStartDate, endDate, firstLeaseType, firstLeaseEndDate, leaseTime);
		
		ChargeDateTypeEnum chargeDateType = tenancyBill.getChargeDateType();
		int chargeOffsetDays = tenancyBill.getChargeOffsetDays();
		
		RentCountTypeEnum rentCountType = tenancyBill.getRentCountType();
		int daysPerYear = tenancyBill.getDaysPerYear() == 0 ? 360 : tenancyBill.getDaysPerYear();
		boolean isToInteger = tenancyBill.isIsAutoToInteger();
		ToIntegerTypeEnum toIntegerType = tenancyBill.getToIntegerType();
		DigitEnum digit  = tenancyBill.getDigit();
		
		// 增加租金设置周期性费用 eric_wang 2010.08.25
		boolean isToIntegerFee = tenancyBill.isIsAutoToIntegerFee();
		ToIntegerTypeEnum toIntegerTypeFee = tenancyBill.getToIntegetTypeFee();
		DigitEnum digitFee  = tenancyBill.getDigitFee();
		
		MoneyDefineInfo depositMoney = getMoneyDefine(ctx, MoneyTypeEnum.DepositAmount);
		MoneyDefineInfo rentMoney = getMoneyDefine(ctx, MoneyTypeEnum.RentAmount);
		RentFreeEntryCollection rentFress = tenancyBill.getRentFrees();
		
		try {
			TenancyHelper.fillTenEntryPayList(tenRooms, TenancyRoomPayListEntryCollection.class, TenancyRoomPayListEntryInfo.class, 
					depositMoney, rentMoney, leaseList, rentFress, rentCountType, daysPerYear, 
					isToInteger, toIntegerType, digit,isToIntegerFee, toIntegerTypeFee, digitFee, chargeDateType, chargeOffsetDays,tenancyBill.getFristRevDate(),tenancyBill.getFixedDateFromMonth(),tenancyBill.getLeaseTime());
			
			TenancyHelper.fillTenEntryPayList(tenAttaches, TenAttachResourcePayListEntryCollection.class, TenAttachResourcePayListEntryInfo.class, 
					depositMoney, rentMoney, leaseList, rentFress, rentCountType, daysPerYear, 
					isToInteger, toIntegerType, digit,isToIntegerFee, toIntegerTypeFee, digitFee, chargeDateType, chargeOffsetDays,tenancyBill.getFristRevDate(),tenancyBill.getFixedDateFromMonth(),tenancyBill.getLeaseTime());
		} catch (InstantiationException e) {
			throw new BOSException(e);
		} catch (IllegalAccessException e) {
			throw new BOSException(e);
		}
		
		//注意，这里只能调用基类的提交
		super._submit(ctx, tenancyBill);
	}
	
	private MoneyDefineInfo getMoneyDefine(Context ctx, MoneyTypeEnum moneyType) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("moneyType", moneyType));
		view.setFilter(filter);
		MoneyDefineCollection moneyNames = MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineCollection(view);
		if (!moneyNames.isEmpty()) {
			return moneyNames.get(0);
		} else {
			return null;
		}
	}
	
	/**租赁合同 反审批*/
	protected void _antiAudit(Context ctx, BOSUuid billID) throws BOSException, EASBizException {
		super._unAudit(ctx, billID);
		TenancyBillInfo tenBill = TenancyHelper.getTenancyBillInfo(ctx, billID.toString());
		tenBill.setTenancyState(TenancyBillStateEnum.Submitted);
		SelectorItemCollection updateSels = new SelectorItemCollection();
		updateSels.add("tenancyState");
		_updatePartial(ctx, tenBill, updateSels);

	}
}