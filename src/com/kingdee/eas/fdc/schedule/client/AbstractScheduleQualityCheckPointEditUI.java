/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

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
public abstract class AbstractScheduleQualityCheckPointEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractScheduleQualityCheckPointEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCheckPoint;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCheckPost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCheckPercent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contScore;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCheckPoint;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCheckPost;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCheckPercent;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtScore;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker txtSubDate;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtCheckResult;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtCheckDemo;
    protected com.kingdee.eas.fdc.schedule.ScheduleQualityCheckPointInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractScheduleQualityCheckPointEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractScheduleQualityCheckPointEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCheckPoint = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCheckPost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCheckPercent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contScore = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.prmtCheckPoint = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCheckPost = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtCheckPercent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtScore = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtCreate = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSubDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtCheckResult = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtCheckDemo = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contCheckPoint.setName("contCheckPoint");
        this.contCheckPost.setName("contCheckPost");
        this.contCheckPercent.setName("contCheckPercent");
        this.contScore.setName("contScore");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.prmtCheckPoint.setName("prmtCheckPoint");
        this.prmtCheckPost.setName("prmtCheckPost");
        this.txtCheckPercent.setName("txtCheckPercent");
        this.txtScore.setName("txtScore");
        this.prmtCreate.setName("prmtCreate");
        this.txtSubDate.setName("txtSubDate");
        this.txtCheckResult.setName("txtCheckResult");
        this.txtCheckDemo.setName("txtCheckDemo");
        // CoreUI		
        this.setPreferredSize(new Dimension(680,320));		
        this.btnAddNew.setVisible(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnNextPerson.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.btnAudit.setVisible(false);		
        this.btnUnAudit.setVisible(false);		
        this.btnCalculator.setVisible(false);
        // contCheckPoint		
        this.contCheckPoint.setBoundLabelText(resHelper.getString("contCheckPoint.boundLabelText"));		
        this.contCheckPoint.setBoundLabelLength(100);		
        this.contCheckPoint.setBoundLabelUnderline(true);
        // contCheckPost		
        this.contCheckPost.setBoundLabelText(resHelper.getString("contCheckPost.boundLabelText"));		
        this.contCheckPost.setBoundLabelLength(100);		
        this.contCheckPost.setBoundLabelUnderline(true);
        // contCheckPercent		
        this.contCheckPercent.setBoundLabelText(resHelper.getString("contCheckPercent.boundLabelText"));		
        this.contCheckPercent.setBoundLabelLength(100);		
        this.contCheckPercent.setBoundLabelUnderline(true);
        // contScore		
        this.contScore.setBoundLabelText(resHelper.getString("contScore.boundLabelText"));		
        this.contScore.setBoundLabelLength(100);		
        this.contScore.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDScrollPane1
        // kDScrollPane2
        // prmtCheckPoint		
        this.prmtCheckPoint.setRequired(true);
        this.prmtCheckPoint.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCheckPoint_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtCheckPost		
        this.prmtCheckPost.setQueryInfo("com.kingdee.eas.fdc.pm.appSpecialPostQuery");		
        this.prmtCheckPost.setRequired(true);
        // txtCheckPercent
        // txtScore		
        this.txtScore.setPrecision(0);
        this.txtScore.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtScore_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtCreate
        // txtSubDate
        // txtCheckResult
        // txtCheckDemo
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
        this.setBounds(new Rectangle(10, 10, 680, 320));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 680, 320));
        contCheckPoint.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contCheckPoint, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCheckPost.setBounds(new Rectangle(400, 10, 270, 19));
        this.add(contCheckPost, new KDLayout.Constraints(400, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCheckPercent.setBounds(new Rectangle(10, 35, 270, 19));
        this.add(contCheckPercent, new KDLayout.Constraints(10, 35, 270, 19, 0));
        contScore.setBounds(new Rectangle(400, 35, 270, 19));
        this.add(contScore, new KDLayout.Constraints(400, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(10, 60, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(10, 60, 270, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(10, 172, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(10, 172, 270, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(10, 290, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(10, 290, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(400, 290, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(400, 290, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDScrollPane1.setBounds(new Rectangle(14, 197, 653, 86));
        this.add(kDScrollPane1, new KDLayout.Constraints(14, 197, 653, 86, 0));
        kDScrollPane2.setBounds(new Rectangle(14, 85, 653, 81));
        this.add(kDScrollPane2, new KDLayout.Constraints(14, 85, 653, 81, 0));
        //contCheckPoint
        contCheckPoint.setBoundEditor(prmtCheckPoint);
        //contCheckPost
        contCheckPost.setBoundEditor(prmtCheckPost);
        //contCheckPercent
        contCheckPercent.setBoundEditor(txtCheckPercent);
        //contScore
        contScore.setBoundEditor(txtScore);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(prmtCreate);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtSubDate);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtCheckResult, null);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtCheckDemo, null);

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
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
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
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("checkPoint", com.kingdee.eas.fdc.pm.QualityCheckPointInfo.class, this.prmtCheckPoint, "data");
		dataBinder.registerBinding("checkPost", com.kingdee.eas.fdc.pm.SpecialPostInfo.class, this.prmtCheckPost, "data");
		dataBinder.registerBinding("checkPercent", java.math.BigDecimal.class, this.txtCheckPercent, "value");
		dataBinder.registerBinding("score", java.math.BigDecimal.class, this.txtScore, "value");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreate, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.txtSubDate, "value");
		dataBinder.registerBinding("checkResult", String.class, this.txtCheckResult, "text");
		dataBinder.registerBinding("checkPoint.checkCriterion", String.class, this.txtCheckDemo, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.ScheduleQualityCheckPointEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.ScheduleQualityCheckPointInfo)ov;
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
		getValidateHelper().registerBindProperty("checkPoint", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("checkPost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("checkPercent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("score", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("checkResult", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("checkPoint.checkCriterion", ValidateHelper.ON_SAVE);    		
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
     * output prmtCheckPoint_dataChanged method
     */
    protected void prmtCheckPoint_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtScore_dataChanged method
     */
    protected void txtScore_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("checkPoint.*"));
        sic.add(new SelectorItemInfo("checkPost.*"));
        sic.add(new SelectorItemInfo("checkPercent"));
        sic.add(new SelectorItemInfo("score"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("checkResult"));
        sic.add(new SelectorItemInfo("checkPoint.checkCriterion"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "ScheduleQualityCheckPointEditUI");
    }




}