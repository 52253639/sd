package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.IObjectValue;

//客户值对象接口，用以统一处理各业务系统客户的操作逻辑
public interface IPartCustomerInfo extends IObjectValue{
	 FDCMainCustomerInfo getMainCustomer();
	 
	 void setMainCustomer(FDCMainCustomerInfo item);
}
