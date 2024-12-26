package com.dynamiciconchange

import com.facebook.react.TurboReactPackage
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.NativeModule
import com.facebook.react.module.model.ReactModuleInfoProvider
import com.facebook.react.module.model.ReactModuleInfo
import java.util.HashMap

class DynamicIconChangePackage : TurboReactPackage() {
    override fun getModule(name: String, reactContext: ReactApplicationContext): NativeModule? {
        return if (name == DynamicIconChangeModuleImpl.NAME) {
            DynamicIconChangeModule(reactContext)
        } else {
            null
        }
    }

    override fun getReactModuleInfoProvider(): ReactModuleInfoProvider {
        return ReactModuleInfoProvider {
            val moduleInfos: MutableMap<String, ReactModuleInfo> = HashMap()
            val isTurboModule: Boolean = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED
            moduleInfos[DynamicIconChangeModuleImpl.NAME] = ReactModuleInfo(
                DynamicIconChangeModuleImpl.NAME,
                DynamicIconChangeModuleImpl.NAME,
                false,
                false,
                false,
                isTurboModule
            )
            moduleInfos
        }
    }
}
