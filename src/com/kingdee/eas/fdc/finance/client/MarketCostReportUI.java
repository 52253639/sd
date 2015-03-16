/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.param.ParamControlFactory;
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
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.finance.MarketCostReportFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.report.RelateBillUI;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MarketCostReportUI extends AbstractMarketCostReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketCostReportUI.class);
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    public MarketCostReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
    }
    public void onLoad() throws Exception {
    	isOnLoad=true;
    	setShowDialogOnLoad(true);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		initTree();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.btnShowContract.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
		this.btnShowPay.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
		this.btnShowAll.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
		isOnLoad=false;
		
		this.refresh();
    }
    protected void initTree() throws Exception{
    	if(!SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()){
    		FDCMsgBox.showWarning(this,"非公司组织属性不能进入！");
    		SysUtil.abort();
    	}
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,"",SysContext.getSysContext().getCurrentOrgUnit().getId().toString(), null, null);
		treeMain.setModel(orgTreeModel);
		treeMain.setSelectionRow(0);
	}
	protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new MarketCostReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return MarketCostReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
		String[] coloum=new String[52];
		for(int i=4;i<tblMain.getColumnCount()-1;i++){
			coloum[i-4]=tblMain.getColumnKey(i);
			tblMain.getColumn(i).getStyleAttributes().setFontColor(Color.BLUE);
		}
		CRMClientHelper.changeTableNumberFormat(tblMain, coloum);
		tblMain.getViewManager().freeze(0, 4);
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
                
                IRowSet rs = (IRowSet)((RptParams)result).getObject("rowset");
    	        tblMain.setRowCount(rs.size());
    	        
    	        tblMain.setRefresh(false);
    	        int max=1;
    	        Map account=new HashMap();
    	        while(rs.next()){
    	        	boolean isLeaf=rs.getBoolean("isLeaf");
    	        	int level=Integer.parseInt(rs.getString("levelNumber"))-1;
    	        	String number=rs.getString("number");
    	        	String type=rs.getString("type");
    	        	IRow row=null;
    	        	if(account.get(number)!=null){
    	        		row=(IRow) account.get(number);
    	        	}else{
    	        		row=tblMain.addRow();
    	        		
    	        		if(!isLeaf){
    	        			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
    	        		}
    	        		row.getCell("isLeaf").setValue(isLeaf);
        	        	row.getCell("levelNumber").setValue(level);
        	        	if(level>max){
        	        		max=level;
        	        	}
        	        	row.setTreeLevel(level-1);
        	        	row.getCell("number").setValue(number);
        	        	row.getCell("name").setValue(rs.getString("name"));
        	        	account.put(number, row);
    	        	}
    	        	if(rs.getString("year")!=null&&rs.getString("month")!=null){
        	        	String key=rs.getString("year")+"year"+rs.getString("month")+"month";
        	        	String totalKey=rs.getString("year")+"year"+0+"month";
        	        	if(number.indexOf(".")>0){
        	        		String pnumber=number.substring(0, number.lastIndexOf("."));
            	        	for(int k=0;k<level;k++){
            	        		if(account.get(pnumber)!=null){
            	        			IRow prow=(IRow) account.get(pnumber);
            	        			if(type.equals("contract")){
            	        				if(prow.getCell(key+"CONTRACTACT").getValue()!=null){
                        	        		prow.getCell(key+"CONTRACTACT").setValue(((BigDecimal)prow.getCell(key+"CONTRACTACT").getValue()).add(rs.getBigDecimal("amount")));
                        	        	}else{
                        	        		prow.getCell(key+"CONTRACTACT").setValue(rs.getBigDecimal("amount"));
                        	        	}
                        	        	if(prow.getCell(totalKey+"CONTRACTACT").getValue()!=null){
                        	        		prow.getCell(totalKey+"CONTRACTACT").setValue(((BigDecimal)prow.getCell(totalKey+"CONTRACTACT").getValue()).add(rs.getBigDecimal("amount")));
                        	        	}else{
                        	        		prow.getCell(totalKey+"CONTRACTACT").setValue(rs.getBigDecimal("amount"));
                        	        	}
                	        		}else{
                	        			if(prow.getCell(key+"PAYACT").getValue()!=null){
                        	        		prow.getCell(key+"PAYACT").setValue(((BigDecimal)prow.getCell(key+"PAYACT").getValue()).add(rs.getBigDecimal("amount")));
                        	        	}else{
                        	        		prow.getCell(key+"PAYACT").setValue(rs.getBigDecimal("amount"));
                        	        	}
                        	        	if(prow.getCell(totalKey+"PAYACT").getValue()!=null){
                        	        		prow.getCell(totalKey+"PAYACT").setValue(((BigDecimal)prow.getCell(totalKey+"PAYACT").getValue()).add(rs.getBigDecimal("amount")));
                        	        	}else{
                        	        		prow.getCell(totalKey+"PAYACT").setValue(rs.getBigDecimal("amount"));
                        	        	}
                	        		}
            	        		}
            	        		if(pnumber.indexOf(".")>0){
            	        			pnumber=pnumber.substring(0, pnumber.lastIndexOf("."));
            	        		}
            	        	}
        	        	}
    	        		if(type.equals("contract")){
            	        	row.getCell(key+"CONTRACTACT").setValue(rs.getBigDecimal("amount"));
            	        	if(row.getCell(totalKey+"CONTRACTACT").getValue()!=null){
            	        		row.getCell(totalKey+"CONTRACTACT").setValue(((BigDecimal)row.getCell(totalKey+"CONTRACTACT").getValue()).add(rs.getBigDecimal("amount")));
            	        	}else{
            	        		row.getCell(totalKey+"CONTRACTACT").setValue(rs.getBigDecimal("amount"));
            	        	}
    	        		}else{
            	        	row.getCell(key+"PAYACT").setValue(rs.getBigDecimal("amount"));
            	        	if(row.getCell(totalKey+"PAYACT").getValue()!=null){
            	        		row.getCell(totalKey+"PAYACT").setValue(((BigDecimal)row.getCell(totalKey+"PAYACT").getValue()).add(rs.getBigDecimal("amount")));
            	        	}else{
            	        		row.getCell(totalKey+"PAYACT").setValue(rs.getBigDecimal("amount"));
            	        	}
    	        		}
    	        	}
    	        }
    	        if(rs.size() > 0){
    	        	tblMain.getTreeColumn().setDepth(max);
    	        	tblMain.expandTreeColumnTo(1);
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
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	this.refresh();
    }
    private void refresh() throws Exception {
    	DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
    	if(treeNode!=null){
//			Set leafPrjIds = FDCTreeHelper.getAllObjectIdMap(treeNode, "OrgStructure").keySet();
//			String allSpIdStr = FDCTreeHelper.getStringFromSet(leafPrjIds);
    		OrgStructureInfo objectInfo = (OrgStructureInfo) treeNode.getUserObject();
			params.setObject("orgUnit", objectInfo.getUnit().getLongNumber());
		}else{
			params.setObject("orgUnit", null);
		}
    	query();
	}
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
	    if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
	    	String key=this.tblMain.getColumnKey(e.getColIndex());
	    	Object value=this.tblMain.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue();
	    	String number=(String) this.tblMain.getRow(e.getRowIndex()).getCell("number").getValue();
	    	DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
	    	
	    	if(value==null||number==null||treeNode==null||!(value instanceof BigDecimal)) return;
	    	if(key.indexOf("year")<0||key.indexOf("month")<0)return;
	    	
	    	HashMap hmParamIn = new HashMap();
			hmParamIn.put("CIFI_MARKETCOSTREPORT", SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			if (hmAllParam.get("CIFI_MARKETCOSTREPORT") != null&&number.indexOf(hmAllParam.get("CIFI_MARKETCOSTREPORT").toString())>=0) {
				return;
			}
	    	UIContext uiContext = new UIContext(this);
	    	uiContext.put(UIContext.OWNER, this);
	    	uiContext.put("number", number);
	    
//	    	Set leafPrjIds = FDCTreeHelper.getAllObjectIdMap(treeNode, "OrgStructure").keySet();
//			String allSpIdStr = FDCTreeHelper.getStringFromSet(leafPrjIds);
	    	OrgStructureInfo objectInfo = (OrgStructureInfo) treeNode.getUserObject();
			uiContext.put("orgUnit", objectInfo.getUnit().getLongNumber());
	    	
	    	String year=key.substring(0,key.indexOf("year"));
	    	String month=key.substring(key.indexOf("year")+4,key.indexOf("month"));
	    	
	    	if(Integer.parseInt(month)==0){
	    		uiContext.put("isAll", true);
	    	}else{
	    		uiContext.put("isAll", false);
	    	}
	    	uiContext.put("year", year);
	    	uiContext.put("month", month);
	    	if(key.indexOf("CONTRACTACT")>0){
	    		uiContext.put("type", "contract");
	    	}else if(key.indexOf("PAYACT")>0){
	    		uiContext.put("type", "pay");
	    	}
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(MarketCostReprotRelateBillUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
	    }
    }
	protected void btnShowAll_actionPerformed(ActionEvent e) throws Exception {
		for(int i=0;i<this.tblMain.getColumnCount();i++){
			if(this.tblMain.getColumnKey(i).equals("isLeaf")||this.tblMain.getColumnKey(i).equals("levelNumber")){
				continue;
			}
			this.tblMain.getColumn(i).getStyleAttributes().setHided(false);
		}
	}
	protected void btnShowContract_actionPerformed(ActionEvent e) throws Exception {
		for(int i=0;i<this.tblMain.getColumnCount();i++){
			if(this.tblMain.getColumnKey(i).equals("isLeaf")||this.tblMain.getColumnKey(i).equals("levelNumber")){
				continue;
			}
			if(this.tblMain.getColumnKey(i).indexOf("CONTRACT")>0){
				this.tblMain.getColumn(i).getStyleAttributes().setHided(false);
			}else if(this.tblMain.getColumnKey(i).indexOf("PAY")>0){
				this.tblMain.getColumn(i).getStyleAttributes().setHided(true);
			}
		}
	}
	protected void btnShowPay_actionPerformed(ActionEvent e) throws Exception {
		for(int i=0;i<this.tblMain.getColumnCount();i++){
			if(this.tblMain.getColumnKey(i).equals("isLeaf")||this.tblMain.getColumnKey(i).equals("levelNumber")){
				continue;
			}
			if(this.tblMain.getColumnKey(i).indexOf("PAY")>0){
				this.tblMain.getColumn(i).getStyleAttributes().setHided(false);
			}else if(this.tblMain.getColumnKey(i).indexOf("CONTRACT")>0){
				this.tblMain.getColumn(i).getStyleAttributes().setHided(true);
			}
		}
	}
    
}