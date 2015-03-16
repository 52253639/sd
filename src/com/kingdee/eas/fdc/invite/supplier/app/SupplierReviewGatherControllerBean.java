package com.kingdee.eas.fdc.invite.supplier.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStorageNumberFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStorageNumberInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.framework.ObjectBaseCollection;

public class SupplierReviewGatherControllerBean extends AbstractSupplierReviewGatherControllerBean
{
    private static Logger logger =Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.SupplierReviewGatherControllerBean");
    
	protected boolean isUseName() {
		return false;
	}
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._audit(ctx, billId);
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("supplier.*");
		sel.add("supplier.inviteType.*");
		sel.add("supplier.purchaseOrgUnit.*");
		sel.add("orgUnit.*");
		sel.add("isPass");
		sel.add("grade.*");
		sel.add("level.*");
		sel.add("evaluationType.number");
		SupplierReviewGatherInfo info=(SupplierReviewGatherInfo) this.getValue(ctx, new ObjectUuidPK(billId), sel);
		
		SelectorItemCollection updatesel=new SelectorItemCollection();
		SupplierEvaluationTypeInfo type=info.getEvaluationType();
		if(type.getNumber().equals("002")||type.getNumber().equals("003")||
				type.getNumber().equals("004")||type.getNumber().equals("005")){
			updatesel.add("grade");
			updatesel.add("isPass");
			
			info.getSupplier().setGrade(info.getGrade());
			info.getSupplier().setIsPass(info.getIsPass());
			
			if(info.getSupplier().getStorageNumber()==null&&type.getNumber().equals("002")&&IsGradeEnum.ELIGIBILITY.equals(info.getIsPass())){
				updatesel.add("storageNumber");
				updatesel.add("storageDate");
				
				SupplierStorageNumberInfo number=new SupplierStorageNumberInfo();
				number.setSupplier(info.getSupplier());
				number.setInviteType(info.getSupplier().getInviteType());
				number.setPurchaseOrgUnit(info.getSupplier().getPurchaseOrgUnit());
				number.setOrgUnit(info.getOrgUnit());
				handleIntermitNumber(ctx,number);
				
				info.getSupplier().setStorageNumber(number.getNumber());
				info.getSupplier().setStorageDate(new Date());
			}
			SupplierStockFactory.getLocalInstance(ctx).updatePartial(info.getSupplier(), updatesel);
			
			if(IsGradeEnum.ELIGIBILITY.equals(info.getIsPass())){
				SupplierStockFactory.getLocalInstance(ctx).addToSysSupplier(info.getSupplier());
			}
		}else if(type.getNumber().equals("006")||type.getNumber().equals("007")){
			updatesel.add("level");
			
			info.getSupplier().setLevel(info.getLevel());
			SupplierStockFactory.getLocalInstance(ctx).updatePartial(info.getSupplier(), updatesel);
		}
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._unAudit(ctx, billId);
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("supplier.id");
		sel.add("srcGrade.*");
		sel.add("srcIsPass");
		sel.add("srcLevel.*");
		sel.add("evaluationType.number");
		SupplierReviewGatherInfo info=(SupplierReviewGatherInfo) this.getValue(ctx, new ObjectUuidPK(billId), sel);
		
		SelectorItemCollection updatesel=new SelectorItemCollection();
		SupplierEvaluationTypeInfo type=info.getEvaluationType();
		if(type.getNumber().equals("002")||type.getNumber().equals("003")||
				type.getNumber().equals("004")||type.getNumber().equals("005")){
			updatesel.add("grade");
			updatesel.add("isPass");
			
			info.getSupplier().setGrade(info.getSrcGrade());
			info.getSupplier().setIsPass(info.getSrcIsPass());
			SupplierStockFactory.getLocalInstance(ctx).updatePartial(info.getSupplier(), updatesel);
		}else if(type.getNumber().equals("006")||type.getNumber().equals("007")){
			updatesel.add("level");
			
			info.getSupplier().setLevel(info.getSrcLevel());
			SupplierStockFactory.getLocalInstance(ctx).updatePartial(info.getSupplier(), updatesel);
		}
	}
}