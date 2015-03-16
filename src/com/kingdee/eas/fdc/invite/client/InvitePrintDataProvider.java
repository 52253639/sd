package com.kingdee.eas.fdc.invite.client;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;

import javax.imageio.ImageIO;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

/**
 * 采购招标审批数据提供者
 * 
 * @author liangliang_ye
 * 
 */
public class InvitePrintDataProvider extends FDCBillDataProvider {

	public static final String[] col = new String[] { "id", "inviteProject.name", "project.name", "inviteType.name",
		"inviteProject.inviteForm",	"entryImage" };

	public KDTable kdtEntry = null;

	public InvitePrintDataProvider(String billId, IMetaDataPK mainQuery, KDTable kdtEntry) {
		super(billId, mainQuery);
		this.kdtEntry = kdtEntry;
	}

	public InvitePrintDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	}

	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		DynamicRowSet row = null;
		if ("ATTCH".equals(ds.getID().toUpperCase())) {// 假设主数据源名称为mainbill
			iRowSet = getAttchRowSet(ds);// 返加主数据源的结果集
		} else {
			iRowSet = super.getMainBillRowSet(ds);

			try {
				row = new DynamicRowSet(col.length);
				for (int i = 0; i < col.length; i++) {
					ColInfo ci = new ColInfo();
					if (i == col.length - 1) {
						ci.colType = Types.BINARY;
					} else {
						ci.colType = Types.VARCHAR;
					}

					ci.columnName = col[i];
					ci.nullable = 1;
					row.setColInfo(i + 1, ci);
				}

				BufferedImage bi = getImage(kdtEntry);
				byte[] imgbyte = null;
				ByteArrayOutputStream bao = new ByteArrayOutputStream();
				ImageIO.write(bi, "jpg", bao);
				imgbyte = bao.toByteArray();

				while (iRowSet.next()) {
					String id = iRowSet.getString("id");
					String inviteProject = iRowSet.getString("inviteProject.name");
					String project = iRowSet.getString("project.name");
					String inviteType = iRowSet.getString("inviteType.name");
					String inviteForm = iRowSet.getString("inviteProject.inviteForm");

					row.moveToInsertRow();
					row.updateString("id", id);
					row.updateString("inviteProject.name", inviteProject);
					row.updateString("project.name", project);
					row.updateString("inviteType.name", inviteType);
					row.updateString("inviteProject.inviteForm", inviteForm);
					row.updateObject("entryImage", imgbyte);
					row.insertRow();
					row.beforeFirst();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return row;
	}

	/**
	 * 
	 * @param ds
	 * @return
	 */
	public IRowSet getAttchRowSet(BOSQueryDataSource ds){
		IRowSet iRowSet = null;
        try {
        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(getATTCHQueryPK());
            exec.option().isAutoTranslateEnum= true;
            EntityViewInfo ev = new EntityViewInfo();
            FilterInfo filter = new FilterInfo();
            filter.getFilterItems().add(new FilterItemInfo("boAttchAsso.boID", billId, CompareType.EQUALS));
            ev.setFilter(filter);
            exec.setObjectView(ev);
            iRowSet = exec.executeQuery();	
            iRowSet.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iRowSet;
	}
	
	protected IMetaDataPK getATTCHQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.invite.app.AttchForPrintQuery");
	}

	/** 将组件转换为缓存图片 */
	private BufferedImage getImage(Component component) {
        KDTable tdt = (KDTable) component;
        int size = 0;
        
        for(int i=0;i<tdt.getColumnCount();i++){
        	size+=tdt.getColumn(i).getWidth();
        }
        
        tdt.setSize(size,tdt.getHeight());
		
        int height = tdt.getHeight();
		if (height == 0) {
			height = 1; // 因为height＝0时，下面一句会报异常，所以赋为1避免异常
		}
		BufferedImage bi = new BufferedImage(tdt.getWidth(), height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = bi.createGraphics();
		component.paint(g2);
		g2.dispose();
	
		return bi;
	}

}