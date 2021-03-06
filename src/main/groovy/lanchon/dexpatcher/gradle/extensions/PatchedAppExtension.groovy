/*
 * DexPatcher - Copyright 2015-2017 Rodrigo Balerdi
 * (GNU General Public License version 3 or later)
 *
 * DexPatcher is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 */

package lanchon.dexpatcher.gradle.extensions

import groovy.transform.CompileStatic

import org.gradle.api.Project

@CompileStatic
class PatchedAppExtension extends AbstractPatcherExtension {

    static final String EXTENSION_NAME = 'patchedApplication'

    boolean multiDexThreadedForMultiDexDebugBuilds = true
    boolean multiDexThreadedForAllDebugBuilds

    PatchedAppExtension(Project project, DexpatcherConfigExtension dexpatcherConfig) {
        super(project, dexpatcherConfig)
    }

}
