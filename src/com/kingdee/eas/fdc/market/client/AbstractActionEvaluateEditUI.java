/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

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
public abstract class AbstractActionEvaluateEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractActionEvaluateEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmovementName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAppraise;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contperson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contevaluatedate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmeno;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contactionid;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttheme;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMarket;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAppraise;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtperson;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkevaluatedate;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanemeno;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtmeno;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtactionid;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttheme;
    protected com.kingdee.eas.fdc.market.ActionEvaluateInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractActionEvaluateEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractActionEvaluateEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmovementName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAppraise = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contperson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contevaluatedate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmeno = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contactionid = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtMarket = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAppraise = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtperson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkevaluatedate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.scrollPanemeno = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtmeno = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtactionid = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttheme = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contmovementName.setName("contmovementName");
        this.contAppraise.setName("contAppraise");
        this.contperson.setName("contperson");
        this.contevaluatedate.setName("contevaluatedate");
        this.contmeno.setName("contmeno");
        this.contactionid.setName("contactionid");
        this.conttheme.setName("conttheme");
        this.txtNumber.setName("txtNumber");
        this.prmtMarket.setName("prmtMarket");
        this.prmtAppraise.setName("prmtAppraise");
        this.prmtperson.setName("prmtperson");
        this.pkevaluatedate.setName("pkevaluatedate");
        this.scrollPanemeno.setName("scrollPanemeno");
        this.txtmeno.setName("txtmeno");
        this.txtactionid.setName("txtactionid");
        this.txttheme.setName("txttheme");
        // CoreUI		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelAlignment(7);		
        this.kDLabelContainer1.setVisible(true);		
        this.kDLabelContainer1.setForeground(new java.awt.Color(0,0,0));
        // contmovementName		
        this.contmovementName.setBoundLabelText(resHelper.getString("contmovementName.boundLabelText"));		
        this.contmovementName.setBoundLabelLength(100);		
        this.contmovementName.setBoundLabelUnderline(true);		
        this.contmovementName.setVisible(true);		
        this.contmovementName.setBoundLabelAlignment(7);		
        this.contmovementName.setForeground(new java.awt.Color(0,0,0));
        // contAppraise		
        this.contAppraise.setBoundLabelText(resHelper.getString("contAppraise.boundLabelText"));		
        this.contAppraise.setBoundLabelLength(100);		
        this.contAppraise.setBoundLabelUnderline(true);		
        this.contAppraise.setVisible(true);		
        this.contAppraise.setBoundLabelAlignment(7);		
        this.contAppraise.setForeground(new java.awt.Color(0,0,0));
        // contperson		
        this.contperson.setBoundLabelText(resHelper.getString("contperson.boundLabelText"));		
        this.contperson.setBoundLabelLength(100);		
        this.contperson.setBoundLabelUnderline(true);		
        this.contperson.setVisible(true);		
        this.contperson.setBoundLabelAlignment(7);		
        this.contperson.setForeground(new java.awt.Color(0,0,0));
        // contevaluatedate		
        this.contevaluatedate.setBoundLabelText(resHelper.getString("contevaluatedate.boundLabelText"));		
        this.contevaluatedate.setBoundLabelLength(100);		
        this.contevaluatedate.setBoundLabelUnderline(true);		
        this.contevaluatedate.setVisible(true);		
        this.contevaluatedate.setBoundLabelAlignment(7);		
        this.contevaluatedate.setForeground(new java.awt.Color(0,0,0));
        // contmeno		
        this.contmeno.setBoundLabelText(resHelper.getString("contmeno.boundLabelText"));		
        this.contmeno.setBoundLabelLength(100);		
        this.contmeno.setBoundLabelUnderline(true);		
        this.contmeno.setVisible(true);		
        this.contmeno.setForeground(new java.awt.Color(0,0,0));		
        this.contmeno.setBoundLabelAlignment(7);
        // contactionid		
        this.contactionid.setBoundLabelText(resHelper.getString("contactionid.boundLabelText"));		
        this.contactionid.setBoundLabelLength(100);		
        this.contactionid.setBoundLabelUnderline(true);		
        this.contactionid.setVisible(false);		
        this.contactionid.setBoundLabelAlignment(7);		
        this.contactionid.setForeground(new java.awt.Color(0,0,0));
        // conttheme		
        this.conttheme.setBoundLabelText(resHelper.getString("conttheme.boundLabelText"));		
        this.conttheme.setBoundLabelLength(100);		
        this.conttheme.setBoundLabelUnderline(true);		
        this.conttheme.setVisible(true);		
        this.conttheme.setBoundLabelAlignment(7);		
        this.conttheme.setForeground(new java.awt.Color(0,0,0));
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(false);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setForeground(new java.awt.Color(0,0,0));		
        this.txtNumber.setRequired(true);
        // prmtMarket		
        this.prmtMarket.setQueryInfo("com.kingdee.eas.fdc.market.app.MarketTypeQuery");		
        this.prmtMarket.setEditFormat("$number$");		
        this.prmtMarket.setDisplayFormat("$name$");		
        this.prmtMarket.setCommitFormat("$number$");
        		prmtMarket.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.fdc.market.client.MarketTypeListUI prmtMarket_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (prmtMarket_F7ListUI == null) {
					try {
						prmtMarket_F7ListUI = new com.kingdee.eas.fdc.market.client.MarketTypeListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(prmtMarket_F7ListUI));
					prmtMarket_F7ListUI.setF7Use(true,ctx);
					prmtMarket.setSelector(prmtMarket_F7ListUI);
				}
			}
		});
					
        // prmtAppraise		
        this.prmtAppraise.setQueryInfo("com.kingdee.eas.fdc.market.app.AppraiseQuery");		
        this.prmtAppraise.setVisible(true);		
        this.prmtAppraise.setEditable(true);		
        this.prmtAppraise.setDisplayFormat("$name$");		
        this.prmtAppraise.setEditFormat("$number$");		
        this.prmtAppraise.setCommitFormat("$number$");		
        this.prmtAppraise.setRequired(false);		
        this.prmtAppraise.setEnabled(true);		
        this.prmtAppraise.setForeground(new java.awt.Color(0,0,0));
        // prmtperson		
        this.prmtperson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtperson.setVisible(true);		
        this.prmtperson.setEditable(true);		
        this.prmtperson.setDisplayFormat("$name$");		
        this.prmtperson.setEditFormat("$number$");		
        this.prmtperson.setCommitFormat("$number$");		
        this.prmtperson.setRequired(false);		
        this.prmtperson.setEnabled(true);		
        this.prmtperson.setForeground(new java.awt.Color(0,0,0));
        // pkevaluatedate		
        this.pkevaluatedate.setVisible(true);		
        this.pkevaluatedate.setRequired(false);		
        this.pkevaluatedate.setEnabled(true);		
        this.pkevaluatedate.setForeground(new java.awt.Color(0,0,0));
        // scrollPanemeno
        // txtmeno		
        this.txtmeno.setVisible(true);		
        this.txtmeno.setForeground(new java.awt.Color(0,0,0));		
        this.txtmeno.setRequired(false);		
        this.txtmeno.setMaxLength(255);		
        this.txtmeno.setEnabled(true);
        // txtactionid		
        this.txtactionid.setVisible(false);		
        this.txtactionid.setHorizontalAlignment(2);		
        this.txtactionid.setMaxLength(100);		
        this.txtactionid.setRequired(false);		
        this.txtactionid.setEnabled(true);		
        this.txtactionid.setForeground(new java.awt.Color(0,0,0));
        // txttheme		
        this.txttheme.setVisible(true);		
        this.txttheme.setHorizontalAlignment(2);		
        this.txttheme.setMaxLength(100);		
        this.txttheme.setRequired(false);		
        this.txttheme.setEnabled(true);		
        this.txttheme.setForeground(new java.awt.Color(0,0,0));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtAppraise,prmtperson,pkevaluatedate,txtmeno,txtactionid,txtNumber,txttheme}));
        this.setFocusCycleRoot(true);
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
        this.setBounds(new Rectangle(0, 0, 713, 259));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(13, 31, 270, 19));
        this.add(kDLabelContainer1, null);
        contmovementName.setBounds(new Rectangle(13, 74, 270, 19));
        this.add(contmovementName, null);
        contAppraise.setBounds(new Rectangle(423, 72, 270, 19));
        this.add(contAppraise, null);
        contperson.setBounds(new Rectangle(13, 114, 270, 19));
        this.add(contperson, null);
        contevaluatedate.setBounds(new Rectangle(423, 114, 270, 19));
        this.add(contevaluatedate, null);
        contmeno.setBounds(new Rectangle(13, 153, 680, 78));
        this.add(contmeno, null);
        contactionid.setBounds(new Rectangle(170, 250, 270, 19));
        this.add(contactionid, null);
        conttheme.setBounds(new Rectangle(423, 31, 270, 19));
        this.add(conttheme, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //contmovementName
        contmovementName.setBoundEditor(prmtMarket);
        //contAppraise
        contAppraise.setBoundEditor(prmtAppraise);
        //contperson
        contperson.setBoundEditor(prmtperson);
        //contevaluatedate
        contevaluatedate.setBoundEditor(pkevaluatedate);
        //contmeno
        contmeno.setBoundEditor(scrollPanemeno);
        //scrollPanemeno
        scrollPanemeno.getViewport().add(txtmeno, null);
        //contactionid
        contactionid.setBoundEditor(txtactionid);
        //conttheme
        conttheme.setBoundEditor(txttheme);

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
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("movementName", String.class, this.prmtMarket, "data");
		dataBinder.registerBinding("appraise", com.kingdee.eas.fdc.market.AppraiseInfo.class, this.prmtAppraise, "data");
		dataBinder.registerBinding("person", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtperson, "data");
		dataBinder.registerBinding("evaluatedate", java.util.Date.class, this.pkevaluatedate, "value");
		dataBinder.registerBinding("meno", String.class, this.txtmeno, "text");
		dataBinder.registerBinding("actionid", String.class, this.txtactionid, "text");
		dataBinder.registerBinding("theme", String.class, this.txttheme, "text");		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtNumber, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtNumber, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.ActionEvaluateEditUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.prmtAppraise.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.market.ActionEvaluateInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"Sale",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Sale");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("Sale");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer oufip=(com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer)com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
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
		getValidateHelper().registerBindProperty("movementName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("appraise", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("person", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("evaluatedate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("meno", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("actionid", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("theme", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
		            this.txtNumber.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.txtNumber.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.txtNumber.setEnabled(false);
        }
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("movementName.*"));
        sic.add(new SelectorItemInfo("appraise.*"));
        sic.add(new SelectorItemInfo("person.*"));
        sic.add(new SelectorItemInfo("evaluatedate"));
        sic.add(new SelectorItemInfo("meno"));
        sic.add(new SelectorItemInfo("actionid"));
        sic.add(new SelectorItemInfo("theme"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "ActionEvaluateEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.market.client.ActionEvaluateEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.ActionEvaluateFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.ActionEvaluateInfo objectValue = new com.kingdee.eas.fdc.market.ActionEvaluateInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {        
        return null;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}