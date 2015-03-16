package com.kingdee.eas.fdc.market.app;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.market.MovementPlanE2Collection;
import com.kingdee.eas.fdc.market.MovementPlanE2Info;
import com.kingdee.eas.fdc.market.MovementPlanInfo;
import com.kingdee.eas.framework.CoreBaseInfo;

public class MovementPlanControllerBean extends AbstractMovementPlanControllerBean {

	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.market.app.MovementPlanControllerBean");

	public IObjectPK submit(Context ctx, CoreBaseInfo model) throws BOSException, EASBizException {
		MovementPlanE2Info movementPlanE2Info = null;
		MovementPlanE2Collection movementPlanE2Collection = null;
		MovementPlanInfo movementInfo = (MovementPlanInfo) model;
		movementPlanE2Collection = movementInfo.getE2();
		BigDecimal totalMoney = new BigDecimal(0);
		if (movementInfo.isIsEnable()) {
			for (int i = 0; i < movementPlanE2Collection.size(); i++) {
				movementPlanE2Info = movementPlanE2Collection.get(i);
				if (movementPlanE2Info.getMoney() != null) {
					totalMoney = totalMoney.add(movementPlanE2Info.getMoney());
				}
			}
		} else {
			totalMoney = movementInfo.getTotalMoney();
		}
		movementInfo.setTotalMoney(totalMoney);
		return super.submit(ctx, movementInfo);
	}

	public IObjectPK save(Context ctx, CoreBaseInfo model) throws EASBizException, BOSException {
		IObjectPK pk = super.save(ctx, model);
		MovementPlanE2Info movementPlanE2Info = null;
		MovementPlanE2Collection movementPlanE2Collection = null;
		MovementPlanInfo movementInfo = (MovementPlanInfo) model;
		movementPlanE2Collection = movementInfo.getE2();
		BigDecimal totalMoney = new BigDecimal(0);
		if (movementInfo.isIsEnable()) {
			for (int i = 0; i < movementPlanE2Collection.size(); i++) {
				movementPlanE2Info = movementPlanE2Collection.get(i);
				if (movementPlanE2Info.getMoney() != null) {

					totalMoney = totalMoney.add(movementPlanE2Info.getMoney());
				}
			}
		} else {

			totalMoney = movementInfo.getTotalMoney();
		}
		movementInfo.setTotalMoney(totalMoney);
		super.save(ctx, movementInfo);
		return pk;
	}
}