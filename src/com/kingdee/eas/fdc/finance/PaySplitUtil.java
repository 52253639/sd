package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;

public class PaySplitUtil {

	public static ContractCostSplitInfo getMonthlyContractSplit(Context ctx,String contractID,String prjID) throws BOSException,EASBizException{
		ContractCostSplitInfo contractSplitInfo = new ContractCostSplitInfo();
		PeriodInfo current = FDCUtils.getCurrentPeriod(ctx, prjID, false);
		if(current!=null){
			PeriodInfo used = PeriodUtils.getPrePeriodInfo(ctx, current);
			EntityViewInfo viewMonth = new EntityViewInfo();
			FilterInfo filterMonth = new FilterInfo();
			filterMonth.getFilterItems().add(new FilterItemInfo("entityID",contractSplitInfo.getBOSType().toString()));
			filterMonth.getFilterItems().add(new FilterItemInfo("curProjectID",prjID));
			filterMonth.getFilterItems().add(new FilterItemInfo("settlePeriod.id",used.getId().toString()));
			viewMonth.setFilter(filterMonth);
			SettledMonthlyCollection monthColl = new SettledMonthlyCollection();
			if(ctx!=null){
				SettledMonthlyFactory.getLocalInstance(ctx).getSettledMonthlyCollection(viewMonth);
			}else{
				SettledMonthlyFactory.getRemoteInstance().getSettledMonthlyCollection(viewMonth);
			}
		}
		
		return contractSplitInfo;
	}
	
	public static ConChangeSplitCollection getMonthlyConChangeSplit(String contractID) throws BOSException,EASBizException{
		ConChangeSplitCollection conChangeSplitColl = new ConChangeSplitCollection();
		ConChangeSplitInfo conChangeSplitInfo = new ConChangeSplitInfo();
		
		return conChangeSplitColl;
	}
}
