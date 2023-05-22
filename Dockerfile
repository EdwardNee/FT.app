FROM openjdk:11-jdk-slim-buster

USER root

ARG KEY_YANDEX
ARG YANDEX_CLID
ARG YANDEX_MAPKIT

# Just matched `app/build.gradle`
ENV ANDROID_COMPILE_SDK "30"

# Just matched `app/build.gradle`
ENV ANDROID_BUILD_TOOLS "30.0.2"

# Version from https://developer.android.com/studio/releases/sdk-tools
ENV ANDROID_SDK_TOOLS "7583922"
ENV ANDROID_HOME /android-sdk-linux
ENV PATH="${PATH}:/android-sdk-linux/platform-tools/"

# Make sure package is up to date
RUN apt-get update

# Install OS packages
RUN apt-get --quiet update --yes && \
    apt-get --quiet install --yes wget tar unzip lib32stdc++6 lib32z1 build-essential ruby ruby-dev graphicsmagick

# Install Android SDK
RUN wget --quiet --output-document=android-sdk.zip "https://dl.google.com/android/repository/commandlinetools-linux-${ANDROID_SDK_TOOLS}_latest.zip" && \
    unzip -d ${ANDROID_HOME} android-sdk.zip && \
    yes | ./android-sdk-linux/cmdline-tools/bin/sdkmanager "platforms;android-${ANDROID_COMPILE_SDK}" --sdk_root=android-sdk-linux/ && \
    yes | ./android-sdk-linux/cmdline-tools/bin/sdkmanager "build-tools;${ANDROID_BUILD_TOOLS}" --sdk_root=android-sdk-linux && \

    # Clean cache
    apt-get clean autoclean && \
    apt-get autoremove --yes && \
    rm -rf /var/lib/{apt,dpkg,cache,log}/ android-sdk.zip

# RUN chmod +x android-sdk-linux/cmdline-tools/bin/
RUN yes | ./android-sdk-linux/cmdline-tools/bin/sdkmanager "system-images;android-${ANDROID_COMPILE_SDK};google_apis;x86" --sdk_root=android-sdk-linux
# Set up the workspace and copy your Android project into the container
RUN ./android-sdk-linux/cmdline-tools/bin/sdkmanager --list --sdk_root=android-sdk-linux

WORKDIR /app

COPY . /app

RUN chmod +x ./gradlew

# RUN avdmanager list avd

RUN ls -l

# Just check gradle version.
RUN ./gradlew -v

RUN chmod -R +rwx /app/shared

WORKDIR /app/shared/src/commonMain/kotlin/app/ft/ftapp
RUN ls -l
#
RUN echo "package app.ft.ftapp" > HiddenCredentials.kt \
    && echo "const val clid = \"$YANDEX_CLID\"" >> HiddenCredentials.kt \
    && echo "const val key_yandex = \"$KEY_YANDEX\"" >> HiddenCredentials.kt \
    && echo "const val yandex_mapkit = \"$YANDEX_MAPKIT\"" >> HiddenCredentials.kt
RUN ls -l

# RUN cat HiddenCredentials.kt
WORKDIR /app

RUN ./android-sdk-linux/cmdline-tools/bin/emulator -list-avds

RUN echo "Running android UI tests."
# CMD ["./gradlew", "connectedAndroidTest"]

RUN ./gradlew connectedAndroidTest
RUN echo "Running android unit tests."
# Run the tests
RUN ./gradlew test
