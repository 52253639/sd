/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.IRowSetMetaData;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

/**
 * output class name
 */
public class PaymentNoticeReportUI extends AbstractPaymentNoticeReportUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PaymentNoticeReportUI.class);
	private Map filterMap=null;
	private IRowSet rsCopy=null;
	/**
	 * output class constructor
	 */
	public PaymentNoticeReportUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		this.initTable();
		super.onLoad();
//		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.NO_SELECT);
		this.actionAddNew.setVisible(false);
		this.actionView.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
	}

	public class DataProvider implements BOSQueryDelegate {
		private IRowSet iRowSet;
		private String billID;

		public DataProvider(IRowSet rs) throws Exception {
			super();
			this.iRowSet = rs;
//			this.billID = cid;
		}
		public void setRowSet(IRowSet rs ){
			iRowSet =rs;
		}
		public IRowSet execute(BOSQueryDataSource ds) {
			return iRowSet;
		}

	}

	private CommonQueryDialog commonQueryDialog = null;

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	private PaymentNoticeReportFilterUI filterUI = null;

	private PaymentNoticeReportFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new PaymentNoticeReportFilterUI();
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {

		// TODO Auto-generated method stub
//		System.out.println(getSql(filterMap,true,true));
//		System.out.println(getSql(filterMap,true,false));
//		if(rsCopy==null){
		IRowSet rs =this.exePrint();
//		}
		BOSQueryDelegate data = new DataProvider(rs);
		// 定义printer控件
		KDNoteHelper appHlp = new KDNoteHelper();

		// 指定打印模板example的相对路径、数据provider类、窗体句柄
		// 调用打印功能
		//appHlp.print();
		appHlp.printPreview("bim/fdc/tenancy", data, SwingUtilities.getWindowAncestor(this));
	}
	
	protected boolean initDefaultFilter() {
		return true;
	}

	private void initTable() throws BOSException {
		this.tblMain.checkParsed();

		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);        
		String sFomat = "yyyy-mm-dd";
        tblMain.getColumn("STARTDATE").getStyleAttributes().setNumberFormat(sFomat);
        tblMain.getColumn("ENDDATE").getStyleAttributes().setNumberFormat(sFomat);
        sFomat = "#,#0.00";
        tblMain.getColumn("FUNITPRICE").getStyleAttributes().setNumberFormat(sFomat);
        tblMain.getColumn("LASTREAD").getStyleAttributes().setNumberFormat(sFomat);
        tblMain.getColumn("CURREAD").getStyleAttributes().setNumberFormat(sFomat);
        tblMain.getColumn("QTY").getStyleAttributes().setNumberFormat(sFomat);
        tblMain.getColumn("LMFEE").getStyleAttributes().setNumberFormat(sFomat);
        tblMain.getColumn("CMFEE").getStyleAttributes().setNumberFormat(sFomat);
        tblMain.getColumn("LATEAMT").getStyleAttributes().setNumberFormat(sFomat);
        tblMain.getColumn("SUMFEE").getStyleAttributes().setNumberFormat(sFomat);
        tblMain.getColumn("FUSEAREA").getStyleAttributes().setNumberFormat(sFomat);
        tblMain.getColumn("SUMROW").getStyleAttributes().setNumberFormat(sFomat);
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getBuildingTree(this.actionOnLoad,
				MoneySysTypeEnum.SalehouseSys));
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		execQuery();
	}

	private DynamicRowSet exePrint() throws SQLException
	{

    	//建立空数据包
        DynamicRowSet dyrs=createRowSet();
        ResultSetMetaData meta=null;
        
        String sql ;
        StringBuffer sqlSb =new StringBuffer();
        sqlSb.append("select * from ( \r\n");
        sqlSb.append("select * from ( \r\n");
        sqlSb.append(getSql(filterMap,true,true));
        sqlSb.append(") AA  \r\n");
        sqlSb.append("where 1=1 ");
        if(filterMap.get("dateFrom") != null)
        	sqlSb.append(" and FAppPayDate>=to_Date('" + filterMap.get("dateFrom") + "') \r\n");
        if(filterMap.get("dateTo") != null)
        	sqlSb.append(" and FAppPayDate<=to_Date('" + filterMap.get("dateTo") + "') \r\n");
        
        sqlSb.append("union all \r\n");
        sqlSb.append(getSql(filterMap,true,false));
        sqlSb.append(") BB  \r\n");
        sqlSb.append("where 1=1 ");
        
//        sqlSb.append("order by RoomID,CustomerID,MoneyDefineID,feeType,ProjName,SubareaName,BuildingName \r\n");

        sqlSb.append("order by RoomName,RoomID,CustomerID,MoneyDefineName,MoneyDefineID,feeType,ProjName,SubareaName,BuildingName \r\n");
        sql=sqlSb.toString();
        
        System.out.println(sql);

        ISQLExecutor se = SQLExecutorFactory.getRemoteInstance(sql);
        IRowSet rs = null;
        ICell cell = null;
        Date payDate = null;
        java.util.Date curDate = UIRuleUtil.now();
        IRowSetMetaData rsm = null;
        IRow row = null;
        IRowSet rsNext = null;
        boolean isLast = false;
        int firstRow = 0;
        int curRow = 0;
        int inCurRow=0;
        int lMax=0;
        int groupId = 1;
        double amtSum = 0.0;
        try
        {
            rs = se.executeSQL();
            rsCopy=rs.createCopy();
//            rsClone.next();
            rsNext=rs.createCopy();
            rsNext.next();
            int i2=0;
            while(rs.next()) 
            {
            	lMax++;
//            	i2+=1;
//            	System.out.println(i2);
                if(!rsNext.isLast())
                    rsNext.next();
                
                payDate = rs.getDate("FAPPPAYDATE");
                
                amtSum += rs.getDouble("FEEAMT");
    			rs.updateString("GROUPID", String.valueOf(groupId));
                if(rs.isLast() || !rs.getString("CUSTOMERID").equals(rsNext.getString("CUSTOMERID")) || !rs.getString("ROOMID").equals(rsNext.getString("ROOMID")))
                {
                	rsCopy.beforeFirst();
                	inCurRow=0;
                	while(rsCopy.next()){
                		if(inCurRow>curRow){
//                            System.out.println( inCurRow+"amtSum＝" +rsClone.getString("SUMAMT"));
                			break;
                		}
                		else if(inCurRow>=firstRow){
                			rsCopy.updateString("SUMAMT",String.valueOf(amtSum));
                			rsCopy.updateString("GROUPID", rs.getString("GROUPID"));
                		}
                		inCurRow++;
                	}
                		
                    firstRow = curRow + 1;
                    amtSum = 0;
                    groupId++;

                }
                curRow++;
            }
            //本月记录的往来费用处理
            if(rsCopy != null)
            {
//            	i2+=1;
//            	System.out.println(i2);
            	double dSum=0;
            	boolean isDelRow =false ;
                rsCopy.beforeFirst();
                rsNext = rsCopy.createCopy();
                for(int lRow=1;lRow<=lMax;lRow++)
                {
//                	System.out.println(lRow);

                    if(isDelRow){
                    	isDelRow=false;
//                    	System.out.print("------------------挂起--------\r\n");
                    	continue;
                    }
                	rsCopy.absolute(lRow);
                	if(lRow<lMax) 
                		rsNext.absolute(lRow+1);
            
//                    System.out.println("ROOMNAME:" +rsCopy.getString("ROOMNAME"));
//                    System.out.println("ROOMNAME:" +rsNext.getString("ROOMNAME"));
//                    System.out.println("ROOMID:" +rsCopy.getString("ROOMID"));
//                    System.out.println("ROOMID:" +rsNext.getString("ROOMID"));
//                    System.out.println("CUSTOMERID:" +rsCopy.getString("CUSTOMERID"));
//                    System.out.println("CUSTOMERID:" +rsNext.getString("CUSTOMERID"));
//                    System.out.println("CUSTOMERName:" +rsCopy.getString("CUSTOMERName"));
//                    System.out.println("CUSTOMERName:" +rsNext.getString("CUSTOMERName"));
//                    System.out.println("MONEYDEFINEID:" +rsCopy.getString("MONEYDEFINEID"));
//                    System.out.println("MONEYDEFINEID:" +rsNext.getString("MONEYDEFINEID"));
//                    System.out.println("MONEYDEFINENAME:" +rsCopy.getString("MONEYDEFINENAME"));
//                    System.out.println("MONEYDEFINENAME:" +rsNext.getString("MONEYDEFINENAME"));
//                    System.out.println("CUSTOMERID:" +rsCopy.getString("FEETYPE"));
//                    System.out.println("CUSTOMERID:" +rsNext.getString("FEETYPE"));
//                    System.out.println("GROUPID:" +rsCopy.getString("GROUPID"));
//                    System.out.println("GROUPID:" +rsNext.getString("GROUPID"));
                    
                    if(lRow!=lMax
                    		&& rsCopy.getString("CUSTOMERID").equals(rsNext.getString("CUSTOMERID")) 
                    		&& rsCopy.getString("ROOMID").equals(rsNext.getString("ROOMID"))
                    		&& rsCopy.getString("MONEYDEFINEID").equals(rsNext.getString("MONEYDEFINEID"))
                    		&& rsCopy.getString("FEETYPE").equals("1")
                    		&& rsNext.getString("FEETYPE").equals("2")){
                    	
                    	rsCopy.updateString("LMFEE", rsNext.getString("LMFEE"));
       
                		isDelRow=true;
                    }

                	dyrs.moveToInsertRow();
                	for (int lcol=1 ;lcol<=dyrs.getMetaData().getColumnCount();lcol++){
                		meta=dyrs.getMetaData();
                		String sKey=meta.getColumnName(lcol).trim();
                		System.out.println(sKey);
                		if(sKey.toUpperCase().equals("SUMROW")){
                			dSum=Double.parseDouble(rsCopy.getString("LMFEE"))+Double.parseDouble(rsCopy.getString("CMFEE"));
                			dyrs.updateString(sKey, String.valueOf(dSum));
//                			System.out.println("SUMROW:" +dyrs.getString("SUMROW"));
                		}
                		else if(sKey.toUpperCase().equals("NOTE")){
//                			System.out.println("note=" + filterMap.get("NOTE"));
                			dyrs.updateString(sKey, String.valueOf(filterMap.get("NOTE")));
                		}
                		else{
                			dyrs.updateString(sKey,rsCopy.getString(sKey));
                		}
                	}
//                    System.out.println("LMFEE:" +dyrs.getString("LMFEE"));
//                    System.out.println("CMFEE:" +dyrs.getString("CMFEE"));
//                    System.out.println("SUMAMT:" +dyrs.getString("SUMAMT"));
                	
                	dyrs.insertRow();
                }
                dyrs.beforeFirst();
                //临时
//                i2=1;
//                while(dyrs.next()){
//                	System.out.println(i2++);
//                    System.out.println("ROOMNAME:" +dyrs.getString("ROOMNAME"));
//                    System.out.println("CUSTOMERName:" +dyrs.getString("CUSTOMERName"));
//                    System.out.println("MONEYDEFINENAME:" +dyrs.getString("MONEYDEFINENAME"));
//                    System.out.println("startDate:" +dyrs.getString("startDate"));
//                    System.out.println("endDate :" +dyrs.getString("endDate"));
//                	 System.out.println("LMFEE:" +dyrs.getString("LMFEE"));
//                     System.out.println("CMFEE:" +dyrs.getString("CMFEE"));
//                     System.out.println("SUMAMT:" +dyrs.getString("SUMAMT"));
//                     System.out.println("SUMROW:" +dyrs.getString("SUMROW"));
//                }
//                dyrs.beforeFirst();
			}
		} catch (BOSException e) {
			super.handUIExceptionAndAbort(e);
		} catch (Exception e) {
			e.printStackTrace(); 
			return null;
		}
		return dyrs;
	}
	private DynamicRowSet createRowSet() throws SQLException{
		DynamicRowSet dynRs=new DynamicRowSet(21);
		ColInfo colInfo=new ColInfo("PROJNAME","营销项目",Types.VARCHAR,null);
		dynRs.setColInfo(1, colInfo);
		colInfo=new ColInfo("SUBAREANAME","分区",Types.VARCHAR,null);
		dynRs.setColInfo(2, colInfo);
		colInfo=new ColInfo("BUILDINGNAME","楼栋",Types.VARCHAR,null);
		dynRs.setColInfo(3, colInfo);
		colInfo=new ColInfo("BUILDINGUNITNAME","单元",Types.VARCHAR,null);
		dynRs.setColInfo(4, colInfo);
		colInfo=new ColInfo("CUSTOMERNAME","客户",Types.VARCHAR,null);
		dynRs.setColInfo(5, colInfo);
		colInfo=new ColInfo("ROOMNAME","房间",Types.VARCHAR,null);
		dynRs.setColInfo(6, colInfo);
		colInfo=new ColInfo("FUSEAREA","面积",Types.VARCHAR,null);
		dynRs.setColInfo(7, colInfo);
		colInfo=new ColInfo("MONEYDEFINENAME","款项类型",Types.VARCHAR,null);
		dynRs.setColInfo(8, colInfo);
		colInfo=new ColInfo("STARTDATE","开始日期",Types.VARCHAR,null);
		dynRs.setColInfo(9, colInfo);
		colInfo=new ColInfo("ENDDATE","结束日期",Types.VARCHAR,null);
		dynRs.setColInfo(10, colInfo);
		colInfo=new ColInfo("LASTREAD","上月读数",Types.VARCHAR,null);
		dynRs.setColInfo(11, colInfo);
		colInfo=new ColInfo("CURREAD","本月读数",Types.VARCHAR,null);
		dynRs.setColInfo(12, colInfo);
		colInfo=new ColInfo("QTY","用量",Types.VARCHAR,null);
		dynRs.setColInfo(13, colInfo);
		colInfo=new ColInfo("FUNITPRICE","单价",Types.VARCHAR,null);
		dynRs.setColInfo(14, colInfo);
		colInfo=new ColInfo("LMFEE","往月费用",Types.VARCHAR,null);
		dynRs.setColInfo(15, colInfo);
		colInfo=new ColInfo("CMFEE","本月费用",Types.VARCHAR,null);
		dynRs.setColInfo(16, colInfo);
		colInfo=new ColInfo("LATEAMT","滞纳金",Types.VARCHAR,null);
		dynRs.setColInfo(17, colInfo);
		colInfo=new ColInfo("SUMAMT","应缴合计",Types.VARCHAR,null);
		dynRs.setColInfo(18, colInfo);
		colInfo=new ColInfo("SUMROW","小计",Types.VARCHAR,null);
		dynRs.setColInfo(19, colInfo);
		colInfo=new ColInfo("GROUPID","分页标记",Types.VARCHAR,null);
		dynRs.setColInfo(20, colInfo);
		colInfo=new ColInfo("NOTE","备注",Types.VARCHAR,null);
		dynRs.setColInfo(21, colInfo);
		return dynRs;
	}

	protected void execQuery() {
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);

		this.tblMain.removeRows(false);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();

		filterMap = CommerceHelper
				.convertFilterItemCollToMap(this.mainQuery.getFilter());
		if (filterMap.get("dateFrom") != null) {
			this.pkDateFrom.setValue(filterMap.get("dateFrom"));
		}
		if (filterMap.get("dateTo") != null) {
			this.pkDateTo.setValue(filterMap.get("dateTo"));
		}

		if (node != null) {
			if (node.getUserObject() instanceof BuildingInfo) {
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				String buildingId = building.getId().toString();
				filterMap.put("room.building.id", buildingId);
			} else if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo selectPro = (SellProjectInfo) node
						.getUserObject();
				filterMap.put("room.building.sellProject.id", selectPro.getId()
						.toString());
			} else if (node.getUserObject() instanceof SubareaInfo) {
				SubareaInfo subInfo = (SubareaInfo) node.getUserObject();
				filterMap.put("sellProject.id", subInfo.getSellProject()
						.getId().toString());
				filterMap.put("room.building.subarea.id", subInfo.getId()
						.toString());
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				OrgStructureInfo org = (OrgStructureInfo) node.getUserObject();
				try {
					String sellProIdStr = FDCTreeHelper
							.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(
									node, "SellProject").keySet());
					if (sellProIdStr.equals(""))
						sellProIdStr = "'null'";
					filterMap.put("sellProject.id", sellProIdStr);
				} catch (Exception e) {
					handleException(e);
				}
			}
		} else {
			return;
		}
        String sql = getSql(filterMap,false,true);
        StringBuffer sqlSb =new StringBuffer();
        sqlSb.append("select * from ( \r\n");
        sqlSb.append(sql);
        sqlSb.append(") AA  \r\n");
        sqlSb.append("where 1=1 ");
        if(filterMap.get("dateTo") != null)
        	sqlSb.append(" and FAppPayDate<=to_Date('" + filterMap.get("dateTo") + "') \r\n");
        sqlSb.append("order by RoomName,RoomID,CustomerID,MoneyDefineName,MoneyDefineID,ProjName,SubareaName,BuildingName \r\n");
        sql=sqlSb.toString();

        System.out.println(sql);
        
        ISQLExecutor se = SQLExecutorFactory.getRemoteInstance(sql);
        IRowSet rs = null;
        ICell cell = null;
        Date payDate = null;
        java.util.Date curDate = UIRuleUtil.now();
        IRowSetMetaData rsm = null;
        IRow row = null;
        IRowSet rsNext = null;
        boolean isLast = false;
        int firstRow = 0;
        int curRow = 0;
        int groupId = 1;
        double amtSum = 0.0D;
        KDTMergeManager mm = tblMain.getMergeManager();
        try
        {
            rs = se.executeSQL();
            rsNext = rs.createCopy();
            rsm = rs.getRowSetMetaData();
            IRow rowIn = null;
            rsNext.next();
            int i2=0;
            while(rs.next()) 
            {
//            	i2+=1;
//            	System.out.println(i2);
                row = tblMain.addRow();
                curRow = row.getRowIndex();
                if(!rsNext.isLast())
                    rsNext.next();
                for(int i = 1; i <= rsm.getColumnCount() ; i++)
                {
                    cell = row.getCell(rsm.getColumnName(i ).toUpperCase());
                    if(cell != null){
                        cell.setValue(rs.getString(rsm.getColumnName(i )));
                    }
                }

                payDate = rs.getDate("FAPPPAYDATE");
                if(payDate != null){
                    if(payDate.getTime() >= Date.valueOf(filterMap.get("dateFrom").toString()).getTime()
                    			&& payDate.getTime() <= Date.valueOf(filterMap.get("dateTo").toString()).getTime())
                    {
                        row.getCell("LMFEE").setValue(rs.getString("FEEAMT"));
                        rs.updateString("LMFEE", rs.getString("FEEAMT"));
                    } else
                    {
                        row.getCell("CMFEE").setValue(rs.getString("FEEAMT"));
                        rs.updateString("CMFEE", rs.getString("FEEAMT"));
                    }
                }
//                row.getCell("LMFEE").setValue(String.valueOf(rs.getDouble("LMFEE")));
//                row.getCell("CMFEE").setValue(String.valueOf(rs.getDouble("CMFEE")));
                
//                amtSum += rs.getDouble("LMFEE")+rs.getDouble("CMFEE");
                amtSum += rs.getDouble("FEEAMT");
                rs.updateString("groupId", String.valueOf(groupId));
                if(rs.isLast() || !rs.getString("CUSTOMERNAME").equals(rsNext.getString("CUSTOMERNAME")) || !rs.getString("ROOMID").equals(rsNext.getString("ROOMID")))
                {
                    for(int i = firstRow; i <= curRow; i++)
                    {
                        rowIn = tblMain.getRow(i);
                        rowIn.getCell("SUMFEE").setValue(String.valueOf(amtSum));
                    }

                    mm.mergeBlock(firstRow, tblMain.getColumnIndex("CUSTOMERNAME"), curRow, tblMain.getColumnIndex("CUSTOMERNAME"), 4);
                    mm.mergeBlock(firstRow, tblMain.getColumnIndex("ROOMNAME"), curRow, tblMain.getColumnIndex("ROOMNAME"), 4);
                    mm.mergeBlock(firstRow, tblMain.getColumnIndex("FUSEAREA"), curRow, tblMain.getColumnIndex("FUSEAREA"), 4);
                    mm.mergeBlock(firstRow, tblMain.getColumnCount() - 1, curRow, tblMain.getColumnCount() - 1, 4);
                    firstRow = curRow + 1;
                    amtSum = 0;
                    groupId++;
                }
            }
            rsCopy = rs.createCopy();
            if(rsCopy != null)
            {
                rsCopy.first();
                for(int i = 0; i < tblMain.getRowCount(); i++)
                {
//                    System.out.println(i);
//                    System.out.println(rsCopy.getString("SUMAMT"));
                    rsCopy.updateString("SUMAMT", tblMain.getCell(i, "SUMFEE").getValue().toString());
//                    System.out.println(rsCopy.getString("SUMAMT"));
                    rsCopy.next();
                }

                rsCopy.first();
			}
		} catch (BOSException e) {
			super.handUIExceptionAndAbort(e);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	protected String getKeyFieldName() {
		return "CUSTOMERNAME";
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		// super.actionRefresh_actionPerformed(e);
		this.execQuery();
	}

	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
//		if(rsCopy==null){
//			this.execQuery();
//		}
//		else{
//		{
			IRowSet rs =this.exePrint();
//			}
			BOSQueryDelegate data = new DataProvider(rs);
			// 定义printer控件
			KDNoteHelper appHlp = new KDNoteHelper();
	
			// 指定打印模板example的相对路径、数据provider类、窗体句柄
			// 调用打印功能
			//appHlp.print();
			appHlp.print("bim/fdc/tenancy", data, SwingUtilities.getWindowAncestor(this));
//		}
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}


    private String getSql(Map filterMap,boolean isPrint,boolean isDetail)
    {
        StringBuffer sqlRoomSb = new StringBuffer();
        StringBuffer sqlAllSb = new StringBuffer();
        StringBuffer sqlSb = new StringBuffer();
        
//        sqlAllSb.append("select * from ( \r\n");
        //feeType\LmFee\CmFee
        if(isPrint){	
        	if(isDetail){
        		sqlRoomSb.append("select room.ProjName,room.SubareaName,room.BuildingName,room.RoomID,room.RoomName,room.FUseArea,room.BuildingUnitName \r\n");
    	        sqlRoomSb.append(",fee.FRoomID,fee.CustomerName,fee.CustomerID,fee.MoneyDefineName,fee.MoneyDefineID,fee.startDate,fee.endDate \r\n");
    	        sqlRoomSb.append("      ,fee.lastRead,fee.curRead,fee.Qty,fee.FUnitPrice,fee.feeAmt,0 lateAmt,fee.FAppPayDate,0 GROUPID,0 sumAmt,0 LmFee,fee.feeAmt CmFee,1 as feeType,0 as SUMROW \r\n");
        	}
        	else{
        		//huizong
	        	sqlRoomSb.append("select room.ProjName,room.SubareaName,room.BuildingName,room.RoomID,room.RoomName,room.FUseArea,room.BuildingUnitName \r\n");
	        	sqlRoomSb.append("		,fee.FRoomID,fee.CustomerName,fee.CustomerID,fee.MoneyDefineName,fee.MoneyDefineID,NULL startDate,NULL endDate \r\n");
	        	sqlRoomSb.append("      ,0 lastRead,0 curRead,fee.Qty,0 FUnitPrice,fee.feeAmt,0 lateAmt,Null FAppPayDate,0 GROUPID,0 sumAmt,fee.feeAmt LmFee,0 CmFee,2 as feeType,0 as SUMROW \r\n");
        	}
        }
        else{//增加了小计
	        sqlRoomSb.append("select room.ProjName,room.SubareaName,room.BuildingName,room.RoomID,room.RoomName,room.FUseArea,room.BuildingUnitName \r\n");
	        sqlRoomSb.append(",fee.FRoomID,fee.CustomerName,fee.CustomerID,fee.MoneyDefineName,fee.MoneyDefineID,fee.startDate,fee.endDate \r\n");
	        sqlRoomSb.append("      ,fee.lastRead,fee.curRead,fee.Qty,fee.FUnitPrice,fee.feeAmt,0 lateAmt,fee.FAppPayDate,0 GROUPID,0 sumAmt,0 LmFee,0 CmFee,fee.feeAmt SUMROW \r\n");
        }
        sqlRoomSb.append(" from (select T_SHE_SellProject.FName_l2 as ProjName,T_SHE_Subarea.FName_L2 SubareaName \r\n");
        sqlRoomSb.append("      ,T_SHE_Building.FName_L2 as BuildingName,T_SHE_BuildingUnit.FName_l2 as BuildingUnitName \r\n");
        /**
         * 见下面sql语句中的T_SHE_Room.FName_L2字段更换成T_SHE_Room.Fnumber的短字段
         * 也就是房间名称的短名称，即房间编号
         * by renliang at 2010-6-18
         * start
         */
        sqlRoomSb.append("      ,T_SHE_Room.FID RoomID,T_SHE_Room.Fnumber RoomName,T_SHE_Room.FBuildingArea FUseArea \r\n");
        /**
         * end
         */
        sqlRoomSb.append("      from T_SHE_Room T_SHE_Room \r\n");
        sqlRoomSb.append("      left join T_SHE_Building T_SHE_Building \r\n");
        sqlRoomSb.append("             on T_SHE_Building.FID=T_SHE_Room.FBuildingID \r\n");
        sqlRoomSb.append("      left join T_SHE_SellProject T_SHE_SellProject \r\n");
        sqlRoomSb.append("            on T_SHE_SellProject.FID =T_SHE_Building.FSellProjectID \r\n");
        sqlRoomSb.append("      left join T_SHE_Subarea T_SHE_Subarea \r\n");
        sqlRoomSb.append("            on T_SHE_SellProject.FID=T_SHE_Subarea.FSellProjectID \r\n");
        sqlRoomSb.append("            left join T_SHE_BuildingUnit T_SHE_BuildingUnit \r\n");
        sqlRoomSb.append("            on T_SHE_Room.FBuildUnitID=T_SHE_BuildingUnit.FID \r\n");
        sqlRoomSb.append("  where 1=1 ");
		if (!this.txtRoom.getText().trim().equals("")){
			sqlRoomSb.append("  	and T_SHE_Room.FName_L2 like'%"+this.txtRoom.getText().trim() + "%'");
		}
        if(filterMap.get("room.building.id") != null)
            sqlRoomSb.append("            and T_SHE_Building.fid='" + filterMap.get("room.building.id") + "' \r\n");
        if(filterMap.get("room.building.sellProject.id") != null)
            sqlRoomSb.append("            and  T_SHE_SellProject.fid='" + filterMap.get("room.building.sellProject.id") + "' \r\n");
        if(filterMap.get("sellProject.id") != null && filterMap.get("room.building.subarea.id") != null)
        {
            sqlRoomSb.append("            and  T_SHE_Subarea.fid='" + filterMap.get("room.building.subarea.id") + "' \r\n");
            sqlRoomSb.append("            and  T_SHE_SellProject.fid='" + filterMap.get("sellProject.id") + "' \r\n");
        } else
        if(filterMap.get("sellProject.id") != null)
            sqlRoomSb.append("            and  T_SHE_SellProject.fid in (" + filterMap.get("sellProject.id") + ") \r\n");
        sqlRoomSb.append("      ) room  \r\n");
        sqlAllSb.append(sqlRoomSb);
        sqlSb.append("inner  join  ( \r\n");

        
        //计量应收
        if(isPrint&&!isDetail){            
        	sqlSb.append("			  select T_PPM_PPMMeasureAR.FRoomID,T_SHE_FDCCustomer.FName_L2 as CustomerName,T_SHE_FDCCustomer.FID  \r\n");
        	sqlSb.append("              CustomerID,T_SHE_MoneyDefine.FName_L2 as MoneyDefineName,T_SHE_MoneyDefine.FID  MoneyDefineID \r\n");
	//      sqlSb.append("            ,T_PPM_PPMMeasureAR.FStartFeeDate as startDate,T_PPM_PPMMeasureAR.FEndFeeDate as endDate \r\n");
	//      sqlSb.append("            ,TO_DECIMAL(isnull(T_PPM_PPMMeasureAR.FLastReading,0)) as lastRead,TO_DECIMAL(isnull(T_PPM_PPMMeasureAR.FCurrentReading,0)) as curRead \r\n");
		     sqlSb.append("              ,sum(TO_DECIMAL(isnull(T_PPM_PPMMeasureAR.FWastage,0))) as Qty");
		     sqlSb.append("				,sum(TO_DECIMAL(isnull(T_PPM_PPMMeasureAR.FArAmout,0))-TO_DECIMAL(isnull(T_PPM_PPMMeasureAR.FPayedAmount,0))) feeAmt \r\n");
// 		     sqlSb.append("            ,T_PPM_PPMMeasureAR.FARDate as FAppPayDate \r\n");
        }
        else{
            sqlSb.append("			  select T_PPM_PPMMeasureAR.FRoomID,T_SHE_FDCCustomer.FName_L2 as CustomerName,T_SHE_FDCCustomer.FID  \r\n");
            sqlSb.append("            CustomerID,T_SHE_MoneyDefine.FName_L2 as MoneyDefineName,T_SHE_MoneyDefine.FID  MoneyDefineID \r\n");
            sqlSb.append("            ,T_PPM_PPMMeasureAR.FStartFeeDate as startDate,T_PPM_PPMMeasureAR.FEndFeeDate as endDate \r\n");
            sqlSb.append("            ,TO_DECIMAL(isnull(T_PPM_PPMMeasureAR.FLastReading,0)) as lastRead,TO_DECIMAL(isnull(T_PPM_PPMMeasureAR.FCurrentReading,0)) as curRead \r\n");
            sqlSb.append("            ,TO_DECIMAL(isnull(T_PPM_PPMMeasureAR.FWastage,0)) as Qty,TO_DECIMAL(isnull(T_PPM_PPMMeasureAR.FUnitPrice,0)) as FUnitPrice \r\n");
            sqlSb.append("			  ,(TO_DECIMAL(isnull(T_PPM_PPMMeasureAR.FArAmout,0))-TO_DECIMAL(isnull(T_PPM_PPMMeasureAR.FPayedAmount,0))) feeAmt \r\n");
            sqlSb.append("            ,T_PPM_PPMMeasureAR.FARDate as FAppPayDate \r\n");
        }
        sqlSb.append("            from T_PPM_PPMMeasureAR  T_PPM_PPMMeasureAR \r\n");
        sqlSb.append("            inner join  T_SHE_MoneyDefine  T_SHE_MoneyDefine \r\n");
        sqlSb.append("                  on T_PPM_PPMMeasureAR.FChargeItemID=T_SHE_MoneyDefine.FID \r\n");
        sqlSb.append("            left join T_SHE_FDCCustomer T_SHE_FDCCustomer \r\n");
        sqlSb.append("                  on T_PPM_PPMMeasureAR.FCustomerID=T_SHE_FDCCustomer.FID \r\n");
        //-计量应收 汇总项
        if(isPrint&&!isDetail){
            sqlSb.append("			where (TO_DECIMAL(isnull(T_PPM_PPMMeasureAR.FArAmout,0))-TO_DECIMAL(isnull(T_PPM_PPMMeasureAR.FPayedAmount,0)))<>0 \r\n");

            if(filterMap.get("dateFrom") != null)
            	sqlSb.append(" and T_PPM_PPMMeasureAR.FARDate <to_Date('" + filterMap.get("dateFrom") + "') \r\n");
            
        	sqlSb.append("			group by T_PPM_PPMMeasureAR.FRoomID,T_SHE_FDCCustomer.FID,T_SHE_FDCCustomer.FName_L2 \r\n");
        	sqlSb.append("         			,T_SHE_MoneyDefine.FID,T_SHE_MoneyDefine.FName_L2 \r\n");
        }
        sqlSb.append("      )fee on  room.RoomID=Fee.FRoomID \r\n");
        sqlSb.append("where feeAmt<>0 \r\n");
        
        
        sqlAllSb.append(sqlSb);
        
        sqlAllSb.append("union all \r\n");
        
        //常规费用
        sqlAllSb.append(sqlRoomSb);
        sqlSb = new StringBuffer();
        sqlSb.append("inner join ( \r\n");

        if(isPrint&&!isDetail){
        	sqlSb.append("			  select T_PPM_PPMGeneralAR.FRoomID,T_SHE_FDCCustomer.FName_L2 as CustomerName,T_SHE_FDCCustomer.FID  \r\n");
        	sqlSb.append("              CustomerID,T_SHE_MoneyDefine.FName_L2 as MoneyDefineName,T_SHE_MoneyDefine.FID  MoneyDefineID \r\n");
		    sqlSb.append("              ,sum(TO_DECIMAL(isnull(T_PPM_PPMGeneralAR.FFeeQuantity,0))) as Qty");
		    sqlSb.append("				,sum(TO_DECIMAL(isnull(T_PPM_PPMGeneralAR.FArAmout,0))-TO_DECIMAL(isnull(T_PPM_PPMGeneralAR.FPayedAmount,0))) feeAmt \r\n");
        }
        else{
	        sqlSb.append("select T_PPM_PPMGeneralAR.FRoomID,T_SHE_FDCCustomer.FName_L2 as CustomerName,T_SHE_FDCCustomer.FID \r\n");
	        sqlSb.append("CustomerID,T_SHE_MoneyDefine.FName_L2 as MoneyDefineName,T_SHE_MoneyDefine.FID  MoneyDefineID   \r\n");
	        sqlSb.append(",T_PPM_PPMGeneralAR.FStartFeeDate as startDate,T_PPM_PPMGeneralAR.FEndFeeDate as endDate \r\n");
	        sqlSb.append(",0 as lastRead,0 as curRead \r\n");
	        sqlSb.append(",TO_DECIMAL(isnull(T_PPM_PPMGeneralAR.FFeeQuantity,0)) as Qty,TO_DECIMAL(isnull(T_PPM_PPMGeneralAR.FUnitPrice,0)) as FUnitPrice \r\n");
	        sqlSb.append(",(TO_DECIMAL(isnull(T_PPM_PPMGeneralAR.FArAmout,0))-TO_DECIMAL(isnull(T_PPM_PPMGeneralAR.FPayedAmount,0))) feeAmt \r\n");
	        sqlSb.append(",T_PPM_PPMGeneralAR.FARDate as FAppPayDate \r\n");
        }
        sqlSb.append("from T_PPM_PPMGeneralAR  T_PPM_PPMGeneralAR \r\n");
        sqlSb.append("inner join  T_SHE_MoneyDefine  T_SHE_MoneyDefine \r\n");
        sqlSb.append("                  on T_PPM_PPMGeneralAR.FChargeItemID=T_SHE_MoneyDefine.FID \r\n");
        sqlSb.append("left join T_SHE_FDCCustomer T_SHE_FDCCustomer \r\n");
        sqlSb.append("                  on T_PPM_PPMGeneralAR.FCustomerID=T_SHE_FDCCustomer.FID \r\n");
        //-常规费用 汇总项
        if(isPrint&&!isDetail){
            sqlSb.append("			where (TO_DECIMAL(isnull(T_PPM_PPMGeneralAR.FArAmout,0))-TO_DECIMAL(isnull(T_PPM_PPMGeneralAR.FPayedAmount,0)))<>0 \r\n");

            if(filterMap.get("dateFrom") != null)
            	sqlSb.append(" and T_PPM_PPMGeneralAR.FARDate <to_Date('" + filterMap.get("dateFrom") + "') \r\n");
            
        	sqlSb.append("			group by T_PPM_PPMGeneralAR.FRoomID,T_SHE_FDCCustomer.FID,T_SHE_FDCCustomer.FName_L2 \r\n");
        	sqlSb.append("         			,T_SHE_MoneyDefine.FID,T_SHE_MoneyDefine.FName_L2 \r\n");
        }
        sqlSb.append("      )fee on  room.RoomID=Fee.FRoomID   \r\n");
        sqlSb.append("where feeAmt<>0 \r\n");
        
        sqlAllSb.append(sqlSb);
        
        sqlAllSb.append("union all \r\n");
        
        //临时应收
        sqlAllSb.append(sqlRoomSb);
        sqlSb = new StringBuffer();
        sqlSb.append("inner join ( \r\n");
        if(isPrint&&!isDetail){
        	sqlSb.append("			  select T_PPM_PPMTemporary.FRoomID,T_SHE_FDCCustomer.FName_L2 as CustomerName,T_SHE_FDCCustomer.FID  \r\n");
        	sqlSb.append("              CustomerID,T_SHE_MoneyDefine.FName_L2 as MoneyDefineName,T_SHE_MoneyDefine.FID  MoneyDefineID \r\n");
		    sqlSb.append("              ,sum(TO_DECIMAL(isnull(T_PPM_PPMTemporary.FFeeQuantity,0))) as Qty");
		    sqlSb.append("				,sum(TO_DECIMAL(isnull(T_PPM_PPMTemporary.FArAmout,0))-TO_DECIMAL(isnull(T_PPM_PPMTemporary.FPayedAmount,0))) feeAmt \r\n");
        }
        else{
	        sqlSb.append("			select  T_PPM_PPMTemporary.FRoomID,T_SHE_FDCCustomer.FName_L2 as CustomerName,T_SHE_FDCCustomer.FID  \r\n");
	        sqlSb.append("				CustomerID,T_SHE_MoneyDefine.FName_L2 as MoneyDefineName,T_SHE_MoneyDefine.FID  MoneyDefineID   \r\n");
	        sqlSb.append("               ,NULL as startDate,NULL as endDate \r\n");
	        sqlSb.append("            ,0 as lastRead,0 as curRead \r\n");
	        sqlSb.append("            ,TO_DECIMAL(isnull(T_PPM_PPMTemporary.FFeeQuantity,0)) as Qty,TO_DECIMAL(isnull(T_PPM_PPMTemporary.FUnitPrice,0)) as FUnitPrice \r\n");
	        sqlSb.append("            ,(TO_DECIMAL(isnull(T_PPM_PPMTemporary.FArAmout,0))-TO_DECIMAL(isnull(T_PPM_PPMTemporary.FPayedAmount,0))) feeAmt \r\n");
	        sqlSb.append("            ,T_PPM_PPMTemporary.FARDate as FAppPayDate \r\n");
        }
        sqlSb.append("from T_PPM_PPMTemporary  T_PPM_PPMTemporary  \r\n");
        sqlSb.append("            inner join  T_SHE_MoneyDefine  T_SHE_MoneyDefine \r\n");
        sqlSb.append("                  on T_PPM_PPMTemporary.FChargeItemID=T_SHE_MoneyDefine.FID \r\n");
        sqlSb.append("            left join T_SHE_FDCCustomer T_SHE_FDCCustomer \r\n");
        sqlSb.append("                  on T_PPM_PPMTemporary.FCustomerID=T_SHE_FDCCustomer.FID \r\n");
        //-临时应收 汇总项
        if(isPrint&&!isDetail){ 
            sqlSb.append("			where (TO_DECIMAL(isnull(T_PPM_PPMTemporary.FArAmout,0))-TO_DECIMAL(isnull(T_PPM_PPMTemporary.FPayedAmount,0)))<>0 \r\n");

            if(filterMap.get("dateFrom") != null)
            	sqlSb.append(" and T_PPM_PPMTemporary.FARDate <to_Date('" + filterMap.get("dateFrom") + "') \r\n");
            
        	sqlSb.append("			group by T_PPM_PPMTemporary.FRoomID,T_SHE_FDCCustomer.FID,T_SHE_FDCCustomer.FName_L2 \r\n");
        	sqlSb.append("         			,T_SHE_MoneyDefine.FID,T_SHE_MoneyDefine.FName_L2 \r\n");
        }
        sqlSb.append("      )fee on  room.RoomID=Fee.FRoomID   \r\n");
        sqlSb.append("where feeAmt<>0 \r\n");
        
        sqlAllSb.append(sqlSb);
        
        sqlAllSb.append("union all \r\n");
        
        //租赁
        sqlAllSb.append(sqlRoomSb);
        sqlSb = new StringBuffer();
        sqlSb.append("inner join ( \r\n");
        if(isPrint&&!isDetail){
        	sqlSb.append("			  select T_TEN_TenancyRoomEntry.FRoomID,cus.CustomerName,cus.CustomerID  \r\n");
        	sqlSb.append("              ,T_SHE_MoneyDefine.FName_L2 as MoneyDefineName,T_SHE_MoneyDefine.FID  MoneyDefineID \r\n");
		    sqlSb.append("              ,sum(0) as Qty");
		    sqlSb.append("				,sum(TO_DECIMAL(isnull(T_TEN_TenancyRoomPayListEntry.FAppAmount,0))-TO_DECIMAL(isnull(T_TEN_TenancyRoomPayListEntry.FActAmount,0))) feeAmt \r\n");
        }
        else{
	        sqlSb.append("			  select  T_TEN_TenancyRoomEntry.FRoomID,cus.CustomerName,cus.CustomerID \r\n");
	        sqlSb.append("            ,T_SHE_MoneyDefine.FName_L2 as MoneyDefineName,T_SHE_MoneyDefine.FID  MoneyDefineID  \r\n");
	        sqlSb.append("               ,T_TEN_TenancyRoomPayListEntry.FStartDate as startDate,T_TEN_TenancyRoomPayListEntry.FEndDate as \r\n");
	        sqlSb.append("				endDate \r\n");
	        sqlSb.append("            ,0 as lastRead,0 as curRead \r\n");
	        sqlSb.append("            ,0 as Qty,0 FUnitPrice \r\n");
	        sqlSb.append("            ,(TO_DECIMAL(isnull(T_TEN_TenancyRoomPayListEntry.FAppAmount,0))-TO_DECIMAL(isnull(T_TEN_TenancyRoomPayListEntry.FActAmount,0)))feeAmt \r\n");
	        sqlSb.append("            ,T_TEN_TenancyRoomPayListEntry.FAppDate as FAppPayDate \r\n");
        }
        sqlSb.append("            from T_TEN_TenancyBill  T_TEN_TenancyBill \r\n");
        sqlSb.append("            inner join T_TEN_TenancyRoomEntry T_TEN_TenancyRoomEntry \r\n");
        sqlSb.append("                  on  T_TEN_TenancyBill.FID =T_TEN_TenancyRoomEntry.FTenancyID \r\n");
        sqlSb.append("            inner join T_TEN_TenancyRoomPayListEntry T_TEN_TenancyRoomPayListEntry \r\n");
        sqlSb.append("                  on T_TEN_TenancyRoomEntry.FID=T_TEN_TenancyRoomPayListEntry.FTenRoomID \r\n");
        sqlSb.append("            inner join (select T_SHE_FDCCustomer.FName_L2 as CustomerName,T_SHE_FDCCustomer.FID CustomerID \r\n");
        sqlSb.append("                        ,T_TEN_TenancyCustomerEntry.FTenancyBillID \r\n");
        sqlSb.append("                        from  T_TEN_TenancyCustomerEntry T_TEN_TenancyCustomerEntry \r\n");
        sqlSb.append("                        left join T_SHE_FDCCustomer T_SHE_FDCCustomer  \r\n");
        sqlSb.append("                              on T_TEN_TenancyCustomerEntry.FFdcCustomerID=T_SHE_FDCCustomer.FID) cus \r\n");
        sqlSb.append("                  on T_TEN_TenancyBill.FID=cus.FTenancyBillID \r\n");
        sqlSb.append("            inner join  T_SHE_MoneyDefine  T_SHE_MoneyDefine \r\n");
        sqlSb.append("                  on T_TEN_TenancyRoomPayListEntry.FMoneyDefineID=T_SHE_MoneyDefine.FID \r\n");

        //-租赁汇总项
        if(isPrint&&!isDetail){ 
            sqlSb.append("			where (TO_DECIMAL(isnull(T_TEN_TenancyRoomPayListEntry.FAppAmount,0))-TO_DECIMAL(isnull(T_TEN_TenancyRoomPayListEntry.FActAmount,0)))<>0 \r\n");

            if(filterMap.get("dateFrom") != null)
            	sqlSb.append(" and T_TEN_TenancyRoomPayListEntry.FAppPayDate<to_Date('" + filterMap.get("dateFrom") + "') \r\n");
            
        	sqlSb.append("			group by T_TEN_TenancyRoomEntry.FRoomID,cus.CustomerID,cus.CustomerName \r\n");
        	sqlSb.append("         			,T_SHE_MoneyDefine.FID,T_SHE_MoneyDefine.FName_L2 \r\n");
        }
        sqlSb.append("      )fee on  room.RoomID=Fee.FRoomID   \r\n");
        sqlSb.append("where feeAmt<>0 \r\n");
        sqlAllSb.append(sqlSb);
        //System.out.println(sqlAllSb.toString());
        return sqlAllSb.toString();
    }

}