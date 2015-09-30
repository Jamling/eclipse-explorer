/*
 * Copyright 2012-2013 Jamling(li.jamling@gmail.com).
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
package cn.ieclipse.pde.explorer.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.team.ui.synchronize.ISynchronizeModelElement;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import cn.ieclipse.pde.explorer.Explorer;
import cn.ieclipse.pde.explorer.ExplorerPlugin;
import cn.ieclipse.pde.explorer.IExplorable;

/**
 * @author melord
 *         
 */
public class OpenExplorerHandler extends AbstractHandler {
    
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
        ISelection sel = window.getSelectionService().getSelection();
        if (sel instanceof IStructuredSelection) {
            Object obj = ((IStructuredSelection) sel).getFirstElement();
            IResource resource = null;
            String file;
            String path;
            IExplorable explor = null;
            // common resource file
            if (obj instanceof IFile) {
                resource = (IResource) obj;
                file = resource.getLocation().toOSString();
                explor = new Explorer(null, file);
            }
            else if (obj instanceof IProject) {
                IProject prj = (IProject) obj;
                path = prj.getLocation().toOSString();
                explor = new Explorer(path, null);
            }
            // other resource such as folder,project
            else if (obj instanceof IResource) {
                resource = (IResource) obj;
                path = resource.getLocation().toOSString();
                explor = new Explorer(null, path);
            }
            else if (obj instanceof IExplorable) {
                explor = (IExplorable) explor;
            }
            // explorer java element, contain field,method,package
//			else if (obj instanceof IJavaElement) {
//				
//
//			}
            // explorer team ui resource
            else if (obj instanceof ISynchronizeModelElement) {
                resource = ((ISynchronizeModelElement) obj).getResource();
                path = resource.getLocation().toOSString();
            }
            else {
                explor = Platform.getAdapterManager().getAdapter(obj, IExplorable.class);
            }
            // process
            if (explor != null) {
                ExplorerPlugin.explorer(explor.getFolder(), explor.getFile());
            }
        }
        return null;
    }
    
}
