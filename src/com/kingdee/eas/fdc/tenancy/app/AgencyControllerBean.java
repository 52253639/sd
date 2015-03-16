package com.kingdee.eas.fdc.tenancy.app;

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
import com.kingdee.bos.service.ServiceStateManager;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.fdc.sellhouse.app.DataBaseCodeRuleHelper;
import com.kingdee.eas.fdc.tenancy.AgencyCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.EffectedStatusEnum;
import com.kingdee.eas.framework.IFWStatus;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.tenancy.AgencyInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;

public class AgencyControllerBean extends AbstractAgencyControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.AgencyControllerBean");
    //by tim_gao 因断号编码不能使用，添加方法 2010-11-16
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		DataBaseCodeRuleHelper.handleIntermitNumber(ctx, (DataBaseInfo)model);
		CoreBaseInfo coreBaseInfo = (CoreBaseInfo) model;
        if (_isExistPropertyName(ctx , coreBaseInfo , IFWStatus.effectedStatus))
        {
            coreBaseInfo.setInt(IFWStatus.effectedStatus ,
                                EffectedStatusEnum.EFFECTED_VALUE);
        }
        IObjectPK retValue;
        //使用权限服务可用。by yyb at 2005-1-4
        ServiceStateManager.getInstance().enableNextCallServices();
        //说明，与张红斌讨论，只要判断ID为空就可以了，因为this._exists(ctx , pk)会进行一次数据访问，增加了负担。
        //后赵进要求进行判断。
        if (coreBaseInfo.getId() != null &&
            this._exists(ctx , new ObjectUuidPK(coreBaseInfo.getId())))
        //    if (coreBaseInfo.getId() != null)
        {
            retValue = new ObjectUuidPK(coreBaseInfo.getId());
            this._checkNumberDup(ctx, coreBaseInfo);
            super.update(ctx , retValue , coreBaseInfo);
            return retValue;
        }
        else
        {
            retValue = super.addnew(ctx , coreBaseInfo);
            return retValue;
        }
		
		
	}
	
	//by tim_gao 回收编码规则 2010-11-16
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
	    	for (int i = 0; i < arrayPK.length; i++) {
	    		DataBaseCodeRuleHelper.recycleNumber(ctx, (DataBaseInfo) _getValue(ctx, arrayPK[i]));
			}
	    	super._delete(ctx, arrayPK);
	}
	    
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
	    	DataBaseCodeRuleHelper.recycleNumber(ctx, (DataBaseInfo) _getValue(ctx, pk));
	    	super._delete(ctx, pk);
	}
}