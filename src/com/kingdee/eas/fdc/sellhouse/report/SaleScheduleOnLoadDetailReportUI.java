package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTGroupManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.sellhouse.client.SignManageEditUI;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

public class SaleScheduleOnLoadDetailReportUI extends AbstractSaleScheduleOnLoadDetailReportUI
{
  private static final Logger logger = CoreUIObject.getLogger(SaleScheduleOnLoadDetailReportUI.class);
  private boolean isQuery = false;

  public SaleScheduleOnLoadDetailReportUI() throws Exception
  {
    this.tblMain.checkParsed();
    this.tblMain.getDataRequestManager().addDataRequestListener(this);
    this.tblMain.getDataRequestManager().setDataRequestMode(0);
    enableExportExcel(this.tblMain);
  }

  protected RptParams getParamsForInit()
  {
    return null;
  }

  protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
    return null;
  }

  protected ICommRptBase getRemoteInstance() throws BOSException {
    return SaleScheduleOnLoadDetailReportFacadeFactory.getRemoteInstance();
  }

  protected KDTable getTableForPrintSetting() {
    return this.tblMain;
  }
  public void onLoad() throws Exception {
    setShowDialogOnLoad(true);
    this.tblMain.getStyleAttributes().setLocked(true);
    super.onLoad();
    this.tblMain.getSelectManager().setSelectMode(15);
    this.actionPrint.setVisible(false);
    this.actionPrintPreview.setVisible(false);
    this.actionQuery.setVisible(false);
  }
  protected void query() {
    this.tblMain.removeColumns();
    this.tblMain.removeRows();

    CRMClientHelper.fmtDate(this.tblMain, new String[] { "bizDate" });
    CRMClientHelper.changeTableNumberFormat(this.tblMain, new String[] { "contractTotalAmount", "revAmount", "amount", "appRevAmount" });
    CRMClientHelper.getFootRow(this.tblMain, new String[] { "revAmount", "amount", "appRevAmount" });
    this.tblMain.getColumn("room").getStyleAttributes().setFontColor(Color.BLUE);
  }
  public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
    if (this.isQuery) return;
    this.isQuery = true;
    Window win = SwingUtilities.getWindowAncestor(this);
    LongTimeDialog dialog = null;
    if ((win instanceof Frame))
      dialog = new LongTimeDialog((Frame)win);
    else if ((win instanceof Dialog)) {
      dialog = new LongTimeDialog((Dialog)win);
    }
    if (dialog == null) {
      dialog = new LongTimeDialog(new Frame());
    }
    dialog.setLongTimeTask(new ILongTimeTask() {
      public Object exec() throws Exception {
        RptParams resultRpt = getRemoteInstance().query(params);
        return resultRpt;
      }
      public void afterExec(Object result) throws Exception {
        tblMain.setRefresh(false);

        RptParams rpt = getRemoteInstance().createTempTable(params);
        RptTableHeader header = (RptTableHeader)rpt.getObject("header");
        KDTableUtil.setHeader(header, tblMain);

        RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
        KDTableUtil.insertRows(rs, 0, tblMain);
        tblMain.setRowCount(rs.getRowCount());

        if (rs.getRowCount() > 0)
          tblMain.reLayoutAndPaint();
        else {
          tblMain.repaint();
        }
        tblMain.setRefresh(true);

        tblMain.getGroupManager().setGroup(true);

        tblMain.getColumn("sellProject").setGroup(true);
        tblMain.getColumn("sellProject").setMergeable(true);

        tblMain.getColumn("room").setGroup(true);
        tblMain.getColumn("room").setMergeable(true);

        tblMain.getColumn("customer").setGroup(true);
        tblMain.getColumn("customer").setMergeable(true);

        tblMain.getColumn("contractTotalAmount").setGroup(true);
        tblMain.getColumn("contractTotalAmount").setMergeable(true);

        tblMain.getColumn("bizDate").setGroup(true);
        tblMain.getColumn("bizDate").setMergeable(true);
      }
    });
    dialog.show();
    this.isQuery = false;
  }
  protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
    if ((e.getType() == 1) && (e.getButton() == 1) && (e.getClickCount() == 2)) {
      int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
      if (rowIndex == -1) {
        return;
      }
      IRow row = this.tblMain.getRow(rowIndex);
      if (this.tblMain.getColumnKey(e.getColIndex()).equals("room")) {
        UIContext uiContext = new UIContext(this);
        uiContext.put("ID", row.getCell("id").getValue());
        IUIWindow uiWindow = null;
        uiWindow = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory").create(SignManageEditUI.class.getName(), uiContext, null, OprtState.VIEW);
        uiWindow.show();
      }
    }
  }
}