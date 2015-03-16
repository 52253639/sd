package com.kingdee.eas.fdc.invite;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

public class AppraiseResultHelper {

	public static BigDecimal getAIMCost(Context ctx, String id,
			String codingNumber) {
		if (FDCHelper.isEmpty(id) || FDCHelper.isEmpty(codingNumber)) {
			return FDCHelper.ZERO;
		}
		FDCSQLBuilder builder;
		if (ctx != null) {
			builder = new FDCSQLBuilder(ctx);
		} else {
			builder = new FDCSQLBuilder();
		}
		builder.appendSql(" select sum(ce.FCostAmount) as costAmt ");
		builder.appendSql(" from T_INV_AppraiseResult as ar ");
		builder.appendSql(" inner join T_INV_InviteProject as ip ");
		builder.appendSql(" on ar.FInviteProjectID = ip.FID ");
		builder.appendSql(" inner join T_CON_ProgrammingContract as pc ");
		builder.appendSql(" on ip.FProgrammingContractId = pc.FID ");
		builder.appendSql(" inner join T_CON_Programming as pg ");
		builder.appendSql(" on pc.FProgrammingID = pg.FID ");
		builder.appendSql(" inner join T_AIM_AimCost as ac ");
		builder.appendSql(" on pg.FAimCostID = ac.FID ");
		builder.appendSql(" inner join T_AIM_CostEntry as ce ");
		builder.appendSql(" on ce.FHeadID = ac.FID ");
		builder.appendSql(" inner join T_FDC_CostAccount as ca ");
		builder.appendSql(" on ce.FCostAccountID = ca.FID ");
		builder.appendSql(" inner join T_FDC_CostAccount as ca2 ");
		builder
				.appendSql(" on charIndex(ca2.FCodingNumber, ca.FCodingNumber)=1 ");
		builder
				.appendSql(" and (ca2.FCurProject = ca.FCurProject or ca2.FFullOrgUnit = ca.FFullOrgUnit) ");
		builder.appendSql(" where ca2.FCodingNumber = ? ");
		builder.addParam(codingNumber);
		builder.appendSql(" and ar.FID = ? ");
		builder.addParam(id);
		try {
			IRowSet rs = builder.executeQuery();
			while (rs.next()) {
				BigDecimal aimCost = rs.getBigDecimal("costAmt");
				return aimCost;
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return FDCHelper.ZERO;
	}

}
