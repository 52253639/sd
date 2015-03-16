package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineInfo;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineCollection;
import com.kingdee.eas.fdc.invite.AppraiseTemplateEntryCollection;
import com.kingdee.eas.fdc.invite.AppraiseTemplateEntryFactory;
import com.kingdee.eas.fdc.invite.AppraiseTemplateFactory;
import com.kingdee.eas.fdc.invite.InviteProjectException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.eas.framework.DataBaseCollection;

public class AppraiseGuideLineControllerBean extends AbstractAppraiseGuideLineControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.AppraiseGuideLineControllerBean");

    protected void _cancel(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException{
    	AppraiseGuideLineInfo info = (AppraiseGuideLineInfo)model;
    	info.setIsEnable(false);
    	info.setId(BOSUuid.read(pk.toString()));
    	SelectorItemCollection selectors = new SelectorItemCollection();
    	selectors.add(new SelectorItemInfo("isEnable"));
    	this.updatePartial(ctx,info,selectors);
    }

	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
    	super._checkNumberDup(ctx,model);
//    	super._checkNameDup(ctx,model);
		return super._save(ctx, model);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		super._checkNumberDup(ctx,model);
//    	super._checkNameDup(ctx,model);
		return super._submit(ctx, model);
	}

	protected void _cancelCancel(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    	AppraiseGuideLineInfo info = (AppraiseGuideLineInfo)model;
    	info.setIsEnable(true);
    	info.setId(BOSUuid.read(pk.toString()));
    	SelectorItemCollection selectors = new SelectorItemCollection();
    	selectors.add(new SelectorItemInfo("isEnable"));
    	this.updatePartial(ctx,info,selectors);
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		String guideLineId = pk.toString();
		StringBuffer buffer = new StringBuffer();
		buffer.append("select id, guideLine where guideLine = '");
		buffer.append(guideLineId);
		buffer.append("'");
		
		AppraiseTemplateEntryCollection cols = AppraiseTemplateEntryFactory.getLocalInstance(ctx).getAppraiseTemplateEntryCollection(buffer.toString());
		if(cols.size() > 0)
		{
			/**
			 * 已经被引用，不能执行此操作
			 */
			throw new InviteProjectException(InviteProjectException.HASREFERENCEDTEMPLATE);
		}
		
		super._delete(ctx, pk);
	}
}