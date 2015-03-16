package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.invite.AcceptanceLetterFactory;
import com.kingdee.eas.fdc.invite.AcceptanceLetterInfo;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;

public class ContractBillAcceptanceLetterHelper {

	public static void acceptanceLetterDeleteHelper(Context ctx, IObjectPK pk)
	throws BOSException, EASBizException {
		//获取关联生成的合同
		ContractBillInfo billInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(pk);
		String sourceBillId = billInfo.getSourceBillId();
		if(sourceBillId != null)
		{
			FilterInfo Letterfilter = new FilterInfo();
			Letterfilter.getFilterItems().add(new FilterItemInfo("id", sourceBillId));

			if(AcceptanceLetterFactory.getLocalInstance(ctx).exists(Letterfilter))
			{
				//更新中标通知书状态
				AcceptanceLetterFactory.getLocalInstance(ctx).setCreateContract(sourceBillId, false);

				//更新招标立项的状态
				IObjectPK letterPK = new ObjectUuidPK(sourceBillId);
				AcceptanceLetterInfo letterInfo = AcceptanceLetterFactory.getLocalInstance(ctx).getAcceptanceLetterInfo(letterPK);
				String invitePrjId = letterInfo.getInviteProject().getId().toString();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("inviteProject.id", invitePrjId));
				filter.getFilterItems().add(new FilterItemInfo("isCreateContract", Boolean.TRUE));
				filter.getFilterItems().add(new FilterItemInfo("id", sourceBillId, CompareType.NOTEQUALS));
				if (!AcceptanceLetterFactory.getLocalInstance(ctx).exists(filter)) { // 该招标立项下如果没有已关联生成合同的中标通知书
					IObjectPK inviteProjectPK = new ObjectUuidPK(letterInfo.getInviteProject().getId().toString());
//					InviteProjectFactory.getLocalInstance(ctx).setFixed(inviteProjectPK);
				}
			}
		}
	}
	
	public static void acceptanceLetterDeleteHelper(Context ctx, IObjectPK[] arrayPK)
	throws BOSException, EASBizException {
		for(int i = 0; i < arrayPK.length; ++i)
		{
			acceptanceLetterDeleteHelper(ctx, arrayPK[i]);
		}
	}
}
