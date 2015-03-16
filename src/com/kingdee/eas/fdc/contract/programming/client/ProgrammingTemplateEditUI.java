/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.ContractTypePromptSelector;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.contract.programming.PTECostCollection;
import com.kingdee.eas.fdc.contract.programming.PTECostFactory;
import com.kingdee.eas.fdc.contract.programming.PTECostInfo;
import com.kingdee.eas.fdc.contract.programming.PTEEnonomyCollection;
import com.kingdee.eas.fdc.contract.programming.PTEEnonomyFactory;
import com.kingdee.eas.fdc.contract.programming.PTEEnonomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingException;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo;
import com.kingdee.eas.fdc.migrate.StringUtils;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.mm.control.client.TableCellComparator;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class ProgrammingTemplateEditUI extends AbstractProgrammingTemplateEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgrammingTemplateEditUI.class);

    private CreateTableRow create = new CreateTableRow(this.dataBinder);
    private final String LONGNUMBER = "longNumber";
    private final String HEADNUMBER = "headNumber";
	private final String ATTACHMENT = "attachment";
	private final String REMARK = "remark";
    private boolean isCopy = false;
    private boolean isSaved = false;
    
    protected KDWorkButton btnAddnewLine;
    protected KDWorkButton btnInsertLine;
    protected KDWorkButton btnRemoveLines;
    protected KDWorkButton btnDetails;
    
    /**
     * output class constructor
     */
    public ProgrammingTemplateEditUI() throws Exception
    {
        super();
    }
    
    /**
     * output loadFields method
     */
    public void loadFields(){
        super.loadFields(); 

        List rows = this.kdtEntires.getBody().getRows();
        Collections.sort(rows, new TableCellComparator(kdtEntires.getColumnIndex("sortNumber"), KDTSortManager.SORT_ASCEND));
    	this.kdtEntires.setRefresh(true);
        
    	kdtEntires.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
    	kdtEntires.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
        if(!OprtState.ADDNEW.equals(this.getOprtState())){
        	createTree();
        	setNameDisplay();
        	handleOldData();
        }
        try {
			loadCostAccountOnLoad();
		} catch (BOSException e) {
			logger.error("loadCostAccount", e);
		} catch (EASBizException e) {
			logger.error("loadCostAccount", e);
		}
		if (!OprtState.VIEW.equals(getOprtState())) {
			setCellEditorForTable();
		}
		cellAttachment();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
		initTable();
    	Map uiContext = this.getUIContext();
    	if(Boolean.TRUE.equals((Boolean)uiContext.get("Copy"))){
    		actionCopy_actionPerformed(null);
    		createTree();
    		isCopy = true;
    		loadCostAccountOnLoad();
    	}
    	setSmallButton();
    	costAccountF7();
		setAttachmentRenderer();
    	setSmallBtnEnable();
    	setMouseClick();
	}

	private void cellAttachment() {
		for (int i = 0; i < kdtEntires.getRowCount(); i++) {
			Object idObj = kdtEntires.getCell(i, "id").getValue();
			if (idObj != null) {
				String id = idObj.toString();
				StringBuffer allAttachmentName = getAllAttachmentName(id);
				if (!FDCHelper.isEmpty(allAttachmentName)) {
					kdtEntires.getCell(i, "attachment").setValue(allAttachmentName);
				} else {
					kdtEntires.getCell(i, "attachment").setValue(null);
				}
			}
		}
    }

	/**
	 * 获取合约框架所有附件名称字符串，名称与乐称以";"相隔
	 * 
	 * @param boID
	 * @return
	 */
	private StringBuffer getAllAttachmentName(String boID) {
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" select * from T_BAS_Attachment at");
		fdcBuilder.appendSql(" join T_BAS_BoAttchAsso boAt on at.FID=boAt.FAttachmentID");
		fdcBuilder.appendSql(" where boAt.FBoID = '" + boID + "'");
		System.out.println("sql:" + fdcBuilder.getSql().toString());
		StringBuffer sb = new StringBuffer();
		IRowSet rs = null;
		try {
			rs = fdcBuilder.executeQuery();
			while (rs.next()) {
				if (FDCHelper.isEmpty(rs.getString("FSimpleName"))) {
					sb.append(rs.getString("FName_l2") + ",");
				} else {
					if (rs.isLast()) {
						sb.append(rs.getString("FName_l2") + "." + rs.getString("FSimpleName"));
					} else {
						sb.append(rs.getString("FName_l2") + "." + rs.getString("FSimpleName") + ",");
					}
				}
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return sb;
	}
	private void setMouseClick() {
		this.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
				if(!arg0.getComponent().equals(kdtEntires)){
					setKDTableLostFocus();
				}
			}
    	});
    	
    	Component[] com = this.getComponents();
    	for(int i = 0 ; i < com.length ; i++){
    		if(!com[i].equals(kdtEntires)){
    			com[i].addMouseListener(new MouseListener(){
    				public void mouseClicked(MouseEvent arg0) {
    				}
    				public void mouseEntered(MouseEvent arg0) {
    				}
    				public void mouseExited(MouseEvent arg0) {
    				}
    				public void mousePressed(MouseEvent arg0) {
    				}
    				public void mouseReleased(MouseEvent arg0) {
    					if(!arg0.getComponent().equals(kdtEntires)){
    						setKDTableLostFocus();
    					}
    				}
    	    	});
    		}
    	}
    	
    	this.txtName.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
				if(!arg0.getComponent().equals(kdtEntires)){
					setKDTableLostFocus();
				}
			}
    	});
    	
    	this.txtRemark.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
				if(!arg0.getComponent().equals(kdtEntires)){
					setKDTableLostFocus();
				}
			}
    	});
	}
	
	private void setKDTableLostFocus(){
		if(this.kdtEntires.getRowCount()!=0){
			kdtEntires.getEditManager().stopEditing();
			kdtEntires.getSelectManager().remove();
			kdtEntires.getSelectManager().setActiveRowIndex(-1);
			btnInsertLine.setEnabled(false);
			btnRemoveLines.setEnabled(false);
			btnDetails.setEnabled(false);
		}
	}
    
	/**
	 * 初始化分录
	 */
	private void initTable() {
		// 合框框架模版备注限制为255字符
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(new KDTextField());
		KDTextField kdtf = (KDTextField) cellEditor.getComponent();
		kdtf.setMaxLength(1024);
		kdtEntires.getColumn(REMARK).setEditor(cellEditor);

//		kdtEntires.getColumn("sortNumber").getStyleAttributes().setHided(false);
//		kdtEntires.getColumn(HEADNUMBER).getStyleAttributes().setHided(false);
//		kdtEntires.getColumn("longName").getStyleAttributes().setHided(false);
		
		KDBizPromptBox f7Box = new KDBizPromptBox();
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setSelector(new ContractTypePromptSelector(this));
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtEntires.getColumn("contractType").setEditor(f7Editor);
	}

    /**
     * 在面签中添加新增、插入、删除、详细信息按钮
     */
    private void setSmallButton(){
    	btnRefresh.setEnabled(true);
    	menuItemRefresh.setEnabled(true);
    	btnRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
    	
    	btnAddnewLine = new KDWorkButton();
    	btnInsertLine = new KDWorkButton();
    	btnRemoveLines = new KDWorkButton();
    	btnDetails = new KDWorkButton();
    	
    	btnDetails.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    actionDetails_actionPerformed(e);
                }
                catch (Exception e1){
                	logger.error("detials" , e1);
                }
            }});
    	
    	btnAddnewLine.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                	actionAddnewLine_actionPerformed(e);
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }});
    	
    	btnInsertLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					actionInsertLine_actionPerformed(arg0);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
    		
    	});
    	
    	btnRemoveLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRemoveLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
    	
