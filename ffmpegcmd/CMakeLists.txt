# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.10.2)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
        ffmpeg-cmd

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).

        src/main/cpp/ffmpeg/cmdutils.c
        src/main/cpp/ffmpeg/ffmpeg.c
        src/main/cpp/ffmpeg/ffmpeg_filter.c
        src/main/cpp/ffmpeg/ffmpeg_opt.c

        src/main/cpp/ffmpeg_cmd.c

        src/main/cpp/ffmpeg_thread.c)

set(ffmpeg_lib_DIR ${CMAKE_CURRENT_SOURCE_DIR}/src/main/ffmpeg_lib/${ANDROID_ABI})
add_library( libavcodec
        SHARED
        IMPORTED )
set_target_properties( libavcodec
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_DIR}/libavcodec.so)
add_library( libavfilter
        SHARED
        IMPORTED )
set_target_properties( libavfilter
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_DIR}/libavfilter.so)
add_library( libavformat
        SHARED
        IMPORTED )
set_target_properties( libavformat
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_DIR}/libavformat.so)
add_library( libavutil
        SHARED
        IMPORTED )
set_target_properties( libavutil
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_DIR}/libavutil.so )
add_library( libswresample
        SHARED
        IMPORTED )
set_target_properties( libswresample
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_DIR}/libswresample.so )
add_library( libswscale
        SHARED
        IMPORTED )
set_target_properties( libswscale
        PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_lib_DIR}/libswscale.so )

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")

include_directories(src/main/cpp)
include_directories(src/main/cpp/ffmpeg)
include_directories(src/main/cpp/include)

find_library( # Sets the name of the path variable.
        log-lib
        log )

target_link_libraries( # Specifies the target library.
        ffmpeg-cmd
        libavcodec
        libavfilter
        libavformat
        libavutil
        libswresample
        libswscale
        -landroid #native_window
        -ljnigraphics #bitmap
        -lOpenSLES #openSLES
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib} )