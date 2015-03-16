package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;

public interface ISplitImportHandler {
	void handle(IObjectValue info)throws BOSException;
}
