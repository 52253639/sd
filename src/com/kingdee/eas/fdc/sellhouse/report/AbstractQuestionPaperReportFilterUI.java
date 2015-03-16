/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

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
public abstract class AbstractQuestionPaperReportFilterUI extends com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractQuestionPaperReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPaper;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuestion1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuestion2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStayArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWorkArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contClassify;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIntentionType;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPaper;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtQuestion1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtQuestion2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtStayArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtWorkArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtClassify;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtIntentionType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contItem1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCommerceChanceStage;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsShowAll;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelTime;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton isAll;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton isDeal;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtItem1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCommerceChanceStage;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton btnByDate;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton btnByMonth;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton btnBySeason;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton btnByYear;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup btnGroup;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYearTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblYearTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblSeasonFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblSeasonTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblMonthTo;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiSeasonFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiSeasonTo;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpDateTo;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearTo;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearFrom;
    /**
     * output class constructor
     */
    public AbstractQuestionPaperReportFilterUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractQuestionPaperReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contPaper = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQuestion1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQuestion2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStayArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWorkArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contClassify = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIntentionType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.prmtPaper = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtQuestion1 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtQuestion2 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtStayArea = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtWorkArea = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtClassify = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtIntentionType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contItem1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCommerceChanceStage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.chkIsShowAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.panelTime = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.isAll = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.isDeal = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.prmtItem1 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCommerceChanceStage = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnByDate = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.btnByMonth = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.btnBySeason = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.btnByYear = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.btnGroup = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.contDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDateTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contYearTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contYearFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spiMonthFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiMonthTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.lblYearFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblYearTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblSeasonFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblSeasonTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblMonthFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblMonthTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spiSeasonFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiSeasonTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.dpDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dpDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.spiYearTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiYearFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDPanel3.setName("kDPanel3");
        this.kDPanel2.setName("kDPanel2");
        this.contPaper.setName("contPaper");
        this.contQuestion1.setName("contQuestion1");
        this.contQuestion2.setName("contQuestion2");
        this.contStayArea.setName("contStayArea");
        this.contWorkArea.setName("contWorkArea");
        this.contClassify.setName("contClassify");
        this.contIntentionType.setName("contIntentionType");
        this.kDLabel1.setName("kDLabel1");
        this.prmtPaper.setName("prmtPaper");
        this.prmtQuestion1.setName("prmtQuestion1");
        this.prmtQuestion2.setName("prmtQuestion2");
        this.prmtStayArea.setName("prmtStayArea");
        this.prmtWorkArea.setName("prmtWorkArea");
        this.prmtClassify.setName("prmtClassify");
        this.prmtIntentionType.setName("prmtIntentionType");
        this.contItem1.setName("contItem1");
        this.contCommerceChanceStage.setName("contCommerceChanceStage");
        this.kDPanel1.setName("kDPanel1");
        this.chkIsShowAll.setName("chkIsShowAll");
        this.panelTime.setName("panelTime");
        this.isAll.setName("isAll");
        this.isDeal.setName("isDeal");
        this.prmtItem1.setName("prmtItem1");
        this.prmtCommerceChanceStage.setName("prmtCommerceChanceStage");
        this.btnByDate.setName("btnByDate");
        this.btnByMonth.setName("btnByMonth");
        this.btnBySeason.setName("btnBySeason");
        this.btnByYear.setName("btnByYear");
        this.contDateFrom.setName("contDateFrom");
        this.contDateTo.setName("contDateTo");
        this.contYearTo.setName("contYearTo");
        this.contYearFrom.setName("contYearFrom");
        this.spiMonthFrom.setName("spiMonthFrom");
        this.spiMonthTo.setName("spiMonthTo");
        this.lblYearFrom.setName("lblYearFrom");
        this.lblYearTo.setName("lblYearTo");
        this.lblSeasonFrom.setName("lblSeasonFrom");
        this.lblSeasonTo.setName("lblSeasonTo");
        this.lblMonthFrom.setName("lblMonthFrom");
        this.lblMonthTo.setName("lblMonthTo");
        this.spiSeasonFrom.setName("spiSeasonFrom");
        this.spiSeasonTo.setName("spiSeasonTo");
        this.dpDateFrom.setName("dpDateFrom");
        this.dpDateTo.setName("dpDateTo");
        this.spiYearTo.setName("spiYearTo");
        this.spiYearFrom.setName("spiYearFrom");
        // CustomerQueryPanel
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,0,255),1), resHelper.getString("kDPanel3.border.title")));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,0,255),1), resHelper.getString("kDPanel2.border.title")));
        // contPaper		
        this.contPaper.setBoundLabelText(resHelper.getString("contPaper.boundLabelText"));		
        this.contPaper.setBoundLabelLength(100);		
        this.contPaper.setBoundLabelUnderline(true);
        // contQuestion1		
        this.contQuestion1.setBoundLabelText(resHelper.getString("contQuestion1.boundLabelText"));		
        this.contQuestion1.setBoundLabelLength(100);		
        this.contQuestion1.setBoundLabelUnderline(true);
        // contQuestion2		
        this.contQuestion2.setBoundLabelText(resHelper.getString("contQuestion2.boundLabelText"));		
        this.contQuestion2.setBoundLabelLength(100);		
        this.contQuestion2.setBoundLabelUnderline(true);
        // contStayArea		
        this.contStayArea.setBoundLabelText(resHelper.getString("contStayArea.boundLabelText"));		
        this.contStayArea.setBoundLabelLength(100);		
        this.contStayArea.setBoundLabelUnderline(true);
        // contWorkArea		
        this.contWorkArea.setBoundLabelText(resHelper.getString("contWorkArea.boundLabelText"));		
        this.contWorkArea.setBoundLabelLength(100);		
        this.contWorkArea.setBoundLabelUnderline(true);
        // contClassify		
        this.contClassify.setBoundLabelText(resHelper.getString("contClassify.boundLabelText"));		
        this.contClassify.setBoundLabelLength(100);		
        this.contClassify.setBoundLabelUnderline(true);
        // contIntentionType		
        this.contIntentionType.setBoundLabelText(resHelper.getString("contIntentionType.boundLabelText"));		
        this.contIntentionType.setBoundLabelLength(100);		
        this.contIntentionType.setBoundLabelUnderline(true);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));		
        this.kDLabel1.setForeground(new java.awt.Color(255,0,0));
        // prmtPaper
        this.prmtPaper.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtPaper_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtQuestion1
        this.prmtQuestion1.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtQuestion1_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtQuestion1.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtQuestion1_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtQuestion2
        this.prmtQuestion2.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtQuestion2_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtQuestion2.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtQuestion2_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtStayArea		
        this.prmtStayArea.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECusAssistantDataQuery");
        // prmtWorkArea		
        this.prmtWorkArea.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECusAssistantDataQuery");
        // prmtClassify		
        this.prmtClassify.setQueryInfo("com.kingdee.eas.fdc.sellhouse.report.F7ClassifyQuery");
        // prmtIntentionType		
        this.prmtIntentionType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelForSHEQuery");
        // contItem1		
        this.contItem1.setBoundLabelText(resHelper.getString("contItem1.boundLabelText"));		
        this.contItem1.setBoundLabelLength(100);		
        this.contItem1.setBoundLabelUnderline(true);
        // contCommerceChanceStage		
        this.contCommerceChanceStage.setBoundLabelText(resHelper.getString("contCommerceChanceStage.boundLabelText"));		
        this.contCommerceChanceStage.setBoundLabelLength(100);		
        this.contCommerceChanceStage.setBoundLabelUnderline(true);
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(128,128,128),1), resHelper.getString("kDPanel1.border.title")));
        // chkIsShowAll		
        this.chkIsShowAll.setText(resHelper.getString("chkIsShowAll.text"));
        this.chkIsShowAll.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    chkIsShowAll_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // panelTime		
        this.panelTime.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("panelTime.border.title")));
        // isAll		
        this.isAll.setText(resHelper.getString("isAll.text"));
        this.isAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    isAll_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // isDeal		
        this.isDeal.setText(resHelper.getString("isDeal.text"));
        this.isDeal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    isDeal_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.isAll);
        this.kDButtonGroup1.add(this.isDeal);
        // prmtItem1
        // prmtCommerceChanceStage		
        this.prmtCommerceChanceStage.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CommerceAssistantForStageQuery");
        // btnByDate		
        this.btnByDate.setText(resHelper.getString("btnByDate.text"));
        this.btnByDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnByDate_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnByMonth		
        this.btnByMonth.setText(resHelper.getString("btnByMonth.text"));
        this.btnByMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnByMonth_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnBySeason		
        this.btnBySeason.setText(resHelper.getString("btnBySeason.text"));
        this.btnBySeason.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnBySeason_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnByYear		
        this.btnByYear.setText(resHelper.getString("btnByYear.text"));
        this.btnByYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnByYear_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnGroup
        this.btnGroup.add(this.btnByDate);
        this.btnGroup.add(this.btnByMonth);
        this.btnGroup.add(this.btnBySeason);
        this.btnGroup.add(this.btnByYear);
        // contDateFrom		
        this.contDateFrom.setBoundLabelText(resHelper.getString("contDateFrom.boundLabelText"));		
        this.contDateFrom.setBoundLabelLength(100);		
        this.contDateFrom.setBoundLabelUnderline(true);
        // contDateTo		
        this.contDateTo.setBoundLabelText(resHelper.getString("contDateTo.boundLabelText"));		
        this.contDateTo.setBoundLabelLength(100);		
        this.contDateTo.setBoundLabelUnderline(true);
        // contYearTo		
        this.contYearTo.setBoundLabelText(resHelper.getString("contYearTo.boundLabelText"));		
        this.contYearTo.setBoundLabelLength(100);		
        this.contYearTo.setBoundLabelUnderline(true);
        // contYearFrom		
        this.contYearFrom.setBoundLabelText(resHelper.getString("contYearFrom.boundLabelText"));		
        this.contYearFrom.setBoundLabelLength(100);		
        this.contYearFrom.setBoundLabelUnderline(true);
        // spiMonthFrom
        // spiMonthTo
        // lblYearFrom		
        this.lblYearFrom.setText(resHelper.getString("lblYearFrom.text"));
        // lblYearTo		
        this.lblYearTo.setText(resHelper.getString("lblYearTo.text"));
        // lblSeasonFrom		
        this.lblSeasonFrom.setText(resHelper.getString("lblSeasonFrom.text"));
        // lblSeasonTo		
        this.lblSeasonTo.setText(resHelper.getString("lblSeasonTo.text"));
        // lblMonthFrom		
        this.lblMonthFrom.setText(resHelper.getString("lblMonthFrom.text"));
        // lblMonthTo		
        this.lblMonthTo.setText(resHelper.getString("lblMonthTo.text"));
        // spiSeasonFrom
        // spiSeasonTo
        // dpDateFrom
        // dpDateTo
        // spiYearTo
        // spiYearFrom
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
        this.setBounds(new Rectangle(10, 10, 500, 540));
        this.setLayout(null);
        kDPanel3.setBounds(new Rectangle(9, 324, 476, 208));
        this.add(kDPanel3, null);
        kDPanel2.setBounds(new Rectangle(9, 25, 476, 290));
        this.add(kDPanel2, null);
        //kDPanel3
        kDPanel3.setLayout(null);        contPaper.setBounds(new Rectangle(15, 15, 211, 19));
        kDPanel3.add(contPaper, null);
        contQuestion1.setBounds(new Rectangle(15, 45, 211, 19));
        kDPanel3.add(contQuestion1, null);
        contQuestion2.setBounds(new Rectangle(251, 45, 211, 19));
        kDPanel3.add(contQuestion2, null);
        contStayArea.setBounds(new Rectangle(15, 105, 447, 19));
        kDPanel3.add(contStayArea, null);
        contWorkArea.setBounds(new Rectangle(15, 75, 447, 19));
        kDPanel3.add(contWorkArea, null);
        contClassify.setBounds(new Rectangle(15, 168, 447, 19));
        kDPanel3.add(contClassify, null);
        contIntentionType.setBounds(new Rectangle(15, 135, 447, 19));
        kDPanel3.add(contIntentionType, null);
        kDLabel1.setBounds(new Rectangle(251, 15, 188, 19));
        kDPanel3.add(kDLabel1, null);
        //contPaper
        contPaper.setBoundEditor(prmtPaper);
        //contQuestion1
        contQuestion1.setBoundEditor(prmtQuestion1);
        //contQuestion2
        contQuestion2.setBoundEditor(prmtQuestion2);
        //contStayArea
        contStayArea.setBoundEditor(prmtStayArea);
        //contWorkArea
        contWorkArea.setBoundEditor(prmtWorkArea);
        //contClassify
        contClassify.setBoundEditor(prmtClassify);
        //contIntentionType
        contIntentionType.setBoundEditor(prmtIntentionType);
        //kDPanel2
        kDPanel2.setLayout(null);        contItem1.setBounds(new Rectangle(15, 14, 447, 19));
        kDPanel2.add(contItem1, null);
        contCommerceChanceStage.setBounds(new Rectangle(15, 36, 447, 19));
        kDPanel2.add(contCommerceChanceStage, null);
        kDPanel1.setBounds(new Rectangle(15, 195, 447, 52));
        kDPanel2.add(kDPanel1, null);
        chkIsShowAll.setBounds(new Rectangle(15, 256, 178, 19));
        kDPanel2.add(chkIsShowAll, null);
        panelTime.setBounds(new Rectangle(15, 94, 447, 94));
        kDPanel2.add(panelTime, null);
        isAll.setBounds(new Rectangle(32, 65, 140, 19));
        kDPanel2.add(isAll, null);
        isDeal.setBounds(new Rectangle(201, 65, 140, 19));
        kDPanel2.add(isDeal, null);
        //contItem1
        contItem1.setBoundEditor(prmtItem1);
        //contCommerceChanceStage
        contCommerceChanceStage.setBoundEditor(prmtCommerceChanceStage);
        //kDPanel1
        kDPanel1.setLayout(null);        btnByDate.setBounds(new Rectangle(17, 18, 61, 19));
        kDPanel1.add(btnByDate, null);
        btnByMonth.setBounds(new Rectangle(102, 19, 50, 19));
        kDPanel1.add(btnByMonth, null);
        btnBySeason.setBounds(new Rectangle(183, 19, 56, 19));
        kDPanel1.add(btnBySeason, null);
        btnByYear.setBounds(new Rectangle(267, 19, 71, 19));
        kDPanel1.add(btnByYear, null);
        //panelTime
        panelTime.setLayout(null);        contDateFrom.setBounds(new Rectangle(14, 20, 270, 19));
        panelTime.add(contDateFrom, null);
        contDateTo.setBounds(new Rectangle(14, 52, 270, 19));
        panelTime.add(contDateTo, null);
        contYearTo.setBounds(new Rectangle(14, 52, 180, 19));
        panelTime.add(contYearTo, null);
        contYearFrom.setBounds(new Rectangle(14, 20, 180, 19));
        panelTime.add(contYearFrom, null);
        spiMonthFrom.setBounds(new Rectangle(219, 20, 49, 19));
        panelTime.add(spiMonthFrom, null);
        spiMonthTo.setBounds(new Rectangle(219, 52, 49, 19));
        panelTime.add(spiMonthTo, null);
        lblYearFrom.setBounds(new Rectangle(201, 20, 29, 19));
        panelTime.add(lblYearFrom, null);
        lblYearTo.setBounds(new Rectangle(201, 52, 29, 19));
        panelTime.add(lblYearTo, null);
        lblSeasonFrom.setBounds(new Rectangle(280, 20, 29, 19));
        panelTime.add(lblSeasonFrom, null);
        lblSeasonTo.setBounds(new Rectangle(280, 52, 29, 19));
        panelTime.add(lblSeasonTo, null);
        lblMonthFrom.setBounds(new Rectangle(280, 20, 29, 19));
        panelTime.add(lblMonthFrom, null);
        lblMonthTo.setBounds(new Rectangle(280, 52, 29, 19));
        panelTime.add(lblMonthTo, null);
        spiSeasonFrom.setBounds(new Rectangle(219, 20, 49, 19));
        panelTime.add(spiSeasonFrom, null);
        spiSeasonTo.setBounds(new Rectangle(219, 52, 49, 19));
        panelTime.add(spiSeasonTo, null);
        //contDateFrom
        contDateFrom.setBoundEditor(dpDateFrom);
        //contDateTo
        contDateTo.setBoundEditor(dpDateTo);
        //contYearTo
        contYearTo.setBoundEditor(spiYearTo);
        //contYearFrom
        contYearFrom.setBoundEditor(spiYearFrom);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.report.QuestionPaperReportFilterUIHandler";
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
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output prmtPaper_dataChanged method
     */
    protected void prmtPaper_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtQuestion1_willCommit method
     */
    protected void prmtQuestion1_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtQuestion1_willShow method
     */
    protected void prmtQuestion1_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtQuestion2_willShow method
     */
    protected void prmtQuestion2_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtQuestion2_willCommit method
     */
    protected void prmtQuestion2_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output chkIsShowAll_itemStateChanged method
     */
    protected void chkIsShowAll_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output isAll_actionPerformed method
     */
    protected void isAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output isDeal_actionPerformed method
     */
    protected void isDeal_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnByDate_actionPerformed method
     */
    protected void btnByDate_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnByMonth_actionPerformed method
     */
    protected void btnByMonth_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnBySeason_actionPerformed method
     */
    protected void btnBySeason_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnByYear_actionPerformed method
     */
    protected void btnByYear_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.report", "QuestionPaperReportFilterUI");
    }




}