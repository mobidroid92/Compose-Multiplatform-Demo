package com.myapplication.pressentation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.util.DebugLogger
import coil3.util.Logger
import com.myapplication.model.utils.isDebug
import com.myapplication.pressentation.characters.CharactersGraph
import com.myapplication.pressentation.characters.CharactersParentRoot
import com.myapplication.pressentation.welcome.WelcomeGraph
import com.myapplication.pressentation.welcome.WelcomeScreenRoot
import okio.FileSystem
import org.koin.compose.KoinContext

@OptIn(ExperimentalCoilApi::class)
@Composable
fun App() {
    MaterialTheme {

        setSingletonImageLoaderFactory { context ->
            getAsyncImageLoader(context)
        }

        KoinContext {

            val navController = rememberNavController()

            AppLaunchNavigationStack(navController)
        }
    }
}

@Composable
private fun AppLaunchNavigationStack(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = WelcomeGraph.WelcomeScreen
    ) {
        composable<WelcomeGraph.WelcomeScreen> {
            WelcomeScreenRoot(navController)
        }

        composable<CharactersGraph.Parent> {
            CharactersParentRoot()
        }
    }
}

private fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).memoryCachePolicy(CachePolicy.ENABLED).memoryCache {
        newMemoryCache(context)
    }.diskCachePolicy(CachePolicy.ENABLED).networkCachePolicy(CachePolicy.ENABLED).diskCache {
        newDiskCache()
    }.logger(newLogger()).build()

private fun newDiskCache(): DiskCache {
    return DiskCache.Builder()
        .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / DISK_CACHE_DIR_NAME)
        .maxSizeBytes(MAX_DISK_CACHE_SIZE)
        .build()
}

private fun newMemoryCache(context: PlatformContext): MemoryCache {
    return MemoryCache.Builder()
        .maxSizePercent(context, MAX_MEMORY_CACHE_PERCENT)
        .strongReferencesEnabled(true)
        .build()
}

private fun newLogger(): Logger? = if(isDebug) DebugLogger() else null

private const val MAX_DISK_CACHE_SIZE = 1024L * 1024 * 512 // 512MB
private const val MAX_MEMORY_CACHE_PERCENT = 0.3 // 1/3 of the available memory
private const val DISK_CACHE_DIR_NAME = "image_cache"