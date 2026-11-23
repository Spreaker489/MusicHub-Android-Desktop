# Keep GeckoView's public Java API and classes that may be reached
# through GeckoView's internal reflection/JNI integration.
-keep class org.mozilla.geckoview.** { *; }

# Preserve metadata used by annotations, generic signatures and callbacks.
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

# SnakeYAML references java.beans APIs that are not part of Android's runtime.
# Suppress R8's missing-class diagnostics for these optional Java SE APIs.
# If YAML bean introspection is ever added to app code, it needs an Android-
# compatible implementation rather than relying on java.beans.
-dontwarn java.beans.**
