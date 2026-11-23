# Keep GeckoView's public Java API and classes that may be reached
# through GeckoView's internal reflection/JNI integration.
-keep class org.mozilla.geckoview.** { *; }

# Preserve metadata used by annotations, generic signatures and callbacks.
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod
