#include <string.h>
#include <jni.h>
 
jstring Java_nitchie_arruda_gurnee_chiluka_firstnxtproject_MainActivity_invokeNativeFunction(JNIEnv* env, jobject javaThis) {
  return (*env)->NewStringUTF(env, "Welcome to the NXT Controller! :)");
}