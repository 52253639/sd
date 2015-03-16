/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractHeadTypeEditUI extends com.kingdee.eas.framework.client.TreeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractHeadTypeEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLongNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUpperNum;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntries;
    protected com.kingdee.bos.ctrl.swing.KDContainer contHeadColumn;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsDefined;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox bizName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLongNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtUpperNum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHeadName;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox ckcIsQuoting;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHeadDescription;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contColumnType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtHeadName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtHeadDes;
    protected com.kingdee.bos.ctrl.swing.KDTree treeColumn;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboColumnType;
    protected com.kingdee.eas.fdc.invite.HeadTypeInfo editData = null;
    protected ActionAddLine actionAddLine = null;
    protected ActionRemoveLine actionRemoveLine = null;
    /**
     * output class constructor
     */
    public AbstractHeadTypeEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractHeadTypeEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddLine
        this.actionAddLine = new ActionAddLine(this);
        getActionManager().registerAction("actionAddLine", actionAddLine);
         this.actionAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveLine
        this.actionRemoveLine = new ActionRemoveLine(this);
        getActionManager().registerAction("actionRemoveLine", actionRemoveLine);
         this.actionRemoveLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLongNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUpperNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contHeadColumn = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.chkIsDefined = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.bizName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLongNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtUpperNum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contHeadName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.ckcIsQuoting = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contHeadDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.contColumnType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtHeadName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtHeadDes = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.treeColumn = new com.kingdee.bos.ctrl.swing.KDTree();
        this.comboColumnType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contName.setName("contName");
        this.contDescription.setName("contDescription");
        this.contLongNumber.setName("contLongNumber");
        this.contUpperNum.setName("contUpperNum");
        this.kdtEntries.setName("kdtEntries");
        this.contHeadColumn.setName("contHeadColumn");
        this.chkIsDefined.setName("chkIsDefined");
        this.bizName.setName("bizName");
        this.txtDescription.setName("txtDescription");
        this.txtLongNumber.setName("txtLongNumber");
        this.txtUpperNum.setName("txtUpperNum");
        this.contHeadName.setName("contHeadName");
        this.ckcIsQuoting.setName("ckcIsQuoting");
        this.contHeadDescription.setName("contHeadDescription");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.contColumnType.setName("contColumnType");
        this.txtHeadName.setName("txtHeadName");
        this.txtHeadDes.setName("txtHeadDes");
        this.treeColumn.setName("treeColumn");
        this.comboColumnType.setName("comboColumnType");
        // CoreUI		
        this.btnSave.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.btnAttachment.setVisible(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(60);		
        this.contName.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(60);		
        this.contDescription.setBoundLabelUnderline(true);
        // contLongNumber		
        this.contLongNumber.setBoundLabelText(resHelper.getString("contLongNumber.boundLabelText"));		
        this.contLongNumber.setBoundLabelLength(60);		
        this.contLongNumber.setBoundLabelUnderline(true);
        // contUpperNum		
        this.contUpperNum.setBoundLabelText(resHelper.getString("contUpperNum.boundLabelText"));		
        this.contUpperNum.setBoundLabelLength(60);		
        this.contUpperNum.setBoundLabelUnderline(true);
        // kdtEntries
		String kdtEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"isQuoting\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{isQuoting}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntries.setFormatXml(resHelper.translateString("kdtEntries",kdtEntriesStrXML));		
        this.kdtEntries.setVisible(false);

        

        // contHeadColumn		
        this.contHeadColumn.setTitle(resHelper.getString("contHeadColumn.title"));		
        this.contHeadColumn.setEnableActive(false);
        // chkIsDefined		
        this.chkIsDefined.setText(resHelper.getString("chkIsDefined.text"));
        this.chkIsDefined.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkIsDefined_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // bizName		
        this.bizName.setMaxLength(80);		
        this.bizName.setRequired(true);
        // txtDescription		
        this.txtDescription.setMaxLength(200);
        // txtLongNumber		
        this.txtLongNumber.setMaxLength(80);		
        this.txtLongNumber.setRequired(true);
        // txtUpperNum		
        this.txtUpperNum.setEnabled(false);		
        this.txtUpperNum.setMaxLength(80);
        // contHeadName		
        this.contHeadName.setBoundLabelText(resHelper.getString("contHeadName.boundLabelText"));		
        this.contHeadName.setBoundLabelLength(60);		
        this.contHeadName.setBoundLabelUnderline(true);
        // ckcIsQuoting		
        this.ckcIsQuoting.setText(resHelper.getString("ckcIsQuoting.text"));
        this.ckcIsQuoting.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    ckcIsQuoting_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contHeadDescription		
        this.contHeadDescription.setBoundLabelText(resHelper.getString("contHeadDescription.boundLabelText"));		
        this.contHeadDescription.setBoundLabelLength(60);		
        this.contHeadDescription.setBoundLabelUnderline(true);
        // kDScrollPane1
        // contColumnType		
        this.contColumnType.setBoundLabelText(resHelper.getString("contColumnType.boundLabelText"));		
        this.contColumnType.setBoundLabelLength(60);		
        this.contColumnType.setBoundLabelUnderline(true);
        // txtHeadName		
        this.txtHeadName.setMaxLength(80);
        this.txtHeadName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                try {
                    txtHeadName_keyReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtHeadDes		
        this.txtHeadDes.setMaxLength(80);
        // treeColumn
        this.treeColumn.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeColumn_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboColumnType		
        this.comboColumnType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.ColumnTypeEnum").toArray());		
        this.comboColumnType.setRequired(true);
        this.comboColumnType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    comboColumnType_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 556, 385));
        this.setLayout(null);
        contName.setBounds(new Rectangle(10, 37, 245, 19));
        this.add(contName, null);
        contDescription.setBounds(new Rectangle(10, 64, 536, 19));
        this.add(contDescription, null);
        contLongNumber.setBounds(new Rectangle(301, 10, 245, 19));
        this.add(contLongNumber, null);
        contUpperNum.setBounds(new Rectangle(10, 10, 245, 19));
        this.add(contUpperNum, null);
        kdtEntries.setBounds(new Rectangle(10, 20, 156, 42));
        this.add(kdtEntries, null);
        contHeadColumn.setBounds(new Rectangle(10, 90, 536, 285));
        this.add(contHeadColumn, null);
        chkIsDefined.setBounds(new Rectangle(301, 37, 245, 19));
        this.add(chkIsDefined, null);
        //contName
        contName.setBoundEditor(bizName);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contLongNumber
        contLongNumber.setBoundEditor(txtLongNumber);
        //contUpperNum
        contUpperNum.setBoundEditor(txtUpperNum);
        //contHeadColumn
        contHeadColumn.getContentPane().setLayout(null);        contHeadName.setBounds(new Rectangle(8, 5, 236, 19));
        contHeadColumn.getContentPane().add(contHeadName, null);
        ckcIsQuoting.setBounds(new Rectangle(8, 32, 236, 19));
        contHeadColumn.getContentPane().add(ckcIsQuoting, null);
        contHeadDescription.setBounds(new Rectangle(289, 32, 238, 19));
        contHeadColumn.getContentPane().add(contHeadDescription, null);
        kDScrollPane1.setBounds(new Rectangle(8, 60, 520, 200));
        contHeadColumn.getContentPane().add(kDScrollPane1, null);
        contColumnType.setBounds(new Rectangle(289, 5, 238, 19));
        contHeadColumn.getContentPane().add(contColumnType, null);
        //contHeadName
        contHeadName.setBoundEditor(txtHeadName);
        //contHeadDescription
        contHeadDescription.setBoundEditor(txtHeadDes);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(treeColumn, null);
        //contColumnType
        contColumnType.setBoundEditor(comboColumnType);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isDefined", boolean.class, this.chkIsDefined, "selected");
		dataBinder.registerBinding("name", String.class, this.bizName, "_multiLangItem");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("longNumber", String.class, this.txtLongNumber, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.HeadTypeEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.invite.HeadTypeInfo)ov;
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("isDefined", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("longNumber", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output chkIsDefined_stateChanged method
     */
    protected void chkIsDefined_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output ckcIsQuoting_stateChanged method
     */
    protected void ckcIsQuoting_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output txtHeadName_keyReleased method
     */
    protected void txtHeadName_keyReleased(java.awt.event.KeyEvent e) throws Exception
    {
    }

    /**
     * output treeColumn_valueChanged method
     */
    protected void treeColumn_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output comboColumnType_actionPerformed method
     */
    protected void comboColumnType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("isDefined"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("longNumber"));
        return sic;
    }        
    	

    /**
     * output actionAddLine_actionPerformed method
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveLine_actionPerformed method
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAddLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddLine() {
    	return false;
    }
	public RequestContext prepareActionRemoveLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemoveLine() {
    	return false;
    }

    /**
     * output ActionAddLine class
     */     
    protected class ActionAddLine extends ItemAction {     
    
        public ActionAddLine()
        {
            this(null);
        }

        public ActionAddLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractHeadTypeEditUI.this, "ActionAddLine", "actionAddLine_actionPerformed", e);
        }
    }

    /**
     * output ActionRemoveLine class
     */     
    protected class ActionRemoveLine extends ItemAction {     
    
        public ActionRemoveLine()
        {
            this(null);
        }

        public ActionRemoveLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRemoveLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractHeadTypeEditUI.this, "ActionRemoveLine", "actionRemoveLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "HeadTypeEditUI");
    }




}