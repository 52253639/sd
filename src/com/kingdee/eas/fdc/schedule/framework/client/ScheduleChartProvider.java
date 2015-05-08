package com.kingdee.eas.fdc.schedule.framework.client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ScheduleChartProvider extends FDCBillDataProvider {
	private static final Logger logger = CoreUIObject
			.getLogger(ScheduleChartProvider.class);

	String name = null;
	BufferedImage img = null;

	public ScheduleChartProvider(String name, BufferedImage bufferedImage) {
		super(null, null);
		this.name = name;
		this.img = bufferedImage;
	}

	/**
	 * 新建一个数据集，利用传入的图片，构造byte[]图片字段并返回给套打
	 */
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		try {
			DynamicRowSet rs = new DynamicRowSet(2);
			ColInfo ci = new ColInfo();
			ci.colType = Types.VARCHAR;
			ci.columnName = "imgName";
			ci.nullable = 1;
			rs.setColInfo(1, ci);
			ci = new ColInfo();
			ci.colType = Types.BLOB;
			ci.columnName = "img";
			ci.nullable = 1;
			rs.setColInfo(2, ci);
			rs.beforeFirst();

			rs.moveToInsertRow();
			if (!FDCHelper.isEmpty(name)) {
				rs.updateObject("imgName", name);
			}
			if (!FDCHelper.isEmpty(img)) {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
				encoder.encode(img);
				rs.updateObject("img", os.toByteArray());
			}
			rs.insertRow();
			rs.beforeFirst();
			return rs;
		} catch (SQLException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		return null;
	}
}
