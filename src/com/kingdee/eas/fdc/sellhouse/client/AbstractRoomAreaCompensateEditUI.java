/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

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
public abstract class AbstractRoomAreaCompensateEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomAreaCompensateEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCompensateAmount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCompensateDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTransactor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtScheme;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlRoomInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlRoomAreaCompensateInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompensateAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompensateDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTransactor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contScheme;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane sclPanel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCalc;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCalc;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCalculator;
    protected com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo editData = null;
    protected ActionCalc actionCalc = null;
    /**
     * output class constructor
     */
    public AbstractRoomAreaCompensateEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomAreaCompensateEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionCalculator
        String _tempStr = null;
        actionCalculator.setEnabled(true);
        actionCalculator.setDaemonRun(false);

        actionCalculator.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F12"));
        _tempStr = resHelper.getString("ActionCalculator.SHORT_DESCRIPTION");
        actionCalculator.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCalculator.LONG_DESCRIPTION");
        actionCalculator.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCalculator.NAME");
        actionCalculator.putValue(ItemAction.NAME, _tempStr);
         this.actionCalculator.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCalc
        this.actionCalc = new ActionCalc(this);
        getActionManager().registerAction("actionCalc", actionCalc);
         this.actionCalc.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCompensateAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkCompensateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtTransactor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtScheme = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pnlRoomInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlRoomAreaCompensateInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompensateAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompensateDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTransactor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contScheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.sclPanel = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.btnCalc = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemCalc = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnCalculator = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtNumber.setName("txtNumber");
        this.txtDescription.setName("txtDescription");
        this.txtCompensateAmount.setName("txtCompensateAmount");
        this.pkCompensateDate.setName("pkCompensateDate");
        this.prmtTransactor.setName("prmtTransactor");
        this.prmtScheme.setName("prmtScheme");
        this.pnlRoomInfo.setName("pnlRoomInfo");
        this.pnlRoomAreaCompensateInfo.setName("pnlRoomAreaCompensateInfo");
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contCompensateAmount.setName("contCompensateAmount");
        this.contCompensateDate.setName("contCompensateDate");
        this.contTransactor.setName("contTransactor");
        this.contScheme.setName("contScheme");
        this.contAuditor.setName("contAuditor");
        this.prmtAuditor.setName("prmtAuditor");
        this.sclPanel.setName("sclPanel");
        this.btnCalc.setName("btnCalc");
        this.menuItemCalc.setName("menuItemCalc");
        this.btnCalculator.setName("btnCalculator");
        // CoreUI		
        this.menuItemCalculator.setVisible(true);
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(80);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // txtCompensateAmount		
        this.txtCompensateAmount.setDataType(1);		
        this.txtCompensateAmount.setPrecision(2);		
        this.txtCompensateAmount.setSupportedEmpty(true);		
        this.txtCompensateAmount.setRequired(true);
        this.txtCompensateAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtCompensateAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkCompensateDate		
        this.pkCompensateDate.setRequired(true);
        // prmtTransactor		
        this.prmtTransactor.setDisplayFormat("$name$");		
        this.prmtTransactor.setEditFormat("$number$");		
        this.prmtTransactor.setCommitFormat("$number$");		
        this.prmtTransactor.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtTransactor.setRequired(true);
        // prmtScheme		
        this.prmtScheme.setCommitFormat("$number$ $name$");		
        this.prmtScheme.setDisplayFormat("$name$");		
        this.prmtScheme.setEditFormat("$number$");		
        this.prmtScheme.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CompensateSchemeQuery");
        this.prmtScheme.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtScheme_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pnlRoomInfo		
        this.pnlRoomInfo.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlRoomInfo.border.title")));
        // pnlRoomAreaCompensateInfo		
        this.pnlRoomAreaCompensateInfo.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlRoomAreaCompensateInfo.border.title")));
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contCompensateAmount		
        this.contCompensateAmount.setBoundLabelText(resHelper.getString("contCompensateAmount.boundLabelText"));		
        this.contCompensateAmount.setBoundLabelLength(100);		
        this.contCompensateAmount.setBoundLabelUnderline(true);
        // contCompensateDate		
        this.contCompensateDate.setBoundLabelText(resHelper.getString("contCompensateDate.boundLabelText"));		
        this.contCompensateDate.setBoundLabelLength(100);		
        this.contCompensateDate.setBoundLabelUnderline(true);
        // contTransactor		
        this.contTransactor.setBoundLabelText(resHelper.getString("contTransactor.boundLabelText"));		
        this.contTransactor.setBoundLabelLength(100);		
        this.contTransactor.setBoundLabelUnderline(true);
        // contScheme		
        this.contScheme.setBoundLabelText(resHelper.getString("contScheme.boundLabelText"));		
        this.contScheme.setBoundLabelLength(100);		
        this.contScheme.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // prmtAuditor		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$number$");		
        this.prmtAuditor.setCommitFormat("$number$");		
        this.prmtAuditor.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
        // sclPanel
        // btnCalc
        this.btnCalc.setAction((IItemAction)ActionProxyFactory.getProxy(actionCalc, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCalc.setText(resHelper.getString("btnCalc.text"));		
        this.btnCalc.setToolTipText(resHelper.getString("btnCalc.toolTipText"));
        // menuItemCalc
        this.menuItemCalc.setAction((IItemAction)ActionProxyFactory.getProxy(actionCalc, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCalc.setText(resHelper.getString("menuItemCalc.text"));		
        this.menuItemCalc.setToolTipText(resHelper.getString("menuItemCalc.toolTipText"));		
        this.menuItemCalc.setMnemonic(67);
        // btnCalculator
        this.btnCalculator.setAction((IItemAction)ActionProxyFactory.getProxy(actionCalculator, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCalculator.setText(resHelper.getString("btnCalculator.text"));		
        this.btnCalculator.setToolTipText(resHelper.getString("btnCalculator.toolTipText"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        pnlRoomInfo.setBounds(new Rectangle(5, 10, 1005, 200));
        this.add(pnlRoomInfo, new KDLayout.Constraints(5, 10, 1005, 200, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        pnlRoomAreaCompensateInfo.setBounds(new Rectangle(5, 210, 1005, 409));
        this.add(pnlRoomAreaCompensateInfo, new KDLayout.Constraints(5, 210, 1005, 409, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlRoomInfo
pnlRoomInfo.setLayout(new BorderLayout(0, 0));        pnlRoomInfo.add(sclPanel, BorderLayout.CENTER);
        //pnlRoomAreaCompensateInfo
        pnlRoomAreaCompensateInfo.setLayout(new KDLayout());
        pnlRoomAreaCompensateInfo.putClientProperty("OriginalBounds", new Rectangle(5, 210, 1005, 409));        contNumber.setBounds(new Rectangle(15, 15, 270, 19));
        pnlRoomAreaCompensateInfo.add(contNumber, new KDLayout.Constraints(15, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(15, 65, 975, 19));
        pnlRoomAreaCompensateInfo.add(contDescription, new KDLayout.Constraints(15, 65, 975, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCompensateAmount.setBounds(new Rectangle(720, 15, 270, 19));
        pnlRoomAreaCompensateInfo.add(contCompensateAmount, new KDLayout.Constraints(720, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCompensateDate.setBounds(new Rectangle(15, 40, 270, 19));
        pnlRoomAreaCompensateInfo.add(contCompensateDate, new KDLayout.Constraints(15, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTransactor.setBounds(new Rectangle(367, 40, 270, 19));
        pnlRoomAreaCompensateInfo.add(contTransactor, new KDLayout.Constraints(367, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contScheme.setBounds(new Rectangle(367, 15, 270, 19));
        pnlRoomAreaCompensateInfo.add(contScheme, new KDLayout.Constraints(367, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(720, 40, 270, 19));
        pnlRoomAreaCompensateInfo.add(contAuditor, new KDLayout.Constraints(720, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contCompensateAmount
        contCompensateAmount.setBoundEditor(txtCompensateAmount);
        //contCompensateDate
        contCompensateDate.setBoundEditor(pkCompensateDate);
        //contTransactor
        contTransactor.setBoundEditor(prmtTransactor);
        //contScheme
        contScheme.setBoundEditor(prmtScheme);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);

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
        menuBiz.add(menuItemCalc);
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
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
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
        this.toolBar.add(btnCalc);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("compensateAmount", java.math.BigDecimal.class, this.txtCompensateAmount, "value");
		dataBinder.registerBinding("compensateDate", java.util.Date.class, this.pkCompensateDate, "value");
		dataBinder.registerBinding("transactor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtTransactor, "data");
		dataBinder.registerBinding("scheme", com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo.class, this.prmtScheme, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomAreaCompensateEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("compensateAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("compensateDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("transactor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("scheme", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    		
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
     * output txtCompensateAmount_dataChanged method
     */
    protected void txtCompensateAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtScheme_dataChanged method
     */
    protected void prmtScheme_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("compensateAmount"));
        sic.add(new SelectorItemInfo("compensateDate"));
        sic.add(new SelectorItemInfo("transactor.*"));
        sic.add(new SelectorItemInfo("scheme.*"));
        sic.add(new SelectorItemInfo("auditor.*"));
        return sic;
    }        
    	

    /**
     * output actionCalculator_actionPerformed method
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }
    	

    /**
     * output actionCalc_actionPerformed method
     */
    public void actionCalc_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionCalculator(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCalculator(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCalculator() {
    	return false;
    }
	public RequestContext prepareActionCalc(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCalc() {
    	return false;
    }

    /**
     * output ActionCalc class
     */     
    protected class ActionCalc extends ItemAction {     
    
        public ActionCalc()
        {
            this(null);
        }

        public ActionCalc(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_compute"));
            _tempStr = resHelper.getString("ActionCalc.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalc.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalc.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomAreaCompensateEditUI.this, "ActionCalc", "actionCalc_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomAreaCompensateEditUI");
    }




}