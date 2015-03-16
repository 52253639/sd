package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import java.sql.SQLException;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class PurchaseFacadeControllerBean extends
		AbstractPurchaseFacadeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.PurchaseFacadeControllerBean");

	Set set = null;

	protected Set _findByChanged(Context ctx, int state, boolean i)
			throws BOSException {
		MsgBox.showConfirm2("进入facade开始查询");
		String sql = "select a.fid,a.FPurchaseState,sum(c.FAmount) as FSumAmount from T_SHE_Purchase as a left join T_SHE_FDCReceiveBill as b on a.fid=b.FPurchaseID left join T_CAS_ReceivingBill as c on b.Fid=c.FFdcReceiveBillID group by a.fid,a.FPurchaseState";
		IRowSet rs = null;
		try {
			rs = DbUtil.executeQuery(ctx, sql);
		} catch (BOSException e) {
			MsgBox.showConfirm2("查询报错");
		}
		MsgBox.showConfirm2("查询结束");
		if (rs != null && rs.size() > 0) {
			MsgBox.showConfirm2("44");
			try {
				for (int ii = 0; ii < rs.size(); ii++) {
					String FPurchaseState = null;
					FPurchaseState = rs.getString("FPurchaseState");
					if (state == 1) {
						if (PurchaseStateEnum.PREPURCHASEAPPLY_VALUE
								.equals(FPurchaseState)
								|| PurchaseStateEnum.PREPURCHASECHECK_VALUE
										.equals(FPurchaseState)) {
							addSet(i, rs);
						}
					} else if (state == 2) {
						if ((!PurchaseStateEnum.PREPURCHASEAPPLY_VALUE
								.equals(FPurchaseState))
								&& (!PurchaseStateEnum.PREPURCHASECHECK_VALUE
										.equals(FPurchaseState))) {
							addSet(i, rs);
						}
					} else {
						set.add(rs.getString("fid"));
					}
				}
			} catch (SQLException e) {
				MsgBox.showConfirm2("报错");
			}
		}
		return null;
	}

	private void addSet(boolean i, IRowSet rs) throws SQLException {
		double FSumAmount = 0;
		FSumAmount = rs.getDouble("FSumAmount");
		if (i) {
			if (FSumAmount > 0) {
				set.add(rs.getString("fid"));
			}
		} else {
			if (FSumAmount == 0) {
				set.add(rs.getString("fid"));
			}
		}
	}

	protected IRowSet _findAllState(Context ctx) throws BOSException {
		String sql = "select a.fid,a.FPurchaseState,sum(c.FAmount) as FSumAmount from T_SHE_Purchase as a left join T_SHE_FDCReceiveBill as b on a.fid=b.FPurchaseID left join T_CAS_ReceivingBill as c on b.Fid=c.FFdcReceiveBillID group by a.fid,a.FPurchaseState";
		IRowSet rs = null;
		try {
			rs = DbUtil.executeQuery(ctx, sql);
		} catch (BOSException e) {
			MsgBox.showConfirm2("查询报错");
		}
		return rs;
	}

}