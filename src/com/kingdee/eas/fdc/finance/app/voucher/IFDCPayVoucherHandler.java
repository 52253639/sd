package com.kingdee.eas.fdc.finance.app.voucher;

import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.common.EASBizException;

public interface IFDCPayVoucherHandler {
	public IObjectCollection createPayEntrys(Map dataMap) throws EASBizException, BOSException;
	public void save(Map param,IObjectCollection payEntrys) throws BOSException;
}
