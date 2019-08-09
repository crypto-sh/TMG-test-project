#include <jni.h>
#include <string>

extern "C"
jstring
Java_nl_tmg_dutchnews_utils_Apps_apiKey(
        JNIEnv *env,
        jobject /* this */) {
    std::string clientId= "26e75ca12c4d4f5e8e4854d1e25865bb";
    return env->NewStringUTF(clientId.c_str());
}