//    	setButtonStyle(btnAddnewLine,"新增行","imgTbtn_addline");
//    	setButtonStyle(btnRemoveLines,"删除行","imgTbtn_deleteline");
//    	setButtonStyle(btnInsertLine,"插入行","imgTbtn_insert");
//    	setButtonStyle(btnDetails,"详细信息","imgTbtn_particular");
    	
    	setButtonStyle(btnInsertLine,"新增框架合约(同级)","imgTbtn_insert");
    	setButtonStyle(btnAddnewLine,"新增下级框架合约","imgTbtn_addline");
    	setButtonStyle(btnRemoveLines,"删除行","imgTbtn_deleteline");
    	setButtonStyle(btnDetails,"详细信息","imgTbtn_particular");
    }

    private void setButtonStyle(KDWorkButton button , String text , String icon){
    	button.setText(text);
    	button.setToolTipText(text);
    	button.setVisible(true);
    	button.setIcon(EASResource.getIcon(icon));
    	pnlContract.addButton(button);
    }
    
    private void setButtionEnable(boolean isEnable){
		btnAddnewLine.setEnabled(isEnable);
		btnInsertLine.setEnabled(isEnable);
		btnRemoveLines.setEnabled(isEnable);
		btnDetails.setEnabled(isEnable);
    }
    
    private void setSmallBtnEnable(){
    	if(OprtState.VIEW.equals(getOprtState())){
    		setButtionEnable(false);
    		if(kdtEntires.getSelectManager().getActiveRowIndex() < 0 || kdtEntires.getRowCount() <= 0){
    			btnDetails.setEnabled(false);
    		}else{
    			btnDetails.setEnabled(true);
    		}
    	}else{
    		btnInsertLine.setEnabled(true);
    		if(kdtEntires.getSelectManager().getActiveRowIndex() < 0 || kdtEntires.getRowCount() <= 0){
    			btnRemoveLines.setEnabled(false);
    			btnDetails.setEnabled(false);
    			btnAddnewLine.setEnabled(false);
    		}else{
    			btnAddnewLine.setEnabled(true);
    			btnRemoveLines.setEnabled(true);
    			btnDetails.setEnabled(true);
    		}
    	}
    	
		String cuID = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		if (!cuID.equals(OrgConstants.DEF_CU_ID)) {
			btnEdit.setEnabled(false);
			menuItemEdit.setEnabled(false);
		}
    }
    
    public void onShow() throws Exception{
		super.onShow();
		kdtEntires.addKDTMouseListener(new KDTSortManager(kdtEntires));
		kdtEntires.getSortMange().setSortAuto(false);
	}
    
    private void setCellEditorForTable(){
		for (int i = 0; i < kdtEntires.getRowCount(); i++) {
			KDTextField txtLongNumber = new KDTextField();
			String txt = "";
			Object longNumber = kdtEntires.getCell(i, LONGNUMBER).getValue();
			Object subNumber = kdtEntires.getCell(i, HEADNUMBER).getValue();
			int level = new Integer(kdtEntires.getCell(i, "level").getValue().toString()).intValue();
			if(longNumber == null || longNumber.toString().trim().length() == 0){
    			if(subNumber != null && subNumber.toString().trim().length() > 0){
    				txt = subNumber.toString().trim()+".";
    			}
			}else{
				txt = longNumber.toString().trim();
			}
			if(level > 1){
				if(subNumber==null){
					subNumber=longNumber.toString().substring(0,longNumber.toString().lastIndexOf("."));
					kdtEntires.getCell(i, HEADNUMBER).setValue(subNumber);
				}
				LimitedTextDocument document = new LimitedTextDocument(subNumber.toString().trim()+"." , true);
				txtLongNumber.setMaxLength(80);
				txtLongNumber.setDocument(document);
				txtLongNumber.setText(txt);
				document.setIsOnload(false);
			}else{
				LimitedTextDocument document = new LimitedTextDocument("");
				txtLongNumber.setDocument(document);
				document.setIsAutoUpdate(true);
				document.setIsOnload(true);
				txtLongNumber.setText(txt);
				document.setIsAutoUpdate(false);
				document.setIsOnload(false);
			}
			
			KDTDefaultCellEditor cellEditorNumber = new KDTDefaultCellEditor(txtLongNumber);
			kdtEntires.getCell(i , LONGNUMBER).setEditor(cellEditorNumber);
		}
    }
    
    /**
     * 加载时生成树形
     */
    private void createTree(){
		int maxLevel = 0;
		int[] levelArray = new int[kdtEntires.getRowCount()];
		
		for (int i = 0; i < kdtEntires.getRowCount(); i++) {
			IRow row = kdtEntires.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);
		}
		
		for (int i = 0; i < kdtEntires.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
		kdtEntires.getTreeColumn().setDepth(maxLevel);
		
		kdtEntires.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    }
    
    
    /**
     * 点击新增下级按钮
     * @param e
     * @throws Exception
     */
    public void actionAddnewLine_actionPerformed(ActionEvent e) throws Exception{
    	int rowIndex = this.kdtEntires.getSelectManager().getActiveRowIndex();
    	int rowCount = kdtEntires.getRowCount();
    	int row = -1;
    	if(rowIndex < 0){
    		create.addLine(kdtEntires, 1);
    		if(rowCount == 0)
    			row = 0;
    		else
    			row = rowCount;
    	}else{
        	this.storeFields();
        	
    		Object o = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
    		Object head = kdtEntires.getCell(rowIndex, HEADNUMBER).getValue();
    		Object headlevel = kdtEntires.getCell(rowIndex, "level").getValue();
    		Object name = kdtEntires.getCell(rowIndex, "name").getValue();
    		Object longName = kdtEntires.getCell(rowIndex, "longName").getValue();
    		
    		ProgrammingTemplateEntireInfo headObject = (ProgrammingTemplateEntireInfo)kdtEntires.getRow(rowIndex).getUserObject();
    		headObject.setContractType((ContractTypeInfo)kdtEntires.getCell(rowIndex, "contractType").getValue());
    	
    		int newLevel = 0;
    		if(o == null || o.toString().trim().length() == 0){
    			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架合约编码不能为空！");
    			return;
    		}else if((o.toString().trim()+".").length() >= 80){
    			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架合约编码超长\n请修改后再新增子级框架合约！");
    			return;
    		}else if(name == null || StringUtils.isEmpty(name.toString())){
    			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架合约名称不能为空！");
    			return;
//    		}else if(head == null){
//    			int level = new Integer(headlevel.toString()).intValue();
//    			if(level == 1){
//    				row = getInsertRowIndex(o , rowIndex , rowCount);
//    				create.insertLine(kdtEntires , row , level + 1 , headObject);
//    				kdtEntires.getCell(row, HEADNUMBER).setValue(o);
//    				if(name != null)
//    					kdtEntires.getCell(row, "longName").setValue(name.toString().trim()+".");
//    			}
    		}else{
    			String ln = o.toString();
//    			if(ln.length() == (head.toString().length() + 1)){
//    				MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架合约编码不能为空！");
//        			return;
//    			}
    			row = getInsertRowIndex(o , rowIndex , rowCount);
    			newLevel =  new Integer(headlevel.toString()).intValue() + 1;
    			//如果已经有下级了则不clone上级的数据，
    			//如果 （插入行 = 当前选择行 + 1） 则 需要clone选择行的数据到插入行。否则不做任何处理 
    			if(rowIndex +1 == row){
    				create.insertLine(kdtEntires, row , newLevel, headObject);//clone
    			}else{
    				create.insertSameLine(kdtEntires , row , newLevel, headObject);
    			}
    			kdtEntires.getCell(row, HEADNUMBER).setValue(o);
    			if(longName != null)
    				kdtEntires.getCell(row, "longName").setValue(longName.toString().trim()+".");
    		}
    	}
    	//调用一下
    	loadCostAccountAddNew();
		
    	KDTextField txtLongNumber = new KDTextField();
		LimitedTextDocument document = new LimitedTextDocument("");
		txtLongNumber.setMaxLength(80);
		txtLongNumber.setDocument(document);
		KDTDefaultCellEditor cellEditorNumber = new KDTDefaultCellEditor(txtLongNumber);
		kdtEntires.getCell(row, LONGNUMBER).setEditor(cellEditorNumber);
    	createTree();
    	setSmallBtnEnable();
    }
    
    /**
     * 新增同级按钮
     * @param e
     * @throws Exception
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception{
    	int rowIndex = kdtEntires.getSelectManager().getActiveRowIndex();
    	int rowCount = kdtEntires.getRowCount();
    	int row = -1;
    	if(rowIndex < 0){
    		//下标为0则正常新增
    		create.addLine(kdtEntires, 1);
    		if(rowCount == 0)
    			row = 0;
    		else
    			row = rowCount;
    	}else{
    		this.storeFields();
    		
    		Object o = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
    		Object head = kdtEntires.getCell(rowIndex, HEADNUMBER).getValue();
    		Object headlevel = kdtEntires.getCell(rowIndex, "level").getValue();
    		Object name = kdtEntires.getCell(rowIndex, "name").getValue();
    		Object longName = kdtEntires.getCell(rowIndex, "longName").getValue();
    		
    		ProgrammingTemplateEntireInfo headObject = (ProgrammingTemplateEntireInfo)kdtEntires.getRow(rowIndex).getUserObject();
    		int activelevel = new Integer(headlevel.toString()).intValue();
    		if(activelevel == 1 ){ //如果是一级则增加一行到最后
    			create.insertLine(kdtEntires , rowCount , 1 , null);
    			row = rowCount;
    		}else if(o == null || o.toString().trim().length() == 0){//新增时判断数据是否合法
    			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架合约编码不能为空！");
    			return;
    		}else if((o.toString().trim()+".").length() >= 80){
    			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架合约编码过长\n请修改后再新增子级框架合约！");
    			return;
    		}else if(name == null || StringUtils.isEmpty(name.toString())){
    			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架合约名称不能为空！");
    			return;
    		} else{
    			String ln = o.toString();
    			if(ln.length() == (head.toString().length() + 1)){
    				MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架合约编码不能为空！");
        			return;
    			}
    			row = getInsertRowIndexSameLevel(rowIndex , rowCount);
    			create.insertSameLine(kdtEntires , row , activelevel , headObject.getHead());
    			//调用一下
    			loadCostAccountTabData();
    			kdtEntires.getCell(row, HEADNUMBER).setValue(head);
    			if(longName != null)
    				kdtEntires.getCell(row, "longName").setValue(longName.toString().trim()+".");
    		}
    	}
    	//设置编码列的编辑格式、限制上级编码不可修改
    	KDTextField txtLongNumber = new KDTextField();
		LimitedTextDocument document = new LimitedTextDocument("");
		txtLongNumber.setMaxLength(80);
		txtLongNumber.setDocument(document);
		KDTDefaultCellEditor cellEditorNumber = new KDTDefaultCellEditor(txtLongNumber);
		kdtEntires.getCell(row, LONGNUMBER).setEditor(cellEditorNumber);
    	createTree();
    	setSmallBtnEnable();
//    	int rowIndex = this.kdtEntires.getSelectManager().getActiveRowIndex();
//    	Object o = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
//    	Object name = kdtEntires.getCell(rowIndex, "name").getValue();
//    	if(o == null || o.toString().trim().length() == 0){
//			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架合约编码不能为空！");
//			return;
//		}else if(name == null || StringUtils.isEmpty(name.toString())){
//			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架合约名称不能为空！");
//			return;
//		}
//    	
//    	Object headNumber = kdtEntires.getCell(rowIndex, HEADNUMBER).getValue();
//		Object level = kdtEntires.getCell(rowIndex, "level").getValue();
//		ProgrammingTemplateEntireInfo headObject = (ProgrammingTemplateEntireInfo)kdtEntires.getRow(rowIndex).getUserObject();
//    	create.insertLine(kdtEntires , rowIndex , new Integer(level.toString()).intValue() , headObject.getHead());
//		kdtEntires.getCell(rowIndex, HEADNUMBER).setValue(headNumber);
//		if(headObject.getHead() != null && headObject.getHead().getDisplayName() != null){
//			kdtEntires.getCell(rowIndex, "longName").setValue(headObject.getHead().getDisplayName().toString().trim()+".");
//		}
//		KDTextField txtLongNumber = new KDTextField();
//		LimitedTextDocument document = new LimitedTextDocument("");
//		txtLongNumber.setDocument(document);
//		KDTDefaultCellEditor cellEditorNumber = new KDTDefaultCellEditor(txtLongNumber);
//		kdtEntires.getCell(rowIndex, LONGNUMBER).setEditor(cellEditorNumber);
//		createTree();
    }

    /**
     * 获取要新增行的位置（下标）新增同级的时候使用
     * @param o
     * @param rowIndex
     * @param rowCount
     * @return
     */
    protected int getInsertRowIndexSameLevel(int rowIndex , int rowCount){
    	int row = 0;
    	if(rowIndex + 1 == rowCount){
    		return rowIndex+1;
    	}
		for(int i = rowIndex ; i < rowCount ; ++i){//寻找本级的最后一列
			int level = new Integer(kdtEntires.getCell(rowIndex, "level").getValue().toString()).intValue();
			int level_2 = new Integer(kdtEntires.getCell(i, "level").getValue().toString()).intValue();
			if (level_2 != level) {
				return i;
			}
		}
		return row == 0 ? rowCount : row;
    
    }
    /**
     * 点击删除行按钮
     * @param e
     * @throws Exception
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception{
    	int rowIndex = this.kdtEntires.getSelectManager().getActiveRowIndex();
    	Object longNumber = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
    	ArrayList list = new ArrayList();
    	if(longNumber != null){
			list.add(new Integer(rowIndex));
			getSublevel(longNumber.toString() , rowIndex , list);
    	}
		if(list.size() > 1){
			if(MsgBox.OK == MsgBox.showConfirm2New(null,"您当前删除的父节点”"+longNumber.toString()+"”下还有其他的框架合约，确定要一起删除吗？")){
				create.removeLine(kdtEntires, list);
			}
		}else{
			if(MsgBox.OK == MsgBox.showConfirm2New(null,"是否确认删除数据？")){
		    	create.removeLine(kdtEntires,rowIndex);
			}
	    	createTree();
	    	
	    	setSmallBtnEnable();
    	}
    }
    
    private void getSublevel(String longNumber , int rowIndex , ArrayList list){
    	int rowCount = kdtEntires.getRowCount();
    	for(int i = rowIndex+1 ; i < rowCount ; i++){
			Object headNumber = kdtEntires.getCell(i, HEADNUMBER).getValue();
			if(headNumber != null && headNumber.toString() != null){
				if(headNumber.toString().startsWith(longNumber)){
					list.add(new Integer(i));
				}else{
					break;
				}
			}
    	}
    }
    
    /**
     * 点击详细信息按钮
     * @param e
     * @throws Exception
     */
    public void actionDetails_actionPerformed(ActionEvent e) throws Exception
	{
		int rowIndex = kdtEntires.getSelectManager().getActiveRowIndex();
		Object oldValue = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
		int level = new Integer(kdtEntires.getCell(rowIndex, "level").getValue().toString()).intValue();
		//校验是否有下级，如果有是没有明细的
		if(rowIndex+1 <kdtEntires.getRowCount()){
			Object nextHeadNumber = kdtEntires.getCell(rowIndex+1, HEADNUMBER).getValue();
			if(oldValue.equals(nextHeadNumber)){
				FDCMsgBox.showInfo("非明细目录没有详细信息！");
				return;
			}
		}
    	UIContext uiContext = new UIContext(this);
    	IUIWindow uiWindow = null;
		ProgrammingTemplateEntireCollection pteCollection = getPTECollection();
		uiContext.put("pteCollection", pteCollection);
    	ProgrammingTemplateEntireInfo rowObject = (ProgrammingTemplateEntireInfo)kdtEntires.getRow(rowIndex).getUserObject();
		setContractToEditData(rowIndex, rowObject);
    	uiContext.put("rowObject", rowObject);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PTEEditUI.class.getName(), uiContext, null, oprtState);
    	uiWindow.show();
    	//绑定数据到分录上
		dataBinder.loadLineFields(kdtEntires, kdtEntires.getRow(rowIndex), rowObject);
		kdtEntires.getRow(rowIndex).setUserObject(rowObject);
		if(rowObject.getPteCost().size() > 0)
			kdtEntires.getCell(rowIndex, "costAccount").setValue(rowObject);
		setEntriesNameCol(rowIndex, level);
		Object newValue = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
		if (oldValue != null && newValue != null) {
			if (oldValue.equals(newValue)) {
				return;
			}
		}
		setEntriesNumberCol(rowIndex, level);
	}
    
    protected int getInsertRowIndex(Object o , int rowIndex , int rowCount){
    	int row = 0;
    	String longNumber = o.toString();
    	if(rowIndex + 1 == rowCount){
    		return rowIndex+1;
    	}
    	
		for(int i = rowIndex+1 ; i < rowCount ; i++){
			int level = new Integer(kdtEntires.getCell(rowIndex, "level").getValue().toString()).intValue();
			int level_2 = new Integer(kdtEntires.getCell(i, "level").getValue().toString()).intValue();
			if (level_2 == level || level_2 < level) {
				return i;
			}
			Object n = kdtEntires.getCell(i, LONGNUMBER).getValue();
			if(n != null && n.toString() != null){
				if(!n.toString().startsWith(longNumber)){
					row = i;
					break;
				}
//				else if(!longNumber.equals(headNumber.toString())){
//						row = i;
//				}
			}else{
				return i+1;
			}
			
			if(rowIndex + 2 == rowCount){
				return rowCount;
			}
		}
		return row == 0 ? rowCount : row;
    }
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void kdtEntires_editStarting(KDTEditEvent e) throws Exception {
    	super.kdtEntires_editStarting(e);
    	int rowIndex = e.getRowIndex();
    	if(e.getColIndex() == kdtEntires.getColumnIndex(LONGNUMBER)){
    		Object longNumber = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
			if(longNumber != null && longNumber.toString().trim().length() > 80){
				MsgBox.showInfo("分录第 "+(e.getRowIndex()+1)+" 行，框架合约编码超长\n请修改上级编码后再进行编辑！");
				kdtEntires.getEditManager().cancelEditing();
				e.setCancel(true);
			}
    	}
    }
    
    protected void kdtEntires_editStarted(KDTEditEvent e) throws Exception {
    	int rowIndex = kdtEntires.getSelectManager().getActiveRowIndex();
    	if(e.getColIndex() == kdtEntires.getColumnIndex(LONGNUMBER)){
    		longNumberEditStarted(rowIndex);
    	}
    }

	private void longNumberEditStarted(int rowIndex) {
		ICellEditor editor = kdtEntires.getCell(rowIndex, LONGNUMBER).getEditor();
		if(editor != null){
			if(editor instanceof KDTDefaultCellEditor){
				int level = new Integer(kdtEntires.getCell(rowIndex, "level").getValue().toString()).intValue();
				KDTDefaultCellEditor de = (KDTDefaultCellEditor)editor;
				KDTextField txtLongNumber = (KDTextField) de.getComponent();
				LimitedTextDocument doc = (LimitedTextDocument)txtLongNumber.getDocument();
				txtLongNumber.setMaxLength(80);
				String txt = "";
				Object longNumber = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
				Object subNumber = kdtEntires.getCell(rowIndex, HEADNUMBER).getValue();
				if(longNumber == null || longNumber.toString().trim().length() == 0){
					if(subNumber != null && subNumber.toString().trim().length() > 0){
						txt = subNumber.toString().trim()+".";
						kdtEntires.getCell(rowIndex, LONGNUMBER).setValue(txt);
					}
				}
				else{
					txt = longNumber.toString().trim();
				}
				if(level > 1){
					if(subNumber==null){
						subNumber=longNumber.toString().substring(0,longNumber.toString().lastIndexOf("."));
					}
					doc.setLimitedText(subNumber.toString().trim()+".");
					doc.setIsOnload(true);
					doc.setIsAutoUpdate(true);
					txtLongNumber.setText(txt);
					doc.setIsOnload(false);
					doc.setIsAutoUpdate(false);
				}else{
					doc.setIsAutoUpdate(true);
					doc.setIsOnload(true);
					txtLongNumber.setText(txt);
					doc.setIsAutoUpdate(false);
					doc.setIsOnload(false);
				}
			}
		}
	}
    
    protected void kdtEntires_activeCellChanged(KDTActiveCellEvent e) throws Exception {
    	setSmallBtnEnable();
    }
    
    protected void setFieldsNull(AbstractObjectValue newData) {
    	ProgrammingTemplateInfo temp = (ProgrammingTemplateInfo)newData;
    	temp.setId(BOSUuid.create(temp.getBOSType()));
    	this.txtName.setText("复件 "+temp.getName());
    	editData.setName("复件 "+temp.getName());
    	txtNumber.setText(null);
    	editData.setNumber(null);
    	ProgrammingTemplateEntireCollection col = temp.getEntires();
    	for(int i = 0 ; i < col.size() ; i++){
    		ProgrammingTemplateEntireInfo info = col.get(i);
    		info.setAttachment(null);
    		PTECostCollection  costCol = info.getPteCost();
    		for(int j = 0 ; j < costCol.size() ; j++){
    			PTECostInfo pteInfo = costCol.get(j);
    			if(pteInfo.getCostAccount() != null){
    				CostAccountInfo costAccountInfo = pteInfo.getCostAccount();
    				if(!costAccountInfo.isIsEnabled()){
    					pteInfo.setCostAccount(null);
    					costCol.remove(pteInfo);
    				}
    			}
    		}
    	}
    }
    
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
    	dataBinder.storeFields();
    	setKDTableLostFocus();
    	createTree();
    }
    
    public void verifyDataBySave() throws Exception {
		String name = txtName.getText();
		if (name == null || name.trim().equals("")) {
			throw new ProgrammingException(ProgrammingException.NAMENOTNULL);
		}
		
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name, name, CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (editData.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, editData.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
			filter.setMaskString("#0 and #1");
		}

		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
		if (ProgrammingTemplateFactory.getRemoteInstance().exists(filter)) {
			throw new ProgrammingException(ProgrammingException.CHECKDUPLICATED, new Object[] { name });
		}
	        
    	int rowCount = kdtEntires.getRowCount();
    	for(int i = 0 ; i < rowCount ; i++){
    		Object number = kdtEntires.getCell(i, "longNumber").getValue();
    		Object head = kdtEntires.getCell(i, HEADNUMBER).getValue();
    		Object longName = kdtEntires.getCell(i, "longName").getValue();
    		if(number == null || number.toString().trim() == null){
	    		throw new ProgrammingException(ProgrammingException.NUMBER_NULL , new Object[]{new Integer(i+1)});
        	}
    		Object level = kdtEntires.getCell(i, "level").getValue();
    		int level_int = new Integer(level.toString()).intValue();
			if(level_int != 1){
	    		String ln = number.toString();
	    		if(ln.length() == (head.toString().length() + 1)){
	    			throw new ProgrammingException(ProgrammingException.NUMBER_NULL , new Object[]{new Integer(i+1)});
				}
			}
			
			String longNumber = number.toString().trim();
			if(longNumber.length() > 80){
				throw new EASBizException(new NumericExceptionSubItem("1", "分录第"+(i+1)+"行，编码超长，请重新输入！"));
			}
			
    		Object proName = kdtEntires.getCell(i, "name").getValue();
    		if(proName == null || proName.toString().trim() == null){
    			throw new ProgrammingException(ProgrammingException.NAME_NULL , new Object[]{new Integer(i+1)});
        	}
    		
    		if(proName != null && proName.toString().trim().length() > 80){
    			throw new EASBizException(new NumericExceptionSubItem("1", "分录第"+(i+1)+"行，框架合约名称超长！"));
        	}
    		
    		if(longName != null && !StringUtils.isEmpty(longName.toString())){
    			if(longName.toString().length() > 255){
    				throw new EASBizException(new NumericExceptionSubItem("1", "分录第"+(i+1)+"行，框架合约长名称超长\n请修改框架合约名称数据！"));
    			}
    		}
    		
    		String lnumber = number.toString();
    		String name_pro = proName.toString().trim();
    		
    		for(int j = 0 ; j < rowCount ; j++){
    			if(j == i)
    				continue;
    			
    			Object number_2 = kdtEntires.getCell(j, "longNumber").getValue();
        		Object proName_2 = kdtEntires.getCell(j, "name").getValue();
        		
        		if(number_2 != null && number_2.toString().trim().length() > 0){
        			if(lnumber.equals(number_2.toString().trim())){
        				throw new ProgrammingException(ProgrammingException.NUMBER_REPEAT , new Object[]{new Integer(i+1) , new Integer(j+1) , "编码"});
        			}
        		}
        		
        		if(proName_2 != null && proName_2.toString().trim().length() > 0){
        			if(name_pro.equals(proName_2.toString().trim())){
        				throw new ProgrammingException(ProgrammingException.NUMBER_REPEAT , new Object[]{new Integer(i+1) , new Integer(j+1) , "名称"});
        			}
        		}
    		}
    	}
    }
    
    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception{
    	kdtEntires.getEditManager().editingStopped();
    	verifyDataBySave();
    	int rowCount = this.kdtEntires.getRowCount();
		for(int i = 0 ; i < rowCount ; i++){
			IRow row = kdtEntires.getRow(i);
			Object name =  row.getCell("name").getValue();
			if(name != null && name.toString().trim().length() > 0){
				row.getCell("name").setValue(name.toString().trim());
			}
			row.getCell("sortNumber").setValue(new Integer(i));
			ProgrammingTemplateEntireInfo rowObject = (ProgrammingTemplateEntireInfo)kdtEntires.getRow(i).getUserObject();
    		setCostAccountToEditData(i,rowObject);
			if(rowObject.getId() != null){
	    		FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("parent" , rowObject.getId().toString() , CompareType.EQUALS));
				PTECostFactory.getRemoteInstance().delete(filter);
				for (int j = 0; j < rowObject.getPteCost().size(); j++) {
					PTECostInfo info = rowObject.getPteCost().get(j);
					info.setParent(rowObject);
					PTECostFactory.getRemoteInstance().save(info);
				}
				rowObject.getPteCost().clear();
				PTEEnonomyFactory.getRemoteInstance().delete(filter);
				for (int j = 0; j < rowObject.getPteEnonomy().size(); j++) {
					PTEEnonomyInfo info = rowObject.getPteEnonomy().get(j);
					info.setParent(rowObject);
					PTEEnonomyFactory.getRemoteInstance().save(info);
				}
				rowObject.getPteEnonomy().clear();
			}
		}
    	
    	if(txtNumber.getText() == null || "".equals(txtNumber.getText()))
    		txtNumber.setText(getDateString());
    	super.actionSubmit_actionPerformed(e);
		handleOldData();
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionEdit_actionPerformed(e);
    	setSmallBtnEnable();
    	setCellEditorForTable();
    	handleOldData();
    }
    
    protected void kdtEntires_editStopped(KDTEditEvent e) throws Exception {
    	//设置编码
    	Object oldValue = e.getOldValue();
		Object newValue = e.getValue();
		if(oldValue == null && newValue == null){
			return;
		}
		
		final int rowIndex = kdtEntires.getSelectManager().getActiveRowIndex();
		int level = new Integer(kdtEntires.getCell(rowIndex, "level").getValue().toString()).intValue();
    	if(e.getColIndex() == kdtEntires.getColumnIndex(LONGNUMBER)){
    		if(oldValue != null && newValue != null){
    			if(oldValue.equals(newValue)){
    				return;
    			}
    		}
    		setEntriesNumberCol(rowIndex, level);
    	}
    	
    	if(e.getColIndex() == kdtEntires.getColumnIndex("name")){
    		setEntriesNameCol(rowIndex, level);
    	}
    	
    	if(e.getColIndex() == kdtEntires.getColumnIndex("costAccount")){
    		Object cost = kdtEntires.getCell(rowIndex, "costAccount").getValue();
    		ProgrammingTemplateEntireInfo rowObject = (ProgrammingTemplateEntireInfo)kdtEntires.getRow(rowIndex).getUserObject();
    		if(cost == null){
    			rowObject.getPteCost().clear();
    		}
    		setCostAccountToEditData(rowIndex,rowObject);
    	}
    	if (e.getColIndex() == kdtEntires.getColumnIndex("contractType")) {
			if(rowIndex+1 <kdtEntires.getRowCount()){
				Object oldLongNumber = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
				if(oldLongNumber == null){
					FDCMsgBox.showInfo("请填写编码！");
					kdtEntires.getRow(rowIndex).getCell("contractType").setValue(null);
					return;
				}
				Object nextHeadNumber = kdtEntires.getCell(rowIndex+1, HEADNUMBER).getValue();
				if(oldLongNumber.equals(nextHeadNumber)){
					FDCMsgBox.showInfo("非明细目录没有合同类型！");
					kdtEntires.getRow(rowIndex).getCell("contractType").setValue(null);
					return;
				}
			}
		}
    }

	private void setEntriesNumberCol(int rowIndex, int level) {
		Object longNumber = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
		if(longNumber != null && longNumber.toString().trim().length() > 0){
			String lnumber = longNumber.toString();
			if(level == 1){
				kdtEntires.getCell(rowIndex, "number").setValue(lnumber);
			}else{
				String number = lnumber.substring(lnumber.lastIndexOf(".")+1 , lnumber.length());
				kdtEntires.getCell(rowIndex, "number").setValue(number);
			}
			for(int i = rowIndex+1 ; i < kdtEntires.getRowCount() ; i++){
				Object headNumber = kdtEntires.getCell(i, HEADNUMBER).getValue();
				Object longNumber_2 = kdtEntires.getCell(i, LONGNUMBER).getValue();
				int level_2 = new Integer(kdtEntires.getCell(i, "level").getValue().toString()).intValue();
				if (level_2 == level || level_2 < level) {
					break;
				}
				String[] editString = lnumber.split("\\.");
				if(longNumber_2 != null && longNumber_2.toString().trim().length() > 0){
					String hNumber_2 = headNumber.toString();
					String lNumber_2 = longNumber_2.toString();
					String[] newL = lNumber_2.split("\\.");
					String[] newH = hNumber_2.split("\\.");
					for(int j = 0 ; j < editString.length ; j++){
						if(newL[j] != null && newL[j].length() > 0)
							newL[j] = editString[j];
						if(newH[j] != null && newH[j].length() > 0)
							newH[j] = editString[j];
					}
					StringBuffer str = new StringBuffer();
					for(int j = 0 ; j < newL.length ; j++){
						str.append(newL[j]).append(".");
					}
					if(newL.length < level_2)
						str.append(".");
					StringBuffer str2 = new StringBuffer();
					for(int j = 0 ; j < newH.length ; j++){
						str2.append(newH[j]).append(".");
					}
					setkdtEntriesNumber(i , str.substring(0, str.length() - 1) , str2.substring(0, str2.length() - 1));
				}
			}
		}
	}

	private void setEntriesNameCol(int rowIndex, int level) {
		Object name =  kdtEntires.getCell(rowIndex , "name").getValue();
		if(name != null && name.toString().trim().length() > 0){
			String nameStr = name.toString().trim();
			String blank = setNameIndent(level);
			kdtEntires.getCell(rowIndex ,"name").setValue(blank+nameStr);
			if(level == 1){
				kdtEntires.getCell(rowIndex ,"longName").setValue(nameStr);
			}else{
				Object lo = kdtEntires.getCell(rowIndex ,"longName").getValue();
				String displayName = lo == null ? "" : lo.toString();
				String ln = displayName.substring(0 , displayName.lastIndexOf("."))+".";
				kdtEntires.getCell(rowIndex ,"longName").setValue(ln + nameStr);
			}
			
			Object lo = kdtEntires.getCell(rowIndex ,"longName").getValue();
			String displayName = lo == null ? "" : lo.toString();
			if(level == 1){
				displayName = displayName+".";
			}
			String [] l = displayName.split("\\.");
			for(int i = rowIndex+1 ; i < kdtEntires.getRowCount() ; i++){
				int level_2 = new Integer(kdtEntires.getCell(i, "level").getValue().toString()).intValue();
				if (level_2 == level || level_2 < level) {
					break;
				}
				Object l2 = kdtEntires.getCell(i ,"longName").getValue();
				if(l2 == null)
					return;
				String l3[] = l2.toString().split("\\.");
				for(int j = 0 ; j < l.length ; j++){
					if(l3[j] != null && l3[j].length() > 0){
						l3[j] = l[j];
					}
				}
				StringBuffer str = new StringBuffer();
				for(int j = 0 ; j < l3.length ; j++){
					str.append(l3[j]).append(".");
				}
				Object n2 = kdtEntires.getCell(i ,"name").getValue();
				if(n2 == null){
					str.append(".");
				}
				kdtEntires.getCell(i ,"longName").setValue(str.substring(0, str.length() - 1));
				displayName = null;
			}
		}
	}
    
    private void setkdtEntriesNumber(int i , String lnumber , String hNumber){
    	kdtEntires.getCell(i, HEADNUMBER).setValue(hNumber);
		kdtEntires.getCell(i, LONGNUMBER).setValue(lnumber);
		
		ICellEditor editor = kdtEntires.getCell(i, LONGNUMBER).getEditor();
		if(editor != null){
			if(editor instanceof KDTDefaultCellEditor){
	    		KDTDefaultCellEditor de = (KDTDefaultCellEditor)editor;
    			KDTextField txtLongNumber = (KDTextField) de.getComponent();
    			LimitedTextDocument doc = (LimitedTextDocument)txtLongNumber.getDocument();
    			doc.setIsAutoUpdate(true);
    			doc.setIsOnload(true);
	    		txtLongNumber.setText(lnumber);
	    		doc.setIsAutoUpdate(false);
	    		doc.setIsOnload(false);
			}
		}
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo objectValue = new com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
        return objectValue;
    }
    
    private String getDateString(){
    	Calendar cal = Calendar.getInstance();
    	Timestamp ts   =  new Timestamp(cal.getTimeInMillis());
    	Date bizDate = new Date(ts.getTime());
    	return bizDate.toString();
    }
    
    /**
     * 加载成本科目F7
     */
    private void costAccountF7(){
    	CostAccountPromptBox selector=new CostAccountPromptBox(this);
		KDBizPromptBox prmtCostAccount=new KDBizPromptBox(){
			protected String valueToString(Object o) {
				StringBuffer str = new StringBuffer();
				if (o != null && o instanceof CostAccountInfo) {
					str.append(((CostAccountInfo)o).getLongNumber().replace('!', '.'));
				}
				if (o != null && o instanceof CostAccountInfo []) {
					CostAccountInfo [] costInfo = (CostAccountInfo [])o;
					for(int i = 0 ; i < costInfo.length ; i++){
						str.append(costInfo[i].getLongNumber().replace('!', '.')).append(",");
					}
				}
				if (o instanceof ProgrammingTemplateEntireInfo) {
					return	getProF7DisplayPercent(o);
				}
				if(str != null && str.toString().length() > 1)
					return str.toString().substring(0, str.toString().length() -1);
				return null;
			}
		};
		prmtCostAccount.setSelector(selector);
		prmtCostAccount.setEnabledMultiSelection(true);
		prmtCostAccount.setDisplayFormat("$problem$");
		prmtCostAccount.setEditFormat("$problem$");
		prmtCostAccount.setCommitFormat("$problem$");
		EntityViewInfo entityView = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",OrgConstants.DEF_CU_ID,CompareType.EQUALS));
    	entityView.setFilter(filter);
    	prmtCostAccount.setEntityViewInfo(entityView);
    	KDTDefaultCellEditor caEditor = new KDTDefaultCellEditor(prmtCostAccount);
    	kdtEntires.getColumn("costAccount").setEditor(caEditor);
    	setCostAccountRenderer();
    }
    
    /**
     * 设置成本科目F7显示格式
     */
	private void setCostAccountRenderer() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		objectValueRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o instanceof ProgrammingTemplateEntireInfo) {
					return	getProF7DisplayPercent(o);				
				}
				
				if (o != null) {
					return o.toString();
				}
				return null;
			}
		});
		this.kdtEntires.getColumn("costAccount").setRenderer(objectValueRender);
	}
	
	private String getProF7DisplayPercent(Object o) {
		ProgrammingTemplateEntireInfo info = (ProgrammingTemplateEntireInfo) o;
		PTECostCollection pteCol = info.getPteCost();
		if (pteCol.size() > 0) {
			StringBuffer br = new StringBuffer();
			for (int j = 0; j < pteCol.size(); j++) {
				if(pteCol.get(j).getCostAccount() != null){
					br.append(pteCol.get(j).getCostAccount().getName());
				}
				if (pteCol.get(j).getContractScale() != null) {
					if (pteCol.get(j).getContractScale().compareTo(new BigDecimal(0)) > 0
							&& pteCol.get(j).getContractScale().compareTo(new BigDecimal(100)) < 0) {
						br.append(pteCol.get(j).getContractScale() + "%");
					}
				}
				if(pteCol.get(j).getCostAccount() != null){
					br.append(",");
				}
			}
			if(br != null && br.toString().length() > 1)
				return br.toString().substring(0, br.toString().length() -1 );
			else
				return null;
		}
		return null;
	}
	

	private void setAttachmentRenderer() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		objectValueRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o != null) {
					return "查看";
				}
				return null;
			}
		});
		this.kdtEntires.getColumn("attachment").setRenderer(objectValueRender);
	}
    
    /**
	 * 分录双击操作
	 */

    private String attachMentTempID=null;
	protected void kdtEntires_tableClicked(KDTMouseEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		if (e.getType() != KDTStyleConstants.BODY_ROW || e.getClickCount() < 2 || e.getColIndex() == 0) {
			return;
		}
		Object oldValue = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
		int level = new Integer(kdtEntires.getCell(rowIndex, "level").getValue().toString()).intValue();
    	UIContext uiContext = new UIContext(this);
    	IUIWindow uiWindow = null;
		ProgrammingTemplateEntireCollection pteCollection = getPTECollection();
		uiContext.put("pteCollection", pteCollection);
		ProgrammingTemplateEntireInfo rowObject = (ProgrammingTemplateEntireInfo) kdtEntires.getRow(rowIndex).getUserObject();
		setContractToEditData(rowIndex, rowObject);
		uiContext.put("rowObject", rowObject);

		/* 双击查看或编辑附件 */
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2
				&& e.getColIndex() == kdtEntires.getColumnIndex(ATTACHMENT)) {
			boolean isEdit = false;// 默认为查看状态
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
			AttachmentUIContextInfo info = getAttacheInfo();
			if (info == null) {
				info = new AttachmentUIContextInfo();
			}
			if (FDCHelper.isEmpty(info.getBoID())) {
				String boID = rowObject.getId().toString();
				if (boID == null) {
					if (!isEdit) {
						if (attachMentTempID == null) {
							boID = acm.getAttID().toString();
							attachMentTempID = boID;
						} else {
							boID = attachMentTempID;
						}
					} else {
						return;
					}
				}
				info.setBoID(boID);
				acm.showAttachmentListUIByBoID(boID, this, isEdit);
			}
			SysUtil.abort();
		}
		/* 双击查看框架合约详细信息 */
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			Object oldLongNumber = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
			//校验是否有下级，如果有是没有明细的
			if(rowIndex+1 <kdtEntires.getRowCount()){
				Object nextHeadNumber = kdtEntires.getCell(rowIndex+1, HEADNUMBER).getValue();
				if(oldLongNumber.equals(nextHeadNumber)){
					FDCMsgBox.showInfo("非明细目录没有详细信息！");
					return;
				}
			}
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PTEEditUI.class.getName(), uiContext, null, oprtState);
			uiWindow.show();
			// 绑定数据到分录上
			dataBinder.loadLineFields(kdtEntires, kdtEntires.getRow(rowIndex), rowObject);
			kdtEntires.getRow(rowIndex).setUserObject(rowObject);
			if (rowObject.getPteCost().size() > 0) {
				kdtEntires.getCell(rowIndex, "costAccount").setValue(rowObject);
			}
			setEntriesNameCol(rowIndex, level);
			Object newValue = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
			if (oldValue != null && newValue != null) {
				if (oldValue.equals(newValue)) {
					return;
				}
			}
			setEntriesNumberCol(rowIndex, level);
		}
	}
	
	/**
	 * 获取框架合约模版所有分录对象集合
	 * 
	 * @param uiContext
	 * @return
	 */
	private ProgrammingTemplateEntireCollection getPTECollection() {
		ProgrammingTemplateEntireCollection pteCollection = new ProgrammingTemplateEntireCollection();
		ProgrammingTemplateEntireInfo pteInfo = null;
		int columnCount = kdtEntires.getRowCount();
		for (int i = 0; i < columnCount; i++) {
			pteInfo = (ProgrammingTemplateEntireInfo) kdtEntires.getRow(i).getUserObject();
			setContractToEditData(i, pteInfo);
//			setCostAccountToEditData(i, pteInfo);
			pteCollection.add(pteInfo);
		}
		return pteCollection;
	}

	/**
	 * 手动添加成本科目到合约框架模板分录
	 * @param rowIndex
	 * @param rowObject
	 */
	protected void setCostAccountToEditData(int rowIndex , ProgrammingTemplateEntireInfo rowObject){
		Object cost = kdtEntires.getCell(rowIndex, "costAccount").getValue();
		PTECostCollection pteCost = rowObject.getPteCost();
		if(cost instanceof Object[]){
			Object[] list = (Object[])cost;
			
			if(pteCost.size() > 0){
				setCoatAccountToPTECol(pteCost, list);
			}else{
				for(int i = 0 ; i < list.length ; i++){
					CostAccountInfo costInfo = (CostAccountInfo)list[i];
					PTECostInfo info = new PTECostInfo();
					info.setCostAccount(costInfo);
					pteCost.add(info);
				}
			}
		}
		if(pteCost.size() > 0)
			kdtEntires.getCell(rowIndex, "costAccount").setValue(rowObject);
	}

	private void setCoatAccountToPTECol(PTECostCollection pteCost, Object[] list) {
		ArrayList sameList = new ArrayList();
		for(int i = 0 ; i < pteCost.size() ; i++){
			PTECostInfo pteCostInfo = pteCost.get(i);
			boolean isSame = false;
			for(int j = 0 ; j < list.length ; j++){
				CostAccountInfo costInfo = (CostAccountInfo)list[j];
				if(pteCostInfo.getCostAccount().equals(costInfo)){
					isSame = true;
				}
			}
			
			if(!isSame){
				sameList.add(pteCostInfo);
			}
		}
		
		for(int i = 0 ; i < sameList.size() ; i++){
			pteCost.remove(((PTECostInfo)sameList.get(i)));
		}
		
		for(int i = 0 ; i < list.length ; i++){
			CostAccountInfo costInfo = (CostAccountInfo)list[i];
			if(pteCost.size() > 0){
				boolean isSame = false;
				for(int j = 0 ; j < pteCost.size() ; j++){
					PTECostInfo pteCostInfo = pteCost.get(j);
					if(pteCostInfo.getCostAccount().equals(costInfo)){
						isSame = true;
					}
				}
				if(!isSame){
					PTECostInfo info = new PTECostInfo();
					info.setCostAccount(costInfo);
					pteCost.add(info);
				}
			}else{
				PTECostInfo info = new PTECostInfo();
				info.setCostAccount(costInfo);
				pteCost.add(info);
			}
		}
	}

	private void setContractToEditData(int rowIndex,ProgrammingTemplateEntireInfo rowObject){
		if (rowObject == null) {
			return;
		}
		int level = new Integer(kdtEntires.getCell(rowIndex, "level").getValue().toString()).intValue();
		if (level > 1) {
			Object longNumber = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
			if (FDCHelper.isEmpty(longNumber)) {
				String subNumber = (String) kdtEntires.getCell(rowIndex, HEADNUMBER).getValue();
				if (!FDCHelper.isEmpty(subNumber)) {
					rowObject.setLongNumber(subNumber.toString().trim() + ".");// 长编码
				}
			} else {
				rowObject.setLongNumber((String) kdtEntires.getCell(rowIndex, "longNumber").getValue());// 长编码
			}
		} else {
			rowObject.setLongNumber((String) kdtEntires.getCell(rowIndex, "longNumber").getValue());// 长编码
		}
		rowObject.setDisplayName((String) kdtEntires.getCell(rowIndex, "longName").getValue());// 长名称
		rowObject.setLevel(level);
		rowObject.setNumber((String)kdtEntires.getCell(rowIndex, "number").getValue());//编码
		rowObject.setName((String)kdtEntires.getCell(rowIndex, "name").getValue());//名称
		rowObject.setDescription((String)kdtEntires.getCell(rowIndex, "remark").getValue());//备注
	}
	
	private void setNameDisplay(){
		int rowCount = this.kdtEntires.getRowCount();
		for(int i = 0 ; i < rowCount ; i++){
			IRow row = kdtEntires.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			Object name =  row.getCell("name").getValue();
			if(name != null && name.toString().trim().length() > 0){
				String blank = setNameIndent(level);
				row.getCell("name").setValue(blank+name.toString().trim());
			}
		}
	}
	
	/**
	 * 在名称前添加空格，显示缩进效果
	 * @param level
	 * @return
	 */
	private String setNameIndent(int level){
		StringBuffer blank = new StringBuffer("");
		for(int i = level ; i > 1 ; i--){
			blank.append("        ");
		}
		return blank.toString();
	}
	private void loadCostAccountAddNew()throws BOSException, EASBizException {
		int rowCount = this.kdtEntires.getRowCount();
		for(int i = 0 ; i < rowCount ; i++){
			IRow row = kdtEntires.getRow(i);
			ProgrammingTemplateEntireInfo rowObject = (ProgrammingTemplateEntireInfo)row.getUserObject();
			if(rowObject.getId() !=  null){
				if(rowObject.getPteCost().size() > 0)
					row.getCell("costAccount").setValue(rowObject);
			}
		}
	}
	/**
	 * 界面加载时手动绑定成本科目对象到分录字段上。
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void loadCostAccountOnLoad() throws BOSException, EASBizException{
		int rowCount = this.kdtEntires.getRowCount();
		for(int i = 0 ; i < rowCount ; i++){
			IRow row = kdtEntires.getRow(i);
			ProgrammingTemplateEntireInfo rowObject = (ProgrammingTemplateEntireInfo)row.getUserObject();
			if(!isCopy){
//				rowObject.getPteCost().clear();
			}else{
				if(rowObject.getPteCost().size() > 0)
					row.getCell("costAccount").setValue(rowObject);
			}
			if(rowObject.getId() !=  null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("parent" , rowObject.getId().toString() , CompareType.EQUALS));
				view.setFilter(filter);
				PTECostCollection col = PTECostFactory.getRemoteInstance().getPTECostCollection(view);
				PTEEnonomyCollection pteEC = PTEEnonomyFactory.getRemoteInstance().getPTEEnonomyCollection(view);

				int size = col.size();
				Object[] costValue = new Object[size];
				for(int j = 0 ; j < size ; j++){
					PTECostInfo pteInfo = col.get(j);
					if(pteInfo != null){
						if(pteInfo.getCostAccount() != null){
							costValue[j] = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(pteInfo.getCostAccount().getId()));
							pteInfo.setCostAccount((CostAccountInfo)costValue[j]);
						}
						rowObject.getPteCost().add(pteInfo);
					}
				}
				if(costValue.length > 0){
					row.getCell("costAccount").setValue(rowObject);
				}else{
					filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("head.id" , rowObject.getId().toString() , CompareType.EQUALS));
					if(ProgrammingTemplateEntireFactory.getRemoteInstance().exists(filter)){
						row.getCell("costAccount").getStyleAttributes().setLocked(true);
					}
				}

				int pteeSize = pteEC.size();
				for (int j = 0; j < pteeSize; j++) {
					PTEEnonomyInfo pteeInfo = pteEC.get(j);
					if (pteeInfo != null) {
						if (pteeInfo.getPaymentType() != null) {
							PaymentTypeInfo paymentTypeInfo = PaymentTypeFactory.getRemoteInstance().getPaymentTypeInfo(
									new ObjectUuidPK(pteeInfo.getPaymentType().getId()));
							pteeInfo.setPaymentType(paymentTypeInfo);
						}
						rowObject.getPteEnonomy().add(pteeInfo);
					}
				}
			}
		}
	}
	
	protected void pnlBizLayer_stateChanged(ChangeEvent e) throws Exception {
		if(paneBIZLayerControl9.getSelectedIndex() == 1){
			loadCostAccountTabData();
			setCostNumberFormat();
			createCostTree();
			
			List rows = this.kdtCosetEntries.getBody().getRows();
		    Collections.sort(rows, new TableCellComparator(kdtCosetEntries.getColumnIndex("costNumber"), KDTSortManager.SORT_ASCEND));
		    kdtCosetEntries.setRefresh(false);
		}
	}
	
	/**
	 * 加载成本科目页签数据显示
	 */
	private void loadCostAccountTabData(){
		kdtCosetEntries.removeRows();
		kdtCosetEntries.getStyleAttributes().setLocked(true);
		int rowCount = kdtEntires.getRowCount();
		for(int i = 0 ; i < rowCount ; i++){
			Object cost = kdtEntires.getCell(i, "costAccount").getValue();
			Object name = kdtEntires.getCell(i, "name").getValue();//框架合约模板名称
			String proName = "";
			String oldName = "";
			if(name != null && name.toString().trim().length() > 0){
				proName = name.toString().trim();
				oldName = name.toString().trim();
			}
			createCostEntriesRow(i, cost, proName, oldName);
		}
	}

	private void createCostEntriesRow(int i, Object cost, String proName, String oldName) {
		if(cost instanceof ProgrammingTemplateEntireInfo){
			ProgrammingTemplateEntireInfo rowObject = (ProgrammingTemplateEntireInfo)kdtEntires.getRow(i).getUserObject();
			if (rowObject != null) {
				PTECostCollection pteCol = rowObject.getPteCost();
				if (pteCol.size() > 0) {
					addRowForCost(proName, oldName, pteCol);
				}
			}
		}
	}

	private void addRowForCost(String proName, String oldName, PTECostCollection pteCol) {
		for (int i = 0; i < pteCol.size(); i++) {
			boolean isHas = false;
			PTECostInfo info = pteCol.get(i);
			if (info.getContractScale() != null) {
				if (info.getContractScale().compareTo(new BigDecimal(0)) > 0
						&& info.getContractScale().compareTo(new BigDecimal(100)) < 0) {
					proName = oldName + info.getContractScale() + "%";
				}
			}
			CostAccountInfo costInfo = info.getCostAccount();
			String name = null;
			for (int j = 0; j < kdtCosetEntries.getRowCount(); j++) {
				name = null;
				IRow row_k = kdtCosetEntries.getRow(j);
				String number = row_k.getCell("costNumber").getValue().toString();
				Object name_1 = row_k.getCell("name").getValue();
				if(name_1 != null)
					name = name_1.toString();
				if (number.equals(costInfo.getLongNumber())) {
					isHas = true;
					if(name == null){
						row_k.getCell("name").setValue(proName);
					}else{
						row_k.getCell("name").setValue(name + "," + proName);
					}
				}
			}
			if (!isHas) {
				List list = new ArrayList();
				getParentCostAccountInfo(costInfo , list);
				getCostAccountParent(list);
				IRow row = kdtCosetEntries.addRow();
				row.getCell("costNumber").setValue(costInfo.getLongNumber());
				row.getCell("costName").setValue(setNameIndent(costInfo.getLevel())+costInfo.getName());
				row.getCell("level").setValue(costInfo.getLevel() + "");
				row.getCell("name").setValue(proName);
			}
		}
	}

	private void getCostAccountParent(List list) {
		for(int i = 0 ; i < list.size() ; i++){
			CostAccountInfo costAccountInfo = (CostAccountInfo)list.get(i);
			boolean isHas = false;
			for (int j = 0; j < kdtCosetEntries.getRowCount(); j++) {
				IRow row = kdtCosetEntries.getRow(j);
				String number = row.getCell("costNumber").getValue().toString();
				if (number.equals(costAccountInfo.getLongNumber())) {
					isHas = true;
				}
			}
			if(!isHas){
				IRow row = kdtCosetEntries.addRow();
				row.getCell("costNumber").setValue(costAccountInfo.getLongNumber());
				row.getCell("costName").setValue(setNameIndent(costAccountInfo.getLevel())+costAccountInfo.getName());
				row.getCell("level").setValue(costAccountInfo.getLevel() + "");
			}
		}
	}
	
	private CostAccountInfo getParentCostAccountInfo(CostAccountInfo info , List list){
		if(info.getParent() != null){
			CostAccountInfo costInfo = null;;
			try {
				costInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(info.getParent().getId()));
			} catch (EASBizException e) {
				logger.error(e);
			} catch (BOSException e) {
				logger.error(e);
			}
			list.add(costInfo);
			return getParentCostAccountInfo(costInfo , list);
		}
		return null;
	}
	
	/**
	 * 设置成本页签的成本科目长编码显示格式
	 */
	private void setCostNumberFormat(){
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o instanceof String) {
					return o.toString().replace('!', '.');
				} else
					return null;
			}
		});
		kdtCosetEntries.getColumn("costNumber").setRenderer(render);
	}
	
	/**
	 * 设置成本页签的树形
	 */
	private void createCostTree() {
		int maxLevel = 0;
		int[] levelArray = new int[kdtCosetEntries.getRowCount()];
		for (int i = 0; i < kdtCosetEntries.getRowCount(); i++) {
			IRow row = kdtCosetEntries.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);
		}
		for (int i = 0; i < kdtCosetEntries.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
		kdtCosetEntries.getTreeColumn().setDepth(maxLevel);
		kdtCosetEntries.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("Entires.*"));
		sic.add(new SelectorItemInfo("Entires.pteCost.*"));
		sic.add(new SelectorItemInfo("Entires.pteEnonomy.*"));
		sic.add(new SelectorItemInfo("Entires.number"));
		sic.add(new SelectorItemInfo("Entires.name"));
		sic.add(new SelectorItemInfo("Entires.description"));
		sic.add(new SelectorItemInfo("Entires.id"));
		sic.add(new SelectorItemInfo("Entires.level"));
		sic.add(new SelectorItemInfo("Entires.longNumber"));
		sic.add(new SelectorItemInfo("Entires.head.*"));
		sic.add(new SelectorItemInfo("Entires.head.longNumber"));
		sic.add(new SelectorItemInfo("Entires.attachment"));
		sic.add(new SelectorItemInfo("Entires.displayName"));
		sic.add(new SelectorItemInfo("Entires.sortNumber"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("Entires.contractType.*"));
		return sic;
	}
	
//	public boolean checkBeforeWindowClosing() {
//		return super.checkBeforeWindowClosing();
//	}
	
	protected void handleOldData() {
		if(!(getOprtState()==STATUS_VIEW)){
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}
}