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
package cn.ieclipse.pde.explorer;

import java.util.Locale;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ExplorerPlugin extends AbstractUIPlugin {
	public static final int OS_WINDOWS = 0x01;
	public static final int OS_LINUX = 0x02;
	public static final int OS_UNKNOW = 0x03;
	// The plug-in ID
	public static final String PLUGIN_ID = "cn.ieclipse.pde.explorer";

	// The shared instance
	private static ExplorerPlugin plugin;

	/**
	 * The constructor
	 */
	public ExplorerPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static ExplorerPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	public static int getOS() {
		int type = OS_WINDOWS;
		String osName = System.getProperty("os.name").toLowerCase(Locale.US);
		if (osName.indexOf("linux") >= 0) {
			type = OS_LINUX;
		} else if (osName.indexOf("windows") >= 0) {
			type = OS_WINDOWS;
		} else {
			type = OS_UNKNOW;
		}
		return type;
	}
}
