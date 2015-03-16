package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;

public class AppraiseGuideLineTypeControllerBean extends AbstractAppraiseGuideLineTypeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.AppraiseGuideLineTypeControllerBean");


	protected void _checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		super._checkNumberDup(ctx, model);
	}

	
	protected void _checkNameBlank(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		super._checkNameBlank(ctx, model);
	}

	
	protected void _checkNameDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		super._checkNameDup(ctx, model);
	}

	protected void _checkNumberBlank(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		super._checkNumberBlank(ctx, model);
	}

	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		_checkNumberDup(ctx, model);
		_checkNameDup(ctx,model);
		return super._save(ctx, model);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		_checkNumberDup(ctx, model);
		_checkNameDup(ctx,model);
		return super._submit(ctx, model);
	}
}