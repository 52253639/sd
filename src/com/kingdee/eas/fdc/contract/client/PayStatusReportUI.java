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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

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
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.PayStatusReportFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.CommercialStageEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.report.RoomLoanDetailReportUI;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.DefaultKDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class PayStatusReportUI extends AbstractPayStatusReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(PayStatusReportUI.class);
    protected Set authorizedOrgs = null;
    public PayStatusReportUI() throws Exception
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
		return new PayStatusReportFilterUI();
	}
	protected ICommRptBase getRemoteInstance() throws BOSException {
		return PayStatusReportFacadeFactory.getRemoteInstance();
	}
	protected KDTable getTableForPrintSetting() {
		return tblMain;
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
						if(info.equals("5PAY")){
							return "已付款";
						}else{
							return "";
						}
					}else{
						return FDCBillStateEnum.getEnum(info).getAlias();
					}
				}
				return super.getText(obj);
			}
		});
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"amount"});
		
		tblMain.getColumn("amount").getStyleAttributes().setFontColor(Color.BLUE);
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
                     BigDecimal amount=FDCHelper.ZERO;
                     
                     BigDecimal totalAmount=FDCHelper.ZERO;
                     BigDecimal subAmount=FDCHelper.ZERO;
                     
                     while(rs.next()){
                    	 row=tblMain.addRow();
                    	 ((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(row, rs.toRowArray());
                    	 row.getCell("orgUnit").setValue(row.getCell("orgUnit").getValue().toString().split("_")[row.getCell("orgUnit").getValue().toString().split("_").length-1]);
                    	 subAmount=(BigDecimal) row.getCell("amount").getValue();
                    	 amount=FDCHelper.add(amount, subAmount);
                    	 totalAmount=FDCHelper.add(totalAmount, subAmount);
                    	 if(row.getCell("actPayCompany").getValue()!=null&&tblMain.getRow(row.getRowIndex()-1)!=null&&
                    			 tblMain.getRow(row.getRowIndex()-1).getCell("actPayCompany").getValue()!=null&&!row.getCell("actPayCompany").getValue().toString().equals(tblMain.getRow(row.getRowIndex()-1).getCell("actPayCompany").getValue().toString())){
                    		 IRow totalRow=tblMain.addRow(row.getRowIndex());
                    		 totalRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
                    		 totalRow.getCell("amount").setValue(FDCHelper.subtract(totalAmount, subAmount));
                    		 totalRow.getCell("actPayCompany").setValue(tblMain.getRow(row.getRowIndex()-1).getCell("actPayCompany").getValue().toString()+"项目公司小计");
                    		 totalAmount=subAmount;
                    	 }
                	 }
                     if(tblMain.getRowCount()>0){
                    	 IRow totalRow=tblMain.addRow();
                		 totalRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
                		 totalRow.getCell("amount").setValue(totalAmount);
                		 totalRow.getCell("actPayCompany").setValue(tblMain.getRow(totalRow.getRowIndex()-1).getCell("actPayCompany").getValue().toString()+"项目公司小计");
                		 
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
                     footRow.getCell("amount").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                     footRow.getCell("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                     footRow.getCell("amount").setValue(amount);
         	         tblMain.setRefresh(true);
         	         if(rs.getRowCount() > 0){
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
	protected void initTree() throws Exception{
    	if(!SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()){
    		FDCMsgBox.showWarning(this,"非公司组织属性不能进入！");
    		SysUtil.abort();
    	}
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,"",SysContext.getSysContext().getCurrentOrgUnit().getId().toString(), null, null);
		this.treeMain.setModel(orgTreeModel);
		this.treeMain.setSelectionRow(0);
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
    		OrgStructureInfo objectInfo = (OrgStructureInfo) treeNode.getUserObject();
			params.setObject("orgUnit", objectInfo.getUnit().getLongNumber());
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
		initTree();
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
			
	    	Date fromDate=(Date)params.getObject("fromDate");
	    	Date toDate=(Date)params.getObject("toDate");
	    	String bgItemId=(String) row.getCell("bgItemId").getValue();
	    	String curProjectId=(String) row.getCell("curProjectId").getValue();
	    	String state=(String) row.getCell("state").getValue();
	    	String costedCompanyId=(String) row.getCell("costedCompanyId").getValue();
	    	String costedDeptId=(String) row.getCell("costedDeptId").getValue();
	    	
	    	
			StringBuffer sb=new StringBuffer();
			sb.append(" select distinct payRequest.fid id from T_CON_PayRequestBillBgEntry entry left join T_CON_PayRequestBill payRequest on payRequest.fid=entry.fheadid ");
			sb.append(" left join t_fdc_curProject curProject on curProject.fid=payRequest.fcurProjectId left join t_org_baseUnit bu on bu.fid=curProject.ffullOrgUnit ");
			sb.append(" where 1=1");
			if(state==null){
				sb.append(" and payRequest.fcurProjectid='"+this.tblMain.getRow(e.getRowIndex()-1).getCell("curProjectId").getValue().toString()+"'");
				sb.append(" and bu.fid='"+this.tblMain.getRow(e.getRowIndex()-1).getCell("buId").getValue().toString()+"'");
			}else{
				sb.append(" and payRequest.fcurProjectid='"+curProjectId+"'");
				sb.append(" and payRequest.fcostedCompanyId='"+costedCompanyId+"'");
				sb.append(" and payRequest.fcostedDeptId='"+costedDeptId+"'");
				if(state.equals("5PAY")){
					sb.append(" and payRequest.fpaydate is not null and payRequest.fstate='4AUDITTED'");
					if(fromDate!=null){
						sb.append(" and payRequest.fpaydate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
					}
					if(toDate!=null){
						sb.append(" and payRequest.fpaydate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
					}
				}else if(state.equals("4AUDITTED")){
					sb.append(" and payRequest.fpaydate is null and payRequest.fstate='4AUDITTED'");
					if(fromDate!=null){
						sb.append(" and payRequest.fauditTime>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
					}
					if(toDate!=null){
						sb.append(" and payRequest.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
					}
				}else{
					sb.append(" and payRequest.fstate!='4AUDITTED'");
				}
			}
			RptParams param=new RptParams();
			param.setObject("sb", sb);
			param.setObject("bgItemId", bgItemId);
			uiContext.put("RPTFilter", param);
			uiContext.put("title", "付款申请单查询");
			uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(AccActOnLoadBgUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}

}