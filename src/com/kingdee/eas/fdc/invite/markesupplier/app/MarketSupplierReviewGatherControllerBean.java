package com.kingdee.eas.fdc.invite.markesupplier.app;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kingdee.bos.*;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockFactory;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStorageNumberInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStorageNumberInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.SysUtil;

public class MarketSupplierReviewGatherControllerBean extends AbstractMarketSupplierReviewGatherControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.markesupplier.app.MarketSupplierReviewGatherControllerBean");
    protected boolean isUseName() {
		return false;
	}
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
		super._audit(ctx, billId);
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("supplier.*");
		sel.add("supplier.inviteType.*");
		sel.add("supplier.purchaseOrgUnit.*");
		sel.add("orgUnit.*");
		sel.add("isPass");
		sel.add("name");
		sel.add("grade.*");
		sel.add("level.*");
		sel.add("evaluationType.number");
		MarketSupplierReviewGatherInfo info=(MarketSupplierReviewGatherInfo) this.getValue(ctx, new ObjectUuidPK(billId), sel);
		
		SelectorItemCollection updatesel=new SelectorItemCollection();
		MarketAccreditationTypeInfo type=info.getEvaluationType();
		if(type.getNumber().equals("002")||type.getNumber().equals("003")||
				type.getNumber().equals("004")||type.getNumber().equals("005")){
			updatesel.add("grade");
			updatesel.add("isPass");
			updatesel.add("level");
			
			info.getSupplier().setGrade(info.getGrade());
			info.getSupplier().setIsPass(info.getIsPass());
			info.getSupplier().setLevel(info.getLevel());
			if(info.getSupplier().getStorageNumber()==null&&type.getNumber().equals("002")&&IsGradeEnum.ELIGIBILITY.equals(info.getIsPass())){
				updatesel.add("storageNumber");
				updatesel.add("storageDate");
				
				MarketSupplierStorageNumberInfo number=new MarketSupplierStorageNumberInfo();
				number.setMarketSupplier(info.getSupplier());
				number.setInviteType(info.getSupplier().getInviteType());
				number.setPurchaseOrgUnit(info.getSupplier().getPurchaseOrgUnit());
				number.setOrgUnit(info.getOrgUnit());
				handleIntermitNumber(ctx,number);
				
				info.getSupplier().setStorageNumber(number.getNumber());
				if(UIRuleUtil.isNotNull(info.getName())){
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
					try {
						info.getSupplier().setStorageDate(df.parse(info.getName()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}else{
					info.getSupplier().setStorageDate(SysUtil.getAppServerTime(ctx));
				}
			}
			MarketSupplierStockFactory.getLocalInstance(ctx).updatePartial(info.getSupplier(), updatesel);
			
			if(IsGradeEnum.ELIGIBILITY.equals(info.getIsPass())){
				MarketSupplierStockFactory.getLocalInstance(ctx).addToSysSupplier(info.getSupplier());
			}
		}else if(type.getNumber().equals("006")||type.getNumber().equals("007")){
			updatesel.add("level");
			
			info.getSupplier().setLevel(info.getLevel());
			MarketSupplierStockFactory.getLocalInstance(ctx).updatePartial(info.getSupplier(), updatesel);
		}
	}
    
    
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
		super._unAudit(ctx, billId);
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("supplier.id");
		sel.add("srcGrade.*");
		sel.add("srcIsPass");
		sel.add("srcLevel.*");
		sel.add("evaluationType.number");
		MarketSupplierReviewGatherInfo info=(MarketSupplierReviewGatherInfo) this.getValue(ctx, new ObjectUuidPK(billId), sel);
		
		SelectorItemCollection updatesel=new SelectorItemCollection();
		MarketAccreditationTypeInfo  type=info.getEvaluationType();
		if(type.getNumber().equals("002")||type.getNumber().equals("003")||
				type.getNumber().equals("004")||type.getNumber().equals("005")){
			updatesel.add("grade");
			updatesel.add("isPass");
			
			info.getSupplier().setGrade(info.getGrade());
			info.getSupplier().setIsPass(info.getSrcIsPass());
			MarketSupplierStockFactory.getLocalInstance(ctx).updatePartial(info.getSupplier(), updatesel);
		}else if(type.getNumber().equals("006")||type.getNumber().equals("007")){
			updatesel.add("level");
			
			info.getSupplier().setLevel(info.getSrcLevel());
			MarketSupplierStockFactory.getLocalInstance(ctx).updatePartial(info.getSupplier(), updatesel);
		}
	}
    
	public IObjectPK save(Context ctx, CoreBaseInfo model) throws BOSException,
	EASBizException {
		MarketSupplierReviewGatherInfo info = (MarketSupplierReviewGatherInfo) model;
		updateSupplierInfo(ctx, info.getSupplier());
		return super.save(ctx, model);
	}
	
	public void save(Context ctx, IObjectPK pk, CoreBaseInfo model)
		throws BOSException, EASBizException {
		MarketSupplierReviewGatherInfo info = (MarketSupplierReviewGatherInfo) model;
		updateSupplierInfo(ctx, info.getSupplier());
		super.save(ctx, pk, model);
	}

    public IObjectPK submit(Context ctx, CoreBaseInfo model)
	throws BOSException, EASBizException {
		MarketSupplierReviewGatherInfo info = (MarketSupplierReviewGatherInfo) model;
		updateSupplierInfo(ctx, info.getSupplier());
		return super.submit(ctx, model);
	}
	
	public void submit(Context ctx, IObjectPK arg1, CoreBaseInfo model)
		throws BOSException, EASBizException {
		MarketSupplierReviewGatherInfo info = (MarketSupplierReviewGatherInfo) model;
		updateSupplierInfo(ctx, info.getSupplier());
		super.submit(ctx, arg1, model);
	}
	
	protected void updateSupplierInfo(Context ctx, MarketSupplierStockInfo info)
		throws EASBizException, BOSException {
	if (info != null) {
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("partProject");
		sel.add("authorizePerson");
		sel.add("authorizePhone");
		sel.add("authorizeJob");
		sel.add("contractor");
		sel.add("contractorPhone");
		sel.add("manager");
		sel.add("managerPhone");
		MarketSupplierStockFactory.getLocalInstance(ctx).updatePartial(info, sel);
	}
	}

}