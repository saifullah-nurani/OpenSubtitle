# Add project specific ProGuard rules here.
# Preserve core interfaces and data classes
-keep public class io.github.saifullah.nurani.opensubtitle.core.** {
    public *;
}

-keep class io.github.saifullah.nurani.opensubtitle.core.HttpResponse { *; }