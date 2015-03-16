package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.WebMarkFieldInfo;
import com.kingdee.eas.fdc.sellhouse.WebMarkProcessInfo;
import com.kingdee.eas.framework.CoreBillEntryBaseCollection;
import com.kingdee.eas.framework.Result;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.sellhouse.WebMarkFieldCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.app.CoreBaseControllerBean;
import com.kingdee.eas.framework.app.CoreBillEntryBaseControllerBean;
import com.kingdee.eas.util.app.DbUtil;
import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;

public class WebMarkFieldControllerBean extends
		AbstractWebMarkFieldControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.WebMarkFieldControllerBean");

	public void submit(Context ctx, IObjectPK pk, CoreBaseInfo model)
			throws BOSException, EASBizException {
	}

	public Result submit(Context ctx, CoreBaseCollection colls) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		// 处理方案修改时步骤总是新增的问题:把当前方案下所有字段删掉先，但要确保model中每个步骤数据有原来的Id
		if(colls.size()<1)return null;
		WebMarkFieldInfo wkFieldInfo=(WebMarkFieldInfo) colls.get(0);
		WebMarkProcessInfo wkProcInfo=wkFieldInfo.getParent();
		String pk=wkProcInfo.getId().toString();
		String sqlOld = "delete t_SHE_WebMarkField where FParentID='"
				+ pk.toString() + "'";
		DbUtil.execute(ctx, sqlOld);
		return super.submit(ctx, colls);
	}
	
	
}