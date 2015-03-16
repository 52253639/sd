/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.client.EnterprisePlanEditUI;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.AccountReportFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.CommercialStageEnum;
import com.kingdee.eas.fdc.sellhouse.PeriodEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryFactory;
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
public class SaleAmountReportUI extends AbstractSaleAmountReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(AccountReportUI.class);
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    public SaleAmountReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(1);
        enableExportExcel(tblMain);
    }

    public void onLoad() throws Exception {
    	isOnLoad=true;
		setShowDialogOnLoad(true);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		isOnLoad=false;
    }
    protected void initTree() throws Exception
	{
    	this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
    	this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
	protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new AccountReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return AccountReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}
	public static int diffdates(Date date1, Date date2)
    {
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        return (int)((time2 - time1) / 86400000L);
    }

	protected void query() {
		tblMain.removeColumns();
		tblMain.removeRows();
		
		for(int i=0;i<tblMain.getRowCount();i++){
			for(int j=i+1;j<this.tblMain.getRowCount();j++){
				if(tblMain.getRow(i).getCell("id").getValue().toString().equals(tblMain.getRow(j).getCell("id").getValue().toString())){
					tblMain.getRow(j).setTreeLevel(1);
					for(int k=0;k<tblMain.getColumnCount();k++){
						if(!(tblMain.getColumnKey(k).equals("alreadyAmount")||tblMain.getColumnKey(k).equals("revDate")||tblMain.getColumnKey(k).equals("id")||tblMain.getColumnKey(k).equals("newID"))){
							tblMain.getRow(j).getCell(k).setValue(null);
						}
					}
				}else{
					tblMain.getRow(j).setTreeLevel(0);
					break;
				}
			}
		}
		try {
			tblMain.getColumn("commercialStage").setRenderer(new ObjectValueRender(){
				public String getText(Object obj) {
					if(obj instanceof String){
						String info = (String)obj;
						if(CommercialStageEnum.getEnum(info)==null){
							return "";
						}else{
							return CommercialStageEnum.getEnum(info).getAlias();
						}
					}
					return super.getText(obj);
				}
			});
			tblMain.getColumn("returnPeriod").setRenderer(new ObjectValueRender(){
				public String getText(Object obj) {
					if(obj instanceof BigDecimal){
						int day = Integer.parseInt(obj.toString());
						if(day>=0&&day<7){
							return PeriodEnum.InnerSeven.getAlias();
						}else if(day>=7&&day<=14){
							return PeriodEnum.SevenToFourteen.getAlias();
						}else if(day>=15&&day<=30){
							return PeriodEnum.FifteenToThirty.getAlias();
						}else if(day>=31&&day<=60){
							return PeriodEnum.thirtyoneToSixty.getAlias();
						}else if(day>60){
							return PeriodEnum.SixtyMore.getAlias();
						}else{
							return "";
						}
					}
					return super.getText(obj);
				}
			});
			getRowSum();
			
			CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"contractTotal","alreadyAmount","actevAmount","notAccept","defaultAmount","notAccept","notproPortion","proPortion"});
			CRMClientHelper.fmtDate(tblMain, new String[]{"revDate","bizDate","lastTime","applyDate","defCalDate"});
			
			tblMain.getColumn("roomName").getStyleAttributes().setFontColor(Color.BLUE);
			tblMain.getColumn("customerNames").getStyleAttributes().setFontColor(Color.BLUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
		if(isOnLoad){
			try {
				initTree();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
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
                	RptParams pp=(RptParams)params.clone();
                	tblMain.setRefresh(false);
                	
                	RptParams resultRpt= getRemoteInstance().query(pp);
                 	return resultRpt;
                }
                public void afterExec(Object result)throws Exception{
                	tblMain.setRefresh(false);
                	
                	RptParams pp=(RptParams)params.clone();
                	RptParams rpt = getRemoteInstance().createTempTable(pp);
                    RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                    KDTableUtil.setHeader(header, tblMain);
                    
                    RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
        	        tblMain.setRowCount(rs.getRowCount()+tblMain.getRowCount());
        	        
        	        Date getdate=new Date();
        			try {
        				getdate = FDCCommonServerHelper.getServerTime();
        			} catch (BOSException e) {
        				e.printStackTrace();
        			}
        	        while(rs.next()){
    	        		IRow addRow=tblMain.addRow();
            			((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(addRow, rs.toRowArray());
            			Date appDate=(Date)addRow.getCell("applyDate").getValue();
            			if(appDate!=null){
            				int day=diffdates(FDCDateHelper.getDayBegin(appDate),FDCDateHelper.getDayBegin(getdate));
            				if(day>0){
            					BigDecimal notAccept=(BigDecimal)addRow.getCell("notAccept").getValue();
            					if(notAccept!=null){
            						if(notAccept.compareTo(FDCHelper.ZERO)<=0){
            							for(int j=10;j<tblMain.getColumnCount();j++){
            								addRow.getCell(j).getStyleAttributes().setBackground(Color.GREEN);
            							}
            						}else{
            							for(int j=10;j<tblMain.getColumnCount();j++){
            								addRow.getCell(j).getStyleAttributes().setBackground(Color.PINK);
            							}
            						}
            					}
            				}
            			}
        	        }
        	        if(rs.getRowCount() > 0){
        	        	tblMain.getTreeColumn().setDepth(2);
        	        	tblMain.reLayoutAndPaint();
        	        }else{
        	        	tblMain.repaint();
        	        }
        	        tblMain.setRefresh(true);
                }
            }
            );
            dialog.show();
    	}else{
			try {
				RptParams pp = (RptParams)params.clone();
	        	RptParams rpt = getRemoteInstance().createTempTable(pp);
	            RptTableHeader header = (RptTableHeader)rpt.getObject("header");
	            KDTableUtil.setHeader(header, tblMain);
	            tblMain.setRowCount(0);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
    	isQuery=false;
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj instanceof OrgStructureInfo||obj instanceof SellProjectInfo) {
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
				params.setObject("sellProject", allSpIdStr);
				if(obj instanceof SellProjectInfo){
					params.setObject("psp","'"+SHEManageHelper.getParentSellProject(null, (SellProjectInfo) obj).getId().toString()+"'");
				}else{
					params.setObject("psp",allSpIdStr);
				}
			}else{
				params.setObject("sellProject", null);
				params.setObject("psp",null);
			}
			query();
		}
	}
	/**
	 * 行总计、融合
	 * */
	private void getRowSum(){
		tblMain.setRowCount(tblMain.getRowCount());
		CRMClientHelper.getFootRow(tblMain, new String[]{"alreadyAmount","actevAmount","notAccept","defaultAmount"});
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex==-1){
    		return;
    	}
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
	    	IRow row   = tblMain.getRow(rowIndex);
	    	if(tblMain.getColumnKey(e.getColIndex()).equals("customerNames")){
	    		SignCustomerEntryCollection col=SignCustomerEntryFactory.getRemoteInstance().getSignCustomerEntryCollection("select customer.id from where head.id='"+row.getCell("newID").getValue()+"' and isMain=1");	    		
	    		if(col.size()>0){
	    			UIContext uiContext = new UIContext(this);
				    uiContext.put("ID", col.get(0).getCustomer().getId());
				    IUIWindow uiWindow = null;
					uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerRptEditUI.class.getName(),uiContext,null,OprtState.VIEW);
					uiWindow.show();
	    		}
	    	}else if(tblMain.getColumnKey(e.getColIndex()).equals("roomName")){
		    	UIContext uiContext = new UIContext(this);
			    uiContext.put("ID", row.getCell("newID").getValue());
			    IUIWindow uiWindow = null;
				uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SignManageEditUI.class.getName(),uiContext,null,OprtState.VIEW);
				uiWindow.show();
	    	}
	    }
	}
	}