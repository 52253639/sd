package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;

public class TemplateTypeControllerBean extends AbstractTemplateTypeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.TemplateTypeControllerBean");
    
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		super._checkNumberDup(ctx,model);
		super._checkNameDup(ctx,model);
		
		return super._save(ctx, model);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		super._checkNumberDup(ctx,model);
		super._checkNameDup(ctx,model);
		return super._submit(ctx, model);
	}
}