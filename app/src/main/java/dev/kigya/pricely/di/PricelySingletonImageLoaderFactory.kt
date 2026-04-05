package dev.kigya.pricely.di

import android.content.Context
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.request.crossfade
import coil3.svg.SvgDecoder

class PricelySingletonImageLoaderFactory : SingletonImageLoader.Factory {

    override fun newImageLoader(context: Context): ImageLoader =
        ImageLoader.Builder(context).components { add(SvgDecoder.Factory()) }.crossfade(true).build()
}
