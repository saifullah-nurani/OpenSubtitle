# Add project specific ProGuard rules here.
# Preserve okhttp implementation layer
-keep public class io.github.saifullah.nurani.opensubtitle.okhttp.** {
    public *;
}