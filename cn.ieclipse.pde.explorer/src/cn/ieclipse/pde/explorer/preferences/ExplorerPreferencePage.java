/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ieclipse.pde.explorer.preferences;

import java.net.URL;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import cn.ieclipse.pde.explorer.ExplorerPlugin;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class ExplorerPreferencePage extends FieldEditorPreferencePage
        implements IWorkbenchPreferencePage {
        
    public ExplorerPreferencePage() {
        super(GRID);
        setPreferenceStore(ExplorerPlugin.getDefault().getPreferenceStore());
        setDescription(Messages.ExplorerPreferencePage_desc
                + System.getProperty("line.separator") //$NON-NLS-1$
                + Messages.ExplorerPreferencePage_eg);
                
    }
    
    /**
     * Creates the field editors. Field editors are abstractions of the common
     * GUI blocks needed to manipulate various types of preferences. Each field
     * editor knows how to save and restore itself.
     */
    public void createFieldEditors() {
        addField(new MyStringFieldEditor(PreferenceConstants.EXPLORER_CMD,
                Messages.ExplorerPreferencePage_cmd, getFieldEditorParent()));
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
    }
    
    private class MyStringFieldEditor extends StringFieldEditor {
        
        public MyStringFieldEditor(String name, String labelText,
                Composite parent) {
            super(name, labelText, parent);
        }
        
        @Override
        protected void doFillIntoGrid(Composite parent, int numColumns) {
            super.doFillIntoGrid(parent, numColumns);
            GridData gd;
            Link l = new Link(parent, SWT.NONE);
            l.setText(Messages.ExplorerPreferencePage_note);
            gd = new GridData(GridData.FILL_HORIZONTAL);
            gd.horizontalSpan = numColumns;
            l.setLayoutData(gd);
            l.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    String url = e.text;
                    if (url.indexOf("://") < 0) {
                        url = "http://" + url;
                    }
                    openBrowser(url);
                }
                
                private void openBrowser(String url) {
                    IWorkbenchBrowserSupport support = PlatformUI.getWorkbench()
                            .getBrowserSupport();
                    IWebBrowser browser;
                    try {
                        browser = support.getExternalBrowser();
                        browser.openURL(new URL(url));
                    } catch (PartInitException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            });
        }
    }
    
}