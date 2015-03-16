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
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
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
public class BackAmountCycleReportUI extends AbstractBackAmountCycleReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(BackAmountCycleReportUI.class);
    
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    public BackAmountCycleReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
    }
    protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new BackAmountCycleReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return BackAmountCycleReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
		
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"account1","account2","account3","account4","account5","account6","account7"});
		
		tblMain.getColumn("amount1").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("amount2").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("amount3").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("amount4").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("amount5").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("amount6").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("amount7").getStyleAttributes().setFontColor(Color.BLUE);
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
                     BigDecimal amount1=FDCHelper.ZERO;
                     BigDecimal amount2=FDCHelper.ZERO;
                     BigDecimal amount3=FDCHelper.ZERO;
                     BigDecimal amount4=FDCHelper.ZERO;
                     BigDecimal amount5=FDCHelper.ZERO;
                     BigDecimal amount6=FDCHelper.ZERO;
                     BigDecimal amount7=FDCHelper.ZERO;
                     BigDecimal account1=FDCHelper.ZERO;
                     BigDecimal account2=FDCHelper.ZERO;
                     BigDecimal account3=FDCHelper.ZERO;
                     BigDecimal account4=FDCHelper.ZERO;
                     BigDecimal account5=FDCHelper.ZERO;
                     BigDecimal account6=FDCHelper.ZERO;
                     BigDecimal account7=FDCHelper.ZERO;
                     while(rs.next()){
                    	 row=tblMain.addRow();
                    	 ((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(row, rs.toRowArray());
                    	 row.getCell("orgUnit").setValue(row.getCell("orgUnit").getValue().toString().split("_")[3]);
                    	 if(row.getCell("typeName").getValue().toString().equals("小计")){
                    		 amount1=FDCHelper.add(amount1, row.getCell("amount1").getValue());
                    		 amount2=FDCHelper.add(amount2, row.getCell("amount2").getValue());
                    		 amount3=FDCHelper.add(amount3, row.getCell("amount3").getValue());
                    		 amount4=FDCHelper.add(amount4, row.getCell("amount4").getValue());
                    		 amount5=FDCHelper.add(amount5, row.getCell("amount5").getValue());
                    		 amount6=FDCHelper.add(amount6, row.getCell("amount6").getValue());
                    		 amount7=FDCHelper.add(amount7, row.getCell("amount7").getValue());
                    		 account1=FDCHelper.add(account1, row.getCell("account1").getValue());
                    		 account2=FDCHelper.add(account2, row.getCell("account2").getValue());
                    		 account3=FDCHelper.add(account3, row.getCell("account3").getValue());
                    		 account4=FDCHelper.add(account4, row.getCell("account4").getValue());
                    		 account5=FDCHelper.add(account5, row.getCell("account5").getValue());
                    		 account6=FDCHelper.add(account6, row.getCell("account6").getValue());
                    		 account7=FDCHelper.add(account7, row.getCell("account7").getValue());
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
                     footRow.getCell("amount1").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount2").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount3").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount4").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount5").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount6").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                     footRow.getCell("amount7").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
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
                     footRow.getCell("amount1").setValue(amount1);
                     footRow.getCell("amount2").setValue(amount2);
                     footRow.getCell("amount3").setValue(amount3);
                     footRow.getCell("amount4").setValue(amount4);
                     footRow.getCell("amount5").setValue(amount5);
                     footRow.getCell("amount6").setValue(amount6);
                     footRow.getCell("amount7").setValue(amount7);
                     footRow.getCell("account1").setValue(account1);
                     footRow.getCell("account2").setValue(account2);
                     footRow.getCell("account3").setValue(account3);
                     footRow.getCell("account4").setValue(account4);
                     footRow.getCell("account5").setValue(account5);
                     footRow.getCell("account6").setValue(account6);
                     footRow.getCell("account7").setValue(account7);
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
			if(this.tblMain.getColumnKey(e.getColIndex()).indexOf("amount")<0){
				return;
			}
			IRow row=this.tblMain.getRow(e.getRowIndex());
			Object amount=row.getCell(e.getColIndex()).getValue();
			if(amount==null||!(amount instanceof BigDecimal)||((BigDecimal)amount).compareTo(FDCHelper.ZERO)==0){
				return;
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.OWNER, this);
			RptParams param=new RptParams();
			param.setObject("sellProject", row.getCell("sellProjectId").getValue().toString());
			if(!this.tblMain.getHeadRow(1).getCell(e.getColIndex()).getValue().toString().equals("合计")){
				param.setObject("approach", this.tblMain.getHeadRow(1).getCell(e.getColIndex()).getValue().toString());
			}
			param.setObject("typeNumber", row.getCell("typeNumber").getValue().toString());
			param.setObject("fromActDate", params.getObject("fromActDate"));
			param.setObject("fromSignDate", params.getObject("fromSignDate"));
			param.setObject("fromAppDate", params.getObject("fromAppDate"));
			param.setObject("toActDate", params.getObject("toActDate"));
			param.setObject("toSignDate", params.getObject("toSignDate"));
			param.setObject("toAppDate", params.getObject("toAppDate"));
			uiContext.put("RPTFilter", param);
			uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(RoomLoanDetailReportUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}

}