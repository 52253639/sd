package com.kingdee.eas.fdc.invite.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.invite.ColumnTypeEnum;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.HeadTypeFactory;
import com.kingdee.eas.fdc.invite.HeadTypeInfo;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.jdbc.rowset.IRowSet;

public class HeadTypeControllerBean extends AbstractHeadTypeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.HeadTypeControllerBean");
    protected boolean _updateRelateData(Context ctx, String oldID, String newID, Object[] tables)throws BOSException
    {
    	if (tables == null) {
			return false;
		}

		for (int i = 0; i < tables.length; i++) {
			String sql = "update " + tables[i] + " set FHeadTypeID='" + newID
					+ "' where FHeadTypeID='" + oldID + "'";
			SQLExecutorFactory.getLocalInstance(ctx, sql).executeSQL();
		}
		return true;
    }

    protected Object[] _getRelateData(Context ctx, String id, String[] tables)throws BOSException
    {
    	if (tables == null) {
			return new Object[0];
		}
		HashSet set = new HashSet();
		for (int i = 0; i < tables.length; i++) {
			String table = tables[i];
			String sql = "select count(*) as count from " + table
					+ " where FHeadTypeID='" + id + "'";
			IRowSet rowSet = SQLExecutorFactory.getLocalInstance(ctx, sql)
					.executeSQL();
			try {
				if (rowSet.next()) {
//					Collection collection = rowSet.toCollection();
//					Iterator iterator = collection.iterator();
//					Vector obj = (Vector) iterator.next();
//					BigDecimal count = new BigDecimal(obj.get(0).toString());
					BigDecimal count = rowSet.getBigDecimal(1);
					if (count.intValue() > 0) {
						set.add(table);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e.getMessage());
			}
		}
		return set.toArray();
    }

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		HeadTypeInfo info = (HeadTypeInfo)model;
		if(info.getParent()!=null){
			for (int i = 0; i < info.getParent().getEntries().size(); i++) {
				info.getParent().getEntries().removeObject(i);
				i--;
			}
			HeadTypeFactory.getLocalInstance(ctx).save(info.getParent());
		}
		return super._submit(ctx, model);
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		HeadTypeInfo info = (HeadTypeInfo) super.getValue(ctx, pk);
		if(info.getParent()!=null){
//			HeadColumnInfo entryInfoItemNum = new HeadColumnInfo();
//			entryInfoItemNum.setName(EASResource
//					.getString(
//							"com.kingdee.eas.fdc.invite.FDCInviteResource",
//							"ListintItemNumber"));
//			entryInfoItemNum.setDescription(EASResource
//					.getString(
//							"com.kingdee.eas.fdc.invite.FDCInviteResource",
//							"system"));
//			entryInfoItemNum.setIsQuoting(false);
//			entryInfoItemNum.setLevel(1);
//			entryInfoItemNum.setHeadType(info.getParent());
//			entryInfoItemNum.setIsHasChild(false);
//			entryInfoItemNum.setColumnType(ColumnTypeEnum.String);
//			entryInfoItemNum.setIsRefPrice(false);
//			entryInfoItemNum.setProperty(DescriptionEnum.System);
//			entryInfoItemNum.setId(BOSUuid.create(entryInfoItemNum.getBOSType()));
//			info.getParent().getEntries().add(entryInfoItemNum);
			Locale locale = ctx.getLocale();
			HeadColumnInfo entryInfoItemName = new HeadColumnInfo();
			String resBase = "com.kingdee.eas.fdc.invite.FDCInviteResource";
			entryInfoItemName.setName(ResourceBase.getString(resBase,
					"ListintItemName", locale));
			entryInfoItemName.setDescription(ResourceBase.getString(resBase,
					"system", locale));
			entryInfoItemName.setLongNumber("null!01");
			entryInfoItemName.setIsQuoting(false);
			entryInfoItemName.setLevel(1);
			entryInfoItemName.setHeadType(info.getParent());
			entryInfoItemName.setIsHasChild(false);
			entryInfoItemName.setColumnType(ColumnTypeEnum.String);
			entryInfoItemName.setIsRefPrice(false);
			entryInfoItemName.setProperty(DescriptionEnum.System);
			entryInfoItemName.setId(BOSUuid.create(entryInfoItemName
					.getBOSType()));
			info.getParent().getEntries().add(entryInfoItemName);

			HeadColumnInfo entryInfoUnit = new HeadColumnInfo();
			entryInfoUnit.setName(ResourceBase.getString(resBase, "Unit",
					locale));
			entryInfoUnit.setDescription(ResourceBase.getString(resBase,
					"system", locale));
			entryInfoUnit.setLongNumber("null!02");
			entryInfoUnit.setIsQuoting(false);
			entryInfoUnit.setLevel(1);
			entryInfoUnit.setHeadType(info.getParent());
			entryInfoUnit.setIsHasChild(false);
			entryInfoUnit.setColumnType(ColumnTypeEnum.String);
			entryInfoUnit.setIsRefPrice(false);
			entryInfoUnit.setProperty(DescriptionEnum.System);
			entryInfoUnit.setId(BOSUuid.create(entryInfoUnit.getBOSType()));
			info.getParent().getEntries().add(entryInfoUnit);

			HeadColumnInfo entryInfoProjectAmt = new HeadColumnInfo();
			entryInfoProjectAmt.setName(ResourceBase.getString(resBase,
					"ProjectAmt", locale));
			entryInfoProjectAmt.setDescription(ResourceBase.getString(resBase,
					"system", locale));
			entryInfoProjectAmt.setLongNumber("null!03");
			entryInfoProjectAmt.setIsQuoting(false);
			entryInfoProjectAmt.setLevel(1);
			entryInfoProjectAmt.setHeadType(info.getParent());
			entryInfoProjectAmt.setIsHasChild(false);
			entryInfoProjectAmt.setColumnType(ColumnTypeEnum.Amount);
			entryInfoProjectAmt.setIsRefPrice(false);
			entryInfoProjectAmt.setProperty(DescriptionEnum.ProjectAmtSum);
			entryInfoProjectAmt.setId(BOSUuid.create(entryInfoProjectAmt
					.getBOSType()));
			info.getParent().getEntries().add(entryInfoProjectAmt);

			HeadColumnInfo entryInfoPrice = new HeadColumnInfo();
			entryInfoPrice.setName(ResourceBase.getString(resBase,
					"TotalPrice", locale));
			entryInfoPrice.setDescription(ResourceBase.getString(resBase,
					"system", locale));
			entryInfoPrice.setLongNumber("null!04");
			entryInfoPrice.setIsQuoting(true);
			entryInfoPrice.setLevel(1);
			entryInfoPrice.setHeadType(info.getParent());
			entryInfoPrice.setIsHasChild(true);
			entryInfoPrice.setColumnType(ColumnTypeEnum.Amount);
			entryInfoPrice.setIsRefPrice(true);
			entryInfoPrice.setProperty(DescriptionEnum.TotalPriceSum);
			entryInfoPrice.setId(BOSUuid.create(entryInfoPrice.getBOSType()));
			info.getParent().getEntries().add(entryInfoPrice);

			HeadColumnInfo entryInfoAmount = new HeadColumnInfo();
			entryInfoAmount.setName(ResourceBase.getString(resBase, "Amount",
					locale));
			entryInfoAmount.setDescription(ResourceBase.getString(resBase,
					"system", locale));
			entryInfoAmount.setLongNumber("null!05");
			entryInfoAmount.setIsQuoting(false);
			entryInfoAmount.setLevel(1);
			entryInfoAmount.setHeadType(info.getParent());
			entryInfoAmount.setIsHasChild(false);
			entryInfoAmount.setColumnType(ColumnTypeEnum.Amount);
			entryInfoAmount.setIsRefPrice(false);
			entryInfoAmount.setProperty(DescriptionEnum.AmountSum);
			entryInfoAmount.setId(BOSUuid.create(entryInfoAmount.getBOSType()));
			info.getParent().getEntries().add(entryInfoAmount);

			HeadColumnInfo entryInfoDes = new HeadColumnInfo();
			entryInfoDes.setName(ResourceBase.getString(resBase, "Descri",
					locale));
			entryInfoDes.setDescription(ResourceBase.getString(resBase,
					"system", locale));
			entryInfoDes.setLongNumber("null!06");
			entryInfoDes.setIsQuoting(false);
			entryInfoDes.setLevel(1);
			entryInfoDes.setHeadType(info.getParent());
			entryInfoDes.setIsHasChild(false);
			entryInfoDes.setColumnType(ColumnTypeEnum.String);
			entryInfoDes.setIsRefPrice(false);
			entryInfoDes.setProperty(DescriptionEnum.System);
			entryInfoDes.setId(BOSUuid.create(entryInfoDes.getBOSType()));
			info.getParent().getEntries().add(entryInfoDes);

			HeadTypeFactory.getLocalInstance(ctx).save(info.getParent());
		}
		super._delete(ctx, pk);
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {

		super._delete(ctx, arrayPK);
	}


}