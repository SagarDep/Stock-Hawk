#!/bin/bash -e


echo "Executing build.sh"

export CI_LANE_DEV=${CI_LANE_DEV:="alpha"}
export CI_LANE_STAGE=${CI_LANE_STAGE:="beta"}
export CI_LANE_PROD=${CI_LANE_PROD:="deploy"}
export CI_BRANCH=${GIT_BRANCH:=""}


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


if [[ "$CI_BRANCH" == *"master" ]]; then
    echo ">> This is a PROD build"
else
    echo ">> This is DEV build."
fi

