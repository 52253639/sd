package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.invite.InviteClarifyInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;

public class InviteClarifyControllerBean extends AbstractInviteClarifyControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.InviteClarifyControllerBean");
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		InviteClarifyInfo info=this.getInviteClarifyInfo(ctx, pk);
		for(int i=0;i<info.getEntry().size();i++){
			deleteAttachment(ctx,info.getEntry().get(i).getId().toString());
		}
		super._delete(ctx, pk);
	}
}