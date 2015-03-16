/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.rpt;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SupplierStockAccountReportUI extends AbstractSupplierStockAccountReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierStockAccountReportUI.class);
    
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    public SupplierStockAccountReportUI() throws Exception
    {
        super();
        kDTable1.checkParsed();
        kDTable1.getDataRequestManager().addDataRequestListener(this);
        kDTable1.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(kDTable1);
    }
    protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new SupplierStockAccountReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return SupplierStockAccountReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return kDTable1;
	}

	protected void query() {
		if(isOnLoad) return;
		kDTable1.removeColumns();
		kDTable1.removeRows();
		
		for(int i=0;i<this.kDTable1.getColumnCount();i++){
			if(this.kDTable1.getColumnKey(i).indexOf("Rate")>0){
				this.kDTable1.getColumn(this.kDTable1.getColumnKey(i)).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			}
	        this.kDTable1.getColumn(this.kDTable1.getColumnKey(i)).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		}
		
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
		kDTable1.getColumn("excellentRate").setRenderer(render_scale);
		kDTable1.getColumn("qualifiedRate").setRenderer(render_scale);
		kDTable1.getColumn("limitRate").setRenderer(render_scale);
		kDTable1.getColumn("unCopPassRate").setRenderer(render_scale);
		kDTable1.getColumn("newRate").setRenderer(render_scale);
		kDTable1.getColumn("unPassRate").setRenderer(render_scale);
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)this.kDTree1.getLastSelectedPathComponent();
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
                    KDTableUtil.setHeader(header, kDTable1);
                    
                    RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
        	        kDTable1.setRowCount(rs.getRowCount()+kDTable1.getRowCount());
        	        KDTableUtil.insertRows(rs, 0, kDTable1);
                }
            }
            );
            dialog.show();
    	}
    	isQuery=false;
	}
	protected void buildOrgTree() throws Exception{
		OrgUnitInfo cuInfo = SysContext.getSysContext().getCurrentOrgUnit();
		if (!cuInfo.isIsPurchaseOrgUnit()) {
			MsgBox.showInfo(this, "非采购组织不能操作");
			SysUtil.abort();
		}
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.PURCHASE,"", cuInfo.getId().toString(), null, FDCHelper.getActionPK(this.actionOnLoad));
		this.kDTree1.setModel(orgTreeModel);
		this.kDTree1.setSelectionRow(0);
	}
	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree.getLastSelectedPathComponent();
			return treeNode;
			}
		return null;
	}
	private void refresh() throws Exception {
    	DefaultKingdeeTreeNode OrgNode = this.getSelectedTreeNode(kDTree1);
    	if(OrgNode.getUserObject() instanceof OrgStructureInfo){
    		OrgStructureInfo org=(OrgStructureInfo)OrgNode.getUserObject();
			String longNumber=org.getLongNumber();
    		params.setObject("org", longNumber);
    	}else{
    		params.setObject("org", null);
    	}
    	query();
	}
	protected void orgTree_valueChanged(TreeSelectionEvent e) throws Exception {
		this.refresh();
	}
	public void onLoad() throws Exception {
		isOnLoad=true;
		setShowDialogOnLoad(true);
		kDTable1.getStyleAttributes().setLocked(true);
		super.onLoad();
		buildOrgTree();
		kDTable1.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		isOnLoad=false;
		
		this.refresh();
	}


}