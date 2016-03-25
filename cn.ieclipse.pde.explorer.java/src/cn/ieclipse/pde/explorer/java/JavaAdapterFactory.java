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
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;

import cn.ieclipse.pde.explorer.AdapterFactory;
import cn.ieclipse.pde.explorer.Explorer;
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
public class JavaAdapterFactory extends AdapterFactory {
    
    @Override
    public IExplorable getExplorable(Object obj) {
        String path = null;
        if (obj instanceof IJavaElement) {
            // java project.
            if (obj instanceof IJavaProject) {
                path = ((IJavaProject) obj).getProject().getLocation()
                        .toOSString();
                return new Explorer(path, null);
            }
            // jar resource is null
            else if (obj instanceof JarPackageFragmentRoot) {
                String file = ((IPackageFragmentRoot) obj).getPath()
                        .toOSString();
                // get folder
                return new Explorer(null, file);
            }
            else if (obj instanceof IPackageFragmentRoot) {
                // src folder
                IPackageFragmentRoot src = ((IPackageFragmentRoot) obj);
                IProject p = src.getJavaProject().getProject();
                String prjPath = p.getLocation().toOSString();
                path = new File(prjPath, src.getElementName())
                        .getAbsolutePath();
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
        return null;
    }
    
}
