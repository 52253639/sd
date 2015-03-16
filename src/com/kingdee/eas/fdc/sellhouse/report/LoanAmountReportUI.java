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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
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
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.contract.client.AccActOnLoadBgUI;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.AccountReportUI;
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

/**
 * output class name
 */
public class LoanAmountReportUI extends AbstractLoanAmountReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(LoanAmountReportUI.class);
    private DefaultKingdeeTreeNode resultTreeNode; 
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    private String txtSp;
    IUIWindow uiWindow=null;
    public LoanAmountReportUI() throws Exception
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
		return new LoanAmountReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return LoanAmountReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}
	protected void query() {
		tblMain.removeColumns();
		tblMain.removeRows();
		
		sum();
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"amount","revAmount","stockAmount","rate","revRate"});
		
		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				String str = o.toString();
				if (!FDCHelper.isEmpty(str)) {
					if(str.equals("0")){
						return "0.00%";
					}else{
						return str + "%";
					}
					
				}
				return str;
			}
		});
		tblMain.getColumn("amount").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("revAmount").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("stockAmount").getStyleAttributes().setFontColor(Color.BLUE);
		
		tblMain.getColumn("rate").setRenderer(render_scale);
		tblMain.getColumn("revRate").setRenderer(render_scale);
		tblMain.getViewManager().freeze(0, 2);
	}
	public void addSumRow(DefaultKingdeeTreeNode node,int dep) throws EASBizException, BOSException{
		if(node!=null){
			Object obj = node.getUserObject();
			IRow row=this.tblMain.addRow();
			row.setTreeLevel(dep);
			if(!node.isLeaf()){
				row.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
			}
			
			String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet());
			row.getCell("sellProjectId").setValue(allSpIdStr);
			if(obj instanceof SellProjectInfo){
				row.getCell("sellProjectName").setValue(((SellProjectInfo)obj).getName());
			}else{
				row.getCell("sellProjectName").setValue(((OrgStructureInfo)obj).getUnit().getName());
			}
			row.getCell("amount").setValue(FDCHelper.ZERO);
			row.getCell("revAmount").setValue(FDCHelper.ZERO);
			row.getCell("stockAmount").setValue(FDCHelper.ZERO);
			row.getCell("rate").setValue(FDCHelper.ZERO);
			row.getCell("revRate").setValue(FDCHelper.ZERO);
			++dep;
			for (int i = 0; i < node.getChildCount(); i++) {
				addSumRow((DefaultKingdeeTreeNode) node.getChildAt(i),dep);
			}
		}
	}
	public IRow getParentRow(String sellProjectId){
		for(int i=0;i<this.tblMain.getRowCount();i++){
			IRow row=this.tblMain.getRow(i);
			if(row.getCell("sellProjectId").getValue()!=null&&!"".equals(row.getCell("sellProjectId").getValue().toString().trim())
					&&row.getCell("sellProjectId").getValue().toString().replaceAll("'", "").equals(sellProjectId)){
				return row;
			}
		}
		return null;
	}
	public void sumRow(IRow sumRow,IRow row){
		SHEManageHelper.addSumColoum(sumRow,row,new String[]{"amount","revAmount","stockAmount"});
		sumRow.getCell("rate").setValue(FDCHelper.multiply(FDCHelper.divide(sumRow.getCell("revAmount").getValue(), sumRow.getCell("amount").getValue(),4, BigDecimal.ROUND_HALF_UP),new BigDecimal(100)));
		sumRow.getCell("revRate").setValue(FDCHelper.multiply(FDCHelper.divide(FDCHelper.add(sumRow.getCell("revAmount").getValue(), sumRow.getCell("stockAmount").getValue()), sumRow.getCell("amount").getValue(),4, BigDecimal.ROUND_HALF_UP), new BigDecimal(100)));
	}
	public void sum(){
		for(int i=this.tblMain.getRowCount()-1;i>=0;i--){
			IRow row=this.tblMain.getRow(i);
			String id=row.getCell("sellProjectId").getValue().toString();
			if(id.indexOf("'")<0){
				for(int j=0;j<i;j++){
					IRow sumRow=this.tblMain.getRow(j);
					if(sumRow.getCell("sellProjectId").getValue().toString().indexOf(id)>0&&sumRow.getCell("sellProjectId").getValue().toString().indexOf("'")==0){
						sumRow(sumRow,row);
					}
				}
			}
		}
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
		if(isOnLoad||(!txtSp.equals(params.getObject("txtSp")))){
			try {
				initTree();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
        resultTreeNode=treeNode;
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
                	pp.setObject("tree", null);
                	pp.setObject("baseTree", null);
                	tblMain.setRefresh(false);
                	
                	RptParams resultRpt= getRemoteInstance().query(pp);
                 	return resultRpt;
                }
                public void afterExec(Object result)throws Exception{
                	tblMain.setRefresh(false);
                	
                	RptParams pp=(RptParams)params.clone();
                	pp.setObject("tree", null);
                	pp.setObject("baseTree", null);
                	
                	RptParams rpt = getRemoteInstance().createTempTable(pp);
                    RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                    KDTableUtil.setHeader(header, tblMain);
                    
                    RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
        	        if(!resultTreeNode.isLeaf()){
        	        	tblMain.getTreeColumn().setDepth(resultTreeNode.getDepth()+1);
        		        addSumRow(resultTreeNode,0);
        	        }
        	        tblMain.setRowCount(rs.getRowCount()+tblMain.getRowCount());
        	        
        	        IRow row;
        	        while(rs.next()){
                		if(resultTreeNode.isLeaf()){
        	        		IRow addRow=tblMain.addRow();
                			((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(addRow, rs.toRowArray());
        	        	}else{
        	        		String id=rs.getString("sellProjectId");
        	        		row=getParentRow(id);
        		        	if(row!=null){
        	        			((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(row, rs.toRowArray());
        	        			row.setTreeLevel(row.getTreeLevel());
        		        	}
        	        	}
        	        }
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
    	}else{
			try {
				RptParams pp = (RptParams)params.clone();
				pp.setObject("tree", null);
				pp.setObject("baseTree", null);
	        	
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
	protected void initTree() throws Exception{
		try {
			txtSp=(String) params.getObject("txtSp");
			if(params.getObject("tree")!=null){
				this.treeMain.setModel((TreeModel) params.getObject("tree"));
			}else if(params.getObject("baseTree")!=null){
				this.treeMain.setModel((TreeModel) params.getObject("baseTree"));
			}else{
				this.treeMain.setModel(FDCTreeHelper.getSellProjectForSHESellProject(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
			}
		    this.treeMain.expandPath(this.treeMain.getSelectionPath());
		  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj instanceof OrgStructureInfo||obj instanceof SellProjectInfo) {
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
				params.setObject("sellProject", allSpIdStr);
			}else{
				params.setObject("sellProject", null);
			}
			query();
		}
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			Object value=this.tblMain.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue();
			if(value==null
					||(value!=null&&value instanceof BigDecimal
							&&((BigDecimal)value).compareTo(FDCHelper.ZERO)<=0)){
				return;
			}
			Date fromDate = (Date)params.getObject("fromDate");
	    	Date toDate =   (Date)params.getObject("toDate");
			
			MoneyDefineCollection col=MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection("select * from where moneyType ='LoanAmount'");
			Object[] moneyDefine = new Object[col.size()];
			for(int i=0;i<col.size();i++){
				moneyDefine[i]=col.get(i);
			}
			RptParams param=new RptParams();
			param.setObject("moneyDefine", moneyDefine);
			
			String sellProjectId=this.tblMain.getRow(e.getRowIndex()).getCell("sellProjectId").getValue().toString();
			if(sellProjectId.indexOf("'")>=0){
				param.setObject("sellProject", sellProjectId);
			}else{
				param.setObject("sellProject", "'"+sellProjectId+"'");
			}
			param.setObject("isClick", Boolean.TRUE);
			if(this.tblMain.getColumnKey(e.getColIndex()).equals("amount")){
				param.setObject("fromDate", fromDate);
				param.setObject("toDate", toDate);
			}else if(this.tblMain.getColumnKey(e.getColIndex()).equals("revAmount")){
				param.setObject("fromDate", fromDate);
				param.setObject("toDate", toDate);
				param.setObject("fromRevDate", fromDate);
				param.setObject("toRevDate", toDate);
			}else if(this.tblMain.getColumnKey(e.getColIndex()).equals("stockAmount")){
				param.setObject("toDate", fromDate);
				param.setObject("fromRevDate", fromDate);
				param.setObject("toRevDate", toDate);
			}
			if(uiWindow!=null){
				uiWindow.close();
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.OWNER, this);
			uiContext.put("RPTFilter", param);
			uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(AccountReportUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}
}