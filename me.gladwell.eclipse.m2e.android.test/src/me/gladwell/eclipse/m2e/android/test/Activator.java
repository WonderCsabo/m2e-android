/*******************************************************************************
 * Copyright (c) 2009, 2010 Ricardo Gladwell and Hugo Josefson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package me.gladwell.eclipse.m2e.android.test;

import java.io.File;

import me.gladwell.eclipse.m2e.android.Log;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.android.ide.eclipse.adt.internal.preferences.AdtPrefs;
import com.android.sdkstats.DdmsPreferenceStore;

public class Activator extends AbstractUIPlugin implements IStartup {

    public static final String PLUGIN_ID = "me.gladwell.eclipse.m2e.android.test";
    
    static {
        DdmsPreferenceStore prefStore = new DdmsPreferenceStore();
        prefStore.generateNewPingId();
    }

    private static Activator plugin;

    public Activator() {
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        DdmsPreferenceStore prefStore = new DdmsPreferenceStore();
        AdtPrefs.getPrefs().setSdkLocation(new File(System.getenv("ANDROID_HOME")));
        prefStore.setPingTime("hello", System.currentTimeMillis());
        Log.warn("adtUsed" + prefStore.isAdtUsed());
        Log.warn("pingId" + prefStore.getPingId());
        prefStore.setLastSdkPath(System.getenv("ANDROID_HOME"));
        prefStore.setAdtUsed(true);
        prefStore.generateNewPingId();
        
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        
        final IWorkbench workbench = PlatformUI.getWorkbench();
        final Shell[] shells = workbench.getDisplay().getShells();
        
        workbench.getDisplay().timerExec(2000, new Runnable() {
            @Override
            public void run() {
                @SuppressWarnings("unused")
                final Shell lastShell = findLastShell(workbench.getDisplay().getShells(), shells);

            }
            
            private Shell findLastShell(Shell[] currentShells, Shell[] oldShells) {
                CheckNext: for (final Shell cs : currentShells) {
                    for (final Shell os : oldShells) {
                        if (os == cs) {
                            continue CheckNext;
                        }
                    }
                    return cs;
                }
                return null;
            }
        });
    }
    
    
    

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    public static Activator getDefault() {
        return plugin;
    }

    @Override
    public void earlyStartup() {
        // TODO Auto-generated method stub
        
    }

}
