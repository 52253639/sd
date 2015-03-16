package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.ExpertQualifyInfo;
import com.kingdee.eas.fdc.invite.InviteProjectException;

public class ExpertQualifyControllerBean extends AbstractExpertQualifyControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.ExpertQualifyControllerBean");

	protected void _setStatus(Context ctx, String billId) throws BOSException,
			EASBizException {
		ExpertQualifyInfo info = new ExpertQualifyInfo();
		info.setState(FDCBillStateEnum.SAVED);
		info.setId(BOSUuid.read(billId));
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("state");
		this.updatePartial(ctx,info,sic);		
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
//		ExpertQualifyInfo model = this.getExpertQualifyInfo(ctx, new ObjectUuidPK(billId));
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("inviteProject",model.getInviteProject().getId().toString()));
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select expApp.fid from t_inv_expertappraise expApp ");
		builder.appendSql("left outer join t_inv_expertqualify expQfy on expApp.Finviteprojectid = expQfy.Finviteprojectid ");
		builder.appendSql(" where expQfy.fid = ? ");
		builder.addParam(billId.toString());
		
		if(builder.isExist())
			throw new InviteProjectException(InviteProjectException.HASEXPERTAPPRAISECANTUNAUDIT);
		super._unAudit(ctx, billId);
	}
	
}