#!/bin/bash -e


echo "Executing build.sh"

export CI_LANE_DEV=${CI_LANE_DEV:="alpha"}
export CI_LANE_STAGE=${CI_LANE_STAGE:="beta"}
export CI_LANE_PROD=${CI_LANE_PROD:="deploy"}

############################################
## Define custom fastlane parameters here ##
############################################


exists()
{
  command -v "$1" >/dev/null 2>&1
}

if exists fastlane; then
    echo ">> Found existing version of fastlane"
    fastlane -v
else
    echo ">> fastlane not found"
    echo ">> install fastlane now? "
    gem install fastlane -NV
fi

if exists ruby; then
        echo ">> Installing fastlane via ruby"
        gem install fastlane -NV
    else
        echo $GIT_BRANCH
        echo ">> Could not find a ruby installation."
        echo ">> Please install ruby on the CI system so that we can install fastlane or provide your own installation of fastlane."
        exit 1
fi

