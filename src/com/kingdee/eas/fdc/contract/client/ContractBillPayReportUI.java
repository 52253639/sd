/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ContractBillPayReportFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractBillReportFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ContractBillPayReportUI extends AbstractContractBillPayReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractBillPayReportUI.class);
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    public ContractBillPayReportUI() throws Exception
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
		return new ContractBillPayReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return ContractBillPayReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
		
		tblMain.getColumn("contractPropert").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof String){
					String info = (String)obj;
					if(ContractPropertyEnum.getEnum(info)==null){
						return "";
					}else{
						return ContractPropertyEnum.getEnum(info).getAlias();
					}
				}
				return super.getText(obj);
			}
		});
		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				String str = o.toString();
				if (!FDCHelper.isEmpty(str)) {
					return str + "%";
				}
				return str;
			}
		});
		tblMain.getColumn("payRate").setRenderer(render_scale);
		
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"pcAmount","srcAmount","originalAmount","amount","settleOriginalAmount","settleAmount","costAmount","appAmount","reqAmount","payAmount","payRate",
				"lstAppAmount","lstPayAmount","lstEndAppAmount","lstCostAmount","nowAppAmount","nowReqAmount","nowPayAmount"});
		
		tblMain.getColumn("number").getStyleAttributes().setFontColor(Color.BLUE);
		getFootRow(tblMain, new String[]{"srcAmount","amount","settleAmount","costAmount","appAmount","reqAmount","payAmount",
				"lstAppAmount","lstPayAmount","lstEndAppAmount","lstCostAmount","nowAppAmount","nowReqAmount","nowPayAmount"});
	}
	public static void getFootRow(KDTable tblMain,String[] columnName){
		IRow footRow = null;
        KDTFootManager footRowManager = tblMain.getFootManager();
        if(footRowManager == null)
        {
            String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
            footRowManager = new KDTFootManager(tblMain);
            footRowManager.addFootView();
            tblMain.setFootManager(footRowManager);
            footRow = footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
            tblMain.getIndexColumn().setWidthAdjustMode((short)1);
            tblMain.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        } else
        {
            footRow = footRowManager.getFootRow(0);
        }
        int columnCount = tblMain.getColumnCount();
        for(int c = 0; c < columnCount; c++)
        {
            String fieldName = tblMain.getColumn(c).getKey();
            for(int i = 0; i < columnName.length; i++)
            {
                String colName = (String)columnName[i];
                if(colName.equalsIgnoreCase(fieldName))
                {
                    ICell cell = footRow.getCell(c);
                    cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                    cell.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
                    cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
                    cell.setValue(getColumnValueSum(tblMain,colName));
                }
            }

        }
        footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
	}
	public static BigDecimal getColumnValueSum(KDTable table,String columnName) {
    	BigDecimal sum = new BigDecimal(0);
        for(int i=0;i<table.getRowCount();i++){
        	if(table.getRow(i).getCell(columnName).getValue()!=null&&table.getRow(i).getCell(columnName).getStyleAttributes().getBackground().equals(new java.awt.Color(246, 246, 191))){
        		if( table.getRow(i).getCell(columnName).getValue() instanceof BigDecimal)
            		sum = sum.add((BigDecimal)table.getRow(i).getCell(columnName).getValue());
            	else if(table.getRow(i).getCell(columnName).getValue() instanceof String){
            		String value = (String)table.getRow(i).getCell(columnName).getValue();
            		if(value.indexOf("Áã")==-1 && value.indexOf("[]")==-1){
            			value = value.replaceAll(",", "");
                		sum = sum.add(new BigDecimal(value));
            		}
            	}
            	else if(table.getRow(i).getCell(columnName).getValue() instanceof Integer){
            		String value = table.getRow(i).getCell(columnName).getValue().toString();
            		sum = sum.add(new BigDecimal(value));
            	}
        	}
        }
        return sum;
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
                     
                     Map value = (HashMap)((RptParams)result).getObject("value");
                     Object[] key=(Object[])((RptParams)result).getObject("key");
         	         tblMain.setRefresh(false);
         	         for (int sort = 0;sort < key.length; sort++) { 
         	        	 String contractType = (String) key[sort];
         	        	 IRow addRow=tblMain.addRow();
         	        	 addRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
         	        	 addRow.setTreeLevel(0);
         	        	 addRow.getCell("name").setValue(contractType);
         	        	 
         	        	 
         	        	 List list = (ArrayList) value.get(contractType);
         	        	 BigDecimal srcAmount=FDCHelper.ZERO;
         	        	 BigDecimal amount=FDCHelper.ZERO;
         	        	 BigDecimal settleAmount=FDCHelper.ZERO;
         	        	 
         	        	 BigDecimal costAmount=FDCHelper.ZERO;
         	        	 BigDecimal appAmount=FDCHelper.ZERO;
         	        	 BigDecimal reqAmount=FDCHelper.ZERO;
         	        	 BigDecimal payAmount=FDCHelper.ZERO;
         	        	 BigDecimal lstAppAmount=FDCHelper.ZERO;
         	        	 BigDecimal lstPayAmount=FDCHelper.ZERO;
         	        	 BigDecimal lstEndAppAmount=FDCHelper.ZERO;
         	        	 BigDecimal lstCostAmount=FDCHelper.ZERO;
         	        	 BigDecimal nowAppAmount=FDCHelper.ZERO;
         	        	 BigDecimal nowReqAmount=FDCHelper.ZERO;
         	        	 BigDecimal nowPayAmount=FDCHelper.ZERO;
        				
         	        	 for(int i=0;i<list.size();i++){
         	        		 IRow row=tblMain.addRow();
         	        		 row.setTreeLevel(1);
         	        		 Object[] rowData=(Object[]) list.get(i);
         	        		 for(int k=0;k<rowData.length;k++){
         	                    row.getCell(k).setValue(rowData[k]);
         	        		 }
         	        		 addRow.getCell("pcAmount").setValue(row.getCell("pcAmount").getValue());
         	        		 row.getCell("pcAmount").setValue(null);
         	        		 
         	        		 srcAmount=FDCHelper.add(srcAmount, row.getCell("srcAmount").getValue());
         	        		 amount=FDCHelper.add(amount, row.getCell("amount").getValue());
         	        		 settleAmount=FDCHelper.add(settleAmount, row.getCell("settleAmount").getValue());
         	        		 
         	        		costAmount=FDCHelper.add(costAmount, row.getCell("costAmount").getValue());
         	        		appAmount=FDCHelper.add(appAmount, row.getCell("appAmount").getValue());
         	        		reqAmount=FDCHelper.add(reqAmount, row.getCell("reqAmount").getValue());
         	        		payAmount=FDCHelper.add(payAmount, row.getCell("payAmount").getValue());
         	        		lstAppAmount=FDCHelper.add(lstAppAmount, row.getCell("lstAppAmount").getValue());
         	        		lstPayAmount=FDCHelper.add(lstPayAmount, row.getCell("lstPayAmount").getValue());
         	        		lstEndAppAmount=FDCHelper.add(lstEndAppAmount, row.getCell("lstEndAppAmount").getValue());
         	        		lstCostAmount=FDCHelper.add(lstCostAmount, row.getCell("lstCostAmount").getValue());
         	        		nowAppAmount=FDCHelper.add(nowAppAmount, row.getCell("nowAppAmount").getValue());
         	        		nowReqAmount=FDCHelper.add(nowReqAmount, row.getCell("nowReqAmount").getValue());
         	        		nowPayAmount=FDCHelper.add(nowPayAmount, row.getCell("nowPayAmount").getValue());
         	        	 }
         	        	 addRow.getCell("srcAmount").setValue(srcAmount);
         	        	 addRow.getCell("amount").setValue(amount);
         	        	 addRow.getCell("settleAmount").setValue(settleAmount);
         	        	 
         	        	 addRow.getCell("costAmount").setValue(costAmount);
        	        	 addRow.getCell("appAmount").setValue(appAmount);
        	        	 addRow.getCell("reqAmount").setValue(reqAmount);
        	        	 addRow.getCell("payAmount").setValue(payAmount);
         	        	 addRow.getCell("lstAppAmount").setValue(lstAppAmount);
         	        	 addRow.getCell("lstPayAmount").setValue(lstPayAmount);
        	        	 addRow.getCell("lstEndAppAmount").setValue(lstEndAppAmount);
         	        	 addRow.getCell("lstCostAmount").setValue(lstCostAmount);
         	        	 addRow.getCell("nowAppAmount").setValue(nowAppAmount);
         	        	 addRow.getCell("nowReqAmount").setValue(nowReqAmount);
         	        	 addRow.getCell("nowPayAmount").setValue(nowPayAmount);
         	         }
         	         tblMain.setRowCount(tblMain.getRowCount());
         	         tblMain.setRefresh(true);
         	         if(tblMain.getRowCount() > 0){
         	        	tblMain.getTreeColumn().setDepth(2);
         	        	tblMain.reLayoutAndPaint();
         	         }else{
         	        	tblMain.repaint();
         	         }
                 }
            }
            );
            dialog.show();
    	}
    	isQuery=false;
	}
	protected void buildOrgTree() throws Exception{
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, this.treeMain, actionOnLoad);
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
			Set leafPrjIds = FDCClientHelper.getProjectLeafsOfNode(treeNode);
			String allSpIdStr = FDCTreeHelper.getStringFromSet(leafPrjIds);
			params.setObject("curProject", allSpIdStr);
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
		buildOrgTree();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		isOnLoad=false;
		this.actionQuery.setVisible(true);
		this.refresh();
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			if(tblMain.getColumnKey(e.getColIndex()).equals("number")){
				String id=(String)this.tblMain.getRow(e.getRowIndex()).getCell("id").getValue();
				if(id!=null){
					UIContext uiContext = new UIContext(this);
					uiContext.put(UIContext.OWNER, this);
					uiContext.put(UIContext.ID, id);
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
					uiWindow.show();
				}
			}
		}
	}
}