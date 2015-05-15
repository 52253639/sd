/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

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
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.WorkFlowReportFacadeFactory;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;

/**
 * output class name
 */
public class WorkFlowReportUI extends AbstractWorkFlowReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(WorkFlowReportUI.class);
    IUIWindow uiWindow=null;
    public WorkFlowReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
    }
    private boolean isQuery=false;
    protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new WorkFlowReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return WorkFlowReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}
	public void onLoad() throws Exception {
    	setShowDialogOnLoad(true);
    	tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
    }
	protected void query() {
		tblMain.removeColumns();
		tblMain.removeRows();
		
		Object obj[] = (Object[])params.getObject("person");
		String totalColoum[]=new String[2+obj.length*2];
		
		totalColoum[0]="TOTALTIME";
		totalColoum[1]="TOTALAMOUNT";
		int k=2;
		for(int i = 0; i < obj.length; i++){
    		PersonInfo info=(PersonInfo) obj[i];
    		
    		CRMClientHelper.changeTableNumberFormat(tblMain,info.getId().toString()+"TIME");
    		CRMClientHelper.changeTableNumberFormat(tblMain,info.getId().toString()+"AVERAGE");
    		
    		totalColoum[k]=info.getId().toString()+"TIME";
    		totalColoum[k+1]=info.getId().toString()+"AMOUNT";
    		
    		tblMain.getColumn(info.getId().toString()+"AMOUNT").getStyleAttributes().setFontColor(Color.BLUE);
    		
    		k=k+2;
		}
		tblMain.getColumn("TOTALAMOUNT").getStyleAttributes().setFontColor(Color.BLUE);
		
		CRMClientHelper.changeTableNumberFormat(tblMain,"TOTALTIME");
		CRMClientHelper.changeTableNumberFormat(tblMain,"TOTALAVERAGE");
		
		CRMClientHelper.getFootRow(tblMain,totalColoum);
		for(int i = 0; i < totalColoum.length; i++){
			if(totalColoum[i].indexOf("AMOUNT")>0){
				tblMain.getFootRow(0).getCell(totalColoum[i]).getStyleAttributes().setNumberFormat("#,##0;-#,##0");
			}
		}
		for(int i = 0; i < obj.length; i++){
    		PersonInfo info=(PersonInfo) obj[i];
    		tblMain.getFootRow(0).getCell(info.getId().toString()+"AVERAGE").setValue(FDCHelper.divide(tblMain.getFootRow(0).getCell(info.getId().toString()+"TIME").getValue(), tblMain.getFootRow(0).getCell(info.getId().toString()+"AMOUNT").getValue(), 2, BigDecimal.ROUND_HALF_UP));
		}
		tblMain.getFootRow(0).getCell("TOTALAVERAGE").setValue(FDCHelper.divide(tblMain.getFootRow(0).getCell("TOTALTIME").getValue(), tblMain.getFootRow(0).getCell("TOTALAMOUNT").getValue(), 2, BigDecimal.ROUND_HALF_UP));
	
		tblMain.getColumn("TOTALTIME").getStyleAttributes().setBackground(tblMain.getFootRow(0).getStyleAttributes().getBackground());
		tblMain.getColumn("TOTALAMOUNT").getStyleAttributes().setBackground(tblMain.getFootRow(0).getStyleAttributes().getBackground());
		tblMain.getColumn("TOTALAVERAGE").getStyleAttributes().setBackground(tblMain.getFootRow(0).getStyleAttributes().getBackground());
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
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
            	tblMain.setRefresh(false);
            	
            	RptParams rpt = getRemoteInstance().createTempTable(params);
                RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                KDTableUtil.setHeader(header, tblMain);
                
    	        RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
    	        RptRowSet detailRowset = (RptRowSet)((RptParams)result).getObject("detailRowset");
    	        
    	        Map workRow=new HashMap();
    	        while(rs.next()){
    	        	IRow row=tblMain.addRow();
    	        	row.getCell("id").setValue(rs.getString("id"));
    	        	row.getCell("name").setValue(rs.getString("name"));
    	        	
    	        	workRow.put(rs.getString("id"), row);
    	        }
    	        while(detailRowset.next()){
    	        	IRow row=(IRow) workRow.get(detailRowset.getString("id"));
    	        	if(row!=null){
    	        		row.getCell(detailRowset.getString("personId")+"TIME").setValue(detailRowset.getBigDecimal("workTime"));
    	        		row.getCell(detailRowset.getString("personId")+"AMOUNT").setValue(detailRowset.getInt("workAmount"));
    	        		row.getCell(detailRowset.getString("personId")+"AVERAGE").setValue(FDCHelper.divide(row.getCell(detailRowset.getString("personId")+"TIME").getValue(), row.getCell(detailRowset.getString("personId")+"AMOUNT").getValue(), 2, BigDecimal.ROUND_HALF_UP));
    	        	
    	        		row.getCell("TOTALTIME").setValue(FDCHelper.add(row.getCell("TOTALTIME").getValue(), row.getCell(detailRowset.getString("personId")+"TIME").getValue()));
    	        		row.getCell("TOTALAMOUNT").setValue(Integer.parseInt(FDCHelper.add(row.getCell("TOTALAMOUNT").getValue(), row.getCell(detailRowset.getString("personId")+"AMOUNT").getValue()).toString()));
    	        		row.getCell("TOTALAVERAGE").setValue(FDCHelper.divide(row.getCell("TOTALTIME").getValue(), row.getCell("TOTALAMOUNT").getValue(), 2, BigDecimal.ROUND_HALF_UP));
    	        	}
    	        }
    	        tblMain.setRowCount(rs.getRowCount());
    	        if(rs.getRowCount() > 0){
    	        	tblMain.reLayoutAndPaint();
    	        }else{
    	        	tblMain.repaint();
    	        }
    	        tblMain.setRefresh(true);
            }
        }
        );
        dialog.show();
        isQuery=false;
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			RptParams param=new RptParams();
			param.setObject("fromDate", params.getObject("fromDate"));
			param.setObject("toDate", params.getObject("toDate"));
			String key=this.tblMain.getColumnKey(e.getColIndex());
			if(key.equals("TOTALAMOUNT")){
				param.setObject("personObj", params.getObject("person"));
			}else if(key.indexOf("AMOUNT")>0){
				param.setObject("person", key.substring(0, key.indexOf("AMOUNT")));
			}else{
				return;
			}
			IRow row=this.tblMain.getRow(e.getRowIndex());
			Object amount=(Object)row.getCell(e.getColIndex()).getValue();
			if(amount==null||!(amount instanceof Integer)||Integer.parseInt(amount.toString())<=0){
				return;
			}
			param.setObject("procdefId", row.getCell("id").getValue().toString());
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.OWNER, this);
			if(uiWindow!=null){
				uiWindow.close();
			}
			uiContext.put("RPTFilter", param);
			uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(WorkFlowDetailReportUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}

}