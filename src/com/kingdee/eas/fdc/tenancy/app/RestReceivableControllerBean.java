package com.kingdee.eas.fdc.tenancy.app;

import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.app.DataBaseCodeRuleHelper;
import com.kingdee.eas.fdc.tenancy.RestReceivableInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;

import com.kingdee.util.StringUtils;

public class RestReceivableControllerBean extends AbstractRestReceivableControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.RestReceivableControllerBean");

//	protected IObjectPK _save(Context ctx,IObjectValue model)
//			throws BOSException, EASBizException {
//		RestReceivableInfo restRevModel = (RestReceivableInfo)model;
//		RestReceivableEntryCollection restRevColls = restRevModel.getEntrys();
//		// 新增租赁合同其他应收数据
//		ITenBillOtherPay  opayDao = TenBillOtherPayFactory.getLocalInstance(ctx);
//		TenancyBillInfo tb = restRevModel.getTenancyBill();
//		if(tb != null && restRevColls != null && !restRevColls.isEmpty()){
//			Iterator iterator = restRevColls.iterator();
//			RestReceivableEntryInfo restRevEntry = null;
//			TenBillOtherPayInfo otherPay = null;
//			IObjectPK otherPayPK = null;
//			while(iterator.hasNext()){
//				restRevEntry = (RestReceivableEntryInfo)iterator.next();
//				otherPay = restRevEntry.readData(restRevEntry);
//				otherPay.setHead(tb);
//				otherPayPK = opayDao.save(otherPay);
//				otherPay.setId(BOSUuid.read(otherPayPK.toString()));
//				
//				restRevEntry.setTenancyBillEntry(otherPay);
//			}
//		}
//		
//		return super._save(ctx, model);
//	}


//	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
//			EASBizException {
//		// 级联删除租赁合同其他应收
//		ITenBillOtherPay  opayDao = TenBillOtherPayFactory.getLocalInstance(ctx);
//		
//		RestReceivableInfo model = this.getRestReceivableInfo(ctx,pk,getSelectors());
//		RestReceivableEntryCollection colls = model.getEntrys();
//		
//		RestReceivableEntryInfo entry = null;
//		TenBillOtherPayInfo otherPay = null;
//		IObjectPK opayPK = null; 
//		if(colls != null && !colls.isEmpty()){
//			Iterator iterator = colls.iterator();
//			while(iterator.hasNext()){
//				entry = (RestReceivableEntryInfo)iterator.next();
//				otherPay = entry.getTenancyBillEntry();
//				if(otherPay != null){
//					opayPK = new ObjectUuidPK(otherPay.getId());
//					opayDao.delete(opayPK);
//				}
//			}
//		}
//		super._delete(ctx, pk);
//	}
    
	private SelectorItemCollection getSelectors(){
		SelectorItemCollection sic = new SelectorItemCollection ();
		sic.add("id");
		sic.add("entrys.id");
		sic.add("entrys.tenancyBillEntry.id");
		return sic;
	}

	protected void _passAudit(Context ctx, IObjectPK pk, IObjectValue model)
			throws EASBizException, BOSException {
//		super._passAudit(ctx, pk, model);
		
		UserInfo auditor = ContextUtil.getCurrentUserInfo(ctx);
		Date auditDate = new Date(System.currentTimeMillis());
		
		RestReceivableInfo restRev = (RestReceivableInfo)model;
		restRev.setAuditor(auditor);
		restRev.setAuditDate(auditDate);
		restRev.setBillState(TenancyBillStateEnum.Audited);
		
		//add by yangfan
		for (int i = 0; i < restRev.getOtherPayList().size(); i++) {
			if (restRev.getOtherPayList().get(i).getHead() == null) {
				restRev.getOtherPayList().get(i).setHead(
						restRev.getTenancyBill());
			}
		}
		this.updatePartial(ctx, restRev, getUpdateSIC());
	}

	protected void _unpassAudit(Context ctx, IObjectPK pk, IObjectValue model)
			throws EASBizException, BOSException {
//		super._unpassAudit(ctx, pk, model);
		
		RestReceivableInfo restRev = (RestReceivableInfo)model;
		restRev.setAuditor(null);
		restRev.setAuditDate(null);
		restRev.setBillState(TenancyBillStateEnum.Submitted);
		this.updatePartial(ctx, restRev, getUpdateSIC());
	}
	
	private SelectorItemCollection getUpdateSIC(){
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("auditor");
		sic.add("auditDate");
		sic.add("billState");
		
		//add by yangfan
		sic.add("otherPayList.head");
		return sic;
	}
	
	//by tim_gao 因断号编码不能使用，添加方法 2010-11-16
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
		handleIntermitNumber(ctx,(RestReceivableInfo)model);
		return super._submit(ctx, model);
	}
	
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
		handleIntermitNumber(ctx,(RestReceivableInfo)model);
		return super._save(ctx, model);
	}
	
	//by tim_gao 回收编码规则 2010-11-16
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
	    	for (int i = 0; i < arrayPK.length; i++) {
	    		recycleNumber(ctx, (RestReceivableInfo) _getValue(ctx, arrayPK[i]));
			}
	    	super._delete(ctx, arrayPK);
	}
	    
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		recycleNumber(ctx, (RestReceivableInfo) _getValue(ctx, pk));
	    	super._delete(ctx, pk);
	}

	public static void handleIntermitNumber(Context ctx, RestReceivableInfo info)
		throws BOSException, CodingRuleException, EASBizException {
		// 如果用户在客户端手工选择了断号,则此处不必在抢号
		if (info.getNumber() != null && info.getNumber().length() > 0)
			return;

		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
			.getLocalInstance(ctx);

		String costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId()
			.toString();

		if (StringUtils.isEmpty(costUnitId)) {
			return;
		}

	if (iCodingRuleManager.isExist(info, costUnitId)) {
		// 选择了断号支持或者没有选择新增显示,获取并设置编号
		if (iCodingRuleManager.isUseIntermitNumber(info, costUnitId)
				|| !iCodingRuleManager.isAddView(info, costUnitId))
			// 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
			{
			// 启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
			String number = iCodingRuleManager.getNumber(info, costUnitId);
			info.setNumber(number);
		}
	}
	}
	
	/**
	 * 回收Number，如果配置了编码规则并支持断号的话
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws CodingRuleException
	 */
	public static void recycleNumber(Context ctx, RestReceivableInfo info) throws BOSException, EASBizException, CodingRuleException {
		SaleOrgUnitInfo currentSaleUnit = ContextUtil.getCurrentSaleUnit(ctx);
		String curOrgId = currentSaleUnit.getId().toString();
		if(info.getNumber()!=null&&info.getNumber().length()>0){
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
	        if (iCodingRuleManager.isExist(info, curOrgId) && iCodingRuleManager.isUseIntermitNumber(info, curOrgId)) {
	            iCodingRuleManager.recycleNumber(info, curOrgId, info.getNumber());
	        }
		}
	}
	}