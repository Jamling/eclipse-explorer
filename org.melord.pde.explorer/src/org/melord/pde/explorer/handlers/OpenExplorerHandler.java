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
package org.melord.pde.explorer.handlers;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.team.ui.synchronize.ISynchronizeModelElement;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.melord.pde.explorer.Activator;
import org.melord.pde.explorer.preferences.PreferenceConstants;

/**
 * @author melord
 * 
 */
public class OpenExplorerHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		ISelection sel = window.getSelectionService().getSelection();
		if (sel instanceof IStructuredSelection) {
			Object obj = ((IStructuredSelection) sel).getFirstElement();
			IResource resource = null;
			String path = null;
			String file = null;
			// common resource file
			if (obj instanceof IFile) {
				resource = (IResource) obj;
				file = resource.getLocation().toOSString();
				path = file.substring(0, file.lastIndexOf(File.separator));
			} else if (obj instanceof IProject) {
				IProject prj = (IProject) obj;
				path = prj.getLocation().toOSString();
			}
			// other resource such as folder,project
			else if (obj instanceof IResource) {
				resource = (IResource) obj;
				path = resource.getLocation().toOSString();
			}
			// explorer java element, contain field,method,package
			else if (obj instanceof IJavaElement) {
				// java project.
				if (obj instanceof IJavaProject) {
					path = ((IJavaProject) obj).getProject().getLocation()
							.toOSString();
				}
				// jar resource is null
				else if (obj instanceof JarPackageFragmentRoot) {
					file = ((IPackageFragmentRoot) obj).getPath().toOSString();
					// get folder
					path = file.substring(0, file.lastIndexOf(File.separator));
				} else if (obj instanceof IPackageFragmentRoot) {
					// src folder
					IPackageFragmentRoot src = ((IPackageFragmentRoot) obj);
					IProject p = src.getJavaProject().getProject();
					String prjPath = p.getLocation().toOSString();
					path = new File(prjPath, src.getElementName()).getAbsolutePath();
					// System.out.println(path);
				} else if (obj instanceof IPackageFragment) {// other : package
					resource = ((IPackageFragment) obj).getResource();
					path = resource.getLocation().toOSString();
				} else {// member:filed:
					resource = ((IJavaElement) obj).getResource();
					file = resource.getLocation().toOSString();
					// get folder
					path = file.substring(0, file.lastIndexOf(File.separator));
				}

			}
			// explorer team ui resource
			else if (obj instanceof ISynchronizeModelElement) {
				resource = ((ISynchronizeModelElement) obj).getResource();
			}
			// process
			if (path != null) {
				// System.out.println(path);
				String cmd = null;
				try {
					cmd = Activator.getDefault().getPreferenceStore()
							.getString(PreferenceConstants.EXPLORER_CMD).trim();
					if (cmd.toLowerCase().startsWith("explorer")) {
						String winCmd = String.format("%s %s", cmd, path);
						if (file != null) {
							winCmd = String.format("%s /select,%s", cmd, file);
						}
						Runtime.getRuntime().exec(winCmd);
						return null;
					}
					Runtime.getRuntime().exec(cmd.trim() + " " + path); //$NON-NLS-1$
				} catch (IOException e) {
					//
					e.printStackTrace();
				}
			}

		}
		return null;
	}

}
