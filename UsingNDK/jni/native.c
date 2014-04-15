#include <string.h>
#include <jni.h>
 
jstring Java_com_example_usingndk_MainActivity_invokeNativeFunction(JNIEnv* env, jobject javaThis) {
  return (*env)->NewStringUTF(env, "Welcome to the NXT Controller! :)");
}