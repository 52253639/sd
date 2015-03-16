package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.BOTRelationInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.botp.BOTBillOperStateEnum;
import com.kingdee.eas.base.dap.DAPTransformResult;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.DeductTypeFactory;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryFactory;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillFactory;
import com.kingdee.eas.fdc.finance.DeductBillCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.fdc.finance.DeductBillException;
import com.kingdee.eas.fdc.finance.DeductBillFactory;
import com.kingdee.eas.fdc.finance.DeductBillInfo;
import com.kingdee.eas.fdc.finance.PartAMainContractCollection;
import com.kingdee.eas.fdc.finance.PartAMainContractFactory;
import com.kingdee.eas.fdc.finance.PartAMainContractInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * 
 * 描述:扣款单
 * 
 * @author liupd date:2006-10-13
 *         <p>
 * @version EAS5.1.3
 */
public class DeductBillControllerBean extends AbstractDeductBillControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.finance.app.DeductBillControllerBean");

	protected boolean isUseName() {

		return false;
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		
		checkBeforeUnAudit(ctx, billId);
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("deductBillEntry.Parent.id", billId
						.toString()));
		// 不能反审核处理
		if (DeductOfPayReqBillEntryFactory.getLocalInstance(ctx).exists(filter)) {
			throw new DeductBillException(DeductBillException.CANNT_UNADUIT);
		} else {
			DeductBillInfo billInfo = getDeductBillInfo(ctx, new ObjectUuidPK(
					billId));
			DeductBillEntryCollection entrys = billInfo.getEntrys();
			DeductBillEntryInfo entryInfo;
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				entryInfo = (DeductBillEntryInfo) iter.next();
				entryInfo.setHasApplied(false);
			}

			billInfo.setId(billId);
			billInfo.setState(FDCBillStateEnum.SUBMITTED);
			billInfo.setAuditor(null);
			billInfo.setAuditTime(null);
			submit(ctx, billInfo);
			// 避免多次访问数据库故不调用父类方法
			// super._unAudit(ctx, billId);

		}
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		DeductBillInfo billInfo = DeductBillFactory.getLocalInstance(
				ctx).getDeductBillInfo(new ObjectUuidPK(billId));
		int num = billInfo.getEntrys().size();
		DeductBillEntryCollection c = billInfo.getEntrys();
		if (num > 0) {
			Set set = new HashSet();
			for (int i = 0; i < num; i++) {
				DeductBillEntryInfo entry = c.get(i);
				set.add(entry.getContractId());
			}
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(new FilterInfo());
			view.getFilter().getFilterItems().add(
					new FilterItemInfo("id", set, CompareType.INCLUDE));
			view.getSelector().add("hasSettled");
			view.getSelector().add("number");
			ContractBillCollection cons = ContractBillFactory.getLocalInstance(
					ctx).getContractBillCollection(view);
			boolean hasSettled = false;
			String conNumber = "";
			for (int i = 0; i < cons.size(); i++) {
				if (cons.get(i).isHasSettled()) {
					hasSettled = true;
					conNumber += "、“" + cons.get(i).getNumber() + "”";
				}
			}
			//BT292169 去掉对已结算合同的校验
//			if (hasSettled) {
//				throw new EASBizException(new NumericExceptionSubItem("111",
//						"审批不通过：扣款单分录中存在已结算的合同，合同编码："
//								+ conNumber.substring(1)));
//			}
		}
		super._audit(ctx, billId);
	}

	protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {

		removeTotalEntry(model);
		super._addnew(ctx, pk, model);
	}

	protected IObjectPK _addnew(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		removeTotalEntry(model);
		return super._addnew(ctx, model);

	}

	/**
	 * 删除合计行
	 * 
	 * @param model
	 */
	private void removeTotalEntry(IObjectValue model) {
		DeductBillInfo info = (DeductBillInfo) model;
		DeductBillEntryCollection entrys = info.getEntrys();
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			DeductBillEntryInfo element = (DeductBillEntryInfo) iter.next();
			if (element.getContractId() == null) {
				iter.remove();
			}

		}
	}

	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		removeTotalEntry(model);
		super._update(ctx, pk, model);
	}
	
	/**
	 * 描述：付款单生成甲供扣款单<p>
	 * 注意实体字段要全，否则不能显示<p>
	 * @param ctx
	 * @param paymentId
	 * */
	protected void _createPartADeductBill(Context ctx, String paymentId) throws BOSException {
		CompanyOrgUnitInfo company = ContextUtil.getCurrentFIUnit(ctx);
		if(company!=null&&company.getId()!=null){
			try {
				//得到付款单
				EntityViewInfo payView = new EntityViewInfo();
				payView.getSelector().add("number");
				payView.getSelector().add("contractBillId");
				payView.getSelector().add("contractNo");
				payView.getSelector().add("curProject.*");
				payView.getSelector().add("fdcPayeeName.name");
				payView.getSelector().add("actFdcPayeeName.name");
				payView.getSelector().add("currency.id");
				FilterInfo payFilter = new FilterInfo();
				payFilter.getFilterItems().add(new FilterItemInfo("id", paymentId));
				payView.setFilter(payFilter);
				PaymentBillInfo paymentBillInfo = PaymentBillFactory.getLocalInstance(ctx)
					.getPaymentBillCollection(payView).get(0);
			
				EntityViewInfo deductView = new EntityViewInfo();
				deductView.getSelector().add("number");
				FilterInfo deductFilter = new FilterInfo();
				paymentBillInfo.getCurProject().getName();
				deductFilter.getFilterItems().add(new FilterItemInfo("curProject.id", paymentBillInfo.getCurProject().getId().toString()));
				deductView.setFilter(deductFilter);
				DeductBillCollection deductBillCollection = DeductBillFactory.getLocalInstance(ctx).getDeductBillCollection(deductView);
				
				//编码不能与当前工程中存在的重复,重复则加"_JG"
				String number = paymentBillInfo.getNumber();
				String appendStr = "_JG";
				for(int i=0;i<deductBillCollection.size();i++){
					String curNumber = deductBillCollection.get(i).getNumber();
					if(curNumber != null && curNumber.equals(number)){
						number = number + appendStr;
						i = -1;
					}
				}
				
				//扣款类型
				EntityViewInfo typeView = new EntityViewInfo();
				FilterInfo typeFilter = new FilterInfo();
				typeFilter.getFilterItems().add(new FilterItemInfo("id", DeductTypeInfo.partAMaterialType, CompareType.EQUALS));
				typeView.setFilter(typeFilter);
				DeductTypeInfo typeInfo = DeductTypeFactory.getLocalInstance(ctx).getDeductTypeCollection(typeView).get(0);
					
				//取甲供材 主合同列表
				EntityViewInfo partAView = new EntityViewInfo();
				partAView.getSelector().add("amount");
				partAView.getSelector().add("mainContractBill.partB");
				partAView.getSelector().add("mainContractBill.exRate");
				partAView.getSelector().add("mainContractBill.currency.id");
				partAView.getSelector().add("mainContractBill.curProject.*");
				FilterInfo partAFilter = new FilterInfo();
				partAFilter.getFilterItems().add(new FilterItemInfo("paymentBill.id",paymentBillInfo.getId()));
				partAView.setFilter(partAFilter);
					
				PartAMainContractCollection partAMainContractCol = PartAMainContractFactory.getLocalInstance(ctx).getPartAMainContractCollection(partAView);
				DeductBillInfo deductBillInfo = new DeductBillInfo();
				DeductBillEntryInfo deductBillEntryInfo = null;
				for(Iterator iter = partAMainContractCol.iterator();iter.hasNext();){
					PartAMainContractInfo mainCon = (PartAMainContractInfo) iter.next();
					deductBillEntryInfo = new DeductBillEntryInfo();
					 
					deductBillEntryInfo.setDeductType(typeInfo);
					deductBillEntryInfo.setHasApplied(false);
					deductBillEntryInfo.setContractId(mainCon.getMainContractBill().getId().toString());
					deductBillEntryInfo.setDeductItem(paymentBillInfo.getFdcPayeeName().toString());
					deductBillEntryInfo.setRemark("甲供材合同"+paymentBillInfo.getContractNo()+"付款单自动生成");
					deductBillEntryInfo.setDeductDate(new Date());
					/****
					 * 需要取材料合同的乙方
					 * old:::
					 * deductBillEntryInfo.setDeductUnit(mainCon.getMainContractBill().getPartB());
					 * 2010-8-12 by yong_zhou
					 * 相关提单：R100719-304
					 * BOTP中只能取到对应付款的总包合同的乙方，而取不到扣甲供付款的材料合同的乙方
					 */
					deductBillEntryInfo.setDeductUnit(paymentBillInfo.getActFdcPayeeName());
					if(!paymentBillInfo.getCurrency().getId().equals(mainCon.getMainContractBill().getCurrency().getId())){
						deductBillEntryInfo.setDeductOriginalAmt(mainCon.getAmount().divide(mainCon.getMainContractBill().getExRate(), 5, BigDecimal.ROUND_CEILING));
					}else{
						deductBillEntryInfo.setDeductOriginalAmt(mainCon.getAmount());
					}
					deductBillEntryInfo.setDeductAmt(mainCon.getAmount());
					deductBillEntryInfo.setExRate(mainCon.getMainContractBill().getExRate());
					deductBillEntryInfo.setParent(deductBillInfo);
					//分录设置对应的工程项目
					deductBillEntryInfo.getParent().setCurProject(mainCon.getMainContractBill().getCurProject());
					deductBillInfo.getEntrys().add(deductBillEntryInfo);
				}
				deductBillInfo.setPaymentId(paymentId);
				deductBillInfo.setNumber(number);
				deductBillInfo.setCurProject(paymentBillInfo.getCurProject());
				//扣款类型在合同之前
				deductBillInfo.setConTypeBefContr(false);
				IObjectPK pk = _submit(ctx,deductBillInfo);
				_audit(ctx,BOSUuid.read(pk.toString()));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 描述：甲供扣款被引用不能反审批或是否已生成凭证
	 * @param ctx
	 * @param billId
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void checkBeforeUnAudit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("deductBill.id", billId.toString()));
		boolean isRefered = PartAOfPayReqBillFactory.getLocalInstance(ctx).exists(filter);
		if(isRefered){
			throw new DeductBillException(DeductBillException.CANNT_UNADUIT);
		}
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", billId.toString()));
		filter.getFilterItems().add(new FilterItemInfo("fiVouchered",Boolean.valueOf(true)));
		boolean isVouchered = DeductBillFactory.getLocalInstance(ctx).exists(filter);
		DeductBillInfo info = getDeductBillInfo(ctx, new ObjectUuidPK(
				billId));
		if(isVouchered){
			String msg = "单据: "+info.getNumber()+" 已生成凭证，不能反审批!";
			throw new EASBizException(new NumericExceptionSubItem("100",msg));
		}
	}
	
	protected void _reverseSave(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		//super._reverseSave(ctx, pk, model);
	}
	protected void _reverseSave(Context ctx, IObjectPK srcBillPK,
			IObjectValue srcBillVO, BOTBillOperStateEnum bOTBillOperStateEnum,
			IObjectValue bOTRelationInfo) throws BOSException, EASBizException {
		// 反写导致扣款分录数据删除，暂屏蔽
		// super._reverseSave(ctx, srcBillPK, srcBillVO,
		// bOTBillOperStateEnum,bOTRelationInfo);

		BOTRelationInfo botRelation = (BOTRelationInfo) bOTRelationInfo;
		if (new VoucherInfo().getBOSType().toString().equals(
				botRelation.getDestEntityID())) {
			DeductBillInfo info = new DeductBillInfo();
			info.setId(BOSUuid.read(srcBillPK.toString()));
			if (BOTBillOperStateEnum.ADDNEW.equals(bOTBillOperStateEnum)) {
				info.setFiVouchered(true);
				VoucherInfo voucherInfo = new VoucherInfo();
				voucherInfo.setId(BOSUuid.read(botRelation.getDestObjectID()));
				info.setVoucher(voucherInfo);

				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("fiVouchered");
				selector.add("voucher.id");
				DeductBillFactory.getLocalInstance(ctx).updatePartial(info,
						selector);

			} else if (BOTBillOperStateEnum.DELETE.equals(bOTBillOperStateEnum)) {
				info.setFiVouchered(false);
				info.setVoucher(null);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("fiVouchered");
				selector.add("voucher.id");
				DeductBillFactory.getLocalInstance(ctx).updatePartial(info,
						selector);
			}
		}
	}
	protected SelectorItemCollection getBOTPSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("curProject.*");
		sic.add("entrys.*");
		sic.add("entrys.deductUnit.*");
		sic.add("entrys.accountView.id");
		sic.add("entrys.accountView.longNumber");
		sic.add("entrys.accountView.longName");
		return sic;
	}
	protected DAPTransformResult _generateVoucher(Context ctx,
			IObjectCollection sourceBillCollection, IObjectPK botMappingPK)
			throws BOSException, EASBizException {
		Set ids=new HashSet();
		for(Iterator iter=sourceBillCollection.iterator();iter.hasNext();){
			FDCBillInfo info=(FDCBillInfo)iter.next();
			ids.add(info.getId().toString());
		}
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("id", ids, CompareType.INCLUDE));
		boolean  isAudited = DeductBillFactory.getLocalInstance(ctx).exists(filter);
		if(isAudited){
			throw new EASBizException(new NumericExceptionSubItem("100","存在不符合生成凭证条件的记录，请重新选择，保证所选的记录都是审批状态的!"));
		}
		DAPTransformResult voucher = super._generateVoucher(ctx,sourceBillCollection,botMappingPK);
		return voucher;
	}
}