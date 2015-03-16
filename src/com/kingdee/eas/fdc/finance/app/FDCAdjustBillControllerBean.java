package com.kingdee.eas.fdc.finance.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.metadata.bot.BOTRelationInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.botp.BOTBillOperStateEnum;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutText;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.finance.FDCAdjustBillCollection;
import com.kingdee.eas.fdc.finance.FDCAdjustBillFactory;
import com.kingdee.eas.fdc.finance.FDCAdjustBillInfo;
import com.kingdee.eas.fdc.finance.IFDCAdjustBill;
import com.kingdee.eas.fi.gl.VoucherFactory;
import com.kingdee.eas.fi.gl.VoucherInfo;

public class FDCAdjustBillControllerBean extends AbstractFDCAdjustBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.FDCAdjustBillControllerBean");

    protected void _reverseSave(Context ctx, IObjectPK srcBillPK,
			IObjectValue srcBillVO, BOTBillOperStateEnum bOTBillOperStateEnum,
			IObjectValue bOTRelationInfo) throws BOSException, EASBizException {

		super._reverseSave(ctx, srcBillPK, srcBillVO, bOTBillOperStateEnum,
				bOTRelationInfo);
		BOTRelationInfo botRelation = (BOTRelationInfo) bOTRelationInfo;
		FDCAdjustBillInfo adjustBillInfo =new FDCAdjustBillInfo();
		adjustBillInfo.setId(BOSUuid.read(srcBillPK.toString()));
		
		IFDCAdjustBill adjustBill = FDCAdjustBillFactory.getLocalInstance(ctx);
		if (new VoucherInfo().getBOSType().toString().equals(
				botRelation.getDestEntityID())) {
			if (BOTBillOperStateEnum.ADDNEW.equals(bOTBillOperStateEnum)) {
				adjustBillInfo.setFiVouchered(true);
				
				// payBillInfo.setBillStatus(BillStatusEnum.VOUCHERED);
				VoucherInfo voucherInfo = new VoucherInfo();
				voucherInfo.setId(BOSUuid.read(botRelation.getDestObjectID()));
				adjustBillInfo.setVoucher(voucherInfo);
				
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("fiVouchered");
				selector.add("voucher");
				adjustBill.updatePartial(adjustBillInfo,
						selector);

			} else if (BOTBillOperStateEnum.DELETE.equals(bOTBillOperStateEnum)) {
				adjustBillInfo.setFiVouchered(false);
				adjustBillInfo.setVoucher(null);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("fiVouchered");
				selector.add("voucher");
				adjustBill.updatePartial(adjustBillInfo,
						selector);
			}
		}
	}

	public FDCAdjustBillCollection getFDCAdjustBillCollection(Context ctx, EntityViewInfo view) throws BOSException {
		// TODO 自动生成方法存根
		FDCAdjustBillCollection coll = super.getFDCAdjustBillCollection(ctx, view);
		
		Map conWithoutText = new HashMap(); 
		for(Iterator it=coll.iterator();it.hasNext();){
			FDCAdjustBillInfo info = (FDCAdjustBillInfo)it.next();
			if(info.getContractBill()!=null){
				String conId = info.getContractBill().getId().toString();
				BOSObjectType contract = new ContractBillInfo().getBOSType();
				if(BOSUuid.read(conId).getType().equals(contract))
					continue;
				else{
					if(!conWithoutText.containsKey(conId))
						conWithoutText.put(conId,info.getContractBill());
				}
			}
		}
		if(conWithoutText.size()>0){
			EntityViewInfo conWt = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",conWithoutText.keySet(),CompareType.INCLUDE));
			conWt.setFilter(filter);
			conWt.getSelector().add("id");
			conWt.getSelector().add("name");
			conWt.getSelector().add("number");
			ContractWithoutTextCollection cc = ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextCollection(conWt);
			for(Iterator it=cc.iterator();it.hasNext();){
				ContractWithoutTextInfo info = (ContractWithoutTextInfo)it.next();
				if(conWithoutText.containsKey(info.getId().toString())){
					ContractBillInfo cInfo = (ContractBillInfo)conWithoutText.get(info.getId().toString());
					cInfo.setName(info.getName());
					cInfo.setNumber(info.getNumber());
				}
				
			}
		}
		return coll;
	}
}