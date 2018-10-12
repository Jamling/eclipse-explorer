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
package cn.ieclipse.pde.explorer.java;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;

import cn.ieclipse.pde.explorer.AdapterFactory;
import cn.ieclipse.pde.explorer.Explorer;
import cn.ieclipse.pde.explorer.ExplorerPlugin;
import cn.ieclipse.pde.explorer.IExplorable;

/**
 * Migrated from cn.ieclipse.pde.explorer project.
 * <p>
 * Adapt {@link IJavaElement} to {@link IExplorable}
 * </p>
 * 
 * @author Jamling
 *         
 */
@SuppressWarnings("restriction")
public class JavaAdapterFactory extends AdapterFactory {
    
    @Override
    public IExplorable getExplorable(Object obj) {
        IExplorable ret = null;
        if (obj instanceof IJavaElement) {
            ret = getFromJava((IJavaElement) obj);
        }
        if (ret != null) {
            ret.setIdentifier("explorer4java");
        }
        return ret;
    }
    
    public IExplorable getFromJava(IJavaElement obj) {
        String path = null;
        // java project.
        if (obj instanceof IJavaProject) {
            path = ((IJavaProject) obj).getProject().getLocation().toOSString();
            return new Explorer(path, null);
        }
        // jar resource is null
        else if (obj instanceof JarPackageFragmentRoot) {
            IPackageFragmentRoot lib = ((IPackageFragmentRoot) obj);
            if (!lib.isExternal()) {
                IWorkspaceRoot root = lib.getJavaProject().getProject()
                        .getWorkspace().getRoot();
                IResource res = root.findMember(lib.getPath());
                if (res != null) {
                    return ExplorerPlugin.getFromResource(res);
                }
            }
            // external lib
            String file = lib.getPath().toOSString();
            return new Explorer(null, file);
        }
        else if (obj instanceof IPackageFragmentRoot) {
            // src folder
            IPackageFragmentRoot src = ((IPackageFragmentRoot) obj);
            IProject p = src.getJavaProject().getProject();
            String prjPath = p.getLocation().toOSString();
            IResource res = src.getResource();
            // Fix multi-folder source folder issue
            String srcospath = res.getProjectRelativePath().toOSString();
            path = new File(prjPath, srcospath).getAbsolutePath();
            // end fix
            return new Explorer(path, null);
            // System.out.println(path);
        }
        else if (obj instanceof IPackageFragment) {// other : package
            IResource resource = ((IPackageFragment) obj).getResource();
            path = resource.getLocation().toOSString();
            return new Explorer(path, null);
        }
        else {// member:filed:
            IResource resource = ((IJavaElement) obj).getResource();
            String file = resource.getLocation().toOSString();
            // get folder
            return new Explorer(null, file);
        }
    }
}
