/*
 * DexPatcher - Copyright 2015-2017 Rodrigo Balerdi
 * (GNU General Public License version 3 or later)
 *
 * DexPatcher is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 */

package lanchon.dexpatcher.gradle.tasks

import groovy.transform.CompileStatic

import lanchon.dexpatcher.gradle.Resolver

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile

/*
Apktool v2.2.1 - a tool for reengineering Android apk files
usage: apktool [-q|--quiet OR -v|--verbose] b[uild] [options] <app_path>
 -a,--aapt <loc>         Loads aapt from specified location.
 -c,--copy-original      Copies original AndroidManifest.xml and META-INF. See project page for more info.
 -d,--debug              Sets android:debuggable to "true" in the APK's compiled manifest
 -f,--force-all          Skip changes detection and build all files.
 -o,--output <dir>       The name of apk that gets written. Default is dist/name.apk
 -p,--frame-path <dir>   Uses framework files located in <dir>.
*/

@CompileStatic
class BuildApkTask extends AbstractApktoolTask {

    def inputDir
    def apkFile
    def aaptFile
    def copyOriginal
    def forceDebuggableBuild
    def forceCleanBuild

    BuildApkTask() {
        super('build')
    }

    @InputDirectory File getInputDir() { Resolver.resolveNullableFile(project, inputDir) }
    @OutputFile File getApkFile() { Resolver.resolveNullableFile(project, apkFile) }
    @Optional @InputFile File getAaptFile() { Resolver.resolveNullableFile(project, aaptFile) }
    @Optional @Input Boolean getCopyOriginal() { Resolver.resolve(copyOriginal) as Boolean }
    @Optional @Input Boolean getForceDebuggableBuild() { Resolver.resolve(forceDebuggableBuild) as Boolean }
    @Optional @Input Boolean getForceCleanBuild() { Resolver.resolve(forceCleanBuild) as Boolean }

    @Override List<String> getArgs() {
        def args = super.getArgs()
        args.addAll(['--output', getApkFile() as String])
        if (getAaptFile()) args.addAll(['--aapt', getAaptFile() as String])
        if (getCopyOriginal()) args.add('--copy-original')
        if (getForceDebuggableBuild()) args.add('--debug')
        if (getForceCleanBuild()) args.add('--force-all')
        args.addAll(getExtraArgs())
        args.add(getInputDir() as String)
        return args
    }

    @Override void beforeExec() {
        deleteOutputFile getApkFile()
    }

    @Override void afterExec() {
        checkOutputFile getApkFile()
    }

}
