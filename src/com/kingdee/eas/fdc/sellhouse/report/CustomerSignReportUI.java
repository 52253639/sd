/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.market.client.EnterprisePlanEditUI;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.BankPaymentInfo;
import com.kingdee.eas.fdc.sellhouse.CalcTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CommercialStageEnum;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.fdc.sellhouse.client.BankPaymentEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SignManageEditUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.DefaultKDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class CustomerSignReportUI extends AbstractCustomerSignReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerSignReportUI.class);
    
    public CustomerSignReportUI() throws Exception
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
		return new CustomerSignReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return CustomerSignReportFacadeFactory.getRemoteInstance();
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
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"revAmount","strdTotalAmount","dealBuildPrice","dealRoomPrice","dealTotalAmount","attachmentAmount","fitmentTotalAmount"});
		CRMClientHelper.fmtDate(tblMain, new String[]{"bizDate","busAdscriptionDate","createTime","auditTime","lastUpdateTime"});
		tblMain.getColumn("bizState").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof String){
					String info = (String)obj;
					if(TransactionStateEnum.getEnum(info)==null){
						return "";
					}else{
						return TransactionStateEnum.getEnum(info).getAlias();
					}
				}
				return super.getText(obj);
			}
		});
		tblMain.getColumn("sellType").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof String){
					String info = (String)obj;
					if(SellTypeEnum.getEnum(info)==null){
						return "";
					}else{
						return SellTypeEnum.getEnum(info).getAlias();
					}
				}
				return super.getText(obj);
			}
		});
		tblMain.getColumn("valuationType").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof String){
					String info = (String)obj;
					if(CalcTypeEnum.getEnum(info)==null){
						return "";
					}else{
						return CalcTypeEnum.getEnum(info).getAlias();
					}
				}
				return super.getText(obj);
			}
		});
		EnterprisePlanEditUI.mergerTable(this.tblMain, new String[]{"id"},new String[]{"cusName","cusTel","cusCerNumber","cusAddress","amount"});
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
       try {
        	RptParams pp=(RptParams)params.clone();
        	pp.setObject("tree", null);
        	RptParams rpt = getRemoteInstance().createTempTable(pp);
            RptTableHeader header = (RptTableHeader)rpt.getObject("header");
            KDTableUtil.setHeader(header, tblMain);
            rpt=getRemoteInstance().query(pp);
	        tblMain.setRowCount(rpt.getInt("count"));
	        RptRowSet rs = (RptRowSet)rpt.getObject("rowset");
	        KDTableUtil.insertRows(rs, 0, tblMain);
        } catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row = this.tblMain.getRow(e.getRowIndex());
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", row.getCell("signId").getValue().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(SignManageEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}
}