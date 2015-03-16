package com.kingdee.eas.fdc.aimcost.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
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

import com.kingdee.bos.metadata.bot.BOTRelationInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.base.botp.BOTBillOperStateEnum;
import com.kingdee.eas.base.dap.DAPTransformResult;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.eas.fdc.aimcost.VoucherForDynamicInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.finance.ProductSettleBillInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.aimcost.VoucherForDynamicCollection;
import com.kingdee.eas.fi.gl.VoucherFactory;
import com.kingdee.eas.fi.gl.VoucherInfo;

public class VoucherForDynamicControllerBean extends AbstractVoucherForDynamicControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.VoucherForDynamicControllerBean");
    
    protected boolean isUseName() {
		return false;
	}
		
	protected boolean isUseNumber() {
		return false;
	}
	
	protected void _reverseSave(Context ctx, IObjectPK srcBillPK,
			IObjectValue srcBillVO, BOTBillOperStateEnum bOTBillOperStateEnum,
			IObjectValue bOTRelationInfo) throws BOSException, EASBizException {

		super._reverseSave(ctx, srcBillPK, srcBillVO, bOTBillOperStateEnum,
				bOTRelationInfo);
		BOTRelationInfo botRelation = (BOTRelationInfo) bOTRelationInfo;
		VoucherForDynamicInfo info = (VoucherForDynamicInfo)srcBillVO;
		if (new VoucherInfo().getBOSType().toString().equals(
				botRelation.getDestEntityID())) {
			if (BOTBillOperStateEnum.ADDNEW.equals(bOTBillOperStateEnum)) {
				VoucherInfo voucherInfo = (VoucherInfo) VoucherFactory
				.getLocalInstance(ctx).getValue(
						new ObjectStringPK(botRelation
								.getDestObjectID()));
				info.setVoucher(voucherInfo);
				super.save(ctx, info);
			} else if (BOTBillOperStateEnum.DELETE.equals(bOTBillOperStateEnum)) {
				info.setVoucher(null);
				super.save(ctx, info);
			}
		}
	}
	
	protected DAPTransformResult _generateVoucher(Context ctx,
			IObjectCollection sourceBillCollection, IObjectPK botMappingPK)
			throws BOSException, EASBizException {
		return super._generateVoucher(ctx, sourceBillCollection, botMappingPK);
	}

	protected boolean _deleteVoucher(Context ctx, IObjectPK sourceBillPk)
			throws BOSException, EASBizException {
		boolean success = super._deleteVoucher(ctx, sourceBillPk);
		return success;
	}
}