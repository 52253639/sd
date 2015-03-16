package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryInfo;

public interface IRevDistillFace {

	//判断计提单是否已经对该收款单提佣分录计提
	boolean isAlreadyDistill(Context ctx,ReceiveDistillCommisionEntryInfo recDistillEntryInfo)throws BOSException;
}
