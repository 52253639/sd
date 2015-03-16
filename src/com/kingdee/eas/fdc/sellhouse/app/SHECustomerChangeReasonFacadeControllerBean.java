package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.SHECustomerChangeDetailFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerChangeDetailInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellHouseException;

public class SHECustomerChangeReasonFacadeControllerBean extends AbstractSHECustomerChangeReasonFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.SHECustomerChangeReasonFacadeControllerBean");
    
    
    
    protected void _addNewMessage(Context ctx, IObjectValue company, String reason, IObjectValue sheCustomer, IObjectValue propertyConsultant) throws BOSException, SellHouseException, EASBizException {
    	SHECustomerChangeDetailInfo info = new SHECustomerChangeDetailInfo();
    	if(sheCustomer != null){
    		info.setSheCustomer((SHECustomerInfo)sheCustomer);
    	}
    	if(company != null){
        	info.setOrgUnit((FullOrgUnitInfo)company);
    	}
    	if(propertyConsultant == null){
    		BOSUuid obj = ((SHECustomerInfo)sheCustomer).getPropertyConsultant().getId();
    		UserInfo ui = UserFactory.getLocalInstance(ctx).getUserInfo(new ObjectUuidPK(obj.toString()));
    		info.setPropertyConsultant(ui);
    	}
    	else{
    		UserInfo ui = (UserInfo)propertyConsultant;
    		info.setPropertyConsultant(ui);
    	}
    	info.setReason(reason);
    	SHECustomerChangeDetailFactory.getLocalInstance(ctx).save(info);
		
	}
}