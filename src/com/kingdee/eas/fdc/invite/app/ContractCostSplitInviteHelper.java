package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.invite.AcceptanceLetterFactory;
import com.kingdee.eas.fdc.invite.AcceptanceLetterInfo;
import com.kingdee.eas.fdc.invite.InvitePreSplitCollection;
import com.kingdee.eas.fdc.invite.InvitePreSplitFactory;
import com.kingdee.eas.fdc.invite.InvitePreSplitInfo;

public class ContractCostSplitInviteHelper {

	public static void setInvitePreSplitState(Context ctx, IObjectPK pk, boolean flag) throws BOSException, EASBizException {
		ContractCostSplitInfo contractSplitInfo = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitInfo(pk);

		//拆分保存更新对应招标立项下对应的招标预先拆分的状态为已拆分
		if(contractSplitInfo.getContractBill() != null)
		{
			IObjectPK contractBillPK = new ObjectUuidPK(contractSplitInfo.getContractBill().getId().toString());

			ContractBillInfo billInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(contractBillPK);
			if(billInfo.getSourceBillId() != null){
				String sourceBillId = billInfo.getSourceBillId();
				IObjectPK letterPK = new ObjectUuidPK(sourceBillId);
				AcceptanceLetterInfo letterInfo = AcceptanceLetterFactory.getLocalInstance(ctx).getAcceptanceLetterInfo(letterPK);

				if(letterInfo.getInviteProject() != null){
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add(new SelectorItemInfo("id"));
					view.getSelector().add(new SelectorItemInfo("inviteProject"));
					view.getSelector().add(new SelectorItemInfo("state"));

					FilterInfo preSplitFilter = new FilterInfo();
					preSplitFilter.getFilterItems().add(new FilterItemInfo("inviteProject", letterInfo.getInviteProject().getId().toString()));
//					preSplitFilter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
					view.setFilter(preSplitFilter);

					InvitePreSplitCollection splitCols = InvitePreSplitFactory.getLocalInstance(ctx).getInvitePreSplitCollection(view);
					if(splitCols.size() == 1)
					{
						InvitePreSplitInfo splitInfo = splitCols.get(0);
						InvitePreSplitFactory.getLocalInstance(ctx).setContractSplit(splitInfo.getId().toString(), flag);
					}
				}
			}
		}
	}
	
	public static void setInvitePreSplitState(Context ctx, IObjectPK[] arrayPK, boolean flag) throws BOSException, EASBizException {
		for(int i = 0; i < arrayPK.length; ++i)
		{
			setInvitePreSplitState(ctx, arrayPK[i], flag);
		}
	}
	
}
