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
public abstract class AbstractSubjectViewUI3 extends com.kingdee.eas.framework.client.BillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSubjectViewUI3.class);
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtOptions;
    protected com.kingdee.bos.ctrl.swing.KDButton btnOK;
    protected com.kingdee.bos.ctrl.swing.KDButton addLine;
    protected com.kingdee.bos.ctrl.swing.KDButton deleLine;
    protected com.kingdee.bos.ctrl.swing.KDButton btnNO;
    protected com.kingdee.bos.ctrl.swing.KDSpinner txtXFontSize;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSubjectTopic;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comSubjectType;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.swing.KDSpinner subjectFontSize;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel5;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel8;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator6;
    protected com.kingdee.bos.ctrl.swing.KDButton btnAddItem;
    protected com.kingdee.bos.ctrl.swing.KDButton btnDeleItem;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator7;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtItems;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comOptonAlignType;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comHorizonType;
    protected com.kingdee.bos.ctrl.swing.KDTextArea descriptionText;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel7;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtSubjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox comSubjectNumberShow;
    protected com.kingdee.eas.fdc.market.DocumentItemInfo editData = null;
    protected com.kingdee.eas.fdc.market.DocumentSubjectInfo documentSubject = null;
    /**
     * output class constructor
     */
    public AbstractSubjectViewUI3() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSubjectViewUI3.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kdtOptions = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnOK = new com.kingdee.bos.ctrl.swing.KDButton();
        this.addLine = new com.kingdee.bos.ctrl.swing.KDButton();
        this.deleLine = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnNO = new com.kingdee.bos.ctrl.swing.KDButton();
        this.txtXFontSize = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtSubjectTopic = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comSubjectType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.subjectFontSize = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel8 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator6 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnAddItem = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnDeleItem = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDSeparator7 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kdtItems = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.comOptonAlignType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.comHorizonType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.descriptionText = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDLabel7 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtSubjectNumber = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.comSubjectNumberShow = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kdtOptions.setName("kdtOptions");
        this.btnOK.setName("btnOK");
        this.addLine.setName("addLine");
        this.deleLine.setName("deleLine");
        this.btnNO.setName("btnNO");
        this.txtXFontSize.setName("txtXFontSize");
        this.kDLabel2.setName("kDLabel2");
        this.kDLabel3.setName("kDLabel3");
        this.txtSubjectTopic.setName("txtSubjectTopic");
        this.comSubjectType.setName("comSubjectType");
        this.kDLabel4.setName("kDLabel4");
        this.subjectFontSize.setName("subjectFontSize");
        this.kDLabel5.setName("kDLabel5");
        this.kDLabel8.setName("kDLabel8");
        this.kDSeparator6.setName("kDSeparator6");
        this.btnAddItem.setName("btnAddItem");
        this.btnDeleItem.setName("btnDeleItem");
        this.kDSeparator7.setName("kDSeparator7");
        this.kdtItems.setName("kdtItems");
        this.comOptonAlignType.setName("comOptonAlignType");
        this.kDLabel1.setName("kDLabel1");
        this.comHorizonType.setName("comHorizonType");
        this.descriptionText.setName("descriptionText");
        this.kDLabel7.setName("kDLabel7");
        this.txtSubjectNumber.setName("txtSubjectNumber");
        this.comSubjectNumberShow.setName("comSubjectNumberShow");
        // CoreUI
        // kdtOptions		
        this.kdtOptions.setFormatXml(resHelper.getString("kdtOptions.formatXml"));

                this.kdtOptions.putBindContents("editData",new String[] {"optionNumber","topic","xLength","isTopicInverse","xFontSize","id","xFontName"});


        // btnOK		
        this.btnOK.setText(resHelper.getString("btnOK.text"));
        this.btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnOK_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // addLine		
        this.addLine.setText(resHelper.getString("addLine.text"));
        this.addLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    addLine_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // deleLine		
        this.deleLine.setText(resHelper.getString("deleLine.text"));
        this.deleLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    deleLine_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnNO		
        this.btnNO.setText(resHelper.getString("btnNO.text"));
        this.btnNO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnNO_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtXFontSize		
        this.txtXFontSize.setVisible(false);
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));		
        this.kDLabel2.setVisible(false);
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // txtSubjectTopic
        this.txtSubjectTopic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtSubjectTopic_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // comSubjectType		
        this.comSubjectType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum").toArray());
        this.comSubjectType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    comSubjectType_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabel4		
        this.kDLabel4.setText(resHelper.getString("kDLabel4.text"));
        // subjectFontSize
        // kDLabel5		
        this.kDLabel5.setText(resHelper.getString("kDLabel5.text"));
        // kDLabel8		
        this.kDLabel8.setText(resHelper.getString("kDLabel8.text"));
        // kDSeparator6
        // btnAddItem		
        this.btnAddItem.setText(resHelper.getString("btnAddItem.text"));
        this.btnAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddItem_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDeleItem		
        this.btnDeleItem.setText(resHelper.getString("btnDeleItem.text"));
        this.btnDeleItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDeleItem_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDSeparator7
        // kdtItems		
        this.kdtItems.setFormatXml(resHelper.getString("kdtItems.formatXml"));

        

        // comOptonAlignType		
        this.comOptonAlignType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.DocumentOptionLayoutEnum").toArray());
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // comHorizonType		
        this.comHorizonType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.market.DocumentOptionHorizonLayoutEnum").toArray());		
        this.comHorizonType.setVisible(false);
        // descriptionText		
        this.descriptionText.setVisible(false);
        // kDLabel7		
        this.kDLabel7.setText(resHelper.getString("kDLabel7.text"));
        // txtSubjectNumber
        // comSubjectNumberShow		
        this.comSubjectNumberShow.setText(resHelper.getString("comSubjectNumberShow.text"));		
        this.comSubjectNumberShow.setSelected(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 800, 600));
        this.setLayout(null);
        kdtOptions.setBounds(new Rectangle(10, 400, 778, 178));
        this.add(kdtOptions, null);
        btnOK.setBounds(new Rectangle(670, 9, 122, 21));
        this.add(btnOK, null);
        addLine.setBounds(new Rectangle(670, 341, 122, 21));
        this.add(addLine, null);
        deleLine.setBounds(new Rectangle(670, 370, 122, 21));
        this.add(deleLine, null);
        btnNO.setBounds(new Rectangle(670, 34, 122, 21));
        this.add(btnNO, null);
        txtXFontSize.setBounds(new Rectangle(687, 232, 56, 19));
        this.add(txtXFontSize, null);
        kDLabel2.setBounds(new Rectangle(684, 237, 55, 19));
        this.add(kDLabel2, null);
        kDLabel3.setBounds(new Rectangle(10, 10, 52, 19));
        this.add(kDLabel3, null);
        txtSubjectTopic.setBounds(new Rectangle(67, 10, 589, 19));
        this.add(txtSubjectTopic, null);
        comSubjectType.setBounds(new Rectangle(67, 40, 70, 19));
        this.add(comSubjectType, null);
        kDLabel4.setBounds(new Rectangle(10, 40, 52, 19));
        this.add(kDLabel4, null);
        subjectFontSize.setBounds(new Rectangle(170, 40, 54, 19));
        this.add(subjectFontSize, null);
        kDLabel5.setBounds(new Rectangle(142, 40, 27, 19));
        this.add(kDLabel5, null);
        kDLabel8.setBounds(new Rectangle(13, 65, 54, 19));
        this.add(kDLabel8, null);
        kDSeparator6.setBounds(new Rectangle(12, 64, 640, 10));
        this.add(kDSeparator6, null);
        btnAddItem.setBounds(new Rectangle(670, 72, 122, 21));
        this.add(btnAddItem, null);
        btnDeleItem.setBounds(new Rectangle(670, 99, 122, 21));
        this.add(btnDeleItem, null);
        kDSeparator7.setBounds(new Rectangle(10, 386, 646, 9));
        this.add(kDSeparator7, null);
        kdtItems.setBounds(new Rectangle(14, 80, 638, 295));
        this.add(kdtItems, null);
        comOptonAlignType.setBounds(new Rectangle(280, 40, 64, 19));
        this.add(comOptonAlignType, null);
        kDLabel1.setBounds(new Rectangle(230, 40, 54, 19));
        this.add(kDLabel1, null);
        comHorizonType.setBounds(new Rectangle(672, 74, 122, 19));
        this.add(comHorizonType, null);
        descriptionText.setBounds(new Rectangle(13, 68, 642, 509));
        this.add(descriptionText, null);
        kDLabel7.setBounds(new Rectangle(352, 40, 30, 19));
        this.add(kDLabel7, null);
        txtSubjectNumber.setBounds(new Rectangle(376, 36, 39, 19));
        this.add(txtSubjectNumber, null);
        comSubjectNumberShow.setBounds(new Rectangle(421, 40, 76, 19));
        this.add(comSubjectNumberShow, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
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
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);

    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("options.topic", String.class, this.kdtOptions, "topic.text");
		dataBinder.registerBinding("options.xLength", int.class, this.kdtOptions, "xLength.text");
		dataBinder.registerBinding("options.xFontSize", int.class, this.kdtOptions, "xFontSize.text");
		dataBinder.registerBinding("options.xFontName", String.class, this.kdtOptions, "xFontName.text");
		dataBinder.registerBinding("options.optionNumber", int.class, this.kdtOptions, "optionNumber.text");
		dataBinder.registerBinding("options", com.kingdee.eas.fdc.market.DocumentOptionInfo.class, this.kdtOptions, "userObject");
		dataBinder.registerBinding("options.isTopicInverse", boolean.class, this.kdtOptions, "isTopicInverse.text");
		dataBinder.registerBinding("options.id", com.kingdee.bos.util.BOSUuid.class, this.kdtOptions, "id.text");
		dataBinder.registerBinding("xFontSize", int.class, this.txtXFontSize, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.SubjectViewUI3Handler";
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
        this.editData = (com.kingdee.eas.fdc.market.DocumentItemInfo)ov;
    }

    /**
     * output setDataObject method
     */
    public void setDataObject(String key, IObjectValue dataObject)
    {
        super.setDataObject(key, dataObject);
        if (key.equalsIgnoreCase("documentSubject")) {
            this.documentSubject = (com.kingdee.eas.fdc.market.DocumentSubjectInfo)dataObject;
		}
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
		getValidateHelper().registerBindProperty("options.topic", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("options.xLength", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("options.xFontSize", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("options.xFontName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("options.optionNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("options", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("options.isTopicInverse", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("options.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("xFontSize", ValidateHelper.ON_SAVE);    		
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
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output btnOK_actionPerformed method
     */
    protected void btnOK_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output addLine_actionPerformed method
     */
    protected void addLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output deleLine_actionPerformed method
     */
    protected void deleLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnNO_actionPerformed method
     */
    protected void btnNO_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtSubjectTopic_actionPerformed method
     */
    protected void txtSubjectTopic_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comSubjectType_actionPerformed method
     */
    protected void comSubjectType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAddItem_actionPerformed method
     */
    protected void btnAddItem_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDeleItem_actionPerformed method
     */
    protected void btnDeleItem_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
    sic.add(new SelectorItemInfo("options.topic"));
    sic.add(new SelectorItemInfo("options.xLength"));
    sic.add(new SelectorItemInfo("options.xFontSize"));
    sic.add(new SelectorItemInfo("options.xFontName"));
    sic.add(new SelectorItemInfo("options.optionNumber"));
        sic.add(new SelectorItemInfo("options.*"));
//        sic.add(new SelectorItemInfo("options.number"));
    sic.add(new SelectorItemInfo("options.isTopicInverse"));
    sic.add(new SelectorItemInfo("options.id"));
        sic.add(new SelectorItemInfo("xFontSize"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "SubjectViewUI3");
    }




}