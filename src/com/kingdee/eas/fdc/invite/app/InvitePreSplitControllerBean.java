package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.invite.InvitePreSplitInfo;

public class InvitePreSplitControllerBean extends AbstractInvitePreSplitControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.InvitePreSplitControllerBean");
    
    protected IObjectPK _save(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	return super._save(ctx, model);
    }
    
    protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
    		throws BOSException, EASBizException {
    	super._save(ctx, pk, model);
    }

	protected void _setContractSplit(Context ctx, String billId,
			boolean paramIsSplit) throws BOSException, EASBizException {
		InvitePreSplitInfo info = new InvitePreSplitInfo();
		info.setId(BOSUuid.read(billId));
		info.setIsContractSplit(paramIsSplit);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("isContractSplit");
		
		this.updatePartial(ctx, info, selector);
	}

	protected void _setCreateContract(Context ctx, String billId,
			boolean paramIsCreate) throws BOSException, EASBizException {
		InvitePreSplitInfo info = new InvitePreSplitInfo();
		info.setId(BOSUuid.read(billId));
		info.setIsAssContract(paramIsCreate);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("isAssContract");
		
		this.updatePartial(ctx, info, selector);
	}
}