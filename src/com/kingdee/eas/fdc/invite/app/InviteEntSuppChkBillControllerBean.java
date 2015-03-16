package com.kingdee.eas.fdc.invite.app;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.InviteEntSuppChkBillEntryCollection;
import com.kingdee.eas.fdc.invite.InviteEntSuppChkBillInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.UpdateHistoryTypeEnum;
import com.kingdee.jdbc.rowset.IRowSet;

public class InviteEntSuppChkBillControllerBean extends AbstractInviteEntSuppChkBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.InviteEntSuppChkBillControllerBean");
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._audit(ctx, billId);
		InviteEntSuppChkBillInfo billInfo = (InviteEntSuppChkBillInfo) _getValue(ctx, new ObjectUuidPK(billId.toString()));
		InviteEntSuppChkBillEntryCollection entry = billInfo.getEntry();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String sid = "";
		
		if(entry.size()!=0){
			String fid = "";
			for(int i=0;i<entry.size();i++){
				builder.appendSql("select fsupplierId from T_INV_InvEntSuppChkBillEntry where fid = '"+entry.get(i).getId().toString()+"'");
				IRowSet set = builder.executeQuery();
				builder.clear();
				try {
					while (set.next()) {
						fid = set.getString("fsupplierId");
						builder.appendSql("select fid from t_fdc_supplierstock where fnumber=select fnumber from T_BD_Supplier where fid = '"
								+ fid.toString() + "'");
							IRowSet set2 = builder.executeQuery();
							builder.clear();
							while (set2.next()) {
								sid = set2.getString("fid");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				IObjectPK pk = new ObjectUuidPK(sid);
				SupplierStockFactory.getLocalInstance(ctx).updateHisCount(UpdateHistoryTypeEnum.ENTERHIS, pk , true);
			}
		}
		
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._unAudit(ctx, billId);
		InviteEntSuppChkBillInfo billInfo = (InviteEntSuppChkBillInfo) _getValue(ctx,
				new ObjectUuidPK(billId.toString()));
		InviteEntSuppChkBillEntryCollection entry = billInfo.getEntry();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String sid = "";

		if (entry.size() != 0) {
			String fid = "";
			for (int i = 0; i < entry.size(); i++) {
				builder
						.appendSql("select fsupplierId from T_INV_InvEntSuppChkBillEntry where fid = '"
								+ entry.get(i).getId().toString() + "'");
				IRowSet set = builder.executeQuery();
				builder.clear();
				try {
					while (set.next()) {
						fid = set.getString("fsupplierId");
						builder
								.appendSql("select fid from t_fdc_supplierstock where fnumber=select fnumber from T_BD_Supplier where fid = '"
								+ fid.toString() + "'");
							IRowSet set2 = builder.executeQuery();
							builder.clear();
							while (set2.next()) {
								sid = set2.getString("fid");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				IObjectPK pk = new ObjectUuidPK(sid);
				SupplierStockFactory.getLocalInstance(ctx).updateHisCount(
						UpdateHistoryTypeEnum.ENTERHIS, pk, false);
			}
		}
	}
}