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
import java.sql.SQLException;

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
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.AccActOnLoadBgFacadeFactory;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.market.client.EnterprisePlanEditUI;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.report.CusChangeReportFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.report.CustomerChangeReportFilterUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class AccActOnLoadBgUI extends AbstractAccActOnLoadBgUI
{
    private static final Logger logger = CoreUIObject.getLogger(AccActOnLoadBgUI.class);
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    public AccActOnLoadBgUI() throws Exception
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
		return null;
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return AccActOnLoadBgFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}
	public void onLoad() throws Exception {
		isOnLoad=true;
    	setShowDialogOnLoad(false);
    	tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionRefresh.setVisible(false);
		this.actionQuery.setVisible(false);
		if(this.getUIContext().get("title")!=null){
			this.setUITitle(this.getUIContext().get("title").toString());
		}
		isOnLoad=false;
		this.query();
    }
	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
		
		tblMain.getColumn("state").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof String){
					String info = (String)obj;
					if(FDCBillStateEnum.getEnum(info)==null){
						return "";
					}else{
						return FDCBillStateEnum.getEnum(info).getAlias();
					}
				}
				return super.getText(obj);
			}
		});
		tblMain.getColumn("number").getStyleAttributes().setFontColor(Color.BLUE);
	
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"amount","payAmount","actOnLoadAmount"});
		
		FDCHelper.formatTableDate(tblMain, "bizDate");
		FDCHelper.formatTableDate(tblMain, "createTime");
		FDCHelper.formatTableDate(tblMain, "auditTime");
		FDCHelper.formatTableDate(tblMain, "payDate");
		String[] fields=new String[tblMain.getColumnCount()];
		for(int i=0;i<tblMain.getColumnCount();i++){
			fields[i]=tblMain.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(tblMain,fields);
		CRMClientHelper.getFootRow(tblMain, new String[]{"amount","payAmount","actOnLoadAmount"});
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
            	RptParams rpt = getRemoteInstance().createTempTable(params);
                RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                KDTableUtil.setHeader(header, tblMain);
                
                RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
    	        tblMain.setRowCount(rs.getRowCount()+tblMain.getRowCount());
    	        KDTableUtil.insertRows(rs, 0, tblMain);
            }
        }
        );
        dialog.show();
    	isQuery=false;
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			String id=(String)this.tblMain.getRow(e.getRowIndex()).getCell("id").getValue();
			if(id!=null){
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put(UIContext.ID, id);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(PayRequestBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}
		}
	}
}