/*
 * @(#)AchievementMangerDataProvider.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.sql.SQLException;
import java.sql.Types;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

public class AchievementMangerDataProvider extends FDCBillDataProvider {

	public AchievementMangerDataProvider(String billId, IMetaDataPK mainQuery) {
		/* TODO 自动生成构造函数存根 */
		super(billId, mainQuery);
	}
	
	
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		if (ds.getID().equals("DocumentListTDQuery")) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boId", billId));
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("attachment.name");
			sic.add("attachment.type");
			sic.add("attachment.size");
			view.setSelector(sic);
			view.setFilter(filter);
			try {
				String[] colsDesc = new String[] { "type", "size", "name" };
				DynamicRowSet rowSet = new DynamicRowSet(colsDesc.length);
				for (int i = 0; i < colsDesc.length; i++) {
					ColInfo ci = new ColInfo();
					ci.colType = Types.VARCHAR;
					ci.columnName = colsDesc[i];
					ci.nullable = 1;
					rowSet.setColInfo(i + 1, ci);
				}
				BoAttchAssoCollection cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
				if (cols.size() == 0) {
					return rowSet;
				}

				BoAttchAssoInfo boAsso = null;
				for (int i = 0; i < cols.size(); i++) {
					boAsso = cols.get(i);
					rowSet.moveToInsertRow();
					rowSet.updateString("type", boAsso.getAttachment().getType());
					rowSet.updateString("size", boAsso.getAttachment().getSize());
					rowSet.updateString("name", boAsso.getAttachment().getName());
					rowSet.insertRow();
				}
				rowSet.beforeFirst();
				return rowSet;
			} catch (BOSException e) {
				/* TODO 自动生成 catch 块 */
				e.printStackTrace();
			} catch (SQLException e) {
				/* TODO 自动生成 catch 块 */
				e.printStackTrace();
			}
		}
		if (ds.getID().equals("AchievementManagerTDQuery")) {
			return super.getMainBillRowSet(ds);
		}
		return null;
	}

}
