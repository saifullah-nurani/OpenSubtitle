# Preserve all public classes and their public members in the main library package
-keep public class io.github.saifullah.nurani.opensubtitle.** {
    public *;
}

# Preserve all data classes used for parsing/response
-keep class io.github.saifullah.nurani.opensubtitle.data.** { *; }

# Preserve enums and their methods
-keepclassmembers enum io.github.saifullah.nurani.opensubtitle.** {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}