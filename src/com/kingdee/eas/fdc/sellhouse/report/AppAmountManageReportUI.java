/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.DefaultKDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class AppAmountManageReportUI extends AbstractAppAmountManageReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(AppAmountManageReportUI.class);
    
    public AppAmountManageReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
    }
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new BackAmountCycleReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return AppAmountManageReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
	    
		for(int i=1;i<12;i++){
			CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"YQaccount"+i,"NQaccount"+i,"account"+i});
			
//			tblMain.getColumn("YQamount"+i).getStyleAttributes().setFontColor(Color.BLUE);
//			tblMain.getColumn("NQamount"+i).getStyleAttributes().setFontColor(Color.BLUE);
//			tblMain.getColumn("amount"+i).getStyleAttributes().setFontColor(Color.BLUE);
		}
		
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"account12","account13"});
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
    	if(treeNode!=null){
    		Window win = SwingUtilities.getWindowAncestor(this);
            LongTimeDialog dialog = null;
            if(win instanceof Frame){
            	dialog = new LongTimeDialog((Frame)win);
            }else if(win instanceof Dialog){
            	dialog = new LongTimeDialog((Dialog)win);
            }
            if(dialog==null){
            	dialog = new LongTimeDialog(new Frame());
            }
            dialog.setLongTimeTask(new ILongTimeTask() {
            	 public Object exec()throws Exception{
                 	 RptParams resultRpt= getRemoteInstance().query(params);
                  	 return resultRpt;
                 }
                 public void afterExec(Object result)throws Exception{
                 	 RptParams rpt = getRemoteInstance().createTempTable(params);
                     RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                     KDTableUtil.setHeader(header, tblMain);
                     
                     RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
                     IRow row=null;
                     BigDecimal YQamount1=FDCHelper.ZERO;
                     BigDecimal YQamount2=FDCHelper.ZERO;
                     BigDecimal YQamount3=FDCHelper.ZERO;
                     BigDecimal YQamount4=FDCHelper.ZERO;
                     BigDecimal YQamount5=FDCHelper.ZERO;
                     BigDecimal YQamount6=FDCHelper.ZERO;
                     BigDecimal YQamount7=FDCHelper.ZERO;
                     BigDecimal YQamount8=FDCHelper.ZERO;
                     BigDecimal YQamount9=FDCHelper.ZERO;
                     BigDecimal YQamount10=FDCHelper.ZERO;
                     BigDecimal YQamount11=FDCHelper.ZERO;
                     BigDecimal YQaccount1=FDCHelper.ZERO;
                     BigDecimal YQaccount2=FDCHelper.ZERO;
                     BigDecimal YQaccount3=FDCHelper.ZERO;
                     BigDecimal YQaccount4=FDCHelper.ZERO;
                     BigDecimal YQaccount5=FDCHelper.ZERO;
                     BigDecimal YQaccount6=FDCHelper.ZERO;
                     BigDecimal YQaccount7=FDCHelper.ZERO;
                     BigDecimal YQaccount8=FDCHelper.ZERO;
                     BigDecimal YQaccount9=FDCHelper.ZERO;
                     BigDecimal YQaccount10=FDCHelper.ZERO;
                     BigDecimal YQaccount11=FDCHelper.ZERO;
                     
                     BigDecimal NQamount1=FDCHelper.ZERO;
                     BigDecimal NQamount2=FDCHelper.ZERO;
                     BigDecimal NQamount3=FDCHelper.ZERO;
                     BigDecimal NQamount4=FDCHelper.ZERO;
                     BigDecimal NQamount5=FDCHelper.ZERO;
                     BigDecimal NQamount6=FDCHelper.ZERO;
                     BigDecimal NQamount7=FDCHelper.ZERO;
                     BigDecimal NQamount8=FDCHelper.ZERO;
                     BigDecimal NQamount9=FDCHelper.ZERO;
                     BigDecimal NQamount10=FDCHelper.ZERO;
                     BigDecimal NQamount11=FDCHelper.ZERO;
                     BigDecimal NQaccount1=FDCHelper.ZERO;
                     BigDecimal NQaccount2=FDCHelper.ZERO;
                     BigDecimal NQaccount3=FDCHelper.ZERO;
                     BigDecimal NQaccount4=FDCHelper.ZERO;
                     BigDecimal NQaccount5=FDCHelper.ZERO;
                     BigDecimal NQaccount6=FDCHelper.ZERO;
                     BigDecimal NQaccount7=FDCHelper.ZERO;
                     BigDecimal NQaccount8=FDCHelper.ZERO;
                     BigDecimal NQaccount9=FDCHelper.ZERO;
                     BigDecimal NQaccount10=FDCHelper.ZERO;
                     BigDecimal NQaccount11=FDCHelper.ZERO;
                     
                     BigDecimal amount1=FDCHelper.ZERO;
                     BigDecimal amount2=FDCHelper.ZERO;
                     BigDecimal amount3=FDCHelper.ZERO;
                     BigDecimal amount4=FDCHelper.ZERO;
                     BigDecimal amount5=FDCHelper.ZERO;
                     BigDecimal amount6=FDCHelper.ZERO;
                     BigDecimal amount7=FDCHelper.ZERO;
                     BigDecimal amount8=FDCHelper.ZERO;
                     BigDecimal amount9=FDCHelper.ZERO;
                     BigDecimal amount10=FDCHelper.ZERO;
                     BigDecimal amount11=FDCHelper.ZERO;
                     BigDecimal amount12=FDCHelper.ZERO;
                     BigDecimal amount13=FDCHelper.ZERO;
                     BigDecimal account1=FDCHelper.ZERO;
                     BigDecimal account2=FDCHelper.ZERO;
                     BigDecimal account3=FDCHelper.ZERO;
                     BigDecimal account4=FDCHelper.ZERO;
                     BigDecimal account5=FDCHelper.ZERO;
                     BigDecimal account6=FDCHelper.ZERO;
                     BigDecimal account7=FDCHelper.ZERO;
                     BigDecimal account8=FDCHelper.ZERO;
                     BigDecimal account9=FDCHelper.ZERO;
                     BigDecimal account10=FDCHelper.ZERO;
                     BigDecimal account11=FDCHelper.ZERO;
                     BigDecimal account12=FDCHelper.ZERO;
                     BigDecimal account13=FDCHelper.ZERO;
                     while(rs.next()){
                    	 row=tblMain.addRow();
                    	 ((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(row, rs.toRowArray());
                    	 row.getCell("orgUnit").setValue(row.getCell("orgUnit").getValue().toString().split("_")[3]);
                    	 
                    	 if(row.getCell("bpName").getValue().toString().equals("Ð¡¼Æ")){
                    		 YQamount1=FDCHelper.add(YQamount1, row.getCell("YQamount1").getValue());
                    		 YQamount2=FDCHelper.add(YQamount2, row.getCell("YQamount2").getValue());
                    		 YQamount3=FDCHelper.add(YQamount3, row.getCell("YQamount3").getValue());
                    		 YQamount4=FDCHelper.add(YQamount4, row.getCell("YQamount4").getValue());
                    		 YQamount5=FDCHelper.add(YQamount5, row.getCell("YQamount5").getValue());
                    		 YQamount6=FDCHelper.add(YQamount6, row.getCell("YQamount6").getValue());
                    		 YQamount7=FDCHelper.add(YQamount7, row.getCell("YQamount7").getValue());
                    		 YQamount8=FDCHelper.add(YQamount8, row.getCell("YQamount8").getValue());
                    		 YQamount9=FDCHelper.add(YQamount9, row.getCell("YQamount9").getValue());
                    		 YQamount10=FDCHelper.add(YQamount10, row.getCell("YQamount10").getValue());
                    		 YQamount11=FDCHelper.add(YQamount11, row.getCell("YQamount11").getValue());
                    		 
                    		 YQaccount1=FDCHelper.add(YQaccount1, row.getCell("YQaccount1").getValue());
                    		 YQaccount2=FDCHelper.add(YQaccount2, row.getCell("YQaccount2").getValue());
                    		 YQaccount3=FDCHelper.add(YQaccount3, row.getCell("YQaccount3").getValue());
                    		 YQaccount4=FDCHelper.add(YQaccount4, row.getCell("YQaccount4").getValue());
                    		 YQaccount5=FDCHelper.add(YQaccount5, row.getCell("YQaccount5").getValue());
                    		 YQaccount6=FDCHelper.add(YQaccount6, row.getCell("YQaccount6").getValue());
                    		 YQaccount7=FDCHelper.add(YQaccount7, row.getCell("YQaccount7").getValue());
                    		 YQaccount8=FDCHelper.add(YQaccount8, row.getCell("YQaccount8").getValue());
                    		 YQaccount9=FDCHelper.add(YQaccount9, row.getCell("YQaccount9").getValue());
                    		 YQaccount10=FDCHelper.add(YQaccount10, row.getCell("YQaccount10").getValue());
                    		 YQaccount11=FDCHelper.add(YQaccount11, row.getCell("YQaccount11").getValue());
                    		 
                    		 NQamount1=FDCHelper.add(NQamount1, row.getCell("NQamount1").getValue());
                    		 NQamount2=FDCHelper.add(NQamount2, row.getCell("NQamount2").getValue());
                    		 NQamount3=FDCHelper.add(NQamount3, row.getCell("NQamount3").getValue());
                    		 NQamount4=FDCHelper.add(NQamount4, row.getCell("NQamount4").getValue());
                    		 NQamount5=FDCHelper.add(NQamount5, row.getCell("NQamount5").getValue());
                    		 NQamount6=FDCHelper.add(NQamount6, row.getCell("NQamount6").getValue());
                    		 NQamount7=FDCHelper.add(NQamount7, row.getCell("NQamount7").getValue());
                    		 NQamount8=FDCHelper.add(NQamount8, row.getCell("NQamount8").getValue());
                    		 NQamount9=FDCHelper.add(NQamount9, row.getCell("NQamount9").getValue());
                    		 NQamount10=FDCHelper.add(NQamount10, row.getCell("NQamount10").getValue());
                    		 NQamount11=FDCHelper.add(NQamount11, row.getCell("NQamount11").getValue());
                    		 
                    		 NQaccount1=FDCHelper.add(NQaccount1, row.getCell("NQaccount1").getValue());
                    		 NQaccount2=FDCHelper.add(NQaccount2, row.getCell("NQaccount2").getValue());
                    		 NQaccount3=FDCHelper.add(NQaccount3, row.getCell("NQaccount3").getValue());
                    		 NQaccount4=FDCHelper.add(NQaccount4, row.getCell("NQaccount4").getValue());
                    		 NQaccount5=FDCHelper.add(NQaccount5, row.getCell("NQaccount5").getValue());
                    		 NQaccount6=FDCHelper.add(NQaccount6, row.getCell("NQaccount6").getValue());
                    		 NQaccount7=FDCHelper.add(NQaccount7, row.getCell("NQaccount7").getValue());
                    		 NQaccount8=FDCHelper.add(NQaccount8, row.getCell("NQaccount8").getValue());
                    		 NQaccount9=FDCHelper.add(NQaccount9, row.getCell("NQaccount9").getValue());
                    		 NQaccount10=FDCHelper.add(NQaccount10, row.getCell("NQaccount10").getValue());
                    		 NQaccount11=FDCHelper.add(NQaccount11, row.getCell("NQaccount11").getValue());
                    		 
                    		 amount1=FDCHelper.add(amount1, row.getCell("amount1").getValue());
                    		 amount2=FDCHelper.add(amount2, row.getCell("amount2").getValue());
                    		 amount3=FDCHelper.add(amount3, row.getCell("amount3").getValue());
                    		 amount4=FDCHelper.add(amount4, row.getCell("amount4").getValue());
                    		 amount5=FDCHelper.add(amount5, row.getCell("amount5").getValue());
                    		 amount6=FDCHelper.add(amount6, row.getCell("amount6").getValue());
                    		 amount7=FDCHelper.add(amount7, row.getCell("amount7").getValue());
                    		 amount8=FDCHelper.add(amount8, row.getCell("amount8").getValue());
                    		 amount9=FDCHelper.add(amount9, row.getCell("amount9").getValue());
                    		 amount10=FDCHelper.add(amount10, row.getCell("amount10").getValue());
                    		 amount11=FDCHelper.add(amount11, row.getCell("amount11").getValue());
                    		 amount12=FDCHelper.add(amount11, row.getCell("amount12").getValue());
                    		 amount13=FDCHelper.add(amount11, row.getCell("amount13").getValue());
                    		 
                    		 account1=FDCHelper.add(account1, row.getCell("account1").getValue());
                    		 account2=FDCHelper.add(account2, row.getCell("account2").getValue());
                    		 account3=FDCHelper.add(account3, row.getCell("account3").getValue());
                    		 account4=FDCHelper.add(account4, row.getCell("account4").getValue());
                    		 account5=FDCHelper.add(account5, row.getCell("account5").getValue());
                    		 account6=FDCHelper.add(account6, row.getCell("account6").getValue());
                    		 account7=FDCHelper.add(account7, row.getCell("account7").getValue());
                    		 account8=FDCHelper.add(account8, row.getCell("account8").getValue());
                    		 account9=FDCHelper.add(account9, row.getCell("account9").getValue());
                    		 account10=FDCHelper.add(account10, row.getCell("account10").getValue());
                    		 account11=FDCHelper.add(account11, row.getCell("account11").getValue());
                    		 account12=FDCHelper.add(account11, row.getCell("account12").getValue());
                    		 account13=FDCHelper.add(account11, row.getCell("account13").getValue());
                    		 
                    		 row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
                    	 }
                	 }
                     IRow footRow = null;
                     KDTFootManager footRowManager = tblMain.getFootManager();
                     if(footRowManager == null)
                     {
                         String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
                         footRowManager = new KDTFootManager(tblMain);
                         footRowManager.addFootView();
                         tblMain.setFootManager(footRowManager);
                         footRow = footRowManager.addFootRow(0);
                         tblMain.getIndexColumn().setWidthAdjustMode((short)1);
                         tblMain.getIndexColumn().setWidth(30);
                         footRowManager.addIndexText(0, total);
                     } else
                     {
                         footRow = footRowManager.getFootRow(0);
                     }
                     footRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
                     footRow.getCell("YQamount1").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("YQamount2").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("YQamount3").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("YQamount4").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("YQamount5").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("YQamount6").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("YQamount7").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("YQamount8").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("YQamount9").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("YQamount10").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("YQamount11").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("YQaccount1").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("YQaccount1").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("YQaccount2").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("YQaccount2").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("YQaccount3").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("YQaccount3").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("YQaccount4").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("YQaccount4").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("YQaccount5").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("YQaccount5").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("YQaccount6").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("YQaccount6").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("YQaccount7").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("YQaccount7").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("YQaccount8").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("YQaccount8").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("YQaccount9").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("YQaccount9").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("YQaccount10").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("YQaccount10").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("YQaccount11").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("YQaccount11").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("YQamount1").setValue(YQamount1);
                     footRow.getCell("YQamount2").setValue(YQamount2);
                     footRow.getCell("YQamount3").setValue(YQamount3);
                     footRow.getCell("YQamount4").setValue(YQamount4);
                     footRow.getCell("YQamount5").setValue(YQamount5);
                     footRow.getCell("YQamount6").setValue(YQamount6);
                     footRow.getCell("YQamount7").setValue(YQamount7);
                     footRow.getCell("YQamount8").setValue(YQamount8);
                     footRow.getCell("YQamount9").setValue(YQamount9);
                     footRow.getCell("YQamount10").setValue(YQamount10);
                     footRow.getCell("YQamount11").setValue(YQamount11);
                     footRow.getCell("YQaccount1").setValue(YQaccount1);
                     footRow.getCell("YQaccount2").setValue(YQaccount2);
                     footRow.getCell("YQaccount3").setValue(YQaccount3);
                     footRow.getCell("YQaccount4").setValue(YQaccount4);
                     footRow.getCell("YQaccount5").setValue(YQaccount5);
                     footRow.getCell("YQaccount6").setValue(YQaccount6);
                     footRow.getCell("YQaccount7").setValue(YQaccount7);
                     footRow.getCell("YQaccount8").setValue(YQaccount8);
                     footRow.getCell("YQaccount9").setValue(YQaccount9);
                     footRow.getCell("YQaccount10").setValue(YQaccount10);
                     footRow.getCell("YQaccount11").setValue(YQaccount11);
                     
                     footRow.getCell("NQamount1").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("NQamount2").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("NQamount3").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("NQamount4").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("NQamount5").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("NQamount6").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("NQamount7").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("NQamount8").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("NQamount9").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("NQamount10").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("NQamount11").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("NQaccount1").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("NQaccount1").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("NQaccount2").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("NQaccount2").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("NQaccount3").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("NQaccount3").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("NQaccount4").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("NQaccount4").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("NQaccount5").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("NQaccount5").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("NQaccount6").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("NQaccount6").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("NQaccount7").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("NQaccount7").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("NQaccount8").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("NQaccount8").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("NQaccount9").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("NQaccount9").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("NQaccount10").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("NQaccount10").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("NQaccount11").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("NQaccount11").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("NQamount1").setValue(NQamount1);
                     footRow.getCell("NQamount2").setValue(NQamount2);
                     footRow.getCell("NQamount3").setValue(NQamount3);
                     footRow.getCell("NQamount4").setValue(NQamount4);
                     footRow.getCell("NQamount5").setValue(NQamount5);
                     footRow.getCell("NQamount6").setValue(NQamount6);
                     footRow.getCell("NQamount7").setValue(NQamount7);
                     footRow.getCell("NQamount8").setValue(NQamount8);
                     footRow.getCell("NQamount9").setValue(NQamount9);
                     footRow.getCell("NQamount10").setValue(NQamount10);
                     footRow.getCell("NQamount11").setValue(NQamount11);
                     footRow.getCell("NQaccount1").setValue(NQaccount1);
                     footRow.getCell("NQaccount2").setValue(NQaccount2);
                     footRow.getCell("NQaccount3").setValue(NQaccount3);
                     footRow.getCell("NQaccount4").setValue(NQaccount4);
                     footRow.getCell("NQaccount5").setValue(NQaccount5);
                     footRow.getCell("NQaccount6").setValue(NQaccount6);
                     footRow.getCell("NQaccount7").setValue(NQaccount7);
                     footRow.getCell("NQaccount8").setValue(NQaccount8);
                     footRow.getCell("NQaccount9").setValue(NQaccount9);
                     footRow.getCell("NQaccount10").setValue(NQaccount10);
                     footRow.getCell("NQaccount11").setValue(NQaccount11);
                     
                     footRow.getCell("amount1").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount2").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount3").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount4").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount5").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount6").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount7").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount8").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount9").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount10").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount11").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount12").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount13").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("account1").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("account1").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("account2").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("account2").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("account3").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("account3").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("account4").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("account4").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("account5").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("account5").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("account6").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("account6").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("account7").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("account7").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("account8").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("account8").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("account9").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("account9").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("account10").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("account10").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("account11").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("account11").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("account12").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("account12").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("account13").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("account13").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("amount1").setValue(amount1);
                     footRow.getCell("amount2").setValue(amount2);
                     footRow.getCell("amount3").setValue(amount3);
                     footRow.getCell("amount4").setValue(amount4);
                     footRow.getCell("amount5").setValue(amount5);
                     footRow.getCell("amount6").setValue(amount6);
                     footRow.getCell("amount7").setValue(amount7);
                     footRow.getCell("amount8").setValue(amount8);
                     footRow.getCell("amount9").setValue(amount9);
                     footRow.getCell("amount10").setValue(amount10);
                     footRow.getCell("amount11").setValue(amount11);
                     footRow.getCell("amount12").setValue(amount12);
                     footRow.getCell("amount13").setValue(amount13);
                     footRow.getCell("account1").setValue(account1);
                     footRow.getCell("account2").setValue(account2);
                     footRow.getCell("account3").setValue(account3);
                     footRow.getCell("account4").setValue(account4);
                     footRow.getCell("account5").setValue(account5);
                     footRow.getCell("account6").setValue(account6);
                     footRow.getCell("account7").setValue(account7);
                     footRow.getCell("account8").setValue(account8);
                     footRow.getCell("account9").setValue(account9);
                     footRow.getCell("account10").setValue(account10);
                     footRow.getCell("account11").setValue(account11);
                     footRow.getCell("account12").setValue(account12);
                     footRow.getCell("account13").setValue(account13);
         	         tblMain.setRefresh(true);
         	         if(rs.getRowCount() > 0){
         	        	tblMain.reLayoutAndPaint();
         	         }else{
         	        	tblMain.repaint();
         	         }
         	        tblMain.getGroupManager().setGroup(true);
        			
        			tblMain.getColumn("orgUnit").setGroup(true);
        			tblMain.getColumn("orgUnit").setMergeable(true);
        			
        			tblMain.getColumn("sellProject").setGroup(true);
        			tblMain.getColumn("sellProject").setMergeable(true);
                 }
            }
            );
            dialog.show();
    	}
    	isQuery=false;
	}
	protected void buildOrgTree() throws Exception{
		this.treeMain.setModel(TimeAccountReportFilterUI.getSellProjectForSHESellProject(actionOnLoad, MoneySysTypeEnum.SalehouseSys,true));
		this.treeMain.setSelectionRow(0);
		this.treeMain.expandRow(0);
	}
	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree.getLastSelectedPathComponent();
			return treeNode;
		}
		return null;
	}
	private void refresh() throws Exception {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj instanceof OrgStructureInfo) {
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
				params.setObject("sellProject", allSpIdStr);
			}else if(obj instanceof SellProjectInfo){
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
				params.setObject("sellProject", allSpIdStr);
			}
			query();
		}
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.refresh();
	}
	public void onLoad() throws Exception {
		isOnLoad=true;
		setShowDialogOnLoad(true);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		buildOrgTree();
		isOnLoad=false;
		this.refresh();
	}
	IUIWindow uiWindow=null;
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
		}
	}


}