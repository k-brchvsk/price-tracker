# Keep generated serializers used by Kotlin serialization in networking and navigation.
-keepclassmembers class **$$serializer { *; }
-keepclassmembers class * {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep source line information to make release stack traces readable during review.
-keepattributes SourceFile,LineNumberTable