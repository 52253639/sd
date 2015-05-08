package com.kingdee.eas.fdc.schedule;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;

public class FDCSCHUtils {

	private static NumberFormat NUMBER_FORMAT = new DecimalFormat("00");

	public static String formatTaskNumber(int number) {
		return NUMBER_FORMAT.format(number);
	}

	public static BigDecimal getDefaultDuration() {
		return FDCHelper.ONE;
	}

	/**
	 * 描述：更新进度任务工程量引用ID
	 * 
	 * @param isDel
	 *            false:新增/true :删除
	 * @param contractId
	 * @param objectPK
	 *            单据id集合
	 * @param idList
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void updateTaskRef(Context ctx, boolean isFromFillBill, boolean isLoadStrict, boolean isDel, String contractId,
			IObjectPK[] objectPKs, List idList) throws EASBizException, BOSException {
		if (objectPKs.length == 0) {
			return;
		}

		String[] pks = new String[objectPKs.length];
		for (int i = 0; i < objectPKs.length; i++) {
			pks[i] = objectPKs[i].toString();
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		if (isFromFillBill) {
			if (isLoadStrict) {
				if (isDel) {
					builder.appendSql(" update t_fpm_projectfillbill set fobjectid=null where ");
					builder.appendParam("fobjectid", pks);
				} else {
					builder.appendSql(" update t_fpm_projectfillbill set fobjectid=? ");
					builder.addParam(objectPKs[0].toString());
					builder.appendSql("where ");
					builder.appendParam("fid", idList.toArray());
				}

			} else {
				if (isDel) { // 删除单据，更新引用
					builder.appendSql("update t_sch_taskloadentry set fobjectid=null where ");
					builder.appendParam("fobjectid", pks);
				} else { // 新增，标识引用
					builder.appendSql("update t_sch_taskloadentry set fobjectid=? ");
					builder.addParam(objectPKs[0].toString());
					builder.appendSql("where ");
					builder.appendParam("fid", idList.toArray());
				}
			}

			builder.executeUpdate();
			builder.clear();
		}
	}
}
