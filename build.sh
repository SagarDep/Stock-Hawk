#!/bin/bash -e

echo "Executing build.sh"

export CI_LANE_DEV=${CI_LANE_DEV:="alpha"}
export CI_LANE_PROD=${CI_LANE_PROD:="deploy"}

export CI_BRANCH=${GIT_BRANCH:=""}
export CI_BRANCH_PROD=${CI_BRANCH_PROD:="master"}
export CI_BRANCH_STAGE=${CI_BRANCH_DEV:="develop"*}

export CI_CONFIGURATION=${CI_CONFIGURATION:="Release"}
export CI_VERSION_CODE=${BUILD_NUMBER:="1"}

export CI_GOOGLE_PLAY_CHANNEL=${CI_GOOGLE_PLAY_CHANNEL:="beta"}
CI_LANE=${CI_LANE:=""}



exists()
{
  command -v "$1" >/dev/null 2>&1
}

# Install fastlane if not already installed
if exists fastlane; then
    echo ">> Found existing version of fastlane"
    fastlane -v
else
    INSTALL_FASTLANE=${CI_INSTALL_FASTLANE:=true}
    echo ">> fastlane not found"
    echo ">> install fastlane now? $CI_INSTALL_FASTLANE"
fi

export CI_INSTALL_FASTLANE=${INSTALL_FASTLANE:=false}

if [[ $CI_INSTALL_FASTLANE == "true" ]]; then
    if exists ruby; then
            echo ">> Installing fastlane via ruby"
            gem install fastlane -NV
        else
            echo ">> Could not find a ruby installation."
            echo ">> Please install ruby on the CI system so that we can install fastlane or provide your own installation of fastlane."
            exit 1
    fi
fi


# Set the appropriate lane for fastlane

if [[ "$CI_BRANCH" == $CI_BRANCH_PROD ]]; then
    echo ">> This is a PROD build"
    CI_LANE=$CI_LANE_PROD
elif [[ "$CI_BRANCH" == $CI_BRANCH_DEV ]]; then
    echo ">> This is DEV build."
    CI_LANE=$CI_LANE_DEV
    CI_GOOGLE_PLAY_CHANNEL=""
else
    echo ">> This is an UNKNOWN build."
    echo ">> Make sure that the CI_BRANCH environment variable is set to the working branch!"
fi



# Run fastlane

if [[ "$CI_LANE" != "" ]]; then
    echo ">> Building lane $CI_LANE"
    export CI_FAST_LANE_CMD="fastlane android $CI_LANE configuration:$CI_CONFIGURATION versionCode:$CI_VERSION_CODE googlePlayChannel:$CI_GOOGLE_PLAY_CHANNEL"
    echo ">> Running command: $CI_FAST_LANE_CMD"
    $CI_FASTLANE_CMD

else
    echo ">> Could not find a lane to build!"
    echo ">> DEVELOP"
    echo ">> branch: $CI_BRANCH_DEV lane: $CI_LANE_DEV"
    echo ">> PROD"
    echo ">> branch: $CI_BRANCH_PROD lane: $CI_LANE_PROD"
fi


if [[ $? -ne 0 ]]; then
    echo "Error: Build fail."
    exit 1
fi
